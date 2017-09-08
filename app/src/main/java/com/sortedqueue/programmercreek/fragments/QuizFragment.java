package com.sortedqueue.programmercreek.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.ProgramListActivity;
import com.sortedqueue.programmercreek.adapter.QuizRecyclerAdapter;
import com.sortedqueue.programmercreek.asynctask.ProgramFetcherTask;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.QuizModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener;
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener;
import com.sortedqueue.programmercreek.interfaces.UIProgramFetcherListener;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;
import com.sortedqueue.programmercreek.interfaces.WizardNavigationListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekAnalytics;
import com.sortedqueue.programmercreek.util.ShuffleList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 03/01/17.
 */

public class QuizFragment extends Fragment implements UIUpdateListener, UIProgramFetcherListener, TestCompletionListener {

    ArrayList<String> mProgramList;
    ArrayList<String> mShuffleProgramList;
    ArrayList<String> mProgramExplanationList;
    Button mCheckSolutionBtn = null;
    Button mTimerBtn = null;
    long remainingTime = 0;
    long time = 0;
    long interval = 0;
    CountDownTimer mCountDownTimer;
    boolean mWizard = false;
    int mQuizMode = -1;
    @BindView(R.id.circular_progress_bar)
    ProgressBar circularProgressBar;
    @BindView(R.id.progressTextView)
    TextView progressTextView;
    @BindView(R.id.progressLayout)
    FrameLayout progressLayout;
    @BindView(R.id.quizRecyclerView)
    RecyclerView quizRecyclerView;
    private ArrayList<QuizModel> quizModels;
    private QuizRecyclerAdapter quizRecyclerAdapter;
    private Bundle bundle;
    private int mInvokeMode;
    private ArrayList<ProgramTable> mProgramTableList;
    private ModuleDetailsScrollPageListener moduleDetailsScrollPageListener;
    private String TAG = QuizFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_quiz, container, false);
        ButterKnife.bind(this, view);

        mCheckSolutionBtn = (Button) view.findViewById(R.id.checkQuizButton);
        mTimerBtn = (Button) view.findViewById(R.id.timerButton);
        mTimerBtn.setText("00:00");
        mTimerBtn.setEnabled(false);


        mWizard = bundle.getBoolean(ProgramListActivity.KEY_WIZARD);

        if (bundle != null) {
            mQuizMode = bundle.getInt(ProgramListActivity.KEY_QUIZ_TYPE, ProgramListActivity.KEY_QUIZ_PROGRAM_CODE_QUESTION);
            initQuiz(mQuizMode);
        }

        return view;
    }

    public ArrayList<String> getmProgramList() {
        ArrayList<String> programList = new ArrayList<>();
        for( ProgramTable programTable : mProgramTableList ) {
            programList.add(programTable.getProgram_Line());
        }
        return programList;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    int mProgramIndex = 0;
    private ProgramIndex program_index;

    @SuppressLint("SimpleDateFormat")
    private void initQuiz(int quizMode) {
        mInvokeMode = bundle.getInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST, -1);
        progressLayout.setVisibility(View.GONE);
        mProgramTableList = bundle.getParcelableArrayList(ProgrammingBuddyConstants.KEY_USER_PROGRAM);
        if( mProgramTableList != null && mProgramTableList.size() > 0 ) {
            program_index = (ProgramIndex) bundle.get(ProgrammingBuddyConstants.KEY_PROG_ID);
            mProgramIndex = program_index.getProgram_index();
            initUI(mProgramTableList);
        }
        else {
            if( mInvokeMode  == ProgrammingBuddyConstants.KEY_LESSON ) {
                mWizard = false;
                new FirebaseDatabaseHandler(getContext()).getProgramIndexInBackGround(bundle.getInt(ProgrammingBuddyConstants.KEY_PROG_ID),
                        new FirebaseDatabaseHandler.GetProgramIndexListener() {
                            @Override
                            public void onSuccess(ProgramIndex programIndex) {
                                program_index = programIndex;
                                mProgramIndex = programIndex.getProgram_index();
                                getProgramTables();
                            }
                            @Override
                            public void onError(DatabaseError databaseError) {
                                CommonUtils.displayToast(getContext(), R.string.unable_to_fetch_data);
                            }
                        });
            }
            else {
                program_index = (ProgramIndex) bundle.get(ProgrammingBuddyConstants.KEY_PROG_ID);
                mProgramIndex = program_index.getProgram_index();
                getProgramTables();
            }
        }

    }

    private void getProgramTables() {
        new FirebaseDatabaseHandler(getContext())
                .getProgramTablesInBackground(mProgramIndex, new FirebaseDatabaseHandler.GetProgramTablesListener() {
                    @Override
                    public void onSuccess(ArrayList<ProgramTable> programTables) {
                        {
                            initUI( programTables );
                        }
                    }

                    @Override
                    public void onError(DatabaseError databaseError) {

                    }
                });
    }

    private void initUI(ArrayList<ProgramTable> program_TableList) {
        if(  program_index != null ) {
            try {
                CreekAnalytics.logEvent(TAG, new JSONObject(new Gson().toJson(program_index)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        getActivity().setTitle("Quiz : " + program_index.getProgram_Description());
        mProgramTableList = program_TableList;
        if (program_TableList != null && program_TableList.size() > 0) {
            mProgramList = new ArrayList<String>();
            mProgramExplanationList = new ArrayList<String>();

            Iterator<ProgramTable> iterator = program_TableList.iterator();
            String programLine = null;
            if (mQuizMode == ProgramListActivity.KEY_QUIZ_DESCRIPTION_QUESTION) {
                while (iterator.hasNext()) {
                    ProgramTable newProgram_Table = iterator.next();
                    programLine = newProgram_Table.getProgram_Line().trim();
                    if (programLine.trim().equals("{") == false && programLine.trim().equals("}") == false) {
                        mProgramList.add(programLine);
                        mProgramExplanationList.add(newProgram_Table.getProgram_Line_Description());
                    }

                }
            } else {
                while (iterator.hasNext()) {
                    ProgramTable newProgram_Table = iterator.next();
                    programLine = newProgram_Table.getProgram_Line().trim();
                    if (programLine.trim().equals("{") == false && programLine.trim().equals("}") == false) {
                        mProgramList.add(newProgram_Table.getProgram_Line_Description());
                        mProgramExplanationList.add(programLine);
                    }
                }
            }

        }

        final int programSize = mProgramList.size();
        ArrayList<String> optionList = null;
        quizModels = new ArrayList<>();
        for (int questionIndex = 0; questionIndex < programSize; questionIndex++) {
            QuizModel quizModel = new QuizModel();
            quizModel.setQuestionIndex(questionIndex + 1);
            quizModel.setQuestion(mProgramList.get(questionIndex).trim());
            quizModel.setCorrectOption(mProgramTableList.get(questionIndex).getProgram_Line());
            //Get Options List
            optionList = getOptionsList(questionIndex, programSize);
            //Shuffle Options
            mShuffleProgramList = ShuffleList.shuffleList(optionList);
            quizModel.setOptionsList(mShuffleProgramList);
            quizModels.add(quizModel);
        }

        quizRecyclerView.setLayoutManager( new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        quizRecyclerAdapter = new QuizRecyclerAdapter(getContext(), quizModels, mProgramExplanationList, new QuizRecyclerAdapter.CustomQuizAdapterListner() {
            @Override
            public void onOptionSelected(int position, String option) {

                quizRecyclerAdapter.notifyItemChanged(position);
            }
        });
        quizRecyclerView.setAdapter(quizRecyclerAdapter);
        time = (programSize / 2) * 60 * 1000;
        interval = 1000;
        circularProgressBar.setMax((int) (time / 1000));

        mCountDownTimer = new CountDownTimer(time, interval) {

            @Override
            public void onTick(long millisUntilFinished) {
                mTimerBtn.setText("" + String.format("%d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                progressTextView.setText(mTimerBtn.getText());
                remainingTime = time - millisUntilFinished;
                circularProgressBar.setProgress((int) (remainingTime / 1000));
            }

            @Override
            public void onFinish() {

                mTimerBtn.setText("Time up");
                mTimerBtn.setVisibility(View.VISIBLE);
                progressLayout.setVisibility(View.GONE);
                if (mWizard == true) {
                    mTimerBtn.setText("Next");
                    mTimerBtn.setEnabled(true);
                    mTimerBtn.setOnClickListener(mNextBtnClickListener);
                }
                checkScore(programSize);
            }
        };

        mCheckSolutionBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showConfirmSubmitDialog(programSize, mCountDownTimer);
            }
        });

        if( mInvokeMode != ProgrammingBuddyConstants.KEY_LESSON ) {
            progressLayout.setVisibility(View.VISIBLE);
            mCountDownTimer.start();
        }



    }

    View.OnClickListener mNextBtnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.timerButton:
                    navigateToMatchMaker();
                    break;
            }

        }
    };


    protected void checkScore(int programSize) {
        int score = 0;

        quizRecyclerAdapter.setAnswerChecked(true);
        int i = 0;
        for( QuizModel quizModel : quizModels ) {
            if( quizModel.getSelectedOption().equals(mProgramExplanationList.get(i++)) ) {
                score++;
            }
        }

        String message = null;
        switch (score) {

            case 0:
                message = "Your Score is " + score + "/" + programSize + " in " + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                        TimeUnit.MILLISECONDS.toSeconds(remainingTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))) + ", Good Luck next time";
                break;

            default:
                message = "Your Score is " + score + "/" + programSize + " in " + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                        TimeUnit.MILLISECONDS.toSeconds(remainingTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))) + ", Keep Working..";
                break;

        }

        if (score == programSize) {
            message = "Your Score is " + score + "/" + programSize + " in " + String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                    TimeUnit.MILLISECONDS.toSeconds(remainingTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))) + ", Fantastic Work..!!";
        }

        AuxilaryUtils.displayResultAlert(getActivity(), "Quiz Complete", message, score, programSize);
        quizComplete = true;

        if( moduleDetailsScrollPageListener != null ) moduleDetailsScrollPageListener.toggleFABDrawable();

        if (mWizard == true) {
            mTimerBtn.setText("Next");
            mTimerBtn.setEnabled(true);
            mTimerBtn.setVisibility(View.VISIBLE);
            progressLayout.setVisibility(View.GONE);
            mTimerBtn.setOnClickListener(mNextBtnClickListener);
        }
        mCheckSolutionBtn.setEnabled(false);
    }

    protected void navigateToMatchMaker() {
        Bundle newIntentBundle = new Bundle();
        newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, program_index);
        newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true);
        newIntentBundle.putParcelableArrayList( ProgrammingBuddyConstants.KEY_USER_PROGRAM, bundle.getParcelableArrayList(ProgrammingBuddyConstants.KEY_USER_PROGRAM) );
        wizardNavigationListener.loadMatchMakerFragment(bundle);
    }

    private ArrayList<String> getOptionsList(int questionIndex, int programSize) {
        ArrayList<String> optionList = new ArrayList<String>();
        for (int optionIndex = 0; optionIndex < 4; optionIndex++) {
            optionList.add(mProgramExplanationList.get(((questionIndex + optionIndex)) % programSize));
        }
        return optionList;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mCountDownTimer != null)
            mCountDownTimer.cancel();
        wizardNavigationListener = null;
    }

    boolean quizComplete = false;

    public void onBackPressed() {
        if (quizComplete == false) {
            AuxilaryUtils.showConfirmationDialog(getActivity());

        } else {
            getActivity().finish();
        }

    }

    private WizardNavigationListener wizardNavigationListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if( context instanceof WizardNavigationListener ) {
            wizardNavigationListener = (WizardNavigationListener) context;
        }
    }

    @Override
    public void updateUI(ArrayList<ProgramTable> program_TableList) {

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mTimerBtn.setText("00:00");
            mCheckSolutionBtn.setEnabled(true);
        }
        initUI(program_TableList);
    }


    private void showConfirmSubmitDialog(final int programSize, final CountDownTimer countDownTimer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkScore(programSize);
                countDownTimer.cancel();
            }

        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //dialog.dismiss();
            }
        });

        builder.setMessage("Are you sure you want to submit the Quiz?");
        builder.setTitle(getActivity().getTitle());
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.show();

    }

    @Override
    public void updateUI() {
        new ProgramFetcherTask(getContext(), mProgramIndex).execute();
    }

    @Override
    public int isTestComplete() {
        return quizComplete ? ProgrammingBuddyConstants.KEY_QUIZ : -1;
    }

    public void setModuleDetailsScrollPageListener(ModuleDetailsScrollPageListener moduleDetailsScrollPageListener) {
        this.moduleDetailsScrollPageListener = moduleDetailsScrollPageListener;
    }
}

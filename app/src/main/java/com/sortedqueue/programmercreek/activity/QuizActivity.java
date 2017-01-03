package com.sortedqueue.programmercreek.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.QuizRecyclerAdapter;
import com.sortedqueue.programmercreek.asynctask.ProgramFetcherTask;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.QuizModel;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.database.operations.DataBaseInsertAsyncTask;
import com.sortedqueue.programmercreek.interfaces.UIProgramFetcherListener;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.ShuffleList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

public class QuizActivity extends AppCompatActivity implements UIUpdateListener, UIProgramFetcherListener {

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
    DatabaseHandler mDatabaseHandler;
    @Bind(R.id.circular_progress_bar)
    ProgressBar circularProgressBar;
    @Bind(R.id.progressTextView)
    TextView progressTextView;
    @Bind(R.id.progressLayout)
    FrameLayout progressLayout;
    @Bind(R.id.quizRecyclerView)
    RecyclerView quizRecyclerView;
    private ArrayList<QuizModel> quizModels;
    private QuizRecyclerAdapter quizRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);

        mCheckSolutionBtn = (Button) findViewById(R.id.checkQuizButton);
        mTimerBtn = (Button) findViewById(R.id.timerButton);
        mTimerBtn.setText("00:00");
        mTimerBtn.setEnabled(false);

        Bundle bundle = getIntent().getExtras();
        mWizard = bundle.getBoolean(ProgramListActivity.KEY_WIZARD);

        if (bundle != null) {
            mQuizMode = bundle.getInt(ProgramListActivity.KEY_QUIZ_TYPE);
            initQuiz(mQuizMode);
        }
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

    }

    int mProgramIndex = 0;
    private Program_Index program_index;

    @SuppressLint("SimpleDateFormat")
    private void initQuiz(int quizMode) {

        Bundle newProgramActivityBundle = getIntent().getExtras();
        program_index = (Program_Index) newProgramActivityBundle.get(ProgrammingBuddyConstants.KEY_PROG_ID);
        mProgramIndex = program_index.getIndex();

        //PrettifyHighlighter prettifyHighlighter = new PrettifyHighlighter();
        if (mDatabaseHandler == null) {
            mDatabaseHandler = new DatabaseHandler(QuizActivity.this);
        }
        new ProgramFetcherTask(this, mDatabaseHandler, mProgramIndex).execute();
    }

    private void initUI(List<Program_Table> program_TableList) {

        setTitle("Quiz : " + AuxilaryUtils.getProgramTitle(mProgramIndex, QuizActivity.this, mDatabaseHandler));

        if (program_TableList != null && program_TableList.size() > 0) {
            mProgramList = new ArrayList<String>();
            mProgramExplanationList = new ArrayList<String>();

            Iterator<Program_Table> iterator = program_TableList.iterator();
            String programLine = null;
            if (mQuizMode == ProgramListActivity.KEY_QUIZ_DESCRIPTION_QUESTION) {
                while (iterator.hasNext()) {
                    Program_Table newProgram_Table = iterator.next();
                    programLine = newProgram_Table.getProgram_Line().trim();
                    if (programLine.trim().equals("{") == false && programLine.trim().equals("}") == false) {
                        mProgramList.add(programLine);
                        mProgramExplanationList.add(newProgram_Table.getProgram_Line_Description());
                    }

                }
            } else {
                while (iterator.hasNext()) {
                    Program_Table newProgram_Table = iterator.next();
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
            //Get Options List
            optionList = getOptionsList(questionIndex, programSize);
            //Shuffle Options
            mShuffleProgramList = ShuffleList.shuffleList(optionList);
            quizModel.setOptionsList(mShuffleProgramList);
            quizModels.add(quizModel);
        }

        quizRecyclerView.setLayoutManager( new LinearLayoutManager(QuizActivity.this, LinearLayoutManager.VERTICAL, false));
        quizRecyclerAdapter = new QuizRecyclerAdapter(QuizActivity.this, quizModels, mProgramExplanationList, new QuizRecyclerAdapter.CustomQuizAdapterListner() {
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

        mCheckSolutionBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showConfirmSubmitDialog(programSize, mCountDownTimer);
            }
        });

        mCountDownTimer.start();


    }

    OnClickListener mNextBtnClickListener = new OnClickListener() {

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

        AuxilaryUtils.displayResultAlert(QuizActivity.this, "Quiz Complete", message, score, programSize);
        quizComplete = true;
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
        Intent intent = new Intent(QuizActivity.this, WizardActivity.class);
        intent.putExtras(newIntentBundle);
        startActivity(intent);
        this.finish();
    }

    private ArrayList<String> getOptionsList(int questionIndex, int programSize) {
        ArrayList<String> optionList = new ArrayList<String>();
        for (int optionIndex = 0; optionIndex < 4; optionIndex++) {
            optionList.add(mProgramExplanationList.get(((questionIndex + optionIndex)) % programSize));
        }
        return optionList;
    }

    boolean quizComplete = false;

    @Override
    public void onBackPressed() {
        if (quizComplete == false) {
            AuxilaryUtils.showConfirmationDialog(this);
            if (mCountDownTimer != null)
                mCountDownTimer.cancel();
        } else {
            finish();
        }

    }


    private void showConfirmSubmitDialog(final int programSize, final CountDownTimer countDownTimer) {
        Builder builder = new Builder(this);
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
        builder.setTitle(this.getTitle());
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.show();

    }

    @Override
    public void updateUI() {
        new ProgramFetcherTask(this, mDatabaseHandler, mProgramIndex).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.program, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_refresh_database:
                new DataBaseInsertAsyncTask(this, mProgramIndex, this).execute();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void updateUI(List<Program_Table> program_TableList) {

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mTimerBtn.setText("00:00");
            mCheckSolutionBtn.setEnabled(true);
        }

        if (program_TableList == null || program_TableList.size() == 0) {
            new DataBaseInsertAsyncTask(this, mProgramIndex, this).execute();
        } else {
            initUI(program_TableList);
        }
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

}

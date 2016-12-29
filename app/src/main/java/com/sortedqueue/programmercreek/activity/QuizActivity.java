package com.sortedqueue.programmercreek.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.asynctask.ProgramFetcherTask;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Program_Table;
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

public class QuizActivity extends Activity implements UIUpdateListener, UIProgramFetcherListener {

    LinearLayout mQuizLinearLayout = null;

    ArrayList<String> mProgramList;
    ArrayList<String> mShuffleProgramList;
    ArrayList<String> mProgramExplanationList;
    Button mCheckSolutionBtn = null;
    Button mTimerBtn = null;
    RadioGroup[] mOptionRadioGroupList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);

        mQuizLinearLayout = (LinearLayout) findViewById(R.id.quizLinearLayout);

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

    int mProgram_Index = 0;

    @SuppressLint("SimpleDateFormat")
    private void initQuiz(int quizMode) {

        Bundle newProgramActivityBundle = getIntent().getExtras();
        mProgram_Index = newProgramActivityBundle.getInt(ProgrammingBuddyConstants.KEY_PROG_ID);

        //PrettifyHighlighter prettifyHighlighter = new PrettifyHighlighter();
        if (mDatabaseHandler == null) {
            mDatabaseHandler = new DatabaseHandler(QuizActivity.this);
        }
        new ProgramFetcherTask(this, mDatabaseHandler, mProgram_Index).execute();
    }

    private void initUI(List<Program_Table> program_TableList) {

        if (mQuizLinearLayout != null) {
            mQuizLinearLayout.removeAllViews();
        }
        setTitle("Quiz : " + AuxilaryUtils.getProgramTitle(mProgram_Index, QuizActivity.this, mDatabaseHandler));

        if (program_TableList != null && program_TableList.size() > 0) {
            mProgramList = new ArrayList<String>();
            mProgramExplanationList = new ArrayList<String>();

            Iterator<Program_Table> iterator = program_TableList.iterator();
            String programLine = null;
            if (mQuizMode == ProgramListActivity.KEY_QUIZ_DESCRIPTION_QUESTION) {
                while (iterator.hasNext()) {
                    Program_Table newProgram_Table = iterator.next();
                    programLine = newProgram_Table.getProgram_Line().trim();
                    if (programLine.equals("{") == false && programLine.equals("}") == false) {
                        mProgramList.add(programLine);
                        mProgramExplanationList.add(newProgram_Table.getProgram_Line_Description());
                    }

                }
            } else {
                while (iterator.hasNext()) {
                    Program_Table newProgram_Table = iterator.next();
                    programLine = newProgram_Table.getProgram_Line().trim();
                    if (programLine.equals("{") == false && programLine.equals("}") == false) {
                        mProgramList.add(newProgram_Table.getProgram_Line_Description());
                        mProgramExplanationList.add(programLine);
                    }
                }
            }

        }

        final int programSize = mProgramList.size();
        final TextView[] questionTextViewList = new TextView[programSize];
        mOptionRadioGroupList = new RadioGroup[programSize];
        RadioButton[] optionRadioButtonList = new RadioButton[(programSize * 4)];
        ArrayList<String> optionList = null;

        for (int questionIndex = 0; questionIndex < programSize; questionIndex++) {

            questionTextViewList[questionIndex] = new TextView(QuizActivity.this);
            questionTextViewList[questionIndex].setTextColor(Color.parseColor("#6D4C41"));
            questionTextViewList[questionIndex].setText((questionIndex + 1) + ". " + mProgramList.get(questionIndex).trim());
            questionTextViewList[questionIndex].setBackgroundResource(R.drawable.question_view);
            questionTextViewList[questionIndex].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            mQuizLinearLayout.addView(questionTextViewList[questionIndex]);

            mOptionRadioGroupList[questionIndex] = new RadioGroup(QuizActivity.this);

            //Get Options List
            optionList = getOptionsList(questionIndex, programSize);
            //Shuffle Options
            mShuffleProgramList = ShuffleList.shuffleList(optionList);

            for (int optionIndex = 0; optionIndex < 4; optionIndex++) {
                optionRadioButtonList[(questionIndex + optionIndex)] = new RadioButton(QuizActivity.this);
                optionRadioButtonList[(questionIndex + optionIndex)].setText(mShuffleProgramList.get(optionIndex));
                mOptionRadioGroupList[questionIndex].addView(optionRadioButtonList[(questionIndex + optionIndex)]);
                //mOptionRadioGroupList[questionIndex].setBackgroundResource(R.drawable.error);
            }

            mQuizLinearLayout.addView(mOptionRadioGroupList[(questionIndex)]);
        }

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
        String optionSelected;
        for (int i = 0; i < programSize; i++) {
            RadioButton optionSelectedRadioButton = (RadioButton) (findViewById(mOptionRadioGroupList[i].getCheckedRadioButtonId()));
            if (optionSelectedRadioButton == null) {
                continue;
            }
            optionSelected = optionSelectedRadioButton.getText().toString().trim();
            if (optionSelected.equals(mProgramExplanationList.get(i).trim()) == true) {
                optionSelectedRadioButton.setTextColor(Color.BLUE);
                score++;
            } else {
                optionSelectedRadioButton.setTextColor(Color.RED);
            }
            for (int j = 0; j < mOptionRadioGroupList[i].getChildCount(); j++) {
                RadioButton answerRadioButton = ((RadioButton) mOptionRadioGroupList[i].getChildAt(j));
                answerRadioButton.setEnabled(false);
                optionSelected = answerRadioButton.getText().toString().trim();

                if (optionSelected.equals(mProgramExplanationList.get(i).trim()) == true)
                    answerRadioButton.setTextColor(Color.BLUE);
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

        AuxilaryUtils.displayResultAlert( QuizActivity.this, QuizActivity.this.getTitle().toString(), message, score, programSize );
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
        newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_PROG_ID, mProgram_Index);
        newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true);
        Intent intent = new Intent(QuizActivity.this, MatchMakerActivity.class);
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
        new ProgramFetcherTask(this, mDatabaseHandler, mProgram_Index).execute();
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
                new DataBaseInsertAsyncTask(this, mProgram_Index, this).execute();
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
            new DataBaseInsertAsyncTask(this, mProgram_Index, this).execute();
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

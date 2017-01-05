package com.sortedqueue.programmercreek.fragments;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.ProgramListActivity;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;
import com.sortedqueue.programmercreek.interfaces.WizardNavigationListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.PrettifyHighlighter;
import com.sortedqueue.programmercreek.util.ShuffleList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-01-02.
 */

public class MatchMakerFragment extends Fragment implements UIUpdateListener {

    @Bind(R.id.checkQuizButton)
    Button checkQuizButton;
    @Bind(R.id.circular_progress_bar)
    ProgressBar circularProgressBar;
    @Bind(R.id.progressTextView)
    TextView progressTextView;
    @Bind(R.id.progressLayout)
    FrameLayout progressLayout;
    @Bind(R.id.timerButton)
    Button timerButton;
    private LinearLayout mMatchMakerLeftLinearLayout = null;
    private LinearLayout mMatchMakerRightLinearLayout = null;
    ArrayList<String> mProgramList;
    ArrayList<String> mProgramCheckList;
    ArrayList<String> mShuffleProgramList;
    ArrayList<String> mProgramExplanationList;
    ProgramIndex mProgramIndex;
    View mSelectedProgramLineView = null;
    PrettifyHighlighter mHighlighter = new PrettifyHighlighter();
    long remainingTime = 0;
    long time = 0;
    long interval = 0;
    TextView[] mSummaryTextViewList;
    TextView[] mProgramLineTextViewList;
    CountDownTimer mCountDownTimer;
    boolean mWizard = false;
    int mProgramSize;
    private Bundle newProgramActivityBundle;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_match_maker, container, false);
        ButterKnife.bind(this, view);
        mMatchMakerLeftLinearLayout = (LinearLayout) view.findViewById(R.id.leftLinearLyt);
        mMatchMakerRightLinearLayout = (LinearLayout) view.findViewById(R.id.rightLinearLyt);
        checkQuizButton = (Button) view.findViewById(R.id.checkQuizButton);
        timerButton = (Button) view.findViewById(R.id.timerButton);
        initUI();
        return view;
    }

    public void setBundle( Bundle bundle ) {
        this.newProgramActivityBundle = bundle;
    }
    
    private void initUI() {

        if( newProgramActivityBundle.getInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST, -1) == ProgrammingBuddyConstants.KEY_LESSON ) {
            mWizard = false;
            new FirebaseDatabaseHandler(getContext()).getProgramIndexInBackGround(newProgramActivityBundle.getInt(ProgrammingBuddyConstants.KEY_PROG_ID), new FirebaseDatabaseHandler.GetProgramIndexListener() {
                @Override
                public void onSuccess(ProgramIndex programIndex) {
                    mProgramIndex = programIndex;
                    getProgramTables();
                }
            });
        }
        else {
            mProgramIndex = (ProgramIndex) newProgramActivityBundle.get(ProgrammingBuddyConstants.KEY_PROG_ID);
            mWizard = newProgramActivityBundle.getBoolean(ProgramListActivity.KEY_WIZARD, false);

            getProgramTables();
        }


    }

    private void getProgramTables() {
        List<ProgramTable> program_TableList = new FirebaseDatabaseHandler(getContext()).getProgramTables(mProgramIndex.getProgram_index());
        {
            initUI(program_TableList);
        }
    }

    private void initUI(List<ProgramTable> program_TableList) {
        if (program_TableList != null && program_TableList.size() > 0) {
            //TODO
            getActivity().setTitle("Match : " + mProgramIndex.getProgram_Description());

            mProgramList = new ArrayList<String>();
            mProgramExplanationList = new ArrayList<String>();
            mProgramCheckList = new ArrayList<String>();

            ProgramTable newProgram_Table;
            String programLine_html;
            Iterator<ProgramTable> iteraor = program_TableList.iterator();
            while (iteraor.hasNext()) {
                newProgram_Table = iteraor.next();
                programLine_html = newProgram_Table.getProgram_Line_Html();
                if (newProgram_Table.getProgram_Line().contains("<")) {
                    programLine_html = newProgram_Table.getProgram_Line().trim();
                }
                mProgramList.add(programLine_html);
                mProgramCheckList.add(newProgram_Table.getProgram_Line());
                mProgramExplanationList.add(newProgram_Table.getProgram_Line_Description());
            }
        }

        mShuffleProgramList = new ArrayList<String>();
        mShuffleProgramList.addAll(mProgramList);
        mShuffleProgramList = ShuffleList.shuffleList(mShuffleProgramList);

        

        if (mMatchMakerLeftLinearLayout != null) {
            mMatchMakerLeftLinearLayout.removeAllViews();
        }

        mProgramSize = mShuffleProgramList.size();
        mProgramLineTextViewList = new TextView[mProgramSize];
        String programLine = null;
        for (int i = 0; i < mProgramSize; i++) {
            mProgramLineTextViewList[i] = new TextView(getContext());
            setProgramLineTextViewParms(mProgramLineTextViewList[i]);
            mMatchMakerLeftLinearLayout.addView(mProgramLineTextViewList[i]);
            programLine = mShuffleProgramList.get(i);
            if (programLine.contains("font") == false) {
                mProgramLineTextViewList[i].setText(programLine);
                mProgramLineTextViewList[i].setTextColor(Color.parseColor("#006699"));
            } else {
                mProgramLineTextViewList[i].setText(Html.fromHtml(/*mHighlighter.highlight("c", programLine)*/programLine));
            }
            mProgramLineTextViewList[i].setId(i);
        }


        //mMatchMakerRightLinearLayout.setLayoutParams(layoutParams);
        if (mMatchMakerRightLinearLayout != null) {
            mMatchMakerRightLinearLayout.removeAllViews();
        }

        mSummaryTextViewList = new TextView[mProgramSize];
        for (int i = 0; i < mProgramSize; i++) {
            mSummaryTextViewList[i] = new TextView(getContext());
            setSummaryTextViewParms(mSummaryTextViewList[i]);
            mMatchMakerRightLinearLayout.addView(mSummaryTextViewList[i]);
            mSummaryTextViewList[i].setText(mProgramExplanationList.get(i));
            mProgramLineTextViewList[i].setId(mProgramSize + i);
        }


        timerButton.setText("00:00");
        timerButton.setEnabled(false);

        time = (mProgramSize / 2) * 60 * 1000;
        interval = 1000;
        circularProgressBar.setMax((int) (time / 1000));
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            checkQuizButton.setEnabled(true);
        }

        mCountDownTimer = new CountDownTimer(time, interval) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerButton.setText("" + String.format("%d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                progressTextView.setText(timerButton.getText());
                remainingTime = time - millisUntilFinished;
                circularProgressBar.setProgress((int) (remainingTime / 1000));
            }


            @Override
            public void onFinish() {

                timerButton.setText("Time up");
                timerButton.setVisibility(View.VISIBLE);
                progressLayout.setVisibility(View.GONE);
                if (mWizard == true) {
                    timerButton.setText("Next");
                    timerButton.setVisibility(View.VISIBLE);
                    progressLayout.setVisibility(View.GONE);
                    timerButton.setEnabled(true);
                    timerButton.setOnClickListener(mNextBtnClickListener);
                }

                checkScore(mProgramSize);
            }
        };


        checkQuizButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showConfirmSubmitDialog(mProgramSize, mCountDownTimer);
            }
        });

        mCountDownTimer.start();
    }
    //On Long Press Clear the program line in summary text view
    View.OnLongClickListener mSummaryTextViewLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {

			/*TextView textView = (TextView) v;
			int lineNo = textView.getId() - mProgramSize;
			if( lineNo >= 0 && lineNo <= mProgramExplanationList.size())
			textView.setText(mProgramExplanationList.get(lineNo));*/
            // TODO Auto-generated method stub
            return false;
        }
    };

    View.OnClickListener mSummaryTextViewOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (mSelectedProgramLineView != null) {
                TextView summaryTextView = (TextView) v;
                summaryTextView.setText(((TextView) mSelectedProgramLineView).getText());
                mSelectedProgramLineView.setOnTouchListener(null);
            }
        }
    };
    private void setSummaryTextViewParms(TextView textView) {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 4, 8, 4);
        textView.setLayoutParams(layoutParams);
        textView.setBackgroundResource(R.drawable.choice);
        textView.setGravity(Gravity.CENTER);
        textView.setOnDragListener(new ChoiceDragListener());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setOnClickListener(mSummaryTextViewOnClickListener);
        textView.setOnLongClickListener(mSummaryTextViewLongClickListener);

    }

    protected void checkScore(int programSize) {

        int CheckSolution = 0;
        int matchScore = 0;

        for (int i = 0; i < programSize; i++) {
            //Log.d("SOLUTION", mSummaryTextViewList[i].getText().toString().trim());
            if (mProgramCheckList.get(i).trim().equals(mSummaryTextViewList[i].getText().toString().trim()) == true) {
                CheckSolution++;
                matchScore++;
                mSummaryTextViewList[i].setBackgroundResource(R.drawable.answer);
            } else {
                mSummaryTextViewList[i].setBackgroundResource(R.drawable.error);
                String programLine = mSummaryTextViewList[i].getText().toString();
                for (int j = 0; j < programSize; j++) {
                    if (programLine.equals(mProgramLineTextViewList[j].getText().toString()) == true) {
                        mProgramLineTextViewList[j].setBackgroundResource(R.drawable.option);
                    }
                }
                mSummaryTextViewList[i].setText(mProgramExplanationList.get(i));
                CheckSolution--;
            }
        }
        if (CheckSolution == programSize) {
            if (quizComplete == false) {
                AuxilaryUtils.displayResultAlert(getActivity(), "Match Complete", "Congratulations.. Your Score is " + CheckSolution + "/" + programSize + " in " + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                        TimeUnit.MILLISECONDS.toSeconds(remainingTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))) + ", Fantastic Work..!!", CheckSolution, programSize );
                checkQuizButton.setEnabled(false);
            } else {
                AuxilaryUtils.displayResultAlert(getActivity(), "Match Complete", "Congratulations.. Your Score is " + CheckSolution + "/" + programSize + ", Fantastic Work..!!",
                        CheckSolution, programSize);
                checkQuizButton.setEnabled(false);
            }

        } else {
            //Toast.makeText(getContext(), "Please check the program again...", Toast.LENGTH_SHORT).show();
            String message = null;

            if (quizComplete == false) {
                switch (matchScore) {

                    case 0:
                        message = "Your Score is " + matchScore + "/" + programSize + " in " + String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                                TimeUnit.MILLISECONDS.toSeconds(remainingTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))) + ", Good Luck next time";
                        break;

                    default:
                        message = "Your Score is " + matchScore + "/" + programSize + " in " + String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                                TimeUnit.MILLISECONDS.toSeconds(remainingTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))) + ", Keep Working..";
                        break;

                }

            } else {
                switch (matchScore) {

                    case 0:
                        message = "Your Score is " + matchScore + "/" + programSize + ", Good Luck next time";
                        break;

                    default:
                        message = "Your Score is " + matchScore + "/" + programSize + ", Keep Working..";
                        break;

                }
            }
            AuxilaryUtils.displayResultAlert(getActivity(), "Match Complete", message, matchScore, programSize);
        }
        if (mWizard == true) {
            timerButton.setText("Next");
            timerButton.setVisibility(View.VISIBLE);
            progressLayout.setVisibility(View.GONE);
            timerButton.setEnabled(true);
            timerButton.setOnClickListener(mNextBtnClickListener);
        }
        quizComplete = true;

    }

    View.OnClickListener mNextBtnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.timerButton:
                    navigateToTest();
                    break;
            }

        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

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



    protected void navigateToTest() {

        Bundle newIntentBundle = new Bundle();
        newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, mProgramIndex);
        newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true);

        wizardNavigationListener.loadTestFragment(newIntentBundle);

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

            }
        });

        builder.setMessage("Are you sure you want to submit the Match?");
        builder.setTitle(getActivity().getTitle());
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.show();

    }
    
    View.OnLongClickListener mProgramLineLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View view) {

            view.setOnTouchListener(new ChoiceTouchListener());
            view.setBackgroundResource(R.drawable.selected);
            mSelectedProgramLineView = view;
            //Toast.makeText(getContext(), "Selected", Toast.LENGTH_SHORT).show();

            //To start drag immediately after a view has been selected.
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);

            return false;
        }
    };

    private class ChoiceDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:
                    //handle the dragged view being dropped over a drop view
                    View view = (View) event.getLocalState();
                    //stop displaying the view where it was before it was dragged
                    //view.setVisibility(View.INVISIBLE);
                    //view dragged item is being dropped on
                    TextView dropTarget = (TextView) v;
                    //view being dragged and dropped
                    TextView dropped = (TextView) view;
                    dropped.setOnTouchListener(null);
                    mSelectedProgramLineView = null;
                    //update the text in the target view to reflect the data being dropped
                    dropTarget.setText(dropped.getText());
                    //make it bold to highlight the fact that an item has been dropped
                    dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                    //if an item has already been dropped here, there will be a tag
                    Object tag = dropTarget.getTag();
                    //if there is already an item here, set it back visible in its original place
                    if (tag != null) {
                        //the tag is the view id already dropped here
                        int existingID = (Integer) tag;
                        //set the original view visible again
                        view.findViewById(existingID).setVisibility(View.VISIBLE);
                    }
                    //set the tag in the target view being dropped on - to the ID of the view being dropped
                    dropTarget.setTag(dropped.getId());
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    boolean quizComplete = false;

    /**
     * ChoiceTouchListener will handle touch events on draggable views
     */
    private final class ChoiceTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				/*
				 * Drag details: we only need default behavior
				 * - clip data could be set to pass data as part of drag
				 * - shadow can be tailored
				 */
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    private void setProgramLineTextViewParms(TextView textView) {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 4, 8, 4);
        textView.setLayoutParams(layoutParams);
        textView.setBackgroundResource(R.drawable.option);
        textView.setGravity(Gravity.CENTER);
        textView.setOnLongClickListener(mProgramLineLongClickListener);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    public void updateUI() {
        ArrayList<ProgramTable> program_TableList = new FirebaseDatabaseHandler(getContext()).getProgramTables(mProgramIndex.getProgram_index());
        int prevProgramSize = 0;
        prevProgramSize = program_TableList.size();
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            program_TableList = new FirebaseDatabaseHandler(getContext()).getProgramTables(mProgramIndex.getProgram_index());
            if (prevProgramSize == program_TableList.size()) {
                break;
            }
        } while (true);

        if (program_TableList != null) {
            initUI(program_TableList);
        }

    }
}

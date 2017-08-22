package com.sortedqueue.programmercreek.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener;
import com.sortedqueue.programmercreek.interfaces.UIProgramFetcherListener;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.ShuffleList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.kbiakov.codeview.CodeView;

public class FillBlankFragment extends Fragment implements UIProgramFetcherListener, CompoundButton.OnCheckedChangeListener, TestCompletionListener {


    @BindView(R.id.headerTextView)
    TextView headerTextView;
    @BindView(R.id.programBlankLineTextView)
    TextView programBlankLineTextView;
    @BindView(R.id.programCodeView)
    CodeView programCodeView;

    @BindView(R.id.programLayout)
    CardView programLayout;
    @BindView(R.id.answer1RadioButton1)
    RadioButton answer1RadioButton1;
    @BindView(R.id.answer1RadioButton2)
    RadioButton answer1RadioButton2;
    @BindView(R.id.answer1RadioButton3)
    RadioButton answer1RadioButton3;
    @BindView(R.id.answer2RadioButton1)
    RadioButton answer2RadioButton1;
    @BindView(R.id.answer2RadioButton2)
    RadioButton answer2RadioButton2;
    @BindView(R.id.answer2RadioButton3)
    RadioButton answer2RadioButton3;
    @BindView(R.id.answer3RadioButton1)
    RadioButton answer3RadioButton1;
    @BindView(R.id.answer3RadioButton2)
    RadioButton answer3RadioButton2;
    @BindView(R.id.answer3RadioButton3)
    RadioButton answer3RadioButton3;
    @BindView(R.id.answer4RadioButton1)
    RadioButton answer4RadioButton1;
    @BindView(R.id.answer4RadioButton2)
    RadioButton answer4RadioButton2;
    @BindView(R.id.answer4RadioButton3)
    RadioButton answer4RadioButton3;
    @BindView(R.id.checkButton)
    Button checkButton;
    @BindView(R.id.option1TextView)
    TextView option1TextView;
    @BindView(R.id.option2TextView)
    TextView option2TextView;
    @BindView(R.id.answerLayout1)
    LinearLayout answerLayout1;
    @BindView(R.id.option3TextView)
    TextView option3TextView;
    @BindView(R.id.option4TextView)
    TextView option4TextView;
    @BindView(R.id.answerLayout2)
    LinearLayout answerLayout2;
    @BindView(R.id.option5TextView)
    TextView option5TextView;
    @BindView(R.id.answer5RadioButton1)
    RadioButton answer5RadioButton1;
    @BindView(R.id.answer5RadioButton2)
    RadioButton answer5RadioButton2;
    @BindView(R.id.answer5RadioButton3)
    RadioButton answer5RadioButton3;
    @BindView(R.id.option6TextView)
    TextView option6TextView;
    @BindView(R.id.answer6RadioButton1)
    RadioButton answer6RadioButton1;
    @BindView(R.id.answer6RadioButton2)
    RadioButton answer6RadioButton2;
    @BindView(R.id.answer6RadioButton3)
    RadioButton answer6RadioButton3;
    @BindView(R.id.answerLayout3)
    LinearLayout answerLayout3;
    @BindView(R.id.option7TextView)
    TextView option7TextView;
    @BindView(R.id.answer7RadioButton1)
    RadioButton answer7RadioButton1;
    @BindView(R.id.answer7RadioButton2)
    RadioButton answer7RadioButton2;
    @BindView(R.id.answer7RadioButton3)
    RadioButton answer7RadioButton3;
    @BindView(R.id.option8TextView)
    TextView option8TextView;
    @BindView(R.id.answer8RadioButton1)
    RadioButton answer8RadioButton1;
    @BindView(R.id.answer8RadioButton2)
    RadioButton answer8RadioButton2;
    @BindView(R.id.answer8RadioButton3)
    RadioButton answer8RadioButton3;
    @BindView(R.id.answerLayout4)
    LinearLayout answerLayout4;
    private int mProgram_Index = 1;
    private ArrayList<String> shuffleList;
    private ArrayList<String> fillBlanksQuestionList;
    private boolean isAnswered;
    private FirebaseDatabaseHandler firebaseDatabaseHandler;
    private boolean wizardMode;
    private CreekUserStats creekUserStats;
    private ProgramIndex mProgramIndex;

    private String programLanguage;
    private Bundle bundle;

    public FillBlankFragment() {
        // Required empty public constructor
    }

    public void setmProgram_Index(int mProgram_Index) {
        this.mProgram_Index = mProgram_Index;
    }

    public int getmProgram_Index() {
        return mProgram_Index;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fill_blank, container, false);
        ButterKnife.bind(this, view);
        programLanguage = CreekApplication.getCreekPreferences().getProgramLanguage();
        if( programLanguage.equals("c++") ) {
            programLanguage = "cpp";
        }
        getProgram();


        return view;
    }

    private void getProgram() {

        if( answerLayout1 != null ) {
            answerLayout1.setAlpha(0.0f);
            answerLayout2.setAlpha(0.0f);
            answerLayout3.setAlpha(0.0f);
            answerLayout4.setAlpha(0.0f);
        }
        if( bundle != null ) {
            mProgramIndex = bundle.getParcelable(ProgrammingBuddyConstants.KEY_PROG_ID);
            mProgram_Index = mProgramIndex.getProgram_index();
            ArrayList<ProgramTable> programTables = bundle.getParcelableArrayList(ProgrammingBuddyConstants.KEY_USER_PROGRAM);
            if( programTables != null && programTables.size() > 0 ) {
                updateUI(programTables);
            }
            else {
                firebaseDatabaseHandler = new FirebaseDatabaseHandler(getContext());
                firebaseDatabaseHandler.getProgramIndexInBackGround(mProgram_Index, new FirebaseDatabaseHandler.GetProgramIndexListener() {
                    @Override
                    public void onSuccess(ProgramIndex programIndex) {
                        headerTextView.setText(programIndex.getProgram_Description());
                        mProgramIndex = programIndex;
                        firebaseDatabaseHandler.getProgramTablesInBackground(mProgram_Index, new FirebaseDatabaseHandler.GetProgramTablesListener() {
                            @Override
                            public void onSuccess(ArrayList<ProgramTable> programTables) {
                                updateUI(programTables);
                            }

                            @Override
                            public void onError(DatabaseError databaseError) {
                                CommonUtils.displaySnackBar(getActivity(), R.string.unable_to_fetch_data);
                            }
                        });
                    }

                    @Override
                    public void onError(DatabaseError databaseError) {
                        CommonUtils.displaySnackBar(getActivity(), R.string.unable_to_fetch_data);
                    }
                });
            }
        }
        else {
            firebaseDatabaseHandler = new FirebaseDatabaseHandler(getContext());
            firebaseDatabaseHandler.getProgramIndexInBackGround(mProgram_Index, new FirebaseDatabaseHandler.GetProgramIndexListener() {
                @Override
                public void onSuccess(ProgramIndex programIndex) {
                    headerTextView.setText(programIndex.getProgram_Description());
                    mProgramIndex = programIndex;
                    firebaseDatabaseHandler.getProgramTablesInBackground(mProgram_Index, new FirebaseDatabaseHandler.GetProgramTablesListener() {
                        @Override
                        public void onSuccess(ArrayList<ProgramTable> programTables) {
                            updateUI(programTables);
                        }

                        @Override
                        public void onError(DatabaseError databaseError) {
                            CommonUtils.displaySnackBar(getActivity(), R.string.unable_to_fetch_data);
                        }
                    });
                }

                @Override
                public void onError(DatabaseError databaseError) {
                    CommonUtils.displaySnackBar(getActivity(), R.string.unable_to_fetch_data);
                }
            });
        }



    }

    private ArrayList<Integer> fillBlanksIndex;

    @Override
    public void updateUI(ArrayList<ProgramTable> program_TableList) {

        ArrayList<ProgramTable> originalList = new ArrayList<>();
        for (ProgramTable program_table : program_TableList) {
            originalList.add(program_table);
        }
        fillBlanksQuestionList = ProgramTable.getFillTheBlanksList(program_TableList, new ProgramTable.FillBlanksSolutionListener() {
            @Override
            public void getSolution(ArrayList<Integer> fillBlanksIndex) {
                FillBlankFragment.this.fillBlanksIndex = fillBlanksIndex;
                int index = 0;
                option1TextView.setText("Options for line : " + (fillBlanksIndex.get(index++) + 1));
                option2TextView.setText("Options for line : " + (fillBlanksIndex.get(index++) + 1));
                option3TextView.setText("Options for line : " + (fillBlanksIndex.get(index++) + 1));
                option4TextView.setText("Options for line : " + (fillBlanksIndex.get(index++) + 1));
                option5TextView.setText("Options for line : " + (fillBlanksIndex.get(index++) + 1));
                option6TextView.setText("Options for line : " + (fillBlanksIndex.get(index++) + 1));
                option7TextView.setText("Options for line : " + (fillBlanksIndex.get(index++) + 1));
                option8TextView.setText("Options for line : " + (fillBlanksIndex.get(index++) + 1));
            }
        });

        ArrayList<ProgramTable> solution_tables = new ArrayList<>();

        for (int i = 0; i < fillBlanksQuestionList.size(); i++) {
            String program_table = fillBlanksQuestionList.get(i);
            ProgramTable solution_table = originalList.get(i);
            if (program_table.equals("")) {
                solution_tables.add(solution_table);
            }
        }


        setSolutionViews(solution_tables);
        setupQuestionViews(fillBlanksQuestionList);
        animateViews();
    }

    private void animateViews() {
        int delay = 0;
        int standardDelay = 270;
        if( answerLayout1 != null ) {
            initAnimations(answerLayout1, delay);
            delay = delay + standardDelay;
            initAnimations(answerLayout2, delay);
            delay = delay + standardDelay;
            initAnimations(answerLayout3, delay);
            delay = delay + standardDelay;
            initAnimations(answerLayout4, delay);
        }
    }

    private void initAnimations(LinearLayout frameLayout, int delay) {
        frameLayout.animate().setStartDelay(delay).setDuration(350).alpha(1.0f);
    }

    private void setupQuestionViews(ArrayList<String> fillBlanksQuestionList) {
        if (fillBlanksQuestionList.size() > 4) {
            int position = 1;
            String programDescription = "";
            programBlankLineTextView.setText("");
            for (String program_table : fillBlanksQuestionList) {
                if (position == 1) {
                    programDescription += position + ". " + program_table;
                    programBlankLineTextView.append((position++) + ". ");
                } else {
                    programDescription += "\n" + position + ". " + program_table;
                    programBlankLineTextView.append("\n" + (position++) + ". ");
                }
                programBlankLineTextView.append(program_table.trim());
            }
            
            /*programCodeView.setOptions(Options.Default.get(getContext())
                    .withLanguage(programLanguage)
                    .withCode(programBlankLineTextView.getText().toString())
                    .withTheme(ColorTheme.MONOKAI));*/
        }
    }

    private ArrayList<ProgramTable> solutionTables;

    private void setSolutionViews(ArrayList<ProgramTable> solutionTables) {

        this.solutionTables = solutionTables;

        answer1RadioButton1.setOnCheckedChangeListener(this);
        answer1RadioButton2.setOnCheckedChangeListener(this);
        answer1RadioButton3.setOnCheckedChangeListener(this);
        answer2RadioButton1.setOnCheckedChangeListener(this);
        answer2RadioButton2.setOnCheckedChangeListener(this);
        answer2RadioButton3.setOnCheckedChangeListener(this);
        answer3RadioButton1.setOnCheckedChangeListener(this);
        answer3RadioButton2.setOnCheckedChangeListener(this);
        answer3RadioButton3.setOnCheckedChangeListener(this);
        answer4RadioButton1.setOnCheckedChangeListener(this);
        answer4RadioButton2.setOnCheckedChangeListener(this);
        answer4RadioButton3.setOnCheckedChangeListener(this);
        answer5RadioButton1.setOnCheckedChangeListener(this);
        answer5RadioButton2.setOnCheckedChangeListener(this);
        answer5RadioButton3.setOnCheckedChangeListener(this);
        answer6RadioButton1.setOnCheckedChangeListener(this);
        answer6RadioButton2.setOnCheckedChangeListener(this);
        answer6RadioButton3.setOnCheckedChangeListener(this);
        answer7RadioButton1.setOnCheckedChangeListener(this);
        answer7RadioButton2.setOnCheckedChangeListener(this);
        answer7RadioButton3.setOnCheckedChangeListener(this);
        answer8RadioButton1.setOnCheckedChangeListener(this);
        answer8RadioButton2.setOnCheckedChangeListener(this);
        answer8RadioButton3.setOnCheckedChangeListener(this);

        ArrayList<String> solutions = new ArrayList<>();
        for (ProgramTable program_table : solutionTables) {
            solutions.add(program_table.getProgram_Line().trim());
        }

        shuffleList = new ArrayList<>();
        shuffleList.add(solutions.get(0));
        shuffleList.add(solutions.get(1));
        shuffleList.add(solutions.get(2));

        shuffleList = ShuffleList.shuffleList(shuffleList);

        answer1RadioButton1.setText(shuffleList.get(0));
        answer1RadioButton2.setText(shuffleList.get(1));
        answer1RadioButton3.setText(shuffleList.get(2));


        shuffleList = new ArrayList<>();
        shuffleList.add(solutions.get(1));
        shuffleList.add(solutions.get(2));
        shuffleList.add(solutions.get(3));

        shuffleList = ShuffleList.shuffleList(shuffleList);

        answer2RadioButton1.setText(shuffleList.get(0));
        answer2RadioButton2.setText(shuffleList.get(1));
        answer2RadioButton3.setText(shuffleList.get(2));

        shuffleList = new ArrayList<>();
        shuffleList.add(solutions.get(2));
        shuffleList.add(solutions.get(3));
        shuffleList.add(solutions.get(4));

        shuffleList = ShuffleList.shuffleList(shuffleList);

        answer3RadioButton1.setText(shuffleList.get(0));
        answer3RadioButton2.setText(shuffleList.get(1));
        answer3RadioButton3.setText(shuffleList.get(2));

        shuffleList = new ArrayList<>();
        shuffleList.add(solutions.get(3));
        shuffleList.add(solutions.get(4));
        shuffleList.add(solutions.get(5));

        shuffleList = ShuffleList.shuffleList(shuffleList);

        answer4RadioButton1.setText(shuffleList.get(0));
        answer4RadioButton2.setText(shuffleList.get(1));
        answer4RadioButton3.setText(shuffleList.get(2));


        /*********************************************************/

        shuffleList = new ArrayList<>();
        shuffleList.add(solutions.get(4));
        shuffleList.add(solutions.get(5));
        shuffleList.add(solutions.get(6));

        shuffleList = ShuffleList.shuffleList(shuffleList);

        answer5RadioButton1.setText(shuffleList.get(0));
        answer5RadioButton2.setText(shuffleList.get(1));
        answer5RadioButton3.setText(shuffleList.get(2));


        shuffleList = new ArrayList<>();
        shuffleList.add(solutions.get(5));
        shuffleList.add(solutions.get(6));
        shuffleList.add(solutions.get(7));

        shuffleList = ShuffleList.shuffleList(shuffleList);

        answer6RadioButton1.setText(shuffleList.get(0));
        answer6RadioButton2.setText(shuffleList.get(1));
        answer6RadioButton3.setText(shuffleList.get(2));

        shuffleList = new ArrayList<>();
        shuffleList.add(solutions.get(6));
        shuffleList.add(solutions.get(7));
        shuffleList.add(solutions.get(0));

        shuffleList = ShuffleList.shuffleList(shuffleList);

        answer7RadioButton1.setText(shuffleList.get(0));
        answer7RadioButton2.setText(shuffleList.get(1));
        answer7RadioButton3.setText(shuffleList.get(2));

        shuffleList = new ArrayList<>();
        shuffleList.add(solutions.get(7));
        shuffleList.add(solutions.get(0));
        shuffleList.add(solutions.get(1));

        shuffleList = ShuffleList.shuffleList(shuffleList);

        answer8RadioButton1.setText(shuffleList.get(0));
        answer8RadioButton2.setText(shuffleList.get(1));
        answer8RadioButton3.setText(shuffleList.get(2));



        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSolution();
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private ArrayList<String> solutionsList;

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (solutionsList == null) {
            solutionsList = new ArrayList<>();
            int index  = 0;
            solutionsList.add(index++, "");
            solutionsList.add(index++, "");
            solutionsList.add(index++, "");
            solutionsList.add(index++, "");
            solutionsList.add(index++, "");
            solutionsList.add(index++, "");
            solutionsList.add(index++, "");
            solutionsList.add(index++, "");
        }

        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.answer1RadioButton1:
                    solutionsList.set(0, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(0), buttonView.getText().toString());
                    break;
                case R.id.answer1RadioButton2:
                    solutionsList.set(0, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(0), buttonView.getText().toString());
                    break;
                case R.id.answer1RadioButton3:
                    solutionsList.set(0, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(0), buttonView.getText().toString());
                    break;
                case R.id.answer2RadioButton1:
                    solutionsList.set(1, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(1), buttonView.getText().toString());
                    break;
                case R.id.answer2RadioButton2:
                    solutionsList.set(1, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(1), buttonView.getText().toString());
                    break;
                case R.id.answer2RadioButton3:
                    solutionsList.set(1, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(1), buttonView.getText().toString());
                    break;
                case R.id.answer3RadioButton1:
                    solutionsList.set(2, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(2), buttonView.getText().toString());
                    break;
                case R.id.answer3RadioButton2:
                    solutionsList.set(2, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(2), buttonView.getText().toString());
                    break;
                case R.id.answer3RadioButton3:
                    solutionsList.set(2, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(2), buttonView.getText().toString());
                    break;
                case R.id.answer4RadioButton1:
                    solutionsList.set(3, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(3), buttonView.getText().toString());
                    break;
                case R.id.answer4RadioButton2:
                    solutionsList.set(3, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(3), buttonView.getText().toString());
                    break;
                case R.id.answer4RadioButton3:
                    solutionsList.set(3, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(3), buttonView.getText().toString());
                    break;
                /*******************************************************************************/
                case R.id.answer5RadioButton1:
                    solutionsList.set(4, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(4), buttonView.getText().toString());
                    break;
                case R.id.answer5RadioButton2:
                    solutionsList.set(4, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(4), buttonView.getText().toString());
                    break;
                case R.id.answer5RadioButton3:
                    solutionsList.set(4, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(4), buttonView.getText().toString());
                    break;
                case R.id.answer6RadioButton1:
                    solutionsList.set(5, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(5), buttonView.getText().toString());
                    break;
                case R.id.answer6RadioButton2:
                    solutionsList.set(5, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(5), buttonView.getText().toString());
                    break;
                case R.id.answer6RadioButton3:
                    solutionsList.set(5, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(5), buttonView.getText().toString());
                    break;
                case R.id.answer7RadioButton1:
                    solutionsList.set(6, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(6), buttonView.getText().toString());
                    break;
                case R.id.answer7RadioButton2:
                    solutionsList.set(6, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(6), buttonView.getText().toString());
                    break;
                case R.id.answer7RadioButton3:
                    solutionsList.set(6, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(6), buttonView.getText().toString());
                    break;
                case R.id.answer8RadioButton1:
                    solutionsList.set(7, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(7), buttonView.getText().toString());
                    break;
                case R.id.answer8RadioButton2:
                    solutionsList.set(7, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(7), buttonView.getText().toString());
                    break;
                case R.id.answer8RadioButton3:
                    solutionsList.set(7, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(7), buttonView.getText().toString());
                    break;
            }
            setupQuestionViews(fillBlanksQuestionList);
        }

    }

    public void checkSolution() {
        if (solutionsList != null && solutionsList.size() > 0) {

            String checkedSolution = "";
            int i = 0;
            int rightAnswers = 0;
            if (answer1RadioButton1.isChecked()) {
                checkedSolution = answer1RadioButton1.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer1RadioButton1.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer1RadioButton1.setTextColor(Color.RED);
                }
            }
            if (answer1RadioButton2.isChecked()) {
                checkedSolution = answer1RadioButton2.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer1RadioButton2.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer1RadioButton2.setTextColor(Color.RED);
                }
            }
            if (answer1RadioButton3.isChecked()) {
                checkedSolution = answer1RadioButton3.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer1RadioButton3.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer1RadioButton3.setTextColor(Color.RED);
                }
            }
            i++;
            if (answer2RadioButton1.isChecked()) {
                checkedSolution = answer2RadioButton1.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer2RadioButton1.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer2RadioButton1.setTextColor(Color.RED);
                }
            }
            if (answer2RadioButton2.isChecked()) {
                checkedSolution = answer2RadioButton2.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer2RadioButton2.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer2RadioButton2.setTextColor(Color.RED);
                }
            }
            if (answer2RadioButton3.isChecked()) {
                checkedSolution = answer2RadioButton3.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer2RadioButton3.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer2RadioButton3.setTextColor(Color.RED);
                }
            }
            i++;
            if (answer3RadioButton1.isChecked()) {
                checkedSolution = answer3RadioButton1.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer3RadioButton1.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer3RadioButton1.setTextColor(Color.RED);
                }
            }
            if (answer3RadioButton2.isChecked()) {
                checkedSolution = answer3RadioButton2.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer3RadioButton2.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer3RadioButton2.setTextColor(Color.RED);
                }
            }
            if (answer3RadioButton3.isChecked()) {
                checkedSolution = answer3RadioButton3.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer3RadioButton3.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer3RadioButton3.setTextColor(Color.RED);
                }
            }
            i++;
            if (answer4RadioButton1.isChecked()) {
                checkedSolution = answer4RadioButton1.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer4RadioButton1.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer4RadioButton1.setTextColor(Color.RED);
                }
            }
            if (answer4RadioButton2.isChecked()) {
                checkedSolution = answer4RadioButton2.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer4RadioButton2.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer4RadioButton2.setTextColor(Color.RED);
                }
            }
            if (answer4RadioButton3.isChecked()) {
                checkedSolution = answer4RadioButton3.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer4RadioButton3.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer4RadioButton3.setTextColor(Color.RED);
                }
            }
            /**********************************************************************************/
            i++;
            if (answer5RadioButton1.isChecked()) {
                checkedSolution = answer5RadioButton1.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer5RadioButton1.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer5RadioButton1.setTextColor(Color.RED);
                }
            }
            if (answer5RadioButton2.isChecked()) {
                checkedSolution = answer5RadioButton2.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer5RadioButton2.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer5RadioButton2.setTextColor(Color.RED);
                }
            }
            if (answer5RadioButton3.isChecked()) {
                checkedSolution = answer5RadioButton3.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer5RadioButton3.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer5RadioButton3.setTextColor(Color.RED);
                }
            }
            i++;
            if (answer6RadioButton1.isChecked()) {
                checkedSolution = answer6RadioButton1.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer6RadioButton1.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer6RadioButton1.setTextColor(Color.RED);
                }
            }
            if (answer6RadioButton2.isChecked()) {
                checkedSolution = answer6RadioButton2.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer6RadioButton2.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer6RadioButton2.setTextColor(Color.RED);
                }
            }
            if (answer6RadioButton3.isChecked()) {
                checkedSolution = answer6RadioButton3.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer6RadioButton3.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer6RadioButton3.setTextColor(Color.RED);
                }
            }
            i++;
            if (answer7RadioButton1.isChecked()) {
                checkedSolution = answer7RadioButton1.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer7RadioButton1.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer7RadioButton1.setTextColor(Color.RED);
                }
            }
            if (answer7RadioButton2.isChecked()) {
                checkedSolution = answer7RadioButton2.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer7RadioButton2.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer7RadioButton2.setTextColor(Color.RED);
                }
            }
            if (answer7RadioButton3.isChecked()) {
                checkedSolution = answer7RadioButton3.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer7RadioButton3.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer7RadioButton3.setTextColor(Color.RED);
                }
            }
            i++;
            if (answer8RadioButton1.isChecked()) {
                checkedSolution = answer8RadioButton1.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer8RadioButton1.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer8RadioButton1.setTextColor(Color.RED);
                }
            }
            if (answer8RadioButton2.isChecked()) {
                checkedSolution = answer8RadioButton2.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer8RadioButton2.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer8RadioButton2.setTextColor(Color.RED);
                }
            }
            if (answer8RadioButton3.isChecked()) {
                checkedSolution = answer8RadioButton3.getText().toString();
                if (checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim())) {
                    answer8RadioButton3.setTextColor(Color.GREEN);
                    rightAnswers++;
                } else {
                    answer8RadioButton3.setTextColor(Color.RED);
                }
            }
            isAnswered = true;
            String message = "Congratulations, you scored : " + rightAnswers + "/8";
            switch (rightAnswers) {
                case 1:
                case 2:
                    message = "You need improvement, retry again";
                    break;
                case 3:
                case 4:
                    message = "You are half way there, Keep coming back";
                    break;
                case 5:
                case 6:
                    message = "Almost perfect, you are one step away, retry";
                    break;
                case 7:
                case 8:
                    message = "Congratulations, you've got it";
                    break;
            }
            isAnswered = rightAnswers == 8;
            CommonUtils.displaySnackBar(getActivity(), message + ". You scored : " + rightAnswers + "/8");
            if (wizardMode) {
                updateCreekStats();
            }
            //Check and disable radio groups, mark correct answers - as green and wrong ones as red
        }


    }

    private void updateCreekStats() {

        creekUserStats = CreekApplication.getInstance().getCreekUserStats();
        switch (mProgramIndex.getProgram_Language().toLowerCase()) {
            case "c":
                creekUserStats.addToUnlockedCProgramIndexList(mProgramIndex.getProgram_index() + 1);
                break;
            case "cpp":
            case "c++":
                creekUserStats.addToUnlockedCppProgramIndexList(mProgramIndex.getProgram_index() + 1);
                break;
            case "java":
                creekUserStats.addToUnlockedJavaProgramIndexList(mProgramIndex.getProgram_index() + 1);
                break;
            case "usp":
                creekUserStats.addToUnlockedUspProgramIndexList(mProgramIndex.getProgram_index() + 1);
                break;
        }
        new FirebaseDatabaseHandler(getContext()).writeCreekUserStats(creekUserStats);
    }

    @Override
    public int isTestComplete() {
        return isAnswered ? ProgrammingBuddyConstants.KEY_FILL_BLANKS : -1;
    }

    public void setWizardMode(boolean wizardMode) {
        this.wizardMode = wizardMode;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
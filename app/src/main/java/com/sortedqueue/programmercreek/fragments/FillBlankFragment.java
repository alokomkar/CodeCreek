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
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener;
import com.sortedqueue.programmercreek.interfaces.UIProgramFetcherListener;
import com.sortedqueue.programmercreek.util.ShuffleList;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FillBlankFragment extends Fragment implements UIProgramFetcherListener, CompoundButton.OnCheckedChangeListener, TestCompletionListener {


    @Bind(R.id.programDescriptionTextView)
    TextView programDescriptionTextView;
    @Bind(R.id.descriptionLayout)
    CardView descriptionLayout;
    @Bind(R.id.programBlankLineTextView)
    TextView programBlankLineTextView;
    @Bind(R.id.programLayout)
    CardView programLayout;
    @Bind(R.id.answer1RadioButton1)
    RadioButton answer1RadioButton1;
    @Bind(R.id.answer1RadioButton2)
    RadioButton answer1RadioButton2;
    @Bind(R.id.answer1RadioButton3)
    RadioButton answer1RadioButton3;
    @Bind(R.id.answer2RadioButton1)
    RadioButton answer2RadioButton1;
    @Bind(R.id.answer2RadioButton2)
    RadioButton answer2RadioButton2;
    @Bind(R.id.answer2RadioButton3)
    RadioButton answer2RadioButton3;
    @Bind(R.id.answer3RadioButton1)
    RadioButton answer3RadioButton1;
    @Bind(R.id.answer3RadioButton2)
    RadioButton answer3RadioButton2;
    @Bind(R.id.answer3RadioButton3)
    RadioButton answer3RadioButton3;
    @Bind(R.id.answer4RadioButton1)
    RadioButton answer4RadioButton1;
    @Bind(R.id.answer4RadioButton2)
    RadioButton answer4RadioButton2;
    @Bind(R.id.answer4RadioButton3)
    RadioButton answer4RadioButton3;
    @Bind(R.id.checkButton)
    Button checkButton;
    private int mProgram_Index = 1;
    private ArrayList<String> shuffleList;
    private ArrayList<String> fillBlanksQuestionList;
    private boolean isAnswered;

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
        getProgram();
        View view = inflater.inflate(R.layout.fragment_fill_blank, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void getProgram() {
        new FirebaseDatabaseHandler(getContext())
                .getProgramTablesInBackground(mProgram_Index, new FirebaseDatabaseHandler.GetProgramTablesListener() {
                    @Override
                    public void onSuccess(ArrayList<ProgramTable> programTables) {
                        updateUI(programTables);
                    }

                    @Override
                    public void onError(DatabaseError databaseError) {
                    }
                });
    }

    private ArrayList<Integer> fillBlanksIndex;
    @Override
    public void updateUI(ArrayList<ProgramTable> program_TableList) {

        ArrayList<ProgramTable> originalList = new ArrayList<>();
        for( ProgramTable program_table : program_TableList ) {
            originalList.add(program_table);
        }
        fillBlanksQuestionList = ProgramTable.getFillTheBlanksList(program_TableList, new ProgramTable.FillBlanksSolutionListener() {
            @Override
            public void getSolution(ArrayList<Integer> fillBlanksIndex) {
                FillBlankFragment.this.fillBlanksIndex = fillBlanksIndex;
            }
        });

        ArrayList<ProgramTable> solution_tables = new ArrayList<>();

        for ( int i = 0; i < fillBlanksQuestionList.size(); i++ ) {
            String program_table = fillBlanksQuestionList.get(i);
            ProgramTable solution_table = originalList.get(i);
            if( program_table.equals("") ) {
                solution_tables.add(solution_table);
            }
        }


        setSolutionViews(solution_tables);
        setupQuestionViews(fillBlanksQuestionList);



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
            programDescriptionTextView.setText(programDescription);
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

        ArrayList<String> solutions = new ArrayList<>();
        for( ProgramTable program_table : solutionTables ) {
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
        shuffleList.add(solutions.get(0));

        shuffleList = ShuffleList.shuffleList(shuffleList);

        answer3RadioButton1.setText(shuffleList.get(0));
        answer3RadioButton2.setText(shuffleList.get(1));
        answer3RadioButton3.setText(shuffleList.get(2));

        shuffleList = new ArrayList<>();
        shuffleList.add(solutions.get(3));
        shuffleList.add(solutions.get(0));
        shuffleList.add(solutions.get(1));

        shuffleList = ShuffleList.shuffleList(shuffleList);

        answer4RadioButton1.setText(shuffleList.get(0));
        answer4RadioButton2.setText(shuffleList.get(1));
        answer4RadioButton3.setText(shuffleList.get(2));

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
        ButterKnife.unbind(this);
    }

    private ArrayList<String> solutionsList;
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if( solutionsList == null ) {
            solutionsList = new ArrayList<>();
            solutionsList.add(0, "");
            solutionsList.add(1, "");
            solutionsList.add(2, "");
            solutionsList.add(3, "");
        }

        if( isChecked ) {
            switch ( buttonView.getId() ) {
                case R.id.answer1RadioButton1 :
                    solutionsList.set(0, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(0), buttonView.getText().toString());
                    break;
                case R.id.answer1RadioButton2 :
                    solutionsList.set(0, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(0), buttonView.getText().toString());
                    break;
                case R.id.answer1RadioButton3 :
                    solutionsList.set(0, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(0), buttonView.getText().toString());
                    break;
                case R.id.answer2RadioButton1 :
                    solutionsList.set(1, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(1), buttonView.getText().toString());
                    break;
                case R.id.answer2RadioButton2 :
                    solutionsList.set(1, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(1), buttonView.getText().toString());
                    break;
                case R.id.answer2RadioButton3 :
                    solutionsList.set(1, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(1), buttonView.getText().toString());
                    break;
                case R.id.answer3RadioButton1 :
                    solutionsList.set(2, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(2), buttonView.getText().toString());
                    break;
                case R.id.answer3RadioButton2 :
                    solutionsList.set(2, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(2), buttonView.getText().toString());
                    break;
                case R.id.answer3RadioButton3 :
                    solutionsList.set(2, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(2), buttonView.getText().toString());
                    break;
                case R.id.answer4RadioButton1 :
                    solutionsList.set(3, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(3), buttonView.getText().toString());
                    break;
                case R.id.answer4RadioButton2 :
                    solutionsList.set(3, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(3), buttonView.getText().toString());
                    break;
                case R.id.answer4RadioButton3 :
                    solutionsList.set(3, buttonView.getText().toString());
                    fillBlanksQuestionList.set(fillBlanksIndex.get(3), buttonView.getText().toString());
                    break;
            }
            setupQuestionViews(fillBlanksQuestionList);
        }

    }

    public void checkSolution() {
        if( solutionsList != null && solutionsList.size() > 0 ) {

            String checkedSolution = "";
            int i = 0;
            if( answer1RadioButton1.isChecked() ) {
                checkedSolution = answer1RadioButton1.getText().toString();
                if( checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim()) ) {
                    answer1RadioButton1.setTextColor(Color.GREEN);
                }
                else {
                    answer1RadioButton1.setTextColor(Color.RED);
                }
            }
            if( answer1RadioButton2.isChecked() ) {
                checkedSolution = answer1RadioButton2.getText().toString();
                if( checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim()) ) {
                    answer1RadioButton2.setTextColor(Color.GREEN);
                }
                else {
                    answer1RadioButton2.setTextColor(Color.RED);
                }
            }
            if( answer1RadioButton3.isChecked() ) {
                checkedSolution = answer1RadioButton3.getText().toString();
                if( checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim()) ) {
                    answer1RadioButton3.setTextColor(Color.GREEN);
                }
                else {
                    answer1RadioButton3.setTextColor(Color.RED);
                }
            }
            i++;
            if( answer2RadioButton1.isChecked() ) {
                checkedSolution = answer2RadioButton1.getText().toString();
                if( checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim()) ) {
                    answer2RadioButton1.setTextColor(Color.GREEN);
                }
                else {
                    answer2RadioButton1.setTextColor(Color.RED);
                }
            }
            if( answer2RadioButton2.isChecked() ) {
                checkedSolution = answer2RadioButton2.getText().toString();
                if( checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim()) ) {
                    answer2RadioButton2.setTextColor(Color.GREEN);
                }
                else {
                    answer2RadioButton2.setTextColor(Color.RED);
                }
            }
            if( answer2RadioButton3.isChecked() ) {
                checkedSolution = answer2RadioButton3.getText().toString();
                if( checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim()) ) {
                    answer2RadioButton3.setTextColor(Color.GREEN);
                }
                else {
                    answer2RadioButton3.setTextColor(Color.RED);
                }
            }
            i++;
            if( answer3RadioButton1.isChecked() ) {
                checkedSolution = answer3RadioButton1.getText().toString();
                if( checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim()) ) {
                    answer3RadioButton1.setTextColor(Color.GREEN);
                }
                else {
                    answer3RadioButton1.setTextColor(Color.RED);
                }
            }
            if( answer3RadioButton2.isChecked() ) {
                checkedSolution = answer3RadioButton2.getText().toString();
                if( checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim()) ) {
                    answer3RadioButton2.setTextColor(Color.GREEN);
                }
                else {
                    answer3RadioButton2.setTextColor(Color.RED);
                }
            }
            if( answer3RadioButton3.isChecked() ) {
                checkedSolution = answer3RadioButton3.getText().toString();
                if( checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim()) ) {
                    answer3RadioButton3.setTextColor(Color.GREEN);
                }
                else {
                    answer3RadioButton3.setTextColor(Color.RED);
                }
            }
            i++;
            if( answer4RadioButton1.isChecked() ) {
                checkedSolution = answer4RadioButton1.getText().toString();
                if( checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim()) ) {
                    answer4RadioButton1.setTextColor(Color.GREEN);
                }
                else {
                    answer4RadioButton1.setTextColor(Color.RED);
                }
            }
            if( answer4RadioButton2.isChecked() ) {
                checkedSolution = answer4RadioButton2.getText().toString();
                if( checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim()) ) {
                    answer4RadioButton2.setTextColor(Color.GREEN);
                }
                else {
                    answer4RadioButton2.setTextColor(Color.RED);
                }
            }
            if( answer4RadioButton3.isChecked() ) {
                checkedSolution = answer4RadioButton3.getText().toString();
                if( checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim()) ) {
                    answer4RadioButton3.setTextColor(Color.GREEN);
                }
                else {
                    answer4RadioButton3.setTextColor(Color.RED);
                }
            }
            isAnswered = true;
            //Check and disable radio groups, mark correct answers - as green and wrong ones as red
        }


    }

    @Override
    public int isTestComplete() {
        return isAnswered ? ProgrammingBuddyConstants.KEY_FILL_BLANKS : -1;
    }
}
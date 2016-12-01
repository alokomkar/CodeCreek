package com.sortedqueue.programmercreek.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.asynctask.ProgramFetcherTask;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.UIProgramFetcherListener;
import com.sortedqueue.programmercreek.util.ShuffleList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FillBlankFragment extends Fragment implements UIProgramFetcherListener, CompoundButton.OnCheckedChangeListener {


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
    private DatabaseHandler mDatabaseHandler;
    private int mProgram_Index = 1;
    private ArrayList<String> shuffleList;

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
        if (mDatabaseHandler == null) {
            mDatabaseHandler = new DatabaseHandler(getContext());
        }
        new ProgramFetcherTask(getContext(), this, mDatabaseHandler, mProgram_Index).execute();
    }


    @Override
    public void updateUI(List<Program_Table> program_TableList) {

        ArrayList<Program_Table> originalList = new ArrayList<>(program_TableList);
        ArrayList<Program_Table> program_tables = Program_Table.getFillTheBlanksList(program_TableList);

        ArrayList<Program_Table> solution_tables = new ArrayList<>();

        for ( int i = 0; i < program_tables.size(); i++ ) {
            Program_Table program_table = program_tables.get(i);
            Program_Table solution_table = originalList.get(i);
            if( program_table.getProgram_Line().equals("") ) {
                solution_tables.add(solution_table);
            }
        }
        setSolutionViews(solution_tables);

        if (program_tables.size() > 4) {
            int position = 1;
            String programDescription = "";
            programBlankLineTextView.setText("");
            for (Program_Table program_table : program_tables) {
                if (position == 1) {
                    programDescription += position + ". " + program_table.getProgram_Line_Description();
                    programBlankLineTextView.append((position++) + ". ");
                } else {
                    programDescription += "\n" + position + ". " + program_table.getProgram_Line_Description();
                    programBlankLineTextView.append("\n" + (position++) + ". ");
                }
                programBlankLineTextView.append(program_table.getProgram_Line().trim());
            }
            programDescriptionTextView.setText(programDescription);
        }

    }

    private ArrayList<Program_Table> solutionTables;
    private void setSolutionViews(ArrayList<Program_Table> solutionTables) {

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
        for( Program_Table program_table : solutionTables ) {
            solutions.add(program_table.getProgram_Line());
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
                    solutionsList.add(0, buttonView.getText().toString());
                    break;
                case R.id.answer1RadioButton2 :
                    solutionsList.add(0, buttonView.getText().toString());
                    break;
                case R.id.answer1RadioButton3 :
                    solutionsList.add(0, buttonView.getText().toString());
                    break;
                case R.id.answer2RadioButton1 :
                    solutionsList.add(1, buttonView.getText().toString());
                    break;
                case R.id.answer2RadioButton2 :
                    solutionsList.add(1, buttonView.getText().toString());
                    break;
                case R.id.answer2RadioButton3 :
                    solutionsList.add(1, buttonView.getText().toString());
                    break;
                case R.id.answer3RadioButton1 :
                    solutionsList.add(2, buttonView.getText().toString());
                    break;
                case R.id.answer3RadioButton2 :
                    solutionsList.add(2, buttonView.getText().toString());
                    break;
                case R.id.answer3RadioButton3 :
                    solutionsList.add(2, buttonView.getText().toString());
                    break;
                case R.id.answer4RadioButton1 :
                    solutionsList.add(3, buttonView.getText().toString());
                    break;
                case R.id.answer4RadioButton2 :
                    solutionsList.add(3, buttonView.getText().toString());
                    break;
                case R.id.answer4RadioButton3 :
                    solutionsList.add(3, buttonView.getText().toString());
                    break;
            }
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
                if( checkedSolution.trim().equals(solutionTables.get(i++).getProgram_Line().trim()) ) {
                    answer1RadioButton3.setTextColor(Color.GREEN);
                }
                else {
                    answer1RadioButton3.setTextColor(Color.RED);
                }
            }
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
                if( checkedSolution.trim().equals(solutionTables.get(i++).getProgram_Line().trim()) ) {
                    answer2RadioButton3.setTextColor(Color.GREEN);
                }
                else {
                    answer2RadioButton3.setTextColor(Color.RED);
                }
            }
            if( answer3RadioButton1.isChecked() ) {
                checkedSolution = answer2RadioButton1.getText().toString();
                if( checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim()) ) {
                    answer3RadioButton1.setTextColor(Color.GREEN);
                }
                else {
                    answer3RadioButton1.setTextColor(Color.RED);
                }
            }
            if( answer3RadioButton2.isChecked() ) {
                checkedSolution = answer2RadioButton2.getText().toString();
                if( checkedSolution.trim().equals(solutionTables.get(i).getProgram_Line().trim()) ) {
                    answer3RadioButton2.setTextColor(Color.GREEN);
                }
                else {
                    answer3RadioButton2.setTextColor(Color.RED);
                }
            }
            if( answer3RadioButton3.isChecked() ) {
                checkedSolution = answer2RadioButton3.getText().toString();
                if( checkedSolution.trim().equals(solutionTables.get(i++).getProgram_Line().trim()) ) {
                    answer3RadioButton3.setTextColor(Color.GREEN);
                }
                else {
                    answer3RadioButton3.setTextColor(Color.RED);
                }
            }
            //Check and disable radio groups, mark correct answers - as green and wrong ones as red
        }


    }
}

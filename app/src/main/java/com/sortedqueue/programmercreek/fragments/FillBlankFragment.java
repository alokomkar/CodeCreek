package com.sortedqueue.programmercreek.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.asynctask.ProgramFetcherTask;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.UIProgramFetcherListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FillBlankFragment extends Fragment implements UIProgramFetcherListener {


    @Bind(R.id.programDescriptionTextView)
    TextView programDescriptionTextView;
    @Bind(R.id.descriptionLayout)
    CardView descriptionLayout;
    @Bind(R.id.programBlankLineTextView)
    TextView programBlankLineTextView;
    @Bind(R.id.programLayout)
    CardView programLayout;
    private DatabaseHandler mDatabaseHandler;
    private int mProgram_Index = 1;

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
        ArrayList<Program_Table> program_tables = new ArrayList<>();
        for( Program_Table program_table : program_TableList ) {
            if( !program_table.getProgram_Line().trim().equals("{") &&
                !program_table.getProgram_Line().trim().equals("}") ) {
                program_tables.add(program_table);
            }
        }

        if( program_tables.size() > 4 ) {
            int position = 1;
            String programDescription = "";
            programBlankLineTextView.setText("");
            for( Program_Table program_table : program_tables ) {
                if( position == 1 ) {
                    programDescription += position + ". " + program_table.getProgram_Line_Description();
                    programBlankLineTextView.append((position++) + ". ");
                }
                else {
                    programDescription += "\n" + position + ". " + program_table.getProgram_Line_Description();
                    programBlankLineTextView.append("\n" + (position++) + ". ");
                }
                programBlankLineTextView.append(program_table.getProgram_Line().trim());
            }
            programDescriptionTextView.setText(programDescription);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

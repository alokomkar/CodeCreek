package com.sortedqueue.programmercreek.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.MemorizeProgramActivity;
import com.sortedqueue.programmercreek.activity.ProgramActivity;
import com.sortedqueue.programmercreek.activity.ProgramListActivity;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.sortedqueue.programmercreek.activity.ProgramListActivity.KEY_WIZARD;

/**
 * Created by Alok on 08/08/17.
 */

public class SearchFragment extends Fragment implements TextWatcher, FirebaseDatabaseHandler.ProgramIndexInterface, CustomProgramRecyclerViewAdapter.AdapterClickListner {

    @BindView(R.id.searchEditText)
    EditText searchEditText;
    @BindView(R.id.noProgramsLayout)
    LinearLayout noProgramsLayout;
    @BindView(R.id.searchRecyclerView)
    RecyclerView searchRecyclerView;
    Unbinder unbinder;
    private CustomProgramRecyclerViewAdapter customProgramRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchEditText.addTextChangedListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if( s.toString().length() > 3 ) {
            new FirebaseDatabaseHandler(getContext()).searchPrograms(s.toString(), this);
        }
        else {
            if( customProgramRecyclerViewAdapter != null ) {
                customProgramRecyclerViewAdapter.clearAll();
                showEmptyView();
            }
        }
    }


    @Override
    public void getProgramIndexes(ArrayList<ProgramIndex> program_indices) {
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        customProgramRecyclerViewAdapter = new CustomProgramRecyclerViewAdapter(getContext(), program_indices, this);
        searchRecyclerView.setAdapter(customProgramRecyclerViewAdapter);
        showEmptyView();
    }

    private void showEmptyView() {
        int programsSize = customProgramRecyclerViewAdapter.getItemCount();
        noProgramsLayout.setVisibility(programsSize > 0 ? View.GONE : View.VISIBLE);
        searchRecyclerView.setVisibility(programsSize > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onError(DatabaseError databaseError) {

    }

    @Override
    public void onItemClick(int position) {

        Bundle newIntentBundle = new Bundle();
        Intent newIntent = null;
        newIntentBundle.putBoolean(KEY_WIZARD, true);
        newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, customProgramRecyclerViewAdapter.getItemAtPosition(position));
        newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, 1);
        newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, customProgramRecyclerViewAdapter.getItemAtPosition(position).getProgram_Description());
        newIntent = new Intent(getContext(), ProgramActivity.class);
        newIntent.putExtras(newIntentBundle);
        startActivity(newIntent);

    }
}

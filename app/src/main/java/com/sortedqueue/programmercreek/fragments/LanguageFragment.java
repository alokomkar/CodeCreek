package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 02/01/17.
 */

public class LanguageFragment extends Fragment {
    private static LanguageFragment instance;
    @Bind(R.id.languageSelectionSpinner)
    AppCompatSpinner languageSelectionSpinner;

    private String[] languageArray;
    private CreekPreferences creekPreferences;
    private FirebaseDatabaseHandler firebaseDatabaseHandler;

    public static LanguageFragment getInstance() {
        if (instance == null) {
            instance = new LanguageFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_language, container, false);
        ButterKnife.bind(this, view);
        creekPreferences = new CreekPreferences(getContext());
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.language_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        languageArray = getResources().getStringArray(R.array.language_array);
        languageSelectionSpinner.setAdapter(adapter);
        languageSelectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position != -1) {
                    String selectedString = languageArray[position];
                    selectedString = selectedString.replace(" Programming", "").toLowerCase();
                    creekPreferences.setProgramLanguage(selectedString);
                    initDB();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        int selectedPosition = -1;
        String selectedLanguage = creekPreferences.getProgramLanguage();

        for (String language : languageArray) {
            selectedPosition++;
            language = language.replace("Programming", "").trim().toLowerCase();
            if (language.equals(selectedLanguage)) {
                languageSelectionSpinner.setSelection(selectedPosition);
            }
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initDB() {
        logDebugMessage("Inserting all Programs Titles..");
        firebaseDatabaseHandler = new FirebaseDatabaseHandler(getContext());
        firebaseDatabaseHandler.initializeProgramIndexes(new FirebaseDatabaseHandler.ProgramIndexInterface() {
            @Override
            public void getProgramIndexes(ArrayList<Program_Index> program_indices) {

            }

            @Override
            public void onError(DatabaseError error) {

            }
        });
    }

    private void logDebugMessage(String message) {
        Log.d("LFragment", message);
    }
}

package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

public class LanguageFragment extends Fragment implements View.OnClickListener {
    private static LanguageFragment instance;
    @Bind(R.id.languageSelectionTextView)
    TextView languageSelectionTextView;
    @Bind(R.id.cProgrammingTextView)
    TextView cProgrammingTextView;
    @Bind(R.id.cProgressBar)
    ProgressBar cProgressBar;
    @Bind(R.id.cProgrammingCardView)
    CardView cProgrammingCardView;
    @Bind(R.id.cppProgrammingTextView)
    TextView cppProgrammingTextView;
    @Bind(R.id.cppProgressBar)
    ProgressBar cppProgressBar;
    @Bind(R.id.cppProgrammingCardView)
    CardView cppProgrammingCardView;
    @Bind(R.id.javaProgrammingTextView)
    TextView javaProgrammingTextView;
    @Bind(R.id.javaProgressBar)
    ProgressBar javaProgressBar;
    @Bind(R.id.javaProgrammingCardView)
    CardView javaProgrammingCardView;
    @Bind(R.id.profileImageView)
    ImageView profileImageView;
    @Bind(R.id.nameTextView)
    TextView nameTextView;

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

        cProgrammingCardView.setOnClickListener(this);
        cppProgrammingCardView.setOnClickListener(this);
        javaProgrammingCardView.setOnClickListener(this);

        int selectedPosition = -1;
        String selectedLanguage = creekPreferences.getProgramLanguage();

        for (String language : languageArray) {
            selectedPosition++;
            language = language.replace("Programming", "").trim().toLowerCase();
            if (language.equals(selectedLanguage)) {
                languageSelectionTextView.setText(languageArray[selectedPosition]);
            }
        }
        initUserValues();
        return view;
    }

    private void initUserValues() {
        Glide.with(getContext()).load(creekPreferences.getAccountPhoto()).fitCenter().into(profileImageView);
        nameTextView.setText(creekPreferences.getAccountName());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cppProgrammingCardView:
                selectAndInitDb(1);
                break;
            case R.id.cProgrammingCardView:
                selectAndInitDb(0);
                break;
            case R.id.javaProgrammingCardView:
                selectAndInitDb(2);
                break;
        }
    }

    private void selectAndInitDb(int position) {
        String selectedString = languageArray[position];
        languageSelectionTextView.setText(selectedString);
        selectedString = selectedString.replace(" Programming", "").toLowerCase();
        creekPreferences.setProgramLanguage(selectedString);
        initDB();

    }
}
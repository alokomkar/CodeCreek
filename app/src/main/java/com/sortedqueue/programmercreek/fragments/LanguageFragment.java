package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
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
import com.sortedqueue.programmercreek.database.CreekUserDB;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.DashboardNavigationListener;
import com.sortedqueue.programmercreek.util.CommonUtils;
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
    private int INDEX_CPP = 1;
    private int INDEX_C = 0;
    private int INDEX_JAVA = 2;
    private DashboardNavigationListener dashboardNavigationListener;

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
        Glide.with(getContext())
                .load(creekPreferences.getAccountPhoto())
                .fitCenter()
                .into(profileImageView);
        nameTextView.setText(creekPreferences.getAccountName());
        if( creekPreferences.getProgramLanguage().equals("")) {
            getFirebaseDBVerion();

        }
    }

    public void getFirebaseDBVerion() {
        //firebaseDatabaseHandler.writeCreekUserDB( new CreekUserDB() );
        //CommonUtils.displayProgressDialog(DashboardActivity.this, "Checking for updates");
        CommonUtils.displayProgressDialog(getContext(), "Fetching database...");
        firebaseDatabaseHandler = new FirebaseDatabaseHandler(getContext());
        firebaseDatabaseHandler.readCreekUserDB(new FirebaseDatabaseHandler.GetCreekUserDBListener() {
            @Override
            public void onSuccess(CreekUserDB creekUserDB) {
                CommonUtils.dismissProgressDialog();
                selectAndInitDb(0);
            }

            @Override
            public void onError(DatabaseError databaseError) {
                CommonUtils.dismissProgressDialog();
                Log.d("LanguageFragment", databaseError.getMessage());
                databaseError.toException().printStackTrace();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initDB() {
        logDebugMessage("Inserting all Programs Titles..");
        if( !creekPreferences.checkProgramIndexUpdate() ) {
            firebaseDatabaseHandler = new FirebaseDatabaseHandler(getContext());
            firebaseDatabaseHandler.initializeProgramIndexes(new FirebaseDatabaseHandler.ProgramIndexInterface() {
                @Override
                public void getProgramIndexes(ArrayList<ProgramIndex> program_indices) {
                    dashboardNavigationListener.navigateToDashboard();
                }

                @Override
                public void onError(DatabaseError error) {

                }
            });
        }
        else {
            dashboardNavigationListener.navigateToDashboard();
        }
    }

    private void logDebugMessage(String message) {
        Log.d("LFragment", message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cppProgrammingCardView:
                selectAndInitDb(INDEX_CPP);
                break;
            case R.id.cProgrammingCardView:
                selectAndInitDb(INDEX_C);
                break;
            case R.id.javaProgrammingCardView:
                selectAndInitDb(INDEX_JAVA);
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

    @Override
    public void onDetach() {
        super.onDetach();
        this.dashboardNavigationListener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if( context instanceof DashboardNavigationListener ) {
            this.dashboardNavigationListener = (DashboardNavigationListener) context;
        }
    }
}

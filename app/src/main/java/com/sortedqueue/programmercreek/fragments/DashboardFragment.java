package com.sortedqueue.programmercreek.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.NewProgramWikiActivity;
import com.sortedqueue.programmercreek.activity.ProgramListActivity;
import com.sortedqueue.programmercreek.activity.SyntaxLearnActivity;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 02/01/17.
 */

public class DashboardFragment extends Fragment implements View.OnClickListener {
    
    private static DashboardFragment instance;
    
    @Bind(R.id.syntaxTextView)
    TextView syntaxTextView;
    @Bind(R.id.syntaxLayout)
    FrameLayout syntaxLayout;
    @Bind(R.id.indexTextView)
    TextView indexTextView;
    @Bind(R.id.indexLayout)
    FrameLayout indexLayout;
    @Bind(R.id.wikiTextView)
    TextView wikiTextView;
    @Bind(R.id.wikiLayout)
    FrameLayout wikiLayout;
    @Bind(R.id.reviseTextView)
    TextView reviseTextView;
    @Bind(R.id.reviseLayout)
    FrameLayout reviseLayout;
    @Bind(R.id.quizTextView)
    TextView quizTextView;
    @Bind(R.id.quizLayout)
    FrameLayout quizLayout;
    @Bind(R.id.matchTextView)
    TextView matchTextView;
    @Bind(R.id.matchLayout)
    FrameLayout matchLayout;
    @Bind(R.id.fillBlanksTextView)
    TextView fillBlanksTextView;
    @Bind(R.id.fillLayout)
    FrameLayout fillLayout;
    @Bind(R.id.testTextView)
    TextView testTextView;
    @Bind(R.id.testLayout)
    FrameLayout testLayout;
    @Bind(R.id.wizardTextView)
    TextView wizardTextView;
    @Bind(R.id.wizardLayout)
    FrameLayout wizardLayout;
    private CreekPreferences creekPreferences;
    private FirebaseDatabaseHandler firebaseDatabaseHandler;

    public static DashboardFragment getInstance() {
        if (instance == null) {
            instance = new DashboardFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child_dashboard, container, false);
        ButterKnife.bind(this, view);
        creekPreferences = new CreekPreferences(getContext());

        initUI();
        return view;
    }
    private void initUI() {
        wikiLayout.setOnClickListener(this);
        syntaxLayout.setOnClickListener(this);
        indexLayout.setOnClickListener(this);
        matchLayout.setOnClickListener(this);
        testLayout.setOnClickListener(this);
        reviseLayout.setOnClickListener(this);
        quizLayout.setOnClickListener(this);
        wizardLayout.setOnClickListener(this);
        fillLayout.setOnClickListener(this);
        
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.wikiLayout:
                Intent intent = new Intent(getContext(), NewProgramWikiActivity.class);
                startActivity(intent);
                break;
            case R.id.syntaxLayout:
                Intent syntaxIntent = new Intent(getContext(), SyntaxLearnActivity.class);
                syntaxIntent.putExtra(ProgrammingBuddyConstants.KEY_WIKI, creekPreferences.getProgramWiki());
                startActivity(syntaxIntent);
                break;
            case R.id.indexLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_LIST);
                break;

            case R.id.reviseLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_REVISE);
                break;

            case R.id.wizardLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_WIZARD);
                break;

            case R.id.testLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_TEST);
                break;

            case R.id.matchLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_MATCH);
                break;

            case R.id.quizLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_QUIZ);
                break;
        }

    }


    private void LaunchProgramListActivity(final int invokeMode) {
        if (creekPreferences.getProgramTables() == -1) {
            CommonUtils.displayProgressDialog(getActivity(), "Initializing data for the first time : " + creekPreferences.getProgramLanguage().toUpperCase());
            firebaseDatabaseHandler = new FirebaseDatabaseHandler(getActivity());
            firebaseDatabaseHandler.initializeProgramTables(new FirebaseDatabaseHandler.ProgramTableInterface() {
                @Override
                public void getProgramTables(ArrayList<ProgramTable> program_tables) {
                    CommonUtils.dismissProgressDialog();
                    LaunchProgramListActivity(invokeMode);
                }

                @Override
                public void onError(DatabaseError error) {
                    CommonUtils.dismissProgressDialog();
                }
            });
        } else {
            Intent programListIntent = new Intent(getContext(), ProgramListActivity.class);
            programListIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, invokeMode);
            boolean isWizard = invokeMode == ProgrammingBuddyConstants.KEY_WIZARD;
            programListIntent.putExtra(ProgramListActivity.KEY_WIZARD, isWizard);
            if (isWizard) {
                programListIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_REVISE);
            }
            startActivity(programListIntent);
            /*this.overridePendingTransition(R.anim.animation_leave,
                    R.anim.animation_enter);*/
        }

    }
}

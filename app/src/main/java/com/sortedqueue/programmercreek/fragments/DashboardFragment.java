package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.AlgorithmListActivity;
import com.sortedqueue.programmercreek.activity.ChaptersActivity;
import com.sortedqueue.programmercreek.activity.CodeLabActivity;
import com.sortedqueue.programmercreek.activity.InterviewActivity;
import com.sortedqueue.programmercreek.activity.IntroActivity;
import com.sortedqueue.programmercreek.activity.LessonActivity;
import com.sortedqueue.programmercreek.activity.NewProgramWikiActivity;
import com.sortedqueue.programmercreek.activity.ProgramInserterActivity;
import com.sortedqueue.programmercreek.activity.ProgramListActivity;
import com.sortedqueue.programmercreek.activity.SyntaxLearnActivity;
import com.sortedqueue.programmercreek.adapter.AlgorithmsRecyclerAdapter;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.AlgorithmsIndex;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.DashboardNavigationListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekAnalytics;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 02/01/17.
 */

public class DashboardFragment extends Fragment implements View.OnClickListener, FirebaseDatabaseHandler.GetAllAlgorithmsListener, CustomProgramRecyclerViewAdapter.AdapterClickListner {

    private static DashboardFragment instance;

    @BindView(R.id.syntaxTextView)
    TextView syntaxTextView;
    @BindView(R.id.syntaxLayout)
    FrameLayout syntaxLayout;
    @BindView(R.id.indexTextView)
    TextView indexTextView;
    @BindView(R.id.indexLayout)
    FrameLayout indexLayout;
    @BindView(R.id.wikiTextView)
    TextView wikiTextView;
    @BindView(R.id.wikiLayout)
    FrameLayout wikiLayout;
    @BindView(R.id.reviseTextView)
    TextView reviseTextView;
    @BindView(R.id.reviseLayout)
    FrameLayout reviseLayout;
    @BindView(R.id.quizTextView)
    TextView quizTextView;
    @BindView(R.id.quizLayout)
    FrameLayout quizLayout;
    @BindView(R.id.matchTextView)
    TextView matchTextView;
    @BindView(R.id.matchLayout)
    FrameLayout matchLayout;
    @BindView(R.id.fillBlanksTextView)
    TextView fillBlanksTextView;
    @BindView(R.id.fillLayout)
    FrameLayout fillLayout;
    @BindView(R.id.testTextView)
    TextView testTextView;
    @BindView(R.id.testLayout)
    FrameLayout testLayout;
    @BindView(R.id.interviewLayout)
    FrameLayout interviewLayout;
    @BindView(R.id.quickReferenceLayout)
    FrameLayout quickReferenceLayout;

    @BindView(R.id.wizardTextView)
    TextView wizardTextView;
    @BindView(R.id.wizardLayout)
    FrameLayout wizardLayout;
    @BindView(R.id.introLayout)
    FrameLayout introLayout;
    @BindView(R.id.codeLabLayout)
    FrameLayout codeLabLayout;
    @BindView(R.id.addCodeCardView)
    CardView addCodeCardView;
    @BindView(R.id.dashboardScrollView)
    NestedScrollView dashboardScrollView;
    @BindView(R.id.adaScrollView)
    NestedScrollView adaScrollView;
    @BindView(R.id.interviewTextView)
    TextView interviewTextView;
    @BindView(R.id.adaRecyclerView)
    RecyclerView adaRecyclerView;
    @BindView(R.id.addCodeTextView)
    TextView addCodeTextView;
    @BindView(R.id.downloadFileTextView)
    TextView downloadFileTextView;
    @BindView(R.id.lessonsLayout)
    FrameLayout lessonsLayout;
    @BindView(R.id.codeLabTextView)
    TextView codeLabTextView;
    private CreekPreferences creekPreferences;
    private FirebaseDatabaseHandler firebaseDatabaseHandler;
    private AlgorithmsRecyclerAdapter algorithmsRecyclerAdapter;
    private String TAG = DashboardFragment.class.getSimpleName();

    public static DashboardFragment getInstance() {
        if (instance == null) {
            instance = new DashboardFragment();
        }
        return instance;
    }

    private DashboardNavigationListener dashboardNavigationListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DashboardNavigationListener) {
            dashboardNavigationListener = (DashboardNavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dashboardNavigationListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child_dashboard, container, false);
        ButterKnife.bind(this, view);
        creekPreferences = CreekApplication.getCreekPreferences();
        //creekPreferences.setProgramLanguage("sql");
        initUI();
        animateViews();
        return view;
    }

    public void animateViews() {
        if (creekPreferences == null) {
            creekPreferences = CreekApplication.getCreekPreferences();
        }
        if (creekPreferences.getProgramLanguage().toLowerCase().equals("ada")) {
            dashboardScrollView.setVisibility(View.GONE);
            adaScrollView.setVisibility(View.VISIBLE);
            new FirebaseDatabaseHandler(getContext()).getAllAlgorithmIndex(this);
        } else {
            if( null == dashboardScrollView ) {
                return;
            }

            dashboardScrollView.setVisibility(View.VISIBLE);
            adaScrollView.setVisibility(View.GONE);
            if (creekPreferences.isProgramsOnly()) {
                introLayout.setVisibility(View.GONE);
                wizardLayout.setVisibility(View.GONE);
                syntaxLayout.setVisibility(View.GONE);
                lessonsLayout.setVisibility(View.GONE);
            } else {
                introLayout.setVisibility(View.VISIBLE);
                wizardLayout.setVisibility(View.VISIBLE);
                syntaxLayout.setVisibility(View.VISIBLE);
                if( creekPreferences.getProgramLanguage().equalsIgnoreCase("java") ) {
                    lessonsLayout.setVisibility(View.VISIBLE);
                }
                else {
                    lessonsLayout.setVisibility(View.GONE);
                }
            }

            dashboardScrollView.scrollTo(0, 0);
            introLayout.setAlpha(0.0f);
            wizardLayout.setAlpha(0.0f);
            if( creekPreferences.getProgramLanguage().equalsIgnoreCase("java") )
                lessonsLayout.setAlpha(0.0f);
            syntaxLayout.setAlpha(0.0f);
            indexLayout.setAlpha(0.0f);
            wikiLayout.setAlpha(0.0f);
            quizLayout.setAlpha(0.0f);
            matchLayout.setAlpha(0.0f);
            testLayout.setAlpha(0.0f);
            interviewLayout.setAlpha(0.0f);
            quickReferenceLayout.setAlpha(0.0f);
            fillLayout.setAlpha(0.0f);
            //codeLabLayout.setAlpha(0.0f);

            int delay = 0;
            int standardDelay = 270;
            initAnimations(introLayout, delay);
            delay = delay + standardDelay;
            initAnimations(wizardLayout, delay);
            if( creekPreferences.getProgramLanguage().equalsIgnoreCase("java") ) {
                delay = delay + standardDelay;
                initAnimations(lessonsLayout, delay);
            }
            delay = delay + standardDelay;
            initAnimations(syntaxLayout, delay);
            delay = delay + standardDelay;
            initAnimations(quickReferenceLayout, delay);
            delay = delay + standardDelay;
            initAnimations(indexLayout, delay);
            delay = delay + standardDelay;
            initAnimations(wikiLayout, delay);
            delay = delay + standardDelay;
            initAnimations(quizLayout, delay);
            delay = delay + standardDelay;
            initAnimations(matchLayout, delay);
            delay = delay + standardDelay;
            initAnimations(fillLayout, delay);
            delay = delay + standardDelay;
            initAnimations(testLayout, delay);

            //delay = delay + standardDelay;
            /*initAnimations(interviewLayout, delay);
            delay = delay + standardDelay;*/
            //initAnimations(codeLabLayout, delay);
        }


    }

    private void initAnimations(FrameLayout frameLayout, int delay) {
        frameLayout.animate().setStartDelay(delay).setDuration(400).alpha(1.0f);
    }

    private void initUI() {
        wikiLayout.setOnClickListener(this);
        syntaxLayout.setOnClickListener(this);
        indexLayout.setOnClickListener(this);
        matchLayout.setOnClickListener(this);
        testLayout.setOnClickListener(this);
        quickReferenceLayout.setOnClickListener(this);
        interviewLayout.setOnClickListener(this);
        reviseLayout.setOnClickListener(this);
        quizLayout.setOnClickListener(this);
        wizardLayout.setOnClickListener(this);
        lessonsLayout.setOnClickListener(this);
        introLayout.setOnClickListener(this);
        fillLayout.setOnClickListener(this);
        codeLabLayout.setOnClickListener(this);
        downloadFileTextView.setOnClickListener(this);
        addCodeTextView.setOnClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(final View v) {
        if (!AuxilaryUtils.isNetworkAvailable()) {
            CommonUtils.displaySnackBarIndefinite(getActivity(), R.string.internet_unavailable, R.string.retry, new View.OnClickListener() {
                @Override
                public void onClick(View snackBarView) {
                    onClick(v);
                }
            });
            return;
        }
        if (creekPreferences.getProgramLanguage().equals("")) {
            CommonUtils.displaySnackBar(getActivity(), R.string.choose_language);
            if (dashboardNavigationListener != null) {
                dashboardNavigationListener.navigateToLanguage();
            }
            return;
        }

        Intent intent;
        switch (v.getId()) {
            case R.id.wikiLayout:
                CreekAnalytics.logEvent(TAG, "Wiki");
                intent = new Intent(getContext(), NewProgramWikiActivity.class);
                startActivity(intent);
                break;
            case R.id.quickReferenceLayout:
                /*intent = new Intent(getContext(), WebViewActivity.class);
                startActivity(intent);*/
                CreekAnalytics.logEvent(TAG, "Quick Reference");
                dashboardNavigationListener.showQuickReferenceFragment();
                break;
            case R.id.interviewLayout:
                CreekAnalytics.logEvent(TAG, "Interview");
                intent = new Intent(getContext(), InterviewActivity.class);
                startActivity(intent);
                break;
            case R.id.syntaxLayout:
                CreekAnalytics.logEvent(TAG, "Syntax");
                Intent syntaxIntent = new Intent(getContext(), SyntaxLearnActivity.class);
                syntaxIntent.putExtra(ProgrammingBuddyConstants.KEY_WIKI, creekPreferences.getProgramWiki());
                startActivity(syntaxIntent);
                break;
            case R.id.indexLayout:
                CreekAnalytics.logEvent(TAG, "Wizard - Program Index");
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_WIZARD);
                break;

            case R.id.introLayout:
                CreekAnalytics.logEvent(TAG, "Intro");
                Intent introIntent = new Intent(getContext(), IntroActivity.class);
                startActivity(introIntent);
                break;

            //TODO : To be removed later
            case R.id.reviseLayout:
                Intent programInserterIntent = new Intent(getContext(), ProgramInserterActivity.class);
                startActivity(programInserterIntent);
                //LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_REVISE);
                break;

            case R.id.wizardLayout:
                CreekAnalytics.logEvent(TAG, "Chapters");
                Intent textModeIntent = new Intent(getContext(), ChaptersActivity.class);
                startActivity(textModeIntent);
                break;

            case R.id.lessonsLayout:
                CreekAnalytics.logEvent(TAG, "Bits and Bytes");
                Intent lessonIntent = new Intent(getContext(), LessonActivity.class);
                startActivity(lessonIntent);
                break;

            case R.id.testLayout:
                CreekAnalytics.logEvent(TAG, "Test");
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_TEST);
                break;

            case R.id.matchLayout:
                CreekAnalytics.logEvent(TAG, "Match");
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_MATCH);
                break;

            case R.id.fillLayout:
                CreekAnalytics.logEvent(TAG, "Fill Code");
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_FILL_BLANKS);
                break;

            case R.id.quizLayout:
                CreekAnalytics.logEvent(TAG, "Quiz");
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_QUIZ);
                break;

            case R.id.codeLabLayout:
                CreekAnalytics.logEvent(TAG, "Code Lab");
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_CODE_LAB);
                break;

            case R.id.downloadFileTextView:
                dashboardNavigationListener.importFromFile();
                //dashboardNavigationListener.onProgressStatsUpdate(50);
                /*Intent searchIntent =
                new Intent(getContext(), ProgramWikiActivity.class);
                startActivity(searchIntent);*/
                break;
            case R.id.addCodeTextView:
                dashboardNavigationListener.importCodeFile();
                break;
        }

    }


    private void LaunchProgramListActivity(final int invokeMode) {
        if (creekPreferences.getProgramTables() == -1) {
            CommonUtils.displayProgressDialog(getActivity(), "Initializing data for the first time : " + creekPreferences.getProgramLanguage().toUpperCase());
            firebaseDatabaseHandler = new FirebaseDatabaseHandler(getContext());
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
            if (invokeMode == ProgrammingBuddyConstants.KEY_CODE_LAB) {
                Intent intent = new Intent(getContext(), CodeLabActivity.class);
                startActivity(intent);
                return;
            }
            Intent programListIntent = new Intent(getContext(), ProgramListActivity.class);
            programListIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, invokeMode);
            boolean isWizard = invokeMode == ProgrammingBuddyConstants.KEY_WIZARD;
            programListIntent.putExtra(ProgramListActivity.KEY_WIZARD, isWizard);
            if (isWizard) {
                programListIntent.putExtra(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_REVISE);
            }
            startActivity(programListIntent);
            /*AuxilaryUtils.displayInformation(getContext(), R.string.unlock_programs, R.string.unlock_programs_description, new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {

                }

            });*/

            /*this.overridePendingTransition(R.anim.animation_leave,
                    R.anim.animation_enter);*/
        }

    }

    @Override
    public void onSuccess(ArrayList<AlgorithmsIndex> algorithmsIndexArrayList) {
        adaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        algorithmsRecyclerAdapter = new AlgorithmsRecyclerAdapter(getContext(), this, algorithmsIndexArrayList);
        adaRecyclerView.setAdapter(algorithmsRecyclerAdapter);
        CommonUtils.dismissProgressDialog();
    }

    @Override
    public void onError(DatabaseError databaseError) {
        CommonUtils.dismissProgressDialog();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), AlgorithmListActivity.class);
        intent.putExtra(ProgrammingBuddyConstants.KEY_PROG_ID, algorithmsRecyclerAdapter.getItemAtPosition(position));
        startActivity(intent);
    }
}

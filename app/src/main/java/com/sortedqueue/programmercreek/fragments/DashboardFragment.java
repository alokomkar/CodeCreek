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
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.AlgorithmListActivity;
import com.sortedqueue.programmercreek.activity.ChaptersActivity;
import com.sortedqueue.programmercreek.activity.CodeLabActivity;
import com.sortedqueue.programmercreek.activity.InterviewActivity;
import com.sortedqueue.programmercreek.activity.IntroActivity;
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
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 02/01/17.
 */

public class DashboardFragment extends Fragment implements View.OnClickListener, FirebaseDatabaseHandler.GetAllAlgorithmsListener, CustomProgramRecyclerViewAdapter.AdapterClickListner {

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
    @Bind(R.id.interviewLayout)
    FrameLayout interviewLayout;
    @Bind(R.id.wizardTextView)
    TextView wizardTextView;
    @Bind(R.id.wizardLayout)
    FrameLayout wizardLayout;
    @Bind(R.id.introLayout)
    FrameLayout introLayout;
    @Bind(R.id.codeLabLayout)
    FrameLayout codeLabLayout;
    @Bind(R.id.searchCardView)
    CardView searchCardView;
    @Bind(R.id.dashboardScrollView)
    NestedScrollView dashboardScrollView;
    @Bind(R.id.adaScrollView)
    NestedScrollView adaScrollView;
    @Bind(R.id.interviewTextView)
    TextView interviewTextView;
    @Bind(R.id.adaRecyclerView)
    RecyclerView adaRecyclerView;
    private CreekPreferences creekPreferences;
    private FirebaseDatabaseHandler firebaseDatabaseHandler;
    private AlgorithmsRecyclerAdapter algorithmsRecyclerAdapter;

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
        creekPreferences = new CreekPreferences(getContext());
        //creekPreferences.setProgramLanguage("sql");
        initUI();
        animateViews();
        return view;
    }

    public void animateViews() {
        if (creekPreferences == null) {
            creekPreferences = new CreekPreferences(getContext());
        }
        if (creekPreferences.getProgramLanguage().toLowerCase().equals("ada")) {
            dashboardScrollView.setVisibility(View.GONE);
            adaScrollView.setVisibility(View.VISIBLE);
            new FirebaseDatabaseHandler(getContext()).getAllAlgorithmIndex( this );
        } else {
            dashboardScrollView.setVisibility(View.VISIBLE);
            adaScrollView.setVisibility(View.GONE);
            if (creekPreferences.isProgramsOnly()) {
                introLayout.setVisibility(View.GONE);
                wizardLayout.setVisibility(View.GONE);
                syntaxLayout.setVisibility(View.GONE);
            } else {
                introLayout.setVisibility(View.VISIBLE);
                wizardLayout.setVisibility(View.VISIBLE);
                syntaxLayout.setVisibility(View.VISIBLE);
            }

            dashboardScrollView.scrollTo(0, 0);
            introLayout.setAlpha(0.0f);
            wizardLayout.setAlpha(0.0f);
            syntaxLayout.setAlpha(0.0f);
            indexLayout.setAlpha(0.0f);
            wikiLayout.setAlpha(0.0f);
            quizLayout.setAlpha(0.0f);
            matchLayout.setAlpha(0.0f);
            testLayout.setAlpha(0.0f);
            interviewLayout.setAlpha(0.0f);
            //codeLabLayout.setAlpha(0.0f);

            int delay = 0;
            int standardDelay = 270;
            initAnimations(introLayout, delay);
            delay = delay + standardDelay;
            initAnimations(wizardLayout, delay);
            delay = delay + standardDelay;
            initAnimations(syntaxLayout, delay);
            delay = delay + standardDelay;
            initAnimations(indexLayout, delay);
            delay = delay + standardDelay;
            initAnimations(wikiLayout, delay);
            delay = delay + standardDelay;
            initAnimations(quizLayout, delay);
            delay = delay + standardDelay;
            initAnimations(matchLayout, delay);
            delay = delay + standardDelay;
            initAnimations(testLayout, delay);
            delay = delay + standardDelay;
            initAnimations(interviewLayout, delay);
            delay = delay + standardDelay;
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
        interviewLayout.setOnClickListener(this);
        reviseLayout.setOnClickListener(this);
        quizLayout.setOnClickListener(this);
        wizardLayout.setOnClickListener(this);
        introLayout.setOnClickListener(this);
        fillLayout.setOnClickListener(this);
        codeLabLayout.setOnClickListener(this);
        searchCardView.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
                intent = new Intent(getContext(), NewProgramWikiActivity.class);
                startActivity(intent);
                break;
            case R.id.interviewLayout:
                intent = new Intent(getContext(), InterviewActivity.class);
                startActivity(intent);
                break;
            case R.id.syntaxLayout:
                Intent syntaxIntent = new Intent(getContext(), SyntaxLearnActivity.class);
                syntaxIntent.putExtra(ProgrammingBuddyConstants.KEY_WIKI, creekPreferences.getProgramWiki());
                startActivity(syntaxIntent);
                break;
            case R.id.indexLayout:
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_WIZARD);
                break;

            case R.id.introLayout:
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
                Intent textModeIntent = new Intent(getContext(), ChaptersActivity.class);
                startActivity(textModeIntent);
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

            case R.id.codeLabLayout :
                LaunchProgramListActivity(ProgrammingBuddyConstants.KEY_CODE_LAB);
                break;

            /*case R.id.searchCardView :
                Intent searchIntent = new Intent(getContext(), ProgramWikiActivity.class);
                startActivity(searchIntent);
                break;*/
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
            if( invokeMode == ProgrammingBuddyConstants.KEY_CODE_LAB ) {
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
        adaRecyclerView.setLayoutManager( new LinearLayoutManager(getContext()) );
        algorithmsRecyclerAdapter = new AlgorithmsRecyclerAdapter( getContext(), this, algorithmsIndexArrayList );
        adaRecyclerView.setAdapter( algorithmsRecyclerAdapter );
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

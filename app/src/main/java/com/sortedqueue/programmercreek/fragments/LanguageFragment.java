package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.adapter.ProgramLanguageAdapter;
import com.sortedqueue.programmercreek.database.CreekUserDB;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.ProgramLanguage;
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

public class LanguageFragment extends Fragment {
    private static LanguageFragment instance;
    @Bind(R.id.languageSelectionTextView)
    TextView languageSelectionTextView;
    @Bind(R.id.profileImageView)
    ImageView profileImageView;
    @Bind(R.id.nameTextView)
    TextView nameTextView;
    @Bind(R.id.selectedLanguageCardView)
    CardView selectedLanguageCardView;
    @Bind(R.id.programLanguageRecyclerView)
    RecyclerView programLanguageRecyclerView;
    @Bind(R.id.reputationProgressBar)
    ProgressBar reputationProgressBar;
    @Bind(R.id.reputationTextView)
    TextView reputationTextView;

    private Handler handler;

    private CreekPreferences creekPreferences;
    private FirebaseDatabaseHandler firebaseDatabaseHandler;
    private DashboardNavigationListener dashboardNavigationListener;
    private ArrayList<ProgramLanguage> programLanguages;

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
        getProgramLanguages();
        handler = new Handler();
        animateProgress();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if( isVisibleToUser ) {
            animateProgress();
        }
    }

    private void getProgramLanguages() {
        if (!AuxilaryUtils.isNetworkAvailable()) {
            CommonUtils.displaySnackBarIndefinite(getActivity(), R.string.internet_unavailable, R.string.retry, new View.OnClickListener() {
                @Override
                public void onClick(View snackBarView) {
                    getProgramLanguages();
                }
            });
            return;
        }
        CommonUtils.displayProgressDialog(getContext(), "Fetching all languages...");
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(getContext());
        firebaseDatabaseHandler.getAllProgramLanguages(new FirebaseDatabaseHandler.GetProgramLanguageListener() {
            @Override
            public void onSuccess(ArrayList<ProgramLanguage> programLanguages) {
                ProgramLanguage programLanguage = new ProgramLanguage();
                programLanguage.setDescription("Analysis and design of algorithms");
                programLanguage.setProgramLanguage("Analysis and Design of Algorithms");
                programLanguage.setIsProgramsOnly("true");
                programLanguage.setLanguageId("ADA");

                programLanguages.add(programLanguage);
                setupRecyclerview(programLanguages);
            }

            @Override
            public void onError(DatabaseError databaseError) {
                CommonUtils.dismissProgressDialog();
                CommonUtils.displaySnackBar(getActivity(), R.string.unable_to_fetch_data);
            }
        });
    }

    private void setupRecyclerview(ArrayList<ProgramLanguage> programLanguages) {
        this.programLanguages = programLanguages;
        programLanguageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        programLanguageRecyclerView.setAdapter(
                new ProgramLanguageAdapter(getContext(),
                        programLanguages,
                        new CustomProgramRecyclerViewAdapter.AdapterClickListner() {
            @Override
            public void onItemClick(int position) {
                selectAndInitDb(position);
            }
        }));
        CommonUtils.dismissProgressDialog();
        int selectedPosition = -1;
        String selectedLanguage = creekPreferences.getProgramLanguage();

        for (ProgramLanguage programLanguage : programLanguages) {
            selectedPosition++;
            String language = programLanguage.getLanguageId();
            if (language.equalsIgnoreCase(selectedLanguage)) {
                languageSelectionTextView.setText(programLanguage.getProgramLanguage());
            }
        }
        initUserValues();

    }


    private void initUserValues() {
        Glide.with(getContext())
                .load(creekPreferences.getAccountPhoto())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(profileImageView);
        nameTextView.setText(creekPreferences.getAccountName());
        if (creekPreferences == null) {
            creekPreferences = new CreekPreferences(getContext());
        }
        if (creekPreferences.getCreekUserStats() != null) {
            int level = creekPreferences.getCreekUserStats().getCreekUserReputation() / 100;
            if (level > 0) {
                nameTextView.setText(creekPreferences.getAccountName());
                nameTextView.append("\nLevel " + level);
            }
        }
        getFirebaseDBVerion();
    }

    public void getFirebaseDBVerion() {
        //firebaseDatabaseHandler.writeCreekUserDB( new CreekUserDB() );
        //CommonUtils.displayProgressDialog(DashboardActivity.this, "Checking for updates");
        if (!AuxilaryUtils.isNetworkAvailable()) {
            CommonUtils.displaySnackBarIndefinite(getActivity(), R.string.internet_unavailable, R.string.retry, new View.OnClickListener() {
                @Override
                public void onClick(View snackBarView) {
                    getFirebaseDBVerion();
                }
            });
            return;
        }
        CommonUtils.displayProgressDialog(getContext(), "Fetching database...");
        firebaseDatabaseHandler = new FirebaseDatabaseHandler(getContext());
        firebaseDatabaseHandler.readCreekUserDB(new FirebaseDatabaseHandler.GetCreekUserDBListener() {
            @Override
            public void onSuccess(CreekUserDB creekUserDB) {
                CommonUtils.dismissProgressDialog();
                selectedLanguageCardView.setVisibility(creekPreferences.getProgramLanguage().equals("") ? View.GONE : View.VISIBLE);
                dashboardNavigationListener.showInviteDialog();
                //selectAndInitDb(0);
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
        if (!creekPreferences.checkProgramIndexUpdate()) {
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
        } else {
            dashboardNavigationListener.navigateToDashboard();
        }
    }

    private void logDebugMessage(String message) {
        Log.d("LFragment", message);
    }

    private void selectAndInitDb(final int position) {
        if (!AuxilaryUtils.isNetworkAvailable()) {
            CommonUtils.displaySnackBarIndefinite(getActivity(), R.string.internet_unavailable, R.string.retry, new View.OnClickListener() {
                @Override
                public void onClick(View snackBarView) {
                    selectAndInitDb(position);
                }
            });
            return;
        }
        String selectedString = programLanguages.get(position).getLanguageId();
        languageSelectionTextView.setText(programLanguages.get(position).getProgramLanguage());
        creekPreferences.setProgramLanguage(selectedString.toLowerCase());
        creekPreferences.setIsProgramsOnly(programLanguages.get(position).getIsProgramsOnly().equalsIgnoreCase("true"));
        selectedLanguageCardView.setVisibility(creekPreferences.getProgramLanguage().equals("") ? View.GONE : View.VISIBLE);
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
        if (context instanceof DashboardNavigationListener) {
            this.dashboardNavigationListener = (DashboardNavigationListener) context;
            dashboardNavigationListener.calculateReputation();
        }
    }

    private int progressBarStatus;

    public void animateProgress() {

        if (reputationProgressBar != null) {

            if (handler == null) {
                handler = new Handler();
            }
            if (creekPreferences == null) {
                creekPreferences = new CreekPreferences(getContext());
            }
            CreekUserStats creekUserStats = creekPreferences.getCreekUserStats();
            if (creekUserStats == null) {
                reputationProgressBar.setVisibility(View.GONE);
                reputationTextView.setVisibility(View.GONE);
                return;
            }
            final int progress = creekUserStats.getCreekUserReputation() % 100;
            if (progress > 0) {
                reputationProgressBar.setVisibility(View.VISIBLE);
                reputationTextView.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (progressBarStatus = 0; progressBarStatus <= progress; progressBarStatus++) {

                            handler.post(new Runnable() {
                                public void run() {
                                    if( reputationProgressBar != null ) {
                                        reputationProgressBar.setProgress(progressBarStatus);
                                        reputationTextView.setText(progressBarStatus +"% Complete");
                                        int level = creekPreferences.getCreekUserStats().getCreekUserReputation() / 100;
                                        if (level > 0) {
                                            nameTextView.setText(creekPreferences.getAccountName());
                                            nameTextView.append("\nLevel " + level);
                                        }
                                    }

                                }
                            });


                            try {
                                Thread.sleep(40);
                            } catch (Exception ex) {
                            }
                        }
                    }
                }).start();
            } else {
                reputationProgressBar.setVisibility(View.GONE);
                reputationTextView.setVisibility(View.GONE);
            }
        }
    }
}
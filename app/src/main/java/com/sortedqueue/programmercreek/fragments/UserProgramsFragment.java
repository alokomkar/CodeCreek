package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SpinnerAdapter;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.ProgramActivity;
import com.sortedqueue.programmercreek.activity.ProgramListActivity;
import com.sortedqueue.programmercreek.adapter.UserProgramRecyclerAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.UserProgramDetails;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.DashboardNavigationListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 16/05/17.
 */

public class UserProgramsFragment extends Fragment implements UserProgramRecyclerAdapter.UserProgramClickListener, FirebaseDatabaseHandler.GetAllUserProgramsListener, AdapterView.OnItemSelectedListener {

    private static UserProgramsFragment instance;
    @BindView(R.id.userProgramsRecyclerView)
    RecyclerView userProgramsRecyclerView;
    @BindView(R.id.allProgramsRadioButton)
    RadioButton allProgramsRadioButton;
    @BindView(R.id.myProgramsRadioButton)
    RadioButton myProgramsRadioButton;
    @BindView(R.id.myFavoritesRadioButton)
    RadioButton myFavoritesRadioButton;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.noProgramsLayout)
    LinearLayout noProgramsLayout;
    @BindView(R.id.adView)
    AdView adView;
    @BindView(R.id.languageSpinner)
    AppCompatSpinner languageSpinner;
    @BindView(R.id.communityScrollView)
    NestedScrollView communityScrollView;
    private UserProgramRecyclerAdapter adapter;
    private String accessSpecifier;
    private DashboardNavigationListener dashboardNavigationListener;

    private String TAG = UserProgramsFragment.class.getSimpleName();
    private String language = "All";

    public static UserProgramsFragment getInstance() {
        if (instance == null) {
            instance = new UserProgramsFragment();
        }
        return instance;
    }

    private String businessType[] = { "All", "C", "C++", "Java"};
    ArrayAdapter<String> adapterBusinessType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_programs, container, false);
        ButterKnife.bind(this, view);
        //language = getArguments().getString("Language");
        allProgramsRadioButton.setChecked(true);
        allProgramsRadioButton.setOnCheckedChangeListener(checkChangedListener);
        myProgramsRadioButton.setOnCheckedChangeListener(checkChangedListener);
        adapterBusinessType = new ArrayAdapter<String>(getContext(),
                R.layout.item_language_select, businessType);
        languageSpinner.setAdapter(adapterBusinessType);
        languageSpinner.setOnItemSelectedListener(this);
        //myFavoritesRadioButton.setOnCheckedChangeListener(checkChangedListener);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // Setup refresh listener which triggers new data loading
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchUserPrograms(accessSpecifier);
            }
        });
        fetchUserPrograms("All programs");
        if( !CreekApplication.Companion.getCreekPreferences().isPremiumUser() ) {
            initAds();
        }
        return view;
    }

    private void initAds() {
        /*if(CreekApplication.getPreferences().getAdsEnabled())*/ {
            MobileAds.initialize(getContext(), getString(R.string.mobile_banner_id));
            //For actual ads : AdRequest adRequest = new AdRequest.Builder().build();
            //For creating test ads
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                    .build();
            adView.loadAd(adRequest);
            adView.setVisibility(View.GONE);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    adView.setVisibility(View.VISIBLE);
                }
            });
        }
    }


    private CompoundButton.OnCheckedChangeListener checkChangedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.allProgramsRadioButton:
                    if (allProgramsRadioButton.isChecked()) {
                        languageSpinner.setEnabled(true);
                        fetchUserPrograms("All Programs");
                    }
                    break;
                case R.id.myProgramsRadioButton:
                    if (myProgramsRadioButton.isChecked()) {
                        languageSpinner.setEnabled(false);
                        fetchUserPrograms("My Programs");
                    }
                    break;
                /*case R.id.myFavoritesRadioButton :
                    if( myFavoritesRadioButton.isChecked() ) {
                        fetchUserPrograms("Favorites");
                    }
                    break;*/
            }
        }
    };

    private void fetchUserPrograms(String accessSpecifier) {
        this.accessSpecifier = accessSpecifier;
        swipeRefreshLayout.setRefreshing(true);
        new FirebaseDatabaseHandler(getContext()).getAllUserPrograms(accessSpecifier, language, this);
    }

    private void setupRecyclerView(ArrayList<UserProgramDetails> userProgramDetailsArrayList) {
        userProgramsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserProgramRecyclerAdapter(getContext(), accessSpecifier, userProgramDetailsArrayList, this);
        userProgramsRecyclerView.setAdapter(adapter);
        noProgramsLayout.setVisibility(View.GONE);
        userProgramsRecyclerView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSuccess(ArrayList<UserProgramDetails> userProgramDetailsArrayList) {
        setupRecyclerView(userProgramDetailsArrayList);
    }

    @Override
    public void onError(DatabaseError databaseError) {
        swipeRefreshLayout.setRefreshing(false);
        noProgramsLayout.setVisibility(View.VISIBLE);
        userProgramsRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(int position) {

        UserProgramDetails userProgramDetails = adapter.getItemAtPosition(position);
        userProgramDetails.setViews(userProgramDetails.getViews() + 1);
        adapter.notifyDataSetChanged();

        new FirebaseDatabaseHandler(getContext()).updateViewCount(userProgramDetails);
        Bundle newIntentBundle = new Bundle();
        Intent newIntent = null;
        newIntentBundle.putBoolean(ProgramListActivity.Companion.getKEY_WIZARD(), true);
        newIntentBundle.putParcelable(ProgrammingBuddyConstants.INSTANCE.getKEY_PROG_ID(), userProgramDetails.getProgramIndex());
        newIntentBundle.putInt(ProgrammingBuddyConstants.INSTANCE.getKEY_TOTAL_PROGRAMS(), 1);
        newIntentBundle.putString(ProgrammingBuddyConstants.INSTANCE.getKEY_PROG_TITLE(), userProgramDetails.getProgramIndex().getProgram_Description());
        newIntentBundle.putParcelableArrayList(ProgrammingBuddyConstants.INSTANCE.getKEY_USER_PROGRAM(), userProgramDetails.getProgramTables());
        newIntent = new Intent(getContext(), ProgramActivity.class);
        newIntent.putExtras(newIntentBundle);
        startActivity(newIntent);

    }

    @Override
    public void onLikeClicked(boolean isLiked, int position) {
        final UserProgramDetails userProgramDetails = adapter.getItemAtPosition(position);
        /*if( isLiked ) {
            userProgramDetails.save(new RushCallback() {
                @Override
                public void complete() {
                    Log.d(TAG, "UserProgramDetails : liked : " + userProgramDetails.getProgramId());
                }
            });
            userProgramDetails.getProgramIndex().save(new RushCallback() {
                @Override
                public void complete() {
                    Log.d(TAG, "UserProgramDetails : liked : Program Index saved : " + userProgramDetails.getProgramId());
                }
            });
            RushCore.getInstance().save(userProgramDetails.getProgramTables(), new RushCallback() {
                @Override
                public void complete() {
                    Log.d(TAG, "UserProgramDetails : liked : Program Tables saved : " + userProgramDetails.getProgramId());
                }
            });
        }
        else {

            new RushSearch().whereEqual("programId", userProgramDetails.getProgramId()).find(UserProgramDetails.class, new RushSearchCallback<UserProgramDetails>() {
                @Override
                public void complete(List<UserProgramDetails> list) {
                    for ( UserProgramDetails programDetails : list ) {
                        programDetails.delete(new RushCallback() {
                            @Override
                            public void complete() {
                                Log.d(TAG, "UserProgramDetails : unliked : " + userProgramDetails.getProgramId());
                            }
                        });
                    }
                }
            });
            new RushSearch().whereEqual("userProgramId", userProgramDetails.getProgramId()).find(ProgramIndex.class, new RushSearchCallback<ProgramIndex>() {
                @Override
                public void complete(List<ProgramIndex> list) {
                    for ( ProgramIndex programIndex : list ) {
                        programIndex.delete(new RushCallback() {
                            @Override
                            public void complete() {
                                Log.d(TAG, "UserProgramDetails : unliked : " + userProgramDetails.getProgramId());
                            }
                        });
                    }
                }
            });
            new RushSearch().whereEqual("userProgramId", userProgramDetails.getProgramId()).find(ProgramTable.class, new RushSearchCallback<ProgramTable>() {
                @Override
                public void complete(List<ProgramTable> list) {
                    for ( ProgramTable programTable : list ) {
                        programTable.delete(new RushCallback() {
                            @Override
                            public void complete() {
                                Log.d(TAG, "UserProgramDetails : unliked : " + userProgramDetails.getProgramId());
                            }
                        });
                    }
                }
            });
        }*/
        new FirebaseDatabaseHandler(getContext()).updateLikes(isLiked, userProgramDetails);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onShareClicked(int position) {
        UserProgramDetails userProgramDetails = adapter.getItemAtPosition(position);
        String shareString = userProgramDetails.getShareString();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareString + "\nCheck out this app : \n" + getString(R.string.app_url));
        startActivity(Intent.createChooser(shareIntent, "Share Program : " + userProgramDetails.getProgramIndex().getProgram_Description()));
    }

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

    public static UserProgramsFragment newInstance(String c) {
        UserProgramsFragment userProgramsFragment = new UserProgramsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Language", c);
        userProgramsFragment.setArguments(bundle);
        return userProgramsFragment;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        language = parent.getSelectedItem().toString();
        fetchUserPrograms(accessSpecifier);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

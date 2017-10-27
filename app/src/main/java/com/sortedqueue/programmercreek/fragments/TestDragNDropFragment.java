package com.sortedqueue.programmercreek.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.ProgramListActivity;
import com.sortedqueue.programmercreek.adapter.DragNDropAdapter;
import com.sortedqueue.programmercreek.adapter.SubTestPagerAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.DragListenerInterface;
import com.sortedqueue.programmercreek.interfaces.DropListenerInterface;
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener;
import com.sortedqueue.programmercreek.interfaces.RemoveListenerInterface;
import com.sortedqueue.programmercreek.interfaces.SubTestCommunicationListener;
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekAnalytics;
import com.sortedqueue.programmercreek.view.DragNDropListView;
import com.sortedqueue.programmercreek.view.ScrollableViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Alok on 03/01/17.
 */

public class TestDragNDropFragment extends Fragment implements UIUpdateListener, TestCompletionListener, SubTestCommunicationListener {

    ProgramIndex mProgramIndex;
    ArrayList<String> mRandomTest;
    ArrayList<String> mProgramList;
    ArrayList<String> mProgramCheckList;
    long remainingTime = 0;
    long time = 0;
    long interval = 0;
    CountDownTimer mCountDownTimer;
    boolean mWizard = false;

    @BindView(R.id.checkQuizButton)
    Button checkQuizButton;
    @BindView(R.id.circular_progress_bar)
    ProgressBar circularProgressBar;
    @BindView(R.id.progressTextView)
    TextView progressTextView;
    @BindView(R.id.progressLayout)
    FrameLayout progressLayout;
    @BindView(R.id.containerPager)
    ScrollableViewPager containerPager;
    @BindView(R.id.timerButton)
    Button timerButton;
    @BindView(R.id.testTabLayout)
    TabLayout testTabLayout;
    private InterstitialAd interstitialAd;
    private DragNDropListView dragNDropListView;
    private DragNDropAdapter dragNDropAdapter;
    private int programSize;
    private View view;
    private Bundle bundle;
    private CreekUserStats creekUserStats;
    private ArrayList<ProgramTable>[] programTableArray;
    private SubTestPagerAdapter subTestPagerAdapter;
    private ArrayList<ProgramTable> mProgramTableList;
    private ModuleDetailsScrollPageListener moduleDetailsScrollPageListener;
    private String TAG = TestDragNDropFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_test_drag_n_drop, container, false);
        ButterKnife.bind(this, view);
        dragNDropListView = (DragNDropListView) view.findViewById(R.id.dragNDropListView);

        handleBundle();

        MobileAds.initialize(getApplicationContext(), getString(R.string.mobile_banner_id));
        interstitialAd = new InterstitialAd(getContext());
        interstitialAd.setAdUnitId(getString(R.string.interstital_wiki_ad_id));
        requestNewInterstitial();
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                getActivity().finish();
            }
        });

        return view;
    }

    public ArrayList<String> getmProgramList() {
        ArrayList<String> programList = new ArrayList<>();
        for( ProgramTable programTable : mProgramTableList ) {
            programList.add(programTable.getProgram_Line());
        }
        return programList;
    }

    private void handleBundle() {
        mProgramTableList = bundle.getParcelableArrayList(ProgrammingBuddyConstants.INSTANCE.getKEY_USER_PROGRAM());

        if( mProgramTableList != null && mProgramTableList.size() > 0 ) {
            mProgramIndex = (ProgramIndex) bundle.get(ProgrammingBuddyConstants.INSTANCE.getKEY_PROG_ID());
            mWizard = bundle.getBoolean(ProgramListActivity.Companion.getKEY_WIZARD(), false);
            initUI(mProgramTableList);
        }
        else {
            if (bundle.getInt(ProgrammingBuddyConstants.INSTANCE.getKEY_INVOKE_TEST(), -1) == ProgrammingBuddyConstants.INSTANCE.getKEY_LESSON()) {
                mWizard = false;
                new FirebaseDatabaseHandler(getContext()).getProgramIndexInBackGround(bundle.getInt(ProgrammingBuddyConstants.INSTANCE.getKEY_PROG_ID()),
                        new FirebaseDatabaseHandler.GetProgramIndexListener() {
                            @Override
                            public void onSuccess(ProgramIndex programIndex) {
                                mProgramIndex = programIndex;
                                getProgramTablesInBackground();
                            }

                            @Override
                            public void onError(DatabaseError databaseError) {
                                CommonUtils.INSTANCE.displayToast(getContext(), R.string.unable_to_fetch_data);
                            }
                        });
            } else {
                mProgramIndex = (ProgramIndex) bundle.get(ProgrammingBuddyConstants.INSTANCE.getKEY_PROG_ID());
                mWizard = bundle.getBoolean(ProgramListActivity.Companion.getKEY_WIZARD());
                getProgramTables();
            }
        }



    }

    private void getProgramTablesInBackground() {
        new FirebaseDatabaseHandler(getContext())
                .getProgramTablesInBackground(mProgramIndex.getProgram_index(), new FirebaseDatabaseHandler.GetProgramTablesListener() {
                    @Override
                    public void onSuccess(ArrayList<ProgramTable> programTables) {
                        initUI(programTables);
                    }

                    @Override
                    public void onError(DatabaseError databaseError) {
                        CommonUtils.INSTANCE.displayToast(getContext(), R.string.unable_to_fetch_data);
                    }
                });

    }

    private void getProgramTables() {
        new FirebaseDatabaseHandler(getContext())
                .getProgramTablesInBackground(mProgramIndex.getProgram_index(), new FirebaseDatabaseHandler.GetProgramTablesListener() {
                    @Override
                    public void onSuccess(ArrayList<ProgramTable> programTables) {
                        ArrayList<ProgramTable> program_TableList = programTables;
                        initUI(program_TableList);
                    }

                    @Override
                    public void onError(DatabaseError databaseError) {

                    }
                });
    }

    private void initUI( ArrayList<ProgramTable> program_TableList) {
        if(  mProgramIndex != null ) {
            try {
                CreekAnalytics.INSTANCE.logEvent(TAG, new JSONObject(new Gson().toJson(mProgramIndex)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        program_TableList = ProgramTable.getMinimalisticCode(program_TableList);

        getActivity().setTitle("Test : " + mProgramIndex.getProgram_Description());
        mProgramTableList = program_TableList;
        //Split lengthy programs into modules
        programTableArray = ProgramTable.splitIntoModules(program_TableList);
        mProgramList = new ArrayList<String>();
        mProgramCheckList = new ArrayList<String>();
        String programLine = null;
        Iterator<ProgramTable> iteraor = program_TableList.iterator();
        while (iteraor.hasNext()) {
            ProgramTable newProgramTable = iteraor.next();
            programLine = newProgramTable.getProgram_Line();
            mProgramCheckList.add(programLine);
            mProgramList.add(programLine);
        }
        containerPager.setCanScroll(false);
        /*if (programTableArray.length > 1) */{
            if( programTableArray.length >  1 ) {
                AuxilaryUtils.INSTANCE.displayInformation(getContext(), R.string.divide_and_conquer, R.string.divide_and_conquer_description, new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                    setupSubTests(mProgramTableList);
                    }

                });

            }
            else {
                setupSubTests(program_TableList);
            }
        } /*else {

            containerPager.setVisibility(View.GONE);
            testTabLayout.setVisibility(View.GONE);
            dragNDropListView.setVisibility(View.VISIBLE);




            mRandomTest = new ArrayList<String>(mProgramList.size());
            for (String item : mProgramList) {
                mRandomTest.add(item);
            }

            mRandomTest = ShuffleList.shuffleList(mRandomTest);
            programSize = mRandomTest.size();
            dragNDropAdapter = new DragNDropAdapter(getContext(), new int[]{R.layout.dragitem}, new int[]{R.id.programLineTextView}, mRandomTest);
            dragNDropListView.setAdapter(dragNDropAdapter);//new DragNDropAdapter(this,content)
            //mListView.setBackgroundResource(R.drawable.error);
            dragNDropListView.setDropListener(mDropListener);
            dragNDropListView.setRemoveListener(mRemoveListener);
            dragNDropListView.setDragListener(mDragListener);

            enableTimer();
        }*/

    }

    private void setupSubTests(ArrayList<ProgramTable> program_TableList) {
        containerPager.setVisibility(View.VISIBLE);
        testTabLayout.setVisibility(View.VISIBLE);
        dragNDropListView.setVisibility(View.GONE);
        subTestPagerAdapter = new SubTestPagerAdapter(getChildFragmentManager(), programTableArray, this);
        containerPager.setAdapter(subTestPagerAdapter);
        containerPager.setOffscreenPageLimit(programTableArray.length + 1);
        testTabLayout.setupWithViewPager(containerPager);
        programSize = program_TableList.size();
        enableTimer();
    }

    private void enableTimer() {
        timerButton.setEnabled(false);
        progressLayout.setVisibility(View.GONE);
        if (bundle.getInt(ProgrammingBuddyConstants.INSTANCE.getKEY_INVOKE_TEST(), -1) != ProgrammingBuddyConstants.INSTANCE.getKEY_LESSON()) {
            progressLayout.setVisibility(View.VISIBLE);
            time = (programSize / 2) * 60 * 1000;
            interval = 1000;
            circularProgressBar.setMax((int) (time / 1000));
            if (mCountDownTimer != null) {
                mCountDownTimer.cancel();
                checkQuizButton.setEnabled(true);
            }

            mCountDownTimer = new CountDownTimer(time, interval) {

                @Override
                public void onTick(long millisUntilFinished) {
                    timerButton.setText("" + String.format("%d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    progressTextView.setText(timerButton.getText());
                    remainingTime = time - millisUntilFinished;
                    circularProgressBar.setProgress((int) (remainingTime / 1000));
                }

                @Override
                public void onFinish() {

                    timerButton.setText("Time up");
                    timerButton.setVisibility(View.VISIBLE);
                    progressLayout.setVisibility(View.GONE);
                    if (mWizard == true) {
                        timerButton.setText("Finish");
                        timerButton.setVisibility(View.VISIBLE);
                        progressLayout.setVisibility(View.GONE);
                        timerButton.setEnabled(true);
                        timerButton.setOnClickListener(mFinishBtnClickListener);
                    }

                    checkScore(programSize, dragNDropListView, mProgramList);
                }
            };
            mCountDownTimer.start();
        }


        checkQuizButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showConfirmSubmitDialog(programSize, mCountDownTimer);
            }
        });
    }

    View.OnClickListener mFinishBtnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.timerButton:
                    showAd();
                    break;
            }

        }
    };

    private void showAd() {

        if (interstitialAd != null && interstitialAd.isLoaded() /*&& CreekApplication.getCreekPreferences().getAdsEnabled()*/ ) {
            interstitialAd.show();
        } else
            getActivity().finish();
    }


    int mProgramHint = 0;

    protected void checkScore(int programLength, DragNDropListView dragNDropListView, ArrayList<String> mProgramList) {
        int programCheck = 0;
        /*if (programTableArray.length == 1) {

            ListAdapter listAdapter = dragNDropListView.getAdapter();
            for (int i = 0; i < programLength; i++) {
                String item = listAdapter.getItem(i).toString().trim();
                String actualItem = mProgramList.get(i).toString().trim();
                if (actualItem.equals(item) == true) {
                    programCheck++;
                } else {
                    programCheck--;
                }
            }
            checkProgramScore(programCheck, programLength);

        } else */{
            CodeViewFragment codeViewFragment = subTestPagerAdapter.getCodeViewFragment();
            ArrayList<String> programCode = codeViewFragment.getProgramCode();
            if( programCode != null && programCode.size() == programLength ) {
                for (int i = 0; i < programLength; i++) {
                    String item = programCode.get(i).trim();
                    String actualItem = mProgramList.get(i).toString().trim();
                    if (actualItem.equals(item) == true) {
                        programCheck++;
                    } else {
                        programCheck--;
                    }
                }
                checkProgramScore(programCheck, programLength);
            }
            else {
                CommonUtils.INSTANCE.displaySnackBar(getActivity(), "Complete the test to proceed");
            }


        }

    }

    private void checkProgramScore(int programCheck, int programLength) {
        if (programCheck < programLength) {
            displayToast("Please check the program again");
            mProgramHint++;
            return;
        }
        if (programCheck == programLength) {
            String score = null;
            String resultAlert = "";
            int maxScore = programLength / 2;

            if (mProgramHint >= maxScore) {
                score = "0 %";
                resultAlert = "Your score is " + score + " in " + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                        TimeUnit.MILLISECONDS.toSeconds(remainingTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))) + ", Good Luck next time..";
            } else if (mProgramHint == 0) {
                score = "100 %";
                resultAlert = "Congratulations, Your score is " + score + " in " + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                        TimeUnit.MILLISECONDS.toSeconds(remainingTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))) + ", Fantastic Work..!!";
            } else if (mProgramHint < maxScore) {
                score = String.format("%.2f", ((float) (maxScore - mProgramHint) / maxScore * 100)) + " %";
                resultAlert = "Congratulations, Your score is " + score + " in " + String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                        TimeUnit.MILLISECONDS.toSeconds(remainingTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))) + ", Keep Working..";
            }
            if (bundle.getInt(ProgrammingBuddyConstants.INSTANCE.getKEY_INVOKE_TEST(), -1) == ProgrammingBuddyConstants.INSTANCE.getKEY_LESSON()) {
                resultAlert = "You have scored " + score;
            }
            AuxilaryUtils.INSTANCE.displayResultAlert(getActivity(), "Test Complete", resultAlert, (int) ((float) (maxScore - mProgramHint) / maxScore * 100), 100);
            checkQuizButton.setEnabled(false);

            mQuizComplete = true;
            if( moduleDetailsScrollPageListener != null ) {
                moduleDetailsScrollPageListener.toggleFABDrawable();
            }
            updateCreekStats();
            if (mWizard == true) {
                timerButton.setText("Finish");
                timerButton.setEnabled(true);
                timerButton.setVisibility(View.VISIBLE);
                progressLayout.setVisibility(View.GONE);
                timerButton.setOnClickListener(mFinishBtnClickListener);
            }

        }
    }

    private void updateCreekStats() {
        creekUserStats = CreekApplication.getInstance().getCreekUserStats();
        if( mProgramIndex.getUserProgramId() == null || mProgramIndex.getUserProgramId().trim().length() == 0 ) {
            switch (mProgramIndex.getProgram_Language().toLowerCase()) {
                case "c":
                    creekUserStats.addToUnlockedCProgramIndexList(mProgramIndex.getProgram_index() + 1);
                    break;
                case "cpp":
                case "c++":
                    creekUserStats.addToUnlockedCppProgramIndexList(mProgramIndex.getProgram_index() + 1);
                    break;
                case "java":
                    creekUserStats.addToUnlockedJavaProgramIndexList(mProgramIndex.getProgram_index() + 1);
                    break;
                case "usp":
                    creekUserStats.addToUnlockedUspProgramIndexList(mProgramIndex.getProgram_index() + 1);
                    break;
            }
            new FirebaseDatabaseHandler(getContext()).writeCreekUserStats(creekUserStats);
        }
        else {
            if( !mProgramIndex.getUserProgramId().equals("trial") ) {
                creekUserStats.addToUnlockedUserAddedPrograms(mProgramIndex.getUserProgramId());
                new FirebaseDatabaseHandler(getContext()).writeCreekUserStats(creekUserStats);
            }
        }
    }


    private void showConfirmSubmitDialog(final int programSize, final CountDownTimer countDownTimer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkScore(programSize, dragNDropListView, mProgramList);
                if (countDownTimer != null)
                    countDownTimer.cancel();
            }

        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setMessage("Are you sure you want to submit the Match?");
        builder.setTitle(getActivity().getTitle());
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.show();

    }


    public View getViewByPosition(int position, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition) {
            return listView.getAdapter().getView(position, listView.getChildAt(position), listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    protected void displayToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }

    private DropListenerInterface mDropListener =
            new DropListenerInterface() {
                public void onDrop(int from, int to) {
                    dragNDropAdapter.onDrop(from, to);
                    dragNDropListView.invalidateViews();

                }
            };

    private RemoveListenerInterface mRemoveListener =
            new RemoveListenerInterface() {
                public void onRemove(int which) {
                    dragNDropAdapter.onRemove(which);
                    dragNDropListView.invalidateViews();
                }
            };

    private DragListenerInterface mDragListener =
            new DragListenerInterface() {

                int backgroundColor = 0xe0103010;
                int defaultBackgroundColor;

                public void onDrag(int x, int y, ListView listView) {

                }

                public void onStartDrag(View itemView) {
                    itemView.setVisibility(View.INVISIBLE);
                    defaultBackgroundColor = itemView.getDrawingCacheBackgroundColor();
                    //itemView.setBackgroundColor(backgroundColor);
                    ImageView imageView = (ImageView) itemView.findViewById(R.id.dragItemImageView);
                    if (imageView != null) imageView.setVisibility(View.INVISIBLE);
                }

                public void onStopDrag(View itemView) {
                    itemView.setVisibility(View.VISIBLE);
                    //itemView.setBackgroundColor(defaultBackgroundColor);
                    ImageView imageView = (ImageView) itemView.findViewById(R.id.dragItemImageView);
                    if (imageView != null) imageView.setVisibility(View.VISIBLE);
                }

            };

    boolean mQuizComplete = false;

    public void onBackPressed() {
        if (mQuizComplete == false) {
            AuxilaryUtils.INSTANCE.showConfirmationDialog(getActivity());
            if (mCountDownTimer != null) {
                mCountDownTimer.cancel();
            }
        } else {
            showAd();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    public void updateUI() {

        new FirebaseDatabaseHandler(getContext())
                .getProgramTablesInBackground(mProgramIndex.getProgram_index(), new FirebaseDatabaseHandler.GetProgramTablesListener() {
                    @Override
                    public void onSuccess(ArrayList<ProgramTable> programTables) {
                        ArrayList<ProgramTable> program_TableList = programTables;
                        int prevProgramSize = 0;
                        prevProgramSize = program_TableList.size();
                        do {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            program_TableList = new FirebaseDatabaseHandler(getContext()).getProgramTables(mProgramIndex.getProgram_index());
                            if (prevProgramSize == program_TableList.size()) {
                                break;
                            }
                        } while (true);

                        if (program_TableList != null && program_TableList.size() > 0) {
                            initUI(program_TableList);
                        }
                    }

                    @Override
                    public void onError(DatabaseError databaseError) {

                    }
                });


    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                .build();
        interstitialAd.loadAd(adRequest);
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public int isTestComplete() {
        return mQuizComplete ? ProgrammingBuddyConstants.INSTANCE.getKEY_MATCH() : -1;
    }

    @Override
    public void submitSubTest(int index, ArrayList<String> content) {
        CodeViewFragment codeViewFragment = subTestPagerAdapter.getCodeViewFragment();
        codeViewFragment.submitSubTest(index, content);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public void setModuleDetailsScrollPageListener(ModuleDetailsScrollPageListener moduleDetailsScrollPageListener) {
        this.moduleDetailsScrollPageListener = moduleDetailsScrollPageListener;
    }
}

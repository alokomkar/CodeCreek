package com.sortedqueue.programmercreek.fragments;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.adapter.OptionsRecyclerViewAdapter;
import com.sortedqueue.programmercreek.database.Chapter;
import com.sortedqueue.programmercreek.database.ChapterDetails;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.LanguageModule;
import com.sortedqueue.programmercreek.database.ModuleOption;
import com.sortedqueue.programmercreek.database.SyntaxModule;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener;
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener;
import com.sortedqueue.programmercreek.util.AnimationUtils;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;

/**
 * A placeholder fragment containing a simple view.
 */
public class SyntaxLearnActivityFragment extends Fragment implements View.OnClickListener, TestCompletionListener, RewardedVideoAdListener {

    @BindView(R.id.syntaxExplanationCardView)
    CardView syntaxExplanationCardView;
    @BindView(R.id.syntaxQuestionCardView)
    CardView syntaxQuestionCardView;
    @BindView(R.id.optionsCardView)
    CardView optionsCardView;
    @BindView(R.id.checkSyntaxImageView)
    ImageView checkSyntaxImageView;
    @BindView(R.id.clearSyntaxImageView)
    ImageView clearSyntaxImageView;
    @BindView(R.id.hintSyntaxImageView)
    ImageView hintSyntaxImageView;
    @BindView(R.id.voiceTypeImageView)
    ImageView voiceTypeImageView;
    @BindView(R.id.content_syntax_learn)
    RelativeLayout contentSyntaxLearn;
    @BindView(R.id.syntaxNameTextView)
    TextView syntaxNameTextView;
    @BindView(R.id.syntaxDescriptionTextView)
    TextView syntaxDescriptionTextView;
    @BindView(R.id.syntaxCommandTextView)
    TextView syntaxCommandTextView;
    @BindView(R.id.syntaxCommandOutputTextView)
    TextView syntaxCommandOutputTextView;
    @BindView(R.id.syntaxQuestionTextView)
    TextView syntaxQuestionTextView;
    @BindView(R.id.syntaxSolutionTextView)
    TextView syntaxSolutionTextView;
    @BindView(R.id.syntaxQuestionOutputTextView)
    TextView syntaxQuestionOutputTextView;
    @BindView(R.id.optionsRecyclerView)
    RecyclerView optionsRecyclerView;
    @BindView(R.id.checkButtonLayout)
    LinearLayout checkButtonLayout;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.programCodeView)
    CodeView programCodeView;
    @BindView(R.id.resultTextView)
    TextView resultTextView;
    @BindView(R.id.proceedTextView)
    TextView proceedTextView;
    @BindView(R.id.resultLayout)
    RelativeLayout resultLayout;
    private SyntaxModule syntaxModule;
    private List<ModuleOption> moduleOptions;
    private String TAG = SyntaxLearnActivityFragment.class.getSimpleName();
    private ModuleDetailsScrollPageListener modulteDetailsScrollPageListener;
    private boolean isLastFragment;
    private LanguageModule nextModule;
    private String wizardUrl = null;
    private String syntaxId;
    private boolean isAnswered = false;
    private Chapter nextChapter;
    private String programLanguage;

    public SyntaxLearnActivityFragment() {
    }

    private RewardedVideoAd rewardedVideoAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_syntax_learn, container, false);
        ButterKnife.bind(this, view);
        programLanguage = CreekApplication.getCreekPreferences().getProgramLanguage();
        //initializeRewardedVideoAd();
        if (programLanguage.equals("c++")) {
            programLanguage = "cpp";
        }
        if (wizardUrl == null) {
            bindData(syntaxModule);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            new FirebaseDatabaseHandler(getContext()).getSyntaxModule(syntaxId, wizardUrl,
                    new FirebaseDatabaseHandler.SyntaxModuleInterface() {
                        @Override
                        public void onSuccess(SyntaxModule syntaxModule) {
                            SyntaxLearnActivityFragment.this.syntaxModule = syntaxModule;
                            bindData(syntaxModule);
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(DatabaseError error) {
                            progressBar.setVisibility(View.GONE);
                            CommonUtils.displayToast(getContext(), R.string.unable_to_fetch_data);
                        }
                    });
        }
        proceedTextView.setOnClickListener(this);
        return view;
    }

    private void initializeRewardedVideoAd() {
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getActivity());
        rewardedVideoAd.setRewardedVideoAdListener(this);
        if (!rewardedVideoAd.isLoaded()) {
            rewardedVideoAd.loadAd(getString(R.string.hint_rewarded_interstital_ad), new AdRequest.Builder().build());
        }
    }

    @Override
    public void onResume() {
        //rewardedVideoAd.resume(getContext());
        super.onResume();
    }

    @Override
    public void onPause() {
        //rewardedVideoAd.pause(getContext());
        super.onPause();
    }

    @Override
    public void onDestroy() {
        //rewardedVideoAd.destroy(getContext());
        super.onDestroy();
    }

    private void bindData(SyntaxModule syntaxModule) {
        syntaxNameTextView.setText(syntaxModule.getSyntaxName());
        syntaxDescriptionTextView.setText(syntaxModule.getSyntaxDescription());
        syntaxCommandTextView.setText(syntaxModule.getSyntaxCommand());
        syntaxCommandOutputTextView.setText(syntaxModule.getSyntaxCommandOutput());
        syntaxQuestionTextView.setText(syntaxModule.getSyntaxQuestion());
        syntaxSolutionTextView.setText("");
        setCodeView(syntaxSolutionTextView.getText().toString());
        syntaxQuestionOutputTextView.setText("Expected Output : " + syntaxModule.getSyntaxQuestionOutput());
        if (syntaxModule.getSyntaxOptions() != null) {
            setupRecyclerView(syntaxModule.getSyntaxOptions());
            Log.d(TAG, "SyntaxModule : " + syntaxModule.toString());
        }
        checkSyntaxImageView.setOnClickListener(this);
        clearSyntaxImageView.setOnClickListener(this);
        hintSyntaxImageView.setOnClickListener(this);
        voiceTypeImageView.setOnClickListener(this);
    }

    private void setCodeView(String programLines) {
        programCodeView
                .setOptions(Options.Default.get(getContext())
                        .withLanguage(programLanguage)
                        .withCode(programLines)
                        .withTheme(ColorTheme.SOLARIZED_LIGHT));
    }

    private ArrayList<String> solutionList = new ArrayList<>();

    private void setupRecyclerView(List<ModuleOption> syntaxOptions) {
        moduleOptions = new ArrayList<>();
        for (ModuleOption moduleOption : syntaxOptions) {
            if (moduleOption.getOption().trim().length() != 0) {
                moduleOptions.add(moduleOption);
            }
        }
        Log.d(TAG, "Module Options : " + moduleOptions.toString());
        optionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        optionsRecyclerView.setAdapter(new OptionsRecyclerViewAdapter(getContext(), moduleOptions, new CustomProgramRecyclerViewAdapter.AdapterClickListner() {
            @Override
            public void onItemClick(int position) {
                solutionList.add(moduleOptions.get(position).getOption());
                syntaxSolutionTextView.setText(getSolution(solutionList));
                setCodeView(syntaxSolutionTextView.getText().toString());
            }
        }));

        // No question in the module : Toggle views
        int visibility = syntaxOptions.size() > 1 ? View.VISIBLE : View.GONE;
        optionsCardView.setVisibility(visibility);
        syntaxQuestionCardView.setVisibility(visibility);
        checkSyntaxImageView.setVisibility(visibility);
        clearSyntaxImageView.setVisibility(visibility);
        hintSyntaxImageView.setVisibility(visibility);
        //voiceTypeImageView.setVisibility(visibility);
        isAnswered = syntaxOptions.size() == 1;
        if (isAnswered)
            updateCreekUserStats();

    }

    private String getSolution(ArrayList<String> solutionList) {
        String solution = "";
        for (String solutionString : solutionList) {
            solution += solutionString;
        }
        return solution;
    }

    public void setSyntaxModule(SyntaxModule syntaxModule) {
        this.syntaxModule = syntaxModule;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clearSyntaxImageView:
                if (solutionList.size() == 0) {
                    syntaxSolutionTextView.setText("");
                    setCodeView("");
                } else {
                    solutionList.remove(solutionList.size() - 1);
                    syntaxSolutionTextView.setText(getSolution(solutionList));
                    setCodeView(syntaxSolutionTextView.getText().toString());
                }
                break;
            case R.id.checkSyntaxImageView:
                checkSolution();
                break;
            case R.id.hintSyntaxImageView:
                /*if (rewardedVideoAd.isLoaded()) {
                    rewardedVideoAd.show();
                }*/
                showHint();
                break;
            case R.id.voiceTypeImageView:
                openVoiceIntent();
                break;
            case R.id.proceedTextView:
                AnimationUtils.slideDown(resultLayout);
                if (modulteDetailsScrollPageListener != null) {
                    modulteDetailsScrollPageListener.onScrollForward();
                }
                break;
        }
    }

    private static final int SPEECH_REQUEST = 9878;

    private void openVoiceIntent() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        try {
            startActivityForResult(intent, SPEECH_REQUEST);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            AuxilaryUtils.displayAlert("No Voice App", "Your device doesn't support voice input - no app found", getContext());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST && resultCode == AppCompatActivity.RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            CommonUtils.displayToast(getContext(), "I heard : " + results);
            if (spokenText.equalsIgnoreCase("print formatted")) {
                solutionList.add("printf(");
                syntaxSolutionTextView.setText(getSolution(solutionList));
                setCodeView(syntaxSolutionTextView.getText().toString());
            }
        }
    }

    private void checkSolution() {
        String solutionText = syntaxSolutionTextView.getText().toString();
        if (solutionText.trim().replaceAll("\\s+", "").equals(syntaxModule.getSyntaxSolution().trim().replaceAll("\\s+", ""))) {
            AnimationUtils.slideUp(resultLayout);
            CommonUtils.displaySnackBar(getActivity(), R.string.congratulations, R.string.proceed, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            syntaxQuestionOutputTextView.setText(syntaxModule.getSyntaxQuestionOutput());
            syntaxQuestionOutputTextView.setTextColor(Color.GREEN);
            isAnswered = true;
            updateCreekUserStats();

        } else {
            //Snackbar.make(getActivity().findViewById(android.R.id.content), "Check the syntax again", Snackbar.LENGTH_LONG).show();
            syntaxQuestionOutputTextView.setText("Error..!!");
            syntaxQuestionOutputTextView.setTextColor(Color.RED);
        }
    }

    private void updateCreekUserStats() {
        CreekUserStats creekUserStats = CreekApplication.getInstance().getCreekUserStats();
        switch (syntaxModule.getSyntaxLanguage().toLowerCase()) {
            case "c":
                creekUserStats.addToUnlockedCLanguageModuleIdList(syntaxModule.getModuleId());
                creekUserStats.addToUnlockedCSyntaxModuleIdList(syntaxModule.getModuleId() + "_" + syntaxModule.getSyntaxModuleId());
                if (isLastFragment) {
                    if (nextModule != null) {
                        if (creekUserStats.addToUnlockedCLanguageModuleIdList(nextModule.getModuleId())) {
                            AuxilaryUtils.displayAchievementUnlockedDialog(getActivity(), "Congratulations..!!", getString(R.string.new_module_unlocked));
                        }

                        //CommonUtils.displaySnackBar(getActivity(), R.string.new_module_unlocked);
                    } else if (nextChapter != null) {
                        if (creekUserStats.addToUnlockedCLanguageModuleIdList(nextChapter.getChapterDetailsArrayList().get(0).getSyntaxId())) {
                            AuxilaryUtils.displayAchievementUnlockedDialog(getActivity(), "Congratulations..!!", getString(R.string.new_chapter_unlocked));
                        }
                        //CommonUtils.displaySnackBar(getActivity(), R.string.new_chapter_unlocked);
                    }
                }

                break;
            case "cpp":
            case "c++":
                creekUserStats.addToUnlockedCppLanguageModuleIdList(syntaxModule.getModuleId());
                creekUserStats.addToUnlockedCppSyntaxModuleIdList(syntaxModule.getModuleId() + "_" + syntaxModule.getSyntaxModuleId());
                if (isLastFragment) {
                    if (nextModule != null) {
                        if (creekUserStats.addToUnlockedCppLanguageModuleIdList(nextModule.getModuleId())) {
                            AuxilaryUtils.displayAchievementUnlockedDialog(getActivity(), "Congratulations..!!", getString(R.string.new_module_unlocked));
                        }
                        //CommonUtils.displaySnackBar(getActivity(), R.string.new_module_unlocked);
                    } else if (nextChapter != null) {
                        if (creekUserStats.addToUnlockedCppLanguageModuleIdList(nextChapter.getChapterDetailsArrayList().get(0).getSyntaxId()))
                            AuxilaryUtils.displayAchievementUnlockedDialog(getActivity(), "Congratulations..!!", getString(R.string.new_chapter_unlocked));
                        //CommonUtils.displaySnackBar(getActivity(), R.string.new_chapter_unlocked);
                    }
                }
                break;
            case "java":
                creekUserStats.addToUnlockedJavaLanguageModuleIdList(syntaxModule.getModuleId());
                creekUserStats.addToUnlockedJavaSyntaxModuleIdList(syntaxModule.getModuleId() + "_" + syntaxModule.getSyntaxModuleId());
                if (isLastFragment) {
                    if (nextModule != null) {
                        if (creekUserStats.addToUnlockedJavaLanguageModuleIdList(nextModule.getModuleId()))
                            AuxilaryUtils.displayAchievementUnlockedDialog(getActivity(), "Congratulations..!!", getString(R.string.new_module_unlocked));
                        //CommonUtils.displaySnackBar(getActivity(), R.string.new_module_unlocked);
                    } else if (nextChapter != null) {
                        if (creekUserStats.addToUnlockedJavaLanguageModuleIdList(nextChapter.getChapterDetailsArrayList().get(0).getSyntaxId()))
                            AuxilaryUtils.displayAchievementUnlockedDialog(getActivity(), "Congratulations..!!", getString(R.string.new_chapter_unlocked));
                        //CommonUtils.displaySnackBar(getActivity(), R.string.new_chapter_unlocked);
                    }
                }
                break;
            case "sql":
                creekUserStats.addToUnlockedSqlLanguageModuleIdList(syntaxModule.getModuleId());
                creekUserStats.addToUnlockedSqlSyntaxModuleIdList(syntaxModule.getModuleId() + "_" + syntaxModule.getSyntaxModuleId());
                if (isLastFragment) {
                    if (nextModule != null) {
                        if (creekUserStats.addToUnlockedSqlLanguageModuleIdList(nextModule.getModuleId()))
                            AuxilaryUtils.displayAchievementUnlockedDialog(getActivity(), "Congratulations..!!", getString(R.string.new_module_unlocked));
                        //CommonUtils.displaySnackBar(getActivity(), R.string.new_module_unlocked);
                    } else if (nextChapter != null) {
                        if (creekUserStats.addToUnlockedSqlLanguageModuleIdList(nextChapter.getChapterDetailsArrayList().get(0).getSyntaxId()))
                            AuxilaryUtils.displayAchievementUnlockedDialog(getActivity(), "Congratulations..!!", getString(R.string.new_chapter_unlocked));
                        //CommonUtils.displaySnackBar(getActivity(), R.string.new_chapter_unlocked);
                    }
                }
                break;
        }
        new FirebaseDatabaseHandler(getContext()).writeCreekUserStats(creekUserStats);

    }

    public void setModulteDetailsScrollPageListener(ModuleDetailsScrollPageListener modulteDetailsScrollPageListener) {
        this.modulteDetailsScrollPageListener = modulteDetailsScrollPageListener;
    }

    public void setIsLastFragment(boolean isLastFragment, LanguageModule nextModule) {
        this.isLastFragment = isLastFragment;
        this.nextModule = nextModule;
    }

    public void setIsLastFragment(boolean isLastFragment, Chapter nextChapter) {
        this.isLastFragment = isLastFragment;
        this.nextChapter = nextChapter;
    }

    public void setSyntaxModule(String syntaxId, String wizardUrl) {
        this.wizardUrl = wizardUrl;
        this.syntaxId = syntaxId;
    }

    @Override
    public int isTestComplete() {

        return isAnswered ? ChapterDetails.TYPE_SYNTAX_MODULE : -1;
    }


    //RewardedVideoAdListener methods :

    @Override
    public void onRewardedVideoAdLoaded() {
        Log.d(TAG, "onRewardedVideoAdLoaded");
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Log.d(TAG, "onRewardedVideoAdOpened");
    }

    @Override
    public void onRewardedVideoStarted() {
        Log.d(TAG, "onRewardedVideoStarted");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Log.d(TAG, "onRewardedVideoAdClosed");
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        if (rewardItem != null) {
            Log.d(TAG, "onRewarded : " + rewardItem.getAmount() + " : " + rewardItem.getType());
            showHint();
        }
    }

    private void showHint() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("Got it", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setMessage(syntaxModule.getSyntaxSolution());
        builder.setTitle("Solution :");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.show();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Log.d(TAG, "onRewardedVideoAdLoaded");
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Log.d(TAG, "onRewardedVideoAdLoaded");
    }
}

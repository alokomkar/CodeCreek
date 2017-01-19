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
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;

/**
 * A placeholder fragment containing a simple view.
 */
public class SyntaxLearnActivityFragment extends Fragment implements View.OnClickListener, TestCompletionListener {

    @Bind(R.id.syntaxExplanationCardView)
    CardView syntaxExplanationCardView;
    @Bind(R.id.syntaxQuestionCardView)
    CardView syntaxQuestionCardView;
    @Bind(R.id.optionsCardView)
    CardView optionsCardView;
    @Bind(R.id.checkSyntaxImageView)
    ImageView checkSyntaxImageView;
    @Bind(R.id.clearSyntaxImageView)
    ImageView clearSyntaxImageView;
    @Bind(R.id.hintSyntaxImageView)
    ImageView hintSyntaxImageView;
    @Bind(R.id.voiceTypeImageView)
    ImageView voiceTypeImageView;
    @Bind(R.id.content_syntax_learn)
    RelativeLayout contentSyntaxLearn;
    @Bind(R.id.syntaxNameTextView)
    TextView syntaxNameTextView;
    @Bind(R.id.syntaxDescriptionTextView)
    TextView syntaxDescriptionTextView;
    @Bind(R.id.syntaxCommandTextView)
    TextView syntaxCommandTextView;
    @Bind(R.id.syntaxCommandOutputTextView)
    TextView syntaxCommandOutputTextView;
    @Bind(R.id.syntaxQuestionTextView)
    TextView syntaxQuestionTextView;
    @Bind(R.id.syntaxSolutionTextView)
    TextView syntaxSolutionTextView;
    @Bind(R.id.syntaxQuestionOutputTextView)
    TextView syntaxQuestionOutputTextView;
    @Bind(R.id.optionsRecyclerView)
    RecyclerView optionsRecyclerView;
    @Bind(R.id.checkButtonLayout)
    LinearLayout checkButtonLayout;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.progressBar)
    ContentLoadingProgressBar progressBar;
    @Bind(R.id.programCodeView)
    CodeView programCodeView;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_syntax_learn, container, false);
        ButterKnife.bind(this, view);
        programLanguage = new CreekPreferences(getContext()).getProgramLanguage();
        if( programLanguage.equals("c++") ) {
            programLanguage = "cpp";
        }
        if( wizardUrl == null ) {
            bindData(syntaxModule);
        }
        else {
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

        return view;
    }

    private void bindData(SyntaxModule syntaxModule) {
        syntaxNameTextView.setText(syntaxModule.getSyntaxName());
        syntaxDescriptionTextView.setText(syntaxModule.getSyntaxDescription());
        /*if(Build.VERSION.SDK_INT >= 24 ) {
            syntaxCommandTextView.setText(Html.fromHtml(PrettifyHighlighter.getInstance().highlight("cpp", syntaxModule.getSyntaxCommand()), Html.FROM_HTML_MODE_LEGACY));
        }
        else {
            syntaxCommandTextView.setText(Html.fromHtml(PrettifyHighlighter.getInstance().highlight("cpp", syntaxModule.getSyntaxCommand())));
        }*/
        syntaxCommandTextView.setText(syntaxModule.getSyntaxCommand());
        syntaxCommandOutputTextView.setText(syntaxModule.getSyntaxCommandOutput());
        syntaxQuestionTextView.setText(syntaxModule.getSyntaxQuestion());
        syntaxSolutionTextView.setText("");
        setCodeView( syntaxSolutionTextView.getText().toString() );
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
        programCodeView.setOptions(Options.Default.get(getContext())
                .withLanguage(programLanguage)
                .withCode(programLines)
                .withTheme(ColorTheme.MONOKAI));
    }

    private ArrayList<String> solutionList = new ArrayList<>();

    private void setupRecyclerView(List<ModuleOption> syntaxOptions) {
        moduleOptions = syntaxOptions;
        Log.d(TAG, "Module Options : " + syntaxOptions.toString());
        optionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        optionsRecyclerView.setAdapter(new OptionsRecyclerViewAdapter(getContext(), syntaxOptions, new CustomProgramRecyclerViewAdapter.AdapterClickListner() {
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
        isAnswered = syntaxOptions.size() == 1  ;
        if( isAnswered )
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
        ButterKnife.unbind(this);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setPositiveButton("Got it", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setMessage(syntaxModule.getSyntaxSolution());
                builder.setTitle("Solution :");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.show();
                break;
            case R.id.voiceTypeImageView :
                openVoiceIntent();
                break;
        }
    }

    private static final int SPEECH_REQUEST = 9878;
    private void openVoiceIntent() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        try {
            startActivityForResult(intent, SPEECH_REQUEST);
        } catch ( ActivityNotFoundException e ) {
            e.printStackTrace();
            AuxilaryUtils.displayAlert("No Voice App", "Your device doesn't support voice input - no app found", getContext());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == SPEECH_REQUEST && resultCode == AppCompatActivity.RESULT_OK ) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            CommonUtils.displayToast(getContext(), "I heard : " + results);
            if( spokenText.equalsIgnoreCase("print formatted") ) {
                solutionList.add("printf(");
                syntaxSolutionTextView.setText(getSolution(solutionList));
                setCodeView(syntaxSolutionTextView.getText().toString());
            }
        }
    }

    private void checkSolution() {
        String solutionText = syntaxSolutionTextView.getText().toString();
        if (solutionText.trim().replaceAll("\\s+", "").equals(syntaxModule.getSyntaxSolution().trim().replaceAll("\\s+", ""))) {
            CommonUtils.displaySnackBar(getActivity(), R.string.congratulations, R.string.proceed, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if( modulteDetailsScrollPageListener != null ) {
                        modulteDetailsScrollPageListener.onScrollForward();
                    }
                }
            });
            syntaxQuestionOutputTextView.setText(syntaxModule.getSyntaxQuestionOutput());
            syntaxQuestionOutputTextView.setTextColor(Color.GREEN);
            isAnswered = true;
            updateCreekUserStats();

        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Check the syntax again", Snackbar.LENGTH_LONG).show();
            syntaxQuestionOutputTextView.setText("Error..!!");
            syntaxQuestionOutputTextView.setTextColor(Color.RED);
        }
    }

    private void updateCreekUserStats() {
        CreekUserStats creekUserStats = CreekApplication.getInstance().getCreekUserStats();
        switch ( syntaxModule.getSyntaxLanguage().toLowerCase() ) {
            case "c":
                creekUserStats.addToUnlockedCLanguageModuleIdList(syntaxModule.getModuleId());
                creekUserStats.addToUnlockedCSyntaxModuleIdList(syntaxModule.getModuleId() + "_" + syntaxModule.getSyntaxModuleId());
                if( isLastFragment ) {
                    if( nextModule != null ) {
                        creekUserStats.addToUnlockedCLanguageModuleIdList(nextModule.getModuleId());
                        CommonUtils.displaySnackBar(getActivity(), R.string.new_module_unlocked);
                    }
                    else if( nextChapter != null ) {
                        creekUserStats.addToUnlockedCLanguageModuleIdList(nextChapter.getChapterDetailsArrayList().get(0).getSyntaxId());
                        CommonUtils.displaySnackBar(getActivity(), R.string.new_chapter_unlocked);
                    }
                }

                break;
            case "cpp":
            case "c++":
                creekUserStats.addToUnlockedCppLanguageModuleIdList(syntaxModule.getModuleId());
                creekUserStats.addToUnlockedCppSyntaxModuleIdList(syntaxModule.getModuleId() + "_" + syntaxModule.getSyntaxModuleId());
                if( isLastFragment ) {
                    if( nextModule != null ) {
                        creekUserStats.addToUnlockedCppLanguageModuleIdList(nextModule.getModuleId());
                        CommonUtils.displaySnackBar(getActivity(), R.string.new_module_unlocked);
                    }
                    else if( nextChapter != null ) {
                        creekUserStats.addToUnlockedCppLanguageModuleIdList(nextChapter.getChapterDetailsArrayList().get(0).getSyntaxId());
                        CommonUtils.displaySnackBar(getActivity(), R.string.new_chapter_unlocked);
                    }
                }
                break;
            case "java":
                creekUserStats.addToUnlockedCppLanguageModuleIdList(syntaxModule.getModuleId());
                creekUserStats.addToUnlockedCppSyntaxModuleIdList(syntaxModule.getModuleId() + "_" + syntaxModule.getSyntaxModuleId());
                if( isLastFragment ) {
                    if( nextModule != null ) {
                        creekUserStats.addToUnlockedJavaLanguageModuleIdList(nextModule.getModuleId());
                        CommonUtils.displaySnackBar(getActivity(), R.string.new_module_unlocked);
                    }
                    else if( nextChapter != null ) {
                        creekUserStats.addToUnlockedJavaLanguageModuleIdList(nextChapter.getChapterDetailsArrayList().get(0).getSyntaxId());
                        CommonUtils.displaySnackBar(getActivity(), R.string.new_chapter_unlocked);
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
}

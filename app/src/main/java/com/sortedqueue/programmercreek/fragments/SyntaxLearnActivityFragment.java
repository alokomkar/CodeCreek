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

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.adapter.OptionsRecyclerViewAdapter;
import com.sortedqueue.programmercreek.database.ModuleOption;
import com.sortedqueue.programmercreek.database.SyntaxModule;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class SyntaxLearnActivityFragment extends Fragment implements View.OnClickListener {

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
    private SyntaxModule syntaxModule;
    private ArrayList<ModuleOption> moduleOptions;
    private String TAG = SyntaxLearnActivityFragment.class.getSimpleName();
    private ModuleDetailsScrollPageListener modulteDetailsScrollPageListener;
    private boolean isLastFragment;
    private String wizardUrl = null;

    public SyntaxLearnActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_syntax_learn, container, false);
        ButterKnife.bind(this, view);
        if( wizardUrl == null ) {
            bindData(syntaxModule);
        }
        else {
            new FirebaseDatabaseHandler(getContext()).getSyntaxModule(wizardUrl, new FirebaseDatabaseHandler.SyntaxModuleInterface() {
                @Override
                public void onSuccess(SyntaxModule syntaxModule) {
                    SyntaxLearnActivityFragment.this.syntaxModule = syntaxModule;
                    bindData(syntaxModule);
                }
            });
        }

        return view;
    }

    private void bindData(SyntaxModule syntaxModule) {
        syntaxNameTextView.setText(syntaxModule.getSyntaxName());
        syntaxDescriptionTextView.setText(syntaxModule.getSyntaxDescription());
        syntaxCommandTextView.setText(syntaxModule.getSyntaxCommand());
        syntaxCommandOutputTextView.setText(syntaxModule.getSyntaxCommandOutput());
        syntaxQuestionTextView.setText(syntaxModule.getSyntaxQuestion());
        syntaxSolutionTextView.setText("");
        syntaxQuestionOutputTextView.setText("Expected Output : " + syntaxModule.getSyntaxQuestionOutput());
        if (syntaxModule.getSyntaxOptions() != null) {
            setupRecyclerView(syntaxModule.getSyntaxOptions());
        }
        checkSyntaxImageView.setOnClickListener(this);
        clearSyntaxImageView.setOnClickListener(this);
        hintSyntaxImageView.setOnClickListener(this);
        voiceTypeImageView.setOnClickListener(this);
    }

    private ArrayList<String> solutionList = new ArrayList<>();

    private void setupRecyclerView(ArrayList<ModuleOption> syntaxOptions) {
        moduleOptions = syntaxOptions;
        Log.d(TAG, "Module Options : " + syntaxOptions.toString());
        optionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        optionsRecyclerView.setAdapter(new OptionsRecyclerViewAdapter(getContext(), syntaxOptions, new CustomProgramRecyclerViewAdapter.AdapterClickListner() {
            @Override
            public void onItemClick(int position) {
                solutionList.add(moduleOptions.get(position).getOption());
                syntaxSolutionTextView.setText(getSolution(solutionList));
            }
        }));

        // No question in the module : Toggle views
        int visibility = syntaxOptions.size() > 1 ? View.VISIBLE : View.GONE;
        optionsCardView.setVisibility(visibility);
        syntaxQuestionCardView.setVisibility(visibility);
        checkSyntaxImageView.setVisibility(visibility);
        clearSyntaxImageView.setVisibility(visibility);
        hintSyntaxImageView.setVisibility(visibility);
        voiceTypeImageView.setVisibility(visibility);

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
                } else {
                    solutionList.remove(solutionList.size() - 1);
                    syntaxSolutionTextView.setText(getSolution(solutionList));
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
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
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
            if( spokenText.equalsIgnoreCase("print formatted") ) {
                solutionList.add("printf(");
                syntaxSolutionTextView.setText(getSolution(solutionList));
            }
        }
    }

    private void checkSolution() {
        String solutionText = syntaxSolutionTextView.getText().toString();
        if (solutionText.trim().replaceAll("\\s+", "").equals(syntaxModule.getSyntaxSolution().trim().replaceAll("\\s+", ""))) {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Congratulations, You've got it right", Snackbar.LENGTH_LONG).show();
            syntaxQuestionOutputTextView.setText(syntaxModule.getSyntaxQuestionOutput());
            syntaxQuestionOutputTextView.setTextColor(Color.GREEN);
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Check the syntax again", Snackbar.LENGTH_LONG).show();
            syntaxQuestionOutputTextView.setText("Error..!!");
            syntaxQuestionOutputTextView.setTextColor(Color.RED);
        }
    }

    public void setModulteDetailsScrollPageListener(ModuleDetailsScrollPageListener modulteDetailsScrollPageListener) {
        this.modulteDetailsScrollPageListener = modulteDetailsScrollPageListener;
    }

    public void setIsLastFragment(boolean isLastFragment) {
        this.isLastFragment = isLastFragment;
    }

    public void setSyntaxModule(String wizardUrl) {
        this.wizardUrl = wizardUrl;
    }
}

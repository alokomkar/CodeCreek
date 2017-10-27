package com.sortedqueue.programmercreek.fragments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CodeEditorRecyclerAdapter;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.adapter.LanguageRecyclerAdapter;
import com.sortedqueue.programmercreek.constants.LanguageConstants;
import com.sortedqueue.programmercreek.database.firebase.Code;
import com.sortedqueue.programmercreek.database.firebase.CodeOutputResponse;
import com.sortedqueue.programmercreek.database.firebase.IdResponse;
import com.sortedqueue.programmercreek.interfaces.retrofit.SubmitCodeService;
import com.sortedqueue.programmercreek.network.RetrofitCreator;
import com.sortedqueue.programmercreek.util.AnimationUtils;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.FileUtils;
import com.sortedqueue.programmercreek.util.PermissionUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alok on 04/04/17.
 */

public class CompileCodeFragment extends Fragment implements View.OnClickListener, CustomProgramRecyclerViewAdapter.AdapterClickListner {

    @BindView(R.id.inputEditText)
    EditText inputEditText;
    @BindView(R.id.progressImageView)
    ImageView progressImageView;
    @BindView(R.id.progressTextView)
    TextView progressTextView;
    @BindView(R.id.compilerProgressLayout)
    RelativeLayout compilerProgressLayout;
    @BindView(R.id.codeEditRecyclerView)
    RecyclerView codeEditRecyclerView;
    @BindView(R.id.languageTextView)
    TextView languageTextView;
    @BindView(R.id.importFromFileTextView)
    TextView importFromFileTextView;
    @BindView(R.id.languageRecyclerView)
    RecyclerView languageRecyclerView;
    @BindView(R.id.importLayout)
    LinearLayout importLayout;
    @BindView(R.id.outputLayout)
    FrameLayout outputLayout;
    @BindView(R.id.outputTextView)
    TextView outputTextView;
    @BindView(R.id.dividerView)
    View dividerView;

    private SubmitCodeService submitCodeService;
    private String TAG = CompileCodeFragment.class.getSimpleName();
    private Code code;
    private CodeEditorRecyclerAdapter codeEditorRecyclerAdapter;
    private ArrayList<String> languages;
    private ArrayList<String> inputList;
    private LanguageRecyclerAdapter languageRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compile_code, container, false);
        ButterKnife.bind(this, view);
        inputList = new ArrayList<>();
        compilerProgressLayout.setVisibility(View.GONE);
        languageTextView.setOnClickListener(this);
        importFromFileTextView.setOnClickListener(this);
        setupRecyclerView();
        setupLanguageRecyclerView();
        submitCodeService = RetrofitCreator.createService(SubmitCodeService.class);
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    inputList.add(s.toString());
                }
            }
        });
        importLayout.setVisibility(fromWiki ? View.GONE : View.VISIBLE);
        if (fromWiki) {
            setupRecyclerView();
        }
        return view;
    }

    private void setupLanguageRecyclerView() {
        languageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        languages = new ArrayList<>();
        languages.add("C");
        languages.add("C++");
        languages.add("Java");
        languageRecyclerAdapter = new LanguageRecyclerAdapter(languages, this);
        languageRecyclerView.setAdapter(languageRecyclerAdapter);
        if (fromWiki) {
            return;
        }
        languageRecyclerAdapter.setSelectedLanguage(languageTextView.getText().toString().trim());
        getCodeTemplate(languageRecyclerAdapter.getSelectedLanguage());
    }

    private void setupRecyclerView() {
        if (code != null) {
            ArrayList<String> programLines = AuxilaryUtils.INSTANCE.splitProgramIntolines(code.getSourceCode());
            codeEditRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            codeEditorRecyclerAdapter = new CodeEditorRecyclerAdapter(getContext(), programLines, selectedLanguage);
            codeEditRecyclerView.setAdapter(codeEditorRecyclerAdapter);
            codeEditRecyclerView.getItemAnimator().setChangeDuration(0);
        }
    }

    private void getOutputResponse(int submissionId) {
        //TODO : Execute this after a delay
        Call<CodeOutputResponse> codeOutputResponseCall = submitCodeService.getOutput(
                submissionId,
                RetrofitCreator.getTokenCompilerApi(),
                true,
                true,
                true,
                true,
                true);
        codeOutputResponseCall.enqueue(new Callback<CodeOutputResponse>() {
            @Override
            public void onResponse(Call<CodeOutputResponse> call, Response<CodeOutputResponse> response) {
                Log.d(TAG, "Output Response : " + response.body().toString());
                outputTextView.setText(response.body().getOutput());
                stopAnimation();
            }

            @Override
            public void onFailure(Call<CodeOutputResponse> call, Throwable t) {
                Log.e(TAG, "Output Error : " + t.getMessage());
                t.printStackTrace();
                outputTextView.setText("Output Error : " + t.getMessage());
                stopAnimation();
            }
        });
    }

    public void executeProgram() {

        String input = inputEditText.getText().toString();
        code.setInput(input);

        HashMap<String, String> codeMap = new HashMap<>();
        codeMap.put("language", selectedLanguageIndex);
        String sourceCode = codeEditorRecyclerAdapter.getCode();
        codeMap.put("sourceCode", sourceCode);
        if (code.getInput() != null && input.trim().length() > 0) {
            codeMap.put("input", code.getInput());
        }
        inputEditText.setText("");
        startAnimation();
        Call<IdResponse> idResponseCall = submitCodeService.postCode(codeMap, RetrofitCreator.getTokenCompilerApi());
        idResponseCall.enqueue(new Callback<IdResponse>() {
            @Override
            public void onResponse(Call<IdResponse> call, final Response<IdResponse> response) {
                Log.d(TAG, "Execute Response : " + response.body().toString());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getProgramOutput(response.body());
                    }
                }, 4000);

            }

            private void getProgramOutput(IdResponse body) {
                getOutputResponse(body.getId());
            }

            @Override
            public void onFailure(Call<IdResponse> call, Throwable t) {
                Log.e(TAG, "Execute Error : " + t.getMessage());
                t.printStackTrace();
                outputTextView.setText("Execute Error : " + t.getMessage());
                stopAnimation();
            }
        });
    }

    private void startAnimation() {
        compilerProgressLayout.setVisibility(View.VISIBLE);
        Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(800);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        progressImageView.startAnimation(animation);
    }

    private void stopAnimation() {
        progressImageView.clearAnimation();
        compilerProgressLayout.setVisibility(View.GONE);
    }

    private boolean fromWiki = false;

    public void setParameter(Code code) {
        this.code = code;
        if (code != null) {
            this.selectedLanguageIndex = code.getLanguage();
            fromWiki = true;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.languageTextView:
                if (languageRecyclerView.getVisibility() == View.GONE) {
                    AnimationUtils.INSTANCE.enterReveal(languageRecyclerView);
                } else {
                    AnimationUtils.INSTANCE.exitRevealGone(languageRecyclerView);
                }
                break;
            case R.id.importFromFileTextView:
                importFromFile();
                break;
        }
    }


    private int REQUEST_CODE_SEARCH = 1000;

    private void importFromFile() {
        if (PermissionUtils.INSTANCE.checkSelfPermission(this,
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE})) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("text/plain");
            startActivityForResult(Intent.createChooser(intent,
                    "Load file from directory"), REQUEST_CODE_SEARCH);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.INSTANCE.getPERMISSION_REQUEST()) {
            if (PermissionUtils.INSTANCE.checkDeniedPermissions((AppCompatActivity) getActivity(), permissions).length == 0) {
                importFromFile();
            } else {
                if (permissions.length == 3) {
                    Toast.makeText(getContext(), "Some permissions were denied", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SEARCH && resultCode == AppCompatActivity.RESULT_OK) {
            try {
                Uri uri = data.getData();
                if (uri != null) {

                    Log.d(TAG, "File Uri : " + uri.getEncodedPath() + " Path " + uri.getPath());
                    String filepath = FileUtils.INSTANCE.getPath(getContext(), uri);
                    Log.d(TAG, "File path : " + filepath);
                    InputStream fis = new FileInputStream(filepath);
                    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    String completeLine = "";
                    while ((line = br.readLine()) != null) {
                        completeLine += line.trim() + " ";
                    }
                } else {
                }
                // Rest of code that converts txt file's content into arraylist
            } catch (IOException e) {
                e.printStackTrace();
                // Codes that handles IOException
            }
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }


    private String selectedLanguageIndex;
    private String codeTemplate;
    private String selectedLanguage;

    @Override
    public void onItemClick(int position) {
        String selectedLanguage = languages.get(position);
        languageRecyclerAdapter.setSelectedLanguage(selectedLanguage);
        languageTextView.setText(selectedLanguage);
        getCodeTemplate(selectedLanguage);

        AnimationUtils.INSTANCE.exitRevealGone(languageRecyclerView);
    }

    private void getCodeTemplate(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
        switch (selectedLanguage) {
            case "C":
                selectedLanguageIndex = LanguageConstants.C_INDEX;
                codeTemplate = LanguageConstants.C_TEMPLATE;
                inputList.clear();
                break;
            case "C++":
                selectedLanguageIndex = LanguageConstants.CPP_INDEX;
                codeTemplate = LanguageConstants.CPP_TEMPLATE;
                inputList.clear();
                break;
            case "Java":
                selectedLanguageIndex = LanguageConstants.JAVA_INDEX;
                codeTemplate = LanguageConstants.JAVA_TEMPLATE;
                inputList.clear();
                break;
        }

        code = new Code();
        code.setLanguage((selectedLanguageIndex));
        code.setSourceCode(codeTemplate);
        setupRecyclerView();
    }
}

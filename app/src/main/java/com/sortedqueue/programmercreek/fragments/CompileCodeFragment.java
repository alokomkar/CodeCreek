package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CodeEditorRecyclerAdapter;
import com.sortedqueue.programmercreek.constants.LanguageConstants;
import com.sortedqueue.programmercreek.database.firebase.Code;
import com.sortedqueue.programmercreek.database.firebase.CodeOutputResponse;
import com.sortedqueue.programmercreek.interfaces.retrofit.SubmitCodeService;
import com.sortedqueue.programmercreek.network.RetrofitCreator;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.OnCodeLineClickListener;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alok on 04/04/17.
 */

public class CompileCodeFragment extends Fragment {

    @Bind(R.id.algorithmCodeView)
    CodeView algorithmCodeView;
    @Bind(R.id.outputTextView)
    TextView outputTextView;
    @Bind(R.id.progressImageView)
    ImageView progressImageView;
    @Bind(R.id.progressTextView)
    TextView progressTextView;
    @Bind(R.id.compilerProgressLayout)
    RelativeLayout compilerProgressLayout;
    @Bind(R.id.outputScrollView)
    NestedScrollView outputScrollView;
    @Bind(R.id.codeEditRecyclerView)
    RecyclerView codeEditRecyclerView;
    private SubmitCodeService submitCodeService;
    private String TAG = CompileCodeFragment.class.getSimpleName();
    private Code code;
    private CodeEditorRecyclerAdapter codeEditorRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compile_code, container, false);
        ButterKnife.bind(this, view);
        compilerProgressLayout.setVisibility(View.GONE);
        code = new Code();
        code.setLanguage(Integer.parseInt(LanguageConstants.C_INDEX));
        code.setSourceCode("#include<stdio.h>\n" +
                "#include<math.h>\n" +
                "int main(void)\n" +
                "{\n" +
                "   double Adjacent=2, Opposite=3, Hypotenuse=4;\n" +
                "   //Hypotenuse\n" +
                "   double Hypotenuse1 = (pow(Adjacent,2)) + (pow(Opposite,2));\n" +
                "   Hypotenuse1=sqrt(Hypotenuse1);\n" +
                "   printf(\"\\nHypotenuse: %lf\",Hypotenuse1);\n" +
                "   //Adjacent\n" +
                "   double Adjacent1 = (pow(Hypotenuse,2)) - (pow(Opposite,2)) ;\n" +
                "   Adjacent1=sqrt(Adjacent1);\n" +
                "   printf(\"\\nAdjacent: %lf\",Adjacent1);\n" +
                "     \n" +
                "   //Opposite\n" +
                "   double Opposite1 = (pow(Hypotenuse,2)) - (pow(Adjacent,2));\n" +
                "   Opposite1=sqrt(Opposite1);\n" +
                "   printf(\"\\nOpposite: %lf\",Opposite1);\n" +
                "   return 0;\n" +
                "}\n");
        algorithmCodeView.setOptions(Options.Default.get(getContext())
                .withLanguage("C")
                .withCode(code.getSourceCode())
                .withTheme(ColorTheme.MONOKAI));
        algorithmCodeView.getOptionsOrDefault().addCodeLineClickListener(new OnCodeLineClickListener() {
            @Override
            public void onCodeLineClicked(int i, @NotNull String s) {

            }
        });
        setupRecyclerView();
        submitCodeService = RetrofitCreator.createService(SubmitCodeService.class);
        return view;
    }

    private void setupRecyclerView() {
        ArrayList<String> programLines = AuxilaryUtils.splitProgramIntolines(code.getSourceCode());
        codeEditRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        codeEditorRecyclerAdapter = new CodeEditorRecyclerAdapter(getContext(), programLines, "C");
        codeEditRecyclerView.setAdapter(codeEditorRecyclerAdapter);
        codeEditRecyclerView.getItemAnimator().setChangeDuration(0);
    }

    private void getOutputResponse(int submissionId) {
        //TODO : Execute this after a delay
        Call<CodeOutputResponse> codeOutputResponseCall = submitCodeService.getOutput(
                submissionId,
                RetrofitCreator.getTokenCompilerApi(),
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

        HashMap<String, String> codeMap = new HashMap<>();
        codeMap.put("language", LanguageConstants.C_INDEX);
        codeMap.put("sourceCode", codeEditorRecyclerAdapter.getCode());
        Log.d(TAG, "Source Code to be executed : " + codeEditorRecyclerAdapter.getCode());
        outputTextView.setText("");
        /*startAnimation();
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
        });*/
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

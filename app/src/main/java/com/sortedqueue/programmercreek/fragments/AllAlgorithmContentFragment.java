package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.constants.AlgorithmConstants;
import com.sortedqueue.programmercreek.database.AlgorithmContent;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;

/**
 * Created by Alok Omkar on 2017-03-18.
 */

public class AllAlgorithmContentFragment extends Fragment {

    @BindView(R.id.titleTextView)
    TextView titleTextView;
    @BindView(R.id.contentTextView)
    TextView contentTextView;
    @BindView(R.id.algorithmsTextView)
    TextView algorithmsTextView;
    @BindView(R.id.algorithmCodeView)
    CodeView algorithmCodeView;
    @BindView(R.id.outputTextView)
    TextView outputTextView;
    @BindView(R.id.algorithmNestedScrollview)
    NestedScrollView algorithmNestedScrollview;
    private AlgorithmContent algorithmContent;
    private String programLanguage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_content_algorithm, container, false);
        ButterKnife.bind(this, view);
        setupViews();
        return view;
    }

    private void setupViews() {
        titleTextView.setVisibility(View.GONE);
        contentTextView.setVisibility(View.GONE);
        algorithmsTextView.setVisibility(View.GONE);
        algorithmCodeView.setVisibility(View.GONE);
        outputTextView.setVisibility(View.GONE);
        algorithmNestedScrollview.setVisibility(View.GONE);

        switch (algorithmContent.getContentType()) {
            case AlgorithmConstants.Companion.getCONTENT_AIM_DESCRIPTION():
                algorithmNestedScrollview.setVisibility(View.VISIBLE);
                titleTextView.setVisibility(View.VISIBLE);
                contentTextView.setVisibility(View.VISIBLE);
                titleTextView.setText(algorithmContent.getAim());
                contentTextView.setText(algorithmContent.getProgramDescription());
                break;
            case AlgorithmConstants.Companion.getCONTENT_ALGORITHM():
                algorithmNestedScrollview.setVisibility(View.VISIBLE);
                algorithmsTextView.setVisibility(View.VISIBLE);
                algorithmsTextView.setText(algorithmContent.getAlgorithmPseudoCode());
                break;
            case AlgorithmConstants.Companion.getCONTENT_CODE():
                algorithmCodeView.setVisibility(View.VISIBLE);
                algorithmCodeView.setOptions(Options.Default.get(getContext())
                        .withLanguage(programLanguage)
                        .withCode(algorithmContent.getProgramCode())
                        .withTheme(ColorTheme.SOLARIZED_LIGHT));
                break;
            case AlgorithmConstants.Companion.getCONTENT_OUTPUT():
                algorithmNestedScrollview.setVisibility(View.VISIBLE);
                outputTextView.setVisibility(View.VISIBLE);
                outputTextView.setText(algorithmContent.getOutput());
                break;
        }
    }

    public void setParameter(AlgorithmContent algorithmContent, String programLanguage) {
        this.algorithmContent = algorithmContent;
        this.programLanguage = programLanguage;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}

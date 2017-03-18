package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.constants.AlgorithmConstants;
import com.sortedqueue.programmercreek.database.AlgorithmContent;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;

/**
 * Created by Alok Omkar on 2017-03-18.
 */

public class AllAlgorithmContentFragment extends Fragment {

    @Bind(R.id.titleTextView)
    TextView titleTextView;
    @Bind(R.id.contentTextView)
    TextView contentTextView;
    @Bind(R.id.algorithmsTextView)
    TextView algorithmsTextView;
    @Bind(R.id.algorithmCodeView)
    CodeView algorithmCodeView;
    @Bind(R.id.outputTextView)
    TextView outputTextView;
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

        switch ( algorithmContent.getContentType() ) {
            case AlgorithmConstants.CONTENT_AIM_DESCRIPTION :
                titleTextView.setVisibility(View.VISIBLE);
                contentTextView.setVisibility(View.VISIBLE);
                titleTextView.setText(algorithmContent.getAim());
                contentTextView.setText(algorithmContent.getProgramDescription());
                break;
            case AlgorithmConstants.CONTENT_ALGORITHM:
                algorithmsTextView.setVisibility(View.VISIBLE);
                algorithmsTextView.setText(algorithmContent.getAlgorithmPseudoCode());
                break;
            case AlgorithmConstants.CONTENT_CODE:
                algorithmCodeView.setVisibility(View.VISIBLE);
                algorithmCodeView.setOptions(Options.Default.get(getContext())
                    .withLanguage(programLanguage)
                    .withCode(algorithmContent.getProgramCode())
                    .withTheme(ColorTheme.SOLARIZED_LIGHT));
                break;
            case AlgorithmConstants.CONTENT_OUTPUT:
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
        ButterKnife.unbind(this);
    }
}

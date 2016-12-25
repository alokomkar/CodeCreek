package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.SyntaxModule;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class SyntaxLearnActivityFragment extends Fragment {

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
    @Bind(R.id.content_syntax_learn)
    RelativeLayout contentSyntaxLearn;
    private SyntaxModule syntaxModule;

    public SyntaxLearnActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_syntax_learn, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void setSyntaxModule(SyntaxModule syntaxModule) {
        this.syntaxModule = syntaxModule;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

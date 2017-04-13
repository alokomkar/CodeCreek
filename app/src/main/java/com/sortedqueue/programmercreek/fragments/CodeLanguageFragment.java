package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.interfaces.CodeLabNavigationListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 12/04/17.
 */

public class CodeLanguageFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.cProgramsTextView)
    TextView cProgramsTextView;
    @Bind(R.id.cppProgramsTextView)
    TextView cppProgramsTextView;
    @Bind(R.id.javaProgramsTextView)
    TextView javaProgramsTextView;
    @Bind(R.id.adaProgramsTextView)
    TextView adaProgramsTextView;

    private CodeLabNavigationListener codeLabNavigationListener;

    private static CodeLanguageFragment instance;

    public static CodeLanguageFragment getInstance() {
        if (instance == null) {
            instance = new CodeLanguageFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_code_language_list, container, false);
        ButterKnife.bind(this, view);
        setupListeners();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if( context instanceof  CodeLabNavigationListener ) {
            codeLabNavigationListener = (CodeLabNavigationListener) context;
        }
    }

    private void setupListeners() {
        cProgramsTextView.setOnClickListener(this);
        cppProgramsTextView.setOnClickListener(this);
        javaProgramsTextView.setOnClickListener(this);
        adaProgramsTextView.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.cProgramsTextView :
                codeLabNavigationListener.loadCompileCodeFragment();
                break;
            case R.id.cppProgramsTextView :
                codeLabNavigationListener.loadCompileCodeFragment();
                break;
            case R.id.javaProgramsTextView :
                codeLabNavigationListener.loadCompileCodeFragment();
                break;
            case R.id.adaProgramsTextView:
                codeLabNavigationListener.loadCompileCodeFragment();
                break;
        }
    }
}

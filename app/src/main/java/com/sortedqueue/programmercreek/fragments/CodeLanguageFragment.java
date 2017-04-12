package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 12/04/17.
 */

public class CodeLanguageFragment extends Fragment {

    @Bind(R.id.cProgramsTextView)
    TextView cProgramsTextView;
    @Bind(R.id.cppProgramsTextView)
    TextView cppProgramsTextView;
    @Bind(R.id.javaProgramsTextView)
    TextView javaProgramsTextView;
    @Bind(R.id.adaProgramsTextView)
    TextView adaProgramsTextView;

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
        return view;
    }

    public void onItemClick( View v ) {
        switch ( v.getId() ) {
            case R.id.cProgramsTextView :
                break;
            case R.id.cppProgramsTextView :
                break;
            case R.id.javaProgramsTextView :
                break;
            case R.id.adaProgramsTextView:
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

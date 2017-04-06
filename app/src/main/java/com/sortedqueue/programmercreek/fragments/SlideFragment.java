package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.sortedqueue.programmercreek.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.kbiakov.codeview.CodeView;

/**
 * Created by Alok on 06/04/17.
 */

public class SlideFragment extends Fragment {

    @Bind(R.id.titleEditText)
    EditText titleEditText;
    @Bind(R.id.subTitleEditText)
    EditText subTitleEditText;
    @Bind(R.id.codeView)
    CodeView codeView;
    @Bind(R.id.recordImageView)
    ImageView recordImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slide, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

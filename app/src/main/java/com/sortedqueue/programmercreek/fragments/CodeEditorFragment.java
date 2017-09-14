package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.util.CreekPreferences;
import com.sortedqueue.programmercreek.view.CodeEditor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Alok on 14/09/17.
 */

public class CodeEditorFragment extends Fragment implements CodeEditor.OnTextChangedListener {


    @BindView(R.id.editor)
    CodeEditor editor;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    Unbinder unbinder;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle state) {
        View view = inflater.inflate(
                R.layout.fragment_editor,
                container,
                false);
        unbinder = ButterKnife.bind(this, view);
        setupViews();
        return view;
    }

    private void setupViews() {
        editor.setOnTextChangedListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onTextChanged(String text) {
        if (!CreekApplication.getCreekPreferences().doesRunOnChange()) {
            return;
        }

        if (editor.hasErrorLine()) {
            editor.setErrorLine(0);
            editor.updateHighlighting();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateToPreferences();
    }

    private void updateToPreferences() {
        CreekPreferences preferences = CreekApplication.getCreekPreferences();

        editor.setUpdateDelay(preferences.getUpdateDelay());

        editor.setTextSize(
                android.util.TypedValue.COMPLEX_UNIT_SP,
                preferences.getTextSize());

        editor.setTabWidth(preferences.getTabWidth());
    }
}

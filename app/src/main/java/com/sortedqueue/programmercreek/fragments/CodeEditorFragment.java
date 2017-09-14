package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CodeShortCutsAdapter;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.database.CodeShortCuts;
import com.sortedqueue.programmercreek.util.CreekPreferences;
import com.sortedqueue.programmercreek.view.CodeEditor;

import java.util.ArrayList;

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
    @BindView(R.id.codeShortCutsRecyclerView)
    RecyclerView codeShortCutsRecyclerView;

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
        editor.setText("#include \"stdio.h\"\n" +
                "#include \"conio.h\"");
        codeShortCutsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        final ArrayList<CodeShortCuts> codeShortCuts = new ArrayList<>();
        codeShortCuts.add(new CodeShortCuts("do_while", "do{ \n\n }while();"));
        codeShortCuts.add(new CodeShortCuts("for_loop", "for( ; ; ){\n\n}"));
        codeShortCuts.add(new CodeShortCuts("if", "if(  ){\n\n}"));
        codeShortCuts.add(new CodeShortCuts("else", "else{\n\n}"));
        codeShortCuts.add(new CodeShortCuts("else_if", "else{\n\n}"));
        codeShortCuts.add(new CodeShortCuts("printf", "printf(\"\");"));
        codeShortCuts.add(new CodeShortCuts("scanf", "scanf(\"\");"));
        codeShortCuts.add(new CodeShortCuts("{}", "{\n\n}"));
        codeShortCuts.add(new CodeShortCuts("stdio", "#include \"stdio.h\""));
        codeShortCuts.add(new CodeShortCuts("conio", "#include \"conio.h\""));
        codeShortCuts.add(new CodeShortCuts("main", "void main{\n\n\n}"));
        codeShortCuts.add(new CodeShortCuts("int main", "void main{\n\n\nreturn0;\n}"));
        codeShortCutsRecyclerView.setAdapter(new CodeShortCutsAdapter(codeShortCuts, new CustomProgramRecyclerViewAdapter.AdapterClickListner() {
            @Override
            public void onItemClick(int position) {
                int start = Math.max(editor.getSelectionStart(), 0);
                int end = Math.max(editor.getSelectionEnd(), 0);
                editor.getText().insert(Math.min(start, end), codeShortCuts.get(position).getValue());
            }
        }));
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
                TypedValue.COMPLEX_UNIT_SP,
                preferences.getTextSize());

        editor.setTabWidth(preferences.getTabWidth());
    }
}

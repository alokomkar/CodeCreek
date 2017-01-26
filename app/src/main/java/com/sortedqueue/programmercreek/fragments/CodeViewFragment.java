package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;

/**
 * Created by Alok Omkar on 2017-01-26.
 */

public class CodeViewFragment extends Fragment {

    @Bind(R.id.programCodeView)
    CodeView programCodeView;
    private View view;
    private ArrayList<String> programCode;
    private ArrayList<ArrayList<String>> allCode;
    private String programLanguage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_code_view, container, false);
        ButterKnife.bind(this, view);
        programLanguage = new CreekPreferences(getContext()).getProgramLanguage();
        if( programLanguage.equals("c++") ) {
            programLanguage = "cpp";
        }
        programCode = new ArrayList<>();
        allCode = new ArrayList<>();
        return view;
    }

    public ArrayList<String> getProgramCode() {
        return programCode;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void submitSubTest(int index, ArrayList<String> content) {
        allCode.add(index, content);
        programCode.clear();
        for( ArrayList<String> arrayList : allCode ) {
            programCode.addAll(arrayList);
        }
        initCodeView();
    }

    private void initCodeView() {
        String programLines = "";
        int position = 1;
        String programDescription = "";
        for (String program_table : programCode) {
            if (position == 1) {
                programDescription += position + ". " + program_table;
                programLines += ((position++) + ". ");
            } else {
                programDescription += "\n" + position + ". " + program_table;
                programLines += ("\n" + (position++) + ". ");
            }
            programLines += (program_table.trim());
        }
        programCodeView
                .setOptions(Options.Default.get(getContext())
                        .withLanguage(programLanguage)
                        .withCode(programLines)
                        .withTheme(ColorTheme.SOLARIZED_LIGHT));
    }
}

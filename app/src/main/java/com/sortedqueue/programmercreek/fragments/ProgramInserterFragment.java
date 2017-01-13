package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.ProgramInserterWikiAdapter;
import com.sortedqueue.programmercreek.database.ProgramWiki;
import com.sortedqueue.programmercreek.database.WikiModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-01-11.
 */

public class ProgramInserterFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.wikiHeaderEditText)
    AppCompatEditText wikiHeaderEditText;
    @Bind(R.id.wikiIdEditText)
    AppCompatEditText wikiIdEditText;
    @Bind(R.id.syntaxLanguageTextView)
    TextView syntaxLanguageTextView;
    @Bind(R.id.insertButton)
    Button insertButton;
    @Bind(R.id.deleteButton)
    Button deleteButton;
    @Bind(R.id.programWikiRecyclerView)
    RecyclerView programWikiRecyclerView;
    @Bind(R.id.saveButton)
    Button saveButton;
    @Bind(R.id.clearButton)
    Button clearButton;
    @Bind(R.id.formatCodeButton)
    Button formatCodeButton;
    @Bind(R.id.headerEditText)
    EditText headerEditText;
    @Bind(R.id.explanationEditText)
    EditText explanationEditText;
    @Bind(R.id.exampleEditText)
    EditText exampleEditText;
    @Bind(R.id.ouptputEditText)
    EditText ouptputEditText;

    private WikiModel wikiModel = new WikiModel();
    private List<ProgramWiki> programWikis = new ArrayList<>();
    private ProgramInserterWikiAdapter adapter;
    private ProgramWiki programWiki;
    private String language;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_program_inserter, null);
        ButterKnife.bind(this, fragmentView);
        setupViews();
        return fragmentView;
    }

    private void setupViews() {
        language = new CreekPreferences(getContext()).getProgramLanguage();
        if (language.equals("c++")) {
            language = "cpp";
        }
        if( language.equals("java") ) {

        }
        wikiIdEditText.setText(language);
        syntaxLanguageTextView.setText(language);

        wikiModel.setSyntaxLanguage(syntaxLanguageTextView.getText().toString());
        deleteButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        insertButton.setOnClickListener(this);
        formatCodeButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        setupRecyelerView();
    }

    private void setupRecyelerView() {
        adapter = new ProgramInserterWikiAdapter(programWikis);
        programWikiRecyclerView.setAdapter(adapter);
        programWikiRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.deleteButton:
                deleteWiki();
                break;
            case R.id.insertButton:
                insertWiki();
                break;
            case R.id.saveButton:
                saveAllDetails();
                break;
            case R.id.formatCodeButton:
                formatCode();
                break;
            case R.id.clearButton:
                clearAllFields();
                break;
        }
    }

    private void clearAllFields() {
        wikiIdEditText.setText(language);
        headerEditText.setText("");
        wikiHeaderEditText.setText("");
        explanationEditText.setText("");
        exampleEditText.setText("");
        ouptputEditText.setText("");
    }

    private void formatCode() {
        String programCode = exampleEditText.getText().toString().trim();
        String formattedCode = "";
        String[] splitString = programCode.split(";");
        int lastIndex = 0;
        for( String string : splitString ) {
            lastIndex++;
            if( lastIndex == splitString.length ) {
                formattedCode += string.trim();
            }
            else
                formattedCode += string.trim() + ";\n";

        }
        lastIndex = 0;
        Log.d("Formatted", "Regex : ; \n " + formattedCode);
        splitString = formattedCode.split("\\{");
        formattedCode = "";
        for( String string : splitString ) {
            lastIndex++;
            if( lastIndex == splitString.length ) {
                formattedCode += string.trim();
            }
            else
                formattedCode += string.trim() + " {\n";

        }
        Log.d("Formatted", "Regex : { \n " + formattedCode);
        lastIndex = 0;
        splitString = formattedCode.split("\\}");
        formattedCode = "";
        for( String string : splitString ) {
           /* lastIndex++;
            if( lastIndex == splitString.length ) {
                formattedCode += string;
            }
            else*/
                formattedCode += string.trim() + "\n}\n";

        }
        Log.d("Formatted", "Regex : } \n " + formattedCode);

        lastIndex = 0;
        splitString = formattedCode.split("cout");
        formattedCode = "";
        for( String string : splitString ) {
            lastIndex++;
            if( lastIndex == 1 /*|| lastIndex == splitString.length*/ ) {
                formattedCode += string.trim();
            }
            else
                formattedCode += "\ncout"+ string.trim();

        }
        Log.d("Formatted", "Regex : cout \n " + formattedCode);
        /*lastIndex = 0;
        splitString = formattedCode.split("cin");
        formattedCode = "";
        for( String string : splitString ) {
            lastIndex++;
            if( lastIndex == splitString.length ) {
                formattedCode += string;
            }
            else
                formattedCode += "\ncin"+ string;

        }
        Log.d("Formatted", "Regex : cin \n " + formattedCode);*/

        Log.d("Formatted", "Regex : final \n " + formattedCode.trim());
        exampleEditText.setText(formattedCode);
    }


    private void insertWiki() {
        ProgramWiki programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_HEADER);
        programWikis.add(programWiki);
        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM_EXPLANATION);
        programWikis.add(programWiki);
        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM);
        programWikis.add(programWiki);
        adapter.notifyDataSetChanged();
    }

    private void deleteWiki() {
        if (programWikis.size() == 3) {
            return;
        }
        int size = programWikis.size();
        programWikis.remove(size - 1);
        programWikis.remove(size - 2);
        programWikis.remove(size - 3);
        adapter.notifyDataSetChanged();
    }

    private boolean validateEmptyEditText( EditText editText ) {
        boolean result =  editText.getText().toString().trim().length() == 0;
        CommonUtils.displaySnackBar(getActivity(), "Content missing");
        editText.requestFocus();
        return result;
    }
    private void saveAllDetails() {

        if (validateEmptyEditText(wikiHeaderEditText)) {
            return;
        }
        if (validateEmptyEditText(wikiIdEditText)) {
            return;
        }
        if( validateEmptyEditText(exampleEditText) ) {
            return;
        }
        if( validateEmptyEditText(explanationEditText) ) {
            return;
        }
        if( validateEmptyEditText(headerEditText) ) {
            return;
        }
        if( validateEmptyEditText(ouptputEditText) ) {
            return;
        }

        programWikis.clear();
        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_HEADER);
        programWiki.setHeader(headerEditText.getText().toString());
        programWikis.add(programWiki);
        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM_EXPLANATION);
        programWiki.setProgramExplanation(explanationEditText.getText().toString());
        programWikis.add(programWiki);
        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM);
        programWiki.setProgramExample(exampleEditText.getText().toString());
        programWiki.setOutput(ouptputEditText.getText().toString());
        programWikis.add(programWiki);

        wikiModel.setWikiHeader(wikiHeaderEditText.getText().toString());
        wikiModel.setWikiId(wikiIdEditText.getText().toString());
        wikiModel.setProgramWikis(programWikis);
        new FirebaseDatabaseHandler(getContext()).writeProgramWiki(wikiModel);
    }
}

package com.sortedqueue.programmercreek.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.ProgramTable;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;

/**
 * Created by Alok on 10/05/17.
 */

public class UserProgramDialog implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    @Bind(R.id.codeRadioButton)
    RadioButton codeRadioButton;
    @Bind(R.id.explanationRadioButton)
    RadioButton explanationRadioButton;
    @Bind(R.id.codeView)
    CodeView codeView;
    @Bind(R.id.doneButton)
    Button doneButton;
    @Bind(R.id.saveButton)
    Button saveButton;
    @Bind(R.id.discardButton)
    Button discardButton;
    private UserProgramDialogListener userProgramDialogListener;
    private Context context;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private ArrayList<ProgramTable> programTables;
    private ProgramIndex programIndex;

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch ( buttonView.getId() ) {
            case R.id.codeRadioButton :
                if( codeRadioButton.isChecked() )
                    showCode(R.id.codeRadioButton);
                break;
            case R.id.explanationRadioButton :
                if( explanationRadioButton.isChecked())
                    showCode(R.id.explanationRadioButton);
                break;
        }
    }

    private void showCode(int codeRadioButton) {
        String code = "";
        switch ( codeRadioButton ) {
            case R.id.codeRadioButton :
                for( ProgramTable programTable : programTables ) {
                    code += programTable.getProgram_Line() + "\n";
                }
                break;
            case R.id.explanationRadioButton :
                for( ProgramTable programTable : programTables ) {
                    code += programTable.getProgram_Line_Description() + "\n";
                }
                break;
        }
        codeView
                .setOptions(Options.Default.get(context)
                        .withLanguage(programIndex.getProgram_Language())
                        .withCode(code)
                        .withTheme(ColorTheme.SOLARIZED_LIGHT));
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.saveButton :
                userProgramDialogListener.onSave();
                alertDialog.dismiss();
                break;
            case R.id.doneButton :
                userProgramDialogListener.onPreview();
                break;
            case R.id.discardButton :
                userProgramDialogListener.onCancel();
                alertDialog.dismiss();
                break;
        }
    }

    public void showDialog() {
        alertDialog.show();
        codeRadioButton.setChecked(true);
    }

    public interface UserProgramDialogListener {
        void onSave();
        void onCancel();
        void onPreview();
    }

    public UserProgramDialog(Context context,
                             ProgramIndex programIndex,
                             ArrayList<ProgramTable> programTables,
                             UserProgramDialogListener userProgramDialogListener) {
        this.context = context;
        this.userProgramDialogListener = userProgramDialogListener;
        this.programTables = programTables;
        this.programIndex = programIndex;
        initViews();
    }

    private void initViews() {
        builder = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.sign_in);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_user_program, null);
        ButterKnife.bind(this, dialogView);
        builder.setView(dialogView);
        alertDialog = builder.create();
        codeRadioButton.setOnCheckedChangeListener(this);
        explanationRadioButton.setOnCheckedChangeListener(this);
        doneButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        discardButton.setOnClickListener(this);
    }
}

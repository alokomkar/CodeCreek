package com.sortedqueue.programmercreek.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.TagsRecyclerAdapter;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.TagModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.util.CommonUtils;

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
    @Bind(R.id.accessSwitchCompat)
    SwitchCompat accessSwitchCompat;
    @Bind(R.id.accessTextView)
    TextView accessTextView;
    @Bind(R.id.presentationTitleEditText)
    EditText presentationTitleEditText;
    @Bind(R.id.languageRecyclerView)
    RecyclerView languageRecyclerView;
    @Bind(R.id.programTitleLayout)
    LinearLayout programTitleLayout;
    private UserProgramDialogListener userProgramDialogListener;
    private WebUserProgramDialogListener webUserProgramDialogListener;
    private Context context;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private ArrayList<ProgramTable> programTables;
    private ProgramIndex programIndex;

    private int mode = -1;
    private View dialogView;
    private TagsRecyclerAdapter tagsRecyclerAdapter;

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.codeRadioButton:
                if (codeRadioButton.isChecked())
                    showCode(R.id.codeRadioButton);
                break;
            case R.id.explanationRadioButton:
                if (explanationRadioButton.isChecked())
                    showCode(R.id.explanationRadioButton);
                break;
            case R.id.accessSwitchCompat:
                if (accessSwitchCompat.isChecked()) {
                    accessTextView.setText("Public");
                } else {
                    accessTextView.setText("Private");
                }
                break;
        }
    }

    private void showCode(int codeRadioButton) {
        String code = "";
        switch (codeRadioButton) {
            case R.id.codeRadioButton:
                for (ProgramTable programTable : programTables) {
                    code += programTable.getProgram_Line() + "\n";
                }
                break;
            case R.id.explanationRadioButton:
                for (ProgramTable programTable : programTables) {
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
        switch (v.getId()) {
            case R.id.saveButton:
                if (validateFields()) {
                    if( mode == -1 )
                        userProgramDialogListener.onSave(accessTextView.getText().toString());
                    else webUserProgramDialogListener.onSave(accessTextView.getText().toString(), programIndex, programTables);
                    alertDialog.dismiss();
                }
                break;
            case R.id.doneButton:
                if (validateFields()) {
                    if( mode == -1 )
                        userProgramDialogListener.onPreview();
                    else webUserProgramDialogListener.onPreview(programIndex, programTables);
                }
                break;
            case R.id.discardButton:
                if( mode == -1 )
                    userProgramDialogListener.onCancel();
                else webUserProgramDialogListener.onCancel();
                alertDialog.dismiss();
                break;
        }
    }

    private boolean validateFields() {

        if (mode == -1) return true;

        String title = presentationTitleEditText.getText().toString();
        if( title.trim().length() == 0 ) {
            CommonUtils.displayToast(context, "Enter program name");
            presentationTitleEditText.requestFocus();
            programIndex.setProgram_Description(title);
            return false;
        }
        if( tagsRecyclerAdapter.getSelectedTag().equals("") ) {
            CommonUtils.displayToast(context, "Select language");
            programIndex.setProgram_Language(tagsRecyclerAdapter.getSelectedTag());
            for( ProgramTable programTable : programTables ) {
                programTable.setProgram_Language(tagsRecyclerAdapter.getSelectedTag());
            }
            return false;
        }

        return true;

    }

    public void showDialog() {
        alertDialog.show();
        accessSwitchCompat.setChecked(true);
        codeRadioButton.setChecked(true);

        programTitleLayout.setVisibility( mode == -1 ? View.GONE : View.VISIBLE );
        if( mode != -1 ) {
            fetchAllTags();
        }
    }

    public interface UserProgramDialogListener {
        void onSave(String accessSpecifier);

        void onCancel();

        void onPreview();
    }

    public interface WebUserProgramDialogListener {
        void onSave(String accessSpecifier, ProgramIndex programIndex, ArrayList<ProgramTable> programTables);

        void onCancel();

        void onPreview(ProgramIndex programIndex, ArrayList<ProgramTable> programTables);
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

    public UserProgramDialog(Context context,
                             ProgramIndex programIndex,
                             ArrayList<ProgramTable> programTables,
                             WebUserProgramDialogListener userProgramDialogListener,
                             int mode) {
        this.context = context;
        this.mode = mode;
        this.webUserProgramDialogListener = userProgramDialogListener;
        this.programTables = programTables;
        this.programIndex = programIndex;
        initViews();
    }

    private void initViews() {

        builder = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setIcon(R.mipmap.ic_launcher);
        builder.setTitle(programIndex.getProgram_Description());

        dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_user_program, null);
        ButterKnife.bind(this, dialogView);

        builder.setView(dialogView);
        alertDialog = builder.create();
        codeRadioButton.setOnCheckedChangeListener(this);
        explanationRadioButton.setOnCheckedChangeListener(this);
        accessSwitchCompat.setOnCheckedChangeListener(this);
        doneButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        discardButton.setOnClickListener(this);


    }

    private void fetchAllTags() {

        CommonUtils.displayProgressDialog(context, context.getString(R.string.loading));
        new FirebaseDatabaseHandler(context).getAllTags(new FirebaseDatabaseHandler.GetAllTagsListener() {
            @Override
            public void onError(DatabaseError databaseError) {
                CommonUtils.dismissProgressDialog();
            }

            @Override
            public void onSuccess(TagModel tagModel) {
                setupRecyclerView(tagModel);
            }
        });
    }

    private void setupRecyclerView(TagModel tagModel) {
        languageRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        tagsRecyclerAdapter = new TagsRecyclerAdapter(tagModel.getTagArrayList(), 1);
        languageRecyclerView.setAdapter(tagsRecyclerAdapter);
        CommonUtils.dismissProgressDialog();
    }
}

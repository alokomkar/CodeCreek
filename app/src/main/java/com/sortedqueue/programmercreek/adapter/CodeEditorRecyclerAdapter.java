package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.util.PrettifyHighlighter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 12/04/17.
 */
public class CodeEditorRecyclerAdapter extends RecyclerView.Adapter<CodeEditorRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> programLines;
    private PrettifyHighlighter prettifyHighlighter;
    private String programLanguage;
    private int editableIndex = -1;
    private String TAG = CodeEditorRecyclerAdapter.class.getSimpleName();

    public CodeEditorRecyclerAdapter(Context context, ArrayList<String> programLines, String programLanguage) {
        this.context = context;
        this.programLines = programLines;
        this.programLanguage = programLanguage;
        this.prettifyHighlighter = PrettifyHighlighter.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_code, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String programLine = programLines.get(position);
        //holder.codeEditText.setEnabled(position == editableIndex);
        if( programLine.contains("<") || programLine.contains(">")) {
            holder.codeEditText.setText(programLine);
            holder.codeEditText.setTextColor(Color.parseColor("#006699"));
        }
        else {
            if(Build.VERSION.SDK_INT >= 24) {
                holder.codeEditText.setText(Html.fromHtml(prettifyHighlighter.highlight(programLanguage, programLine), Html.FROM_HTML_MODE_LEGACY));
            }
            else {
                holder.codeEditText.setText(Html.fromHtml(prettifyHighlighter.highlight(programLanguage, programLine)));
            }
        }
    }

    @Override
    public int getItemCount() {
        return programLines.size();
    }

    public String getCode() {
        String code = "";
        for( String programLine : programLines ) {
            code += programLine + "\n";
        }
        return code;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, TextWatcher {

        @Bind(R.id.codeEditText)
        EditText codeEditText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            codeEditText.setOnClickListener(this);
            codeEditText.addTextChangedListener(this);
        }

        @Override
        public void onClick(View v) {
            editableIndex = getAdapterPosition();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if( editableIndex == -1 ) {
                return;
            }
            String programLine = s.toString().trim();
            Log.d(TAG, "Setting line : " + programLine);
            if( programLine.trim().length() > 0 ) {
                int position = getAdapterPosition();
                programLines.set(position, programLine);

                if( position != RecyclerView.NO_POSITION ) {
                    if( programLine.contains("<") || programLine.contains(">")) {
                        codeEditText.setText(programLine);
                        codeEditText.setTextColor(Color.parseColor("#006699"));
                    }
                    if(Build.VERSION.SDK_INT >= 24) {
                        codeEditText.setText(Html.fromHtml(prettifyHighlighter.highlight(programLanguage, programLine), Html.FROM_HTML_MODE_LEGACY));
                    }
                    else {
                        codeEditText.setText(Html.fromHtml(prettifyHighlighter.highlight(programLanguage, programLine)));
                    }
                }

            }

        }
    }

}

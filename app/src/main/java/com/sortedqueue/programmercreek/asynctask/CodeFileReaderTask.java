package com.sortedqueue.programmercreek.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by Alok on 27/07/17.
 */

public class CodeFileReaderTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private String code;
    private String fileId;

    public interface OnDataReadListener {
        void onDataReadComplete( String code );
    }

    //Interface to communicate back the response to UI
    private OnDataReadListener onDataReadListener;
    private ProgressBar progressBar;

    public CodeFileReaderTask(ProgressBar codeProgressBar, Context context, String fileId, OnDataReadListener onDataReadListener) {
        this.context = context;
        this.fileId = fileId;
        this.onDataReadListener = onDataReadListener;
        this.progressBar = codeProgressBar;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        readFileAsList(context);
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if( progressBar != null ) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if( progressBar != null ) {
            progressBar.setVisibility(View.GONE);
        }
        if( onDataReadListener != null ) {
            onDataReadListener.onDataReadComplete(code);
        }
    }

    private void readFileAsList(Context context) {
        String line;
        try {

            InputStream inputStream = context.getResources().openRawResource(
                    context.getResources().getIdentifier(fileId,
                            "raw", context.getPackageName()));
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            code = "";
            while ((line = bufferedReader.readLine()) != null) {
                code += line + "\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

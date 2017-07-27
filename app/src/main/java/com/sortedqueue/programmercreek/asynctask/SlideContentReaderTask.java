package com.sortedqueue.programmercreek.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.sortedqueue.programmercreek.database.InterviewQuestionModel;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Alok on 27/07/17.
 */

public class SlideContentReaderTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private String contentText;
    private String fileId;
    private ArrayList<InterviewQuestionModel> contentArrayList;

    public interface OnDataReadListener {
        void onDataReadComplete( ArrayList<InterviewQuestionModel> contentArrayList );
    }

    //To display progress Dialog
    private ProgressDialog progressDialog;

    //Interface to communicate back the response to UI
    private OnDataReadListener onDataReadListener;

    public SlideContentReaderTask(Context context, String fileId, OnDataReadListener onDataReadListener) {
        this.context = context;
        this.fileId = fileId;
        this.onDataReadListener = onDataReadListener;
        this.contentArrayList = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        readFileAsList(context);
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        CommonUtils.displayProgressDialog(context, "Loading questions");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        CommonUtils.dismissProgressDialog();
        if( onDataReadListener != null ) {
            onDataReadListener.onDataReadComplete(contentArrayList);
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
            contentText = "";
            String mainTitle = "C Programming Questions and Answers";

            while ((line = bufferedReader.readLine()) != null) {
                if( line.startsWith("<image>") ) {
                    contentText = "";
                    line = line.replace("<image>", "");
                    line = line.replace("</image>", "");
                    contentArrayList.add(new InterviewQuestionModel());

                }
                else if( line.startsWith("<code>") ) {
                    contentText = "";
                    line = line.replace("<code>", "");
                    line = line.replace("</code>", "");
                    contentArrayList.add(new InterviewQuestionModel());
                }
                else if( line.startsWith("<url>") ) {
                    contentText = "";
                    line = line.replace("<url>", "");
                    line = line.replace("</url>", "");
                    contentArrayList.add(new InterviewQuestionModel());
                }
                else if( line.startsWith("<text>") ) {
                    line = bufferedReader.readLine();
                    while( true ) {
                        if( !line.startsWith("</text>") )
                            contentText += line + "<br>";
                        else break;
                        line = bufferedReader.readLine();
                    }
                    contentArrayList.add(new InterviewQuestionModel());
                    contentText = "";
                }

            }
            if( contentArrayList.size() == 0 ) {
                if( contentText.trim().length() > 0 ) {
                    contentArrayList.add( new InterviewQuestionModel() );
                }
            }
            else if( contentText.trim().length() > 0 ) {
                contentArrayList.add( new InterviewQuestionModel() );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

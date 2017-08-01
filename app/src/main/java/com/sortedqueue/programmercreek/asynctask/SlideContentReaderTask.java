package com.sortedqueue.programmercreek.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sortedqueue.programmercreek.constants.InterviewQuestionConstants;
import com.sortedqueue.programmercreek.database.InterviewQuestionModel;
import com.sortedqueue.programmercreek.database.OptionModel;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Alok on 27/07/17.
 */

public class SlideContentReaderTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private String contentText;
    private String fileId;
    private ArrayList<InterviewQuestionModel> contentArrayList;
    private String TAG = SlideContentReaderTask.class.getSimpleName();

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
            InterviewQuestionModel interviewQuestionModel = null;
            while( (line = bufferedReader.readLine()) != null ) {
                if( line.contains("<question>") ) {

                    if( interviewQuestionModel != null )
                        contentArrayList.add(interviewQuestionModel);

                    interviewQuestionModel = new InterviewQuestionModel();
                    String question = "";
                    while ( !(line = bufferedReader.readLine()).equals("</>") ) {
                        question += line + "\n";
                    }
                    line = bufferedReader.readLine();
                    interviewQuestionModel.setQuestion(question);


                }
                if( line.contains("<code>") ) {
                    String code = "";
                    while ( !(line = bufferedReader.readLine()).equals("</>") ) {
                        code += line + "\n";
                    }
                    line = bufferedReader.readLine();
                    if( interviewQuestionModel != null )
                        interviewQuestionModel.setCode(code);
                }
                if( line.contains("<option>") ) {
                    while ( !(line = bufferedReader.readLine()).equals("</>") ) {
                        if (line.startsWith("a)")) {
                            String optionA = line.split(Pattern.quote("a)"))[1];
                            interviewQuestionModel.setOptionModels(new ArrayList<OptionModel>());
                            interviewQuestionModel.getOptionModels().add(new OptionModel(1, optionA));
                            interviewQuestionModel.setTypeOfQuestion(InterviewQuestionConstants.TYPE_TRUE_FALSE);
                            Log.d(TAG, "Options : " + optionA);
                            line = bufferedReader.readLine();
                        }
                        if (line.startsWith("b)")) {
                            String optionB = line.split(Pattern.quote("b)"))[1];
                            line = bufferedReader.readLine();
                            interviewQuestionModel.getOptionModels().add(new OptionModel(2, optionB));
                            interviewQuestionModel.setTypeOfQuestion(InterviewQuestionConstants.TYPE_TRUE_FALSE);
                            Log.d(TAG, "Options : " + optionB);
                        }
                        if (line.startsWith("c)")) {
                            String optionC = line.split(Pattern.quote("c)"))[1];
                            line = bufferedReader.readLine();
                            interviewQuestionModel.getOptionModels().add(new OptionModel(3, optionC));
                            interviewQuestionModel.setTypeOfQuestion(InterviewQuestionConstants.TYPE_SINGLE_RIGHT);
                            Log.d(TAG, "Options : " + optionC);
                        }
                        if (line.startsWith("d)")) {
                            String optionD = line.split(Pattern.quote("d)"))[1];
                            interviewQuestionModel.getOptionModels().add(new OptionModel(4, optionD));
                            interviewQuestionModel.setTypeOfQuestion(InterviewQuestionConstants.TYPE_SINGLE_RIGHT);
                            Log.d(TAG, "Options : " + optionD);
                        }
                    }
                    line = bufferedReader.readLine();
                }
                if( line.startsWith("Answer:") ) {
                    String answer = line.split(":")[1].trim();
                    switch (answer) {
                        case "a" :
                            interviewQuestionModel.setCorrectOption(1);
                            break;
                        case "b" :
                            interviewQuestionModel.setCorrectOption(2);
                            break;
                        case "c" :
                            interviewQuestionModel.setCorrectOption(3);
                            break;
                        case "d" :
                            interviewQuestionModel.setCorrectOption(4);
                            break;
                    }
                }
                if( line.contains("<output>") ) {
                    String output = "";
                    while ( !(line = bufferedReader.readLine()).equals("</>") ) {
                        output += line + "\n";
                    }
                    line = bufferedReader.readLine();
                    if( interviewQuestionModel != null )
                        interviewQuestionModel.setOutput(output);
                }
                if( line.contains("<Explanation>") ) {
                    String explanation = "";
                    while ( !(line = bufferedReader.readLine()).equals("</>") ) {
                        explanation += line + "\n";
                    }
                    line = bufferedReader.readLine();
                    if( interviewQuestionModel != null )
                        interviewQuestionModel.setOutput(explanation);
                }

            }
            if( interviewQuestionModel != null )
                contentArrayList.add(interviewQuestionModel);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

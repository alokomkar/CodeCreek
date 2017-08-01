package com.sortedqueue.programmercreek.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sortedqueue.programmercreek.constants.InterviewQuestionConstants;
import com.sortedqueue.programmercreek.database.InterviewQuestionModel;
import com.sortedqueue.programmercreek.database.OptionModel;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;
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
        readFileFromRawDirectory(context.getResources().getIdentifier(fileId,
                "raw", context.getPackageName()));
        InputStream inputStream = context.getResources().openRawResource(
                context.getResources().getIdentifier(fileId,
                        "raw", context.getPackageName()));
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        contentText = "";
        InterviewQuestionModel interviewQuestionModel = null;
        Scanner sc = new Scanner(inputStream, "UTF-8");
        while( sc.hasNext() ) {
            line = sc.nextLine();
            Log.d(TAG, "Read Line : " + line);
            if( line.contains("<question>") ) {

                if( interviewQuestionModel != null ) {
                    contentArrayList.add(interviewQuestionModel);
                    Log.d(TAG, "Question : " + interviewQuestionModel.getQuestion());
                }


                Log.d(TAG, "Forming question");
                interviewQuestionModel = new InterviewQuestionModel();
                String question = "";
                while ( !(line = sc.nextLine()).equals("</>") ) {
                    question += line + "\n";
                }
                if( sc.hasNext() )
                line = sc.nextLine();
                interviewQuestionModel.setQuestion(question);


            }
            if( line.contains("<code>") ) {
                String code = "";
                while ( !(line = sc.nextLine()).equals("</>") ) {
                    code += line + "\n";
                }
                if( sc.hasNext() )
                line = sc.nextLine();
                if( interviewQuestionModel != null )
                    interviewQuestionModel.setCode(code);
            }
            if( line.contains("<option>") ) {
                while ( sc.hasNext() && !(line = sc.nextLine()).equals("</>") ) {
                    if (line.startsWith("a)")) {
                        String optionA = line.split(Pattern.quote("a)"))[1];
                        interviewQuestionModel.setOptionModels(new ArrayList<OptionModel>());
                        interviewQuestionModel.getOptionModels().add(new OptionModel(1, optionA));
                        interviewQuestionModel.setTypeOfQuestion(InterviewQuestionConstants.TYPE_TRUE_FALSE);
                        Log.d(TAG, "Options : " + optionA);
                        if( sc.hasNext() )
                        line = sc.nextLine();
                    }
                    if (line.startsWith("b)")) {
                        String optionB = line.split(Pattern.quote("b)"))[1];
                        if( sc.hasNext() )
                        line = sc.nextLine();
                        interviewQuestionModel.getOptionModels().add(new OptionModel(2, optionB));
                        interviewQuestionModel.setTypeOfQuestion(InterviewQuestionConstants.TYPE_TRUE_FALSE);
                        Log.d(TAG, "Options : " + optionB);
                    }
                    if (line.startsWith("c)")) {
                        String optionC = line.split(Pattern.quote("c)"))[1];
                        if( sc.hasNext() )
                        line = sc.nextLine();
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
                if( sc.hasNext() )
                line = sc.nextLine();
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
                while ( sc.hasNext() && !(line = sc.nextLine()).equals("</>") ) {
                    output += line + "\n";
                }
                if( sc.hasNext() )
                line = sc.nextLine();
                if( interviewQuestionModel != null )
                    interviewQuestionModel.setOutput(output);
            }
            if( line.contains("<Explanation>") ) {
                String explanation = "";
                while ( sc.hasNext() && !(line = sc.nextLine()).equals("</>") ) {
                    explanation += line + "\n";
                }
                if( sc.hasNext() )
                line = sc.nextLine();
                if( interviewQuestionModel != null )
                    interviewQuestionModel.setOutput(explanation);
            }

        }
        if( interviewQuestionModel != null ) {
            contentArrayList.add(interviewQuestionModel);
            Log.d(TAG, "Question : " + interviewQuestionModel.getQuestion());
        }


    }

    private String readFileFromRawDirectory(int resourceId){
        InputStream iStream = context.getResources().openRawResource(resourceId);
        System.out.println(iStream);
        try {

            int count = 0;
            byte[] bytes = new byte[iStream.available()];
            StringBuilder builder = new StringBuilder();
            while ( (count = iStream.read(bytes, 0, iStream.available())) > 0) {
                builder.append(new String(bytes, 0, count));
            }
            iStream.close();

            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }


}

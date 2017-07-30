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

            line = "";
            while ( line != null ) {
                line = bufferedReader.readLine();
                if( line == null ) {
                    break;
                }
                if( line.contains("Topic") ) {

                }
                else {
                    int questionIndex = 0;
                    int codeIndex = 0;
                    String currentQuestion = null;
                    String code = null;
                    String answer = null;
                    String output = null;
                    String explanation = null;
                    String optionA = null;
                    String optionB = null;
                    String optionC = null;
                    String optionD = null;

                    InterviewQuestionModel interviewQuestionModel = new InterviewQuestionModel();
                    if( line.contains(".") ) {

                        String[] words = line.split(Pattern.quote("."));
                        int index = Integer.parseInt(words[0]);

                        questionIndex = index;
                        currentQuestion = words[1];
                        interviewQuestionModel.setQuestion(currentQuestion);
                        Log.d(TAG, "Question : " + currentQuestion);


                        line = bufferedReader.readLine();
                        if( line.contains(".") && !line.startsWith(Pattern.quote("a)"))) {
                            code = "";
                            while ( line.contains(".") ) {
                                //Extract code part here :
                                codeIndex = index;
                                words = line.split(Pattern.quote("."));
                                code += words[1].trim() + "\n";
                                line = bufferedReader.readLine();
                            }
                            Log.d(TAG, "Code : " + code);
                            interviewQuestionModel.setCode(code);
                        }

                        if( line.startsWith("a)") ) {
                            optionA = line.split(Pattern.quote("a)"))[1];
                            interviewQuestionModel.setOptionModels(new ArrayList<OptionModel>());
                            interviewQuestionModel.getOptionModels().add(new OptionModel(1, optionA));
                            interviewQuestionModel.setTypeOfQuestion(InterviewQuestionConstants.TYPE_TRUE_FALSE);
                            Log.d(TAG, "Options : " + optionA);
                            line = bufferedReader.readLine();
                        }
                        if( line.startsWith("b)") ) {
                            optionB = line.split(Pattern.quote("b)"))[1];
                            line = bufferedReader.readLine();
                            interviewQuestionModel.getOptionModels().add(new OptionModel(2, optionB));
                            interviewQuestionModel.setTypeOfQuestion(InterviewQuestionConstants.TYPE_TRUE_FALSE);
                            Log.d(TAG, "Options : " + optionB);
                        }
                        if( line.startsWith("c)") ) {
                            optionC = line.split(Pattern.quote("c)"))[1];
                            line = bufferedReader.readLine();
                            interviewQuestionModel.getOptionModels().add(new OptionModel(3, optionC));
                            interviewQuestionModel.setTypeOfQuestion(InterviewQuestionConstants.TYPE_SINGLE_RIGHT);
                            Log.d(TAG, "Options : " + optionC);
                        }
                        if( line.startsWith("d)") ) {
                            optionD = line.split(Pattern.quote("d)"))[1];
                            line = bufferedReader.readLine();
                            interviewQuestionModel.getOptionModels().add(new OptionModel(4, optionD));
                            interviewQuestionModel.setTypeOfQuestion(InterviewQuestionConstants.TYPE_SINGLE_RIGHT);
                            Log.d(TAG, "Options : " + optionD);
                        }
                        if( line.equals("View Answer") ) {
                            line = bufferedReader.readLine();
                        }
                        if( line.startsWith("Answer:") ) {
                            answer = line.split(":")[1].trim();
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
                            line = bufferedReader.readLine();
                        }
                        if( line.startsWith("Output:") ) {
                            output = "";
                            while ( line != null  ) {

                                if( line == null ) {
                                    break;
                                }
                                output += line + "\n";
                                line = bufferedReader.readLine();
                                interviewQuestionModel.setOutput(output);
                                if( line.contains("Topic") ) {
                                    break;
                                }
                                if( line.contains(".") && line.split(Pattern.quote("."))[0].matches("^-?\\d+$") ) {
                                    break;
                                }
                            }
                            Log.d(TAG, "OutPut : " + output);

                        }
                        if( line != null && line.startsWith("Explanation:") ) {
                            explanation = line;
                            interviewQuestionModel.setExplanation(explanation);
                            while ( line != null && !line.contains(".")  ) {
                                if( line.contains(".") && line.split(Pattern.quote("."))[0].matches("^-?\\d+$") ) {
                                    break;
                                }
                                if( line.contains("Topic") ) {
                                    break;
                                }
                                if( line == null ) {
                                    break;
                                }
                                explanation += line + "\n";
                                line = bufferedReader.readLine();
                                interviewQuestionModel.setExplanation(explanation);
                            }
                            Log.d(TAG, "Explanation : " + interviewQuestionModel.getExplanation());
                        }
                        if( line == null ) {
                            break;
                        }
                    }
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

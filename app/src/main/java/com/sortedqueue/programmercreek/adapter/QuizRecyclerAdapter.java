package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.QuizModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok on 30/12/16.
 */
public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<QuizModel> quizModels;
    private CustomQuizAdapterListner adapterClickListner;
    private boolean isAnswerChecked = false;
    private ArrayList<String> mProgramExplanationList;

    public interface CustomQuizAdapterListner {
        void onOptionSelected( int position, String option );
    }

    public QuizRecyclerAdapter(Context context, ArrayList<QuizModel> quizModels, ArrayList<String> mProgramExplanationList, CustomQuizAdapterListner adapterClickListner) {
        this.context = context;
        this.quizModels = quizModels;
        this.adapterClickListner = adapterClickListner;
        this.mProgramExplanationList = mProgramExplanationList;
    }

    public void setAnswerChecked(boolean answerChecked) {
        isAnswerChecked = answerChecked;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QuizModel quizModel = quizModels.get(position);
        String solution = mProgramExplanationList.get(position);
        holder.questionTextView.setText(quizModel.getQuestionIndex() + ". " + quizModel.getQuestion());
        ArrayList<String> optionsList = quizModel.getOptionsList();
        int index = 0;
        holder.optionRadioButton1.setText(optionsList.get(index++));
        holder.optionRadioButton2.setText(optionsList.get(index++));
        holder.optionRadioButton3.setText(optionsList.get(index++));
        holder.optionRadioButton4.setText(optionsList.get(index++));
        holder.optionRadioButton1.setChecked(holder.optionRadioButton1.getText().toString().equals(quizModel.getSelectedOption()));
        holder.optionRadioButton2.setChecked(holder.optionRadioButton2.getText().toString().equals(quizModel.getSelectedOption()));
        holder.optionRadioButton3.setChecked(holder.optionRadioButton3.getText().toString().equals(quizModel.getSelectedOption()));
        holder.optionRadioButton4.setChecked(holder.optionRadioButton4.getText().toString().equals(quizModel.getSelectedOption()));
        if( isAnswerChecked ) {
            holder.optionRadioButton1.setTextColor(holder.optionRadioButton1.getText().toString().equals(solution) ? Color.BLUE : Color.RED );
            holder.optionRadioButton2.setTextColor(holder.optionRadioButton2.getText().toString().equals(solution) ? Color.BLUE : Color.RED);
            holder.optionRadioButton3.setTextColor(holder.optionRadioButton3.getText().toString().equals(solution) ? Color.BLUE : Color.RED);
            holder.optionRadioButton4.setTextColor(holder.optionRadioButton4.getText().toString().equals(solution) ? Color.BLUE : Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return quizModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.questionTextView)
        TextView questionTextView;
        @Bind(R.id.optionRadioButton1)
        RadioButton optionRadioButton1;
        @Bind(R.id.optionRadioButton2)
        RadioButton optionRadioButton2;
        @Bind(R.id.optionRadioButton3)
        RadioButton optionRadioButton3;
        @Bind(R.id.optionRadioButton4)
        RadioButton optionRadioButton4;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            optionRadioButton1.setOnClickListener(this);
            optionRadioButton2.setOnClickListener(this);
            optionRadioButton3.setOnClickListener(this);
            optionRadioButton4.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION ) {
                String optionSelected = "";
                if( optionRadioButton1.isChecked() ) {
                    optionSelected = optionRadioButton1.getText().toString();
                }
                else if( optionRadioButton2.isChecked() ) {
                    optionSelected = optionRadioButton1.getText().toString();
                }
                else if( optionRadioButton3.isChecked() ) {
                    optionSelected = optionRadioButton1.getText().toString();
                }
                else if( optionRadioButton4.isChecked() ) {
                    optionSelected = optionRadioButton1.getText().toString();
                }

                if( !optionSelected.equals("") ) {
                    QuizModel quizModel = quizModels.get(position);
                    quizModel.setSelectedOption(optionSelected);
                    notifyItemChanged(position);
                }
            }
        }
    }

}

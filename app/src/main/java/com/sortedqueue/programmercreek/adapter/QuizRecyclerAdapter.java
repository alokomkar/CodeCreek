package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.QuizModel;

import java.util.ArrayList;

import butterknife.BindView;
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
        void onOptionSelected(int position, String option);
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
        holder.option1TextView.setText(optionsList.get(index++));
        holder.option2TextView.setText(optionsList.get(index++));
        holder.option3TextView.setText(optionsList.get(index++));
        holder.option4TextView.setText(optionsList.get(index++));

        holder.option1Layout.setSelected(holder.option1TextView.getText().toString().equals(quizModel.getSelectedOption()));
        holder.option2Layout.setSelected(holder.option2TextView.getText().toString().equals(quizModel.getSelectedOption()));
        holder.option3Layout.setSelected(holder.option3TextView.getText().toString().equals(quizModel.getSelectedOption()));
        holder.option4Layout.setSelected(holder.option4TextView.getText().toString().equals(quizModel.getSelectedOption()));

        if (isAnswerChecked) {
            if (holder.option1Layout.isSelected()) {
                checkAndChangeUI(holder, holder.option1Layout, holder.option1TextView, solution, position);
            }
            else if (holder.option2Layout.isSelected()) {
                checkAndChangeUI(holder, holder.option2Layout, holder.option2TextView, solution, position);
            }
            else if (holder.option3Layout.isSelected()) {
                checkAndChangeUI(holder, holder.option3Layout, holder.option3TextView, solution, position);
            }
            else if (holder.option4Layout.isSelected()) {
                checkAndChangeUI(holder, holder.option4Layout, holder.option4TextView, solution, position);
            }
        }
    }



    private void checkAndChangeUI(ViewHolder holder, LinearLayout optionLayout, TextView optionTextView, String solution, int index) {

        holder.option1Layout.setBackground(null);
        holder.option2Layout.setBackground(null);
        holder.option3Layout.setBackground(null);
        holder.option4Layout.setBackground(null);

        holder.option1TextView.setTextColor(Color.BLACK);
        holder.option2TextView.setTextColor(Color.BLACK);
        holder.option3TextView.setTextColor(Color.BLACK);
        holder.option4TextView.setTextColor(Color.BLACK);

        optionTextView.setTextColor(Color.WHITE);
        optionLayout.setBackground(ContextCompat.getDrawable(context, optionTextView.getText().toString().equals(solution) ? R.drawable.button_check : R.drawable.button_clear));

    }

    @Override
    public int getItemCount() {
        return quizModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.questionTextView)
        TextView questionTextView;
        @BindView(R.id.option1TextView)
        TextView option1TextView;
        @BindView(R.id.option1Layout)
        LinearLayout option1Layout;
        @BindView(R.id.option2TextView)
        TextView option2TextView;
        @BindView(R.id.option2Layout)
        LinearLayout option2Layout;
        @BindView(R.id.option3TextView)
        TextView option3TextView;
        @BindView(R.id.option3Layout)
        LinearLayout option3Layout;
        @BindView(R.id.option4TextView)
        TextView option4TextView;
        @BindView(R.id.option4Layout)
        LinearLayout option4Layout;
        @BindView(R.id.option1ImageView)
        ImageView option1ImageView;
        @BindView(R.id.option2ImageView)
        ImageView option2ImageView;
        @BindView(R.id.option3ImageView)
        ImageView option3ImageView;
        @BindView(R.id.option4ImageView)
        ImageView option4ImageView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            option1Layout.setOnClickListener(this);
            option2Layout.setOnClickListener(this);
            option3Layout.setOnClickListener(this);
            option4Layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if( isAnswerChecked ) {
                return;
            }
            int position = getAdapterPosition();
            switch (view.getId()) {
                case R.id.option1Layout:
                    option1Layout.setSelected(true);
                    option2Layout.setSelected(false);
                    option3Layout.setSelected(false);
                    option4Layout.setSelected(false);
                    option1ImageView.setSelected(true);
                    option2ImageView.setSelected(false);
                    option3ImageView.setSelected(false);
                    option4ImageView.setSelected(false);
                    break;
                case R.id.option2Layout:
                    option1Layout.setSelected(false);
                    option2Layout.setSelected(true);
                    option3Layout.setSelected(false);
                    option4Layout.setSelected(false);
                    option1ImageView.setSelected(false);
                    option2ImageView.setSelected(true);
                    option3ImageView.setSelected(false);
                    option4ImageView.setSelected(false);
                    break;
                case R.id.option3Layout:
                    option1Layout.setSelected(false);
                    option2Layout.setSelected(false);
                    option3Layout.setSelected(true);
                    option4Layout.setSelected(false);
                    option1ImageView.setSelected(false);
                    option2ImageView.setSelected(false);
                    option3ImageView.setSelected(true);
                    option4ImageView.setSelected(false);
                    break;
                case R.id.option4Layout:
                    option1Layout.setSelected(false);
                    option2Layout.setSelected(false);
                    option3Layout.setSelected(false);
                    option4Layout.setSelected(true);
                    option1ImageView.setSelected(false);
                    option2ImageView.setSelected(false);
                    option3ImageView.setSelected(false);
                    option4ImageView.setSelected(true);
                    break;

            }

            if (position != RecyclerView.NO_POSITION) {
                String optionSelected = "";
                if (option1Layout.isSelected()) {
                    optionSelected = option1TextView.getText().toString();
                } else if (option2Layout.isSelected()) {
                    optionSelected = option2TextView.getText().toString();
                } else if (option3Layout.isSelected()) {
                    optionSelected = option3TextView.getText().toString();
                } else if (option4Layout.isSelected()) {
                    optionSelected = option4TextView.getText().toString();
                }

                if (!optionSelected.equals("")) {
                    QuizModel quizModel = quizModels.get(position);
                    quizModel.setSelectedOption(optionSelected);
                    notifyDataSetChanged();
                }
            }
        }
    }

}

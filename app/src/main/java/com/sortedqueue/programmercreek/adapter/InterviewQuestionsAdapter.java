package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.InterviewQuestionModel;
import com.sortedqueue.programmercreek.database.OptionModel;
import com.sortedqueue.programmercreek.util.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.sortedqueue.programmercreek.constants.InterviewQuestionConstants.TYPE_MULTIPLE_RIGHT;
import static com.sortedqueue.programmercreek.constants.InterviewQuestionConstants.TYPE_REARRANGE;
import static com.sortedqueue.programmercreek.constants.InterviewQuestionConstants.TYPE_SINGLE_RIGHT;
import static com.sortedqueue.programmercreek.constants.InterviewQuestionConstants.TYPE_TRUE_FALSE;

/**
 * Created by Alok on 09/03/17.
 */
public class InterviewQuestionsAdapter extends RecyclerView.Adapter<InterviewQuestionsAdapter.ViewHolder>
 implements ItemTouchHelperAdapter {


    private InterviewQuestionModel interviewQuestionModel;
    private boolean isAnswerChecked = false;
    private Context context;
    private ArrayList<OptionModel> optionModels;

    public InterviewQuestionsAdapter(InterviewQuestionModel interviewQuestionModel) {
        this.interviewQuestionModel = interviewQuestionModel;
        this.optionModels = new ArrayList<>();
        optionModels.addAll(interviewQuestionModel.getOptionModels());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interview_option, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OptionModel optionModel = getItemAtPosition( position );
        holder.progamLineTxtView.setText(optionModel.getOption());
        if( isAnswerChecked ) switch (interviewQuestionModel.getTypeOfQuestion()) {
            case TYPE_TRUE_FALSE :
            case TYPE_SINGLE_RIGHT :
                holder.optionCardView.setCardBackgroundColor(
                        ContextCompat.getColor(
                                context,
                                optionModel.getOptionId() == interviewQuestionModel.getCorrectOption() ?
                                        R.color.md_green_500 :
                                        R.color.md_red_500));
                holder.progamLineTxtView.setTextColor(ContextCompat.getColor(context, R.color.white));
                break;
            case TYPE_MULTIPLE_RIGHT :
                break;
            case TYPE_REARRANGE :
                break;
        }

    }

    public void isAnswerChecked( boolean isAnswerChecked ) {
        this.isAnswerChecked = isAnswerChecked;
        notifyDataSetChanged();
    }

    private OptionModel getItemAtPosition(int position) {
        return optionModels.get(position);
    }

    @Override
    public int getItemCount() {
        return optionModels.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(optionModels, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(optionModels, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        interviewQuestionModel.getOptionModels().remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.progamLineTxtView)
        TextView progamLineTxtView;
        @Bind(R.id.optionCardView)
        CardView optionCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch ( interviewQuestionModel.getTypeOfQuestion() ) {
                case TYPE_TRUE_FALSE :
                case TYPE_SINGLE_RIGHT :
                    isAnswerChecked(true);
                    break;
                case TYPE_MULTIPLE_RIGHT :
                    break;
                case TYPE_REARRANGE :
                    break;
            }
        }
    }


}

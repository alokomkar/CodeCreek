package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.InterviewQuestionModel;
import com.sortedqueue.programmercreek.database.OptionModel;
import com.sortedqueue.programmercreek.util.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
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
    private CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner;

    public InterviewQuestionsAdapter(InterviewQuestionModel interviewQuestionModel, CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner) {
        this.interviewQuestionModel = interviewQuestionModel;
        this.optionModels = new ArrayList<>();
        optionModels.addAll(interviewQuestionModel.getOptionModels());
        this.adapterClickListner = adapterClickListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interview_option, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OptionModel optionModel = getItemAtPosition(position);
        holder.progamLineTxtView.setText(optionModel.getOption());

        if( interviewQuestionModel.getTypeOfQuestion() == TYPE_MULTIPLE_RIGHT ) {
            holder.optionCardView.setBackgroundColor(
                    ContextCompat.getColor(
                            context,
                            optionModel.isSelected() ?
                                    R.color.md_blue_grey_100 :
                                    R.color.white));
        }

        if( isAnswerChecked ) {
            switch (interviewQuestionModel.getTypeOfQuestion()) {
                case TYPE_TRUE_FALSE:
                case TYPE_SINGLE_RIGHT:
                    holder.optionCardView.setBackgroundColor(
                            ContextCompat.getColor(
                                    context,
                                    optionModel.getOptionId() == interviewQuestionModel.getCorrectOption() ?
                                            R.color.md_green_500 :
                                            R.color.md_red_500));
                    holder.progamLineTxtView.setTextColor(ContextCompat.getColor(context, R.color.white));
                    break;
                case TYPE_MULTIPLE_RIGHT:
                    if (optionModel.isSelected()) {
                        holder.optionCardView.setBackgroundColor(
                                ContextCompat.getColor(
                                        context,
                                        interviewQuestionModel.getCorrectOptions().contains(optionModel.getOptionId()) ?
                                                R.color.md_green_500 :
                                                R.color.md_red_500));
                        holder.progamLineTxtView.setTextColor(ContextCompat.getColor(context, R.color.white));
                    }
                    break;
                case TYPE_REARRANGE:
                    holder.optionCardView.setBackgroundColor(
                            ContextCompat.getColor(
                                    context,
                                    optionModel.getOptionId() == interviewQuestionModel.getCorrectSequence().get(position) ?
                                            R.color.md_green_500 :
                                            R.color.md_red_500));
                    holder.progamLineTxtView.setTextColor(ContextCompat.getColor(context, R.color.white));
                    break;
            }
        }

        holder.reorderImageView.setVisibility( interviewQuestionModel.getTypeOfQuestion() == TYPE_REARRANGE ? View.VISIBLE : View.GONE);


    }

    public void isAnswerChecked(boolean isAnswerChecked) {
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
        @BindView(R.id.progamLineTxtView)
        TextView progamLineTxtView;
        @BindView(R.id.reorderImageView)
        ImageView reorderImageView;
        @BindView(R.id.optionCardView)
        LinearLayout optionCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position == RecyclerView.NO_POSITION) {
                return;
            }
            switch (interviewQuestionModel.getTypeOfQuestion()) {
                case TYPE_TRUE_FALSE:
                case TYPE_SINGLE_RIGHT:
                    isAnswerChecked(true);
                    break;
                case TYPE_MULTIPLE_RIGHT:
                    OptionModel optionModel = getItemAtPosition(position);
                    if (optionModel != null) {
                        optionModel.setSelected(!optionModel.isSelected());
                        notifyItemChanged(position);
                    }
                    break;
                case TYPE_REARRANGE:
                    break;
            }
            adapterClickListner.onItemClick(position);
        }
    }


}

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
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants.MULTI_CHOICE;
import static com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants.REARRANGE;
import static com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants.SINGLE_CHOICE;

/**
 * Created by Alok on 06/10/17.
 */

public class SubTopicsQuestionAdapter extends RecyclerView.Adapter<SubTopicsQuestionAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {



    private boolean isAnswerChecked = false;
    private Context context;
    private ArrayList<OptionModel> optionModels;
    private CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner;
    private String quizType;

    public SubTopicsQuestionAdapter(ArrayList<OptionModel> optionModels, String quizType, CustomProgramRecyclerViewAdapter.AdapterClickListner adapterClickListner) {
        this.optionModels = optionModels;
        this.quizType = quizType;
        this.adapterClickListner = adapterClickListner;
    }

    @Override
    public SubTopicsQuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interview_option, parent, false);
        context = parent.getContext();
        return new SubTopicsQuestionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubTopicsQuestionAdapter.ViewHolder holder, int position) {
        OptionModel optionModel = getItemAtPosition(position);
        holder.progamLineTxtView.setText(optionModel.getOption());

        if( quizType.equals(SINGLE_CHOICE) || quizType.equals(MULTI_CHOICE) ) {
            holder.optionCardView.setBackgroundColor(
                    ContextCompat.getColor(
                            context,
                            optionModel.isSelected() ?
                                    R.color.md_blue_grey_100 :
                                    R.color.white));
        }

        if( isAnswerChecked ) {
            switch (quizType) {
                case SINGLE_CHOICE:
                    holder.optionCardView.setBackgroundColor(
                            ContextCompat.getColor(
                                    context,
                                    optionModel.getOption().equals( correctAnswers.get(0) ) ?
                                            R.color.md_green_500 :
                                            R.color.md_red_500));
                    holder.progamLineTxtView.setTextColor(ContextCompat.getColor(context, R.color.white));
                    break;
                case MULTI_CHOICE:
                    if (optionModel.isSelected()) {
                        holder.optionCardView.setBackgroundColor(
                                ContextCompat.getColor(
                                        context,
                                        correctAnswers.contains(optionModel.getOption()) ?
                                                R.color.md_green_500 :
                                                R.color.md_red_500));
                        holder.progamLineTxtView.setTextColor(ContextCompat.getColor(context, R.color.white));
                    }
                    break;
                case REARRANGE:
                    holder.optionCardView.setBackgroundColor(
                            ContextCompat.getColor(
                                    context,
                                    optionModel.getOption().equals(correctAnswers.get(position)) ?
                                            R.color.md_green_500 :
                                            R.color.md_red_500));
                    holder.progamLineTxtView.setTextColor(ContextCompat.getColor(context, R.color.white));
                    break;
            }
        }

        holder.reorderImageView.setVisibility( quizType.equals( REARRANGE ) ? View.VISIBLE : View.GONE);


    }

    public void isAnswerChecked(boolean isAnswerChecked) {
        this.isAnswerChecked = isAnswerChecked;
        notifyDataSetChanged();
    }

    public int countCorrectAnswers() {
        int correctAnswers = 0;
        switch ( quizType ) {
            case SINGLE_CHOICE :
                for( OptionModel optionModel : optionModels ) {
                    if( optionModel.isSelected() && optionModel.getOption().equals( this.correctAnswers.get(0) ) ) {
                        correctAnswers = 1;
                    }
                }
                break;
            case MULTI_CHOICE :
                for( OptionModel optionModel : optionModels ) {
                    if( optionModel.isSelected() && this.correctAnswers.contains(optionModel.getOption()) ) {
                        correctAnswers++;
                    }
                }
                break;
            case REARRANGE :
                int position = 0;
                for( OptionModel optionModel : optionModels ) {
                    if( optionModel.isSelected() && optionModel.getOption().equals(this.correctAnswers.get(position++)) ) {
                        correctAnswers++;
                    }
                }
                break;
        }
        return correctAnswers;
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
    }

    private ArrayList<String> correctAnswers;
    public void setCorrectAnswers(String answer) {
        correctAnswers = new ArrayList<>();
        correctAnswers.addAll(Arrays.asList(answer.split(Pattern.quote("|||"))));
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
            switch (quizType) {
                case SINGLE_CHOICE:
                case MULTI_CHOICE:
                    OptionModel optionModel = getItemAtPosition(position);
                    if (optionModel != null) {
                        optionModel.setSelected(!optionModel.isSelected());
                        notifyItemChanged(position);
                    }
                    break;
                case REARRANGE:
                    break;
            }
            adapterClickListner.onItemClick(position);
        }
    }


}

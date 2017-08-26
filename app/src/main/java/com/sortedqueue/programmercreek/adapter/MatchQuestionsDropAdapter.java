package com.sortedqueue.programmercreek.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.util.DoubleClickListener;
import com.sortedqueue.programmercreek.util.PrettifyHighlighter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-08-14.
 */
public class MatchQuestionsDropAdapter extends RecyclerView.Adapter<MatchQuestionsDropAdapter.ViewHolder> {

    private ArrayList<ProgramTable> mProgramList;
    private PrettifyHighlighter prettifyHighlighter;
    private Drawable choiceDrawable;
    private Drawable correctDrawable;
    private Drawable wrongDrawable;
    private boolean isChecked;
    private int white;
    private int option;
    private int answer;
    private int wrong;
    private boolean isFillBlanks = false;

    public MatchQuestionsDropAdapter(ArrayList<ProgramTable> mProgramList) {
        this.mProgramList = mProgramList;
        prettifyHighlighter = PrettifyHighlighter.getInstance();
    }

    public MatchQuestionsDropAdapter(ArrayList<ProgramTable> mProgramQuestionList, boolean isFillBlanks) {
        this.isFillBlanks = true;
        this.mProgramList = mProgramQuestionList;
        prettifyHighlighter = PrettifyHighlighter.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        choiceDrawable = ContextCompat.getDrawable(parent.getContext(), R.drawable.option);
        correctDrawable = ContextCompat.getDrawable(parent.getContext(), R.drawable.answer);
        wrongDrawable = ContextCompat.getDrawable(parent.getContext(), R.drawable.error);
        answer = ContextCompat.getColor(parent.getContext(), R.color.md_green_200);
        option = ContextCompat.getColor(parent.getContext(), R.color.md_blue_grey_100);
        wrong = ContextCompat.getColor(parent.getContext(), R.color.md_red_200);
        white = ContextCompat.getColor(parent.getContext(), R.color.white);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match_question, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProgramTable programTable = getItemAtPosition(position);
        if( programTable.isChoice ) {
            if( programTable.getProgram_Line().equals("") ) {
                if( isFillBlanks ) {
                    holder.questionTextView.setText("");
                }
                else {
                    holder.questionTextView.setText(programTable.getProgram_Line_Description());
                }

            }
            else {
                String programLine = programTable.getProgram_Line();
                if (programLine.contains("<") || programLine.contains(">")) {
                    holder.questionTextView.setText(programTable.getProgram_Line());
                    holder.questionTextView.setTextColor(Color.parseColor("#006699"));
                } else {
                    String programLineHtml = prettifyHighlighter.highlight("c", programLine);
                    if( Build.VERSION.SDK_INT >= 24 ) {
                        holder.questionTextView.setText(Html.fromHtml(programLineHtml, Html.FROM_HTML_MODE_LEGACY));
                    }
                    else {
                        holder.questionTextView.setText(Html.fromHtml(programLineHtml));
                    }
                }
            }

            if( isChecked ) {
                holder.itemView.setBackground(programTable.isCorrect ? correctDrawable : wrongDrawable);
                if( programTable.isCorrect ) {
                    String programLine = programTable.getProgram_Line();
                    if (programLine.contains("<") || programLine.contains(">")) {
                        holder.questionTextView.setText(programTable.getProgram_Line());
                        holder.questionTextView.setTextColor(Color.parseColor("#006699"));
                    } else {
                        String programLineHtml = prettifyHighlighter.highlight("c", programLine);
                        if( Build.VERSION.SDK_INT >= 24 ) {
                            holder.questionTextView.setText(Html.fromHtml(programLineHtml, Html.FROM_HTML_MODE_LEGACY));
                        }
                        else {
                            holder.questionTextView.setText(Html.fromHtml(programLineHtml));
                        }
                    }
                }
                else {
                    if( isFillBlanks ) {
                        holder.questionTextView.setText("");
                    }
                    else
                        holder.questionTextView.setText(programTable.getProgram_Line_Description());
                }
                holder.questionTextView.setTextColor(Color.parseColor("#FFFFFF"));
            }
            else {
                holder.itemView.setBackground(choiceDrawable);
            }

        }
        else {
            String programLine = programTable.getProgram_Line();
            if (programLine.contains("<") || programLine.contains(">")) {
                holder.questionTextView.setText(programTable.getProgram_Line());
                holder.questionTextView.setTextColor(Color.parseColor("#006699"));
            } else {
                String programLineHtml = prettifyHighlighter.highlight("c", programLine);
                if( Build.VERSION.SDK_INT >= 24 ) {
                    holder.questionTextView.setText(Html.fromHtml(programLineHtml, Html.FROM_HTML_MODE_LEGACY));
                }
                else {
                    holder.questionTextView.setText(Html.fromHtml(programLineHtml));
                }
            }
            holder.itemView.setBackgroundColor(white);
            holder.itemView.setBackground(null);
            //holder.itemView.setOnDragListener(null);
            //holder.itemView.setOnClickListener(null);


        }
    }

    public void setChecked(boolean checked, ArrayList<ProgramTable> programTables) {
        isChecked = checked;
        this.mProgramList = programTables;
        notifyDataSetChanged();
    }

    public ArrayList<ProgramTable> getProgramList() {
        return mProgramList;
    }

    public ProgramTable getItemAtPosition(int position) {
        return mProgramList.get(position);
    }

    @Override
    public int getItemCount() {
        return mProgramList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnDragListener, View.OnLongClickListener {
        @BindView(R.id.questionTextView)
        TextView questionTextView;
        @BindView(R.id.matchQuestionLayout)
        LinearLayout matchQuestionLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnDragListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(new DoubleClickListener() {
                @Override
                public void onSingleClick(View v) {

                }

                @Override
                public void onDoubleClick(View v) {

                }
            });

        }

        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:
                    int position = getAdapterPosition();
                    if( position != RecyclerView.NO_POSITION  ) {
                        ProgramTable programTable = getItemAtPosition(position);
                        if( programTable.isChoice ) {
                            //handle the dragged view being dropped over a drop view
                            View loacalView = (View) dragEvent.getLocalState();
                            //stop displaying the view where it was before it was dragged
                            //view.setVisibility(View.INVISIBLE);
                            //view dragged item is being dropped on
                            TextView dropTarget = (TextView) view.findViewById(R.id.questionTextView);
                            //view being dragged and dropped
                            TextView dropped = (TextView) loacalView.findViewById(R.id.questionTextView);
                            dropped.setOnTouchListener(null);
                            //update the text in the target view to reflect the data being dropped
                            dropTarget.setText(dropped.getText());

                            //make it bold to highlight the fact that an item has been dropped
                            //dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                            //if an item has already been dropped here, there will be a tag
                            Object tag = dropTarget.getTag();
                            //if there is already an item here, set it back visible in its original place
                            if (tag != null) {
                                //the tag is the view id already dropped here
                                int existingID = (Integer) tag;
                                //set the original view visible again
                                if( loacalView != null && loacalView.findViewById(existingID) != null )
                                    loacalView.findViewById(existingID).setVisibility(View.VISIBLE);
                            }
                            //set the tag in the target view being dropped on - to the ID of the view being dropped
                            if( dropTarget != null && dropped != null )
                                dropTarget.setTag(dropped.getId());

                            programTable.setProgram_Line(dropped.getText().toString());
                            notifyDataSetChanged();
                        }
                    }


                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }





        @Override
        public boolean onLongClick(View view) {
            int postion = getAdapterPosition();
            if( postion != RecyclerView.NO_POSITION ) {
                ProgramTable programTable = getItemAtPosition(postion);
                if( programTable.isChoice ) {
                    programTable.setProgram_Line("");
                    if( isFillBlanks ) {
                        questionTextView.setText("");
                    }
                    else {
                        questionTextView.setText(programTable.getProgram_Line_Description());
                        questionTextView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.md_blue_grey_600));
                        questionTextView.setBackground(choiceDrawable);
                    }
                }
            }
            return false;
        }


    }

}

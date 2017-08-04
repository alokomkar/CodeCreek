package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.QuickReference;
import com.sortedqueue.programmercreek.util.PrettifyHighlighter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 04/08/17.
 */

public class QuickRefernceRecyclerAdapter extends RecyclerView.Adapter<QuickRefernceRecyclerAdapter.ViewHolder> {

    private ArrayList<QuickReference> quickReferences;
    private LayoutInflater inflater;
    private PrettifyHighlighter prettifyHighlighter;
    private Context context;

    public QuickRefernceRecyclerAdapter(ArrayList<QuickReference> cQuickReference) {
        this.quickReferences = cQuickReference;
        this.prettifyHighlighter = PrettifyHighlighter.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_quick_reference, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        QuickReference quickReference = quickReferences.get(position);
        holder.headerTextView.setText(quickReference.getHeader());
        for( String content : quickReference.getContentArray() ) {
            View contentView = inflater.inflate(R.layout.item_edit_code, null);
            EditText codeEditText = (EditText) contentView.findViewById(R.id.codeEditText);
            codeEditText.setEnabled(false);
            if( content.contains("<") || content.contains(">")) {
                codeEditText.setText(content);
                codeEditText.setTextColor(Color.parseColor("#006699"));
            }
            else {
                if(Build.VERSION.SDK_INT >= 24) {
                    codeEditText.setText(Html.fromHtml(prettifyHighlighter.highlight("c", content), Html.FROM_HTML_MODE_LEGACY));
                }
                else {
                    codeEditText.setText(Html.fromHtml(prettifyHighlighter.highlight("c", content)));
                }
            }
            holder.explanationLayout.addView(contentView);
        }

        holder.explanationLayout.setVisibility( quickReference.isExpanded ? View.GONE : View.VISIBLE );

        holder.indicatorImageview.setImageDrawable(ContextCompat.getDrawable(context, quickReference.isExpanded ? android.R.drawable.arrow_down_float : android.R.drawable.arrow_up_float ));

    }

    public void expandItem( int position ) {
        QuickReference quickReference = quickReferences.get(position);
        quickReference.isExpanded = true;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return quickReferences.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.headerTextView)
        TextView headerTextView;
        @BindView(R.id.explanationLayout)
        LinearLayout explanationLayout;
        @BindView(R.id.indicatorImageview)
        ImageView indicatorImageview;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            headerTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if( position != RecyclerView.NO_POSITION ) {
                QuickReference quickReference = quickReferences.get(position);
                quickReference.isExpanded = !quickReference.isExpanded;
                explanationLayout.setVisibility( quickReference.isExpanded ? View.GONE : View.VISIBLE );
                if( explanationLayout.getVisibility() == View.GONE ) {
                    explanationLayout.removeAllViews();
                }
                notifyItemChanged(position);
            }
        }
    }


}

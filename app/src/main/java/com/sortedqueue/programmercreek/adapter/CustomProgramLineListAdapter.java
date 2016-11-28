package com.sortedqueue.programmercreek.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.util.PrettifyHighlighter;


public class CustomProgramLineListAdapter extends ArrayAdapter<String> {

	private Context mContext;
	private ArrayList <String> mProgramLineList;
	private static LayoutInflater mLayoutInflater = null;
	PrettifyHighlighter highlighter = new PrettifyHighlighter();
	String highlighted = null;


	public CustomProgramLineListAdapter( Context context, int resource, int textViewResourceId,
			ArrayList<String> programLineList ) {
		super( context, resource, textViewResourceId, programLineList);
		this.mContext = context;
		this.mProgramLineList = programLineList;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return mProgramLineList.size();
	}

	@Override
	public String getItem(int position) {
		return mProgramLineList.get(position);
	}

	@Override
	public long getItemId(int itemId) {
		return itemId;
	}

	ViewHolder mViewHolder = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(convertView==null) {
			view = mLayoutInflater.inflate(R.layout.program_list, null);
			mViewHolder = new ViewHolder();
			mViewHolder.programLineTextView = (TextView) view.findViewById(R.id.progamLineTxtView);
			view.setTag(mViewHolder);
		}
		else {
			mViewHolder = (ViewHolder) view.getTag();
		}
			

		String programLine = mProgramLineList.get(position);
        if( programLine.contains("<") || programLine.contains(">")) {
        	mViewHolder.programLineTextView.setText(programLine);
        	mViewHolder.programLineTextView.setTextColor(Color.parseColor("#006699"));
            return view;
        }
        
		highlighted = highlighter.highlight("c", " "+mProgramLineList.get(position));
		mViewHolder.programLineTextView.setText(Html.fromHtml(highlighted));

		return view;
	}
	
	static class ViewHolder { 
		TextView programLineTextView;
	}

}

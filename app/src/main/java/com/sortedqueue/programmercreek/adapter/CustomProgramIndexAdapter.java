package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;
import java.util.List;


public class CustomProgramIndexAdapter extends ArrayAdapter<Program_Index> {

	private Context mContext;
	private ArrayList <Program_Index> mProgramIndexList;
	private static LayoutInflater mLayoutInflater = null;
	private String programType;
	//private ArrayList<Scores_Table> mScores_Tables;

	public CustomProgramIndexAdapter( Context context, int resource, int textViewResourceId,
			List<Program_Index> program_Indexs ) {
		super( context, resource, textViewResourceId, program_Indexs);
		this.mContext = context;
		this.mProgramIndexList = (ArrayList<Program_Index>) program_Indexs;
		programType = new CreekPreferences(mContext).getProgramLanguage().substring(0).toUpperCase();
		//this.mScores_Tables = (ArrayList<Scores_Table>) scores_Tables;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if( mProgramIndexList == null ) {
			return 0;
		}
		return mProgramIndexList.size();
	}

	@Override
	public Program_Index getItem(int position) {
		return mProgramIndexList.get(position);
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
			view = mLayoutInflater.inflate(R.layout.index_list, null);
			mViewHolder = new ViewHolder();
			mViewHolder.programLineTextView = (TextView) view.findViewById(R.id.txtViewProgDescription);
			mViewHolder.wikiTextView = (TextView) view.findViewById(R.id.wikiTextView);
			mViewHolder.programTypeTextView = (TextView) view.findViewById(R.id.programTypeTextView);
			view.setTag(mViewHolder);
		}
		else {
			view = convertView;
			mViewHolder = (ViewHolder)view.getTag();
		}
		mViewHolder.programTypeTextView.setText(programType);
		mViewHolder.programLineTextView.setText(mProgramIndexList.get(position).toString());

		return view;
	}

	static final class ViewHolder { 
		TextView programLineTextView;
		TextView programTypeTextView;
		TextView wikiTextView;
	}

}

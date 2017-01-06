package com.sortedqueue.programmercreek.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sortedqueue.programmercreek.interfaces.DropListenerInterface;
import com.sortedqueue.programmercreek.interfaces.RemoveListenerInterface;
import com.sortedqueue.programmercreek.util.PrettifyHighlighter;

import java.util.ArrayList;


public final class DragNDropAdapter extends BaseAdapter implements RemoveListenerInterface, DropListenerInterface {

	private int[] mIds;
    private int[] mLayouts;
    private LayoutInflater mInflater;
    private ArrayList<String> mContent;
    
    PrettifyHighlighter highlighter = new PrettifyHighlighter();
	//String highlighted = null;
	

    public DragNDropAdapter(Context context, ArrayList<String> content) {
        init(context,new int[]{android.R.layout.simple_list_item_1},new int[]{android.R.id.text1}, content);
    }
    
    public DragNDropAdapter(Context context, int[] itemLayouts, int[] itemIDs, ArrayList<String> content) {
    	init(context,itemLayouts,itemIDs, content);
    }

    private void init(Context context, int[] layouts, int[] ids, ArrayList<String> content) {
    	// Cache the LayoutInflate to avoid asking for a new one each time.
    	mInflater = LayoutInflater.from(context);
    	mIds = ids;
    	mLayouts = layouts;
    	mContent = content;
    }
    
    /**
     * The number of items in the list
     * @see android.widget.ListAdapter#getCount()
     */
    public int getCount() {
        return mContent.size();
    }

    /**
     * Since the data comes from an array, just returning the index is
     * sufficient to get at the data. If we were using a more complex data
     * structure, we would return whatever object represents one row in the
     * list.
     *
     * @see android.widget.ListAdapter#getItem(int)
     */
    public String getItem(int position) {
        return mContent.get(position);
    }

    /**
     * Use the array index as a unique id.
     * @see android.widget.ListAdapter#getItemId(int)
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * Make a view to hold each row.
     *
     * @see android.widget.ListAdapter#getView(int, View,
     *      ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        // A HeaderViewHolder keeps references to children views to avoid unneccessary calls
        // to findViewById() on each row.
        ViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
            convertView = mInflater.inflate(mLayouts[0], null);
            //convertView.setBackgroundResource(R.drawable.error);
            // Creates a HeaderViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(mIds[0]);

            convertView.setTag(holder);
        } else {
            // Get the HeaderViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

        // Bind the data efficiently with the holder.
        String programLine = mContent.get(position).trim();
        if( programLine.contains("<") || programLine.contains(">")) {
        	holder.text.setText(programLine);
        	holder.text.setTextColor(Color.parseColor("#006699"));
            return convertView;
        }
        //highlighted = highlighter.highlight("c++", mContent.get(position));
        
        //progarmLine = StringEscapeUtils.unescapeHtml4(highlighted);
        if(Build.VERSION.SDK_INT >= 24 ) {
            holder.text.setText(Html.fromHtml(highlighter.highlight("c++", programLine), Html.FROM_HTML_MODE_LEGACY));
        }
        else {
            holder.text.setText(Html.fromHtml(highlighter.highlight("c++", programLine)));
        }

        //holder.text.setText(programLine);

        return convertView;
    }

    static class ViewHolder {
        TextView text;
    }

	public void onRemove(int which) {
		if (which < 0 || which > mContent.size()) return;		
		mContent.remove(which);
	}

	public void onDrop(int from, int to) {
		String temp = mContent.get(from);
		mContent.remove(from);
		mContent.add(to,temp);
	}
}
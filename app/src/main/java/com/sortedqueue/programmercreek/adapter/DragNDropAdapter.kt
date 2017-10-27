package com.sortedqueue.programmercreek.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.sortedqueue.programmercreek.interfaces.DropListenerInterface
import com.sortedqueue.programmercreek.interfaces.RemoveListenerInterface
import com.sortedqueue.programmercreek.util.PrettifyHighlighter

import java.util.ArrayList


class DragNDropAdapter : BaseAdapter, RemoveListenerInterface, DropListenerInterface {

    private var mIds: IntArray? = null
    private var mLayouts: IntArray? = null
    private var mInflater: LayoutInflater? = null
    private var mContent: ArrayList<String>? = null

    internal var highlighter = PrettifyHighlighter.getInstance()
    //String highlighted = null;


    constructor(context: Context, content: ArrayList<String>) {
        init(context, intArrayOf(android.R.layout.simple_list_item_1), intArrayOf(android.R.id.text1), content)
    }

    constructor(context: Context, itemLayouts: IntArray, itemIDs: IntArray, content: ArrayList<String>) {
        init(context, itemLayouts, itemIDs, content)
    }

    private fun init(context: Context, layouts: IntArray, ids: IntArray, content: ArrayList<String>) {
        // Cache the LayoutInflate to avoid asking for a new one each time.
        mInflater = LayoutInflater.from(context)
        mIds = ids
        mLayouts = layouts
        mContent = content
    }

    fun getmContent(): ArrayList<String> {
        return mContent!!
    }

    /**
     * The number of items in the list
     * @see android.widget.ListAdapter.getCount
     */
    override fun getCount(): Int {
        return mContent!!.size
    }

    /**
     * Since the data comes from an array, just returning the index is
     * sufficient to get at the data. If we were using a more complex data
     * structure, we would return whatever object represents one row in the
     * list.

     * @see android.widget.ListAdapter.getItem
     */
    override fun getItem(position: Int): String {
        return mContent!![position]
    }

    /**
     * Use the array index as a unique id.
     * @see android.widget.ListAdapter.getItemId
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * Make a view to hold each row.

     * @see android.widget.ListAdapter.getView
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        // A HeaderViewHolder keeps references to children views to avoid unneccessary calls
        // to findViewById() on each row.
        val holder: ViewHolder

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
            convertView = mInflater!!.inflate(mLayouts!![0], null)
            //convertView.setBackgroundResource(R.drawable.error);
            // Creates a HeaderViewHolder and store references to the two children views
            // we want to bind data to.
            holder = ViewHolder()
            holder.text = convertView!!.findViewById(mIds!![0]) as TextView

            convertView.tag = holder
        } else {
            // Get the HeaderViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = convertView.tag as ViewHolder
        }

        // Bind the data efficiently with the holder.
        val programLine = mContent!![position].trim { it <= ' ' }
        if (programLine.contains("<") || programLine.contains(">")) {
            holder.text!!.text = programLine
            holder.text!!.setTextColor(Color.parseColor("#006699"))
            return convertView
        }
        //highlighted = highlighter.highlight("c++", mContent.get(position));

        //progarmLine = StringEscapeUtils.unescapeHtml4(highlighted);
        if (Build.VERSION.SDK_INT >= 24) {
            holder.text!!.text = Html.fromHtml(highlighter.highlight("c++", programLine), Html.FROM_HTML_MODE_LEGACY)
        } else {
            holder.text!!.text = Html.fromHtml(highlighter.highlight("c++", programLine))
        }

        //holder.text.setText(programLine);

        return convertView
    }

    internal class ViewHolder {
        var text: TextView? = null
    }

    override fun onRemove(which: Int) {
        if (which < 0 || which > mContent!!.size) return
        mContent!!.removeAt(which)
    }

    override fun onDrop(from: Int, to: Int) {
        val temp = mContent!![from]
        mContent!!.removeAt(from)
        mContent!!.add(to, temp)
    }
}
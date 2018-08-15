package com.sortedqueue.programmercreek.v2.ui.contentshare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.BaseFragment
import com.sortedqueue.programmercreek.v2.base.hide
import com.sortedqueue.programmercreek.v2.base.show
import com.sortedqueue.programmercreek.v2.data.helper.Content
import com.sortedqueue.programmercreek.v2.data.helper.ContentType
import kotlinx.android.synthetic.main.fragment_content.*

class ContentFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_content, container, false)

    private lateinit var content: Content

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        content = arguments!!.getParcelable( Content::class.java.simpleName )

        tvContent.text = content.contentString
        tvHeader.text = content.contentString
        tvBullets.text = content.contentString
        editor.setText( content.contentString )
        setContentType( content.contentType )

    }

    fun setContentType( contentType: ContentType ) : Content {

        tvContent.hide()
        tvHeader.hide()
        tvBullets.hide()
        scroll_view.hide()
        tvContent.hide()

        when( contentType.contentType ) {
            0 -> tvHeader.show()
            1 -> tvBullets.show()
            2 -> tvContent.show()
            3 -> scroll_view.show()
        }

        content.contentType = contentType
        tvType.text = contentType.contentTag
        return content

    }

    companion object {
        fun newInstance( content : Content ) : ContentFragment {
            val contentFragment = ContentFragment()
            val bundle = Bundle()
            bundle.putParcelable( Content::class.java.simpleName, content )
            contentFragment.arguments = bundle
            return contentFragment
        }
    }
}
package com.sortedqueue.programmercreek.v2.ui.codelanguage

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.v2.base.BaseAdapterClickListener
import com.sortedqueue.programmercreek.v2.base.BaseFragment
import com.sortedqueue.programmercreek.v2.base.BasePreferencesAPI
import com.sortedqueue.programmercreek.v2.base.PCPreferences
import com.sortedqueue.programmercreek.v2.data.model.CodeLanguage
import com.sortedqueue.programmercreek.v2.ui.mastercontent.MasterContentViewModel
import kotlinx.android.synthetic.main.fragment_code_language.*

class CodeLanguageFragment : BaseFragment(),
        BaseAdapterClickListener<CodeLanguage>,
        Observer<List<CodeLanguage>> {

    private lateinit var mCodeLanguageViewModel: CodeLanguageViewModel
    private lateinit var mCodeLanguageAdapter : CodeLanguageAdapter
    private var mCodeLanguages : ArrayList<CodeLanguage> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle? ): View?
            = inflater.inflate(R.layout.fragment_code_language, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mCodeLanguageViewModel = ViewModelProviders.of(this).get(CodeLanguageViewModel::class.java)
        ViewModelProviders.of(this).get(MasterContentViewModel::class.java)
        rvCodeLanguage.layoutManager = LinearLayoutManager( context )
        mCodeLanguageAdapter = CodeLanguageAdapter(mCodeLanguages, this)
        rvCodeLanguage.adapter = mCodeLanguageAdapter
        mCodeLanguageViewModel.fetchLiveCodeLanguages().observe( this, this )
    }

    override fun onChanged(t: List<CodeLanguage>?) {
        if( t != null ) {
            mCodeLanguages.clear()
            mCodeLanguages.addAll( t )
            mCodeLanguageAdapter.notifyDataSetChanged()
        }
    }

    override fun onItemClick(position: Int, item: CodeLanguage) {
        if( activity != null ) {
            mPreferencesAPI.setLanguage( item )
            activity?.onBackPressed()
        }
    }
}
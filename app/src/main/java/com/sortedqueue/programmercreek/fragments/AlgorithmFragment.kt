package com.sortedqueue.programmercreek.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.AlgorithmPagerAdapter
import com.sortedqueue.programmercreek.database.Algorithm
import com.sortedqueue.programmercreek.database.AlgorithmsIndex
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.interfaces.AlgorithmNavigationListener
import com.sortedqueue.programmercreek.util.CommonUtils
import kotlinx.android.synthetic.main.fragment_algorithm.*


/**
 * Created by Alok Omkar on 2017-03-18.
 */

class AlgorithmFragment : Fragment(), FirebaseDatabaseHandler.GetAlgorithmListener {


    private var algorithmNavigationListener: AlgorithmNavigationListener? = null
    private var algorithm: Algorithm? = null
    private val TAG = AlgorithmFragment::class.java.simpleName

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is AlgorithmNavigationListener) {
            algorithmNavigationListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        algorithmNavigationListener = null
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_algorithm, container, false)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchAlgorithmsIndex()
    }

    private fun fetchAlgorithmsIndex() {
        FirebaseDatabaseHandler(context).getAlgorithmForIndex(mAlgorithmsIndex!!.programIndex, this)
    }

    override fun onSuccess(algorithm: Algorithm) {
        this.algorithm = algorithm
        algorithmViewPager!!.adapter = AlgorithmPagerAdapter(childFragmentManager, algorithm)
        algorithmTabLayout!!.setupWithViewPager(algorithmViewPager)
    }

    override fun onError(databaseError: DatabaseError?) {
        CommonUtils.displaySnackBar(activity, R.string.unable_to_fetch_data, R.string.retry, View.OnClickListener { fetchAlgorithmsIndex() })
    }

    fun shareAlgorithm() {
        val algorithmString = algorithm!!.toAlgorithmString()
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, algorithmString)
        startActivity(Intent.createChooser(shareIntent, "Share Algorithm"))
    }

    companion object {
        private var mAlgorithmsIndex: AlgorithmsIndex? = null

        fun newInstance(algorithm: AlgorithmsIndex): AlgorithmFragment {
            mAlgorithmsIndex = algorithm
            return AlgorithmFragment()
        }
    }
}

package com.sortedqueue.programmercreek.fragments

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CodeEditorRecyclerAdapter
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.adapter.LanguageRecyclerAdapter
import com.sortedqueue.programmercreek.constants.LanguageConstants
import com.sortedqueue.programmercreek.database.firebase.Code
import com.sortedqueue.programmercreek.database.firebase.CodeOutputResponse
import com.sortedqueue.programmercreek.database.firebase.IdResponse
import com.sortedqueue.programmercreek.interfaces.retrofit.SubmitCodeService
import com.sortedqueue.programmercreek.network.RetrofitCreator
import com.sortedqueue.programmercreek.util.AnimationUtils
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.FileUtils
import com.sortedqueue.programmercreek.util.PermissionUtils

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.util.ArrayList
import java.util.HashMap

import butterknife.BindView
import butterknife.ButterKnife
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Alok on 04/04/17.
 */

class CompileCodeFragment : Fragment(), View.OnClickListener, CustomProgramRecyclerViewAdapter.AdapterClickListner {

    @BindView(R.id.inputEditText)
    internal var inputEditText: EditText? = null
    @BindView(R.id.progressImageView)
    internal var progressImageView: ImageView? = null
    @BindView(R.id.progressTextView)
    internal var progressTextView: TextView? = null
    @BindView(R.id.compilerProgressLayout)
    internal var compilerProgressLayout: RelativeLayout? = null
    @BindView(R.id.codeEditRecyclerView)
    internal var codeEditRecyclerView: RecyclerView? = null
    @BindView(R.id.languageTextView)
    internal var languageTextView: TextView? = null
    @BindView(R.id.importFromFileTextView)
    internal var importFromFileTextView: TextView? = null
    @BindView(R.id.languageRecyclerView)
    internal var languageRecyclerView: RecyclerView? = null
    @BindView(R.id.importLayout)
    internal var importLayout: LinearLayout? = null
    @BindView(R.id.outputLayout)
    internal var outputLayout: FrameLayout? = null
    @BindView(R.id.outputTextView)
    internal var outputTextView: TextView? = null
    @BindView(R.id.dividerView)
    internal var dividerView: View? = null

    private var submitCodeService: SubmitCodeService? = null
    private val TAG = CompileCodeFragment::class.java.simpleName
    private var code: Code? = null
    private var codeEditorRecyclerAdapter: CodeEditorRecyclerAdapter? = null
    private var languages: ArrayList<String>? = null
    private var inputList: ArrayList<String>? = null
    private var languageRecyclerAdapter: LanguageRecyclerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_compile_code, container, false)
        ButterKnife.bind(this, view)
        inputList = ArrayList<String>()
        compilerProgressLayout!!.visibility = View.GONE
        languageTextView!!.setOnClickListener(this)
        importFromFileTextView!!.setOnClickListener(this)
        setupRecyclerView()
        setupLanguageRecyclerView()
        submitCodeService = RetrofitCreator.createService(SubmitCodeService::class.java)
        inputEditText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    inputList!!.add(s.toString())
                }
            }
        })
        importLayout!!.visibility = if (fromWiki) View.GONE else View.VISIBLE
        if (fromWiki) {
            setupRecyclerView()
        }
        return view
    }

    private fun setupLanguageRecyclerView() {
        languageRecyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        languages = ArrayList<String>()
        languages!!.add("C")
        languages!!.add("C++")
        languages!!.add("Java")
        languageRecyclerAdapter = LanguageRecyclerAdapter(languages!!, this)
        languageRecyclerView!!.adapter = languageRecyclerAdapter
        if (fromWiki) {
            return
        }
        languageRecyclerAdapter!!.selectedLanguage = languageTextView!!.text.toString().trim { it <= ' ' }
        getCodeTemplate(languageRecyclerAdapter!!.selectedLanguage)
    }

    private fun setupRecyclerView() {
        if (code != null) {
            val programLines = AuxilaryUtils.splitProgramIntolines(code!!.sourceCode)
            codeEditRecyclerView!!.layoutManager = LinearLayoutManager(context)
            codeEditorRecyclerAdapter = CodeEditorRecyclerAdapter(context, programLines, selectedLanguage!!)
            codeEditRecyclerView!!.adapter = codeEditorRecyclerAdapter
            codeEditRecyclerView!!.itemAnimator.changeDuration = 0
        }
    }

    private fun getOutputResponse(submissionId: Int) {
        //TODO : Execute this after a delay
        val codeOutputResponseCall = submitCodeService!!.getOutput(
                submissionId,
                RetrofitCreator.getTokenCompilerApi(),
                true,
                true,
                true,
                true,
                true)
        codeOutputResponseCall.enqueue(object : Callback<CodeOutputResponse> {
            override fun onResponse(call: Call<CodeOutputResponse>, response: Response<CodeOutputResponse>) {
                Log.d(TAG, "Output Response : " + response.body().toString())
                outputTextView!!.text = response.body().output
                stopAnimation()
            }

            override fun onFailure(call: Call<CodeOutputResponse>, t: Throwable) {
                Log.e(TAG, "Output Error : " + t.message)
                t.printStackTrace()
                outputTextView!!.text = "Output Error : " + t.message
                stopAnimation()
            }
        })
    }

    fun executeProgram() {

        val input = inputEditText!!.text.toString()
        code!!.input = input

        val codeMap = HashMap<String, String>()
        codeMap.put("language", selectedLanguageIndex!!)
        val sourceCode = codeEditorRecyclerAdapter!!.code
        codeMap.put("sourceCode", sourceCode)
        if (code!!.input != null && input.trim { it <= ' ' }.length > 0) {
            codeMap.put("input", code!!.input)
        }
        inputEditText!!.setText("")
        startAnimation()
        val idResponseCall = submitCodeService!!.postCode(codeMap, RetrofitCreator.getTokenCompilerApi())
        idResponseCall.enqueue(object : Callback<IdResponse> {
            override fun onResponse(call: Call<IdResponse>, response: Response<IdResponse>) {
                Log.d(TAG, "Execute Response : " + response.body().toString())
                Handler().postDelayed({ getProgramOutput(response.body()) }, 4000)

            }

            private fun getProgramOutput(body: IdResponse) {
                getOutputResponse(body.id!!)
            }

            override fun onFailure(call: Call<IdResponse>, t: Throwable) {
                Log.e(TAG, "Execute Error : " + t.message)
                t.printStackTrace()
                outputTextView!!.text = "Execute Error : " + t.message
                stopAnimation()
            }
        })
    }

    private fun startAnimation() {
        compilerProgressLayout!!.visibility = View.VISIBLE
        val animation = AlphaAnimation(1f, 0f)
        animation.duration = 800
        animation.interpolator = LinearInterpolator()
        animation.repeatCount = Animation.INFINITE
        animation.repeatMode = Animation.REVERSE
        progressImageView!!.startAnimation(animation)
    }

    private fun stopAnimation() {
        progressImageView!!.clearAnimation()
        compilerProgressLayout!!.visibility = View.GONE
    }

    private var fromWiki = false

    fun setParameter(code: Code?) {
        this.code = code
        if (code != null) {
            this.selectedLanguageIndex = code.language
            fromWiki = true
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.languageTextView -> if (languageRecyclerView!!.visibility == View.GONE) {
                AnimationUtils.enterReveal(languageRecyclerView)
            } else {
                AnimationUtils.exitRevealGone(languageRecyclerView)
            }
            R.id.importFromFileTextView -> importFromFile()
        }
    }


    private val REQUEST_CODE_SEARCH = 1000

    private fun importFromFile() {
        if (PermissionUtils.checkSelfPermission(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE))) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "text/plain"
            startActivityForResult(Intent.createChooser(intent,
                    "Load file from directory"), REQUEST_CODE_SEARCH)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionUtils.PERMISSION_REQUEST) {
            if (PermissionUtils.checkDeniedPermissions(activity as AppCompatActivity, permissions).size == 0) {
                importFromFile()
            } else {
                if (permissions.size == 3) {
                    Toast.makeText(context, "Some permissions were denied", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_SEARCH && resultCode == AppCompatActivity.RESULT_OK) {
            try {
                val uri = data!!.data
                if (uri != null) {

                    Log.d(TAG, "File Uri : " + uri.encodedPath + " Path " + uri.path)
                    val filepath = FileUtils.getPath(context, uri)
                    Log.d(TAG, "File path : " + filepath!!)
                    val fis = FileInputStream(filepath)
                    val isr = InputStreamReader(fis, Charset.forName("UTF-8"))
                    val br = BufferedReader(isr)
                    var line: String
                    var completeLine = ""
                    line = br.readLine()
                    while (line != null) {
                        completeLine += line.trim { it <= ' ' } + " "
                        line = br.readLine()
                    }
                } else {
                }
                // Rest of code that converts txt file's content into arraylist
            } catch (e: IOException) {
                e.printStackTrace()
                // Codes that handles IOException
            }

        } else
            super.onActivityResult(requestCode, resultCode, data)
    }


    private var selectedLanguageIndex: String? = null
    private var codeTemplate: String? = null
    private var selectedLanguage: String? = null

    override fun onItemClick(position: Int) {
        val selectedLanguage = languages!![position]
        languageRecyclerAdapter!!.selectedLanguage = selectedLanguage
        languageTextView!!.text = selectedLanguage
        getCodeTemplate(selectedLanguage)

        AnimationUtils.exitRevealGone(languageRecyclerView)
    }

    private fun getCodeTemplate(selectedLanguage: String) {
        this.selectedLanguage = selectedLanguage
        when (selectedLanguage) {
            "C" -> {
                selectedLanguageIndex = LanguageConstants.C_INDEX
                codeTemplate = LanguageConstants.C_TEMPLATE
                inputList!!.clear()
            }
            "C++" -> {
                selectedLanguageIndex = LanguageConstants.CPP_INDEX
                codeTemplate = LanguageConstants.CPP_TEMPLATE
                inputList!!.clear()
            }
            "Java" -> {
                selectedLanguageIndex = LanguageConstants.JAVA_INDEX
                codeTemplate = LanguageConstants.JAVA_TEMPLATE
                inputList!!.clear()
            }
        }

        code = Code()
        code!!.language = selectedLanguageIndex
        code!!.sourceCode = codeTemplate
        setupRecyclerView()
    }
}

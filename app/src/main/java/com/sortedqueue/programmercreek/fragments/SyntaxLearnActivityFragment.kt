package com.sortedqueue.programmercreek.fragments

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter
import com.sortedqueue.programmercreek.adapter.OptionsRecyclerViewAdapter
import com.sortedqueue.programmercreek.database.Chapter
import com.sortedqueue.programmercreek.database.ChapterDetails
import com.sortedqueue.programmercreek.database.CreekUserStats
import com.sortedqueue.programmercreek.database.LanguageModule
import com.sortedqueue.programmercreek.database.ModuleOption
import com.sortedqueue.programmercreek.database.SyntaxModule
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.interfaces.ModuleDetailsScrollPageListener
import com.sortedqueue.programmercreek.interfaces.TestCompletionListener
import com.sortedqueue.programmercreek.util.AnimationUtils
import com.sortedqueue.programmercreek.util.AuxilaryUtils
import com.sortedqueue.programmercreek.util.CommonUtils

import java.util.ArrayList
import java.util.Locale

import butterknife.BindView
import butterknife.ButterKnife
import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme

/**
 * A placeholder fragment containing a simple view.
 */
class SyntaxLearnActivityFragment : Fragment(), View.OnClickListener, TestCompletionListener, RewardedVideoAdListener {

    @BindView(R.id.syntaxExplanationCardView)
    internal var syntaxExplanationCardView: CardView? = null
    @BindView(R.id.syntaxQuestionCardView)
    internal var syntaxQuestionCardView: CardView? = null
    @BindView(R.id.optionsCardView)
    internal var optionsCardView: CardView? = null
    @BindView(R.id.checkSyntaxImageView)
    internal var checkSyntaxImageView: ImageView? = null
    @BindView(R.id.clearSyntaxImageView)
    internal var clearSyntaxImageView: ImageView? = null
    @BindView(R.id.hintSyntaxImageView)
    internal var hintSyntaxImageView: ImageView? = null
    @BindView(R.id.voiceTypeImageView)
    internal var voiceTypeImageView: ImageView? = null
    @BindView(R.id.content_syntax_learn)
    internal var contentSyntaxLearn: RelativeLayout? = null
    @BindView(R.id.syntaxNameTextView)
    internal var syntaxNameTextView: TextView? = null
    @BindView(R.id.syntaxDescriptionTextView)
    internal var syntaxDescriptionTextView: TextView? = null
    @BindView(R.id.syntaxCommandTextView)
    internal var syntaxCommandTextView: TextView? = null
    @BindView(R.id.syntaxCommandOutputTextView)
    internal var syntaxCommandOutputTextView: TextView? = null
    @BindView(R.id.syntaxQuestionTextView)
    internal var syntaxQuestionTextView: TextView? = null
    @BindView(R.id.syntaxSolutionTextView)
    internal var syntaxSolutionTextView: TextView? = null
    @BindView(R.id.syntaxQuestionOutputTextView)
    internal var syntaxQuestionOutputTextView: TextView? = null
    @BindView(R.id.optionsRecyclerView)
    internal var optionsRecyclerView: RecyclerView? = null
    @BindView(R.id.checkButtonLayout)
    internal var checkButtonLayout: LinearLayout? = null
    @BindView(R.id.scrollView)
    internal var scrollView: ScrollView? = null
    @BindView(R.id.progressBar)
    internal var progressBar: ContentLoadingProgressBar? = null
    @BindView(R.id.programCodeView)
    internal var programCodeView: CodeView? = null
    @BindView(R.id.resultTextView)
    internal var resultTextView: TextView? = null
    @BindView(R.id.proceedTextView)
    internal var proceedTextView: TextView? = null
    @BindView(R.id.resultLayout)
    internal var resultLayout: RelativeLayout? = null
    private var syntaxModule: SyntaxModule? = null
    private var moduleOptions: MutableList<ModuleOption>? = null
    private val TAG = SyntaxLearnActivityFragment::class.java.simpleName
    private var modulteDetailsScrollPageListener: ModuleDetailsScrollPageListener? = null
    private var isLastFragment: Boolean = false
    private var nextModule: LanguageModule? = null
    private var wizardUrl: String? = null
    private var syntaxId: String? = null
    private var isAnswered = false
    private var nextChapter: Chapter? = null
    private var programLanguage: String? = null

    private var rewardedVideoAd: RewardedVideoAd? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_syntax_learn, container, false)
        ButterKnife.bind(this, view)
        programLanguage = CreekApplication.creekPreferences!!.programLanguage
        //initializeRewardedVideoAd();
        if (programLanguage == "c++") {
            programLanguage = "cpp"
        }
        if (wizardUrl == null) {
            bindData(syntaxModule!!)
        } else {
            progressBar!!.visibility = View.VISIBLE
            FirebaseDatabaseHandler(context).getSyntaxModule(syntaxId!!, wizardUrl!!,
                    object : FirebaseDatabaseHandler.SyntaxModuleInterface {
                        override fun onSuccess(syntaxModule: SyntaxModule) {
                            this@SyntaxLearnActivityFragment.syntaxModule = syntaxModule
                            bindData(syntaxModule)
                            progressBar!!.visibility = View.GONE
                        }

                        override fun onError(error: DatabaseError?) {
                            progressBar!!.visibility = View.GONE
                            CommonUtils.displayToast(context, R.string.unable_to_fetch_data)
                        }
                    })
        }
        proceedTextView!!.setOnClickListener(this)
        return view
    }

    private fun initializeRewardedVideoAd() {
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(activity)
        rewardedVideoAd!!.rewardedVideoAdListener = this
        if (!rewardedVideoAd!!.isLoaded) {
            rewardedVideoAd!!.loadAd(getString(R.string.hint_rewarded_interstital_ad), AdRequest.Builder().build())
        }
    }

    override fun onResume() {
        //rewardedVideoAd.resume(getContext());
        super.onResume()
    }

    override fun onPause() {
        //rewardedVideoAd.pause(getContext());
        super.onPause()
    }

    override fun onDestroy() {
        //rewardedVideoAd.destroy(getContext());
        super.onDestroy()
    }

    private fun bindData(syntaxModule: SyntaxModule) {
        syntaxNameTextView!!.text = syntaxModule.syntaxName
        syntaxDescriptionTextView!!.text = syntaxModule.syntaxDescription
        syntaxCommandTextView!!.text = syntaxModule.syntaxCommand
        syntaxCommandOutputTextView!!.text = syntaxModule.syntaxCommandOutput
        syntaxQuestionTextView!!.text = syntaxModule.syntaxQuestion
        syntaxSolutionTextView!!.text = ""
        setCodeView(syntaxSolutionTextView!!.text.toString())
        syntaxQuestionOutputTextView!!.text = "Expected Output : " + syntaxModule.syntaxQuestionOutput
        if (syntaxModule.syntaxOptions != null) {
            setupRecyclerView(syntaxModule.syntaxOptions)
            Log.d(TAG, "SyntaxModule : " + syntaxModule.toString())
        }
        checkSyntaxImageView!!.setOnClickListener(this)
        clearSyntaxImageView!!.setOnClickListener(this)
        hintSyntaxImageView!!.setOnClickListener(this)
        voiceTypeImageView!!.setOnClickListener(this)
    }

    private fun setCodeView(programLines: String) {
        programCodeView!!
                .setOptions(Options.get(context)
                        .withLanguage(programLanguage!!)
                        .withCode(programLines)
                        .withTheme(ColorTheme.SOLARIZED_LIGHT))
    }

    private val solutionList = ArrayList<String>()

    private fun setupRecyclerView(syntaxOptions: List<ModuleOption>) {
        moduleOptions = ArrayList<ModuleOption>()
        for (moduleOption in syntaxOptions) {
            if (moduleOption.option.trim { it <= ' ' }.length != 0) {
                moduleOptions!!.add(moduleOption)
            }
        }
        Log.d(TAG, "Module Options : " + moduleOptions!!.toString())
        optionsRecyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        optionsRecyclerView!!.adapter = OptionsRecyclerViewAdapter(context, moduleOptions!!, object : CustomProgramRecyclerViewAdapter.AdapterClickListner {
            override fun onItemClick(position: Int) {
                solutionList.add(moduleOptions!![position].option)
                syntaxSolutionTextView!!.text = getSolution(solutionList)
                setCodeView(syntaxSolutionTextView!!.text.toString())
            }
        })

        // No question in the module : Toggle views
        val visibility = if (syntaxOptions.size > 1) View.VISIBLE else View.GONE
        optionsCardView!!.visibility = visibility
        syntaxQuestionCardView!!.visibility = visibility
        checkSyntaxImageView!!.visibility = visibility
        clearSyntaxImageView!!.visibility = visibility
        hintSyntaxImageView!!.visibility = visibility
        //voiceTypeImageView.setVisibility(visibility);
        isAnswered = syntaxOptions.size == 1
        if (isAnswered)
            updateCreekUserStats()

    }

    private fun getSolution(solutionList: ArrayList<String>): String {
        var solution = ""
        for (solutionString in solutionList) {
            solution += solutionString
        }
        return solution
    }

    fun setSyntaxModule(syntaxModule: SyntaxModule) {
        this.syntaxModule = syntaxModule
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.clearSyntaxImageView -> if (solutionList.size == 0) {
                syntaxSolutionTextView!!.text = ""
                setCodeView("")
            } else {
                solutionList.removeAt(solutionList.size - 1)
                syntaxSolutionTextView!!.text = getSolution(solutionList)
                setCodeView(syntaxSolutionTextView!!.text.toString())
            }
            R.id.checkSyntaxImageView -> checkSolution()
            R.id.hintSyntaxImageView ->
                /*if (rewardedVideoAd.isLoaded()) {
                    rewardedVideoAd.show();
                }*/
                showHint()
            R.id.voiceTypeImageView -> openVoiceIntent()
            R.id.proceedTextView -> {
                AnimationUtils.slideOutToLeft(resultLayout)
                if (modulteDetailsScrollPageListener != null) {
                    modulteDetailsScrollPageListener!!.onScrollForward()
                }
            }
        }
    }

    private fun openVoiceIntent() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        try {
            startActivityForResult(intent, SPEECH_REQUEST)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            AuxilaryUtils.displayAlert("No Voice App", "Your device doesn't support voice input - no app found", context)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SPEECH_REQUEST && resultCode == AppCompatActivity.RESULT_OK) {
            val results = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = results[0]
            CommonUtils.displayToast(context, "I heard : " + results)
            if (spokenText.equals("print formatted", ignoreCase = true)) {
                solutionList.add("printf(")
                syntaxSolutionTextView!!.text = getSolution(solutionList)
                setCodeView(syntaxSolutionTextView!!.text.toString())
            }
        }
    }

    private fun checkSolution() {
        val solutionText = syntaxSolutionTextView!!.text.toString()
        if (solutionText.trim { it <= ' ' }.replace("\\s+".toRegex(), "") == syntaxModule!!.syntaxSolution.trim { it <= ' ' }.replace("\\s+".toRegex(), "")) {
            AnimationUtils.slideInToLeft(resultLayout)
            /*CommonUtils.displaySnackBar(getActivity(), R.string.congratulations, R.string.proceed, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });*/
            syntaxQuestionOutputTextView!!.text = syntaxModule!!.syntaxQuestionOutput
            syntaxQuestionOutputTextView!!.setTextColor(Color.GREEN)
            isAnswered = true
            updateCreekUserStats()

        } else {
            //Snackbar.make(getActivity().findViewById(android.R.id.content), "Check the syntax again", Snackbar.LENGTH_LONG).show();
            syntaxQuestionOutputTextView!!.text = "Error..!!"
            syntaxQuestionOutputTextView!!.setTextColor(Color.RED)
        }
    }

    private fun updateCreekUserStats() {
        val creekUserStats = CreekApplication.instance.creekUserStats
        when (syntaxModule!!.syntaxLanguage.toLowerCase()) {
            "c" -> {
                creekUserStats!!.addToUnlockedCLanguageModuleIdList(syntaxModule!!.moduleId)
                creekUserStats.addToUnlockedCSyntaxModuleIdList(syntaxModule!!.moduleId + "_" + syntaxModule!!.syntaxModuleId)
                if (isLastFragment) {
                    if (nextModule != null) {
                        if (creekUserStats.addToUnlockedCLanguageModuleIdList(nextModule!!.moduleId)) {
                            AuxilaryUtils.displayAchievementUnlockedDialog(activity, "Congratulations..!!", getString(R.string.new_module_unlocked))
                        }

                        //CommonUtils.displaySnackBar(getActivity(), R.string.new_module_unlocked);
                    } else if (nextChapter != null) {
                        if (creekUserStats.addToUnlockedCLanguageModuleIdList(nextChapter!!.chapterDetailsArrayList[0].syntaxId)) {
                            AuxilaryUtils.displayAchievementUnlockedDialog(activity, "Congratulations..!!", getString(R.string.new_chapter_unlocked))
                        }
                        //CommonUtils.displaySnackBar(getActivity(), R.string.new_chapter_unlocked);
                    }
                }
            }
            "cpp", "c++" -> {
                creekUserStats!!.addToUnlockedCppLanguageModuleIdList(syntaxModule!!.moduleId)
                creekUserStats.addToUnlockedCppSyntaxModuleIdList(syntaxModule!!.moduleId + "_" + syntaxModule!!.syntaxModuleId)
                if (isLastFragment) {
                    if (nextModule != null) {
                        if (creekUserStats.addToUnlockedCppLanguageModuleIdList(nextModule!!.moduleId)) {
                            AuxilaryUtils.displayAchievementUnlockedDialog(activity, "Congratulations..!!", getString(R.string.new_module_unlocked))
                        }
                        //CommonUtils.displaySnackBar(getActivity(), R.string.new_module_unlocked);
                    } else if (nextChapter != null) {
                        if (creekUserStats.addToUnlockedCppLanguageModuleIdList(nextChapter!!.chapterDetailsArrayList[0].syntaxId))
                            AuxilaryUtils.displayAchievementUnlockedDialog(activity, "Congratulations..!!", getString(R.string.new_chapter_unlocked))
                        //CommonUtils.displaySnackBar(getActivity(), R.string.new_chapter_unlocked);
                    }
                }
            }
            "java" -> {
                creekUserStats!!.addToUnlockedJavaLanguageModuleIdList(syntaxModule!!.moduleId)
                creekUserStats.addToUnlockedJavaSyntaxModuleIdList(syntaxModule!!.moduleId + "_" + syntaxModule!!.syntaxModuleId)
                if (isLastFragment) {
                    if (nextModule != null) {
                        if (creekUserStats.addToUnlockedJavaLanguageModuleIdList(nextModule!!.moduleId))
                            AuxilaryUtils.displayAchievementUnlockedDialog(activity, "Congratulations..!!", getString(R.string.new_module_unlocked))
                        //CommonUtils.displaySnackBar(getActivity(), R.string.new_module_unlocked);
                    } else if (nextChapter != null) {
                        if (creekUserStats.addToUnlockedJavaLanguageModuleIdList(nextChapter!!.chapterDetailsArrayList[0].syntaxId))
                            AuxilaryUtils.displayAchievementUnlockedDialog(activity, "Congratulations..!!", getString(R.string.new_chapter_unlocked))
                        //CommonUtils.displaySnackBar(getActivity(), R.string.new_chapter_unlocked);
                    }
                }
            }
            "sql" -> {
                creekUserStats!!.addToUnlockedSqlLanguageModuleIdList(syntaxModule!!.moduleId)
                creekUserStats.addToUnlockedSqlSyntaxModuleIdList(syntaxModule!!.moduleId + "_" + syntaxModule!!.syntaxModuleId)
                if (isLastFragment) {
                    if (nextModule != null) {
                        if (creekUserStats.addToUnlockedSqlLanguageModuleIdList(nextModule!!.moduleId))
                            AuxilaryUtils.displayAchievementUnlockedDialog(activity, "Congratulations..!!", getString(R.string.new_module_unlocked))
                        //CommonUtils.displaySnackBar(getActivity(), R.string.new_module_unlocked);
                    } else if (nextChapter != null) {
                        if (creekUserStats.addToUnlockedSqlLanguageModuleIdList(nextChapter!!.chapterDetailsArrayList[0].syntaxId))
                            AuxilaryUtils.displayAchievementUnlockedDialog(activity, "Congratulations..!!", getString(R.string.new_chapter_unlocked))
                        //CommonUtils.displaySnackBar(getActivity(), R.string.new_chapter_unlocked);
                    }
                }
            }
        }
        FirebaseDatabaseHandler(context).writeCreekUserStats(creekUserStats!!)

    }

    fun setModulteDetailsScrollPageListener(modulteDetailsScrollPageListener: ModuleDetailsScrollPageListener) {
        this.modulteDetailsScrollPageListener = modulteDetailsScrollPageListener
    }

    fun setIsLastFragment(isLastFragment: Boolean, nextModule: LanguageModule) {
        this.isLastFragment = isLastFragment
        this.nextModule = nextModule
    }

    fun setIsLastFragment(isLastFragment: Boolean, nextChapter: Chapter) {
        this.isLastFragment = isLastFragment
        this.nextChapter = nextChapter
    }

    fun setSyntaxModule(syntaxId: String, wizardUrl: String) {
        this.wizardUrl = wizardUrl
        this.syntaxId = syntaxId
    }

    override fun isTestComplete(): Int {

        return if (isAnswered) ChapterDetails.TYPE_SYNTAX_MODULE else -1
    }


    //RewardedVideoAdListener methods :

    override fun onRewardedVideoAdLoaded() {
        Log.d(TAG, "onRewardedVideoAdLoaded")
    }

    override fun onRewardedVideoAdOpened() {
        Log.d(TAG, "onRewardedVideoAdOpened")
    }

    override fun onRewardedVideoStarted() {
        Log.d(TAG, "onRewardedVideoStarted")
    }

    override fun onRewardedVideoAdClosed() {
        Log.d(TAG, "onRewardedVideoAdClosed")
    }

    override fun onRewarded(rewardItem: RewardItem?) {
        if (rewardItem != null) {
            Log.d(TAG, "onRewarded : " + rewardItem.amount + " : " + rewardItem.type)
            showHint()
        }
    }

    private fun showHint() {
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Got it") { dialog, which -> dialog.dismiss() }
        builder.setMessage(syntaxModule!!.syntaxSolution)
        builder.setTitle("Solution :")
        builder.setIcon(R.mipmap.ic_launcher)
        builder.show()
    }

    override fun onRewardedVideoAdLeftApplication() {
        Log.d(TAG, "onRewardedVideoAdLoaded")
    }

    override fun onRewardedVideoAdFailedToLoad(i: Int) {
        Log.d(TAG, "onRewardedVideoAdLoaded")
    }

    companion object {

        private val SPEECH_REQUEST = 9878
    }
}

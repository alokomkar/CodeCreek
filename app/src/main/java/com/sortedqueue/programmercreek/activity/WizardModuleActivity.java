package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.ChapterDetails;
import com.sortedqueue.programmercreek.database.Chapters;
import com.sortedqueue.programmercreek.fragments.WizardModuleFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Alok on 05/01/17.
 */

public class WizardModuleActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.container)
    FrameLayout container;
    private FragmentTransaction mFragmentTransaction;
    private WizardModuleFragment wizardModuleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard_module);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadModulesFragment();
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_syntax_learn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

    private void loadModulesFragment() {

        Chapters chapters = new Chapters();
        chapters.setProgram_Language("c");
        chapters.setChapterId("c_w_1");
        chapters.setChapterName("Simple I/O : Hello world");
        chapters.setChapteBrief("Learn about simple input and output commands, write your very first program");

        ArrayList<ChapterDetails> chapterDetailsArrayList = new ArrayList<>();
        int index = 1;
        ChapterDetails chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setSyntaxId("c_1");
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setSyntaxId("c_1");
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setSyntaxId("c_1");
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setSyntaxId("c_1");
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setSyntaxId("c_1");
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index);
        chapterDetails.setSyntaxId("c_1");
        chapterDetails.setChapterType(ChapterDetails.TYPE_SYNTAX_MODULE);
        chapterDetails.setChapterReferenceId("s_" + index++);

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_WIKI);
        chapterDetails.setChapterReferenceId("c1");

        chapterDetailsArrayList.add(chapterDetails);

        chapterDetails = new ChapterDetails();
        chapterDetails.setChapterModuleIndex(index++);
        chapterDetails.setChapterType(ChapterDetails.TYPE_PROGRAM_INDEX);
        chapterDetails.setChapterReferenceId("1");
        chapterDetails.setChapterTestType(ProgrammingBuddyConstants.KEY_TEST);

        chapterDetailsArrayList.add(chapterDetails);

        chapters.setWizardModules(chapterDetailsArrayList);

        getSupportActionBar().setTitle(chapters.getChapterName());

        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        wizardModuleFragment = (WizardModuleFragment) getSupportFragmentManager().findFragmentByTag(WizardModuleFragment.class.getSimpleName());
        if( wizardModuleFragment == null ) {
            wizardModuleFragment = new WizardModuleFragment();
        }
        wizardModuleFragment.setChapters(chapters);
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, wizardModuleFragment, WizardModuleFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

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
import com.sortedqueue.programmercreek.database.WizardDetails;
import com.sortedqueue.programmercreek.database.WizardModule;
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

        WizardModule wizardModule = new WizardModule();
        wizardModule.setProgram_Language("c");
        wizardModule.setWizardModuleId("c_w_1");
        wizardModule.setWizardName("Simple I/O : Hello world");

        ArrayList<WizardDetails> wizardDetailsArrayList = new ArrayList<>();
        int index = 1;
        WizardDetails wizardDetails = new WizardDetails();
        wizardDetails.setWizardIndex(index);
        wizardDetails.setSyntaxId("c_1");
        wizardDetails.setWizardType(WizardDetails.TYPE_SYNTAX_MODULE);
        wizardDetails.setWizardUrl("s_" + index++);

        wizardDetailsArrayList.add(wizardDetails);

        wizardDetails = new WizardDetails();
        wizardDetails.setWizardIndex(index);
        wizardDetails.setWizardType(WizardDetails.TYPE_SYNTAX_MODULE);
        wizardDetails.setSyntaxId("c_1");
        wizardDetails.setWizardUrl("s_" + index++);

        wizardDetailsArrayList.add(wizardDetails);

        wizardDetails = new WizardDetails();
        wizardDetails.setWizardIndex(index);
        wizardDetails.setSyntaxId("c_1");
        wizardDetails.setWizardType(WizardDetails.TYPE_SYNTAX_MODULE);
        wizardDetails.setWizardUrl("s_" + index++);

        wizardDetailsArrayList.add(wizardDetails);

        wizardDetails = new WizardDetails();
        wizardDetails.setWizardIndex(index);
        wizardDetails.setSyntaxId("c_1");
        wizardDetails.setWizardType(WizardDetails.TYPE_SYNTAX_MODULE);
        wizardDetails.setWizardUrl("s_" + index++);

        wizardDetailsArrayList.add(wizardDetails);

        wizardDetails = new WizardDetails();
        wizardDetails.setWizardIndex(index);
        wizardDetails.setSyntaxId("c_1");
        wizardDetails.setWizardType(WizardDetails.TYPE_SYNTAX_MODULE);
        wizardDetails.setWizardUrl("s_" + index++);

        wizardDetailsArrayList.add(wizardDetails);

        wizardDetails = new WizardDetails();
        wizardDetails.setWizardIndex(index);
        wizardDetails.setSyntaxId("c_1");
        wizardDetails.setWizardType(WizardDetails.TYPE_SYNTAX_MODULE);
        wizardDetails.setWizardUrl("s_" + index++);

        wizardDetailsArrayList.add(wizardDetails);

        wizardDetails = new WizardDetails();
        wizardDetails.setWizardIndex(index++);
        wizardDetails.setWizardType(WizardDetails.TYPE_WIKI);
        wizardDetails.setWizardUrl("c1");

        wizardDetailsArrayList.add(wizardDetails);

        wizardDetails = new WizardDetails();
        wizardDetails.setWizardIndex(index++);
        wizardDetails.setWizardType(WizardDetails.TYPE_PROGRAM_INDEX);
        wizardDetails.setWizardUrl("1");
        wizardDetails.setProgramTestType(ProgrammingBuddyConstants.KEY_TEST);

        wizardDetailsArrayList.add(wizardDetails);

        wizardModule.setWizardModules(wizardDetailsArrayList);

        getSupportActionBar().setTitle(wizardModule.getWizardName());

        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        wizardModuleFragment = (WizardModuleFragment) getSupportFragmentManager().findFragmentByTag(WizardModuleFragment.class.getSimpleName());
        if( wizardModuleFragment == null ) {
            wizardModuleFragment = new WizardModuleFragment();
        }
        wizardModuleFragment.setWizardModule( wizardModule );
        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, wizardModuleFragment, WizardModuleFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

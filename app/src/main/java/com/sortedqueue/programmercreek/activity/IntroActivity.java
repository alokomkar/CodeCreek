package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.IntroChapter;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class IntroActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.progressBar)
    ContentLoadingProgressBar progressBar;
    @Bind(R.id.chapterHeaderTextView)
    TextView chapterHeaderTextView;
    @Bind(R.id.chapterIntroTextView)
    TextView chapterIntroTextView;
    @Bind(R.id.chapterNoteTextView)
    TextView chapterNoteTextView;
    @Bind(R.id.chapterProgramCodeView)
    CodeView chapterProgramCodeView;
    @Bind(R.id.chapterProgramOutputTextView)
    TextView chapterProgramOutputTextView;
    @Bind(R.id.chapterProgramDescriptionTextView)
    TextView chapterProgramDescriptionTextView;
    @Bind(R.id.syntaxExplanationCardView)
    CardView syntaxExplanationCardView;
    @Bind(R.id.content_syntax_learn)
    RelativeLayout contentSyntaxLearn;
    @Bind(R.id.content_intro)
    RelativeLayout contentIntro;
    private Toolbar toolbar;
    private ImageView drawerImageView;

    private TextView drawerNameTextView;
    private TextView drawerEmailTextView;

    private ArrayList<IntroChapter> introChapters = new ArrayList<>();
    private String programLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initAndSetUserValues(navigationView.getHeaderView(0));
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    private void initAndSetUserValues(View view) {
        CreekPreferences creekPreferences = new CreekPreferences(IntroActivity.this);
        drawerEmailTextView = (TextView) view.findViewById(R.id.drawerEmailTextView);
        drawerNameTextView = (TextView) view.findViewById(R.id.drawerNameTextView);
        drawerImageView = (ImageView) view.findViewById(R.id.drawerImageView);
        /*Glide.with(IntroActivity.this)
                .load(creekPreferences.getAccountPhoto())
                .fitCenter()
                .into(drawerImageView);*/
        drawerNameTextView.setText(creekPreferences.getAccountName());
        loadChapters();
    }

    private void loadChapters() {
        introChapters = new ArrayList<>();

        programLanguage = new CreekPreferences(IntroActivity.this).getProgramLanguage();
        IntroChapter introChapter = new IntroChapter();
        introChapter.setChapterHeader("Introduction");
        introChapter.setChapterId("c_1");
        introChapter.setChapterIndex(1);
        introChapter.setChapterIntro("The C programming language is a computer programming language that " +
                "was developed to do system programming for the operating system UNIX and is " +
                "an imperative programming language. C was developed in the early 1970 " +
                "by Ken Thompson and Dennis Ritchie. It is a procedural language, " +
                "which means that people can write their programs as a series of step-by-step instructions. C is a compiled language.");
        introChapter.setChapterNote("The very first thing you need to do, before starting out in C, " +
                "is to make sure that you have a compiler. What is a compiler, you ask? " +
                "A compiler turns the program that you write into an executable that your computer can actually understand and run. " +
                "If you're taking a course, you probably have one provided through your school.");
        introChapter.setChapterLanguage(programLanguage);

        introChapters.add(introChapter);

        initChapter( introChapter );

    }

    private void initChapter(IntroChapter introChapter) {

        chapterNoteTextView.setVisibility(View.GONE);
        chapterProgramCodeView.setVisibility(View.GONE);
        chapterProgramDescriptionTextView.setVisibility(View.GONE);
        chapterProgramOutputTextView.setVisibility(View.GONE);

        chapterHeaderTextView.setText(introChapter.getChapterHeader());
        chapterIntroTextView.setText(introChapter.getChapterIntro());

        if( introChapter.getChapterNote() != null ) {
            chapterNoteTextView.setVisibility(View.VISIBLE);
            chapterNoteTextView.setText(introChapter.getChapterNote());
        }

        if( introChapter.getChapterProgram() != null ) {
            chapterProgramCodeView.setVisibility(View.VISIBLE);
            chapterProgramCodeView.setOptions(Options.Default.get(IntroActivity.this)
                    .withLanguage(programLanguage)
                    .withCode(introChapter.getChapterProgram())
                    .withTheme(ColorTheme.MONOKAI));
        }

        if( introChapter.getChapterProgramDescription() != null ) {
            chapterProgramDescriptionTextView.setVisibility(View.VISIBLE);
            chapterProgramDescriptionTextView.setText(introChapter.getChapterProgramDescription());
        }

        if( introChapter.getChapterProgramOutput() != null ) {
            chapterProgramOutputTextView.setVisibility(View.VISIBLE);
            chapterProgramOutputTextView.setText(introChapter.getChapterProgramOutput());
        }



    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.intro_item) {
            toolbar.setTitle(item.getTitle());
        } else if (id == R.id.basics_item) {
            toolbar.setTitle(item.getTitle());
        } else if (id == R.id.comments_item) {
            toolbar.setTitle(item.getTitle());
        } else if (id == R.id.installation_item) {
            toolbar.setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }
}

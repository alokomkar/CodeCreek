package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
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

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.IntroChapter;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.util.CommonUtils;
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
    @Bind(R.id.doneFAB)
    FloatingActionButton doneFAB;

    private TextView drawerNameTextView;
    private TextView drawerEmailTextView;

    private ArrayList<IntroChapter> introChapters = new ArrayList<>();
    private String programLanguage;

    private int currentIndex = 0;

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

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initAndSetUserValues(navigationView.getHeaderView(0));
        doneFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex++;
                if( currentIndex <= 3 ) {
                    int drawable = currentIndex == 3 ? R.drawable.ic_done_all : android.R.drawable.ic_media_play;
                    doneFAB.setImageDrawable(ContextCompat.getDrawable(IntroActivity.this, drawable));
                    initChapter(introChapters.get(currentIndex));
                }
                else {
                    finish();
                    //navigateToChapters();
                }
            }
        });
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    private void initAndSetUserValues(View view) {
        CreekPreferences creekPreferences = new CreekPreferences(IntroActivity.this);
        drawerEmailTextView = (TextView) view.findViewById(R.id.drawerEmailTextView);
        drawerNameTextView = (TextView) view.findViewById(R.id.drawerNameTextView);
        drawerImageView = (ImageView) view.findViewById(R.id.drawerImageView);
        drawerNameTextView.setText(creekPreferences.getAccountName());
        loadChapters();
    }

    private void loadChapters() {
        introChapters = new ArrayList<>();
        programLanguage = new CreekPreferences(IntroActivity.this).getProgramLanguage();
        /*IntroChapter introChapter = new IntroChapter();
        introChapter.setChapterHeader("Introduction");
        introChapter.setChapterId("usp_intro_1");
        introChapter.setChapterIndex(1);
        introChapter.setChapterIntro("UNIX is a computer operating system originally developed in 1969 by a group of AT&T employees at Bell Labs, including Ken Thompson, Dennis Ritchie, Douglas McElroy and Joe Ossanna. Today UNIX systems are split into various branches, developed over time by AT&T as well as various commercial vendors and non-profit organizations." +
                "");
        introChapter.setChapterNote("The ANSI C Standard\n" +
                "In 1989, American National Standard Institute (ANSI) proposed C programming language standard X3.159-1989 to standardise the language constructs and libraries. This is termed as ANSI C standard. This attempt to unify the implementation of the C language supported on all computer system. The major differences between ANSI C and K&R C [Kernighan and Ritchie] are as follows:\n" +
                "\uF0B7 Function prototyping\n" +
                "\uF0B7 Support of the const and volatile data type qualifiers.\n" +
                "\uF0B7 Support wide characters and internationalization.\n" +
                "\uF0B7 Permit function pointers to be used without dereferencing.");
        introChapter.setChapterLanguage(programLanguage);
        introChapters.add(introChapter);

        introChapter = new IntroChapter();
        introChapter.setChapterHeader("Basics with your first program");
        introChapter.setChapterId("usp_intro_2");
        introChapter.setChapterIndex(2);
        introChapter.setChapterIntro("The POSIX Environment\n" +
                "Although POSIX was developed on UNIX, a POSIX complaint system is not necessarily a UNIX system. A few UNIX conventions have different meanings according to the POSIX standards. Most C and C++ header files are stored under the /usr/include directory in any UNIX system and each of them is referenced by #include<header-file-name> This method is adopted in POSIX. There need not be a physical file of that name existing on a POSIX conforming system.\n" +
                "The POSIX Feature Test Macros\n" +
                "POSIX.1 defines a set of feature test macro’s which if defined on a system, means that the system has implemented the corresponding features.");
        introChapter.setChapterNote(
                "_POSIX_JOB_CONTROL : The system supports the BSD style job control.\n" +
                "_POSIX_SAVED_IDS :" +
                "Each process running on the system keeps the saved set UID and the set-GID, so that they can change its effective user-ID and group-ID to those values via seteuid and setegid API's. _POSIX_CHOWN_RESTRICTED If the defined value is -1, users may change ownership of files owned by them, otherwise only users with special privilege may change ownership of any file on the system.\n" +
                "_POSIX_NO_TRUNC :" +
                "If the defined value is -1, any long pathname passed to an API is silently truncated to NAME_MAX bytes, otherwise error is generated.\n " +
                        "_POSIX_VDISABLE : If defined value is -1, there is no disabling character for special characters for all terminal device files. Otherwise the value is the disabling character value.");
        introChapter.setChapterProgram(
                "The following test_config.C illustrates the use of sysconf, pathcong and fpathconf:\n " +
                        "#define _POSIX_SOURCE\n" +
                        "#define _POSIX_C_SOURCE 199309L\n " +
                        "#include<stdio.h>\n " +
                        "#include<iostream.h>\n " +
                        "#include<unistd.h>\n " +
                        "int main() {\n " +
                        "int res;\n " +
                        "if((res=sysconf(_SC_OPEN_MAX))==-1)\n " +
                        "perror(“sysconf”);\n " +
                        "else\n" +
                        "cout<<”OPEN_MAX:”<<res<<endl;\n" +
                        "if((res=pathconf(“/”,_PC_PATH_MAX))==-1)\n " +
                        "perror(“pathconf”);\n " +
                        "else cout<<”max path name:”<<(res+1)<<endl;\n " +
                        "if((res=fpathconf(0,_PC_CHOWN_RESTRICTED))==-1)\n " +
                        "perror(“fpathconf”);\n " +
                        "else\n " +
                        "cout<<”chown_restricted for stdin:”<<res<<endl;\n " +
                        "return 0;\n " +
                        "}");

        introChapter.setChapterLanguage(programLanguage);

        introChapters.add(introChapter);

        introChapter = new IntroChapter();
        introChapter.setChapterHeader("Using APIs");
        introChapter.setChapterId("usp_intro_3");
        introChapter.setChapterIndex(3);
        introChapter.setChapterIntro("UNIX AND POSIX APIs\n" +
                "API \uF0E0 A set of application programming interface functions that can be called by user programs to perform system specific functions. Most UNIX systems provide a common set of API’s to perform the following functions.\n" +
                "\uF0A7 Determine the system configuration and user information.\n" +
                "\uF0A7 Files manipulation.\n" +
                "\uF0A7 Processes creation and control.\n" +
                "\uF0A7 Inter-process communication.\n" +
                "\uF0A7 Signals and daemons\n" +
                "\uF0A7 Network communication.\n" +
                "The POSIX APIs\n" +
                "In general POSIX API’s uses and behaviours’ are similar to those of Unix API’s. However, user’s programs should define the _POSIX_SOURCE or _POSIX_C_SOURCE in their programs to enable the POSIX API’s declaration in header files that they include.");
        introChapter.setChapterNote("API Common Characteristics\n" +
                "\uF0A7 Many APIs returns an integer value which indicates the termination status of their execution\n" +
                "\uF0A7 API return -1 to indicate the execution has failed, and the global variable errno is set with an error code.\n" +
                "\uF0A7 a user process may call perror function to print a diagnostic message of the failure to the std o/p, or it may call strerror function and gives it errno as the actual argument value; the strerror function returns a diagnostic message string and the user process may print that message in its preferred way\n" +
                "\uF0A7 the possible error status codes that may be assigned to errno by any API are defined in the <errno.h> header.");

        introChapter.setChapterLanguage(programLanguage);

        introChapters.add(introChapter);

        introChapter = new IntroChapter();
        introChapter.setChapterHeader("The UNIX and POSIX Development Environment");
        introChapter.setChapterId("usp_intro_4");
        introChapter.setChapterIndex(4);
        introChapter.setChapterIntro("The UNIX and POSIX Development Environment\n" +
                "POSIX provides portability at the source level. This means that you transport your source program to the target machine, compile it with the standard C compiler using conforming headers and link it with the standard libraries." +
                "\nSome commonly used POSIX.1 and UNIX API’s are declared in <unistd.h> header. Most of POSIX.1, POSIX>1b and UNIX API object code is stored in the libc.a and lib.so libraries.");
        introChapter.setChapterLanguage(programLanguage);

        introChapters.add(introChapter);

        initChapter( introChapters.get(0) );*/


        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(IntroActivity.this);
        firebaseDatabaseHandler.getIntroChapters(new FirebaseDatabaseHandler.GetIntroChaptersListener() {
            @Override
            public void onSuccess(ArrayList<IntroChapter> introChapters) {
                IntroActivity.this.introChapters = introChapters;
                initChapter( introChapters.get(0) );
                currentIndex = 0;
            }

            @Override
            public void onError(DatabaseError error) {
                CommonUtils.displaySnackBar(IntroActivity.this, R.string.unable_to_fetch_data);
            }
        });
        /*for( IntroChapter chapter : introChapters ) {
            firebaseDatabaseHandler.writeIntroChapter( chapter );
        }*/

    }
    @Override
    protected void onResume() {
        super.onResume();
        CreekApplication.getInstance().setAppRunning(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        CreekApplication.getInstance().setAppRunning(false);
    }

    private void initChapter(IntroChapter introChapter) {

        toolbar.setTitle(introChapter.getChapterHeader());
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
        if( introChapters.size() > 0 ) {
            if (id == R.id.intro_item) {
                toolbar.setTitle(item.getTitle());
                initChapter( introChapters.get(0) );
                currentIndex = 0;
                doneFAB.setImageDrawable(ContextCompat.getDrawable(IntroActivity.this, android.R.drawable.ic_media_play));
            } else if (id == R.id.basics_item) {
                toolbar.setTitle(item.getTitle());
                initChapter( introChapters.get(1) );
                currentIndex = 1;
                doneFAB.setImageDrawable(ContextCompat.getDrawable(IntroActivity.this, android.R.drawable.ic_media_play));
            } else if (id == R.id.comments_item) {
                toolbar.setTitle(item.getTitle());
                initChapter( introChapters.get(2) );
                currentIndex = 2;
                doneFAB.setImageDrawable(ContextCompat.getDrawable(IntroActivity.this, android.R.drawable.ic_media_play));
            } else if (id == R.id.installation_item) {
                toolbar.setTitle(item.getTitle());
                initChapter(introChapters.get(3));
                currentIndex = 3;
                doneFAB.setImageDrawable(ContextCompat.getDrawable(IntroActivity.this, R.drawable.ic_done_all));
            } /*else if( id == R.id.chapters_item ) {
                navigateToChapters();
            }*/
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigateToChapters() {
        Intent textModeIntent = new Intent(IntroActivity.this, ChaptersActivity.class);
        startActivity(textModeIntent);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }
}

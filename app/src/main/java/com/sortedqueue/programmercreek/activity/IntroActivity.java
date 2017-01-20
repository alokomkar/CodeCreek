package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.content.Intent;
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

import com.google.firebase.database.DatabaseError;
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
        /*
        IntroChapter introChapter = new IntroChapter();
        introChapter.setChapterHeader("Introduction");
        introChapter.setChapterId("java_intro_1");
        introChapter.setChapterIndex(1);
        introChapter.setChapterIntro("Java is a high-level programming " +
                "language originally developed by Sun Microsystems and released in 1995. " +
                "Java runs on a variety of platforms, such as Windows, Mac OS, and the various versions of UNIX." +
                "\n\nThe latest release of the Java Standard Edition is Java SE 8. With the advancement of Java and its widespread popularity, multiple configurations were built to suit various types of platforms. " +
                "\n\nFor example: J2EE for Enterprise Applications, J2ME for Mobile Applications.");
        introChapter.setChapterNote("Java is −\n" +
                "\n" +
                "Object Oriented − In Java, everything is an Object. Java can be easily extended since it is based on the Object model.\n" +
                "\n" +
                "Platform Independent − Unlike many other programming languages including C and C++, when Java is compiled, it is not compiled into platform specific machine, rather into platform independent byte code. This byte code is distributed over the web and interpreted by the Virtual Machine (JVM) on whichever platform it is being run on.\n" +
                "\n" +
                "Simple − Java is designed to be easy to learn. If you understand the basic concept of OOP Java, it would be easy to master.\n" +
                "\n" +
                "Secure − With Java's secure feature it enables to develop virus-free, tamper-free systems. Authentication techniques are based on public-key encryption.\n" +
                "\n" +
                "Architecture-neutral − Java compiler generates an architecture-neutral object file format, which makes the compiled code executable on many processors, with the presence of Java runtime system.\n" +
                "\n" +
                "Portable − Being architecture-neutral and having no implementation dependent aspects of the specification makes Java portable. Compiler in Java is written in ANSI C with a clean portability boundary, which is a POSIX subset.\n" +
                "\n" +
                "Robust − Java makes an effort to eliminate error prone situations by emphasizing mainly on compile time error checking and runtime checking.\n" +
                "\n" +
                "Multithreaded − With Java's multithreaded feature it is possible to write programs that can perform many tasks simultaneously. This design feature allows the developers to construct interactive applications that can run smoothly.\n" +
                "\n" +
                "Interpreted − Java byte code is translated on the fly to native machine instructions and is not stored anywhere. The development process is more rapid and analytical since the linking is an incremental and light-weight process.\n" +
                "\n" +
                "High Performance − With the use of Just-In-Time compilers, Java enables high performance.\n" +
                "\n" +
                "Distributed − Java is designed for the distributed environment of the internet.\n" +
                "\n" +
                "Dynamic − Java is considered to be more dynamic than C or C++ since it is designed to adapt to an evolving environment. Java programs can carry extensive amount of run-time information that can be used to verify and resolve accesses to objects on run-time.");
        introChapter.setChapterLanguage(programLanguage);
        introChapters.add(introChapter);

        introChapter = new IntroChapter();
        introChapter.setChapterHeader("Basics with your first program");
        introChapter.setChapterId("java_intro_2");
        introChapter.setChapterIndex(2);
        introChapter.setChapterIntro("* Let us look at a simple code that will print the words Hello World.");
        introChapter.setChapterProgram(
                "public class MyFirstJavaProgram {\n" +
                        "\n" +
                        "   *//* This is my first java program.\n" +
                        "    * This will print 'Hello World' as the output\n" +
                        "    *//*\n" +
                        "\n" +
                        "   public static void main(String []args) {\n" +
                        "      System.out.println(\"Hello World\"); // prints Hello World\n" +
                        "   }\n" +
                        "}");
        introChapter.setChapterProgramDescription(
                "Basic Syntax\n" +
                        "About Java programs, it is very important to keep in mind the following points.\n" +
                        "\n" +
                        "Case Sensitivity − Java is case sensitive, which means identifier Hello and hello would have different meaning in Java.\n" +
                        "\n" +
                        "Class Names − For all class names the first letter should be in Upper Case. If several words are used to form a name of the class, each inner word's first letter should be in Upper Case.\n" +
                        "\n" +
                        "Example: class MyFirstJavaClass\n" +
                        "\n" +
                        "Method Names − All method names should start with a Lower Case letter. If several words are used to form the name of the method, then each inner word's first letter should be in Upper Case.\n" +
                        "\n" +
                        "Example: public void myMethodName()\n" +
                        "\n" +
                        "Program File Name − Name of the program file should exactly match the class name.\n" +
                        "\n" +
                        "When saving the file, you should save it using the class name (Remember Java is case sensitive) and append '.java' to the end of the name (if the file name and the class name do not match, your program will not compile).\n" +
                        "\n" +
                        "Example: Assume 'MyFirstJavaProgram' is the class name. Then the file should be saved as 'MyFirstJavaProgram.java'\n" +
                        "\n" +
                        "public static void main(String args[]) − Java program processing starts from the main() method which is a mandatory part of every Java program."
                       );
        introChapter.setChapterProgramOutput("Hello World");
        introChapter.setChapterLanguage(programLanguage);

        introChapters.add(introChapter);

        introChapter = new IntroChapter();
        introChapter.setChapterHeader("Using comments");
        introChapter.setChapterId("java_intro_3");
        introChapter.setChapterIndex(3);
        introChapter.setChapterIntro("Java supports single-line and multi-line comments very similar to C and C++. All characters available inside any comment are ignored by Java compiler.");
        introChapter.setChapterProgram(
                "public class MyFirstJavaProgram {\n" +
                        "\n" +
                        "   *//* This is my first java program.\n" +
                        "    * This will print 'Hello World' as the output\n" +
                        "    * This is an example of multi-line comments.\n" +
                        "    *//*\n" +
                        "\n" +
                        "   public static void main(String []args) {\n" +
                        "      // This is an example of single line comment\n" +
                        "      *//* This is also an example of single line comment. *//*\n" +
                        "      System.out.println(\"Hello World\");\n" +
                        "   }\n" +
                        "}");
        introChapter.setChapterLanguage(programLanguage);

        introChapters.add(introChapter);

        introChapter = new IntroChapter();
        introChapter.setChapterHeader("Installation");
        introChapter.setChapterId("java_intro_4");
        introChapter.setChapterIndex(4);
        introChapter.setChapterIntro("Setting Up the Path for Windows\n" +
                "Assuming you have installed Java in c:\\Program Files\\java\\jdk directory −\n" +
                "\n" +
                "Right-click on 'My Computer' and select 'Properties'.\n" +
                "\n" +
                "Click the 'Environment variables' button under the 'Advanced' tab.\n" +
                "\n" +
                "Now, alter the 'Path' variable so that it also contains the path to the Java executable. Example, if the path is currently set to 'C:\\WINDOWS\\SYSTEM32', then change your path to read 'C:\\WINDOWS\\SYSTEM32;c:\\Program Files\\java\\jdk\\bin'.\n" +
                "\n" +
                "Setting Up the Path for Linux, UNIX, Solaris, FreeBSD\n" +
                "Environment variable PATH should be set to point to where the Java binaries have been installed. Refer to your shell documentation, if you have trouble doing this.\n" +
                "\n" +
                "Example, if you use bash as your shell, then you would add the following line to the end of your '.bashrc: export PATH = /path/to/java:$PATH'");
        introChapter.setChapterProgramDescription(
                "Popular Java Editors\n" +
                        "To write your Java programs, you will need a text editor. There are even more sophisticated IDEs available in the market. But for now, you can consider one of the following −\n" +
                        "\n" +
                        "Notepad − On Windows machine, you can use any simple text editor like Notepad (Recommended for this tutorial), TextPad.\n" +
                        "\n" +
                        "Netbeans − A Java IDE that is open-source and free which can be downloaded from https://www.netbeans.org/index.html.\n" +
                        "\n" +
                        "Eclipse − A Java IDE developed by the eclipse open-source community and can be downloaded from https://www.eclipse.org/.");
        introChapter.setChapterLanguage(programLanguage);

        introChapters.add(introChapter);*/


        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(IntroActivity.this);
        firebaseDatabaseHandler.getIntroChapters(new FirebaseDatabaseHandler.GetIntroChaptersListener() {
            @Override
            public void onSuccess(ArrayList<IntroChapter> introChapters) {
                IntroActivity.this.introChapters = introChapters;
                initChapter( introChapters.get(0) );
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
        if( introChapters.size() > 0 ) {
            if (id == R.id.intro_item) {
                toolbar.setTitle(item.getTitle());
                initChapter( introChapters.get(0) );
            } else if (id == R.id.basics_item) {
                toolbar.setTitle(item.getTitle());
                initChapter( introChapters.get(1) );
            } else if (id == R.id.comments_item) {
                toolbar.setTitle(item.getTitle());
                initChapter( introChapters.get(2) );
            } else if (id == R.id.installation_item) {
                toolbar.setTitle(item.getTitle());
                initChapter(introChapters.get(3));
            } else if( id == R.id.chapters_item ) {
                navigateToChapters();
            }
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

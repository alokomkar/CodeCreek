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
        introChapter.setChapterId("c_intro_1");
        introChapter.setChapterIndex(1);
        introChapter.setChapterIntro("The C programming language is a computer programming language that " +
                "was developed to do system programming for the operating system UNIX and is " +
                "an imperative programming language.\n\nC was developed in the early 1970 " +
                "by Ken Thompson and Dennis Ritchie.\n\nIt is a procedural language, " +
                "which means that people can write their programs as a series of step-by-step instructions. C is a compiled language.");
        introChapter.setChapterNote("The very first thing you need to do, before starting out in C, " +
                "is to make sure that you have a compiler.\n\nWhat is a compiler, you ask? " +
                "\nA compiler turns the program that you write into an executable that your computer can actually understand and run. " +
                "If you're taking a course, you probably have one provided through your school.");
        introChapter.setChapterLanguage(programLanguage);
        introChapters.add(introChapter);

        introChapter = new IntroChapter();
        introChapter.setChapterHeader("Basics with Hello world");
        introChapter.setChapterId("c_intro_2");
        introChapter.setChapterIndex(2);
        introChapter.setChapterIntro("* Every full C program begins inside a function called \"main\"." +
                "\n\n* A function is simply a collection of commands that do \"something\"." +
                "\n\n* The main function is always called when the program first executes." +
                "\n\n* From main, we can call other functions, whether they be written by us or by others or use built-in " +
                "language features." +
                "\n\n* To access the standard functions that comes with your compiler, you need to include a header with the " +
                "#include directive. What this does is effectively take everything in the header and paste it into your program." +
                "\n\nLet's look at a working program:");
        introChapter.setChapterProgram(
                "#include <stdio.h>\n" +
                "int main()\n" +
                "{\n" +
                "    printf( \"Hello world\\n\" );\n" +
                "    getchar();\n" +
                "    return 0;\n" +
                "}");
        introChapter.setChapterProgramDescription(
                "1. The #include is a \"preprocessor\" directive that tells the compiler to put code from the header called stdio.h into our program before actually creating the executable. By including header files, you can gain access to many different functions--both the printf and getchar functions are included in stdio.h" +
                "\n\n2. The next important line is int main(). This line tells the compiler that there is a function named main, and that the function returns an integer, hence int. The \"curly braces\" ({ and }) signal the beginning and end of functions and other code blocks." +
                        "\n\n3. The printf function is the standard C way of displaying output on the screen. The quotes tell the compiler that you want to output the literal string as-is (almost). The '\\n' sequence is actually treated as a single character that stands for a newline " +
                        "\n\n4. The actual effect of '\\n' is to move the cursor on your screen to the next line. Notice the semicolon: it tells the compiler that you're at the end of a command, such as a function call. You will see that the semicolon is used to end many lines in C" +
                        "\n\n5. The next command is getchar(). This is another function call: it reads in a single character and waits for the user to hit enter before reading the character. This line is included because many compiler environments will open a new console window, run the program, and then close the window before you can see the output. This command keeps that window from closing because the program is not done yet because it waits for you to hit enter. Including that line gives you time to see the program run. " +
                        "\n\n6. Finally, at the end of the program, we return a value from main to the operating system by using the return statement. This return value is important as it can be used to tell the operating system whether our program succeeded or not. A return value of 0 means success. ");
        introChapter.setChapterProgramOutput("Hello world");
        introChapter.setChapterLanguage(programLanguage);

        introChapters.add(introChapter);

        introChapter = new IntroChapter();
        introChapter.setChapterHeader("Using comments");
        introChapter.setChapterId("c_intro_3");
        introChapter.setChapterIndex(3);
        introChapter.setChapterIntro("* Comments are critical for all but the most trivial programs and this tutorial will often use " +
                "them to explain sections of code. " +
                "\n\nWhen you tell the compiler a section of text is a comment, it will ignore it when running the code, " +
                "allowing you to use any text you want to describe the real code. " +
                "\n\nTo create a comment in C, you surround the text with /* and then */ to block off everything between as a comment. " +
                "\n\nCertain compiler environments or text editors will change the color of a commented area to make it easier to spot, " +
                "but some will not. " +
                "Be certain not to accidentally comment out code (that is, " +
                "to tell the compiler part of your code is a comment) you need for the program.\n" +
                "\n\n" +
                "When you are learning to program, it is also useful to comment out sections of code in order to see how the output is affected. ");
        introChapter.setChapterProgram(
                "//This is a single line comment" +
                        "\n/*This is a multiline comment\nThis is the second line in comment*/");
        introChapter.setChapterLanguage(programLanguage);

        introChapters.add(introChapter);

        introChapter = new IntroChapter();
        introChapter.setChapterHeader("Installation");
        introChapter.setChapterId("c_intro_4");
        introChapter.setChapterIndex(4);
        introChapter.setChapterIntro("If you want to set up your environment for C programming language, you need the following two software tools available on your computer, " +
                "\n(a) Text Editor and " +
                "\n(b) The C Compiler." +
                "\n\nText Editor\n" +
                "\n" +
                "This will be used to type your program. Examples of few a editors include Windows Notepad, OS Edit command, Brief, Epsilon, EMACS, and vim or vi.\n" +
                "\n" +
                "The name and version of text editors can vary on different operating systems. For example, Notepad will be used on Windows, and vim or vi can be used on windows as well as on Linux or UNIX.\n" +
                "\n" +
                "The files you create with your editor are called the source files and they contain the program source codes. The source files for C programs are typically named with the extension \".c\".\n" +
                "\n" +
                "Before starting your programming, make sure you have one text editor in place and you have enough experience to write a computer program, save it in a file, compile it and finally execute it.");
        introChapter.setChapterProgramDescription(
                "The C Compiler\n" +
                        "\n" +
                        "The source code written in source file is the human readable source for your program. It needs to be \"compiled\", into machine language so that your CPU can actually execute the program as per the instructions given.\n" +
                        "\n" +
                        "The compiler compiles the source codes into final executable programs. The most frequently used and free available compiler is the GNU C/C++ compiler, otherwise you can have compilers either from HP or Solaris if you have the respective operating systems.\n" +
                        "\n" +
                        "The following section explains how to install GNU C/C++ compiler on various OS. We keep mentioning C/C++ together because GNU gcc compiler works for both C and C++ programming languages.\n" +
                        "Installation on UNIX/Linux\n" +
                        "\n" +
                        "If you are using Linux or UNIX, then check whether GCC is installed on your system by entering the following command from the command line −\n" +
                        "\n" +
                        "$ gcc -v\n" +
                        "\n" +
                        "If you have GNU compiler installed on your machine, then it should print a message as follows −\n" +
                        "\n" +
                        "Using built-in specs.\n" +
                        "Target: i386-redhat-linux\n" +
                        "Configured with: ../configure --prefix=/usr .......\n" +
                        "Thread model: posix\n" +
                        "gcc version 4.1.2 20080704 (Red Hat 4.1.2-46)\n" +
                        "\n" +
                        "If GCC is not installed, then you will have to install it yourself using the detailed instructions available at http://gcc.gnu.org/install/\n" +
                        "\n" +
                        "This tutorial has been written based on Linux and all the given examples have been compiled on the Cent OS flavor of the Linux system.\n" +
                        "Installation on Mac OS\n" +
                        "\n" +
                        "If you use Mac OS X, the easiest way to obtain GCC is to download the Xcode development environment from Apple's web site and follow the simple installation instructions. Once you have Xcode setup, you will be able to use GNU compiler for C/C++.\n" +
                        "\n" +
                        "Xcode is currently available at developer.apple.com/technologies/tools/.\n" +
                        "Installation on Windows\n" +
                        "\n" +
                        "To install GCC on Windows, you need to install MinGW. To install MinGW, go to the MinGW homepage, www.mingw.org, and follow the link to the MinGW download page. Download the latest version of the MinGW installation program, which should be named MinGW-<version>.exe.\n" +
                        "\n" +
                        "While installing Min GW, at a minimum, you must install gcc-core, gcc-g++, binutils, and the MinGW runtime, but you may wish to install more.\n" +
                        "\n" +
                        "Add the bin subdirectory of your MinGW installation to your PATH environment variable, so that you can specify these tools on the command line by their simple names.");
        introChapter.setChapterLanguage(programLanguage);

        introChapters.add(introChapter);

        initChapter( introChapters.get(0) );

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

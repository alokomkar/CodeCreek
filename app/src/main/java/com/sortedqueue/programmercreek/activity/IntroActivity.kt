package com.sortedqueue.programmercreek.activity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.firebase.database.DatabaseError
import com.sortedqueue.programmercreek.CreekApplication
import com.sortedqueue.programmercreek.R
import com.sortedqueue.programmercreek.database.IntroChapter
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler
import com.sortedqueue.programmercreek.util.CommonUtils
import com.startapp.android.publish.adsCommon.StartAppAd
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.app_bar_intro.*
import kotlinx.android.synthetic.main.content_intro.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.*

class IntroActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {



    private var introChapters = ArrayList<IntroChapter>()
    private var programLanguage: String? = null

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        initAndSetUserValues(nav_view.getHeaderView(0))
        doneFAB!!.setOnClickListener {
            currentIndex++
            if (currentIndex <= 3) {
                val drawable = if (currentIndex == 3) R.drawable.ic_done_all else android.R.drawable.ic_media_play
                doneFAB!!.setImageDrawable(ContextCompat.getDrawable(this@IntroActivity, drawable))
                initChapter(introChapters[currentIndex])
            } else {
                onBackPressed()
                //navigateToChapters();
            }
        }
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left)
    }

    private fun initAndSetUserValues(view: View) {
        val creekPreferences = CreekApplication.getPreferences()
        val drawerNameTextView : TextView = view.findViewById(R.id.drawerNameTextView)
        drawerNameTextView.text = creekPreferences.getAccountName()
        loadChapters()
    }

    private fun loadChapters() {
        introChapters = ArrayList<IntroChapter>()
        programLanguage = CreekApplication.creekPreferences!!.programLanguage
        /*programLanguage = "sql";
        new CreekPreferences(IntroActivity.this).setProgramLanguage(programLanguage);
        IntroChapter introChapter = new IntroChapter();
        introChapter.setChapterHeader("Introduction");
        introChapter.setChapterId("sql_intro_1");
        introChapter.setChapterIndex(1);
        introChapter.setChapterIntro(
                "SQL is a special-purpose domain-specific language used " +
                        "in programming and designed for managing data held " +
                        "in a relational database management system (RDBMS), " +
                        "or for stream processing in a relational data " +
                        "stream management system (RDSMS)." +
                        "\n\nThe SQL language is subdivided into several language elements, including:\n" +
                "\n" +
                "** Clauses : which are constituent components of statements and queries. (In some cases, these are optional.)\n\n" +
                "** Expressions : which can produce either scalar values, or tables consisting of columns and rows of data\n\n" +
                "** Predicates : which specify conditions that can be evaluated to SQL three-valued logic (3VL) (true/false/unknown) or Boolean truth values and are used to limit the effects of statements and queries, or to change program flow.\n\n" +
                "** Queries : which retrieve the data based on specific criteria. This is an important element of SQL.\n\n" +
                "** Statements : which may have a persistent effect on schemata and data, or may control transactions, program flow, connections, sessions, or diagnostics.\n\n" +
                "** SQL statements also include the semicolon (\";\") statement terminator. Though not required on every platform, it is defined as a standard part of the SQL grammar.\n\n" +
                "** Insignificant whitespace is generally ignored in SQL statements and queries, making it easier to format SQL code for readability.\n\n" +
                "");
        introChapter.setChapterProgram("An example of a complete statement with \n" +
                "several of its language elements.\n\n" +
                "UPDATE user \n" +
                "SET level = level + 1\n" +
                "WHERE name = 'Infinite Programmer';\n\n" +
                "Description\n" +
                "UPDATE CLAUSE : UPDATE user \n" +
                "SET CLAUSE : SET level = level + 1\n" +
                "EXPRESSION : level + 1\n" +
                "WHERE CLAUSE : WHERE name = 'Infinite Programmer'\n" +
                "Predicate : name = 'Infinite Programmer'"
                );
        introChapter.setChapterLanguage(programLanguage);
        introChapters.add(introChapter);

        introChapter = new IntroChapter();
        introChapter.setChapterHeader("What is RDBMS?");
        introChapter.setChapterId("sql_intro_2");
        introChapter.setChapterIndex(2);
        introChapter.setChapterIntro("RDBMS stands for Relational Database Management System. " +
                "RDBMS is the basis for SQL, and for all modern database systems like MS SQL Server, IBM DB2, Oracle, MySQL, and Microsoft Access.\n" +
                "\n" +
                "A Relational database management system (RDBMS) is a database management system (DBMS) that is based on the relational model as introduced by E. F. Codd.\n\n" +
                "What is table?\n" +
                "The data in RDBMS is stored in database objects called tables. The table is a collection of related data entries and it consists of columns and rows.\n\n" +
                "Following is the example of a CUSTOMERS table:\n" +
                "\n" +
                "+----+----------+-----+-----------+----------+\n" +
                "| ID | NAME     | AGE | ADDRESS   | SALARY   |\n" +
                "+----+----------+-----+-----------+----------+\n" +
                "|  1 | Ramesh   |  32 | Ahmedabad |  2000.00 |\n" +
                "|  2 | Khilan   |  25 | Delhi     |  1500.00 |\n" +
                "|  3 | kaushik  |  23 | Kota      |  2000.00 |\n" +
                "|  4 | Chaitali |  25 | Mumbai    |  6500.00 |\n" +
                "|  5 | Hardik   |  27 | Bhopal    |  8500.00 |\n" +
                "|  6 | Komal    |  22 | MP        |  4500.00 |\n" +
                "|  7 | Muffy    |  24 | Indore    | 10000.00 |\n" +
                "+----+----------+-----+-----------+----------+\n\n" +
                "What is field?\n" +
                "Every table is broken up into smaller entities called fields. The fields in the CUSTOMERS table consist of ID, NAME, AGE, ADDRESS and SALARY.\n" +
                "\n" +
                "A field is a column in a table that is designed to maintain specific information about every record in the table.\n" +
                "\n" +
                "What is record or row?\n" +
                "A record, also called a row of data, is each individual entry that exists in a table. For example there are 7 records in the above CUSTOMERS table. Following is a single row of data or record in the CUSTOMERS table:\n" +
                "\n" +
                "+----+----------+-----+-----------+----------+\n" +
                "|  1 | Ramesh   |  32 | Ahmedabad |  2000.00 |\n" +
                "+----+----------+-----+-----------+----------+\n" +
                "A record is a horizontal entity in a table.\n" +
                "\n" +
                "What is column?\n" +
                "A column is a vertical entity in a table that contains all information associated with a specific field in a table.\n" +
                "\n" +
                "For example, a column in the CUSTOMERS table is ADDRESS, which represents location description and would consist of the following:\n" +
                "\n" +
                "+-----------+\n" +
                "| ADDRESS   |\n" +
                "+-----------+\n" +
                "| Ahmedabad |\n" +
                "| Delhi     |\n" +
                "| Kota      |\n" +
                "| Mumbai    |\n" +
                "| Bhopal    |\n" +
                "| MP        |\n" +
                "| Indore    |\n" +
                "+----+------+\n" +
                "What is NULL value?\n" +
                "A NULL value in a table is a value in a field that appears to be blank, which means a field with a NULL value is a field with no value.\n" +
                "\n" +
                "It is very important to understand that a NULL value is different than a zero value or a field that contains spaces. A field with a NULL value is one that has been left blank during record creation.");
        introChapter.setChapterLanguage(programLanguage);

        introChapters.add(introChapter);

        introChapter = new IntroChapter();
        introChapter.setChapterHeader("Extras");
        introChapter.setChapterId("sql_intro_3");
        introChapter.setChapterIndex(3);
        introChapter.setChapterIntro("SQL Constraints:\n" +
                "Constraints are the rules enforced on data columns on table. These are used to limit the type of data that can go into a table. This ensures the accuracy and reliability of the data in the database.\n" +
                "\n" +
                "Constraints could be column level or table level. Column level constraints are applied only to one column where as table level constraints are applied to the whole table.\n" +
                "\n" +
                "Following are commonly used constraints available in SQL:\n" +
                "\n" +
                "NOT NULL Constraint: Ensures that a column cannot have NULL value.\n" +
                "\n" +
                "DEFAULT Constraint: Provides a default value for a column when none is specified.\n" +
                "\n" +
                "UNIQUE Constraint: Ensures that all values in a column are different.\n" +
                "\n" +
                "PRIMARY Key: Uniquely identified each rows/records in a database table.\n" +
                "\n" +
                "FOREIGN Key: Uniquely identified a rows/records in any another database table.\n" +
                "\n" +
                "CHECK Constraint: The CHECK constraint ensures that all values in a column satisfy certain conditions.\n" +
                "\n" +
                "INDEX: Use to create and retrieve data from the database very quickly.\n" +
                "\n" +
                "Data Integrity:\n" +
                "The following categories of the data integrity exist with each RDBMS:\n" +
                "\n" +
                "Entity Integrity: There are no duplicate rows in a table.\n" +
                "\n" +
                "Domain Integrity: Enforces valid entries for a given column by restricting the type, the format, or the range of values.\n" +
                "\n" +
                "Referential integrity: Rows cannot be deleted, which are used by other records.\n" +
                "\n" +
                "User-Defined Integrity: Enforces some specific business rules that do not fall into entity, domain or referential integrity.\n" +
                "\n");

        introChapter.setChapterLanguage(programLanguage);

        introChapters.add(introChapter);

        introChapter = new IntroChapter();
        introChapter.setChapterHeader("Databases");
        introChapter.setChapterId("sql_intro_4");
        introChapter.setChapterIndex(4);
        introChapter.setChapterIntro("SQL - RDBMS Databases\n" +
                "There are many popular RDBMS available to work with. This tutorial gives a brief overview of few most popular RDBMS. This would help you to compare their basic features.\n" +
                "\n" +
                "MySQL\n" +
                "MySQL is an open source SQL database, which is developed by Swedish company MySQL AB. MySQL is pronounced \"my ess-que-ell,\" in contrast with SQL, pronounced \"sequel.\"\n" +
                "\n" +
                "MySQL is supporting many different platforms including Microsoft Windows, the major Linux distributions, UNIX, and Mac OS X.\n" +
                "\n" +
                "MySQL has free and paid versions, depending on its usage (non-commercial/commercial) and features. MySQL comes with a very fast, multi-threaded, multi-user, and robust SQL database server.\n\n" +
                "MS SQL Server\n" +
                "MS SQL Server is a Relational Database Management System developed by Microsoft Inc. Its primary query languages are:\n" +
                "\n" +
                "T-SQL.\n" +
                "\n" +
                "ANSI SQL.\n\n" +
                "ORACLE\n" +
                        "It is a very large and multi-user database management system. Oracle is a relational database management system developed by 'Oracle Corporation'.\n" +
                        "\n" +
                        "Oracle works to efficiently manage its resource, a database of information, among the multiple clients requesting and sending data in the network.\n" +
                        "\n" +
                        "It is an excellent database server choice for client/server computing. Oracle supports all major operating systems for both clients and servers, including MSDOS, NetWare, UnixWare, OS/2 and most UNIX flavors.\n\n" +
                "MS ACCESS\n" +
                        "This is one of the most popular Microsoft products. Microsoft Access is an entry-level database management software. MS Access database is not only an inexpensive but also powerful database for small-scale projects.\n" +
                        "\n" +
                        "MS Access uses the Jet database engine, which utilizes a specific SQL language dialect (sometimes referred to as Jet SQL).\n" +
                        "\n" +
                        "MS Access comes with the professional edition of MS Office package. MS Access has easy-to-use intuitive graphical interface.\n" +
                        "\n" +
                        "1992 - Access version 1.0 was released.\n" +
                        "\n" +
                        "1993 - Access 1.1 released to improve compatibility with inclusion the Access Basic programming language.\n" +
                        "\n" +
                        "The most significant transition was from Access 97 to Access 2000\n" +
                        "\n" +
                        "2007 - Access 2007, a new database format was introduced ACCDB which supports complex data types such as multi valued and attachment fields.\n" +
                        "\n"
                );
        introChapter.setChapterLanguage(programLanguage);

        introChapters.add(introChapter);

        initChapter( introChapters.get(0) );*/


        val firebaseDatabaseHandler = FirebaseDatabaseHandler(this@IntroActivity)
        firebaseDatabaseHandler.getIntroChapters(object : FirebaseDatabaseHandler.GetIntroChaptersListener {
            override fun onSuccess(introChapters: ArrayList<IntroChapter>) {
                this@IntroActivity.introChapters = introChapters
                initChapter(introChapters[0])
                currentIndex = 0
            }

            override fun onError(error: DatabaseError) {
                CommonUtils.displaySnackBar(this@IntroActivity, R.string.unable_to_fetch_data)
            }
        })
        for (chapter in introChapters) {
            firebaseDatabaseHandler.writeIntroChapter(chapter)
        }

    }

    override fun onResume() {
        super.onResume()
        CreekApplication.instance.isAppRunning = (true)
    }

    override fun onPause() {
        super.onPause()
        CreekApplication.instance.isAppRunning = (false)
    }

    private fun initChapter(introChapter: IntroChapter) {

        toolbar!!.title = introChapter.chapterHeader
        chapterNoteTextView!!.visibility = View.GONE
        chapterProgramCodeView!!.visibility = View.GONE
        chapterProgramDescriptionTextView!!.visibility = View.GONE
        chapterProgramOutputTextView!!.visibility = View.GONE

        chapterHeaderTextView!!.text = introChapter.chapterHeader
        chapterIntroTextView!!.text = introChapter.chapterIntro

        if (introChapter.chapterNote != null) {
            chapterNoteTextView!!.visibility = View.VISIBLE
            chapterNoteTextView!!.text = introChapter.chapterNote
        }

        if (introChapter.chapterProgram != null) {
            chapterProgramCodeView!!.visibility = View.VISIBLE
            chapterProgramCodeView!!.setOptions(Options.get(this@IntroActivity)
                    .withLanguage(programLanguage!!)
                    .withCode(introChapter.chapterProgram)
                    .withTheme(ColorTheme.MONOKAI))
        }

        if (introChapter.chapterProgramDescription != null) {
            chapterProgramDescriptionTextView!!.visibility = View.VISIBLE
            chapterProgramDescriptionTextView!!.text = introChapter.chapterProgramDescription
        }

        if (introChapter.chapterProgramOutput != null) {
            chapterProgramOutputTextView!!.visibility = View.VISIBLE
            chapterProgramOutputTextView!!.text = introChapter.chapterProgramOutput
        }


    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (CreekApplication.creekPreferences!!.adsEnabled) {
                StartAppAd.onBackPressed(this)
                super.onBackPressed()
            } else {
                finish()
            }
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        if (introChapters.size > 0) {
            if (id == R.id.intro_item) {
                toolbar!!.title = item.title
                initChapter(introChapters[0])
                currentIndex = 0
                doneFAB!!.setImageDrawable(ContextCompat.getDrawable(this@IntroActivity, android.R.drawable.ic_media_play))
            } else if (id == R.id.basics_item) {
                toolbar!!.title = item.title
                initChapter(introChapters[1])
                currentIndex = 1
                doneFAB!!.setImageDrawable(ContextCompat.getDrawable(this@IntroActivity, android.R.drawable.ic_media_play))
            } else if (id == R.id.comments_item) {
                toolbar!!.title = item.title
                initChapter(introChapters[2])
                currentIndex = 2
                doneFAB!!.setImageDrawable(ContextCompat.getDrawable(this@IntroActivity, android.R.drawable.ic_media_play))
            } else if (id == R.id.installation_item) {
                toolbar!!.title = item.title
                initChapter(introChapters[3])
                currentIndex = 3
                doneFAB!!.setImageDrawable(ContextCompat.getDrawable(this@IntroActivity, R.drawable.ic_done_all))
            } /*else if( id == R.id.chapters_item ) {
                navigateToChapters();
            }*/
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun navigateToChapters() {
        val textModeIntent = Intent(this@IntroActivity, ChaptersActivity::class.java)
        startActivity(textModeIntent)
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right)
    }
}

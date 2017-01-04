package com.sortedqueue.programmercreek.database.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "MyProgrammingBuddy";

    //Program_Index table name
    private static final String TABLE_PROGRAM_INDEX = "Program_Index";

    //Program_Index Table Column names
    private static final String KEY_PROGRAM_INDEX_ID = "id";
    private static final String KEY_PROGRAM_NAME = "program_name";
    public static final String KEY_WIKI = "wiki";
    public static final String KEY_PROGRAM_LANGUAGE = "program_language";

    //Program_Table table name
    private static final String TABLE_PROGRAM_TABLE = "Program_Table";

    //Program_Table Table Column names.
    private static final String KEY_PROGRAM_TABLE_ID = "id";
    private static final String KEY_PROGRAM_LINE_NO = "Line_No";
    private static final String KEY_PROGRAM_LINE = "Program_Line";
    private static final String KEY_PROGRAM_LINE_DESCRIPTION = "Program_Line_Description";
    private static final String KEY_PROGRAM_LINE_HTML = "Program_Line_Html";


    private static final String TABLE_SCORES_TABLE = "Scores_Table";

    private static final String KEY_PROGRAM_INDEX = "program_Index";
    private static final String KEY_QUIZ_SCORE	= "quiz_Score";
    private static final String KEY_MATCH_SCORE = "match_Score";
    private static final String KEY_TEST_SCORE = "test_Score";
    private static final String KEY_MAX_QUIZ_SCORE	= "max_Quiz_Score";
    private static final String KEY_MAX_MATCH_SCORE = "max_Match_Score";
    private static final String KEY_MAX_TEST_SCORE = "max_Test_Score";

    String CREATE_PROGRAM_INDEX_TABLE =
            "CREATE TABLE " + TABLE_PROGRAM_INDEX
                    + "("
                    + KEY_PROGRAM_INDEX_ID + " INTEGER,"
                    + KEY_PROGRAM_LANGUAGE + " TEXT, "
                    + KEY_WIKI + " TEXT, "
                    + KEY_PROGRAM_NAME + " TEXT," + "PRIMARY KEY("+KEY_PROGRAM_INDEX_ID+" , "
                    + KEY_PROGRAM_LANGUAGE+"))";



    String CREATE_SCORES_TABLE =
            "CREATE TABLE " + TABLE_SCORES_TABLE
                    + "("
                    + KEY_PROGRAM_INDEX + " INTEGER,"
                    + KEY_QUIZ_SCORE + " FLOAT,"
                    + KEY_MATCH_SCORE + " FLOAT, "
                    + KEY_TEST_SCORE + " FLOAT,"
                    + KEY_MAX_QUIZ_SCORE + " FLOAT,"
                    + KEY_MAX_MATCH_SCORE + " FLOAT, "
                    + KEY_MAX_TEST_SCORE + " FLOAT"
                    + ")";

    Context mContext;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_PROGRAM_INDEX_TABLE);
        db.execSQL(CREATE_PROGRAM_TABLE_TABLE);
        db.execSQL(CREATE_SCORES_TABLE);

        //executeInsertStatements();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRAM_INDEX);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRAM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES_TABLE);

        // Create tables again
        onCreate(db);
        //TODO Check if this works
        //new DataBaseInsertAsyncTask( mContext, PreferenceManager.getDefaultSharedPreferences(mContext).edit()).execute();

    }

    // Adding new Program_Index
    public void addProgram_Index(Program_Index program_Index) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROGRAM_INDEX_ID, program_Index.getIndex()); // Contact Name
        values.put(KEY_PROGRAM_NAME, program_Index.getProgram_Description()); // Contact Phone Number
        values.put(KEY_WIKI, program_Index.getWiki());
        values.put(KEY_PROGRAM_LANGUAGE, program_Index.getmProgram_Language());
        // Inserting Row
        db.insert(TABLE_PROGRAM_INDEX, null, values);
        db.close(); // Closing database connection
    }

    // Adding new Program_Table
    public void addProgram_Table(Program_Table program_Table) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROGRAM_LINE_DESCRIPTION, program_Table.getProgram_Line_Description());
        values.put(KEY_PROGRAM_LINE_NO, program_Table.getLine_No()); // Contact Phone Number
        values.put(KEY_PROGRAM_TABLE_ID, program_Table.getIndex()); // Contact Name
        values.put(KEY_PROGRAM_LINE, program_Table.getProgram_Line());
        values.put(KEY_PROGRAM_LINE_HTML, program_Table.getmProgram_Line_Html());
        values.put(KEY_PROGRAM_LANGUAGE, program_Table.getmProgram_Language());

        // Inserting Row
        db.insert(TABLE_PROGRAM_TABLE, null, values);
        db.close(); // Closing database connection
    }



    // Getting single Program_Index
    public Program_Index getProgram_Index(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String programLanguage = new CreekPreferences(mContext).getProgramLanguage();
        if( programLanguage.equals("c++") ) {
            programLanguage = "cpp";
        }
        String[] selectionArgs = new String[] { String.valueOf(id), programLanguage };
        Cursor cursor;
        cursor = db.query(TABLE_PROGRAM_INDEX, new String[] { KEY_PROGRAM_INDEX_ID, KEY_PROGRAM_NAME, KEY_WIKI,
                        KEY_PROGRAM_LANGUAGE }, KEY_PROGRAM_INDEX_ID + "=? AND " + KEY_PROGRAM_LANGUAGE + "=?", selectionArgs
                , null, null, null, null);

        if (cursor != null) {
            try {
                cursor.moveToFirst();
            } catch( Exception e ) {
                return null;
            }
        }


        if( cursor == null ) {
            return null;
        }
        Program_Index program_Index;
        try {
            program_Index = new Program_Index(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(3), cursor.getString(2), cursor.getString(1));
        } catch( Exception e ) {
            return null;
        }
        db.close();
        // return contact
        return program_Index;
    }

    // Getting single Program_Table
    public Program_Table getProgram_Table(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PROGRAM_TABLE, new String[] { KEY_PROGRAM_TABLE_ID,
                        KEY_PROGRAM_LINE_NO, KEY_PROGRAM_LINE, KEY_PROGRAM_LINE_DESCRIPTION, KEY_PROGRAM_LINE_HTML  }, KEY_PROGRAM_TABLE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Program_Table program_Table = new Program_Table(cursor.getInt(0),
                cursor.getInt(1), cursor.getString(2),cursor.getString(3), cursor.getString(4));
        db.close();
        // return contact
        return program_Table;
    }



    // Getting All Program_Indexs
    public List<Program_Index> getAllProgram_Indexs(String program_language) {
        List<Program_Index> program_IndexList = new ArrayList<Program_Index>();

        SQLiteDatabase db = this.getWritableDatabase();
        String[] selectionArgs;
        Cursor cursor = null ;
        if( program_language.equalsIgnoreCase("c++") || program_language.equalsIgnoreCase("cpp") ) {
            selectionArgs = new String[] { "c++", "cpp" };
            cursor = db.query(
                    TABLE_PROGRAM_INDEX, //Table name
                    new String[] {KEY_PROGRAM_INDEX_ID, KEY_PROGRAM_LANGUAGE, KEY_WIKI, KEY_PROGRAM_NAME }, //Table columns
                    KEY_PROGRAM_LANGUAGE + "=? OR " + KEY_PROGRAM_LANGUAGE +"=?", //Selection arg
                    selectionArgs, //Selection arg values
                    null, //group by
                    null, // havinb
                    KEY_PROGRAM_INDEX_ID, //order by
                    null  ); //limit
        }
        else {
            selectionArgs = new String[] { program_language };
            cursor = db.query(
                    TABLE_PROGRAM_INDEX, //Table name
                    new String[] {KEY_PROGRAM_INDEX_ID, KEY_PROGRAM_LANGUAGE, KEY_WIKI, KEY_PROGRAM_NAME }, //Table columns
                    KEY_PROGRAM_LANGUAGE + "=?", //Selection arg
                    selectionArgs, //Selection arg values
                    null, //group by
                    null, // havinb
                    KEY_PROGRAM_INDEX_ID, //order by
                    null  ); //limit
        }

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Program_Index program_Index = new Program_Index();
                program_Index.setIndex(Integer.parseInt(cursor.getString(0)));
                program_Index.setmProgram_Language(cursor.getString(1));
                program_Index.setWiki(cursor.getString(2));
                program_Index.setProgram_Description(cursor.getString(3));
                // Adding contact to list
                program_IndexList.add(program_Index);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return program_IndexList;
    }



    // Getting All Program_Tables
    public List<Program_Table> getAllProgram_Tables() {
        List<Program_Table> program_TableList = new ArrayList<Program_Table>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PROGRAM_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Program_Table program_Table = new Program_Table();
                program_Table.setIndex(Integer.parseInt(cursor.getString(0)));
                program_Table.setmProgram_Language(cursor.getString(1));
                program_Table.setLine_No(cursor.getInt(2));
                program_Table.setProgram_Line(cursor.getString(3));
                program_Table.setProgram_Line_Description(cursor.getString(4));
                program_Table.setmProgram_Line_Html(cursor.getString(5));
                // Adding contact to list
                program_TableList.add(program_Table);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return program_TableList;
    }

    String CREATE_PROGRAM_TABLE_TABLE =
            "CREATE TABLE " + TABLE_PROGRAM_TABLE
                    + "("
                    + KEY_PROGRAM_TABLE_ID + " INTEGER,"
                    + KEY_PROGRAM_LANGUAGE + " TEXT, "
                    + KEY_PROGRAM_LINE_NO + " INTEGER,"
                    + KEY_PROGRAM_LINE + " TEXT, "
                    + KEY_PROGRAM_LINE_DESCRIPTION + " TEXT, "
                    + KEY_PROGRAM_LINE_HTML + " TEXT, "
                    + "PRIMARY KEY("+KEY_PROGRAM_TABLE_ID+" , "
                    + KEY_PROGRAM_LINE_NO
                    + " , "
                    + KEY_PROGRAM_LANGUAGE+"))";

    // Getting All Program_Tables
    public List<Program_Table> getAllProgram_Tables(int index, String program_language) {
        List<Program_Table> program_TableList = new ArrayList<Program_Table>();
        // Select All Query
		/*String selectQuery = "SELECT  * FROM " + TABLE_PROGRAM_TABLE
				+" WHERE " + KEY_PROGRAM_TABLE_ID + " = " + index
				+ " AND " + KEY_PROGRAM_LANGUAGE + " = " + program_language;*/

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                TABLE_PROGRAM_TABLE, //Table name
                new String[] {KEY_PROGRAM_TABLE_ID, KEY_PROGRAM_LANGUAGE, KEY_PROGRAM_LINE_NO, KEY_PROGRAM_LINE, KEY_PROGRAM_LINE_DESCRIPTION, KEY_PROGRAM_LINE_HTML }, //Table columns
                KEY_PROGRAM_LANGUAGE + "=? AND " + KEY_PROGRAM_TABLE_ID + " =?", //Selection arg
                new String[] { program_language, String.valueOf(index)}, //Selection arg values
                null, //group by
                null, // havinb
                KEY_PROGRAM_LINE_NO, //order by
                null  ); //limit
        if( program_language.equals("c++") ) {
            cursor = db.query(
                    TABLE_PROGRAM_TABLE, //Table name
                    new String[] {KEY_PROGRAM_TABLE_ID, KEY_PROGRAM_LANGUAGE, KEY_PROGRAM_LINE_NO, KEY_PROGRAM_LINE, KEY_PROGRAM_LINE_DESCRIPTION, KEY_PROGRAM_LINE_HTML }, //Table columns
                    KEY_PROGRAM_LANGUAGE + "=? OR "+ KEY_PROGRAM_LANGUAGE + "=? AND " + KEY_PROGRAM_TABLE_ID + " =?", //Selection arg
                    new String[] { program_language, "cpp", String.valueOf(index)}, //Selection arg values
                    null, //group by
                    null, // havinb
                    KEY_PROGRAM_LINE_NO, //order by
                    null  ); //limit
        }
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Program_Table program_Table = new Program_Table();
                program_Table.setIndex(Integer.parseInt(cursor.getString(0)));
                program_Table.setmProgram_Language(cursor.getString(1));
                program_Table.setLine_No(cursor.getInt(2));
                program_Table.setProgram_Line(cursor.getString(3));
                program_Table.setProgram_Line_Description(cursor.getString(4));
                program_Table.setmProgram_Line_Html(cursor.getString(5));
                // Adding contact to list
                program_TableList.add(program_Table);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return program_TableList;
    }



    // Getting Program_Indexs Count
    public int getProgram_IndexsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PROGRAM_INDEX;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return count;
    }

    // Getting Program_Tables Count
    public int getProgram_TablesCount() {

        String countQuery = "SELECT  distinct("+KEY_PROGRAM_TABLE_ID+") FROM " + TABLE_PROGRAM_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return count;
    }


    // Updating single Program_Index
    public int updateProgram_Index(Program_Index program_Index) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROGRAM_INDEX_ID, program_Index.getIndex());
        values.put(KEY_PROGRAM_NAME, program_Index.getProgram_Description());

        int result = db.update(TABLE_PROGRAM_INDEX, values, KEY_PROGRAM_INDEX_ID + " = ?",
                new String[] { String.valueOf(program_Index.getIndex()) });
        db.close();
        // updating row
        return result;

    }

    // Updating single Program_Table
    public int updateProgram_Table(Program_Table program_Table) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROGRAM_TABLE_ID, program_Table.getIndex());
        values.put(KEY_PROGRAM_LINE_NO, program_Table.getLine_No());
        values.put(KEY_PROGRAM_LINE, program_Table.getProgram_Line());
        values.put(KEY_PROGRAM_LINE_NO, program_Table.getProgram_Line_Description());
        values.put(KEY_PROGRAM_LINE_HTML, program_Table.getmProgram_Line_Html());
        int result = db.update(TABLE_PROGRAM_TABLE, values, KEY_PROGRAM_TABLE_ID + " = ?",
                new String[] { String.valueOf(program_Table.getIndex()) });
        // updating row
        db.close();
        return result;
    }



    // Deleting single Program_Index
    public void deleteProgram_Index(Program_Index program_Index) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROGRAM_INDEX, KEY_PROGRAM_INDEX_ID + " = ?",
                new String[] { String.valueOf(program_Index.getIndex()) });
        db.close();
    }

    // Deleting single Program_Table
    public void deleteProgram_Table(Program_Table program_Table) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROGRAM_TABLE, KEY_PROGRAM_TABLE_ID + " = ?",
                new String[] { String.valueOf(program_Table.getIndex()) });
        db.close();
    }




}

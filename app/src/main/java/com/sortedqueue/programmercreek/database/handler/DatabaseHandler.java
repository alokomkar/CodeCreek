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

	/*private void executeInsertStatements() {
		*//**
     * CRUD Operations
     * *//*
		// Inserting Contacts
		Log.d("Insert: ", "Inserting ..");
		this.addProgram_Index(new Program_Index(1, "Hello World"));
		this.addProgram_Index(new Program_Index(2, "Sum of two numbers"));
		this.addProgram_Index(new Program_Index(3, "Pythagora's theorem"));
		this.addProgram_Index(new Program_Index(4, "Fibonaci Series - Recursion"));
		this.addProgram_Index(new Program_Index(5, "Stack Definition"));
		this.addProgram_Index(new Program_Index(6, "Stack Push Operation"));
		this.addProgram_Index(new Program_Index(7, "Stack Pop Operation"));
		this.addProgram_Index(new Program_Index(8, "Quick Sort"));

		*//**
     * CRUD Operations
     * *//*

		Log.d("Insert: ", "Inserting ..");
		int index = 0;
		this.addProgram_Table(new Program_Table(1, ++index, "#include \"stdio.h\"", "Header include"));
		this.addProgram_Table(new Program_Table(1, ++index, "void main()", "Main Declaration"));
		this.addProgram_Table(new Program_Table(1, ++index, "{", "Start"));
		this.addProgram_Table(new Program_Table(1, ++index, "	printf(\"HelloWorld\");", "Print Statement"));
		this.addProgram_Table(new Program_Table(1, ++index, "	getch();", "Wait for keyboard input"));
		this.addProgram_Table(new Program_Table(1, ++index, "}", "Finish"));

		index = 0;
		this.addProgram_Table(new Program_Table(2, ++index, "#include \"stdio.h\"", "Header include"));
		this.addProgram_Table(new Program_Table(2, ++index, "void main()", "Main Declaration"));
		this.addProgram_Table(new Program_Table(2, ++index, "{", "Start"));
		this.addProgram_Table(new Program_Table(2, ++index, "  int a = 1, b = 2;", "Variables declaration"));
		this.addProgram_Table(new Program_Table(2, ++index, "	printf(\"%d\", a + b );", "Print Statement : prints 3"));
		this.addProgram_Table(new Program_Table(2, ++index, "	getch();", "Wait for keyboard input"));
		this.addProgram_Table(new Program_Table(2, ++index, "}", "Finish"));

		index = 0;
		this.addProgram_Table(new Program_Table(3, ++index, "#include \"stdio.h\" #include \"math.h\"", "Headers include"));
		this.addProgram_Table(new Program_Table(3, ++index, "void main()", "Main Declaration"));
		this.addProgram_Table(new Program_Table(3, ++index, "{", "Start"));
		this.addProgram_Table(new Program_Table(3, ++index, "  int a = 1, b = 2;", "Variables declaration"));
		this.addProgram_Table(new Program_Table(3, ++index, "	printf(\"%f\", (float) sqrt((1*1)+(2*2)) );", "Print Statement : prints 2.236\n   sqrt() -\" inbuilt square root function \n  from math.h"));
		this.addProgram_Table(new Program_Table(3, ++index, "	getch();", "Wait for keyboard input"));
		this.addProgram_Table(new Program_Table(3, ++index, "}", "Finish"));

		index = 0;
		this.addProgram_Table(new Program_Table(4, ++index, "#include \"stdio.h\"", "Header include"));
		this.addProgram_Table(new Program_Table(4, ++index, "int fibonaci( int i )", "Function Definition"));
		this.addProgram_Table(new Program_Table(4, ++index, "{", "Start"));
		this.addProgram_Table(new Program_Table(4, ++index, "  if( i == 0 )", "Check if i is 0?"));
		this.addProgram_Table(new Program_Table(4, ++index, "    {", "i = 0, start of if block"));
		this.addProgram_Table(new Program_Table(4, ++index, "      return 0;", "return 0 "));
		this.addProgram_Table(new Program_Table(4, ++index, "    }", "end of if block"));
		this.addProgram_Table(new Program_Table(4, ++index, "  if( i == 1 )", "Check if i is 1?"));
		this.addProgram_Table(new Program_Table(4, ++index, "    {", "i = 0, start of if block"));
		this.addProgram_Table(new Program_Table(4, ++index, "      return 1;", "return 1 "));
		this.addProgram_Table(new Program_Table(4, ++index, "    }", "end of if block"));
		this.addProgram_Table(new Program_Table(4, ++index, "	return fibonaci(i-1) + fibonaci(i-2);", "Recursive call to function"));
		this.addProgram_Table(new Program_Table(4, ++index, "}", "Finish"));
		this.addProgram_Table(new Program_Table(4, ++index, "int main()", "Main Declaration - return type int"));
		this.addProgram_Table(new Program_Table(4, ++index, "{", "Start"));
		this.addProgram_Table(new Program_Table(4, ++index, "  int i;", "Variable declaration"));
		this.addProgram_Table(new Program_Table(4, ++index, "  for( i = 0; i < 10; i++ )", "for loop to print first 10 numbers"));
		this.addProgram_Table(new Program_Table(4, ++index, "    {", "i = 0, start of for block"));
		this.addProgram_Table(new Program_Table(4, ++index, "      printf(\"%d\\t%n\", fibonaci(i));", "print fibonacci series "));
		this.addProgram_Table(new Program_Table(4, ++index, "    }", "end of for block"));
		this.addProgram_Table(new Program_Table(4, ++index, "	return 0;", "return 0 - return type int"));
		this.addProgram_Table(new Program_Table(4, ++index, "}", "Finish"));

		index = 0;
			mProgressDialog.setMessage("Inserting Program "+programIndex+++"...");
			this.addModule_Table(new Module_Table(5, 5, "Stack Definition"));
			this.addModule_Table(new Module_Table(5, 6, "Stack Push Operation"));
			this.addModule_Table(new Module_Table(5, 7, "Stack Pop Operation"));
			this.addModule_Table(new Module_Table(5, 8, "Stack Display Operation"));

		index = 0;
		this.addProgram_Table(new Program_Table(5, ++index, "#include \"stdio.h\"", "Header include"));
		this.addProgram_Table(new Program_Table(5, ++index, "#define MAXSIZE 5", "MAXSIZE definition"));
		this.addProgram_Table(new Program_Table(5, ++index, "struct stack", "Struct definition - name : stack"));
		this.addProgram_Table(new Program_Table(5, ++index, "{", "Start"));
		this.addProgram_Table(new Program_Table(5, ++index, "  int stk[MAXSIZE]; int top;", "Integer array to hold stack values; To point to top of stack"));
		//this.addProgram_Table(new Program_Table(5, ++index, "  int top;", "To point to top of stack"));
		this.addProgram_Table(new Program_Table(5, ++index, "};", "End"));
		this.addProgram_Table(new Program_Table(5, ++index, "typedef struct stack STACK;", "typedef used to declare stack"));
		this.addProgram_Table(new Program_Table(5, ++index, "STACK s;", "STACK variable declaration"));

		index = 0;
		this.addProgram_Table(new Program_Table(6, ++index, "void push ()", "Function Definition"));
		this.addProgram_Table(new Program_Table(6, ++index, "{", "Start"));
		this.addProgram_Table(new Program_Table(6, ++index, "  int num; if (s.top == (MAXSIZE - 1))", "Variable declaration : num \nCheck if stack is full"));
		//this.addProgram_Table(new Program_Table(6, ++index, "  if (s.top == (MAXSIZE - 1))", "Check if stack is full"));
		this.addProgram_Table(new Program_Table(6, ++index, "  {", "Start"));
		this.addProgram_Table(new Program_Table(6, ++index, "    printf (\"Stack is Full\\n\");", "Prints \"Stack is Full\""));
		this.addProgram_Table(new Program_Table(6, ++index, "    return;", "Exit without push"));
		this.addProgram_Table(new Program_Table(6, ++index, "  }", "End"));
		this.addProgram_Table(new Program_Table(6, ++index, "  else", "Else block - Stack not full"));
		this.addProgram_Table(new Program_Table(6, ++index, "  {", "Start"));
		this.addProgram_Table(new Program_Table(6, ++index, "    printf (\"Enter the element to be pushed\\n\");", "Prints the message in double quotes"));
		this.addProgram_Table(new Program_Table(6, ++index, "    scanf (\"%d\", &num);", "Reads integer from standard input"));
		this.addProgram_Table(new Program_Table(6, ++index, "    s.top = s.top + 1;", "Increment the top index"));
		this.addProgram_Table(new Program_Table(6, ++index, "    s.stk[s.top] = num;", "Insert num to top of stack"));
		this.addProgram_Table(new Program_Table(6, ++index, "  }", "End"));
		this.addProgram_Table(new Program_Table(6, ++index, "  return;", "Exit the function"));
		this.addProgram_Table(new Program_Table(6, ++index, "}", "End"));

		index = 0;
		this.addProgram_Table(new Program_Table(7, ++index, "int pop ()", "Function Definition"));
		this.addProgram_Table(new Program_Table(7, ++index, "{", "Start"));
		this.addProgram_Table(new Program_Table(7, ++index, "  int num; \nif (s.top == - 1)", "Variable declaration : num \nCheck if stack is empty"));
		//this.addProgram_Table(new Program_Table(7, ++index, "  if (s.top == - 1)", "Check if stack is empty"));
		this.addProgram_Table(new Program_Table(7, ++index, "  {", "Start"));
		this.addProgram_Table(new Program_Table(7, ++index, "    printf (\"Stack is Empty\\n\");", "Prints \"Stack is Empty\""));
		this.addProgram_Table(new Program_Table(7, ++index, "    return (s.top);", "Exit without pop; return top index"));
		this.addProgram_Table(new Program_Table(7, ++index, "  }", "End"));
		this.addProgram_Table(new Program_Table(7, ++index, "  else", "Else block - Stack not full"));
		this.addProgram_Table(new Program_Table(7, ++index, "  {", "Start"));
		this.addProgram_Table(new Program_Table(7, ++index, "    num = s.stk[s.top];", "The element to be poped"));
		this.addProgram_Table(new Program_Table(7, ++index, "    printf (\"poped element is = %dn\", s.stk[s.top]);", "Print element being poped"));
		this.addProgram_Table(new Program_Table(7, ++index, "    s.top = s.top - 1;", "Decrement the top index"));
		this.addProgram_Table(new Program_Table(7, ++index, "  }", "End"));
		this.addProgram_Table(new Program_Table(7, ++index, "  return num;", "Return the element poped"));
		this.addProgram_Table(new Program_Table(7, ++index, "}", "End"));

		index = 0;
		this.addProgram_Table(new Program_Table(8, ++index,"void quick_sort(int arr[20],int low,int high)","Function Definition"));
		this.addProgram_Table(new Program_Table(8, ++index,"{","Start"));
		this.addProgram_Table(new Program_Table(8, ++index,"	int pivot, j, temp, i;","Variable declaration"));
		this.addProgram_Table(new Program_Table(8, ++index,"	if(low < high)","If low < high "));
		this.addProgram_Table(new Program_Table(8, ++index,"	{","Start"));
		this.addProgram_Table(new Program_Table(8, ++index,"		pivot = low;","Assign low to pivot"));
		this.addProgram_Table(new Program_Table(8, ++index,"		i = low;","Assign low to i"));
		this.addProgram_Table(new Program_Table(8, ++index,"		j = high;","Assign high to j"));
		this.addProgram_Table(new Program_Table(8, ++index,"		while(i < j)","While i < j repeat"));
		this.addProgram_Table(new Program_Table(8, ++index,"		{","Start"));
		this.addProgram_Table(new Program_Table(8, ++index,"			while((arr[i]<=arr[pivot])&&(i<high))","While arr[i] <= arr[pivot] and i < high"));
		this.addProgram_Table(new Program_Table(8, ++index,"				i++;","Increment i"));
		this.addProgram_Table(new Program_Table(8, ++index,"			while(arr[j]>arr[pivot])","While arr[j] > arr[pivot]"));
		this.addProgram_Table(new Program_Table(8, ++index,"				j--;","Decrement j"));
		this.addProgram_Table(new Program_Table(8, ++index,"			if(i<j)","If i < j"));
		this.addProgram_Table(new Program_Table(8, ++index,"			{","Start"));
		this.addProgram_Table(new Program_Table(8, ++index,"				temp=arr[i];","Assign arr[i] to temp"));
		this.addProgram_Table(new Program_Table(8, ++index,"				arr[i]=arr[j];","Assign arr[j] to arr[i]"));
		this.addProgram_Table(new Program_Table(8, ++index,"				arr[j]=temp;","Assign temp to arr[j]"));
		this.addProgram_Table(new Program_Table(8, ++index,"			}","End"));
		this.addProgram_Table(new Program_Table(8, ++index,"		}","End"));
		this.addProgram_Table(new Program_Table(8, ++index,"		temp=arr[pivot];","Assign arr[pivot] to temp"));
		this.addProgram_Table(new Program_Table(8, ++index,"		arr[pivot]=arr[j];","Assign arr[j] to arr[pivot]"));
		this.addProgram_Table(new Program_Table(8, ++index,"		arr[j]=temp;","Assign temp to arr[j]"));
		this.addProgram_Table(new Program_Table(8, ++index,"		quick_sort(arr,low,j-1);","Recursive Call - arr, low, j-1"));
		this.addProgram_Table(new Program_Table(8, ++index,"		quick_sort(arr,j+1,high);","Recursive Call - arr, j+1, high"));
		this.addProgram_Table(new Program_Table(8, ++index,"	}","End"));
		this.addProgram_Table(new Program_Table(8, ++index,"}","End"));


	}*/

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
        String[] selectionArgs = new String[] { String.valueOf(id), programLanguage };
        Cursor cursor;
        cursor = db.query(TABLE_PROGRAM_INDEX, new String[] { KEY_PROGRAM_INDEX_ID, KEY_PROGRAM_NAME, KEY_WIKI,
                        KEY_PROGRAM_LANGUAGE }, KEY_PROGRAM_INDEX_ID + "=? AND " + KEY_PROGRAM_LANGUAGE + "=?", selectionArgs
                , null, null, null, null);
        if( programLanguage.equals("c++") ) {
            selectionArgs = new String[] { String.valueOf(id), programLanguage, "cpp" };
            cursor = db.query(TABLE_PROGRAM_INDEX, new String[] { KEY_PROGRAM_INDEX_ID, KEY_PROGRAM_NAME, KEY_WIKI,
                            KEY_PROGRAM_LANGUAGE }, KEY_PROGRAM_INDEX_ID
                            + "=? AND "
                            + KEY_PROGRAM_LANGUAGE + "=? OR "
                            + KEY_PROGRAM_LANGUAGE + " =? "
                    , selectionArgs
                    , null, null, null, null);

        }
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

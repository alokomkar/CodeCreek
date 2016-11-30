package com.sortedqueue.programmercreek.database.operations;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;


public class DataBaseInserterAsyncTask extends AsyncTask<Void, Void, Void> {

	Context mContext = null;
	ProgressDialog mProgressDialog = null;
	DatabaseHandler mDatabaseHandler;
	int mIndex = 0;
	UIUpdateListener mUiUpdateListener;
	
	private String TAG = getClass().getSimpleName();
	private void logDebugMessage( String message ) {
		Log.d(TAG, message);
	}


	public DataBaseInserterAsyncTask( Context context, int index, UIUpdateListener uiUpdateListener ) {

		this.mContext = context;
		mDatabaseHandler = new DatabaseHandler(context);
		this.mIndex = index;
		this.mUiUpdateListener = uiUpdateListener;

	}


	public DatabaseHandler insertProgramtoDB( Context context ) {

		/**
		 * Adding data to database
		 * */
		if( mDatabaseHandler == null ) {
			mDatabaseHandler = new DatabaseHandler(context);	
		}

		/**
		 * CRUD Operations
		 * */
		logDebugMessage("Inserting Programs...");

		insertProgram_1( mDatabaseHandler );
		insertProgram_2( mDatabaseHandler );
		insertProgram_3( mDatabaseHandler );
		insertProgram_4( mDatabaseHandler );
		insertProgram_5( mDatabaseHandler );
		insertProgram_6( mDatabaseHandler );
		insertProgram_7( mDatabaseHandler );
		insertProgram_8( mDatabaseHandler );
		insertProgram_9( mDatabaseHandler );
		insertProgram_10( mDatabaseHandler );
		insertProgram_11( mDatabaseHandler );
		insertProgram_12( mDatabaseHandler );
		insertProgram_13( mDatabaseHandler );
		insertProgram_14( mDatabaseHandler );
		insertProgram_15( mDatabaseHandler );
		insertProgram_16( mDatabaseHandler );
		insertProgram_17( mDatabaseHandler );
		insertProgram_18( mDatabaseHandler );
		insertProgram_19( mDatabaseHandler );
		insertProgram_20( mDatabaseHandler );
		insertProgram_21( mDatabaseHandler );
		insertProgram_22( mDatabaseHandler );
		insertProgram_23( mDatabaseHandler );
		insertProgram_24( mDatabaseHandler );
		insertProgram_25( mDatabaseHandler );
		insertProgram_26( mDatabaseHandler );
		insertProgram_27( mDatabaseHandler );
		insertProgram_28( mDatabaseHandler );
		insertProgram_29( mDatabaseHandler );
		insertProgram_30( mDatabaseHandler );
		insertProgram_31( mDatabaseHandler );

		return mDatabaseHandler;

	}

	private void insertProgram_30(DatabaseHandler databaseHandler) {
		
		logDebugMessage("Inserting Program 30");
		int index;
		index = 0;

		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "#include<stdio.h>","Header Include"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "int match(char*, char*);","Function Declaration"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "main()","Function Definition - Main"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "{","Start"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, " char text[100], searchtext[100]; int position;","Variable Declaration"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, " printf(\"Enter some text\\n\");","Print - Enter some text"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, " gets(text); ","Read text"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, " printf(\"Enter a string to find\n\");","Print - Enter a string to find"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, " gets(searchtext); ","Read searchtext"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, " position = match(text, searchtext);","Call match function - returns position"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, " if(position != -1)","Check if position != -1"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "  printf(\"Found at location %d\\n\", position+1); ","Print Found at location"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, " else ","else block"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "  printf(\"Not found.\\n\"); ","Print Not found"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, " return 0;","return 0"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "} ","End"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "int match(char *text, char *searchtext) ","Function Definition - match"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "{ ","Start"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, " int c; int position = 0; char *x, *y;","Variable Declaration"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, " x = text; ","Assign text to x"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, " y = searchtext; ","Assign searchtext to y"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, " while(*text)","While *text is not empty"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, " {","Start"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "  while(*x == *y) ","Repeat while *x = *y"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "  { ","Start"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "    x++; y++; ","increment x, y"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "    if(*x=='\0'||*y=='\0') ","if x or y = '\0'"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "    break;","break loop *x = *y"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "  }","End"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "  if(*y=='\0')","if *y = '\0'"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "   break; ","break loop *text"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "  text++; position++;","increment text, position"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "  x = text; y = searchtext;","Assign text to x, searchtext to y"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, " }","End"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, " if(*text)","if text not empty"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "   return position;","return position - found"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, " else ","else block"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "   return -1;","return -1; not found"));
		databaseHandler.addProgram_Table(new Program_Table( 30, ++index, "}","End"));


	}

	private void insertProgram_29(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 29");
		int index;
		index = 0;

		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, "#include <stdio.h>#include <stdlib.h>","Headers Include"));
		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, "int main()","Main Definition"));
		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, "{ ","Start"));
		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, " char ch, file_name[25]; FILE *fp;","Variables Declaration"));
		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, " printf(\"Enter the name of file\\n\");","Print - Enter the name of file"));
		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, " gets(file_name); ","Read file_name"));
		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, " fp = fopen(file_name,\"r\");","Open file in read mode"));
		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, " if( fp == NULL ) ","Check if fp = NULL"));
		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, " {","Start"));
		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, "   perror(\"Error opening the file.\\n\");","Print Error message"));
		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, "   exit(EXIT_FAILURE); ","exit with EXIT_FAILURE"));
		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, " }","End"));
		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, " printf(\"The contents of %s file are :\\n\", file_name);","Print - The contents of file are :"));
		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, " while( ( ch = fgetc(fp) ) != EOF ) ","repeat - read ch from file till EOF(End Of File)"));
		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, "   printf(\"%c\",ch);","print ch from file"));
		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, " fclose(fp);","close fp"));
		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, " return 0;","return 0"));
		databaseHandler.addProgram_Table( new Program_Table( 29, ++index, "} ","End"));


	}

	private void insertProgram_28(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 28");
		int index;
		index = 0;

		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, "#include <stdio.h>#include <string.h>","Headers Include"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, "int main()","Main Definition"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, "{ ","Start"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, " char string[100]; int c = 0, count[26] = {0};","Variable Declaration"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, " printf(\"Enter a string\\n\");","Print Enter a string"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, " gets(string);","Read string"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, " while ( string[c] != '\0' )","Repeat while string[c] != '\0'"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, " {","Start"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, "  if ( string[c] >= 'a' && string[c] <= 'z' ) ","check if string[c] is between 'a' and 'z'"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, "    count[string[c]-'a']++;","increment count"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, "  c++;","increment c"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, " }","End"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, " for ( c = 0 ; c < 26 ; c++ ) ","for loop c - 0 to 25"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, " {","Start"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, "  if( count[c] != 0 ) ","check if count[c] != 0"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, "    printf(\"%c occurs %d times in the entered string.\\n\",c+'a',count[c]);","Print Number of Occurences of entered string"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, " }","End"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, " return 0;","return 0"));
		databaseHandler.addProgram_Table( new Program_Table( 28, ++index, "} ","End"));


	}

	private void insertProgram_27(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 27");
		int index;
		index = 0;

		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "#include <stdio.h>","Header include"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "int main()","Main Definition"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "{","Start"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, " int array[100], n, c, d, position, swap;","Variable Declaration - array, n, c, d, position, swap"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, " printf(\"Enter number of elements\\n\");","Print Enter number of elements"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, " scanf(\"%d\", &n);","Read n"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, " printf(\"Enter %d integers\\n\", n);","Print Enter n integers"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, " for ( c = 0 ; c < n ; c++ )","for loop c - 0 to n-1"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "  scanf(\"%d\", &array[c]);","Read array[c]"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, " for ( c = 0 ; c < ( n - 1 ) ; c++ ) ","for loop c - 0 to n-2"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, " {","Start"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "  position = c;","Assign position = c"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "  for ( d = c + 1 ; d < n ; d++ )","for loop d - c+1 to n-1"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "  {","Start"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "    if ( array[position] > array[d] ) ","Check if array[position] > array[d]"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "     position = d;","Assign position = d"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "  }","End"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "  if ( position != c )","Check if postion not equal to c"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "  {","Start"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "    swap = array[c];","Assign swap = array[c]"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "    array[c] = array[position]; ","Assign array[c] = array[position]"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "    array[position] = swap; ","Assign array[position] = swap"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "  }","End"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, " }","End"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, " printf(\"Sorted list in ascending order:\\n\");","print Sorted list in ascending order"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, " for ( c = 0 ; c < n ; c++ ) ","for loop c - 0 to n-1"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "  printf(\"%d\\n\", array[c]);","print array[c]"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, " return 0; ","return 0"));
		databaseHandler.addProgram_Table( new Program_Table(27, ++index, "}","End"));


	}

	private void insertProgram_26(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 26");
		int index;
		index = 0;

		databaseHandler.addProgram_Table(new Program_Table(26, ++index, "#include <stdio.h>","Header include"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, "int main()","Main Definition"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, "{","Start"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, " int n, array[1000], c, d, t;","Variable declaration - n, array, c, d, t"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, " printf(\"Enter number of elements\\n\"); ","Print Enter number of elements"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, " scanf(\"%d\", &n);","Read n"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, " printf(\"Enter %d integers\\n\", n); ","Print Enter n integers"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, " for (c = 0; c < n; c++) { ","for loop c - 0 to n-1"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, "  scanf(\"%d\", &array[c]); ","Read array[c]"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, " }","End"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, " for (c = 1 ; c <= n - 1; c++) { ","for loop c = 1 to n-1"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, "  d = c;","Assign d = c"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, "  while ( d > 0 && array[d] < array[d-1]) {","Repeat while d > 0 and array[d] < array[d-1]"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, "   t = array[d];","Assign t = array[d]"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, "   array[d] = array[d-1];","Assign array[d] = array[d-1]"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, "   array[d-1] = t; ","Assign array[d-1] = t"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, "   d--;","Decrement d"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, "  }","End"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, " }","End"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, " printf(\"Sorted list in ascending order:\\n\");","Print Sorted List in ascending order"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, " for (c = 0; c <= n - 1; c++) {","for loop c - 0 to n-1"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, "  printf(\"%d\\n\", array[c]);","Print array[c]"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, " }","End"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, " return 0;","return 0"));
		databaseHandler.addProgram_Table(new Program_Table(26, ++index, "}","End"));


	}

	private void insertProgram_25(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 25");
		int index;
		index = 0;

		databaseHandler.addProgram_Table(new Program_Table(25, ++index, "void bubble_sort(long list[], long n)","Function Definition"));
		databaseHandler.addProgram_Table(new Program_Table(25, ++index, "{","Start"));
		databaseHandler.addProgram_Table(new Program_Table(25, ++index, " long c, d, t;","Variable Declaration - c, d, t"));
		databaseHandler.addProgram_Table(new Program_Table(25, ++index, " for (c = 0 ; c < ( n - 1 ); c++) ","for loop - c - 0 to n - 2"));
		databaseHandler.addProgram_Table(new Program_Table(25, ++index, " {","Start"));
		databaseHandler.addProgram_Table(new Program_Table(25, ++index, "  for (d = 0 ; d < n - c - 1; d++) ","for loop d - 0 to n - c - 2"));
		databaseHandler.addProgram_Table(new Program_Table(25, ++index, "  {","Start"));
		databaseHandler.addProgram_Table(new Program_Table(25, ++index, "   if (list[d] > list[d+1]) ","Check if list[d] > list[d+1]"));
		databaseHandler.addProgram_Table(new Program_Table(25, ++index, "   {","Start"));
		databaseHandler.addProgram_Table(new Program_Table(25, ++index, "    t = list[d]; ","Assign t = list[d]"));
		databaseHandler.addProgram_Table(new Program_Table(25, ++index, "    list[d] = list[d+1]; ","list[d] = list[d+1]"));
		databaseHandler.addProgram_Table(new Program_Table(25, ++index, "    list[d+1] = t; ","list[d+1] = t"));
		databaseHandler.addProgram_Table(new Program_Table(25, ++index, "   }","End"));
		databaseHandler.addProgram_Table(new Program_Table(25, ++index, "  }","End"));
		databaseHandler.addProgram_Table(new Program_Table(25, ++index, " }","End"));
		databaseHandler.addProgram_Table(new Program_Table(25, ++index, "}","End"));

	}

	private void insertProgram_24(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 24");
		int index;
		index = 0;

		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "void merge(int a[], int m, int b[], int n, int sorted[]) {","Function Definition"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, " int i, j, k;","Variable Declaration - i, j, k"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, " j = k = 0;","Assign j = k = 0"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, " for (i = 0; i < m + n;) { ","for loop i - 0 to m + n - 1"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "  if (j < m && k < n) {","Check if j<m and k<n"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "   if (a[j] < b[k]) {","Check if a[j] < b[k]"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "    sorted[i] = a[j]; ","Assign sorted[i] = a[j]"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "    j++;","increment j"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "   }","End"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "   else {","Else"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "    sorted[i] = b[k]; ","Assign sorted[i] = b[k]"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "    k++;","increment k"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "   }","End"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "   i++;","increment i"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "  }","End"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, " else if (j == m) {","Else check if j = m"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "  for (; i < m + n;) {","for loop i < m+n"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "   sorted[i] = b[k]; ","Assign sorted[i] = b[k]"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "   k++; i++;","increment k, i"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "  }","End"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, " }","End"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, " else {","Else"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "  for (; i < m + n;) {","for loop i < m+n"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "   sorted[i] = a[j]; ","Assign sorted[i] = a[j]"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "   j++; i++;","Increment j, i"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "  } ","End"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, " } ","End"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, " } ","End"));
		databaseHandler.addProgram_Table(new Program_Table(24, ++index, "} ","End"));

	}

	private void insertProgram_23(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 23");
		int index;
		index = 0;

		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, "#include <stdio.h> #include <stdlib.h>", "Headers Include"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, "void reverse_array(int*, int);", "Function Declaration - reverse_array"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, "int main()", "Main Definition"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, "{", "Start"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, " int n, c, *pointer;", "Variable declaration - n, c, pointer"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, " scanf(\"%d\",&n);", "Read n"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, " pointer = (int*)malloc(sizeof(int)*n); ", "Allocate size to pointer"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, " if( pointer == NULL )", "Check if pointer = NULL"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, "  exit(EXIT_FAILURE); ", "exit with EXIT_FAILURE"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, " for ( c = 0 ; c < n ; c++ )", "For loop c - 0 to n-1"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, "  scanf(\"%d\",(pointer+c));", "Read to pointer+c"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, " reverse_array(pointer, n); ", "Call reverse_array"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, " printf(\"Original array on reversal is\\n\"); ", "Print Original Array on reversal"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, " for ( c = 0 ; c < n ; c++ )", "For loop - c = 0 to n-1"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, "printf(\"%d\\n\",*(pointer+c));", "Print *(pointer+c)"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, " free(pointer); ", "Free memory of pointer"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, " return 0;", "return 0"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, "}", "End"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, "void reverse_array(int *pointer, int n) ", "Function Definition - reverse_array"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, "{", "Start"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, " int *s, c, d;", "Variable declaration - *s, c, d"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, " s = (int*)malloc(sizeof(int)*n); ", "Allocate memory of size n to s"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, " if( s == NULL )", "Check if s = NULL"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, "  exit(EXIT_FAILURE); ", "exit with EXIT_FAILURE"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, " for ( c = n - 1, d = 0 ; c >= 0 ; c--, d++ ) ", "for loop c - n-1 to 0, d - 0 to n-1"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, "  *(s+d) = *(pointer+c);", "Assign *(s+d) = *(pointer+c)"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, " for ( c = 0 ; c < n ; c++ )", "for loop c - 0 to n-1"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, "  *(pointer+c) = *(s+c);", "Assign *(pointer + c) = *(s + c)"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, " free(s);", "Free memory of s"));
		databaseHandler.addProgram_Table(new Program_Table( 23, ++index, "}", "End"));


	}

	private void insertProgram_22(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 22");
		int index;
		index = 0;

		databaseHandler.addProgram_Table(new Program_Table(22, ++index, "#include <stdio.h>","Header include"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, "int main()","Main Definition"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, "{","Start"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, " int c, first, last, middle, n, search, array[100];","Variable Declartion - c, first, last, middle..."));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, " printf(\"Enter number of elements\\n\");","Print Enter number of elements"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, " scanf(\"%d\",&n);","Read n"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, " printf(\"Enter %d integers\\n\", n);","Print Enter n integers"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, " for ( c = 0 ; c < n ; c++ )","for loop - c - 0 to n-1"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, "scanf(\"%d\",&array[c]);","Read array[c]"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, " printf(\"Enter value to find\\n\"); ","Print Enter value to find"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, " scanf(\"%d\",&search); ","Read search"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, " first = 0; ","Assign first = 0"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, " last = n - 1;","Assign last = n-1"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, " middle = (first+last)/2; ","Assign middle = (first + last)/2"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, " while( first <= last )","Repeat while first <= last"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, " {","Start"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, "  if ( array[middle] < search ) ","Check if array[middle] < search"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, "    first = middle + 1;","Assign first = middle + 1"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, "  else if ( array[middle] == search ) ","Check if array[middle] = search"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, "  {","Start"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, "    printf(\"%d found at location %d.\\n\", search, middle+1);","Print search found at location"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, "    break; ","break loop"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, "  }","End"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, "  else","Else"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, "    last = middle - 1;","Assign last = middle - 1"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, "    middle = (first + last)/2;","Assign middle = (first + last)/2"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, " }","End"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, " if ( first > last )","Check if first > last"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, "   printf(\"Not found! %d is not present in the list.\\n\", search);","Print Not Found"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, " return 0;","return 0"));
		databaseHandler.addProgram_Table(new Program_Table(22, ++index, "}","End"));


	}

	private void insertProgram_21(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 21");
		int index;
		index = 0;

		databaseHandler.addProgram_Table(new Program_Table(21, ++index, "#include <stdio.h>","Header Include"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, "int main()","Main Definition"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, "{","Start"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, " int array[100], search, c, n, count = 0;","Variable declaration - array, search, c, n, count"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, " printf(\"Enter the number of elements in array\\n\");","Print Enter the number of elements in array"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, " scanf(\"%d\",&n); ","Read n"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, " printf(\"Enter %d numbers\\n\", n);","Print Enter n numbers"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, " for ( c = 0 ; c < n ; c++ ) ","for loop c - 0 to n-1"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, "  scanf(\"%d\",&array[c]); ","read array[c]"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, " printf(\"Enter the number to search\\n\"); ","Print Enter number to search"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, " scanf(\"%d\",&search);","Read search integer"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, " for ( c = 0 ; c < n ; c++ ) ","for loop - c - 0 to n-1"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, " { ","Start"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, "  if ( array[c] == search )","Check if array[c] = search"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, "  {","Start"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, "   printf(\"%d is present at location %d.\\n\", search, c+1);","Print location"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, "	count++; ","increment count"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, "  }","End"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, " } ","End"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, " if ( count == 0 )","Check if count = 0"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, "  printf(\"%d is not present in array.\\n\", search); ","Print search not present"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, " else","Else"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, "  printf(\"%d is present %d times in array.\\n\", search, count)","print search present count times"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, " return 0; ","return 0"));
		databaseHandler.addProgram_Table(new Program_Table(21, ++index, "}","End"));


	}

	private void insertProgram_20(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 20");
		int index;
		index = 0;

		databaseHandler.addProgram_Table(new Program_Table(20, ++index, "#include <stdio.h>", "Header include"));
		databaseHandler.addProgram_Table(new Program_Table(20, ++index, "int main()", "Main Definition"));
		databaseHandler.addProgram_Table(new Program_Table(20, ++index, "{", "Start"));
		databaseHandler.addProgram_Table(new Program_Table(20, ++index, " int first, second, *p, *q, sum;", "Variable Declaration"));
		databaseHandler.addProgram_Table(new Program_Table(20, ++index, " printf(\"Enter 2 integers to add\\n\");", "Print Enter 2 integers to add"));
		databaseHandler.addProgram_Table(new Program_Table(20, ++index, " scanf(\"%d%d\", &first, &second); ", "Read integers - first and second"));
		databaseHandler.addProgram_Table(new Program_Table(20, ++index, " p = &first; ", "Assign Address of first to p"));
		databaseHandler.addProgram_Table(new Program_Table(20, ++index, " q = &second;", "Assign Address of second to q"));
		databaseHandler.addProgram_Table(new Program_Table(20, ++index, " sum = *p + *q;", "Assign *p + *q to sum"));
		databaseHandler.addProgram_Table(new Program_Table(20, ++index, " printf(\"Sum of entered numbers = %d\\n\",sum);", "Print Sum of entered numbers"));
		databaseHandler.addProgram_Table(new Program_Table(20, ++index, " return 0;", "return 0"));
		databaseHandler.addProgram_Table(new Program_Table(20, ++index, "}", "End"));


	}

	private void insertProgram_19(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 19");
		int index;
		index = 0;

		databaseHandler.addProgram_Table(new Program_Table(19, ++index, "#include <stdio.h>","Header include"));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, "int main()","Main Definition"));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, "{","Start"));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, " int n, reverse = 0, temp;","Variable Declaration - n, reverse, temp"));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, " printf(\"Enter a number to check if it is a palindrome or not\\n\");","Print Enter a number..."));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, " scanf(\"%d\",&n);","Read n"));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, " temp = n;","Assign n to temp"));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, " while( temp != 0 )","Repeat while temp != 0"));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, " {","Start"));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, "  reverse = reverse * 10;","Assign reverse * 10 to reverse"));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, "  reverse = reverse + temp % 10;","Assign reverse + temp % 10 to reverse"));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, "  temp = temp / 10;","Assign temp / 10 to temp"));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, " }","End"));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, " if ( n == reverse )","Check if n = reverse"));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, "  printf(\"%d is a palindrome number.\\n\", n);","Print n is palindrome"));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, " else","Else"));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, "  printf(\"%d is not a palindrome number.\\n\", n);","print n is not a palindrome"));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, " return 0;","return 0"));
		databaseHandler.addProgram_Table(new Program_Table(19, ++index, "}","End"));


	}

	private void insertProgram_18(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 18");
		int index;
		index = 0;

		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"#include<stdio.h>","Header Include"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"long factorial(int);long find_ncr(int, int);long find_npr(int, int);","Function Declarations"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"main()","Main Definition"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"{","Start"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"   int n, r;long ncr, npr;","Variable Declaration"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"   printf(\"Enter the value of n and r\\n\");","Print Enter the value of n and r"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"   scanf(\"%d%d\",&n,&r);","Read n and r"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"   ncr = find_ncr(n, r);","call find_ncr(), returns long"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"   npr = find_npr(n, r);","call find_npr(), returns long"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"   printf(\"%dC%d = %ld\\n\", n, r, ncr);","print nCr"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"   printf(\"%dP%d = %ld\\n\", n, r, npr);","print nPr"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"   return 0;","return 0"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"}","End"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"long find_ncr(int n, int r)","find_ncr - Function Definition"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"{","Start"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"   long result;","Variable Declaration - result"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"   result = factorial(n) / ( factorial(r) * factorial(n-r) );","result = factorial(n)/[factorial(r) * factorial(n-r)]"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"   return result;","return result"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"}","End"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"long find_npr(int n, int r)","Function Definition find_npr"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"{","Start"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"   long result;","Variable Declaration - result"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"   result = factorial(n)/factorial(n-r);","result = factorial(n) / factorial(n-r)"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"   return result;","return result"));
		databaseHandler.addProgram_Table(new Program_Table(18, ++index,"}","End"));


	}

	private void insertProgram_17(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 17");
		int index;
		index = 0;

		databaseHandler.addProgram_Table(new Program_Table(17, ++index, "#include <stdio.h>","Header include"));
		databaseHandler.addProgram_Table(new Program_Table(17, ++index, "int main()","Main Definition"));
		databaseHandler.addProgram_Table(new Program_Table(17, ++index, "{","Start"));
		databaseHandler.addProgram_Table(new Program_Table(17, ++index, " int n, c, k;","Variable Declaration"));
		databaseHandler.addProgram_Table(new Program_Table(17, ++index, " printf(\"Enter an integer in decimal number system\\n\");","Print Enter an integer in decimal number system"));
		databaseHandler.addProgram_Table(new Program_Table(17, ++index, " scanf(\"%d\", &n);","read n"));
		databaseHandler.addProgram_Table(new Program_Table(17, ++index, " printf(\"%d in binary number system is:\\n\", n);","Print binary number format of number n"));
		databaseHandler.addProgram_Table(new Program_Table(17, ++index, "for (c = 31; c >= 0; c--) ","for loop c = 31 - 0 "));
		databaseHandler.addProgram_Table(new Program_Table(17, ++index, "{","Start"));
		databaseHandler.addProgram_Table(new Program_Table(17, ++index, " k = n >> c;","Assign k = n >> c "));
		databaseHandler.addProgram_Table(new Program_Table(17, ++index, " if (k & 1)","Check if k & 1"));
		databaseHandler.addProgram_Table(new Program_Table(17, ++index, "  printf(\"1\");","Print 1"));
		databaseHandler.addProgram_Table(new Program_Table(17, ++index, " else","else"));
		databaseHandler.addProgram_Table(new Program_Table(17, ++index, "  printf(\"0\");","Print 0"));
		databaseHandler.addProgram_Table(new Program_Table(17, ++index, "}","End"));
		databaseHandler.addProgram_Table(new Program_Table(17, ++index, "printf(\"\\n\"); ","Print newline"));
		databaseHandler.addProgram_Table(new Program_Table(17, ++index, "return 0; ","return 0"));
		databaseHandler.addProgram_Table(new Program_Table(17, ++index, "}","End"));


	}

	private void insertProgram_16(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 16");
		int index;
		index = 0;

		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "#include <stdio.h>","Header Include"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "long gcd(long, long);","Function Declaration - gcd"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "int main() {","Main Definition"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "  long x, y, hcf, lcm;","Variable Declaration"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "  printf(\"Enter 2 integers\\n\");","Print Statement - Enter 2 integers"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "  scanf(\"%ld%ld\", &x, &y);","Read 2 integers"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "  hcf = gcd(x, y);","Calculate hcf by calling gcd(x, y)"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "  lcm = (x*y)/hcf;","Calculate lcm - (x*y)/hcf"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "  printf(\"GCD of %ld and %ld = %ld\\n\", x, y, hcf);","Print GCD"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "  printf(\"LCM of %ld and %ld = %ld\\n\", x, y, lcm);","Print LCM"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "  return 0;","return 0"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "}","End"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "long gcd(long a, long b) {","Function Definition"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "  if (b == 0) {","Check if b = 0 ?"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "    return a;","return a"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "  }","End"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "  else {","Else block"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "    return gcd(b, a % b);","Return result of recursive call - gcd(b, a%b)"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "  }","End"));
		databaseHandler.addProgram_Table(new Program_Table(16, ++index, "}","End"));


	}

	private void insertProgram_15(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 15");
		int index;
		index = 0;

		databaseHandler.addProgram_Table(new Program_Table(15, ++index, "long factorial(int n)","Function Definition"));
		databaseHandler.addProgram_Table(new Program_Table(15, ++index, "{","Start"));
		databaseHandler.addProgram_Table(new Program_Table(15, ++index, "  if (n == 0)","Check if n = 0 ?"));
		databaseHandler.addProgram_Table(new Program_Table(15, ++index, "    return 1;","return 1"));
		databaseHandler.addProgram_Table(new Program_Table(15, ++index, "  else","else block"));
		databaseHandler.addProgram_Table(new Program_Table(15, ++index, "    return(n * factorial(n-1));","return n * recursive call to function with n-1"));
		databaseHandler.addProgram_Table(new Program_Table(15, ++index, "}","End"));

	}

	private void insertProgram_14(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 14");
		int index;
		index = 0;

		databaseHandler.addProgram_Table(new Program_Table(14,++index,"#include<stdio.h>","Header Include"));
		databaseHandler.addProgram_Table(new Program_Table(14,++index,"main()","Main Declaration"));
		databaseHandler.addProgram_Table(new Program_Table(14,++index,"{","Start"));
		databaseHandler.addProgram_Table(new Program_Table(14,++index,"   int n;","Variable Declaration"));
		databaseHandler.addProgram_Table(new Program_Table(14,++index,"   printf(\"Input an integer\\n\");","Print Statement"));
		databaseHandler.addProgram_Table(new Program_Table(14,++index,"   scanf(\"%d\",&n);","Read Integer - n"));
		databaseHandler.addProgram_Table(new Program_Table(14,++index,"   n % 2 == 0 ? printf(\"Even\\n\") : printf(\"Odd\\n\");","Check if n % 2 is 0 then even, else odd"));
		databaseHandler.addProgram_Table(new Program_Table(14,++index,"   return 0;","Return 0"));
		databaseHandler.addProgram_Table(new Program_Table(14,++index,"}","End"));

	}

	private void insertProgram_13(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 13");
		int index;
		index = 0;
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"void quick_sort(int arr[20],int low,int high) ","Function Definition"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"{","Start"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"	int pivot, j, temp, i;","Variable declaration"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"	if(low < high)","If low < high "));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"	{","Start"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"		pivot = low;","Assign low to pivot"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"		i = low;","Assign low to i"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"		j = high;","Assign high to j"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"		while(i < j)","While i < j repeat"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"		{","Start"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"			while((arr[i]<=arr[pivot])&&(i<high))","While arr[i] <= arr[pivot] and i < high"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"				i++;","Increment i"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"			while(arr[j]>arr[pivot])","While arr[j] > arr[pivot]"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"				j--;","Decrement j"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"			if(i<j)","If i < j"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"			{","Start"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"				temp=arr[i];","Assign arr[i] to temp"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"				arr[i]=arr[j];","Assign arr[j] to arr[i]"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"				arr[j]=temp;","Assign temp to arr[j]"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"			}","End"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"		}","End"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"		temp=arr[pivot];","Assign arr[pivot] to temp"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"		arr[pivot]=arr[j];","Assign arr[j] to arr[pivot]"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"		arr[j]=temp;","Assign temp to arr[j]"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"		quick_sort(arr,low,j-1);","Recursive Call - arr, low, j-1"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"		quick_sort(arr,j+1,high);","Recursive Call - arr, j+1, high"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"	}","End"));
		databaseHandler.addProgram_Table(new Program_Table(13, ++index,"}","End"));


	}

	private void insertProgram_12(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 12");
		int index;
		index = 0;
		databaseHandler.addProgram_Table(new Program_Table(12,  ++index, "int reverse(int n) {","Function Definition"));   
		databaseHandler.addProgram_Table(new Program_Table(12,	++index, "while (n != 0)","While n not equal to 0"));
		databaseHandler.addProgram_Table(new Program_Table(12,	++index, "{","Start"));
		databaseHandler.addProgram_Table(new Program_Table(12,	++index, "	reverse = reverse * 10;  ","Assign reverse * 10 to reverse"));
		databaseHandler.addProgram_Table(new Program_Table(12,	++index, "	reverse = reverse + n % 10;","Assign reverse + n % 10 to reverse"));
		databaseHandler.addProgram_Table(new Program_Table(12,	++index, "	n = n/10;","Assign n/10 to n"));
		databaseHandler.addProgram_Table(new Program_Table(12,	++index, "}","End"));
		databaseHandler.addProgram_Table(new Program_Table(12,	++index, "return reverse;","return reversed number"));
		databaseHandler.addProgram_Table(new Program_Table(12,  ++index, "}","End"));


	}

	private void insertProgram_11(DatabaseHandler databaseHandler) {

		logDebugMessage("Inserting Program 11");
		int index;
		index = 0;
		databaseHandler.addProgram_Table(new Program_Table(11, ++index,"void swap(int x, int y) {","Function Definition"));
		databaseHandler.addProgram_Table(new Program_Table(11, ++index,"	x = x + y;","Assign x + y to x"));
		databaseHandler.addProgram_Table(new Program_Table(11, ++index,"	y = x - y;","Assign x - y to y"));
		databaseHandler.addProgram_Table(new Program_Table(11, ++index,"	x = x - y;","Assign x - y to x"));
		databaseHandler.addProgram_Table(new Program_Table(11, ++index,"}","End"));


	}

	private void insertProgram_10(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 10");
		int index;
		index = 0;
		databaseHandler.addProgram_Table(new Program_Table(10, ++index,"void swap(int x, int y) {","Function Definition"));
		databaseHandler.addProgram_Table(new Program_Table(10, ++index,"	x = x ^ y;","Assign x = x ^ y"));
		databaseHandler.addProgram_Table(new Program_Table(10, ++index,"	y = x ^ y;","Assign y = x ^ y"));
		databaseHandler.addProgram_Table(new Program_Table(10, ++index,"	x = x ^ y;","Assign x = x ^ y"));
		databaseHandler.addProgram_Table(new Program_Table(10, ++index,"}","End"));


	}

	private void insertProgram_9(DatabaseHandler databaseHandler) {
		
		logDebugMessage("Inserting Program 9");
		int index;
		index = 0;
		databaseHandler.addProgram_Table(new Program_Table(9, ++index,"void swap(int *x, int *y) {","Function Definition"));
		databaseHandler.addProgram_Table(new Program_Table(9, ++index,"	int temp = *x;","Assign temp = *x"));
		databaseHandler.addProgram_Table(new Program_Table(9, ++index,"	*x = *y;","Assign *x = *y"));
		databaseHandler.addProgram_Table(new Program_Table(9, ++index,"	*y = temp;","Assign *y = temp"));
		databaseHandler.addProgram_Table(new Program_Table(9, ++index,"}","End"));


	}

	private void insertProgram_8(DatabaseHandler databaseHandler) {
		
		logDebugMessage("Inserting Program 8");
		int index;
		index = 0;
		databaseHandler.addProgram_Table(new Program_Table(8, ++index,"void swap(int x, int y) {","Function Definition"));
		databaseHandler.addProgram_Table(new Program_Table(8, ++index,"	int temp = x;","Assign x to temp"));
		databaseHandler.addProgram_Table(new Program_Table(8, ++index,"	x = y;","Assign y to x"));
		databaseHandler.addProgram_Table(new Program_Table(8, ++index,"	y = temp;","Assign y to temp"));
		databaseHandler.addProgram_Table(new Program_Table(8, ++index,"}","End"));


	}

	private void insertProgram_7(DatabaseHandler databaseHandler) {
		
		logDebugMessage("Inserting Program 7");
		int index;
		index = 0;
		databaseHandler.addProgram_Table(new Program_Table(7, ++index, "int pop ()", "Function Definition"));
		databaseHandler.addProgram_Table(new Program_Table(7, ++index, "{", "Start"));
		databaseHandler.addProgram_Table(new Program_Table(7, ++index, "  int num; if (s.top == - 1)", "Variable declaration : num \nCheck if stack is empty"));
		databaseHandler.addProgram_Table(new Program_Table(7, ++index, "  {", "Start"));
		databaseHandler.addProgram_Table(new Program_Table(7, ++index, "    printf (\"Stack is Empty\\n\");", "Prints \"Stack is Empty\""));
		databaseHandler.addProgram_Table(new Program_Table(7, ++index, "    return (s.top);", "Exit without pop; return top index"));
		databaseHandler.addProgram_Table(new Program_Table(7, ++index, "  }", "End"));
		databaseHandler.addProgram_Table(new Program_Table(7, ++index, "  else", "Else block - Stack not full"));
		databaseHandler.addProgram_Table(new Program_Table(7, ++index, "  {", "Start"));
		databaseHandler.addProgram_Table(new Program_Table(7, ++index, "    num = s.stk[s.top];", "The element to be poped"));
		databaseHandler.addProgram_Table(new Program_Table(7, ++index, "    printf (\"poped element is = %dn\", s.stk[s.top]);", "Print element being poped"));
		databaseHandler.addProgram_Table(new Program_Table(7, ++index, "    s.top = s.top - 1;", "Decrement the top index"));
		databaseHandler.addProgram_Table(new Program_Table(7, ++index, "  }", "End"));
		databaseHandler.addProgram_Table(new Program_Table(7, ++index, "  return num;", "Return the element poped"));
		databaseHandler.addProgram_Table(new Program_Table(7, ++index, "}", "End"));

	}

	private void insertProgram_6(DatabaseHandler databaseHandler) {
		
		logDebugMessage("Inserting Program 6");
		int index;
		index = 0;
		databaseHandler.addProgram_Table(new Program_Table(6, ++index, "void push ()", "Function Definition"));
		databaseHandler.addProgram_Table(new Program_Table(6, ++index, "{", "Start"));
		databaseHandler.addProgram_Table(new Program_Table(6, ++index, "  int num; if (s.top == (MAXSIZE - 1))", "Variable declaration : num; Check if stack is full"));
		//databaseHandler.addProgram_Table(new Program_Table(6, ++index, "  if (s.top == (MAXSIZE - 1))", "Check if stack is full"));
		databaseHandler.addProgram_Table(new Program_Table(6, ++index, "  {", "Start"));
		databaseHandler.addProgram_Table(new Program_Table(6, ++index, "    printf (\"Stack is Full\\n\");", "Prints \"Stack is Full\""));
		databaseHandler.addProgram_Table(new Program_Table(6, ++index, "    return;", "Exit without push"));
		databaseHandler.addProgram_Table(new Program_Table(6, ++index, "  }", "End"));
		databaseHandler.addProgram_Table(new Program_Table(6, ++index, "  else", "Else block - Stack not full"));
		databaseHandler.addProgram_Table(new Program_Table(6, ++index, "  {", "Start"));
		databaseHandler.addProgram_Table(new Program_Table(6, ++index, "    printf (\"Enter the element to be pushed\\n\");", "Prints the message in double quotes"));
		databaseHandler.addProgram_Table(new Program_Table(6, ++index, "    scanf (\"%d\", &num);", "Reads integer from standard input"));
		databaseHandler.addProgram_Table(new Program_Table(6, ++index, "    s.top = s.top + 1;", "Increment the top index"));
		databaseHandler.addProgram_Table(new Program_Table(6, ++index, "    s.stk[s.top] = num;", "Insert num to top of stack"));
		databaseHandler.addProgram_Table(new Program_Table(6, ++index, "  }", "End"));
		databaseHandler.addProgram_Table(new Program_Table(6, ++index, "  return;", "Exit the function"));
		databaseHandler.addProgram_Table(new Program_Table(6, ++index, "}", "End"));


	}

	private void insertProgram_5(DatabaseHandler databaseHandler) {
		
		logDebugMessage("Inserting Program 5");
		int index;
		index = 0;
		databaseHandler.addProgram_Table(new Program_Table(5, ++index, "#include \"stdio.h\"", "Header include"));
		databaseHandler.addProgram_Table(new Program_Table(5, ++index, "#define MAXSIZE 5", "MAXSIZE macro definition"));
		databaseHandler.addProgram_Table(new Program_Table(5, ++index, "struct stack", "Struct definition - name : stack"));
		databaseHandler.addProgram_Table(new Program_Table(5, ++index, "{", "Start"));
		databaseHandler.addProgram_Table(new Program_Table(5, ++index, "  int stk[MAXSIZE]; int top;", "Integer array to hold stack values; To point to top of stack"));
		//databaseHandler.addProgram_Table(new Program_Table(5, ++index, "  int top;", "To point to top of stack"));
		databaseHandler.addProgram_Table(new Program_Table(5, ++index, "};", "End"));
		databaseHandler.addProgram_Table(new Program_Table(5, ++index, "typedef struct stack STACK;", "typedef used to declare stack"));
		databaseHandler.addProgram_Table(new Program_Table(5, ++index, "STACK s;", "STACK variable declaration"));


	}

	private void insertProgram_4(DatabaseHandler databaseHandler) {
		
		logDebugMessage("Inserting Program 4");
		int index;
		index = 0;
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "#include \"stdio.h\"", "Header include"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "int fibonaci( int i )", "Function Definition"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "{", "Start"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "  if( i == 0 )", "Check if i is 0?"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "    {", "i = 0, start of if block"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "      return 0;", "return 0 "));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "    }", "end of if block"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "  if( i == 1 )", "Check if i is 1?"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "    {", "i = 0, start of if block"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "      return 1;", "return 1 "));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "    }", "end of if block"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "	return fibonaci(i-1) + fibonaci(i-2);", "Recursive call to function"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "}", "Finish"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "int main()", "Main Declaration - return type int"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "{", "Start"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "  int i;", "Variable declaration"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "  for( i = 0; i < 10; i++ )", "for loop to print first 10 numbers"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "    {", "i = 0, start of for block"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "      printf(\"%d\\t%n\", fibonaci(i));", "print fibonacci series "));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "    }", "end of for block"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "	return 0;", "return 0 - return type int"));
		databaseHandler.addProgram_Table(new Program_Table(4, ++index, "}", "Finish"));


	}

	private void insertProgram_3(DatabaseHandler databaseHandler) {
		
		logDebugMessage("Inserting Program 3");
		int index;
		index = 0;
		databaseHandler.addProgram_Table(new Program_Table(3, ++index, "#include \"stdio.h\" #include \"math.h\"", "Headers include"));
		databaseHandler.addProgram_Table(new Program_Table(3, ++index, "void main()", "Main Declaration"));
		databaseHandler.addProgram_Table(new Program_Table(3, ++index, "{", "Start"));
		databaseHandler.addProgram_Table(new Program_Table(3, ++index, "  int a = 1, b = 2;", "Variables declaration"));
		databaseHandler.addProgram_Table(new Program_Table(3, ++index, "	printf(\"%f\", (float) sqrt((1*1)+(2*2)) );", "Print Statement : prints 2.236\n   sqrt() -\" inbuilt square root function \n  from math.h"));
		databaseHandler.addProgram_Table(new Program_Table(3, ++index, "	getch();", "Wait for keyboard input"));
		databaseHandler.addProgram_Table(new Program_Table(3, ++index, "}", "Finish"));


	}

	private void insertProgram_2(DatabaseHandler databaseHandler) {

		logDebugMessage("Inserting Program 2");
		int index;
		index = 0;
		databaseHandler.addProgram_Table(new Program_Table(2, ++index, "#include \"stdio.h\"", "Header include"));
		databaseHandler.addProgram_Table(new Program_Table(2, ++index, "void main()", "Main Declaration"));
		databaseHandler.addProgram_Table(new Program_Table(2, ++index, "{", "Start"));
		databaseHandler.addProgram_Table(new Program_Table(2, ++index, "  int a = 1, b = 2;", "Variables declaration"));
		databaseHandler.addProgram_Table(new Program_Table(2, ++index, "	printf(\"%d\", a + b );", "Print Statement : prints 3"));
		databaseHandler.addProgram_Table(new Program_Table(2, ++index, "	getch();", "Wait for keyboard input"));
		databaseHandler.addProgram_Table(new Program_Table(2, ++index, "}", "Finish"));


	}

	private void insertProgram_1(DatabaseHandler databaseHandler) {

		logDebugMessage("Inserting Program 1");
		int index = 0;
		databaseHandler.addProgram_Table(new Program_Table(1, ++index, "#include \"stdio.h\"", "Header include"));
		databaseHandler.addProgram_Table(new Program_Table(1, ++index, "void main()", "Main Declaration"));
		databaseHandler.addProgram_Table(new Program_Table(1, ++index, "{", "Start"));
		databaseHandler.addProgram_Table(new Program_Table(1, ++index, "	printf(\"HelloWorld\");", "Print Statement"));
		databaseHandler.addProgram_Table(new Program_Table(1, ++index, "	getch();", "Wait for keyboard input"));
		databaseHandler.addProgram_Table(new Program_Table(1, ++index, "}", "Finish"));

	}

	public DatabaseHandler insertProgramListtoDB( Context context ) {
		/**
		 * Adding data to database
		 * */
		DatabaseHandler databaseHandler = new DatabaseHandler(context);

		/**
		 * CRUD Operations
		 * */
		// Inserting Contacts
		logDebugMessage("Inserting ProgramList");
		int index = 1;
		databaseHandler.addProgram_Index(new Program_Index(index++, "Hello World"));       
		databaseHandler.addProgram_Index(new Program_Index(index++, "Sum of two numbers"));       
		databaseHandler.addProgram_Index(new Program_Index(index++, "Pythagora's theorem"));       
		databaseHandler.addProgram_Index(new Program_Index(index++, "Fibonaci Series - Recursion"));       
		databaseHandler.addProgram_Index(new Program_Index(index++, "Stack Definition"));       
		databaseHandler.addProgram_Index(new Program_Index(index++, "Stack Push Operation"));       
		databaseHandler.addProgram_Index(new Program_Index(index++, "Stack Pop Operation"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Swap By Value"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Swap By Reference"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Swap By XOR"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Swap - 2 Variables"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Reverse Number"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Quick Sort"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Odd Even - ? Operator"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Factorial"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "HCF and LCM"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Decimal to binary conversion"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "nCr, nPr, Factorial"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Palindrome number"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Addition using Pointers"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Linear Search - Multiple Occurences"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Binary Search"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Reverse Array - Pointers"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Merge 2 Sorted Arrays"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Bubble Sort"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Insertion Sort"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Selection Sort"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Characters Frequency"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Read File"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "Pattern Matching - Pointers"));
		databaseHandler.addProgram_Index(new Program_Index(index++, "String Concatenation - without strcat"));


		return databaseHandler;
	}

	@Override
	protected Void doInBackground(Void... params) {

		logDebugMessage("Inserting Program Index : " + mIndex);
		switch( mIndex ) {
		case -1:
			insertProgramListtoDB(mContext);
			break;
		case 1:
			insertProgram_1(mDatabaseHandler);
			break;
		case 2:
			insertProgram_2(mDatabaseHandler);
			break;
		case 3:
			insertProgram_3(mDatabaseHandler);
			break;
		case 4:
			insertProgram_4(mDatabaseHandler);
			break;
		case 5:
			insertProgram_5(mDatabaseHandler);
			break;
		case 6:
			insertProgram_6(mDatabaseHandler);
			break;
		case 7:
			insertProgram_7(mDatabaseHandler);
			break;
		case 8:
			insertProgram_8(mDatabaseHandler);
			break;
		case 9:
			insertProgram_9(mDatabaseHandler);
			break;
		case 10:
			insertProgram_10(mDatabaseHandler);
			break;
		case 11:
			insertProgram_11(mDatabaseHandler);
			break;
		case 12:
			insertProgram_12(mDatabaseHandler);
			break;
		case 13:
			insertProgram_13(mDatabaseHandler);
			break;
		case 14:
			insertProgram_14(mDatabaseHandler);
			break;
		case 15:
			insertProgram_15(mDatabaseHandler);
			break;
		case 16:
			insertProgram_16(mDatabaseHandler);
			break;
		case 17:
			insertProgram_17(mDatabaseHandler);
			break;
		case 18:
			insertProgram_18(mDatabaseHandler);
			break;
		case 19:
			insertProgram_19(mDatabaseHandler);
			break;
		case 20:
			insertProgram_20(mDatabaseHandler);
			break;
		case 21:
			insertProgram_21(mDatabaseHandler);
			break;
		case 22:
			insertProgram_22(mDatabaseHandler);
			break;
		case 23:
			insertProgram_23(mDatabaseHandler);
			break;
		case 24:
			insertProgram_24(mDatabaseHandler);
			break;
		case 25:
			insertProgram_25(mDatabaseHandler);
			break;
		case 26:
			insertProgram_26(mDatabaseHandler);
			break;
		case 27:
			insertProgram_27(mDatabaseHandler);
			break;
		case 28:
			insertProgram_28(mDatabaseHandler);
			break;
		case 29:
			insertProgram_29(mDatabaseHandler);
			break;
		case 30:
			insertProgram_30(mDatabaseHandler);
			break;
		case 31:
			insertProgram_31(mDatabaseHandler);
			break;

		case -2 :
			insertProgramtoDB(mContext);
			break;
		default : 
			if( mUiUpdateListener != null ) {
				if( mProgressDialog.isShowing() ) {
					mProgressDialog.dismiss();
				}
				mUiUpdateListener.updateUI( );
			}

		}

		//insertProgramtoDB(mContext);

		return null;

	}


	private void insertProgram_31(DatabaseHandler databaseHandler) {
		logDebugMessage("Inserting Program 31");
		int index = 0;

		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "#include <stdio.h>","Header Include"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "void concatenate_string(char*, char*);","Function Declaration - concatenate_string"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "int main() {","Main Definition"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " char original[100], add[100];","Variable Declaration - original, add"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " printf(\"Enter source string\\n\"); ","Print Enter source string"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " gets(original);","Read orginal"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " printf(\"Enter string to concatenate\\n\");","Print Enter string to concatenate"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " gets(add);","Read add"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " concatenate_string(original, add);","Call function concatenate_string"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " printf(\"String after concatenation is \"%s\"\\n\", original);","Print String after concatenation"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " return 0;","return 0"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "}","End"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "void concatenate_string(char *original, char *add) {","Function Definition - concatenate_string"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " while(*original)","Repeat while *original not empty"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "  original++;","increment original"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " while(*add) {","Repeat while *add not empty"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "  *original = *add;","Assign *original = *add"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "  add++; original++;","increment add, original"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " }","End"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, " *original = '\0';","Assign *original = '\0'"));
		databaseHandler.addProgram_Table( new Program_Table( 31, ++index, "}","End"));


	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setMessage("Initializing data for the first time...");
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		preferences.edit().putBoolean(ProgrammingBuddyConstants.KEY_PROG_INDEX_INSERT, true).commit();
		preferences.edit().putBoolean(ProgrammingBuddyConstants.KEY_PROG_TABLE_INSERT, true).commit();
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
		if( mUiUpdateListener != null ) {
			mUiUpdateListener.updateUI( );
		}
	}
}

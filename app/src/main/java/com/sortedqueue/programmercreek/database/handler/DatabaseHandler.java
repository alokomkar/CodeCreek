package com.sortedqueue.programmercreek.database.handler;


import android.content.Context;
import android.util.Log;

import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.util.CreekPreferences;
import java.util.List;

import co.uk.rushorm.core.RushCallback;
import co.uk.rushorm.core.RushSearch;

public class DatabaseHandler {

	public static final String KEY_WIKI = "wiki";

	Context mContext;
	private String TAG = DatabaseHandler.class.getSimpleName();

	public DatabaseHandler(Context context) {
		this.mContext = context;
	}

	// Adding new Program_Index
	public void addProgram_Index(final Program_Index program_Index) {

		program_Index.save(new RushCallback() {
			@Override
			public void complete() {
				Log.d(TAG, "Program_Index saved : " + program_Index.toString());
			}
		});
	}

	// Adding new Program_Table
	public void addProgram_Table(final Program_Table program_Table) {
		program_Table.save(new RushCallback() {
			@Override
			public void complete() {
				Log.d(TAG, "Program_Table saved : " + program_Table.toString());
			}
		});
	}

	// Getting single Program_Index
	public Program_Index getProgram_Index(int id) {
		String programLanguage = new CreekPreferences(mContext).getProgramLanguage();
		if( programLanguage.equalsIgnoreCase("c++") ) {
			return new RushSearch()
					.whereEqual("mProgramIndex", id)
					.and()
					.whereEqual("mProgram_Language", programLanguage)
					.or().whereEqual("mProgram_Language", "cpp")
					.findSingle(Program_Index.class);
		}
        else
		return new RushSearch().whereEqual("mProgramIndex", id).and().whereEqual("mProgram_Language", programLanguage).findSingle(Program_Index.class);
	}

	// Getting All Program_Indexs
	public List<Program_Index> getAllProgram_Indexs(String program_language) {
		if( program_language.equalsIgnoreCase("c++") ) {
			return new RushSearch()
					.whereEqual("mProgram_Language", program_language)
					.or()
					.whereEqual("mProgram_Language", "cpp")
					.find(Program_Index.class);
		}
        else {
            return new RushSearch().whereEqual("mProgram_Language", program_language).find(Program_Index.class);
        }
	}

	// Getting All Program_Tables
	public List<Program_Table> getAllProgram_Tables(int index, String program_language) {
		if( program_language.equalsIgnoreCase("c++") ) {
			return new RushSearch()
					.whereEqual("mProgramTableIndex", index)
					.and()
					.whereEqual("mProgram_Language", program_language)
					.or()
					.whereEqual("mProgram_Language", "cpp")
					.find(Program_Table.class);
		}
        else
		return new RushSearch().whereEqual("mProgramTableIndex", index).and().whereEqual("mProgram_Language", program_language).find(Program_Table.class);
	}

}

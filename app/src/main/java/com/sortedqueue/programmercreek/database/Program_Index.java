package com.sortedqueue.programmercreek.database;

import com.google.firebase.database.IgnoreExtraProperties;

import co.uk.rushorm.core.RushObject;

/**
 * Program_Index - POJO for Programer_Index table in database.
 * */
@IgnoreExtraProperties
public class Program_Index extends RushObject {
	
	int mProgramIndex;
	String mProgram_Description;
	String wiki;
	String mProgram_Language;
	
	public Program_Index(int index, String program_Description, String wiki, String mProgram_Language) {
		super();
		mProgramIndex = index;
		mProgram_Description = program_Description;
		this.wiki = wiki;
		this.mProgram_Language = mProgram_Language;
	}



	public Program_Index() {
		super();
	}

	public int getIndex() {
		return mProgramIndex;
	}

	public void setIndex(int index) {
		mProgramIndex = index;
	}

	public String getProgram_Description() {
		return mProgram_Description;
	}

	public void setProgram_Description(String program_Description) {
		mProgram_Description = program_Description;
	}

	public String getWiki() {
		return wiki;
	}

	public void setWiki(String wiki) {
		this.wiki = wiki;
	}

	public String getmProgram_Language() {
		return mProgram_Language;
	}

	public void setmProgram_Language(String mProgram_Language) {
		this.mProgram_Language = mProgram_Language;
	}

	@Override
	public String toString() { 
		return mProgramIndex+": "+mProgram_Description;
	}

}

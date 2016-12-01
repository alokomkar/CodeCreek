package com.sortedqueue.programmercreek.database;


import com.sortedqueue.programmercreek.util.PrettifyHighlighter;

public class Program_Table {
	
	int mProgramTableIndex;
	int mProgramLine_No;
	String mProgram_Line;
	String mProgram_Line_Description;
	String mProgram_Line_Html;
	
	/**
	 * @param index
	 * @param line_No
	 * @param program_Line
	 * @param program_Line_Description
	 */
	public Program_Table(int index, int line_No, String program_Line,
			String program_Line_Description) {
		super();
		mProgramTableIndex = index;
		mProgramLine_No = line_No;
		mProgram_Line = program_Line;
		mProgram_Line_Description = program_Line_Description;
		mProgram_Line_Html = PrettifyHighlighter.getInstance().highlight("c", mProgram_Line);
	}
	
	
	/**
	 * @param mProgramTableIndex
	 * @param mProgramLine_No
	 * @param mProgram_Line
	 * @param mProgram_Line_Description
	 * @param mProgram_Line_Html
	 */
	public Program_Table(int mProgramTableIndex, int mProgramLine_No,
			String mProgram_Line, String mProgram_Line_Description,
			String mProgram_Line_Html) {
		super();
		this.mProgramTableIndex = mProgramTableIndex;
		this.mProgramLine_No = mProgramLine_No;
		this.mProgram_Line = mProgram_Line;
		this.mProgram_Line_Description = mProgram_Line_Description;
		this.mProgram_Line_Html = mProgram_Line_Html;
	}


	/**
	 * Default Constructor 
	 */
	public Program_Table() {
		super();
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return mProgramTableIndex;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		mProgramTableIndex = index;
	}
	/**
	 * @return the line_No
	 */
	public int getLine_No() {
		return mProgramLine_No;
	}
	/**
	 * @param line_No the line_No to set
	 */
	public void setLine_No(int line_No) {
		mProgramLine_No = line_No;
	}
	/**
	 * @return the program_Line
	 */
	public String getProgram_Line() {
		return mProgram_Line;
	}
	/**
	 * @param program_Line the program_Line to set
	 */
	public void setProgram_Line(String program_Line) {
		mProgram_Line = program_Line;
	}
	/**
	 * @return the program_Line_Description
	 */
	public String getProgram_Line_Description() {
		return mProgram_Line_Description;
	}
	/**
	 * @param program_Line_Description the program_Line_Description to set
	 */
	public void setProgram_Line_Description(String program_Line_Description) {
		mProgram_Line_Description = program_Line_Description;
	}

	public String getmProgram_Line_Html() {
		return mProgram_Line_Html;
	}

	public void setmProgram_Line_Html(String mProgram_Line_Html) {
		this.mProgram_Line_Html = mProgram_Line_Html;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Program_Table that = (Program_Table) o;

		return mProgramLine_No == that.mProgramLine_No;

	}

	@Override
	public int hashCode() {
		return mProgramLine_No;
	}
}

package com.sortedqueue.programmercreek.database;


import com.google.firebase.database.IgnoreExtraProperties;
import com.sortedqueue.programmercreek.util.PrettifyHighlighter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
@IgnoreExtraProperties
public class Program_Table {

    //DataSnapshot { key = 1, value = {5={
    // program_Line=	getch();,
    // line_No=5,
    // index=1,
    // mProgram_Line_Html=<font color="#000000">	getch</font><font color="#ff4500">();</font>, program_Line_Description=Wait for keyboard input}, 4={
    // program_Line=	printf("HelloWorld");, line_No=4, index=1, mProgram_Line_Html=<font color="#000000">	printf</font><font color="#ff4500">(</font><font color="#ff4500">"HelloWorld"</font><font color="#ff4500">);</font>, program_Line_Description=Print Statement}, 1={program_Line=#include "stdio.h", line_No=1, index=1, mProgram_Line_Html=<font color="#6B6B00">#include</font><font color="#000000"> </font><font color="#ff4500">"stdio.h"</font>, program_Line_Description=Header include}, 3={program_Line={, line_No=3, index=1, mProgram_Line_Html=<font color="#ff4500">{</font>, program_Line_Description=Start}, 6={program_Line=}, line_No=6, index=1, mProgram_Line_Html=<font color="#ff4500">}</font>, program_Line_Description=Finish}, 2={program_Line=void main(), line_No=2, index=1, mProgram_Line_Html=<font color="#006600">void</font><font color="#000000"> main</font><font color="#ff4500">()</font>, program_Line_Description=Main Declaration}} }
    public static final String COL_INDEX = "index";
    public static final String COL_LINE_NO = "line_No";
    public static final String COL_LINE_HTML = "mProgram_Line_Html";
    public static final String COL_LINE = "program_Line";
    public static final String COL_LINE_DESCRIPTION = "program_Line_Description";

    int mProgramTableIndex;
    int mProgramLine_No;
    String mProgram_Line;
    String mProgram_Line_Description;
    String mProgram_Line_Html;
    String mProgram_Language;

    /**
     * @param index
     * @param line_No
     * @param program_Line
     * @param program_Line_Description
     */
    public Program_Table(int index, int line_No, String program_Line,
                         String program_Line_Description, String mProgram_Language) {
        super();
        mProgramTableIndex = index;
        mProgramLine_No = line_No;
        mProgram_Line = program_Line;
        mProgram_Line_Description = program_Line_Description;
        mProgram_Line_Html = PrettifyHighlighter.getInstance().highlight("c++", mProgram_Line);
        this.mProgram_Language = mProgram_Language;
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

    public String getmProgram_Language() {
        return mProgram_Language;
    }

    public void setmProgram_Language(String mProgram_Language) {
        this.mProgram_Language = mProgram_Language;
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

    public interface FillBlanksSolutionListener {
        void getSolution( ArrayList<Integer> fillBlanksIndex );
    }

    public static ArrayList<String> getFillTheBlanksList(List<Program_Table> program_tableList, FillBlanksSolutionListener fillBlanksSolutionListener) {
        ArrayList<String> fillBlanksQuestionList = new ArrayList<>();
        ArrayList<Integer> fillBlanksIndex = new ArrayList<>();
        for( Program_Table program_table : program_tableList ) {
            fillBlanksQuestionList.add(program_table.getProgram_Line().trim());
        }
        for( int i = 0; i < 4; i++ ) {
            int randomIndex = getRandomNumberInRange(0, fillBlanksQuestionList.size() - 1);
            Program_Table program_table = program_tableList.get(randomIndex);
            if( !program_table.getProgram_Line().trim().equals("{") &&
                    !program_table.getProgram_Line().trim().equals("}") ) {
                if( fillBlanksQuestionList.get(randomIndex).equals("") ) {
                    //Line already cleared
                    i--;
                }
                else {
                    fillBlanksIndex.add(program_table.getLine_No() - 1);
                    fillBlanksQuestionList.set(randomIndex, "");
                }
            }
            else i--;

        }
        Collections.sort(fillBlanksIndex, new Comparator<Integer>() {
            @Override
            public int compare(Integer int1, Integer int2) {
                return int1 < int2 ? -1 : int1 == int2 ? 0 : 1;
            }
        });
        fillBlanksSolutionListener.getSolution(fillBlanksIndex);
        return fillBlanksQuestionList;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}

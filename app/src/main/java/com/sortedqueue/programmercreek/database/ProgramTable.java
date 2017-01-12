package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.sortedqueue.programmercreek.util.PrettifyHighlighter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushTableAnnotation;

/**
 * Created by Alok on 04/01/17.
 */
@RushTableAnnotation
public class ProgramTable extends RushObject implements Parcelable {

    private int program_index;
    private int line_No;
    private String program_Language;
    private String program_Line;
    private String program_Line_Description;
    private String program_Line_Html;

    public ProgramTable() {
    }

    public ProgramTable(int program_index, int line_No, String program_Language, String program_Line, String program_Line_Description) {
        this.program_index = program_index;
        this.line_No = line_No;
        this.program_Language = program_Language;
        this.program_Line = program_Line;
        this.program_Line_Description = program_Line_Description;
        this.program_Line_Html = PrettifyHighlighter.getInstance().highlight("c++", program_Line);
    }

    public String getProgram_Line_Html() {
        return program_Line_Html;
    }

    public void setProgram_Line_Html(String program_Line_Html) {
        this.program_Line_Html = program_Line_Html;
    }

    public int getProgram_index() {
        return program_index;
    }

    public void setProgram_index(int program_index) {
        this.program_index = program_index;
    }

    public int getLine_No() {
        return line_No;
    }

    public void setLine_No(int line_No) {
        this.line_No = line_No;
    }

    public String getProgram_Language() {
        return program_Language;
    }

    public void setProgram_Language(String program_Language) {
        this.program_Language = program_Language;
    }

    public String getProgram_Line() {
        return program_Line;
    }

    public void setProgram_Line(String program_Line) {
        this.program_Line = program_Line;
    }

    public String getProgram_Line_Description() {
        return program_Line_Description;
    }

    public void setProgram_Line_Description(String program_Line_Description) {
        this.program_Line_Description = program_Line_Description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProgramTable that = (ProgramTable) o;

        if (program_index != that.program_index) return false;
        if (line_No != that.line_No) return false;
        if (program_Language != null ? !program_Language.equals(that.program_Language) : that.program_Language != null)
            return false;
        if (program_Line != null ? !program_Line.equals(that.program_Line) : that.program_Line != null)
            return false;
        return program_Line_Description != null ? program_Line_Description.equals(that.program_Line_Description) : that.program_Line_Description == null;

    }

    @Override
    public int hashCode() {
        int result = program_index;
        result = 31 * result + line_No;
        result = 31 * result + (program_Language != null ? program_Language.hashCode() : 0);
        result = 31 * result + (program_Line != null ? program_Line.hashCode() : 0);
        result = 31 * result + (program_Line_Description != null ? program_Line_Description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProgramTable{" +
                "program_index=" + program_index +
                ", line_No=" + line_No +
                ", program_Language='" + program_Language + '\'' +
                ", program_Line='" + program_Line + '\'' +
                ", program_Line_Description='" + program_Line_Description + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.program_index);
        dest.writeInt(this.line_No);
        dest.writeString(this.program_Language);
        dest.writeString(this.program_Line);
        dest.writeString(this.program_Line_Description);
    }

    protected ProgramTable(Parcel in) {
        this.program_index = in.readInt();
        this.line_No = in.readInt();
        this.program_Language = in.readString();
        this.program_Line = in.readString();
        this.program_Line_Description = in.readString();
    }

    public static final Parcelable.Creator<ProgramTable> CREATOR = new Parcelable.Creator<ProgramTable>() {
        @Override
        public ProgramTable createFromParcel(Parcel source) {
            return new ProgramTable(source);
        }

        @Override
        public ProgramTable[] newArray(int size) {
            return new ProgramTable[size];
        }
    };

    public interface FillBlanksSolutionListener {
        void getSolution( ArrayList<Integer> fillBlanksIndex );
    }

    public static ArrayList<String> getFillTheBlanksList(List<ProgramTable> program_tableList, FillBlanksSolutionListener fillBlanksSolutionListener) {
        ArrayList<String> fillBlanksQuestionList = new ArrayList<>();
        ArrayList<Integer> fillBlanksIndex = new ArrayList<>();
        for( ProgramTable program_table : program_tableList ) {
            fillBlanksQuestionList.add(program_table.getProgram_Line().trim());
        }
        for( int i = 0; i < 4; i++ ) {
            int randomIndex = getRandomNumberInRange(0, fillBlanksQuestionList.size() - 1);
            ProgramTable program_table = program_tableList.get(randomIndex);
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

package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushTableAnnotation;

/**
 * Created by Alok Omkar on 2016-12-31.
 */
@RushTableAnnotation
public class ProgramWiki extends RushObject implements Parcelable {


    public final static int CONTENT_HEADER = 1;
    public final static int CONTENT_PROGRAM = 2;
    public final static int CONTENT_PROGRAM_EXPLANATION = 3;
    public final static int CONTENT_OUTPUT = 4;

    private String header;
    private int contentType;
    private String programExplanation;
    private String programExample;
    private String output;

    public ProgramWiki(int contentType, String header, String programExplanation, String programExample, String output) {
        this.contentType = contentType;
        this.header = header;
        this.programExplanation = programExplanation;
        this.programExample = programExample;
        this.output = output;
    }

    public ProgramWiki() {
    }



    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getProgramExplanation() {
        return programExplanation;
    }

    public void setProgramExplanation(String programExplanation) {
        this.programExplanation = programExplanation;
    }

    public String getProgramExample() {
        return programExample;
    }

    public void setProgramExample(String programExample) {
        this.programExample = programExample;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "ProgramWiki{" +
                "header='" + header + '\'' +
                ", contentType=" + contentType +
                ", programExplanation='" + programExplanation + '\'' +
                ", programExample='" + programExample + '\'' +
                ", output='" + output + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProgramWiki that = (ProgramWiki) o;

        if (contentType != that.contentType) return false;
        if (header != null ? !header.equals(that.header) : that.header != null) return false;
        if (programExplanation != null ? !programExplanation.equals(that.programExplanation) : that.programExplanation != null)
            return false;
        if (programExample != null ? !programExample.equals(that.programExample) : that.programExample != null)
            return false;
        return output != null ? output.equals(that.output) : that.output == null;

    }

    @Override
    public int hashCode() {
        int result = header != null ? header.hashCode() : 0;
        result = 31 * result + contentType;
        result = 31 * result + (programExplanation != null ? programExplanation.hashCode() : 0);
        result = 31 * result + (programExample != null ? programExample.hashCode() : 0);
        result = 31 * result + (output != null ? output.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.header);
        dest.writeInt(this.contentType);
        dest.writeString(this.programExplanation);
        dest.writeString(this.programExample);
        dest.writeString(this.output);
    }

    protected ProgramWiki(Parcel in) {
        this.header = in.readString();
        this.contentType = in.readInt();
        this.programExplanation = in.readString();
        this.programExample = in.readString();
        this.output = in.readString();
    }

    public static final Parcelable.Creator<ProgramWiki> CREATOR = new Parcelable.Creator<ProgramWiki>() {
        @Override
        public ProgramWiki createFromParcel(Parcel source) {
            return new ProgramWiki(source);
        }

        @Override
        public ProgramWiki[] newArray(int size) {
            return new ProgramWiki[size];
        }
    };
}

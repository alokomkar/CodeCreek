package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Program_Index - POJO for Programer_Index table in database.
 * */
@IgnoreExtraProperties
public class Program_Index implements Parcelable {

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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.mProgramIndex);
		dest.writeString(this.mProgram_Description);
		dest.writeString(this.wiki);
		dest.writeString(this.mProgram_Language);
	}

	protected Program_Index(Parcel in) {
		this.mProgramIndex = in.readInt();
		this.mProgram_Description = in.readString();
		this.wiki = in.readString();
		this.mProgram_Language = in.readString();
	}

	public static final Parcelable.Creator<Program_Index> CREATOR = new Parcelable.Creator<Program_Index>() {
		@Override
		public Program_Index createFromParcel(Parcel source) {
			return new Program_Index(source);
		}

		@Override
		public Program_Index[] newArray(int size) {
			return new Program_Index[size];
		}
	};
}

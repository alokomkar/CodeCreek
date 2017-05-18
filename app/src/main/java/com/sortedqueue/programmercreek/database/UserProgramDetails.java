package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushTableAnnotation;

/**
 * Created by binay on 05/12/16.
 */
@RushTableAnnotation
public class UserProgramDetails extends RushObject implements Parcelable {

    private String emailId;
    private String programLanguage;
    private String programId;
    private String accessSpecifier;
    private String md5;

    private String programTitle; //All lower case
    private ProgramIndex programIndex;
    private ArrayList<ProgramTable> programTables;
    private ArrayList<String> likesList;
    private int likes = 0;
    private int views = 0;
    private String preview;

    public UserProgramDetails() {
    }

    public UserProgramDetails(String emailId, String programLanguage, String programId) {
        this.emailId = emailId;
        this.programLanguage = programLanguage;
        this.programId = programId;
    }


    public String getAccessSpecifier() {
        return accessSpecifier;
    }

    public void setAccessSpecifier(String accessSpecifier) {
        this.accessSpecifier = accessSpecifier;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    public ProgramIndex getProgramIndex() {
        return programIndex;
    }

    public void setProgramIndex(ProgramIndex programIndex) {
        this.programIndex = programIndex;
    }

    public ArrayList<ProgramTable> getProgramTables() {
        return programTables;
    }

    public void setProgramTables(ArrayList<ProgramTable> programTables) {
        this.programTables = programTables;
    }

    public ArrayList<String> getLikesList() {
        if( likesList == null ) {
            likesList = new ArrayList<>();
        }
        return likesList;
    }

    public void setLikesList(ArrayList<String> likesList) {
        this.likesList = likesList;
    }

    public int getLikes() {
        if( likesList == null ) {
            likesList = getLikesList();
        }
        likes = likesList.size();
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getProgramLanguage() {
        return programLanguage;
    }

    public void setProgramLanguage(String programLanguage) {
        this.programLanguage = programLanguage;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "UserProgramDetails{" +
                "programLanguage='" + programLanguage + '\'' +
                ", programId='" + programId + '\'' +
                '}';
    }

    public String getPreview() {
        if( preview == null ) {
            preview = "Preview : \n";
            for( int i = 0; i < 3; i++ ) {
                ProgramTable programTable = programTables.get(i);
                if( i != 2 )
                preview += programTable.getProgram_Line() + "\n";
                else preview += programTable.getProgram_Line();
            }
        }
        return preview;
    }

    private boolean isLiked;
    public boolean isLiked(String userEmail) {
        if( likesList == null ) {
            likesList = getLikesList();
        }
        isLiked = likesList.contains(userEmail);
        return isLiked;
    }

    public void setLiked( boolean isLiked, String userEmail ) {
        this.isLiked = isLiked;
        if( likesList == null ) {
            likesList = getLikesList();
        }
        if( !isLiked ) {
            likesList.remove( userEmail );
        }
        else {
            if( !likesList.contains(userEmail) )
            likesList.add(userEmail);
        }
    }

    public String getShareString() {

        String shareString = "";
        String explanationString = "";
        for( ProgramTable programTable : getProgramTables() ) {
            shareString += programTable.getProgram_Line() + "\n";
            explanationString += programTable.getProgram_Line_Description() + "\n";
        }

        return shareString + "\nExplanation : \n" + explanationString;

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.emailId);
        dest.writeString(this.programLanguage);
        dest.writeString(this.programId);
        dest.writeString(this.accessSpecifier);
        dest.writeString(this.md5);
        dest.writeString(this.programTitle);
        dest.writeParcelable(this.programIndex, flags);
        dest.writeTypedList(this.programTables);
        dest.writeStringList(this.likesList);
        dest.writeInt(this.likes);
        dest.writeInt(this.views);
        dest.writeString(this.preview);
        dest.writeByte(this.isLiked ? (byte) 1 : (byte) 0);
    }

    protected UserProgramDetails(Parcel in) {
        this.emailId = in.readString();
        this.programLanguage = in.readString();
        this.programId = in.readString();
        this.accessSpecifier = in.readString();
        this.md5 = in.readString();
        this.programTitle = in.readString();
        this.programIndex = in.readParcelable(ProgramIndex.class.getClassLoader());
        this.programTables = in.createTypedArrayList(ProgramTable.CREATOR);
        this.likesList = in.createStringArrayList();
        this.likes = in.readInt();
        this.views = in.readInt();
        this.preview = in.readString();
        this.isLiked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<UserProgramDetails> CREATOR = new Parcelable.Creator<UserProgramDetails>() {
        @Override
        public UserProgramDetails createFromParcel(Parcel source) {
            return new UserProgramDetails(source);
        }

        @Override
        public UserProgramDetails[] newArray(int size) {
            return new UserProgramDetails[size];
        }
    };
}

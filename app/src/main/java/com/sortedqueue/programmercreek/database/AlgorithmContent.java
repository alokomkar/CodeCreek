package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;


import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushTableAnnotation;

import static com.sortedqueue.programmercreek.constants.AlgorithmConstantsKt.CONTENT_ALGORITHM;
import static com.sortedqueue.programmercreek.constants.AlgorithmConstantsKt.CONTENT_OUTPUT;

/**
 * Created by Alok Omkar on 2017-03-15.
 */
@RushTableAnnotation
public class AlgorithmContent extends RushObject implements Parcelable {

    private int contentType;
    private String tabTitle;
    private String aim;
    private String programDescription;
    private String output;
    private String input;
    private String programCode;
    private String algorithmPseudoCode;
    private String contentId;

    public AlgorithmContent(int contentType, String programDescription, String output, String input, String programCode, String algorithmPseudoCode) {
        this.contentType = contentType;
        this.programDescription = programDescription;
        this.output = output;
        this.input = input;
        this.programCode = programCode;
        this.algorithmPseudoCode = algorithmPseudoCode;
    }

    public AlgorithmContent(int contentType, String tabTitle, String programDescription, String output, String input, String programCode, String algorithmPseudoCode) {
        this.contentType = contentType;
        this.tabTitle = tabTitle;
        this.programDescription = programDescription;
        this.output = output;
        this.input = input;
        this.programCode = programCode;
        this.algorithmPseudoCode = algorithmPseudoCode;
    }

    public AlgorithmContent(int contentType, String tabTitle, String aim, String programDescription) {
        this.contentType = contentType;
        this.tabTitle = tabTitle;
        this.aim = aim;
        this.programDescription = programDescription;
    }

    public AlgorithmContent(int contentType, String tabTitle, String algorithmPseudoCode) {
        this.contentType = contentType;
        this.tabTitle = tabTitle;
        if( contentType == CONTENT_ALGORITHM) {
            this.algorithmPseudoCode = algorithmPseudoCode;
        }
        else if( contentType == CONTENT_OUTPUT) {
            this.output = algorithmPseudoCode;
        }
        else {
            this.programCode = algorithmPseudoCode;
        }
    }

    public AlgorithmContent() {
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getAlgorithmPseudoCode() {
        return algorithmPseudoCode;
    }

    public void setAlgorithmPseudoCode(String algorithmPseudoCode) {
        this.algorithmPseudoCode = algorithmPseudoCode;
    }

    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlgorithmContent that = (AlgorithmContent) o;

        if (contentType != that.contentType) return false;
        if (tabTitle != null ? !tabTitle.equals(that.tabTitle) : that.tabTitle != null)
            return false;
        if (aim != null ? !aim.equals(that.aim) : that.aim != null) return false;
        if (programDescription != null ? !programDescription.equals(that.programDescription) : that.programDescription != null)
            return false;
        if (output != null ? !output.equals(that.output) : that.output != null) return false;
        if (input != null ? !input.equals(that.input) : that.input != null) return false;
        if (programCode != null ? !programCode.equals(that.programCode) : that.programCode != null)
            return false;
        if (algorithmPseudoCode != null ? !algorithmPseudoCode.equals(that.algorithmPseudoCode) : that.algorithmPseudoCode != null)
            return false;
        return contentId != null ? contentId.equals(that.contentId) : that.contentId == null;
    }

    @Override
    public int hashCode() {
        int result = contentType;
        result = 31 * result + (tabTitle != null ? tabTitle.hashCode() : 0);
        result = 31 * result + (aim != null ? aim.hashCode() : 0);
        result = 31 * result + (programDescription != null ? programDescription.hashCode() : 0);
        result = 31 * result + (output != null ? output.hashCode() : 0);
        result = 31 * result + (input != null ? input.hashCode() : 0);
        result = 31 * result + (programCode != null ? programCode.hashCode() : 0);
        result = 31 * result + (algorithmPseudoCode != null ? algorithmPseudoCode.hashCode() : 0);
        result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.contentType);
        dest.writeString(this.tabTitle);
        dest.writeString(this.aim);
        dest.writeString(this.programDescription);
        dest.writeString(this.output);
        dest.writeString(this.input);
        dest.writeString(this.programCode);
        dest.writeString(this.algorithmPseudoCode);
        dest.writeString(this.contentId);
    }

    protected AlgorithmContent(Parcel in) {
        this.contentType = in.readInt();
        this.tabTitle = in.readString();
        this.aim = in.readString();
        this.programDescription = in.readString();
        this.output = in.readString();
        this.input = in.readString();
        this.programCode = in.readString();
        this.algorithmPseudoCode = in.readString();
        this.contentId = in.readString();
    }

    public static final Creator<AlgorithmContent> CREATOR = new Creator<AlgorithmContent>() {
        @Override
        public AlgorithmContent createFromParcel(Parcel source) {
            return new AlgorithmContent(source);
        }

        @Override
        public AlgorithmContent[] newArray(int size) {
            return new AlgorithmContent[size];
        }
    };
}

package com.sortedqueue.programmercreek.database;

/**
 * Created by Alok Omkar on 2017-01-01.
 */

public class CreekUserStats {

    private int javaProgressIndex = 0;
    private int cProgressIndex = 0;
    private int cppProgressIndex = 0;

    private int unlockedCProgramIndex = 1;
    private int unlockedCppProgramIndex = 1;
    private int unlockedJavaProgramIndex = 1;

    private String unlockedCLanguageModuleId = "c_1";
    private String unlockedCppLanguageModuleId = "cpp_1";
    private String unlockedJavaLanguageModuleId = "java_1";

    private String unlockedCSyntaxModuleId = "c_1_s_1";
    private String unlockedCppSyntaxModuleId = "cpp_1_s_1";
    private String unlockedJavaSyntaxModuleId = "java_1_s_1";

    private String unlockedCWikiId = "c1";
    private String unlockedCppWikiId = "cpp1";
    private String unlockedJavaWikiId = "java1";

    public CreekUserStats(int javaProgressIndex, int cProgressIndex, int cppProgressIndex) {
        this.javaProgressIndex = javaProgressIndex;
        this.cProgressIndex = cProgressIndex;
        this.cppProgressIndex = cppProgressIndex;
    }

    public CreekUserStats() {
    }

    public int getJavaProgressIndex() {
        return javaProgressIndex;
    }

    public void setJavaProgressIndex(int javaProgressIndex) {
        this.javaProgressIndex = javaProgressIndex;
    }

    public int getcProgressIndex() {
        return cProgressIndex;
    }

    public void setcProgressIndex(int cProgressIndex) {
        this.cProgressIndex = cProgressIndex;
    }

    public int getCppProgressIndex() {
        return cppProgressIndex;
    }

    public void setCppProgressIndex(int cppProgressIndex) {
        this.cppProgressIndex = cppProgressIndex;
    }

    public int getUnlockedCProgramIndex() {
        return unlockedCProgramIndex;
    }

    public void setUnlockedCProgramIndex(int unlockedCProgramIndex) {
        this.unlockedCProgramIndex = unlockedCProgramIndex;
    }

    public int getUnlockedCppProgramIndex() {
        return unlockedCppProgramIndex;
    }

    public void setUnlockedCppProgramIndex(int unlockedCppProgramIndex) {
        this.unlockedCppProgramIndex = unlockedCppProgramIndex;
    }

    public int getUnlockedJavaProgramIndex() {
        return unlockedJavaProgramIndex;
    }

    public void setUnlockedJavaProgramIndex(int unlockedJavaProgramIndex) {
        this.unlockedJavaProgramIndex = unlockedJavaProgramIndex;
    }

    public String getUnlockedCLanguageModuleId() {
        return unlockedCLanguageModuleId;
    }

    public void setUnlockedCLanguageModuleId(String unlockedCLanguageModuleId) {
        this.unlockedCLanguageModuleId = unlockedCLanguageModuleId;
    }

    public String getUnlockedCppLanguageModuleId() {
        return unlockedCppLanguageModuleId;
    }

    public void setUnlockedCppLanguageModuleId(String unlockedCppLanguageModuleId) {
        this.unlockedCppLanguageModuleId = unlockedCppLanguageModuleId;
    }

    public String getUnlockedJavaLanguageModuleId() {
        return unlockedJavaLanguageModuleId;
    }

    public void setUnlockedJavaLanguageModuleId(String unlockedJavaLanguageModuleId) {
        this.unlockedJavaLanguageModuleId = unlockedJavaLanguageModuleId;
    }

    public String getUnlockedCSyntaxModuleId() {
        return unlockedCSyntaxModuleId;
    }

    public void setUnlockedCSyntaxModuleId(String unlockedCSyntaxModuleId) {
        this.unlockedCSyntaxModuleId = unlockedCSyntaxModuleId;
    }

    public String getUnlockedCppSyntaxModuleId() {
        return unlockedCppSyntaxModuleId;
    }

    public void setUnlockedCppSyntaxModuleId(String unlockedCppSyntaxModuleId) {
        this.unlockedCppSyntaxModuleId = unlockedCppSyntaxModuleId;
    }

    public String getUnlockedJavaSyntaxModuleId() {
        return unlockedJavaSyntaxModuleId;
    }

    public void setUnlockedJavaSyntaxModuleId(String unlockedJavaSyntaxModuleId) {
        this.unlockedJavaSyntaxModuleId = unlockedJavaSyntaxModuleId;
    }

    public String getUnlockedCWikiId() {
        return unlockedCWikiId;
    }

    public void setUnlockedCWikiId(String unlockedCWikiId) {
        this.unlockedCWikiId = unlockedCWikiId;
    }

    public String getUnlockedCppWikiId() {
        return unlockedCppWikiId;
    }

    public void setUnlockedCppWikiId(String unlockedCppWikiId) {
        this.unlockedCppWikiId = unlockedCppWikiId;
    }

    public String getUnlockedJavaWikiId() {
        return unlockedJavaWikiId;
    }

    public void setUnlockedJavaWikiId(String unlockedJavaWikiId) {
        this.unlockedJavaWikiId = unlockedJavaWikiId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreekUserStats that = (CreekUserStats) o;

        if (javaProgressIndex != that.javaProgressIndex) return false;
        if (cProgressIndex != that.cProgressIndex) return false;
        if (cppProgressIndex != that.cppProgressIndex) return false;
        if (unlockedCProgramIndex != that.unlockedCProgramIndex) return false;
        if (unlockedCppProgramIndex != that.unlockedCppProgramIndex) return false;
        if (unlockedJavaProgramIndex != that.unlockedJavaProgramIndex) return false;
        if (unlockedCLanguageModuleId != null ? !unlockedCLanguageModuleId.equals(that.unlockedCLanguageModuleId) : that.unlockedCLanguageModuleId != null)
            return false;
        if (unlockedCppLanguageModuleId != null ? !unlockedCppLanguageModuleId.equals(that.unlockedCppLanguageModuleId) : that.unlockedCppLanguageModuleId != null)
            return false;
        if (unlockedJavaLanguageModuleId != null ? !unlockedJavaLanguageModuleId.equals(that.unlockedJavaLanguageModuleId) : that.unlockedJavaLanguageModuleId != null)
            return false;
        if (unlockedCSyntaxModuleId != null ? !unlockedCSyntaxModuleId.equals(that.unlockedCSyntaxModuleId) : that.unlockedCSyntaxModuleId != null)
            return false;
        if (unlockedCppSyntaxModuleId != null ? !unlockedCppSyntaxModuleId.equals(that.unlockedCppSyntaxModuleId) : that.unlockedCppSyntaxModuleId != null)
            return false;
        if (unlockedJavaSyntaxModuleId != null ? !unlockedJavaSyntaxModuleId.equals(that.unlockedJavaSyntaxModuleId) : that.unlockedJavaSyntaxModuleId != null)
            return false;
        if (unlockedCWikiId != null ? !unlockedCWikiId.equals(that.unlockedCWikiId) : that.unlockedCWikiId != null)
            return false;
        if (unlockedCppWikiId != null ? !unlockedCppWikiId.equals(that.unlockedCppWikiId) : that.unlockedCppWikiId != null)
            return false;
        return unlockedJavaWikiId != null ? unlockedJavaWikiId.equals(that.unlockedJavaWikiId) : that.unlockedJavaWikiId == null;

    }

    @Override
    public int hashCode() {
        int result = javaProgressIndex;
        result = 31 * result + cProgressIndex;
        result = 31 * result + cppProgressIndex;
        result = 31 * result + unlockedCProgramIndex;
        result = 31 * result + unlockedCppProgramIndex;
        result = 31 * result + unlockedJavaProgramIndex;
        result = 31 * result + (unlockedCLanguageModuleId != null ? unlockedCLanguageModuleId.hashCode() : 0);
        result = 31 * result + (unlockedCppLanguageModuleId != null ? unlockedCppLanguageModuleId.hashCode() : 0);
        result = 31 * result + (unlockedJavaLanguageModuleId != null ? unlockedJavaLanguageModuleId.hashCode() : 0);
        result = 31 * result + (unlockedCSyntaxModuleId != null ? unlockedCSyntaxModuleId.hashCode() : 0);
        result = 31 * result + (unlockedCppSyntaxModuleId != null ? unlockedCppSyntaxModuleId.hashCode() : 0);
        result = 31 * result + (unlockedJavaSyntaxModuleId != null ? unlockedJavaSyntaxModuleId.hashCode() : 0);
        result = 31 * result + (unlockedCWikiId != null ? unlockedCWikiId.hashCode() : 0);
        result = 31 * result + (unlockedCppWikiId != null ? unlockedCppWikiId.hashCode() : 0);
        result = 31 * result + (unlockedJavaWikiId != null ? unlockedJavaWikiId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CreekUserStats{" +
                "javaProgressIndex=" + javaProgressIndex +
                ", cProgressIndex=" + cProgressIndex +
                ", cppProgressIndex=" + cppProgressIndex +
                ", unlockedCProgramIndex=" + unlockedCProgramIndex +
                ", unlockedCppProgramIndex=" + unlockedCppProgramIndex +
                ", unlockedJavaProgramIndex=" + unlockedJavaProgramIndex +
                ", unlockedCLanguageModuleId='" + unlockedCLanguageModuleId + '\'' +
                ", unlockedCppLanguageModuleId='" + unlockedCppLanguageModuleId + '\'' +
                ", unlockedJavaLanguageModuleId='" + unlockedJavaLanguageModuleId + '\'' +
                ", unlockedCSyntaxModuleId='" + unlockedCSyntaxModuleId + '\'' +
                ", unlockedCppSyntaxModuleId='" + unlockedCppSyntaxModuleId + '\'' +
                ", unlockedJavaSyntaxModuleId='" + unlockedJavaSyntaxModuleId + '\'' +
                ", unlockedCWikiId='" + unlockedCWikiId + '\'' +
                ", unlockedCppWikiId='" + unlockedCppWikiId + '\'' +
                ", unlockedJavaWikiId='" + unlockedJavaWikiId + '\'' +
                '}';
    }
}

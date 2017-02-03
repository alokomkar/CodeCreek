package com.sortedqueue.programmercreek.database;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushTableAnnotation;

/**
 * Created by Alok Omkar on 2016-12-26.
 */
@RushTableAnnotation
public class CreekUserDB extends RushObject {

    //Free version database versions
    private String javaSyntaxDBVersion;
    private String javaModuleDBVersion;
    private String javaWikiDBVersion;
    private double javaProgramIndexDBVersion = 1.0;
    private double javaProgramTableDBVersion = 1.0;

    //Free version database versions
    private String uspSyntaxDBVersion;
    private String uspModuleDBVersion;
    private String uspWikiDBVersion;
    private double uspProgramIndexDBVersion = 1.0;
    private double uspProgramTableDBVersion = 1.0;

    private String cSyntaxDBVersion;
    private String cModuleDBVersion;
    private String cWikiDBVersion;
    private double cProgramIndexDBVersion = 1.0;
    private double cProgramTableDBVersion = 1.0;

    private String cppSyntaxDBVersion;
    private String cppModuleDBVersion;
    private String cppWikiDBVersion;
    private double cppProgramIndexDBVersion = 1.0;
    private double cppProgramTableDBVersion = 1.0;

    private String sqlSyntaxDBVersion = "sql_7_s_4";
    private String sqlModuleDBVersion = "sql_7";
    private String sqlWikiDBVersion;
    private double sqlProgramIndexDBVersion = 13.0;
    private double sqlProgramTableDBVersion = 13.0;

    //Premium version database versions
    private String javaSyntaxDBVersionPremium;
    private String javaModuleDBVersionPremium;
    private String javaWikiDBVersionPremium;
    private double javaProgramIndexDBVersionPremium = 1.0;
    private double javaProgramTableDBVersionPremium = 1.0;

    private String cSyntaxDBVersionPremium;
    private String cModuleDBVersionPremium;
    private String cWikiDBVersionPremium;
    private double cProgramIndexDBVersionPremium = 1.0;
    private double cProgramTableDBVersionPremium = 1.0;

    private String cppSyntaxDBVersionPremium;
    private String cppModuleDBVersionPremium;
    private String cppWikiDBVersionPremium;
    private double cppProgramIndexDBVersionPremium = 1.0;
    private double cppProgramTableDBVersionPremium = 1.0;

    public CreekUserDB() {
    }

    public String getJavaSyntaxDBVersion() {
        return javaSyntaxDBVersion;
    }

    public void setJavaSyntaxDBVersion(String javaSyntaxDBVersion) {
        this.javaSyntaxDBVersion = javaSyntaxDBVersion;
    }

    public double getSqlProgramIndexDBVersion() {
        return sqlProgramIndexDBVersion;
    }

    public void setSqlProgramIndexDBVersion(double sqlProgramIndexDBVersion) {
        this.sqlProgramIndexDBVersion = sqlProgramIndexDBVersion;
    }

    public double getSqlProgramTableDBVersion() {
        return sqlProgramTableDBVersion;
    }

    public void setSqlProgramTableDBVersion(double sqlProgramTableDBVersion) {
        this.sqlProgramTableDBVersion = sqlProgramTableDBVersion;
    }

    public String getJavaModuleDBVersion() {
        return javaModuleDBVersion;
    }

    public String getUspSyntaxDBVersion() {
        return uspSyntaxDBVersion;
    }

    public void setUspSyntaxDBVersion(String uspSyntaxDBVersion) {
        this.uspSyntaxDBVersion = uspSyntaxDBVersion;
    }

    public String getUspModuleDBVersion() {
        return uspModuleDBVersion;
    }

    public void setUspModuleDBVersion(String uspModuleDBVersion) {
        this.uspModuleDBVersion = uspModuleDBVersion;
    }

    public String getUspWikiDBVersion() {
        return uspWikiDBVersion;
    }

    public void setUspWikiDBVersion(String uspWikiDBVersion) {
        this.uspWikiDBVersion = uspWikiDBVersion;
    }

    public double getUspProgramIndexDBVersion() {
        return uspProgramIndexDBVersion;
    }

    public void setUspProgramIndexDBVersion(double uspProgramIndexDBVersion) {
        this.uspProgramIndexDBVersion = uspProgramIndexDBVersion;
    }

    public double getUspProgramTableDBVersion() {
        return uspProgramTableDBVersion;
    }

    public void setUspProgramTableDBVersion(double uspProgramTableDBVersion) {
        this.uspProgramTableDBVersion = uspProgramTableDBVersion;
    }

    public void setJavaModuleDBVersion(String javaModuleDBVersion) {
        this.javaModuleDBVersion = javaModuleDBVersion;
    }

    public String getJavaWikiDBVersion() {
        return javaWikiDBVersion;
    }

    public void setJavaWikiDBVersion(String javaWikiDBVersion) {
        this.javaWikiDBVersion = javaWikiDBVersion;
    }

    public double getJavaProgramIndexDBVersion() {
        return javaProgramIndexDBVersion;
    }

    public void setJavaProgramIndexDBVersion(double javaProgramIndexDBVersion) {
        this.javaProgramIndexDBVersion = javaProgramIndexDBVersion;
    }

    public double getJavaProgramTableDBVersion() {
        return javaProgramTableDBVersion;
    }

    public void setJavaProgramTableDBVersion(double javaProgramTableDBVersion) {
        this.javaProgramTableDBVersion = javaProgramTableDBVersion;
    }

    public String getcSyntaxDBVersion() {
        return cSyntaxDBVersion;
    }

    public void setcSyntaxDBVersion(String cSyntaxDBVersion) {
        this.cSyntaxDBVersion = cSyntaxDBVersion;
    }

    public String getcModuleDBVersion() {
        return cModuleDBVersion;
    }

    public void setcModuleDBVersion(String cModuleDBVersion) {
        this.cModuleDBVersion = cModuleDBVersion;
    }

    public String getcWikiDBVersion() {
        return cWikiDBVersion;
    }

    public void setcWikiDBVersion(String cWikiDBVersion) {
        this.cWikiDBVersion = cWikiDBVersion;
    }

    public double getcProgramIndexDBVersion() {
        return cProgramIndexDBVersion;
    }

    public void setcProgramIndexDBVersion(double cProgramIndexDBVersion) {
        this.cProgramIndexDBVersion = cProgramIndexDBVersion;
    }

    public double getcProgramTableDBVersion() {
        return cProgramTableDBVersion;
    }

    public void setcProgramTableDBVersion(double cProgramTableDBVersion) {
        this.cProgramTableDBVersion = cProgramTableDBVersion;
    }

    public String getCppSyntaxDBVersion() {
        return cppSyntaxDBVersion;
    }

    public void setCppSyntaxDBVersion(String cppSyntaxDBVersion) {
        this.cppSyntaxDBVersion = cppSyntaxDBVersion;
    }

    public String getCppModuleDBVersion() {
        return cppModuleDBVersion;
    }

    public void setCppModuleDBVersion(String cppModuleDBVersion) {
        this.cppModuleDBVersion = cppModuleDBVersion;
    }

    public String getCppWikiDBVersion() {
        return cppWikiDBVersion;
    }

    public void setCppWikiDBVersion(String cppWikiDBVersion) {
        this.cppWikiDBVersion = cppWikiDBVersion;
    }

    public double getCppProgramIndexDBVersion() {
        return cppProgramIndexDBVersion;
    }

    public void setCppProgramIndexDBVersion(double cppProgramIndexDBVersion) {
        this.cppProgramIndexDBVersion = cppProgramIndexDBVersion;
    }

    public double getCppProgramTableDBVersion() {
        return cppProgramTableDBVersion;
    }

    public void setCppProgramTableDBVersion(double cppProgramTableDBVersion) {
        this.cppProgramTableDBVersion = cppProgramTableDBVersion;
    }

    public String getJavaSyntaxDBVersionPremium() {
        return javaSyntaxDBVersionPremium;
    }

    public void setJavaSyntaxDBVersionPremium(String javaSyntaxDBVersionPremium) {
        this.javaSyntaxDBVersionPremium = javaSyntaxDBVersionPremium;
    }

    public String getJavaModuleDBVersionPremium() {
        return javaModuleDBVersionPremium;
    }

    public void setJavaModuleDBVersionPremium(String javaModuleDBVersionPremium) {
        this.javaModuleDBVersionPremium = javaModuleDBVersionPremium;
    }

    public String getJavaWikiDBVersionPremium() {
        return javaWikiDBVersionPremium;
    }

    public void setJavaWikiDBVersionPremium(String javaWikiDBVersionPremium) {
        this.javaWikiDBVersionPremium = javaWikiDBVersionPremium;
    }

    public double getJavaProgramIndexDBVersionPremium() {
        return javaProgramIndexDBVersionPremium;
    }

    public void setJavaProgramIndexDBVersionPremium(double javaProgramIndexDBVersionPremium) {
        this.javaProgramIndexDBVersionPremium = javaProgramIndexDBVersionPremium;
    }

    public double getJavaProgramTableDBVersionPremium() {
        return javaProgramTableDBVersionPremium;
    }

    public void setJavaProgramTableDBVersionPremium(double javaProgramTableDBVersionPremium) {
        this.javaProgramTableDBVersionPremium = javaProgramTableDBVersionPremium;
    }

    public String getcSyntaxDBVersionPremium() {
        return cSyntaxDBVersionPremium;
    }

    public void setcSyntaxDBVersionPremium(String cSyntaxDBVersionPremium) {
        this.cSyntaxDBVersionPremium = cSyntaxDBVersionPremium;
    }

    public String getcModuleDBVersionPremium() {
        return cModuleDBVersionPremium;
    }

    public void setcModuleDBVersionPremium(String cModuleDBVersionPremium) {
        this.cModuleDBVersionPremium = cModuleDBVersionPremium;
    }

    public String getcWikiDBVersionPremium() {
        return cWikiDBVersionPremium;
    }

    public void setcWikiDBVersionPremium(String cWikiDBVersionPremium) {
        this.cWikiDBVersionPremium = cWikiDBVersionPremium;
    }

    public double getcProgramIndexDBVersionPremium() {
        return cProgramIndexDBVersionPremium;
    }

    public void setcProgramIndexDBVersionPremium(double cProgramIndexDBVersionPremium) {
        this.cProgramIndexDBVersionPremium = cProgramIndexDBVersionPremium;
    }

    public double getcProgramTableDBVersionPremium() {
        return cProgramTableDBVersionPremium;
    }

    public void setcProgramTableDBVersionPremium(double cProgramTableDBVersionPremium) {
        this.cProgramTableDBVersionPremium = cProgramTableDBVersionPremium;
    }

    public String getCppSyntaxDBVersionPremium() {
        return cppSyntaxDBVersionPremium;
    }

    public void setCppSyntaxDBVersionPremium(String cppSyntaxDBVersionPremium) {
        this.cppSyntaxDBVersionPremium = cppSyntaxDBVersionPremium;
    }

    public String getCppModuleDBVersionPremium() {
        return cppModuleDBVersionPremium;
    }

    public void setCppModuleDBVersionPremium(String cppModuleDBVersionPremium) {
        this.cppModuleDBVersionPremium = cppModuleDBVersionPremium;
    }

    public String getCppWikiDBVersionPremium() {
        return cppWikiDBVersionPremium;
    }

    public void setCppWikiDBVersionPremium(String cppWikiDBVersionPremium) {
        this.cppWikiDBVersionPremium = cppWikiDBVersionPremium;
    }

    public double getCppProgramIndexDBVersionPremium() {
        return cppProgramIndexDBVersionPremium;
    }

    public void setCppProgramIndexDBVersionPremium(double cppProgramIndexDBVersionPremium) {
        this.cppProgramIndexDBVersionPremium = cppProgramIndexDBVersionPremium;
    }

    public double getCppProgramTableDBVersionPremium() {
        return cppProgramTableDBVersionPremium;
    }

    public void setCppProgramTableDBVersionPremium(double cppProgramTableDBVersionPremium) {
        this.cppProgramTableDBVersionPremium = cppProgramTableDBVersionPremium;
    }

    public String getSqlSyntaxDBVersion() {
        return sqlSyntaxDBVersion;
    }

    public void setSqlSyntaxDBVersion(String sqlSyntaxDBVersion) {
        this.sqlSyntaxDBVersion = sqlSyntaxDBVersion;
    }

    public String getSqlModuleDBVersion() {
        return sqlModuleDBVersion;
    }

    public void setSqlModuleDBVersion(String sqlModuleDBVersion) {
        this.sqlModuleDBVersion = sqlModuleDBVersion;
    }

    public String getSqlWikiDBVersion() {
        return sqlWikiDBVersion;
    }

    public void setSqlWikiDBVersion(String sqlWikiDBVersion) {
        this.sqlWikiDBVersion = sqlWikiDBVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreekUserDB that = (CreekUserDB) o;

        if (Double.compare(that.javaProgramIndexDBVersion, javaProgramIndexDBVersion) != 0)
            return false;
        if (Double.compare(that.javaProgramTableDBVersion, javaProgramTableDBVersion) != 0)
            return false;
        if (Double.compare(that.uspProgramIndexDBVersion, uspProgramIndexDBVersion) != 0)
            return false;
        if (Double.compare(that.uspProgramTableDBVersion, uspProgramTableDBVersion) != 0)
            return false;
        if (Double.compare(that.cProgramIndexDBVersion, cProgramIndexDBVersion) != 0) return false;
        if (Double.compare(that.cProgramTableDBVersion, cProgramTableDBVersion) != 0) return false;
        if (Double.compare(that.cppProgramIndexDBVersion, cppProgramIndexDBVersion) != 0)
            return false;
        if (Double.compare(that.cppProgramTableDBVersion, cppProgramTableDBVersion) != 0)
            return false;
        if (Double.compare(that.sqlProgramIndexDBVersion, sqlProgramIndexDBVersion) != 0)
            return false;
        if (Double.compare(that.sqlProgramTableDBVersion, sqlProgramTableDBVersion) != 0)
            return false;
        if (Double.compare(that.javaProgramIndexDBVersionPremium, javaProgramIndexDBVersionPremium) != 0)
            return false;
        if (Double.compare(that.javaProgramTableDBVersionPremium, javaProgramTableDBVersionPremium) != 0)
            return false;
        if (Double.compare(that.cProgramIndexDBVersionPremium, cProgramIndexDBVersionPremium) != 0)
            return false;
        if (Double.compare(that.cProgramTableDBVersionPremium, cProgramTableDBVersionPremium) != 0)
            return false;
        if (Double.compare(that.cppProgramIndexDBVersionPremium, cppProgramIndexDBVersionPremium) != 0)
            return false;
        if (Double.compare(that.cppProgramTableDBVersionPremium, cppProgramTableDBVersionPremium) != 0)
            return false;
        if (javaSyntaxDBVersion != null ? !javaSyntaxDBVersion.equals(that.javaSyntaxDBVersion) : that.javaSyntaxDBVersion != null)
            return false;
        if (javaModuleDBVersion != null ? !javaModuleDBVersion.equals(that.javaModuleDBVersion) : that.javaModuleDBVersion != null)
            return false;
        if (javaWikiDBVersion != null ? !javaWikiDBVersion.equals(that.javaWikiDBVersion) : that.javaWikiDBVersion != null)
            return false;
        if (uspSyntaxDBVersion != null ? !uspSyntaxDBVersion.equals(that.uspSyntaxDBVersion) : that.uspSyntaxDBVersion != null)
            return false;
        if (uspModuleDBVersion != null ? !uspModuleDBVersion.equals(that.uspModuleDBVersion) : that.uspModuleDBVersion != null)
            return false;
        if (uspWikiDBVersion != null ? !uspWikiDBVersion.equals(that.uspWikiDBVersion) : that.uspWikiDBVersion != null)
            return false;
        if (cSyntaxDBVersion != null ? !cSyntaxDBVersion.equals(that.cSyntaxDBVersion) : that.cSyntaxDBVersion != null)
            return false;
        if (cModuleDBVersion != null ? !cModuleDBVersion.equals(that.cModuleDBVersion) : that.cModuleDBVersion != null)
            return false;
        if (cWikiDBVersion != null ? !cWikiDBVersion.equals(that.cWikiDBVersion) : that.cWikiDBVersion != null)
            return false;
        if (cppSyntaxDBVersion != null ? !cppSyntaxDBVersion.equals(that.cppSyntaxDBVersion) : that.cppSyntaxDBVersion != null)
            return false;
        if (cppModuleDBVersion != null ? !cppModuleDBVersion.equals(that.cppModuleDBVersion) : that.cppModuleDBVersion != null)
            return false;
        if (cppWikiDBVersion != null ? !cppWikiDBVersion.equals(that.cppWikiDBVersion) : that.cppWikiDBVersion != null)
            return false;
        if (sqlSyntaxDBVersion != null ? !sqlSyntaxDBVersion.equals(that.sqlSyntaxDBVersion) : that.sqlSyntaxDBVersion != null)
            return false;
        if (sqlModuleDBVersion != null ? !sqlModuleDBVersion.equals(that.sqlModuleDBVersion) : that.sqlModuleDBVersion != null)
            return false;
        if (sqlWikiDBVersion != null ? !sqlWikiDBVersion.equals(that.sqlWikiDBVersion) : that.sqlWikiDBVersion != null)
            return false;
        if (javaSyntaxDBVersionPremium != null ? !javaSyntaxDBVersionPremium.equals(that.javaSyntaxDBVersionPremium) : that.javaSyntaxDBVersionPremium != null)
            return false;
        if (javaModuleDBVersionPremium != null ? !javaModuleDBVersionPremium.equals(that.javaModuleDBVersionPremium) : that.javaModuleDBVersionPremium != null)
            return false;
        if (javaWikiDBVersionPremium != null ? !javaWikiDBVersionPremium.equals(that.javaWikiDBVersionPremium) : that.javaWikiDBVersionPremium != null)
            return false;
        if (cSyntaxDBVersionPremium != null ? !cSyntaxDBVersionPremium.equals(that.cSyntaxDBVersionPremium) : that.cSyntaxDBVersionPremium != null)
            return false;
        if (cModuleDBVersionPremium != null ? !cModuleDBVersionPremium.equals(that.cModuleDBVersionPremium) : that.cModuleDBVersionPremium != null)
            return false;
        if (cWikiDBVersionPremium != null ? !cWikiDBVersionPremium.equals(that.cWikiDBVersionPremium) : that.cWikiDBVersionPremium != null)
            return false;
        if (cppSyntaxDBVersionPremium != null ? !cppSyntaxDBVersionPremium.equals(that.cppSyntaxDBVersionPremium) : that.cppSyntaxDBVersionPremium != null)
            return false;
        if (cppModuleDBVersionPremium != null ? !cppModuleDBVersionPremium.equals(that.cppModuleDBVersionPremium) : that.cppModuleDBVersionPremium != null)
            return false;
        return cppWikiDBVersionPremium != null ? cppWikiDBVersionPremium.equals(that.cppWikiDBVersionPremium) : that.cppWikiDBVersionPremium == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = javaSyntaxDBVersion != null ? javaSyntaxDBVersion.hashCode() : 0;
        result = 31 * result + (javaModuleDBVersion != null ? javaModuleDBVersion.hashCode() : 0);
        result = 31 * result + (javaWikiDBVersion != null ? javaWikiDBVersion.hashCode() : 0);
        temp = Double.doubleToLongBits(javaProgramIndexDBVersion);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(javaProgramTableDBVersion);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (uspSyntaxDBVersion != null ? uspSyntaxDBVersion.hashCode() : 0);
        result = 31 * result + (uspModuleDBVersion != null ? uspModuleDBVersion.hashCode() : 0);
        result = 31 * result + (uspWikiDBVersion != null ? uspWikiDBVersion.hashCode() : 0);
        temp = Double.doubleToLongBits(uspProgramIndexDBVersion);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(uspProgramTableDBVersion);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (cSyntaxDBVersion != null ? cSyntaxDBVersion.hashCode() : 0);
        result = 31 * result + (cModuleDBVersion != null ? cModuleDBVersion.hashCode() : 0);
        result = 31 * result + (cWikiDBVersion != null ? cWikiDBVersion.hashCode() : 0);
        temp = Double.doubleToLongBits(cProgramIndexDBVersion);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(cProgramTableDBVersion);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (cppSyntaxDBVersion != null ? cppSyntaxDBVersion.hashCode() : 0);
        result = 31 * result + (cppModuleDBVersion != null ? cppModuleDBVersion.hashCode() : 0);
        result = 31 * result + (cppWikiDBVersion != null ? cppWikiDBVersion.hashCode() : 0);
        temp = Double.doubleToLongBits(cppProgramIndexDBVersion);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(cppProgramTableDBVersion);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (sqlSyntaxDBVersion != null ? sqlSyntaxDBVersion.hashCode() : 0);
        result = 31 * result + (sqlModuleDBVersion != null ? sqlModuleDBVersion.hashCode() : 0);
        result = 31 * result + (sqlWikiDBVersion != null ? sqlWikiDBVersion.hashCode() : 0);
        temp = Double.doubleToLongBits(sqlProgramIndexDBVersion);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(sqlProgramTableDBVersion);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (javaSyntaxDBVersionPremium != null ? javaSyntaxDBVersionPremium.hashCode() : 0);
        result = 31 * result + (javaModuleDBVersionPremium != null ? javaModuleDBVersionPremium.hashCode() : 0);
        result = 31 * result + (javaWikiDBVersionPremium != null ? javaWikiDBVersionPremium.hashCode() : 0);
        temp = Double.doubleToLongBits(javaProgramIndexDBVersionPremium);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(javaProgramTableDBVersionPremium);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (cSyntaxDBVersionPremium != null ? cSyntaxDBVersionPremium.hashCode() : 0);
        result = 31 * result + (cModuleDBVersionPremium != null ? cModuleDBVersionPremium.hashCode() : 0);
        result = 31 * result + (cWikiDBVersionPremium != null ? cWikiDBVersionPremium.hashCode() : 0);
        temp = Double.doubleToLongBits(cProgramIndexDBVersionPremium);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(cProgramTableDBVersionPremium);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (cppSyntaxDBVersionPremium != null ? cppSyntaxDBVersionPremium.hashCode() : 0);
        result = 31 * result + (cppModuleDBVersionPremium != null ? cppModuleDBVersionPremium.hashCode() : 0);
        result = 31 * result + (cppWikiDBVersionPremium != null ? cppWikiDBVersionPremium.hashCode() : 0);
        temp = Double.doubleToLongBits(cppProgramIndexDBVersionPremium);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(cppProgramTableDBVersionPremium);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

package com.sortedqueue.programmercreek.database;

import java.io.Serializable;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushTableAnnotation;

/**
 * Created by Alok Omkar on 2016-12-25.
 */
@RushTableAnnotation
public class LanguageModule extends RushObject implements Serializable {

    private String moduleId;
    private String moduleName;
    private String moduleDescription;
    private String moduleLanguage;

    public LanguageModule() {
    }

    public LanguageModule(String moduleId, String moduleName, String moduleDescription, String moduleLanguage) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.moduleDescription = moduleDescription;
        this.moduleLanguage = moduleLanguage;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

    public String getModuleLanguage() {
        return moduleLanguage;
    }

    public void setModuleLanguage(String moduleLanguage) {
        this.moduleLanguage = moduleLanguage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LanguageModule that = (LanguageModule) o;

        if (moduleId != null ? !moduleId.equals(that.moduleId) : that.moduleId != null)
            return false;
        if (moduleName != null ? !moduleName.equals(that.moduleName) : that.moduleName != null)
            return false;
        if (moduleDescription != null ? !moduleDescription.equals(that.moduleDescription) : that.moduleDescription != null)
            return false;
        return moduleLanguage != null ? moduleLanguage.equals(that.moduleLanguage) : that.moduleLanguage == null;

    }

    @Override
    public int hashCode() {
        int result = moduleId != null ? moduleId.hashCode() : 0;
        result = 31 * result + (moduleName != null ? moduleName.hashCode() : 0);
        result = 31 * result + (moduleDescription != null ? moduleDescription.hashCode() : 0);
        result = 31 * result + (moduleLanguage != null ? moduleLanguage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LanguageModule{" +
                "moduleId='" + moduleId + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", moduleDescription='" + moduleDescription + '\'' +
                ", moduleLanguage='" + moduleLanguage + '\'' +
                '}';
    }
}

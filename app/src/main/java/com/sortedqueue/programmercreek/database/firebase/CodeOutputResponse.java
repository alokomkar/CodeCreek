package com.sortedqueue.programmercreek.database.firebase;

/**
 * Created by Alok Omkar on 2017-03-25.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CodeOutputResponse {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("langId")
    @Expose
    private Integer langId;
    @SerializedName("langName")
    @Expose
    private String langName;
    @SerializedName("langVersion")
    @Expose
    private String langVersion;
    @SerializedName("time")
    @Expose
    private Double time;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("memory")
    @Expose
    private Integer memory;
    @SerializedName("signal")
    @Expose
    private Integer signal;
    @SerializedName("public")
    @Expose
    private Boolean _public;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("output_encoded")
    @Expose
    private String outputEncoded;
    @SerializedName("output_type")
    @Expose
    private String outputType;
    @SerializedName("output")
    @Expose
    private String output;
    @SerializedName("input")
    @Expose
    private String input;
    @SerializedName("stderr")
    @Expose
    private String stderr;
    @SerializedName("cmpinfo")
    @Expose
    private String cmpinfo;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getLangId() {
        return langId;
    }

    public void setLangId(Integer langId) {
        this.langId = langId;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public String getLangVersion() {
        return langVersion;
    }

    public void setLangVersion(String langVersion) {
        this.langVersion = langVersion;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public Integer getSignal() {
        return signal;
    }

    public void setSignal(Integer signal) {
        this.signal = signal;
    }

    public Boolean getPublic() {
        return _public;
    }

    public void setPublic(Boolean _public) {
        this._public = _public;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOutputEncoded() {
        return outputEncoded;
    }

    public void setOutputEncoded(String outputEncoded) {
        this.outputEncoded = outputEncoded;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getStderr() {
        return stderr;
    }

    public void setStderr(String stderr) {
        this.stderr = stderr;
    }

    public String getCmpinfo() {
        return cmpinfo;
    }

    public void setCmpinfo(String cmpinfo) {
        this.cmpinfo = cmpinfo;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public CodeOutputResponse() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeOutputResponse that = (CodeOutputResponse) o;

        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        if (langId != null ? !langId.equals(that.langId) : that.langId != null) return false;
        if (langName != null ? !langName.equals(that.langName) : that.langName != null)
            return false;
        if (langVersion != null ? !langVersion.equals(that.langVersion) : that.langVersion != null)
            return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (result != null ? !result.equals(that.result) : that.result != null) return false;
        if (memory != null ? !memory.equals(that.memory) : that.memory != null) return false;
        if (signal != null ? !signal.equals(that.signal) : that.signal != null) return false;
        if (_public != null ? !_public.equals(that._public) : that._public != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (outputEncoded != null ? !outputEncoded.equals(that.outputEncoded) : that.outputEncoded != null)
            return false;
        if (outputType != null ? !outputType.equals(that.outputType) : that.outputType != null)
            return false;
        if (output != null ? !output.equals(that.output) : that.output != null) return false;
        if (input != null ? !input.equals(that.input) : that.input != null) return false;
        if (stderr != null ? !stderr.equals(that.stderr) : that.stderr != null) return false;
        return cmpinfo != null ? cmpinfo.equals(that.cmpinfo) : that.cmpinfo == null;

    }

    @Override
    public int hashCode() {
        int result1 = error != null ? error.hashCode() : 0;
        result1 = 31 * result1 + (langId != null ? langId.hashCode() : 0);
        result1 = 31 * result1 + (langName != null ? langName.hashCode() : 0);
        result1 = 31 * result1 + (langVersion != null ? langVersion.hashCode() : 0);
        result1 = 31 * result1 + (time != null ? time.hashCode() : 0);
        result1 = 31 * result1 + (date != null ? date.hashCode() : 0);
        result1 = 31 * result1 + (status != null ? status.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (memory != null ? memory.hashCode() : 0);
        result1 = 31 * result1 + (signal != null ? signal.hashCode() : 0);
        result1 = 31 * result1 + (_public != null ? _public.hashCode() : 0);
        result1 = 31 * result1 + (source != null ? source.hashCode() : 0);
        result1 = 31 * result1 + (outputEncoded != null ? outputEncoded.hashCode() : 0);
        result1 = 31 * result1 + (outputType != null ? outputType.hashCode() : 0);
        result1 = 31 * result1 + (output != null ? output.hashCode() : 0);
        result1 = 31 * result1 + (input != null ? input.hashCode() : 0);
        result1 = 31 * result1 + (stderr != null ? stderr.hashCode() : 0);
        result1 = 31 * result1 + (cmpinfo != null ? cmpinfo.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "CodeOutputResponse{" +
                "error='" + error + '\'' +
                ", langId=" + langId +
                ", langName='" + langName + '\'' +
                ", langVersion='" + langVersion + '\'' +
                ", time=" + time +
                ", date='" + date + '\'' +
                ", status=" + status +
                ", result=" + result +
                ", memory=" + memory +
                ", signal=" + signal +
                ", _public=" + _public +
                ", source='" + source + '\'' +
                ", outputEncoded='" + outputEncoded + '\'' +
                ", outputType='" + outputType + '\'' +
                ", output='" + output + '\'' +
                ", input='" + input + '\'' +
                ", stderr='" + stderr + '\'' +
                ", cmpinfo='" + cmpinfo + '\'' +
                '}';
    }
}

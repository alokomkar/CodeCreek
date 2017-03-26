package com.sortedqueue.programmercreek.database.firebase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alok Omkar on 2017-03-25.
 */

public class IdResponse {
    @SerializedName("id")
    @Expose
    private Integer id;

    public IdResponse(Integer id) {
        this.id = id;
    }

    public IdResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IdResponse{" +
                "id=" + id +
                '}';
    }
}

package com.omarInc.mymeal.area.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreasResponse {
    @SerializedName("meals")
    private List<Area> areas;

    // Getter and Setter
    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
}
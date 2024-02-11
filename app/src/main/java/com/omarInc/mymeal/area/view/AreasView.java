package com.omarInc.mymeal.area.view;

import com.omarInc.mymeal.area.model.Area;

import java.util.List;

public interface AreasView {
    void showAreas(List<Area> areas);
    void showError(String message);
}
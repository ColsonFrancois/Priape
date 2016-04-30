package com.example.francois.priape.api.utils;

import java.util.List;

/**
 * Created by Francois on 30/04/2016.
 */
public class BackendlessCollection<T> {

    private int offset;
    private int totalObjects;
    private List<T> data;
    private String nextPage;

    public int getOffset(){return offset;}

    public int getTotalObjects() {
        return totalObjects;
    }
    public List<T> getData() {
        return data;
    }

    public String getNextPage() {
        return nextPage;
    }
}

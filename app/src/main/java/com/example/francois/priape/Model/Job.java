package com.example.francois.priape.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francois on 01/05/2016.
 */
public class Job {

    private String ___class;
    private String name;
    private List<Work> works;

    public Job(String name, List<Work> works)
    {
        this.name = name;
        this.works = works;
        this.___class = getClass().getSimpleName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Work> getWorks() {
        return works;
    }

    public void setWorks(List<Work> works) {
        this.works = works;
    }

    public void addWork(Work work)
    {
        if(works == null)
        {
            this.works = new ArrayList<>();
        }
        this.works.add(work);
    }
}

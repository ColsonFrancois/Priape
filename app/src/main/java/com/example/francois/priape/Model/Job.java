package com.example.francois.priape.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francois on 01/05/2016.
 */
public class Job implements Parcelable {

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

    protected Job(Parcel in) {
        ___class = in.readString();
        name = in.readString();
        if (in.readByte() == 0x01) {
            works = new ArrayList<Work>();
            in.readList(works, Work.class.getClassLoader());
        } else {
            works = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(___class);
        dest.writeString(name);
        if (works == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(works);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Job> CREATOR = new Parcelable.Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };
}
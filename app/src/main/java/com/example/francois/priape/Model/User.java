package com.example.francois.priape.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francois on 30/04/2016.
 *
 */
public class User implements Parcelable {

    private String ___class;
    private String __meta;
    private String ownerId;
    private String objectId;
    private String email;
    private String name;
    private Job job;
    private List<Work> works;
    private Boolean professional;
    @SerializedName("user-token")
    private String userToken;


    public User(String __meta, String objectId, String ownerId, String email, Job job)
    {
        this.__meta = __meta;
        this.objectId = objectId;
        this.ownerId = ownerId;
        this.email = email;
        this.job = job;
        this.___class = getClass().getSimpleName();
    }

    public String get___class() {
        return ___class;
    }

    public void set___class(String ___class) {
        this.___class = ___class;
    }

    public String get__meta() {
        return __meta;
    }

    public void set__meta(String __meta) {
        this.__meta = __meta;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getProfessional() {
        return professional;
    }

    public void setProfessional(Boolean professional) {
        this.professional = professional;
    }

    public List<Work> getWorks() {
        return works;
    }

    public void setWorks(List<Work> works) {
        this.works = works;
    }


    protected User(Parcel in) {
        ___class = in.readString();
        __meta = in.readString();
        ownerId = in.readString();
        objectId = in.readString();
        email = in.readString();
        name = in.readString();
        job = (Job) in.readValue(Job.class.getClassLoader());
        if (in.readByte() == 0x01) {
            works = new ArrayList<Work>();
            in.readList(works, Work.class.getClassLoader());
        } else {
            works = null;
        }
        byte professionalVal = in.readByte();
        professional = professionalVal == 0x02 ? null : professionalVal != 0x00;
        userToken = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(___class);
        dest.writeString(__meta);
        dest.writeString(ownerId);
        dest.writeString(objectId);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeValue(job);
        if (works == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(works);
        }
        if (professional == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (professional ? 0x01 : 0x00));
        }
        dest.writeString(userToken);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
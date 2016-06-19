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
    private String job;
    private String phone;
    private String description;
    private String picture;
    private List<Work> works;
    private Boolean professional;
    private GeoPoint location;
    @SerializedName("user-token")
    private String userToken;

    public User()
    {

    }

    public User(String __meta, String objectId, String ownerId, String email,String name)
    {
        this.__meta = __meta;
        this.objectId = objectId;
        this.ownerId = ownerId;
        this.email = email;
        this.name = name;
        this.___class = getClass().getSimpleName();

    }

    public void addwork(Work work)
    {
        if(works == null)
        {
            this.works = new ArrayList<>();
        }
        this.works.add(work);
    }
    public void removeWork()
    {
        if(works == null)
        {
            this.works = new ArrayList<>();
        }
        this.works.clear();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }




    protected User(Parcel in) {
        ___class = in.readString();
        __meta = in.readString();
        ownerId = in.readString();
        objectId = in.readString();
        email = in.readString();
        name = in.readString();
        job = in.readString();
        phone = in.readString();
        description = in.readString();
        picture = in.readString();
        if (in.readByte() == 0x01) {
            works = new ArrayList<Work>();
            in.readList(works, Work.class.getClassLoader());
        } else {
            works = null;
        }
        byte professionalVal = in.readByte();
        professional = professionalVal == 0x02 ? null : professionalVal != 0x00;
        location = (GeoPoint) in.readValue(GeoPoint.class.getClassLoader());
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
        dest.writeString(job);
        dest.writeString(phone);
        dest.writeString(description);
        dest.writeString(picture);
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
        dest.writeValue(location);
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
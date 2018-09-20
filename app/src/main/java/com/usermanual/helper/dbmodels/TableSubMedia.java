package com.usermanual.helper.dbmodels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class TableSubMedia implements Parcelable, Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @SerializedName("Id")
    public int subMediaId;

    @SerializedName("Pid")
    public int parentSubMediaId;

    @SerializedName("Text")
    public String text;

    @SerializedName("Url")
    public String url;

    protected TableSubMedia(Parcel in) {
        id = in.readInt();
        subMediaId = in.readInt();
        parentSubMediaId = in.readInt();
        text = in.readString();
        url = in.readString();
    }

    public static final Creator<TableSubMedia> CREATOR = new Creator<TableSubMedia>() {
        @Override
        public TableSubMedia createFromParcel(Parcel in) {
            return new TableSubMedia(in);
        }

        @Override
        public TableSubMedia[] newArray(int size) {
            return new TableSubMedia[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(subMediaId);
        dest.writeInt(parentSubMediaId);
        dest.writeString(text);
        dest.writeString(url);
    }
}
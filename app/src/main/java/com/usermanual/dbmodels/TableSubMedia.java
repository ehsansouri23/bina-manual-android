package com.usermanual.dbmodels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class TableSubMedia implements Parcelable, Serializable {

    @PrimaryKey
    @SerializedName("id")
    public int subMediaId;

    @SerializedName("pid")
    public int parentSubMediaId;

    @SerializedName("txt")
    public String text = "";

    @SerializedName("url")
    public String fileKey = "";

    @SerializedName("type")
    public int fileType;

    protected TableSubMedia(Parcel in) {
        subMediaId = in.readInt();
        parentSubMediaId = in.readInt();
        text = in.readString();
        fileKey = in.readString();
        fileType = in.readInt();
    }
    public TableSubMedia() {

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
        dest.writeInt(subMediaId);
        dest.writeInt(parentSubMediaId);
        dest.writeString(text);
        dest.writeString(fileKey);
        dest.writeInt(fileType);
    }
}

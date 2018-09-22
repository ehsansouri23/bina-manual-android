package com.usermanual.dbmodels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class TableMedia implements Parcelable, Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @SerializedName("Id")
    public int mediaId;

    @SerializedName("Pid")
    public int parentSubtitleId;

    @SerializedName("Title")
    public String mediaTitle;

    @SerializedName("Picture")
    public String picUrl;

    public TableMedia() {

    }


    protected TableMedia(Parcel in) {
        id = in.readInt();
        mediaId = in.readInt();
        parentSubtitleId = in.readInt();
        mediaTitle = in.readString();
        picUrl = in.readString();
    }

    public static final Creator<TableMedia> CREATOR = new Creator<TableMedia>() {
        @Override
        public TableMedia createFromParcel(Parcel in) {
            return new TableMedia(in);
        }

        @Override
        public TableMedia[] newArray(int size) {
            return new TableMedia[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(mediaId);
        dest.writeInt(parentSubtitleId);
        dest.writeString(mediaTitle);
        dest.writeString(picUrl);
    }
}

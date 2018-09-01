package com.usermanual.helper.dbmodels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

@Entity
public class TableMedia implements Parcelable, Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int subtitleId;

    public String title;

    public int num;

    public String mediaText;

    public String mediaUrl;

    public String type;

    public TableMedia() {

    }

    protected TableMedia(Parcel in) {
        id = in.readInt();
        subtitleId = in.readInt();
        title = in.readString();
        num = in.readInt();
        mediaText = in.readString();
        mediaUrl = in.readString();
        type = in.readString();
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
        dest.writeInt(subtitleId);
        dest.writeString(title);
        dest.writeInt(num);
        dest.writeString(mediaText);
        dest.writeString(mediaUrl);
        dest.writeString(type);
    }
}

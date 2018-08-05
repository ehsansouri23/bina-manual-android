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

    public String title;
    public String subtitle;
    public String mediaText;
    public String mediaUrl;

    protected TableMedia(Parcel in) {
        id = in.readInt();
        title = in.readString();
        subtitle = in.readString();
        mediaText = in.readString();
        mediaUrl = in.readString();
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
        dest.writeString(title);
        dest.writeString(subtitle);
        dest.writeString(mediaText);
        dest.writeString(mediaUrl);
    }
}

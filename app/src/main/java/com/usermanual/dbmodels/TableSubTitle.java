package com.usermanual.dbmodels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class TableSubTitle implements Serializable, Parcelable {

    @PrimaryKey
    @SerializedName("id")
    public int subtitleId;

    @SerializedName("pid")
    public int parentTitleId;

    @SerializedName("txt")
    public String subtitle;

    @SerializedName("url")
    public String fileKey = "";

    public TableSubTitle() {

    }

    protected TableSubTitle(Parcel in) {
        parentTitleId = in.readInt();
        subtitleId = in.readInt();
        subtitle = in.readString();
        fileKey = in.readString();
    }

    public static final Creator<TableSubTitle> CREATOR = new Creator<TableSubTitle>() {
        @Override
        public TableSubTitle createFromParcel(Parcel in) {
            return new TableSubTitle(in);
        }

        @Override
        public TableSubTitle[] newArray(int size) {
            return new TableSubTitle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(parentTitleId);
        dest.writeInt(subtitleId);
        dest.writeString(subtitle);
        dest.writeString(fileKey);
    }

    @Override
    public String toString() {
        return "id=" + subtitleId + "    pid=" + parentTitleId + "    subtitle=" + subtitle + "    url=" + fileKey;
    }
}

package com.usermanual.dbmodels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class TableTitle implements Serializable, Parcelable {

    @PrimaryKey
    @SerializedName("id")
    public int titleId;

    @SerializedName("txt")
    public String title;

    @SerializedName("url")
    public String fileKey = "";

    public TableTitle() {

    }

    protected TableTitle(Parcel in) {
        titleId = in.readInt();
        title = in.readString();
        fileKey = in.readString();
    }

    public static final Creator<TableTitle> CREATOR = new Creator<TableTitle>() {
        @Override
        public TableTitle createFromParcel(Parcel in) {
            return new TableTitle(in);
        }

        @Override
        public TableTitle[] newArray(int size) {
            return new TableTitle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(titleId);
        dest.writeString(title);
        dest.writeString(fileKey);
    }

    @Override
    public String toString() {
        return "id=" + titleId + "    title=" + title + "    url=" + fileKey;
    }
}

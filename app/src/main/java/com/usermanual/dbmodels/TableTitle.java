package com.usermanual.dbmodels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class TableTitle implements Serializable, Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @SerializedName("Id")
    public int titleId;

    @SerializedName("Title")
    public String title;

    @SerializedName("Picture")
    public String fileKey = "";

    @SerializedName("Type")
    public String fileType;

    public TableTitle() {

    }

    protected TableTitle(Parcel in) {
        id = in.readInt();
        titleId = in.readInt();
        title = in.readString();
        fileKey = in.readString();
        fileType = in.readString();
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
        dest.writeInt(id);
        dest.writeInt(titleId);
        dest.writeString(title);
        dest.writeString(fileKey);
        dest.writeString(fileType);
    }
}

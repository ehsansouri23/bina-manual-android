package com.usermanual.helper.dbmodels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class TableSubTitle implements Serializable, Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @SerializedName("Titleid")
    public int titleId;

    @SerializedName("Id")
    public int subtitleId;

    public String title;

    @SerializedName("Title")
    public String subtitle;

    public TableSubTitle() {

    }

    protected TableSubTitle(Parcel in) {
        id = in.readInt();
        titleId = in.readInt();
        subtitleId = in.readInt();
        title = in.readString();
        subtitle = in.readString();
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
        dest.writeInt(id);
        dest.writeInt(titleId);
        dest.writeInt(subtitleId);
        dest.writeString(title);
        dest.writeString(subtitle);
    }
}

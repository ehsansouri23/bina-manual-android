package com.usermanual.dbmodels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

@Entity
public class TableToDownloadFiles implements Serializable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String fileKey;


    protected TableToDownloadFiles(Parcel in) {
        id = in.readInt();
        fileKey = in.readString();
    }

    public TableToDownloadFiles() {

    }

    public TableToDownloadFiles(String fileKey) {
        this.fileKey = fileKey;
    }

    public static final Creator<TableToDownloadFiles> CREATOR = new Creator<TableToDownloadFiles>() {
        @Override
        public TableToDownloadFiles createFromParcel(Parcel in) {
            return new TableToDownloadFiles(in);
        }

        @Override
        public TableToDownloadFiles[] newArray(int size) {
            return new TableToDownloadFiles[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(fileKey);
    }
}

package com.usermanual.dbmodels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity
public class TableToDownloadFiles implements Serializable, Parcelable {

    @PrimaryKey
    @NonNull
    public String fileKey;

    public int type;

    public int downloaded;


    protected TableToDownloadFiles(Parcel in) {
        fileKey = in.readString();
        type = in.readInt();
        downloaded = in.readInt();
    }

    public TableToDownloadFiles() {
    }

    public TableToDownloadFiles(String fileKey, int type, int downloaded) {
        this.fileKey = fileKey;
        this.type = type;
        this.downloaded = downloaded;
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
        dest.writeString(fileKey);
        dest.writeInt(type);
        dest.writeInt(downloaded);
    }

    @Override
    public String toString() {
        return "key=" + fileKey + "    type=" + type + "    downloaded=" + downloaded;
    }
}

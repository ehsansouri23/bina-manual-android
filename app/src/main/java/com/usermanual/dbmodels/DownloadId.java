package com.usermanual.dbmodels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

@Entity
public class DownloadId implements Parcelable, Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String fileKey;

    public int downloadId;

    protected DownloadId(Parcel in) {
        id = in.readInt();
        fileKey = in.readString();
        downloadId = in.readInt();
    }

    public DownloadId(String fileKey, int downloadId) {
        this.downloadId = downloadId;
        this.fileKey = fileKey;
    }

    public static final Creator<DownloadId> CREATOR = new Creator<DownloadId>() {
        @Override
        public DownloadId createFromParcel(Parcel in) {
            return new DownloadId(in);
        }

        @Override
        public DownloadId[] newArray(int size) {
            return new DownloadId[size];
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
        dest.writeInt(downloadId);
    }
}

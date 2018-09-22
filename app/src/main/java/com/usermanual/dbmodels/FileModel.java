package com.usermanual.dbmodels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

@Entity
public class FileModel implements Parcelable, Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String fileKey;

    public int type;

    protected FileModel(Parcel in) {
        id = in.readInt();
        fileKey = in.readString();
        type = in.readInt();
    }

    public FileModel() {

    }

    public static final Creator<FileModel> CREATOR = new Creator<FileModel>() {
        @Override
        public FileModel createFromParcel(Parcel in) {
            return new FileModel(in);
        }

        @Override
        public FileModel[] newArray(int size) {
            return new FileModel[size];
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
        dest.writeInt(type);
    }
}

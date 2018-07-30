package com.usermanual.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Media implements Serializable, Parcelable {
    String text;
    String url;
    int type;

    public Media(String text, String url, int type) {
        this.text = text;
        this.url = url;
        this.type = type;
    }

    protected Media(Parcel in) {
        text = in.readString();
        url = in.readString();
        type = in.readInt();
    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public int getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(url);
        dest.writeInt(type);
    }
}

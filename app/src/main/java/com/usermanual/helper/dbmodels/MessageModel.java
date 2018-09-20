package com.usermanual.helper.dbmodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MessageModel implements Parcelable, Serializable {

    @SerializedName("Token")
    public String token = "";

    @SerializedName("Text")
    public String text = "";

    @SerializedName("fileAddress")
    public String url = "";

    protected MessageModel(Parcel in) {
        token = in.readString();
        text = in.readString();
        url = in.readString();
    }

    public MessageModel() {

    }

    public static final Creator<MessageModel> CREATOR = new Creator<MessageModel>() {
        @Override
        public MessageModel createFromParcel(Parcel in) {
            return new MessageModel(in);
        }

        @Override
        public MessageModel[] newArray(int size) {
            return new MessageModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeString(text);
        dest.writeString(url);
    }
}

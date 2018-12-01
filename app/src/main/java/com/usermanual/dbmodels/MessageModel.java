package com.usermanual.dbmodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MessageModel implements Parcelable, Serializable {

    @SerializedName("pid")
    public int ticketId;

    @SerializedName("txt")
    public String text = "";

    @SerializedName("type")
    public int type;

    protected MessageModel(Parcel in) {
        ticketId = in.readInt();
        text = in.readString();
        type = in.readInt();
    }

    public MessageModel() {
    }

    public MessageModel(int ticketId, String text, int type) {
        this.ticketId = ticketId;
        this.text = text;
        this.type = type;
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
        dest.writeInt(ticketId);
        dest.writeString(text);
        dest.writeInt(type);
    }

    @Override
    public String toString() {
        return "pid=" + ticketId + "    txt=" + text + "    type=" + type;
    }
}

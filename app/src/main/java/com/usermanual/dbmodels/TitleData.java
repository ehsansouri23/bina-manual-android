package com.usermanual.dbmodels;

public class TitleData {

    public int id;
    public int pid;
    public String text;
    public String fileKey = "";

    public TitleData(int id, int pid, String text, String fileKey) {
        this.id = id;
        this.pid = pid;
        this.text = text;
        this.fileKey = fileKey;
    }
}

package com.mango.artsparkxml;

import android.content.Context;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class tbl_Entry implements Serializable {
    public int entryId;
    public String entryTitle;
    public String content;
    public long date;
    public tbl_Entry() {
    }
    public tbl_Entry(String entryTitle, String content, long date) {
        this.entryTitle = entryTitle;
        this.content = content;
        this.date = date;
    }
    public tbl_Entry(int entryId, String entryTitle, String content, long date) {
        this.entryId = entryId;
        this.entryTitle = entryTitle;
        this.content = content;
        this.date = date;
    }
    public int getEntryId() {
        return entryId;
    }
    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }
    public String getEntryTitle() {
        return entryTitle;
    }
    public void setEntryTitle(String entryTitle) {
        this.entryTitle = entryTitle;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }
    // return the date in a specified format
    public String getDateTimeFormatted(Context context) {
        // DateFormat.getDateInstance(DateFormat.SHORT).format(mDateTime);
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy"
                , context.getResources().getConfiguration().locale);
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.format(new Date(date));
    }
}

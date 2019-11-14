package com.nav.tagger.Model;

import android.support.annotation.NonNull;

/**
 * Created by navitgupta on 09/06/18.
 */

public class AddTagList implements Comparable<AddTagList>{

    private int id;
    private String tag;
    private String timestamp;
    private String hide;

    public AddTagList() {

    }

    public AddTagList(int id, String tag, String timestamp) {
        this.id = id;
        this.tag = tag;
        this.timestamp = timestamp;
    }

    public String getHide() {
        return hide;
    }

    public void setHide(String hide) {
        this.hide = hide;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int compareTo(@NonNull AddTagList tagg) {
        return tag.compareTo(tagg.getTag());
    }
}

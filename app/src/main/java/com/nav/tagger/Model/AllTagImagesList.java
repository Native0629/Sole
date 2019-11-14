package com.nav.tagger.Model;

/**
 * Created by navitgupta on 12/06/18.
 */

public class AllTagImagesList {
    private int id;
    private String tag;
    private String image;
    private String timestamp;

    public AllTagImagesList(int id, String tag, String image, String timestamp) {
        this.id = id;
        this.tag = tag;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

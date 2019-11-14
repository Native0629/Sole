package com.nav.tagger.Model;

/**
 * Created by navitgupta on 09/06/18.
 */

public class TagImageList {

    private int id;
    private String tag;
    private String tag_image;
    private String timestamp;

    public  TagImageList(){

    }
    public TagImageList(int id, String tag, String tag_image, String timestamp) {
        this.id = id;
        this.tag = tag;
        this.tag_image = tag_image;
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

    public String getTag_image() {
        return tag_image;
    }

    public void setTag_image(String tag_image) {
        this.tag_image = tag_image;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

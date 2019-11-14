package com.nav.tagger.Model;

/**
 * Created by navitgupta on 10/06/18.
 */

public class GalleryList {

    private String image;
    private String checked;
    private String tag;

    public GalleryList(String image, String checked, String tag) {
        this.image = image;
        this.checked = checked;
        this.tag = tag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}

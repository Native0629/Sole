package com.nav.tagger.Model;

import android.graphics.drawable.Drawable;

/**
 * Created by Fujitsu on 10-05-2018.
 */

public class DraweList {
    private String name;
    private Drawable image;

    public DraweList(String name, Drawable image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}

package com.nav.tagger.Activity;

import com.nav.tagger.Model.AddTagList;

public class TagComparable implements Comparable<AddTagList>{

    private String name;
    private String lng;
    private String lat;
    private String address;
    private String rate;
    private String comment;



    @Override
    public int compareTo(AddTagList rest) {
        //TODO: check if rate is null if this can happen in your code
        return rate.compareTo(rest.getTag()); //comparing on rate attribute
    }

}

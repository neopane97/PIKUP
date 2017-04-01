package com.pikup.pash.pikup;

import java.util.HashMap;
import java.util.Map;

public class Post {
    private String title;
    private String descr;
    private String location;
    private String image_path;
    private String pcat;
    private String uid;

    public Post() { /* nothing */}

    public Post(String title, String descr, String location, String image_path, String pcat, String uid) {
        this.title = title;
        this.descr = descr;
        this.location = location;
        this.image_path = image_path;
        this.pcat = pcat;
        this.uid = uid;
    }

    public Map<String, String> toMap() {
        HashMap<String, String> res = new HashMap<>();
        res.put("title", title);
        res.put("descr", descr);
        res.put("location", location);
        res.put("image", image_path);
        res.put("category", pcat);
        res.put("uid", uid);
        return res;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public void setPcat(String pcat) {this.pcat = pcat;}

    public String getPcat() {return pcat;}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
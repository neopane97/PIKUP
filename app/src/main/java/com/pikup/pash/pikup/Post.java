package com.pikup.pash.pikup;

import java.util.HashMap;
import java.util.Map;

public class Post {
    private String title;
    private String descr;
    private String location;
    private String image_path;
    private String uid;
    //private PostCat pcat;

    public Post() { /* nothing */}

    public Post(String title, String descr, String location, String image_path, String uid) {
        this.title = title;
        this.descr = descr;
        this.location = location;
        this.image_path = image_path;
        this.uid = uid;
    }

    public Map<String, String> toMap() {
        HashMap<String, String> res = new HashMap<>();
        res.put("title", title);
        res.put("descr", descr);
        res.put("location", location);
        res.put("image", image_path);
        res.put("uid", uid);
        return res;
    }
}
package com.pikup.pash.pikup;

public class Post {
    private String title;
    private String location;
    private String image_path;
    private boolean claimd;
    private String uid;
    private String pcat;
    //private String email;

    public Post() { /* nothing */}

    public Post(String title, String location, String image_path, String uid, String pcat) {
        this.title = title;
        this.location = location;
        this.image_path = image_path;
        this.claimd = false;
        this.uid = uid;
        this.pcat = pcat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isClaimd() {
        return claimd;
    }

    public void setClaimd(boolean claimd) {
        this.claimd = claimd;
    }

    public String getPcat() {
        return pcat;
    }

    public void setPcat(String pcat) {
        this.pcat = pcat;
    }
}

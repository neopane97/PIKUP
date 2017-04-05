package com.pikup.pash.pikup;

public class Post {
	private String title;
	//private String descr;
	private String location;
	private String image_path;
	private boolean claimd;
	private String uid;
	//private PostCat pcat;

	public Post() { /* nothing */}

	public Post(String title, /*String descr,*/ String location, String image_path, String uid) {
		this.title = title;
		//this.descr = descr;
		this.location = location;
		this.image_path = image_path;
		this.claimd = false;
		this.uid = uid;
	}

	public Post(String title, String image_path, boolean claimd, String uid) {
		this.title = title;
		this.image_path = image_path;
		this.claimd = claimd;
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
//
//	public String getDescr() {
//		return descr;
//	}
//
//	public void setDescr(String descr) {
//		this.descr = descr;
//	}
//
//	public String getLocation() {
//		return location;
//	}
//
//	public void setLocation(String location) {
//		this.location = location;
//	}

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
}

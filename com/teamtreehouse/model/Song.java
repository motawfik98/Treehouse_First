package com.teamtreehouse.model;

public class Song {
  protected String mArtist;  //protected give the access to any file in this package to access this variable
  protected String mTitle;
  protected String mVideoUrl;
  
  public Song(String artist, String title, String videoUrl) { //constructor
    mArtist = artist;
    mTitle = title;
    mVideoUrl = videoUrl;
  }
  
  public String getArtist() {
    return mArtist;
  }
  
  public String getTitle() {
    return mTitle;
  }
  
  public String getVideoUrl() {
    return mVideoUrl;
  }
  
  
  @Override
  public String toString() {
    return String.format("Song: %s by %s", mTitle, mArtist);
  }
  
  
  
  
  
}
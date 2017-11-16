package com.teamtreehouse.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class SongBook {
  private List<Song> mSongs;
  
  public SongBook() { //constructor
    mSongs = new ArrayList<Song>();
  }
  
  public void exportTo(String fileName) {
    try(
      FileOutputStream fos = new FileOutputStream(fileName); //Creates a file output stream to write to the file represented by the specified File object
      PrintWriter writer = new PrintWriter(fos); //opens a writer to write in a specific given file
    ) { 
      for (Song song : mSongs) {
        writer.printf("%s|%s|%s|%n", 
                     song.getArtist(),
                     song.getTitle(),
                     song.getVideoUrl()); //writes the data using the writer which choose a specific file to write in
      }
    } catch (IOException ioe) {
      System.out.printf("Problem saving %s %n", fileName);
      ioe.printStackTrace();
    }
  }
  
  public void importFrom(String fileName) {
    try (
      FileInputStream fis = new FileInputStream(fileName); // open a connection to an actual file, the file named by the File object fileName in the file system
      BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
    ) {
      String line;
      while ( (line = reader.readLine()) != null ) { //readLine() will return null if there is no lines in the file
        String[] args = line.split("\\|"); //to escape the pipe we need to escape the backslash
        addSong(new Song(args[0], args[1], args[2])); //call the addSong method, pass to it a new song with the 3 parameters 
                                                      // args[0] -- artist, args[1] -- title, args[2] -- videoUrl
      }
    } catch (IOException ioe) {
      System.out.printf("Problems loading %s %n", fileName);
      ioe.printStackTrace();
    }
  }
  
  public void addSong(Song song) { //to add a song to the list
    mSongs.add(song);
  }
  
  public int getSongCount() { //to get how many songs are there
    return mSongs.size();
  }
  
  //FIX ME:This should be cached!
  private Map<String, List<Song>> byArtist() {
    Map<String, List<Song>> byArtist = new TreeMap<String, List<Song>>(); //TreeMap to sort the map 
    //Java 7 allows you not to define once it was already been defined on the left
    for (Song song : mSongs) { //loop through the songs in the songbook
      List<Song> artistSongs = byArtist.get(song.getArtist()); //try to pull out the artist from the byArtist map
      if (artistSongs == null) { //if it's not found
        artistSongs = new ArrayList<Song>(); //create a list of artist's songs
        byArtist.put(song.getArtist(), artistSongs); //put the artist as the key and the empty list as the value
      }
      artistSongs.add(song); //add the song to the empty list of the specific artist
    }
    return byArtist;
  }
  
  public Set<String> getArtists() {
    return byArtist().keySet(); //gets the artist names in a set from the map
  }
  
  public List<Song> getSongsForArtist(String artistName) {
    List<Song> songs = byArtist().get(artistName); //gets a list of songs from the map by using artistName as a key
    songs.sort(new Comparator<Song>() { //this is an annonymus class
      
      @Override
      public int compare(Song song1, Song song2) {
        if (song1.equals(song2)) {
          return 0; 
        } 
        return song1.mTitle.compareTo(song2.mTitle); //mTitle is a protected not a private field
      }
      
    } );
    return songs;
  }
}
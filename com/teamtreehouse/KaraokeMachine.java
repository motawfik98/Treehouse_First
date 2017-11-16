package com.teamtreehouse;

import com.teamtreehouse.model.Song;
import com.teamtreehouse.model.SongBook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class KaraokeMachine {
  private SongBook mSongBook;
  private BufferedReader mReader;
  private Queue<Song> mSongQueue;
  private Map<String, String> mMenu;
  
  public KaraokeMachine(SongBook songBook) {
    mSongBook = songBook;
    mReader = new BufferedReader(new InputStreamReader(System.in)); //to be able to get input from the user
    mSongQueue = new ArrayDeque<Song>();
    mMenu = new HashMap<String, String>();
    mMenu.put("add", "Add a new song to the song book");
    mMenu.put("choose", "Choose a sog to sing");
    mMenu.put("play", "Play next song in the queue");
    mMenu.put("quit", "Give up.  Exit the program");
  }
  
  private String promptAction() throws IOException { //to throw the exception wherever this method is caleed
    System.out.printf("There are %d songs available and %d songs in the queue.  Your choices are: %n",
                      mSongBook.getSongCount(),
                      mSongQueue.size());
    for (Map.Entry<String, String> option : mMenu.entrySet()) {
      System.out.printf("%s - %s %n",
                        option.getKey(),
                        option.getValue());
    }
    
    System.out.print("What do you want to do: "); //print is to print in the same line instead of (println)
    String choice = mReader.readLine(); //to get a line from the user
    return choice.trim().toLowerCase(); //trim() is to remove the spaces in the first and the last of the string
  
  }
  public void run() {
    String choice = "";
    do {
      try {
        choice = promptAction(); //as this method throws and exception we must put it in an try catch 
        switch(choice) {
          case "add": //if the choice is to add
            Song song = promptNewSong();  //make a new song
            mSongBook.addSong(song);  //add the song to the songbook
            System.out.printf("%s added! %n%n", song);
            break;
          case "choose":
            String artist = promptArtist(); //use this method to show the artists and get the choosen artist
            Song artistSong = promptSongForArtist(artist);
            mSongQueue.add(artistSong); //to add the song to the queue
            System.out.printf("You chose: %s %n", artistSong);
            break;
          case "play":
            playNext();
            break;
          case "quit":
            System.out.println("Thanks for playing!");
            break;
          default: //if none of the above was correct, this will run
            System.out.printf("Unknown choice:   '%s'. Try again %n%n%n", choice); 
          
        }
      } catch(IOException ioe) {
        System.out.println("Problem with input");
        ioe.printStackTrace();
      }
    } while (!choice.equals("quit"));
  } 
    
  private Song promptNewSong() throws IOException { //the method to add the new song
    System.out.print("Enter the artist's name:  ");
    String artist = mReader.readLine(); //gets the artist name
    System.out.print("Enter the title:  ");
    String title = mReader.readLine();  //gets the title
    System.out.print("Enter the video URL:  ");
    String videoUrl = mReader.readLine();  //gets the video URL
    return new Song(artist, title, videoUrl);  //return a new song with the data above
  }
  
  private String promptArtist() throws IOException {
    System.out.println("Available artists:");
    List<String> artists = new ArrayList<String>(mSongBook.getArtists()); //to return a list instead of a set
    int index = promptForIndex(artists); //pass the artists in promptForIndex to make the user choose the required artist
    return artists.get(index); //return the choosen artist
  }
  
  private Song promptSongForArtist(String artist) throws IOException {
    List<Song> songs = mSongBook.getSongsForArtist(artist); //gets the songs from the songbok of the specified artist
    List<String> songTitles = new ArrayList<String>(); //make a new list  for the songTitles
    for (Song song : songs) { //loop through the songs
      songTitles.add(song.getTitle()); //get the song title and add it to the new list of titles
    }
    System.out.printf("Available songs for %s: %n", artist);
    int index = promptForIndex(songTitles);
    return songs.get(index);
  }
  
  private int promptForIndex(List<String> options) throws IOException { //return the number of the song which the user will choose 
                                                                        // the parameter is the list of songs from which the user will choose
    int counter = 1;
    for (String option : options) { //loop throgh the list
      System.out.printf("%d.)  %s %n", counter, option); //print out the option
      counter++; 
    }
    System.out.println("Your choice:  ");
    String optionAsString = mReader.readLine(); //let the user choose a number, but readLine() returns a string, so we'll convert it
    int choice = Integer.parseInt(optionAsString.trim()); //to convert the string to integer
    return choice - 1; //as the list in base zero (starts at index zero) and choice is initialized = 1
    
  }
  
  public void playNext() {
    Song song = mSongQueue.poll(); //get the second song, remove it from the queue and return it
    if (song == null) {
      System.out.println("Sorry there are no songs in the queue");
    } else {
      System.out.printf("%n%n%n Open %s to hear %s by %s %n%n%n",
                        song.getVideoUrl(),
                        song.getTitle(),
                        song.getArtist());
    }
  }
}
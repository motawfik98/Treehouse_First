import com.teamtreehouse.KaraokeMachine;
import com.teamtreehouse.model.Song;
import com.teamtreehouse.model.SongBook;

public class Karaoke {
  
  public static void main(String[] args) {
    SongBook songBook = new SongBook();
    songBook.importFrom("songs.txt"); //to make sure to import all saved songs when the program starts
    KaraokeMachine machine = new KaraokeMachine(songBook);
    machine.run();
    System.out.println("Saving book.....");
    songBook.exportTo("songs.txt"); //to make sure that all added songs where saved in the file
  }
}
// --== CS400 Spring 2023 File Header Information ==--
// Name: Sanjay Thasma
// Email: thasma@wisc.edu
// Team: DZ
// TA: April Roszkowski
// Lecturer: Florian
// Notes to Grader: /(;p;)/

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class implements the text user interface of the the Song Sorter project
 * 
 * @author Sanjay T
 *
 */
public class SongSearchFrontendFD implements SongSearchFrontendInterface {
  private Scanner userInput; // Scanner to read in user input
  private SongSearchBackendInterface backend;

  /**
   * Constructor to initialize frontend with a scanner and the SongSearchBackend
   * 
   * @param userInput - scanner used to read user input
   * @param bd        - backend object
   */
  public SongSearchFrontendFD(Scanner userInput, SongSearchBackendInterface bd) {
    this.userInput = userInput;
    this.backend = bd;
  }

  /**
   * This method implements a loop that allows the user to access the commands of the song searcher.
   * This loop executes till the user quits by pressing 'q'
   */
  @Override
  public void runCommandLoop() {
    // welcome message
    System.out
        .println("-------------------------------------------------------------------------------");
    System.out.println("                           Welcome to Song Searcher");
    System.out
        .println("-------------------------------------------------------------------------------");

    String input;
    char command = '\0';
    while (command != 'Q') { // main loop continues until user chooses to quit
      command = this.mainMenuPrompt();
      // checking which command user chose
      if (command == 'L') {
        // load dataset
        this.loadDataCommand();

      } else if (command == 'X') {
        // // prints songs that are higher than duration
        System.out.print("Enter Maximum Duration [minutes : seconds] -> ");
        input = userInput.nextLine().trim();
        this.searchSongLowerDuration(input);

      } else if (command == 'N') {
        // prints songs that are lower than duration
        System.out.print("Enter Minimum Duration [minutes : seconds] -> ");
        input = userInput.nextLine().trim();
        this.searchSongHigherDuration(input);

      } else if (command == 'A') {
        // search songs by artist
        System.out.print("Enter Artist Name: ");
        input = userInput.nextLine().trim();
       // input = userInput.nextLine();
        this.searchSongArtist(input);

      } else if (command == 'T') {
        // search songs by title
        System.out.print("Enter Song Title: ");
        input = userInput.nextLine().trim();
        this.searchSongTitle(input);

      } else if (command == 'I') {
        // insert song
        this.loadNewSongCommand();

      } else if (command == 'D') {
        // delete song
        this.removeSongCommand();

      } else if (command == 'S') {
        // display statistics about dataset
        this.displayStatsCommand();

      } else if (command == 'Q') {
        // quit the program
        break;
      } else {
        System.out
            .println("Invalid Command. Please enter one of the letters presented within []s ");
        System.out.println();
        // NOTE: if the input is a string, then the first char is taken. So if first char is one of
        // the commands, then this block won't execute
      }
    }

  }

  /**
   * Prints the command options to System.out and reads user's choice through userInput scanner.
   */
  @Override
  public char mainMenuPrompt() {
    // display menu of choices
    System.out.println("Choose one of the following commands:");
    System.out.println("    [L]oad data from file");
    System.out.println("    Search Songs by Ma[X] Duration");
    System.out.println("    Search Songs by Mi[N] Duration");
    System.out.println("    Search Songs by [A]rtist");
    System.out.println("    Search Songs by [T]itle");
    System.out.println("    [I]nsert Song to dataset");
    System.out.println("    [D]elete Song to dataset");
    System.out.println("    Display [S]tatistics for dataset");
    System.out.println("    [Q]uit");

    // read user input
    System.out.print("Choose command: ");
    String input = userInput.nextLine().trim();
    if (input.length() == 0) // if user's choice is blank, return null character
      return '\0';
    // otherwise, return an uppercase version of the first character in input
    return Character.toUpperCase(input.charAt(0));
  }

  /**
   * This method uses the backend method loadData() to load a dataset from a given filename
   */

  @Override
  public void loadDataCommand() {
    // get user input
    System.out.print("Enter filename: ");
    String filename = userInput.nextLine().trim(); // .replaceAll(",", "")

    // checking for invalid filename input
    try {
      this.backend.loadData(filename);
      System.out.println("Load complete");
      System.out.println();

    } catch (FileNotFoundException e) {
      System.out.println("File Not Found");
      System.out.println();
    }
  }

  /**
   * This method creates and adds a new Song object using the backend insertSong() method. The Song
   * object is created by reading user input.
   */
  @Override
  public void loadNewSongCommand() {
    String[] song = new String[3];
    String input;
    int duration = -1; // duration cannot be negative

    try {
      // getting Song name from the user
      System.out.println();
      System.out.print("Enter Song Name: ");
      input = this.userInput.nextLine();
      song[0] = input;

      // getting Song artist from the user
      System.out.print("Enter Artist: ");
      input = this.userInput.nextLine();
      song[1] = input;

      // getting and checking if the user inputed duration is a number
      while (!input.toUpperCase().equals("Q")) {
        try {
          // getting input
          System.out.print("Enter Duration [minutes : seconds] -> ");
          input = this.userInput.nextLine();

          // formatting duration into seconds
          song[2] = input;
          String[] parts = input.split(":");
          if (parts.length != 1) {

            if (Integer.parseInt(parts[1]) > 60) // checking if seconds is correct
              throw new NumberFormatException();

            // converting duration into seconds
            duration = (Integer.parseInt(parts[0].trim()) * 60) + Integer.parseInt(parts[1]);

          } else {
            // duration inputed as seconds
            duration = Integer.parseInt(song[2]);
          }

          // checking for proper duration input
          if (duration < 0) {
            throw new NumberFormatException();
          }
          break;

        } catch (NumberFormatException e) {
          System.out.println("Invalid Duration (press 'Q' to exit)");
        } catch (NullPointerException e1) {
          System.out.println("Invalid Duration (press 'Q' to exit)");
        }
      }

      // checking if user quit insertion
      if (input.equals("Q") || duration < 0) {
        throw new IllegalArgumentException();
      }

      // adding song to Red-Black Tree
      SongDW toAdd = new SongDW(song[0], song[1], Integer.toString(duration));
      Boolean check = this.backend.insertSong(toAdd);
      // Remember to change SongFD to SongDW or Song after integration

      System.out.println("Insertion Sucessful");
      System.out.println();

    } catch (IllegalArgumentException e) {
      if (duration < 0) {
        System.out.println("Insert Aborted: Illegal Duration");
      } else {
        System.out.println("Cannot Insert: Song with same duration in Searcher");
      }
      System.out.println();
    }
  }

  /**
   * This method uses the backend removeSong() method to remove a song. This method reads user
   * input, creates a new Song object, and passes the object to backend to remove an equivalent node
   * from the Red-Black Tree.
   */
  @Override
  public void removeSongCommand() {
    String[] song = new String[3];
    String input;
    int duration = -1; // duration cannot be negative

    try {
      // getting Song name from the user
      System.out.println();
      System.out.print("Enter Song Name: ");
      input = this.userInput.nextLine();
      song[0] = input;

      // getting Song artist from the user
      System.out.print("Enter Artist: ");
      input = this.userInput.nextLine();
      song[1] = input;

      // getting and checking if the user inputed duration is a number
      while (!input.toUpperCase().equals("Q")) {
        try {
          // getting input
          System.out.print("Enter Duration [minutes : seconds] -> ");
          input = this.userInput.nextLine();

          // formatting duration into seconds
          song[2] = input;
          String[] parts = input.split(":");
          if (parts.length == 2) {

            if (Integer.parseInt(parts[1]) > 60) // checking if seconds is correct
              throw new NumberFormatException();

            // converting duration into seconds
            duration = (Integer.parseInt(parts[0].trim()) * 60) + Integer.parseInt(parts[1]);

          } else if (parts.length == 1) {
            // duration inputed as seconds
            duration = Integer.parseInt(song[2]);
          } else {
            throw new NumberFormatException();
          }

          // checking for proper duration input
          if (duration < 0) {
            throw new NumberFormatException();
          }
          break;
        } catch (NumberFormatException e) {
          System.out.println("Invalid Duration (press 'Q' to exit)");
        } catch (NullPointerException e1) {
          System.out.println("Invalid Duration (press 'Q' to exit)");
        }
      }

      // checking if user quit insertion
      if (input.equals("Q") || duration < 0) {
        throw new IllegalArgumentException();
      }

      // deleting song from Red-Black Tree
      SongDW toRemove = new SongDW(song[0], song[1], Integer.toString(duration));
      this.backend.removeSong(toRemove);
      // Remember to change SongFD to SongDW or Song after integration

      System.out.println("Removal Sucessful");
      System.out.println();

    } catch (IllegalArgumentException e) {
      if (duration < 0) {
        System.out.println("Deletion Aborted: Illegal Duration");
      } else {
        System.out.println("Cannot remove: Song isn't in dataset");
      }
      System.out.println();
    }
  }

  /**
   * This method uses the backend getLowerDuration() method to print information about songs with
   * lower duration than a baseline duration
   * 
   * @param duration - the baseline duration
   */
  @Override
  public void searchSongLowerDuration(String duration) {
    try {
      int d; // duration input as int
      List<SongInterface> result = new ArrayList<>();
      String[] parts = duration.split(":");

      // formatting duration into seconds and passing it as baseline duration for getLowerDuration()

      if (parts.length == 1) { // duration given as seconds
        d = Integer.parseInt(duration.replaceAll("\\s+", "").trim());
        if (d < 0)
          throw new NumberFormatException();
        result = this.backend.getLowerDuration(d);

      } else if (parts.length == 2) { // duration given in [minutes : seconds]

        int seconds = Integer.parseInt(parts[1].replaceAll("\\s+", "").trim());
        if (seconds > 60)
          throw new NumberFormatException();

        // removing whitespace from duration and converting to int
        d = (Integer.parseInt(parts[0].replaceAll("\\s+", "").trim()) * 60) + seconds;

        if (d < 0) // duration can't be negative
          throw new NumberFormatException();
        result = this.backend.getLowerDuration(d);

      } else {
        throw new NumberFormatException();
      }

      // checking if any songs found
      if (result.size() == 0) {
        System.out.println("No song(s) under " + duration + " seconds in dataset");
        System.out.println();
      }

      // printing songs that are lower than the baseline duration
      for (int i = 0; i < result.size(); i++) {
        System.out.println(result.get(i).toString());
      }
      System.out.println();

    } catch (NumberFormatException e) {
      System.out.println("Invalid duration");
      System.out.println();
    } catch (NullPointerException e1) {
      System.out.println("Invalid duration");
      System.out.println();
    }
  }

  /**
   * This method uses the backend getHigherDuration() method to print information about songs with
   * greater duration than a baseline duration
   * 
   * @param duration - the baseline duration
   */
  @Override
  public void searchSongHigherDuration(String duration) {
    try {
      int d;
      List<SongInterface> result = new ArrayList<>();
      String[] parts = duration.split(":");

      // Formatting duration into seconds and passing it as baseline duration for
      // getHigherDuration()

      if (parts.length == 1) { // duration inputed as seconds
        d = Integer.parseInt(duration.replaceAll("\\s+", "").trim());
        if (d < 0)
          throw new NumberFormatException();
        result = this.backend.getHigherDuration(d);

      } else if (parts.length == 2) { // duration given in [minutes : seconds]

        int seconds = Integer.parseInt(parts[1].replaceAll("\\s+", "").trim());
        if (seconds > 60)
          throw new NumberFormatException();

        // removing whitespace from duration and converting to int
        d = (Integer.parseInt(parts[0].replaceAll("\\s+", "").trim()) * 60) + seconds;

        if (d < 0) // duration can't be negative
          throw new NumberFormatException();
        result = this.backend.getHigherDuration(d);

      } else {
        throw new NumberFormatException();
      }

      // checking if any songs found
      if (result.size() == 0) {
        System.out.println("No song(s) over " + duration + " seconds in dataset");
        System.out.println();
      }

      // printing songs that are higher than the baseline duration
      for (int i = 0; i < result.size(); i++) {
        System.out.println(result.get(i).toString());
      }
      System.out.println();

    } catch (NumberFormatException e) {
      System.out.println("Invalid duration");
      System.out.println();
    } catch (NullPointerException e1) {
      System.out.println("Invalid duration");
      System.out.println();
    }

  }

  /**
   * This method uses the backend searchTitle() method to print information about songs with a given
   * title
   * 
   * @param title - the song title searched in the Red-Black tree
   */
  @Override
  public void searchSongTitle(String title) {
    try {
      List<SongInterface> result = this.backend.searchTitle(title);

      // printing songs with the specified title
      System.out.println();
      for (int i = 0; i < result.size(); i++) {
        System.out.println(result.get(i).toString());
      }
      System.out.println();
    } catch (NoSuchElementException e) {
      System.out.println("No song(s) with title '" + title + "' found in dataset");
      System.out.println();
    }

  }

  /**
   * This method uses the backend searchArtist() method to print information about songs with a
   * given artist
   * 
   * @param artist - the song artist searched in the Red-Black tree
   */
  @Override
  public void searchSongArtist(String artist) {
    try {
      List<SongInterface> result = this.backend.searchArtist(artist);

      // printing songs with the specified artist
      System.out.println();
      for (int i = 0; i < result.size(); i++) {
        System.out.println(result.get(i).toString());
      }
      System.out.println();
    } catch (NoSuchElementException e) {
      System.out.println("No song(s) with artist '" + artist + "' found in dataset");
      System.out.println();
    }

  }

  /**
   * This method uses the backend displayInfo method to display statistics about the Red-Black tree
   */
  @Override
  public void displayStatsCommand() {
    System.out.println(this.backend.displayInfo());

  }

  public static void main(String[] args) {
	  SongSearchBackendBD back = new SongSearchBackendBD();

      // setting up frontend
      Scanner input = new Scanner(System.in);
      SongSearchFrontendFD frontend = new SongSearchFrontendFD(input, back);
	  frontend.runCommandLoop();
  }

}

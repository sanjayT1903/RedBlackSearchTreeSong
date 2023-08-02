
public interface SongSearchFrontendInterface {
//public SongSearchFrontendInterfaceXX (Scanner userInput, SongSearchBackendInterface backend);

public void runCommandLoop();
public char mainMenuPrompt();
public void loadDataCommand();
//uses BD loadData

public void loadNewSongCommand(); 
//asks the user input on Song Title, Artist, and Duration to create a new Song object to 
//send to backend (uses DW implementation)

 public void removeSongCommand(); 
//asks the user input on Song Title, Artist, and Duration to create a new Song object to 
//send to backend (uses DW implementation)

public void searchSongLowerDuration(String duration); 
//takes duration and turns into seconds (uses BD implementation)

public void searchSongHigherDuration(String duration); 
//takes duration and turns into seconds (uses BD implementation)

public void searchSongTitle(String Title); //returns songs based on title
public void searchSongArtist(String Artist); //returns songs based on artist

public void displayStatsCommand();
//uses BD displayInfo
}

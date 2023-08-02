


import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

public interface SongSearchBackendInterface {
	
		public List<SongInterface> getLowerDuration(int time); //returns array of songs lower than param
		public List<SongInterface>  getHigherDuration(int time); //returns array of songs higher than param
		public List<SongInterface>  searchTitle(String title) throws NoSuchElementException; 
		public List<SongInterface>   searchArtist(String artist) throws NoSuchElementException;
		public String displayInfo(); 
		public Boolean insertSong(SongInterface s);
		public SongInterface removeSong(SongInterface s);
		public void loadData(String filename) throws FileNotFoundException;

	

}

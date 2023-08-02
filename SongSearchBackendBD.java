//--== CS400 Spring 2023 File Header Information ==--
//Name: Sanjay Thasma
//Email: thasma@wisc.edu
//Team: DZ
//TA: April Roszkowski
//Lecturer: Florian
//Notes to Grader: <optional extra notes>


import java.io.FileNotFoundException;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

//import RedBlackTreeBD.Node;
//Autheor Sanjay T
//import RedBlackTreeBD.Node;
    
public class SongSearchBackendBD extends RedBlackTree<SongInterface> implements SongSearchBackendInterface {
	  
     
	private  RedBlackTree<SongInterface> example ; //instance of the tree
	private SongReaderInterface filer;
	
	//Default constructor
	public SongSearchBackendBD(){
		example = new  RedBlackTree<SongInterface>();
		filer =  new SongReaderDW();
	}
	
	/*
	 * Constructor with tree
	 * @param newer tree is set to the class instance tree.
	 */
	public SongSearchBackendBD(RedBlackTree<SongInterface> newer){
		example = newer;
		filer = new SongReaderDW();
	}
	
	/*
	 * Constructor with tree and file 
	 * @param newer tree is set to the class instance tree.
	 * @param file for loadFile method is set
	 */
	public SongSearchBackendBD(RedBlackTree<SongInterface> newer, SongReaderInterface songReader){
		example = newer;
		filer = songReader;
	}
	
	
	/*
	 * Gets lower duration that given
	 * @param int time- time to be compared in tree
	 * @return values of song that have a lower or EQUAL time
	 */
	@Override
	public List<SongInterface> getLowerDuration(int time){
		//NOTE time is INT becuase no user would make it into double and be exact
		List<SongInterface> finish = new ArrayList<SongInterface> ();
		//mkeas the int into a string for the song behavior of duration
		String timeS = time + "";
		SongInterface test = new SongDW("test", "example", timeS);
	
		
		//get All method by AE returns all values EQUAL to the duration and lower than duration
		finish = example.getAll(test);

		return finish;
	}
	

	
	
	
	/*
	 * Gets higher duration that given 
	 * basically reverses getLowerDuration's results
	 * @param int time- time to be compared in tree
	 * @return values of song that have a ONLY a higher  time
	 * 
	 */
	@Override
	public List<SongInterface>  getHigherDuration(int time){
		
      List<SongInterface> finish = new ArrayList<SongInterface> ();
		String timeS = time + "";
		SongInterface test = new SongDW("test", "example", timeS);
		
		List<SongInterface> holdGreater = new ArrayList<SongInterface> ();
		
		
		
		//getList grabs all values in a tree
		holdGreater= example.getList(holdGreater);
		
		//getAll gets vales lower or equal that the duration stated
		finish = example.getAll(test);
		
		//eliminates holdGreaters shared elements with finish - leaving only songs with higher duration
		for(int i =0; i<finish.size(); i++) {
			if(finish.get(i)!=null) {
				holdGreater.remove(finish.get(i));
			}
		}
		
		
		//return higher duration
		return holdGreater;
	} //returns array of songs higher than param
	
	
	
	/*
	 * Searches for song with title o(n) complexity
	 * @throws NoSuchElementException if Song is not present
	 * @param title to search for
	 * @return list of Songs that match
	 */
	@Override
	public List<SongInterface>  searchTitle(String title) throws NoSuchElementException{
		 List<SongInterface> finish = new ArrayList<SongInterface> ();
		 List<SongInterface> searchMatches = new ArrayList<SongInterface> ();
		 SongInterface test = new SongDW("test", title, "0");
		 
		 //gets list of tree nodes of songs
			finish = example.getList(finish);
			
			//adds to an array all song title mathces and keeps it in a list
			for(int i =0; i<finish.size(); i++) {
				if(finish.get(i).songTitle().equals(test.songTitle())) {
					searchMatches.add(finish.get(i));
					//holdGreater.remove(finish.get(i));
				}
			}
			//if the array of searchmatches caught nothing than the title didnt exist
			if(searchMatches.isEmpty()) {
				//searchMatches should ideally never be empty on return
				throw new NoSuchElementException("Title not present");
			}
			
			//return the list
			return searchMatches;
		
	}
	
	
	
	
	
	/*
	 * Searches for song with artist simsilar to title class
	 * @throws NoSuchElementException if SOng is not present
	 * @param artist to search for
	 * @return list of Songs that match
	 */
	@Override
	public List<SongInterface>  searchArtist(String artist) throws NoSuchElementException{
		 List<SongInterface> finish = new ArrayList<SongInterface> ();
		 List<SongInterface> searchMatches = new ArrayList<SongInterface> ();
		 SongInterface test = new SongDW(artist," title", "0");
		 
		//gets list of tree nodes of songs
			finish = example.getList(finish);
		//	System.out.println(finish);
			
			//adds to an array all song artist mathces and keeps it in a list
			for(int i =0; i<finish.size(); i++) {
			//	System.out.println(finish.get(i).artist());
			//	System.out.println(test.artist());
				if(finish.get(i).artist().equals(test.artist())) {
					
					searchMatches.add(finish.get(i));
					//holdGreater.remove(finish.get(i));
				}
			}
			if(searchMatches.isEmpty()) {
				//searchMatches should ideally never be empty on return
				throw new NoSuchElementException("Artist not present");
			}
			return searchMatches;
	}
	
	
	
	/*
	 * displayInfo returns aspect of Tree like size
	 * @return string of data of tree
	 */
	@Override
	public String displayInfo() {
		
		
		return "The tree has " + example.size() +" Songs"; //simple statistic on the tree
		
	}
	
	/*
	 * inserts Song into RedBalck tree by calling AE's method
	 *  * @param Song to be inserted
	 * @return true if insert worked false if not
	 */
	@Override
	public Boolean insertSong(SongInterface s) {
	
		//if this is the first insert
		if(example.size()==0) {
			example.insert(s);
			return true;
		}
		
		//chesck for duplicates
		List<SongInterface> finish = new ArrayList<SongInterface> ();
		List<SongInterface> holder = new ArrayList<SongInterface> ();
		
		
		finish = example.getAll(s); 
		for(int i =0; i<finish.size();i++) {
			if(finish.get(i).duration().equals(s.duration())) {
				//finish.remove(i);
				//holder holds any song with the same duration as song S. 
				//red black trees CANNOT store duplicates of values and in this case same durations
				holder.add(s);
			}
		}
		
		//this means a duplicate was found
		if(holder.isEmpty()==false) {
			return false;
		} 
		
		//if all the checks have passed than begin the insert
			example.insert(s);
		
		
		//checks insertion success
		if(example.contains(s)) {
			return true;
		}
		return false;
		
	}
	
	/*
	 * removes Song into RedBalck tree by calling AE's method
	 * @param Song to be removed
	 * @return song if the rmeove was successful
	 */
	@Override
	public SongInterface removeSong(SongInterface s) {
		SongInterface end = s;
		//if it holds it than remove it.
		if(example.contains(s)) {
			example.remove(s);
			return end;
		}
		
		return null;
		
	}
	
	/*
	 * loadData loades data from the file by calling DW's Song Reader class and object
	 * @param fileName for name of the file
	 * @throws FileNotFoundException if file is not present
	 */
	@Override
	public void loadData(String filename) throws FileNotFoundException{
	//	List<Song> a = new ArrayList<Song>();
	
		
		try {
			filer =  new SongReaderDW();
			//adds to the songs reader
			List<SongInterface> songList =filer.readSongsFromFile(filename);
			//System.out.println(songList);
			for (int i = 0; i<=songList.size();i++) {
				try {
					//inserts song in the list from the file
			insertSong(songList.get(i));
				}
				catch(Exception E) {
					//for any unexpected errors
				}
			}
		
		}
		catch (FileNotFoundException e) {
			//if no file is found
			throw new FileNotFoundException(e.getMessage());
		}
	}

}

//--== CS400 Spring 2023 File Header Information ==--
//Name: Sanjay Thasma
//Email: thasma@wisc.edu
//Team: DZ
//TA: April Roszkowski
//Lecturer: Florian
//Notes to Grader: <optional extra notes>


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

//mport junit.framework.Assert;

import static org.junit.jupiter.api.Assertions.*;

public class BackendDeveloperTests {

	
	/*
	 * Asserts if the getLowerDuration and getHigherDuration methods work as intended 
	 * Inserts 5 nodes into a Backend class and checks expented results
	 * @return true on Jnuit if system runs as expected
	 */
	@Test
	void test1() {
		
		SongDW one = new SongDW("one", "A", "5");
		SongDW two = new SongDW("two", "B", "2");
		SongDW three = new SongDW("three", "C", "6");
		SongDW four = new SongDW("four", "D", "57");
		SongDW five = new SongDW("five", "E", "1");
		
		//SongBD five3 = new SongBD("five", "T", "1:23");
		

		SongSearchBackendBD a = new SongSearchBackendBD();
		//adding in songs
		a.insertSong(one);
		a.insertSong(two);
		a.insertSong(three);
		a.insertSong(five);
		a.insertSong(four);
		
		//these two Songs are the only ones less than set Duration
		assertEquals(a.getLowerDuration(5).contains(five),true);
		assertEquals(a.getLowerDuration(5).contains(two),true);
		assertEquals(a.getLowerDuration(5).contains(one),true);
		
		//checks if the higher and lower methods add to the total number of songs. 
		assertEquals((a.getLowerDuration(5).size() +a.getHigherDuration(5).size()) ,5);
		
		//these three songs have a higher duration than 5
		
		assertEquals(a.getHigherDuration(5).contains(three),true);
		assertEquals(a.getHigherDuration(5).contains(four),true);
		
	
	}
	
	
	
	/**
	 * Test the search methods for Artist
	 * If all run true than the Junit tests asserts true
	 * @return true on Jnuit if system runs as expected
	 */
	@Test
	void test2() {
		
		SongDW one = new SongDW("one", "A", "5");
		SongDW two = new SongDW("two", "B", "2");
		SongDW three = new SongDW("three", "C", "6");
		SongDW four = new SongDW("four", "D", "57");
		SongDW five = new SongDW("five", "E", "1");
		//SongBD five3 = new SongBD("five", "T", "1:23");
		

		SongSearchBackendBD a = new SongSearchBackendBD();
		a.insertSong(one);
		a.insertSong(two);
		a.insertSong(three);
		a.insertSong(five);
		a.insertSong(four);
		
		//should  contain song one as seen as SongBD one = new SongBD("one", "A", "5:23"); where first parameter is for artist
		//same for the rest of the tests
		assertEquals(a.searchArtist("one").contains(one),true);
		
		assertEquals(a.searchArtist("two").contains(two),true);
		
		assertEquals(a.searchArtist("five").contains(five),true);
		
		assertEquals(a.searchArtist("four").contains(four),true);
		
		
	}

	/**
	 * Test the search methods for Title
	 * If all run true than the Junit tests asserts true
	 * @return true on Jnuit if system runs as expected
	 */
@Test
void test3() {
	SongDW one = new SongDW("one", "A", "5");
	SongDW two = new SongDW("two", "B", "2");
	SongDW three = new SongDW("three", "C", "6");
	SongDW four = new SongDW("four", "D", "57");
	SongDW five = new SongDW("five", "E", "1");
	
	
	//SongBD five3 = new SongBD("five", "T", "1:23");
	

	SongSearchBackendBD a = new SongSearchBackendBD();
	a.insertSong(one);
	a.insertSong(two);
	a.insertSong(three);
	a.insertSong(five);
	a.insertSong(four);
	
	
	//should  contain sone one as seen as SongBD one = new SongBD("one", "A", "5:23"); where second parameter is for Title
	//same for the rest of the tests
	assertEquals(a.searchTitle("A").contains(one),true);
	
	assertEquals(a.searchTitle("B").contains(two),true);
	
	assertEquals(a.searchTitle("E").contains(five),true);
	
	assertEquals(a.searchTitle("D").contains(four),true);
	
}


/**
 * Tests remove and displayData
 * If all run true than the Junit tests asserts true
 * @return true on Jnuit if system runs as expected
 */
@Test
void test4() {
	
	SongDW one = new SongDW("one", "A", "5");
	SongDW two = new SongDW("two", "B", "2");
	SongDW three = new SongDW("three", "C", "6");
	SongDW four = new SongDW("four", "D", "57");
	SongDW five = new SongDW("five", "E", "1");
	
	//SongBD five3 = new SongBD("five", "T", "1:23");
	

	SongSearchBackendBD a = new SongSearchBackendBD();
	a.insertSong(one);
	a.insertSong(two);
	a.insertSong(three);
	a.insertSong(five);
	a.insertSong(four);
	
	//System.out.println(a.displayInfo());
	
	//this is a test of the dsiplay which should result in 5
	assertEquals(a.displayInfo(),"The tree has 5 Songs");
	
	//now the display should be 4 if remove was successful
	a.removeSong(one);
	
	
	assertEquals(a.displayInfo(),"The tree has 4 Songs");
	a.removeSong(two); //now the display should be 4 if remove was successful
	
	assertEquals(a.displayInfo(),"The tree has 3 Songs");
	
}


/*
 * test 5 is checking loadData and the extend of remove wit errors
 * If all run true than the Junit tests asserts true
 * @return true on Jnuit if system runs as expected
 */
@Test
void test5() {
	
	SongDW one = new SongDW("one", "A", "5");
	SongDW two = new SongDW("two", "B", "2");
	SongDW three = new SongDW("three", "C", "6");
	SongDW four = new SongDW("four", "D", "57");
	SongDW five = new SongDW("five", "E", "1");
	
	//SongBD five3 = new SongBD("five", "T", "1:23");
	

	SongSearchBackendBD a = new SongSearchBackendBD();
	a.insertSong(one);
	a.insertSong(two);
	a.insertSong(three);
	a.insertSong(five);
	a.insertSong(four);
	
	//tests the loadFile actually runs without error even though I know that LoadFile does not really need to work.
	//PlaceHolder does not work just creates an empty arrayList
	//Just checking if it gets called
	//Part 1 of test 5
	String file = "songList.txt";
	try {
		a.loadData(file);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	//Part 2 of test 5
	//remove more than avilable
	a.removeSong(one);
	a.removeSong(two);
	a.removeSong(three);
	a.removeSong(five);
	a.removeSong(four);
	
	int errorCount =0;
	//this should error the system as all value are removed
	try {
		//repeat remove
		if(a.removeSong(four) ==null) {
			errorCount++;
		}
	//errorCount++;
	}
	catch(Exception e) {
		errorCount++;
	}
	
	assertEquals(errorCount,1);
	//Accounts for the load files 8 original VALID songs
	assertEquals(a.displayInfo(),"The tree has 8 Songs");
	
	
}
	

/**
 * checks integration of the Song class with the 
 * toString method and the remove method that returns a song
 * The insertSong and remove use the AE implementation
 * returns true if test returns proper information
 */
@Test
void test1Integration() {
	
	SongDW one = new SongDW("one", "A", "5");
	SongDW two = new SongDW("two", "B", "2");
	SongDW three = new SongDW("three", "C", "6");
	SongDW four = new SongDW("four", "D", "57");
	SongDW five = new SongDW("five", "E", "1");
	
	//SongBD five3 = new SongBD("five", "T", "1:23");
	

	SongSearchBackendBD a = new SongSearchBackendBD();
	a.insertSong(one);
	a.insertSong(two);
	a.insertSong(three);
	a.insertSong(five);
	a.insertSong(four);
	
	//expexcted string from the toString of song class vs the removeSOng moethids result
	String result = "Artist: " + "one" + ", Title: " + "A" + ", Duration: " + "5";
	assertEquals(a.removeSong(one).toString(),result);
	
	
	String result2 = "Artist: " + "two" + ", Title: " + "B" + ", Duration: " + "2";
	assertEquals(a.removeSong(two).toString(),result2);
	
	String result3 = "Artist: " + "three" + ", Title: " + "C" + ", Duration: " + "6";
	assertEquals(a.removeSong(three).toString(),result3);
	//a.removeSong(one);
	
	
}
	
/**
 * checks integration of the SongReader class with the 
 * loadFile method. This test uses AE Backend and SongReader. 
 * The insertSong and remove use the AE implementation
 * returns true if test returns proper information
 */
@Test
void test2Integration() {
	
	SongDW one = new SongDW("one", "A", "5");
	SongDW two = new SongDW("two", "B", "2");
	SongDW three = new SongDW("three", "C", "6");
	SongDW four = new SongDW("four", "D", "57");
	SongDW five = new SongDW("five", "E", "1");
	
	//SongBD five3 = new SongBD("five", "T", "1:23");
	

	SongSearchBackendBD a = new SongSearchBackendBD();
	a.insertSong(one);
	a.insertSong(two);
	a.insertSong(three);
	a.insertSong(five);
	a.insertSong(four);
	
	
	assertEquals(a.displayInfo(),"The tree has 5 Songs");
	
	a.insertSong(four); //THIS IS A REPEAT insert so there is no insert bc no duplicates
	
	assertEquals(a.displayInfo(),"The tree has 5 Songs");
	
	String file = "songList.txt"; //8 songs come from this class
	try {
		a.loadData(file);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	//the file has 9n lines but only 8 suitable lines.
	
	//8+5 = 13 songs in the total tree
	assertEquals(a.displayInfo(),"The tree has 13 Songs");

	
	
	
}
	

/**
 * checks the frontEnd class insert and remove method with the display method
 * no error inouts that is for the next test
 * returns true if test returns proper information
 */
@Test
void CodeReviewOfFrontendDeveloperTest1() {
	//int testCount = 0; //counts 1+ when a test runs propelry
	 TextUITester testHelper = new TextUITester("I\nA\nA\n2:00\nI\nB\nB\n4:00\nS\nD\nA\nA\n2:00\nS\nQ"); //runs 3 main commands,
	//Add two times, gets the stats of the tree, then removes and then gets stats again then quits
	 SongSearchBackendBD a = new SongSearchBackendBD();
	Scanner scan = new Scanner(System.in) ;
	SongSearchFrontendFD front = new SongSearchFrontendFD(scan,a);
		  
	front.runCommandLoop();
	

	String result = testHelper.checkOutput();
	//two songs were inserted and the stats were checked
	Assert.assertTrue(result.contains("The tree has 2 Songs"));

	//then the one sone was removed and the stats were checked
	Assert.assertTrue(result.contains("The tree has 1 Songs"));
	
}
	
	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	//System.out.println("test5(): " + test5() );
	}

}


public class tester {

	
	public static Boolean test1() {
		
		SongDW one = new SongDW("two", "A", "5");
		SongDW two = new SongDW("two", "B", "2");
		SongDW three = new SongDW("three", "B", "6");
		SongDW four = new SongDW("two", "D", "57");
		SongDW five = new SongDW("five", "E", "19");
		
		//SongBD five3 = new SongBD("five", "T", "1:23");
		

		SongSearchBackendBD a = new SongSearchBackendBD();
		//adding in songs
		a.insertSong(one);
		a.insertSong(two);
		a.insertSong(three);
		a.insertSong(five);
		a.insertSong(four);
		
		System.out.println(a.displayInfo());
		System.out.println(a.getLowerDuration(5));
		System.out.println(a.getHigherDuration(5));
		System.out.println(a.searchTitle("B"));
		System.out.println(a.searchArtist("two"));
		return true;
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
System.out.println(test1());
	}

}

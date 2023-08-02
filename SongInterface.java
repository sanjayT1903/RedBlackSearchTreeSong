
public interface SongInterface  extends Comparable<SongInterface> {
	public String artist();
	public String songTitle();
	public String duration();
	public String toString();
	
	
	 @Override
	public int compareTo(SongInterface o) ;
	//returns based on durations of songs

}

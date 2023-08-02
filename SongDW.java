
/**
 * This is a Song class (which contains the artist, title, duration)
 */
public class SongDW implements SongInterface
{
  private String artist, title, duration;
   
  /**
   * this is the constructor that sets the instance fields of the song to the info in the text
   */
  public SongDW(String artist, String title, String duration)
  {
	  
    this.artist = artist;
    this.title = title;
    
    
    //Sometimes through a file duration can come in without being translated to seconds to INT, 
    //this is just a LAST-check to make sure it has been tranlsated
    if(duration.contains(":")) {
    	//this is similar to how the frontEnd takes in value and makes t into seconds
		  String min =duration.substring(0, duration.indexOf(':'));
		 int minutes = Integer.parseInt(min)*60;
		  int seconds = Integer.parseInt(duration.substring(duration.indexOf(':')+1,duration.length()-1));
		 
		 int dur = minutes + seconds;
		 String newDuration = dur+"";
		 this.duration = newDuration;
	  }
    
    
    else {
    this.duration = duration;
    }
  }
  
  /**
   * @inheritDoc
   */
  @Override
  public String artist() 
  {
    return artist;
  }

  /**
   * @inheritDoc
   */
  @Override
  public String songTitle() 
  {
    return title;
  }

  /**
   * @inheritDoc
   */
  @Override
  public String duration() 
  {
    return duration;
  }
  
  /**
   * @inheritDoc
   */
  @Override
  public String toString()
  {
    return "Artist: " + artist() + ", Title: " + songTitle() + ", Duration: " + duration();
  }

  /**
  * @inheritDoc
  */
 @Override
 public int compareTo(SongInterface otherSong) 
 {
	 
	 int thisDuration = 0;
	   int otherDuration = 0;
	   
	   thisDuration = Integer.parseInt(this.duration());
	   
	   //making sure otherSOng isnt null
	   if(otherSong!=null) {
	   otherDuration = Integer.parseInt(otherSong.duration());
	   }
	   else {
		   return 1;
	   }
	 
	 

   if (thisDuration > otherDuration)
     return 1;
   else if (thisDuration < otherDuration)
     return -1;
   else
     return 0;
 }


}

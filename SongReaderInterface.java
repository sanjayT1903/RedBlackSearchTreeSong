
import java.io.FileNotFoundException;
import java.util.List;

/**
 * This interface specifies the class that reads the file and formats the information into song 
 * objects
 */
public interface SongReaderInterface 
{
  /**
   * Reads from a text file containing songs in the format of: [Artist], [Title], [Duration]. The 
   * duration is formatted as [Minutes : Seconds], and if the colon is not there then it is just
   * assumed as seconds. Each line contains one song entry. Each song, along with its information,
   * is made into a SongDW object and added to a list.
   * 
   * @param the name of the file that wants to be used
   * @return returns a list of song objects of the songs from the file
   * @throws FileNotFoundException when the file is not found
   */
  public List<SongInterface> readSongsFromFile(String filename) throws FileNotFoundException;
}


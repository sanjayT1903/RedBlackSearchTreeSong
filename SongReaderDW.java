
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class reads song files and formats the information into a list of song objects. Each item
 * is stored as a single line of text. Each of these lines contain an artist name, song title, and
 * duration. Each field is seperated by a comma and are surrounded by: [].
 */
public class SongReaderDW implements SongReaderInterface 
{
  /**
   * @inheritDoc
   */
  @Override
  public List<SongInterface> readSongsFromFile(String filename) throws FileNotFoundException 
  {
    ArrayList<SongInterface> songs = new ArrayList<SongInterface>();
 
    try
    {
      Scanner in = new Scanner(new File(filename));

      while (in.hasNextLine()) {
        // for each line in the file being read:
        String line = in.nextLine();

        // split that line into parts around around the delimiter: ],
        String[] parts = line.split("],");

        // all lines should have 3 parts
        if (parts.length == 3)
        {
        	//System.out.println("09990000999900");
          // gets rid of the first bracket: [
          String artist = parts[0].substring(1, parts[0].length());

          // gets rid of the first bracket: [
          String title = parts[1].substring(2, parts[1].length());

          // gets rid of the first and last bracket: [...]
          String duration = parts[2].substring(2, parts[2].length() - 1);
          

          songs.add(new SongDW(artist, title, duration));
        }
        else
            // lines that do not have the artist, title, and duration are INVALID
              System.out.println("Warning: found a line that does not contain artist, title, and song");
          }

          // then close the scanner before returning the list of songs
          in.close();
        }
        catch (FileNotFoundException e)
        {
          throw new FileNotFoundException("File Not Found");
        }
       
        return songs;
      }
    }
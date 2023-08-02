////--== CS400 Spring 2023 File Header Information ==--
//Name: Sanjay Thasma
//Email: thasma@wisc.edu
//Team: DZ
//TA: April Roszkowski
//Lecturer: Florian
//Notes to Grader: <optional extra notes>


//PLACEHOLDER SONG CLASS
//now this class is wrong post integration
//Changed the compare to and constructor sot eh placeHolder will allow tests to pass through
public class SongBD implements  SongInterface {

        private String artistName = ""; //artistName
        private String title = ""; // title 
        private String time = ""; //title

        /*
         * consturctor thats sets the the artist title and duration for an instance of a song
         * @param artist name
         * @param songTitle is title of song 
         * @param duration in string form is changed and added to time variable
         */
        public SongBD(String artist,String songTitle, String duration){
                artistName = artist;
                title = songTitle;
//              duration = duration.replace(':', '.');
                   if(duration.contains(":")) {
        //this is similar to how the frontEnd takes in value and makes t into seconds
                  String min =duration.substring(0, duration.indexOf(':'));
                 int minutes = Integer.parseInt(min)*60;
                  int seconds = Integer.parseInt(duration.substring(duration.indexOf(':')+1,duration.length()-1));

                 int dur = minutes + seconds;
                 String newDuration = dur+"";
                 time = newDuration;
          }
    
    
    else {
    time = duration;
    }
        }

        /*
         * artist method returns artist of song
         * @return string Artist
         */
        public String artist() {
                return artistName;
        }

        /*
         * songTitle reuturns title fo song object
         * @return String Title
         */
        public String songTitle() {
                return title;
        }

        /*
         * duration returns the duration of the song
         * @return duration as String
         */
        public String duration() {
                return time;

        }

        /*
         * placeholder ToString Class
         * @return String version of Object
         */
        public String toString() {
                return "|Artist:" + artistName +", "+"Title:" + title +", " +"Duration:" + time + "|" ;
        }

  


/*
 * implemented by comparable
 * @return int for the difference in song object Duration
 * @param O is a instance of a Song object
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
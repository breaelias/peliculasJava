package model.streaming.genre;

public enum Genre {
    ACTION,
    ADVENTURE,
    ANIMATION,
    BIOGRAPHY,
    COMEDY,
    CRIME,
    DOCUMENTARY,
    DRAMA,
    FAMILY,
    FANTASY,
    HISTORY,
    HORROR,
    MUSIC,
    MYSTERY,
    ROMANCE,
    SCIFI,       
    SPORT,
    THRILLER,
    TV_MOVIE,
    WAR,
    WESTERN;

    @Override
    public String toString() {
        String s = name();        
        s = s.replace('_', ' ');        
        s = s.toLowerCase(); 
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
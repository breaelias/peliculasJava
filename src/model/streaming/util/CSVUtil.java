package model.streaming.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import model.streaming.content.Movie;
import model.streaming.dao.Factory; 
import model.streaming.dao.MovieDAO;
import model.streaming.genre.Genre;

public class CSVUtil {

	public static List<Movie> loadMoviesFromCSV(String filePath, boolean notDBEmpty) {
        MovieDAO movieDAO = Factory.getMovieDao(); 
        String line;
        List<Movie> movieList = new LinkedList<>(); 
        
        String csvSeparator = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        int i=1;
        
        java.io.InputStream is = CSVUtil.class.getResourceAsStream(filePath);

        try (BufferedReader br = new BufferedReader(new java.io.InputStreamReader(is))) {
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSeparator);

                if (data.length >= 8) {
                    Movie movie = new Movie();
                    
                    try {
                        String date = cleanString(data[0]);
                        if (date.contains("-")) {
                            movie.setYear(Integer.parseInt(date.split("-")[0]));
                        } else {
                            movie.setYear(Integer.parseInt(date));
                        }
                    } catch (Exception e) { 
                        movie.setYear(2025); 
                    }

                    movie.setTitle(cleanString(data[1]));
                    
                    String overview = cleanString(data[2]);
                    if (overview.length() > 500) {
                        overview = overview.substring(0, 497) + "...";
                    }
                    movie.setSynopsis(overview);

                    try {
                        Double voteAvg = Double.parseDouble(cleanString(data[5]));
                        movie.setRating(voteAvg); 
                    } catch (Exception e) { 
                        movie.setRating(0.0);  
                    }

                    String genreRaw = cleanString(data[7]).split(",")[0].trim(); 
                    movie.setGenre(mapGenre(genreRaw));

                    movie.setPoster(cleanString(data[8]));

                    movie.setDirector("Unknown"); 
                    movie.setDuration(Duration.ofMinutes(120)); 
                    
                    movie.setID(i); 
                    
                    if (!notDBEmpty) {
                        movieDAO.save(movie);
                    } 
                    
                    movieList.add(movie);
                }
                i++;
            }
            System.out.println("carga del CSV completa. cant de peliculas: " + movieList.size());
            
        } catch (IOException | NullPointerException e) {
            System.err.println("Error de lectura CSV: " + e.getMessage());
        }
        
        return movieList; 
    }

//    metodo aux para limpiar el string
    private static String cleanString(String input) {
        if (input != null && input.startsWith("\"") && input.endsWith("\"")) {
            return input.substring(1, input.length() - 1);
        }
        return input;
    }

    //metodo para no tener problemas con generos pq son enums
    private static Genre mapGenre(String csvGenre) {
        if (csvGenre == null || csvGenre.isEmpty()) return Genre.DRAMA;

        String normalized = csvGenre.toUpperCase().trim().replace(" ", "_").replace("-", "_");

        try {
            return Genre.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            if (normalized.contains("SCIENCE_FICTION")) return Genre.SCIFI;
            if (normalized.contains("ACTION")) return Genre.ACTION;
            if (normalized.contains("ADVENTURE")) return Genre.ADVENTURE;
            if (normalized.contains("ANIMATION")) return Genre.ANIMATION;
            if (normalized.contains("COMEDY")) return Genre.COMEDY; 
            if (normalized.contains("CRIME")) return Genre.CRIME;
            if (normalized.contains("DOCUMENTARY")) return Genre.DOCUMENTARY;
            if (normalized.contains("DRAMA")) return Genre.DRAMA;
            if (normalized.contains("FAMILY")) return Genre.FAMILY;
            if (normalized.contains("FANTASY")) return Genre.FANTASY;
            if (normalized.contains("HISTORY")) return Genre.HISTORY;
            if (normalized.contains("HORROR")) return Genre.HORROR;
            if (normalized.contains("MYSTERY")) return Genre.MYSTERY;
            if (normalized.contains("ROMANCE")) return Genre.ROMANCE;
            if (normalized.contains("THRILLER")) return Genre.THRILLER;
            if (normalized.contains("TV_MOVIE")) return Genre.TV_MOVIE;
            if (normalized.contains("WAR")) return Genre.WAR;
            if (normalized.contains("WESTERN")) return Genre.WESTERN;
            
             	//por defecto drama
            return Genre.DRAMA;
        }
    }
}
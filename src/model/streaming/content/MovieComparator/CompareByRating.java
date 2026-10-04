package model.streaming.content.MovieComparator;

import java.util.Comparator;

import model.streaming.content.Movie;

public class CompareByRating implements Comparator<Movie>{

	@Override
	public int  compare(Movie o1, Movie o2) {
		return Double.compare(o2.getRating(), o1.getRating());
	}
	
}
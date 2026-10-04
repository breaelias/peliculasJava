package model.streaming.content.MovieComparator;

import java.util.Comparator;

import model.streaming.content.Movie;

public class CompareByDuration implements Comparator<Movie>{

	@Override
	public int compare(Movie o1, Movie o2) {
		
		return o1.getDuration().compareTo(o2.getDuration());
	}

}

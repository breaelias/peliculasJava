package model.streaming.content.MovieComparator;

import java.util.Comparator;

import model.streaming.content.Movie;



public class CompareByGenre implements Comparator<Movie> {

	@Override
	public int compare(Movie o1, Movie o2) {
		return (o1.getGenre().name().compareTo(o2.getGenre().name()));
	}
}

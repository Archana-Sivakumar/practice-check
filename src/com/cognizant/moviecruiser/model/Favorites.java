package com.cognizant.moviecruiser.model;

import java.util.List;

public class Favorites {
	private List<Movies> movieList;
	private int noOfFavorites;

	public Favorites(List<Movies> movieList, int noOfFavorites) {
		super();
		this.movieList = movieList;
		this.noOfFavorites = noOfFavorites;
	}

	public List<Movies> getMovieList() {
		return movieList;
	}

	public void setMovieList(List<Movies> movieList) {
		this.movieList = movieList;
	}

	public int getNoOfFavorites() {
		return noOfFavorites;
	}

	public void setNoOfFavorites(int noOfFavorites) {
		this.noOfFavorites = noOfFavorites;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((movieList == null) ? 0 : movieList.hashCode());
		result = prime * result + noOfFavorites;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Favorites other = (Favorites) obj;
		if (movieList == null) {
			if (other.movieList != null)
				return false;
		} else if (!movieList.equals(other.movieList))
			return false;
		if (noOfFavorites != other.noOfFavorites)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Favorites [movieList=" + movieList + ", noOfFavorites=" + noOfFavorites + "]";
	}

}
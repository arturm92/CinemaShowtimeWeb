package cinemaShowtime.filters;

public interface FilterInterface {
	
	public void initFilter();
	
	public void initRenderedMap();

	public MovieFilter getMovieFilter();

	public MovieSorter getMovieSorter();
}

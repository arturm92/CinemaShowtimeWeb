package cinemaShowtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.json.Cinema;
import model.json.City;
import model.json.complex.Cinemas;
import model.json.complex.Cities;
import model.json.complex.Genres;
import model.json.complex.Movies;
import model.json.complex.Showtimes;
import model.json.movie.Movie;
import util.Consts;
import util.DateFormater;

public class ApiHelper {

	public static String getDataFromApi(String url) {
		try {
			System.out.println("QUERY: " + url);

			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			request.addHeader("X-API-Key", Consts.API_KEY);
			HttpResponse response = client.execute(request);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			return readResultContent(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String readResultContent(HttpResponse response) throws UnsupportedEncodingException, IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}

	public static Cities getCitiesFromApi() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Cities> map = new TypeReference<Cities>() {
			};
			String params = "?lang=" + Consts.LANGUAGE;
			String json = getDataFromApi(Consts.CITIES + params);
			return mapper.readValue(json, map);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Cinemas getAllCinemasInCity(City city) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Cinemas> map = new TypeReference<Cinemas>() {
			};
			String params = "?location=" + city.getLat() + "," + city.getLon();
			params += "&distance=10";
			params += "&lang=" + Consts.LANGUAGE;
			String json = getDataFromApi(Consts.CINEMAS + params);
			return mapper.readValue(json, map);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Movies getAllMoviesInCinema(Cinema cinema) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Movies> map = new TypeReference<Movies>() {
			};
			String params = "?cinema_id=" + cinema.getId();
			params += "&fields=id,poster_image.flat,scene_images.flat,trailers,ratings";
			String json = getDataFromApi(Consts.MOVIES + params);
			return mapper.readValue(json, map);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Movies getAllMoviesDescriptionInCinema(Cinema cinema) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Movies> map = new TypeReference<Movies>() {
			};
			String params = "?cinema_id=" + cinema.getId();
			params += "&fields=id,title,original_title,synopsis,website,cast,crew,genres";
			params += "&lang=" + Consts.LANGUAGE;
			String json = getDataFromApi(Consts.MOVIES + params);
			return mapper.readValue(json, map);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Showtimes getMovieShowtimesInCinema(Movie movie, Cinema cinema) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Showtimes> map = new TypeReference<Showtimes>() {
			};
			String params = "?cinema_id=" + cinema.getId();
			params += "&movie_id=" + movie.getId();
			String json = getDataFromApi(Consts.SHOWTIMES + params);
			return mapper.readValue(json, map);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Movie getMovieMultimedia(Long movieId) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
			String params = "/" + movieId;
			params += "?fields=id,poster_image.flat,scene_images.flat,trailers,ratings";
			params += "&lang=" + Consts.MULTIMEDIA_LANGUAGE;
			params += "&countries=" + Consts.COUNTRIES;
			String json = getDataFromApi(Consts.MOVIES + params);
			return mapper.readValue(json, Movie.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Movie getMovieDescription(Long movieId) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
			String params = "/" + movieId;
			params += "?fields=id,title,original_title,synopsis,website,cast,crew,genres,release_dates,age_limits";
			params += "&lang=" + Consts.LANGUAGE;
			params += "&countries=" + Consts.COUNTRIES;
			String json = getDataFromApi(Consts.MOVIES + params);
			return mapper.readValue(json, Movie.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Movies getNewestMovies() {
		try {
			DateFormater df = new DateFormater();
			String date = df.recalculateDateByMonth(-4);
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Movies> map = new TypeReference<Movies>() {
			};
			String params = "?fields=id,title,poster_image.flat";
			params += "&release_date_from=" + date;
			params += "&countries=" + Consts.COUNTRIES;
			String json = getDataFromApi(Consts.MOVIES + params);
			return mapper.readValue(json, map);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Genres getGenres() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Genres> map = new TypeReference<Genres>() {
			};
			String params = "?lang=pl";
			String json = getDataFromApi(Consts.GENRES + params);
			return mapper.readValue(json, map);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Movies getMoviesCatalogue(Filter filter) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Movies> map = new TypeReference<Movies>() {
			};
			String params = "?fields=id,title,poster_image.flat,original_title";
			params += filter.prepareParameters();
			
			String json = getDataFromApi(Consts.MOVIES + params);
			return mapper.readValue(json, map);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Movies getCinemaPreviewMovies(Filter filter) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Movies> map = new TypeReference<Movies>() {
			};
			String params = "?fields=id,title,original_title,poster_image.flat,release_dates";
			
			params += filter.prepareParameters();
			
			String json = getDataFromApi(Consts.MOVIES + params);
			return mapper.readValue(json, map);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Movies getMoviesPosterEngishVersion(Filter filter) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Movies> map = new TypeReference<Movies>() {
			};
			String params = "?fields=id,poster_image.flat";
			
			params += filter.prepareParameters();
			
			String json = getDataFromApi(Consts.MOVIES + params);
			return mapper.readValue(json, map);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Movies getMoviesRanking(Filter filter) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Movies> map = new TypeReference<Movies>() {
			};
			String params = "?fields=id,title,original_title,poster_image.flat,ratings";
			
			params += filter.prepareParameters();
			
			String json = getDataFromApi(Consts.MOVIES + params);
			return mapper.readValue(json, map);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

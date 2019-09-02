package cinemaShowtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

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

import model.json.cinema.Cinema;
import model.json.complex.Cinemas;
import model.json.complex.Cities;
import model.json.complex.Genres;
import model.json.complex.Movies;
import model.json.complex.Showtimes;
import model.json.movie.MovieFormatted;
import util.Consts;
import util.DateFormater;

public class ApiHelper {

	public static String getDataFromApi(String url) {
		try {
			//System.out.println("QUERY: " + url);
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			request.addHeader("X-API-Key", Consts.API_KEY);
			HttpResponse response = client.execute(request);
			//System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
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

	public static Cinemas getCinemas(Filter filter) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Cinemas> map = new TypeReference<Cinemas>() {
			};
			String json = getDataFromApi(Consts.CINEMAS + filter.prepareParameters());
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

	public static Movies getMoviesInCinema(Filter filter) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Movies> map = new TypeReference<Movies>() {
			};
			String json = getDataFromApi(Consts.MOVIES + filter.prepareParameters());
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

	public static Showtimes getMovieShowtimesInCinema(Filter filter) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Showtimes> map = new TypeReference<Showtimes>() {
			};
			String json = getDataFromApi(Consts.SHOWTIMES + filter.prepareParameters());
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

	public static MovieFormatted getMovieMultimedia(Long movieId) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
			String params = "/" + movieId;
			params += "?fields=id,poster_image.flat,scene_images.flat,trailers,ratings";
			params += "&lang=" + Consts.MULTIMEDIA_LANGUAGE;
			params += "&countries=" + Consts.COUNTRIES;
			String json = getDataFromApi(Consts.MOVIES + params);
			return mapper.readValue(json, MovieFormatted.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static MovieFormatted getMovieDescription(Long movieId) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
			String params = "/" + movieId;
			params += "?fields=id,title,original_title,synopsis,website,cast,crew,genres,release_dates,age_limits";
			params += "&lang=" + Consts.LANGUAGE;
			params += "&countries=" + Consts.COUNTRIES;
			String json = getDataFromApi(Consts.MOVIES + params);
			return mapper.readValue(json, MovieFormatted.class);
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
			if (json.contains("\"code\":10005")) {
				System.out.println("Wygasł klucz do API");
				return null;
			}
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

	public static Movies getMovies(Filter filter) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Movies> map = new TypeReference<Movies>() {
			};
			String json = getDataFromApi(Consts.MOVIES + filter.prepareParameters());
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
			String json = getDataFromApi(Consts.MOVIES + filter.prepareParameters());
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
			String json = getDataFromApi(Consts.MOVIES + filter.prepareParameters());
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

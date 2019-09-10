package cinemaShowtime.helpers;

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

import cinemaShowtime.filters.ApiFilter;
import cinemaShowtime.utils.Const;
import cinemaShowtime.utils.Logger;
import model.json.cinema.Cinema;
import model.json.complex.Cinemas;
import model.json.complex.Cities;
import model.json.complex.Genres;
import model.json.complex.Movies;
import model.json.complex.Showtimes;
import model.json.movie.MovieFormatted;

public class ApiHelper {

	public static String getDataFromApi(String url) {
		try {
			Logger.log("QUERY: " + url);
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			request.addHeader("X-API-Key", Const.API_KEY);
			HttpResponse response = client.execute(request);
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
			String params = "?lang=" + Const.LANGUAGE;
			String json = getDataFromApi(Const.CITIES + params);
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

	public static Cinemas getCinemas(ApiFilter filter) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Cinemas> map = new TypeReference<Cinemas>() {
			};
			String json = getDataFromApi(Const.CINEMAS + filter.prepareParameters());
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

	public static Movies getMoviesInCinema(ApiFilter filter) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Movies> map = new TypeReference<Movies>() {
			};
			String json = getDataFromApi(Const.MOVIES + filter.prepareParameters());
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
			params += "&lang=" + Const.LANGUAGE;
			String json = getDataFromApi(Const.MOVIES + params);
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

	public static Showtimes getMovieShowtimesInCinema(ApiFilter filter) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Showtimes> map = new TypeReference<Showtimes>() {
			};
			String json = getDataFromApi(Const.SHOWTIMES + filter.prepareParameters());
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
			params += "?fields=id,poster_image.flat,scene_images.flat,trailers,ratings,synopsis";
			params += "&lang=" + Const.MULTIMEDIA_LANGUAGE;
			params += "&countries=" + Const.COUNTRIES;
			String json = getDataFromApi(Const.MOVIES + params);
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
			params += "&lang=" + Const.LANGUAGE;
			params += "&countries=" + Const.COUNTRIES;
			String json = getDataFromApi(Const.MOVIES + params);
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

	public static Movies getNewestMovies(ApiFilter filter) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Movies> map = new TypeReference<Movies>() {
			};
			String json = getDataFromApi(Const.MOVIES + filter.prepareParameters());
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
			String json = getDataFromApi(Const.GENRES + params);
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

	public static Movies getMovies(ApiFilter filter) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Movies> map = new TypeReference<Movies>() {
			};
			String json = getDataFromApi(Const.MOVIES + filter.prepareParameters());
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

	public static Movies getMoviesCatalogue(ApiFilter filter) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Movies> map = new TypeReference<Movies>() {
			};
			String json = getDataFromApi(Const.MOVIES + filter.prepareParameters());
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

	public static Movies getMoviesPosterEngishVersion(ApiFilter filter) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<Movies> map = new TypeReference<Movies>() {
			};
			String json = getDataFromApi(Const.MOVIES + filter.prepareParameters());
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

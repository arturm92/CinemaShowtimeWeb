package cinemaShowtime;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Cinema;
import model.Cinemas;
import util.Consts;

@WebServlet("/hi")

public class Main extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void listCinemas(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			TypeReference<Cinemas> mapType = new TypeReference<Cinemas>(){};
			Cinemas cinemas = mapper.readValue(json, mapType);
			for (Cinema cinema : cinemas.getCinemas()) {
				System.out.println(cinema.getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain; charset=UTF-8");
		ApiHelper api = new ApiHelper();
		String json = api.getDataFromApi(Consts.CINEMAS);
		listCinemas(json);
		resp.getWriter().write("Response on cinema request: \n" + json);
	}

}
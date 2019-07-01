package cinemaShowtime;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Consts;
import util.Utils;

@WebServlet("/hi")

public class Main extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public Utils utils = new Utils();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain; charset=UTF-8");
		String json = ApiHelper.getDataFromApi(Consts.CITIES);
		utils.listValues(json);
		resp.getWriter().write("Response on cinema request: \n" + json);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//String param = req.getParameter("endpoint");
		String json = ApiHelper.getDataFromApi(Consts.CITIES);
		utils.listValues(json);
		Object[] forwardParams = utils.getForwardParams();
		req.setAttribute("list",forwardParams[0]);
		req.getRequestDispatcher((String) forwardParams[1]).forward(req, resp);
		
	}

}
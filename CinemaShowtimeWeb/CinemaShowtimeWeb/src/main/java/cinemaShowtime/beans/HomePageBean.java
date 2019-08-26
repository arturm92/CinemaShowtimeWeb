package cinemaShowtime.beans;

import cinemaShowtime.LocationApiHelper;
import model.json.LocationApi;

public class HomePageBean {

	public HomePageBean() {
		try {
			LocationApi locationApi = LocationApiHelper.getLocation();
			System.out.println(locationApi.getCity());
			System.out.println(locationApi.getLatitude());
			System.out.println(locationApi.getLongitude());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}

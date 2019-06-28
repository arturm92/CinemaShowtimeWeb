package cinemaShowtime.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import cinemaShowtime.ApiHelper;
import util.Consts;
import util.Utils;

@ManagedBean(name = "controllerBean", eager = true)
@ApplicationScoped
public class ControllerBean {

	private String endpoint;
	public Utils utils;
	public ApiHelper api;
	//private List<BaseModel> checkedItems;
	private String checkedItems;
	
	private Map<Long, Boolean> checked = new HashMap<Long, Boolean>();
	//private List<> checkedItemsList = new ArrayList<Application>();
	
	public ControllerBean() {
		utils = new Utils();
		api = new ApiHelper();
		System.out.println("ControllerBean started!");
	}

	public String getMessage() {
		return "Hello World!";
	}

	public List<String> getEndpointList() {
		return Consts.ENDPOINTS;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getNextPage() {
		String url = utils.getUrl(endpoint);
		String json = api.getDataFromApi(url);
		utils.listValues(json);
		/*return endpoint.toLowerCase() + ".xhtml";*/
		return "result.xhtml";
				
	}
	
	public Object getList() {
		return utils.getList();
	}
	
}

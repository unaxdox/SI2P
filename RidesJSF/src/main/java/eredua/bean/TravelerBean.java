package eredua.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "travelerBean")
@SessionScoped
public class TravelerBean {

	public String queryRides() {
		return "QueryRides";
	}
	
	public String logout() {
		return "index";
	}
}

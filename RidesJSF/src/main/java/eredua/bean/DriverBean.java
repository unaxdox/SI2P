package eredua.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "driverBean")
@RequestScoped
public class DriverBean {

	public String createRide() {
		return "CreateRide";
	}

	public String logout() {
		return "index";
	}
}

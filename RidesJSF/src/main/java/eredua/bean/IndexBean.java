package eredua.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "indexBean")
@RequestScoped
public class IndexBean {

	public String goToLogin() {
		return "login";
	}

	public String goToRegister() {
		return "register";
	}
	
	public String gidaribidai() {
		return "gidaribidai";
	}
}

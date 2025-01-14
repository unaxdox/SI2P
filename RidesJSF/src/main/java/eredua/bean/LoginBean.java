package eredua.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import businessLogic.BLFacade;
import businessLogic.FacadeBean;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {
	private String username;
	private String password;
	private BLFacade facadeBL;

	public LoginBean() {
		facadeBL = FacadeBean.getBusinessLogic();
	}
	
	public String login() {
		if (!facadeBL.isLoginValid(username, password)) {
			return "login.xhtml"; // Credenciales inválidas
		}

		String userType = facadeBL.getMotabyUsername(username);
		if ("Driver".equalsIgnoreCase(userType)) {
			return "driver.xhtml";
		} else if ("Traveler".equalsIgnoreCase(userType)) {
			return "traveler.xhtml";
		} else {
			return "index.xhtml"; // Error inesperado
		}
	}

	// Getters y setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

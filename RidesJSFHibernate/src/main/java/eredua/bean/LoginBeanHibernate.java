package eredua.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import eredua.businessLogic.BLFacade;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBeanHibernate {
	private String username;
	private String password;
	private BLFacade facadeBL;

	public LoginBeanHibernate() {
		facadeBL = FacadeBeanHibernate.getBusinessLogic();
	}

	public String login() {
//		if (!facadeBL.isLoginValid(username, password)) {
//			return "login.xhtml"; // Credenciales inválidas
//		}
		boolean sartu = facadeBL.isRegistered(username, password);
		if (sartu) {
			String userType = facadeBL.getMotaByUsername(username);
			if ("Driver".equalsIgnoreCase(userType)) {
				return "driver.xhtml";
			} else if ("Traveler".equalsIgnoreCase(userType)) {
				return "traveler.xhtml";
			} else {
				return "index.xhtml"; // Error inesperado
			}
		} else {
			return null;
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

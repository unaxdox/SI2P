package eredua.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import businessLogic.BLFacade;
import businessLogic.FacadeBean;

@ManagedBean(name = "registerBean")
@SessionScoped
public class RegisterBean {

	private String username;
	private String password;
	private String confirmPassword;
	private String userType; 

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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String reg() {
		BLFacade facadeBL = FacadeBean.getBusinessLogic();

	    if (!password.equals(confirmPassword)) {
	        return "register";
	    }

	    if ("Driver".equals(userType)) {
	        if (!facadeBL.addDriver(username, password)) { 
	        	return "register"; 
	        }
	    } else if ("Traveler".equals(userType)) {
	        if (!facadeBL.addTraveler(username, password)) {  
	            return "register"; 
	        }
	    }

	    return "index";
	}
}

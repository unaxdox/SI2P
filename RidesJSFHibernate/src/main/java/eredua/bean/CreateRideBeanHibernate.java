package eredua.bean;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import eredua.businessLogic.BLFacade;

@ManagedBean(name = "create")

public class CreateRideBeanHibernate {

	private String departCity;
	private String arrivalCity;
	private int seats;
	private float price;
	private Date data;

	private String username;

	// Constructor
	public CreateRideBeanHibernate() {
		retrieveUsernameFromLoginBean();
	}

	private void retrieveUsernameFromLoginBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		LoginBeanHibernate loginBean = (LoginBeanHibernate) context.getExternalContext().getSessionMap()
				.get("loginBean");
		if (loginBean != null) {
			this.username = loginBean.getUsername();
			System.out.println(username);
		} else {
			System.out.println("LoginBean no encontrado en el contexto.");
		}
	}

	public String getDepartCity() {
		return departCity;
	}

	public void setDepartCity(String departCity) {
		this.departCity = departCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String create() {
		try {
			BLFacade facadeBL = FacadeBeanHibernate.getBusinessLogic();
			System.out.println(username);

			facadeBL.createRide(departCity, arrivalCity, data, seats, price, username);
			return "success";

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	public String index() {
		return "driver";
	}

}

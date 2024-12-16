package eredua.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import businessLogic.BLFacade;
import businessLogic.FacadeBean;
import domain.Ride;
import domain.User;

@ManagedBean(name = "gidaribidaiBean")
@SessionScoped
public class gidaribidaiBean {

	private String selectedUsername;
	private List<User> drivers;
	private List<String> dusernames;
	private List<Ride> driverRides; // Lista de viajes del conductor seleccionado

	public void loadDrivers() {
		BLFacade facadeBL = FacadeBean.getBusinessLogic();

		drivers = facadeBL.getUserList(); // Usar la función de tu fachada para obtener los conductores
		if (drivers == null) {
			drivers = new ArrayList<>(); // Si es nula, inicializamos una lista vacía
		}
	}

	public void loadDriverRides() {
		if (selectedUsername != null && !selectedUsername.isEmpty()) {
			BLFacade facadeBL = FacadeBean.getBusinessLogic();
			driverRides = facadeBL.getDriver(selectedUsername).getCreatedRides();
		} else {
			driverRides = new ArrayList<>();
		}
	}

	// Getters y setters
	public String getSelectedUsername() {
		return selectedUsername;
	}

	public void setSelectedUsername(String selectedUsername) {
		this.selectedUsername = selectedUsername;
	}

	public List<String> getDriverUsernames() {
		if (drivers == null) {
			loadDrivers(); // Asegurarse de que drivers esté cargado
		}

		if (dusernames == null) {
			dusernames = new ArrayList<>(); // Inicializar la lista si es null
		}
		dusernames.clear(); // Limpiar para evitar duplicados

		// Si drivers ya está cargado, generamos la lista de usernames
		for (User user : drivers) {
			dusernames.add(user.getUsername());
		}
		return dusernames;
	}

	public List<Ride> getDriverRides() {
		if (driverRides == null) {
			driverRides = new ArrayList<>();
		}
		return driverRides;
	}

	public void setDriverRides(List<Ride> driverRides) {
		this.driverRides = driverRides;
	}
}

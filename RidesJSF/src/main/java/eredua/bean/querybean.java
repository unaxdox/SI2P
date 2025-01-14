package eredua.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import businessLogic.BLFacade;
import businessLogic.FacadeBean;
import domain.Ride;

@ManagedBean(name = "queryBean")
@ViewScoped 
public class querybean {

	private String selectedOrigin;
	private String selectedDestination;
	private Date selectedDate = new Date();
	private List<String> origins;
	private List<String> destinations;
	private List<Date> availableDates;
	private List<Ride> rides;

	private BLFacade facadeBL;

	public querybean() {
		facadeBL = FacadeBean.getBusinessLogic();
		loadOrigins();
	}

	public void loadOrigins() {
		origins = facadeBL.getDepartCities();
	}

	public void loadDestinations() {
		if (selectedOrigin != null && !selectedOrigin.isEmpty()) {
			destinations = facadeBL.getDestinationCities(selectedOrigin);
		} else {
			destinations = new ArrayList<>();
		}
	}

	public void loadAvailableDates() {
		if (selectedOrigin != null && selectedDestination != null) {
			if (selectedDate == null) {
				selectedDate = new Date(); 
			}
			availableDates = facadeBL.getThisMonthDatesWithRides(selectedOrigin, selectedDestination, selectedDate);
		} else {
			availableDates = new ArrayList<>();
		}
	}

	public void loadRides() {
		// Verifica si todos los campos están establecidos antes de consultar los viajes
		if (selectedOrigin != null && selectedDestination != null && selectedDate != null) {
			rides = facadeBL.getRides(selectedOrigin, selectedDestination, selectedDate); // Consulta los viajes
		} else {
			rides = new ArrayList<>(); // Limpiar si no hay datos suficientes
		}
	}

	public String index() {
		return "traveler";
	}

	// Getters y setters
	public String getSelectedOrigin() {
		return selectedOrigin;
	}

	public void setSelectedOrigin(String selectedOrigin) {
		this.selectedOrigin = selectedOrigin;
		loadDestinations(); // Cargar destinos al cambiar el origen
	}

	public String getSelectedDestination() {
		return selectedDestination;
	}

	public void setSelectedDestination(String selectedDestination) {
		this.selectedDestination = selectedDestination;
		loadAvailableDates(); // Cargar fechas disponibles al cambiar el destino
	}

	public Date getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
		loadRides(); 
	}

	public List<String> getOrigins() {
		return origins;
	}

	public List<String> getDestinations() {
		return destinations;
	}

	public List<Date> getAvailableDates() {
		return availableDates;
	}

	public List<Ride> getRides() {
		return rides;
	}
}


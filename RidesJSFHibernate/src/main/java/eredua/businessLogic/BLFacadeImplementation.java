package eredua.businessLogic;

import java.util.Date;

import java.util.List;

import eredua.HibernateDataAccess;
import eredua.domeinua.*;
import eredua.exceptions.RideAlreadyExistException;
import eredua.exceptions.RideMustBeLaterThanTodayException;

/**
 * Implementation of the business logic using Hibernate.
 */
public class BLFacadeImplementation implements BLFacade {
	private HibernateDataAccess dbManager;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		dbManager = new HibernateDataAccess();
	}

	public BLFacadeImplementation(HibernateDataAccess da) {
		System.out.println("Creating BLFacadeImplementation instance with HibernateDataAccess parameter");
		dbManager = da;
	}

	@Override
	public List<String> getDepartCities() {
		return dbManager.getDepartCities();
	}

	@Override
	public List<String> getDestinationCities(String from) {
		return dbManager.getArrivalCities(from);
	}

	@Override
	public RideHibernate createRide(String from, String to, Date date, int nPlaces, float price, String driverName)
			throws RideMustBeLaterThanTodayException, RideAlreadyExistException {
		return dbManager.createRide(from, to, date, nPlaces, price, driverName);
	}

	@Override
	public List<RideHibernate> getRides(String from, String to, Date date) {
		return dbManager.getRides(from, to, date);
	}

	@Override
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		return dbManager.getThisMonthDatesWithRides(from, to, date);
	}

	@Override
	public void initializeBD() {
		dbManager.initializeDB();
	}

	@Override
	public UserHibernate getUser(String erab) {
		return dbManager.getUser(erab);
	}

	@Override
	public boolean isRegistered(String erab, String passwd) {
		return dbManager.isRegistered(erab, passwd);
	}

	@Override
	public String getMotaByUsername(String erab) {
		return dbManager.getMotabyUsername(erab);
	}

	@Override
	public boolean addDriver(String username, String password) {
		return dbManager.addDriver(username, password);
	}

	@Override
	public boolean addTraveler(String username, String password) {
		return dbManager.addTraveler(username, password);
	}
}

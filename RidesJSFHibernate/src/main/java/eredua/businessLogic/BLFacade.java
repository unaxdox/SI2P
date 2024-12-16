package eredua.businessLogic;

import java.util.Date;

import java.util.List;

import eredua.domeinua.*;
import eredua.exceptions.RideMustBeLaterThanTodayException;
import eredua.exceptions.RideAlreadyExistException;

/**
 * Interface that specifies the business logic.
 */
public interface BLFacade {

	/**
	 * Returns all the cities where rides depart
	 *
	 * @return collection of cities
	 */
	List<String> getDepartCities();

	/**
	 * Returns all the arrival destinations from all rides that depart from a given
	 * city
	 *
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	List<String> getDestinationCities(String from);

	/**
	 * Creates a ride for a driver
	 *
	 * @param from       the origin location of a ride
	 * @param to         the destination location of a ride
	 * @param date       the date of the ride
	 * @param nPlaces    available seats
	 * @param price      the price of the ride
	 * @param driverName the username of the driver
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today
	 * @throws RideAlreadyExistException         if the same ride already exists for
	 *                                           the driver
	 */
	RideHibernate createRide(String from, String to, Date date, int nPlaces, float price, String driverName)
			throws RideMustBeLaterThanTodayException, RideAlreadyExistException;

	/**
	 * Retrieves the rides from two locations on a given date
	 *
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date the date of the ride
	 * @return collection of rides
	 */
	List<RideHibernate> getRides(String from, String to, Date date);

	/**
	 * Retrieves the dates of a month for which there are rides
	 *
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date of the month for which days with rides want to be retrieved
	 * @return collection of rides
	 */
	List<Date> getThisMonthDatesWithRides(String from, String to, Date date);

	/**
	 * Calls the data access to initialize the database with some rides and users.
	 */
	void initializeBD();

	UserHibernate getUser(String erab);

	boolean isRegistered(String erab, String passwd);

	String getMotaByUsername(String erab);

	boolean addDriver(String username, String password);

	boolean addTraveler(String username, String password);
}

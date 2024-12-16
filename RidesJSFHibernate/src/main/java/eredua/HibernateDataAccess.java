package eredua;

import org.hibernate.Session;
import org.hibernate.Transaction;

import eredua.domeinua.*;
import eredua.exceptions.RideAlreadyExistException;
import eredua.exceptions.RideMustBeLaterThanTodayException;
//import configuration.ConfigXML;
import eredua.configuration.UtilDate;

//import java.io.File;
import java.util.*;

public class HibernateDataAccess {

//	private ConfigXML c = ConfigXML.getInstance();

	public HibernateDataAccess() {
//		if (c.isDatabaseInitialized()) {
//			String fileName = c.getDbFilename();
//			File fileToDelete = new File(fileName);
//			if (fileToDelete.delete()) {
//				File fileToDeleteTemp = new File(fileName + "$");
//				fileToDeleteTemp.delete();
//				System.out.println("File deleted");
//			} else {
//				System.out.println("Operation failed");
//			}
//		}
//		open();
//		if (c.isDatabaseInitialized()) {
//			initializeDB();
//		}
//
//		System.out.println("DataAccess created => isDatabaseLocal: " + c.isDatabaseLocal() + " isDatabaseInitialized: "
//				+ c.isDatabaseInitialized());
//
//		close();
		initializeDB();
	}

	public void initializeDB() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			DriverHibernate driver1 = new DriverHibernate("Urtzi", "123");
			DriverHibernate driver2 = new DriverHibernate("Zuri", "456");
			session.saveOrUpdate(driver1);
			session.saveOrUpdate(driver2);

			TravelerHibernate traveler1 = new TravelerHibernate("Unax", "789");
			TravelerHibernate traveler2 = new TravelerHibernate("Luken", "abc");
			session.saveOrUpdate(traveler1);
			session.saveOrUpdate(traveler2);

			Calendar cal = Calendar.getInstance();
			cal.set(2024, Calendar.DECEMBER, 20);
			Date date1 = UtilDate.trim(cal.getTime());

			cal.set(2024, Calendar.DECEMBER, 30);
			Date date2 = UtilDate.trim(cal.getTime());

			cal.set(2024, Calendar.DECEMBER, 10);
			Date date3 = UtilDate.trim(cal.getTime());

			cal.set(2024, Calendar.DECEMBER, 20);
			Date date4 = UtilDate.trim(cal.getTime());

			driver1.addRide("Donostia", "Madrid", date2, 5, 20); // ride1
			driver1.addRide("Irun", "Donostia", date2, 5, 2); // ride2
			driver1.addRide("Madrid", "Donostia", date3, 5, 5); // ride3
			driver1.addRide("Barcelona", "Madrid", date4, 0, 10); // ride4
			driver2.addRide("Donostia", "Hondarribi", date1, 5, 3); // ride5

			session.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public List<String> getDepartCities() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List departingCities = session.createQuery("SELECT DISTINCT r.to FROM RideHibernate r ORDER BY r.to").list();
		session.getTransaction().commit();

		return departingCities;
	}

	public List<String> getArrivalCities(String from) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List arrivingCities = session
				.createQuery("SELECT DISTINCT r.to FROM RideHibernate r WHERE r.from=:from ORDER BY r.to")
				.setParameter("from", from).list();
		session.getTransaction().commit();

		return arrivingCities;
	}

	public RideHibernate createRide(String from, String to, Date date, int nPlaces, float price, String driverName)
			throws RideMustBeLaterThanTodayException, RideAlreadyExistException {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		System.out.println(
				">> DataAccess: createRide=> from= " + from + " to= " + to + " driver=" + driverName + " date " + date);

		try {
			if (new Date().compareTo(date) > 0) {
				throw new RideMustBeLaterThanTodayException("The ride date must be later than today.");
			}

			DriverHibernate driver = (DriverHibernate) session
					.createQuery("FROM UserHibernate d WHERE d.username = :username")
					.setParameter("username", driverName).list().get(0);
			if (driver != null && driver.doesRideExists(from, to, date)) {
				transaction.commit();
				throw new RideAlreadyExistException("Ride already exists.");
			}

			RideHibernate ride = driver.addRide(from, to, date, nPlaces, price);
			session.persist(ride);
			transaction.commit();
			return ride;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return null;
		}
	}

	public List<RideHibernate> getRides(String from, String to, Date date) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List rides = session
				.createQuery("FROM RideHibernate r WHERE r.from=:from AND r.to=:to AND r.date=:date AND r.active=true")
				.setParameter("from", from).setParameter("to", to).setParameter("date", date).list();

		session.getTransaction().commit();

		return rides;
	}

	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getThisMonthActiveRideDates");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List<Date> res = new ArrayList<>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		List dates = session.createQuery(
				"SELECT DISTINCT r.date FROM RideHibernate r WHERE r.from = :from AND r.to = :to AND r.date BETWEEN :startDate AND :endDate AND r.active = true")
				.setParameter("from", from).setParameter("to", to).setParameter("startDate", firstDayMonthDate)
				.setParameter("endDate", lastDayMonthDate).list();
		res.addAll(dates);

		session.getTransaction().commit();
		return res;
	}

	public void open() {
		System.out.println("DataAccess opened");
	}

	public void close() {
		HibernateUtil.getSessionFactory().close();
		System.out.println("DataAccess closed");
	}

	public UserHibernate getUser(String erab) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List user = session.createQuery("FROM UserHibernate u WHERE u.username = :username")
				.setParameter("username", erab).list();
		session.getTransaction().commit();

		return user.isEmpty() ? null : (UserHibernate) user.get(0);
	}

	public boolean isRegistered(String erab, String passwd) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List travelerCount = session
				.createQuery(
						"SELECT COUNT(t) FROM TravelerHibernate t WHERE t.username = :username AND t.passwd = :passwd")
				.setParameter("username", erab).setParameter("passwd", passwd).list();

		List driverCount = session
				.createQuery(
						"SELECT COUNT(d) FROM DriverHibernate d WHERE d.username = :username AND d.passwd = :passwd")
				.setParameter("username", erab).setParameter("passwd", passwd).list();

		session.getTransaction().commit();
		return travelerCount != null || driverCount != null;
	}

	public String getMotabyUsername(String erab) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List driverResultList = session.createQuery("SELECT d.mota FROM DriverHibernate d WHERE d.username = :username")
				.setParameter("username", erab).list();

		List travelerResultList = session
				.createQuery("SELECT t.mota FROM TravelerHibernate t WHERE t.username = :username")
				.setParameter("username", erab).list();

		session.getTransaction().commit();

		if (!driverResultList.isEmpty()) {
			return (String) driverResultList.get(0);
		} else if (!travelerResultList.isEmpty()) {
			return (String) travelerResultList.get(0);
		} else {
			return "Admin";
		}
	}

	public boolean addDriver(String username, String password) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			List drivers = session.createQuery("FROM DriverHibernate d WHERE d.username = :username")
					.setParameter("username", username).list();
			if (!drivers.isEmpty()) {
				return false;
			}
			DriverHibernate driver = new DriverHibernate(username, password);
			session.persist(driver);
			transaction.commit();
			return true;
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	public boolean addTraveler(String username, String password) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			List travelers = session.createQuery("FROM TravelerHibernate t WHERE t.username = :username")
					.setParameter("username", username).list();
			if (!travelers.isEmpty()) {
				return false;
			}
			TravelerHibernate traveler = new TravelerHibernate(username, password);
			session.persist(traveler);
			transaction.commit();
			return true;
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

}

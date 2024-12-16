package eredua.domeinua;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;

@Entity
@DiscriminatorValue("DRIVER")
public class DriverHibernate extends UserHibernate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<RideHibernate> createdRides = new Vector<RideHibernate>();

	public DriverHibernate(String username, String passwd) {
		super(username, passwd, "Driver");
	}

	public String toString() {
		return (super.toString());
	}

	public List<RideHibernate> getCreatedRides() {
		return createdRides;
	}

	public void setCreatedRides(List<RideHibernate> createdRides) {
		this.createdRides = createdRides;
	}

	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual
	 * profit
	 * 
	 * @param question   to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public RideHibernate addRide(String from, String to, Date date, int nPlaces, float price) {
		RideHibernate ride = new RideHibernate(from, to, date, nPlaces, price, this);
		createdRides.add(ride);
		return ride;
	}

	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location
	 * @param to   the destination location
	 * @param date the date of the ride
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(String from, String to, Date date) {
		for (RideHibernate r : createdRides)
			if ((java.util.Objects.equals(r.getFrom(), from)) && (java.util.Objects.equals(r.getTo(), to))
					&& (java.util.Objects.equals(r.getDate(), date)))
				return true;

		return false;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);

	}

	public RideHibernate removeRide(String from, String to, Date date) {
		RideHibernate r = null;
		int pos = 0;
		int index = 0;
		boolean encontrado = false;
		for (RideHibernate ride : createdRides) {
			// if (ride.getFrom().equals(from) && ride.getTo().equals(to) &&
			// ride.getDate().equals(date)) {
			if ((ride.getFrom() == from) && (ride.getTo() == to) && (ride.getDate().equals(date))) {

				encontrado = true;
				pos = index;
			}
			index++;
		}
		if (encontrado) {
			System.out.println("posicion " + pos);
			r = createdRides.get(pos);
			System.out.println("ride recuperado " + r);
			createdRides.remove(pos);
		}
		return r;
	}

}
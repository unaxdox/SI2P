package eredua.domeinua;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TRAVELER")
public class TravelerHibernate extends UserHibernate implements Serializable {
	private static final long serialVersionUID = 1L;


	public TravelerHibernate(String username, String passwd) {
		super(username, passwd, "Traveler");
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
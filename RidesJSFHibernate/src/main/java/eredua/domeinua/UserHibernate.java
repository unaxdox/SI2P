package eredua.domeinua;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class UserHibernate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String username;
	private String passwd;
	private String mota;

	public UserHibernate(String username, String passwd, String mota) {
		this.username = username;
		this.passwd = passwd;
		this.mota = mota;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String email) {
		this.username = email;
	}

	public String getPassword() {
		return passwd;
	}

	public void setPassword(String name) {
		this.passwd = name;
	}

	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = mota;
	}

	public String toString() {
		return username + ";" + passwd;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserHibernate other = (UserHibernate) obj;
		if (username != other.username)
			return false;
		return true;
	}

}
package eredua.bean;

import eredua.businessLogic.BLFacade;

import eredua.businessLogic.BLFacadeImplementation;
import eredua.HibernateDataAccess;

public class FacadeBeanHibernate {
	@SuppressWarnings("unused")
	private static FacadeBeanHibernate singleton = new FacadeBeanHibernate();
	private static BLFacade facadeInterface;

	private FacadeBeanHibernate() {
		try {
			facadeInterface = new BLFacadeImplementation(new HibernateDataAccess());
		} catch (Exception e) {
			System.out.println("FacadeBean: negozioaren logika sortzean errorea: " + e.getMessage());
		}
	}

	public static BLFacade getBusinessLogic() {
		return facadeInterface;
	}
}
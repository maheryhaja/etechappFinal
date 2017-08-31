package mg.etech.mobile.etechapp.contrainte.factory;


import java.util.Collection;
import java.util.List;

/**
 * Interface generique pour la fabrique de conversion d'un domainobject en data
 * transfer object
 * 
 * @author Matelli
 * 
 * @param <DO>
 *            domainobject
 * @param <DTO>
 *            dto
 */
public interface DtoFactory<DO, DTO> {

	/**
	 * retourne une instance d'un data transfer object
	 * 
	 * @return
	 */
	DTO getInstance();

	/**
	 * convertit un domainobject en data transfer object
	 * 
	 * @param dObj
	 * @return
	 */
	DTO getInstance(DO dObj);

	/**
	 * convertit une liste de domainobject en data transfer object
	 * 
	 * @param dObjs
	 * @return
	 */
	List<DTO> getInstance(Collection<DO> dObjs);
}

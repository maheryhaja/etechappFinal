package mg.etech.mobile.etechapp.repository.commun;

import java.util.List;
import java.util.Map;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;

/**
 * @author Mahmoud Salim Bouyahyaoui
 * @param <C>
 */
public interface CommunRepository<C, K> {
	/**
	 * Generic method used to get all objects of a particular type. This is the
	 * same as lookup up all rows in a table.
	 * 
	 * @return List of populated objects
	 */
	List<C> findAll();

	/**
	 * Generic method used to get all objects by page of a particular type. This
	 * is the same as lookup up all rows in a table.
	 * 
	 * @param numeroPage
	 *            indice de la page a recupere commence a 0 , [0 .... ]
	 * @param nombreDeLignesParPage
	 *            nombre de ligne pas page , ( le pas , l offset )
	 * 
	 * @return List of populated objects
	 */
	List<C> findAllPagination(int numeroPage, int nombreDeLignesParPage);

	/**
	 * Generic method to get an object based on class and identifier. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param id
	 *            the identifier (primary key) of the class
	 * @return a populated object
	 * @see org.springframework.orm.ObjectRetrievalFailureException
	 */
	C findById(final K id);

	/**
	 * Generic method to get all objects based on criteria. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param criteria
	 *            Map with criteria
	 * @return a populated object
	 * @see org.springframework.orm.ObjectRetrievalFailureException
	 */
	List<C> findAllByCriteria(final Map<String, Object> criteria);

	/**
	 * Generic method to add an object.
	 * 
	 * @param entity
	 *            the object to add
	 */
	void insert(final C entity);

	/**
	 * Generic method to add list of object using batch
	 * 
	 * @param listeObjectToInsert
	 */
	void insertBatch(final List<C> listeObjectToInsert);

	/**
	 * Generic method to update an object.
	 * 
	 * @param entity
	 *            the object to save
	 */
	void update(final C entity);

	/**
	 * Test existence data from database
	 * 
	 */
	boolean exists(final K id);

	/**
	 * Generic method to delete an object based on class and id
	 * 
	 * @param entity
	 *            the identifier (primary key) of the class
	 */
	void delete(final K id);

	/**
	 * Generic method to delete an object based on class and id
	 * 
	 * @param entity
	 *            the identifier (primary key) of the class
	 */
	// void delete(final C entity);

	/**
	 * Remove all data from database
	 * 
	 */
	void deleteAll();

	/**
	 * Generic method used to count all objects a particular type. This is the
	 * same as lookup up all rows in a table.
	 * 
	 * @return number of rows in a table
	 */
	Long countAllRows();

	/**
	 * for conveniance
	 * 
	 * @return
	 */
	ConnectionSource getConnectionSource();

	RuntimeExceptionDao<C, K> getDao();
	
	

}

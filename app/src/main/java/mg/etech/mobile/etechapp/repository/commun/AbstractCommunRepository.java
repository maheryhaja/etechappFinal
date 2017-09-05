package mg.etech.mobile.etechapp.repository.commun;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import mg.etech.mobile.etechapp.commun.exception.TechnicalException;


/**
 * Classe abstraite pour la manipulation des entites dans la base de donnees
 * 
 * @param <C>
 * @param <K>
 */
public class AbstractCommunRepository<C, K> implements CommunRepository<C, K> {

	protected static DatabaseHelper databaseHelper = null;

	protected transient RuntimeExceptionDao<C, K> abstractRunTimeExceptionDao = null;

	public static final String INSERT_ERROR_MESSAGE = "la valeur n'a pu être insérée";
	public static final String UPDATE_ERROR_MESSAGE = "la valeur n'a pu être mise à jour";
	public static final String DELETE_ERROR_MESSAGE = "la valeur n'a pu être supprimée";

	public AbstractCommunRepository() {

	}

	protected void initDatabase(Context context) {
		if (databaseHelper != null) {
			return;
		}

		databaseHelper = new DatabaseHelper(context);
	}

	/**
	 * {@link CommunRepository#insertBatch(List)}
	 */
	@Override
	public void insertBatch(final List<C> listeObjectToInsert) {
		abstractRunTimeExceptionDao.callBatchTasks(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				for (C c : listeObjectToInsert) {
					insert(c);
				}
				return null;
			}
		});

	}

	/**
	 * {@link CommunRepository#findAll()}
	 */
	@Override
	public List<C> findAll() {
		return abstractRunTimeExceptionDao.queryForAll();
	}

	/**
	 * {@link CommunRepository#findAllByCriteria(Map)}
	 */
	@Override
	public List<C> findAllByCriteria(final Map<String, Object> criteria) {
		return abstractRunTimeExceptionDao.queryForFieldValues(criteria);
	}

	/**
	 * {@link CommunRepository#insert(Object)}
	 */
	@Override
	public void insert(C entity) {
		if (entity != null) {
			if (abstractRunTimeExceptionDao.create(entity) == 0) {
				throw new TechnicalException(INSERT_ERROR_MESSAGE
						+ entity.toString());
			}
		}
	}

	/**
	 * {@link CommunRepository#update(Object)}
	 */
	@Override
	public void update(C entity) {
		if (entity != null) {
			if (abstractRunTimeExceptionDao.update(entity) == 0) {
				throw new TechnicalException(UPDATE_ERROR_MESSAGE
						+ entity.toString());
			}
		}
	}

	/**
	 * {@link CommunRepository#findAllPagination(int, int)}
	 */
	@Override
	public List<C> findAllPagination(int numeroPage, int nombreDeLignesParPage) {
		QueryBuilder<C, K> queryBuilder = abstractRunTimeExceptionDao
				.queryBuilder();

		try {
			queryBuilder.limit(Long.valueOf(nombreDeLignesParPage)).offset(
					Long.valueOf((numeroPage - 1) * nombreDeLignesParPage));

			PreparedQuery<C> preparedQuery = queryBuilder.prepare();
			return abstractRunTimeExceptionDao.query(preparedQuery);

		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

	/**
	 * {@link CommunRepository#findById(Object)}
	 */
	@Override
	public C findById(K id) {
		return abstractRunTimeExceptionDao.queryForId(id);
	}

	/**
	 * {@link CommunRepository#exists(Object)}
	 */
	@Override
	public boolean exists(K id) {
		return abstractRunTimeExceptionDao.idExists(id);
	}

	/**
	 * {@link CommunRepository#delete(Object)}
	 */
	@Override
	public void delete(K id) {
		if (abstractRunTimeExceptionDao.deleteById(id) == 0) {
			throw new TechnicalException(DELETE_ERROR_MESSAGE + id.toString());
		}
	}

	/**
	 * {@link CommunRepository#deleteAll()}
	 */
	@Override
	public void deleteAll() {
		try {
			abstractRunTimeExceptionDao.deleteBuilder().delete();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@link CommunRepository#countAllRows()}
	 */
	@Override
	public Long countAllRows() {
		return abstractRunTimeExceptionDao.countOf();

	}

	@Override
	public ConnectionSource getConnectionSource() {
		return databaseHelper.getConnectionSource();
	}

	public RuntimeExceptionDao<C, K> getDao() {
		return abstractRunTimeExceptionDao;
	}

	
}

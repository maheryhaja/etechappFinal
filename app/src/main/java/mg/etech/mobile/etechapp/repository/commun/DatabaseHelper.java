package mg.etech.mobile.etechapp.repository.commun;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import mg.etech.mobile.etechapp.commun.exception.TechnicalException;
import mg.etech.mobile.etechapp.donnee.domainobject.Pole;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	// name of the database file for your application
	public static final String DATABASE_NAME = "etechapp.sqlite";
	
	// any time you make changes to your database objects, you may have to
	// increase the database version
	private static final int DATABASE_VERSION = 1;
	
	protected Context context;


	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		this.context=context;
       
		
	}

	

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		try {
//		create tables here	TableUtils.createTable(connectionSource,Users.class);

			TableUtils.createTable(connectionSource, Pole.class);
		}
	catch (SQLException e) {
		throw new TechnicalException(e);
	} catch (java.sql.SQLException e) {
		throw new TechnicalException(e);
	}
	}
	

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
//			TableUtils.dropTable(connectionSource, Users.class, false); drop Table here

			TableUtils.dropTable(connectionSource, Pole.class, false);
			onCreate(db);
		} catch(Exception ex) {
			throw new TechnicalException(ex);
		}
		
	}
	
}

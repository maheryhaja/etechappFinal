package mg.etech.mobile.etechapp.commun.utils.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;


import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;
import org.androidannotations.annotations.RootContext;

import mg.etech.mobile.etechapp.R;


/**
 * Classe singleton pour la gestion des preferences
 * 
 * @author Matelli
 * 
 */
@EBean(scope = Scope.Singleton)
public class PreferencesSingleton {

	@RootContext
	protected Context mApplicationContext;
	private SharedPreferences mSettings;

	private String mPreferencesFileName = null;
	private String mPreferencesStringEmpty = null;
	private final boolean defaultBoolValue = false;
	private final long defaultLongValue = 0;

	@SuppressLint("InlinedApi")
	@AfterInject
	public void init() {
		if (mPreferencesFileName == null)
			mPreferencesFileName = mApplicationContext
					.getString(R.string.sharedPreferencesName);
		if (mPreferencesStringEmpty == null)
			mPreferencesStringEmpty = mApplicationContext
					.getString(R.string.defaultStringEmpty);

		mSettings = mApplicationContext.getSharedPreferences(
				mPreferencesFileName, Context.MODE_PRIVATE);
	}

	/**
	 * Supprimer une preference
	 * 
	 * @param key
	 *            Cle de la preference a supprimer
	 */
	public void removeObjectForKey(String key) {
		SharedPreferences.Editor prefEditor = mSettings.edit();
		prefEditor.remove(key);
		prefEditor.commit();
	}

	/**
	 * Tester si une preference existe
	 * 
	 * @param key
	 *            Cle de la preference a tester l'existence
	 * @return true si la preference existe, false sinon
	 */
	public boolean contains(String key) {
		if (mSettings.contains(key)) {
			return true;
		}
		return false;
	}

	/**
	 * Ajouter une preference de type {@link String}
	 * 
	 * @param key
	 *            Cle de la preference a ajouter
	 * @param value
	 *            Valeur de la preference a ajouter
	 */
	public void setString(String key, String value) {
		SharedPreferences.Editor prefEditor = mSettings.edit();
		prefEditor.putString(key, value);
		prefEditor.commit();
	}

	/**
	 * Recuperer une preference de type {@link String}
	 * 
	 * @param key
	 *            Cle de la preference a recuperer
	 * @return Valeur de la preference ayant pour cle key, si la cle n'existe
	 *         pas c'est la valeur empty qui est retourne
	 */
	public String getString(String key) {
		return mSettings.getString(key, mPreferencesStringEmpty);
	}

	/**
	 * Recuperer une preference de type {@link String}
	 * 
	 * @param key
	 *            Cle de la preference a recuperer
	 * @param defaultValue
	 *            Valeur de retour si la cle n'existe pas
	 * @return Valeur de la preference ayant pour cle key, si la cle n'existe
	 *         pas c'est la valeur defaultValue qui est retourne
	 */
	public String getString(String key, String defaultValue) {
		return mSettings.getString(key, defaultValue);
	}

	/**
	 * Ajouter un {@link Boolean} dans les preferences
	 * 
	 * @param key
	 *            Cle du boolean a ajouter
	 * @param value
	 *            Valeur du boolean a ajouter
	 */
	public void setBool(String key, boolean value) {
		SharedPreferences.Editor prefEditor = mSettings.edit();
		prefEditor.putBoolean(key, value);
		prefEditor.commit();
	}

	/**
	 * Recuperer un {@link Boolean} contenu dans le preferences ayant pour cle
	 * key
	 * 
	 * @param key
	 *            Cle du boolean a recuperer
	 * @return Valeur du boolean contenu dans les preferences. Si la cle
	 *         n'existe pas, false est retourne
	 */
	public boolean getBool(String key) {
		boolean result = defaultBoolValue;
		if (mSettings.contains(key)) {
			result = mSettings.getBoolean(key, defaultBoolValue);
		}
		return result;
	}

	/**
	 * Recuperer un {@link Boolean} contenu dans le preferences ayant pour cle
	 * key
	 * 
	 * @param key
	 *            Cle du boolean a recuperer
	 * @param defaultValue
	 *            Valeur de retour si la cle key n'existe pas
	 * @return Valeur du boolean contenu dans les preferences. Si la cle
	 *         n'existe pas, defaultValue est retourne
	 */
	public boolean getBool(String key, boolean defaultValue) {
		boolean result = defaultValue;
		if (mSettings.contains(key)) {
			result = mSettings.getBoolean(key, defaultValue);
		}
		return result;
	}

	/**
	 * Ajouter un {@link Long} dans les preferences
	 * 
	 * @param key
	 *            Cle du long a ajouter
	 * @param value
	 *            Valeur du long a ajouter
	 */
	public void setLong(String key, long value) {
		SharedPreferences.Editor prefEditor = mSettings.edit();
		prefEditor.putLong(key, value);
		prefEditor.commit();
	}

	/**
	 * Recuperer un {@link Long} contenu dans le preferences ayant pour cle key
	 * 
	 * @param key
	 *            Cle du long a recuperer
	 * @return Valeur du long contenu dans les preferences. Si la cle n'existe
	 *         pas, 0L est retourne
	 */
	public long getLong(String key) {
		long result = defaultLongValue;
		if (mSettings.contains(key)) {
			result = mSettings.getLong(key, defaultLongValue);
		}
		return result;
	}

	/**
	 * Recuperer un {@link Long} contenu dans le preferences ayant pour cle key
	 * 
	 * @param key
	 *            Cle du long a recuperer
	 * @param defaultValue
	 *            Valeur de retour si la cle key n'existe pas
	 * @return Valeur du long contenu dans les preferences. Si la cle n'existe
	 *         pas, defaultValue est retourne
	 */
	public long getLong(String key, long defaultValue) {
		long result = defaultValue;
		if (mSettings.contains(key)) {
			result = mSettings.getLong(key, defaultValue);
		}
		return result;
	}

	/**
	 * Ajouter un {@link Integer} dans les preferences
	 * 
	 * @param key
	 *            Cle du integer a ajouter
	 * @param value
	 *            Valeur du integer a ajouter
	 */
	public void setInt(String key, int value) {
		SharedPreferences.Editor prefEditor = mSettings.edit();
		prefEditor.putInt(key, value);
		prefEditor.commit();
	}

	/**
	 * Ajouter un {@link Integer} dans les preferences
	 * 
	 * @param resId
	 *            Cle du integer a ajouter. La cle se trouve dans les ressources
	 * @param value
	 *            Valeur du integer a ajouter
	 */
	public void setInt(int resId, int value) {
		SharedPreferences.Editor prefEditor = mSettings.edit();
		String key = mApplicationContext.getString(resId);
		prefEditor.putInt(key, value);
		prefEditor.commit();
	}

	/**
	 * Recuperer un {@link Integer} contenu dans le preferences ayant pour cle
	 * key
	 * 
	 * @param key
	 *            Cle du integer a recuperer
	 * @param defaultValue
	 *            Valeur de retour si la cle key n'existe pas
	 * @return Valeur du integer contenu dans les preferences. Si la cle
	 *         n'existe pas, 0 est retourne
	 */
	public int getInt(String key) {
		int result = (int) defaultLongValue;
		if (mSettings.contains(key)) {
			result = mSettings.getInt(key, (int) defaultLongValue);
		}
		return result;
	}
	public int getIntOther(String key) {
		int result = -1;
		if (mSettings.contains(key)) {
			result = mSettings.getInt(key,-1);
		}
		return result;
	}
	/**
	 * Recuperer un {@link Integer} contenu dans le preferences ayant pour cle
	 * resId
	 * 
	 * @param resId
	 *            Cle du integer a recuperer. La cle se trouve dans les
	 *            ressources
	 * @return Valeur du integer contenu dans les preferences. Si la cle
	 *         n'existe pas, 0 est retourne
	 */
	public int getInt(int resId) {
		int result = (int) defaultLongValue;
		String key = mApplicationContext.getString(resId);
		if (mSettings.contains(key)) {
			result = mSettings.getInt(key, (int) defaultLongValue);
		}
		return result;
	}

	/**
	 * Recuperer un {@link Integer} contenu dans le preferences ayant pour cle
	 * key
	 * 
	 * @param key
	 *            Cle du integer a recuperer
	 * @param defaultValue
	 *            Valeur retourne si la cle n'existe pas
	 * @return Valeur du integer contenu dans les preferences. Si la cle
	 *         n'existe pas, defaultValue est retourne
	 */
	public int getInt(String key, int defaultValue) {
		int result = defaultValue;
		if (mSettings.contains(key)) {
			result = mSettings.getInt(key, defaultValue);
		}
		return result;
	}

	/**
	 * Recuperer un {@link Integer} contenu dans le preferences ayant pour cle
	 * resId
	 * 
	 * @param resId
	 *            Cle du integer a recuperer
	 * @param defaultValue
	 *            Valeur retourne si la cle n'existe pas
	 * @return Valeur du integer contenu dans les preferences. Si la cle
	 *         n'existe pas, defaultValue est retourne
	 */
	public int getInt(int resId, int defaultValue) {
		int result = (int) defaultLongValue;
		String key = mApplicationContext.getString(resId);
		if (mSettings.contains(key)) {
			result = mSettings.getInt(key, defaultValue);
		}
		return result;
	}

	
}

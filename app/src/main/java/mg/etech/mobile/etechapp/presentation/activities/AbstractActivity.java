package mg.etech.mobile.etechapp.presentation.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.utils.validator.Rule;
import mg.etech.mobile.etechapp.commun.utils.validator.Validator;
import mg.etech.mobile.etechapp.commun.utils.validator.Validator.ValidationListener;

/**
 * 
 * Classe abstraite dont herite tous les activity
 * 
 */
@EActivity
public abstract class AbstractActivity extends FragmentActivity implements ValidationListener {

	protected Validator validator;

	@Override
	public void onValidationSucceeded() {
		validationSucceeded();
	}

	@Override
	public void onValidationFailed(View failedView, Rule<?> failedRule) {
		String message = failedRule.getFailureMessage();
		if (failedView instanceof EditText) {
			failedView.requestFocus();
			((EditText) failedView).setError(message);
			((EditText) failedView).requestFocus();
		} else if (failedView instanceof TextView) {
			failedView.requestFocus();
			((TextView) failedView).setError(message);
			((TextView) failedView).requestFocus();
		} else {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		}
	}

	public abstract void validationSucceeded();
	
	/**
	 * Afficher un message dans un Toast
	 * 
	 * @param message
	 *            Message a afficher
	 */
	public void showToast(String message) {
		Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Afficher un message dans une boite de dialogue
	 * 
	 * @param context
	 * @param idRestitre
	 * @param idResMessage
	 * @param onClick
	 */
	public void showAlert(Context context, int idRestitre, int idResMessage,
			OnClickListener onClick) {
		showAlert(context, idRestitre,
				context.getResources().getString(idResMessage), onClick);
	}

	/**
	 * Afficher un message dans une boite de dialogue
	 * 
	 * @param context
	 * @param idRestitre
	 * @param message
	 * @param onClick
	 */
	public void showAlert(Context context, int idRestitre, String message,
			OnClickListener onClick) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(idRestitre);
		alertDialog.setMessage(message);
		if (onClick == null) {
			alertDialog.setButton(
					context.getResources().getString(R.string.ok),
					new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});
		} else {
			alertDialog.setButton(
					context.getResources().getString(R.string.ok), onClick);
		}

		alertDialog.show();
	}

	public void hideKeyboard() {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
		View view = getCurrentFocus();
		if (view == null) {
			view = new View(this);
		}
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
}

package mg.etech.mobile.etechapp.presentation.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.EFragment;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.utils.validator.Rule;
import mg.etech.mobile.etechapp.commun.utils.validator.Validator;
import mg.etech.mobile.etechapp.commun.utils.validator.Validator.ValidationListener;
/**
 * Classe abstraite gerant la validation des champs de formulaire dans le popup
 * 
 * @author Solo
 * 
 */
@EFragment
public abstract class DialogFragmentWithValidator extends DialogFragment implements ValidationListener{
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
			Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
		}
	}
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

	public void showAlert(Context context, int idRestitre, int idResMessage,
			OnClickListener onClick) {
		showAlert(context, idRestitre,
				context.getResources().getString(idResMessage), onClick);
	}
	public abstract void validationSucceeded();
}

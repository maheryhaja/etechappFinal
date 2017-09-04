package mg.etech.mobile.etechapp.presentation.fragments;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mg.etech.mobile.etechapp.commun.utils.validator.Rule;
import mg.etech.mobile.etechapp.commun.utils.validator.Validator;
import mg.etech.mobile.etechapp.commun.utils.validator.Validator.ValidationListener;

/**
 * Classe abstraite gerant la validation des champs de formulaire
 * 
 * @author Solo
 * 
 */
public abstract class AbstractFragmentWithValidator extends AbstractFragment
		implements ValidationListener {

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

	public abstract void validationSucceeded();
	// Method to start the service


}

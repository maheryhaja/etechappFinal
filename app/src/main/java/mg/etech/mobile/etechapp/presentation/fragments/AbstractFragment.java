package mg.etech.mobile.etechapp.presentation.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.utils.IBackCallback;




public abstract class AbstractFragment extends Fragment {

	protected AppCompatActivity pActivity;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		pActivity = (AppCompatActivity) activity;
		
	}

	/**
	 * Equivalent of {@link Activity#findViewById(int)}, but the search is only
	 * in the fragment's view
	 * 
	 * @param id
	 *            Id of the view to find
	 * @return View found
	 */
	public View findViewById(int id) {
		// We find the selected id inside the fragment's view
		return getView().findViewById(id);
	}

	/**
	 * @return the MainActivity (parent of the AbstractFragment)
	 * 
	 *         public MainActivity getMainActivity() { // We return the casted
	 *         activity return (MainActivity) getActivity(); }
	 */

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * @return Context of the fragment
	 */
	public Context getContext() {
		// We simply return the activity
		return getActivity();
	}

	/**
	 * Equivalent of {@link Activity#onBackPressed()}
	 * 
	 * @return TRUE if you consume the BACK press, else return FALSE
	 */
	public boolean onBackPressed() {
		if (this instanceof IBackCallback) {
			((IBackCallback) this).setStateOnBack();
			return true;
		}
		return false;
	}

	/**
	 * Hide the soft keyboard
	 * 
	 * @param editText
	 *            EditText used by the keyboard
	 */
	protected void hideKeyBoardFromEditText(EditText editText) {
		if (editText == null) {
			return;
		}
		// We hide the software keyboard
		((InputMethodManager) getContext().getSystemService(
				Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
				editText.getWindowToken(), 0);
		// We remove the focus from the editText
		editText.clearFocus();
	}

	protected void hideKeyboard(Activity activity) {
		if (activity == null)
			return;
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		View view = activity.getCurrentFocus();
		if (view == null) {
			view = new View(activity);
		}
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public void showFragment(Fragment fragment, int id, String tag) {
		if (fragment == null)
			return;

		FragmentTransaction t = getActivity().getSupportFragmentManager()
				.beginTransaction();
		t.add(id, fragment, tag);
		t.commit();
	}
	public void showOtherFragment(Fragment fragment, int id, String tag) {
		if (fragment == null)
			return;

		FragmentTransaction t = getActivity().getSupportFragmentManager()
				.beginTransaction();
		t.replace(id, fragment, tag);
		t.commit();
	}
	public void goToFragment(Fragment fragment, int id, String tag) {
		if (fragment == null) {
			return;
		}

		FragmentTransaction t = getActivity().getSupportFragmentManager()
				.beginTransaction();
		t.replace(id, fragment);
		t.addToBackStack(tag);
		t.commit();
	}

	public void removeFragment(Fragment fragment) {
		if (fragment == null)
			return;
		FragmentTransaction t = getActivity().getSupportFragmentManager()
				.beginTransaction();
		t.remove(fragment);
		t.commit();
	}

	@Override
	public void onStop() {
		hideKeyboard(getActivity());
		super.onStop();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	public void replaceFragment(Fragment fragment, int idLayout) {
		replaceFragment(fragment, idLayout, true);
	}
	
	public void replaceFragment(Fragment fragment, int idLayout,
			boolean isAddToBack) {
		if (getActivity() == null)
			return;

		FragmentTransaction t = getActivity().getSupportFragmentManager()
				.beginTransaction();
		t.replace(idLayout, fragment);
		if (isAddToBack) {
			t.addToBackStack(null);
		}
		t.commit();
	}
	public void replaceFragmentTag(Fragment fragment, int idLayout,
			boolean isAddToBack,String tag) {
		if (getActivity() == null)
			return;

		FragmentTransaction t = getActivity().getSupportFragmentManager()
				.beginTransaction();
		t.replace(idLayout, fragment,tag);
		if (isAddToBack) {
			t.addToBackStack(null);
		}
		t.commit();
	}
	public void showFragment(Fragment fragment, int id) {
		if (fragment == null)
			return;

		FragmentTransaction t = getActivity().getSupportFragmentManager()
				.beginTransaction();
		t.add(id, fragment);
		t.addToBackStack(null);
		t.commit();
	}
	public void popBackStackFragment() {
		if (getFragmentManager() != null
				&& getFragmentManager().getBackStackEntryCount() > 0) {
			getFragmentManager().popBackStack();
			if (this instanceof IBackCallback) {
				((IBackCallback) this).setStateOnBack();
			}
		}
	}

	
	/**
	 * Launch search request version 1
	 * 
	 * @param editText
	 * @param idContainer
	 *            -1 if container of {@see SearchFragment} is
	 *            R.id.content_protocoles
	 */
	/*public void launchSearchRequest(EditText editText, int idContainer) {
		if (editText == null)
			return;
		String searchRequest = editText.getText().toString();
		if (searchRequest.length() != 0) {
			SearchFragment f = new SearchFragment_();
			f.putQuery(searchRequest);
			if (idContainer == -1) {
				replaceFragment(f, R.id.content_protocoles);
			} else {
				replaceFragment(f, idContainer);
			}
			// editText.setText("");
		}
	}*/

	
	
	public void removeFragmentById(int idFragment) {
		if (getActivity() == null)
			return;
		Fragment f = getActivity().getSupportFragmentManager()
				.findFragmentById(idFragment);
		if (f != null) {
			FragmentTransaction transaction = getActivity()
					.getSupportFragmentManager().beginTransaction();
			transaction.remove(f);
			transaction.commit();
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

	// Send an Intent with an action .
	protected void sendMessage(String action) {
		Intent intent = new Intent(action);
		LocalBroadcastManager.getInstance(pActivity).sendBroadcast(intent);
	}
	
	public AbstractFragment getChildFragment(){
		return null;
	}
	
	
}

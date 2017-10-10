package mg.etech.mobile.etechapp.commun.utils.date.datepicker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Rect;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import mg.etech.mobile.etechapp.commun.constante.SimpleDate;
import mg.etech.mobile.etechapp.commun.utils.TypefaceEditText;
import mg.etech.mobile.etechapp.commun.utils.date.SimpleDateUtils;

/**
 * Created by mahery.haja on 14/09/2017.
 */

//doit etre utilisé dans une activity support
public class SimpleDatePicker extends TypefaceEditText implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private FragmentManager fragmentManager;
    final Calendar calendar = Calendar.getInstance();
    private Context context;
    private Date date;

    private DatePickerDialog datePickerDialog;

    public SimpleDatePicker(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        context = ctx;
        this.fragmentManager = ((AppCompatActivity) ctx).getSupportFragmentManager();
        this.setOnClickListener(this);
        datePickerDialog = new DatePickerDialog(context, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void showDatePickerDialog() {

    }



    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Log.d("mahery-haja", day + "//" + month + "//" + year);
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        date = cal.getTime();
        setText(SimpleDateUtils.format(date, SimpleDate.GENERAL_DATE_PATTERN));
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        setText(SimpleDateUtils.format(date, SimpleDate.GENERAL_DATE_PATTERN));
    }

    @Override
    public void onClick(View view) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        datePickerDialog.show();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            onClick(this);
        }
    }
}

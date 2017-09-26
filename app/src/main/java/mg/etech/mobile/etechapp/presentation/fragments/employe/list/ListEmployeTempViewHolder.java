package mg.etech.mobile.etechapp.presentation.fragments.employe.list;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import mg.etech.mobile.etechapp.commun.simpleserializer.OperationType;

/**
 * Created by mahery.haja on 21/09/2017.
 */

public class ListEmployeTempViewHolder extends ListEmployeViewHolder {


    protected String operationName = OperationType.CREATE;
    public ListEmployeTempViewHolder(View view, FlexibleAdapter adapter) {
        super(view, adapter);
    }

    public ListEmployeTempViewHolder(View view, FlexibleAdapter adapter, String operationName) {
        super(view, adapter);
        this.operationName = operationName;
    }

    @Override
    public void setPhoto(String photourl) {
        byte[] decodeString = Base64.decode(photourl, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);

        if (bitmap != null) {
            this.imageViewPhoto.setImageBitmap(bitmap);
        } else {
            Log.d("mahery-haja", "erreur de transformation bitmap");
        }
    }
}
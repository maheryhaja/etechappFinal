package mg.etech.mobile.etechapp.presentation.fragments.employe.list;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.simpleserializer.OperationType;

/**
 * Created by mahery.haja on 21/09/2017.
 */

public class ListEmployeTempViewHolder extends ListEmployeViewHolder {


    protected String operationName = OperationType.CREATE;
    protected ImageView imgOperationType;
    public ListEmployeTempViewHolder(View view, FlexibleAdapter adapter) {
        super(view, adapter);
    }

    public ListEmployeTempViewHolder(View view, FlexibleAdapter adapter, String operationName) {
        super(view, adapter);
        this.operationName = operationName;
        imgOperationType = (ImageView) view.findViewById(R.id.imgTempOperationType);

    }

    public void setOperationImage(String operationType) {
        int icTempId = R.drawable.ic_temp_create;
        if (operationType.equals(OperationType.CREATE)) {
            icTempId = R.drawable.ic_temp_create_32;
        }

        if (operationType.equals(OperationType.DELETE)) {
            icTempId = R.drawable.ic_temp_delete_32;
        }

        if (operationType.equals(OperationType.UPDATE)) {
            icTempId = R.drawable.ic_temp_update_32;
        }
        imgOperationType.setImageDrawable(imageViewPhoto.getContext().getResources().getDrawable(icTempId));
    }

    @Override
    public void setPhoto(String photourl) {

        try {

            byte[] decodeString = Base64.decode(photourl, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);

            if (bitmap != null) {
                this.imageViewPhoto.setImageBitmap(bitmap);
            } else {
                Log.d("mahery-haja", "erreur de transformation bitmap");
            }

        } catch (NullPointerException e) {
            imageViewPhoto.setImageDrawable(imageViewPhoto.getResources().getDrawable(R.drawable.ic_mahery));
        }

    }
}
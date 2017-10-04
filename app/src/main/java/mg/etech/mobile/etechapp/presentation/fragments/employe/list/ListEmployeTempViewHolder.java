package mg.etech.mobile.etechapp.presentation.fragments.employe.list;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.simpleserializer.OperationType;

/**
 * Created by mahery.haja on 21/09/2017.
 */

public class ListEmployeTempViewHolder extends ListEmployeViewHolder {


    protected String operationName = OperationType.CREATE;
    protected ImageView imgOperationType;
    protected ImageView imgNoConnection;
    protected ProgressBar progressBar;
    public ListEmployeTempViewHolder(View view, FlexibleAdapter adapter) {
        super(view, adapter);
    }

    public ListEmployeTempViewHolder(View view, FlexibleAdapter adapter, String operationName) {
        super(view, adapter);
        this.operationName = operationName;
        imgOperationType = (ImageView) view.findViewById(R.id.imgTempOperationType);
        progressBar = (ProgressBar) view.findViewById(R.id.progressProcessing);
        imgNoConnection = (ImageView) view.findViewById(R.id.imgTempLoading);
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

    public void afficherLoader() {
        progressBar.setVisibility(View.VISIBLE);
        imgNoConnection.setVisibility(View.INVISIBLE);
    }

    public void afficherNoConnection() {
        progressBar.setVisibility(View.INVISIBLE);
        imgNoConnection.setVisibility(View.VISIBLE);

    }

//
//    @Override
//    public void setPhoto(String photourl) {
//
////        try {
////
////            byte[] decodeString = Base64.decode(photourl, Base64.DEFAULT);
////            Bitmap bitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
////
////            if (bitmap != null) {
////                this.imageViewPhoto.setImageBitmap(bitmap);
////            } else {
////                Log.d("mahery-haja", "erreur de transformation bitmap");
////            }
////
////        } catch (NullPointerException e) {
////            imageViewPhoto.setImageDrawable(imageViewPhoto.getResources().getDrawable(R.drawable.ic_mahery));
////        }
//
//    }


}
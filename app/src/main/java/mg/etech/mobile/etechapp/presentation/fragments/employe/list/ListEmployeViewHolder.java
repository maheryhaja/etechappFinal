package mg.etech.mobile.etechapp.presentation.fragments.employe.list;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.viewholders.FlexibleViewHolder;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.presentation.customviews.PicassoImageView;

/**
 * Created by mahery.haja on 08/09/2017.
 */
public class ListEmployeViewHolder extends FlexibleViewHolder {

    protected TextView titre;
    protected TextView matricule;
    protected ImageView imageViewPhoto;
    protected View innerView;
    protected PicassoImageView pImageViewPhoto;
    protected boolean isFront = true;

    public ListEmployeViewHolder(View view, FlexibleAdapter adapter) {
        super(view, adapter);
        titre = (TextView) view.findViewById(R.id.txtItemNom);
        matricule = (TextView) view.findViewById(R.id.txtItemMatricule);
        imageViewPhoto = (ImageView) view.findViewById(R.id.imageViewItemPhoto);
        pImageViewPhoto = (PicassoImageView) view.findViewById(R.id.pImageViewItemPhoto);
        pImageViewPhoto.setFrontImage(R.drawable.ic_default_men);
        this.innerView = view;
    }

    public void setTitre(String titre) {
        this.titre.setText(titre);
    }

    public void setMatricule(long matricule) {

        Log.d("mahery-haja", "set matricule titre" + matricule);
        this.matricule.setText(String.valueOf(matricule));

    }

    public void setPhoto(String photourl) {

        if (photourl != null && !photourl.equals("") && !photourl.isEmpty()) {
            pImageViewPhoto.setPhotoWithUrl(photourl);
        }
    }

    public void setDefault(boolean isMale) {
        pImageViewPhoto.setFrontImage(isMale?R.drawable.ic_default_men:R.drawable.ic_default_women);

    }

    public void setEncodedPhoto(String base64) {
        if (base64 != null && !base64.equals("") && !base64.isEmpty()) {
            pImageViewPhoto.setPhotoWithBase64(base64);
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        Log.d("mahery-haja", "a click was made");

    }
}

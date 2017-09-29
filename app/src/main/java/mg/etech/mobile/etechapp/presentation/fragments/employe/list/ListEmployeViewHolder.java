package mg.etech.mobile.etechapp.presentation.fragments.employe.list;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.viewholders.FlexibleViewHolder;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.config.ConfigUrl;

/**
 * Created by mahery.haja on 08/09/2017.
 */
public class ListEmployeViewHolder extends FlexibleViewHolder {

    protected TextView titre;
    protected TextView matricule;
    protected ImageView imageViewPhoto;
    protected View innerView;

    public ListEmployeViewHolder(View view, FlexibleAdapter adapter) {
        super(view, adapter);
        titre = (TextView) view.findViewById(R.id.txtItemNom);
        matricule = (TextView) view.findViewById(R.id.txtItemMatricule);
        imageViewPhoto = (ImageView) view.findViewById(R.id.imageViewItemPhoto);
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

            Log.d("mahery-haja", "appel set photo " + ConfigUrl.BASE_URL + "/" + photourl);
            Picasso
                    .with(innerView.getContext())
                    .load(ConfigUrl.BASE_URL + "/" + photourl)
                    .into(imageViewPhoto);
        } else {
            imageViewPhoto.setImageDrawable(imageViewPhoto.getResources().getDrawable(R.drawable.ic_mahery));
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        Log.d("mahery-haja", "a click was made");

    }
}

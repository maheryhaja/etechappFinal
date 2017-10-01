package mg.etech.mobile.etechapp.presentation.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.config.ConfigUrl;

/**
 * Created by maheryHaja on 10/1/2017.
 */
@EViewGroup(R.layout.viewgroup_picasso_image_view)
public class PicassoImageView extends LinearLayout{

    @ViewById(R.id.picassoFlipView)
    EasyFlipView flipView;


    @ViewById(R.id.picassoImageFront)
    ImageView imageViewFront;


    @ViewById(R.id.picassoImageBack)
    ImageView imageViewBack;

    @ViewById(R.id.picassoRealImage)
    ImageView imageReal;

    private String photoUrl;
    private String photoEncoded;
    private boolean isFront = true;

    public PicassoImageView(Context context) {
        super(context);
    }

    public PicassoImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public void setFrontImage(int id) {
        Drawable drawable = getResources().getDrawable(id);
        imageViewFront.setImageDrawable(drawable);
        imageReal.setImageDrawable(drawable);
        imageReal.setVisibility(VISIBLE);
        flipView.setVisibility(GONE);
//        flipView.flipTheView();
    }

    public void setPhotoWithUrl(String url) {
        photoUrl = url;
        imageReal.setVisibility(GONE);
        flipView.setVisibility(VISIBLE);
        Picasso
                .with(getContext())
                .load(ConfigUrl.BASE_URL + "/" + url)
                .into(imageViewBack, new Callback() {
                    @Override
                    public void onSuccess() {
                        if(isFront)flipView.flipTheView();
                        isFront = false;
                    }

                    @Override
                    public void onError() {
                        flipView.setVisibility(GONE);
                        imageReal.setVisibility(VISIBLE);
                    }
                });
    }

    public void setPhotoWithBase64(String base64) {
        photoEncoded = base64;

        try {

            byte[] decodeString = Base64.decode(base64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);

            if (bitmap != null) {
                this.imageReal.setImageBitmap(bitmap);
                imageReal.setVisibility(VISIBLE);
                flipView.setVisibility(GONE);
                isFront = false;
            } else {
                Log.d("mahery-haja", "erreur de transformation bitmap");
            }

        } catch (NullPointerException e) {
            imageViewBack.setImageDrawable(imageViewBack.getResources().getDrawable(R.drawable.ic_mahery));
        }
    }

    public boolean isFront() {
        return isFront;
    }

    public void setFront(boolean front) {
        isFront = front;
    }

    public void flip() {
        flipView.flipTheView();
    }
}

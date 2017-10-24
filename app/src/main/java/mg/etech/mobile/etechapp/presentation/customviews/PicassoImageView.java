package mg.etech.mobile.etechapp.presentation.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.config.ConfigUrl;

import static mg.etech.mobile.etechapp.commun.constante.PicassoSize.MAX_HEIGHT;
import static mg.etech.mobile.etechapp.commun.constante.PicassoSize.MAX_WIDTH;

/**
 * Created by maheryHaja on 10/1/2017.
 */
@EViewGroup(R.layout.viewgroup_picasso_image_view)
public class PicassoImageView extends LinearLayout{

    @ViewById(R.id.picassoFlipView)
    EasyFlipView flipView;


    @ViewById(R.id.picassoImageFront)
    CircleImageView imageViewFront;


    @ViewById(R.id.picassoImageBack)
    CircleImageView imageViewBack;

    @ViewById(R.id.picassoRealImage)
    CircleImageView imageReal;

    private String photoUrl;
    private String photoEncoded;
    private boolean isFront = true;
    private Callback afterLoadCallBack;
    private static final int size  = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));

    public PicassoImageView(Context context) {
        super(context);
    }

    public PicassoImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        afterLoadCallBack = new Callback() {
            @Override
            public void onSuccess() {

                if (isFront) flipView.flipTheView();
                isFront = false;
            }

            @Override
            public void onError() {
                flipView.setVisibility(GONE);
                imageReal.setVisibility(VISIBLE);
                Log.d("mahery-haja", "picasso error loading");

            }
        };
    }

    @AfterViews
    void initAfterView() {
        flipView.setFlipOnTouch(false);
    }

    public void setFrontImage(int id) {

        Drawable drawable = getResources().getDrawable(id);

        imageViewFront.setImageDrawable(drawable);

        imageReal.setImageDrawable(drawable);
        flipView.setVisibility(View.GONE);
        imageReal.setVisibility(View.VISIBLE);



//        flipView.flipTheView();
    }

    public void setPhotoWithUrl(String url) {
        photoUrl = url;
        imageReal.setVisibility(GONE);
        flipView.setVisibility(VISIBLE);
        Picasso
                .with(getContext())
                .load(ConfigUrl.BASE_URL + "/" + url)
                .transform(new BitMapTransformation(MAX_WIDTH, MAX_HEIGHT))
                .resize(size, size)
                .centerInside()
                .into(imageViewBack, afterLoadCallBack);
    }

    public void setPhotoWithBase64(String base64) {

        photoEncoded = base64;

        try {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inDither = true;
            options.inSampleSize = 2;

            byte[] decodeString = Base64.decode(base64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length, options);


            if (bitmap != null) {
                imageReal.setVisibility(VISIBLE);
                this.imageReal.setImageBitmap(bitmap);
                flipView.setVisibility(GONE);
            } else {
                Log.d("mahery-haja", "erreur de transformation bitmap");
            }

        } catch (NullPointerException e) {
            imageViewBack.setImageDrawable(imageViewBack.getResources().getDrawable(R.drawable.ic_mahery));
            Log.d("mahery-haja", "error base 64 null pointer");
        } catch (OutOfMemoryError error) {
            Log.d("mahery-haja", "attention out of memory exception");
        }
    }

    public void setPhotoWithUri(Uri uri) {
//            setPhotoWithBase64(convertToBase64(uri));
        imageReal.setVisibility(GONE);
        flipView.setVisibility(VISIBLE);
        Picasso
                .with(getContext())
                .load(uri)
                .transform(new BitMapTransformation(MAX_WIDTH, MAX_HEIGHT))
                .resize(size, size)
                .centerInside()
                .into(imageViewBack, afterLoadCallBack);

    }

    private String convertToBase64(@NonNull Uri uri) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
        Log.d("mahery-haja", "concersion de " + uri);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] image = stream.toByteArray();
        return Base64.encodeToString(image, Base64.DEFAULT);
    }

    public boolean isFront() {
        return isFront;
    }

    public void setFront(boolean front) {
        isFront = front;
    }

    public void flip() {
        flipView.flipTheView();
        isFront = !isFront;


    }

    public void setBorderWidth(int borderWidth) {
        imageViewFront.setBorderWidth(borderWidth);
        imageViewBack.setBorderWidth(borderWidth);
        imageReal.setBorderWidth(borderWidth);
        int color = getResources().getColor(R.color.white);
        imageReal.setBorderColor(color);
        imageViewBack.setBorderColor(color);
        imageViewFront.setBorderColor(color);
    }
}

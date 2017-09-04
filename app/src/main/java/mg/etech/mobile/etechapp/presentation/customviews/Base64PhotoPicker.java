package mg.etech.mobile.etechapp.presentation.customviews;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import mg.etech.mobile.etechapp.R;
import rx_activity_result2.Result;
import rx_activity_result2.RxActivityResult;

/**
 * Created by mahery.haja on 01/09/2017.
 */
@EViewGroup(R.layout.viewgroup_base64_photo_picker)
public class Base64PhotoPicker extends LinearLayout {

    public static final int UPLOAD_PHOTO_REQUEST_CODE = 101;
    private boolean isSet = false;

    public String getValue() {
        return value;
    }

    private String value;

    @ViewById(R.id.btnUploadPhoto)
    ImageView btnUploadPhoto;

    @ViewById(R.id.btnTakePhoto)
    ImageView btnTakePhoto;


    public Base64PhotoPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isSet() {
        return isSet;
    }


    @Click(R.id.btnUploadPhoto)
    void onUploadPhotoClicked() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");

        RxActivityResult
                .on((Activity) getContext())
                .startIntent(galleryIntent)
                .map(new Function<Result<Activity>, Uri>() {
                    @Override
                    public Uri apply(@NonNull Result<Activity> activityResult) throws Exception {
                        Intent data = activityResult.data();
                        Uri uri = null;
                        int resulCode = activityResult.resultCode();
                        if (resulCode == Activity.RESULT_OK) {
                            uri = data.getData();

                        }

                        return uri;
                    }
                })
                .subscribeOn(Schedulers.io())
                .map(new Function<Uri, String>() {
                    @Override
                    public String apply(@NonNull Uri uri) throws Exception {
                        //convert uri to bitmap
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] image = stream.toByteArray();

                        String encodedImage = Base64.encodeToString(image, Base64.DEFAULT);

                        return encodedImage;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {

                    @Override
                    public void accept(String s) throws Exception {
                        isSet = true;
                        value = s;
                    }
                });
    }

    @Click(R.id.btnTakePhoto)
    void onTakePhotoClicked() {
        Toast.makeText(Base64PhotoPicker.this.getContext(), "vous voulez prendre une photo", Toast.LENGTH_SHORT).show();
    }

}

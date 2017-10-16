package mg.etech.mobile.etechapp.presentation.customviews;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private boolean isUrlSet = true;
    private Uri photoTakenURI;
    private Consumer<String> onSelectedPhotoSucceed;
    private Consumer<Throwable> onNoPhotoSelected;
    private boolean beforeLast = false;

    public String getValue() {
        return value;
    }

    private String value;
    private String urlValue;

    @ViewById(R.id.btnUploadPhoto)
    ImageView btnUploadPhoto;

    @ViewById(R.id.btnTakePhoto)
    ImageView btnTakePhoto;

    @ViewById(R.id.btnRevertPhoto)
    ImageView btnRevertPhoto;

    @ViewById(R.id.txtChoosePhoto)
    TextView txtChoosePhoto;

    @ViewById(R.id.pImageViewBase64)
    PicassoImageView picassoImageView;

    public Base64PhotoPicker(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    @AfterViews
    void initAfterViews() {
        onSelectedPhotoSucceed = new Consumer<String>() {

            @Override
            public void accept(String s) throws Exception {
                isSet = true;
                value = s;
                picassoImageView.setPhotoWithUri(Uri.parse(s));
                afficherImpage();

            }
        };
        onNoPhotoSelected = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                // aucun resultat
            }
        };
    }




    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public boolean isSet() {
        return isSet;
    }


    @Click(R.id.btnUploadPhoto)
    void onUploadPhotoClicked() {
        Intent galleryIntent;


        if (Build.VERSION.SDK_INT < 19) {
            galleryIntent = new Intent();
            galleryIntent.setType("image/jpeg");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
            galleryIntent.setType("image/jpeg");
        }

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
//                        String encodedImage = convertToBase64(uri);
//                        return encodedImage;
                        return uri.toString();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSelectedPhotoSucceed,
                        onNoPhotoSelected
                );
    }

    private String convertToBase64(@NonNull Uri uri) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
        Log.d("mahery-haja", "concersion de " + uri);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] image = stream.toByteArray();
        return Base64.encodeToString(image, Base64.DEFAULT);
    }

    @Click(R.id.btnTakePhoto)
    void onTakePhotoClicked() {

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //request more permission
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{android.Manifest.permission.CAMERA},
                    5);

            return;
        }


        //Create intent for taking photo
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent

        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoTakenURI = FileProvider.getUriForFile(getContext(),
                        "mg.etech.mobile.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoTakenURI);

                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clip=
                            ClipData.newUri(getContext().getContentResolver(), "photo", photoTakenURI);

                    takePictureIntent.setClipData(clip);
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }


                RxActivityResult
                        .on((Activity) getContext())
                        .startIntent(takePictureIntent)
                        .map(new Function<Result<Activity>, Uri>() {
                            @Override
                            public Uri apply(@NonNull Result<Activity> activityResult) throws Exception {
                                Intent data = activityResult.data();
                                Uri uri = null;
                                int resulCode = activityResult.resultCode();
                                if (resulCode == Activity.RESULT_OK) {
                                    uri = photoTakenURI;

                                } else {
                                    Log.d("mahery-haja","erreur de la camera");
                                }
                                return uri;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .map(new Function<Uri, String>() {
                            @Override
                            public String apply(@NonNull Uri uri) throws Exception {
//                                return convertToBase64(uri);
                                return uri.toString();
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onSelectedPhotoSucceed, onNoPhotoSelected);



            }
        }


    }

    public void afficherImpage() {
        picassoImageView.setVisibility(VISIBLE);
        btnRevertPhoto.setVisibility(VISIBLE);
        txtChoosePhoto.setVisibility(GONE);
    }

    public void cacherImage() {
        picassoImageView.setVisibility(GONE);
        btnRevertPhoto.setVisibility(GONE);
        txtChoosePhoto.setVisibility(VISIBLE);
    }

    @Click(R.id.btnRevertPhoto)
    public void onRevertClicked() {

        if (beforeLast && isSet) {
            setPhotoWithUrl(urlValue);
            isSet = false;
            beforeLast = false;
        } else {

            this.isSet = false;
            this.value = null;
            isUrlSet = false;
            cacherImage();
        }
    }

    public void setPhotoWithUrl(String url) {
        urlValue = url;
        picassoImageView.setPhotoWithUrl(url);
        beforeLast = true;
        isUrlSet = true;
        afficherImpage();

    }

    public void setPhotoWithBase64(String base64) {
        value = base64;
        picassoImageView.setPhotoWithBase64(base64);
        isSet = true;
        afficherImpage();
    }

    public void setPhotowithUri(Uri uri) {
        value = uri.toString();
        picassoImageView.setPhotoWithUri(uri);
        isSet = true;
        afficherImpage();
    }

    public boolean isUrlSet() {
        return isUrlSet;
    }

    public String getUrlValue() {
        return urlValue;
    }
}

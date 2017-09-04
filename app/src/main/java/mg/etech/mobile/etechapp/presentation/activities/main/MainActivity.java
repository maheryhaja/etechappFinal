package mg.etech.mobile.etechapp.presentation.activities.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.presentation.activities.login.LoginActivity_;
import mg.etech.mobile.etechapp.service.applicatif.PreferenceSA;
import mg.etech.mobile.etechapp.service.applicatif.PreferenceSAImpl;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    @Bean(PreferenceSAImpl.class)
    PreferenceSA preferenceSA;

    @ViewById(R.id.myImageView)
    ImageView mImageView;

    @Click(R.id.btnLogout)
    void logoutClicked() {
        preferenceSA.setUserConnected(false);
        LoginActivity_.intent(this).start();
        finish();
    }


    @Click(R.id.btnPrendrePhoto)
    public void prendrePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }
}

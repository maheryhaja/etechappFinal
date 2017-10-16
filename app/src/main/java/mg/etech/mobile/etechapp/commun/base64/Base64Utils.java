package mg.etech.mobile.etechapp.commun.base64;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import io.reactivex.annotations.NonNull;

/**
 * Created by mahery.haja on 16/10/2017.
 */

public class Base64Utils {
    public static String convertToBase64(@NonNull Uri uri, @NonNull Context context) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        Log.d("mahery-haja", "concersion de " + uri);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] image = stream.toByteArray();
        return Base64.encodeToString(image, Base64.DEFAULT);
    }
}

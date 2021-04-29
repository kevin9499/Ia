package net.merryservices.apptestia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import net.merryservices.apptestia.ui.FragmentResultIA;
import net.merryservices.apptestia.ui.FragmentSelectPicture;


public class MainActivity extends AppCompatActivity {


    public final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;

    String CAMERA_PERMISSION = Manifest.permission.CAMERA;
    String READ_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    String WRITE_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private FragmentSelectPicture fragmentSelectPicture;
    private FragmentResultIA fragmentResultIA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fragmentSelectPicture = (FragmentSelectPicture) getSupportFragmentManager().findFragmentById(R.id.fragmentSelectPicture);
        fragmentResultIA = (FragmentResultIA) getSupportFragmentManager().findFragmentById(R.id.fragmentResultIA);
        fragmentSelectPicture.setListener(fragmentResultIA);
        checkPermission();
    }

    private void checkPermission() {
        String[] PERMISSIONS = {CAMERA_PERMISSION, READ_EXTERNAL_STORAGE_PERMISSION, WRITE_EXTERNAL_STORAGE_PERMISSION};
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
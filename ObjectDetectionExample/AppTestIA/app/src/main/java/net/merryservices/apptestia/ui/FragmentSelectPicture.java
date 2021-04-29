package net.merryservices.apptestia.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import net.merryservices.apptestia.services.IOnResultAPI;
import net.merryservices.apptestia.R;
import net.merryservices.apptestia.services.ServiceAPI;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class FragmentSelectPicture extends Fragment {

    int TAKE_PICTURE = 100;
    int SELECT_PICTURE = 200;

    ImageView imageViewUpload;
    Button buttonUpload;

    Bitmap currentPicture;
    IOnResultAPI listener;

    public void setListener(IOnResultAPI listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_selectpicture, null);
        imageViewUpload = v.findViewById(R.id.imageViewUpload);
        imageViewUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        buttonUpload= v.findViewById(R.id.buttonUpload);
        buttonUpload.setEnabled(false);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceAPI.uploadBitmap(getActivity(), currentPicture, listener);
                buttonUpload.setEnabled(false);
            }
        });
        return v;
    }

    public void setCurrentPicture(Bitmap currentPicture) {
        this.currentPicture = currentPicture;
        imageViewUpload.setImageBitmap(currentPicture);
        imageViewUpload.invalidate();
        buttonUpload.setEnabled(true);
        listener.OnSelectImage();
    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePictureIntent, TAKE_PICTURE);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent i = new Intent();
                    i.setType("image/*");
                    i.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    try {
                        Bitmap imageBitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                        // update the preview image in the layout
                        setCurrentPicture(imageBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else if(requestCode== TAKE_PICTURE){
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                setCurrentPicture(imageBitmap);
            }
        }
    }
}

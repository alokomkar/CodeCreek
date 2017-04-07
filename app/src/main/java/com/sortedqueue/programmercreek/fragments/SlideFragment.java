package com.sortedqueue.programmercreek.fragments;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.firebase.FirebaseStorageHandler;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.PermissionUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.kbiakov.codeview.CodeView;

/**
 * Created by Alok on 06/04/17.
 */

public class SlideFragment extends Fragment implements View.OnClickListener, AuxilaryUtils.PhotoOptionListener {

    @Bind(R.id.titleEditText)
    EditText titleEditText;
    @Bind(R.id.subTitleEditText)
    EditText subTitleEditText;
    @Bind(R.id.codeView)
    CodeView codeView;
    @Bind(R.id.slideImageView)
    ImageView slideImageView;
    @Bind(R.id.slideHintTextView)
    TextView slideHintTextView;
    @Bind(R.id.slideImageLayout)
    FrameLayout slideImageLayout;
    private final int ACTION_CAMERA = 1;
    private final int ACTION_GALLERY = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slide, container, false);
        ButterKnife.bind(this, view);
        titleEditText.clearFocus();
        subTitleEditText.clearFocus();
        slideImageLayout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.slideImageLayout ) {
            if( PermissionUtils.checkSelfPermission(this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE})) {
                AuxilaryUtils.displayPhotoDialog(getContext(), this);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if( requestCode == PermissionUtils.PERMISSION_REQUEST) {
            if( PermissionUtils.checkDeniedPermissions((AppCompatActivity) getActivity(), permissions).length == 0 ) {
                AuxilaryUtils.displayPhotoDialog(getContext(), this);
            }
            else {
                if( permissions.length == 3 ) {
                    CommonUtils.displaySnackBar(getActivity(), R.string.camera_read_write_storage_permission_to_open_gallery );
                }
            }
        }

        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onChoiceSelected(int choice) {
        switch ( choice ) {
            case AuxilaryUtils.CHOICE_CAMERA :
                loadCamera();
                break;
            case AuxilaryUtils.CHOICE_GALLERY :
                loadGallery();
                break;
        }
    }

    private void loadCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                CommonUtils.displayToast(getContext(), "Unable to open Camera");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.sortedqueue.programmercreek.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, ACTION_CAMERA);
            }
        }

    }

    private void loadGallery() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
        } else {
            intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), ACTION_GALLERY);
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PNG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void handleBigCameraPhoto() {
        if (mCurrentPhotoPath != null) {
            setCameraPhoto();
        }
    }

    private void setCameraPhoto() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        int targetW = slideImageView.getWidth();
        int targetH = slideImageView.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

		/* Associate the Bitmap to the ImageView */
        slideImageView.setImageBitmap(bitmap);
        slideImageView.setVisibility(View.VISIBLE);
        uploadFileToFirebase(Uri.fromFile(new File(mCurrentPhotoPath)));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch ( requestCode ) {
            case ACTION_CAMERA :
                if( resultCode == AppCompatActivity.RESULT_OK ) {
                    handleBigCameraPhoto();
                }
                break;
            case ACTION_GALLERY :
                if( resultCode == AppCompatActivity.RESULT_OK ) {
                    Uri selectedImageUri = data.getData();
                    Glide.with(getContext()).load(selectedImageUri).asBitmap().centerCrop().into(slideImageView);
                    uploadFileToFirebase(selectedImageUri);

                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadFileToFirebase(Uri selectedImageUri) {
        CommonUtils.displayProgressDialog(getContext(), "Uploading Image");
        new FirebaseStorageHandler(getContext()).uploadSlideImage(selectedImageUri, new FirebaseStorageHandler.FileUploadListener() {
            @Override
            public void onSuccess(Uri downloadUri) {
                CommonUtils.dismissProgressDialog();
                CommonUtils.displayToast(getContext(), "Success");
                Glide.with(getContext()).load(downloadUri).asBitmap().centerCrop().into(slideImageView);
                slideHintTextView.setVisibility(View.GONE);
            }

            @Override
            public void onProgressUpdate(double currentProgress) {

            }

            @Override
            public void onError(Exception e) {
                CommonUtils.dismissProgressDialog();
                if( e != null ) {
                    CommonUtils.displayToast(getContext(), "Error occurred while upload : " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}

package com.sortedqueue.programmercreek.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.SlideModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.database.firebase.FirebaseStorageHandler;
import com.sortedqueue.programmercreek.interfaces.PresentationCommunicationsListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.PermissionUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import io.github.kbiakov.codeview.CodeView;

import static com.facebook.GraphRequest.TAG;

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
    @Bind(R.id.slideImageLayout)
    FrameLayout slideImageLayout;
    private final int ACTION_CAMERA = 1;
    private final int ACTION_GALLERY = 2;
    @Bind(R.id.deleteImageView)
    ImageView deleteImageView;
    @Bind(R.id.changeImageView)
    ImageView changeImageView;
    @Bind(R.id.rotateImageView)
    ImageView rotateImageView;
    @Bind(R.id.saveImageView)
    ImageView saveImageView;
    @Bind(R.id.doneButton)
    Button doneButton;
    @Bind(R.id.uploadProgressBar)
    ProgressBar uploadProgressBar;

    private Uri selectedImageUri;
    private Bitmap selectedBitmap;
    private FirebaseDatabaseHandler firebaseDatabaseHandler;
    private String code = "";
    private PresentationCommunicationsListener presentationCommunicationsListener;
    private SlideModel slideModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slide, container, false);
        ButterKnife.bind(this, view);
        slideModel = new SlideModel();
        firebaseDatabaseHandler = new FirebaseDatabaseHandler(getContext());
        titleEditText.clearFocus();
        subTitleEditText.clearFocus();
        slideImageLayout.setVisibility(View.GONE);
        codeView.setVisibility(View.GONE);
        codeView.setOnClickListener(this);
        deleteImageView.setOnClickListener(this);
        changeImageView.setOnClickListener(this);
        rotateImageView.setOnClickListener(this);
        saveImageView.setOnClickListener(this);
        doneButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PresentationCommunicationsListener) {
            presentationCommunicationsListener = (PresentationCommunicationsListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presentationCommunicationsListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeImageView:
                startCropPhotoActivity(selectedImageUri);
                break;
            case R.id.codeView:
                break;
            case R.id.deleteImageView:
                slideModel.setSlideImageUrl(null);
                slideImageView.setImageBitmap(null);
                slideImageLayout.setVisibility(View.GONE);
                break;
            case R.id.rotateImageView:
                rotateImage();
                break;
            case R.id.saveImageView:
                uploadFileToFirebase(selectedImageUri);
                break;
            case R.id.doneButton:
                save();
                break;
        }

    }


    private void save() {
        String imageUrl = null;
        if( selectedImageUri != null ) {
            imageUrl = selectedImageUri.toString();
        }
        slideModel = new SlideModel(null, code, titleEditText.getText().toString(), subTitleEditText.getText().toString(), imageUrl);
        String presentationPushId = firebaseDatabaseHandler.writeSlide(slideModel);
        presentationCommunicationsListener.onPresentationCreation(presentationPushId, slideModel);
    }

    private void rotateImage() {
        if (selectedBitmap != null) {
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            selectedBitmap = Bitmap.createBitmap(selectedBitmap, 0, 0, selectedBitmap.getWidth(), selectedBitmap.getHeight(),
                    matrix, true);
            slideImageView.setImageBitmap(selectedBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.PERMISSION_REQUEST) {
            if (PermissionUtils.checkDeniedPermissions((AppCompatActivity) getActivity(), permissions).length == 0) {
                AuxilaryUtils.displayPhotoDialog(getContext(), this);
            } else {
                if (permissions.length == 3) {
                    CommonUtils.displaySnackBar(getActivity(), R.string.camera_read_write_storage_permission_to_open_gallery);
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onChoiceSelected(int choice) {
        switch (choice) {
            case AuxilaryUtils.CHOICE_CAMERA:
                loadCamera();
                break;
            case AuxilaryUtils.CHOICE_GALLERY:
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
            compressAndCropPhoto(mCurrentPhotoPath);
        }
    }

    private void compressAndCropPhoto(String mCurrentPhotoPath) {
        Compressor compressor = new Compressor.Builder(getContext())
                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                .setQuality(100)
                .build();
        Uri compressedImageUri = Uri.fromFile(compressor.compressToFile(new File(mCurrentPhotoPath)));
        startCropPhotoActivity(compressedImageUri);
    }

    private void startCropPhotoActivity(Uri selectedImageUri) {
        CropImage.activity(selectedImageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setFixAspectRatio(true)
                .setAspectRatio(16, 9)
                .start(getContext(), this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case ACTION_CAMERA:
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    handleBigCameraPhoto();
                }
                break;
            case ACTION_GALLERY:
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    Uri selectedImageUri = data.getData();
                    compressAndCropPhoto(AuxilaryUtils.getFilePath(getContext(), selectedImageUri));
                }
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (result != null) {
                        selectedImageUri = result.getUri();
                        Glide.with(getContext()).load(result.getUri()).asBitmap().listener(new RequestListener<Uri, Bitmap>() {
                            @Override
                            public boolean onException(Exception e, Uri model, Target<Bitmap> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Uri model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                selectedBitmap = resource;
                                return false;
                            }
                        }).centerCrop().into(slideImageView);
                    } else {
                        CommonUtils.displaySnackBar(getActivity(), R.string.unable_to_crop_image);
                    }
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadFileToFirebase(final Uri selectedImageUri) {

        uploadProgressBar.setVisibility(View.VISIBLE);
        if (selectedImageUri != null) {
            new FirebaseStorageHandler(getContext()).uploadSlideImage(selectedImageUri, new FirebaseStorageHandler.FileUploadListener() {
                @Override
                public void onSuccess(Uri downloadUri) {
                    uploadProgressBar.setVisibility(View.GONE);
                    CommonUtils.displayToast(getContext(), "Success");
                    Log.d(TAG, "Upload Success : " + downloadUri.toString());
                    SlideFragment.this.selectedImageUri = downloadUri;
                    slideModel.setSlideImageUrl(downloadUri.toString());
                    Glide.with(getContext()).load(downloadUri).asBitmap().centerCrop().into(slideImageView);
                }

                @Override
                public void onProgressUpdate(double currentProgress) {
                    uploadProgressBar.setProgress((int) currentProgress);
                }

                @Override
                public void onError(Exception e) {
                    uploadProgressBar.setVisibility(View.GONE);
                    if (e != null) {
                        CommonUtils.displayToast(getContext(), "Error occurred while upload : " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
        } else {
            uploadProgressBar.setVisibility(View.GONE);
            CommonUtils.displaySnackBar(getActivity(), R.string.image_upload_failed);
        }

    }

    public void insertCode() {
        codeView.setVisibility(View.VISIBLE);
        codeView.callOnClick();
    }

    public void insertPhoto() {
        slideImageLayout.setVisibility(View.VISIBLE);
        if( PermissionUtils.checkSelfPermission(this,
                new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE})) {
            AuxilaryUtils.displayPhotoDialog(getContext(), this);
        }
    }

    public void saveImage() {
        if ( selectedImageUri != null && slideModel.getSlideImageUrl() == null ) {
            uploadFileToFirebase(selectedImageUri);
        }
    }
}

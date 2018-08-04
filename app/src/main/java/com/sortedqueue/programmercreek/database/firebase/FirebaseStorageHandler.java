package com.sortedqueue.programmercreek.database.firebase;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.io.File;

/**
 * Created by Alok on 07/04/17.
 */

public class FirebaseStorageHandler {

    private FirebaseStorage defaultInstance;
    private CreekPreferences creekPreferences;

    private static final String PROGRAM_TEMPLATE_FILE = "gs://creek-55ef6.appspot.com/Sample_Template.txt";

    public FirebaseStorageHandler(Context context) {
        defaultInstance = FirebaseStorage.getInstance();
        creekPreferences = CreekApplication.Companion.getCreekPreferences();
    }

    public interface TemplateDownloadListener {
        void onSuccess( String filePath );
        void onError( String error );
    }

    public static void downloadTemplateFile(Context context, final TemplateDownloadListener templateDownloadListener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        //downloadUrl = "gs://rangde-test.appspot.com/literacy_extras/kannada/please_enter_aadhar.mp3";
        StorageReference gsReference = storage.getReferenceFromUrl(PROGRAM_TEMPLATE_FILE);

        File storagePath = new File(Environment.getExternalStorageDirectory(), "IPROGRAMMER");
        if(!storagePath.exists()) {
            storagePath.mkdirs();
        }

        final File myFile = new File(storagePath, "Sample_Template.txt");
        gsReference.getFile(myFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                templateDownloadListener.onSuccess(myFile.getPath());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                templateDownloadListener.onError(exception.getMessage());
            }
        });
    }

    public interface FileUploadListener {
        void onSuccess(Uri downloadUri);
        void onProgressUpdate( double currentProgress );
        void onError(Exception e);
    }

    public void uploadSlideImage(Uri fileUri, final FileUploadListener fileUploadListener) {

        StorageReference slideStorageReference = defaultInstance.getReference().child("slides/" + creekPreferences.getSignInAccount().replaceAll("[-+.^:,]","") + "/" + fileUri.getLastPathSegment());
        UploadTask fileUploadTask = slideStorageReference.putFile(fileUri);
        fileUploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                fileUploadListener.onError(e);
            }
        });
        fileUploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                fileUploadListener.onProgressUpdate(progress);
            }
        });
        fileUploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if( task.isSuccessful() ) {
                    //fileUploadListener.onSuccess(task.getResult().getDownloadUrl());
                }
                else {
                    fileUploadListener.onError(null);
                }
            }
        });

    }
}

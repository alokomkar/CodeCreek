package com.sortedqueue.programmercreek.database.firebase;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sortedqueue.programmercreek.CreekApplication;
import com.sortedqueue.programmercreek.util.CreekPreferences;

/**
 * Created by Alok on 07/04/17.
 */

public class FirebaseStorageHandler {

    private FirebaseStorage defaultInstance;
    private CreekPreferences creekPreferences;

    public FirebaseStorageHandler(Context context) {
        defaultInstance = FirebaseStorage.getInstance();
        creekPreferences = CreekApplication.getCreekPreferences();
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
                    fileUploadListener.onSuccess(task.getResult().getDownloadUrl());
                }
                else {
                    fileUploadListener.onError(null);
                }
            }
        });

    }
}

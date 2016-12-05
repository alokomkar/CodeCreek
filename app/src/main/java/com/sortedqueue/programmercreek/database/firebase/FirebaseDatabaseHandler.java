package com.sortedqueue.programmercreek.database.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.database.Program_Table;

/**
 * Created by binay on 05/12/16.
 */

public class FirebaseDatabaseHandler {

    private DatabaseReference mDatabase;
    private String PROGRAM_INDEX_CHILD = "program_index";
    private String PROGRAM_TABLE_CHILD = "program_table";

    private String CREEK_BASE_FIREBASE_URL = "https://creek-55ef6.firebaseio.com/";

    public DatabaseReference getDatabase() {
        if( mDatabase == null ) {
            mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/programs/c" );
            mDatabase.keepSynced(true);
        }
        return mDatabase;
    }

    public FirebaseDatabaseHandler() {
       getDatabase();
    }

    public void writeProgramIndex( Program_Index program_index ) {
        mDatabase.child(PROGRAM_INDEX_CHILD).setValue(program_index);
    }

    public void writeProgramTable( Program_Table program_table ) {
        mDatabase.child(PROGRAM_TABLE_CHILD).setValue(program_table);
    }

    /***
     * Program Index storage :
     *  CREEK_BASE_FIREBASE_URL/programs/c/program_indexes
     *
     * Program storage :
     *  CREEK_BASE_FIREBASE_URL/programs/c/program_storage
     *
     * User profile storage :
     *  CREEK_BASE_FIREBASE_URL/users/email_id/
     *
     * User program details :
     *  CREEK_BASE_FIREBASE_URL/user_program_details/email_id/program_language
     ***/


}

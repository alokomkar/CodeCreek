package com.sortedqueue.programmercreek.database.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sortedqueue.programmercreek.database.CreekUser;
import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.UserProgramDetails;

/**
 * Created by binay on 05/12/16.
 */

public class FirebaseDatabaseHandler {

    private DatabaseReference mProgramDatabase;
    private DatabaseReference mUserDatabase;
    private DatabaseReference mUserDetailsDatabase;
    private String PROGRAM_INDEX_CHILD = "program_indexes";
    private String PROGRAM_TABLE_CHILD = "program_tables";
    private String CREEK_USER_CHILD = "users";
    private String CREEK_USER_PROGRAM_DETAILS_CHILD = "user_program_details";
    private String CREEK_BASE_FIREBASE_URL = "https://creek-55ef6.firebaseio.com/";
    private String programLanguage = "c";

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

    public DatabaseReference getDatabase() {
        if( mProgramDatabase == null ) {
            mProgramDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/programs/" + programLanguage );
            mProgramDatabase.keepSynced(true);
        }
        return mProgramDatabase;
    }

    public DatabaseReference getUserDatabase() {
        if( mUserDatabase == null ) {
            mUserDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" +CREEK_USER_CHILD );
            mUserDatabase.keepSynced(true);
        }
        return mUserDatabase;
    }

    public DatabaseReference getUserDetailsDatabase() {
        if( mUserDetailsDatabase == null ) {
            mUserDetailsDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" +CREEK_USER_PROGRAM_DETAILS_CHILD );
            mUserDetailsDatabase.keepSynced(true);
        }
        return mUserDetailsDatabase;
    }

    public FirebaseDatabaseHandler() {
        getDatabase();
        getUserDatabase();
    }

    public void writeProgramIndex( Program_Index program_index ) {
        mProgramDatabase.child(PROGRAM_INDEX_CHILD + "/" + program_index.getIndex()).setValue(program_index);
    }

    public void writeProgramTable( Program_Table program_table ) {
        mProgramDatabase.child(PROGRAM_TABLE_CHILD + "/" + program_table.getIndex() + "/" + program_table.getLine_No()).setValue(program_table);
    }

    public void writeCreekUser(CreekUser creekUser) {
        mUserDatabase.child( creekUser.getEmailId().replaceAll("[-+.^:,]","")).setValue(creekUser);
    }

    public void writeUserProgramDetails(UserProgramDetails userProgramDetails) {
        mUserDatabase.child( userProgramDetails.getEmailId().replaceAll("[-+.^:,]","")).setValue(userProgramDetails);
    }




}

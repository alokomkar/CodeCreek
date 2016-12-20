package com.sortedqueue.programmercreek.database.firebase;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sortedqueue.programmercreek.database.CreekUser;
import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.UserProgramDetails;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.database.operations.DataBaseInserterAsyncTask;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;

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
    private Context mContext;
    private CreekPreferences creekPreferences;

    private String TAG = FirebaseDatabaseHandler.class.getSimpleName();
    private DatabaseHandler databaseHandler;
    private ArrayList<Program_Table> program_tables;

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

    public FirebaseDatabaseHandler(Context context) {
        this.mContext = context;
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

    public interface ProgramIndexInterface {
        void getProgramIndexes(ArrayList<Program_Index> program_indices);
        void onError( DatabaseError error );
    }



    public void initializeProgramIndexes( final ProgramIndexInterface programIndexInterface ) {

        //Get last n number of programs : ? Store total programs in firebase, total_programs - existing max index
        databaseHandler = new DatabaseHandler(mContext);
        int initialPrograms = 31;
        creekPreferences = new CreekPreferences(mContext);
        if( creekPreferences.getProgramIndex() == -1 ) {
            CommonUtils.displayAdsProgressDialog(mContext, "Loading program index");
            mProgramDatabase.child(PROGRAM_INDEX_CHILD).limitToFirst(initialPrograms).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Program_Index> program_indices = new ArrayList<Program_Index>();
                    for( DataSnapshot programIndexSnapshot : dataSnapshot.getChildren() ) {
                        Program_Index program_index = programIndexSnapshot.getValue(Program_Index.class);
                        databaseHandler.addProgram_Index(program_index);
                        program_indices.add(program_index);
                    }
                    programIndexInterface.getProgramIndexes(program_indices);
                    creekPreferences.setProgramIndex(program_indices.size());
                    Log.d(TAG, "Inserted program indexes : " + program_indices.size());
                    CommonUtils.dismissProgressDialog();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    programIndexInterface.onError(databaseError);
                    CommonUtils.dismissProgressDialog();
                }
            });
        }
        else {
            Log.d(TAG, "Inserted program indexes found : " + creekPreferences.getProgramIndex());
            programIndexInterface.getProgramIndexes(new ArrayList<Program_Index>());
        }

    }

    public interface ProgramTableInterface {
        void getProgramTables(ArrayList<Program_Table> program_tables);
        void onError( DatabaseError error );
    }

    public void initializeProgramTables(final ProgramTableInterface programTableInterface ) {
        databaseHandler = new DatabaseHandler(mContext);
        int initialPrograms = 31;
        creekPreferences = new CreekPreferences(mContext);
        if( creekPreferences.getProgramTables() == -1 ) {
            CommonUtils.displayAdsProgressDialog(mContext, "Loading program tables");
            program_tables = new ArrayList<>();
            mProgramDatabase.child(PROGRAM_TABLE_CHILD).limitToFirst(initialPrograms).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for( DataSnapshot indexSnapshot : dataSnapshot.getChildren() ) {
                        for( DataSnapshot lineSnapShot : indexSnapshot.getChildren() ) {
                            Program_Table program_table = lineSnapShot.getValue(Program_Table.class);
                            if( program_table != null ) {
                                program_tables.add(program_table);
                            }
                            Log.d(TAG, "Inserted program tables : " + program_tables.size());
                            if( program_tables.size() == 530 ) {
                                creekPreferences.setProgramTables(program_tables.size());
                                new DataBaseInserterAsyncTask(mContext, -3, program_tables, new UIUpdateListener() {
                                    @Override
                                    public void updateUI() {
                                        programTableInterface.getProgramTables(program_tables);
                                        CommonUtils.dismissProgressDialog();
                                    }
                                }).execute();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    programTableInterface.onError(databaseError);
                    CommonUtils.dismissProgressDialog();
                }
            });
        }
        else {
            Log.d(TAG, "Inserted program tables found : " + creekPreferences.getProgramTables());
            programTableInterface.getProgramTables(new ArrayList<Program_Table>());
        }
    }




}

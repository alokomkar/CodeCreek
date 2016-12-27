package com.sortedqueue.programmercreek.database.firebase;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sortedqueue.programmercreek.database.CreekUser;
import com.sortedqueue.programmercreek.database.CreekUserDB;
import com.sortedqueue.programmercreek.database.LanguageModule;
import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.SyntaxModule;
import com.sortedqueue.programmercreek.database.UserProgramDetails;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.database.operations.DataBaseInsertAsyncTask;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;

import co.uk.rushorm.core.RushCallback;
import co.uk.rushorm.core.RushSearch;

/**
 * Created by binay on 05/12/16.
 */

public class FirebaseDatabaseHandler {

    private DatabaseReference mProgramDatabase;
    private DatabaseReference mLanguageModuleDatabase;
    private DatabaseReference mSyntaxModuleDatabase;
    private DatabaseReference mUserDatabase;
    private DatabaseReference mUserDetailsDatabase;
    private String PROGRAM_INDEX_CHILD = "program_indexes";
    private String PROGRAM_TABLE_CHILD = "program_tables";
    private String CREEK_USER_CHILD = "users";
    private String CREEK_USER_PROGRAM_DETAILS_CHILD = "user_program_details";
    private String LANGUAGE_MODULE = "language_module";
    private String SYNTAX_MODULE = "syntax_module";
    private String CREEK_BASE_FIREBASE_URL = "https://creek-55ef6.firebaseio.com/";
    private String programLanguage = "java";
    private Context mContext;
    private CreekPreferences creekPreferences;

    private String TAG = FirebaseDatabaseHandler.class.getSimpleName();
    private DatabaseHandler databaseHandler;
    private ArrayList<Program_Table> program_tables;
    private DatabaseReference mCreekUserDBDatabase;
    private String CREEK_USER_DB = "creek_user_db_version";

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

    public DatabaseReference getProgramDatabase() {
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

    public void getCreekUserDBDatabase() {
        if( mCreekUserDBDatabase == null ) {
            mCreekUserDBDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" +CREEK_USER_DB );
            mCreekUserDBDatabase.keepSynced(true);
        }
    }

    public FirebaseDatabaseHandler(Context context) {
        this.mContext = context;
        creekPreferences = new CreekPreferences(mContext);
        programLanguage = creekPreferences.getProgramLanguage();
        if( programLanguage.equals("c++") ) {
            programLanguage = "cpp";
        }
        getCreekUserDBDatabase();
        getProgramDatabase();
        getUserDatabase();
        getUserDetailsDatabase();
        getLanguageModuleDatabase();
        getSyntaxModuleDatabase();
    }

    private void getSyntaxModuleDatabase() {
        if( mSyntaxModuleDatabase == null ) {
            mSyntaxModuleDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/"+SYNTAX_MODULE+"/" + programLanguage);
            mSyntaxModuleDatabase.keepSynced(true);
        }
    }

    private void getLanguageModuleDatabase() {
        if( mLanguageModuleDatabase == null ) {
            mLanguageModuleDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/"+LANGUAGE_MODULE+"/" + programLanguage);
            mLanguageModuleDatabase.keepSynced(true);
        }
    }

    public void writeSyntaxModule(final SyntaxModule syntaxModule) {
        mSyntaxModuleDatabase.child(  programLanguage + "_" + syntaxModule.getModuleId() + "_" + syntaxModule.getSyntaxModuleId()  ).setValue(syntaxModule);
        syntaxModule.save(new RushCallback() {
            @Override
            public void complete() {
                Log.d(TAG, "Rush ORM : saved successfully " + syntaxModule.toString()  );
            }
        });
    }

    public void writeLanguageModule(final LanguageModule languageModule) {
        mLanguageModuleDatabase.child(languageModule.getModuleLanguage() + "_" + languageModule.getModuleId() ).setValue(languageModule);
        languageModule.save(new RushCallback() {
            @Override
            public void complete() {
                Log.d(TAG, "Rush ORM : saved successfully " + languageModule.toString()  );
            }
        });
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

    public void writeCreekUserDB(CreekUserDB creekUserDB) {
        mCreekUserDBDatabase.setValue(creekUserDB);
    }

    public interface GetCreekUserDBListener {
        void onSuccess( CreekUserDB creekUserDB );
        void onError( DatabaseError databaseError );
    }
    public void readCreekUserDB(final GetCreekUserDBListener getCreekUserDBListener ) {
        mCreekUserDBDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if( dataSnapshot != null ) {
                    CreekUserDB creekUserDB = dataSnapshot.getValue(CreekUserDB.class);
                    if( creekUserDB != null ) {
                        getCreekUserDBListener.onSuccess(creekUserDB);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getCreekUserDBListener.onError(databaseError);
            }
        });
    }


    public void writeUserProgramDetails(UserProgramDetails userProgramDetails) {
        mUserDatabase.child( userProgramDetails.getEmailId().replaceAll("[-+.^:,]","")).setValue(userProgramDetails);
    }


    public interface ModuleInterface {
        void getModules( ArrayList<LanguageModule> languageModules );
        void onError( DatabaseError error );
    }

    public interface ProgramIndexInterface {
        void getProgramIndexes(ArrayList<Program_Index> program_indices);
        void onError( DatabaseError error );
    }

    public interface SyntaxInterface {
        void getSyntaxModules( ArrayList<SyntaxModule> syntaxModules );
        void onError( DatabaseError error );
    }

    public void initializeSyntax(final LanguageModule languageModule, final SyntaxInterface syntaxInterface ) {
        if( !creekPreferences.getSyntaxInserted() ) {

            mSyntaxModuleDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<SyntaxModule> syntaxModules = new ArrayList<>();
                    for( DataSnapshot childDataSnapShot : dataSnapshot.getChildren() ) {
                        SyntaxModule syntaxModule = childDataSnapShot.getValue(SyntaxModule.class);
                        syntaxModule.save();
                        if( syntaxModule.getModuleId().equals(languageModule.getModuleId()) ) {
                            syntaxModules.add(syntaxModule);
                        }
                    }
                    syntaxInterface.getSyntaxModules(syntaxModules);
                    creekPreferences.setSyntaxInserted(true);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    syntaxInterface.onError(databaseError);
                }
            });
        }
        else {
            new AsyncTask<Void, Void, ArrayList<SyntaxModule>>() {

                @Override
                protected ArrayList<SyntaxModule> doInBackground(Void... voids) {
                    ArrayList<SyntaxModule> syntaxModules = new ArrayList<>(new RushSearch()
                            .whereEqual("syntaxLanguage", creekPreferences.getProgramLanguage())
                            .and()
                            .whereEqual("moduleId", languageModule.getModuleId())
                            .find(SyntaxModule.class));
                    return syntaxModules;
                }

                @Override
                protected void onPostExecute(ArrayList<SyntaxModule> syntaxModules) {
                    super.onPostExecute(syntaxModules);
                    syntaxInterface.getSyntaxModules(syntaxModules);
                }
            }.execute();

        }
    }


    public void initializeModules( final ModuleInterface moduleInterface ) {
        if( !creekPreferences.getModulesInserted() ) {

            mLanguageModuleDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<LanguageModule> languageModules = new ArrayList<LanguageModule>();
                    for( DataSnapshot childDataSnapShot : dataSnapshot.getChildren() ) {
                        LanguageModule languageModule = childDataSnapShot.getValue(LanguageModule.class);
                        languageModule.save();
                        languageModules.add(languageModule);
                    }
                    moduleInterface.getModules(languageModules);
                    creekPreferences.setModulesInserted(true);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    moduleInterface.onError(databaseError);
                }
            });
        }
        else {
            new AsyncTask<Void, Void, ArrayList<LanguageModule>>() {

                @Override
                protected ArrayList<LanguageModule> doInBackground(Void... voids) {
                    String programLanguage = creekPreferences.getProgramLanguage();
                    if( programLanguage.equalsIgnoreCase("c++") ) {
                        return new ArrayList<LanguageModule>(new RushSearch()
                                .whereEqual("moduleLanguage", creekPreferences.getProgramLanguage())
                                .or()
                                .whereEqual("moduleLanguage", "cpp")
                                .find(LanguageModule.class));
                    }
                    else {
                        return new ArrayList<LanguageModule>(new RushSearch()
                                .whereEqual("moduleLanguage", creekPreferences.getProgramLanguage())
                                .find(LanguageModule.class));
                    }
                }

                @Override
                protected void onPostExecute(ArrayList<LanguageModule> languageModules) {
                    super.onPostExecute(languageModules);
                    moduleInterface.getModules(languageModules);
                }
            }.execute();

        }
    }

    public void initializeProgramIndexes( final ProgramIndexInterface programIndexInterface ) {

        //Get last n number of programs : ? Store total programs in firebase, total_programs - existing max index
        databaseHandler = new DatabaseHandler(mContext);
        int initialPrograms = 31;

        if( creekPreferences.getProgramIndex() == -1 ) {
            CommonUtils.displayProgressDialog(mContext, "Loading program index");
            if( !creekPreferences.isWelcomeDone() ) {
                AuxilaryUtils.generateBigNotification(mContext, "Welcome", "Hey there, Welcome to programmer creek, we have an array of " + programLanguage.toUpperCase() +" programs to be explored; Your learning starts here...");
                creekPreferences.setWelcomeDone(true);
            }
            mProgramDatabase.child(PROGRAM_INDEX_CHILD).limitToFirst(initialPrograms).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Program_Index> program_indices = new ArrayList<Program_Index>();
                    for( DataSnapshot programIndexSnapshot : dataSnapshot.getChildren() ) {
                        Program_Index program_index = programIndexSnapshot.getValue(Program_Index.class);
                        program_index.setmProgram_Language(programLanguage);
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
        if( creekPreferences.getProgramTables() == -1 ) {
            CommonUtils.displayProgressDialog(mContext, "Loading program tables");
            program_tables = new ArrayList<>();
            mProgramDatabase.child(PROGRAM_TABLE_CHILD).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for( DataSnapshot indexSnapshot : dataSnapshot.getChildren() ) {
                        for( DataSnapshot lineSnapShot : indexSnapshot.getChildren() ) {
                            Program_Table program_table = lineSnapShot.getValue(Program_Table.class);
                            program_table.setmProgram_Language(programLanguage);
                            if( program_table != null ) {
                                program_tables.add(program_table);
                            }
                            Log.d(TAG, "Inserted program tables : " + program_tables.size());
                        }
                    }
                    if( program_tables.size() > 0 ) {
                        creekPreferences.setProgramTables(program_tables.size());
                        new DataBaseInsertAsyncTask(mContext, -3, program_tables, new UIUpdateListener() {
                            @Override
                            public void updateUI() {
                                programTableInterface.getProgramTables(program_tables);
                                CommonUtils.dismissProgressDialog();
                            }
                        }).execute();
                    }
                    else {
                        CommonUtils.dismissProgressDialog();
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

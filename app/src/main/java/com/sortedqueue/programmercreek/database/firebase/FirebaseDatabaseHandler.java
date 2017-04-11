package com.sortedqueue.programmercreek.database.firebase;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.Algorithm;
import com.sortedqueue.programmercreek.database.AlgorithmsIndex;
import com.sortedqueue.programmercreek.database.Chapter;
import com.sortedqueue.programmercreek.database.CreekUser;
import com.sortedqueue.programmercreek.database.CreekUserDB;
import com.sortedqueue.programmercreek.database.CreekUserStats;
import com.sortedqueue.programmercreek.database.IntroChapter;
import com.sortedqueue.programmercreek.database.LanguageModule;
import com.sortedqueue.programmercreek.database.PresentationModel;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.ProgramLanguage;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.SlideModel;
import com.sortedqueue.programmercreek.database.SyntaxModule;
import com.sortedqueue.programmercreek.database.UserProgramDetails;
import com.sortedqueue.programmercreek.database.UserRanking;
import com.sortedqueue.programmercreek.database.WikiModel;
import com.sortedqueue.programmercreek.util.AlphaNumComparator;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;
import java.util.HashMap;

import co.uk.rushorm.core.RushCallback;
import co.uk.rushorm.core.RushCore;
import co.uk.rushorm.core.RushSearch;

/**
 * Created by binay on 05/12/16.
 */

public class FirebaseDatabaseHandler {

    private DatabaseReference mProgramDatabase;
    private DatabaseReference mLanguageModuleDatabase;
    private DatabaseReference mSyntaxModuleDatabase;
    private DatabaseReference mProgramWikiDatabase;
    private DatabaseReference mChapterDatabase;
    private DatabaseReference mUserDatabase;
    private DatabaseReference mUserStatsDatabase;
    private DatabaseReference mUserDetailsDatabase;
    private DatabaseReference mIntroChapterDatabase;
    private DatabaseReference mProgramLanguageDatabase;
    private DatabaseReference mPresentationDatabase;

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
    private ArrayList<ProgramTable> program_tables;
    private DatabaseReference mCreekUserDBDatabase;
    private String CREEK_INTRO_DB = "intro_db";
    private String CREEK_USER_DB = "creek_user_db_version";
    private String WIKI_MODULE = "wiki_module";
    private String CREEK_USER_STATS = "user_stats";
    private String CREEK_CHAPTERS = "creek_chapters";
    private String CREEK_PROGRAM_LANGUAGE = "program_language";
    private ArrayList<ProgramLanguage> programLanguages;
    private String ALGORITHM_INDEX = "algorithm_index";
    private String ALGORITHM = "algorithm";
    private String CREEK_PRESENTATIONS_SLIDES = "presentations_slides";
    private String CREEK_PRESENTATIONS = "presentations";
    private String TO_BE_APPROVED = "to_be_approved";

    private DatabaseReference mPresentationSlidesDatabase;

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
        mProgramDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/programs/" + programLanguage );
        mProgramDatabase.keepSynced(true);
        return mProgramDatabase;
    }

    public DatabaseReference getUserDatabase() {
        mUserDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" +CREEK_USER_CHILD );
        mUserDatabase.keepSynced(true);
        return mUserDatabase;
    }

    public DatabaseReference getUserStatsDatabase() {
        mUserStatsDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" +CREEK_USER_STATS );
        mUserStatsDatabase.keepSynced(true);
        return mUserStatsDatabase;
    }

    public DatabaseReference getUserDetailsDatabase() {
        mUserDetailsDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" +CREEK_USER_PROGRAM_DETAILS_CHILD );
        mUserDetailsDatabase.keepSynced(true);
        return mUserDetailsDatabase;
    }

    public DatabaseReference getmPresentationSlidesDatabase() {
        mPresentationSlidesDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + CREEK_PRESENTATIONS_SLIDES);
        mPresentationSlidesDatabase.keepSynced(true);
        return mPresentationSlidesDatabase;
    }

    public DatabaseReference getmPresentationDatabase() {
        mPresentationDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + CREEK_PRESENTATIONS);
        mPresentationDatabase.keepSynced(true);
        return mPresentationDatabase;
    }

    public void getCreekUserDBDatabase() {
        mCreekUserDBDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" +CREEK_USER_DB );
        mCreekUserDBDatabase.keepSynced(true);
    }

    public void getIntroDB() {
        mIntroChapterDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + CREEK_INTRO_DB + "/" + programLanguage );
    }

    public void getProgramLanguageDB() {
        mProgramLanguageDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + CREEK_PROGRAM_LANGUAGE );
    }

    public void writeProgramLanguage(ProgramLanguage programLanguage) {
        getProgramLanguageDB();
        mProgramLanguageDatabase.push().setValue(programLanguage);
    }


    public void updateInviteCount(final int inviteCount) {
        FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/invite_count")
                .runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
        FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/total_invites")
                .runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData currentData) {
                        if (currentData.getValue() == null) {
                            currentData.setValue(inviteCount);
                        } else {
                            currentData.setValue((Long) currentData.getValue() + inviteCount);
                        }
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                    }
                });
    }

    private DatabaseReference mAlgorithmIndexReference;
    public void writeAlgorithmIndex(AlgorithmsIndex algorithmsIndex) {
       /* mAlgorithmIndexReference = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + ALGORITHM_INDEX);
        mAlgorithmIndexReference.push().setValue(algorithmsIndex);*/

    }

    private DatabaseReference mAlgorithmReference;
    public void writeAlgorithm(Algorithm algorithm) {

        mAlgorithmReference = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + ALGORITHM);
        mAlgorithmReference.child( ALGORITHM + "_" + algorithm.getAlgorithmsIndex().getProgramIndex()).setValue(algorithm);
    }

    private String presentationPushId;

    public String getPresentationPushId() {
        return presentationPushId;
    }

    public void setPresentationPushId(String presentationPushId) {
        this.presentationPushId = presentationPushId;
    }

    public String writeSlide(SlideModel slideModel) {
        getmPresentationSlidesDatabase();
        if( presentationPushId == null ) {
            presentationPushId = mPresentationSlidesDatabase.child(creekPreferences.getSignInAccount().replaceAll("[-+.^:,]","")).push().getKey();
        }
        mPresentationSlidesDatabase.child(creekPreferences.getSignInAccount().replaceAll("[-+.^:,]","") + "/"  + presentationPushId).push().setValue(slideModel);
        return presentationPushId;
    }

    public void writeNewPresentation(PresentationModel presentationModel) {
        getmPresentationDatabase();
        mPresentationDatabase.child(TO_BE_APPROVED).push().setValue(presentationModel);
    }

    public interface GetAllPresentationsListener {
        void onSuccess( ArrayList<PresentationModel> presentationModelArrayList );
        void onError( DatabaseError databaseError );
    }
    public void getAllPresentations(final GetAllPresentationsListener getAllPresentationsListener ) {
        getmPresentationDatabase();
        mPresentationDatabase.child(TO_BE_APPROVED).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<PresentationModel> presentationModels = new ArrayList<PresentationModel>();
                for( DataSnapshot childSnapShot : dataSnapshot.getChildren() ) {
                    PresentationModel presentationModel = childSnapShot.getValue(PresentationModel.class);
                    if( presentationModel != null ) {
                        presentationModels.add(presentationModel);
                    }
                }
                if( presentationModels.size() == 0 ) {
                    getAllPresentationsListener.onError(null);
                }
                else {
                    getAllPresentationsListener.onSuccess(presentationModels);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getAllPresentationsListener.onError(databaseError);
            }
        });
    }

    public interface GetAllSlidesListener {
        void onSuccess( ArrayList<SlideModel> slideModelArrayList );
        void onFailure( DatabaseError databaseError );
    }
    public void getAllSlidesListener(String presenterId, String presentationPushId, final GetAllSlidesListener getAllSlidesListener ) {
        getmPresentationSlidesDatabase();
        mPresentationSlidesDatabase.child(presenterId).child(presentationPushId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<SlideModel> slideModelArrayList = new ArrayList<SlideModel>();

                for( DataSnapshot child : dataSnapshot.getChildren() ) {
                    SlideModel slideModel = child.getValue(SlideModel.class);
                    if( slideModel != null ) {
                        slideModelArrayList.add(slideModel);
                    }
                }
                if( slideModelArrayList.size() > 0 )
                    getAllSlidesListener.onSuccess(slideModelArrayList);
                else
                    getAllSlidesListener.onFailure(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getAllSlidesListener.onFailure(databaseError);
            }
        });
    }

    public interface GetAllAlgorithmsListener {
        void onSuccess( ArrayList<AlgorithmsIndex> algorithmsIndexArrayList );
        void onError( DatabaseError databaseError );
    }
    public void getAllAlgorithmIndex(final GetAllAlgorithmsListener getAllAlgorithmsListener ) {
        //CommonUtils.displayProgressDialog(mContext, mContext.getString(R.string.loading));
        mAlgorithmIndexReference = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + ALGORITHM_INDEX);
        mAlgorithmIndexReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<AlgorithmsIndex> algorithmsIndices = new ArrayList<AlgorithmsIndex>();
                for( DataSnapshot snapshot : dataSnapshot.getChildren() ) {
                    AlgorithmsIndex algorithmsIndex = snapshot.getValue(AlgorithmsIndex.class);
                    algorithmsIndices.add(algorithmsIndex);
                }
                if( algorithmsIndices.size() == 0 ) {
                    getAllAlgorithmsListener.onError(null);
                }
                else {
                    getAllAlgorithmsListener.onSuccess(algorithmsIndices);
                }
                CommonUtils.dismissProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getAllAlgorithmsListener.onError(databaseError);
                CommonUtils.dismissProgressDialog();
            }
        });
    }

    public interface GetAlgorithmListener {
        void onSuccess( Algorithm algorithm );
        void onError( DatabaseError databaseError );
    }
    public void getAlgorithmForIndex(int algorithmIndex, final GetAlgorithmListener getAlgorithmListener ) {
        CommonUtils.displayProgressDialog(mContext, mContext.getString(R.string.loading));
        mAlgorithmReference = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" + ALGORITHM);
        mAlgorithmReference.child( ALGORITHM + "_" + algorithmIndex).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Algorithm algorithm = dataSnapshot.getValue(Algorithm.class);
                if( algorithm != null ) {
                    getAlgorithmListener.onSuccess(algorithm);
                }
                else {
                    getAlgorithmListener.onError(null);
                }
                CommonUtils.dismissProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getAlgorithmListener.onError(databaseError);
                CommonUtils.dismissProgressDialog();
            }
        });
    }

    public interface GetProgramLanguageListener {
        void onSuccess( ArrayList<ProgramLanguage> programLanguages );
        void onError(DatabaseError databaseError);
    }

    public void getAllProgramLanguages(final GetProgramLanguageListener getProgramLanguageListener ) {
        CreekUserDB creekUserDB = creekPreferences.getCreekUserDB();
        int totalLocalLanguages = creekPreferences.getTotalLanguages();
        if( totalLocalLanguages == 0 || (creekUserDB != null && totalLocalLanguages < creekUserDB.getTotalLanguages()) ) {
            getProgramLanguageDB();
            mProgramLanguageDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    programLanguages = new ArrayList<>();
                    new AsyncTask<Void, Void,Void>() {

                        @Override
                        protected Void doInBackground(Void... voids) {
                            RushCore.getInstance().deleteAll(ProgramLanguage.class);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            for( DataSnapshot child : dataSnapshot.getChildren() ) {
                                ProgramLanguage programLanguage = child.getValue(ProgramLanguage.class);
                                programLanguages.add(programLanguage);
                                programLanguage.save(new RushCallback() {
                                    @Override
                                    public void complete() {

                                    }
                                });
                            }

                            creekPreferences.setTotalLanguages(programLanguages.size());
                            getProgramLanguageListener.onSuccess(programLanguages);
                        }
                    }.execute();



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    getProgramLanguageListener.onError(databaseError);

                }
            });
        }
        else {
            new AsyncTask<Void, Void, ArrayList<ProgramLanguage>>() {

                @Override
                protected ArrayList<ProgramLanguage> doInBackground(Void... params) {

                    return new ArrayList<>(new RushSearch().find(ProgramLanguage.class));
                }

                @Override
                protected void onPostExecute(ArrayList<ProgramLanguage> programLanguages) {
                    super.onPostExecute(programLanguages);
                    getProgramLanguageListener.onSuccess(programLanguages);
                }
            }.execute();

        }

    }


    public FirebaseDatabaseHandler(Context context) {
        this.mContext = context;
        creekPreferences = new CreekPreferences(mContext);
        programLanguage = creekPreferences.getProgramLanguage();
        if( programLanguage.equals("c++") ) {
            programLanguage = "cpp";
        }
    }

    private void getChaptersDatabase() {
        mChapterDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/" +CREEK_CHAPTERS + "/" + programLanguage );
        mChapterDatabase.keepSynced(true);
    }

    private void getSyntaxModuleDatabase() {
        if( mSyntaxModuleDatabase == null ) {
            mSyntaxModuleDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/"+SYNTAX_MODULE+"/" + programLanguage);
            mSyntaxModuleDatabase.keepSynced(true);
        }
    }

    private void getProgramWikiDatabase() {
        if( mProgramWikiDatabase == null ) {
            mProgramWikiDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/"+WIKI_MODULE+"/" + programLanguage);
            mProgramWikiDatabase.keepSynced(true);
        }
    }


    private void getLanguageModuleDatabase() {
        if( mLanguageModuleDatabase == null ) {
            mLanguageModuleDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl(CREEK_BASE_FIREBASE_URL + "/"+LANGUAGE_MODULE+"/" + programLanguage);
            mLanguageModuleDatabase.keepSynced(true);
        }
    }

    public void writeProgramWiki(final WikiModel wikiModel) {
        getProgramWikiDatabase();
        mProgramWikiDatabase.child(wikiModel.getWikiId()).setValue(wikiModel);
        wikiModel.save(new RushCallback() {
            @Override
            public void complete() {

            }
        });
    }

    public void writeChapter(final Chapter chapter) {
        getChaptersDatabase();
        mChapterDatabase.push().child(chapter.getChapterId()).setValue(chapter);
        chapter.save(new RushCallback() {
            @Override
            public void complete() {
                Log.d(TAG, "Rush ORM : saved successfully " + chapter.toString()  );
            }
        });
    }

    public void writeSyntaxModule(final SyntaxModule syntaxModule) {
        getSyntaxModuleDatabase();
        mSyntaxModuleDatabase.child(  programLanguage + "_" + syntaxModule.getModuleId() + "_" + syntaxModule.getSyntaxModuleId()  ).setValue(syntaxModule);
        syntaxModule.save(new RushCallback() {
            @Override
            public void complete() {
                Log.d(TAG, "Rush ORM : saved successfully " + syntaxModule.toString()  );
            }
        });
    }

    public void writeLanguageModule(final LanguageModule languageModule) {
        getLanguageModuleDatabase();
        mLanguageModuleDatabase.child(languageModule.getModuleLanguage() + "_" + languageModule.getModuleId() ).setValue(languageModule);
        languageModule.save(new RushCallback() {
            @Override
            public void complete() {
                Log.d(TAG, "Rush ORM : saved successfully " + languageModule.toString()  );
            }
        });
    }

    public void writeProgramIndex( ProgramIndex program_index ) {
        getProgramDatabase();
        mProgramDatabase.child(PROGRAM_INDEX_CHILD + "/" + program_index.getProgram_index()).setValue(program_index);
    }

    public void writeProgramTable( ProgramTable program_table ) {
        getProgramDatabase();
        mProgramDatabase.child(PROGRAM_TABLE_CHILD + "/" + program_table.getProgram_index() + "/" + program_table.getLine_No()).setValue(program_table);
    }

    public void writeCreekUser(CreekUser creekUser) {
        getUserDatabase();
        mUserDatabase.child( creekUser.getEmailId().replaceAll("[-+.^:,]","")).setValue(creekUser);
    }

    public void clearAllTables() {
        RushCore.getInstance().clearDatabase();
    }

    public interface GetCreekUserListner {
        void onSuccess( CreekUser creekUser );
        void onFailure( DatabaseError databaseError );
    }
    public void getCreekUser(String emailId, final GetCreekUserListner getCreekUserListner) {
        getUserDatabase();
        mUserDatabase.child( emailId.replaceAll("[-+.^:,]","")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CreekUser creekUser = dataSnapshot.getValue(CreekUser.class);
                if( creekUser != null ) {
                    creekPreferences.setSignInAccount(creekUser.getEmailId());
                    creekPreferences.setAccountName(creekUser.getUserFullName());
                    creekPreferences.setAccountPhoto(creekUser.getUserPhotoUrl());
                    if( creekUser.getProgramLanguage() != null )
                        creekPreferences.setProgramLanguage(creekUser.getProgramLanguage().toLowerCase());
                    getCreekUserListner.onSuccess(creekUser);
                }
                else {
                    getCreekUserListner.onFailure(null);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getCreekUserListner.onFailure(databaseError);
            }
        });

    }

    public void writeCreekUserDB(CreekUserDB creekUserDB) {
        getCreekUserDBDatabase();
        mCreekUserDBDatabase.setValue(creekUserDB);
    }

    public void getLatestCModules() {

    }

    public interface GetIntroChaptersListener {
        void onSuccess( ArrayList<IntroChapter> introChapters );
        void onError( DatabaseError error );
    }

    public void getIntroChapters(final GetIntroChaptersListener getIntroChaptersListener ) {
        getIntroDB();
        CommonUtils.displayProgressDialog(mContext, "Fetching chapters");
        if( !creekPreferences.getIntroChapters() ) {
            mIntroChapterDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final ArrayList<IntroChapter> introChapters = new ArrayList<IntroChapter>();
                    for( DataSnapshot keySnapShot : dataSnapshot.getChildren() ) {
                        introChapters.add(keySnapShot.getValue(IntroChapter.class));
                    }
                    getIntroChaptersListener.onSuccess(introChapters);
                    RushCore.getInstance().save(introChapters, new RushCallback() {
                        @Override
                        public void complete() {
                            Log.d(TAG, "getIntroChapters : Saved to local : " + programLanguage + " : " + introChapters.toString() );
                        }
                    });
                    creekPreferences.setIntroChapters(true);
                    CommonUtils.dismissProgressDialog();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    getIntroChaptersListener.onError(databaseError);
                    CommonUtils.dismissProgressDialog();
                }
            });
        }
        else {
            new AsyncTask<Void, Void, ArrayList<IntroChapter>>() {

                @Override
                protected ArrayList<IntroChapter> doInBackground(Void... voids) {
                    if( programLanguage.equals("c++") || programLanguage.equals("cpp") ) {
                        return new ArrayList<>(new RushSearch()
                                .startGroup()
                                .whereEqual("chapterLanguage", "cpp")
                                .or()
                                .whereEqual("chapterLanguage", "c++")
                                .endGroup()
                                .orderAsc("chapterIndex")
                                .find(IntroChapter.class));
                    }
                    else {
                        return new ArrayList<>(new RushSearch()
                                .whereEqual("chapterLanguage", programLanguage)
                                .orderAsc("chapterIndex")
                                .find(IntroChapter.class));
                    }

                }

                @Override
                protected void onPostExecute(ArrayList<IntroChapter> introChapters) {
                    super.onPostExecute(introChapters);
                    getIntroChaptersListener.onSuccess(introChapters);
                    CommonUtils.dismissProgressDialog();
                }
            }.execute();
        }

    }

    public void writeIntroChapter(IntroChapter chapter) {
        getIntroDB();
        mIntroChapterDatabase.push().setValue(chapter);
    }

    public interface GetChapterListener {
        void onSuccess( ArrayList<Chapter> chaptersList );
        void onErrror( DatabaseError error );
    }

    public void getChaptersInBackground(final GetChapterListener getChapterListener ) {
        getChaptersDatabase();
        if( AuxilaryUtils.isNetworkAvailable() ) {
            mChapterDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Chapter> chapterArrayList = new ArrayList<Chapter>();
                    for( DataSnapshot keyValue : dataSnapshot.getChildren() ) {
                        for( DataSnapshot chapterIdValue : keyValue.getChildren() ) {
                            Chapter chapter = chapterIdValue.getValue(Chapter.class);
                            if (chapter != null) {
                                chapterArrayList.add(chapter);
                            }
                        }
                    }
                    updateChaptersList(chapterArrayList);
                    getChapterListener.onSuccess(chapterArrayList);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    getChapterListener.onErrror(databaseError);
                }
            });
        }
        else {
            new AsyncTask<Void, Void, ArrayList<Chapter>>() {

                @Override
                protected ArrayList<Chapter> doInBackground(Void... voids) {
                    return getOfflineChapters();
                }

                @Override
                protected void onPostExecute(ArrayList<Chapter> chapters) {
                    super.onPostExecute(chapters);
                    if( chapters.size() > 0 )
                        getChapterListener.onSuccess(chapters);
                    else
                        getChapterListener.onErrror(null);
                }
            }.execute();
        }

    }

    private ArrayList<Chapter> getOfflineChapters() {
        return new ArrayList<>(
                new RushSearch()
                .whereEqual("program_Language", programLanguage)
                .orderAsc("chapterId")
                .find(Chapter.class));
    }

    private void updateChaptersList(final ArrayList<Chapter> chapterArrayList) {
        RushCore.getInstance().deleteAll(Chapter.class, new RushCallback() {
            @Override
            public void complete() {
                RushCore.getInstance().save(chapterArrayList, new RushCallback() {
                    @Override
                    public void complete() {

                    }
                });
            }
        });
    }

    public interface GetProgramTablesListener {
        void onSuccess( ArrayList<ProgramTable> programTables );
        void onError( DatabaseError databaseError );
    }

    public void getProgramTablesInBackground(final int mProgramIndex, final GetProgramTablesListener getProgramTablesListener ) {
        if( creekPreferences.getProgramTables() != -1 ) {
            new AsyncTask<Void, Void, ArrayList<ProgramTable>>( ) {

                @Override
                protected ArrayList<ProgramTable> doInBackground(Void... params) {
                    return getProgramTables(mProgramIndex);
                }

                @Override
                protected void onPostExecute(ArrayList<ProgramTable> programTables) {
                    super.onPostExecute(programTables);
                    getProgramTablesListener.onSuccess(programTables);

                }
            }.execute();
        }
       else {
            getProgramDatabase();
            mProgramDatabase.child( "program_tables/" + String.valueOf(mProgramIndex))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<ProgramTable> programTables = new ArrayList<ProgramTable>();
                    for( DataSnapshot indexSnapShot : dataSnapshot.getChildren() ) {
                        ProgramTable programTable = indexSnapShot.getValue(ProgramTable.class);
                        if( programTable != null ) {
                            programTables.add(programTable);
                        }
                    }
                    if( programTables.size() > 0 )
                        getProgramTablesListener.onSuccess(programTables);
                    else
                        getProgramTablesListener.onError(null);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    getProgramTablesListener.onError(databaseError);
                }
            });
        }


    }

    public ArrayList<ProgramTable> getProgramTables(int mProgramIndex) {
            if( programLanguage.equals("c++") || programLanguage.equals("cpp")) {
                return new ArrayList<>(new RushSearch()
                        .whereEqual("program_index", mProgramIndex)
                        .and()
                        .startGroup()
                        .whereEqual("program_Language", "c++")
                        .or()
                        .whereEqual("program_Language", "cpp")
                        .endGroup()
                        .orderAsc("line_No")
                        .find(ProgramTable.class));
            }
            else {
                return new ArrayList<>(new RushSearch()
                        .whereEqual("program_Language", programLanguage)
                        .and()
                        .whereEqual("program_index", mProgramIndex)
                        .orderAsc("line_No")
                        .find(ProgramTable.class));
            }

    }

    public interface GetProgramIndexListener {
        void onSuccess( ProgramIndex programIndex );
        void onError( DatabaseError databaseError );
    }
    public void getProgramIndexInBackGround(final int mProgramIndex, final GetProgramIndexListener getProgramIndexListener ) {

        if( mProgramIndex <= creekPreferences.getProgramIndex() ) {
            new AsyncTask<Void, Void, ProgramIndex>() {

                @Override
                protected ProgramIndex doInBackground(Void... voids) {
                    if( programLanguage.equals("c++") || programLanguage.equals("cpp")) {
                        return new RushSearch()
                                .startGroup()
                                .whereEqual("program_Language", "c++")
                                .or()
                                .whereEqual("program_Language", "cpp")
                                .endGroup()
                                .and()
                                .whereEqual("program_index", mProgramIndex)
                                .findSingle(ProgramIndex.class);
                    }
                    else {
                        return new RushSearch()
                                .whereEqual("program_Language", programLanguage)
                                .and()
                                .whereEqual("program_index", mProgramIndex)
                                .findSingle(ProgramIndex.class);
                    }
                }

                @Override
                protected void onPostExecute(ProgramIndex programIndex) {
                    super.onPostExecute(programIndex);
                    getProgramIndexListener.onSuccess(programIndex);
                }
            }.execute();
        }
        else {
            getProgramDatabase();
            mProgramDatabase.child(PROGRAM_INDEX_CHILD).child(String.valueOf(mProgramIndex)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ProgramIndex programIndex = dataSnapshot.getValue(ProgramIndex.class);
                    getProgramIndexListener.onSuccess(programIndex);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "getProgramIndexInBackGround : " + databaseError.toException().getMessage());
                    databaseError.toException().printStackTrace();
                    getProgramIndexListener.onError(databaseError);
                }
            });
        }




    }

    public interface SyntaxModuleInterface {
        void onSuccess( SyntaxModule syntaxModule );
        void onError( DatabaseError error );
    }
    public void getSyntaxModule(final String syntaxId, final String wizardUrl, final SyntaxModuleInterface syntaxModuleInterface ) {
        Log.d(TAG, "getSyntaxModule : Syntax module comparison : " + ( syntaxId + "_" +wizardUrl + " : " + (creekPreferences.getSyntaxInserted())));
        Log.d(TAG, "getSyntaxModule : Syntax module comparison : " + new AlphaNumComparator().compare( syntaxId + "_" +wizardUrl, (creekPreferences.getSyntaxInserted())));
        if( new AlphaNumComparator().compare( syntaxId + "_" +wizardUrl, (creekPreferences.getSyntaxInserted())) <= 0 ) {
            Log.d(TAG, "getSyntaxModule : Running Async task");
            new AsyncTask<Void, Void, SyntaxModule>( ) {

                @Override
                protected SyntaxModule doInBackground(Void... voids) {
                    if( programLanguage.equals("c++") || programLanguage.equals("cpp")) {
                        return new RushSearch()
                                .whereEqual("syntaxLanguage", "c++")
                                .or()
                                .whereEqual("syntaxLanguage", "cpp")
                                .and()
                                .whereEqual("syntaxModuleId", wizardUrl)
                                .and()
                                .whereEqual("moduleId", syntaxId)
                                .findSingle(SyntaxModule.class);
                    }
                    else {
                        return new RushSearch()
                                .whereEqual("syntaxLanguage", programLanguage)
                                .and()
                                .whereEqual("syntaxModuleId", wizardUrl)
                                .and()
                                .whereEqual("moduleId", syntaxId)
                                .findSingle(SyntaxModule.class);
                    }
                }

                @Override
                protected void onPostExecute(SyntaxModule syntaxModule) {
                    super.onPostExecute(syntaxModule);
                    syntaxModuleInterface.onSuccess(syntaxModule);
                }
            }.execute();
        }
        else {
            Log.d(TAG, "getSyntaxModule : Running Firebase task");
            getSyntaxModuleDatabase();
            mSyntaxModuleDatabase.child(programLanguage + "_" + syntaxId + "_" + wizardUrl ).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    SyntaxModule syntaxModule = dataSnapshot.getValue(SyntaxModule.class);
                    if( syntaxModule == null ) {
                        syntaxModuleInterface.onError(null);
                    }
                    else
                        syntaxModuleInterface.onSuccess(syntaxModule);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "getSyntaxModule : " + databaseError.toException().getMessage());
                    databaseError.toException().printStackTrace();
                    syntaxModuleInterface.onError( databaseError );
                }
            });
        }





    }

    public interface GetWikiModelListener {
        void onSuccess( WikiModel wikiModel );
        void onError( DatabaseError databaseError );
    }

    public void getWikiModel(final String wizardUrl, final GetWikiModelListener getWikiModelListener ) {
        Log.d(TAG, "getWikiModel : Wiki module comparison : " + ( wizardUrl + " : " + (creekPreferences.getProgramWikiInserted())));
        Log.d(TAG, "getWikiModel : Wiki module comparison : " + new AlphaNumComparator().compare( wizardUrl, (creekPreferences.getProgramWikiInserted())));
        if( new AlphaNumComparator().compare( wizardUrl, (creekPreferences.getProgramWikiInserted())) <= 0 ) {
            new AsyncTask<Void, Void, WikiModel>() {

                @Override
                protected WikiModel doInBackground(Void... voids) {
                    if( programLanguage.equals("c++") || programLanguage.equals("cpp")) {
                        return new RushSearch()
                                .whereEqual("wikiId", wizardUrl)
                                .and()
                                .startGroup()
                                .whereEqual("syntaxLanguage", "c++")
                                .or()
                                .whereEqual("syntaxLanguage", "cpp")
                                .endGroup()
                                .findSingle(WikiModel.class);
                    }
                    else {
                        return new RushSearch()
                                .whereEqual("syntaxLanguage", programLanguage)
                                .and()
                                .whereEqual("wikiId", wizardUrl)
                                .findSingle(WikiModel.class);
                    }
                }

                @Override
                protected void onPostExecute(WikiModel wikiModel) {
                    super.onPostExecute(wikiModel);
                    if( wikiModel.getProgramWikis() == null || wikiModel.getProgramWikis().size() == 0 ) {
                        getWikiFromFirebase(wizardUrl, getWikiModelListener);
                    }
                    else
                        getWikiModelListener.onSuccess(wikiModel);
                }
            }.execute();

        }
        else {
            getWikiFromFirebase(wizardUrl, getWikiModelListener);
        }

    }

    private void getWikiFromFirebase(String wizardUrl, final GetWikiModelListener getWikiModelListener) {
        getProgramWikiDatabase();
        mProgramWikiDatabase.child(wizardUrl).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                WikiModel wikiModel = dataSnapshot.getValue(WikiModel.class);
                getWikiModelListener.onSuccess(wikiModel);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "getWikiModel : " + databaseError.toException().getMessage());
                databaseError.toException().printStackTrace();
                getWikiModelListener.onError(databaseError);
            }
        });
    }

    public interface GetCreekUserDBListener {
        void onSuccess( CreekUserDB creekUserDB );
        void onError( DatabaseError databaseError );
    }
    public void readCreekUserDB(final GetCreekUserDBListener getCreekUserDBListener ) {
        getCreekUserDBDatabase();
        mCreekUserDBDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if( dataSnapshot != null ) {
                    CreekUserDB creekUserDB = dataSnapshot.getValue(CreekUserDB.class);
                    creekPreferences.setCreekUserDB(creekUserDB);
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
        getUserDatabase();
        mUserDatabase.child( userProgramDetails.getEmailId().replaceAll("[-+.^:,]","")).setValue(userProgramDetails);
    }

    public interface ProgramWikiInterface {
        void getProgramWiki( ArrayList<WikiModel> programWikis );
        void onError( DatabaseError error );
    }

    public void initializeProgramWiki( final ProgramWikiInterface programWikiInterface ) {
        if( creekPreferences.checkWikiUpdate()  < 0 ) {
            getProgramWikiDatabase();
            mProgramWikiDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<WikiModel> programWikis = new ArrayList<>();
                    Log.d(TAG, "Wiki model Resoponse : " + dataSnapshot.toString());
                    for( DataSnapshot childDataSnapShot : dataSnapshot.getChildren() ) {
                        WikiModel wiki = childDataSnapShot.getValue(WikiModel.class);
                        wiki.save(new RushCallback() {
                            @Override
                            public void complete() {

                            }
                        });
                        programWikis.add(wiki);
                    }
                    programWikiInterface.getProgramWiki(programWikis);
                    creekPreferences.setProgramWikiInserted(programWikis.get( programWikis.size() -1 ).getWikiId());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "initializeProgramWiki : " + databaseError.toException().getMessage());
                    databaseError.toException().printStackTrace();
                    programWikiInterface.onError(databaseError);
                }
            });
        }
        else {
            new AsyncTask<Void, Void, ArrayList<WikiModel>>() {

                @Override
                protected ArrayList<WikiModel> doInBackground(Void... voids) {
                    ArrayList<WikiModel> programWikis;
                    if( creekPreferences.getProgramLanguage().equals("c++") ) {
                        programWikis = new ArrayList<>(new RushSearch()
                                .startGroup()
                                .whereEqual("syntaxLanguage", creekPreferences.getProgramLanguage())
                                .or()
                                .whereEqual("syntaxLanguage", "cpp")
                                .endGroup()
                                .orderAsc("wikiId")
                                .find(WikiModel.class));
                    }
                    else {
                        programWikis = new ArrayList<>(new RushSearch()
                                .whereEqual("syntaxLanguage", creekPreferences.getProgramLanguage())
                                .orderAsc("wikiId")
                                .find(WikiModel.class));
                    }
                    return programWikis;
                }

                @Override
                protected void onPostExecute(ArrayList<WikiModel> programWikis) {
                    super.onPostExecute(programWikis);
                    programWikiInterface.getProgramWiki(programWikis);
                }
            }.execute();

        }
    }

    public interface ModuleInterface {
        void getModules( ArrayList<LanguageModule> languageModules );
        void onError( DatabaseError error );
    }

    public interface ProgramIndexInterface {
        void getProgramIndexes(ArrayList<ProgramIndex> program_indices);
        void onError( DatabaseError error );
    }

    public interface SyntaxInterface {
        void getSyntaxModules( ArrayList<SyntaxModule> syntaxModules );
        void onError( DatabaseError error );
    }

    public void initializeSyntax(final LanguageModule languageModule, final SyntaxInterface syntaxInterface ) {
        if( creekPreferences.checkSyntaxUpdate() < 0 ) {
            Log.d(TAG, "initializeSyntax : Firebase task");
            getSyntaxModuleDatabase();
            mSyntaxModuleDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<SyntaxModule> syntaxModules = new ArrayList<>();
                    String largestSyntaxId = "";
                    AlphaNumComparator alphaNumComparator = new AlphaNumComparator();
                    for( DataSnapshot childDataSnapShot : dataSnapshot.getChildren() ) {
                        SyntaxModule syntaxModule = childDataSnapShot.getValue(SyntaxModule.class);
                        if( largestSyntaxId.equals("") ) {
                            largestSyntaxId = syntaxModule.getModuleId() + "_" + syntaxModule.getSyntaxModuleId();
                        }
                        else {
                            if( alphaNumComparator.compare(largestSyntaxId, syntaxModule.getModuleId() + "_" + syntaxModule.getSyntaxModuleId()) <= 0 ) {
                                largestSyntaxId = syntaxModule.getModuleId() + "_" + syntaxModule.getSyntaxModuleId();
                            }
                        }
                        syntaxModule.save(new RushCallback() {
                            @Override
                            public void complete() {

                            }
                        });
                        if( syntaxModule.getModuleId().equals(languageModule.getModuleId()) ) {
                            syntaxModules.add(syntaxModule);
                        }
                    }
                    syntaxInterface.getSyntaxModules(syntaxModules);
                    Log.d(TAG, "initialize Syntax : Largest Syntax : " + largestSyntaxId);
                    creekPreferences.setSyntaxInserted( largestSyntaxId );
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "initializeSyntax : " + databaseError.toException().getMessage());
                    databaseError.toException().printStackTrace();
                    syntaxInterface.onError(databaseError);
                }
            });
        }
        else {
            Log.d(TAG, "initializeSyntax : Async task");
            new AsyncTask<Void, Void, ArrayList<SyntaxModule>>() {

                @Override
                protected ArrayList<SyntaxModule> doInBackground(Void... voids) {
                    ArrayList<SyntaxModule> syntaxModules;
                    if( creekPreferences.getProgramLanguage().equals("c++") ) {
                        syntaxModules = new ArrayList<>(new RushSearch()
                                .whereEqual("moduleId", languageModule.getModuleId())
                                .and()
                                .startGroup()
                                .whereEqual("syntaxLanguage", creekPreferences.getProgramLanguage())
                                .or()
                                .whereEqual("syntaxLanguage", "cpp")
                                .endGroup()
                                .find(SyntaxModule.class));
                    }
                    else {
                        syntaxModules = new ArrayList<>(new RushSearch()
                                .whereEqual("syntaxLanguage", creekPreferences.getProgramLanguage())
                                .and()
                                .whereEqual("moduleId", languageModule.getModuleId())
                                .find(SyntaxModule.class));
                    }
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

        if( creekPreferences.checkModulesUpdate() < 0 ) {
            Log.d(TAG, "initializeModules : FirebaseDBTask");
            getLanguageModuleDatabase();
            mLanguageModuleDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<LanguageModule> languageModules = new ArrayList<LanguageModule>();
                    for( DataSnapshot childDataSnapShot : dataSnapshot.getChildren() ) {
                        LanguageModule languageModule = childDataSnapShot.getValue(LanguageModule.class);
                        languageModule.save(new RushCallback() {
                            @Override
                            public void complete() {

                            }
                        });
                        languageModules.add(languageModule);
                    }
                    moduleInterface.getModules(languageModules);
                    creekPreferences.setModulesInserted(languageModules.get(languageModules.size() - 1 ).getModuleId());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "initializeModules : " + databaseError.toException().getMessage());
                    databaseError.toException().printStackTrace();
                    moduleInterface.onError(databaseError);
                }
            });
        }
        else {
            Log.d(TAG, "initializeModules : Asnctask");
            new AsyncTask<Void, Void, ArrayList<LanguageModule>>() {

                @Override
                protected ArrayList<LanguageModule> doInBackground(Void... voids) {
                    String programLanguage = creekPreferences.getProgramLanguage();
                    if( programLanguage.equalsIgnoreCase("c++") ) {
                        return new ArrayList<LanguageModule>(new RushSearch()
                                .whereEqual("moduleLanguage", creekPreferences.getProgramLanguage())
                                .or()
                                .whereEqual("moduleLanguage", "cpp")
                                .orderAsc("moduleId")
                                .find(LanguageModule.class));
                    }
                    else {
                        return new ArrayList<LanguageModule>(new RushSearch()
                                .whereEqual("moduleLanguage", creekPreferences.getProgramLanguage())
                                .orderAsc("moduleId")
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

        if( !creekPreferences.checkProgramIndexUpdate() ) {
            CommonUtils.displayProgressDialog((Activity) mContext, "Loading program index");
            if( !creekPreferences.isWelcomeDone() ) {
                AuxilaryUtils.generateBigNotification(mContext, "Welcome", "Hey there, Welcome to Infinite Programmer, we have an array of " + programLanguage.toUpperCase() +" programs to be explored; Your learning starts here...");
                creekPreferences.setWelcomeDone(true);
                getCreekUserStatsInBackground(new CreekUserStatsListener() {
                    @Override
                    public void onSuccess(CreekUserStats creekUserStats) {

                    }

                    @Override
                    public void onFailure(DatabaseError databaseError) {

                    }
                });
            }
            getProgramDatabase();
            mProgramDatabase.child(PROGRAM_INDEX_CHILD)
                    .orderByKey()
                    .limitToLast(creekPreferences.getProgramIndexDifference())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ArrayList<ProgramIndex> program_indices = new ArrayList<>();
                            for( DataSnapshot programIndexSnapshot : dataSnapshot.getChildren() ) {
                                ProgramIndex program_index = programIndexSnapshot.getValue(ProgramIndex.class);
                                program_index.save(new RushCallback() {
                                    @Override
                                    public void complete() {

                                    }
                                });
                                program_indices.add(program_index);
                            }
                            creekPreferences.setProgramIndex(program_indices.get(program_indices.size() - 1 ).getProgram_index());
                            Log.d(TAG, "Inserted program indexes : " + program_indices.size());
                            CommonUtils.dismissProgressDialog();
                            programIndexInterface.getProgramIndexes(program_indices);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d(TAG, "initializeProgramIndexes : " + databaseError.toException().getMessage());
                            databaseError.toException().printStackTrace();
                            programIndexInterface.onError(databaseError);
                            CommonUtils.dismissProgressDialog();
                        }
                    });
        }
        else {
            CommonUtils.displayProgressDialog((Activity) mContext, "Loading program index");
            new AsyncTask<Void, Void, ArrayList<ProgramIndex>>() {

                @Override
                protected ArrayList<ProgramIndex> doInBackground(Void... params) {
                    return new FirebaseDatabaseHandler(mContext).getProgramIndexes();

                }

                @Override
                protected void onPostExecute(ArrayList<ProgramIndex> programIndices) {
                    super.onPostExecute(programIndices);
                    CommonUtils.dismissProgressDialog();
                    programIndexInterface.getProgramIndexes(programIndices);
                }
            }.execute();
            Log.d(TAG, "Inserted program indexes found : " + creekPreferences.getProgramIndex());

        }

    }

    private ArrayList<ProgramIndex> getProgramIndexes() {
        if( programLanguage.equals("c++") || programLanguage.equals("cpp")) {
            return new ArrayList<>(new RushSearch()
                    .startGroup()
                    .whereEqual("program_Language", "c++")
                    .or()
                    .whereEqual("program_Language", "cpp")
                    .endGroup()
                    .orderAsc("program_index")
                    .find(ProgramIndex.class));
        }
        else {
            return new ArrayList<>(new RushSearch()
                    .whereEqual("program_Language", programLanguage)
                    .orderAsc("program_index")
                    .find(ProgramIndex.class));
        }
    }

    public interface ProgramTableInterface {
        void getProgramTables(ArrayList<ProgramTable> program_tables);
        void onError( DatabaseError error );
    }

    public void initializeProgramTables(final ProgramTableInterface programTableInterface ) {
        if( !creekPreferences.checkProgramTableUpdate() ) {
            program_tables = new ArrayList<>();
            getProgramDatabase();
            mProgramDatabase
                    .child(PROGRAM_TABLE_CHILD)
                    .orderByKey()
                    .limitToLast(creekPreferences.getProgramTableDifference())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d(TAG, "initializeProgramTables : indexSnapshot size : " + dataSnapshot.getChildrenCount() );
                    for( DataSnapshot indexSnapshot : dataSnapshot.getChildren() ) {
                        Log.d(TAG, "initializeProgramTables : indexSnapshot size : " + indexSnapshot.getChildrenCount() );
                        for( DataSnapshot lineSnapShot : indexSnapshot.getChildren() ) {
                            ProgramTable program_table = lineSnapShot.getValue(ProgramTable.class);
                            program_table.save(new RushCallback() {
                                @Override
                                public void complete() {

                                }
                            });
                            program_tables.add(program_table);
                            Log.d(TAG, "Inserted program tables : " + program_tables.size());
                        }
                    }
                    Log.d(TAG, "Set Program Tables : " + programLanguage + " : " + program_tables.get(program_tables.size() - 1).getProgram_index());
                    creekPreferences.setProgramTables(program_tables.get(program_tables.size() - 1).getProgram_index());
                    programTableInterface.getProgramTables(program_tables);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "initializeProgramTables : " + databaseError.toException().getMessage());
                    databaseError.toException().printStackTrace();
                    programTableInterface.onError(databaseError);
                }
            });
        }
        else {
            Log.d(TAG, "Inserted program tables found : " + creekPreferences.getProgramTables());
            programTableInterface.getProgramTables(new ArrayList<ProgramTable>());
        }
    }

    public void getAllCreekUserStatsInBackground() {
        getUserStatsDatabase();
        getUserDatabase();
        mUserStatsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, CreekUserStats> creekUserStatsHashMap = new HashMap<>();
                for( DataSnapshot child : dataSnapshot.getChildren() ) {
                    CreekUserStats creekUserStats = child.getValue(CreekUserStats.class);
                    Log.d(TAG, "CreekUserStats : account : " + child.getKey() );
                    creekUserStats.calculateReputation();
                    creekUserStatsHashMap.put(child.getKey(), creekUserStats);
                    mUserStatsDatabase.child( child.getKey().replaceAll("[-+.^:,]","")).setValue(creekUserStats);
                    updateRankingForAllUsers(creekUserStatsHashMap);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateRankingForAllUsers(final HashMap<String, CreekUserStats> creekUserStatsHashMap) {
        mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for( DataSnapshot child : dataSnapshot.getChildren() ) {
                    CreekUser creekUser = child.getValue(CreekUser.class);
                    if( creekUser != null && creekUser.getEmailId() != null ) {
                        String accountEmail = creekUser.getEmailId().replaceAll("[-+.^:,]","");
                        CreekUserStats creekUserStats = creekUserStatsHashMap.get(accountEmail);
                        if( creekUserStats != null ) {
                            UserRanking userRanking = new UserRanking();
                            userRanking.setEmailId(creekUser.getEmailId());
                            userRanking.setUserPhotoUrl(creekUser.getUserPhotoUrl());
                            userRanking.setUserFullName(creekUser.getUserFullName());
                            userRanking.setReputation( creekUserStats.getCreekUserReputation() );
                            mUserDatabase.child("ranking/" + creekUser.getEmailId().replaceAll("[-+.^:,]",""))
                                    .setValue(userRanking);
                        }
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public interface CreekUserStatsListener {
        void onSuccess( CreekUserStats creekUserStats );
        void onFailure( DatabaseError databaseError );
    }

    public void getCreekUserStatsInBackground(final CreekUserStatsListener creekUserStatsListener ) {
        getUserStatsDatabase();
        mUserStatsDatabase.child( creekPreferences.getSignInAccount().replaceAll("[-+.^:,]","")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                CreekUserStats creekUserStats = dataSnapshot.getValue(CreekUserStats.class);
                if( creekUserStats != null ) {
                    creekPreferences.saveCreekUserStats( creekUserStats );
                    Log.d(TAG, "getCreekUserStatsInBackground : success : retrieved stats are : " + creekUserStats.toString());
                    creekUserStatsListener.onSuccess(creekUserStats);

                }
                else {
                    creekUserStatsListener.onFailure(null);
                    creekPreferences.saveCreekUserStats(new CreekUserStats());
                    Log.d(TAG, "getCreekUserStatsInBackground : Failed : creating new stats : " + new CreekUserStats().toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                creekUserStatsListener.onFailure(databaseError);
                creekPreferences.saveCreekUserStats(new CreekUserStats());
            }
        });

    }

    public void writeCreekUserStats( CreekUserStats creekUserStats ) {
        getUserStatsDatabase();
        mUserStatsDatabase.child( creekPreferences.getSignInAccount().replaceAll("[-+.^:,]","")).setValue(creekUserStats);
        creekPreferences.saveCreekUserStats(creekUserStats);
        updateUserRanking(creekUserStats);
    }

    private void updateUserRanking(CreekUserStats creekUserStats) {
        if( creekUserStats.getCreekUserReputation() > 0 ) {
            getUserDatabase();
            UserRanking userRanking = new UserRanking();
            userRanking.setEmailId(creekPreferences.getSignInAccount());
            userRanking.setUserPhotoUrl(creekPreferences.getAccountPhoto());
            userRanking.setUserFullName(creekPreferences.getAccountName());
            userRanking.setReputation( creekUserStats.getCreekUserReputation() );
            mUserDatabase.child("ranking/" + creekPreferences.getSignInAccount().replaceAll("[-+.^:,]",""))
                    .setValue(userRanking);
        }

    }

    public interface GetTopLearnersInterface {
        void onSuccess( ArrayList<UserRanking> userRankings );
        void onFailure( DatabaseError databaseError );
    }
    public void getTopLearners(final GetTopLearnersInterface getTopLearnersInterface ) {
        getUserDatabase();
        Query query = mUserDatabase.child("ranking");
        query.orderByChild("reputation")
                .limitToLast(20)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "UserRanking : " + dataSnapshot.toString());
                ArrayList<UserRanking> userRankings = new ArrayList<UserRanking>();
                for( DataSnapshot child : dataSnapshot.getChildren() ) {
                    userRankings.add(child.getValue(UserRanking.class));
                }
                getTopLearnersInterface.onSuccess(userRankings);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getTopLearnersInterface.onFailure(databaseError);
            }
        });
    }


}

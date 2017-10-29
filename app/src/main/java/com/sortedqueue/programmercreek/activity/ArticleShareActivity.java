package com.sortedqueue.programmercreek.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.ArticleShareAdaper;
import com.sortedqueue.programmercreek.adapter.TagsRecyclerAdapter;
import com.sortedqueue.programmercreek.database.NotesModel;
import com.sortedqueue.programmercreek.database.TagModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.fragments.NotesPreviewFragment;

import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;
import com.sortedqueue.programmercreek.util.NotesUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 24/08/17.
 */

public class ArticleShareActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.languageRecyclerView)
    RecyclerView languageRecyclerView;
    @BindView(R.id.notesRecyclerView)
    RecyclerView notesRecyclerView;
    @BindView(R.id.previewButton)
    Button previewButton;
    @BindView(R.id.saveButton)
    Button saveButton;
    @BindView(R.id.discardButton)
    Button discardButton;
    @BindView(R.id.buttonLayout)
    LinearLayout buttonLayout;
    @BindView(R.id.container)
    FrameLayout container;
    private CreekPreferences creekPreferences;
    private TagsRecyclerAdapter tagsRecyclerAdapter;
    private String sharedText;
    private ArticleShareAdaper notesShareRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_share);
        ButterKnife.bind(this);
        creekPreferences = new CreekPreferences(ArticleShareActivity.this);
        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }
        fetchAllTags();
        previewButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        discardButton.setOnClickListener(this);
        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

    }

    private void fetchAllTags() {

        new FirebaseDatabaseHandler(ArticleShareActivity.this).getAllTags(new FirebaseDatabaseHandler.GetAllTagsListener() {
            @Override
            public void onError(DatabaseError databaseError) {
                CommonUtils.INSTANCE.dismissProgressDialog();
            }

            @Override
            public void onSuccess(TagModel tagModel) {
                setupRecyclerView(tagModel);
            }
        });
    }

    private void setupRecyclerView(TagModel tagModel) {
        languageRecyclerView.setLayoutManager(new LinearLayoutManager(ArticleShareActivity.this, LinearLayoutManager.HORIZONTAL, false));
        tagsRecyclerAdapter = new TagsRecyclerAdapter(tagModel.getTagArrayList(), 1);
        languageRecyclerView.setAdapter(tagsRecyclerAdapter);
    }

    private void handleSendText(Intent intent) {
        sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            new AsyncTask<Void, Void, ArrayList<NotesModel>>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    CommonUtils.INSTANCE.displayProgressDialog(ArticleShareActivity.this, "Loading");
                }

                @Override
                protected void onPostExecute(ArrayList<NotesModel> notesModelArrayList) {
                    super.onPostExecute(notesModelArrayList);
                    CommonUtils.INSTANCE.dismissProgressDialog();
                    setupRecyclerView(notesModelArrayList);
                }

                @Override
                protected ArrayList<NotesModel> doInBackground(Void... voids) {
                    return NotesUtils.INSTANCE.splitParaIntoNotes(sharedText);
                }

            }.execute();
        }
    }

    private void setupRecyclerView(ArrayList<NotesModel> notesModelArrayList) {
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(ArticleShareActivity.this));
        notesShareRecyclerAdapter = new ArticleShareAdaper(notesModelArrayList);
        notesRecyclerView.setAdapter(notesShareRecyclerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                if( tagsRecyclerAdapter.getSelectedTag().equals("") ) {
                    CommonUtils.INSTANCE.displayToast(ArticleShareActivity.this, "Select language");
                    return;
                }
                break;
            case R.id.previewButton:
                if( tagsRecyclerAdapter.getSelectedTag().equals("") ) {
                    CommonUtils.INSTANCE.displayToast(ArticleShareActivity.this, "Select language");
                    return;
                }
                container.setVisibility(View.VISIBLE);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down, R.anim.slide_out_down, R.anim.slide_out_up);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.container, NotesPreviewFragment.Companion.newInstance(notesShareRecyclerAdapter.getNotesModelArrayList(), tagsRecyclerAdapter.getSelectedTag())).commit();
                break;
            case R.id.discardButton:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            return;
        }
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();
    }
}

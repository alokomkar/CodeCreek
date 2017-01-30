package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.adapter.ModulesRecyclerViewAdapter;
import com.sortedqueue.programmercreek.database.LanguageModule;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.SyntaxNavigationListener;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2016-12-25.
 */

public class  ModuleFragment extends Fragment {

    @Bind(R.id.modulesRecyclerView)
    RecyclerView modulesRecyclerView;

    private SyntaxNavigationListener syntaxNavigationListener;
    private ArrayList<LanguageModule> languageModules;
    private String TAG = ModuleFragment.class.getSimpleName();
    private ModulesRecyclerViewAdapter moduleRecyclerAdapter;
    //TODO https://github.com/AdColony/AdColony-Android-SDK-3/wiki/Showing-Interstitial-Ads

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_module, container, false);
        ButterKnife.bind(this, view);
        getModules();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if( context instanceof SyntaxNavigationListener ) {
            syntaxNavigationListener = (SyntaxNavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        syntaxNavigationListener = null;
        super.onDetach();
    }

    private void getModules() {
        CommonUtils.displayProgressDialog(getContext(), "Loading modules");
        new FirebaseDatabaseHandler(getContext()).initializeModules(new FirebaseDatabaseHandler.ModuleInterface() {
            @Override
            public void getModules(ArrayList<LanguageModule> languageModules) {
                setupRecyclerView( languageModules );
            }

            @Override
            public void onError(DatabaseError error) {
                Log.e(TAG, "Error : " + error.getMessage() + " : Details : " + error.getDetails() );
                CommonUtils.dismissProgressDialog();
            }
        });
    }

    private void setupRecyclerView(ArrayList<LanguageModule> languageModulesList) {
        this.languageModules = languageModulesList;
        modulesRecyclerView.setLayoutManager( new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        moduleRecyclerAdapter = new ModulesRecyclerViewAdapter(getContext(), languageModules, new CustomProgramRecyclerViewAdapter.AdapterClickListner() {
            @Override
            public void onItemClick(int position) {
                if( position + 1 < languageModules.size() ) {
                    syntaxNavigationListener.onModuleLoad(languageModules.get(position), languageModules.get(position+1));
                }
                else {
                    syntaxNavigationListener.onModuleLoad(languageModules.get(position), null);
                }

            }
        });
        modulesRecyclerView.setAdapter( moduleRecyclerAdapter );
        CommonUtils.dismissProgressDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if( moduleRecyclerAdapter != null ) {
            moduleRecyclerAdapter.resetAdapter();
        }
    }
}

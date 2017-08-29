package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CodeEditorRecyclerAdapter;
import com.sortedqueue.programmercreek.database.lessons.BitModule;
import com.sortedqueue.programmercreek.interfaces.BitModuleNavigationListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 29/08/17.
 */

public class BitModuleFragment extends Fragment implements View.OnClickListener {


    @BindView(R.id.titleTextView)
    TextView titleTextView;
    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;
    @BindView(R.id.codeRecyclerView)
    RecyclerView codeRecyclerView;
    @BindView(R.id.outputTextView)
    TextView outputTextView;
    @BindView(R.id.backImageView)
    ImageView backImageView;
    @BindView(R.id.nextImageView)
    ImageView nextImageView;
    @BindView(R.id.navigationLayout)
    RelativeLayout navigationLayout;
    @BindView(R.id.checkFAB)
    FloatingActionButton checkFAB;
    private BitModule bitModule;
    private int lastFirstIndicator = -1; //first - 0, last = 1
    private BitModuleNavigationListener navigationListener;

    public void setNavigationListener(BitModuleNavigationListener navigationListener) {
        this.navigationListener = navigationListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bit_module, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleTextView.setText(bitModule.getTitle());
        checkFAB.setVisibility(View.GONE);
        descriptionTextView.setVisibility(View.GONE);
        if (bitModule.getDescription() != null) {
            descriptionTextView.setVisibility(View.VISIBLE);
            descriptionTextView.setText(bitModule.getDescription());
        }

        codeRecyclerView.setVisibility(View.GONE);
        if (bitModule.getCode() != null) {
            codeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            codeRecyclerView.setAdapter(new CodeEditorRecyclerAdapter(getContext(), AuxilaryUtils.splitProgramIntolines(bitModule.getCode()), bitModule.getProgramLanguage(), true));
            checkFAB.setVisibility(View.VISIBLE);
            codeRecyclerView.setVisibility(View.VISIBLE);
        }

        outputTextView.setVisibility(View.GONE);
        if (bitModule.getOutput() != null) {
            outputTextView.setText(bitModule.getOutput());
            outputTextView.setVisibility(View.VISIBLE);
        }

        backImageView.setOnClickListener(this);
        nextImageView.setOnClickListener(this);
        backImageView.setVisibility(lastFirstIndicator == 0 ? View.GONE : View.VISIBLE);
        nextImageView.setVisibility(lastFirstIndicator == 1 ? View.GONE : View.VISIBLE);
    }

    public void setBitModule(BitModule bitModule) {
        this.bitModule = bitModule;
    }

    public void setLastFirstIndicator(int lastFirstIndicator) {
        this.lastFirstIndicator = lastFirstIndicator;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextImageView:
                navigationListener.onMoveForward();
                break;
            case R.id.backImageView:
                navigationListener.onMoveBackward();
                break;
        }
    }
}

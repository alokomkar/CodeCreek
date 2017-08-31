package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CodeEditorRecyclerAdapter;
import com.sortedqueue.programmercreek.database.lessons.BitModule;
import com.sortedqueue.programmercreek.interfaces.BitModuleNavigationListener;
import com.sortedqueue.programmercreek.interfaces.OnBackPressListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 29/08/17.
 */

public class BitModuleFragment extends Fragment implements View.OnClickListener, OnBackPressListener {


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
    @BindView(R.id.slideImageView)
    ImageView slideImageView;
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
        checkFAB.setOnClickListener(this);
        descriptionTextView.setVisibility(View.GONE);
        if (bitModule.getDescription() != null) {
            descriptionTextView.setVisibility(View.VISIBLE);
            descriptionTextView.setText(bitModule.getDescription());
        }

        slideImageView.setVisibility(View.GONE);
        if( bitModule.getImageUrl() != null && bitModule.getImageUrl().trim().length() > 0 ) {
            Glide.with(getContext())
                    .load(bitModule.getImageUrl())
                    .asBitmap()
                    .centerCrop()
                    .error(R.color.md_blue_600)
                    .placeholder(R.color.md_blue_600)
                    .into(slideImageView);
            slideImageView.setVisibility(View.VISIBLE);
        }

        codeRecyclerView.setVisibility(View.GONE);
        if (bitModule.getCode() != null) {
            codeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            codeRecyclerView.setAdapter(new CodeEditorRecyclerAdapter(getContext(), AuxilaryUtils.splitProgramIntolines(bitModule.getCode()), bitModule.getProgramLanguage(), true));
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
        checkFAB.setVisibility(bitModule.getTestMode() == null || bitModule.getTestMode().equalsIgnoreCase("Random") ? View.GONE : View.VISIBLE);
        if (lastFirstIndicator == 1) {
            checkFAB.setVisibility(View.VISIBLE);
            checkFAB.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_done_all));
        }

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
            case R.id.checkFAB:
                if (lastFirstIndicator == 1) {
                    getActivity().onBackPressed();
                } else {
                    if (bitModule.getTestMode() != null) {
                        switch (bitModule.getTestMode()) {
                            case "fill":
                                navigationListener.onTestTriggered(bitModule.getTestMode());
                                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down, R.anim.slide_out_down, R.anim.slide_out_up);
                                fragmentTransaction.addToBackStack(null);
                                BitFillBlankFragment bitFillBlankFragment = new BitFillBlankFragment();
                                bitFillBlankFragment.setOnBackPressListener(BitModuleFragment.this);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("BitModule", bitModule);
                                bitFillBlankFragment.setArguments(bundle);
                                fragmentTransaction.replace(R.id.testContainer, bitFillBlankFragment).commit();
                                break;
                            case "random":
                                break;
                        }
                    }
                }
                break;
            case R.id.nextImageView:
                navigationListener.onMoveForward();
                break;
            case R.id.backImageView:
                navigationListener.onMoveBackward();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            navigationListener.onTestTriggered(null);
            getChildFragmentManager().popBackStack();
        }
    }

    public BitModule getBitModule() {
        return bitModule;
    }

    public boolean isTestLoaded() {
        return getChildFragmentManager().getBackStackEntryCount() > 0;
    }
}

package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CodeEditorRecyclerAdapter;
import com.sortedqueue.programmercreek.adapter.CustomProgramRecyclerViewAdapter;
import com.sortedqueue.programmercreek.adapter.FillCodeRecyclerAdapter;
import com.sortedqueue.programmercreek.adapter.FillOptionsRecyclerAdapter;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.SubTopics;
import com.sortedqueue.programmercreek.interfaces.BitModuleNavigationListener;
import com.sortedqueue.programmercreek.interfaces.OnBackPressListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekAnalytics;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 19/09/17.
 */

public class SubTopicFragment extends Fragment implements View.OnClickListener, OnBackPressListener, CustomProgramRecyclerViewAdapter.AdapterClickListner {


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
    @BindView(R.id.fillCodeLayout)
    LinearLayout fillCodeLayout;
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.testContainer)
    FrameLayout testContainer;
    @BindView(R.id.fillOptionsRecyclerView)
    RecyclerView fillOptionsRecyclerView;
    private SubTopics subTopics;
    private int lastFirstIndicator = -1; //first - 0, last = 1
    private BitModuleNavigationListener navigationListener;
    private CodeEditorRecyclerAdapter codeEditorRecyclerAdapter;
    private String blankString;
    private FillCodeRecyclerAdapter fillCodeAdapter;
    private FillOptionsRecyclerAdapter fillOptionsAdapter;
    private ArrayList<FillCodeRecyclerAdapter> fillCodeRecyclerAdapters;
    private String TAG = BitModuleFragment.class.getSimpleName();

    public void setNavigationListener(BitModuleNavigationListener navigationListener) {
        this.navigationListener = navigationListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_topic, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CreekAnalytics.logEvent(TAG, subTopics.getTitle());
        titleTextView.setText(subTopics.getTitle());
        checkFAB.setVisibility(View.GONE);
        checkFAB.setOnClickListener(this);
        descriptionTextView.setVisibility(View.GONE);
        if (subTopics.getDescription() != null) {
            descriptionTextView.setVisibility(View.VISIBLE);
            descriptionTextView.setText(subTopics.getDescription());
        }

        slideImageView.setVisibility(View.GONE);
        if (subTopics.getImageUrl() != null && subTopics.getImageUrl().trim().length() > 0) {
            Glide.with(getContext())
                    .load(subTopics.getImageUrl())
                    .asBitmap()
                    .fitCenter()
                    .error(R.color.md_blue_600)
                    .placeholder(R.color.md_blue_600)
                    .into(slideImageView);
            slideImageView.setVisibility(View.VISIBLE);
        }

        codeRecyclerView.setVisibility(View.GONE);
        if (subTopics.getCode() != null) {
            codeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            codeEditorRecyclerAdapter = new CodeEditorRecyclerAdapter(getContext(), AuxilaryUtils.splitProgramIntolines(subTopics.getCode()), subTopics.getProgramLanguage(), true);
            codeRecyclerView.setAdapter(codeEditorRecyclerAdapter);
            codeRecyclerView.setVisibility(View.VISIBLE);
        }

        outputTextView.setVisibility(View.GONE);
        if (subTopics.getOutput() != null) {
            outputTextView.setText(subTopics.getOutput());
            outputTextView.setVisibility(View.VISIBLE);
        }

        backImageView.setOnClickListener(this);
        nextImageView.setOnClickListener(this);
        backImageView.setVisibility(lastFirstIndicator == 0 ? View.GONE : View.VISIBLE);
        nextImageView.setVisibility(lastFirstIndicator == 1 ? View.GONE : View.VISIBLE);
        fillOptionsRecyclerView.setVisibility(View.GONE);
        /*if (subTopics.getTestMode() != null && subTopics.getTestMode().equalsIgnoreCase("random")) {
            constructFillBlanks(codeEditorRecyclerAdapter.getProgramLines());
        }*/
        checkFAB.setVisibility(subTopics.getTestMode() == null || subTopics.getTestMode().equalsIgnoreCase("random") ? View.GONE : View.VISIBLE);
        if (lastFirstIndicator == 1) {
            checkFAB.setVisibility(View.VISIBLE);
            checkFAB.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_done_all));
        }

    }

    private void constructFillBlanks(ArrayList<String> programLines) {
        ArrayList<String> fillBlankOptions = new ArrayList<>();
        ArrayList<String[]> fillBlankQuestions = new ArrayList<>();
        fillCodeRecyclerAdapters = new ArrayList<>();
        blankString = "________";
        for (String programLine : programLines) {
            programLine = programLine.replaceAll("  ", " ");
            String[] codeWords = programLine.split(" ");
            if( codeWords != null && codeWords.length > 1 ) {
                int randomIndex = ProgramTable.getRandomNumberInRange(0, codeWords.length - 1);
                fillBlankOptions.add(codeWords[randomIndex]);
                codeWords[randomIndex] = blankString;
                fillBlankQuestions.add(codeWords);
                RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(getContext()).inflate(R.layout.recyclerview_fill_code, null).findViewById(R.id.codeBlanksRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                fillCodeAdapter = new FillCodeRecyclerAdapter(codeWords, randomIndex);
                fillCodeRecyclerAdapters.add(fillCodeAdapter);
                recyclerView.setAdapter(fillCodeAdapter);
                fillCodeLayout.addView(recyclerView);
            }

        }

        fillOptionsRecyclerView.setVisibility(View.VISIBLE);
        fillOptionsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        fillOptionsAdapter = new FillOptionsRecyclerAdapter(fillBlankOptions, this);
        fillOptionsRecyclerView.setAdapter(fillOptionsAdapter);

    }

    public void setSubTopics(SubTopics subTopics) {
        this.subTopics = subTopics;
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
                    if (subTopics.getTestMode() != null) {
                        switch (subTopics.getTestMode().toLowerCase()) {
                            case "fill":
                                navigationListener.onTestTriggered(subTopics.getTestMode());
                                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down, R.anim.slide_out_down, R.anim.slide_out_up);
                                fragmentTransaction.addToBackStack(null);
                                BitFillBlankFragment bitFillBlankFragment = new BitFillBlankFragment();
                                bitFillBlankFragment.setOnBackPressListener(SubTopicFragment.this);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("BitModule", subTopics);
                                bitFillBlankFragment.setArguments(bundle);
                                fragmentTransaction.replace(R.id.testContainer, bitFillBlankFragment).commit();
                                break;
                            case "random":
                                checkSolution();
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

    private void checkSolution() {
        int position = 0;
        int correctAnswers = 0;
        for( String programLine : codeEditorRecyclerAdapter.getProgramLines() ) {
            FillCodeRecyclerAdapter fillCodeRecyclerAdapter = fillCodeRecyclerAdapters.get(position++);
            if( fillCodeRecyclerAdapter.getCodeLine().trim().replaceAll("\\s+", "").equals( programLine.trim().replaceAll("\\s+", "") ) ) {
                correctAnswers++;
            }
            CommonUtils.displayToast(getContext(), "You've got " + correctAnswers + " / " + codeEditorRecyclerAdapter.getProgramLines().size() + " correct");
        }
    }

    @Override
    public void onBackPressed() {
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            navigationListener.onTestTriggered(null);
            getChildFragmentManager().popBackStack();
        }
    }

    public SubTopics getSubTopics() {
        return subTopics;
    }

    public boolean isTestLoaded() {
        return getChildFragmentManager().getBackStackEntryCount() > 0;
    }

    @Override
    public void onItemClick(int position) {
        //Check the adapter with blank position and insert the selected text
        String option = fillOptionsAdapter.getItem(position);
        for( FillCodeRecyclerAdapter fillCodeRecyclerAdapter : fillCodeRecyclerAdapters ) {
            if( fillCodeRecyclerAdapter.getBlankSpace().equals(blankString) ) {
                fillCodeRecyclerAdapter.setCode(option);
                break;
            }
        }
    }
}

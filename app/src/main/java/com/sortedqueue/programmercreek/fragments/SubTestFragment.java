package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.DragNDropAdapter;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.interfaces.DragListenerInterface;
import com.sortedqueue.programmercreek.interfaces.DropListenerInterface;
import com.sortedqueue.programmercreek.interfaces.RemoveListenerInterface;
import com.sortedqueue.programmercreek.interfaces.SubTestCommunicationListener;
import com.sortedqueue.programmercreek.util.ShuffleList;
import com.sortedqueue.programmercreek.view.DragNDropListView;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alok Omkar on 2017-01-26.
 */

public class SubTestFragment extends Fragment {

    @Bind(R.id.dragNDropListView)
    DragNDropListView dragNDropListView;
    private View view;
    private ArrayList<ProgramTable> programTables;
    private ArrayList<String> mProgramList;
    private ArrayList<String> mProgramCheckList;
    private ArrayList<String> mRandomTest;
    private int programSize;
    private DragNDropAdapter dragNDropAdapter;
    private SubTestCommunicationListener subTestCommunicationListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sub_test, container, false);
        ButterKnife.bind(this, view);
        setupAdapter();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if( context instanceof SubTestCommunicationListener ) {
            subTestCommunicationListener = (SubTestCommunicationListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        subTestCommunicationListener = null;
    }

    private void setupAdapter() {
        mProgramList = new ArrayList<String>();
        mProgramCheckList = new ArrayList<String>();
        String programLine = null;
        Iterator<ProgramTable> iteraor = programTables.iterator();
        while(iteraor.hasNext()) {

            ProgramTable newProgramTable = iteraor.next();
            programLine = newProgramTable.getProgram_Line();
            mProgramCheckList.add(programLine);
            mProgramList.add(programLine);
			/*if( programLine.contains("for") ) {
				mProgramList.add(programLine);
			}
			else {
				mProgramList.add(newProgramTable.getmProgram_Line_Html());
			}*/
        }

        mRandomTest = new ArrayList<String>(mProgramList.size());
        for( String item : mProgramList ) {
            mRandomTest.add(item);
        }

        mRandomTest = ShuffleList.shuffleList(mRandomTest);
        programSize = mRandomTest.size();
        dragNDropAdapter = new DragNDropAdapter(getContext(), new int[]{R.layout.dragitem}, new int[]{R.id.programLineTextView}, mRandomTest);
        dragNDropListView.setAdapter(dragNDropAdapter);//new DragNDropAdapter(this,content)
        //mListView.setBackgroundResource(R.drawable.error);
        dragNDropListView.setDropListener(mDropListener);
        dragNDropListView.setRemoveListener(mRemoveListener);
        dragNDropListView.setDragListener(mDragListener);
        subTestCommunicationListener.submitSubTest(index, dragNDropAdapter.getmContent());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setProgramTables(ArrayList<ProgramTable> programTables) {
        this.programTables = programTables;
    }

    private DropListenerInterface mDropListener =
            new DropListenerInterface() {
                public void onDrop(int from, int to) {
                    dragNDropAdapter.onDrop(from, to);
                    dragNDropListView.invalidateViews();
                    subTestCommunicationListener.submitSubTest(index, dragNDropAdapter.getmContent());
                }
            };

    private RemoveListenerInterface mRemoveListener =
            new RemoveListenerInterface() {
                public void onRemove(int which) {
                    dragNDropAdapter.onRemove(which);
                    dragNDropListView.invalidateViews();
                }
            };

    private DragListenerInterface mDragListener =
            new DragListenerInterface() {

                int backgroundColor = 0xe0103010;
                int defaultBackgroundColor;

                public void onDrag(int x, int y, ListView listView) {

                }

                public void onStartDrag(View itemView) {
                    itemView.setVisibility(View.INVISIBLE);
                    defaultBackgroundColor = itemView.getDrawingCacheBackgroundColor();
                    //itemView.setBackgroundColor(backgroundColor);
                    ImageView imageView = (ImageView)itemView.findViewById(R.id.dragItemImageView);
                    if (imageView != null) imageView.setVisibility(View.INVISIBLE);
                }

                public void onStopDrag(View itemView) {
                    itemView.setVisibility(View.VISIBLE);
                    //itemView.setBackgroundColor(defaultBackgroundColor);
                    ImageView imageView = (ImageView)itemView.findViewById(R.id.dragItemImageView);
                    if (imageView != null) imageView.setVisibility(View.VISIBLE);
                }

            };

    private int index;
    public void setIndex(int index) {
        this.index = index;
    }

    public void setSubmitTestCommunicationListener(SubTestCommunicationListener subTestCommunicationListener) {
        this.subTestCommunicationListener = subTestCommunicationListener;
    }
}

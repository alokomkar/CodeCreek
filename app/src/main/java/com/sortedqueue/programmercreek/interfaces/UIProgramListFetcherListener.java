package com.sortedqueue.programmercreek.interfaces;

import com.sortedqueue.programmercreek.database.ProgramIndex;

import java.util.ArrayList;



public interface UIProgramListFetcherListener {
	void updateUIProgramList(ArrayList<ProgramIndex> program_Indexes);
}

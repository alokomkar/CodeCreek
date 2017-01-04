package com.sortedqueue.programmercreek.interfaces;

import com.sortedqueue.programmercreek.database.ProgramTable;

import java.util.ArrayList;



public interface UIProgramFetcherListener {
	void updateUI(ArrayList<ProgramTable> program_TableList);
}

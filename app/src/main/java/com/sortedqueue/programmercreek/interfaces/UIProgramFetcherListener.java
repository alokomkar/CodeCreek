package com.sortedqueue.programmercreek.interfaces;

import com.sortedqueue.programmercreek.database.Program_Table;

import java.util.List;



public interface UIProgramFetcherListener {
	void updateUI(List<Program_Table> program_TableList);
}

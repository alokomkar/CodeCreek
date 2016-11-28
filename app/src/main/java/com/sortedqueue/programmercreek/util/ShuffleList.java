package com.sortedqueue.programmercreek.util;

import java.util.ArrayList;
import java.util.Random;

public class ShuffleList {
	public static ArrayList<String> shuffleList(ArrayList<String> randomList) {
		int n = randomList.size();
		Random random = new Random();
		random.nextInt();
		for (int i = 0; i < n; i++) {
			int change = i + random.nextInt(n - i);
			swap(randomList, i, change);
		}
		return randomList;
	}

	private static void swap(ArrayList<String> list, int i, int change) {
		String helper = list.get(i);
		list.set(i, list.get(change));
		list.set(change, helper);
	}

}

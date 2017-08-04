package com.sortedqueue.programmercreek.interfaces;

/**
 * Created by Alok on 05/01/17.
 */

public interface DashboardNavigationListener {
    void navigateToDashboard();
    void navigateToLanguage();
    void calculateReputation();
    void showInviteDialog();
    void importFromFile();
    void importCodeFile();
    void onProgressStatsUpdate(int points);
    void hideLanguageFragment();
    void showQuickReferenceFragment();
}

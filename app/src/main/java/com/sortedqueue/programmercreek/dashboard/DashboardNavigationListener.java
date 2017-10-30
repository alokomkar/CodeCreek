package com.sortedqueue.programmercreek.dashboard;

/**
 * Created by Alok on 05/01/17.
 */

public interface DashboardNavigationListener {
    void navigateToDashboard();
    void navigateToLanguage();
    void calculateReputation();
    void showInviteDialog();
    void importFromWeb();
    void onProgressStatsUpdate(int points);
    void hideLanguageFragment();
    void showQuickReferenceFragment();
}

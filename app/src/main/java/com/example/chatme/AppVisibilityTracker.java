package com.example.chatme;
public class AppVisibilityTracker {

    private static AppVisibilityTracker instance;
    private boolean isAppInForeground;

    private AppVisibilityTracker() {
        // Private constructor to enforce singleton pattern
        isAppInForeground = false;
    }

    public static AppVisibilityTracker getInstance() {
        if (instance == null) {
            instance = new AppVisibilityTracker();
        }
        return instance;
    }

    public boolean isAppInForeground() {
        return isAppInForeground;
    }

    public void setAppInForeground(boolean inForeground) {
        isAppInForeground = inForeground;
    }
}

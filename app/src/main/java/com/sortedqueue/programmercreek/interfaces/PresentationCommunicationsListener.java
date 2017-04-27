package com.sortedqueue.programmercreek.interfaces;

import com.sortedqueue.programmercreek.database.SlideModel;

import java.util.ArrayList;

/**
 * Created by Alok on 10/04/17.
 */

public interface PresentationCommunicationsListener {
    void onPresentationTitle(String presentationTitle, String presentationDescription, ArrayList<String> tagsList );
    void onPresentationCreation(String presentationId, SlideModel slideModel);
    void onPresentationComplete();
}

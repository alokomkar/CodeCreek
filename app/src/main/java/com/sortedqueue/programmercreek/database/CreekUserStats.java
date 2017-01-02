package com.sortedqueue.programmercreek.database;

/**
 * Created by Alok Omkar on 2017-01-01.
 */

public class CreekUserStats {

    public static final int CHOICE_C = 1;
    public static final int CHOICE_CPP = 1;
    public static final int CHOICE_JAVA = 1;

    private int choiceOfLanguage; //Can take the above values

    private String progressJson;
    //{"programLanguage" : {"wiki" : progress, "syntax" : progress},
    // "programsProgress" : {
    //      "programIndex" : programIndex,
    //      "programProgress" : {"quiz" : {
    //                                     "isAttempted" : true/false,
    //                                     "score" : int
    //                                     "maxScore" : int
    //                                    },
    //                           "test" : {
    //                                     "isAttempted" : true/false,
    //                                     "score" : int
    //                                     "maxScore" : int
    //                                     },
    //                           "match2rddddf" : {
    //                                     "isAttempted" : true/false,
    //                                     "score" : int
    //                                     "maxScore" : int
    //                                     }
    //                          }
    // }

}

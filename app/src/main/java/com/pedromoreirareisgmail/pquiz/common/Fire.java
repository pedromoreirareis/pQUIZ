package com.pedromoreirareisgmail.pquiz.common;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fire {

    private static DatabaseReference refRoot;
    private static DatabaseReference refUsers;
    private static DatabaseReference refCategorys;
    private static DatabaseReference refQuestions;
    private static DatabaseReference refScores;
    private static DatabaseReference refRanking;
    private static DatabaseReference refAnswer;



    public static void setRefRoot() {

        Fire.refRoot = null;
    }

    public static void setRefUsers() {

        Fire.refUsers = null;
    }

    public static void setRefCategorys() {

        Fire.refCategorys = null;
    }

    public static void setRefQuestions() {

        Fire.refQuestions = null;
    }

    public static void setRefScores() {

        Fire.refScores = null;
    }

    public static void setRefRanking() {

        Fire.refRanking = null;
    }

    public static void setRefAnswer() {
        Fire.refAnswer = null;
    }

    //********************************************************************************************//

    private static DatabaseReference getRefRoot() {

        if (refRoot == null) {

            refRoot = FirebaseDatabase.getInstance().getReference();
        }

        return refRoot;
    }

    public static DatabaseReference getRefUsers() {

        if (refUsers == null) {

            refUsers = getRefRoot().child(Common.FOLDER_USERS);
        }
        return refUsers;
    }

    public static DatabaseReference getRefCategorys() {

        if (refCategorys == null) {

            refCategorys = getRefRoot().child(Common.FOLDER_CATEGORYS);
        }

        return refCategorys;
    }


    public static DatabaseReference getRefQuestions() {

        if(refQuestions == null){

            refQuestions = getRefRoot().child(Common.FOLDER_QUESTIONS);
        }

        return refQuestions;
    }

    public static DatabaseReference getRefScores() {

        if(refScores == null){

            refScores = getRefRoot().child(Common.FOLDER_SCORES_USER);
        }

        return refScores;
    }

    public static DatabaseReference getRefRanking() {

        if(refRanking == null){

            refRanking = getRefRoot().child(Common.FOLDER_RANKINGS);
        }

        return refRanking;
    }

    public static DatabaseReference getRefAnswer() {

        if(refAnswer == null){

            refAnswer = getRefRoot().child(Common.FOLDER_ANSWERS);
        }

        return refAnswer;
    }
}

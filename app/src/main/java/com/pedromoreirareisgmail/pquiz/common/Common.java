package com.pedromoreirareisgmail.pquiz.common;

import com.pedromoreirareisgmail.pquiz.models.Answer;
import com.pedromoreirareisgmail.pquiz.models.Question;
import com.pedromoreirareisgmail.pquiz.models.User;

import java.util.ArrayList;
import java.util.List;

public class Common {

    /*  Nome das Pastas no Firebase */
    public static final String FOLDER_USERS = "Users";
    public static final String FOLDER_CATEGORYS = "Categorys";
    public static final String FOLDER_QUESTIONS = "Questions";
    public static final String FOLDER_SCORES_USER = "ScoresUser";
    public static final String FOLDER_RANKINGS = "Rankings";
    public static final String FOLDER_ANSWERS = "Answers";


    /*  Listas com dados das questoes e respostas*/
    public static List<Question> questionsList = new ArrayList<>(); //  Lista das questões
    public static List<Answer> answersList = new ArrayList<>();    //  Lista das respostas

    /*  Constantes*/
    public static final String CATEGORY_ID = "categoryId";
    public static final String USER = "user";
    public static final String SCORE = "score";
    public static final String TRUE = "true";

    //  Váriaveis
    public static String categoryId;
    public static String categoryName;
    public static User currentUser;

    //  Constantes para INTENTS
    public static final String INTENT_SCORE = "score";
    public static final String INTENT_TOTAL = "total";
    public static final String INTENT_CORRECT = "correct";
    public static final String INTENT_USER_NAME = "userName";
    public static final String INTENT_CATEGORY_ID = "categoryId";

}

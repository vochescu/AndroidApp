package ro.ase.eu.proiect.util;

import java.text.SimpleDateFormat;

public interface Constants {
    String ADD_LINE_NUMBER = "BusNumber";
    Integer ADD_LINE_REQUEST_CODE=123;

    String ADD_LINE_EDIT_KEY = "BusEdit";
    Integer ADD_LINE_EDIT_REQUEST_CODE=124;
    Integer RESULT_DELETE_LINE = -100;

    String ADD_STOP_NUMBER = "StopAdd";
    Integer ADD_STOP_REQUEST_CODE=125;

    String ADD_STOP_EDIT_KEY = "StopEdit";
    Integer ADD_STOP_EDIT_REQUEST_CODE=126;
    Integer RESULT_DELETE_STOP = -101;

    String STOP_LINE_SEND_KEY = "LineSend";
    Integer STOP_ACTIVITY_REQUEST_CODE = 127;

    String SPECIAL_PHONE_NUMBER="7458";
    String LINE_E780="E780";
    String LINE_E783="E783";

    Integer EDIT_FAVORITE_LINE_REQUEST_CODE=125;
    String EDIT_FAVORITE_LINE_KEY="FavoriteLine";

    Integer MAP_LINE_REQUEST_CODE=200;
    String MAP_LINE_KEY1 = "MapPoint1";
    String MAP_LINE_KEY2 = "MapPoint2";

    Float BUCHAREST_LAT = 44.4377401f;
    Float BUCHAREST_LNG = 25.9545541f;

    String HTTP_BUS_TAG="bus";
    String HTTP_BUS_NUMBER_TAG="busNumber";
    String HTTP_STARTING_POINT_TAG="startingPoint";
    String HTTP_ENDING_POINT_TAG="endingPoint";
    String HTTP_STATION_NAME_TAG="stationName";
    String HTTP_LATITUDE_TAG="latitude";
    String HTTP_LONGITUDE_TAG="longitude";
    String HTTP_PROGRAM_TAG="program";
    String HTTP_TIMETABLE_MF_TAG="timetableMF";
    String HTTP_TIMETABLE_SS_TAG="timetableSS";
    String HTTP_PERIOD_VALIDITY_TAG="periodValidity";

    String ABOUT_SHARED_PREFERENCES_NAME = "aboutSharedPreferences";
    String ABOUT_RATING_BAR_NAME = "rating";

    String FEEDBACK_SHARED_PREFERENCES_NAME = "feedbackSharedPreferences";
    String FEEDBACK_PHONE_VIEW_NAME =  "phone";
    String FEEDBACK_EMAIL_RECIPIENT = "anca.vochescu@gmail.com";
    Integer FEEDBACK_GALLERY_REQUEST_CODE = 400;

    String BUS_ACTIVITY_SHARED_PREFERENCES_NAME="busSharedPreferences";
    String BUS_ACTIVITY_INIT_DATABASE="busDBInitiated";

    String CSV_SEPARATOR=",";
    String CSV_NEW_LINE="\n";
    String CSV_LINES_FILE_NAME="LinesReport.csv";
    String CSV_FEEDBACK_FILE_NAME="FeedbackReport.csv";
    String CSV_FAV_LINES_FILE_NAME="FavLinesReport.csv";

    String RAPORT_MAIN_CALLER_KEY = "mainCaller";
    String RAPORT_FAV_LINES_CALLER_KEY = "favLinesCaller";


}

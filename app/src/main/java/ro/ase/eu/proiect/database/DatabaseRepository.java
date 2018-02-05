package ro.ase.eu.proiect.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ro.ase.eu.proiect.util.FavoriteLine;
import ro.ase.eu.proiect.util.Line;
import ro.ase.eu.proiect.util.Route;
import ro.ase.eu.proiect.util.RouteStop;
import ro.ase.eu.proiect.util.Stop;

public class DatabaseRepository implements DatabaseConstants{
    SQLiteDatabase database;
    DatabaseController controller;

    public DatabaseRepository(Context context){
        controller = DatabaseController.getInstance(context);
    }

    public void open(){
        try {
            database = controller.getWritableDatabase();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            database.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    // insert into stop table
    public Long insertStop(Stop stop){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STOP_NAME,stop.getName());
        contentValues.put(COLUMN_STOP_LATITUDE,stop.getLatitude());
        contentValues.put(COLUMN_STOP_LONGITUDE,stop.getLongitude());
        return database.insert(TABLE_NAME_STOP,null,contentValues);
    }

    // select * from stop;
    public List<Stop> getAllStops(){
        List<Stop> stops = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NAME_STOP,null,null,null,null,null,null);
        while(cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_STOP_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_STOP_NAME));
            String lat = cursor.getString(cursor.getColumnIndex(COLUMN_STOP_LATITUDE));
            String lng = cursor.getString(cursor.getColumnIndex(COLUMN_STOP_LONGITUDE));
            stops.add(new Stop(id, name, lat, lng));
        }
        cursor.close();
        return stops;
    }

    // select one from stop;
    public Stop getOneStop(Long i){
        Stop stop = new Stop();
        Cursor cursor = database.query(TABLE_NAME_STOP,null,COLUMN_STOP_ID+ "=?",new String[]{i.toString()},null,null,null);
        while(cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_STOP_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_STOP_NAME));
            String lat = cursor.getString(cursor.getColumnIndex(COLUMN_STOP_LATITUDE));
            String lng = cursor.getString(cursor.getColumnIndex(COLUMN_STOP_LONGITUDE));
            stop = new Stop(id, name, lat, lng);
        }
        cursor.close();
        return stop;
    }
// Am folosit deja pentru doua clase toate tipurile de DML... voi face si pentru tabela Stop implementarea
    // update one stop
    public void updateStop(Stop stop) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STOP_NAME,stop.getName());
        contentValues.put(COLUMN_STOP_LATITUDE,stop.getLatitude());
        contentValues.put(COLUMN_STOP_LONGITUDE,stop.getLongitude());

        database.update(TABLE_NAME_STOP,contentValues,COLUMN_STOP_ID + "=?",
                new String[]{stop.getId().toString()});
    }

    // delete one stop
    public void removeStop(Stop stop){
        database.delete(TABLE_NAME_STOP,COLUMN_STOP_ID + "=?",new String[]{stop.getId().toString()});
    }

    // delete one RouteStop
    public void removeRouteStop(RouteStop routeStop){
        database.delete(TABLE_NAME_ROUTESTOP,COLUMN_ROUTESTOP_LINEID + " = ? AND " + COLUMN_ROUTESTOP_STOPID + "=?",
                new String[]{routeStop.getRouteId().toString(),routeStop.getStopId().toString()});
    }

    // insert into RouteStop
    public void insertRouteStop(RouteStop routeStop){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ROUTESTOP_LINEID,routeStop.getRouteId());
        contentValues.put(COLUMN_ROUTESTOP_STOPID,routeStop.getStopId());
        database.insert(TABLE_NAME_ROUTESTOP,null,contentValues);
    }


    // insert into Line values
    public Long insertLine(Line line){
        if(line == null)
            return -1L;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LINE_GLOBALID,line.getGlobalId());
        contentValues.put(COLUMN_LINE_NUMBER,line.getNumber());
        contentValues.put(COLUMN_LINE_STARTINGPOINT,line.getStartingPoint());
        contentValues.put(COLUMN_LINE_ENDINGPOINT,line.getEndingPoint());
        return  database.insert(TABLE_NAME_LINE, null, contentValues);
    }

    // select * from lines;
    public List<Line> getAllLines(){
        List<Line> lines = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NAME_LINE,null,null,null,null,null,null);
        while(cursor.moveToNext()) {
            String globalId = cursor.getString(cursor.getColumnIndex(COLUMN_LINE_GLOBALID));
            Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_LINE_ID));
            String number = cursor.getString(cursor.getColumnIndex(COLUMN_LINE_NUMBER));
            String startingPoint = cursor.getString(cursor.getColumnIndex(COLUMN_LINE_STARTINGPOINT));
            String endingPoint = cursor.getString(cursor.getColumnIndex(COLUMN_LINE_ENDINGPOINT));

            List<Stop> stops = new ArrayList<>();
            Cursor cursorRouteStop = database.query(TABLE_NAME_ROUTESTOP,null, COLUMN_ROUTESTOP_LINEID + " = ? ",new String[]{id.toString()},null,null,null);
            while(cursorRouteStop.moveToNext()){
                Long stopId = cursorRouteStop.getLong(cursorRouteStop.getColumnIndex(COLUMN_ROUTESTOP_STOPID));
                Cursor cursorStop = database.query(TABLE_NAME_STOP,null,COLUMN_STOP_ID+ " = ? ",new String[]{stopId.toString()},null,null,null);
                if(cursorStop != null){
                    cursorStop.moveToFirst();
                    Long idStop = cursorStop.getLong(cursor.getColumnIndex(COLUMN_STOP_ID));
                    String name = cursorStop.getString(cursorStop.getColumnIndex(COLUMN_STOP_NAME));
                    String lat = cursorStop.getString(cursorStop.getColumnIndex(COLUMN_STOP_LATITUDE));
                    String lng = cursorStop.getString(cursorStop.getColumnIndex(COLUMN_STOP_LONGITUDE));
                    stops.add(new Stop(idStop, name, lat, lng));
            }
            cursorStop.close();
            }
            cursorRouteStop.close();
            lines.add(new Line(globalId, id, number, startingPoint, endingPoint,stops));
        }
        cursor.close();
        return lines;
    }

    // select one from lines;
    public Line getOneLine(Long i){
        Line line = new Line();
        Cursor cursor = database.query(TABLE_NAME_LINE,null,COLUMN_LINE_ID + "=?",new String[]{i.toString()},null,null,null);
        while(cursor.moveToNext()) {
            String globalId = cursor.getString(cursor.getColumnIndex(COLUMN_LINE_GLOBALID));
            Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_LINE_ID));
            String number = cursor.getString(cursor.getColumnIndex(COLUMN_LINE_NUMBER));
            String startingPoint = cursor.getString(cursor.getColumnIndex(COLUMN_LINE_STARTINGPOINT));
            String endingPoint = cursor.getString(cursor.getColumnIndex(COLUMN_LINE_ENDINGPOINT));
            List<Stop> stops = new ArrayList<>();
            Cursor cursorRouteStop = database.query(TABLE_NAME_ROUTESTOP,null, COLUMN_ROUTESTOP_LINEID + "=?",new String[]{id.toString()},null,null,null);
            while(cursorRouteStop.moveToNext()){
                Long stopId = cursorRouteStop.getLong(cursorRouteStop.getColumnIndex(COLUMN_ROUTESTOP_STOPID));
                Cursor cursorStop = database.query(TABLE_NAME_STOP,null,COLUMN_STOP_ID+ "=?",new String[]{stopId.toString()},null,null,null);
                if(cursorStop != null){
                    cursorStop.moveToFirst();
                    Long idStop = cursorStop.getLong(cursorStop.getColumnIndex(COLUMN_STOP_ID));
                    String name = cursorStop.getString(cursorStop.getColumnIndex(COLUMN_STOP_NAME));
                    String lat = cursorStop.getString(cursorStop.getColumnIndex(COLUMN_STOP_LATITUDE));
                    String lng = cursorStop.getString(cursorStop.getColumnIndex(COLUMN_STOP_LONGITUDE));
                    stops.add(new Stop(idStop, name, lat, lng));
                }
                cursorStop.close();
            }
            cursorRouteStop.close();
            line = new Line(globalId, id, number, startingPoint, endingPoint, stops);
        }
        cursor.close();
        return line;
    }
    // update one line
    public void updateLine(Line line) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LINE_GLOBALID,line.getGlobalId());
        contentValues.put(COLUMN_LINE_NUMBER,line.getNumber());
        contentValues.put(COLUMN_LINE_STARTINGPOINT,line.getStartingPoint());
        contentValues.put(COLUMN_LINE_ENDINGPOINT,line.getEndingPoint());

        database.update(TABLE_NAME_LINE,contentValues,COLUMN_LINE_ID + "=?",
                new String[]{line.getId().toString()});
    }

    // delete one line
    public void removeLine(Line line){
        database.delete(TABLE_NAME_LINE,COLUMN_LINE_ID + "=?",new String[]{line.getId().toString()});
    }

    // select * from line join favoriteline
    public List<Line> getAllFavLinesJoin(){
        List<Line> lines = new ArrayList<>();
        Cursor cursor = database.rawQuery(JOIN_TABLES_FAVORITELINE_LINE,null);
        while(cursor.moveToNext()) {
            String globalId = cursor.getString(cursor.getColumnIndex(COLUMN_LINE_GLOBALID));
            Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_LINE_ID));
            String number = cursor.getString(cursor.getColumnIndex(COLUMN_LINE_NUMBER));
            String startingPoint = cursor.getString(cursor.getColumnIndex(COLUMN_LINE_STARTINGPOINT));
            String endingPoint = cursor.getString(cursor.getColumnIndex(COLUMN_LINE_ENDINGPOINT));
            lines.add(new Line(globalId, id, number, startingPoint, endingPoint,null));
        }
        cursor.close();
        return lines;
    }
    // select * from favorite line
    public List<FavoriteLine> getAllFavLines(){
        List<FavoriteLine> favLines = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NAME_FAVORITELINE, null,
                null, null, null, null, null
        );

        while(cursor.moveToNext()) {
            Long lineId = cursor.getLong(cursor.getColumnIndex(COLUMN_FAVORITELINE_ID));
            favLines.add(new FavoriteLine(lineId));
        }
        cursor.close();
        return favLines;
    }

    // insert into favorite line
    public Long insertFavLine(FavoriteLine line) {
        if (line == null)
            return -1L;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FAVORITELINE_ID, line.getLineId());

        return database.insert(TABLE_NAME_FAVORITELINE, null, contentValues);
    }

    // update favorite line
    public void updateFavLine(FavoriteLine lineToUpdate, FavoriteLine line) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FAVORITELINE_ID, line.getLineId());

        database.update(TABLE_NAME_FAVORITELINE,contentValues,COLUMN_FAVORITELINE_ID + "=?",
                new String[]{lineToUpdate.getLineId().toString()});
    }
    // delete from favLine
    public void removeFavLine(FavoriteLine line){
        database.delete(TABLE_NAME_FAVORITELINE,COLUMN_FAVORITELINE_ID+ "=?",new String[]{line.getLineId().toString()});
    }
}

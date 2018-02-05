package ro.ase.eu.proiect.networking;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ro.ase.eu.proiect.util.Constants;

public class HttpConnection extends AsyncTask<String,Void,HttpResponse>{

    URL url;
    HttpURLConnection connection;

    @Override
    protected HttpResponse doInBackground(String... strings) {
        try {
            url= new URL(strings[0]);
            connection=(HttpURLConnection)url.openConnection();
            InputStream inputStream=connection.getInputStream();
            InputStreamReader inputStreamReader= new InputStreamReader(inputStream);
            BufferedReader reader= new BufferedReader(inputStreamReader);

            String line= reader.readLine();
            reader.close();
            inputStreamReader.close();
            inputStream.close();
            connection.disconnect();

            return getHttpResponseFromJson(line);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }

    private HttpResponse getHttpResponseFromJson(String json) throws JSONException{
        if(json == null){
            return null;
        }
        JSONObject object = new JSONObject(json);

        List<BusItem> buses= getArrayItemFromJson(object.getJSONArray(Constants.HTTP_BUS_TAG));
        return new HttpResponse(buses);
    }

    private List<BusItem> getArrayItemFromJson(JSONArray array) throws JSONException{
        if(array == null){
            return null;
        }
        List<BusItem> result = new ArrayList<>();

        for(int i=0;i<array.length();i++){
            BusItem busItem= getBusItemFromJson(array.getJSONObject(i));
            if(busItem != null){
                result.add(busItem);
            }
        }
        return result;
    }

    private BusItem getBusItemFromJson(JSONObject object) throws JSONException{
        if(object == null){
            return null;
        }
        BusItem busItem = new BusItem();
        Station station = new Station();

        busItem.setBusNumber(object.getString(Constants.HTTP_BUS_NUMBER_TAG));

        station = getStationItemFromJson(object.getJSONObject(Constants.HTTP_STARTING_POINT_TAG));
        if(station != null){
            busItem.setStartingPoint(station);
        }
        station = getStationItemFromJson(object.getJSONObject(Constants.HTTP_ENDING_POINT_TAG));
        if(station != null){
            busItem.setEndingPoint(station);
        }
        return busItem;
    }

    private Station getStationItemFromJson(JSONObject object) throws JSONException{
        if(object == null){
            return  null;
        }
        Station station = new Station();

        station.setStationName(object.getString(Constants.HTTP_STATION_NAME_TAG));
        station.setLatitude(object.getString(Constants.HTTP_LATITUDE_TAG));
        station.setLongitude(object.getString(Constants.HTTP_LONGITUDE_TAG));

        Program program = getProgramItemFromJson(object.getJSONObject(Constants.HTTP_PROGRAM_TAG));
        if(program != null){
            station.setProgram(program);
        }
        return station;
    }

    private Program getProgramItemFromJson(JSONObject object) throws JSONException{
        if(object == null){
            return null;
        }
        Program program = new Program();
        List<String>  timetableMF = getStringArrayItemFromJson(object.getJSONArray(Constants.HTTP_TIMETABLE_MF_TAG));
        if (timetableMF != null){
            program.setTimetableMF(timetableMF);
        }

        List<String>  timetableSS = getStringArrayItemFromJson(object.getJSONArray(Constants.HTTP_TIMETABLE_SS_TAG));
        if (timetableSS != null){
            program.setTimetableSS(timetableSS);
        }

        program.setPeriodValidity(object.getString(Constants.HTTP_PERIOD_VALIDITY_TAG));

        return program;
    }

    private List<String>  getStringArrayItemFromJson(JSONArray array) throws JSONException {
        if(array == null){
            return null;
        }
        List<String> strings = new ArrayList<>();

        for(int i=0;i<array.length();i++){
            strings.add(array.getString(i));

        }
        return  strings;
    }
}

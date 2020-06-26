package com.tvrtest.tverskoi2.adapter;

import android.content.Context;
import android.os.AsyncTask;

import com.tvrtest.tverskoi2.model.Employee;
import com.tvrtest.tverskoi2.model.Profession;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class DataLoader extends AsyncTask<Void, Void, Void> {

    private final String DATA_URL = "https://gitlab.65apps.com/65gb/static/raw/master/testTask.json";

    private ArrayList<Profession> specialties;
    private ArrayList<Employee> employees;
    private Context context;
    private JSONObject responseJSON;

    public DataLoader(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        DatabaseManager databaseManager = new DatabaseManager(context);
        loadEmployee(requestToServer(DATA_URL));
        loadProfession(requestToServer(DATA_URL));
        for (int i = 0; i < employees.size(); i++) {
            databaseManager.addEmployee(employees.get(i));
        }
        for (int i = 0; i < specialties.size(); i++) {
            databaseManager.addSpecialty(specialties.get(i));
        }
        return null;
    }

    private JSONObject requestToServer(String requestUrl) {
        StringBuffer response = new StringBuffer();
        String line;
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((line = reader.readLine()) != null) {
                response.append(line); //Создаем длинную строчку "Line"
            }
            connection.disconnect();
            reader.close();
            responseJSON = new JSONObject(response.toString());
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return responseJSON;
    }

    private void loadProfession(JSONObject jsonObject) {
        ArrayList<Profession> professionList = new ArrayList<>();
        Set<String> specialtyNamesSet = new HashSet<>();
        JSONArray dataJSON;
        //парсим Json
        try {
            dataJSON = jsonObject.getJSONArray("response");
            for (int i = 0; i < dataJSON.length(); i++) {
                JSONArray specialtiesJSON = dataJSON.getJSONObject(i).getJSONArray("specialty");
                for (int j = 0; j < specialtiesJSON.length(); j++) {
                    String specialtyName = specialtiesJSON.getJSONObject(j).getString("name");
                    if (specialtyNamesSet.add(specialtyName)) {
                        Profession profession = new Profession();
                        profession.setId(specialtiesJSON.getJSONObject(j).getInt("specialty_id"));
                        profession.setName(specialtyName);
                        professionList.add(profession);
                    }
                }
            }
        } catch (Exception exc){
            exc.printStackTrace();
        }
        this.specialties = professionList;
    }

    private void loadEmployee(JSONObject jsonObject) {
        ArrayList<Employee> employeeList = new ArrayList<>();
        JSONArray dataJSON;
        //парсим - достаем Employee
        try {
            dataJSON = jsonObject.getJSONArray("response");
            for (int i = 0; i < dataJSON.length(); i++) {
                JSONObject workerJSON = dataJSON.getJSONObject(i);
                JSONArray specialtiesJSON = workerJSON.getJSONArray("specialty");
                for (int j = 0; j < specialtiesJSON.length(); j++) {
                    Employee employee = new Employee();
                    Profession profession = new Profession();
                    profession.setName(specialtiesJSON.getJSONObject(j).getString("name"));
                    profession.setId(specialtiesJSON.getJSONObject(j).getInt("specialty_id"));
                    employee.setFirstName(workerJSON.getString("f_name"));
                    employee.setLastName(workerJSON.getString("l_name"));
                    employee.setBirthday(workerJSON.getString("birthday"));
                    employee.setProfession(profession);
                    employee.setAvatarUrl(workerJSON.getString("avatr_url"));
                    employeeList.add(employee);
                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        this.employees = employeeList;
    }
}

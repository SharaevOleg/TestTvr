package com.tvrtest.tverskoi2.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tvrtest.tverskoi2.model.Employee;
import com.tvrtest.tverskoi2.model.Profession;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "testapp65_database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_WORKERS = "WORKERS";
    private static final String TABLE_SPECIALTIES = "SPECIALTIES";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    private static final String COLUMN_LAST_NAME = "LAST_NAME";
    private static final String COLUMN_BIRTHDAY = "BIRTHDAY";
    private static final String COLUMN_AGE = "AGE";
    private static final String COLUMN_SPECIALTY_NAME = "SPECIALTY_NAME";
    private static final String COLUMN_AVATAR_URL = "AVATAR_URL";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_WORKERS + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FIRST_NAME + " TEXT," + COLUMN_LAST_NAME + " TEXT,"
                + COLUMN_BIRTHDAY + " NUMERIC," + COLUMN_AGE + " INTEGER,"
                + COLUMN_SPECIALTY_NAME + " TEXT," + COLUMN_AVATAR_URL + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_SPECIALTIES + "(" + COLUMN_ID
                + " INTEGER," + COLUMN_SPECIALTY_NAME + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPECIALTIES);
        onCreate(db);
    }

    public void addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_BIRTHDAY, COLUMN_SPECIALTY_NAME};
        String selection = COLUMN_FIRST_NAME + " = ? AND " + COLUMN_LAST_NAME + " = ? AND " + COLUMN_BIRTHDAY + " = ? AND " + COLUMN_SPECIALTY_NAME + " = ?";
        String[] selectionArgs = {employee.getFirstName(), employee.getLastName(), employee.getBirthday(), employee.getProfession().getName()};
        Cursor cursor = db.query(TABLE_WORKERS, columns, selection, selectionArgs, null, null, null);
        if (cursor.getCount() == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_FIRST_NAME, employee.getFirstName());
            contentValues.put(COLUMN_LAST_NAME, employee.getLastName());
            contentValues.put(COLUMN_BIRTHDAY, employee.getBirthday());
            contentValues.put(COLUMN_SPECIALTY_NAME, employee.getProfession().getName());
            contentValues.put(COLUMN_AGE, employee.getAge());
            contentValues.put(COLUMN_AVATAR_URL, employee.getAvatarUrl());
            db.insert(TABLE_WORKERS, null, contentValues);
        }
        cursor.close();
        db.close();
    }

    public ArrayList<Employee> getEmployeeBySpecialty(String specialtyName) {
        ArrayList<Employee> employees = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_BIRTHDAY, COLUMN_SPECIALTY_NAME, COLUMN_AGE, COLUMN_AVATAR_URL};
        String selection = COLUMN_SPECIALTY_NAME + " = ?";
        String[] selectionArgs = {specialtyName};
        String orderBy = COLUMN_FIRST_NAME;
        Cursor cursor = db.query(TABLE_WORKERS, columns, selection, selectionArgs, null, null, orderBy);
        if (cursor.moveToFirst()) {
            do {
                try {
                    employees.add(getWorker(cursor));
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return employees;
    }

    public Employee getWorker(Cursor cursor) throws Exception {
        Employee employee = new Employee();
        employee.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
        employee.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
        employee.setBirthday(cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDAY)));
        Profession profession = new Profession();
        profession.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        profession.setName(cursor.getString(cursor.getColumnIndex(COLUMN_SPECIALTY_NAME)));
        employee.setProfession(profession);
        employee.setAvatarUrl(cursor.getString(cursor.getColumnIndex(COLUMN_AVATAR_URL)));
        return employee;
    }

    //Добавляем специальность в базу данных
    public void addSpecialty(Profession profession) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_SPECIALTY_NAME};
        String selection = COLUMN_ID + " = ? AND " + COLUMN_SPECIALTY_NAME + " = ?";
        String[] selectionArgs = {String.valueOf(profession.getId()), profession.getName()};
        Cursor cursor = db.query(TABLE_SPECIALTIES, columns, selection, selectionArgs, null, null, null);
        if (cursor.getCount() == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, profession.getId());
            contentValues.put(COLUMN_SPECIALTY_NAME, profession.getName());
            db.insert(TABLE_SPECIALTIES, null, contentValues);
        }
        cursor.close();
        db.close();
    }

    public ArrayList<Profession> getAllSpecialties() {
        ArrayList<Profession> specialties = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_SPECIALTY_NAME};
        String orderBy = COLUMN_SPECIALTY_NAME;
        Cursor cursor = db.query(TABLE_SPECIALTIES, columns, null, null, null, null, orderBy);
        if (cursor.moveToFirst()) {
            do {
                try {
                    specialties.add(getSpecialty(cursor));
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return specialties;
    }

    public Profession getSpecialty(Cursor cursor) throws Exception {
        Profession profession = new Profession();
        profession.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        profession.setName(cursor.getString(cursor.getColumnIndex(COLUMN_SPECIALTY_NAME)));
        return profession;
    }
}

package com.example.contactsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseControl {

    SQLiteDatabase database;
    DatabaseHelper helper;

    public DatabaseControl(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public boolean insert(String name, String state, String platform) {
        ContentValues values = new ContentValues();
        values.put("name", name); // corresponds with line 24 string name
        values.put("state", state); // corresponds with line 24 string state
        values.put("platform", platform);
        return database.insert("contact", null, values) > 0;
    }

    public void delete(String n) {
        database.delete("contact", "name =\""+n+"\"", null);
    }

    public String getState(String name) {
        String query = "select state from contact where name =\""+name+"\""; // slash quotes are used for strings, not implemented with integers
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String state = cursor.getString(0);
        cursor.close();
        return state;
    }
    public String getState2(String state) {
        String query = "select name from contact where state =\""+state+"\""; // slash quotes are used for strings, not implemented with integers
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String name = cursor.getString(0);
        cursor.close();
        return name;
    }
    public String[] getAllNamesArray() {
        String query = "select name from contact";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        ArrayList<String> list = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            String name = cursor.getString(0);
            list.add(name);
            cursor.moveToNext();

        }
        cursor.close();
        String[] array = new String[list.size()];
        return list.toArray(array);
    }

    public String[] getAllArtistArray() {
        String query = "select state from contact";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        ArrayList<String> list = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            String state = cursor.getString(1);
            list.add(state);
            cursor.moveToNext();

        }
        cursor.close();
        String[] array = new String[list.size()];
        return list.toArray(array);
    }

    public String[] getAllPlatformArray() {
        String query = "select platform from contact";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        ArrayList<String> list = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            String platform = cursor.getString(2);
            list.add(platform);
            cursor.moveToNext();

        }
        cursor.close();
        String[] array = new String[list.size()];
        return list.toArray(array);
    }

    public ArrayList<String> getAllNames() {
        String query = "select everything from contact";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        ArrayList<String> list = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            String name = cursor.getString(0);
            list.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}

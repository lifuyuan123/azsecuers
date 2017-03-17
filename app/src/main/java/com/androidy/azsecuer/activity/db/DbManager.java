package com.androidy.azsecuer.activity.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.androidy.azsecuer.activity.entity.TellNumber;
import com.androidy.azsecuer.activity.entity.TellType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/12.
 */
public class DbManager {
    public static File fileDB;

    public static void createFile(Context context) {
        File fileDir = new File("data/data/" + context.getPackageName() + "/db");
        boolean mkSure = fileDir.mkdirs();//创建路径
        fileDB = new File(fileDir, "commonnum.db");
    }

    public static boolean dbFileIsExists() {
        if (fileDB.length() == 0 || !fileDB.exists()) {
            return true;
        }
        return false;
    }

    //查询信息
    public static List<TellType> rendTellType() {
        List<TellType> tellTypes = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(fileDB, null);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from classlist", null);
        if (cursor.moveToFirst()) {
            TellType tellType = null;
            do {
                tellType = new TellType(cursor.getString(cursor.getColumnIndex("name"))
                        , cursor.getInt(cursor.getColumnIndex("idx")));
                tellTypes.add(tellType);
                Log.i("DbManager-readTellType", tellType.toString());
            } while (cursor.moveToNext());
        } else {
            Log.i("DbManager-readTellType", "没有找到数据");
        }
        return tellTypes;
    }

    public static List<TellNumber> readTellNumber(Integer idx) {
        List<TellNumber> tellNumbers = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(fileDB, null);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from table" + idx, null);
        if (cursor.moveToFirst()) {
            TellNumber tellNumber = null;
            do {
                tellNumber = new TellNumber(cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("number")));
                tellNumbers.add(tellNumber);
                Log.i("DbManager-tellNumber", tellNumber.toString());
            } while (cursor.moveToNext());
        } else {
            Log.i("DbManager-tellNumber", "没有找到数据");
        }
        return tellNumbers;
    }
}

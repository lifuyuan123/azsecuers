package com.androidy.azsecuer.activity.db.dbclean;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.entity.clean.Clean;
import com.androidy.azsecuer.activity.util.FileUtil;
import com.androidy.azsecuer.activity.util.PublicUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/17.
 */
public class DBmanager {
    public static File fileDB;

    public static void createFile(Context context) {
        File filedir = new File("data/data/" + context.getPackageName() + "/db");
        boolean mkSure = filedir.mkdirs();//创建文件目录
        fileDB = new File(filedir, "clearpath.db");
    }

    public static boolean dbFileIsExists() {
        if (fileDB.length() == 0 || !fileDB.exists()) {
            return true;
        }
        return false;
    }

    //查询信息
    public static List<Clean> rendClean(Context context) {
        List<Clean> cleans = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(fileDB, null);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from softdetail", null);
        if (cursor.moveToFirst()) {
            Clean clean = null;
            do {
                String name = cursor.getString(cursor.getColumnIndex("softChinesename"));
                String englishname = cursor.getString(cursor.getColumnIndex("softEnglishname"));
                String apkname = cursor.getString(cursor.getColumnIndex("apkname"));
                String filepath = Environment.getExternalStorageDirectory() + cursor.getString(cursor.getColumnIndex("filepath"));
                String stype = cursor.getString(cursor.getColumnIndex("stype"));
                File file = new File(filepath);
                if(file.exists()){
                long fileSizes = FileUtil.getFilesize(file);
                String fileSize = PublicUtils.formatSize(fileSizes);
                Drawable drawable = null;
                try {
                    drawable = context.getPackageManager().getApplicationIcon(apkname);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    drawable = context.getResources().getDrawable(R.drawable.ic_launcher);
                }
                clean = new Clean(name, englishname, apkname, filepath, stype, false, drawable, fileSize);
                cleans.add(clean);
                Log.i("DbManager-readTellType", clean.toString());
                }
            } while (cursor.moveToNext());
        } else {
            Log.i("DbManager-readTellType", "没有找到数据");
        }
        return cleans;
    }


}

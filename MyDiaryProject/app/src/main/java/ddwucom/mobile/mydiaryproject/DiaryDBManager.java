package ddwucom.mobile.mydiaryproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DiaryDBManager {

    DiaryDBHelper diaryDBHelper = null;
    android.database.Cursor cursor = null;


    public DiaryDBManager(Context context) {
        diaryDBHelper = new DiaryDBHelper(context);
    }

    public ArrayList<Diary> getAllDiary() {
        ArrayList DiaryList = new ArrayList();
        SQLiteDatabase db = diaryDBHelper.getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT * FROM " + DiaryDBHelper.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex(DiaryDBHelper.COL_ID));
            String date = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_DATE));
            String title = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_TITLE));
            String content = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_CONTENT));
            String picture = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_PICTURE));
            String place = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_PLACE));
            String weather = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_WEATHER));
            String feeling = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_FEELING));
            String category = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_CATEGORY));
            String rating = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_RATING));

            DiaryList.add(new Diary(id, date, title, content, picture, place, weather, feeling, category, rating));
        }
        cursor.close();
        diaryDBHelper.close();
        return DiaryList;
    }

    public Diary getDiary(long id) {
        SQLiteDatabase db = diaryDBHelper.getReadableDatabase();
        Diary diary = null;

        String selection = "_id=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        android.database.Cursor cursor = db.query(DiaryDBHelper.TABLE_NAME, null, selection, selectionArgs,
                null, null, null, null);
        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_DATE));
            String title = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_TITLE));
            String content = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_CONTENT));
            String picture = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_PICTURE));
            String place = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_PLACE));
            String weather = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_WEATHER));
            String feeling = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_FEELING));
            String category = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_CATEGORY));
            String rating = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_RATING));
            String _id = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_ID));
            diary = new Diary(Long.parseLong(_id),date, title, content, picture, place, weather, feeling, category, rating);
        }

        cursor.close();
        diaryDBHelper.close();

        return diary;


    }


    public boolean addNewDiary(Diary newDiary) {
        SQLiteDatabase db = diaryDBHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(DiaryDBHelper.COL_DATE, newDiary.getDate());
        value.put(DiaryDBHelper.COL_TITLE, newDiary.getTitle());
        value.put(DiaryDBHelper.COL_CONTENT, newDiary.getContent());
        value.put(DiaryDBHelper.COL_PICTURE, newDiary.getPicture());
        value.put(DiaryDBHelper.COL_PLACE, newDiary.getPlace());
        value.put(DiaryDBHelper.COL_WEATHER, newDiary.getWeather());
        value.put(DiaryDBHelper.COL_FEELING, newDiary.getFeeling());
        value.put(DiaryDBHelper.COL_CATEGORY, newDiary.getCategory());
        value.put(DiaryDBHelper.COL_RATING, newDiary.getRating());


        long count = db.insert(DiaryDBHelper.TABLE_NAME, null, value);
        diaryDBHelper.close();
        if (count > 0) return true;

        return false;
    }


    public boolean modifyDiary(Diary diary) {

        SQLiteDatabase sqLiteDatabase = diaryDBHelper.getWritableDatabase();

        ContentValues row = new ContentValues();
        row.put(DiaryDBHelper.COL_DATE, diary.getDate());
        row.put(DiaryDBHelper.COL_TITLE, diary.getTitle());
        row.put(DiaryDBHelper.COL_CONTENT, diary.getContent());
        row.put(DiaryDBHelper.COL_PICTURE, diary.getPicture());
        row.put(DiaryDBHelper.COL_PLACE, diary.getPlace());
        row.put(DiaryDBHelper.COL_WEATHER, diary.getWeather());
        row.put(DiaryDBHelper.COL_FEELING, diary.getFeeling());
        row.put(DiaryDBHelper.COL_CATEGORY, diary.getCategory());
        row.put(DiaryDBHelper.COL_RATING, diary.getRating());

        String whereClause = DiaryDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(diary.get_id())};

        int result = sqLiteDatabase.update(DiaryDBHelper.TABLE_NAME, row, whereClause, whereArgs);
        diaryDBHelper.close();
        if (result > 0) return true;
        return false;
    }



    public boolean removeDiary(long id) {
        SQLiteDatabase sqLiteDatabase = diaryDBHelper.getWritableDatabase();
        String whereclause = DiaryDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        int result = sqLiteDatabase.delete(DiaryDBHelper.TABLE_NAME, whereclause, whereArgs);
        diaryDBHelper.close();
        if (result > 0)
            return true;

        return false;
    }


    public int[] getFeelingSt() {
        int[] diaryArray = new int[6];
        SQLiteDatabase db = diaryDBHelper.getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT * FROM " + DiaryDBHelper.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            String feeling = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_FEELING));
            if (feeling.equals("happy")) {
                diaryArray[0] += 1;
                diaryArray[5] += 1;
            } else if (feeling.equals("sad")) {
                diaryArray[1] += 1;
                diaryArray[5] += 1;
            } else if (feeling.equals("cry")) {
                diaryArray[2] += 1;
                diaryArray[5] += 1;
            } else if (feeling.equals("love")) {
                diaryArray[3] += 1;
                diaryArray[5] += 1;
            } else if (feeling.equals("angry")) {
                diaryArray[4] += 1;
                diaryArray[5] += 1;
            }

        }
        cursor.close();
        diaryDBHelper.close();
        return diaryArray;


    }

    public int[] getCategorylistSt() {
        int[] diaryArray = new int[6];
        SQLiteDatabase db = diaryDBHelper.getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT * FROM " + DiaryDBHelper.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            String categroy = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_CATEGORY));
            if (categroy.equals("일상")) {
                diaryArray[0] += 1;

            } else if (categroy.equals("연애")) {
                diaryArray[1] += 1;

            } else if (categroy.equals("공부")) {
                diaryArray[2] += 1;

            } else if (categroy.equals("교훈")) {
                diaryArray[3] += 1;

            } else if (categroy.equals("우정")) {
                diaryArray[4] += 1;

            }
            else if (categroy.equals("여행")) {
                diaryArray[5] += 1;

            }

        }
        cursor.close();
        diaryDBHelper.close();
        return diaryArray;
    }

    public String getTimeRatingSt(){
        SQLiteDatabase db = diaryDBHelper.getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT * FROM " + DiaryDBHelper.TABLE_NAME, null);
        int i = 0;
        double total = 0;
        while (cursor.moveToNext()) {
            String rating= cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_RATING));
            total += Double.valueOf(rating);
            i += 1;
        }
        total = total /i;
        return String.valueOf(total);

    }
    public ArrayList<Diary> searchByTitle(String searchtitle) {
        SQLiteDatabase db = diaryDBHelper.getReadableDatabase();
        ArrayList<Diary> DiaryList = new ArrayList();
        String selection = "title=?";
        String[] selectionArgs = new String[]{searchtitle};
        android.database.Cursor cursor = db.query(DiaryDBHelper.TABLE_NAME, null, selection, selectionArgs,
                null, null, null, null);
        while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_TITLE));
                long id = cursor.getInt(cursor.getColumnIndex(DiaryDBHelper.COL_ID));
                String date = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_DATE));
                String content = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_CONTENT));
                String picture = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_PICTURE));
                String place = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_PLACE));
                String weather = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_WEATHER));
                String feeling = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_FEELING));
                String category = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_CATEGORY));
                String rating = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_RATING));
                DiaryList.add(new Diary(id,date, title, content, picture, place, weather, feeling, category, rating));

        }
        cursor.close();
        diaryDBHelper.close();

        return DiaryList;

    }
    public ArrayList<Diary> searchByDate(String searchdate) {
        SQLiteDatabase db = diaryDBHelper.getReadableDatabase();
        ArrayList<Diary> DiaryList = new ArrayList();
        String selection = "date=?";
        String[] selectionArgs = new String[]{searchdate};
        android.database.Cursor cursor = db.query(DiaryDBHelper.TABLE_NAME, null, selection, selectionArgs,
                null, null, null, null);
        while (cursor.moveToNext()) {
                String date = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_DATE));
                long id = cursor.getInt(cursor.getColumnIndex(DiaryDBHelper.COL_ID));
                String title = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_TITLE));
                String content = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_CONTENT));
                String picture = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_PICTURE));
                String place = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_PLACE));
                String weather = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_WEATHER));
                String feeling = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_FEELING));
                String category = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_CATEGORY));
                String rating = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_RATING));
                DiaryList.add(new Diary(id,date, title, content, picture, place, weather, feeling, category, rating));

        }
        cursor.close();
        diaryDBHelper.close();

        return DiaryList;

    }
    public ArrayList<Diary> searchByPlace(String searchplace) {
        SQLiteDatabase db = diaryDBHelper.getReadableDatabase();
        ArrayList<Diary> DiaryList = new ArrayList();
        String selection = "place=?";
        String[] selectionArgs = new String[]{searchplace};
        android.database.Cursor cursor = db.query(DiaryDBHelper.TABLE_NAME, null, selection, selectionArgs,
                null, null, null, null);
        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_DATE));
            long id = cursor.getInt(cursor.getColumnIndex(DiaryDBHelper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_TITLE));
            String content = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_CONTENT));
            String picture = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_PICTURE));
            String place = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_PLACE));
            String weather = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_WEATHER));
            String feeling = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_FEELING));
            String category = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_CATEGORY));
            String rating = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_RATING));
            DiaryList.add(new Diary(id,date, title, content, picture, place, weather, feeling, category, rating));

        }
        cursor.close();
        diaryDBHelper.close();

        return DiaryList;

    }

    public ArrayList<Diary> searchAll(String search) {
        SQLiteDatabase db = diaryDBHelper.getReadableDatabase();
        ArrayList DiaryList = new ArrayList();
        String selection = "date=?OR title=?OR place=?";
        String[] selectionArgs = new String[]{search,search,search};
        android.database.Cursor cursor = db.query(DiaryDBHelper.TABLE_NAME, null, selection, selectionArgs,
                null, null, null, null);
        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_DATE));
            String title = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_TITLE));
            long id = cursor.getInt(cursor.getColumnIndex(DiaryDBHelper.COL_ID));
            String content = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_CONTENT));
            String picture = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_PICTURE));
            String place = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_PLACE));
            String weather = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_WEATHER));
            String feeling = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_FEELING));
            String category = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_CATEGORY));
            String rating = cursor.getString(cursor.getColumnIndex(DiaryDBHelper.COL_RATING));
            DiaryList.add(new Diary(id, date, title, content, picture, place, weather, feeling, category, rating));

        }

        cursor.close();
        diaryDBHelper.close();

        return DiaryList;

    }





}

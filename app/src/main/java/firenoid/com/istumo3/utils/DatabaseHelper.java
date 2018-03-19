package firenoid.com.istumo3.utils;

/**
 * Created by OpiFrame on 07/12/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static firenoid.com.istumo3.LoginActivity.currentEmail;
import static firenoid.com.istumo3.LoginActivity.currentUserId;

/**
 * Created by csimon on 12/11/13.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

        // Database Version
        private static final int DATABASE_VERSION = 1;

        // Database Name
        private static final String DATABASE_NAME = "User.db";

        // User table name
        static final String TABLE_USER = "user";

        // User Table Columns names
        private static final String COLUMN_USER_ID = "user_id";
private static final String COLUMN_USER_NAME = "user_name";
private static final String COLUMN_USER_EMAIL = "user_email";
private static final String COLUMN_USER_PASSWORD = "user_password";
private static final String COLUMN_USER_VALUE = "user_value";
private static final String COLUMN_USER_DATE = "user_date";
private static final String COLUMN_USER_TIME = "user_time";
    private static final String PERSON_COLUMN_HEI = "user_height";
    private static final String PERSON_COLUMN_WEI = "user_weight";


    public static final String PERSON_COLUMN_TYPE = "type";
    public static final String PERSON_COLUMN_GENDER = "gender";
    public static final String PERSON_COLUMN_AGE = "age";
    public static final String PERSON_COLUMN_Picture = "picture";






        private AppDatabaseHelper appDB;
        private SQLiteDatabase db;


        // create table sql quer
        private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT,"
                + COLUMN_USER_PASSWORD + " TEXT,"
        //        + COLUMN_USER_VALUE + " TEXT,"
         //       + COLUMN_USER_DATE + " TEXT,"
        //        + COLUMN_USER_TIME + " TEXT,"
                + PERSON_COLUMN_TYPE + " TEXT,"
            + PERSON_COLUMN_GENDER + " TEXT,"
            + PERSON_COLUMN_AGE + " TEXT,"
                + PERSON_COLUMN_HEI + " TEXT,"
                + PERSON_COLUMN_WEI + " TEXT,"
            + PERSON_COLUMN_Picture + " TEXT"

                + ")";


        // drop table sql query
        private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

        /**
  * Constructor
  *
  * @param context
  */
        public DatabaseHelper(Context context) {
super(context, DATABASE_NAME, null, DATABASE_VERSION);
                appDB = new AppDatabaseHelper(context);

}

        @Override
public void onCreate(SQLiteDatabase db) {
db.execSQL(CREATE_USER_TABLE);
}

        
        @Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//Drop User Table if exist
db.execSQL(DROP_USER_TABLE);

// Create tables again
onCreate(db);

}

        /**
  * This method is to create user record
  *
  * @param user
  */
        public void addUser(User user) {
SQLiteDatabase db = this.getWritableDatabase();

ContentValues values = new ContentValues();
values.put(COLUMN_USER_NAME, user.getName());
values.put(COLUMN_USER_EMAIL, user.getEmail());
values.put(COLUMN_USER_PASSWORD, user.getPassword());
              //  values.put(COLUMN_USER_VALUE, user.getvalue());
              //  values.put(COLUMN_USER_DATE, user.getdate());
              //  values.put(COLUMN_USER_TIME, user.gettime());
                values.put(PERSON_COLUMN_GENDER, user.getgender());
                values.put(PERSON_COLUMN_AGE, user.getage());
                 values.put(PERSON_COLUMN_HEI, user.gethei());
                    values.put(PERSON_COLUMN_WEI, user.getwei());
            values.put(PERSON_COLUMN_TYPE, user.gettype());

                values.put(PERSON_COLUMN_Picture, user.getpicture());




// Inserting Row
db.insert(TABLE_USER, null, values);

}

        /**
  * This method is to fetch all user and return the list of user records
  *
  * @return list
  */
        public List<User> getAllUser() {
// array of columns to fetch
String[] columns = {
COLUMN_USER_ID,
COLUMN_USER_EMAIL,
COLUMN_USER_NAME,
COLUMN_USER_PASSWORD,
//COLUMN_USER_VALUE,
//COLUMN_USER_DATE ,
//COLUMN_USER_TIME ,
PERSON_COLUMN_TYPE ,
PERSON_COLUMN_GENDER,
        PERSON_COLUMN_HEI,
        PERSON_COLUMN_WEI,
PERSON_COLUMN_AGE,
PERSON_COLUMN_Picture

};
// sorting orders
String sortOrder =
COLUMN_USER_NAME + " ASC";
List<User> userList = new ArrayList<User>();

SQLiteDatabase db = this.getReadableDatabase();

            String selection = COLUMN_USER_ID + " = ?";
                String[] selectionArgs = { currentUserId };
// query the user table
/**
          * Here query function is used to fetch records from user table this function works like we use sql query.
          * SQL query equivalent to this query function is
          * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
          */
Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,//columns to return
        selection,
        selectionArgs,
null, //group the rows
null, //filter by row groups
sortOrder); //The sort order




// Traversing through all rows and adding to list


if (cursor.moveToFirst()) {
do {
User user = new User();
//user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
//user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
//user.setvalue(cursor.getString(cursor.getColumnIndex(COLUMN_USER_VALUE)));
//user.setdate(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DATE)));
//user.settime(cursor.getString(cursor.getColumnIndex(COLUMN_USER_TIME)));
user.settype(cursor.getString(cursor.getColumnIndex(PERSON_COLUMN_TYPE)));
user.setgender(cursor.getString(cursor.getColumnIndex(PERSON_COLUMN_GENDER)));
user.sethei(cursor.getString(cursor.getColumnIndex(PERSON_COLUMN_HEI)));
user.setwei(cursor.getString(cursor.getColumnIndex(PERSON_COLUMN_WEI)));

user.setage(cursor.getString(cursor.getColumnIndex(PERSON_COLUMN_AGE)));
user.setpicture(cursor.getString(cursor.getColumnIndex(PERSON_COLUMN_Picture)));



// Adding user record to list
userList.add(user);
} while (cursor.moveToNext());
}



// return user list
return userList;
}

    String Userfinal="";

    public String getData(String email){
        SQLiteDatabase db = this.getReadableDatabase();


        db = this.getWritableDatabase();
        String[] columns = {
                COLUMN_USER_NAME
        };
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        Log.d(TAG, "getUserName: "+ String.valueOf(DatabaseUtils.dumpCursorToString(cursor)));
        int cursorCount = cursor.getCount();
        Log.d(TAG, "getUserName Count: "+ String.valueOf(cursorCount));
        cursor.moveToFirst();
        for(int i=0;i<cursorCount;i++) {
            Userfinal+=cursor.getString(i)+",";
        }




        if (cursorCount > 0){

           return Userfinal;

        }



        return Userfinal;

                }
















        /**
  * This method to update user record
  *

         */

        public void updateUser(User user) {
SQLiteDatabase db = this.getWritableDatabase();

ContentValues values = new ContentValues();
            values.put(COLUMN_USER_NAME, user.getName());
            values.put(COLUMN_USER_EMAIL, user.getEmail());
            values.put(COLUMN_USER_PASSWORD, user.getPassword());
       //     values.put(COLUMN_USER_VALUE, user.getvalue());
         //   values.put(COLUMN_USER_DATE, user.getdate());
       //     values.put(COLUMN_USER_TIME, user.gettime());
            values.put(PERSON_COLUMN_GENDER, user.getgender());
            values.put(PERSON_COLUMN_HEI, user.gethei());
            values.put(PERSON_COLUMN_WEI, user.getwei());
            values.put(PERSON_COLUMN_AGE, user.getage());
            values.put(PERSON_COLUMN_TYPE, user.gettype());
            values.put(PERSON_COLUMN_Picture, user.getpicture());



// updating row
db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(currentUserId)});

}


    public void updateoneUser(User user,String what) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

     //   values.put(what, user.getName());


        switch( what) {

            case "COLUMN_USER_NAME":
                values.put(COLUMN_USER_NAME, user.getName());
                break;
            case "COLUMN_USER_EMAIL":
                values.put(COLUMN_USER_EMAIL, user.getEmail());
                break;
            case "PERSON_COLUMN_AGE":
                values.put(PERSON_COLUMN_AGE, user.getage());
                break;
            case "PERSON_COLUMN_GENDER":
                values.put(PERSON_COLUMN_GENDER, user.getgender());
                break;
            case "PERSON_COLUMN_HEI":
                values.put(PERSON_COLUMN_HEI, user.gethei());
                break;
            case "PERSON_COLUMN_WEI":
                values.put(PERSON_COLUMN_WEI, user.getwei());
                break;

            case "PERSON_COLUMN_TYPE":
                values.put(PERSON_COLUMN_TYPE, user.gettype());
                break;
            case "COLUMN_USER_PASSWORD":
                values.put(COLUMN_USER_PASSWORD, user.getPassword());
                break;

            case "PERSON_COLUMN_Picture":
                values.put(PERSON_COLUMN_Picture, user.getpicture());
                break;

        }




// updating row
        db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?",
                new String[]{String.valueOf(currentEmail)});

    }



        /**
  * This method is to delete user record
  *
  * @param user
  */
        public void deleteUser(User user) {
SQLiteDatabase db = this.getWritableDatabase();
// delete user record by id
db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});

}

        /**
  * This method to check user exist or not
  *
  * @param email
  * @return true/false
  */
        public boolean checkUser(String email) {

                // array of columns to fetch
                String[] columns = {
                COLUMN_USER_ID
                };
                SQLiteDatabase db = this.getReadableDatabase();

                // selection criteria
                String selection = COLUMN_USER_EMAIL + " = ?";

                // selection argument
                String[] selectionArgs = {email};

                // query user table with condition
                /**
                          * Here query function is used to fetch records from user table this function works like we use sql query.
                          * SQL query equivalent to this query function is
                          * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
                          */
                Cursor cursor = db.query(TABLE_USER, //Table to query
                                columns,//columns to return
                                selection,//columns for the WHERE clause
                                selectionArgs,//The values for the WHERE clause
                                null, //group the rows
                null,//filter by row groups
                null);//The sort order
                int cursorCount = cursor.getCount();
                cursor.close();


                if (cursorCount > 0) {
                return true;
                }

                return false;
                }

        /**
  * This method to check user exist or not
  *
  * @param email
  * @param password
  * @return true/false
  */
        public boolean checkUser(String email, String password) {

                        // array of columns to fetch
                        String[] columns = {
                        COLUMN_USER_ID
                        };
                        SQLiteDatabase db = this.getReadableDatabase();
                        // selection criteria
                        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

                        // selection arguments
                        String[] selectionArgs = {email, password};

                        // query user table with conditions
                        /**
                                  * Here query function is used to fetch records from user table this function works like we use sql query.
                                  * SQL query equivalent to this query function is
                                  * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
                                  */
                        Cursor cursor = db.query(TABLE_USER, //Table to query
                                        columns,//columns to return
                                        selection,//columns for the WHERE clause
                                        selectionArgs,//The values for the WHERE clause
                                        null, //group the rows
                        null, //filter by row groups
                        null);//The sort order

                        int cursorCount = cursor.getCount();

                        cursor.close();

                        if (cursorCount > 0) {
                        return true;
                        }

                        return false;
                        }



        public long getUserId (String email){
                long userId = 0;
                SQLiteDatabase db = this.getWritableDatabase();
                String[] columns = {
                        COLUMN_USER_ID
                };
                String selection = COLUMN_USER_EMAIL + " = ?";
                String[] selectionArgs = { email };

                Cursor cursor = db.query(TABLE_USER,
                        columns,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
                int cursorCount = cursor.getCount();
                cursor.moveToFirst();
                userId = cursor.getLong(0);
                cursor.close();


                if (cursorCount > 0){
                        return userId;
                }
                return userId;
        }



     public String getUserName (String email){
                String userName = "";
                SQLiteDatabase db = this.getWritableDatabase();
                String[] columns = {
                        COLUMN_USER_NAME
                };
                String selection = COLUMN_USER_EMAIL + " = ?";
                String[] selectionArgs = { email };

                Cursor cursor = db.query(TABLE_USER,
                        columns,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
                Log.d(TAG, "getUserName: "+ String.valueOf(DatabaseUtils.dumpCursorToString(cursor)));
                int cursorCount = cursor.getCount();
                Log.d(TAG, "getUserName Count: "+ String.valueOf(cursorCount));
                cursor.moveToFirst();
                userName = cursor.getString(0);
                cursor.close();
                db.close();

                if (cursorCount > 0){

                        return String.valueOf(userName);

                }
                return userName;

        }


    public String getUserPicture (String email){
        String userName = "";
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {
                PERSON_COLUMN_Picture
        };
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
//        Log.d(TAG, "getUserName: "+String.valueOf(DatabaseUtils.dumpCursorToString(cursor)));
        int cursorCount = cursor.getCount();
        Log.d(TAG, "getUserName Count: "+ String.valueOf(cursorCount));
        cursor.moveToFirst();
        userName = cursor.getString(0);



        if (cursorCount > 0){

            return String.valueOf(userName);

        }
        return userName;

    }


    public String getColumValue (String email, String colum){
        String userName = "";
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {
                colum
        };
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
//        Log.d(TAG, "getUserName: "+String.valueOf(DatabaseUtils.dumpCursorToString(cursor)));
        int cursorCount = cursor.getCount();
        Log.d(TAG, "getUserName Count: "+ String.valueOf(cursorCount));
        cursor.moveToFirst();
        userName = cursor.getString(0);



        if (cursorCount > 0){

            return String.valueOf(userName);

        }
        return userName;

    }









        public static class AppDatabaseHelper extends SQLiteOpenHelper {
                AppDatabaseHelper(Context context) {
                        super(context, DATABASE_NAME, null, DATABASE_VERSION);
                }




                @Override
                public void onCreate(SQLiteDatabase _db) {
                        _db.execSQL(CREATE_USER_TABLE);
                }

                @Override
                public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
                        Log.w(TAG, "Upgrading application's database from version " + oldVersion
                                + " to " + newVersion + ", which will destroy all old data!");
                        // Destroy old database:
                        _db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
                        // Recreate new database:
                        onCreate(_db);
                }
        }





}
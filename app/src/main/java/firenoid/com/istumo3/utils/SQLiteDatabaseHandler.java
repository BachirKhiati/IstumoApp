package firenoid.com.istumo3.utils;

/**
 * Created by OpiFrame on 07/12/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;


public class SQLiteDatabaseHandler extends SQLiteOpenHelper {



    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "UsersDB";

    private static final String TABLE_NAME = "user";

    private static final String KEY_ID = "id";

    private static final String KEY_NAME = "name";

    private static final String KEY_POSITION = "position";

    private static final String KEY_HEIGHT = "height";

    private static final String[] COLUMNS = { KEY_ID, KEY_NAME, KEY_POSITION,

            KEY_HEIGHT };



    public SQLiteDatabaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }



    @Override

    public void onCreate(SQLiteDatabase db) {

        String CREATION_TABLE = "CREATE TABLE Users ( "

                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, "

                + "position TEXT, " + "height INTEGER )";



        db.execSQL(CREATION_TABLE);

    }



    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // you can implement here migration process

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        this.onCreate(db);

    }



    public void deleteOne(User User) {

        // Get reference to writable DB

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(User.getId()) });

        db.close();

    }



    public User getUser(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, // a. table

                COLUMNS, // b. column names

                " id = ?", // c. selections

                new String[] { String.valueOf(id) }, // d. selections args

                null, // e. group by

                null, // f. having

                null, // g. order by

                null); // h. limit



        if (cursor != null)

            cursor.moveToFirst();



        User User = new User();

        User.setId(Integer.parseInt(cursor.getString(0)));

        User.setName(cursor.getString(1));





        return User;

    }



    public List<User> allUsers() {



        List<User> Users = new LinkedList<User>();

        String query = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        User User = null;



        if (cursor.moveToFirst()) {

            do {

                User = new User();

                User.setId(Integer.parseInt(cursor.getString(0)));

                User.setName(cursor.getString(1));



                Users.add(User);

            } while (cursor.moveToNext());

        }



        return Users;

    }



    public void addUser(User User) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, User.getName());



        // insert

        db.insert(TABLE_NAME,null, values);

        db.close();

    }



    public int updateUser(User User) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, User.getName());




        int i = db.update(TABLE_NAME, // table

                values, // column/value

                "id = ?", // selections

                new String[] { String.valueOf(User.getId()) });



        db.close();



        return i;

    }



}
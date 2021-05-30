package android.example.mobilecoursework2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.example.mobilecoursework2.database.MovieConstants.PRIMARY_KEY;
import static android.example.mobilecoursework2.database.MovieConstants.TABLE_NAME;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_NAME;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_DIRECTOR;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_ACTORS;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_YEAR;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_RATING;
import static android.example.mobilecoursework2.database.MovieConstants.MOVIE_REVIEW;
import static android.example.mobilecoursework2.database.MovieConstants.FAVOURITE;

public class MovieEventsData extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;


    public MovieEventsData(Context applicationContext) {
        super(applicationContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating table.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MOVIE_NAME + " TEXT NOT NULL, "
                + MOVIE_YEAR + " INTEGER NOT NULL, "
                + MOVIE_DIRECTOR + " TEXT NOT NULL, "
                + MOVIE_ACTORS + " TEXT NOT NULL, "
                + MOVIE_RATING + " INTEGER NOT NULL, "
                + MOVIE_REVIEW + " TEXT NOT NULL, "
                + FAVOURITE + " TEXT);");
    }

    // Updating table.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

// cd C:\Users\krish\AppData\Local\Android\Sdk\platform-tools
// adb -s emulator-5556 shell
// cd data/data/android.example.mobilecoursework2/databases/
// sqlite3 movie.db
// SELECT * FROM movies;
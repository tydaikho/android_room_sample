package systems.mp.roomwordsample;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


// Room is a wrapper around and implements a SQLite database.
// Listing the entities will create tables in the database.
@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {

    private static volatile WordRoomDatabase INSTANCE;

    // must create an abstract "getter" for each @Dao
    public abstract WordDao wordDao();

    // to enforce that there's only 1 db instance for the whole app
    static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(
                                context.getApplicationContext(),
                                WordRoomDatabase.class,
                                "word_database"
                        ).addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    // delete all content and repopulate the database whenever the app is started
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                // creates and executes an AsyncTask to add content to the database.
                /*@Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }*/
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Word word = new Word("Hello");
            mDao.insert(word);
            word = new Word("World");
            mDao.insert(word);
            return null;
        }
    }
}

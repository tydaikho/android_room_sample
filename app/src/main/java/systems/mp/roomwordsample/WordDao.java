package systems.mp.roomwordsample;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

// The database access object associates SQL queries with method calls
@Dao
public interface WordDao {

    // Annotation abstracts SQL from me
    @Insert
    void insert(Word word);

    // There is no annotation to delete all so the query must be provided
    @Query("DELETE FROM word_table")
    void deleteAll();

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWords();
}

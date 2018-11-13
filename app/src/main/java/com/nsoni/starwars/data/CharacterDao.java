package com.nsoni.starwars.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CharacterDao {
    @Query("SELECT * FROM " + Character.TABLE_NAME + " WHERE name LIKE '%' || :name || '%'")
    List<Character> getCharacterByName(String name);

    @Query("SELECT * FROM " + Character.TABLE_NAME + " ORDER BY created")
    LiveData<List<Character>> getCharacterList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacter(Character task);

    @Query("DELETE from " + Character.TABLE_NAME)
    void deleteCharacters();

    @Query("DELETE from " + Character.TABLE_NAME + " WHERE name=:name")
    void deleteCharacter(String name);
}

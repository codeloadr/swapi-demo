package com.nsoni.starwars.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {Character.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class CharacterDatabase extends RoomDatabase {

    private static volatile CharacterDatabase INSTANCE;

    public abstract CharacterDao characterDao();

    public static CharacterDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CharacterDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CharacterDatabase.class, "Characters.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

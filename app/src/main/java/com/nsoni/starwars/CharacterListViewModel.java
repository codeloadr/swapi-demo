package com.nsoni.starwars;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nsoni.starwars.data.Character;
import com.nsoni.starwars.data.CharacterDao;
import com.nsoni.starwars.data.CharacterDatabase;

import java.util.List;

public class CharacterListViewModel extends AndroidViewModel {

    private LiveData<List<Character>> mCharacterList;
    private CharacterDao mCharacterDao;

    public CharacterListViewModel(@NonNull Application application) {
        super(application);
        mCharacterDao = CharacterDatabase.getInstance(application).characterDao();
        mCharacterList = mCharacterDao.getCharacterList();
    }

    public LiveData<List<Character>> getCharacterList() {
        return mCharacterList;
    }

    public List<Character> getCharacterByName(String name) {
        return mCharacterDao.getCharacterByName(name);
    }

    public void insertCharacterList(List<Character> characters) {
        for (Character character : characters) {
            mCharacterDao.insertCharacter(character);
        }
    }
}

package com.nsoni.starwars;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.nsoni.starwars.model.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An activity representing a list of People. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link CharacterDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class CharacterListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    boolean mTwoPane;
    List<com.nsoni.starwars.model.Character> listOfCharacters;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        recyclerView = findViewById(R.id.character_list);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);

        if (findViewById(R.id.character_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search People");
        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Show soft keyboard for the user to enter the value.
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String starWarsCharacterSearchWord) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String starWarsCharacterSearchWord) {
                updateCharacterList(starWarsCharacterSearchWord);
                return false;
            }
        });
        searchView.setIconified(false);

        return true;
    }

    private void updateCharacterList(String starWarsCharacterSearchWord) {
        StarWarsClient client = ServiceGenerator
                .getInstance(CharacterListActivity.this)
                .createService(StarWarsClient.class);

        Call<Result> resultCall = client.lookupCharacterNames(starWarsCharacterSearchWord);

        resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body() != null && response.body().getResults() != null) {
                    listOfCharacters.clear();
                    listOfCharacters.addAll(response.body().getResults());
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable throwable) {

            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        if (listOfCharacters == null) {
            listOfCharacters = new ArrayList<>();
        }
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, listOfCharacters));
    }

}

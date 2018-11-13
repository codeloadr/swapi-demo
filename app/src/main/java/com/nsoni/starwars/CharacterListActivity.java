package com.nsoni.starwars;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.nsoni.starwars.data.Character;
import com.nsoni.starwars.data.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
public class CharacterListActivity extends BaseActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    boolean mTwoPane;
    List<Character> mCharacterList;
    RecyclerView mRecyclerView;
    private CharacterListViewModel mViewModel;
    private AppThreadExecutor mAppThreadExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);
        setupToolbar();


        mRecyclerView = findViewById(R.id.character_list);
        assert mRecyclerView != null;
        setupRecyclerView(mRecyclerView);

        setupViewModelObserver();

        if (findViewById(R.id.character_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        mAppThreadExecutor = new AppThreadExecutor();

        fetchCharactersFromNetwork("", 1);
    }

    private void setupToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupViewModelObserver() {
        mViewModel = ViewModelProviders.of(this).get(CharacterListViewModel.class);
        mViewModel.getCharacterList().observe(this, new Observer<List<Character>>() {
            @Override
            public void onChanged(@Nullable List<Character> characters) {
                if (characters != null && !characters.isEmpty()) {
                    mCharacterList.clear();
                    mCharacterList.addAll(mCharacterList);
                    mAppThreadExecutor.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView.getAdapter().notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Find Star Wars People By Name");
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
            public boolean onQueryTextChange(final String starWarsCharacterSearchWord) {
                mAppThreadExecutor.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Character> list = mViewModel.getCharacterByName(starWarsCharacterSearchWord);
                        if (list != null && !list.isEmpty()) {
                            mCharacterList.clear();
                            mCharacterList.addAll(list);
                        } else {
                            mCharacterList.clear();
                        }
                        mAppThreadExecutor.mainThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                mRecyclerView.getAdapter().notifyDataSetChanged();
                            }
                        });
                    }
                });
                return false;
            }
        });
        searchView.setIconified(false);

        return true;
    }

    private void fetchCharactersFromNetwork(final String starWarsCharacterSearchWord, final int next) {
        StarWarsClient client = ServiceGenerator
                .getInstance(CharacterListActivity.this)
                .createService(StarWarsClient.class);

        Call<Result> resultCall = client.lookupCharacterNames(starWarsCharacterSearchWord, next);

        resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, final Response<Result> response) {
                mAppThreadExecutor.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (response.body() != null && response.body().getResults() != null) {
                            mViewModel.insertCharacterList(response.body().getResults());
                        }
                    }
                });

                if (response.body().getNext() != null && !response.body().getNext().isEmpty()) {
                    mAppThreadExecutor.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            fetchCharactersFromNetwork(starWarsCharacterSearchWord, next + 1);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable throwable) {
                mNetworkStatusSnackbar.setText(throwable.getMessage());
                mNetworkStatusSnackbar.show();
            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        if (mCharacterList == null) {
            mCharacterList = new ArrayList<>();
        }
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, mCharacterList));
    }
}

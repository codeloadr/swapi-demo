package com.nsoni.starwars;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private CharacterListActivity characterListActivity;
    private final List<com.nsoni.starwars.data.Character> mValues;

    public SimpleItemRecyclerViewAdapter(CharacterListActivity characterListActivity, List<com.nsoni.starwars.data.Character> items) {
        this.characterListActivity = characterListActivity;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (characterListActivity.mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(CharacterDetailFragment.ARG_ITEM_ID, holder.mItem);
                    CharacterDetailFragment fragment = new CharacterDetailFragment();
                    fragment.setArguments(arguments);
                    characterListActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.character_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, CharacterDetailActivity.class);
                    intent.putExtra(CharacterDetailFragment.ARG_ITEM_ID, holder.mItem);

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public com.nsoni.starwars.data.Character mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.name);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}

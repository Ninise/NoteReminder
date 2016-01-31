package com.ninise.notereminder.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ninise.notereminder.R;
import com.ninise.notereminder.database.NoteModel;
import com.ninise.notereminder.database.NoteWorker;
import com.ninise.notereminder.main.adapters.NoteAdapter;

import java.util.List;

public class NoteListFragment extends Fragment {

    private static final String TAG = "NoteListFragment";

    List<NoteModel> mNoteList;
    RecyclerView mRecyclerView;
    NoteWorker mNoteWorker;
    NoteAdapter mAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNoteWorker = new NoteWorker(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragments_list_layout, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv);

        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(true);

        final RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(itemAnimator);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mNoteList = mNoteWorker.getAllNotes();
        mAdapter = new NoteAdapter(mNoteList, getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }
}

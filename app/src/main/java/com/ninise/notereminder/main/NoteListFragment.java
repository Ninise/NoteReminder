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

        updateList();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragments_list_layout, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv);

        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        final RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(itemAnimator);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNoteWorker.close();
    }

    public void updateList() {
        mNoteList = mNoteWorker.getAllNotes();
        mAdapter = new NoteAdapter(mNoteList, getActivity());
    }

}

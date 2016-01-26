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

public class ReminderListFragment extends Fragment {

    public static final String TAG = "ReminderListFragment";

    List<NoteModel> mNoteList;
    RecyclerView mRecyclerView;
    NoteWorker mNoteWorker;
    NoteAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNoteWorker = new NoteWorker(getActivity());

        updateList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragments_list_layout, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
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

    private void updateList() {
        mNoteList = mNoteWorker.getAllReminders();
        mAdapter = new NoteAdapter(mNoteList, getActivity());
    }
}

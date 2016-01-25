package com.ninise.notereminder.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ninise.notereminder.R;
import com.ninise.notereminder.Utils.Constants;
import com.ninise.notereminder.database.NoteModel;
import com.ninise.notereminder.database.NoteWorker;
import com.ninise.notereminder.notedata.NoteActivity;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    List <NoteModel> mDataSet;
    Context context;
    NoteWorker noteWorker;

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView mUrlTextView;
        CardView cv;
        ImageButton deleteBtn;
        ImageButton alarmBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv);

            mUrlTextView = (TextView) itemView.findViewById(R.id.data_title);

            deleteBtn = (ImageButton) itemView.findViewById(R.id.dataBtnDelete);

            alarmBtn = (ImageButton) itemView.findViewById(R.id.dataBtnSetAlarm);
        }
    }

    public NoteAdapter (List<NoteModel> dataset, Context context) {
        mDataSet = dataset;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mUrlTextView.setText(mDataSet.get(position).getTitle());

        /** Click on CardView for send data to NoteActivity */
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(holder.getAdapterPosition());
            }
        });

        /** Click on "trash" button for delete note */
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               remove(holder.getAdapterPosition());
            }
        });

        /** Click on "alarm" button for set note as alarm  */
        holder.alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO ALARM FOR NOTE
            }
        });

        animate(holder);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void insert(int position, NoteModel data) {
        mDataSet.add(position, data);
        notifyItemInserted(position);
    }

    private void animate(RecyclerView.ViewHolder viewHolder) {
        Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context,
                R.anim.anticipate_overshoot_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    private void remove(int position) {
        noteWorker = new NoteWorker(context);
        noteWorker.deleteNote(mDataSet.get(position));
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }

    private void sendData(int position) {
        Intent intent = new Intent(context, NoteActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.EXTRA_ID, mDataSet.get(position).getId());
        bundle.putString(Constants.EXTRA_TITLE, mDataSet.get(position).getTitle());
        bundle.putString(Constants.EXTRA_DESCRIPT, mDataSet.get(position).getDescription());
        bundle.putLong(Constants.EXTRA_TIME, mDataSet.get(position).getTime());

        intent.putExtras(bundle);

        context.startActivity(intent);
    }
}

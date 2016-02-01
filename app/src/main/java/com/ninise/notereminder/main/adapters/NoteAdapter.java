package com.ninise.notereminder.main.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ninise.notereminder.R;
import com.ninise.notereminder.Utils.Constants;
import com.ninise.notereminder.database.NoteModel;
import com.ninise.notereminder.database.NoteWorker;
import com.ninise.notereminder.notedata.NoteActivity;

import java.text.SimpleDateFormat;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private static final String TAG = "NoteAdapter";
    final List <NoteModel> mDataSet;
    final Context context;
    NoteWorker noteWorker;
    final SimpleDateFormat date;

    public class ViewHolder extends RecyclerView.ViewHolder  {
        final TextView mTitleTextView;
        final CardView cv;
        final ImageButton mDeleteBtn;
        final TextView mToTextView;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        double disp_width = (double) display.getWidth() / 1.7;

        public ViewHolder(final View itemView) {
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv);

            mTitleTextView = (TextView) itemView.findViewById(R.id.data_title);
            mTitleTextView.setMaxWidth((int) disp_width);

            mDeleteBtn = (ImageButton) itemView.findViewById(R.id.dataBtnDelete);

            mToTextView = (TextView) itemView.findViewById(R.id.data_to_time);

        }
    }

    @SuppressLint("SimpleDateFormat")
    public NoteAdapter (final List<NoteModel> dataset, final Context context) {
        mDataSet = dataset;
        this.context = context;
        date = new SimpleDateFormat("dd MMM hh:mm:ss");
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String title = mDataSet.get(holder.getAdapterPosition()).getTitle();

        holder.mTitleTextView.setText(title);


        if (mDataSet.get(holder.getAdapterPosition()).getTime() > 0) {

            final String deadline = context.getString(R.string.to_time);

            holder.mToTextView.setText(deadline + " " + date.format(mDataSet.get(holder.getAdapterPosition()).getTime()));
        }

        /** Click on CardView for send data to NoteActivity */
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(holder.getAdapterPosition());
            }
        });

        /** Click on "trash" button for delete note */
        holder.mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               remove(holder.getAdapterPosition());
            }
        });

        animate(holder);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void animate(final RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context,
                R.anim.anticipate_overshoot_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    private void remove(final int position) {
        noteWorker = new NoteWorker(context);
        noteWorker.deleteNote(mDataSet.get(position));
        mDataSet.remove(position);

        noteWorker.close();
        notifyItemRemoved(position);
    }

    private void sendData(final int position) {
        final Intent intent = new Intent(context, NoteActivity.class);

        final Bundle bundle = new Bundle();
        bundle.putInt(Constants.EXTRA_ID, mDataSet.get(position).getId());
        bundle.putString(Constants.EXTRA_TITLE, mDataSet.get(position).getTitle());
        bundle.putString(Constants.EXTRA_DESCRIPT, mDataSet.get(position).getDescription());
        bundle.putLong(Constants.EXTRA_TIME, mDataSet.get(position).getTime());

        intent.putExtras(bundle);

        context.startActivity(intent);
    }
}

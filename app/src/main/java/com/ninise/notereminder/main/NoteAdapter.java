package com.ninise.notereminder.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ninise.notereminder.R;
import com.ninise.notereminder.Utils.Constants;
import com.ninise.notereminder.database.NoteModel;
import com.ninise.notereminder.database.NoteWorker;
import com.ninise.notereminder.notedata.NoteActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    List <NoteModel> mDataSet;
    Context context;
    NoteWorker noteWorker;
    SimpleDateFormat date;
    long time;

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView mUrlTextView;
        CardView cv;
        ImageButton mDeleteBtn;
        ImageButton mAlarmBtn;
        TextView mToTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv);

            mUrlTextView = (TextView) itemView.findViewById(R.id.data_title);

            mDeleteBtn = (ImageButton) itemView.findViewById(R.id.dataBtnDelete);

            mAlarmBtn = (ImageButton) itemView.findViewById(R.id.dataBtnSetAlarm);

            mToTextView = (TextView) itemView.findViewById(R.id.data_to_time);

        }
    }

    public NoteAdapter (List<NoteModel> dataset, Context context) {
        mDataSet = dataset;
        this.context = context;
        date = new SimpleDateFormat("dd MMM hh:mm:ss");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mUrlTextView.setText(mDataSet.get(holder.getAdapterPosition()).getTitle());

        if (mDataSet.get(holder.getAdapterPosition()).getTime() > 0) {

            String deadline = context.getString(R.string.to_time);

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

        /** Click on "alarm" button for set note as alarm  */
        holder.mAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(holder.getAdapterPosition());
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

    private void update(int position) {
        noteWorker = new NoteWorker(context);

        NoteModel newNote = mDataSet.get(position);
        Log.d("NoteAdapter", mDataSet.get(position).toString());
        newNote.setTime(time);
        noteWorker.updateNote(newNote);
        mDataSet.set(position, newNote);
        notifyDataSetChanged();

        Log.d("NoteAdapter", mDataSet.get(position).toString());
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

    private void showDateTimeDialog(final int position) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View dateAndTimePicker = factory.inflate(R.layout.date_time_picker, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(dateAndTimePicker)
                .setCancelable(true)
                .setPositiveButton(R.string.on_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatePicker datePicker = (DatePicker) dateAndTimePicker.findViewById(R.id.date_picker);
                        TimePicker timePicker = (TimePicker) dateAndTimePicker.findViewById(R.id.time_picker);

                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute());

                        time = calendar.getTimeInMillis();

                        update(position);
                    }
                });

        final AlertDialog alert = builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveBtn = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveBtn.setTextSize(30);
            }
        });
        alert.show();
    }
}

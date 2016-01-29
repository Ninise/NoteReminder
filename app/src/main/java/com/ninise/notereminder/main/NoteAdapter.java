package com.ninise.notereminder.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import com.ninise.notereminder.notification.AlarmNotification;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    final List <NoteModel> mDataSet;
    final Context context;
    NoteWorker noteWorker;
    final SimpleDateFormat date;
    long time;

    public class ViewHolder extends RecyclerView.ViewHolder  {
        final TextView mUrlTextView;
        final CardView cv;
        final ImageButton mDeleteBtn;
        final ImageButton mAlarmBtn;
        final TextView mToTextView;

        public ViewHolder(final View itemView) {
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv);

            mUrlTextView = (TextView) itemView.findViewById(R.id.data_title);

            mDeleteBtn = (ImageButton) itemView.findViewById(R.id.dataBtnDelete);

            mAlarmBtn = (ImageButton) itemView.findViewById(R.id.dataBtnSetAlarm);

            mToTextView = (TextView) itemView.findViewById(R.id.data_to_time);

        }
    }

    public NoteAdapter (final List<NoteModel> dataset, final Context context) {
        mDataSet = dataset;
        this.context = context;
        date = new SimpleDateFormat("dd MMM hh:mm:ss");
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        final ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mUrlTextView.setText(mDataSet.get(holder.getAdapterPosition()).getTitle());

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
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void insert(final int position, final NoteModel data) {
        mDataSet.add(position, data);
        notifyItemInserted(position);
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
        notifyItemRemoved(position);
    }

    private void update(final int position) {
        noteWorker = new NoteWorker(context);

        final NoteModel newNote = mDataSet.get(position);
        newNote.setTime(time);

        noteWorker.updateNote(newNote);

        mDataSet.set(position, newNote);

        final AlarmNotification alarm = new AlarmNotification(context);
        alarm.setOnceAlarm(time, mDataSet.get(position).getDescription());

        notifyDataSetChanged();
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

    private void showDateTimeDialog(final int position) {
        final LayoutInflater factory = LayoutInflater.from(context);
        final View dateAndTimePicker = factory.inflate(R.layout.date_time_picker, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context)
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

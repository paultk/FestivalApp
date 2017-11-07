package com.example.paulthomaskorsvold.festivalappclean.checklist;

/**
 * Created by paulthomaskorsvold on 10/7/17.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paulthomaskorsvold.festivalappclean.R;
import com.example.paulthomaskorsvold.festivalappclean.models.ChecklistItem;

import static android.provider.Settings.Global.getString;

public class ChecklistItemAdapter extends RecyclerView.Adapter<ChecklistItemAdapter.ViewHolder>  {
    private Cursor mCursor;
    private ChecklistItemClickListener mChecklistItemClickListener;


    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }


    public interface ChecklistItemClickListener{
        void ChecklistItemOnClick(ChecklistItem ChecklistItem, int position);
//        void ChecklistItemOnLongClick(ChecklistItem ChecklistItem, int position);
    }


    public ChecklistItemAdapter(Cursor c, ChecklistItemClickListener mChecklistItemClickListener) {
        this.mCursor = c;
        this.mChecklistItemClickListener = mChecklistItemClickListener;
    }

    @Override
    public ChecklistItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.single_checklist_item, parent, false);

        // Return a new holder instance
        ChecklistItemAdapter.ViewHolder viewHolder = new ChecklistItemAdapter.ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ChecklistItemAdapter.ViewHolder holder, final int position) {

        int idIndex = mCursor.getColumnIndex(ChecklistItem.KEY_ID);
        int cDescriptionIndex = mCursor.getColumnIndex(ChecklistItem.KEY_DESCRIPTION);
        int cDateIndex= mCursor.getColumnIndex(ChecklistItem.KEY_DATE);
        int cStatusIndex = mCursor.getColumnIndex(ChecklistItem.KEY_STATUS);
        int cTitleIndex = mCursor.getColumnIndex(ChecklistItem.KEY_TITLE);

        mCursor.moveToPosition(position);

        int id = mCursor.getInt(idIndex);
        String cDescription = mCursor.getString(cDescriptionIndex);
        String cDate = mCursor.getString(cDateIndex);
        String cTitle = mCursor.getString(cTitleIndex);
        String cStatus = mCursor.getString(cStatusIndex);

//        holder.mTitle.setText(ChecklistItemText);

        final  ChecklistItem CHECKLISTITEM = new ChecklistItem(id, cDescription, cDate, cTitle, cStatus);

        holder.id = id;

        holder.mTitle.setText(CHECKLISTITEM.getmTitle());
        holder.mDateAdded.setText(CHECKLISTITEM.getmDateCreated());
        holder.mDescription.setText(CHECKLISTITEM.getmDescription());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChecklistItemClickListener.ChecklistItemOnClick(CHECKLISTITEM, position);
            }
        });

    }

    @Override
    public int getItemCount() {

        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public View mView;
        public TextView mTitle, mDateAdded, mDescription;

//        id is used for deleting on swipe
        public int id;

        //Constructor
        public ViewHolder(View v) {


            super(v);
            mTitle =  v.findViewById(R.id.checklist_item_title);
            mDateAdded = v.findViewById(R.id.date_added);
            mDescription = v.findViewById(R.id.description);
            mView = v;
        }
    }
}

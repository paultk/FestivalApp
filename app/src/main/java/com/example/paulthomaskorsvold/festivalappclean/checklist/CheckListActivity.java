package com.example.paulthomaskorsvold.festivalappclean.checklist;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.example.paulthomaskorsvold.festivalappclean.R;
import com.example.paulthomaskorsvold.festivalappclean.models.ChecklistItem;
import com.example.paulthomaskorsvold.festivalappclean.utils.database_utils.ChecklistProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CheckListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, ChecklistItemAdapter.ChecklistItemClickListener{
    private enum DB_ACTION {
        DELETE, INSERT, UPDATE
    }

    private static final String M_TAG = "ChecklistActivity";
    private Cursor mCursor;

    //Local variables
    private ChecklistItemAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private EditText mNewChecklistItemText;

    //Constants used when calling the detail activity
    public static final int REQUESTCODE = 2;



    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

//        Swipe down or up, moves the checklistItems
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            mAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

            Log.d(M_TAG, "something was swiped");

            ChecklistItemAdapter.ViewHolder adapterViewHolder = (ChecklistItemAdapter.ViewHolder) viewHolder;

            Log.d(M_TAG, "id:\t" + adapterViewHolder.id);
            int id = deleteItem(adapterViewHolder.id);
            Log.d(M_TAG, "deleted = " + id);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        //Initialize the local variables
        mRecyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getSupportLoaderManager().initLoader(0, null, this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckListActivity.this, ChecklistFormActivity.class);
                intent.putExtra(ChecklistFormActivity.REQUEST_CODE_STRING, ChecklistFormActivity.ADD_CODE);
                startActivityForResult(intent, 0);
            }

        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (resultCode == RESULT_OK) {
                ChecklistItem checklistItem;
                int id = data.getIntExtra(ChecklistItem.KEY_ID, -1);
                checklistItem = new ChecklistItem(
                        id, data.getStringExtra(ChecklistItem.KEY_DESCRIPTION),
                        new java.util.Date().toString(), data.getStringExtra(ChecklistItem.KEY_TITLE),
                        "New"
                );
                if(id < 0) {
                    insertOrAddChecklistItem(checklistItem, DB_ACTION.INSERT);
                } else {
                    checklistItem.setmStatus("Updated");
                    insertOrAddChecklistItem(checklistItem, DB_ACTION.UPDATE);
                }
                Log.d(M_TAG, checklistItem.toString());
        }
    }

//    Short click = update
    @Override
    public void ChecklistItemOnClick(ChecklistItem checklistItem, int position) {

        Intent intent = new Intent(CheckListActivity.this, ChecklistFormActivity.class);
        intent.putExtra(ChecklistFormActivity.REQUEST_CODE_STRING, ChecklistFormActivity.UPDATE_CODE);
        intent.putExtra(ChecklistItem.KEY_DESCRIPTION, checklistItem.getmDescription());
        intent.putExtra(ChecklistItem.KEY_TITLE, checklistItem.getmTitle());
        intent.putExtra(ChecklistItem.KEY_ID, checklistItem.getmId());
        startActivityForResult(intent, 0);
    }

/*//    Long click = delete
    @Override
    public void ChecklistItemOnLongClick(ChecklistItem checklistItem, int position) {
//        deleteItem(checklistItem.getmId());
    }*/

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        CursorLoader cursorLoader = new CursorLoader(this, ChecklistProvider.CONTENT_URI, null,
                null, null, null);
        return cursorLoader;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor mCursor) {
        mAdapter = new ChecklistItemAdapter(mCursor, this);
        mRecyclerView.setAdapter(mAdapter);
//        testPut();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(mCursor);
    }


  /*  private void testPut(int i ) {
        ContentValues values = new ContentValues();

        values.put(ChecklistItem.KEY_DATE, "date" + i);
        values.put(ChecklistItem.KEY_DESCRIPTION, "description" + i);
        values.put(ChecklistItem.KEY_DATE, "date" + i);
        values.put(ChecklistItem.KEY_STATUS, "status" + i);
        values.put(ChecklistItem.KEY_TITLE, "title" + i);


        Uri uri = getContentResolver().insert(ChecklistProvider.CONTENT_URI, values);
        Log.d(M_TAG, "uri:\t" + uri);
    }*/

    private void insertOrAddChecklistItem(ChecklistItem checklistItem, DB_ACTION dbAction) {

        ContentValues values = new ContentValues();

        values.put(ChecklistItem.KEY_DESCRIPTION, checklistItem.getmDescription());
        values.put(ChecklistItem.KEY_TITLE, checklistItem.getmTitle());
        values.put(ChecklistItem.KEY_STATUS, checklistItem.getmStatus());


        if(dbAction == DB_ACTION.INSERT) {
            values.put(ChecklistItem.KEY_DATE, checklistItem.getmDateCreated());
            Uri uri = getContentResolver().insert(ChecklistProvider.CONTENT_URI, values);
        }
        else {
            Uri singleUri = ContentUris.withAppendedId(ChecklistProvider.CONTENT_URI, checklistItem.getmId());
                getContentResolver().update(singleUri, values, null, null);
        }
    }

    private int deleteItem(int id) {
        Uri singleUri = ContentUris.withAppendedId(ChecklistProvider.CONTENT_URI, id);
        Log.d(M_TAG, "before delete Id: " + id);
        return getContentResolver().delete(singleUri, null, null);
    }
}


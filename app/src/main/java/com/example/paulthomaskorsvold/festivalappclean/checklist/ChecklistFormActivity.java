package com.example.paulthomaskorsvold.festivalappclean.checklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paulthomaskorsvold.festivalappclean.R;
import com.example.paulthomaskorsvold.festivalappclean.models.ChecklistItem;

public class ChecklistFormActivity extends AppCompatActivity {

    private Button mAddButton;
    private EditText mEditTextTitle, mEditTextDescription;

    public final static int UPDATE_CODE = 0;
    public final static int ADD_CODE = 1;
    public final static String REQUEST_CODE_STRING = "REQUEST_CODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_form);

        mAddButton =  findViewById(R.id.addButton);
        mEditTextDescription = (EditText) findViewById(R.id.editTextDescription);
        mEditTextTitle = (EditText) findViewById(R.id.editTextTitle);

//        Set the button text to "update" or leave it to be "add" depending on request code
        if (getIntent().getIntExtra(REQUEST_CODE_STRING, 1) == UPDATE_CODE) {
            mEditTextTitle.setText(getIntent().getStringExtra(ChecklistItem.KEY_TITLE));
            mEditTextDescription.setText(getIntent().getStringExtra(ChecklistItem.KEY_DESCRIPTION));
            mAddButton.setText(R.string.update);
        }

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = mEditTextDescription.getText().toString();
                String title = mEditTextTitle.getText().toString();
                if(!title.equals("")) {
                    Intent resultIntent = new Intent();

                    resultIntent.putExtra(ChecklistItem.KEY_DESCRIPTION, description);
                    resultIntent.putExtra(ChecklistItem.KEY_TITLE, title);
                    resultIntent.putExtra(ChecklistItem.KEY_ID, getIntent().getIntExtra(ChecklistItem.KEY_ID, -1));
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Enter text", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}

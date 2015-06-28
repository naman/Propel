package com.propel.bluemix.propel;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.ibm.mobile.services.data.IBMDataObject;
import com.propel.bluemix.propel.Data.Item;
import com.propel.bluemix.propel.Database.DbContract;
import com.propel.bluemix.propel.Database.DbSingleton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import bolts.Continuation;
import bolts.Task;

import static com.propel.bluemix.propel.R.color.white;

@SuppressWarnings("ResourceType")
public class PostActivity extends AppCompatActivity {
    public static final String CLASS_NAME = "PostActivity";

    Calendar myCalendar = Calendar.getInstance();
    BlueListApplication blApplication;
    List<Item> itemsList;

    private Toolbar mToolbar;
    private EditText goal;
    private EditText pickdate;
    private EditText picktime;
    private EditText description;
    private Button submit;
    private DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        setUpToolbar();

        blApplication = (BlueListApplication) getApplication();
        goal = (EditText) findViewById(R.id.edit_goal);
        description = (EditText) findViewById(R.id.edit_descr);
        submit = (Button) findViewById(R.id.submit_post);
        pickdate = (EditText) findViewById(R.id.date);
        picktime = (EditText) findViewById(R.id.time);
        submit.setBackgroundColor(Color.CYAN);
        submit.setTextColor(white);
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SUBMIT", "butoon clicked");
                createItem(v);


//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.content_frame, new ViewFragment()).commit();
//                finish();
                onBackPressed();
            }
        });

        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdate.setInputType(InputType.TYPE_NULL);
                new DatePickerDialog(PostActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        picktime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picktime.setInputType(InputType.TYPE_NULL);
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(PostActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        picktime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        pickdate.setText(sdf.format(myCalendar.getTime()));
    }

    public void createItem(View v) {
        String title = goal.getText().toString();
        String descr = description.getText().toString();
        String date = pickdate.getText().toString();
        String time = picktime.getText().toString();
        String date_time = date + "T" + time;

        if (!goal.equals("")) {
            Log.d("CREATE", "item");
            Item item = new Item(title, descr, date_time);

            itemsList = blApplication.itemList;
            Log.d("SIZE ITEM", itemsList.size() + "");
            DbSingleton dbSingleton = DbSingleton.getInstance();
            dbSingleton.clearDatabase(DbContract.Posts.TABLE_NAME);
            int id = 1;
            itemsList.add(item);

            for (Item item1 : itemsList) {
                String query = item1.generateSql(id);
                Log.d("qUERY", query);
                id++;
                dbSingleton.insertQueries(query);
            }

            // Use the IBMDataObject to create and persist the Item object.
            item.save().continueWith(new Continuation<IBMDataObject, Void>() {

                @Override
                public Void then(Task<IBMDataObject> task) throws Exception {
                    // Log if the save was cancelled.
                    if (task.isCancelled()) {
                        Log.e(CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
                    }
                    // Log error message, if the save task fails.
                    else if (task.isFaulted()) {
                        Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
                    }

                    // If the result succeeds, load the list.
                    else {
                        finish();
                    }
                    return null;
                }

            });

            // Set text field back to empty after item is added.
            goal.setText("");
        }
    }
}

package com.propel.bluemix.propel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMQuery;
import com.propel.bluemix.propel.Data.Item;
import com.propel.bluemix.propel.Database.DbSingleton;
import com.propel.bluemix.propel.Fragments.ViewFragment;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

public class MainActivity extends AppCompatActivity {

    public static final String CLASS_NAME = "MainActivity";
    List<Item> itemList;
    DbSingleton dbSingleton;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        setUpNavDrawer();

        dbSingleton = DbSingleton.getInstance();
        /* Use application class to maintain global state. */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        BlueListApplication blApplication;
        blApplication = (BlueListApplication) getApplication();
        itemList = blApplication.getItemList();

        for (Item item : itemList) {
            int i = 1;
            item.generateSql(i);
            Log.d("ITEM QUERY", item.generateSql(i).toString());
            i++;
        }

    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    private void setUpNavDrawer() {
        if (mToolbar != null) {
            final android.support.v7.app.ActionBar ab = getSupportActionBar();
            assert ab != null;
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
            mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

            mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
            mActionBarDrawerToggle.syncState();
        }
    }

    public void listItems() {
        try {
            IBMQuery<Item> query = IBMQuery.queryForClass(Item.class);
            // Query all the Item objects from the server.
            query.find().continueWith(new Continuation<List<Item>, Void>() {

                @Override
                public Void then(Task<List<Item>> task) throws Exception {
                    final List<Item> objects = task.getResult();
                    // Log if the find was cancelled.
                    if (task.isCancelled()) {
                        Log.e(CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
                    }
                    // Log error message, if the find task fails.
                    else if (task.isFaulted()) {
                        Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
                    }


                    // If the result succeeds, load the list.
                    else {
                        // Clear local itemList.
                        // We'll be reordering and repopulating from DataService.
                        itemList.clear();
                        //  dbSingleton.clearDatabase(DbContract.Posts.TABLE_NAME);
//                        for (IBMDataObject item : objects) {
//                            int i =1;
//                            itemList.add((Item) item);
//                            ((Item) item).generateSql(i);
//                        }
                        sortItems(itemList);

                    }
                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);

        } catch (IBMDataException error) {
            Log.e(CLASS_NAME, "Exception : " + error.getMessage());
        }
    }

    private void sortItems(List<Item> theList) {
        // Sort collection by case insensitive alphabetical order.
        Collections.sort(theList, new Comparator<Item>() {
            public int compare(Item lhs,
                               Item rhs) {
                String lhsName = lhs.getName();
                String rhsName = rhs.getName();
                return lhsName.compareToIgnoreCase(rhsName);
            }
        });
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.nav_feed:
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_frame, new ViewFragment()).commit();
                                break;
                            case R.id.nav_PUSH:
                                Intent push = new Intent(MainActivity.this, PushActivity.class);
                                startActivity(push);
                                break;

                        }

                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );

    }
}


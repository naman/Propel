//-------------------------------------------------------------------------------
//Copyright 2014 IBM Corp. All Rights Reserved
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0 
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License. 
//-------------------------------------------------------------------------------

package com.propel.bluemix.propel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.push.IBMPush;
import com.ibm.mobile.services.push.IBMPushNotificationListener;
import com.ibm.mobile.services.push.IBMSimplePushNotification;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import bolts.Continuation;
import bolts.Task;


public class PushActivity extends Activity {
	private TextView txtVResult = null;

	private IBMPush push = null;
	private IBMPushNotificationListener notificationListener = null;
	
	
	private List<String> allTags;
	private List<String> subscribedTags;

    private static final String CLASS_NAME = PushActivity.class.getSimpleName();
    private static final String APP_ID = "applicationID";
    private static final String APP_SECRET = "applicationSecret";
    private static final String APP_ROUTE = "applicationRoute";
    private static final String PROPS_FILE = "bluelist.properties";

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		txtVResult = (TextView) findViewById(R.id.display);
		
		updateTextView("Starting Quick Start Push Sample..");
        // Read from properties file.
        Properties props = new Properties();
        Context context = getApplicationContext();
        try {
            AssetManager assetManager = context.getAssets();
            props.load(assetManager.open(PROPS_FILE));
            Log.i(CLASS_NAME, "Found configuration file: " + PROPS_FILE);
        } catch (FileNotFoundException e) {
            Log.e(CLASS_NAME, "The bluelist.properties file was not found.", e);
        } catch (IOException e) {
            Log.e(CLASS_NAME, "The bluelist.properties file could not be read properly.", e);
        }
        Log.i(CLASS_NAME, "Application ID is: " + props.getProperty(APP_ID));

        // Initialize the IBM core backend-as-a-service.
        IBMBluemix.initialize(this, props.getProperty(APP_ID), props.getProperty(APP_SECRET), props.getProperty(APP_ROUTE));
		
		push = IBMPush.initializeService();
		push.register("DemoDevice", "DemoUser").continueWith(new Continuation<String, Void>() {

            @Override
            public Void then(Task<String> task) throws Exception {

                if (task.isFaulted()) {
                    updateTextView("Error registering with Push Service. " + task.getError().getMessage() + "\n"
                            + "Push notifications will not be received.");
                } else {
                    updateTextView("Device is registered with Push Service" + "\n" + "Device Id : " + task.getResult());


                    displayTagSubscriptions().continueWith(new Continuation<Void, Void>() {

                        @Override
                        public Void then(Task<Void> task) throws Exception {
                            subscribeToTag();
                            return null;
                        }

                    });
                }
                return null;
            }
        });
		
		displayTags();
		
		notificationListener = new IBMPushNotificationListener() {

			@Override
			public void onReceive(final IBMSimplePushNotification message) {
				showSimplePushMessage(message);

			}

		};
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (push != null) {
			push.listen(notificationListener);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (push != null) {
			push.hold();
		}
	}

	void updateTextView(final String message) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				txtVResult.append("\n"+ message + "\n");
			}
		});
	}

	void showSimplePushMessage(final IBMSimplePushNotification message) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Builder builder = new Builder(PushActivity.this);
				builder.setMessage("Notification Received : "
						+ message.toString());
				builder.setCancelable(true);
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int s) {
							}
						});

				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
	}
	
	private void displayTags() {
		push.getTags().continueWith(new Continuation<List<String>,Void>() { 
			
			@Override
			public Void then(Task<List<String>> task) throws Exception {
				if(task.isFaulted()) {
					updateTextView("Error getting tags. " + task.getError().getMessage());
					return null;
				}
				List<String> tags = task.getResult();
				updateTextView("Retrieved Tags : " + tags);
				allTags = tags;
				return null;
			}
		});		
	}
	
	private Task<Void> displayTagSubscriptions() {
		
		return push.getSubscriptions().continueWith(new Continuation<List<String>,Void>() { 
			
			@Override
			public Void then(Task<List<String>> task) throws Exception {
				if(task.isFaulted()) {
					updateTextView("Error getting subscriptions.. " + task.getError().getMessage());
					return null;
				}
				List<String> tags = task.getResult();
				updateTextView("Retrieved subscriptions : " + tags);
				subscribedTags = tags;
				return null;
			}
		});			
	}

	private void subscribeToTag() {
		
		if ((subscribedTags != null && subscribedTags.size() == 0) && (allTags != null && allTags.size() != 0)) {
			push.subscribe(allTags.get(0)).continueWith(new Continuation<String, Void>() {

                @Override
                public Void then(Task<String> task) throws Exception {
                    if (task.isFaulted()) {
                        updateTextView("Error subscribing to Tag.."
                                + task.getError().getMessage());
                        return null;
                    }                    
                    updateTextView("Successfully Subscribed to Tag "+task.getResult());
                    
                    return null;
                }
            });
			
		} else {			
			updateTextView("Not subscribing to any more tags.");			
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
}
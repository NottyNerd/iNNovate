package io.innovate.innovate.Util;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by NottyNerd on 06/02/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String FRIENDLY_ENGAGE_TOPIC = "quotesmessage";

    @Override
    public void onTokenRefresh() {
       // super.onTokenRefresh();
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("LOGGER", "Refreshed token: " + refreshedToken);
        try {
            FirebaseMessaging.getInstance()
                    .subscribeToTopic(FRIENDLY_ENGAGE_TOPIC);
        }catch (Exception ex)
        {}

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);

    }

    private void sendRegistrationToServer(String refreshedToken) {
        // Once a token is generated, we subscribe to topic.

    }
}

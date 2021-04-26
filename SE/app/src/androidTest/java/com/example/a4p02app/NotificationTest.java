package com.example.a4p02app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static androidx.core.content.ContextCompat.getSystemService;
import static org.junit.Assert.assertEquals; // may change to google truth assertion

public class NotificationTest {
    public static final String NOTIF_CHANNEL_ID = "donation_application";
    private static final String NOTIF_CHANNEL_NAME = "Donation Application";
    private static final String NOTIF_CHANNEL_DESCRIPTION = "Donation Application Notifications";
    Notification newMessage = new Notification();

    @Before
    public void setUp() throws Exception {
        /* COMMENTED AS ITS NOT WORKING
        // required for versions OREO and above for notifications to work.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel nChannel = new NotificationChannel(NOTIF_CHANNEL_ID, NOTIF_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT); // change importance based on notif type
            nChannel.setDescription(NOTIF_CHANNEL_DESCRIPTION);
            NotificationManager nManager = getSystemService(NotificationManager.class); // not working
            nManager.createNotificationChannel(nChannel);
        }
        // create multiple dummy users for testing in database with tokens of my virtual devices
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference dummyUsers = db.collection("NotificationTest");
        // create dummy NPO for posting

        // NOTE: THIS WAS HARD CODED FOR TESTING, WILL INTEGRATE WITH PROFILE + DB IN THE FUTURE
        // will use new singleton class to access user data ex.// userData.getInstance().getPhone();

        //example for setting up new intent click navigation
         Intent newIntent = new Intent(this, MainActivity.class);
         newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
         PendingIntent nIntent = PendingIntent.getActivity(this, 0, newIntent, 0);
         */

    }

    /**
     * Verifies notification sent to only the specified users. Includes mass notifications.
     */
    @Test
    public void sentToCorrectUsers_returnsTrue(){
         // example: newMessage.display(this,1,"Test Title","Test Description",1);
    }

    /**
     * Verifies that the notification has the correct text, body, colour, icon, etc.
     */
    @Test
    public void containsCorrectAttributes_returnsTrue(){
    }

    /**
     * Verifies correct android settings: vibration, sound, blinking, etc.
     */
    @Test
    public void containsCorrectAndroidSettings_returnsTrue(){
    }

    /**
     * Verifies urgent notifications sent only to specified users.
     */
    @Test
    public void urgentNotificiationToUsers_returnsTrue(){
    }

    /**
     * Verifies urgent notifications sent only within correct distance, requires google maps functionality.
     */
    @Test
    public void urgentNotificiationToUsersDistance_returnsTrue(){
        //requires google maps functionality
    }

    /**
     * Verifies custom notifications sent only to specified users.
     */
    @Test
    public void customNotificiationToUsers_returnsTrue(){
    }

    /**
     * Verifies custom notifications sent only within correct distance, requires google maps functionality.
     */
    @Test
    public void customNotificiationToUsersDistance_returnsTrue(){
        //requires google maps functionality
    }

    /**
     * Verifies no notifications sent to users with all notifications disabled.
     */
    @Test
    public void disabledUserNotification_returnsTrue(){
    }

    /**
     * Verifies messages sent to and from correct users.
     */
    @Test
    public void messagesSentCorrectly_returnsTrue(){
    }

    /**
     * Verifies notification occurs in-application when application is open.
     */
    @Test
    public void inAppNotification_returnsTrue(){
    }

    /**
     * Verifies notification occurs in system tray when application is in background.
     */
    @Test
    public void inBackgroundNotification_returnsTrue(){
    }

    /**
     * Verifies notification occurs in system tray when application is not running.
     */
    @Test
    public void applicationClosedNotification_returnsTrue(){
    }

    /**
     * Verifies notification occurs in system tray when another application is in foreground.
     */
    @Test
    public void otherAppForegroundNotification_returnsTrue(){
    }

    /**
     * Verifies notification occurs in system tray when phone is in lock screen.
     */
    @Test
    public void lockscreenNotification_returnsTrue(){
    }

    /**
     * Verifies user is navigated to the home page when in-app, logged in, notification occurs.
     */
    @Test
    public void inAppHomePageNotification_returnsTrue(){
    }

    //MAYBE NOT A CONSIDERATION- what happens when app not logged in "notif waits for you in donoApp"
    /**
     * Verifies user is navigated to the login page when notification occurs and app not running.
     */
    @Test
    public void closedApplicationLoginPageNotification_returnsTrue(){
    }

    /**
     * Verifies notification is sent at correct time. No delays.
     */
    @Test
    public void notificationOnTime_returnsTrue(){
    }

    /**
     * Verifies notification is sent at correct time. No delays.
     */
    @Test
    public void timeZoneCheck_returnsTrue(){
    }

    //FOLLOWING TESTS FOR ON INTERACTION WITH NOTIFICATIONS
    /**
     * Verifies user is navigated to login page if interacts and not currently logged in.
     */
    @Test
    public void onInteractNotLoggedIn_returnsTrue(){
    }

    /**
     * Verifies user is navigated to NPO page when urgent need notification is clicked.
     */
    @Test
    public void onInteractUrgentNeed_returnsTrue(){
    }

    /**
     * Verifies user is navigated to messaging page when new message notification is clicked.
     */
    @Test
    public void onInteractNewMessage_returnsTrue(){
    }

    // perhaps not necessary, depends on how NPOs want to interact with the app.
    /**
     * Verifies NPO is navigated to page when new donation response notification is clicked.
     */
    @Test
    public void onInteractNPONewMessage_returnsTrue(){
    }

    //TODO: ensure all test cases covered with all types of potential NPO and Donor interactions in current iteration.

    /**
     * Verifies single notification is removed after user interaction.
     */
    @Test
    public void onInteractRemove_returnsTrue(){
    }

    /**
     * Verifies all notifications are removed from the system tray when the application is opened.
     */
    @Test
    public void onInteractAllRemoved_returnsTrue(){
    }

    //THREE LOCK SCREEN INTERACTIONS
    /**
     * Verifies correct sliding interaction from lock screen opens application.
     */
    @Test
    public void onLockScreenInteractUrgent_returnsTrue(){
    }

    /**
     * Verifies correct sliding interaction from lock screen opens application.
     */
    @Test
    public void onLockScreenInteractCustom_returnsTrue(){
    }

    /**
     * Verifies correct sliding interaction from lock screen opens application.
     */
    @Test
    public void onLockScreenInteractMessage_returnsTrue(){
    }

    /**
     * Verifies correct order of multiple notifications sent in succession. Checking for importance.
     */
    @Test
    public void correctOrderOfMultipleNotifications_returnsTrue(){
    }

    /**
     * Verifies no duplicate notifications occur when.
     */
    @Test
    public void noDuplicates_returnsTrue(){
    }

    /**
     * Verifies notification counter correctly displays number of pending notifications.
     */
    @Test
    public void correctNotificationCounterSum_returnsTrue(){
    }

    /**
     * Verifies correct preemptive deletion of notification if needs met before user interacts.
     */
    @Test
    public void needsMetDeletion_returnsTrue(){
    }

    /**
     * Verifies correct preemptive deletion of notification if message was deleted before interaction.
     */
    @Test
    public void messageDeletion_returnsTrue(){
    }

    @After
    public void tearDown() throws Exception {
        // delete dummy users from database
        // assertNull(dummyUser1);
        // assertNull(dummyUser2); //etc
    }
}

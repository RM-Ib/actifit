/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.actifit.fitnesstracker.actifitfitnesstracker;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

//import com.amazonaws.mobileconnectors.s3.transferutility.TransferService;


import com.amazonaws.mobileconnectors.s3.transferutility.TransferService;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.ump.ConsentDebugSettings;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.FormError;
import com.google.android.ump.UserMessagingPlatform;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;
import com.scottyab.rootbeer.RootBeer;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

import info.androidhive.fontawesome.FontTextView;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;
import static java.lang.Integer.parseInt;


/**
 * Implementation of this project was made possible via re-use, adoption and improvement of
 * following tutorials and resources:
 * - http://file.allitebooks.com/20170511/Android%20Sensor%20Programming%20By%20Example.pdf.
 * - google's simple-pedometer github work licensed under Apache License
 * https://github.com/google/simple-pedometer (I initially found it under
 * http://gadgetsaint.com/android/create-pedometer-step-counter-android/ who seems to have
 * copied it without any reference to original source/work by google)
 * - https://notes.iopush.net/android-send-a-https-post-request/
 * - additional help and code has been utilized from
 * https://fabcirablog.weebly.com/blog/creating-a-never-ending-background-service-in-android
 * to help with services, but also relying on official Android documentation
 */

/**
 * attributions:
 * success alert image: <a href="https://www.freeiconspng.com/img/23186">Success Hd Icon</a>
 * error alert image: <a href="https://www.freeiconspng.com/img/25248">sign error icon</a>
 */

public class MainActivity extends BaseActivity{
    public static SensorManager sensorManager;
    public static String username = "";
    public static String commToken;
    //private TextView stepDisplay;
    private RelativeLayout thirdPartyTracking;
    private LinearLayout gadgetsll;

    //tracks a reference to an instance of this class
    public static SensorEventListener mainActivitySensorList;

    public static final String TAG = "Actifit";

    private Double blurtPrice = 0.02;

    public static int connectTimeout = 10000;
    public static int connectMaxRetries = 3;


    public static int connectSubsequentRetryDelay = 2; //backoffmultiplier

    private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);



    private AlertDialog.Builder pendingRewardsDialogBuilder;
    private AlertDialog pendingRewardsDialog;
    private JSONObject innerRewards = new JSONObject();

    private AlertDialog.Builder earningsDialogBuilder;
    private AlertDialog earningsDialog;

    private AlertDialog.Builder gadgetsDialogBuilder;
    private AlertDialog gadgetsDialog;

    private AlertDialog.Builder afitBuyDialogBuilder;
    private AlertDialog afitBuyDialog;
    JSONArray afitMarkets, dailyTip;

    TextView giftLoader;

    View referLayout;

    //tracks if listener is active
    public static boolean isListenerActive = false;

    private StepsDBHelper mStepsDBHelper;

    //to utilize built-in step sensors
    private Sensor stepSensor;

    public static boolean isStepSensorPresent = false;
    public static String ACCEL_SENSOR = "ACCEL_SENSOR";
    public static String STEP_SENSOR = "STEP_SENSOR";
    public static String ACTIFIT_CORE_URL = "https://actifit.io";
    public static String ACTIFIT_RANK_URL = "https://actifit.io/userrank";

    //enforcing active sensor by default as ACC
    public static String activeSensor = MainActivity.ACCEL_SENSOR;

    /* items related to batch data capturing */

    //private int curStepCount = 0;
    private static final String BUNDLE_LISTENER = "listener";

    private static Intent mServiceIntent;
    private static ActivityMonitorService mSensorService;
    private Context ctx;

    private BroadcastReceiver receiver;

    //flag if service is bound now
    boolean mBound = false;

    public Context getCtx() {
        return ctx;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    String checkMark = "&#10003;";

    PieChart btnPieChart;
    PieChart fitbitPieChart;

    static boolean isActivityVisible = true;

    private BarData chartBarData, dayBarData;

    private BarChart dayChart, fullChart;

    public static Double userFullBalance = 0.0;
    public static String userRank = "0.0";//default 0
    public boolean hasSteemAccount = false;
    public boolean hasBlurtAccount = false;
    public Double blurtBalance = 0.0;

    int lastChatCount = 0;

    public static Double minTokenCount;
    TextView loginLink, logoutLink, signupLink, accountRCValue, votingStatusText, newbieLink,
            notifCount, chatNotifCount;
    LinearLayout loginContainer, userGadgets, accountRCContainer, votingStatusContainer;
    GridLayout topIconsContainer;
    FontTextView BtnSettings;

    public static JSONObject userSettings;
    JSONArray freeSignupLinks;

    JSONArray productsList;
    JSONArray activeProducts;
    JSONArray userReferrals;
    boolean userCanClaimSignupLinks = false;


    private RewardedAd rewardedAd;
    private Button dailyRewardButton;
    private Button freeRewardButton, fivekRewardButton, sevenkRewardButton, tenkRewardButton,moveTotweets;
    private Button BtnWaves;
    private boolean dailyRewardClaimed = false, fivekRewardClaimed = false, sevenkRewardClaimed = false, tenkRewardClaimed = false;
    private boolean isAdLoading;

    Button fullChartButton, dayChartButton;

    RotateAnimation rotate;

    ScaleAnimation scaler;
    ValueAnimator valueAnimator;

    Button BtnPostSteemit;

    private ConsentInformation consentInformation;
    private ConsentForm consentForm;

    /* news tab related variables */

    private List<Slider_Items_Model_Class> listItems;
    private ViewPager newsPage;
    private TabLayout newsTabLayout;

    //ProgressDialog progress;

    //required function to ask for proper read/write permissions on later Android versions
    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public static ActivityMonitorService getmSensorService() {
        return mSensorService;
    }

    public static void setmSensorService(ActivityMonitorService sensorService) {
        mSensorService = sensorService;
    }

    public static Intent getmServiceIntent(){
        return mServiceIntent;
    }

    public static void setmServiceIntent(Intent serviceIntent){
        mServiceIntent = serviceIntent;
    }

    /**
     * function checks if the sensor service is running or not
     * @param serviceClass
     * @return
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.d(TAG,">>>>[Actifit]isMyServiceRunning?" + true+"");
                return true;
            }
        }
        Log.d(TAG,">>>>[Actifit]isMyServiceRunning?" + false+"");
        return false;
    }

    String mCurrentPhotoPath;
    //handles creating the snapped image file
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                getApplicationContext().getFilesDir()
                //storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //security function to detect emulators
    public static boolean isEmulator() {
        return Build.FINGERPRINT.contains("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic")
                && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT)
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.HARDWARE.contains("andy");
    }

    private static final int VALID = 0;

    private static final int INVALID = 1;

    public int checkAppSignature(Context context) {
        final String SIGNATURE = getString(R.string.sign_key);

        try {

            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_SIGNATURES);

            for (Signature signature : packageInfo.signatures) {

                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                final String currentSignature = Base64.encodeToString(md.digest(), Base64.DEFAULT);

                //compare signatures

                if (SIGNATURE.equals(currentSignature)){
                    return VALID;
                }

            }
        } catch (Exception e) {
            //assumes an issue in checking signature., but we let the caller decide on what to do.
            return VALID;
        }

        return INVALID;

    }

    //function handles killing the app
    private void killActifit(final String reason) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //display notification to user
                Toast toast = Toast.makeText(getCtx(), reason,
                        Toast.LENGTH_LONG);

            /*View view = toast.getView();

            TextView text = view.findViewById(android.R.id.message);

            try {
                //Gets the actual oval background of the Toast then sets the colour filter
                view.getBackground().setColorFilter(getResources().getColor(R.color.actifitRed), PorterDuff.Mode.SRC_IN);
            }catch(Exception e){
                e.printStackTrace();
            }

            text.setTextColor(Color.WHITE);*/

                toast.show();
                finish();
            /*System.exit(0);
            //kill gracefully after waiting for toast
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {



                }
            }, 3000);
            */

                //((MainActivity)getCtx()).finish();
            }
        });
    }


    @TargetApi(23)
    protected void askPermissions(String[] permissions) {
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    //function handles checking if the SIM card is available
    public boolean isSimAvailable(){
        //standard case covering most phones
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT){
            return true;
        }
        //if we could not identify proper SIM (mostly due to multi-SIM), send an alert to user to fix his status
        return false;
    }

    /*
    public void crashMe(View v) {
        //throw new NullPointerException();
        //killActifit(getString(R.string.no_valid_sim));
        Crashlytics.getInstance().crash();

        //new syntax:
        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
        crashlytics.someAction();
    }*/

    // slide the view from below itself to the current position
    public void slideRight(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                view.getWidth(),                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideLeft(View view){
        view.setVisibility(View.GONE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                view.getWidth(),                 // toXDelta
                0,                 // fromYDelta
                0); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);

    }

    /*handles auto-revolving news tab at the top*/
    public class Slide_timer extends TimerTask {
        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (newsPage.getCurrentItem()< listItems.size()-1) {
                        newsPage.setCurrentItem(newsPage.getCurrentItem()+1);
                    }
                    else
                        newsPage.setCurrentItem(0);
                }
            });
        }
    }

    //handles displaying any available surveys
    private void loadSurvey(RequestQueue queue){
        String newsArticlesUrl = Utils.apiUrl(this)+getString(R.string.surveys);

        //also set and popup any mainAnnounce news


        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET,
                newsArticlesUrl, null, listArray -> {
            Survey_Entry_Class activSurvey = null;
            try {
                if (listArray != null && listArray.length() > 0) {
                    //grab first
                    Survey_Entry_Class surv = new Survey_Entry_Class(listArray.getJSONObject(0));
                    if (surv.isIs_survey_active()){
                        activSurvey = surv;
                    }
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }

            if (activSurvey !=null) {

                //check if user voted on survey already, if not show it
                Survey_Entry_Class finalActivSurvey = activSurvey;
                String voteStatusUrl = Utils.apiUrl(this)+getString(R.string.user_voted_survey).replace("_USER_", MainActivity.username)
                        .replace("_ID_", activSurvey.getId());
                JsonObjectRequest voteReq = new JsonObjectRequest
                        (Request.Method.GET, voteStatusUrl, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.has("voted") && !response.getBoolean("voted")){

                                        //show mainAnnounce if there exists one
                                        SurveyFragment survDialog = new SurveyFragment(ctx, finalActivSurvey, LoginActivity.accessToken);
                                        survDialog.show(getSupportFragmentManager(), "survey_announce");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //hide dialog
                                //error.printStackTrace();
                                Log.e(MainActivity.TAG, "error fetching vote status");
                            }
                        });

                // Add balance request to be processed
                queue.add(voteReq);

            }

        }, error -> {

        });

        queue.add(req);
    }

    //handles initiating and filling newsslider data
    //SLIDER SETUP INFO: //https://www.section.io/engineering-education/how-to-create-an-automatic-slider-in-android-studio/
    private void loadNewsSlider(RequestQueue queue){

        newsPage = findViewById(R.id.news_pager) ;

        newsTabLayout = findViewById(R.id.news_tablayout);

        listItems = new ArrayList<>() ;

        String newsArticlesUrl = Utils.apiUrl(this)+getString(R.string.news_articles);

        //also set and popup any mainAnnounce news


        JsonArrayRequest newsArticlesReq = new JsonArrayRequest(Request.Method.GET,
                newsArticlesUrl, null, listArray -> {
            //hide dialog
            //progress.hide();
            Slider_Items_Model_Class mainAnnounce = null;
            // Handle the result
            try {
                if (listArray!=null && listArray.length()>0){
                    for(int i=0;i<listArray.length();i++){
                        Slider_Items_Model_Class entry = new Slider_Items_Model_Class(listArray.getJSONObject(i));
                        listItems.add(entry);
                        if (entry.isMain_announce()){
                            mainAnnounce = entry;
                        }
                    }

                    Slider_items_Pager_Adapter itemsPager_adapter = new Slider_items_Pager_Adapter(this, listItems);
                    newsPage.setAdapter(itemsPager_adapter);

                    newsTabLayout.setupWithViewPager(newsPage,true);

                    // The_slide_timer
                    java.util.Timer timer = new java.util.Timer();
                    timer.scheduleAtFixedRate(new Slide_timer(),2000,3000);
                    newsTabLayout.setupWithViewPager(newsPage,true);

                    //show popup with mainAnnounce, with pseudo-ranom display every 5 times
                    SharedPreferences sharedPreferences = getSharedPreferences("actifitSets",MODE_PRIVATE);
                    int announceViews = (sharedPreferences.getInt(getString(R.string.main_announce_view),0));
                    announceViews += 1;
                    if (announceViews > 4) announceViews = 1;

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(getString(R.string.main_announce_view), announceViews);
                    editor.apply();

                    if (mainAnnounce !=null && announceViews <=1) {
                        //show mainAnnounce if there exists one
                        MainAnnounceFragment mainAnnounceDialog = new MainAnnounceFragment(ctx, mainAnnounce);
                        mainAnnounceDialog.show(getSupportFragmentManager(), "main_announce");
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }, error -> {
        });

        queue.add(newsArticlesReq);

        //test data for slider
        /*

        //listItems.add(new Slider_Items_Model_Class(R.drawable.default_pic,"Slider 1 Title"));
        //listItems.add(new Slider_Items_Model_Class(R.drawable.default_pic,"Slider 2 Title"));
        //listItems.add(new Slider_Items_Model_Class(R.drawable.default_pic,"Slider 3 Title"));
        listItems.add(new Slider_Items_Model_Class("https://actifit.io/img/actifit_hive_fest_4th_anniversary.png", "New Event", "https://actifit.io"));
        listItems.add(new Slider_Items_Model_Class("https://actifit.io/img/actifit_hive_fest_4th_anniversary.png", "Eventto", "https://actifit.io"));
        listItems.add(new Slider_Items_Model_Class("https://actifit.io/img/actifit_hive_fest_4th_anniversary.png", "Uno Event", "https://actifit.io"));
         */

        /***********************/

    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     */
    private void sendRegistrationToServer() {
        if (!username.equals("")) {
            String urlStr = Utils.apiUrl(this)+ getString(R.string.register_user_token_notifications);
            Log.d(MainActivity.TAG, "sendRegistrationToServer - urlStr:" + urlStr);
            ArrayList<String[]> headers = new ArrayList<>();
            headers.add(new String[]{"Content-Type", "application/json"});
            HttpResultHelper httpResult = new HttpResultHelper();

            final JSONObject data = new JSONObject();
            try {
                data.put("token", commToken);
                data.put("user", username);
                data.put("app", "Android");

                String inputLine;
                String result = "";
                httpResult = httpResult.httpPost(urlStr, null, null, data.toString(), headers, 20000);
                BufferedReader in = new BufferedReader(new InputStreamReader(httpResult.getResponse()));
                while ((inputLine = in.readLine()) != null) {
                    result += inputLine;
                }

                Log.d(MainActivity.TAG, ">>>test:" + result);
            } catch (JSONException | IOException e) {
                //e.printStackTrace();
                Log.e(MainActivity.TAG, "error sending registration data");
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //if (DEVELOPER_MODE) {
            /*StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectAll()//.detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
//                    .penaltyDeath()
                    .build());*/
        // }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //trying out cookie handler
        //CookieManager cookieManager = new CookieManager(new PersistentCookieStore(getApplicationContext()), CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        //CookieHandler.setDefault(cookieManager);
        CookieManager manager = new CookieManager();
        CookieHandler.setDefault( manager  );

        //short rotate animation
        rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(2000);
        rotate.setInterpolator(new LinearInterpolator());

        logoutLink = findViewById(R.id.logout_action);
        loginLink = findViewById(R.id.login_action);
        signupLink = findViewById(R.id.signup_action);
        loginContainer = findViewById(R.id.login_container);
        accountRCValue = findViewById(R.id.account_rc);
        topIconsContainer = findViewById(R.id.top_icons_container);
        newbieLink = findViewById(R.id.verify_newbie);
        notifCount = findViewById(R.id.notif_count);
        chatNotifCount = findViewById(R.id.chat_notif_count);

        //accountRCContainer = findViewById(R.id.rc_container);

        votingStatusText = findViewById(R.id.voting_status_text);
        votingStatusContainer = findViewById(R.id.voting_status_container);

        fullChartButton = findViewById(R.id.daily_chart_btn);
        dayChartButton = findViewById(R.id.hourly_chart_btn);

        //allow opening signup link
        signupLink.setMovementMethod(LinkMovementMethod.getInstance());
        signupLink.setPaintFlags(signupLink.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));



        this.isActivityVisible = true;



        /*if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d("MainActivity: ", "Key: " + key + " Value: " + value);
            }
        }*/

        //check if user had unsubscribed from notifications
        SharedPreferences sharedPreferences = getSharedPreferences("actifitSets",MODE_PRIVATE);
        Boolean currentNotifStatus = (sharedPreferences.getBoolean(getString(R.string.notification_status),true));

        //if not set as subscribed by default
        //FirebaseApp.initializeApp(this);
        if (currentNotifStatus) {

            FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.actif_def_not_topic));
        }
        /*****   Script below for fetching new app communication token *******/
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        commToken = task.getResult().getToken();

                        // Log and toast
                        //String msg = getString(R.string.msg_, token);
                        Log.d(TAG, commToken);


                        Thread thread = new Thread(new Runnable(){
                            @Override
                            public void run() {
                                try {
                                    //send out server notification registration with username and token
                                    sendRegistrationToServer();
                                } catch (Exception e) {
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        });
                        thread.start();


                        //Log.d(TAG, msg);
                        //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        //notify user of app restart with a Toast
        /*if (getIntent().getBooleanExtra("crash", false)) {
            Toast toast = Toast.makeText(this,  getString(R.string.actifit_crash_restarted), Toast.LENGTH_SHORT);

            View view = toast.getView();

            TextView text = view.findViewById(android.R.id.message);

            try {
                //Gets the actual oval background of the Toast then sets the colour filter
                view.getBackground().setColorFilter(getResources().getColor(R.color.actifitRed), PorterDuff.Mode.SRC_IN);
            }catch(Exception e){
                e.printStackTrace();
            }

            text.setTextColor(Color.WHITE);

            toast.show();

        }*/

        //for language/locale management
        resetTitles();

        //enforce test crash
        //Crashlytics.getInstance().crash();

        ctx = this;

        RequestQueue queue = Volley.newRequestQueue(this);

        loadNewsSlider(queue);

        loadSurvey(queue);

        //grab pointers to specific elements/buttons to be able to capture events and take action
        //stepDisplay = findViewById(R.id.step_display);
        thirdPartyTracking = findViewById(R.id.third_party_active);


        FontTextView BtnLeaderboard = findViewById(R.id.btn_view_leaderboard);
        FontTextView BtnViewHistory = findViewById(R.id.btn_view_history);
        FontTextView BtnWallet = findViewById(R.id.btn_view_wallet);
        FontTextView BtnViewNotifications = findViewById(R.id.btn_view_notifications);
        LinearLayout BtnWalletAltContainer = findViewById(R.id.wallet_alt_container);

        //FontTextView BtnSnapActiPic = findViewById(R.id.btn_snap_picture);
        TextView BtnVideo = findViewById(R.id.btn_video);

        BtnSettings = findViewById(R.id.btn_settings);
        TextView BtnMarket = findViewById(R.id.btn_view_market);
        TextView BtnSocials = findViewById(R.id.btn_socials);
        TextView BtnPosts = findViewById(R.id.btn_view_social);
        TextView BtnHelp = findViewById(R.id.btn_help);
        TextView BtnChat = findViewById(R.id.btn_chat);
        BtnWaves = findViewById(R.id.btn_waves);
        FontTextView BtnSwitchSettings = findViewById(R.id.switchSettings);



        BtnPostSteemit = findViewById(R.id.btn_post_steemit);
        Button BtnBuyAFIT = findViewById(R.id.btn_buy_afit);
        Button BtnReferFriend = findViewById(R.id.refer_friend_button);

        scaler = new ScaleAnimation(1f, 0.95f, 1f,0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaler.setDuration(400);
        scaler.setRepeatMode(Animation.REVERSE);
        scaler.setRepeatCount(Animation.INFINITE);

        displayActivityChartFitbit(0,true);
        TextView sync = findViewById(R.id.sync);
        sync.startAnimation(scaler);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sync.setTooltipText(getString(R.string.sync_steps));
        }
        sync.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                NxFitbitHelper.sendUserToAuthorisation(ctx);
            }
        });

        Uri returnUrl = getIntent().getData();
        if (returnUrl != null){
            try {
                NxFitbitHelper fitbit = new NxFitbitHelper(ctx);
                fitbit.requestAccessTokenFromIntent(returnUrl);
                try {
                    JSONObject responseProfile = fitbit.getUserProfile();
                    responseProfile.getJSONObject("user");
                } catch (JSONException | InterruptedException | ExecutionException |
                         IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    String soughtInfo = "steps";
                    String targetDate = "today";
                    JSONObject stepActivityList = fitbit.getActivityByDate(soughtInfo, targetDate);
                    JSONArray stepActivityArray = stepActivityList.getJSONArray("activities-tracker-" + soughtInfo);
                    Log.d(MainActivity.TAG, "From JSON distance:" + stepActivityArray.length());
                    int trackedActivityCount = 0;
                    if (stepActivityArray.length() > 0) {
                        Log.d(MainActivity.TAG, "we found matching records");
                        //loop through records adding up recorded steps
                        for (int i = 0; i < stepActivityArray.length(); i++) {
                            trackedActivityCount += parseInt(stepActivityArray.getJSONObject(i).getString("value"));
                        }

                        displayActivityChartFitbit(trackedActivityCount,true);
                        Calendar mCalendar = Calendar.getInstance();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("fitbitLastMainSyncDate",
                                new SimpleDateFormat("dd/MM/yyyy HH:mm",
                                        Locale.getDefault()).format(mCalendar.getTime())
                        );
                        editor.apply();
                    } else {
                        Log.d(MainActivity.TAG, "No auto-tracked activity found for today");
                    }

                } catch (JSONException | InterruptedException | ExecutionException | IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            catch(Exception myExc){
                myExc.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.error_fitbit_fecth), Toast.LENGTH_SHORT).show();
            }


        }

        ImageView fitbitLogo = findViewById(R.id.fitbit_logo);
        fitbitLogo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String lastMainSyncDate = sharedPreferences.getString("fitbitLastMainSyncDate","");
                Toast.makeText(ctx, "Fitbit last synced on : "+lastMainSyncDate, Toast.LENGTH_LONG).show();
            }
        });

        getFitbitPieChartReset();
        boolean resetFitbit = sharedPreferences.getBoolean("resetPieChart",false);
        if(resetFitbit){
            displayActivityChartFitbit(0,true);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("resetPieChart",false);
            editor.apply();
        }


        dayChartButton.setOnClickListener(view -> {

            slideRight(dayChart);
            slideLeft(fullChart);
            dayChartButton.setVisibility(View.GONE);
            fullChartButton.setVisibility(View.VISIBLE);
            /*fullChart.animate()
                    .translationXBy(fullChart.getWidth())
                    .alpha(0.0f);

            dayChart.animate()
                    .translationXBy(dayChart.getWidth())
                    .alpha(1.0f)
                    .setListener(null);*/
            /*fullChart.animate()
                    .translationX(0)
                    .alpha(1.0f)
                    .setListener(null);*/

        });

        fullChartButton.setOnClickListener(view -> {
            slideLeft(dayChart);
            slideRight(fullChart);
            dayChartButton.setVisibility(View.VISIBLE);
            fullChartButton.setVisibility(View.GONE);
            /*dayChart.animate()
                    .translationX(dayChart.getWidth() * -1)
                    .alpha(0.0f);

            fullChart.animate()
                    .translationXBy(fullChart.getWidth() * -1)
                    .alpha(1.0f)
                    .setListener(null);
            /*fullChart.animate()
                    .translationX(0)
                    .alpha(1.0f)
                    .setListener(null);*/

        });

        //preload tutorial vid url
        Handler uiAltHandler = new Handler(Looper.getMainLooper());
        String vidFetchUrl = Utils.apiUrl(this)+getString(R.string.tut_vid_url);
        final String[] tutVidUrl = {""};

        // Request the rank of the user while expecting a JSON response
        JsonObjectRequest vidUrlRequest = new JsonObjectRequest
                (Request.Method.GET, vidFetchUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        uiAltHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (response.has("vidUrl")) {
                                    try {
                                        tutVidUrl[0] = response.getString("vidUrl");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Log.e(MainActivity.TAG, "Load image error");

                            }
                        });

        //queue = Volley.newRequestQueue(this);

        queue.add(vidUrlRequest);

        BtnWaves.setOnClickListener(view -> {
            WavesDialogFragment wavesDialog = new WavesDialogFragment(getCtx());
            wavesDialog.show(getSupportFragmentManager(),"waves_dialog");

        });

        BtnChat.setOnClickListener(view -> {

            //store that user has opened chat to avoid showing notifications predating this event

            storeNotifDate(new Date(), "");
            storeNotifCount(lastChatCount);

            //open chat
            ChatDialogFragment chatDialog = ChatDialogFragment.newInstance(getCtx());
            chatDialog.show(getSupportFragmentManager(), "chat_dialog");
        });

        BtnHelp.setOnClickListener(view -> {

            VideoDialogFragment dialogFragment = VideoDialogFragment.newInstance(tutVidUrl[0]);
            dialogFragment.show(getSupportFragmentManager(), "video_dialog");

            /*AlertDialog.Builder helpDialogBuilder = new AlertDialog.Builder(ctx);
            View helpLayout = getLayoutInflater().inflate(R.layout.help_actifit, null);

            VideoView videoView = helpLayout.findViewById(R.id.videoView);
            videoView.setVideoURI(Uri.parse(tutVidUrl[0]));
            videoView.start();


            AlertDialog pointer = helpDialogBuilder.setView(helpLayout)
                    .setTitle(getString(R.string.socials_note))
                    .setIcon(getResources().getDrawable(R.drawable.actifit_logo))
                    .setPositiveButton(getString(R.string.close_button),null).create();

            helpDialogBuilder.show();*/

        });

        BtnSocials.setOnClickListener(view -> {
            AlertDialog.Builder socialDialogBuilder = new AlertDialog.Builder(ctx);
            View socialLayout = getLayoutInflater().inflate(R.layout.social_actifit, null);
            socialLayout.findViewById(R.id.facebook_actifit).setOnClickListener(view1 -> {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.facebook_actifit))));
                }catch(Exception e){
                    Log.e(MainActivity.TAG, "error opening social media");
                }
            });
            socialLayout.findViewById(R.id.twitter_actifit).setOnClickListener(view1 -> {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.twitter_actifit))));
                }catch(Exception e){
                    Log.e(MainActivity.TAG, "error opening social media");
                }
            });
            socialLayout.findViewById(R.id.telegram_actifit).setOnClickListener(view1 -> {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.telegram_actifit))));
                }catch(Exception e){
                    Log.e(MainActivity.TAG, "error opening social media");
                }
            });
            socialLayout.findViewById(R.id.discord_actifit).setOnClickListener(view1 -> {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.discord_actifit))));
                }catch(Exception e){
                    Log.e(MainActivity.TAG, "error opening social media");
                }
            });
            socialLayout.findViewById(R.id.instagram_actifit).setOnClickListener(view1 -> {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.instagram_actifit))));
                }catch(Exception e){
                    Log.e(MainActivity.TAG, "error opening social media");
                }
            });
            socialLayout.findViewById(R.id.linkedin_actifit).setOnClickListener(view1 -> {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.linkedin_actifit))));
                }catch(Exception e){
                    Log.e(MainActivity.TAG, "error opening social media");
                }
            });
            socialLayout.findViewById(R.id.youtube_actifit).setOnClickListener(view1 -> {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_actifit))));
                }catch(Exception e){
                    Log.e(MainActivity.TAG, "error opening social media");
                }
            });

            //set style for dialog
            //socialDialogBuilder. getWindow()


            AlertDialog pointer = socialDialogBuilder.setView(socialLayout)
                    .setTitle(getString(R.string.socials_note))
                    .setIcon(getResources().getDrawable(R.drawable.actifit_logo))
                    .setPositiveButton(getString(R.string.close_button),null).create();

            socialDialogBuilder.show();
            /*
            pointer.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            pointer.getWindow().getDecorView().setBackground(getDrawable(R.drawable.dialog_shape));
            pointer.show();
            */


        });

        BtnReferFriend.setOnClickListener(view -> {

            AlertDialog.Builder referDialogBuilder = new AlertDialog.Builder(ctx);
            referLayout = getLayoutInflater().inflate(R.layout.refer_friend, null);
            EditText refLink = referLayout.findViewById(R.id.referralLink);
            refLink.setText(getString(R.string.referrals_format)+username);

            TextView referralDescription = referLayout.findViewById(R.id.referral_description);
            referralDescription.setText(Html.fromHtml(getString(R.string.referrals_details)));

            TextView successfulReferral = referLayout.findViewById(R.id.success_referrals);

            if (userReferrals!=null && userReferrals.length() > 0) {


                successfulReferral.setTextColor(ctx.getResources().getColor(R.color.actifitDarkGreen));
                successfulReferral.setText(Html.fromHtml(checkMark + userReferrals.length()));
            }

            TextView copyButton = referLayout.findViewById(R.id.copyButton);
            copyButton.setOnClickListener(view12 -> {
                copyButton.startAnimation(rotate);
                copyText(refLink);
            });

            TextView shareButton = referLayout.findViewById(R.id.shareButton);
            shareButton.setOnClickListener(view1 -> {
                //copyText(refLink);
                shareButton.startAnimation(rotate);
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareSubject = getString(R.string.referral_title);
                String shareBody = getString(R.string.referral_description);
                shareBody += " "+getString(R.string.referral_join_link).replace("_URL_",refLink.getText().toString());

                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                MainActivity.this.startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));

            });

            //also load data relating to free signups
            loadAndUpdateSignupData();

            //display referrals count
            //Html.fromHtml(checkMark +userReferrals.length());

            AlertDialog pointer = referDialogBuilder.setView(referLayout)
                    .setTitle(getString(R.string.referrals_note))
                    .setIcon(getResources().getDrawable(R.drawable.actifit_logo))
                    .setPositiveButton(getString(R.string.close_button),null).create();

            referDialogBuilder.show();
            /*
            pointer.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            pointer.getWindow().getDecorView().setBackground(getDrawable(R.drawable.dialog_shape));
            pointer.show();

             */

        });


        LinearLayout EarningsPanel = findViewById(R.id.earnings_panel);

        minTokenCount = Double.parseDouble(getString(R.string.min_afit_reward_balance));

        Log.d(TAG,">>>>[Actifit] Getting jiggy with it");


        EarningsPanel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                //display alert dialog about pending rewards
                earningsDialogBuilder = new AlertDialog.Builder(ctx);


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //cancel
                                break;
                        }
                    }
                };

                String msg = grabEarningsPanelNote();


                earningsDialog = earningsDialogBuilder.setMessage(Html.fromHtml(msg))
                        .setTitle(getString(R.string.earnings_pane_title))
                        .setIcon(getResources().getDrawable(R.drawable.actifit_logo))
                        .setPositiveButton(getString(R.string.close_button), dialogClickListener).create();

                earningsDialogBuilder.show();
                /*
                earningsDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                earningsDialog.getWindow().getDecorView().setBackground(getDrawable(R.drawable.dialog_shape));
                earningsDialog.show();

                 */


            }

        });

        //introduce the functionality under a separate thread to avoid ANRs

        PrepareGround prepareApp = new PrepareGround();
        prepareApp.execute();



        // Check if notifications are enabled
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        boolean notificationsEnabled = notificationManagerCompat.areNotificationsEnabled();

// If notifications are disabled, request the permission
        if (!notificationsEnabled) {
            //requestNotificationPermissionLauncher.launch(POST_NOTIFICATIONS_PERMISSION);
            // Initialize the permission result launcher

            final String POST_NOTIFICATIONS_PERMISSION = "android.permission.POST_NOTIFICATIONS";
            final int REQUEST_CODE_NOTIFICATION_PERMISSION = 1;

            // Request the notification permission
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{POST_NOTIFICATIONS_PERMISSION},
                    REQUEST_CODE_NOTIFICATION_PERMISSION
            );

            /*PermissionResultLauncher<String> requestNotificationPermissionLauncher = registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    result -> {
                        // Handle the permission grant or denial
                        if (result.isGranted()) {
                            // The user granted the permission, so you can now post notifications
                        } else {
                            // The user denied the permission, so you cannot post notifications
                        }
                    }
            );*/
        }

        //prepare ads
        //prepareAds();

        // Create the "show" button, which shows a rewarded video if one is loaded.
        dailyRewardButton = findViewById(R.id.daily_reward);


        //showVideoButton.setVisibility(View.INVISIBLE);
        //dailyRewardButton.setText(Html.fromHtml(getString(R.string.daily_reward)));
        dailyRewardButton.setOnClickListener(view -> {

            if (username == null || username.length() <1) {
                Toast.makeText(ctx, getString(R.string.username_missing), Toast.LENGTH_LONG).show();
                return;
            }

            //show popup for rewards
            AlertDialog.Builder rewardsDialogBuilder = new AlertDialog.Builder(ctx);
            final View rewardsLayout = getLayoutInflater().inflate(R.layout.reward_popup, null);

            giftLoader = rewardsLayout.findViewById(R.id.daily_reward_icon);


            freeRewardButton = rewardsLayout.findViewById(R.id.daily_free_reward);
            fivekRewardButton = rewardsLayout.findViewById(R.id.daily_5k_reward);
            sevenkRewardButton = rewardsLayout.findViewById(R.id.daily_7k_reward);
            tenkRewardButton = rewardsLayout.findViewById(R.id.daily_10k_reward);

            moveTotweets = rewardsLayout.findViewById(R.id.displayTweets);

            moveTotweets.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogLayout = inflater.inflate(R.layout.activity_tweet_actions, null);
                    builder.setView(dialogLayout);

                    // Optionally, set up the ImageView if you want to manipulate it programmatically
                    // You can set an image programmatically if needed
                    // imageView.setImageResource(R.drawable.your_image);

                    ImageView like = dialogLayout.findViewById(R.id.like);
                    like.setOnClickListener(new OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            String url = "https://x.com/intent/like?tweet_id=1797370316022272474";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }
                    });

                    ImageView retweet = dialogLayout.findViewById(R.id.retweet);
                    retweet.setOnClickListener(new OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            String url = "https://x.com/intent/retweet?tweet_id=1797370316022272474";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }
                    });

                    ImageView follow = dialogLayout.findViewById(R.id.follow);
                    follow.setOnClickListener(new OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            String url = "https://x.com/intent/follow?screen_name=Actifit_fitness";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }
                    });

                    ImageView reply = dialogLayout.findViewById(R.id.reply);
                    reply.setOnClickListener(new OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            String url = "https://x.com/intent/tweet?in_reply_to=1797370316022272474";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }
                    });


                    builder.setPositiveButton("Close", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            freeRewardButton.setOnClickListener(innerView -> showRewardedVideo(innerView, 1));
            fivekRewardButton.setOnClickListener(innerView -> showRewardedVideo(innerView, 2));
            sevenkRewardButton.setOnClickListener(innerView -> showRewardedVideo(innerView, 3));
            tenkRewardButton.setOnClickListener(innerView -> showRewardedVideo(innerView, 4));
            //fetch existing reward status

            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            int curDate = parseInt(dateFormat.format(date));

            //Toast.makeText(getApplicationContext(),"date"+curDate, Toast.LENGTH_LONG).show();

            String strDate = sharedPreferences.getString(getString(R.string.daily_free_reward), "");
            //reinitialize rewards claimed status
            dailyRewardClaimed = false;
            fivekRewardClaimed = false;
            sevenkRewardClaimed = false;
            tenkRewardClaimed = false;

            if (!strDate.equals("")){
                if (curDate<= parseInt(strDate)){
                    //user has already received reward
                    dailyRewardClaimed = true;
                }
            }

            strDate = sharedPreferences.getString(getString(R.string.daily_5k_reward), "");
            if (!strDate.equals("")){
                if (curDate<= parseInt(strDate)){
                    //user has already received reward
                    fivekRewardClaimed = true;
                }
            }

            strDate = sharedPreferences.getString(getString(R.string.daily_7k_reward), "");
            if (!strDate.equals("")){
                if (curDate<= parseInt(strDate)){
                    //user has already received reward
                    sevenkRewardClaimed = true;
                }
            }

            strDate = sharedPreferences.getString(getString(R.string.daily_10k_reward), "");
            if (!strDate.equals("")){
                if (curDate<= parseInt(strDate)){
                    //user has already received reward
                    tenkRewardClaimed = true;
                }
            }

            if (!dailyRewardClaimed){
                freeRewardButton.setAnimation(scaler);
            }else{
                Spanned text = Html.fromHtml(getString(R.string.reward_claimed)+ sharedPreferences.getString("freerewardedValue","")+" AFIT "+checkMark);
                freeRewardButton.setText(text);
            }

            int curStepCount = mStepsDBHelper.fetchTodayStepCount();


            //animate reward button
            if (!fivekRewardClaimed && curStepCount >= 5000){
                fivekRewardButton.setAnimation(scaler);
            }else if (fivekRewardClaimed){
                Spanned text = Html.fromHtml(getString(R.string.reward_claimed)+sharedPreferences.getString("5krewardedValue","")+" AFIT "+checkMark);
                fivekRewardButton.setText(text);
            }

            if (!sevenkRewardClaimed && curStepCount >= 7000){
                sevenkRewardButton.setAnimation(scaler);
            }else if (fivekRewardClaimed){
                Spanned text = Html.fromHtml(getString(R.string.reward_claimed)+sharedPreferences.getString("7krewardedValue","")+" AFIT "+checkMark);
                sevenkRewardButton.setText(text);
            }

            if (!tenkRewardClaimed && curStepCount >= 10000){
                tenkRewardButton.setAnimation(scaler);
            }else if (tenkRewardClaimed){
                Spanned text = Html.fromHtml(getString(R.string.reward_claimed)+sharedPreferences.getString("10krewardedValue","")+" AFIT "+checkMark);
                tenkRewardButton.setText(text);
            }

            AlertDialog pointer = rewardsDialogBuilder.setView(rewardsLayout)
                    .setTitle(getString(R.string.rewards_note))
                    .setIcon(getResources().getDrawable(R.drawable.actifit_logo))
                    .setPositiveButton(getString(R.string.close_button),null)
                    .create();

            rewardsDialogBuilder.show();
            /*
            pointer.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            pointer.getWindow().getDecorView().setBackground(getDrawable(R.drawable.dialog_shape));
            //pointer.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);

            pointer.show();*/
        });

        dailyRewardButton.startAnimation(scaler);

        final FrameLayout picFrame = findViewById(R.id.pic_frame);
        final TextView welcomeUser = findViewById(R.id.welcome_user);
        final TextView userRankTV = findViewById(R.id.user_rank);

        //handle click on user profile
        picFrame.setOnClickListener(view -> {
            final SharedPreferences sharedPreferences1 = getSharedPreferences("actifitSets",MODE_PRIVATE);
            openUserAccount(sharedPreferences1);
        });

        //also handle click on username
        welcomeUser.setOnClickListener(view -> {
            final SharedPreferences sharedPreferences12 = getSharedPreferences("actifitSets",MODE_PRIVATE);
            openUserAccount(sharedPreferences12);
        });

        //also handle click on rank
        userRankTV.setOnClickListener(view -> {
            //final SharedPreferences sharedPreferences13 = getSharedPreferences("actifitSets",MODE_PRIVATE);
            //openUserRank(sharedPreferences13);
            String msg = getString(R.string.user_rank_description)+getString(R.string.user_rank_description_2);

            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_NEUTRAL:
                        //cancel
                        openUserRank();
                        break;
                }
            };


            //display alert dialog about pending rewards
            AlertDialog.Builder userRankDialogBuilder = new AlertDialog.Builder(ctx);

            AlertDialog pointer = userRankDialogBuilder.setMessage(Html.fromHtml(msg))
                    .setTitle(getString(R.string.user_rank_title))
                    .setIcon(getResources().getDrawable(R.drawable.actifit_logo))
                    .setPositiveButton(getString(R.string.close_button), null)
                    .setNeutralButton(getString(R.string.user_rank_web), dialogClickListener)
                    .create();
            userRankDialogBuilder.show();
            /*pointer.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            pointer.getWindow().getDecorView().setBackground(getDrawable(R.drawable.dialog_shape));
            pointer.show();
            */
        });



        //hook up our standard thread catcher to allow auto-restart after crash
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandlerRestartApp(this));

        //this is now needed for proper image upload to AWS
        getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));



        //connecting the activity to the service to receive proper updates on move count
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final int stepCount = intent.getIntExtra("move_count", 0);
                //stepDisplay.setText(getString(R.string.activity_today_string) + (stepCount < 0 ? 0 : stepCount));
                //stepDisplay.setVisibility(View.GONE);

                //if (!mStepsDBHelper.isConnected()){
//                    mStepsDBHelper.reConnect();
                //}

                //display activity count chart
                displayActivityChart(stepCount, false);
                //to avoid system overload, only update activity charts when activity is visible
                if (MainActivity.isActivityVisible) {
                    //display today's chart data
                    //displayDayChartData(false);
                    DisplayDayChartDataAsyncTask dispChartData = new DisplayDayChartDataAsyncTask(false);
                    dispChartData.execute(false);

                    //display all historical data
                    //displayChartData(false);
                    DisplayChartDataAsyncTask dispCData = new DisplayChartDataAsyncTask(false);
                    dispCData.execute(false);

                }
            }
        };

        //handle taking photos
        /*BtnSnapActiPic.setOnClickListener(view -> {

            //make sure we have a cam on device
            PackageManager pm = ctx.getPackageManager();

            //if no cam, notify and leave
            if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                Toast.makeText(getApplicationContext(),getString(R.string.device_has_no_cam), Toast.LENGTH_SHORT).show();
                return;
            }

            //ensure we have proper permissions for image upload
            if (shouldAskPermissions()) {
                String[] permissions = {
                        "android.permission.READ_EXTERNAL_STORAGE",
                        "android.permission.WRITE_EXTERNAL_STORAGE"
                };
                askPermissions(permissions);
            }

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    ex.printStackTrace();
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    try {
                        Uri photoURI = FileProvider.getUriForFile(ctx,
                                "io.actifit.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }catch (Exception myExc){
                        myExc.printStackTrace();
                    }
                }
            }
        }
        );*/

        //handle video activity
        BtnVideo.setOnClickListener( v->{
            //show video modal
            VideoUploadFragment dialog = new VideoUploadFragment(getApplicationContext(), LoginActivity.accessToken, this, false);
            //dialog.getView().setMinimumWidth(400);
            dialog.show(getSupportFragmentManager(), "video_upload_fragment");
        });

        //handle activity to move to step history screen
        BtnViewHistory.setOnClickListener(arg0 -> {

            Intent intent = new Intent(MainActivity.this, StepHistoryActivity.class);
            MainActivity.this.startActivity(intent);

        });

        //handle activity to move to post to steemit screen
        BtnPostSteemit.setOnClickListener(arg0 -> {

            if (username == null || username.length() <1){
                Toast.makeText(ctx, getString(R.string.username_missing), Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(MainActivity.this, PostSteemitActivity.class);
                MainActivity.this.startActivity(intent);
            }

        });

        //load AFIT markets
        //handles sending out API query requests
        //RequestQueue queue = Volley.newRequestQueue(this);

        String afitMarketsUrl = Utils.apiUrl(this)+getString(R.string.afit_markets);


        JsonArrayRequest afitMarketsReq = new JsonArrayRequest(Request.Method.GET,
                afitMarketsUrl, null, listArray -> {
            //hide dialog
            //progress.hide();

            // Handle the result
            try {
                afitMarkets = listArray;
                //actifitTransactions.setText("Response is: "+ response);
            }catch (Exception e) {
                //hide dialog
                //progress.hide();
                //actifitTransactionsError.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }

        }, error -> {
            //hide dialog
            //progress.hide();
            //actifitTransactionsView.setText("Unable to fetch balance");
            //actifitTransactionsError.setVisibility(View.VISIBLE);
        });

        queue.add(afitMarketsReq);

        //load daily tip
        displayDailyTip();

        //load user gadgets
        displayUserGadgets();

        //load referral count
        loadReferrals(queue);

        //load claimable signups
        loadClaimableSignupLinks(queue);

        //load signup links
        loadSignupLinks(queue);

        BtnBuyAFIT.setOnClickListener(arg0 -> {

            afitBuyDialogBuilder = new AlertDialog.Builder(ctx);

            String msg = "";
            msg += getString(R.string.afit_buy_note) + "<br />";

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_NEGATIVE:
                            //cancel
                            break;
                    }
                }
            };

            List<String> listItems = new ArrayList<String>();
            for (int i=0;i<afitMarkets.length();i++){
                try {
                    listItems.add(afitMarkets.getJSONObject(i).getString("exchange"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            CharSequence[] marketBtns = listItems.toArray(new CharSequence[listItems.size()]);


            afitBuyDialog = afitBuyDialogBuilder
                    //.setMessage(Html.fromHtml(msg))
                    .setTitle(getString(R.string.afit_buy_title))
                    .setIcon(getResources().getDrawable(R.drawable.actifit_logo))
                    .setPositiveButton(getString(R.string.close_button), dialogClickListener)
                    .setItems(marketBtns,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

                                    builder.setToolbarColor(getResources().getColor(R.color.actifitRed));

                                    //animation for showing and closing screen
                                    builder.setStartAnimations(ctx, R.anim.slide_in_right, R.anim.slide_out_left);

                                    //animation for back button clicks
                                    builder.setExitAnimations(ctx, android.R.anim.slide_in_left,
                                            android.R.anim.slide_out_right);

                                    CustomTabsIntent customTabsIntent = builder.build();

                                    try {
                                        customTabsIntent.launchUrl(ctx, Uri.parse(afitMarkets.getJSONObject(which).getString("link")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }).create();
            afitBuyDialogBuilder.show();
            /*afitBuyDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            afitBuyDialog.getWindow().getDecorView().setBackground(getDrawable(R.drawable.dialog_shape));
            afitBuyDialog.show();*/

        });

        //handle activity to move over to the Leaderboard screen
        BtnLeaderboard.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
                MainActivity.this.startActivity(intent);

            }
        });




        BtnWalletAltContainer.setOnClickListener(arg0 -> BtnWallet.performClick());

        //handle activity to move over to the Wallet screen
        BtnWallet.setOnClickListener(arg0 -> {
            if (username == null || username.length() <1){
                Toast.makeText(ctx, getString(R.string.username_missing), Toast.LENGTH_LONG).show();
            }else {

                Intent intent = new Intent(MainActivity.this, WalletActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        //BtnViewNotifications.setOnClickListener(arg0 -> BtnWallet.performClick());
        BtnViewNotifications.setOnClickListener(arg0 -> {
            if (username == null || username.length() <1){
                Toast.makeText(ctx, getString(R.string.username_missing), Toast.LENGTH_LONG).show();
            }else {

                Intent intent = new Intent(MainActivity.this, NotificationsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        //handle activity to move over to the Settings screen
        BtnSettings.setOnClickListener(arg0 -> {

            if (username == null || username.length() <1){
                Toast.makeText(ctx, getString(R.string.username_missing), Toast.LENGTH_LONG).show();
            }else {
                //sensorManager.unregisterListener(MainActivity.this);

                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                //overridePendingTransition(0,0);
                MainActivity.this.startActivity(intent);
                //overridePendingTransition(0,0);
            }
        });



        BtnMarket.setOnClickListener(arg0 -> {

            /*if (username == null || username.length() <1){
                Toast.makeText(ctx, getString(R.string.username_missing), Toast.LENGTH_LONG).show();
            }else {*/

            //sensorManager.unregisterListener(MainActivity.this);
            Intent intent = new Intent(MainActivity.this, MarketActivity.class);
            MainActivity.this.startActivity(intent);
            //}
        });

        BtnPosts.setOnClickListener(arg0 -> {

            /*if (username == null || username.length() <1){
                Toast.makeText(ctx, getString(R.string.username_missing), Toast.LENGTH_LONG).show();
            }else {*/

            //sensorManager.unregisterListener(MainActivity.this);
            Intent intent = new Intent(MainActivity.this, SocialActivity.class);
            MainActivity.this.startActivity(intent);
            //}
        });


        userGadgets = findViewById(R.id.user_gadgets);

        userGadgets.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                //display alert dialog about pending rewards
                gadgetsDialogBuilder = new AlertDialog.Builder(ctx);


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_NEUTRAL:
                                BtnMarket.performClick();
                                break;
                            case DialogInterface.BUTTON_POSITIVE:
                                //cancel
                                break;
                        }
                    }
                };

                String msg = "";
                if (activeProducts == null || activeProducts.length() < 1){
                    msg += "<b>"+getString(R.string.active_gadgets_note_1) + " <br />";
                }
                msg += getString(R.string.active_gadgets_note_2) + "<br />";
                msg += getString(R.string.active_gadgets_note_3)+ "<br />";

                gadgetsDialog = gadgetsDialogBuilder.setMessage(Html.fromHtml(msg))
                        .setTitle(getString(R.string.gadgets_earning_title))
                        .setIcon(getResources().getDrawable(R.drawable.actifit_logo))
                        .setNeutralButton(getString(R.string.head_market), dialogClickListener)
                        .setPositiveButton(getString(R.string.close_button), dialogClickListener).create();
                gadgetsDialogBuilder.show();
                /*gadgetsDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                gadgetsDialog.getWindow().getDecorView().setBackground(getDrawable(R.drawable.dialog_shape));
                gadgetsDialog.show();
                */
            }
        });

        FontTextView BtnSwitchSettings2 = findViewById(R.id.switchSettings2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        BtnSwitchSettings2.setTooltipText("Switch to Phone sensors mode");
        }
        BtnSwitchSettings2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                /*if (username == null || username.length() <1){
                    Toast.makeText(ctx, getString(R.string.username_missing), Toast.LENGTH_LONG).show();
                }else {

                    //sensorManager.unregisterListener(MainActivity.this);
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    MainActivity.this.startActivity(intent);
                }*/
                if (username == null || username.length() <1) {
                    Toast.makeText(ctx, getString(R.string.username_missing), Toast.LENGTH_LONG).show();
                }
                else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if(sharedPreferences.getString("dataTrackingSystem", "").equals(getString(R.string.device_tracking_ntt))){
                        hideCharts();
                        editor.putString("dataTrackingSystem", getString(R.string.fitbit_tracking_ntt));
                    }
                    else{
                        dayChart.setVisibility(View.VISIBLE);
                        btnPieChart.setVisibility(View.VISIBLE);
                        fullChartButton.setVisibility(View.VISIBLE);
                        thirdPartyTracking.setVisibility(View.GONE);
                        LinearLayout barCharts = findViewById(R.id.bar_chart_container);
                        barCharts.setVisibility(View.VISIBLE);
                        int steps = mStepsDBHelper.fetchTodayStepCount();
                        displayActivityChart(steps, true);
                        editor.putString("dataTrackingSystem", getString(R.string.device_tracking_ntt));
                    }
                    editor.apply();
                }
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        BtnSwitchSettings.setTooltipText("Switch to Fitbit mode");
        }
        BtnSwitchSettings.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                /*if (username == null || username.length() <1){
                    Toast.makeText(ctx, getString(R.string.username_missing), Toast.LENGTH_LONG).show();
                }else {

                    //sensorManager.unregisterListener(MainActivity.this);
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    MainActivity.this.startActivity(intent);
                }*/
                if (username == null || username.length() <1) {
                    Toast.makeText(ctx, getString(R.string.username_missing), Toast.LENGTH_LONG).show();
                }
                else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if(sharedPreferences.getString("dataTrackingSystem", "").equals(getString(R.string.device_tracking_ntt))){
                        hideCharts();
                        editor.putString("dataTrackingSystem", getString(R.string.fitbit_tracking_ntt));
                    }
                    else{
                        dayChart.setVisibility(View.VISIBLE);
                        btnPieChart.setVisibility(View.VISIBLE);
                        fullChartButton.setVisibility(View.VISIBLE);
                        thirdPartyTracking.setVisibility(View.GONE);
                        LinearLayout barCharts = findViewById(R.id.bar_chart_container);
                        barCharts.setVisibility(View.VISIBLE);
                        int steps = mStepsDBHelper.fetchTodayStepCount();
                        displayActivityChart(steps, true);
                        editor.putString("dataTrackingSystem", getString(R.string.device_tracking_ntt));
                    }
                    editor.apply();
                }
            }
        });

        checkBatteryOptimization(false);


        //redirect user to url of notification
        //script to receive notifications in background mode
        if (getIntent().getExtras() != null) {
            // Call your NotificationActivity here..
            if (getIntent().getExtras().containsKey("url")) {
                String targetUrl = getIntent().getExtras().get("url").toString();
                /*Intent intent = new Intent(this, MainActivity.class);
                intent.setData(Uri.parse(targetUrl));
                startActivity(intent);*/
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

                builder.setToolbarColor(getResources().getColor(R.color.actifitRed));

                //animation for showing and closing fitbit authorization screen
                builder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left);

                //animation for back button clicks
                builder.setExitAnimations(this, android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);

                CustomTabsIntent customTabsIntent = builder.build();

                customTabsIntent.launchUrl(this, Uri.parse(targetUrl));

                return;
            }
        }


    }

    private void loadAndUpdateSignupData(){
        Button claimSignups = referLayout.findViewById(R.id.claimFreeSignups);
        if (userCanClaimSignupLinks){
            claimSignups.setVisibility(View.VISIBLE);
        }else{
            claimSignups.setVisibility(View.GONE);
        }

        claimSignups.setOnClickListener(v->{
            RequestQueue queue = Volley.newRequestQueue(this);
            claimFreeSignupLinks(queue);
        });

        //LinearLayout linksView = referLayout.findViewById(R.id.signupLinksContainer);
        TextView linksHeader = referLayout.findViewById(R.id.available_free_signups_notice);

        //loop through signup links and display them
        if (freeSignupLinks !=null && freeSignupLinks.length() > 0) {
            linksHeader.setVisibility(View.VISIBLE);
            //append all links
            for (int i=0;i<freeSignupLinks.length();i++) {
                try {
                    JSONObject entry = freeSignupLinks.getJSONObject(i);
                    View convertView = LayoutInflater.from(ctx).inflate(R.layout.signup_link, (ViewGroup) referLayout, false);
                    //set link content
                    TextView linkTxt = convertView.findViewById(R.id.signupLink);
                    String fullLink = Utils.apiUrl(this)+getString(R.string.signup_link_format)
                            .replace("PROMO", entry.getString("code"))
                            .replace("REFERRER", username);
                    linkTxt.setText(fullLink);
                    //linksView.addView(convertView);

                    //add copy link functionality
                    TextView copyBtn = convertView.findViewById(R.id.copyBtn);
                    copyBtn.setOnClickListener(view12 -> {
                        copyBtn.startAnimation(rotate);

                        //copy code
                        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
                        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", fullLink);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(MainActivity.this, getString(R.string.copy_success), Toast.LENGTH_SHORT)
                                .show();

                    });

                    //add share button functionality
                    TextView shareBtn = convertView.findViewById(R.id.shareBtn);
                    shareBtn.setOnClickListener(view1 -> {
                        //copyText(refLink);
                        shareBtn.startAnimation(rotate);
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareSubject = getString(R.string.referral_title);
                        String shareBody = getString(R.string.referral_description);
                        shareBody += " "+getString(R.string.referral_join_link).replace("_URL_",fullLink);

                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                        MainActivity.this.startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));

                    });
                }catch(JSONException exc){
                    exc.printStackTrace();
                }
            }
        }else{
            linksHeader.setVisibility(View.GONE);
        }
    }

    private void copyText(EditText src){
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", src.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(MainActivity.this, getString(R.string.copy_success), Toast.LENGTH_SHORT)
                .show();
    }

    // for extra ad related documentation : https://developers.google.com/admob/android/rewarded
    private void prepareAds(){

        if (isMobileAdsInitializeCalled.getAndSet(true)) {

        }else {
            //initialize ads
            MobileAds.initialize(this);
        }
        /*
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        */

        loadRewardedAd();
        //loadConsentData(true);
    }

    private void loadRewardedAd(){
        if (rewardedAd == null) {
            isAdLoading = true;
            AdRequest adRequest = new AdRequest.Builder().build();

            //check if we are good to load ads based on consent data

            RewardedAd.load(this, getString(R.string.admob_ad_unit_1),
                    adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error.
                            Log.d(TAG, loadAdError.toString());
                            MainActivity.this.rewardedAd = null;
                            MainActivity.this.isAdLoading = false;
                            if (getString(R.string.sec_check_signature).equals("off")) {
                                //specific error notice for debugging
                                Toast.makeText(
                                                MainActivity.this, loadAdError.getMessage(), Toast.LENGTH_SHORT)
                                        .show();

                            }else {
                                //generic error notice
                                Toast.makeText(
                                                MainActivity.this, getString(R.string.err_load_ad), Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            MainActivity.this.rewardedAd = rewardedAd;
                            //Log.d(TAG, "Ad was loaded.");
                            MainActivity.this.isAdLoading = false;
                        }
                    });
        }
    }

    private void showRewardedVideo(View view, int tier) {
        int curStepCount = mStepsDBHelper.fetchTodayStepCount();
        giftLoader.startAnimation(rotate);
        if (getString(R.string.test_mode).equals("off")) {
            //switch (view.getId()) {
            int id = view.getId();
            if (id == R.id.daily_free_reward) {//Toast.makeText(MainActivity.this, "test message", Toast.LENGTH_LONG)
                    //        .show();
                    if (dailyRewardClaimed) {
                        //bail out, user already rewarded
                        Toast.makeText(MainActivity.this, getString(R.string.reward_already_claimed), Toast.LENGTH_LONG)
                                .show();
                        return;
                    }
            } else if (id == R.id.daily_5k_reward) {
                    if (fivekRewardClaimed) {
                        Toast.makeText(MainActivity.this, getString(R.string.reward_already_claimed), Toast.LENGTH_LONG)
                                .show();
                        return;
                    }
                    if (curStepCount < 5000) {
                        Toast.makeText(MainActivity.this, getString(R.string.not_eligible), Toast.LENGTH_LONG)
                                .show();
                        return;
                    }
            } else if (id == R.id.daily_7k_reward) {
                    if (sevenkRewardClaimed) {
                        Toast.makeText(MainActivity.this, getString(R.string.reward_already_claimed), Toast.LENGTH_LONG)
                                .show();
                        return;
                    }
                    if (curStepCount < 7000) {
                        Toast.makeText(MainActivity.this, getString(R.string.not_eligible), Toast.LENGTH_LONG)
                                .show();
                        return;
                    }
            } else if (id == R.id.daily_10k_reward) {
                    if (tenkRewardClaimed) {
                        Toast.makeText(MainActivity.this, getString(R.string.reward_already_claimed), Toast.LENGTH_LONG)
                                .show();
                        return;
                    }
                    if (curStepCount < 10000) {
                        Toast.makeText(MainActivity.this, getString(R.string.not_eligible), Toast.LENGTH_LONG)
                                .show();
                        return;
                    }
            }
        }

        if (rewardedAd == null) {
            Log.d("TAG", getString(R.string.ad_not_ready));
            try {
                Toast.makeText(ctx, getString(R.string.ad_not_ready), Toast.LENGTH_LONG);
            }catch(Exception ex){
                ex.printStackTrace();
            }
            //loadRewardedAd();
            //check for consent and load ad
            loadConsentData(true);
            return;
        }

        //Toast.makeText(ctx, "calculate reward value", Toast.LENGTH_LONG);

        //calculate random reward value
        double rewardValue = 0;

        switch(tier){
            case 1:
                rewardValue = generateRandomVal(Float.parseFloat(getString(R.string.tier_one_reward_max)), Float.parseFloat(getString(R.string.tier_one_reward_min)));
                break;

            case 2:
                rewardValue = generateRandomVal(Float.parseFloat(getString(R.string.tier_two_reward_max)), Float.parseFloat(getString(R.string.tier_two_reward_min)));
                break;

            case 3:
                rewardValue = generateRandomVal(Float.parseFloat(getString(R.string.tier_three_reward_max)), Float.parseFloat(getString(R.string.tier_three_reward_min)));
                break;

            case 4:
                rewardValue = generateRandomVal(Float.parseFloat(getString(R.string.tier_four_reward_max)), Float.parseFloat(getString(R.string.tier_four_reward_min)));
                break;
        }

        rewardValue = Math.floor(rewardValue * 1000) / 1000;

        //Toast.makeText(MainActivity.this, "potential reward value: "+rewardValue, Toast.LENGTH_LONG)
        //        .show();

        Button myBtn = (Button)view;
        //prepare SSV for proper server side validation
        ServerSideVerificationOptions options = new ServerSideVerificationOptions.Builder()
                //custom data format: user|AFIT|tier|buttontitle
                //.setCustomData("Tier:"+tier+"rewardedAFIT:"+rewardValue)
                .setCustomData(username+"_"+ rewardValue +"_"+ tier + "_" + Uri.encode(myBtn.getText().toString()))
                .build();

        rewardedAd.setServerSideVerificationOptions(options);

        //showVideoButton.setVisibility(View.INVISIBLE);

        rewardedAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d(TAG, "onAdShowedFullScreenContent");
                        //Toast.makeText(MainActivity.this, "ad is shown", Toast.LENGTH_LONG)
                        //        .show();
                        //hide animation

//                        Toast.makeText(MainActivity.this, "onAdShowedFullScreenContent", Toast.LENGTH_SHORT)
//                                .show();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when ad fails to show.
                        Log.d(TAG, "onAdFailedToShowFullScreenContent");
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        rewardedAd = null;
                        giftLoader.clearAnimation();
                        //Toast.makeText(
                        //        MainActivity.this, "adfailedshow:"+adError.toString(), Toast.LENGTH_SHORT)
                        //        .show();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        rewardedAd = null;
                        Log.d(TAG, "onAdDismissedFullScreenContent");
                        //Toast.makeText(
                        //        MainActivity.this, "addismissed:", Toast.LENGTH_SHORT)
                        //        .show();
//                        Toast.makeText(MainActivity.this, "onAdDismissedFullScreenContent", Toast.LENGTH_SHORT)
//                                .show();
                        // Preload the next rewarded ad.
                        MainActivity.this.loadRewardedAd();
                    }
                });
        Activity activityContext = MainActivity.this;
        double finalRewardValue = rewardValue;
        rewardedAd.show(
                activityContext,
                rewardItem -> {
                    // Handle the reward.
                    Log.d("TAG", "The user earned the reward.");
                    Toast.makeText(MainActivity.this, getString(R.string.ad_reward_success).replace("_VAL_", finalRewardValue+""), Toast.LENGTH_SHORT)
                            .show();
                    //give out the reward

//                        int rewardAmount = rewardItem.getAmount();
//                        String rewardType = rewardItem.getType();

                    giftLoader.clearAnimation();

                    //store locally for validation
                    SharedPreferences sharedPreferences = getSharedPreferences("actifitSets",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                    String strDate = dateFormat.format(date);

                    int id = view.getId();
                    if (id == R.id.daily_free_reward) {
                            dailyRewardClaimed = true;
                            editor.putString(getString(R.string.daily_free_reward), strDate);
                            editor.putString("freerewardedValue",finalRewardValue+"");
                            editor.commit();
                    } else if (id == R.id.daily_5k_reward) {
                            fivekRewardClaimed = true;
                            editor.putString(getString(R.string.daily_5k_reward), strDate);
                            editor.putString("5krewardedValue",finalRewardValue+"");
                            editor.commit();
                    } else if (id == R.id.daily_7k_reward) {
                            sevenkRewardClaimed = true;
                            editor.putString(getString(R.string.daily_7k_reward), strDate);
                            editor.putString("7krewardedValue",finalRewardValue+"");
                            editor.commit();
                    } else if (id == R.id.daily_10k_reward) {
                            tenkRewardClaimed = true;
                            editor.putString(getString(R.string.daily_10k_reward), strDate);
                            editor.putString("10krewardedValue",finalRewardValue+"");
                            editor.commit();
                    }
                    //adjust button text
                    ((Button) view).setText(Html.fromHtml (((Button) view).getText()+"<br/> "+finalRewardValue+" AFIT "+checkMark));
                    adjustRewardButtonsStatus(mStepsDBHelper.fetchTodayStepCount());
                });
    }

    private float generateRandomVal(float max, float min){
        float randomVal = 0;
        Random rand = new Random();
        //bias towards lower values
        float finalVal = rand.nextFloat() * (max - min) + min;
        //run 5 iterations and grab lowest val
        for (int i=0;i<5;i++) {
            randomVal = rand.nextFloat() * (max - min) + min;
            //always save lowest value
            if (randomVal < finalVal){
                finalVal = randomVal;
            }
        }
        if (finalVal < min){
            finalVal = min;
        }
        if (finalVal > max){
            finalVal = max;
        }
        return finalVal;
    }

    private String grabEarningsPanelNote(){
        String msg = "";
        boolean showNotice = false;
        if (userFullBalance < minTokenCount){
            msg += "<b>"+getString(R.string.not_earning_afit) + " " + getString(R.string.min_afit_reward_balance) + " AFIT. <br /></b>";
            showNotice = true;
        }
        if (!hasSteemAccount){
            msg += "<b>"+getString(R.string.not_earning_steem) + "<br /></b>";
            showNotice = true;
        }
        if (!hasBlurtAccount){
            msg += "<b>"+getString(R.string.not_earning_blurt) + "<br /></b>";
            showNotice = true;
        }else if (blurtBalance < Double.parseDouble(getString(R.string.min_blurt_reward_balance))){
            msg += "<b>"+getString(R.string.not_earning_blurt_balance) + "<br /></b>";
            showNotice = true;
        }
        msg += "<i>"+getString(R.string.earnings_pane_note_0) + "<br />";
        msg += getString(R.string.earnings_pane_note_1)+ "<br /></i>";
        msg += getString(R.string.earnings_pane_note_2)+ "<br /></i>";

        FontTextView ftv = findViewById(R.id.token_notice);
        if (showNotice){
            ftv.setVisibility(View.VISIBLE);
        }else{
            ftv.setVisibility(View.GONE);
        }

        return msg;
    }

    //check and notify user about battery optimization
    @TargetApi(23)
    private void checkBatteryOptimization(Boolean forceShow) {
        //we should only check batter optimization if user has not disabled this notification and/or
        //if he does not have 3rd party setting enabled

        //grab stored value, if any
        final SharedPreferences sharedPreferences = getSharedPreferences("actifitSets",MODE_PRIVATE);

        Boolean skipShowingRewards = (sharedPreferences.getBoolean(getString(R.string.donotshowbatteryoptimization),false));

        String dataTrackingSystem = sharedPreferences.getString("dataTrackingSystem","");
        if (dataTrackingSystem.equals(getString(R.string.fitbit_tracking_ntt))){
            skipShowingRewards = true;

        }

        if (forceShow){
            skipShowingRewards = false;
        }

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        FontTextView batteryNotif = findViewById(R.id.battery_notice);
        if (!pm.isIgnoringBatteryOptimizations("io.actifit.fitnesstracker.actifitfitnesstracker")) {
            //display related button
            batteryNotif.setVisibility(View.VISIBLE);
            batteryNotif.setOnClickListener(view -> {

                showBatteryNotice();
            });

            //notify user if not skipping
            if (skipShowingRewards){
                return;
            }


            showBatteryNotice();
        }else{
            batteryNotif.setVisibility(View.GONE);
        }
    }

    private void showBatteryNotice(){
        String msg = getString(R.string.device_ignore_battery_optimization);
        msg += getString(R.string.device_app_launch);

        //display alert dialog about pending rewards
        AlertDialog.Builder batteryDialogBuilder = new AlertDialog.Builder(ctx);

        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //cancel
                    break;


                case DialogInterface.BUTTON_NEUTRAL:
                    SharedPreferences sharedPreferences = getSharedPreferences("actifitSets",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(getString(R.string.donotshowbatteryoptimization), true);
                    editor.commit();
                    break;
            }
        };

        AlertDialog pointer = batteryDialogBuilder.setMessage(Html.fromHtml(msg))
                .setTitle(getString(R.string.battery_optimization_setting))
                .setIcon(getResources().getDrawable(R.drawable.actifit_logo))
                .setPositiveButton(getString(R.string.close_button), dialogClickListener)
                .setNeutralButton(getString(R.string.do_not_show_again), dialogClickListener)
                .create();
        batteryDialogBuilder.show();
        /*pointer.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        pointer.getWindow().getDecorView().setBackground(getDrawable(R.drawable.dialog_shape));
        pointer.show();*/
    }



    private void openUserAccount(SharedPreferences sharedPreferences){
        username = sharedPreferences.getString("actifitUser","");
        if (!username.equals("")) {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

            builder.setToolbarColor(getResources().getColor(R.color.actifitRed));

            //animation for showing and closing fitbit authorization screen
            builder.setStartAnimations(ctx, R.anim.slide_in_right, R.anim.slide_out_left);

            //animation for back button clicks
            builder.setExitAnimations(ctx, android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);

            CustomTabsIntent customTabsIntent = builder.build();

            customTabsIntent.launchUrl(ctx, Uri.parse(MainActivity.ACTIFIT_CORE_URL + '/' + username));
        }
    }

    private void openUserRank(){
        //username = sharedPreferences.getString("actifitUser","");
        //if (username != "") {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

        builder.setToolbarColor(getResources().getColor(R.color.actifitRed));

        //animation for showing and closing fitbit authorization screen
        builder.setStartAnimations(ctx, R.anim.slide_in_right, R.anim.slide_out_left);

        //animation for back button clicks
        builder.setExitAnimations(ctx, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        CustomTabsIntent customTabsIntent = builder.build();

        customTabsIntent.launchUrl(ctx, Uri.parse(MainActivity.ACTIFIT_RANK_URL));
        //}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO double check
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            galleryAddPic();
        }
    }

    //handle appending created pic to the gallery
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    //handles display of local date on front end
    private void displayDate(){
        String date_n = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault()).format(new Date());
        TextView date  = findViewById(R.id.current_date);
        date.setText(date_n);
    }

    private void displayUserBalance(){
        /***************** Fetch user full balance ********************/
        SharedPreferences sharedPreferences = getSharedPreferences("actifitSets",MODE_PRIVATE);
        username = sharedPreferences.getString("actifitUser","");

        // This holds the url to connect to the API and grab the balance.
        // We append to it the username
        if (username.equals("") || username.length()<2) return;
        String balanceUrl = Utils.apiUrl(this)+getString(R.string.user_balance_api_url)+username+"?fullBalance=1";

        //display header
        //actifitBalanceLbl.setVisibility(View.VISIBLE);
        // Request the balance of the user while expecting a JSON response
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest balanceRequest = new JsonObjectRequest
                (Request.Method.GET, balanceUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //hide dialog
                        //progress.hide();
                        // Display the result
                        try {
                            //grab current token count
                            userFullBalance = response.getDouble("tokens");

                            TextView tv =  findViewById(R.id.bal_display);
                            tv.setText(""+ formatValue(userFullBalance)  +" AFIT");

                            //TextView tvTokens = findViewById(R.id.bal_display_note);
                            //tvTokens.setText();

                            //LinearLayout ll = findViewById(R.id.posting_key_link);

                            FontTextView ftvWallet = findViewById(R.id.token_notice_wallet);
                            String msg = "";

                            if (userFullBalance < minTokenCount){
                                //display warning about earning AFIT tokens
                                ImageView iv = findViewById(R.id.afit_logo);
                                iv.setColorFilter(Color.rgb( 210, 215, 211));// @color/cardview_dark_background);

                                //ftv.setVisibility(View.VISIBLE);
                                //ftvWallet.setVisibility(View.VISIBLE);

                            }else{
                                //ftv.setVisibility(View.GONE);
                                //ftvWallet.setVisibility(View.GONE);
                            }

                            try {
                                if (earningsDialog != null){// && earningsDialog.isShowing()) {

                                    msg = grabEarningsPanelNote();

                                    earningsDialog.setMessage(Html.fromHtml(msg));
                                }
                            }catch(Exception ex){

                            }

                            /*
                            //start animations
                            ImageView hiveLogo = findViewById(R.id.hive_logo);
                            ImageView steemLogo = findViewById(R.id.steem_logo);
                            ImageView sportsLogo = findViewById(R.id.sports_logo);
                            hiveLogo.clearAnimation();
                            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
                            animation.setRepeatCount(Animation.INFINITE);
                            //animation.setRepeatMode(Animation.RESTART);
                            //animation.setDuration(1000);
                            //animation.setRepeatCount(200);

                            //hiveLogo.startAnimation(animation);
                            hiveLogo.setAnimation(animation);
                            hiveLogo.animate();

                            steemLogo.setAnimation(animation);
                            steemLogo.animate();

                            sportsLogo.setAnimation(animation);
                            sportsLogo.animate();*/

                        }catch(JSONException e){
                            //hide dialog
                            //progress.hide();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //hide dialog
                        //progress.hide();
                        //actifitBalance.setText(getString(R.string.unable_fetch_afit_balance));
                        error.printStackTrace();
                    }
                });

        // Add balance request to be processed
        queue.add(balanceRequest);

        //grab account RC value
        String accountRCUrl = Utils.apiUrl(this)+getString(R.string.get_account_rc)+username;
        // Request the balance of the user while expecting a JSON response
        JsonObjectRequest accountRCRequest = new JsonObjectRequest
                (Request.Method.GET, accountRCUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //hide dialog
                        //progress.hide();
                        try {
                            //validate if account exists on the chains
                            if (response.has("currentRC")) {
                                String rcVal = response.get("currentRC").toString();
                                accountRCValue.setText(rcVal + "%");
                                //accountRCValue.setVisibility(View.VISIBLE);
                                //accountRCContainer.setVisibility(View.VISIBLE);
                            }else{
                                //accountRCContainer.setVisibility(View.GONE);
                            }

                        } catch (Exception ex) {


                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //hide dialog

                    }
                });

        queue.add(accountRCRequest);

        //handle RC click
        //accountRCContainer.setOnClickListener(new View.OnClickListener() {
        accountRCValue.setOnClickListener(view -> {

            AlertDialog.Builder rcDialogBuilder = new AlertDialog.Builder(ctx);
            String msg = getString(R.string.rc_note);
            AlertDialog pointer = rcDialogBuilder.setMessage(Html.fromHtml(msg))
                    .setTitle(getString(R.string.rc_note_title))
                    .setIcon(getResources().getDrawable(R.drawable.actifit_logo))
                    .setCancelable(true)
                    .setNegativeButton(getString(R.string.close_button), (dialog, id) -> dialog.dismiss()).create();

            rcDialogBuilder.show();
                /*pointer.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                pointer.getWindow().getDecorView().setBackground(getDrawable(R.drawable.dialog_shape));
                pointer.show();*/
        });

        newbieLink.setOnClickListener(iew -> {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ctx);
            String msg = getString(R.string.verify_newbie_note);

            dialogBuilder.setMessage(msg);


            dialogBuilder.setTitle(getString(R.string.verify_newbie_title));
            dialogBuilder.setNegativeButton(getString(R.string.discord),
                    (dialog, id) -> {

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.discord_actifit))));
                        }catch(Exception e){
                            Log.e(MainActivity.TAG, "error opening social media");
                        }

                    });

            dialogBuilder.setPositiveButton(getString(R.string.share_post_button),
                    (dialog, id) -> {
                        //dialog.cancel();

                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareSubject = getString(R.string.newbie_share_socials);
                        String shareBody = getString(R.string.newbie_share_socials);
                        shareBody += " " + getString(R.string.actifit_url);

                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                        MainActivity.this.startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));

                    });


            dialogBuilder.setCancelable(true);

            dialogBuilder.setNeutralButton(
                    getString(R.string.dismiss_button),
                    (dialog, id) -> dialog.cancel());


            //create and display alert window
            try {
                AlertDialog alert11 = dialogBuilder.create();
                //alert11.show();
                dialogBuilder.show();
            }catch(Exception e){
                //Log.e(MainActivity.TAG, e.getMessage());
            }


            //dialogBuilder.show();
                /*pointer.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                pointer.getWindow().getDecorView().setBackground(getDrawable(R.drawable.dialog_shape));
                pointer.show();*/
        });

        //check if user has accounts across all chains

        // This holds the url to connect to the API and grab the balance.
        // We append to it the username
        String accountDataUrl = Utils.apiUrl(this)+getString(R.string.get_account_api_url)+username;

        //display header
        // Request the balance of the user while expecting a JSON response
        JsonObjectRequest userDataRequest = new JsonObjectRequest
                (Request.Method.GET, accountDataUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //hide dialog
                        //progress.hide();
                        try {
                            //validate if account exists on the chains
                            if (!response.has("STEEM")){
                                ImageView iv = findViewById(R.id.steem_logo);
                                iv.setColorFilter(Color.rgb( 210, 215, 211));
                                hasSteemAccount = false;
                            }else{
                                hasSteemAccount = true;
                            }

                            if (!response.has("BLURT")){
                                ImageView iv = findViewById(R.id.blurt_logo);
                                iv.setColorFilter(Color.rgb( 210, 215, 211));
                                hasBlurtAccount = false;
                            }else{
                                hasBlurtAccount = true;
                                JSONObject blurtData = response.getJSONObject("BLURT");
                                String blurtBalanceStr = blurtData.getString("balance");
                                String [] parts = blurtBalanceStr.split(" ");
                                blurtBalance = Double.parseDouble(parts[0]);
                                if (blurtBalance < Double.parseDouble(getString(R.string.min_blurt_reward_balance))){
                                    ImageView iv = findViewById(R.id.blurt_logo);
                                    iv.setColorFilter(Color.rgb( 210, 215, 211));
                                }

                            }

                        } catch (Exception ex) {


                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //hide dialog

                    }
                });

        queue.add(userDataRequest);

    }

    public static String formatValue(double value) {
        DecimalFormat df = new DecimalFormat("###,###,###.###");
        return df.format(value);
    }
    private void displayActivityChartFitbit(final int stepCount, final boolean animate){

        runOnUiThread(() -> {

            fitbitPieChart = findViewById(R.id.step_pie_chart_fitbit);
            ArrayList<PieEntry> activityArray = new ArrayList();
            activityArray.add(new PieEntry(stepCount, ""));

            if (stepCount > 2000){
                //animate waves button
                if (BtnWaves.getAnimation()==null || BtnWaves.getAnimation().hasStarted()) {
                    BtnWaves.setAnimation(scaler);
                }
            }

            if (stepCount < 5000) {
                activityArray.add(new PieEntry(5000 - stepCount, ""));
                activityArray.add(new PieEntry(5000, ""));
            } else if (stepCount < 10000) {
                //enable animation on post & earn button
                //ensure animation is running
                if (BtnPostSteemit!=null && scaler!=null) {
                    if (BtnPostSteemit.getAnimation()==null || BtnPostSteemit.getAnimation().hasStarted()) {
                        BtnPostSteemit.startAnimation(scaler);
                    }
                }

                activityArray.add(new PieEntry(10000 - stepCount, ""));
            }

            PieDataSet dataSet = new PieDataSet(activityArray, "");

            PieData data = new PieData(dataSet);
            fitbitPieChart.setData(data);
//        Description chartDesc = new Description();
//        chartDesc.setText(getString(R.string.activity_count_lbl));
//        btnPieChart.setDescription(chartDesc);
            fitbitPieChart.getDescription().setEnabled(false);
            //chartDesc.setPosition(200, 0);
            fitbitPieChart.setCenterText("" + (stepCount < 0 ? 0 : stepCount));
            fitbitPieChart.setCenterTextColor(getResources().getColor(R.color.actifitRed));
            fitbitPieChart.setCenterTextSize(20f);
            fitbitPieChart.setEntryLabelColor(ColorTemplate.COLOR_NONE);
            fitbitPieChart.setDrawEntryLabels(false);
            fitbitPieChart.getLegend().setEnabled(false);

            //dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            //let's set proper color
            if (stepCount < 5000) {
                //dataSet.setColors(android.R.color.tab_indicator_text, android.R.color.tab_indicator_text);
                dataSet.setColors(getResources().getColor(R.color.actifitRed), getResources().getColor(android.R.color.tab_indicator_text), getResources().getColor(android.R.color.tab_indicator_text));
            } else if (stepCount < 10000) {
                dataSet.setColors(getResources().getColor(R.color.actifitDarkGreen), getResources().getColor(android.R.color.tab_indicator_text), getResources().getColor(android.R.color.tab_indicator_text));
                //enable second level reward
                //fivekRewardButton.setEnabled(true);
            } else {
                dataSet.setColors(getResources().getColor(R.color.actifitDarkGreen));
                //enable third level reward
            }

            adjustRewardButtonsStatus(stepCount);

            //dataSet.setColors(ColorTemplate.rgb("00ff00"), ColorTemplate.rgb("00ffff"), ColorTemplate.rgb("00ffff"));

            dataSet.setSliceSpace(1f);
            dataSet.setHighlightEnabled(true);
            dataSet.setValueTextSize(0f);
            dataSet.setValueTextColor(ColorTemplate.COLOR_NONE);
            dataSet.setValueTextColor(R.color.actifitRed);

            if (animate) {
                fitbitPieChart.animateXY(2000, 2000);
            } else {
                fitbitPieChart.invalidate();
            }

        });
    }

    private void displayActivityChart(final int stepCount, final boolean animate){

        runOnUiThread(() -> {

            btnPieChart = findViewById(R.id.step_pie_chart);
            ArrayList<PieEntry> activityArray = new ArrayList();
            activityArray.add(new PieEntry(stepCount, ""));

            if (stepCount > 2000){
                //animate waves button
                if (BtnWaves.getAnimation()==null || BtnWaves.getAnimation().hasStarted()) {
                    BtnWaves.setAnimation(scaler);
                }
            }

            if (stepCount < 5000) {
                activityArray.add(new PieEntry(5000 - stepCount, ""));
                activityArray.add(new PieEntry(5000, ""));
            } else if (stepCount < 10000) {
                //enable animation on post & earn button
                //ensure animation is running
                if (BtnPostSteemit!=null && scaler!=null) {
                    if (BtnPostSteemit.getAnimation()==null || BtnPostSteemit.getAnimation().hasStarted()) {
                        BtnPostSteemit.startAnimation(scaler);
                    }
                }

                activityArray.add(new PieEntry(10000 - stepCount, ""));
            }

            PieDataSet dataSet = new PieDataSet(activityArray, "");

            PieData data = new PieData(dataSet);
            btnPieChart.setData(data);
//        Description chartDesc = new Description();
//        chartDesc.setText(getString(R.string.activity_count_lbl));
//        btnPieChart.setDescription(chartDesc);
            btnPieChart.getDescription().setEnabled(false);
            //chartDesc.setPosition(200, 0);
            btnPieChart.setCenterText("" + (stepCount < 0 ? 0 : stepCount));
            btnPieChart.setCenterTextColor(getResources().getColor(R.color.actifitRed));
            btnPieChart.setCenterTextSize(20f);
            btnPieChart.setEntryLabelColor(ColorTemplate.COLOR_NONE);
            btnPieChart.setDrawEntryLabels(false);
            btnPieChart.getLegend().setEnabled(false);

            //dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            //let's set proper color
            if (stepCount < 5000) {
                //dataSet.setColors(android.R.color.tab_indicator_text, android.R.color.tab_indicator_text);
                dataSet.setColors(getResources().getColor(R.color.actifitRed), getResources().getColor(android.R.color.tab_indicator_text), getResources().getColor(android.R.color.tab_indicator_text));
            } else if (stepCount < 10000) {
                dataSet.setColors(getResources().getColor(R.color.actifitDarkGreen), getResources().getColor(android.R.color.tab_indicator_text), getResources().getColor(android.R.color.tab_indicator_text));
                //enable second level reward
                //fivekRewardButton.setEnabled(true);
            } else {
                dataSet.setColors(getResources().getColor(R.color.actifitDarkGreen));
                //enable third level reward
            }

            adjustRewardButtonsStatus(stepCount);

            //dataSet.setColors(ColorTemplate.rgb("00ff00"), ColorTemplate.rgb("00ffff"), ColorTemplate.rgb("00ffff"));

            dataSet.setSliceSpace(1f);
            dataSet.setHighlightEnabled(true);
            dataSet.setValueTextSize(0f);
            dataSet.setValueTextColor(ColorTemplate.COLOR_NONE);
            dataSet.setValueTextColor(R.color.actifitRed);

            if (animate) {
                btnPieChart.animateXY(2000, 2000);
            } else {
                btnPieChart.invalidate();
            }

        });
    }

    private void adjustRewardButtonsStatus(int stepCount){
        if (freeRewardButton!=null && fivekRewardButton!=null && tenkRewardButton!=null ) {
            if (dailyRewardClaimed) {
                freeRewardButton.clearAnimation();
            } else if (freeRewardButton.getAnimation() == null || !freeRewardButton.getAnimation().hasStarted()) {
                freeRewardButton.setAnimation(scaler);
                //dailyRewardButton
            }
            if (fivekRewardClaimed) {
                fivekRewardButton.clearAnimation();
            } else if (stepCount >= 5000 && (fivekRewardButton.getAnimation() == null || !fivekRewardButton.getAnimation().hasStarted())) {
                fivekRewardButton.setAnimation(scaler);
                //dailyRewardButton
            }
            if (sevenkRewardClaimed) {
                sevenkRewardButton.clearAnimation();
            } else if (stepCount >= 7000 && (sevenkRewardButton.getAnimation() == null || !sevenkRewardButton.getAnimation().hasStarted())) {
                sevenkRewardButton.setAnimation(scaler);
                //dailyRewardButton
            }
            if (tenkRewardClaimed) {
                tenkRewardButton.clearAnimation();
            } else if (stepCount >= 10000 && (tenkRewardButton.getAnimation() == null || !tenkRewardButton.getAnimation().hasStarted())) {
                tenkRewardButton.setAnimation(scaler);
                //dailyRewardButton
            }
        }
    }

    private class DisplayDayChartDataAsyncTask extends AsyncTask<Boolean, Void, ArrayList<ActivitySlot>> {

        Boolean animate = false;

        public DisplayDayChartDataAsyncTask(Boolean _animate) {
            animate = _animate;
        }

        @Override
        protected ArrayList<ActivitySlot> doInBackground(Boolean... animate) {
            //initializing date
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String strDate = dateFormat.format(date);

            ArrayList<ActivitySlot> mStepCountList = mStepsDBHelper.fetchDateTimeSlotActivity(strDate);
            return mStepCountList;
        }

        @Override
        protected void onPostExecute(ArrayList<ActivitySlot> mStepCountList) {
            super.onPostExecute(mStepCountList);

            //connect to the chart and fill it with data
            dayChart = findViewById(R.id.main_today_activity_chart);

            List<BarEntry> entries = new ArrayList<>();

            int data_id = 0;

            //create a full day chart
            int indHr;
            int indMin;
            int hoursInDay = 24;
            int[] minInt = {0, 15, 30, 45};
            int minSlots = minInt.length;

            final String[] labels = new String[hoursInDay * minSlots];

            //loop through whole day as hours
            for (indHr = 0; indHr < hoursInDay; indHr++) {
                //loop through 15 mins breaks in hour
                for (indMin = 0; indMin < minSlots; indMin++) {
                    String slotLabel = "" + indHr;
                    if (indHr < 10) {
                        slotLabel = "0" + indHr;
                    }
                    labels[data_id] = slotLabel + ":";
                    if (minInt[indMin] < 10) {
                        slotLabel += "0" + minInt[indMin];
                        labels[data_id] += "0" + minInt[indMin];
                    } else {
                        slotLabel += minInt[indMin];
                        labels[data_id] += minInt[indMin];
                    }
                    int matchingSlot = -1;
                    matchingSlot = mStepCountList.indexOf(new ActivitySlot(slotLabel, 0));
                    if (matchingSlot > -1) {
                        //found match, assign values
                        entries.add(new BarEntry(data_id, Float.parseFloat("" + mStepCountList.get(matchingSlot).activityCount)));
                    } else {
                        //default null value
                        entries.add(new BarEntry(data_id, Float.parseFloat("0")));
                    }

                    data_id += 1f;
                }
            }

            BarDataSet dataSet = new BarDataSet(entries, getString(R.string.activity_count_lbl));

            dayBarData = new BarData(dataSet);
            // set custom bar width
            dayBarData.setBarWidth(0.8f);


            //customize X-axis

            IAxisValueFormatter formatter = new IAxisValueFormatter() {

                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return labels[(int) value];
                }

            };

            XAxis xAxis = dayChart.getXAxis();
            xAxis.setGranularity(1f); // minimum axis-step (interval)
            xAxis.setValueFormatter(formatter);

            IValueFormatter yFormatter = new IValueFormatter() {

                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    if (value < 1) {
                        return "";
                    }
                    return "" + (int) value;
                }

            };

            //add limit lines to show marker of min 5K activity
            //YAxis yAxis = chart.getAxisLeft();
            dayBarData.setValueFormatter(yFormatter);
            //yAxis.setAxisMinimum(0);


            //description field of chart
            Description chartDescription = new Description();
            chartDescription.setText(getString(R.string.activity_details_chart_title));
            dayChart.setDescription(chartDescription);
            dayChart.getLegend().setEnabled(false);

            //fill chart with data
            dayChart.setData(dayBarData);

            if (animate) {
                //display data with cool animation
                dayChart.animateXY(1500, 1500);
            } else {
                //render data
                dayChart.invalidate();
            }
        }
    }

    /* function handles displaying today's detailed chart data */
    private void displayDayChartData(final boolean animate){

        //update ui on UI thread
        runOnUiThread(new Runnable() {

            /*Handler uiHandler = new Handler(Looper.getMainLooper());
            uiHandler.post(new Runnable(){*/
            @Override
            public void run() {

                //initializing date
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                String strDate = dateFormat.format(date);

                ArrayList<ActivitySlot> mStepCountList = mStepsDBHelper.fetchDateTimeSlotActivity(strDate);

                //connect to the chart and fill it with data
                dayChart = findViewById(R.id.main_today_activity_chart);

                List<BarEntry> entries = new ArrayList<>();

                int data_id = 0;

                //create a full day chart
                int indHr;
                int indMin;
                int hoursInDay = 24;
                int[] minInt = {0, 15, 30, 45};
                int minSlots = minInt.length;

                final String[] labels = new String[hoursInDay * minSlots];

                //loop through whole day as hours
                for (indHr = 0; indHr < hoursInDay; indHr++) {
                    //loop through 15 mins breaks in hour
                    for (indMin = 0; indMin < minSlots; indMin++) {
                        String slotLabel = "" + indHr;
                        if (indHr < 10) {
                            slotLabel = "0" + indHr;
                        }
                        labels[data_id] = slotLabel + ":";
                        if (minInt[indMin] < 10) {
                            slotLabel += "0" + minInt[indMin];
                            labels[data_id] += "0" + minInt[indMin];
                        } else {
                            slotLabel += minInt[indMin];
                            labels[data_id] += minInt[indMin];
                        }
                        int matchingSlot = -1;
                        matchingSlot = mStepCountList.indexOf(new ActivitySlot(slotLabel, 0));
                        if (matchingSlot > -1) {
                            //found match, assign values
                            entries.add(new BarEntry(data_id, Float.parseFloat("" + mStepCountList.get(matchingSlot).activityCount)));
                        } else {
                            //default null value
                            entries.add(new BarEntry(data_id, Float.parseFloat("0")));
                        }

                        data_id += 1f;
                    }
                }

                BarDataSet dataSet = new BarDataSet(entries, getString(R.string.activity_count_lbl));

                dayBarData = new BarData(dataSet);
                // set custom bar width
                dayBarData.setBarWidth(0.8f);


                //customize X-axis

                IAxisValueFormatter formatter = new IAxisValueFormatter() {

                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return labels[(int) value];
                    }

                };

                XAxis xAxis = dayChart.getXAxis();
                xAxis.setGranularity(1f); // minimum axis-step (interval)
                xAxis.setValueFormatter(formatter);

                IValueFormatter yFormatter = new IValueFormatter() {

                    @Override
                    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                        if (value < 1) {
                            return "";
                        }
                        return "" + (int) value;
                    }

                };

                //add limit lines to show marker of min 5K activity
                //YAxis yAxis = chart.getAxisLeft();
                dayBarData.setValueFormatter(yFormatter);
                //yAxis.setAxisMinimum(0);


                //description field of chart
                Description chartDescription = new Description();
                chartDescription.setText(getString(R.string.activity_details_chart_title));
                dayChart.setDescription(chartDescription);
                dayChart.getLegend().setEnabled(false);

                //fill chart with data
                dayChart.setData(dayBarData);

                if (animate) {
                    //display data with cool animation
                    dayChart.animateXY(1500, 1500);
                } else {
                    //render data
                    dayChart.invalidate();
                }

            }
        });
    }

    private class DisplayChartDataAsyncTask extends AsyncTask<Boolean, Void, ArrayList<DateStepsModel>> {

        Boolean animate = false;

        public DisplayChartDataAsyncTask(Boolean _animate) {
            animate = _animate;
        }

        @Override
        protected ArrayList<DateStepsModel> doInBackground(Boolean... animate) {
            //read activity data
            ArrayList<DateStepsModel> mStepCountList = mStepsDBHelper.readStepsEntries();
            return mStepCountList;
        }

        @Override
        protected void onPostExecute(ArrayList<DateStepsModel> mStepCountList) {
            super.onPostExecute(mStepCountList);

            //initializing date conversion components
            String dateDisplay;
            //existing date format
            SimpleDateFormat dateFormIn = new SimpleDateFormat("yyyyMMdd");
            //output format
            SimpleDateFormat dateFormOut = new SimpleDateFormat("MM/dd");
            SimpleDateFormat dateFormOutFull = new SimpleDateFormat("MM/dd/yy");


            //connect to the chart and fill it with data
            fullChart = findViewById(R.id.main_history_activity_chart);

            List<BarEntry> entries = new ArrayList<BarEntry>();

            final String[] labels = new String[mStepCountList.size()];

            int data_id = 0;
            //int data_id_int = 0;
            try {
                for (DateStepsModel data : mStepCountList) {

                    //grab date entry according to stored format
                    Date feedingDate = dateFormIn.parse(data.mDate);

                    //convert it to new format for display

                    dateDisplay = dateFormOut.format(feedingDate);

                    //if this is month 12, display year along with it
                    if (dateDisplay.substring(0, 2).equals("01") || dateDisplay.substring(0, 2).equals("12")) {
                        dateDisplay = dateFormOutFull.format(feedingDate);
                    }

                    labels[data_id] = dateDisplay;
                    entries.add(new BarEntry(data_id, Float.parseFloat("" + data.mStepCount)));
                    data_id += 1f;
                    //data_id_int++;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            BarDataSet dataSet = new BarDataSet(entries, getString(R.string.activity_count_lbl));

            chartBarData = new BarData(dataSet);
            // set custom bar width
            chartBarData.setBarWidth(0.5f);


            //customize X-axis

            IAxisValueFormatter formatter = new IAxisValueFormatter() {

                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return labels[(int) value];
                }

            };

            XAxis xAxis = fullChart.getXAxis();
            xAxis.setGranularity(1f); // minimum axis-step (interval)
            xAxis.setValueFormatter(formatter);

            //add limit lines to show marker of min 5K activity
            YAxis yAxis = fullChart.getAxisLeft();

            if (yAxis.getLimitLines().size()==0) {

                LimitLine line = new LimitLine(5000, getString(R.string.min_reward_level_chart));
                line.enableDashedLine(10f, 10f, 10f);
                line.setLineColor(Color.RED);
                line.setLineWidth(2f);
                line.setTextStyle(Paint.Style.FILL_AND_STROKE);
                line.setTextColor(Color.BLACK);
                line.setTextSize(12f);

                yAxis.addLimitLine(line);

                //add Limit line for max rewarded activity
                line = new LimitLine(10000, getString(R.string.max_reward_level_chart));
                line.setLineColor(Color.GREEN);
                line.setLineWidth(2f);
                line.setTextStyle(Paint.Style.FILL_AND_STROKE);
                line.setTextColor(Color.BLACK);
                line.setTextSize(12f);


                yAxis.addLimitLine(line);

            }


            //description field of chart
            Description chartDescription = new Description();
            chartDescription.setText(getString(R.string.activity_history_chart_title));

            fullChart.setDescription(chartDescription);
            fullChart.getLegend().setEnabled(false);

            //fill chart with data
            fullChart.setData(chartBarData);

            if (animate) {
                //display data with cool animation
                fullChart.animateXY(1500, 1500);
            } else {
                //render data
                fullChart.invalidate();
            }

        }
    }

    /* function handles displaying full chart data */
    private void displayChartData(final boolean animate){


        //update ui on UI thread
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //read activity data
                ArrayList<DateStepsModel> mStepCountList = mStepsDBHelper.readStepsEntries();

                //initializing date conversion components
                String dateDisplay;
                //existing date format
                SimpleDateFormat dateFormIn = new SimpleDateFormat("yyyyMMdd");
                //output format
                SimpleDateFormat dateFormOut = new SimpleDateFormat("MM/dd");
                SimpleDateFormat dateFormOutFull = new SimpleDateFormat("MM/dd/yy");


                //connect to the chart and fill it with data
                fullChart = findViewById(R.id.main_history_activity_chart);

                List<BarEntry> entries = new ArrayList<BarEntry>();

                final String[] labels = new String[mStepCountList.size()];

                int data_id = 0;
                //int data_id_int = 0;
                try {
                    for (DateStepsModel data : mStepCountList) {

                        //grab date entry according to stored format
                        Date feedingDate = dateFormIn.parse(data.mDate);

                        //convert it to new format for display

                        dateDisplay = dateFormOut.format(feedingDate);

                        //if this is month 12, display year along with it
                        if (dateDisplay.substring(0, 2).equals("01") || dateDisplay.substring(0, 2).equals("12")) {
                            dateDisplay = dateFormOutFull.format(feedingDate);
                        }

                        labels[data_id] = dateDisplay;
                        entries.add(new BarEntry(data_id, Float.parseFloat("" + data.mStepCount)));
                        data_id += 1f;
                        //data_id_int++;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                BarDataSet dataSet = new BarDataSet(entries, getString(R.string.activity_count_lbl));

                chartBarData = new BarData(dataSet);
                // set custom bar width
                chartBarData.setBarWidth(0.5f);


                //customize X-axis

                IAxisValueFormatter formatter = new IAxisValueFormatter() {

                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return labels[(int) value];
                    }

                };

                XAxis xAxis = fullChart.getXAxis();
                xAxis.setGranularity(1f); // minimum axis-step (interval)
                xAxis.setValueFormatter(formatter);

                //add limit lines to show marker of min 5K activity
                YAxis yAxis = fullChart.getAxisLeft();

                if (yAxis.getLimitLines().size()==0) {

                    LimitLine line = new LimitLine(5000, getString(R.string.min_reward_level_chart));
                    line.enableDashedLine(10f, 10f, 10f);
                    line.setLineColor(Color.RED);
                    line.setLineWidth(2f);
                    line.setTextStyle(Paint.Style.FILL_AND_STROKE);
                    line.setTextColor(Color.BLACK);
                    line.setTextSize(12f);

                    yAxis.addLimitLine(line);

                    //add Limit line for max rewarded activity
                    line = new LimitLine(10000, getString(R.string.max_reward_level_chart));
                    line.setLineColor(Color.GREEN);
                    line.setLineWidth(2f);
                    line.setTextStyle(Paint.Style.FILL_AND_STROKE);
                    line.setTextColor(Color.BLACK);
                    line.setTextSize(12f);


                    yAxis.addLimitLine(line);

                }


                //description field of chart
                Description chartDescription = new Description();
                chartDescription.setText(getString(R.string.activity_history_chart_title));

                fullChart.setDescription(chartDescription);
                fullChart.getLegend().setEnabled(false);

                //fill chart with data
                fullChart.setData(chartBarData);

                if (animate) {
                    //display data with cool animation
                    fullChart.animateXY(1500, 1500);
                } else {
                    //render data
                    fullChart.invalidate();
                }

            }
        });

    }

    //handles fetching and displaying pending user rewards
    public void displayPendingRewards(){

        //check if user does not wish to see this notification

        //grab stored value, if any
        final SharedPreferences sharedPreferences = getSharedPreferences("actifitSets",MODE_PRIVATE);

        Boolean skipShowingRewards = (sharedPreferences.getBoolean(getString(R.string.donotshowrewards),false));
        if (skipShowingRewards){
            return;
        }

        username = sharedPreferences.getString("actifitUser","");



        if (username != "") {

            //handles sending out API query requests
            RequestQueue queue = Volley.newRequestQueue(this);

            //fetch blurt price
            String blurtPriceUrl = getString(R.string.coingecko_price).replace("CURRENCY", "BLURT");

            // Request the rank of the user while expecting a JSON response
            JsonObjectRequest blurtPriceReq = new JsonObjectRequest
                    (Request.Method.GET, blurtPriceUrl, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            // Display the result
                            try {
                                blurtPrice = response.getJSONObject("blurt").getDouble("usd");
                                if (pendingRewardsDialog != null){// && pendingRewardsDialog.isShowing()){
                                    String hiveRewards = parseRewards(innerRewards, "HIVE", "HBD", 1.0);
                                    String steemRewards = parseRewards(innerRewards, "STEEM", "SBD", 1.0);
                                    String blurtRewards = parseRewards(innerRewards, "BLURT", "BLURT", blurtPrice);
                                    //update the text message as dialog is already showing
                                    String msg = "";

                                    msg += !hiveRewards.equals("") ? hiveRewards:"";
                                    msg += !steemRewards.equals("") ? steemRewards:"";
                                    msg += !blurtRewards.equals("") ? blurtRewards:"";
                                    if (!msg.equals("")){
                                        //we have some pending rewards, show popup
                                        msg = getString(R.string.pending_rewards_header) + "\r\n" + msg;

                                        msg += "\r\n";
                                        msg += getString(R.string.pending_rewards_note);
                                        pendingRewardsDialogBuilder.setMessage(Html.fromHtml(msg));
                                    }
                                }
                            } catch (JSONException jsex) {
                                jsex.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //hide dialog
                            //error.printStackTrace();
                            Log.e(MainActivity.TAG, "error fetching blurt price");
                        }
                    });

            queue.add(blurtPriceReq);

            //fetch user pending rewards and display notification

            // This holds the url to connect to the API and grab the pending rewards.
            // We append to it the username
            String userPendingRewardsUrl = Utils.apiUrl(this)+getString(R.string.user_pending_rewards_url) + username;

            // Request the rank of the user while expecting a JSON response
            JsonObjectRequest pendRewardsRequest = new JsonObjectRequest
                    (Request.Method.GET, userPendingRewardsUrl, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            // Display the result
                            try {
                                innerRewards = response.getJSONObject("pendingRewards");

                                //display alert dialog about pending rewards
                                pendingRewardsDialogBuilder = new AlertDialog.Builder(ctx);
                                String msg = "";

                                String hiveRewards = parseRewards(innerRewards, "HIVE", "HBD", 1.0);
                                String steemRewards = parseRewards(innerRewards, "STEEM", "SBD", 1.0);
                                String blurtRewards = parseRewards(innerRewards, "BLURT", "BLURT", blurtPrice);
                                //update the text message as dialog is already showing

                                msg += !hiveRewards.equals("") ? hiveRewards:"";
                                msg += !steemRewards.equals("") ? steemRewards:"";
                                msg += !blurtRewards.equals("") ? blurtRewards:"";


                                if (!msg.equals("")){
                                    //we have some pending rewards, show popup
                                    msg = getString(R.string.pending_rewards_header) + "\r\n" + msg;

                                    msg += "\r\n";
                                    msg += getString(R.string.pending_rewards_note);

                                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    //take user to activity list on web

                                                    //private void openUserRank(SharedPreferences sharedPreferences){
                                                    username = sharedPreferences.getString("actifitUser", "");
                                                    if (username != "") {
                                                        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

                                                        builder.setToolbarColor(getResources().getColor(R.color.actifitRed));

                                                        //animation for showing and closing fitbit authorization screen
                                                        builder.setStartAnimations(ctx, R.anim.slide_in_right, R.anim.slide_out_left);

                                                        //animation for back button clicks
                                                        builder.setExitAnimations(ctx, android.R.anim.slide_in_left,
                                                                android.R.anim.slide_out_right);

                                                        CustomTabsIntent customTabsIntent = builder.build();

                                                        customTabsIntent.launchUrl(ctx, Uri.parse(MainActivity.ACTIFIT_CORE_URL + "/" + getString(R.string.activity_url_link) + "/" + username));
                                                    }
                                                    //}

                                                    break;

                                                case DialogInterface.BUTTON_NEUTRAL:
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putBoolean(getString(R.string.donotshowrewards), true);
                                                    editor.commit();

                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    //cancel
                                                    break;
                                            }
                                        }
                                    };



                                    AlertDialog pointer = pendingRewardsDialogBuilder.setMessage(Html.fromHtml(msg))
                                            .setTitle(getString(R.string.pending_rewards_title))
                                            .setIcon(getResources().getDrawable(R.drawable.actifit_logo))
                                            .setPositiveButton(getString(R.string.my_activity_button), dialogClickListener)
                                            .setNegativeButton(getString(R.string.close_button), dialogClickListener)
                                            .setNeutralButton(getString(R.string.do_not_show_again), dialogClickListener).create();

                                    pendingRewardsDialogBuilder.show();
                                    /*pointer.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                    pointer.getWindow().getDecorView().setBackground(getDrawable(R.drawable.dialog_shape));
                                    //if (pointer.getWindow().isActive()) {
                                    pointer.show();*/

                                }



                            }catch (JSONException e) {
                                //hide dialog
                                e.printStackTrace();
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //hide dialog
                            //error.printStackTrace();
                            Log.e(MainActivity.TAG, "error fetching pending rewards");
                        }
                    });

            queue.add(pendRewardsRequest);

        }
    }

    private void loadSignupLinks(RequestQueue queue) {
        String signupLinksUrl = Utils.apiUrl(this)+getString(R.string.my_free_signup_links) + "?user=" + MainActivity.username;

        // Process claim rewards request
        JsonObjectRequest req = new JsonObjectRequest
                (Request.Method.GET, signupLinksUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("result")) {
                            try {
                                freeSignupLinks = response.getJSONArray("result");
                                if (referLayout != null) {
                                    loadAndUpdateSignupData();
                                }
                            }catch(Exception excp){
                                excp.printStackTrace();
                            }
                        }
                    }
                }, error -> {
                    //hide dialog
                    error.printStackTrace();
                    //displayNotification(error_notification, null, callerContext, callerActivity, false);
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put(getString(R.string.validation_header), getString(R.string.validation_pre_data) + " " + LoginActivity.accessToken);
                return params;
            }
        };

        queue.add(req);

    }

    private void claimFreeSignupLinks(RequestQueue queue){
        String claimLink = Utils.apiUrl(this)+getString(R.string.claim_free_signup_links) + username;
        // Request the user's active gadgets list
        JsonObjectRequest claimableSignupsRequest = new JsonObjectRequest
                (Request.Method.GET, claimLink, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // grab result
                        try {

                            if (response.has("status") && response.getString("status").equals("success")) {
                                //claimed already
                                userCanClaimSignupLinks = false;
                                loadSignupLinks(queue);
                            }
                        } catch (Exception exc) {

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(claimableSignupsRequest);

    }

    private void loadClaimableSignupLinks(RequestQueue queue){
        String claimableSignups = Utils.apiUrl(this)+getString(R.string.claimable_free_signup_links)+username;


        // Request the user's active gadgets list
        JsonObjectRequest claimableSignupsRequest = new JsonObjectRequest
                (Request.Method.GET, claimableSignups, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // Display the result
                        try {
                            //grab current user rank
                            if (response.has("status") && response.getBoolean("status")) {
                                userCanClaimSignupLinks = true;
                            }
                        } catch (Exception exc) {

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(claimableSignupsRequest);

    }

    private void loadReferrals(RequestQueue queue){

        String usersReferralsUrl = Utils.apiUrl(this)+getString(R.string.user_referrals_url) + username;


        JsonArrayRequest referralDataRequest = new JsonArrayRequest(Request.Method.GET,
                usersReferralsUrl, null, new Response.Listener<JSONArray>(){

            @Override
            public void onResponse(JSONArray listArray) {

                // Handle the result
                try {
                    userReferrals = listArray;
                    /*if (userReferrals!=null && userReferrals.length() > 0) {
                        TextView successfulReferral = findViewById(R.id.success_referrals);

                        successfulReferral.setTextColor(ctx.getResources().getColor(R.color.actifitDarkGreen));
                        successfulReferral.setText(Html.fromHtml(checkMark +userReferrals.length()));
                    }*/
                }catch (Exception e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(referralDataRequest);
    }

    private void displayDailyTip() {
        SharedPreferences sharedPreferences = getSharedPreferences("actifitSets",MODE_PRIVATE);

        Boolean skipShowingTips = (sharedPreferences.getBoolean(getString(R.string.donotshowtips), false));
        if (skipShowingTips){
            return;
        }
        RequestQueue queue = Volley.newRequestQueue(this);

        //load all product details
        String dailyTipUrl = Utils.apiUrl(this)+getString(R.string.daily_tip_url);

        JsonArrayRequest productsListReq = new JsonArrayRequest(Request.Method.GET,
                dailyTipUrl, null, new Response.Listener<JSONArray>(){

            @Override
            public void onResponse(JSONArray listArray) {
                //hide dialog
                //progress.hide();

                // Handle the result
                try {
                    dailyTip = listArray;
                    //show tip dialog

                    //grab random tip
                    if (dailyTip!=null && dailyTip.length()>0) {
                        Random rand = new Random();

                        JSONObject tipData = dailyTip.getJSONObject(rand.nextInt(dailyTip.length()));
                        if (tipData.has("tip")) {

                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {

                                        case DialogInterface.BUTTON_NEUTRAL:
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putBoolean(getString(R.string.donotshowtips), true);
                                            editor.commit();

                                    }
                                }
                            };

                            AlertDialog.Builder tipDialogBuilder = new AlertDialog.Builder(ctx);
                            View tipLayout = getLayoutInflater().inflate(R.layout.daily_tip, null);
                            TextView tipContent = tipLayout.findViewById(R.id.tip_content);
                            tipContent.setText(tipData.getString("tip"));
                            AlertDialog pointer = tipDialogBuilder
                                    .setView(tipLayout)
                                    //.setMessage(tipData.getString("tip"))
                                    .setTitle(getString(R.string.random_tip))
                                    .setIcon(getResources().getDrawable(R.drawable.actifit_logo))
                                    .setNeutralButton(getString(R.string.do_not_show_again), dialogClickListener)
                                    .setPositiveButton(getString(R.string.close_button), null).create();
                            Button nextBtn = tipLayout.findViewById(R.id.nextButton);
                            nextBtn.setOnClickListener(view ->{
                                try {
                                    Random randInner = new Random();
                                    JSONObject tipInnerData = dailyTip.getJSONObject(randInner.nextInt(dailyTip.length()));
                                    tipContent.setText(tipInnerData.getString("tip"));
                                }catch(Exception ex){

                                }
                            });

                            tipLayout.findViewById(R.id.previousButton).setOnClickListener(view ->{
                                nextBtn.performClick();
                            });

                            tipDialogBuilder.show();
                            /*
                            pointer.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                            pointer.getWindow().getDecorView().setBackground(getDrawable(R.drawable.dialog_shape));
                            //if (pointer.getWindow().isActive()) {
                                pointer.show();
                            //}

                             */
                        }
                    }

                    //actifitTransactions.setText("Response is: "+ response);
                }catch (Exception e) {
                    //hide dialog
                    //progress.hide();
                    //actifitTransactionsError.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //hide dialog
                //progress.hide();
                //actifitTransactionsView.setText("Unable to fetch balance");
                //actifitTransactionsError.setVisibility(View.VISIBLE);
            }
        });

        queue.add(productsListReq);

    }

    //handles fetching and displaying current user and rank
    private void displayUserGadgets(){
        //grab stored value, if any
        final SharedPreferences sharedPreferences = getSharedPreferences("actifitSets",MODE_PRIVATE);
        username = sharedPreferences.getString("actifitUser","");
        if (username != "") {

            RequestQueue queue = Volley.newRequestQueue(this);

            //load all product details
            String productsUrl = Utils.apiUrl(this)+getString(R.string.products_link);

            JsonArrayRequest productsListReq = new JsonArrayRequest(Request.Method.GET,
                    productsUrl, null, new Response.Listener<JSONArray>(){

                @Override
                public void onResponse(JSONArray listArray) {
                    //hide dialog
                    //progress.hide();

                    // Handle the result
                    try {
                        productsList = listArray;

                        //attempt to populate in case this gets executed later
                        populateActiveProducts();
                        //actifitTransactions.setText("Response is: "+ response);
                    }catch (Exception e) {
                        //hide dialog
                        //progress.hide();
                        //actifitTransactionsError.setVisibility(View.VISIBLE);
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //hide dialog
                    //progress.hide();
                    //actifitTransactionsView.setText("Unable to fetch balance");
                    //actifitTransactionsError.setVisibility(View.VISIBLE);
                }
            });

            queue.add(productsListReq);

            //username="pjansen";

            String activeGadgetsListUrl = Utils.apiUrl(this)+getString(R.string.active_gadgets_url) + username;

            // Request the user's active gadgets list
            JsonObjectRequest activeGadgetsRequest = new JsonObjectRequest
                    (Request.Method.GET, activeGadgetsListUrl, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            // Display the result
                            try {
                                //grab current user rank
                                if (response.has("own")) {
                                    activeProducts = response.getJSONArray("own");

                                    //display images of active products alongside count

                                    //List<String> listItems = new ArrayList<String>();
                                    populateActiveProducts();

                                }

                            } catch (JSONException e) {
                                //hide dialog
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //hide dialog
                            //error.printStackTrace();
                            Log.e(MainActivity.TAG, "error fetching gadgets");
                        }
                    });

            // Add balance request to be processed
            queue.add(activeGadgetsRequest);



        }else{
            //display text no gadgets loaded
            //LinearLayout noActiveGadgets = findViewById(R.id.missing_active_gadgets_container);
            TextView noActiveGadgets = findViewById(R.id.missing_active_gadgets);
            noActiveGadgets.setVisibility(View.VISIBLE);

        }

    }

    private void populateActiveProducts(){
        if (activeProducts!=null && productsList!=null &&
                activeProducts.length()>0 && productsList.length()>0) {

            if (gadgetsll!=null){
                userGadgets.removeView(gadgetsll);
            }

            gadgetsll = new LinearLayout(getApplicationContext());

            userGadgets.addView(gadgetsll);

            //hide no gadgets display as we do have active gadgets
            //LinearLayout noActiveGadgets = findViewById(R.id.missing_active_gadgets_container);
            TextView noActiveGadgets = findViewById(R.id.missing_active_gadgets);
            noActiveGadgets.setVisibility(View.GONE);
            for (int i = 0; i < activeProducts.length(); i++) {
                try {
                    //find matching image
                    JSONObject curProd = activeProducts.getJSONObject(i);
                    if (curProd.has("gadget")) {


                        String imgUrl = findMatchingProductImage(curProd.getString("gadget"), "_id", productsList, "image");

                        if (!imgUrl.equals("")) {

                            //LinearLayout pll = new LinearLayout(getApplicationContext());

                            FrameLayout fl = new FrameLayout(getApplicationContext());

                            //add image
                            ImageView iv = new ImageView(getApplicationContext());
                            iv.setScaleType(ImageView.ScaleType.CENTER);
                            //fl.setOrientation(LinearLayout.VERTICAL);
                            fl.addView(iv);


                            //add level
                            TextView tv = new TextView(getApplicationContext());
                            tv.setGravity(Gravity.BOTTOM | Gravity.RIGHT);

                            tv.setText(curProd.getString("gadget_level"));
                            tv.setHeight(10);
                            tv.setWidth(10);
                            tv.setTextSize(10);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                //tv.setBackgroundColor(getColor(R.color.actifitRed));
                                tv.setTextColor(getColor(R.color.actifitRed));
                            }
                            fl.addView(tv);

                            //pll.addView(fl);

                            //add layout to container
                            //userGadgets.addView(fl);
                            gadgetsll.addView(fl);
                            /*
                            ImageView iv = new ImageView(ctx);
                            //iv.setImage

                            //append extra image url on actifit
                            //imgUrl = getString(R.string.actifit_gadget_image)+imgUrl;

                            userGadgets.addView(iv);*/

                            Handler uiHandler = new Handler(Looper.getMainLooper());
                            uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Picasso.with(ctx)
                                    Picasso.get()
                                            .load(getString(R.string.actifit_gadget_image) + imgUrl)
                                            .into(iv);
                                }
                            });
                        }

                        //listItems.add(afitMarkets.getJSONObject(i).getString("exchange"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public String findMatchingProductImage(String needle, String matchfield, JSONArray haystack, String returnfield){
        if (haystack!=null && haystack.length()>0) {
            for (int i = 0; i < haystack.length(); i++) {
                try {
                    JSONObject match = haystack.getJSONObject(i);
                    if (match.has(matchfield) && match.getString(matchfield).equals(needle) && match.has(returnfield)) {
                        return match.getString(returnfield);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    //handles fetching and displaying current user and rank
    private void displayUserAndRank(){
        //grab stored value, if any
        final SharedPreferences sharedPreferences = getSharedPreferences("actifitSets",MODE_PRIVATE);
        username = sharedPreferences.getString("actifitUser","");
        if (username != "") {
            //greet user if user identified
            final TextView welcomeUser = findViewById(R.id.welcome_user);
            final TextView userRankTV = findViewById(R.id.user_rank);

            LinearLayout userRankContainer = findViewById(R.id.rank_container);
            userRankContainer.setVisibility(View.VISIBLE);

            //hide login, show logout
            //logoutLink.setVisibility(View.VISIBLE);
            topIconsContainer.setVisibility(View.VISIBLE);

            //loginLink.setVisibility(View.GONE);
            loginContainer.setVisibility(View.GONE);



            //load unread notification count
            RequestQueue queue = Volley.newRequestQueue(ctx);
            loadNotifCount(queue);

            //load chat notifications
            loadChatNotif(queue);

            //load user settings
            Utils.loadUserSettings(queue, ctx);

            //display profile pic too
            final String userImgUrl = getString(R.string.hive_image_host_url).replace("USERNAME", username);
            final ImageView userProfilePic = findViewById(R.id.user_profile_pic);


            Handler uiHandler = new Handler(Looper.getMainLooper());
            uiHandler.post(new Runnable(){
                @Override
                public void run() {
                    //Picasso.with(ctx)
                    //load user image
                    Picasso.get()
                            .load(userImgUrl)
                            .into(userProfilePic);
                }
            });


            //TODO: check on implementation of background for actifit. This is ready to go, just need proper images
            /*
            Handler uiAltHandler = new Handler(Looper.getMainLooper());
            uiAltHandler.post(new Runnable(){
                @Override
                public void run() {
                    //Picasso.with(ctx)
                    //load custom background image
                    String url = "https://actifit.io/img/header-4.png";
                    LinearLayout mainLayout = MainActivity.this.findViewById(R.id.main_layout_container);
                    Picasso.get()
                            .load(url)
                            //.placeholder()
                            .into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    mainLayout.setBackground(new BitmapDrawable(bitmap));
                                    mainLayout.refreshDrawableState();
                                }

                                @Override
                                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                    Toast.makeText(MainActivity.this, "Error : loading wallpaper", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                }
                            });

                }
            });
*/


            //Picasso.with(ctx).load(userImgUrl).into(userProfilePic);

            //grab user rank if it is already stored today
            userRank = sharedPreferences.getString("userRank", "");
            String userRankUpdateDate =
                    sharedPreferences.getString("userRankUpdateDate", "");
            Boolean fetchNewRankVal = false;
            if (userRank.equals("") || userRankUpdateDate.equals("")){
                fetchNewRankVal = true;
            }else{
                //make sure last value is at least within same day, otherwise grab new val
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                String strDate = dateFormat.format(date);
                if (Integer.parseInt(userRankUpdateDate)<Integer.parseInt(strDate)){
                    fetchNewRankVal = true;
                }

            }

            //set username
            welcomeUser.setText("@"+username);


            if (!fetchNewRankVal){
                //we already have the rank, display the message and the rank
                //welcomeUser.setText(getString(R.string.welcome_user).replace("USER_NAME", username).replace("USER_RANK","("+userRank+")"));
                userRankTV.setText(userRank+"");

            }else {
                //need to fetch user rank data from API

                //handles sending out API query requests
                //RequestQueue queue = Volley.newRequestQueue(this);

                // This holds the url to connect to the API and grab the user rank.
                // We append to it the username
                String userRankUrl = Utils.apiUrl(this)+getString(R.string.user_rank_api_url) + username;

                // Request the rank of the user while expecting a JSON response
                JsonObjectRequest rankRequest = new JsonObjectRequest
                        (Request.Method.GET, userRankUrl, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                // Display the result
                                try {
                                    //grab current user rank
                                    String userRank = response.getString("user_rank");

                                    //store user rank along with date updated
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("userRank", userRank);

                                    Date date = new Date();
                                    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                                    String strDate = dateFormat.format(date);

                                    editor.putString("userRankUpdateDate", strDate);
                                    editor.commit();

                                    //welcomeUser.setText(getString(R.string.welcome_user).replace("USER_NAME", username).replace("USER_RANK", "(" + userRank + ")"));
                                    userRankTV.setText(userRank+"");
                                } catch (JSONException e) {
                                    //hide dialog
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //hide dialog
                                //error.printStackTrace();
                                Log.e(MainActivity.TAG, "error fetching rank");
                            }
                        });

                // Add balance request to be processed
                queue.add(rankRequest);


            }
        }else{
            //hide logout, show login
            //logoutLink.setVisibility(View.GONE);
            topIconsContainer.setVisibility(View.GONE);
            loginContainer.setVisibility(View.VISIBLE);
        }
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validate input values
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(intent);

            }

        });
        /*logoutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove logged in credentials
                final SharedPreferences sharedPreferences = getSharedPreferences("actifitSets",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("actifitUser");
                editor.remove("actifitPst");

                editor.remove("userRank");
                editor.remove("userRankUpdateDate");
                editor.remove("actvKey");

                editor.apply();
                LoginActivity.accessToken = "";
                MainActivity.username = "";
                MainActivity.userRank = "";
                MainActivity.userFullBalance = 0.0;
                LoginActivity.accessToken = "";
                finish();
                overridePendingTransition( 0, 0);
                //startActivity(getIntent());
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(intent);
                overridePendingTransition( 0, 0);

            }

        });*/

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

                builder.setToolbarColor(getResources().getColor(R.color.actifitRed));

                //animation for showing and closing fitbit authorization screen
                builder.setStartAnimations(ctx, R.anim.slide_in_right, R.anim.slide_out_left);

                //animation for back button clicks
                builder.setExitAnimations(ctx, android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);

                CustomTabsIntent customTabsIntent = builder.build();

                customTabsIntent.launchUrl(ctx, Uri.parse(Utils.apiUrl(ctx)+getString(R.string.signup_link)));
            }

        });
    }




    public static String parseRewards(JSONObject innerRewards, String chain, String currency, Double price){
        try {
            if (innerRewards.has(chain) ){
                JSONObject rewards = innerRewards.getJSONObject(chain);
                if (rewards.has("amount")){
                    Double value = Double.parseDouble(rewards.getString("amount")) * price;
                    //String imgUrl = getString(R.string.actifit_image) + chain.toUpperCase() +".png";
                    //return "<li> $"+value.toString()+" ("+rewards.getString("amount") + " "+currency+" <img src="+imgUrl + " width='20px' height='20px' style='width: 20px; height: 20px;' />)</li>";
                    if (value>0) {
                        return "<li> $" + formatValue(value) + " (" + rewards.getString("amount") + " in " + currency + " )</li>";
                    }else{
                        return "";
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter("ACTIFIT_SERVICE")
        );

    }


    private void updateLang(int selectedLang){
        LocaleManager.updateLangChoice(this,selectedLang);
        recreate();
    }

    private void displayVotingStatus(){
        RequestQueue queue = Volley.newRequestQueue(this);

        //grab reward distribution status/timer
        String votingStatusUrl = Utils.apiUrl(this)+getString(R.string.voting_api_url);
        // Request the balance of the user while expecting a JSON response
        JsonObjectRequest votingStatusRequest = new JsonObjectRequest
                (Request.Method.GET, votingStatusUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //hide dialog
                        //progress.hide();
                        try {
                            //validate if account exists on the chains
                            if (response.has("status")) {
                                JSONObject status = response.getJSONObject("status");

                                if (status.has("is_voting")) {
                                    if (!status.getBoolean("is_voting") && response.has("reward_start")){
                                        votingStatusText.setText(response.getString("reward_start"));
                                        votingStatusText.setSelected(false);
                                    }else if (status.getBoolean("is_voting")){
                                        votingStatusText.setText(getString( R.string.rewards_processing));
                                        votingStatusText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                        votingStatusText.setMarqueeRepeatLimit(-1);
                                        votingStatusText.setSelected(true);
                                    }
                                }
                            }else{
                                //accountRCContainer.setVisibility(View.GONE);
                            }

                        } catch (Exception ex) {


                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //hide dialog

                    }
                });

        queue.add(votingStatusRequest);

        //handle RC click
        votingStatusContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder rcDialogBuilder = new AlertDialog.Builder(ctx);
                String msg = getString(R.string.reward_cycle_description);
                AlertDialog pointer = rcDialogBuilder.setMessage(Html.fromHtml(msg))
                        .setTitle(getString(R.string.reward_cycle_title))
                        .setIcon(getResources().getDrawable(R.drawable.actifit_logo))
                        .setNegativeButton(getString(R.string.close_button), null).create();

                rcDialogBuilder.show();
                /*pointer.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                pointer.getWindow().getDecorView().setBackground(getDrawable(R.drawable.dialog_shape));
                pointer.show();
                */
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            public void run() {
                runOnUiThread(() -> {
                    displayDate();

                    displayUserAndRank();

                    displayUserBalance();

                    displayVotingStatus();

                    displayUserGadgets();

                    MainActivity.isActivityVisible =true;

                    //if (!mStepsDBHelper.isConnected()){
//                    mStepsDBHelper.reConnect();
                    //}

                    //displayDayChartData(true);
                    DisplayDayChartDataAsyncTask dispChartData = new DisplayDayChartDataAsyncTask(true);
                    dispChartData.execute(true);

                    //displayChartData(true);
                    DisplayChartDataAsyncTask dispCData = new DisplayChartDataAsyncTask(true);
                    dispCData.execute(true);


                    ResumeAsyncTask resumeAsyncTask = new ResumeAsyncTask();
                    resumeAsyncTask.execute();

                    checkBatteryOptimization(false);
                });

                //LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                //       new IntentFilter("ACTIFIT_SERVICE")
                //);
            }
        }).start();
    }

    @Override
    protected void onStop() {
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
        this.isActivityVisible = false;
    }

    @Override
    protected void onPause() {
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onPause();
        this.isActivityVisible = false;
    }

    /* preventing accidental single back button click leading to exiting the app and losing counter tracking */
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.back_exit_confirmation), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onDestroy(){
        //sensorManager.unregisterListener(this);
        /*isListenerActive = false;
        try{
            if (mServiceIntent!=null) {
                stopService(mServiceIntent);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        this.isActivityVisible = false;

        mStepsDBHelper.closeConnection();

        PowerManager.WakeLock wl = ActivityMonitorService.getWakeLockInstance();
        if (wl!=null && wl.isHeld()) {
            Log.d(MainActivity.TAG,">>>>[Actifit]Settings AGG MODE OFF");
            wl.release();
        }*/

        //mStepsDBHelper.closeConnection();

        super.onDestroy();

        Log.d(TAG,">>>> Actifit destroy state");
    }

    private class ResumeAsyncTask extends AsyncTask<Void, Void, String[]> {
        @Override
        protected String[] doInBackground(Void... voids) {


            //ensure our tracking is active particularly after leaving settings
            final SharedPreferences sharedPreferences = getSharedPreferences("actifitSets", MODE_PRIVATE);

            //only start the tracking service if the device sensors is picked as tracking medium
            String dataTrackingSystem = sharedPreferences.getString("dataTrackingSystem", getString(R.string.device_tracking_ntt));
            String aggModeEnabled = sharedPreferences.getString("aggressiveBackgroundTracking", getString(R.string.aggr_back_tracking_off_ntt));
            String [] result = {dataTrackingSystem, aggModeEnabled};
            return result;
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);

            if (result[0].equals(getString(R.string.device_tracking_ntt))) {

                if (!isMyServiceRunning(mSensorService.getClass())) {
                    //initiate the monitoring service
                    if (mSensorService == null){
                        mSensorService = new ActivityMonitorService(getCtx());
                    }
                    if (mServiceIntent == null){
                        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
                    }
                    //startService(mServiceIntent);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(mServiceIntent);
                    }
                    else {
                        startService(mServiceIntent);
                    }

                    //enable aggressive mode if set
                    String aggModeEnabled = result[1];
                    if (aggModeEnabled.equals(getString(R.string.aggr_back_tracking_on_ntt))) {
                        //enable wake lock to ensure tracking functions in the background
                        PowerManager.WakeLock wl = ActivityMonitorService.getWakeLockInstance();
                        if (wl == null) {
                            //initialize power manager and wake locks either way
                            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                            wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getString(R.string.actifit_wake_lock_tag));
                        }
                        if (!wl.isHeld()) {
                            Log.d(MainActivity.TAG, ">>>>[Actifit]Settings AGG MODE ON");
                            wl.acquire();
                        }
                    }
                }
                /*thirdPartyTracking.setVisibility(View.GONE);
                dayChartButton.setVisibility(View.GONE);
                fullChartButton.setVisibility(View.VISIBLE);*/
            }else {
                //stepDisplay = findViewById(R.id.step_display);
                //inform user that fitbit mode is on
                //stepDisplay.setText(getString(R.string.fitbit_tracking_mode_active));
                //thirdPartyTracking.setVisibility(View.VISIBLE);
                //hideCharts();
            }

            //update language in case it was adjusted
            if (SettingsActivity.languageModified) {
                updateLang(SettingsActivity.langChoice);
            }

        }

    }

    private class PrepareGround extends AsyncTask<Void, Void, Void> {
        String dataTrackingSystem;
        int stepCount = 0;

        @Override
        protected Void doInBackground(Void... voids) {


            //Looper.prepare();
            mStepsDBHelper = new StepsDBHelper(ctx);

            //initiate the monitoring service
            mSensorService = new ActivityMonitorService(getCtx());
            mServiceIntent = new Intent(getCtx(), mSensorService.getClass());

            //retrieving account data for simple reuse. Data is not stored anywhere outside actifit App.
            final SharedPreferences sharedPreferences = getSharedPreferences("actifitSets",MODE_PRIVATE);

            /*************** security features ********************/

            //check if signature has been tampered with

            if (getString(R.string.sec_check_signature).equals("on")) {
                if ((getString(R.string.test_mode).equals("off"))
                        && checkAppSignature(ctx) == MainActivity.INVALID) {
                    //package signature has been manipulated
                    Log.d(TAG, ">>>>[Actifit] Package signature has been manipulated");
                    killActifit(getString(R.string.security_concerns));
                }


                //make sure package name has not been manipulated
                if (!ctx.getPackageName().equals("io.actifit.fitnesstracker.actifitfitnesstracker")) {
                    //package name has been manipulated
                    Log.d(TAG, ">>>>[Actifit] Package name has been manipulated");
                    killActifit(getString(R.string.security_concerns));
                }

                //let's make sure this is a smart phone device by checking SIM Card

                //Crashlytics.getInstance().crash();

                if (!isSimAvailable()) {
                    //no valid active sim card detected
                    Log.d(TAG, ">>>>[Actifit] No valid SIM card detected");
                    killActifit(getString(R.string.no_valid_sim));
                }

                //also let's try to detect if this is a known emulator
                if (isEmulator()) {
                    Log.d(TAG, ">>>>[Actifit] Emulator detected");
                    killActifit(getString(R.string.emulator_device));
                }

                //check if device is rooted
                RootBeer rootBeer = new RootBeer(ctx);
                if (getString(R.string.test_mode).equals("off") && rootBeer.isRootedWithoutBusyBoxCheck()) {
                    Log.d(TAG, ">>>>[Actifit] Device is rooted");
                    killActifit(getString(R.string.device_rooted));
                }

            }
            //require now for GDPR and ad display
            loadConsentData(false);


            //check if user has a proper unique ID already, if not generate one
            String actifitUserID = sharedPreferences.getString("actifitUserID","");
            if (actifitUserID.equals("")) {
                actifitUserID = UUID.randomUUID().toString();
                try{
                    PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    String version = pInfo.versionName;
                    actifitUserID += version;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("actifitUserID", actifitUserID);
                editor.apply();
            }

            //Log.d(TAG,"actifitUserID:"+actifitUserID);


            //only start the tracking service if the device sensors is picked as tracking medium
            dataTrackingSystem = sharedPreferences.getString("dataTrackingSystem",getString(R.string.device_tracking_ntt));
            if (dataTrackingSystem.equals(getString(R.string.device_tracking_ntt))) {

                if (!isMyServiceRunning(mSensorService.getClass())) {
                    //startService(mServiceIntent);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(mServiceIntent);
                    }
                    else {
                        startService(mServiceIntent);
                    }
                }

                stepCount = mStepsDBHelper.fetchTodayStepCount();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void param) {
            super.onPostExecute(param);

            //display current date
            displayDate();

            //display user info
            displayUserAndRank();

            displayUserBalance();

            displayVotingStatus();

            displayPendingRewards();

            //if (!mStepsDBHelper.isConnected()){
//                mStepsDBHelper.reConnect();
            //}

            //display today's chart data
            DisplayDayChartDataAsyncTask dispChartData = new DisplayDayChartDataAsyncTask(true);
            dispChartData.execute(true);

            //display all historical data
            DisplayChartDataAsyncTask dispCData = new DisplayChartDataAsyncTask(true);
            dispCData.execute(true);

            //only display activity count from device if device mode is on
            if (dataTrackingSystem.equals(getString(R.string.device_tracking_ntt))) {

                //display step count while ensuring we don't display negative value if no steps tracked yet
                /*
                stepDisplay.setText(getString(R.string.activity_today_string) + (stepCount < 0 ? 0 : stepCount));

                //adjust color of step account according to milestone achieved
                if (stepCount >= 10000 ){
                    stepDisplay.setTextColor(getResources().getColor(R.color.actifitGreen));
                }else if (stepCount >= 5000 ){
                    stepDisplay.setTextColor(getResources().getColor(R.color.actifitRed));
                }else {
                    stepDisplay.setTextColor(getResources().getColor(android.R.color.tab_indicator_text));
                }*/

                //display relevant chart
                displayActivityChart(stepCount, true);


            }else{
                //inform user that fitbit mode is on
                //stepDisplay.setText(getString(R.string.fitbit_tracking_mode_active));
                hideCharts();
            }

        }
    }

    private void renderChatData(){
        chatNotifCount.setVisibility(View.VISIBLE);

        // Create a new ValueAnimator object.
        if (valueAnimator == null) {
            valueAnimator = new ValueAnimator();

            // Set the duration of the animation.
            valueAnimator.setDuration(1000);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);

            // Set the valueFrom and valueTo properties of the ValueAnimator object.
            //valueAnimator.setFloatValues(Color.RED, Color.parseColor("#FFFFFF"), Color.RED);
            valueAnimator.setFloatValues(getResources().getColor(R.color.actifitRed), getResources().getColor(R.color.colorWhite));//Color.parseColor("#FFFFFF"));


            // Add an AnimatorUpdateListener to the ValueAnimator object.
            valueAnimator.addUpdateListener(animator -> {
                // Set the background color of the TextView object to the current value of the ValueAnimator object.
                int animatedValue = (int) (float) animator.getAnimatedValue();

                chatNotifCount.setTextColor(animatedValue);
            });
        }

// Start the ValueAnimator object.
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
        }

    }

    private void hideChatData(){
        chatNotifCount.setVisibility(View.GONE);
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
    }

    //check community notifications
    private void loadCommunityNotif(RequestQueue queue, String lastChatDate, int commChatCount){

        String notificationsUrl = getString(R.string.sting_chat_query_comm);

        // Request the transactions of the user first via JsonArrayRequest
        // according to our data format
        JsonArrayRequest transactionRequest = new JsonArrayRequest(Request.Method.GET,
                notificationsUrl, null, notificationsListArray -> {
            try {
                JSONObject todayObj = null;
                if (notificationsListArray.length() > 0) {
                    //today's stats
                    todayObj = notificationsListArray.getJSONArray(1).getJSONArray(0).getJSONObject(6);//dual array structure
                    //we have new messages today
                    if (todayObj.has(getString(R.string.actifit_comm))){
                        //new messages today, notify user
                        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                        String todayDate = outputFormat.format(new Date());
                        lastChatCount = todayObj.getInt(getString(R.string.actifit_comm));
                        if ((Integer.parseInt(todayDate) > Integer.parseInt(lastChatDate))){
                            //storeNotifDate(null, notifDate);
                            renderChatData();
                        }else if ((Integer.parseInt(todayDate) == Integer.parseInt(lastChatDate))
                                && lastChatCount > commChatCount){
                            storeNotifCount(lastChatCount);
                            renderChatData();
                        }
                    }
                }

            }catch(Exception ex){
                ex.printStackTrace();
            }
        }, error -> {
            //hide dialog
            error.printStackTrace();

        });

        // Add transaction request to be processed
        queue.add(transactionRequest);
    }

    private void loadChatNotif(RequestQueue queue){
        //start by hiding notifierh
        hideChatData();

        //for testing, set custom date
        /*
        storeNotifDate(null, "20230924");
        storeNotifCount(0);

         */
        //grab last stored update date for sting chat notifications
        SharedPreferences sharedPreferences = getSharedPreferences("actifitSets",MODE_PRIVATE);
        String lastChatDate = sharedPreferences.getString(getString(R.string.sting_chat_update),"");
        int commChatCount = sharedPreferences.getInt(getString(R.string.sting_chat_comm_count),0);
        if (!lastChatDate.equals("")){//(false){//
            //we have a stored date, grab it

            // This holds the url to connect to the API and grab the transactions.
            // We append to it the username
            String notificationsUrl = getString(R.string.sting_chat_query_user).replace("_USER_", username);

            // Request the transactions of the user first via JsonArrayRequest
            // according to our data format
            JsonArrayRequest transactionRequest = new JsonArrayRequest(Request.Method.GET,
                    notificationsUrl, null, notificationsListArray -> {

                boolean foundNew = false;
                // Handle the result
                try {
                    JSONArray innerArray = null;
                    if (notificationsListArray.length() > 0  ){
                        innerArray = notificationsListArray.getJSONArray(1);
                    }
                    for (int i = 0; i < innerArray.length(); i++) {
                        try{
                            // Retrieve each JSON object within the JSON array
                            JSONObject jsonObject = innerArray.getJSONObject(i);

                            //SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault());
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
                            Date date = inputFormat.parse(jsonObject.getString("date"));

                            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                            String notifDate = outputFormat.format(date);
                            if ((Integer.parseInt(notifDate) > Integer.parseInt(lastChatDate))){
                                //storeNotifDate(null, notifDate);
                                renderChatData();
                                foundNew = true;
                                break;
                            }
                        }catch(Exception exc){
                            exc.printStackTrace();
                        }
                    }
                    if (!foundNew){
                        //check community notifications
                        loadCommunityNotif(queue, lastChatDate, commChatCount);
                    }

                    //actifitTransactions.setText("Response is: "+ response);
                }catch (Exception e) {
                    //hide dialog

                    e.printStackTrace();
                }
            }, error -> {
                //hide dialog
                error.printStackTrace();

            });


            // Add transaction request to be processed
            queue.add(transactionRequest);

        }else{
            //default case
            renderChatData();
        }

    }

    private void storeNotifCount(int commCount){
        SharedPreferences shPrefs = getSharedPreferences("actifitSets",MODE_PRIVATE);
        SharedPreferences.Editor editor = shPrefs.edit();
        editor.putInt(getString(R.string.sting_chat_comm_count), commCount);
        editor.commit();
    }

    private void storeNotifDate(Date date, String dateStr){
        SharedPreferences shPrefs = getSharedPreferences("actifitSets",MODE_PRIVATE);
        SharedPreferences.Editor editor = shPrefs.edit();

        //Date date = new Date();
        String strDate = "";
        if (!dateStr.equals("")){
            strDate = dateStr;
        }else{
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            strDate = dateFormat.format(date);
        }

        //String strDate = dateFormat.format(date);

        editor.putString(getString(R.string.sting_chat_update), strDate);
        editor.commit();
    }

    private void loadNotifCount(RequestQueue queue){
        String notificationsUrl = Utils.apiUrl(this)+getString(R.string.user_active_notifications_url)+MainActivity.username;
        notifCount.setText("");
        notifCount.setVisibility(View.GONE);
        // Request the transactions of the user first via JsonArrayRequest
        // according to our data format
        JsonArrayRequest transactionRequest = new JsonArrayRequest(Request.Method.GET,
                notificationsUrl, null, notificationsListArray -> {
            //set proper notif count
            if (notificationsListArray !=null && notificationsListArray.length() > 0) {
                String count = notificationsListArray.length() <1000 ? notificationsListArray.length()+"" : "999+";
                notifCount.setText(Html.fromHtml("<sup><small>"+count+"</small></sup>"));
                notifCount.setVisibility(View.VISIBLE);
            }
        }, error -> {

        });


        // Add transaction request to be processed
        queue.add(transactionRequest);
    }


    private void hideCharts(){
        dayChart = findViewById(R.id.main_today_activity_chart);
        btnPieChart = findViewById(R.id.step_pie_chart);
        dayChart.setVisibility(View.GONE);
        btnPieChart.setVisibility(View.GONE);
        fullChart = findViewById(R.id.main_history_activity_chart);
        fullChart.setVisibility(View.GONE);
        dayChartButton.setVisibility(View.GONE);
        fullChartButton.setVisibility(View.GONE);
        thirdPartyTracking.setVisibility(View.VISIBLE);
        LinearLayout barCharts = findViewById(R.id.bar_chart_container);
        barCharts.setVisibility(View.GONE);
    }


    public void showConsentForm(){
        consentForm.show(
                MainActivity.this,
                formError -> {
                    // Handle dismissal by reloading form.
                    loadForm(false);
                });
    }

    private void loadConsentData(Boolean goForAds){

        ConsentDebugSettings debugSettings = new ConsentDebugSettings.Builder(this)
                .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                .addTestDeviceHashedId(getString(R.string.test_app_id))
                .build();

        // Set tag for under age of consent. false means users are not under
        // age.
        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                //testing purposes
                //        .setConsentDebugSettings(debugSettings)
                .setTagForUnderAgeOfConsent(false)
                .build();

        consentInformation = UserMessagingPlatform.getConsentInformation(this);

        //testing purposes
        //consentInformation.reset();

        consentInformation.requestConsentInfoUpdate(
                this,
                params,
                () -> {
                    // The consent information state was updated.
                    // You are now ready to check if a form is available.
                    UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                            this,
                            (ConsentForm.OnConsentFormDismissedListener) loadAndShowError -> {
                                if (loadAndShowError != null) {
                                    // Consent gathering failed.
                                    Log.w(TAG, String.format("%s: %s",
                                            loadAndShowError.getErrorCode(),
                                            loadAndShowError.getMessage()));
                                }

                                // Consent has been gathered.
                                if (consentInformation.canRequestAds()) {
                                    //initializeMobileAdsSdk();
                                    prepareAds();
                                }
                            }
                    );

                    // Check if you can initialize the Google Mobile Ads SDK in parallel
                    // while checking for new consent information. Consent obtained in
                    // the previous session can be used to request ads.
                    if (consentInformation.canRequestAds()) {
                        prepareAds();
                    }

                    /*
                    if (consentInformation.isConsentFormAvailable()) {
                        loadForm(goForAds);
                    //no form is available, load ads
                    }else if (goForAds){
                        loadRewardedAd();
                    }*/
                },
                formError -> {
                    // Handle the error.
                    //Log.e(MainActivity.TAG, formError.getMessage());
                    if (goForAds){
                        loadRewardedAd();
                    }
                });
    }



    public void loadForm(Boolean goForAds) {
        // Loads a consent form. Must be called on the main thread.
        UserMessagingPlatform.loadConsentForm(
                this,
                consentForm -> {
                    MainActivity.this.consentForm = consentForm;
                    if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
                        consentForm.show(
                                MainActivity.this,
                                formError -> {
                                    if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.OBTAINED) {
                                        // App can start requesting ads.
                                        //Log.e(MainActivity.TAG, formError);
                                        if (goForAds){
                                            loadRewardedAd();
                                        }
                                    }
                                    // Handle dismissal by reloading form.
                                    loadForm(goForAds);
                                });
                    }
                },
                formError -> {
                    // Handle Error.
                    //Log.e(MainActivity.TAG, formError.getMessage());
                    if (goForAds){
                        loadRewardedAd();
                    }
                }
        );
    }
    public void getFitbitPieChartReset() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, ResetPieChart.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.d(MainActivity.TAG, "Alarm set for"+ calendar.getTime()+"daily");
    }


}
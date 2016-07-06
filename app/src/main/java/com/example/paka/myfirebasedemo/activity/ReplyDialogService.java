package com.example.paka.myfirebasedemo.activity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;




import java.util.Random;



/**
 * Created by Noth on 3/7/2559.
 */
public class ReplyDialogService extends Service implements View.OnClickListener , SharedPreferences.OnSharedPreferenceChangeListener {

    private EditText edtSendMessageNotification_Box;
    private ImageButton btnSendMessage;
    private ImageButton btnPrevious;
    private ImageButton btnNext;
    //private TextView noti_msg;
    private ScrollView textAreaScroller;
    private TextView txtGroupTitle;
    private Button btnClose;
    private Button btnShow;
    private LinearLayout layoutContentMessage;
    private LinearLayout layoutContentSticker;
    private ViewPager viewPager;
    private FrameLayout frameContent;
    private SamplePager samplePager;
    private ImageView imgTitle;
    private TextView txtCountMessage;

    //private ArrayList<JsonNotification> notificationArrayList = new ArrayList<>();
    private String TAG = ReplyDialogService.class.getSimpleName();
    private View mView;

    private String ACTION_DIALOG_START = "com.intent.action.start_dialog";
    private String ACTION_DIALOG_HIDDEN = "com.intent.action.hidden_dialog";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerIntentFilter();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();// instance of WindowManager
        //registerIntentFilter();
//        WindowManager mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
//        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        // inflate required layout file
//        mView = mInflater.inflate(R.layout.notification_box, null);
//
//        // attach OnClickListener
////        mView.findViewById(R.id.some_id).setOnClickListener(new View.OnClickListener() {
////
////            @Override
////            public void onClick(View v) {
////                // you can fire an Intent accordingly - to deal with the click event
////                // stop the service - this also removes `mView` from the window
////                // because onDestroy() is called - that's where we remove `mView`
////                stopSelf();
////            }
////        });
//
//        // the LayoutParams for `mView`
//        // main attraction here is `TYPE_SYSTEM_ERROR`
//        // as you noted above, `TYPE_SYSTEM_ALERT` does not work on the lockscreen
//        // `TYPE_SYSTEM_OVERLAY` works very well but is focusable - no click events
//        // `TYPE_SYSTEM_ERROR` supports all these requirements
//        WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0,
//                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
//                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
//                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
//                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
//                PixelFormat.RGBA_8888);
//
////        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getBaseContext());
////        AlertDialog mAlertDialog = mBuilder.create();
//
//        // finally, add the view to window
//        mWindowManager.addView(mView, mLayoutParams);
//
//        initViewDialog(mView);
//        registerIntentFilter();
        setPreference("onCreate");
        showDialog();
        //IReplyDialogActivity.getInstance().setActivityIsRunning(true);
    }

    @Override
    public void onDestroy() {

        // remove `mView` from the window
        //removeViewFromWindow();
        removeNow();
        setPreference("onDestroy");
        unregisterReceiver(onBroadcastReceiver);
        super.onDestroy();
    }

    private void setPreference(String preferenceValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor mEditor = preferences.edit();
        mEditor.putString("ReplyDialogServiceStart",preferenceValue);
        mEditor.apply();
    }

    private void initViewDialog(View v) {
        //Edit  Text
        edtSendMessageNotification_Box = (EditText) v.findViewById(R.id.edtSendMessageNotification_Box);
        //Button & Image Button
        btnSendMessage = (ImageButton) v.findViewById(R.id.btnSendMessage);
        btnPrevious = (ImageButton) v.findViewById(R.id.btnPrevious);
        btnNext = (ImageButton) v.findViewById(R.id.btnNext);
        btnClose = (Button) v.findViewById(R.id.btnClose);
        btnShow = (Button) v.findViewById(R.id.btnShow);
        //Text View
        txtGroupTitle = (TextView) v.findViewById(R.id.txtGroupTitle);
        txtCountMessage = (TextView) v.findViewById(R.id.txtCountMessage);
        //Image View
        imgTitle = (ImageView) v.findViewById(R.id.imgTitle);
        //Layout
        layoutContentMessage = (LinearLayout) v.findViewById(R.id.layoutContentMessage);
        layoutContentSticker = (LinearLayout) v.findViewById(R.id.layoutContentSticker);
        //View Pager
        viewPager = (ViewPager) v.findViewById(R.id.viewpagerContent);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        //samplePager = new SamplePager(getActivity());
        viewPager.setAdapter(samplePager);
        //Button Register Listener
        btnSendMessage.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnShow.setOnClickListener(this);
    }

    // Removes `mView` from the window
    public void removeNow() {
        if (mView != null) {
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.removeView(mView);
            mView = null;
        }
    }

    public void showDialog(){
        WindowManager mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // inflate required layout file
        mView = mInflater.inflate(R.layout.notification_box, null);
        mView.setTag(TAG);

        WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.TRANSPARENT);

//        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getBaseContext());
//        AlertDialog mAlertDialog = mBuilder.create();

        // finally, add the view to window
        mWindowManager.addView(mView, mLayoutParams);
        initViewDialog(mView);
        setPreference("onCreate");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSendMessage:
                Random r = new Random();
                String messageValue = "Test : "+ String.valueOf(r.nextInt(9999999));
                String lat = "";
                String lng = "";
                String chat_id = String.valueOf(r.nextInt(9999999));
                String message = "Test";
                Log.i(TAG," "+messageValue+" "+chat_id+" "+message);
                //mockupDataSamplePager(10);
                //messageManager.sendMessageToServer(messageValue,lat,lng,chat_id,group_id,title);
                break;

            case R.id.btnPrevious:
                int itemIndexPrevious = viewPager.getCurrentItem()-1;
                if(itemIndexPrevious >= 0) {
                    viewPager.setCurrentItem(itemIndexPrevious);
                }
                break;

            case R.id.btnNext:
                int itemIndexNext = viewPager.getCurrentItem()+1;
                if(itemIndexNext < viewPager.getAdapter().getCount()) {
                    viewPager.setCurrentItem(itemIndexNext);
                }
                break;

            case R.id.btnClose:
//                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Contextor.getInstantce().getContext());
//                preferences.unregisterOnSharedPreferenceChangeListener(this);
                //stopSelf();
                removeNow();
                //setPreference("onRemoveView");
                //IReplyDialogActivity.getInstance().setFinishActivity();
                //dismiss();
                break;

            case R.id.btnShow:
                //Send message to server
                break;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i(TAG, "Shared Pref : " + key);
        String jResponseString = null;
        if (key.startsWith("notification_data")) {
            jResponseString = sharedPreferences.getString(key, "");
        }
        if (jResponseString != null) {
            try {
//                JsonNotification jsonNotification = LoganSquare.parse(jResponseString, JsonNotification.class);
//                if (jsonNotification != null) {
//                    updateNotificationReplyDialog(jsonNotification);
//                } else {
//                    Log.i(TAG, "jsonNotification " + jResponseString);
//                }
                Log.i(TAG, "jsonNotification result " + jResponseString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void registerIntentFilter(){
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(ACTION_DIALOG_START);
        mIntentFilter.addAction(ACTION_DIALOG_HIDDEN);
        registerReceiver(onBroadcastReceiver,mIntentFilter);
    }

    BroadcastReceiver onBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null) {
                Log.i(TAG, "Action : " + intent.getAction());
                if (intent.getAction().equals(ACTION_DIALOG_START)) {
                    showDialog();
                } else if (intent.getAction().equals(ACTION_DIALOG_HIDDEN)) {

                }
            }
        }
    };

//    /**
//     *
//     * @param jsonNotification
//     */
//    private void updateNotificationReplyDialog(JsonNotification jsonNotification){
//        if (notificationArrayList != null) {
//            notificationArrayList.add(jsonNotification);
//            txtCountMessage.setText(String.valueOf(notificationArrayList.size()));
//            int indexCurrentItem = viewPager.getCurrentItem();
//            JsonNotification j = notificationArrayList.get(indexCurrentItem);
//            txtGroupTitle.setText(j.message);
//            Glide.with(getBaseContext())
//                    .load(j.user_image)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .fitCenter()
//                    .placeholder(R.drawable.defaultuserimg_sq)
//                    .into(imgTitle);
//            samplePager.notifyDataSetChanged();
//        }
//    }

//    /**
//     *
//     * @param indexCurrentItem
//     */
//    private void refreshNotificationDialog(int indexCurrentItem){
//        JsonNotification j = notificationArrayList.get(indexCurrentItem);
//        txtGroupTitle.setText(j.group_name);
//        Glide.with(getBaseContext())
//                .load(j.user_image)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .fitCenter()
//                .placeholder(R.drawable.defaultuserimg_sq)
//                .into(imgTitle);
//    }

    /**
     * Event listener zone
     */
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //refreshNotificationDialog(position);
            if(position == 0) {
                btnPrevious.setVisibility(View.INVISIBLE);
                if(viewPager.getAdapter().getCount() == 1){
                    btnNext.setVisibility(View.INVISIBLE);
                }else{
                    btnNext.setVisibility(View.VISIBLE);
                }
            }else if(position == viewPager.getAdapter().getCount()-1){
                btnNext.setVisibility(View.INVISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
            }else if(position > 0 && position < viewPager.getAdapter().getCount() -1){
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * Inner class zone
     */
    private class SamplePager extends PagerAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            FrameLayout frameContent = new FrameLayout(container.getContext());
//            JsonNotification jsonNotification = notificationArrayList.get(position);
//
//            String message = (jsonNotification.alert == null) ? jsonNotification.message : jsonNotification.alert;
//            TextView mTextView = new TextView(container.getContext());
//            mTextView.setText(message);
//            mTextView.setTextColor(Color.parseColor("#0000FF"));
//            mTextView.setSingleLine(false);
//            mTextView.setMovementMethod(new ScrollingMovementMethod());
//
//            frameContent.addView(mTextView);
//            else {
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.appicon);
//                ImageView imageView = new ImageView(container.getContext());
//                imageView.setImageBitmap(bitmap);
//                imageView.setScaleType(ImageView.ScaleType.CENTER);
//                frameContent.addView(imageView);
//            }
            container.addView(frameContent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return frameContent;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}

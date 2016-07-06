package com.example.paka.myfirebasedemo;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.paka.myfirebasedemo.activity.MyInterface;
import com.example.paka.myfirebasedemo.activity.MyModel;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Noth on 21/6/2559.
 */
public class ReplyDialogFragment extends DialogFragment implements View.OnClickListener {

    private static ReplyDialogFragment replyDialogFragment;

    //private Pubnub pubnub;
    private String channel;
    //private Group group;
    private String group_id;
    private String title;
    //private MessageManager messageManager;

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

    private ArrayList<MyModel> myModelArrayList = new ArrayList<>();

    private String TAG = ReplyDialogFragment.class.getSimpleName();
    private ImageView imgTitle;
    private TextView txtCountMessage;


    public static ReplyDialogFragment newInstance(String title, String group_id, String cal_id) {
        replyDialogFragment = new ReplyDialogFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("TITLE", title);
        mBundle.putString("group_id", group_id);
        mBundle.putString("cal_id", cal_id);
        replyDialogFragment.setArguments(mBundle);
        return replyDialogFragment;
    }

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        LayoutInflater inflater = (getActivity()).getLayoutInflater();
//        View view = inflater.inflate(R.layout.notification_box,null);
//        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
//        mBuilder.setView(view);
//        initViewDialog(view);
//        return mBuilder.create();
//    }

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
        samplePager = new SamplePager(getActivity());
        viewPager.setAdapter(samplePager);
        //Button Register Listener
        btnSendMessage.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnShow.setOnClickListener(this);
    }

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        LayoutInflater inflater = LayoutInflater.from(getActivity());
//        View view = inflater.inflate(R.layout.notification_box,null);
//        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
//        mBuilder.setView(view);
//        initViewDialog(view);
//        return mBuilder.create();
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG,"onDetach");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG,"onAttach");
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        Log.i(TAG,"onCancel");

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.i(TAG,"onDismiss");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.notification_box,null);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setView(view);
        initViewDialog(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle mBundle = getArguments();
        title = mBundle.getString("TITLE", "");
        group_id = mBundle.getString("group_id", "");
        //messageManager = new MessageManager(getActivity());
        //String cal_id = mBundle.getString("cal_id", "");
        //channel = ConfigSingleton.getInstantce().getCompany_id() + "-" + group_id;
        //group = Database.getInstantce().getGroupsDataSource().getGroupDataById(group_id);
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
                String message = getMessageBox();
                Log.i(TAG," "+messageValue+" "+chat_id+" "+message);
                mockupDataSamplePager(10);
                //messageManager.sendMessageToServer(messageValue,lat,lng,chat_id,group_id,title);
                break;

            case R.id.btnPrevious:
                int itemIndexPrevios = viewPager.getCurrentItem()-1;
                if(itemIndexPrevios >= 0) {
                    viewPager.setCurrentItem(itemIndexPrevios);
                }
                break;

            case R.id.btnNext:
                int itemIndexNext = viewPager.getCurrentItem()+1;
                if(itemIndexNext < viewPager.getAdapter().getCount()) {
                    viewPager.setCurrentItem(itemIndexNext);
                }
                break;

            case R.id.btnClose:
                getDialog().dismiss();
                MyInterface.getInstance().iFinishActivity.OnFinishActivity();
                break;

            case R.id.btnShow:
                //Send message to server
                break;
        }
    }

    public String getMessageBox(){
        if(!edtSendMessageNotification_Box.getText().toString().isEmpty() && edtSendMessageNotification_Box != null){
            return edtSendMessageNotification_Box.getText().toString();
        }else{
            return "Empty";
        }
    }

    public void mockupDataSamplePager(int loop){
        Random random = new Random();
        String test = "Often when launching a tabbed activity, there needs to be a way to select a particular tab to be displayed once the activity loads. For example, an activity has three tabs with one tab being a list of created posts. After a user creates a post on a separate activity, the user needs to be returned to the main activity with the \"new posts\" tab displayed. This can be done through the use of intent extras and the ViewPager#setCurrentItem method. First, when launching the tabbed activity, we need to pass in the selected tab as an extra:";
        for(int i=0;i<loop;i++) {
            int result = random.nextInt(999999);
            MyModel myModel = new MyModel();
            myModel.setGroupID(String.valueOf(result) + "_GroupID");
            if(i % 3 == 2) {
                myModel.setImagePath("Image");
            }
            myModel.setMessage(test + String.valueOf(result));
            myModel.setUserID(String.valueOf(result));
            myModelArrayList.add(myModel);
        }
        samplePager.notifyDataSetChanged();
        viewPager.setCurrentItem(0,true);
        txtCountMessage.setText(String.valueOf(viewPager.getAdapter().getCount()));
    }

    /**
     * Event listener zone
     */
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(position == 0) {
                btnPrevious.setVisibility(View.INVISIBLE);
            }else if(position == viewPager.getAdapter().getCount()-1){
                btnNext.setVisibility(View.INVISIBLE);
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

        private LayoutInflater inflater;
        private Context context;

        public SamplePager(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return myModelArrayList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            FrameLayout frameContent = new FrameLayout(container.getContext());
            MyModel myModel = myModelArrayList.get(position);
            if (myModel.getImagePath() == null) {
                TextView mTextView = new TextView(container.getContext());
                mTextView.setText(myModel.getMessage());
                mTextView.setTextColor(Color.parseColor("#0000FF"));
                mTextView.setSingleLine(false);
                mTextView.setMovementMethod(new ScrollingMovementMethod());
                frameContent.addView(mTextView);
            } else {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.appicon);
                ImageView imageView = new ImageView(container.getContext());
                imageView.setImageBitmap(bitmap);
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                frameContent.addView(imageView);
            }
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

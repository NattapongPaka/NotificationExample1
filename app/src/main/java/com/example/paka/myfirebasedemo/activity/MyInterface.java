package com.example.paka.myfirebasedemo.activity;

/**
 * Created by Noth on 30/6/2559.
 */
public class MyInterface {

    public interface IFinishActivity {
        void OnFinishActivity();
    }

    public IFinishActivity iFinishActivity;

    public void setOnMyInstanceChangerListener(IFinishActivity iFinishActivity) {
        this.iFinishActivity = iFinishActivity;
    }

    public static MyInterface myInterfaceInstance;

    public static MyInterface getInstance() {
        if (myInterfaceInstance == null) {
            myInterfaceInstance = new MyInterface();
        }
        return myInterfaceInstance;
    }
}

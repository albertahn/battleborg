package com.fudgeProductName.kik;

import com.fudgeProductName.kik.comment_stuff.CommentsFrag;
import com.fudgeProductName.kik.home_stuff.HomeFrag;
import com.unity3d.player.*;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.NativeActivity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class UnityPlayerNativeActivity extends NativeActivity implements TabHost.OnTabChangeListener
{
    protected UnityPlayer mUnityPlayer;		// don't change the name of this variable; referenced from native code

    TabHost mTabHost;

    Activity activity = this;

    // Setup activity layout
    @Override protected void onCreate (Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getWindow().takeSurface(null);
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy

        mUnityPlayer = new UnityPlayer(this);
        if (mUnityPlayer.getSettings ().getBoolean ("hide_status_bar", true))
            getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

		/*setContentView(mUnityPlayer);
		mUnityPlayer.requestFocus();*/


        setContentView(R.layout.main);


//set tabhost

        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();

        setupTab(new TextView(activity), "home", R.drawable.iconshot, R.id.home_tab);
        setupTab(new TextView(activity), "comment", R.drawable.iconshot, R.id.comment_tab);
        setupTab(new TextView(activity), "rank", R.drawable.iconshot, R.id.ranking_tab);

        mTabHost.setOnTabChangedListener(this);

        mTabHost.getTabWidget().setDividerDrawable(null);


        //set fitst page to homefrag

        Fragment fragment = new HomeFrag();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.home_tab, fragment).commit();


     /*   Button storybtn = (Button)  findViewById(R.id.story_button);

        storybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//very important!
                mUnityPlayer.UnitySendMessage("Common_script", "SetUser", "workdamn holer");

                Log.e("Common_script","SetUser");

                Fragment fragment = new CommentsFrag();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.theframefather, fragment).commit();

            }
        });


        Button  Playbtn = (Button) findViewById(R.id.play_button);

        Playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // setContentView(mUnityPlayer);
               // mUnityPlayer.requestFocus();

                Dialog d = new Dialog(activity);
                d.setTitle("play game");
                d.show();
            }
        });


*/

    }//endoncreate

    //set tabs



    private void setupTab(final View view, final String tag, int drawableId, int tabcontentid) {


        View tabview = createTabView(mTabHost.getContext(), tag, drawableId);

        TabHost.TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(tabcontentid);

        mTabHost.addTab(setContent);
    }




    private static View createTabView(final Context context, final String text, final int drawableId) {

        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);

        TextView title= (TextView) view.findViewById(R.id.title);

        title.setText(text);

        //image
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        icon.setImageResource(drawableId);

        return view;
    }//tabview


    @Override
    public void onTabChanged(String tabId){

        //Context context = mTabHost.getContext();
        String msg = "id of messnea is: "+ mTabHost.getCurrentTab();

        if ("home".equals(tabId)) {

            //show the homefrag
            getFragmentManager().beginTransaction()
                    .add(R.id.home_tab,  new HomeFrag())
                    .commit();

        } else if ("comment".equals(tabId)) {
            System.out.println(msg);

            //show the homefrag
            getFragmentManager().beginTransaction()
                    .add(R.id.comment_tab,  new CommentsFrag())
                    .commit();


        } else if ("rank".equals(tabId)) {

            getFragmentManager().beginTransaction()
                    .add(R.id.ranking_tab,  new HomeFrag())
                    .commit();


        }

    }//end tabchange

    // Quit Unity
    @Override protected void onDestroy ()
    {
        mUnityPlayer.quit();
        super.onDestroy();
    }

    // Pause Unity
    @Override protected void onPause()
    {
        super.onPause();
        mUnityPlayer.pause();
    }

    // Resume Unity
    @Override protected void onResume()
    {
        super.onResume();
        mUnityPlayer.resume();
    }

    // This ensures the layout will be correct.
    @Override public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onKeyDown(int keyCode, KeyEvent event)   { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
    /*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
}

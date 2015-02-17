package com.fudgeProductName.kik;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.fudgeProductName.kik.loginStuff.ReadFromServer;
import com.fudgeProductName.kik.utils.JsonReader;
import com.fudgeProductName.kik.utils.UserDatabase;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;

/**
 * Created by albert on 5/1/14.
 */


public class LoginHelper extends Activity{

    FragmentActivity mActivity;
    JsonReader myJSONreader = new JsonReader();
    final static String PROFILEURL = "http://mobile.tanggoal.com/user/user_profile/";

    TextView usernameText;
    HttpClient client;
    JSONObject json;
    JSONObject straightJson;
    Bitmap profilePhotoImage;


    UserDatabase myUserDatabase;

    Context context;


    public String[][] checkLogin(Context context) {

        context = context;

        UserDatabase info = new UserDatabase(context);
        info.open();
        String[][] data = info.getData();
        info.close();

        return data;

    } //end checklogin


   /* public void readSavedUser(Context context, ArrayList<LoginItem> generatedLoginItem, View rootView) {

        TextView usernameText = (TextView) rootView.findViewById(R.id.user_name_profile);

        String username = generatedLoginItem.get(0).getUsername().toString();

         usernameText.setText(username);

        String propic = generatedLoginItem.get(0).getProfile_picture();

       // LoadImage loadImage = new LoadImage(context);
        //loadImage.execute(propic);
        ImageView profile_photo = (ImageView) rootView.findViewById(R.id.myprofile_picture);
        String imageurl = "http://tanggoal.com/public/uploads/members_pic/" + propic;




        ImageLoader imageloader = ImageLoader.getInstance();

        //imageloader.displayImage(url, view);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .displayer(new RoundedBitmapDisplayer(250))
                .cacheInMemory(true)
                .considerExifParams(true)
                .build();

        //kill it first

        //imageloader.destroy();

        Boolean isInit = imageloader.isInited();

        if(!isInit) {
            imageloader.init(ImageLoaderConfiguration.createDefault(context));
        }

        imageloader.displayImage(imageurl, profile_photo, options);


    } //end readSavedUser*/

    public void logoutUser(final Context context){

        this.context = context;

        //delete data
        UserDatabase info = new UserDatabase(context);
        info.open();
        info.deleteAllData();
        info.close();

       /* LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
*/
        //root view from activity
        View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
      //  ViewGroup tabView = (ViewGroup)  rootView.findViewById(R.id.profile_tab);
      //  ViewGroup messageView = (ViewGroup)  rootView.findViewById(R.id.message_tab);
       // View layout = (View)  inflater.inflate(R.layout.click_to_login, null);

        //remove everything
      //  tabView.removeAllViewsInLayout();
//add the view
        //tabView.addView(layout);

        //rid of all stuff in message
     //   messageView.removeAllViewsInLayout();

//set so clickable


        //facebook session out
       logoutFB_session(context);

    } //end logout

    public void loginUser (String email, String password, Context context){

       String[] paramy = new String[2];

        paramy[0] = email;
        paramy[1] = password;

        ReadFromServer reading = new  ReadFromServer(context, paramy);

        reading.execute(paramy);

       // String[][] logindata= checkLogin(context);

    }




//logout facebook
public void logoutFB_session(Context context){
    this.context = context;

    //Session session = Session.getActiveSession();
/*
    Dialog d = new Dialog(context);
    d.setTitle("c: ses: "+session.toString());
    TextView t = new TextView(context);
    t.setText(""+session.toString()
    );
    d.setContentView(t);
    d.show();
*/
   /* if(session!=null) {


        Intent i = new Intent(context,
                LoginActivity.class);

        context.startActivity(i);
*/

       /* Dialog da = new Dialog(context);
        da.setTitle(" not null close");
        TextView ta = new TextView(context);
        ta.setText(""+session.toString()
        );
        da.setContentView(ta);
        da.show();*/

     /*   session = new Session(context);
        //session.requestNewReadPermissions(new Session.NewPermissionsRequest(activity, permissionList));
        Session.setActiveSession(session);

        //session.close();
        session.closeAndClearTokenInformation();
        Session.getActiveSession().close();
        Session.setActiveSession(null);

    }else{

        Dialog da = new Dialog(context);
        da.setTitle("logged out");
        TextView ta = new TextView(context);
        ta.setText("");
        da.setContentView(ta);
        da.show();

    }
*/
/*
    if (session != null) {

        if (!session.isClosed()) {
            session.closeAndClearTokenInformation();
            //clear your preferences if saved
        }
    } else {

        session = new Session(context);
        Session.setActiveSession(session);

        session.closeAndClearTokenInformation();
        //clear your preferences if saved

    }
*/

}// logout fb

}

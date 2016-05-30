package com.example.francois.priape.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Francois on 13/03/2016.
 */
public abstract class Default {

 /*  public static String APPLICATION_ID = "CA8317B4-EF6D-D8A1-FFAC-5DC9B7263A00";
    public static String SECRET_KEY = "0DFFFCF3-53DD-0C4C-FF0A-3F74F0A18000";
    public static String VERSION = "v1";
    public static String SERVER_URL = "http://51.255.197.114/api/" + VERSION + "/"; */

   public static String APPLICATION_ID  = "603EA250-3BD9-5EB1-FF62-53D50AC37900";
    public  static  String SECRET_KEY = "0E72338A-D313-ED73-FF03-E7DD53D51D00";
    public  static  String VERSION = "v1";
    public  static String SERVER_URL = "https://api.backendless.com/"+ VERSION + "/";
   public static Context mcontext;

    // COde for ActivityForResult
    public static final int SELECT_PICTURE = 1;

 public static boolean haveNetworkConnection(Context context) {
  boolean haveConnectedWifi = false;
  boolean haveConnectedMobile = false;

  ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
  NetworkInfo[] netInfo = cm.getAllNetworkInfo();
  for (NetworkInfo ni : netInfo) {
   if (ni.getTypeName().equalsIgnoreCase("WIFI"))
    if (ni.isConnected())
     haveConnectedWifi = true;
   if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
    if (ni.isConnected())
     haveConnectedMobile = true;
  }
  return haveConnectedWifi || haveConnectedMobile;
 }


}

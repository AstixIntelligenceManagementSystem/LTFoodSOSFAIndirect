package project.astix.com.ltfoodsosfaindirect;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.astix.Common.CommonInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class DayStartActivity extends BaseActivity implements InterfaceClass,OnMapReadyCallback
{
    static int flgDaySartWorking = 0;
    DatabaseAssistant DASFA = new DatabaseAssistant(this);
    public long syncTIMESTAMP;
    ProgressDialog pDialog2;
    String PersonNodeID="NA";
    String PersonNodeType="NA";
    String PersonName="NA";
    String OptionID="NA";
    String OptionDesc="NA";


    public String fnAddressFromLauncher="NA";
    String SOLattitudeFromLauncher="NA";
    String SOLongitudeFromLauncher="NA";
    String SOAccuracyFromLauncer="NA";

    String SOProviderFromLauncher="NA";

    String SOGpsLatFromLauncher="NA";
    String SOGpsLongFromLauncher="NA";
    String SOGpsAccuracyFromLauncher="NA";

    String SONetworkLatFromLauncher="NA";
    String SONetworkLongFromLauncher="NA";
    String SONetworkAccuracyFromLauncher="NA";

    String SOFusedLatFromLauncher="NA";
    String SOFusedLongFromLauncher="NA";
    String SOFusedAccuracyFromLauncher="NA";

    String SOAllProvidersLocationFromLauncher="NA";

    String SOGpsAddressFromLauncher="NA";
    String SONetwAddressFromLauncher="NA";
    String SOFusedAddressFromLauncher="NA";

    String SOFusedLocationLatitudeWithFirstAttemptFromLauncher="NA";
    String SOFusedLocationLongitudeWithFirstAttemptFromLauncher="NA";
    String SOFusedLocationAccuracyWithFirstAttemptFromLauncher="NA";

    String finalAddress="NA";
    String finalPinCode="NA";
    String finalCity="NA";
    String finalState="NA";
    public static int flgRestart=0;

    String cityID="NA";
    String StateID="NA";
    String MapAddress="NA";
    String MapPincode="NA";
    String MapCity="NA";
    String MapState="NA";

    int refreshCount=0;
    int countSubmitClicked=0;
    public LocationManager locationManager;
    int intentFrom=0;
    LinearLayout ll_map;
    RadioButton rb_NoWorking;
    EditText et_otherPleaseSpecify;
    public String ReasonId="0";;
    public String ReasonText="NA";
    public int chkFlgForErrorToCloseApp=0;
    String[] reasonNames;
    DBAdapterKenya dbengine = new DBAdapterKenya(this);

    LinkedHashMap<String, String> hmapReasonIdAndDescr_details=new LinkedHashMap<String, String>();
    LinkedHashMap<Integer, String> hmapReasonIdAndDescrForOption_details=new LinkedHashMap<Integer, String>();
    public String fDate;
    public SimpleDateFormat sdf;
    SharedPreferences sPrefAttandance;

    FragmentManager manager;
    FragmentTransaction fragTrans;
    MapFragment mapFrag;
    String LattitudeFromLauncher="NA";
    String LongitudeFromLauncher="NA";
    public String AccuracyFromLauncher="NA";
    String AddressFromLauncher="NA";
    LinearLayout ll_start,ll_startAfterDayEndFirst,ll_startAfterDayEndSecond,ll_containView;
    Button but_Next;

    TextView txt_DayStarttime;
    RadioGroup rg;

    public void customHeader()
    {


        TextView tv_heading=(TextView) findViewById(R.id.tv_heading);
        tv_heading.setText("Day Start");

        ImageView imgVw_next=(ImageView) findViewById(R.id.imgVw_next);

        ImageView imgVw_back=(ImageView) findViewById(R.id.imgVw_back);
        imgVw_next.setVisibility(View.GONE);
        imgVw_back.setVisibility(View.GONE);
        if(intentFrom==0)
        {
            imgVw_back.setVisibility(View.GONE);
            imgVw_next.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent i=new Intent(DayStartActivity.this,AllButtonActivity.class);
                    startActivity(i);
                    finish();

                }
            });
        }
        else
        {
            imgVw_next.setVisibility(View.GONE);
            imgVw_back.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent i=new Intent(DayStartActivity.this,StoreSelection.class);

                    startActivity(i);
                    finish();

                }
            });
        }
    }
    private void getReasonDetail() throws IOException
    {

        hmapReasonIdAndDescr_details=dbengine.fetch_Reason_List();

        int index=0;
        if(hmapReasonIdAndDescr_details!=null)
        {
            reasonNames=new String[hmapReasonIdAndDescr_details.size()];
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapReasonIdAndDescr_details);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while(iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry)iterator.next();
                reasonNames[index]=me2.getKey().toString();
                index=index+1;
            }
        }


    }

    private void getOptionDetail() throws IOException
    {

        hmapReasonIdAndDescrForOption_details=dbengine.fetch_Reason_List_for_option();

        int index=0;
      /*  if(hmapReasonIdAndDescrForOption_details!=null)
        {
            reasonNames=new String[hmapReasonIdAndDescrForOption_details.size()];
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapReasonIdAndDescr_details);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while(iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry)iterator.next();
                reasonNames[index]=me2.getKey().toString();
                index=index+1;
            }
        }
*/

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        if(!LattitudeFromLauncher.equals("NA") && !LattitudeFromLauncher.equals("0.0"))
        {
            googleMap.clear();
            try {
                googleMap.setMyLocationEnabled(false);
            }
            catch(SecurityException e)
            {

            }



            if(AccuracyFromLauncher.equals("0.0") || AccuracyFromLauncher.equals("NA")|| AccuracyFromLauncher.equals("")|| AccuracyFromLauncher==null){
                //dont use marker
            }
            else{

                if(Double.parseDouble(AccuracyFromLauncher)>100){
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher))).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    Marker locationMarker=googleMap.addMarker(marker);
                    locationMarker.showInfoWindow();
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)), 15));
                  /*  if(!fetchedFromDb.equals("0") && fetchedFromDb!=null){
                        distanceText.setVisibility(View.VISIBLE);
                    }*/

                }
                else{
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher))).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    Marker locationMarker=googleMap.addMarker(marker);
                    locationMarker.showInfoWindow();
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)), 15));
                   // distanceText.setVisibility(View.GONE);
                }

            }




        }
        else
        {
            if(refreshCount==2)
            {
               // txt_rfrshCmnt.setText(getString(R.string.loc_not_found));
               // btn_refresh.setVisibility(View.GONE);
            }

            try
            {
                googleMap.setMyLocationEnabled(false);
            }
            catch(SecurityException e)
            {

            }
           // googleMap.addMarker(new MarkerOptions().position(new LatLng(22.7253, 75.8655)).title("Indore"));
            googleMap.moveCamera(CameraUpdateFactory.zoomIn());
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                  //  marker.setTitle(StoreName);
                }
            });

        }

    }


    private void createRadioButton()
    {
        final RadioButton[] rb = new RadioButton[hmapReasonIdAndDescrForOption_details.size()];
        rg = new RadioGroup(this); //create the RadioGroup

        rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL

        // using for-each loop for iteration over Map.entrySet()
        int i=0;
        for (Map.Entry<Integer,String> entry : hmapReasonIdAndDescrForOption_details.entrySet())
        {
            rb[i]  = new RadioButton(this);
            rb[i].setText(entry.getValue());
            rb[i].setId(Integer.parseInt(entry.getKey().toString().trim()));
            rg.addView(rb[i]);
            i=i+1;
        }

       // rb_NoWorking=(RadioButton)findViewById(R.id.cb_NoWorking);
       // rb_NoWorking.setVisibility(View.VISIBLE);
       // rg.addView(rb_NoWorking);
        ll_containView.addView(rg);//you add the whole RadioGroup to the layout

        rb_NoWorking=(RadioButton)findViewById(R.id.cb_NoWorking);
        // rb_NoWorking.setVisibility(View.VISIBLE);



        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                rb_NoWorking.setChecked(false);
                if (null != rb)
                {
                    if(checkedId==6)
                    {
                        et_otherPleaseSpecify=(EditText)findViewById(R.id.et_otherPleaseSpecify);
                        et_otherPleaseSpecify.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        et_otherPleaseSpecify=(EditText)findViewById(R.id.et_otherPleaseSpecify);
                        et_otherPleaseSpecify.setVisibility(View.GONE);
                    }
                    if(checkedId==8)
                    {
                        CommonInfo.DayStartClick=1;
                    }
                    else if(checkedId==9)
                    {
                        CommonInfo.DayStartClick=2;
                    }
                    else
                    {
                        CommonInfo.DayStartClick=3;
                    }

                    ReasonText=hmapReasonIdAndDescrForOption_details.get(checkedId);

                    ReasonId=""+checkedId;

                }

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daystart);
        Intent intent=getIntent();
        intentFrom= intent.getIntExtra("IntentFrom", 0);

        sPrefAttandance=getSharedPreferences(CommonInfo.AttandancePreference, MODE_PRIVATE);

        customHeader();
        try
        {
            getReasonDetail();
            getOptionDetail();
        }
        catch(Exception e)
        {

        }
        locationManager=(LocationManager) this.getSystemService(LOCATION_SERVICE);
        Date date1=new Date();
        sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        fDate = sdf.format(date1).toString().trim();

        ll_start=(LinearLayout)findViewById(R.id.ll_start);
        ll_start.setVisibility(View.VISIBLE);
        ll_startAfterDayEndFirst=(LinearLayout)findViewById(R.id.ll_startAfterDayEndFirst);
        ll_startAfterDayEndFirst.setVisibility(View.GONE);
        ll_startAfterDayEndSecond=(LinearLayout)findViewById(R.id.ll_startAfterDayEndSecond);
        ll_startAfterDayEndSecond.setVisibility(View.GONE);
        ll_map=(LinearLayout)findViewById(R.id.ll_map);
        ll_map.setVisibility(View.GONE);

        ll_containView=(LinearLayout)findViewById(R.id.ll_containView);
        ll_containView.setVisibility(View.GONE);



       ll_map=(LinearLayout) findViewById(R.id.ll_map);

        Button but_DayStart=(Button) findViewById(R.id.but_DayStart);
        but_DayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isGPSok = false;
                boolean isNWok=false;
                isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if(!isGPSok)
                {
                    isGPSok = false;
                }
                if(!isNWok)
                {
                    isNWok = false;
                }
                if(!isGPSok && !isNWok)
                {
                    try
                    {
                        showSettingsAlert();
                    }
                    catch(Exception e)
                    {

                    }

                    isGPSok = false;
                    isNWok=false;
                }
                else
                {
                    createRadioButton();
                    txt_DayStarttime=(TextView)findViewById(R.id.txt_DayStarttime);
                    txt_DayStarttime.setText(getDateAndTimeInSecond());

                    manager= getFragmentManager();
                    mapFrag = (MapFragment)manager.findFragmentById(R.id.map);
                    mapFrag.getView().setVisibility(View.VISIBLE);
                   // mapFrag.addMarker(new MarkerOptions().position(new LatLng(22.7253, 75.8655)).title("Indore"));
                   // mapFrag.set

                      mapFrag.getMapAsync(DayStartActivity.this);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.show(mapFrag);

                    ll_start=(LinearLayout)findViewById(R.id.ll_start);
                    ll_start.setVisibility(View.GONE);
                    ll_startAfterDayEndFirst=(LinearLayout)findViewById(R.id.ll_startAfterDayEndFirst);
                    ll_startAfterDayEndFirst.setVisibility(View.VISIBLE);
                    ll_startAfterDayEndSecond=(LinearLayout)findViewById(R.id.ll_startAfterDayEndSecond);
                    ll_startAfterDayEndSecond.setVisibility(View.VISIBLE);
                    ll_map=(LinearLayout)findViewById(R.id.ll_map);
                    ll_map.setVisibility(View.VISIBLE);
                    ll_containView.setVisibility(View.VISIBLE);



                    LocationRetreivingGlobal llaaa=new LocationRetreivingGlobal();
                    llaaa.locationRetrievingAndDistanceCalculating(DayStartActivity.this);
                }



            }
        });
        Button but_Exit=(Button) findViewById(R.id.but_Exit);
        but_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        but_Next=(Button) findViewById(R.id.but_Next);
        but_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                if (CommonInfo.DayStartClick==0)
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Please Select atleast one option to proceed.");
                    alertDialog.setIcon(R.drawable.error);
                    alertDialog.setCancelable(false);

                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog,int which)
                        {
                            dialog.dismiss();


                        }
                    });
                    alertDialog.show();
                    return;
                }
                else if (ReasonId.equals("6"))
                {
                    if(TextUtils.isEmpty(et_otherPleaseSpecify.getText().toString().trim()))
                    {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                        alertDialog.setTitle("Error");
                        alertDialog.setMessage("Please Specify the reason first to proceed.");
                        alertDialog.setIcon(R.drawable.error);
                        alertDialog.setCancelable(false);

                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int which)
                            {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                        return;
                    }
                    else
                    {
                        dbengine.updatetblAttandanceDetails("6","Other Please Specify",ReasonId,et_otherPleaseSpecify.getText().toString().trim());
                        syncStartAfterSavindData();
                    }
                }
                else if (ReasonId.equals("0"))
                {
                    if (ReasonText.equals("")||ReasonText.equals("NA") ||ReasonText.equals("Select Reason"))
                    {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                        alertDialog.setTitle("Error");
                        alertDialog.setMessage("Please Specify the reason first to proceed.");
                        alertDialog.setIcon(R.drawable.error);
                        alertDialog.setCancelable(false);

                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int which)
                            {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                        return;
                    }
                    else
                    {
                        dbengine.updatetblAttandanceDetails("33","No Working",ReasonId,ReasonText);
                        syncStartAfterSavindData();
                    }
                }
                else
                {
                    dbengine.updatetblAttandanceDetails("33","No Working",ReasonId,ReasonText);
                    syncStartAfterSavindData();
                }


            }
        });



    }

    public void syncStartAfterSavindData()
    {
        SharedPreferences.Editor editor=sPrefAttandance.edit();
        editor.clear();
        editor.commit();
        sPrefAttandance.edit().putString("AttandancePref", fDate).commit();
        flgDaySartWorking=1;
        try {

            new bgTasker().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            //System.out.println(e);
        } catch (ExecutionException e) {
            e.printStackTrace();
            //System.out.println(e);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        boolean isGPSok = false;
        boolean isNWok=false;
        isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(!isGPSok)
        {
            isGPSok = false;
        }
        if(!isNWok)
        {
            isNWok = false;
        }
        if(!isGPSok && !isNWok)
        {
            try
            {
                showSettingsAlert();
            }
            catch(Exception e)
            {

            }

            isGPSok = false;
            isNWok=false;
        }
        else
        {
           // LocationRetreivingGlobal llaaa=new LocationRetreivingGlobal();
           // llaaa.locationRetrievingAndDistanceCalculating(DayStartActivity.this);
        }
    }


    @Override
    public void testFunctionOne(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider,
                                String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong,
                                String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy,
                                String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress,
                                String FusedLocationLatitudeWithFirstAttempt,
                                String FusedLocationLongitudeWithFirstAttempt,
                                String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff,
                                int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff,
                                int flgInternetOnOffWhileLocationTracking, String address, String pincode,
                                String city, String state) {

        //System.out.println("SHIVA"+fnLati+","+fnLongi+","+finalAccuracy+","+fnAccurateProvider+","+GpsLat+","+GpsLong+","+GpsAccuracy+","+NetwLat+","+NetwLong+","+NetwAccuracy+","+FusedLat+","+FusedLong+","+FusedAccuracy+","+AllProvidersLocation+","+GpsAddress+","+NetwAddress+","+FusedAddress+","+FusedLocationLatitudeWithFirstAttempt+","+FusedLocationLongitudeWithFirstAttempt+","+FusedLocationAccuracyWithFirstAttempt+","+fnLongi+","+flgLocationServicesOnOff+","+flgGPSOnOff+","+flgNetworkOnOff+","+flgFusedOnOff+","+flgInternetOnOffWhileLocationTracking+","+address+","+pincode+","+city+","+state);

        if(!checkLastFinalLoctionIsRepeated(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(finalAccuracy)))
        {
            LattitudeFromLauncher=String.valueOf(fnLati);
            LongitudeFromLauncher=String.valueOf(fnLongi);
            AccuracyFromLauncher=String.valueOf(finalAccuracy);

            fnCreateLastKnownFinalLocation(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(finalAccuracy));
            manager= getFragmentManager();
            mapFrag = (MapFragment)manager.findFragmentById(R.id.map);
            mapFrag.getView().setVisibility(View.VISIBLE);
            mapFrag.getMapAsync(DayStartActivity.this);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.show(mapFrag);
            dbengine.open();
            dbengine.savetblAttandanceDetails(txt_DayStarttime.getText().toString().trim(),PersonNodeID,PersonNodeType,PersonName,OptionID,OptionDesc,
                    ReasonId,ReasonText,FusedAddress,finalPinCode,finalCity,finalState,fnLati,fnLongi,
                    finalAccuracy,"0",fnAccurateProvider,AllProvidersLocation,FusedAddress,
                    GpsLat,GpsLong,GpsAccuracy,GpsAddress,
                    NetwLat,NetwLong,NetwAccuracy,NetwAddress,
                    FusedLat,FusedLong,FusedAccuracy,FusedAddress,
                    FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,
                    FusedLocationAccuracyWithFirstAttempt,3,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,
                    flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart, cityID, StateID, MapAddress, MapCity, MapPincode, MapState);

            dbengine.close();



        }
        else
        {
            countSubmitClicked++;
            if(countSubmitClicked==1)
            {
                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(DayStartActivity.this);

                // Setting Dialog Title
                alertDialog.setTitle("Information");
                alertDialog.setIcon(R.drawable.error_info_ico);
                alertDialog.setCancelable(false);
                // Setting Dialog Message
                alertDialog.setMessage("Your current location is same as previous, so please turn off your location services then turn on, it back again.");

                // On pressing Settings button
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        countSubmitClicked++;
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });

                // Showing Alert Message
                alertDialog.show();



            }
            else
            {

                LattitudeFromLauncher=String.valueOf(fnLati);
                LongitudeFromLauncher=String.valueOf(fnLongi);
                AccuracyFromLauncher=String.valueOf(finalAccuracy);
                manager= getFragmentManager();
                mapFrag = (MapFragment)manager.findFragmentById(R.id.map);
                mapFrag.getView().setVisibility(View.VISIBLE);
                mapFrag.getMapAsync(DayStartActivity.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.show(mapFrag);

                dbengine.open();
                dbengine.savetblAttandanceDetails(txt_DayStarttime.getText().toString().trim(),PersonNodeID,PersonNodeType,PersonName,OptionID,OptionDesc,
                        ReasonId,ReasonText,FusedAddress,finalPinCode,finalCity,finalState,fnLati,fnLongi,
                        finalAccuracy,"0",fnAccurateProvider,AllProvidersLocation,FusedAddress,
                        GpsLat,GpsLong,GpsAccuracy,GpsAddress,
                        NetwLat,NetwLong,NetwAccuracy,NetwAddress,
                        FusedLat,FusedLong,FusedAccuracy,FusedAddress,
                        FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,
                        FusedLocationAccuracyWithFirstAttempt,3,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,
                        flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart, cityID, StateID, MapAddress, MapCity, MapPincode, MapState);

                dbengine.close();




            }


        }







    }

    public boolean checkLastFinalLoctionIsRepeated(String currentLat,String currentLong,String currentAccuracy){
        boolean repeatedLoction=false;

        try {

            String chekLastGPSLat="0";
            String chekLastGPSLong="0";
            String chekLastGpsAccuracy="0";
            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.FinalLatLngJsonFile);
            if (!jsonTxtFolder.exists())
            {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew="FinalGPSLastLocation.txt";
            File file = new File(jsonTxtFolder,txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory()+"/"+ CommonInfo.FinalLatLngJsonFile+"/"+txtFileNamenew;

            // If file does not exists, then create it
            if (file.exists()) {
                StringBuffer buffer=new StringBuffer();
                String myjson_stampiGPSLastLocation="";
                StringBuffer sb = new StringBuffer();
                BufferedReader br = null;

                try {
                    br = new BufferedReader(new FileReader(file));

                    String temp;
                    while ((temp = br.readLine()) != null)
                        sb.append(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        br.close(); // stop reading
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                myjson_stampiGPSLastLocation=sb.toString();

                JSONObject jsonObjGPSLast = new JSONObject(myjson_stampiGPSLastLocation);
                JSONArray jsonObjGPSLastInneralues = jsonObjGPSLast.getJSONArray("GPSLastLocationDetils");

                String StringjsonGPSLastnew = jsonObjGPSLastInneralues.getString(0);
                JSONObject jsonObjGPSLastnewwewe = new JSONObject(StringjsonGPSLastnew);

                chekLastGPSLat=jsonObjGPSLastnewwewe.getString("chekLastGPSLat");
                chekLastGPSLong=jsonObjGPSLastnewwewe.getString("chekLastGPSLong");
                chekLastGpsAccuracy=jsonObjGPSLastnewwewe.getString("chekLastGpsAccuracy");

                if(currentLat!=null )
                {
                    if(currentLat.equals(chekLastGPSLat) && currentLong.equals(chekLastGPSLong) && currentAccuracy.equals(chekLastGpsAccuracy))
                    {
                        repeatedLoction=true;
                    }
                }
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return repeatedLoction;

    }
    public void fnCreateLastKnownFinalLocation(String chekLastGPSLat,String chekLastGPSLong,String chekLastGpsAccuracy)
    {

        try {

            JSONArray jArray=new JSONArray();
            JSONObject jsonObjMain=new JSONObject();


            JSONObject jOnew = new JSONObject();
            jOnew.put( "chekLastGPSLat",chekLastGPSLat);
            jOnew.put( "chekLastGPSLong",chekLastGPSLong);
            jOnew.put( "chekLastGpsAccuracy", chekLastGpsAccuracy);


            jArray.put(jOnew);
            jsonObjMain.put("GPSLastLocationDetils", jArray);

            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.FinalLatLngJsonFile);
            if (!jsonTxtFolder.exists())
            {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew="FinalGPSLastLocation.txt";
            File file = new File(jsonTxtFolder,txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory()+"/"+ CommonInfo.FinalLatLngJsonFile+"/"+txtFileNamenew;


            // If file does not exists, then create it
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


            FileWriter fw;
            try {
                fw = new FileWriter(file.getAbsoluteFile());

                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(jsonObjMain.toString());

                bw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
				 /*  file=contextcopy.getFilesDir();
				//fileOutputStream=contextcopy.openFileOutput("FinalGPSLastLocation.txt", Context.MODE_PRIVATE);
				fileOutputStream.write(jsonObjMain.toString().getBytes());
				fileOutputStream.close();*/
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{

        }
    }
    public void showSettingsAlert()
    {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Information");
        alertDialog.setIcon(R.drawable.error_info_ico);
        alertDialog.setCancelable(false);
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. \nPlease select all settings on the next page!");

        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void onRadioButtonClicked(View view)
    {
        boolean checked = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.cb_NoWorking:

                CommonInfo.DayStartClick=3;
                ReasonId="0";
               // rg.clearCheck();
                str = checked?displayAlertDialog():"cb_NoWorking Deselected";
                but_Next.setText("Submit");


                break;
        }
       // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    public void showNoConnAlert()
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(DayStartActivity.this);
        alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialogNoConn.setMessage(R.string.NoDataConnectionFullMsg);
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        // finish();
                    }
                });
        alertDialogNoConn.setIcon(R.drawable.error_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }

    public String displayAlertDialog()
    {
        String str="cb_NoWorking Selected;";
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_nostore, null);
        final EditText et_Reason = (EditText) alertLayout.findViewById(R.id.et_Reason);
        et_Reason.setVisibility(View.INVISIBLE);

        final Spinner spinner_reason=(Spinner) alertLayout.findViewById(R.id.spinner_reason);

        ArrayAdapter adapterCategory=new ArrayAdapter(DayStartActivity.this, android.R.layout.simple_spinner_item,reasonNames);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_reason.setAdapter(adapterCategory);

        spinner_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3)
            {
                // TODO Auto-generated method stub
                String	spinnerReasonSelected = spinner_reason.getSelectedItem().toString();
                ReasonText=spinnerReasonSelected;
                int check=dbengine.fetchFlgToShowTextBox(spinnerReasonSelected);
                ReasonId=dbengine.fetchReasonIdBasedOnReasonDescr(spinnerReasonSelected);
                if(check==0)
                {
                    et_Reason.setVisibility(View.INVISIBLE);
                }
                else
                {
                    et_Reason.setVisibility(View.VISIBLE);
                }


                //ReasonId,ReasonText
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub

            }
        });


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.AlertDialogHeaderMsg);
        alert.setView(alertLayout);
        //alert.setIcon(R.drawable.info_ico);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {


                if (ReasonText.equals("")||ReasonText.equals("Select Reason"))
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Please select the reason first.");
                    alertDialog.setIcon(R.drawable.error);
                    alertDialog.setCancelable(false);

                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog,int which)
                        {
                            dialog.dismiss();
                            displayAlertDialog();

                        }
                    });
                    alertDialog.show();
                }
                else
                {


                   /* Date pdaDate=new Date();
                    SimpleDateFormat sdfPDaDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                    String CurDate = sdfPDaDate.format(pdaDate).toString().trim();

                    if(et_Reason.isShown())
                    {

                        if(TextUtils.isEmpty(et_Reason.getText().toString().trim()))
                        {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DayStartActivity.this);
                            alertDialog.setTitle("Error");
                            alertDialog.setMessage("Please enter the reason.");
                            alertDialog.setIcon(R.drawable.error);
                            alertDialog.setCancelable(false);

                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog,int which)
                                {
                                    dialog.dismiss();
                                    displayAlertDialog();

                                }
                            });
                            alertDialog.show();
                        }
                        else
                        {
                            ReasonText = et_Reason.getText().toString();
                            if(isOnline())
                            {
                               GetNoStoreVisitForDay task = new GetNoStoreVisitForDay(DayStartActivity.this);
                                task.execute();
                            }
                            else
                            {
                                dbengine.updateReasonIdAndDescrtblNoVisitStoreDetails(ReasonId,ReasonText);
                                dbengine.updateCurDatetblNoVisitStoreDetails(fDate);
                                dbengine.updateSstattblNoVisitStoreDetails(3);


                                String aab=dbengine.fetchReasonDescr();

                                showNoConnAlert();

                            }



                        }
                    }
                    else
                    {
                        if(isOnline())
                        {
                           GetNoStoreVisitForDay task = new GetNoStoreVisitForDay(DayStartActivity.this);
                            task.execute();
                        }
                        else
                        {
                            dbengine.updateReasonIdAndDescrtblNoVisitStoreDetails(ReasonId,ReasonText);
                            dbengine.updateCurDatetblNoVisitStoreDetails(fDate);
                            dbengine.updateSstattblNoVisitStoreDetails(3);
                            showNoConnAlert();

                        }


                    }*/


                }

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
        return str;
    }

    private class GetNoStoreVisitForDay extends AsyncTask<Void, Void, Void>
    {
        ServiceWorker newservice = new ServiceWorker();

        ProgressDialog pDialogGetNoStoreVisitForDay;

        public GetNoStoreVisitForDay(DayStartActivity activity)
        {
            pDialogGetNoStoreVisitForDay = new ProgressDialog(activity);
        }


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();


            pDialogGetNoStoreVisitForDay.setTitle(getText(R.string.PleaseWaitMsg));
            pDialogGetNoStoreVisitForDay.setMessage(getText(R.string.RetrivingDataMsg));
            pDialogGetNoStoreVisitForDay.setIndeterminate(false);
            pDialogGetNoStoreVisitForDay.setCancelable(false);
            pDialogGetNoStoreVisitForDay.setCanceledOnTouchOutside(false);
            pDialogGetNoStoreVisitForDay.show();


        }

        @Override
        protected Void doInBackground(Void... params)
        {

            try {


                for(int mm = 1; mm < 2  ; mm++)
                {
                    if(mm==1)
                    {
                        newservice = newservice.getCallspSaveReasonForNoVisit(getApplicationContext(), fDate, getIMEI(), ReasonId,ReasonText);

                        if(!newservice.director.toString().trim().equals("1"))
                        {
                            if(chkFlgForErrorToCloseApp==0)
                            {
                                chkFlgForErrorToCloseApp=1;
                            }

                        }

                    }



                }


            } catch (Exception e)
            {
                Log.i("SvcMgr", "Service Execution Failed!", e);
            }

            finally
            {
                Log.i("SvcMgr", "Service Execution Completed...");
            }

            return null;
        }

        @Override
        protected void onCancelled()
        {
            Log.i("SvcMgr", "Service Execution Cancelled");
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);


            if(pDialogGetNoStoreVisitForDay.isShowing())
            {
                pDialogGetNoStoreVisitForDay.dismiss();
            }

            if(chkFlgForErrorToCloseApp==0)
            {
                dbengine.updateReasonIdAndDescrtblNoVisitStoreDetails(ReasonId,ReasonText);
                dbengine.updateCurDatetblNoVisitStoreDetails(fDate);

                dbengine.updateSstattblNoVisitStoreDetails(3);
                String aab=dbengine.fetchReasonDescr();
                //noVisit_tv.setText("Reason :"+ReasonText);
            }
            else
            {
                dbengine.updateSstattblNoVisitStoreDetailsAfterSync(4);

              /*  if(RowId==0)
                {
                    dbengine.updateReasonIdAndDescrtblNoVisitStoreDetails(ReasonId,ReasonText);
                    dbengine.updateCurDatetblNoVisitStoreDetails(fDate);
                    dbengine.updateSstattblNoVisitStoreDetails(3);
                    String aab=dbengine.fetchReasonDescr();
                   // noVisit_tv.setText("Reason :"+ReasonText);
                   // ll_noVisit.setEnabled(false);

                }
                else
                {
                    dbengine.updateSstattblNoVisitStoreDetailsAfterSync(4);
                    String aab=dbengine.fetchReasonDescr();
                  //  noVisit_tv.setText("Reason :"+ReasonText);
                   // ll_noVisit.setEnabled(false);

                }*/
                finishAffinity();
            }
        }
    }

    private class bgTasker extends AsyncTask<Void, Void, Void> {



        @Override
        protected Void doInBackground(Void... params) {

            try {


               /* dbengine.open();
                String rID=dbengine.GetActiveRouteID();
                 dbengine.close();
*/


             //  pDialog2.dismiss();
                   // dbengine.open();

                    //dbengine.updateActiveRoute(rID, 0);
                   // dbengine.close();
                    // sync here


                    SyncNow();



            } catch (Exception e) {}

            finally {}

            return null;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            pDialog2 = ProgressDialog.show(DayStartActivity.this,getText(R.string.PleaseWaitMsg),getText(R.string.genTermProcessingRequest), true);
            pDialog2.setIndeterminate(true);
            pDialog2.setCancelable(false);
            pDialog2.show();

        }

        @Override
        protected void onCancelled() {
            Log.i("bgTasker", "bgTasker Execution Cancelled");
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Log.i("bgTasker", "bgTasker Execution cycle completed");
            pDialog2.dismiss();
           // whatTask = 0;

        }
    }

    public void SyncNow()
    {

        syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);


        dbengine.open();
        String presentRoute="0";
        dbengine.close();
        //syncTIMESTAMP = System.currentTimeMillis();
        //Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd.MMM.yyyy.HH.mm.ss",Locale.ENGLISH);
        //fullFileName1 = df.format(dateobj);
        String newfullFileName=getIMEI()+"."+presentRoute+"."+ df.format(dateobj);



        try
        {

            File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

            if (!OrderXMLFolder.exists())
            {
                OrderXMLFolder.mkdirs();
            }

            String routeID="0";

            DASFA.open();
            DASFA.export(dbengine.DATABASE_NAME, newfullFileName,routeID);


            DASFA.close();

            dbengine.savetbl_XMLfiles(newfullFileName, "3","1");

            dbengine.fnSettblAttandanceDetails();


            if(isOnline())
            {
                Intent syncIntent = new Intent(DayStartActivity.this, SyncMaster.class);
                syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                syncIntent.putExtra("OrigZipFileName", newfullFileName);
                syncIntent.putExtra("whereTo", "DayStart");
                startActivity(syncIntent);
                finish();
            }
            else
            {
                showNoConnAlert();
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}

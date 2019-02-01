package project.astix.com.ltfoodsosfaindirect;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;

import com.astix.Common.CommonInfo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SyncRegistrationDetails extends Activity
{

    // New Sync way
    public Timer timerForDataSubmission;
    public	MyTimerTaskForDataSubmission myTimerTaskForDataSubmission;
    String     ButtonClick="";

    SyncImageDataFromFolder task1;
    SyncXMLfileData task2;
    private File[] listFileFolder;
    public  File fileintialFolder;
    private String FilePathStrings;
    public String fnameIMG;

    public String UploadingImageName;

    private File[] listFile;
    public  File fileintial;
    //  public String routeID="0";
    String xmlFileName;
    int serverResponseCode = 0;

    public String routeID="0";


    Timer timer,timer2;
    String progressMsg;

    public ProgressDialog pDialogGetStores;


    public String[] xmlForWeb = new String[1];


    HttpEntity resEntity;
    private SyncRegistrationDetails _activity;



    public int syncFLAG = 0;
    public int res_code;
    public String zipFileName;
    ProgressDialog PDpicTasker;
    public String whereTo;
    public int IMGsyOK = 0;
    ProgressDialog pDialog2;
    InputStream inputStream;


    ProgressDialog pDialogGetStoresImage;

    public  File dir;
    String FROM="";
    DBAdapterKenya db = new DBAdapterKenya(this);
    DBAdapterKenya dbengine = new DBAdapterKenya(this);
    class MyTimerTaskForDataSubmission extends TimerTask
    {

        @Override
        public void run()
        {

            SyncRegistrationDetails.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (timerForDataSubmission != null) {
                        timerForDataSubmission.cancel();
                        timerForDataSubmission = null;
                    }
                    if(task1!=null){
                        if(task1.getStatus()== AsyncTask.Status.RUNNING)
                        {
                            task1.cancel(true);

                        }
                    }
                    if(task2!=null) {
                        if (task2.getStatus() == AsyncTask.Status.RUNNING) {
                            task2.cancel(true);

                        }
                    }

                    if(pDialogGetStores.isShowing())
                    {
                        pDialogGetStores.dismiss();
                    }


                    Intent submitStoreIntent=null;
                    if(FROM.equals("SPLASH")){



                        if(DayStartActivity.flgDistributorSelectedFromDropdown==1) {


                                Date date1 = new Date();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                            String fDate = sdf.format(date1).toString().trim();


                            String DistributorNodeIDType="0^0^0";
                            if(DayStartActivity.flgDistributorSelectedFromDropdown==1)
                            {
                                DistributorNodeIDType = DayStartActivity.DistributorId_GlobalDistributorNodeType_Global;
                            }
                            else
                            {
                                DistributorNodeIDType = dbengine.fetch_DistributorNodeIDTypeFromAttendanceTable();
                            }


                            int chkDistributorStockTakeMustOrNot = dbengine.fnCheckflgSODistributorFirstVisit(Integer.parseInt(DistributorNodeIDType.split(Pattern.quote("^"))[0]), Integer.parseInt(DistributorNodeIDType.split(Pattern.quote("^"))[1]));



                                if(chkDistributorStockTakeMustOrNot==0){
                                    submitStoreIntent=new Intent(SyncRegistrationDetails.this,DistributorEntryActivity.class);
                                    //Intent i=new Intent(DistributorCheckInForStock.this,DistributorEntryActivity.class);
                                    submitStoreIntent.putExtra("DistributorName",  DistributorNodeIDType.split(Pattern.quote("^"))[2]);
                                    submitStoreIntent.putExtra("imei", CommonInfo.imei);
                                    submitStoreIntent.putExtra("fDate", fDate);
                                }
                                else{
                                    submitStoreIntent=new Intent(SyncRegistrationDetails.this,AllButtonActivity.class);
                                }

                        }
                        else
                        {
                            submitStoreIntent=new Intent(SyncRegistrationDetails.this,AllButtonActivity.class);
                        }

                    }
                    else{
                        submitStoreIntent=new Intent(SyncRegistrationDetails.this,AllButtonActivity.class);
                        if(ButtonClick.equals("DAYEND")){
                            submitStoreIntent.putExtra("Button",ButtonClick);
                        }
                    }
                    startActivity(submitStoreIntent);
                    finish();
                }});
        }

    }
    public void showSyncError()
    {
        AlertDialog.Builder alertDialogSyncError = new AlertDialog.Builder(SyncRegistrationDetails.this);
        alertDialogSyncError.setTitle("Sync Error!");
        alertDialogSyncError.setCancelable(false);


        alertDialogSyncError.setMessage(getText(R.string.syncAlertErrMsg));

        alertDialogSyncError.setNeutralButton("OK",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                Intent submitStoreIntent;
                if(FROM.equals("SPLASH")){
                    submitStoreIntent=new Intent(SyncRegistrationDetails.this,AllButtonActivity.class);
                }
                else{
                    submitStoreIntent=new Intent(SyncRegistrationDetails.this,AllButtonActivity.class);
                    if(ButtonClick.equals("DAYEND")){
                        submitStoreIntent.putExtra("Button",ButtonClick);
                    }
                }
                startActivity(submitStoreIntent);
                finish();

            }
        });
        alertDialogSyncError.setIcon(R.drawable.sync_error_ico);

        AlertDialog alert = alertDialogSyncError.create();
        alert.show();

    }
    public void showSyncErrorStart()
    {
        AlertDialog.Builder alertDialogSyncError = new AlertDialog.Builder(SyncRegistrationDetails.this);
        alertDialogSyncError.setTitle("Sync Error!");
        alertDialogSyncError.setCancelable(false);
        alertDialogSyncError.setMessage("Sync Error! \n\n Please check your Internet Connectivity & Try Again!");
        alertDialogSyncError.setNeutralButton("OK",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                Intent submitStoreIntent;
                if(FROM.equals("SPLASH")){
                    submitStoreIntent=new Intent(SyncRegistrationDetails.this,AllButtonActivity.class);
                }
                else{
                    submitStoreIntent=new Intent(SyncRegistrationDetails.this,AllButtonActivity.class);
                    if(ButtonClick.equals("DAYEND")){
                        submitStoreIntent.putExtra("Button",ButtonClick);
                    }
                }
                startActivity(submitStoreIntent);
                finish();
            }
        });
        alertDialogSyncError.setIcon(R.drawable.sync_error_ico);

        AlertDialog alert = alertDialogSyncError.create();
        alert.show();

    }
    public void showSyncSuccessStart()
    {
        AlertDialog.Builder alertDialogSyncOK = new AlertDialog.Builder(SyncRegistrationDetails.this);
        alertDialogSyncOK.setTitle("Information");
        alertDialogSyncOK.setCancelable(false);
       /* if(StoreSelection.flgChangeRouteOrDayEnd==3)
        {
            alertDialogSyncOK.setMessage(getText(R.string.syncAlertStoreQuotationOKMsg));
        }
        else
        {*/
        alertDialogSyncOK.setMessage(getText(R.string.syncAlertOKMsg));
        // }

        alertDialogSyncOK.setNeutralButton("OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {

                        dialog.dismiss();

                        //  db.open();
                        //System.out.println("Indubati flgChangeRouteOrDayEnd :"+StoreSelection_Old.flgChangeRouteOrDayEnd);
					/*if(StoreSelection.flgChangeRouteOrDayEnd==1 || StoreSelection.flgChangeRouteOrDayEnd==2)
					{
						db.reTruncateRouteTbl();
					}*/


                        //  db.reCreateDB();
                        //  db.close();

                        Intent submitStoreIntent;
                        if(FROM.equals("SPLASH")){
                            submitStoreIntent=new Intent(SyncRegistrationDetails.this,AllButtonActivity.class);
                        }
                        else{
                            submitStoreIntent=new Intent(SyncRegistrationDetails.this,AllButtonActivity.class);
                            if(ButtonClick.equals("DAYEND")){
                                submitStoreIntent.putExtra("Button",ButtonClick);
                            }
                        }
                        startActivity(submitStoreIntent);
                        finish();
					/*destroyNcleanup(1);
					imgs = null;
					uComments.clear();*/

                        //	finish();


                    }
                });
        alertDialogSyncOK.setIcon(R.drawable.info_ico);

        AlertDialog alert = alertDialogSyncOK.create();
        alert.show();

    }








    public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException
    {

        String res = "";
        StringBuffer buffer = new StringBuffer();
        inputStream = response.getEntity().getContent();
        int contentLength = (int) response.getEntity().getContentLength(); //getting content lengthâ€¦..
        if (contentLength < 0)
        {
        }
        else
        {
            byte[] data = new byte[512];
            int len = 0;
            try
            {
                while (-1 != (len = inputStream.read(data)) )
                {
                    buffer.append(new String(data, 0, len)); //converting to string and appending  to stringbufferâ€¦..
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            res = buffer.toString();


        }
        return res;
    }

    public void sysncStart()
    {


        String[] fp2s; // = "/mnt/sdcard/NMPphotos/1539_24-05-2013_1.jpg";

        try {
            //db.open();
            //String[] sySTidS = db.getStoreIDTblSelectedStoreIDinChangeRouteCase();
            //String date= db.GetPickerDate();
            // db.close();

            showSyncSuccessStart();



        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            db.close();
            e.printStackTrace();
        }
    }


    public static boolean deleteFolderFiles(File path)
    {

        if( path.exists() )
        {
            File[] files = path.listFiles();
            for(int i=0; i<files.length; i++)
            {
                if(files[i].isDirectory())
                {
                    deleteFolderFiles(files[i]);
                }
                else
                {
                    files[i].delete();
                }
            }
        }
        return(path.delete());

    }
    public void showSyncSuccess()
    {
        AlertDialog.Builder alertDialogSyncOK = new AlertDialog.Builder(SyncRegistrationDetails.this);
        alertDialogSyncOK.setTitle("Information");
        alertDialogSyncOK.setCancelable(false);


        alertDialogSyncOK.setMessage("submission was successful.");

        alertDialogSyncOK.setNeutralButton("OK",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {


                CommonInfo.AnyVisit=1;
                dialog.dismiss();

                Intent submitStoreIntent;
                if(FROM.equals("SPLASH")){
                    submitStoreIntent=new Intent(SyncRegistrationDetails.this,AllButtonActivity.class);
                }
                else{
                    submitStoreIntent=new Intent(SyncRegistrationDetails.this,AllButtonActivity.class);
                    if(ButtonClick.equals("DAYEND")){
                        submitStoreIntent.putExtra("Button",ButtonClick);
                    }
                }
                startActivity(submitStoreIntent);
                finish();
                // }

            }
        });
        alertDialogSyncOK.setIcon(R.drawable.info_ico);

        AlertDialog alert = alertDialogSyncOK.create();
        alert.show();

    }

    //
    public void delXML(String delPath)
    {
        File file = new File(delPath);
        file.delete();
        File file1 = new File(delPath.toString().replace(".xml", ".zip"));
        file1.delete();
    }
    //
    //
    public static void zip(String[] files, String zipFile) throws IOException
    {
        BufferedInputStream origin = null;
        final int BUFFER_SIZE = 2048;

        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
        try
        {
            byte data[] = new byte[BUFFER_SIZE];

            for (int i = 0; i < files.length; i++)
            {
                FileInputStream fi = new FileInputStream(files[i]);
                origin = new BufferedInputStream(fi, BUFFER_SIZE);
                try
                {
                    ZipEntry entry = new ZipEntry(files[i].substring(files[i].lastIndexOf("/") + 1));
                    out.putNextEntry(entry);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1)
                    {
                        out.write(data, 0, count);
                    }
                }
                finally
                {
                    origin.close();
                }
            }
        }
        finally
        {
            out.close();
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syncregistraion);

        _activity = this;

        Intent syncIntent = getIntent();
        xmlForWeb[0] = syncIntent.getStringExtra("xmlPathForSync");
        zipFileName = syncIntent.getStringExtra("OrigZipFileName");
        whereTo = syncIntent.getStringExtra("whereTo");
        FROM= syncIntent.getStringExtra("IntentFrom");
        if(FROM.equals("AllButtonActivity")){
            ButtonClick= syncIntent.getStringExtra("Button");

        }


        try
        {

            task1 = new SyncImageDataFromFolder(SyncRegistrationDetails.this);
            task1.execute();

            if (timerForDataSubmission != null) {
                timerForDataSubmission.cancel();
                timerForDataSubmission = null;
            }

            timerForDataSubmission = new Timer();
            myTimerTaskForDataSubmission = new MyTimerTaskForDataSubmission();
            timerForDataSubmission.schedule(myTimerTaskForDataSubmission, 120000);


        }
        catch(Exception e)
        {

        }



    }



   /* private class SyncImageData extends AsyncTask<Void, Void, Void>
    {
        String[] fp2s;
        String[] NoOfOutletID;

        public SyncImageData(SyncDoProfileData activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            dbengine.open();
           // routeID=dbengine.GetActiveRouteID();
            routeID="0";
            dbengine.close();

            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));

            pDialogGetStores.setMessage("Submitting Images...");

            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();


            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            {

            }
            else
            {
                // Locate the image folder in your SD Card
                fileintial = new File(Environment.getExternalStorageDirectory()
                        + File.separator + CommonInfo.ImagesFolder);
                // Create a new folder if no folder named SDImageTutorial exist
                fileintial.mkdirs();
            }


            if (fileintial.isDirectory())
            {
                listFile = fileintial.listFiles();
            }





        }

        @Override
        protected Void doInBackground(Void... params)
        {

            // Sync QualityChecklist Images

            try
            {

                try
                {

                    db.open();
                    NoOfOutletID = db.getAlltblCartSalesImages();
                    db.close();

                } catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    db.close();
                    e.printStackTrace();
                }

                for(int chkCountstore=0; chkCountstore < NoOfOutletID.length;chkCountstore++)
                {
                    db.open();
                    int NoOfImages = db.getExistingtblCartSalesPhotoDetails(NoOfOutletID[chkCountstore].toString());
                    String[] NoOfImgsPath = db.getImgsPathFortblCartSalesPHotoDetails(NoOfOutletID[chkCountstore].toString());
                    db.close();

                    fp2s = new String[2];

                    for(int syCOUNT = 0; syCOUNT < NoOfImages; syCOUNT++)
                    {
                        fp2s[0] = NoOfImgsPath[syCOUNT];
                        fp2s[1] = NoOfOutletID[chkCountstore];

                        // New Way

                        fnameIMG = fp2s[0];
                        UploadingImageName=fp2s[0];


                        String stID = fp2s[1];
                        String currentImageFileName=fnameIMG;

                        boolean isImageExist=false;
                        for (int i = 0; i < listFile.length; i++)
                        {
                            FilePathStrings = listFile[i].getAbsolutePath();
                            if(listFile[i].getName().equals(fnameIMG))
                            {
                                fnameIMG=listFile[i].getAbsolutePath();
                                isImageExist=true;
                                break;
                            }
                        }
                        if(isImageExist)
                        {
								*//*Bitmap bmp = BitmapFactory.decodeFile(fnameIMG);
								ByteArrayOutputStream stream = new ByteArrayOutputStream();

								String image_str=  BitMapToString(bmp);
								ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();*//*

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();

                            String image_str= compressImage(fnameIMG);// BitMapToString(bmp);
                            ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();
                            try
                            {
                                stream.flush();
                            }
                            catch (IOException e1)
                            {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            try
                            {
                                stream.close();
                            }
                            catch (IOException e1)
                            {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }

                            long syncTIMESTAMP = System.currentTimeMillis();
                            Date datefromat = new Date(syncTIMESTAMP);
                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
                            String onlyDate=df.format(datefromat);


                            nameValuePairs.add(new BasicNameValuePair("image", image_str));
                            nameValuePairs.add(new BasicNameValuePair("FileName",currentImageFileName));
                            nameValuePairs.add(new BasicNameValuePair("comment","NA"));
                            nameValuePairs.add(new BasicNameValuePair("storeID",stID));
                            nameValuePairs.add(new BasicNameValuePair("date",onlyDate));
                            nameValuePairs.add(new BasicNameValuePair("routeID",routeID));

                            try
                            {

                                HttpParams httpParams = new BasicHttpParams();
                                HttpConnectionParams.setSoTimeout(httpParams, 0);
                                HttpClient httpclient = new DefaultHttpClient(httpParams);
                                HttpPost httppost = new HttpPost(CommonInfo.ImageSyncPath.trim());


                                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                                HttpResponse response = httpclient.execute(httppost);
                                String the_string_response = convertResponseToString(response);

                                System.out.println("Sunil Doing Testing Response after sending Image" + the_string_response);

                                //  if(serverResponseCode == 200)
                                if(the_string_response.equals("Abhinav"))
                                {

                                    System.out.println("Sunil Doing Testing Response after sending Image inside if" + the_string_response);
                                    db.updateImagetblCartSalesCheckinPhotoDetails(UploadingImageName.toString().trim());
                                    // String file_dj_path = Environment.getExternalStorageDirectory() + "/RSPLSFAImages/"+UploadingImageName.toString().trim();
                                    String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +UploadingImageName.toString().trim();

                                    File fdelete = new File(file_dj_path);
                                    if (fdelete.exists())
                                    {
                                        if (fdelete.delete())
                                        {
                                            //Log.e("-->", "file Deleted :" + file_dj_path);
                                            callBroadCast();
                                        }
                                        else
                                        {
                                            //Log.e("-->", "file not Deleted :" + file_dj_path);
                                        }
                                    }
						            	*//* File file = new File(UploadingImageName.toString().trim());
							         	    file.delete();  *//*
                                }

                            }catch(Exception e)
                            {
                                IMGsyOK = 1;

                            }
                        }


                    }


                }

            }
            catch(Exception e)
            {
                IMGsyOK = 1;

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
            if(pDialogGetStores.isShowing())
            {
                pDialogGetStores.dismiss();
            }

            if(IMGsyOK == 1)
            {
                IMGsyOK = 0;
                if (myTimerTaskForDataSubmission != null) {
                    myTimerTaskForDataSubmission.cancel();
                    myTimerTaskForDataSubmission=null;
                }
                if (timerForDataSubmission!=null)
                {
                    timerForDataSubmission.cancel();
                    timerForDataSubmission = null;
                }
                showSyncErrorStart();
            }
            else
            {

                try
                {
                    task2 = new SyncXMLfileData(SyncDoProfileData.this);
                    task2.execute();
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        }
    }*/

    /*public String BitMapToString(Bitmap bitmap)
    {
        int h1=bitmap.getHeight();
        int w1=bitmap.getWidth();
        h1=h1/8;
        w1=w1/8;
        bitmap=Bitmap.createScaledBitmap(bitmap, w1, h1, true);

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] arr=baos.toByteArray();
        String result= Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
    }*/

    public String BitMapToString(Bitmap bitmap)
    {
        int h1=bitmap.getHeight();
        int w1=bitmap.getWidth();

        if(w1 > 768 || h1 > 1024){
            bitmap=Bitmap.createScaledBitmap(bitmap,1024,768,true);

        }


        else {

            bitmap=Bitmap.createScaledBitmap(bitmap,w1,h1,true);
        }

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] arr=baos.toByteArray();
        String result=Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
    }


    private class SyncXMLfileData extends AsyncTask<Void, Void, Integer>
    {



        public SyncXMLfileData(SyncRegistrationDetails activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();


            File MeijiIndirectSFAxmlFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

            if (!MeijiIndirectSFAxmlFolder.exists())
            {
                MeijiIndirectSFAxmlFolder.mkdirs();
            }

            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));

            pDialogGetStores.setMessage("Submitting Details...");

            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();

        }

        @Override
        protected Integer doInBackground(Void... params)
        {



            try {

                String xmlfileNames = dbengine.fnGetXMLFileWithFlag("3","10");

                int index=0;
                if(!xmlfileNames.equals(""))
                {
                    String[] xmlfileArray= xmlfileNames.split(Pattern.quote("^"));

                    for(int i=0;i<xmlfileArray.length;i++){
                        System.out.println("index"+index);
                        xmlFileName=xmlfileArray[i];



//



                        String newzipfile = Environment.getExternalStorageDirectory() + "/"+ CommonInfo.OrderXMLFolder+"/" + xmlFileName + ".zip";
                        xmlForWeb[0]=         Environment.getExternalStorageDirectory() + "/"+ CommonInfo.OrderXMLFolder+"/" + xmlFileName + ".xml";
                        try
                        {
                            zip(xmlForWeb, newzipfile);
                        }
                        catch (Exception e1)
                        {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }

                        HttpURLConnection conn = null;
                        DataOutputStream dos = null;
                        String lineEnd = "\r\n";
                        String twoHyphens = "--";
                        String boundary = "*****";
                        int bytesRead, bytesAvailable, bufferSize;
                        byte[] buffer;
                        int maxBufferSize = 1 * 1024 *1024;

                        File file2send = new File(newzipfile);

                        // It is for Testing Purpose
                        //String urlString = "http://115.124.126.184/ReadXML_PragaSFAForTest/default.aspx?CLIENTFILENAME=" + zipFileName;

                        // It is for Live Purpose
                        // String urlString = "http://115.124.126.184/ReadXML_PragaSFA/default.aspx?CLIENTFILENAME=" + zipFileName;



                        String urlString = CommonInfo.OrderSyncPath.trim()+"?CLIENTFILENAME=" + xmlFileName+".zip";



                        try
                        {

                            // open a URL connection to the Servlet
                            FileInputStream fileInputStream = new FileInputStream(file2send);
                            URL url = new URL(urlString);

                            // Open a HTTP  connection to  the URL
                            conn = (HttpURLConnection) url.openConnection();
                            conn.setDoInput(true); // Allow Inputs
                            conn.setDoOutput(true); // Allow Outputs
                            conn.setUseCaches(false); // Don't use a Cached Copy
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Connection", "Keep-Alive");
                            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                            conn.setRequestProperty("zipFileName", xmlFileName);

                            dos = new DataOutputStream(conn.getOutputStream());

                            dos.writeBytes(twoHyphens + boundary + lineEnd);
                            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                                    + xmlFileName + "\"" + lineEnd);

                            dos.writeBytes(lineEnd);

                            // create a buffer of  maximum size
                            bytesAvailable = fileInputStream.available();

                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            buffer = new byte[bufferSize];

                            // read file and write it into form...
                            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                            while (bytesRead > 0)
                            {

                                dos.write(buffer, 0, bufferSize);
                                bytesAvailable = fileInputStream.available();
                                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                            }

                            // send multipart form data necesssary after file data...
                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                            // Responses from the server (code and message)
                            serverResponseCode = conn.getResponseCode();
                            String serverResponseMessage = conn.getResponseMessage();

                            Log.i("uploadFile", "HTTP Response is : "
                                    + serverResponseMessage + ": " + serverResponseCode);

                            if(serverResponseCode == 200)
                            {

                               dbengine.upDateTblXmlFile(xmlFileName);
                                dbengine.deleteXmlTable("4");
		                     /*dbengine.upDatetbl_allAnswermstr("3");
		                     dbengine.upDatetbl_DynamcDataAnswer("3");*/





                                deleteViewdXml(CommonInfo.OrderXMLFolder+"/"+xmlFileName+".xml");
                                deleteViewdXml(CommonInfo.OrderXMLFolder+"/"+xmlFileName+".zip");


                            }

                            //close the streams //
                            fileInputStream.close();
                            dos.flush();
                            dos.close();
                            index++;
                        } catch (MalformedURLException ex)
                        {

                            if(pDialogGetStores.isShowing())
                            {
                                pDialogGetStores.dismiss();
                            }
                            ex.printStackTrace();


                        } catch (Exception e)
                        {

                            if(pDialogGetStores.isShowing())
                            {
                                pDialogGetStores.dismiss();
                            }

                        }

                        // pDialogGetInvoiceForDay.dismiss();



                    }
                }
                else
                {
                    serverResponseCode=200;
                }
            }
            catch (Exception e) {

                e.printStackTrace();
            }
            return serverResponseCode;
        }

        @Override
        protected void onCancelled()
        {
            Log.i("SyncMaster", "Sync Cancelled");
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            super.onPostExecute(result);
            if(!isFinishing())
            {

                Log.i("SyncMaster", "Sync cycle completed");


                if(pDialogGetStores.isShowing())
                {
                    pDialogGetStores.dismiss();
                }

                if (myTimerTaskForDataSubmission != null) {
                    myTimerTaskForDataSubmission.cancel();
                    myTimerTaskForDataSubmission=null;
                }
                if(result!=200)
                {
                    if (timerForDataSubmission!=null)
                    {
                        timerForDataSubmission.cancel();
                        timerForDataSubmission = null;
                    }
                    if (timer!=null)
                    {
                        timer.cancel();
                        timer = null;
                    }
                    if(timer2!=null)
                    {
                        timer2.cancel();
                        timer2 = null;
                    }

                    showSyncError();

                  /*  Intent submitStoreIntent = new Intent(SyncCartData.this, AllButtonActivity.class);
                    startActivity(submitStoreIntent);
                    finish();*/


                }
                else
                {
                    if (timerForDataSubmission!=null)
                    {
                        timerForDataSubmission.cancel();
                        timerForDataSubmission = null;
                    }
                    if (timer!=null)
                    {
                        timer.cancel();
                        timer = null;
                    }
                    if(timer2!=null)
                    {
                        timer2.cancel();
                        timer2 = null;
                    }
                    int Sstat=4;
                   /* db.open();
                    db. deletetblVRSCheckinCartVisitPhoto();
                    db.updateCARTVISITRecordsSyncd(Sstat);     // update syncd' records
                    db.close();*/

                    //dbengine.fndeleteSbumittedStoreList(4);
                   dbengine.open();
                    dbengine. deleteRegistrationTable(4);
                    dbengine.close();

                    showSyncSuccess();



                }




            }
            else
            {
                if(pDialogGetStores.isShowing())
                {
                    pDialogGetStores.dismiss();
                }

                if (timerForDataSubmission!=null)
                {
                    timerForDataSubmission.cancel();
                    timerForDataSubmission = null;
                }
                if (myTimerTaskForDataSubmission != null) {
                    myTimerTaskForDataSubmission.cancel();
                    myTimerTaskForDataSubmission=null;
                }




                if (timer!=null)
                {
                    timer.cancel();
                    timer = null;
                }
                if(timer2!=null)
                {
                    timer2.cancel();
                    timer2 = null;
                }



                Intent submitStoreIntent;
                if(FROM.equals("SPLASH")){
                    submitStoreIntent=new Intent(SyncRegistrationDetails.this,AllButtonActivity.class);
                }
                else{
                    submitStoreIntent=new Intent(SyncRegistrationDetails.this,AllButtonActivity.class);
                    if(ButtonClick.equals("DAYEND")){
                        submitStoreIntent.putExtra("Button",ButtonClick);
                    }
                }
                startActivity(submitStoreIntent);
                finish();

            }

        }



    }

    public void deleteViewdXml(String file_dj_path)
    {
        File dir=   Environment.getExternalStorageDirectory();
        File fdelete=new File(dir,file_dj_path);
        // File fdelete = new File(file_dj_path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                Log.e("-->", "file Deleted :" + file_dj_path);
                callBroadCast();
            } else {
                Log.e("-->", "file not Deleted :" + file_dj_path);
            }
        }


    }


    public void callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {

                public void onScanCompleted(String path, Uri uri) {

                }
            });
        } else {
            Log.e("-->", " < 14");
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }



    }



    public static String[] checkNumberOfFiles(File dir)
    {
        int NoOfFiles=0;
        String [] Totalfiles = null;

        if (dir.isDirectory())
        {
            String[] children = dir.list();
            NoOfFiles=children.length;
            Totalfiles=new String[children.length];

            for (int i=0; i<children.length; i++)
            {
                Totalfiles[i]=children[i];
            }
        }
        return Totalfiles;
    }

    public String compressImage(String imageUri) {
        String filePath = imageUri;//getRealPathFromURI(imageUri);
        Bitmap scaledBitmap=null;
        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 1024.0f;
        float maxWidth = 768.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[768*1024];

        //bmp
      /*try {
//          load the bitmap from its path


      } catch (OutOfMemoryError exception) {
         exception.printStackTrace();

      }
*/


      /*if(actualWidth > 768 || h1 > 1024)
      {
         bitmap=Bitmap.createScaledBitmap(bitmap,1024,768,true);
      }
      else
      {

         bitmap=Bitmap.createScaledBitmap(bitmap,w1,h1,true);
      }*/
        //Bitmap bitmap=Bitmap.createScaledBitmap(bmp,actualWidth,actualHeight,true);
        //     bmp =BitmapFactory.decodeFile(filePath, options);//Bitmap.createScaledBitmap(bmp,actualWidth,actualHeight,true);;//

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);

            //bmp=Bitmap.createScaledBitmap(bmp,actualWidth,actualHeight,true);
            //scaledBitmap=Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
            // bmp=Bitmap.createScaledBitmap(bmp,actualWidth,actualHeight,true);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }

        //Bitmap scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
        //bmp=Bitmap.createScaledBitmap(bmp,actualWidth,actualHeight,true);
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        //scaledBitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        bmp.compress(Bitmap.CompressFormat.JPEG,100, baos);

        byte [] arr=baos.toByteArray();
        String result=Base64.encodeToString(arr, Base64.DEFAULT);
        return result;

      /*try {
//          load the bitmap from its path
         bmp = BitmapFactory.decodeFile(filePath, options);
      } catch (OutOfMemoryError exception) {
         exception.printStackTrace();

      }
      try {
         scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
      } catch (OutOfMemoryError exception) {
         exception.printStackTrace();
      }*/

      /*float ratioX = actualWidth / (float) options.outWidth;
      float ratioY = actualHeight / (float) options.outHeight;
      float middleX = actualWidth / 2.0f;
      float middleY = actualHeight / 2.0f;*/


        //return filename;

    }
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;      }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }
    private class SyncImageDataFromFolder extends AsyncTask<Void, Void, Void>
    {
        String[] fp2s;
        String[] NoOfOutletID;


        public SyncImageDataFromFolder(SyncRegistrationDetails activity)
        {
            pDialogGetStoresImage = new ProgressDialog(activity);

        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();


            pDialogGetStoresImage.setTitle(getText(R.string.genTermPleaseWaitNew));

            pDialogGetStoresImage.setMessage("Submitting Images...");

            pDialogGetStoresImage.setIndeterminate(false);
            pDialogGetStoresImage.setCancelable(false);
            pDialogGetStoresImage.setCanceledOnTouchOutside(false);
            pDialogGetStoresImage.show();


            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            {

            }
            else
            {
                // Locate the image folder in your SD Card
                fileintialFolder = new File(Environment.getExternalStorageDirectory()
                        + File.separator + CommonInfo.ImagesFolder);
                // Create a new folder if no folder named SDImageTutorial exist
                fileintialFolder.mkdirs();
            }


            if (fileintialFolder.isDirectory())
            {
                listFileFolder = fileintialFolder.listFiles();
            }





        }

        @Override
        protected Void doInBackground(Void... params)
        {


            // Sync Registration Images

            try
            {

                if(listFileFolder!=null && listFileFolder.length>0)
                {
                    for(int chkCountstore=0; chkCountstore < listFileFolder.length;chkCountstore++)
                    {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        String image_str= compressImage(listFileFolder[chkCountstore].getAbsolutePath());// BitMapToString(bmp);
                        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                        String UploadingImageName=listFileFolder[chkCountstore].getName();
                        dbengine.open();
                       int flagCheck= dbengine. CheckImageIntable(UploadingImageName);
                        dbengine.close();
                        //flagCheck 1 means image is exist in table and 0 means not available
                        if(flagCheck==1){



                        try
                        {
                            stream.flush();
                        }
                        catch (IOException e1)
                        {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        try
                        {
                            stream.close();
                        }
                        catch (IOException e1)
                        {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }

                        long syncTIMESTAMP = System.currentTimeMillis();
                        Date datefromat = new Date(syncTIMESTAMP);
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS", Locale.ENGLISH);
                        String onlyDate=df.format(datefromat);


                        nameValuePairs.add(new BasicNameValuePair("image", image_str));
                        nameValuePairs.add(new BasicNameValuePair("FileName",UploadingImageName));
                        nameValuePairs.add(new BasicNameValuePair("comment","NA"));
                        nameValuePairs.add(new BasicNameValuePair("storeID","0"));
                        nameValuePairs.add(new BasicNameValuePair("date",onlyDate));
                        nameValuePairs.add(new BasicNameValuePair("routeID","0"));

                        try
                        {

                            HttpParams httpParams = new BasicHttpParams();
                            HttpConnectionParams.setSoTimeout(httpParams, 0);
                            HttpClient httpclient = new DefaultHttpClient(httpParams);
                            HttpPost httppost = new HttpPost(CommonInfo.ImageSyncPath.trim());


                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            String the_string_response = convertResponseToString(response);

                            System.out.println("Sunil Doing Testing Response after sending Image" + the_string_response);

                            //  if(serverResponseCode == 200)
                            if(the_string_response.equals("Abhinav"))
                            {


                                String file_dj_path = Environment.getExternalStorageDirectory() + "/"+ CommonInfo.ImagesFolder+"/"+ UploadingImageName.trim();
                                File fdelete = new File(file_dj_path);
                                if (fdelete.exists())
                                {
                                    if (fdelete.delete())
                                    {
                                        Log.e("-->", "file Deleted :" + file_dj_path);
                                        callBroadCast();
                                    }
                                    else
                                    {
                                        Log.e("-->", "file not Deleted :" + file_dj_path);
                                    }
                                }


                            }

                        }catch(Exception e)
                        {
                            IMGsyOK=1;

                        }





                    }
                }}


            }
            catch(Exception e)
            {
                IMGsyOK=1;

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
            if(pDialogGetStoresImage.isShowing() && pDialogGetStoresImage!=null)
            {
                pDialogGetStoresImage.dismiss();
            }

            //  version checkup

            if(IMGsyOK == 1)
            {
                IMGsyOK = 0;

                showSyncErrorStart();
            }
            else
            {

                try
                {
                    task2 = new SyncXMLfileData(SyncRegistrationDetails.this);
                    task2.execute();
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        }
    }
}


package project.astix.com.ltfoodsosfaindirect;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import project.astix.com.ltfoodsosfaindirect.R;

public class HttpUtils {

   public static String requestData(String urlString, ArrayList<NameValuePair> nameValuePairs){
       try {
           URL url = new URL(urlString);
           HttpURLConnection conn = (HttpURLConnection) url.openConnection();
           conn.setReadTimeout(0);
           conn.setConnectTimeout(0);
           conn.setRequestMethod("POST");
           conn.setDoInput(true);
           conn.setDoOutput(true);

           OutputStream os = conn.getOutputStream();
           BufferedWriter writer = new BufferedWriter(
                   new OutputStreamWriter(os, "UTF-8"));
           if(nameValuePairs!=null && nameValuePairs.size()>0)
               writer.write(getQuery(nameValuePairs));
           writer.flush();
           writer.close();
           os.close();

           conn.connect();
           BufferedReader br;
           if (200 <= conn.getResponseCode() && conn.getResponseCode() <= 299) {
               br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
           } else {
               br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
           }
           StringBuilder the_string_response = new StringBuilder();
           String output;
           while ((output = br.readLine()) != null) {
               the_string_response.append(output);
           }
           return the_string_response.toString();

       }catch (Exception e){
           e.printStackTrace();
       }

       return "";

   }

    private static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }


}

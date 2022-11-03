package com.lunar.Voice;


import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SMS {

    public static String sendSms(ArrayList<Map> MessageLists) {
        Gson g=new Gson();
        Map data =new HashMap();
        data.put("SenderId", "PALEAHSTORE");
        data.put("ApiKey", "GUFNiyEXJRY+XtyX0Ex+Gb3FSDhL1L5hPK7ttnWrlW0=");
        data.put("ClientId", "57008561-2308-4156-b793-a5985205a2a5");
        data.put("messageParameters", MessageLists);


        String query_url = "https://api.onfonmedia.co.ke/v1/sms/SendBulkSMS";




        System.err.println(g.toJson(data));

        try {
            URL url = new URL(query_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("AccessKey", "gbslyuqNRT7E6mU78VDdlC14ErB9uLmn");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(g.toJson(data).getBytes("UTF-8"));
            os.close();
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = "";
            //   String result = IOUtils.toString(in, "UTF-8");





            BufferedInputStream bis = new BufferedInputStream(in);
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            for (int res = bis.read(); res != -1; res = bis.read()) {
                buf.write((byte) res);
            }
// StandardCharsets.UTF_8.name() > JDK 7
            result= buf.toString("UTF-8");

            in.close();

            conn.disconnect();
            System.err.println(result);

            if(result.contains("["))
            {
//                 JSONArray jsonArray = new JSONArray(result);
//         // jsonArray.put(myResponse);
//               System.out.println(jsonArray);
//               for(int i =0;i<=jsonArray.length();++i)
//               {
//                   System.out.println(jsonArray.getJSONObject(i).get("status"));
//               }
                return MessageLists.size()+" Message(s) Sent SuccessFully";
            }
            else{
                JSONObject myResponse = new JSONObject(result);

                if(myResponse.getString("status").equalsIgnoreCase("201"))
                {
                    return "Error: Invalid Credentials,Please Check The Credentials And Try Again";
                }
                else if(myResponse.getString("status").equalsIgnoreCase("101")){
                    return "Error :You Have Insufficient Balance,Please Recharge and Try Again.Balance ksh:"+myResponse.getString("Balance");

                }
                else if(myResponse.getString("status").equalsIgnoreCase("301")){
                    return "Error :Invalid senderID!";

                }
                else if(myResponse.getString("status").equalsIgnoreCase("0")){
                    return "Messages Sent Successfully";

                }
                else{
                    return "Error: Unknown,Please Contact Developer";
                }
            }



        } catch (Exception e) {

            e.printStackTrace();
            return "Error Occured"+e.toString();
        }

    }


public String sendCallAlert(String sessionId)
{
    Calls calls=new Calls();
    Map <String,String>callDetails=calls.getCallDetails(sessionId);
    String directedTo=  callDetails.get("directedTo");
    VoiceUser voiceUser=new VoiceUser();
    String userDirectedToPhoneNumber=voiceUser.userDetails(directedTo).get("phoneNumber").toString();
    if(!directedTo.isEmpty())
    {
        ArrayList   messagesList=new ArrayList<Map>();
        HashMap messageData=new HashMap<String,String>();
        messageData.put("Text", "You Received A Call From "+callDetails.get("callerContactName")+" "+callDetails.get("CallerNumber"));


        messageData.put("Number", userDirectedToPhoneNumber.replaceFirst("0","254"));
        messagesList.add(messageData);
        sendSms(messagesList);
    }

return "";
}
}

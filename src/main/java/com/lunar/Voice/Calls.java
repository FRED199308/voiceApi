package com.lunar.Voice;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Calls {
DbConnection dbConnection=new DbConnection();

    public ArrayList getAllLoges(String username)
    {
        ArrayList<Map> arrayList=new ArrayList<Map>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectDb();
        try {


            String sqll="Select * from calls where  username='"+username+"' order by datesaved desc";

            ps=con.prepareStatement(sqll);
            rs= ps.executeQuery();
            while(rs.next())
            {
                Map map=new HashMap();
                map.put("responseCode","200");
                map.put("responseDescription","success");
                map.put("feedBack",rs.getString("feedBack"));
                map.put("UserName",rs.getString("Username"));
                map.put("duration",rs.getString("duration"));
                map.put("sessionId",rs.getString("sessionId"));
                map.put("datecalled",rs.getString("Datecalled"));
                map.put("status",rs.getString("status"));
                map.put("level",rs.getString("level"));

                arrayList.add(map);


            }
            return arrayList;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            Map map=new HashMap<>();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
            arrayList.add(map);
            return arrayList;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }
    public  Map registerCall(String username,String Datecalled,String Status,String CallerNumber,
                             String NumberCalled,String calltype,String sessionid,String callstate)
    {
        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {



if(calltype.equalsIgnoreCase("outbound"))
{
    NumberCalled=CallerNumber;
    CallerNumber=username;
}
else {
    username="";
}
                    String querry3 = "insert into calls (username,Datecalled,Status,CallerNumber,NumberCalled,calltype,sessionid,callstate,dateSaved) Values('"+username+"','"+Datecalled+"','"+Status+"','"+CallerNumber+"','"+NumberCalled+"','"+calltype+"','"+sessionid+"','"+callstate +"',now())";
                    ps = con.prepareStatement(querry3);
                    ps.execute();


                    map.put("responseCode","200");
                    map.put("responseDescription","call Registered SuccessFully");
                    return map;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
            return map;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }





    public  Map updateCalls(String callcost,String callstate,String callSessionState,String durationInSeconds,String sessionId,String recordUrl)
    {
        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {
 String querry3 = "update  calls set callSessionState='"+callSessionState+"',durationInSeconds='"+durationInSeconds+"',callcost='"+callcost+"',status='"+callstate+"',callRecordUrl='"+recordUrl+"' where sessionid='"+sessionId+"'";
            ps = con.prepareStatement(querry3);
            ps.execute();


            map.put("responseCode","200");
            map.put("responseDescription","call Updated SuccessFully");
            return map;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
            return map;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }


    public  Map saveFeedBack(String numberCalled,String username,String callerNumber,String feedBack)
    {
        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {


            String querry3 = "update  calls set feedBack='"+feedBack+"' where userName ='"+username+"' or directedTo='"+username+"' order by datesaved desc  limit 1";
            ps = con.prepareStatement(querry3);
            ps.execute();


            map.put("responseCode","200");
            map.put("responseDescription","call Updated SuccessFully");
            return map;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
            return map;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }
    public  Map updateField(String updateField,String updateValue,String sessionId)
    {
        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {
            String querry3 = "update  calls set "+updateField+"='"+updateValue+"' where sessionid='"+sessionId+"'";
            ps = con.prepareStatement(querry3);
            ps.execute();


            map.put("responseCode","200");
            map.put("responseDescription","call Updated SuccessFully");
            return map;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
            return map;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }


    public  Map addContact(String username,String contactName,String phoneNumber)
    {
        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {
            String querry3 = "Insert into contacts values ('"+contactName+"','"+phoneNumber+"','"+username+"',now())";
            ps = con.prepareStatement(querry3);
            ps.execute();


            map.put("responseCode","200");
            map.put("responseDescription","Contact Saved SuccessFully");
            return map;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
            return map;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }
    public  Map addCallDtmf(String dtmf,String sessionId,String tier)
    {
        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {
            String querry3 = "Insert into calldtmfs values ('"+dtmf+"','"+sessionId+"','"+tier+"',now())";
            ps = con.prepareStatement(querry3);
            ps.execute();


            map.put("responseCode","200");
            map.put("responseDescription","Call DTMF Saved SuccessFully");
            return map;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
            return map;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }
    public  Map getSelectedDepartment(String sessionId,String tier)
    {
        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;

        con = dbConnection.connectToVoiceDb();
        try {
            String sql="Select * from calldtmfs where sessionId='"+sessionId+"' and tier='"+tier+"')";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next())
            {

                map.put("responseCode","200");
                map.put("contactName",rs.getString("contactName"));
                map.put("responseDescription","Contact Deleted SuccessFully");

            }
            else{
                map.put("responseCode","200");
                map.put("contactName","");
                map.put("responseDescription","Contact Deleted SuccessFully");

            }


            return map;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
            return map;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }
    public  Map editContact(String username,String contactName,String phoneNumber)
    {
        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {
            String querry3 = "update contacts set contactName='"+contactName+"' where phoneNumber='"+phoneNumber+"' and username='"+username+"',now())";
            ps = con.prepareStatement(querry3);
            ps.execute();


            map.put("responseCode","200");
            map.put("responseDescription","Contact Editted SuccessFully");
            return map;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
            return map;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }

    public  Map deleteContact(String username,String contactName,String phoneNumber)
    {
        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {
            String querry3 = "Delete from contacts where username='"+username+"' and contactName='"+contactName+"' and phonenumber='"+phoneNumber+"'";
            ps = con.prepareStatement(querry3);
            ps.execute();


            map.put("responseCode","200");
            map.put("responseDescription","Contact Deleted SuccessFully");
            return map;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
            return map;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }

    public  Map deleteCallLog(String sessionId)
    {
        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {
            String querry3 = "Delete from calls where sessionId='"+sessionId+"'";
            ps = con.prepareStatement(querry3);
            ps.execute();


            map.put("responseCode","200");
            map.put("responseDescription","Call Log Deleted SuccessFully");
            return map;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
            return map;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }

    public  Map deleteAllCallLogs(String username)
    {
        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {
            String querry3 = "Delete from calls";
            ps = con.prepareStatement(querry3);
            ps.execute();


            map.put("responseCode","200");
            map.put("responseDescription","All Logs SuccessFully");
            return map;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
            return map;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }
    public  Map getContactName(String username,String phoneNumber)
    {
        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        String phoneversion=phoneNumber;
        if(phoneNumber.startsWith("0"))
        {
            phoneversion=phoneNumber.replaceFirst("0","+254");
        }
        else if(phoneNumber.startsWith("+254"))
        {
            phoneversion="0"+phoneNumber.substring(4,phoneNumber.length()) ;
        }
        System.err.println(phoneversion);
        con = dbConnection.connectToVoiceDb();
        try {
            String sql="Select * from contacts where username='"+username+"' and (phoneNumber='"+phoneNumber+"' or phoneNumber='"+phoneversion+"')";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next())
            {

                map.put("responseCode","200");
                map.put("contactName",rs.getString("contactName"));
                map.put("responseDescription","Contact Deleted SuccessFully");

            }
            else{
                map.put("responseCode","200");
                map.put("contactName","");
                map.put("responseDescription","Contact Deleted SuccessFully");

            }


            return map;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
            return map;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }


    public String contactName(String username,String phoneNumber)
    {


        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        String phoneversion=phoneNumber;
        if(phoneNumber.startsWith("0"))
        {
           phoneversion=phoneNumber.replaceFirst("0","+254");
        }
        else if(phoneNumber.startsWith("+254"))
        {
            phoneversion="0"+phoneNumber.substring(4,phoneNumber.length()) ;
        }
        con = dbConnection.connectToVoiceDb();
        try {
           String sql="Select * from contacts where username='"+username+"' and (phoneNumber='"+phoneNumber+"' or phoneNumber='"+phoneversion+"')";
           ps=con.prepareStatement(sql);
           rs=ps.executeQuery();
           if(rs.next())
           {

              return rs.getString("contactName")+"-";

           }
           else{
               return "";
           }
        }
        catch (Exception sq)
        {
            sq.printStackTrace();
            return "";
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }

    }

    public boolean isAdmin(String username)
    {

        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {
            String sql="Select * from useraccounts where username='"+username+"'";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next())
            {
                String level=rs.getString("userlevel");
                if(level.equalsIgnoreCase("Admin"))
                {
                    return true;
                }
                else{
                    return false;
                }

            }
            else{
                return false;
            }
        }
        catch (Exception sq)
        {
            sq.printStackTrace();
            return false;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }

    }
    public  ArrayList getAllcalls(String username)
    {
        ArrayList<Map> arrayList=new ArrayList<Map>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        Config config=new Config();
        con = dbConnection.connectToVoiceDb();
        try {


            String sqll="Select * from calls where username='"+username+"' or directedto='"+username+"' order by dateSaved desc  ";
if(isAdmin(username))
{
    sqll="Select * from calls  order by dateSaved desc";

}
            ps=con.prepareStatement(sqll);
            rs= ps.executeQuery();
            while(rs.next())
            {
                Map map=new HashMap();
                map.put("dateEnded",rs.getString("dateEnded"));
                map.put("dateCalled",rs.getString("dateCalled"));
                map.put("duration",rs.getString("durationinseconds"));
                map.put("CallerNumber",rs.getString("CallerNumber").replace(Globals.systemUsername+".",""));
                map.put("sessionId",rs.getString("sessionId"));
                map.put("userName",rs.getString("username"));
                map.put("feedBack",rs.getString("feedBack"));
                map.put("NumberCalled",rs.getString("NumberCalled"));
                map.put("CallType",rs.getString("CallType"));
                map.put("callCost",rs.getString("callCost"));

                map.put("directedTo",rs.getString("directedTo"));
                map.put("externalCallUrl",rs.getString("callrecordUrl"));
                if(rs.getString("callrecordUrl")==null||rs.getString("callrecordUrl").isEmpty())
                {
                    map.put("recordUrl","");

                }
                else {
                    map.put("recordUrl","https://paleahcallcenter.lunar.cyou/voiceRecords"+"/"+rs.getString("sessionId")+".mp3");
//System.err.println("mappping...."+map);
                }



                map.put("callerContactName",contactName(username,rs.getString("CallerNumber")));
                map.put("numberCalledContactName",contactName(username,rs.getString("NumberCalled")));


                arrayList.add(map);


            }
            return arrayList;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            Map map=new HashMap<>();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
            arrayList.add(map);
            return arrayList;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }


    public  Map<String,String> getCallDetails(String callId)
    {
        ArrayList<Map> arrayList=new ArrayList<Map>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        Config config=new Config();
        con = dbConnection.connectToVoiceDb();
        try {


            String sqll="Select * from calls where sessionId='"+callId+"' ";
            Map map=new HashMap();
            ps=con.prepareStatement(sqll);
            rs= ps.executeQuery();
            if(rs.next())
            {

                map.put("dateEnded",rs.getString("dateEnded"));
                map.put("dateCalled",rs.getString("dateCalled"));
                map.put("duration",rs.getString("durationinseconds"));
                map.put("CallerNumber",rs.getString("CallerNumber").replace(Globals.systemUsername+".",""));
                map.put("sessionId",rs.getString("sessionId"));
                map.put("userName",rs.getString("username"));
                map.put("feedBack",rs.getString("feedBack"));
                map.put("NumberCalled",rs.getString("NumberCalled"));
                map.put("CallType",rs.getString("CallType"));
                map.put("callCost",rs.getString("callCost"));

                map.put("directedTo",rs.getString("directedTo"));
                map.put("externalCallUrl",rs.getString("callrecordUrl"));
                map.put("callerContactName",contactName(rs.getString("directedTo"),rs.getString("CallerNumber")));
               map.put("numberCalledContactName",contactName(rs.getString("directedTo"),rs.getString("NumberCalled")));


                if(rs.getString("callrecordUrl")==null||rs.getString("callrecordUrl").isEmpty())
                {
                    map.put("recordUrl","");

                }
                else {
                    map.put("recordUrl","https://paleahcallcenter.lunar.cyou/voiceRecords"+"/"+rs.getString("sessionId")+".mp3");

                }



            }

            return map;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            Map map=new HashMap<>();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
           return map;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }
public void downloadCallAudioFile(String url,String session)
{
    try {
      Config config=new Config();

        String outputPath = config.getConfigurations().get("voicePath")+"/"+session+".mp3";
        FileUtils.copyURLToFile(new URL(url), new File(outputPath));
    } catch (Exception ex) {
        Logger.getLogger(Calls.class.getName()).log(Level.SEVERE, null, ex);
    }
}
public boolean isAllowedTimeToCall() throws ParseException {
    Format f = new SimpleDateFormat("EEEE");
    Date date=new Date();
    if(f.format(date).equalsIgnoreCase("Saturday")||f.format(date).equalsIgnoreCase("Sunday"))
    {
        return false;
    }
    else{
        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
       date = parseFormat. parse("10:30 PM");

        return true;
    }

}




    public  ArrayList getAllcalls()
    {
        ArrayList<Map> arrayList=new ArrayList<Map>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {

Config config=new Config();
            String sqll="Select * from calls  order by dateSaved desc";

            ps=con.prepareStatement(sqll);
            rs= ps.executeQuery();
            while(rs.next())
            {
                Map map=new HashMap();
                map.put("dateEnded",rs.getString("dateEnded"));
                map.put("dateCalled",rs.getString("dateCalled"));
                map.put("duration",rs.getString("durationinseconds"));
                map.put("CallerNumber",rs.getString("CallerNumber").replace(Globals.systemUsername+".",""));
                map.put("feedBack",rs.getString("feedBack"));
                map.put("userName",rs.getString("username"));
                map.put("NumberCalled",rs.getString("NumberCalled"));
                map.put("CallType",rs.getString("CallType"));
                map.put("callCost",rs.getString("callCost"));
                map.put("directedTo",rs.getString("directedTo"));
                map.put("externalCallUrl",rs.getString("callrecordUrl"));
                if(rs.getString("callrecordUrl")==null||rs.getString("callrecordUrl").isEmpty())
                {
                    map.put("recordUrl","");

                }
                else {
                    map.put("recordUrl","https://paleahcallcenter.lunar.cyou/voiceRecords"+"/"+rs.getString("sessionId")+".mp3");

                }




                arrayList.add(map);


            }
            return arrayList;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            Map map=new HashMap<>();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
            arrayList.add(map);
            return arrayList;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }

    public  ArrayList getAllcontacts(String username)
    {
        ArrayList<Map> arrayList=new ArrayList<Map>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {


            String sqll="Select * from contacts where username='"+username+"'  order by contactname desc";
            if(isAdmin(username))
            {
                sqll="Select * from contacts  order by dateSaved desc";

            }
            ps=con.prepareStatement(sqll);
            rs= ps.executeQuery();
            while(rs.next())
            {
                Map map=new HashMap();

                map.put("userName",rs.getString("username"));
                map.put("phoneNumber",rs.getString("phoneNumber"));
                map.put("contactName",rs.getString("contactname"));
                map.put("dateCreated",rs.getString("dateSaved"));


                arrayList.add(map);


            }
            return arrayList;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            Map map=new HashMap<>();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
            arrayList.add(map);
            return arrayList;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }
    public  ArrayList getDepartments()
    {
        ArrayList<Map> arrayList=new ArrayList<Map>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {


            String sqll="Select * from departments order by redirectionCode asc";

            ps=con.prepareStatement(sqll);
            rs= ps.executeQuery();
            while(rs.next())
            {
                Map map=new HashMap();
                map.put("departmentName",rs.getString("departmentName"));
                map.put("redirectionCode",rs.getString("redirectionCode"));



                arrayList.add(map);


            }
            return arrayList;


        }
        catch ( SQLException sq) {

            sq.printStackTrace();
            Map map=new HashMap<>();
            map.put("responseCode","501");
            map.put("responseDescription","An Error Occured:"+sq.getMessage());
            arrayList.add(map);
            return arrayList;
        }
        finally {
            try {
                con.close();

            } catch (SQLException sq) {

                sq.printStackTrace();
            }

        }
    }


    public String departmentName(String departmentRedirectionCode)
    {
        try {
            Connection con;
            PreparedStatement ps = null;
            ResultSet rs;
            con = dbConnection.connectToVoiceDb();
            String querry="Select * from departments where redirectioncode='"+departmentRedirectionCode+"'";
            ps=con.prepareStatement(querry);
            rs=ps.executeQuery();
            if(rs.next())
            {
                return  rs.getString("departmentName");
            }
            else{
                return "";
            }


        }
        catch (Exception sq)
        {
            sq.printStackTrace();
            return "";
        }


    }
    public Map lastPersonToSpeakTo(String phoneNumber,String department)
    {
        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {
          String  sql = "Select * from calls where CallerNumber='"+phoneNumber+"' and departmentcalled='"+department+"'  and durationInSeconds is not null and directedto and durationInSeconds>30 is not null order by Dateended desc";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
          if (rs.next()) {


//                map.put("userName",rs.getString("directedto") );
//                map.put("sessionId",rs.getString("sessionId"));
//                map.put("dateCalled",rs.getString("Datecalled"));


   return  map;


            }
          else{
              return map;
          }
        }
        catch (Exception sq)
        {
            sq.printStackTrace();
            return  map;
        }
    }
public String call(String numberTocall)
{

    String responseObject="";
    responseObject="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "        <Response>\n" +
            "\n<Record />"+
            "<Dial phoneNumbers=\"" +numberTocall+"\"/>"+
            "       </Response>";
    return responseObject;
}

    public String redirectUser(String numberTocall,String userName)
    {

        String responseObject="";
        responseObject="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "        <Response>\n" +
                "<Dial phoneNumbers=\"" +numberTocall+","+userName+"\"/>"+
                "       </Response>";
        return responseObject;
    }
    public String NoUserToAnswerResponse()
    {

        String responseObject="";
        responseObject = "<Response>\n" +
                "<Say voice=\"en-GB-Wavenet-A\" playBeep=\"false\">All The Department Staff are Currently Engaged Please Try Again Later.Thank you</Say>\n" +
                "</Response>";
        return responseObject;
    }



    public String invalidOptionResponce()
    {

        String responseObject="";


        ArrayList arrayList=getDepartments();
        String option = "You Have Entered an Invalid Option. Please Enter";
        for (int i = 0; i < arrayList.size(); i++) {
            option = option + ((Map) arrayList.get(i)).get("redirectionCode") + " And Harsh to Speak To " + ((Map) arrayList.get(i)).get("departmentName") + ",";
        }
        System.err.println("Message" + option);
        responseObject = "<Response>\n" +
                "<GetDigits timeout=\"10\" finishOnKey=\"#\" callbackUrl=\"https://churchapp.lunar.cyou/voice/voiceevent\">\n" +
                "    <Say voice=\"en-GB-Wavenet-A\" playBeep=\"true\">" + option + "</Say>\n" +
                "</GetDigits>\n" +
                "<Say voice=\"en-GB-Wavenet-A\" playBeep=\"false\">We did not get your Selection, Good Bye</Say>\n" +
                "</Response>";


        return responseObject;
    }


    public  int pickRandomn(int low,int high)
    {
        Random r = new Random();

        int result = r.nextInt(high-low) + low;
        return result;
    }
}

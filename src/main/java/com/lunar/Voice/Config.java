package com.lunar.Voice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Config {



    public Map getConfigurations()
    {
DbConnection dbConnection=new DbConnection();
        Map<String,String> map=new HashMap<String,String>();
        Connection con=dbConnection.connectToVoiceDb();
        PreparedStatement ps = null;
        ResultSet rs;
        try {





            String querry = "Select * from configurations";
            ps = con.prepareStatement(querry);
            rs = ps.executeQuery();
           if (rs.next()) {

               map.put("startTime",rs.getString("startTime"));
               map.put("endTime",rs.getString("endTime"));
               map.put("allowWeekends",rs.getString("allowWeekendCalls"));
               map.put("routeToWeb",rs.getString("routeToWeb"));
               map.put("voicePath",rs.getString("voicePath"));
               map.put("outOfTimeMessage",rs.getString("outOfTimeMessage"));
            }
            map.put("responseCode","200");
            map.put("responseDescription","success");
            return map;
        } catch (Exception sq)
        {

            map.put("responseCode","501");
            map.put("responseDescription","Internal Error :"+sq.getMessage());

            sq.printStackTrace();
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

    public Map setTimeLimits(String maxTime,String minTime,String weekend,String outOfTimeMessage)
    {
        DbConnection dbConnection=new DbConnection();
        Map<String,String> map=new HashMap<String,String>();
        Connection con=dbConnection.connectToVoiceDb();
        PreparedStatement ps = null;
        ResultSet rs;
        try {



//            SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
//            SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
//           // Date date = parseFormat.parse("10:30 PM");
//

            String querry = "update  configurations set starttime='"+minTime+"' ,endtime='"+maxTime+"' ,allowWeekendCalls='"+weekend+"',outOfTimeMessage='"+outOfTimeMessage+"'";
            ps = con.prepareStatement(querry);
         ps.execute();

            map.put("responseCode","200");
            map.put("responseDescription","success");
            return map;
        } catch (Exception sq)
        {

            map.put("responseCode","501");
            map.put("responseDescription","Internal Error :"+sq.getMessage());

            sq.printStackTrace();
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
}

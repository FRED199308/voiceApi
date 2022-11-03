package com.lunar.Voice;



import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {







        public static   Connection connectDb() {
            try {

                Class.forName("com.mysql.cj.jdbc.Driver");
                // Connection con = DriverManager.getConnection("jdbc:mysql://" + databaseLink + "/Church_database", username,password);
                Connection con  = DriverManager.getConnection("jdbc:mysql://localhost:3306/church_database", "admin", "admin@lunartech");

               // System.err.println("connection Sucess");
                return con;
            } catch (Exception sq) {

              sq.printStackTrace();

                return null;
            }

        }
    public  Connection connectToVoiceDb() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            // Connection con = DriverManager.getConnection("jdbc:mysql://" + databaseLink + "/Church_database", username,password);
            Connection con  = DriverManager.getConnection("jdbc:mysql://localhost:3306/voicedb", "admin", "admin@lunartech");

            // System.err.println("connection Sucess");
            return con;
        } catch (Exception sq) {

            sq.printStackTrace();

            return null;
        }

    }


}

package com.lunar.Voice;



import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VoiceUser {
    DbConnection dbConnection;
    DataEncriptor dataEncriptor;

    public VoiceUser() {

        dataEncriptor=new DataEncriptor();
        dbConnection=new DbConnection();
    }

    public  Map registerUser(String username,String level,String firstName,String lastName,String phoneNumber,String department,String email,String sipPhoneUsername)
    {
        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {


               String   querry2 = "Select * from useraccounts where username='" + username + "'";
                ps = con.prepareStatement(querry2);
                rs = ps.executeQuery();

                if (rs.next()) {

                    map.put("responseCode","501");
                    map.put("responseDescription","Username Already Taken");
                    return map;

                }
                else{
                    String querry3 = "insert into useraccounts Values('"+firstName+"','"+lastName+"','" + username + "','" + email + "','" + level + "','" + dataEncriptor.encript("pass") + "','"+phoneNumber+"','" + "Active" + "','" + department + "','"+"offline"+"','"+sipPhoneUsername+"' )";
                    ps = con.prepareStatement(querry3);
                    ps.execute();

                    map.put("responseCode","200");
                    map.put("responseDescription","User Registered SuccessFully");
                    return map;
                }









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





    public  Map updateUserDetails(String username,String level,String firstName,String lastName,String phoneNumber,String department,String email,String sipPhoneUsername,String usernameToEddit)
    {
        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {

            con = dbConnection.connectToVoiceDb();
            String querry = "Update useraccounts  set username='"+username+"',userlevel='"+level+"',lastName='"+lastName+"',phonenumber='"+phoneNumber+"',email='"+email+"',department='"+department+"',Firstname='"+firstName+"',sipPhoneUsername='"+sipPhoneUsername+"' where userName='"+usernameToEddit+"'";

                    ps = con.prepareStatement(querry);
                    ps.execute();

                    map.put("responseCode","200");
                    map.put("responseDescription","User Details Updated");
                    return map;




        } catch ( SQLException sq) {

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


    public  Map changeUserStatus(String userName)
    {

        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();

        try {

            String querry = "Select * from useraccounts where UserName='" + userName + "'";
            ps = con.prepareStatement(querry);
            rs = ps.executeQuery();
            if (rs.next()) {

             String status=rs.getString("Status");

                if(status.equalsIgnoreCase("INACTIVE"))
                {
                    String querry1 = "Update useraccounts set status='" + "ACTIVE" + "' where username='" + userName + "' ";
                    ps = con.prepareStatement(querry1);
                    ps.execute();

                    map.put("responseCode","200");
                    map.put("responseDescription","User Status Changed To Active");
                    return map;
                }
                else{
                    String querry1 = "Update useraccounts set status='" + "INACTIVE" + "' where username='" + userName + "'";
                    ps = con.prepareStatement(querry1);
                    ps.execute();

                    map.put("responseCode","200");
                    map.put("responseDescription","User Status Changed To Inactive");
                    return map;
                }




            }
            else {


                map.put("responseCode","501");
                map.put("responseDescription","No such UserName  Found ");
                return map;
            }
        } catch ( SQLException sq) {

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

    public  Map changeUserPassword(String userName,String newPassord)
    {

        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();

        try {
System.err.println("UserName:"+userName);
            con = dbConnection.connectToVoiceDb();
            String querry = "Select * from useraccounts where UserName='" + userName + "'";
            ps = con.prepareStatement(querry);
            rs = ps.executeQuery();
            if (rs.next()) {



                    String querry1 = "Update useraccounts set password='" + dataEncriptor.encript(newPassord) + "' where username='" + userName + "'";
                    ps = con.prepareStatement(querry1);
                    ps.execute();

                    map.put("responseCode","200");
                    map.put("responseDescription","Password Changed Successfully");


            }
            else {


                map.put("responseCode","404");
                map.put("responseDescription","No such UserName Found ");
            }
            return map;
        } catch ( SQLException sq) {

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
    public  Map resetAccountPassword(String userName)
    {

        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;

        con = dbConnection.connectToVoiceDb();

        try {



            String querry1 = "Update useraccounts set password='" + dataEncriptor.encript("pass") + "' where username='" + userName + "'";
            ps = con.prepareStatement(querry1);
            ps.execute();

            map.put("responseCode","200");
            map.put("responseDescription","Password Reset SuccessFul");
            return map;


        } catch ( SQLException sq) {

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



    public  ArrayList getDepartmentUsersAvailableForCall(String department)
    {
        ArrayList<Map> arrayList=new ArrayList<Map>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {


            String sqll="Select * from useraccounts Where Department='"+department+"'   and status='"+"Active"+"'";
            ps=con.prepareStatement(sqll);
            rs= ps.executeQuery();
            while(rs.next())
            {
                Map map=new HashMap();
                map.put("responseCode","200");
                map.put("responseDescription","success");


                map.put("userName",rs.getString("username"));
                map.put("phoneNumber",rs.getString("phoneNumber"));


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
    public  ArrayList getDepartmentOnlineUsers(String department)
    {
        ArrayList<Map> arrayList=new ArrayList<Map>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {


            String sqll="Select * from useraccounts Where Department='"+department+"'   and reachabillity='"+"Online"+"'";
            ps=con.prepareStatement(sqll);
            rs= ps.executeQuery();
            while(rs.next())
            {
                Map map=new HashMap();
                map.put("responseCode","200");
                map.put("responseDescription","success");


                map.put("userName",rs.getString("username"));
                map.put("phoneNumber",rs.getString("phonenumber"));


               // arrayList.add(map);


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

    public  Map getDepartmentUser(String department)
    {
        ArrayList<Map> arrayList=new ArrayList<Map>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {

            Map map=new HashMap();
            String sqll="Select * from useraccounts Where Department='"+department+"'   and status='"+"Active"+"'";
            ps=con.prepareStatement(sqll);
            rs= ps.executeQuery();
            if(rs.next())
            {
                map=new HashMap();
                map.put("responseCode","200");
                map.put("responseDescription","success");


                map.put("userName",rs.getString("username"));
                map.put("phoneNumber",rs.getString("status"));


                arrayList.add(map);


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
    public Map userDetails(String userIdentifier)
    {
        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {
            String  sql = "Select * from useraccounts where phoneNumber='"+userIdentifier+"' or username='"+userIdentifier+"' or sipPhoneUsername='"+userIdentifier+"'";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {


                map.put("userName",rs.getString("username") );
                map.put("reachabillity",rs.getString("reachabillity"));
                map.put("phoneNumber",rs.getString("phoneNumber"));
                map.put("department",rs.getString("department"));
                map.put("firstName",rs.getString("firstName"));
                map.put("userLevel",rs.getString("userLevel"));
                map.put("status",rs.getString("status"));
                map.put("email",rs.getString("email"));
                map.put("sipPhoneUsername",rs.getString("sipPhoneUsername"));

                map.put("lastName",rs.getString("LastName"));
                map.put("fullName",rs.getString("firstName")+" "+rs.getString("LastName"));



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

    public  ArrayList getAllUsers()
    {
        ArrayList<Map> arrayList=new ArrayList<Map>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs;
        con = dbConnection.connectToVoiceDb();
        try {


            String sqll="Select * from useraccounts order by FirstName";
            ps=con.prepareStatement(sqll);
            rs= ps.executeQuery();
            while(rs.next())
            {
                Map map=new HashMap();
                map.put("userName",rs.getString("username") );
                map.put("status",rs.getString("reachabillity"));
                map.put("isActive",rs.getString("status"));
                map.put("phoneNumber",rs.getString("phoneNumber"));
                map.put("department",rs.getString("department"));
                map.put("firstName",rs.getString("firstName"));
                map.put("LastName",rs.getString("LastName"));
                map.put("userLevel",rs.getString("userLevel"));
                map.put("sipPhoneUsername",rs.getString("sipPhoneUsername"));
                map.put("fullName",rs.getString("firstName")+" "+rs.getString("LastName"));

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

    public  Map deleteUser(String userName)
    {

        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;

        con = dbConnection.connectToVoiceDb();

        try {



            String querry1 = "DELETE from  useraccounts where username='" + userName + "'";
            ps = con.prepareStatement(querry1);
            ps.execute();

            map.put("responseCode","200");
            map.put("responseDescription","User Account Deleted");
            return map;


        } catch ( SQLException sq) {

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


    public  Map deactivateUserAccount(String userName)
    {

        Map map=new HashMap();
        Connection con;
        PreparedStatement ps = null;

        con = dbConnection.connectToVoiceDb();

        try {



            String querry1 = "Update   useraccounts set Status='"+"IN ACTIVE"+"' where username='" + userName + "'   ";
            ps = con.prepareStatement(querry1);
            ps.execute();

            map.put("responseCode","200");
            map.put("responseDescription","User Account Deleted");
            return map;


        } catch ( SQLException sq) {

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


    public  Map authenticate(String email, String password)
    {
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        con= dbConnection.connectToVoiceDb();
        Map map=new HashMap();
        try {

            String empcode,departcode="",name="";
            String sql="Select * from useraccounts where email='"+email+"' and password='"+ dataEncriptor.encript(password)+"'";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next())
            {String level=rs.getString("userlevel");


                map.put("level",level);
                String status=rs.getString("Status");
                String pass= dataEncriptor.decriptor(rs.getString("Password"));



                if(status.equalsIgnoreCase("Active"))
                {
                    if (pass.equalsIgnoreCase("pass")) {

                            map.put("responseCode","200");
                            map.put("responseDescription","Login Success");
                            map.put("firstName",rs.getString("firstname"));
                            map.put("lastName",rs.getString("lastname"));
                            map.put("userLevel",rs.getString("userlevel"));
                            map.put("phoneNumber",rs.getString("phoneNumber"));
                            map.put("password",password);
                            map.put("userName",rs.getString("username"));
                            map.put("department",rs.getString("department"));
                            map.put("email",rs.getString("email"));

                            map.put("status",rs.getString("status"));
                            return map;



                    } else {

                        map.put("responseCode","200");
                        map.put("responseDescription","Login Success");
                        map.put("firstName",rs.getString("firstname"));
                        map.put("lastName",rs.getString("lastname"));
                        map.put("userLevel",rs.getString("userlevel"));
                        map.put("level",rs.getString("userlevel"));
                        map.put("phoneNumber",rs.getString("phoneNumber"));
                        map.put("userName",rs.getString("username"));
                        map.put("department",rs.getString("department"));
                        map.put("email",rs.getString("email"));
                        map.put("password",password);

                        map.put("status",rs.getString("status"));
                        return map;
                    }
                }
                else{
                    map.put("responseCode","501");
                    map.put("responseDescription","User Account Deactivated\n Please Consult Your Admin For Assistance");
                    return map;
                }







            }
            else{
                map.put("responseCode","301");
                map.put("responseDescription","Login Failed\nWrong Username Password Combination");
                return map;
            }



        } catch (HeadlessException | SQLException sq) {
            sq.printStackTrace();
            map.put("responseCode","501");
            map.put("responseDescription",sq.getMessage());
            return map;

        }
        finally{
            try {
                con.close();
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
        }
    }

    public String updateUserStatus(String status,String userName)
    {
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        con= dbConnection.connectToVoiceDb();
        try {

            ps=con.prepareStatement("Update useraccounts set reachabillity='"+status+"' where username='"+userName+"'");
         ps.execute();
         return "Status Updated";

        }
        catch (Exception sq)
        {
            sq.printStackTrace();
            return "Error:"+sq.getMessage();
        }
    }
}

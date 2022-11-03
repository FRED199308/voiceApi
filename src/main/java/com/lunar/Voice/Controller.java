package com.lunar.Voice;


import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {




    //this Api belong to another application that doesnt have any relation with church and will later be moved
    //to act as independnt as it should

    @RequestMapping(value = "/voice/authenticateUser",method = RequestMethod.POST)
    public Map userAuth(@RequestBody Map<String, String> load)
    {
        VoiceUser voiceUser=new VoiceUser();
        return voiceUser.authenticate(load.get("email"),load.get("password"));

    }


    @RequestMapping(value = "/voice/addUser",method = RequestMethod.POST)
    public Map userAdd(@RequestBody Map<String, String> load)
    {
        System.err.println(load);
        VoiceUser voiceUser=new VoiceUser();
        return voiceUser.registerUser(load.get("userName"),load.get("userLevel"),load.get("firstName"),load.get("lastName"),load.get("phoneNumber"),
                load.get("departmentName"),load.get("email"),load.get("sipPhoneUsername"));

    }
    @RequestMapping(value = "/voice/resetUser",method = RequestMethod.POST)
    public Map resetPassword(@RequestBody Map<String, String> load)
    {
        //System.err.println(load);
        VoiceUser voiceUser=new VoiceUser();
        return voiceUser.resetAccountPassword(load.get("userName"));

    }

    @RequestMapping(value = "/voice/suspendUser",method = RequestMethod.POST)
    public Map suspendPassword(@RequestBody Map<String, String> load)
    {
        VoiceUser voiceUser=new VoiceUser();
        return voiceUser.changeUserStatus(load.get("userName"));

    }

    @RequestMapping(value = "/voice/changePassword",method = RequestMethod.POST)
    public Map changePassword(@RequestBody Map<String, String> load)
    {
        System.err.println(load);
        VoiceUser voiceUser=new VoiceUser();
        return voiceUser.changeUserPassword(load.get("userName"),load.get("password"));

    }


    @RequestMapping(value = "/voice/sendSMS",method = RequestMethod.POST)
    public String sendsm()
    {
        ArrayList   messagesList=new ArrayList<Map>();
        HashMap messageData=new HashMap<String,String>();
        messageData.put("Text", "Hello Fred");


        messageData.put("Number", "254707353225");
        messagesList.add(messageData);
     return   SMS.sendSms(messagesList);


    }
    @RequestMapping(value = "/voice/deleteUser",method = RequestMethod.POST)
    public Map deleteUser(@RequestBody Map<String, String> load)
    {
        System.err.println(load);
        VoiceUser voiceUser=new VoiceUser();
        return voiceUser.deleteUser(load.get("userName"));

    }
    @RequestMapping(value = "/voice/deleteContact",method = RequestMethod.POST)
    public Map deleteContact(@RequestBody Map<String, String> load)
    {
        System.err.println(load);
        Calls calls=new Calls();
        return calls.deleteContact(load.get("userName"),load.get("contactName"),load.get("phoneNumber"));

    }


    @RequestMapping(value = "/voice/deleteCallLog",method = RequestMethod.POST)
    public Map deleteCallLoge(@RequestBody Map<String, String> load)
    {
        System.err.println(load);
        Calls calls=new Calls();
        return calls.deleteCallLog(load.get("sessionId"));

    }
    @RequestMapping(value = "/voice/deleteAllLogs",method = RequestMethod.POST)
    public Map deleteAllLogs(@RequestBody Map<String, String> load)
    {
        System.err.println(load);
        Calls calls=new Calls();
        return calls.deleteAllCallLogs(load.get("userName"));

    }
    @RequestMapping(value = "/voice/updateUserDetails",method = RequestMethod.POST)
    public Map userUpdate(@RequestBody Map<String, String> load)
    {
        System.err.println(load);
        VoiceUser voiceUser=new VoiceUser();
        return voiceUser.updateUserDetails(load.get("userName"),load.get("userLevel"),load.get("firstName"),load.get("lastName"),load.get("phoneNumber"),
                load.get("departmentName"),load.get("email"),load.get("sipPhoneUsername"),load.get("userNameToEdit"));

    }

    @RequestMapping(value = "/voice/getContactName",method = RequestMethod.POST)
    public Map getContactName(@RequestBody Map<String, String> load)
    {
        System.err.println(load);
       Calls calls=new Calls();
     return   calls.getContactName(load.get("userName"),load.get("phoneNumber"));

    }

    @RequestMapping(value = "/voice/{username}/allCalls",method = RequestMethod.GET)
    public ArrayList getallLogs(@PathVariable("username") String username)
    {
Calls call=new Calls();
        return call.getAllcalls(username);

    }
    @RequestMapping(value = "/voice/configurations",method = RequestMethod.GET)
    public Map getconfigurations()
    {
        Config config=new Config();
    return config.getConfigurations();

    }
    @RequestMapping(value = "/voice/allCalls",method = RequestMethod.GET)
    public ArrayList getallLogs()
    {
        Calls call=new Calls();
        return call.getAllcalls();

    }
    @RequestMapping(value = "/voice/{username}/allContacts",method = RequestMethod.GET)
    public ArrayList getallcontacts(@PathVariable("username") String username)
    {
        Calls call=new Calls();
        return call.getAllcontacts(username);

    }

    @RequestMapping(value = "/voice/allUsers",method = RequestMethod.GET)
    public ArrayList getAllUsers()
    {
        VoiceUser voiceUser=new VoiceUser();
        return voiceUser.getAllUsers();

    }
    @RequestMapping(value = "/voice/departments",method = RequestMethod.GET)
    public ArrayList getAlldepartments()
    {
        Calls calls=new Calls();
        return calls.getDepartments();

    }
    @RequestMapping(value = "/voice/{username}/{status}/updateUserStatus",method = RequestMethod.GET)
    public String updateUserStatus(@PathVariable("username") String username,@PathVariable("status") String status)
    {
    VoiceUser user=new VoiceUser();
        return user.updateUserStatus(status,username);

    }


    @RequestMapping(value = "/voice/callBackUrl", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,MediaType.ALL_VALUE})
    public String voiceCallBack( @RequestParam Map<String, String> body ) {
      System.err.println(body);
String responseObject;
      String[] response=body.toString().split(",");
        String response2=body.toString().replace(" ","").replace("=","\":\"").replace(",","\",\"").replace("{","{\"").replace("}","\"}");
String clientDialedNumber="",callSessionState="",direction="",callerCountryCode="",durationInSeconds="",amount="",callerNumber="",destinationNumber="";
String callerCarrierName="",status="",recordingUrl="",sessionId="",callStartTime="",isActive="",currencyCode="";
System.err.println(response2);

        JSONObject json = new JSONObject(response2);

if(json.has("callSessionState"))
        callSessionState=json.getString("callSessionState");
        if(json.has("direction"))
        direction=json.getString("direction");
        if(json.has("callerCountryCode"))
        callerCountryCode=json.getString("callerCountryCode");
        if(json.has("recordingUrl"))
            recordingUrl=json.getString("recordingUrl");
        if(json.has("durationInSeconds"))
        durationInSeconds=json.getString("durationInSeconds");
        if(json.has("amount"))
        amount=json.getString("amount");
        if(json.has("clientDialedNumber"))
            clientDialedNumber=json.getString("clientDialedNumber");
        if(json.has("callerNumber"))
            callerNumber=json.getString("callerNumber");
        if(json.has("destinationNumber"))
        destinationNumber=json.getString("destinationNumber");
        if(json.has("callerCarrierName"))
        callerCarrierName=json.getString("callerCarrierName");
        if(json.has("sessionId"))
        sessionId=json.getString("sessionId");
        if(json.has("callStartTime"))
        callStartTime= json.getString("callStartTime");
        if(json.has("isActive"))
        isActive=json.getString("isActive");
        if(json.has("currencyCode"))
        currencyCode= json.getString("currencyCode");
        Calls calls=new Calls();
        if(isActive.equalsIgnoreCase("1")) {
            String caller2="";
            if (callerNumber.startsWith(Globals.systemUsername)||destinationNumber.contains(Globals.systemUsername)) {
caller2=callerNumber;
String sipDetails="";
 if(destinationNumber.contains(Globals.systemUsername))
 {
     VoiceUser user=new VoiceUser();
     System.err.println("\n Reached here....");
     System.err.println("\n Reached here...."+user.userDetails(destinationNumber.substring(0,destinationNumber.indexOf("."))));
     caller2=user.userDetails(destinationNumber.substring(0,destinationNumber.indexOf("."))).get("userName").toString();
     clientDialedNumber=callerNumber;
System.err.println("ggggggggg:"+caller2);
sipDetails="callerId=\""+Globals.virtualPhoneNumber+"\"";
 }
responseObject="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "        <Response>\n" +

        "        <Dial record=\"true\" phoneNumbers=\""+callerNumber+","+clientDialedNumber+"\" "+sipDetails+"  />\n" +

        "       </Response>";
System.err.println("contains ");
                calls.registerCall(caller2.replace(Globals.systemUsername+".",""), callStartTime, status, clientDialedNumber, destinationNumber, "Outbound", sessionId, isActive);

                return responseObject;

            }
            else {

            calls.registerCall(callerNumber, callStartTime, status, callerNumber, destinationNumber, direction, sessionId, isActive);

            ArrayList arrayList = new ArrayList();
            arrayList = calls.getDepartments();
            String option = "Thank For Calling Us, Enter, ";
            for (int i = 0; i < arrayList.size(); i++) {
                option = option + ((Map) arrayList.get(i)).get("redirectionCode") + " And Harsh to Speak To " + ((Map) arrayList.get(i)).get("departmentName") + ",";
            }
            System.err.println("Message" + option);

            responseObject = "<Response>\n" +
                    "<GetDigits timeout=\"10\" finishOnKey=\"#\" callbackUrl=\"https://voice.lunar.cyou/voice/voiceevent\">\n" +
                    "     <Play url=\"https://neovoice.lunar.cyou/voiceAudio.mp3\"/>" +
                    "</GetDigits>\n" +
                    "<Say voice=\"en-GB-Wavenet-A\" playBeep=\"false\">We did not get your Selection, Good Bye</Say>\n" +
                    "</Response>";
            return responseObject;
        }
        }
        else {
System.out.println("Record Found");
            calls.updateCalls(amount,isActive,callSessionState,durationInSeconds,sessionId,recordingUrl);
            if(!recordingUrl.isEmpty())
            {
                calls.downloadCallAudioFile(recordingUrl,sessionId);
            }
            return "";
        }

    }




    @RequestMapping(value = "/voice/voiceevent", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,MediaType.ALL_VALUE})
    public String voiceCallEvent( @RequestParam Map<String, String> body ) {
        System.err.println("voice Event"+body);
        String responseObject;
        String[] response=body.toString().split(",");
        String response2=body.toString().replace(" ","").replace("=","\":\"").replace(",","\",\"").replace("{","{\"").replace("}","\"}");
        String clientDialedNumber="",dtmfDigits="",callSessionState="",direction="",callerCountryCode="",durationInSeconds="",amount="",callerNumber="",destinationNumber="";
        String callerCarrierName="",recordingUrl="",status="",sessionId="",callStartTime="",isActive="",currencyCode="";
        System.err.println(response2);

        JSONObject json = new JSONObject(response2);
        responseObject="";

        if(json.has("callSessionState"))
            callSessionState=json.getString("callSessionState");
        if(json.has("direction"))
            direction=json.getString("direction");
        if(json.has("callerCountryCode"))
            callerCountryCode=json.getString("callerCountryCode");
        if(json.has("recordingUrl"))
            recordingUrl=json.getString("recordingUrl");

        if(json.has("durationInSeconds"))
            durationInSeconds=json.getString("durationInSeconds");
        if(json.has("amount"))
            amount=json.getString("amount");
        if(json.has("callerNumber"))
            callerNumber=json.getString("callerNumber");
        if(json.has("destinationNumber"))
            destinationNumber=json.getString("destinationNumber");
        if(json.has("callerCarrierName"))
            callerCarrierName=json.getString("callerCarrierName");
        if(json.has("sessionId"))
            sessionId=json.getString("sessionId");
        if(json.has("callStartTime"))
            callStartTime= json.getString("callStartTime");
        if(json.has("isActive"))
            isActive=json.getString("isActive");
        if(json.has("currencyCode"))
            currencyCode= json.getString("currencyCode");

        if(json.has("dtmfDigits"))
            dtmfDigits= json.getString("dtmfDigits");

        Calls calls=new Calls();
        if(isActive.equalsIgnoreCase("1"))
        {

            VoiceUser user=new VoiceUser();
if(dtmfDigits.isEmpty())
{




}
else{


    responseObject = "<Response>\n" +
            "<GetDigits timeout=\"10\" finishOnKey=\"#\" callbackUrl=\"https://b29f-41-215-58-26.in.ngrok.io/voice/voiceevent\">\n" +
            "     <Play url=\"https://neovoice.lunar.cyou/subdepartmentAudio.mp3\"/>" +
            "</GetDigits>\n" +
            "<Say voice=\"en-GB-Wavenet-A\" playBeep=\"false\">We did not get your Selection, Good Bye</Say>\n" +
            "</Response>";

    String departmentName=calls.departmentName(dtmfDigits);
calls.updateField("departmentcalled",departmentName,sessionId);
    System.out.println("Selected Department:"+departmentName+"  dtmfDigits:"+dtmfDigits);
 Map lastPersonTospeak=   calls.lastPersonToSpeakTo(callerNumber,departmentName);

 if(departmentName.isEmpty())
 {
responseObject=calls.invalidOptionResponce();
 }
 else{


 if(lastPersonTospeak.isEmpty())
 {

     System.out.println("This Person Has never Called");


     if(user.getDepartmentOnlineUsers(departmentName).isEmpty())
     {
         if(user.getDepartmentUsersAvailableForCall(departmentName).isEmpty())
         {
             responseObject=calls.NoUserToAnswerResponse();
         }
         else{
             ArrayList available=user.getDepartmentUsersAvailableForCall(departmentName);
             int index=calls.pickRandomn(0,available.size());
             System.err.println("Available Users in that DEPT:"+available.size()+" Index Selected:"+calls.pickRandomn(0,available.size()));
             String availableUserTocanswer=((Map)user.getDepartmentUsersAvailableForCall(departmentName).get(index)).get("userName").toString();
             String userToCallPhoneNumber=((Map)user.getDepartmentUsersAvailableForCall(departmentName).get(index)).get("phoneNumber").toString();


             System.err.println(("User Available:"+availableUserTocanswer));
             calls.updateField("directedto",availableUserTocanswer,sessionId);

             responseObject=calls.call(userToCallPhoneNumber);
             SMS sms=new SMS();
             sms.sendCallAlert(sessionId);
         }



     }
     else{

         ArrayList available=user.getDepartmentOnlineUsers(departmentName);
         int index=calls.pickRandomn(0,available.size());
         String availableUserTocanswer=((Map)user.getDepartmentUsersAvailableForCall(departmentName).get(index)).get("userName").toString();


         System.err.println(("User Available:"+availableUserTocanswer));
         calls.updateField("directedto",availableUserTocanswer,sessionId);
         responseObject=calls.call(Globals.systemUsername+"."+availableUserTocanswer);

     }



//String availableUserTocanswer=((Map)user.getDepartmentUsersAvailableForCall(departmentName).get(0)).get("userName").toString();
//System.err.println(("User Available:"+availableUserTocanswer));
//calls.updateField("directedto",availableUserTocanswer,sessionId);
//responseObject=calls.call(Globals.systemUsername+"."+availableUserTocanswer,Globals.systemUsername+"."+availableUserTocanswer);

 }
 else{

     String personCalled=lastPersonTospeak.get("userName").toString();
System.err.println("LastPersonToSpeakTo:"+lastPersonTospeak);
System.err.println("calledUserDetails:"+user.userDetails(personCalled));

    String userStatus=  user.userDetails(personCalled).get("reachabillity").toString();
    if(userStatus.equalsIgnoreCase("Online"))
    {
        calls.updateField("directedto",personCalled,sessionId);
        responseObject= calls.call(Globals.systemUsername+"."+personCalled);

    }
    else{
        String availableUserTocanswer=((Map)user.getDepartmentUsersAvailableForCall(departmentName).get(0)).get("userName").toString();
        String userPhone=((Map)user.getDepartmentUsersAvailableForCall(departmentName).get(0)).get("phoneNumber").toString();
        System.err.println(("User Available:"+availableUserTocanswer));
        calls.updateField("directedto",availableUserTocanswer,sessionId);
        responseObject=calls.call(userPhone);


    }





 }


 }



}


        }
        else {
            String departmentName=calls.departmentName(dtmfDigits);
          calls.updateCalls(amount,isActive,callSessionState,durationInSeconds,sessionId,recordingUrl);

            if(!recordingUrl.isEmpty())
            {
                calls.downloadCallAudioFile(recordingUrl,sessionId);
            }

            return "hello";
        }

        return responseObject;
    }
    @RequestMapping(value = "/voice/subDepartment", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,MediaType.ALL_VALUE})
    public String voiceCallEventsubDepartment( @RequestParam Map<String, String> body ) {
        System.err.println("voice Event"+body);
        String responseObject;
        String[] response=body.toString().split(",");
        String response2=body.toString().replace(" ","").replace("=","\":\"").replace(",","\",\"").replace("{","{\"").replace("}","\"}");
        String clientDialedNumber="",dtmfDigits="",callSessionState="",direction="",callerCountryCode="",durationInSeconds="",amount="",callerNumber="",destinationNumber="";
        String callerCarrierName="",recordingUrl="",status="",sessionId="",callStartTime="",isActive="",currencyCode="";
        System.err.println(response2);

        JSONObject json = new JSONObject(response2);
        responseObject="";

        if(json.has("callSessionState"))
            callSessionState=json.getString("callSessionState");
        if(json.has("direction"))
            direction=json.getString("direction");
        if(json.has("callerCountryCode"))
            callerCountryCode=json.getString("callerCountryCode");
        if(json.has("recordingUrl"))
            recordingUrl=json.getString("recordingUrl");

        if(json.has("durationInSeconds"))
            durationInSeconds=json.getString("durationInSeconds");
        if(json.has("amount"))
            amount=json.getString("amount");
        if(json.has("callerNumber"))
            callerNumber=json.getString("callerNumber");
        if(json.has("destinationNumber"))
            destinationNumber=json.getString("destinationNumber");
        if(json.has("callerCarrierName"))
            callerCarrierName=json.getString("callerCarrierName");
        if(json.has("sessionId"))
            sessionId=json.getString("sessionId");
        if(json.has("callStartTime"))
            callStartTime= json.getString("callStartTime");
        if(json.has("isActive"))
            isActive=json.getString("isActive");
        if(json.has("currencyCode"))
            currencyCode= json.getString("currencyCode");

        if(json.has("dtmfDigits"))
            dtmfDigits= json.getString("dtmfDigits");

        Calls calls=new Calls();
        if(isActive.equalsIgnoreCase("1"))
        {

            VoiceUser user=new VoiceUser();
            if(dtmfDigits.isEmpty())
            {




            }
            else{






                String departmentName=calls.departmentName(dtmfDigits);
                calls.updateField("departmentcalled",departmentName,sessionId);
                System.out.println("Selected Department:"+departmentName+"  dtmfDigits:"+dtmfDigits);
                Map lastPersonTospeak=   calls.lastPersonToSpeakTo(callerNumber,departmentName);

                if(departmentName.isEmpty())
                {
                    responseObject=calls.invalidOptionResponce();
                }
                else{


                    if(lastPersonTospeak.isEmpty())
                    {

                        System.out.println("This Person Has never Called");


                        if(user.getDepartmentOnlineUsers(departmentName).isEmpty())
                        {
                            if(user.getDepartmentUsersAvailableForCall(departmentName).isEmpty())
                            {
                                responseObject=calls.NoUserToAnswerResponse();
                            }
                            else{
                                ArrayList available=user.getDepartmentUsersAvailableForCall(departmentName);
                                int index=calls.pickRandomn(0,available.size());
                                System.err.println("Available Users in that DEPT:"+available.size()+" Index Selected:"+calls.pickRandomn(0,available.size()));
                                String availableUserTocanswer=((Map)user.getDepartmentUsersAvailableForCall(departmentName).get(index)).get("userName").toString();
                                String userToCallPhoneNumber=((Map)user.getDepartmentUsersAvailableForCall(departmentName).get(index)).get("phoneNumber").toString();


                                System.err.println(("User Available:"+availableUserTocanswer));
                                calls.updateField("directedto",availableUserTocanswer,sessionId);

                                responseObject=calls.call(userToCallPhoneNumber);
                                SMS sms=new SMS();
                                sms.sendCallAlert(sessionId);
                            }



                        }
                        else{

                            ArrayList available=user.getDepartmentOnlineUsers(departmentName);
                            int index=calls.pickRandomn(0,available.size());
                            String availableUserTocanswer=((Map)user.getDepartmentUsersAvailableForCall(departmentName).get(index)).get("userName").toString();


                            System.err.println(("User Available:"+availableUserTocanswer));
                            calls.updateField("directedto",availableUserTocanswer,sessionId);
                            responseObject=calls.call(Globals.systemUsername+"."+availableUserTocanswer);

                        }



//String availableUserTocanswer=((Map)user.getDepartmentUsersAvailableForCall(departmentName).get(0)).get("userName").toString();
//System.err.println(("User Available:"+availableUserTocanswer));
//calls.updateField("directedto",availableUserTocanswer,sessionId);
//responseObject=calls.call(Globals.systemUsername+"."+availableUserTocanswer,Globals.systemUsername+"."+availableUserTocanswer);

                    }
                    else{

                        String personCalled=lastPersonTospeak.get("userName").toString();
                        System.err.println("LastPersonToSpeakTo:"+lastPersonTospeak);
                        System.err.println("calledUserDetails:"+user.userDetails(personCalled));

                        String userStatus=  user.userDetails(personCalled).get("reachabillity").toString();
                        if(userStatus.equalsIgnoreCase("Online"))
                        {
                            calls.updateField("directedto",personCalled,sessionId);
                            responseObject= calls.call(Globals.systemUsername+"."+personCalled);

                        }
                        else{
                            String availableUserTocanswer=((Map)user.getDepartmentUsersAvailableForCall(departmentName).get(0)).get("userName").toString();
                            String userPhone=((Map)user.getDepartmentUsersAvailableForCall(departmentName).get(0)).get("phoneNumber").toString();
                            System.err.println(("User Available:"+availableUserTocanswer));
                            calls.updateField("directedto",availableUserTocanswer,sessionId);
                            responseObject=calls.call(userPhone);


                        }





                    }


                }



            }


        }
        else {
            String departmentName=calls.departmentName(dtmfDigits);
            calls.updateCalls(amount,isActive,callSessionState,durationInSeconds,sessionId,recordingUrl);

            if(!recordingUrl.isEmpty())
            {
                calls.downloadCallAudioFile(recordingUrl,sessionId);
            }

            return "hello";
        }

        return responseObject;
    }


    @RequestMapping(value = "/voice/{department}/callBackUrl",method = RequestMethod.POST)
    public Map logAndDepartmentCallReplyCall(@RequestBody Object load)
    {

System.err.println(load);

        return null;

    }
    @RequestMapping(value = "/voice/saveFeedBack",method = RequestMethod.POST)
    public Map saveFeedBack(@RequestBody Map<String, String> load)
    {
System.err.println(load);
    Calls calls=new Calls();
        return calls.saveFeedBack(load.get("numberCalled"),load.get("userName"),load.get("callerNumber"),load.get("feedBack"));




    }

    @RequestMapping(value = "/voice/userDetails",method = RequestMethod.POST)
    public Map getUserDetails(@RequestBody Map<String, String> load)
    {
        System.err.println(load);
        VoiceUser user=new VoiceUser();
        return user.userDetails(load.get("userName"));



    }
    @RequestMapping(value = "/voice/{userName}/userDetails",method = RequestMethod.GET)
    public Map getUserDetail(@PathVariable("userName") String userName)
    {

        VoiceUser user=new VoiceUser();
        return user.userDetails(userName);



    }
    @RequestMapping(value = "/voice/addContact",method = RequestMethod.POST)
    public Map addContact(@RequestBody Map<String, String> load)
    {
        System.err.println(load);
        Calls calls=new Calls();
        return calls.addContact(load.get("userName"),load.get("contactName"),load.get("phoneNumber"));



    }
    @RequestMapping(value = "/voice/editContact",method = RequestMethod.POST)
    public Map editContact(@RequestBody Map<String, String> load)
    {
        System.err.println(load);
        Calls calls=new Calls();
        return calls.editContact(load.get("userName"),load.get("contactName"),load.get("phoneNumber"));



    }


    @RequestMapping(value = "/voice/timeLimits",method = RequestMethod.POST)
    public Map timeLimit(@RequestBody Map<String, String> load)
    {
        System.err.println(load);
        Config config=new Config();
        return config.setTimeLimits(load.get("maxTime"),load.get("minTime"),load.get("weekend"),load.get("outOfTimeMessage"));



    }

}

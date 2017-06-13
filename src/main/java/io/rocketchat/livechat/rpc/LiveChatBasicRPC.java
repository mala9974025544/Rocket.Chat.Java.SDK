package io.rocketchat.livechat.rpc;

import io.rocketchat.common.utils.Utils;

/**
 * Created by sachin on 8/6/17.
 */

public class LiveChatBasicRPC {

    public static String visitorToken =Utils.generateRandomHexToken(16);


    public static String ConnectObject(){
        return "{\"msg\":\"connect\",\"version\":\"1\",\"support\":[\"1\",\"pre2\",\"pre1\"]}";
    }

    public static String getInitialData(int integer){

        return "{\"msg\":\"method\"," +
                "\"method\":\"livechat:getInitialData\"," +
                "\"params\":[\""+ visitorToken +"\"]," +
                "\"id\":\""+integer+"\"}";

//        return "{\"msg\":\"method\",\"method\":\"livechat:getInitialData\",\"params\":[\"7T4jzes7rX3Fr6cQ2\"],\"id\":\"1\"}";
    }

    public static String registerGuest(int integer,String name, String email, String dept){
        return "{\"msg\":\"method\"," +
                "\"method\":\"livechat:registerGuest\"," +
                "\"params\":[{\"visitorToken\":\""+ visitorToken +"\",\"name\":\""+name+"\",\"email\":\""+email+"\",\"department\":\""+dept+"\"}]," +
                "\"id\":\""+integer+"\"}";
    }

    public static String login(int integer,String token){
        return "{\n" +
                "    \"msg\": \"method\",\n" +
                "    \"method\": \"login\",\n" +
                "    \"id\": \""+integer+"\",\n" +
                "    \"params\":[\n" +
                "        { \"resume\": \""+token+"\" }\n" +
                "    ]\n" +
                "}";
    }

    public static String getAgentData(int integer, String roomId){
        return "{\"msg\":\"method\"," +
                "\"method\":\"livechat:getAgentData\"," +
                "\"params\":[\""+roomId+"\"]," +
                "\"id\":\""+integer+"\"}";
    }

    public static String closeConversation(int integer,String roomId){
        return "{\"msg\":\"method\"," +
                "\"method\":\"livechat:closeByVisitor\"," +
                "\"params\":[\""+roomId+"\"]," +
                "\"id\":\""+integer+"\"}";
    }

}

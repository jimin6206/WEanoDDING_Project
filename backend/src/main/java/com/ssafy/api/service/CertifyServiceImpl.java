package com.ssafy.api.service;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.api.request.CompanyRegisterPostReq;
import com.ssafy.api.request.CompanyUpdatePostReq;
import com.ssafy.db.entity.Company;
import com.ssafy.db.entity.Hall;
import com.ssafy.db.entity.Question;
import com.ssafy.db.entity.Wedding;
import com.ssafy.db.repository.*;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service("certifyService")
public class CertifyServiceImpl implements CertifyService{

    JsonObject data = new JsonObject();

    String certificationsInfo;

    @Value("${key.imp_key}")
    private String imp_key;

    @Value("${key.imp_secret}")
    private String imp_secret;

    @Override
    public String certifyLogin(String imp_uid) {

        String access_token="";

        data.addProperty("imp_key",imp_key);
        data.addProperty("imp_secret", imp_secret);

        try {

            //POST 방식
            String host_url = "https://api.iamport.kr/users/getToken";
            HttpURLConnection conn = null;

            URL url = new URL(host_url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            conn.setDoOutput(true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));


            bw.write(data.toString());

            bw.flush();
            bw.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));


            String returnMsg = in.readLine();

            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(returnMsg);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonObject res = jsonObject.get("response").getAsJsonObject();

            JsonObject JsonObject = res.getAsJsonObject();
            access_token = JsonObject.get("access_token").getAsString();

            in.close();
            conn.disconnect();

        } catch (IOException ie) {
            System.out.println("IOException" + ie.getCause());
            ie.printStackTrace();
        } catch (Exception ee) {
            System.out.println(ee.getCause());
            ee.printStackTrace();
        }



        try {
            String host_url_2 = "https://api.iamport.kr/certifications/" + imp_uid;
            HttpURLConnection conn2 = null;
            URL url2 = new URL(host_url_2);
            conn2 = (HttpURLConnection) url2.openConnection();
            conn2.setRequestMethod("GET");

            conn2.setDoOutput(true);

            conn2.setRequestProperty("Authorization", access_token);

            BufferedReader in2 = new BufferedReader(new InputStreamReader(conn2.getInputStream(), "UTF-8"));


            certificationsInfo = in2.readLine();


        } catch (IOException ie) {
            System.out.println("IOException" + ie.getCause());
            ie.printStackTrace();
        } catch (Exception ee) {
            System.out.println(ee.getCause());
            ee.printStackTrace();
        }


        return certificationsInfo;
    }

}

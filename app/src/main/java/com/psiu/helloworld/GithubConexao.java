package com.psiu.helloworld;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;


public class GithubConexao {

    public static String getDados(String uri){
        BufferedReader bufferedReader = null;
        try {
            // Create URL
            URL url = new URL(uri);
            // URL url = new URL("https://raw.githubusercontent.com/policante/Coopercard-mobile/master/service/cards.json");
            // Create connection
            HttpsURLConnection myConnection = (HttpsURLConnection) url.openConnection();
            // Opicional
            myConnection.setRequestMethod("GET");
            myConnection.setRequestProperty("Content-Type", "application/json");
            /**
             * URL url = new URL(uri);
             * HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
             */

            StringBuilder stringBuilder = new StringBuilder();

            bufferedReader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));

            String linha, jsonS = "";

            while ((linha = bufferedReader.readLine()) != null){
                jsonS +=  linha+"\n";
                // stringBuilder.append(linha+"\n");
            }
            // return new Gson().fromJson(jsonS, );
            return stringBuilder.toString();
         }catch(Exception err) {
            err.printStackTrace();
            return null;
        }
        finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

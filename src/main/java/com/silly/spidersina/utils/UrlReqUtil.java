package com.silly.spidersina.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 实现GET以及POST请求
 */
public class UrlReqUtil {
    public static String get(String url) {
        HttpURLConnection http = null;
        InputStream is = null;
        try {
            URL urlGet = new URL(url);
            http = (HttpURLConnection) urlGet.openConnection();

            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");

            StringBuilder html = new StringBuilder();
            Reader reader = new InputStreamReader(http.getInputStream());
            int i;
            while ((i = reader.read()) > 0) {
                html.append((char) i);
            }
            reader.close();
            String htmlText = html.toString();
            return new String(htmlText.getBytes(), "UTF-8");
        } catch (Exception e) {
            return null;
        } finally {
            if (null != http) http.disconnect();
            try {
                if (null != is) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static String post(String url, String data) {
        HttpURLConnection http = null;
        PrintWriter out = null;
        BufferedReader reader = null;
        try {
            //创建连接
            URL urlPost = new URL(url);
            http = (HttpURLConnection) urlPost
                    .openConnection();
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setRequestMethod("POST");
            http.setUseCaches(false);
            http.setInstanceFollowRedirects(true);
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            http.connect();

            //POST请求
            OutputStreamWriter outWriter = new OutputStreamWriter(http.getOutputStream(), "utf-8");
            out = new PrintWriter(outWriter);
            out.print(data);
            out.flush();
            out.close();
            out = null;

            //读取响应
            reader = new BufferedReader(new InputStreamReader(
                    http.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            reader.close();
            reader = null;
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != http) http.disconnect();
            if (null != out) out.close();
            try {
                if (null != reader) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

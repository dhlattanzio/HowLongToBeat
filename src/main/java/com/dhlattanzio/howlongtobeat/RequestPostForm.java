package com.dhlattanzio.howlongtobeat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public final class RequestPostForm {
    private static byte[] convertMapToBytes(Map<String, String> parameters) throws UnsupportedEncodingException {
        StringJoiner sj=new StringJoiner("&");
        for(Map.Entry<String, String> entry : parameters.entrySet()) {
            sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                    + URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return sj.toString().getBytes(StandardCharsets.UTF_8);
    }

    public static String request(String pageUrl) {
        return request(pageUrl, new HashMap<>());
    }

    public static String request(String pageUrl, Map<String, String> parameters) {
        try {
            URL url=new URL(pageUrl);
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");

            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(convertMapToBytes(parameters));
            }

            int status = conn.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder content = new StringBuilder();
            while((line = in.readLine()) != null) {
                content.append(line);
            }
            in.close();
            return content.toString();
        } catch (Exception e) {
            return null;
        }
    }
}

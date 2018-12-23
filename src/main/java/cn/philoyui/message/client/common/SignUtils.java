package cn.philoyui.message.client.common;

import cn.philoyui.message.client.exp.GmosException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;

public class SignUtils {

    public static String sign(Map<String, String> paramValues, String secret) {
        try {

            List<String> paramNames = new ArrayList<String>(paramValues.size());
            paramNames.addAll(paramValues.keySet());
            Collections.sort(paramNames);

            StringBuilder signContentBuilder = new StringBuilder();
            signContentBuilder.append(secret);
            for (String paramName : paramNames) {
                signContentBuilder.append(paramName).append(paramValues.get(paramName));
            }
            signContentBuilder.append(secret);

            byte[] sha1Digest = getSHA1Digest(signContentBuilder.toString());
            return byte2hex(sha1Digest);
        } catch (IOException e) {
            throw new GmosException("签名出错",e);
        }
    }

    private static byte[] getSHA1Digest(String data) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException var3) {
            throw new IOException(var3.getMessage());
        }
    }

    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();

        for(int i = 0; i < bytes.length; ++i) {
            String hex = Integer.toHexString(bytes[i] & 255);
            if (hex.length() == 1) {
                sign.append("0");
            }

            sign.append(hex.toUpperCase());
        }

        return sign.toString();
    }

}


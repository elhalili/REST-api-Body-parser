package com.bodyParser.body.utils;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BodyUtils {
    private String body;
    private String mimeType;
    private String boundry;
    private String encoding;

    public BodyUtils(BufferedReader reader, String mimeType, String encoding) {
        this.encoding = encoding;
        Stream<String> bodyStreams = reader.lines();

        this.body = bodyStreams.reduce("", (acc, ele) -> acc + ele);

        if (mimeType.contains("multipart/form-data")) {
            this.mimeType = "multipart/form-data";
            this.boundry = mimeType.split("; ")[1].split("=")[1];
        } else {
            this.mimeType = mimeType;
            this.boundry = "";
        }
    }
    public BodyUtils() {
        this.body = "";
        this.mimeType = "";
    }
    private Map<String, String> parseFormData() {
        Map<String, String> parsedBody = new HashMap<>();
        String[] content = body.split("--" + boundry);

        for (int i = 1; i < content.length - 1; i++) {
            content[i] = content[i].replace("Content-Disposition: form-data; ", "");
            content[i] = content[i].replace("name=\"", "");

            String[] keyValue = content[i].split("\"", 2);

            parsedBody.put(keyValue[0], keyValue[1]);
        }

        return parsedBody;
    }

    private Map<String, String> parseUrlEncoded() throws UnsupportedEncodingException {
        Map<String, String> parsedBody = new HashMap<>();
        String[] encodedKeyValue = body.split("&");

        for (String keyValue: encodedKeyValue) {
            String[] kv = keyValue.split("=");

            parsedBody.put(URLDecoder.decode(kv[0], encoding), URLDecoder.decode(kv[1], encoding));
        }

        return parsedBody;
    }

    public Map<String, String> getParsedBody() throws UnsupportedEncodingException {
        if (mimeType.equals("multipart/form-data")) return parseFormData();
        else if (mimeType.equals("application/x-www-form-urlencoded")) return parseUrlEncoded();
        else return null;
    }
}

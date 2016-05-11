package com.snail.olaxueyuan.common.manager;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Logger {
    public static final int VERBOSE = 0;
    public static final int INFO = 1;
    public static final int DEBUG = 2;
    public static final int WARN = 3;
    public static final int ERROR = 4;
    public static final int NONE = 5;
    public static int level = VERBOSE;
    public static String tag = "olaxueyuan";

    /**
     * It is used for json pretty print
     */
    private static final int JSON_INDENT = 4;
    /**
     * Android's max limit for a log entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset
     * is UTF-8
     */
    private static final int CHUNK_SIZE = 4000;

    private static String getDebugInfo() {
        Throwable stack = new Throwable().fillInStackTrace();
        StackTraceElement[] trace = stack.getStackTrace();
        int n = 2;
        return trace[n].getClassName() + " " + trace[n].getMethodName() + "()" + ":" + trace[n].getLineNumber() + ": ";
    }

    public static void v(String s) {
        if (VERBOSE >= level) {
            logs(VERBOSE, s);
        }
    }

    public static void i(String s) {
        if (INFO >= level) {
            logs(INFO, s);
        }
    }

    public static void w(String s) {
        if (WARN >= level) {
            logs(WARN, s);
        }
    }

    public static void e(String s) {
        if (ERROR >= level) {
            logs(ERROR, s);
        }
    }

    public static void d(String s) {
        if (DEBUG >= level) {
            logs(DEBUG, s);
        }
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        if (TextUtils.isEmpty(json)) {
            d("Empty/Null json content");
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                d(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                d(message);
            }
        } catch (JSONException e) {
            e(e.getCause().getMessage() + "\n" + json);
        }
    }

    /**
     * Formats the json content and print it
     *
     * @param src the json content
     */
    public static void json(Object src) {
        String json = new Gson().toJson(src);
        if (TextUtils.isEmpty(json)) {
            d("Empty/Null json content");
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                d(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                d(message);
            }
        } catch (JSONException e) {
            e(e.getCause().getMessage() + "\n" + json);
        }
    }

    /**
     * Formats the xml content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        if (TextUtils.isEmpty(xml)) {
            d("Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            d(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            e(e.getCause().getMessage() + "\n" + xml);
        }
    }

    /**
     * This method is synchronized in order to avoid messy of logs' order.
     */
    private static synchronized void logs(int logType, String message) {
        if (level == NONE) {
            return;
        }
        if (TextUtils.isEmpty(message)) {
            message = "Empty/NULL log message";
        }
        byte[] bytes = message.getBytes();
        int length = bytes.length;
        if (length > CHUNK_SIZE) {
            for (int i = 0; i < length; i += CHUNK_SIZE) {
                int count = Math.min(length - i, CHUNK_SIZE);
                logChunk(logType, new String(bytes, i, count));
            }
        } else {
            logChunk(logType, message);
        }
    }

    private static void logChunk(int logType, String chunk) {
        switch (logType) {
            case ERROR:
                Log.e(tag, getDebugInfo() + "\n" + chunk);
                break;
            case INFO:
                Log.i(tag, getDebugInfo() + "\n" + chunk);
                break;
            case VERBOSE:
                Log.v(tag, getDebugInfo() + "\n" + chunk);
                break;
            case WARN:
                Log.w(tag, getDebugInfo() + "\n" + chunk);
                break;
            case DEBUG:
                Log.d(tag, getDebugInfo() + "\n" + chunk);
                break;
            default:
                Log.i(tag, getDebugInfo() + "\n" + chunk);
                break;
        }
    }
}

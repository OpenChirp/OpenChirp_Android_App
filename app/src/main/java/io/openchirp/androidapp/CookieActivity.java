package io.openchirp.androidapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class CookieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookie);
        _instance = this;
        _preferences = PreferenceManager.getDefaultSharedPreferences(this);
        _requestQueue = Volley.newRequestQueue(this);
    }


        private static final String SET_COOKIE_KEY = "Set-Cookie";
        private static final String COOKIE_KEY = "Cookie";
        private static final String SESSION_COOKIE = "sessionid";

        private static io.openchirp.androidapp.CookieActivity _instance;
        private RequestQueue _requestQueue;
        private SharedPreferences _preferences;

        public static io.openchirp.androidapp.CookieActivity get() {
            return _instance;
        }

        public RequestQueue getRequestQueue() {
            return _requestQueue;
        }


        /**
         * Checks the response headers for session cookie and saves it
         * if it finds it.
         * @param headers Response Headers.
         */
        public final void checkSessionCookie(Map<String, String> headers) {
            if (headers.containsKey(SET_COOKIE_KEY)
                    && headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
                String cookie = headers.get(SET_COOKIE_KEY);
                if (cookie.length() > 0) {
                    String[] splitCookie = cookie.split(";");
                    String[] splitSessionId = splitCookie[0].split("=");
                    cookie = splitSessionId[1];
                    SharedPreferences.Editor prefEditor = _preferences.edit();
                    prefEditor.putString(SESSION_COOKIE, cookie);
                    prefEditor.commit();
                }
            }
        }

        /**
         * Adds session cookie to headers if exists.
         * @param headers
         */
        public final void addSessionCookie(Map<String, String> headers) {
            String sessionId = _preferences.getString(SESSION_COOKIE, "");
            if (sessionId.length() > 0) {
                StringBuilder builder = new StringBuilder();
                builder.append(SESSION_COOKIE);
                builder.append("=");
                builder.append(sessionId);
                if (headers.containsKey(COOKIE_KEY)) {
                    builder.append("; ");
                    builder.append(headers.get(COOKIE_KEY));
                }
                headers.put(COOKIE_KEY, builder.toString());
            }
        }

    }



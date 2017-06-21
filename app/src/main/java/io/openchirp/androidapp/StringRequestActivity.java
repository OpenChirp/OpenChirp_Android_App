package io.openchirp.androidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



    class StringRequest extends com.android.volley.toolbox.StringRequest {

        private final Map<String, String> _params;

        public StringRequest(int method, String url, Map<String, String> params, Response.Listener<String> listener,
                             Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);

            Log.d("Cookie Paramss:", params.toString());

            _params = params;
        }

        @Override
        protected Map<String, String> getParams() {

            Log.d("Cookie Params", _params.toString());

            return _params;
        }

        /* (non-Javadoc)
         * @see com.android.volley.toolbox.StringRequest#parseNetworkResponse(com.android.volley.NetworkResponse)
         */
        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            // since we don't know which of the two underlying network vehicles
            // will Volley use, we have to handle and store session cookies manually
            CookieActivity.get().checkSessionCookie(response.headers);

            return super.parseNetworkResponse(response);
        }

        /* (non-Javadoc)
         * @see com.android.volley.Request#getHeaders()
         */
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = super.getHeaders();

            if (headers == null
                    || headers.equals(Collections.emptyMap())) {
                headers = new HashMap<String, String>();
            }

            CookieActivity.get().addSessionCookie(headers);

            Log.d("Cookie Headers:", headers.toString());

            return headers;
        }
    }


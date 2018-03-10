package id.co.firas.crudvolley;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Firas Luthfi on 1/17/2018.
 */

public class Controller extends Application {

    public static final String TAG = Controller.class.getSimpleName();

    private RequestQueue queue;

    private static Controller controller;

    @Override
    public void onCreate() {
        super.onCreate();
        controller = this;
    }

    public static synchronized Controller getPermission() {
        return controller;
    }

    public RequestQueue getRequestQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(getApplicationContext());
        }

        return queue;
    }

    public void addToRequestQueue(Request req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public void addToRequestQueue(Request req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (queue != null) {
            queue.cancelAll(tag);
        }
    }

}

package fidencio.jefferson.hbsisteste.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class ServerConnection {

    private ImageLoader imageLoader;
    private RequestQueue requestQueue;

    private static Context context;
    private static ServerConnection serverConnection;

    public static synchronized ServerConnection getInstance(Context _context){
        context = _context;
        if (serverConnection == null)
            serverConnection = new ServerConnection();
        return serverConnection;
    }

    public ServerConnection(){
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context);

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
    public ImageLoader getImageLoader() { return imageLoader; }
    public String getBaseURI() {
        return "http://www.hshpersonal.com.br/";
    }
}

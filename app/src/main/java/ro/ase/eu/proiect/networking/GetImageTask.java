package ro.ase.eu.proiect.networking;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

public class GetImageTask  extends AsyncTask<String, Void, Bitmap> {
    ImageView ivImg;
    Bitmap myPic = null;
    public GetImageTask(ImageView bmImage) {
        this.ivImg = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        try {
            InputStream in = new java.net.URL(url).openStream();
            myPic = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myPic;
    }

    public Bitmap getBtmapFromURL(){
        return myPic;
    }
    protected void onPostExecute(Bitmap pic) {
        ivImg.setImageBitmap(pic);
    }
}

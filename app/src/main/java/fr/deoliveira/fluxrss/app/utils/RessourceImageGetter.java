package fr.deoliveira.fluxrss.app.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;

/**
 * Created by Romain on 19/01/2016.
 */
public class RessourceImageGetter implements Html.ImageGetter {
    private Context mContext;

    public RessourceImageGetter(Context context) {
        mContext = context;
    }

    @Override
    public Drawable getDrawable(String source) {
        Resources resources = mContext.getResources();
        //int identifier = resources.getIdentifier(source, "drawable", mContext.getPackageName());
        //Drawable res  = mContext.getResources().getDrawable(Integer.parseInt(source));
       // Drawable res = resources.getDrawable(identifier);
        //res.setBounds(0, 0, res.getIntrinsicWidth(), res.getIntrinsicHeight());
        Log.w(this.getClass().getName(),"SOURCE : " + source);
        return null;
    }
}

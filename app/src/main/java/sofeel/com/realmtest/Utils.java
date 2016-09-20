package sofeel.com.realmtest;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by user on 2016/9/13.
 */
public final class Utils {

    private static Context mContext;

    public static Context getContext(){
        if(mContext==null)
            mContext=new MyApplication();
        return mContext;
    }


    public static void toast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }


}

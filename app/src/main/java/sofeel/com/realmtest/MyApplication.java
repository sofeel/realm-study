package sofeel.com.realmtest;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

/**
 * Created by user on 2016/9/13.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration build = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(build);
    }
}

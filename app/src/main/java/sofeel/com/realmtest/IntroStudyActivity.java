package sofeel.com.realmtest;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import sofeel.com.realmtest.model.Person;

/**
 * Created by user on 2016/9/14.
 */
public class IntroStudyActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG= IntroStudyActivity.class.getName();
    private RealmConfiguration realmConfig;
    private Realm realm;
    private LinearLayout rootView;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_introexample);
        realmConfig = new RealmConfiguration.Builder(this).build();
        realm = Realm.getInstance(realmConfig);
        initView();
        add();
    }

    private void initView() {
        rootView = (LinearLayout)findViewById(R.id.container);
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.update).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.find).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.add:
                add();
                break;
            case R.id.update:
                update();
                break;
            case R.id.cancel:
                cancel();
                break;
            case R.id.find:
                find();
                break;
        }
    }

    private void find() {

    }

    private void cancel() {
    }

    private void update() {
    }

    private void add() {//basic
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person person = realm.createObject(Person.class);
                person.setId(1);
                person.setName("young person");
                person.setAge(14);
            }
        });
        Person p = realm.where(Person.class).findFirst();
        showStatus(p.getName()+":"+p.getAge());
    }

    private void showStatus(String txt) {
        Log.i(TAG, txt);
        TextView tv = new TextView(this);
        tv.setText(txt);
        rootView.addView(tv);

    }
}

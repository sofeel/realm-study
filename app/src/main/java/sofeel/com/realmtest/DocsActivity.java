package sofeel.com.realmtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.File;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;
import sofeel.com.realmtest.model.Cat;
import sofeel.com.realmtest.model.User;

/**
 * Created by user on 2016/9/19.
 */
public class DocsActivity extends Activity implements View.OnClickListener {

    public static final String TAG = DocsActivity.class.getName();
    private Realm realm;
    private String content = "";
    private ListView list;
    RealmResults<User> users;
    private MyAdapter myAdapter;
    private RealmChangeListener listener=new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            myAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        users.removeChangeListener(listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docs);
//        RealmConfiguration build = new RealmConfiguration.Builder(this).build();
//        realm = Realm.getInstance(build);
        realm = Realm.getDefaultInstance();
        File filesDir = getFilesDir();
        Log.i(TAG+".onCreate",filesDir.getAbsolutePath()+":"+filesDir.getPath());
        initView();

        initData();
    }

    private void initData() {

        users = realm.where(User.class)
                .equalTo("cats.age", 55)
//                .beginGroup()
////                .between("id",0,5000)
//                .not()
//                .lessThanOrEqualTo("id", 0)
//                .endGroup()
//                .equalTo("cats.age",55)
                .findAll()
                .sort("id", Sort.DESCENDING);
        users.addChangeListener(listener);
        Log.e(TAG, users.size() + "");
        myAdapter = new MyAdapter(this, users, realm);
        list.setAdapter(myAdapter);

        double average = users.average("id");
        Log.i(TAG+".average",average+"");
        if(users.max("id")!=null){
            long id = users.max("id").longValue();
            Log.i(TAG+".max",id+"");
        }
    }



    private void initView() {
        findViewById(R.id.tvAutoUpdating).setOnClickListener(this);
        list = (ListView) findViewById(R.id.list);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAutoUpdating:
                autoUpdate();
                break;
        }
    }

    private void autoUpdate() {
        User user = new User();
        Random random = new Random();
        user.setId(random.nextInt());
        user.setName("person_1");
        user.setAge(1);

        Cat cat = new Cat();
        cat.name="lili";
        cat.age=55;

        RealmList<Cat> cats = new RealmList<>();
        cats.add(cat);
        user.setCats(cats);

        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();

        Log.i(TAG+".autoUpdate","insert ok!");


        /*realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Random random = new Random();
                User user = realm.createObject(User.class);
                user.setId(random.nextInt(2));
                user.setName("person_" + random.nextInt(2));
                user.setAge(random.nextInt(2));
            }
        });*/

        User u = realm.where(User.class).equalTo("age", 1).findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User first = realm.where(User.class).equalTo("age", 1).findFirst();
                if (first != null)
                    first.setAge(10);
            }
        });

        if (u != null) {

        }
    }


}

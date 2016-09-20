package sofeel.com.realmtest;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import sofeel.com.realmtest.model.Dog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int UPDATE_OK = 0;
    public static final int UPDATE_ERROR = 1;
    private static final int NOTIFY_VIEW = 2;
    private Realm mRealm;
    private ListView mList;
    //    private RealmResults<User> users;
    private RealmResults<Dog> puppies;
    private MyAdapter myAdapter;
    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_OK:
//                    Utils.toast("OnSuccess");
                    break;
                case UPDATE_ERROR:
//                    Utils.toast("onError:" + msg.obj);
                    break;
                case NOTIFY_VIEW:
                    myAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        findViewById(R.id.tvIntroExample).setOnClickListener(this);
        findViewById(R.id.tvIntroStudy).setOnClickListener(this);
        findViewById(R.id.tvDOCS).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tvIntroExample:
                startActivity(new Intent(this, IntroExampleActivity.class));
                break;
            case R.id.tvIntroStudy:
                startActivity(new Intent(this, IntroStudyActivity.class));
                break;
            case R.id.tvDOCS:
                startActivity(new Intent(this, DocsActivity.class));
                break;
        }
    }


    private class MyAdapter extends BaseAdapter {

        private RealmResults<Dog> puppies;
        private Context context;

        public MyAdapter(Context context, RealmResults<Dog> puppies) {
            this.puppies = puppies;
            this.context = context;
        }

        @Override
        public int getCount() {
            return puppies.size();
        }

        @Override
        public Object getItem(int i) {
            return puppies.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Holder holder = null;
            if (view == null) {
                holder = new Holder();
                view = LayoutInflater.from(context).inflate(R.layout.item_activity_main, null);
                holder.tvId = (TextView) view.findViewById(R.id.userId);
                holder.tvName = (TextView) view.findViewById(R.id.userName);
                holder.tvAge = (TextView) view.findViewById(R.id.userAge);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            Dog dog = puppies.get(i);
            if (dog != null) {
                holder.tvId.setText("dogId");
            }
            return view;
        }
    }

    private class Holder {
        private TextView tvId;
        private TextView tvName;
        private TextView tvAge;
    }
}

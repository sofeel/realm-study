package sofeel.com.realmtest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import sofeel.com.realmtest.model.Cat;
import sofeel.com.realmtest.model.User;

/**
 * Created by user on 2016/9/19.
 */
public class MyAdapter extends BaseAdapter implements View.OnClickListener{

    public static final String TAG=MyAdapter.class.getName();
    private RealmResults<User> users;
    private Realm realm;
    private Context context;

    public MyAdapter(Context context,RealmResults<User> users,Realm realm){
        this.users=users;
        this.realm=realm;
        this.context=context;
    }
    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{

        private TextView tvId;
        private TextView tvName;
        private TextView tvAge;
        private TextView tvInsert;
        private TextView tvUpdate;
        private TextView tvDelete;
        private TextView tvDogs;
    }

    private ViewHolder vh =null;
    private int position;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        position=i;
        if(view==null)
        {
            vh=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.item_docs,null);
            vh.tvId= (TextView) view.findViewById(R.id.tvId);
            vh.tvName= (TextView) view.findViewById(R.id.tvName);
            vh.tvAge= (TextView) view.findViewById(R.id.tvAge);
            vh.tvDogs= (TextView) view.findViewById(R.id.tvDogs);
            vh.tvInsert= (TextView) view.findViewById(R.id.insert);
            vh.tvUpdate= (TextView) view.findViewById(R.id.update);
            vh.tvDelete= (TextView) view.findViewById(R.id.delete);
            view.setTag(vh);
        }else {
            vh= (ViewHolder) view.getTag();
        }

        User user = users.get(i);
        if(user!=null){
            vh.tvId.setText(user.getId()+"");
            vh.tvName.setText(user.getName());
            vh.tvAge.setText(user.getAge()+"");
            String cats="";
            for(Cat c:user.getCats())
            {
                cats+=c.name+":"+c.age+"  ";
            }
            vh.tvDogs.setText(cats);

            vh.tvInsert.setOnClickListener(this);
            vh.tvUpdate.setOnClickListener(this);
            vh.tvDelete.setOnClickListener(this);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.insert:
               insert();
                break;
            case R.id.update:
                update();
                break;
            case R.id.delete:
                delete(position);
                break;
        }
    }

    private void delete(int position) {
        //        users.get(users.size()).deleteFromRealm();
        realm.beginTransaction();
        users.deleteFromRealm(position);
        realm.commitTransaction();
        Log.i(TAG+"_delete","delete ok");
    }

    private void update() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User first = realm.where(User.class).equalTo("age", 1).findFirst();
                if (first != null)
                    first.setAge(10);
            }
        });
    }

    private void insert() {
        realm.beginTransaction();
        User user =new User();
        user.setId(new Random().nextInt());
        user.setName("person_1");
        user.setAge(1);
        realm.copyToRealm(user);
        realm.commitTransaction();
    }
}

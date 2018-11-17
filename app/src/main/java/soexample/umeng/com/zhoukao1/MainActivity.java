package soexample.umeng.com.zhoukao1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.zhoukao1.Adapter.Wo;
import soexample.umeng.com.zhoukao1.SqliteUtils.SqliteUtils;
import soexample.umeng.com.zhoukao1.Adapter.ShangAdapter;
import soexample.umeng.com.zhoukao1.net.HttpHelper;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;
    List<bean.DataBean.DataList> list1 = new ArrayList<>();
    private TextView textt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(MainActivity.this);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.view);
        textt = (TextView) findViewById(R.id.wo);
        textt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  context.startActivity(new Intent(MainActivity.this, Wo.class));
            }
        });

        okHttp();//判断网络情况
        if (isNetworkAvalible(MainActivity.this)) {
            okHttp();
        } else {
            //取出数据
            Car car = SqliteUtils.getSqliteUtils().queryAll().get(0);
            String data = car.getData();

            bean bean = new Gson().fromJson(data, bean.class);
            List<bean.DataBean> data1 = bean.getData();
            List<bean.DataBean.DataList> list1 = new ArrayList<>();
            for (int i = 0; i < data1.size(); i++) {
                if (data1.get(i).getList() == null || data1.get(i).getList().size() == 0) {

                } else {
                    list1.addAll(data1.get(i).getList());
                }
            }
            ShangAdapter shangAdapter = new ShangAdapter(MainActivity.this, list1);
            StaggeredGridLayoutManager s = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(s);
            recyclerView.setAdapter(shangAdapter);
        }
    }

    private void okHttp() {
        //网络接口
        String url = "http://www.zhaoapi.cn/product/getCarts?uid=71";
        new HttpHelper().get(url).result(new HttpHelper.HttpListener() {
            @Override
            public void success(String data) {
                //清除数据
                SqliteUtils.getSqliteUtils().deleteAll();
                Car car = new Car();
                car.setData(data);//存入数据
                SqliteUtils.getSqliteUtils().insert(car);
               //解析数据
                bean bean = new Gson().fromJson(data, bean.class);
                List<bean.DataBean> data1 = bean.getData();
                for (int i = 0; i < data1.size(); i++) {
                    if (data1.get(i).getList() == null || data1.get(i).getList().size() == 0) {

                    } else {
                        list1.addAll(data1.get(i).getList());
                    }
                }//瀑布流
                ShangAdapter shangAdapter = new ShangAdapter(MainActivity.this, list1);
                StaggeredGridLayoutManager s = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(s);
                recyclerView.setAdapter(shangAdapter);

            }

            @Override
            public void fail(String error) {

            }
        });
    }

    /**
     * 判断网络情况
     */
    public boolean isNetworkAvalible(Context context) {
        // 获得网络状态管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();

            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

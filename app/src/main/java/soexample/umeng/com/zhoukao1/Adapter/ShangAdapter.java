package soexample.umeng.com.zhoukao1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.zhoukao1.bean;
import soexample.umeng.com.zhoukao1.R;

public class ShangAdapter extends RecyclerView.Adapter<ShangAdapter.sViewHplder> {
    private List<bean.DataBean.DataList> list = new ArrayList<>();
    private Context context;


    public ShangAdapter(Context context, List<bean.DataBean.DataList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public sViewHplder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.shang, null);
        sViewHplder vi = new sViewHplder(view);
        return vi;
    }

    @Override
    public void onBindViewHolder(sViewHplder holder, int position) {
        holder.textView.setText(list.get(position).getTitle());
        String images = list.get(position).getImages();
        String[] split = images.split("\\|");
        holder.imageView.setImageURI(split[0]);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class sViewHplder extends RecyclerView.ViewHolder{

        private final SimpleDraweeView imageView;
        private final TextView textView;

        public sViewHplder(View itemView) {
            super(itemView);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.img);
            textView = (TextView) itemView.findViewById(R.id.text);

        }
    }

}

package test.fatiedemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * Created by Administrator on 2017/11/6.
 */

public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    private SetOnClickListen setOnClickListen;

    public void onClick(SetOnClickListen setOnClickListen) {
        this.setOnClickListen = setOnClickListen;
    }

    private Context context;
    private List<LocalMedia> localMedia;

    public Adapter(Context context, List<LocalMedia> localMedia) {
        this.context = context;
        this.localMedia = localMedia;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.e("JGB","适配器的数据："+"http://www.olacos.net"+localMedia.get(position).getPath());
        Glide.with(context).load(localMedia.get(position).getPath()).into(holder.item_iv);
       holder.item_iv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               setOnClickListen.setOnClick(position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return localMedia.size();
    }
}
class ViewHolder extends RecyclerView.ViewHolder{

    public ImageView item_iv;
    public ViewHolder(View itemView) {
        super(itemView);
        item_iv = (ImageView) itemView.findViewById(R.id.item_iv);
    }
}

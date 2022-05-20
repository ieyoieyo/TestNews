package net.jo.testnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

//import android.support.v7.widget.RecyclerView;

/**
 * Created by Yusuf on 09.10.2016.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<JsonBean.GetVectorBean.ItemsBeanX> dataList;
    private Context mContext;

    public static final int TITLE = 1;
    public static final int NEWS = 2;

    // Constructor
    public NewsAdapter(List<JsonBean.GetVectorBean.ItemsBeanX> data, Context context) {
        this.dataList = data;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if ("divider".equalsIgnoreCase(dataList.get(position).getType()))
            return TITLE;
        else if ("news".equalsIgnoreCase(dataList.get(position).getType()))
            return NEWS;
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch (viewType) {
            case TITLE:
                v = LayoutInflater.from(mContext).inflate(R.layout.title, parent, false);
                v.setTag(TITLE);
                return new VHTitle(v);
            case NEWS:
                v = LayoutInflater.from(mContext).inflate(R.layout.json_row, parent, false);
                v.setTag(NEWS);
                return new MyViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        JsonBean.GetVectorBean.ItemsBeanX data = dataList.get(position);
        String mainTitle = "", url = "";
        int created = 0;

        switch (holder.getItemViewType()) {
            case TITLE:
                ((VHTitle) holder).titleTV.setVisibility(View.VISIBLE);
                ((VHTitle) holder).titleTV.setText(data.getTitle());
                break;

            case NEWS:
                if (null != data.getExtra()) {
                    created = data.getExtra().getCreated();
                    if (created != 0) {
                        long l = ((long) created) * 1000;
                        SimpleDateFormat sdf = new SimpleDateFormat("MMM d yyyy 'at' h:mm a",
                                Locale.US);
                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
                        String date = sdf.format(new Date(l));
                        ((MyViewHolder) holder).createdTV.setText(date);
                    } else {
                        ((MyViewHolder) holder).createdTV.setVisibility(View.GONE);
                    }
                } else {
                    ((MyViewHolder) holder).createdTV.setVisibility(View.GONE);
                }

                mainTitle = data.getAppearance().getMainTitle();
                if (null == mainTitle || "".equals(mainTitle)) {
                    ((MyViewHolder) holder).mainTitleTV.setVisibility(View.GONE);
                } else {
                    ((MyViewHolder) holder).mainTitleTV.setText(mainTitle);
                }

                String subTitle = data.getAppearance().getSubTitle();
                if (null == subTitle || "".equals(subTitle.trim())) {
                    ((MyViewHolder) holder).subTitleTV.setVisibility(View.GONE);
                } else {
                    ((MyViewHolder) holder).subTitleTV.setText(subTitle);
                }
                String subscript = data.getAppearance().getSubscript();
                if (null == subscript || "".equals(subscript.trim())) {
                    ((MyViewHolder) holder).subscriptTV.setVisibility(View.GONE);
                } else {
                    ((MyViewHolder) holder).subscriptTV.setText(subscript);
                }

                url = data.getAppearance().getThumbnail();
                Picasso.with(mContext).load(url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(((MyViewHolder) holder).profileImg);

                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

//    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
////        RecyclerView.ViewHolder vh = null;
//        View v = null;
//        switch (viewType) {
//            case TITLE:
//                v = LayoutInflater.from(mContext).inflate(R.layout.title, parent, false);
//                v.setTag(TITLE);
//                return new VHTitle(v);
//            case NEWS:
//                v = LayoutInflater.from(mContext).inflate(R.layout.json_row, parent, false);
//                v.setTag(NEWS);
//                return new MyViewHolder(v);
//        }
//        return null;
//    }

    // View Holder
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView createdTV, mainTitleTV, subTitleTV, subscriptTV;
        public ImageView profileImg;

        public MyViewHolder(View itemView) {
            super(itemView);

            createdTV = (TextView) itemView.findViewById(R.id.createdTV);
            mainTitleTV = (TextView) itemView.findViewById(R.id.mainTitleTV);
            subTitleTV = (TextView) itemView.findViewById(R.id.subTitleTV);
            subscriptTV = (TextView) itemView.findViewById(R.id.subscriptTV);
            profileImg = (ImageView) itemView.findViewById(R.id.imageView);

        }
    }


    public class VHTitle extends RecyclerView.ViewHolder {

        TextView titleTV;

        public VHTitle(@NonNull View itemView) {
            super(itemView);
            titleTV = (TextView) itemView.findViewById(R.id.titleTV);
        }
    }

}

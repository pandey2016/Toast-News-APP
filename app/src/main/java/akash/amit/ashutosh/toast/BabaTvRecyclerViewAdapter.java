package akash.amit.ashutosh.toast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by AKASH on 8/31/2017.
 */
public class BabaTvRecyclerViewAdapter extends RecyclerView.Adapter<BabaTvRecyclerViewHolder> {

    public List<ImageUpload> imgList;
    private Context context;

    public BabaTvRecyclerViewAdapter(Context context, List<ImageUpload> imgList) {
        this.imgList = imgList;
        this.context = context;
    }

    @Override
    public BabaTvRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.babatvcard, null);
        BabaTvRecyclerViewHolder rcv = new BabaTvRecyclerViewHolder(layoutView, imgList);
        return rcv;
    }

    @Override
    public void onBindViewHolder(BabaTvRecyclerViewHolder holder, int position) {
        ImageUpload currentImg=imgList.get(position);
        //Log.i("mymessage", currentImg.url);
        if ((currentImg.url).isEmpty()) {
            holder.iv1.setImageResource(R.drawable.a);
        } else{
            Picasso.with(context).load(currentImg.url).placeholder(R.drawable.a).into(holder.iv1);
        }

        holder.tvTitle.setText(currentImg.title);
        holder.tvDate.setText(currentImg.date);
        holder.tvDescription.setText(currentImg.description);
    }


    @Override
    public int getItemCount() {
        return this.imgList.size();
    }


}
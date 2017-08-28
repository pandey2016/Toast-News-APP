package akash.amit.ashutosh.toast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    public List<NewsItem> newsList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<NewsItem> newsList) {
        this.newsList = newsList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView, newsList);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        NewsItem currentNews=newsList.get(position);

        if ((currentNews.imgpath).isEmpty()) {
            holder.iv1.setImageResource(R.drawable.a11);
        } else{
            Picasso.with(context).load(currentNews.imgpath).into(holder.iv1);
        }
        Log.e("message", currentNews.imgpath);
        holder.tvTitle.setText(currentNews.title);
        holder.tvDate.setText(currentNews.date);
        holder.tvDescription.setText(currentNews.description);

    }

    @Override
    public int getItemCount() {
        return this.newsList.size();
    }

}
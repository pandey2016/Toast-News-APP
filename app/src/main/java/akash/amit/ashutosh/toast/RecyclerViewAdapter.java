package akash.amit.ashutosh.toast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<NewsItem> newsList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<NewsItem> newsList) {
        this.newsList = newsList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        NewsItem currentNews=newsList.get(position);

        Picasso.with(context).load(currentNews.imgpath).into(holder.iv1);
        holder.tvTitle.setText(currentNews.title);
        holder.tvDate.setText(currentNews.date);
        holder.tvDescription.setText(currentNews.description);

        //holder.countryName.setText(itemList.get(position).getName());
        //holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return this.newsList.size();
    }
}
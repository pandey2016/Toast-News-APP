package akash.amit.ashutosh.toast;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView iv1;
    public TextView tvTitle;
    TextView tvDate;
    TextView tvDescription;
    private List<NewsItem> newsList;
    Context context;
    public RecyclerViewHolders(View itemView, List<NewsItem> newsList) {
        super(itemView);
        this.newsList = newsList;
        context=itemView.getContext();
        itemView.setOnClickListener(this);
         iv1= (ImageView) itemView.findViewById(R.id.imageview_1);
         tvTitle= (TextView) itemView.findViewById(R.id.textview_1);
         tvDate= (TextView) itemView.findViewById(R.id.textview_2);
         tvDescription= (TextView) itemView.findViewById(R.id.textview_3);


        //countryName = (TextView)itemView.findViewById(R.id.country_name);
        //countryPhoto = (ImageView)itemView.findViewById(R.id.country_photo);
    }

    @Override
    public void onClick(View view) {
        int position =getAdapterPosition();
        NewsItem currentNews=newsList.get(position);
        //Toast.makeText(view.getContext(), "Clicked  Position = " + currentNews.link, Toast.LENGTH_SHORT).show();
        Intent browserIntent = new Intent(context,webView.class);
        browserIntent.putExtra("name", currentNews.link);
        context.startActivity(browserIntent);
    }
}
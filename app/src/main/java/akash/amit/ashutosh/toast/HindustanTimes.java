package akash.amit.ashutosh.toast;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class HindustanTimes extends AppCompatActivity {
    ArrayList<NewsItem> newsItemList;
    private LinearLayoutManager lLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hindustan_times);

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Breaking News");
        progress.setMessage("connecting...");
        progress.show();
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progress.cancel();
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 3000);

        newsItemList= new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request =new StringRequest("http://www.hindustantimes.com/rss/india/rssfeed.xml", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document doc = Jsoup.parse(response);
                Elements itemElements=doc.getElementsByTag("item");
                //Log.i("mytag","items found :"+itemElements.size());

                for(int i=0;i<itemElements.size();i++){
                    //Getting Elements
                    Element item=itemElements.get(i);
                    //Getting Child
                    String title=removeCdata(item.child(0).text());
                    String guid=item.getElementsByTag("guid").text();
                    String description=removeCdata(item.child(1).text());
                    String pubdate=item.getElementsByTag("pubDate").text();
                    String img=item.getElementsByTag("media:content").attr("url");
                    //String description=removeCdata(item.getElementsByTag("media:description").text());
                    NewsItem news=new NewsItem();
                    news.title=title;
                    news.description=description;
                    news.link=guid;
                    news.imgpath=img;
                    news.date=pubdate;
                    Log.i("yoooooooo","khj"+description);
                    newsItemList.add(news);
                }
                //Recycler View
                lLayout = new LinearLayoutManager(HindustanTimes.this);
                RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
                rView.setLayoutManager(lLayout);
                RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(HindustanTimes.this, newsItemList);
                rView.setAdapter(rcAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HindustanTimes.this, "Request Error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);

    }
    String removeCdata(String data){
        data =data.replace("<![CDATA["," ");
        data =data.replace("]]>"," ");
        return data;
    }
}

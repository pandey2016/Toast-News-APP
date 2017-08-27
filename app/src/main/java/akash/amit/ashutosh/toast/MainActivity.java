package akash.amit.ashutosh.toast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {

    ArrayList<NewsItem> newsItemList;
    private LinearLayoutManager lLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        newsItemList= new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request =new StringRequest("http://indiatoday.intoday.in/rss/article.jsp", new Response.Listener<String>() {
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
                    String guid=removeCdata(item.child(1).text());
                    String description=removeCdata(item.child(2).text());
                    String pubdate=item.getElementsByTag("pubDate").text();
                    Document doc3 = Jsoup.parse(description);
                    String ownText = doc3.body().ownText();
                    //making child document to use child kaa child
                    Document doc2=Jsoup.parse(description);
                    String img=doc2.getElementsByTag("img").attr("src");
                    Log.i("yoooooooo","khj"+pubdate);
                    NewsItem news=new NewsItem();
                    news.title=title;
                    news.description=ownText;
                    news.link=guid;
                    news.imgpath=img;
                    news.date=pubdate;

                    newsItemList.add(news);
                }
                //Recycler View
                lLayout = new LinearLayoutManager(MainActivity.this);
                RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
                rView.setLayoutManager(lLayout);
                RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, newsItemList);
                rView.setAdapter(rcAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Request Error", Toast.LENGTH_SHORT).show();
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

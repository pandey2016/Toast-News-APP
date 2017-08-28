package akash.amit.ashutosh.toast;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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

//NEWS18
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request =new StringRequest("http://www.news18.com/rss/india.xml", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document doc = Jsoup.parse(response);
                Elements itemElements=doc.getElementsByTag("item");
                Element item=itemElements.get(0);
                String title=removeCdata(item.child(0).text());
                String description=removeCdata(item.child(2).text());
                Document doc2=Jsoup.parse(description);
                String img=doc2.getElementsByTag("img").attr("src");
                ImageView iv1= (ImageView) findViewById(R.id.imageview);
                Picasso.with(MainActivity.this).load(img).placeholder(R.drawable.a).into(iv1);
                TextView tv1= (TextView) findViewById(R.id.textview);
                tv1.setText(title);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Request Error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
        //   HINDUSTAN TIMES
        //RequestQueue queue2 = Volley.newRequestQueue(this);
        StringRequest request2 =new StringRequest("http://www.hindustantimes.com/rss/india/rssfeed.xml", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document doc = Jsoup.parse(response);
                Elements itemElements=doc.getElementsByTag("item");
                Element item=itemElements.get(0);
                String title=removeCdata(item.child(0).text());
                String img=item.getElementsByTag("media:content").attr("url");
                ImageView iv2= (ImageView) findViewById(R.id.imageview2);
                Picasso.with(MainActivity.this).load(img).placeholder(R.drawable.a).into(iv2);
                TextView tv2= (TextView) findViewById(R.id.textview2);
                tv2.setText(title);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Request Error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request2);

//INDIAN EXPRESSS
        //RequestQueue queue3 = Volley.newRequestQueue(this);
        StringRequest request3 =new StringRequest("http://indianexpress.com/section/india/feed/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document doc = Jsoup.parse(response);
                Elements itemElements=doc.getElementsByTag("item");
                Element item=itemElements.get(0);
                String title=removeCdata(item.child(0).text());
                String img=item.getElementsByTag("media:content").attr("url");
                ImageView iv3= (ImageView) findViewById(R.id.imageview3);
                Picasso.with(MainActivity.this).load(img).placeholder(R.drawable.a).into(iv3);
                TextView tv3= (TextView) findViewById(R.id.textview3);
                tv3.setText(title);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Request Error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request3);

//INDIA TODAY
        RequestQueue queue4 = Volley.newRequestQueue(this);
        StringRequest request4 =new StringRequest("http://indiatoday.intoday.in/rss/article.jsp", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document doc = Jsoup.parse(response);
                Elements itemElements=doc.getElementsByTag("item");
                Element item=itemElements.get(0);
                String title=removeCdata(item.child(0).text());
                String description=removeCdata(item.child(2).text());
                Document doc2=Jsoup.parse(description);
                String img=doc2.getElementsByTag("img").attr("src");
                ImageView iv4= (ImageView) findViewById(R.id.imageview4);
                Picasso.with(MainActivity.this).load(img).placeholder(R.drawable.a).into(iv4);
                TextView tv4= (TextView) findViewById(R.id.textview4);
                tv4.setText(title);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Request Error", Toast.LENGTH_SHORT).show();
            }
        });
        queue4.add(request4);

//india tv news
        //RequestQueue queue5 = Volley.newRequestQueue(this);
        StringRequest request5 =new StringRequest("http://www.indiatvnews.com/rssfeed/india.xml", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document doc = Jsoup.parse(response);
                Elements itemElements=doc.getElementsByTag("item");
                Element item=itemElements.get(0);
                String title=removeCdata(item.child(0).text());
                String img=item.getElementsByTag("media:image").attr("url");
                ImageView iv5= (ImageView) findViewById(R.id.imageview5);
                Picasso.with(MainActivity.this).load(img).placeholder(R.drawable.a).into(iv5);
                TextView tv5= (TextView) findViewById(R.id.textview5);
                tv5.setText(title);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Request Error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request5);

//ONE INDIA
       // RequestQueue queue6 = Volley.newRequestQueue(this);
        StringRequest request6 =new StringRequest("http://www.oneindia.com/rss/news-india-fb.xml", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document doc = Jsoup.parse(response);
                Elements itemElements=doc.getElementsByTag("item");
                Element item=itemElements.get(0);
                String title=removeCdata(item.child(0).text());
                String img=item.getElementsByTag("enclosure").attr("url");
                ImageView iv6= (ImageView) findViewById(R.id.imageview6);
                Picasso.with(MainActivity.this).load(img).placeholder(R.drawable.a).into(iv6);
                TextView tv6= (TextView) findViewById(R.id.textview6);
                tv6.setText(title);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Request Error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request6);
    }
    String removeCdata(String data){
        data =data.replace("<![CDATA["," ");
        data =data.replace("]]>"," ");
        return data;




    }











    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

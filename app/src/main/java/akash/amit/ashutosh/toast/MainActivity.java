package akash.amit.ashutosh.toast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
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
        ImageView img= (ImageView) findViewById(R.id.imageV);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

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
        CardView cv= (CardView) findViewById(R.id.card_view);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,IBNLive.class);
                startActivity(intent);
            }
        });
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
        CardView cv2= (CardView) findViewById(R.id.card_view2);
        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,HindustanTimes.class);
                startActivity(intent);
            }
        });

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
        CardView cv3= (CardView) findViewById(R.id.card_view3);
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,indianExpress.class);
                startActivity(intent);
            }
        });

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
                if ((img).isEmpty()) {
                    iv4.setImageResource(R.drawable.a);
                } else{
                    Picasso.with(MainActivity.this).load(img).into(iv4);
                }
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
        CardView cv4= (CardView) findViewById(R.id.card_view4);
        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,IndiaToday.class);
                startActivity(intent);
            }
        });
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
        CardView cv5= (CardView) findViewById(R.id.card_view5);
        cv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,IndiaTvNews.class);
                startActivity(intent);
            }
        });
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
        CardView cv6= (CardView) findViewById(R.id.card_view6);
        cv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,OneIndiaNews.class);
                startActivity(intent);
            }
        });
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
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_refresh) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_1) {
            Intent intent=new Intent(MainActivity.this,IBNLive.class);
            startActivity(intent);

        } else if (id == R.id.nav_2) {
            Intent intent=new Intent(MainActivity.this,HindustanTimes.class);
            startActivity(intent);

        } else if (id == R.id.nav_3) {
            Intent intent=new Intent(MainActivity.this,indianExpress.class);
            startActivity(intent);

        } else if (id == R.id.nav_4) {
            Intent intent=new Intent(MainActivity.this,IndiaToday.class);
            startActivity(intent);

        } else if (id == R.id.nav_5) {
            Intent intent=new Intent(MainActivity.this,IndiaTvNews.class);
            startActivity(intent);

        } else if (id == R.id.nav_6) {
            Intent intent=new Intent(MainActivity.this,OneIndiaNews.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_7) {
            Intent i=new Intent(android.content.Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(android.content.Intent.EXTRA_SUBJECT,"Thanks for Sharing");
            i.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName()+"");
            startActivity(Intent.createChooser(i,"Share via"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

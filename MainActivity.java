package com.example.searchonfb;
import org.json.JSONArray;
import org.json.JSONException;

import org.json.JSONObject;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.FacebookSdk;





import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        if (id == R.id.nav_fav) {


            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    Intent inten;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_fav) {
            inten=new Intent(MainActivity.this,FavoriteActivity.class);

            AsynchronousTaskFav fav_task=new AsynchronousTaskFav();
            String key="hi";
            fav_task.execute(key);
        }
        else if(id== R.id.nav_aboutme){
            inten=new Intent(MainActivity.this,AboutActivity.class);
            startActivity(inten);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private class AsynchronousTaskFav extends AsyncTask<String,Void,JSONObject>
    {
        @Override
        protected JSONObject doInBackground(String... keyword) {
            JSONObject js=new JSONObject();

            return js;
        }



        @Override
        protected void onPostExecute(JSONObject result) {

            startActivity(inten);

        }
    }


    public static final String EXTRA_MESSAGE = "com.example.SearchonFB.MESSAGE";
    public void displayTable(View view) {
        // Do something in response to button
       i = new Intent(MainActivity.this, ResultActivity.class);
        EditText ed1 = (EditText) findViewById(R.id.keyword);
        if(!ed1.getText().toString().equals(" ") && !ed1.getText().toString().equals(""))
        {


            String keyword = ed1.getText().toString();
            AsynchronousTask task = new AsynchronousTask();
            task.execute(keyword);
            //i.putExtra(EXTRA_MESSAGE, message);


        }
        else {
            Toast.makeText(getApplicationContext(), "Enter Valid inputs", Toast.LENGTH_LONG).show();
        }


    }

    public void clearText(View view) {
        // Do something in response to button

        EditText ed1 = (EditText) findViewById(R.id.keyword);
        ed1.setText("");



    }


    Intent i;
    private class AsynchronousTask extends AsyncTask<String,Void,JSONObject>
    {
        @Override
        protected JSONObject doInBackground(String... keyword) {
            JSONObject data=new JSONObject();
            try {

                URL url  = new URL("http://sample-env.nhdgnefbsv.us-west-2.elasticbeanstalk.com/index.php?"+"keyword="+keyword[0]+"&type=Users");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                //InputStream in = new BufferedInputStream(con.getInputStream());
                //readStream(in);
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-length", "0");
                con.setUseCaches(false);
                con.setAllowUserInteraction(false);
                //con.setConnectTimeout(timeout);
                //con.setReadTimeout(timeout);
                con.connect();
                String jsonReply;
                boolean success;
                if(con.getResponseCode()==201 || con.getResponseCode()==200)
                {
                    success = true;
                    BufferedReader inp = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String inputLine;
                    StringBuffer json = new StringBuffer(1024);
                    while ((inputLine = inp.readLine()) != null)
                        json.append(inputLine).append("\n");
                    inp.close();
                    data = new JSONObject(json.toString());
                    // Do JSON handling here....
                }
                con.disconnect();
                i.putExtra(EXTRA_MESSAGE,data.toString());
                //////////////////////////////////////////////////////
                URL url2  = new URL("http://sample-env.nhdgnefbsv.us-west-2.elasticbeanstalk.com/index.php?"+"keyword="+keyword[0]+"&type=Pages");
                HttpURLConnection con2 = (HttpURLConnection) url2.openConnection();
                //InputStream in = new BufferedInputStream(con.getInputStream());
                //readStream(in);
                con2.setRequestMethod("GET");
                con2.setRequestProperty("Content-length", "0");
                con2.setUseCaches(false);
                con2.setAllowUserInteraction(false);
                //con.setConnectTimeout(timeout);
                //con.setReadTimeout(timeout);
                con2.connect();
                String jsonReply2;
                boolean success2;
                if(con2.getResponseCode()==201 || con2.getResponseCode()==200)
                {
                    success2 = true;
                    BufferedReader inp2 = new BufferedReader(new InputStreamReader(
                            con2.getInputStream()));
                    String inputLine2;
                    StringBuffer json2 = new StringBuffer(1024);
                    while ((inputLine2 = inp2.readLine()) != null)
                        json2.append(inputLine2).append("\n");
                    inp2.close();
                    data = new JSONObject(json2.toString());
                    // Do JSON handling here....
                }
                con2.disconnect();
                i.putExtra("Pages",data.toString());
                //////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////

                url  = new URL("http://sample-env.nhdgnefbsv.us-west-2.elasticbeanstalk.com/index.php?"+"keyword="+keyword[0]+"&type=Events");
                con = (HttpURLConnection) url.openConnection();
                //InputStream in = new BufferedInputStream(con.getInputStream());
                //readStream(in);
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-length", "0");
                con.setUseCaches(false);
                con.setAllowUserInteraction(false);
                //con.setConnectTimeout(timeout);
                //con.setReadTimeout(timeout);
                con.connect();

                if(con.getResponseCode()==201 || con.getResponseCode()==200)
                {
                    success = true;
                    BufferedReader inp = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String inputLine;
                    StringBuffer json = new StringBuffer(1024);
                    while ((inputLine = inp.readLine()) != null)
                        json.append(inputLine).append("\n");
                    inp.close();
                    data = new JSONObject(json.toString());
                    // Do JSON handling here....
                }
                con.disconnect();
                i.putExtra("Events",data.toString());
                //////////////////////////////////////////////////////
                /////////////////////////////////////////////////////
                url  = new URL("http://sample-env.nhdgnefbsv.us-west-2.elasticbeanstalk.com/index.php?"+"keyword="+keyword[0]+"&type=Places&latitude=34.0199122&longitude=-118.2864086");
                con = (HttpURLConnection) url.openConnection();
                //InputStream in = new BufferedInputStream(con.getInputStream());
                //readStream(in);
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-length", "0");
                con.setUseCaches(false);
                con.setAllowUserInteraction(false);
                //con.setConnectTimeout(timeout);
                //con.setReadTimeout(timeout);
                con.connect();

                if(con.getResponseCode()==201 || con.getResponseCode()==200)
                {
                    success = true;
                    BufferedReader inp = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String inputLine;
                    StringBuffer json = new StringBuffer(1024);
                    while ((inputLine = inp.readLine()) != null)
                        json.append(inputLine).append("\n");
                    inp.close();
                    data = new JSONObject(json.toString());
                    // Do JSON handling here....
                }
                con.disconnect();
                i.putExtra("Places",data.toString());
                ////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////

                url  = new URL("http://sample-env.nhdgnefbsv.us-west-2.elasticbeanstalk.com/index.php?"+"keyword="+keyword[0]+"&type=Groups");
                con = (HttpURLConnection) url.openConnection();
                //InputStream in = new BufferedInputStream(con.getInputStream());
                //readStream(in);
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-length", "0");
                con.setUseCaches(false);
                con.setAllowUserInteraction(false);
                //con.setConnectTimeout(timeout);
                //con.setReadTimeout(timeout);
                con.connect();

                if(con.getResponseCode()==201 || con.getResponseCode()==200)
                {
                    success = true;
                    BufferedReader inp = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String inputLine;
                    StringBuffer json = new StringBuffer(1024);
                    while ((inputLine = inp.readLine()) != null)
                        json.append(inputLine).append("\n");
                    inp.close();
                    data = new JSONObject(json.toString());
                    // Do JSON handling here....
                }
                con.disconnect();
                i.putExtra("Groups",data.toString());
                ///////////////////////////////////////////////////////

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {

            }

            return data;
        }



    @Override
    protected void onPostExecute(JSONObject result) {


        startActivity(i);
    }
    }

}

package com.example.searchonfb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FavoriteActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    //public HashMap<String,String> user;
    //public HashMap<String,String> page;
    //public HashMap<String,String> event;
    //public HashMap<String,String> place;
    //public HashMap<String,String> group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);





        //SharedPreferences.Editor editor = userpreferences.edit();
        //editor.;
        //editor.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            if(sectionNumber==1)
            {
                //SharedPreferences userpreferences= getSharedPreferences("Users", Context.MODE_PRIVATE);

            }

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            SharedPreferences userpreferences= this.getContext().getSharedPreferences("Users", Context.MODE_PRIVATE);
            HashMap<String, String>user= (HashMap<String, String>) userpreferences.getAll();

            SharedPreferences pagepreferences= this.getContext().getSharedPreferences("Pages", Context.MODE_PRIVATE);
            HashMap<String, String>page= (HashMap<String, String>) pagepreferences.getAll();

            SharedPreferences eventpreferences= this.getContext().getSharedPreferences("Events", Context.MODE_PRIVATE);
            HashMap<String, String>event= (HashMap<String, String>) pagepreferences.getAll();

            SharedPreferences placepreferences= this.getContext().getSharedPreferences("Places", Context.MODE_PRIVATE);
            HashMap<String, String>place= (HashMap<String, String>) placepreferences.getAll();

            SharedPreferences grouppreferences= this.getContext().getSharedPreferences("Groups", Context.MODE_PRIVATE);
            HashMap<String, String>group= (HashMap<String, String>) grouppreferences.getAll();

            View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            int option=getArguments().getInt(ARG_SECTION_NUMBER);


            if(option==1) {
                String[] url = new String[user.size()];
                String[] name = new String[user.size()];
                String typ = new String();
                String[] id = new String[user.size()];
                Iterator it = user.entrySet().iterator();
                int i=0;

                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    //System.out.println(pair.getKey() + " = " + pair.getValue());
                    //it.remove(); // avoids a ConcurrentModificationException
                    String[] val=pair.getValue().toString().split(" ");
                    url[i]=val[0];
                    name[i]=val[1];
                    //typ[i]=val[2];
                    id[i]=pair.getKey().toString();
                    i++;


                }

                typ="Users";
                MyAdapter adapter=new MyAdapter(this.getContext(),R.layout.layout,R.id.name_thing,name,url,id,typ);

                //View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
                ListView lv=(ListView)rootView.findViewById(R.id.contentView1);
                lv.setAdapter(adapter);
            }
            else if(option==2){
                String[] url = new String[page.size()];
                String[] name = new String[page.size()];
                String typ = new String();
                String[] id = new String[page.size()];
                Iterator it = page.entrySet().iterator();
                int i=0;

                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    //System.out.println(pair.getKey() + " = " + pair.getValue());
                    //it.remove(); // avoids a ConcurrentModificationException
                    String[] val=pair.getValue().toString().split(" ");
                    url[i]=val[0];
                    name[i]=val[1];
                    //typ[i]=val[2];
                    id[i]=pair.getKey().toString();
                    i++;


                }
                typ="Pages";

                MyAdapter adapter=new MyAdapter(this.getContext(),R.layout.layout,R.id.name_thing,name,url,id,typ);

                //View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
                ListView lv=(ListView)rootView.findViewById(R.id.contentView1);
                lv.setAdapter(adapter);
            }
            else if(option==3){
                String[] url = new String[event.size()];
                String[] name = new String[event.size()];
                String typ = new String();
                String[] id = new String[event.size()];
                Iterator it = event.entrySet().iterator();
                int i=0;

                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    //System.out.println(pair.getKey() + " = " + pair.getValue());
                    //it.remove(); // avoids a ConcurrentModificationException
                    String[] val=pair.getValue().toString().split(" ");
                    url[i]=val[0];
                    name[i]=val[1];
                    //typ[i]=val[2];
                    id[i]=pair.getKey().toString();
                    i++;


                }
                typ="Events";
                MyAdapter adapter=new MyAdapter(this.getContext(),R.layout.layout,R.id.name_thing,name,url,id,typ);

                //View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
                ListView lv=(ListView)rootView.findViewById(R.id.contentView1);
                lv.setAdapter(adapter);
            }
            else if(option==4){
                String[] url = new String[place.size()];
                String[] name = new String[place.size()];
                String typ = new String();
                String[] id = new String[place.size()];
                Iterator it = place.entrySet().iterator();
                int i=0;

                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    //System.out.println(pair.getKey() + " = " + pair.getValue());
                    //it.remove(); // avoids a ConcurrentModificationException
                    String[] val=pair.getValue().toString().split(" ");
                    url[i]=val[0];
                    name[i]=val[1];
                    //typ[i]=val[2];
                    id[i]=pair.getKey().toString();
                    i++;


                }
                typ="Places";
                MyAdapter adapter=new MyAdapter(this.getContext(),R.layout.layout,R.id.name_thing,name,url,id,typ);

                //View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
                ListView lv=(ListView)rootView.findViewById(R.id.contentView1);
                lv.setAdapter(adapter);
            }
            else if(option==5){
                String[] url = new String[group.size()];
                String[] name = new String[group.size()];
                String typ = new String();
                String[] id = new String[group.size()];
                Iterator it = group.entrySet().iterator();
                int i=0;

                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    //System.out.println(pair.getKey() + " = " + pair.getValue());
                    //it.remove(); // avoids a ConcurrentModificationException
                    String[] val=pair.getValue().toString().split(" ");
                    url[i]=val[0];
                    name[i]=val[1];
                    //typ[i]=val[2];
                    id[i]=pair.getKey().toString();
                    i++;


                }
                typ="Groups";
                MyAdapter adapter=new MyAdapter(this.getContext(),R.layout.layout,R.id.name_thing,name,url,id,typ);


                ListView lv=(ListView)rootView.findViewById(R.id.contentView1);
                lv.setAdapter(adapter);
            }



            return rootView;
        }
    }

    static class MyAdapter extends ArrayAdapter<String> {

        public String[] url1;
        public String[] id1;
        public Context con;
        public String typ1;
        public MyAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull String[] objects, String[] url, String[] id, String typ) {
            super(context, resource, textViewResourceId, objects);
            url1=url;
            id1=id;
            con=context;
            typ1=typ;
        }
        Intent j;

        private class AsynchronousTask extends AsyncTask<String,Void,JSONObject>
        {
            @Override
            protected JSONObject doInBackground(String... ident) {
                ///////////////Sending request to php
                JSONObject data1 = new JSONObject();
                try {

                    URL url1 = new URL("http://sample-env.nhdgnefbsv.us-west-2.elasticbeanstalk.com/index.php?id=" + ident[0]);
                    HttpURLConnection con4 = (HttpURLConnection) url1.openConnection();
                    //InputStream in = new BufferedInputStream(con.getInputStream());
                    //readStream(in);
                    con4.setRequestMethod("GET");
                    con4.setRequestProperty("Content-length", "0");
                    con4.setUseCaches(false);
                    con4.setAllowUserInteraction(false);
                    //con.setConnectTimeout(timeout);
                    //con.setReadTimeout(timeout);
                    con4.connect();
                    String jsonReply;
                    boolean success;
                    int code=con4.getResponseCode();
                    if (con4.getResponseCode() == 201 || con4.getResponseCode() == 200) {
                        success = true;
                        BufferedReader inp = new BufferedReader(new InputStreamReader(
                                con4.getInputStream()));
                        String inputLine;
                        StringBuffer json = new StringBuffer(1024);
                        while ((inputLine = inp.readLine()) != null)
                            json.append(inputLine).append("\n");
                        inp.close();
                        data1 = new JSONObject(json.toString());
                        // Do JSON handling here....
                    }
                    con4.disconnect();
                    j.putExtra("detailJSON", data1.toString());

                    //j.putExtra("store_type",Integer.toString(store_type));
                    j.putExtra("type_last_hope",ident[1]);

                    //int i=store_type;

                    /////////////////////////////////////


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return data1;
            }
            protected void onPostExecute(JSONObject result) {
                con.startActivity(j);
            }
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final View base=super.getView(position, convertView, parent);
            //TextView tv=(TextView)base.findViewById(R.id.section_label1);
            //store_type=Integer.parseInt(tv.getText().toString());
            ImageView iv=(ImageView) base.findViewById(R.id.profile_pic);
            Picasso.with(this.getContext()).load(url1[position]).into(iv);
            ImageView fav=(ImageView) base.findViewById(R.id.imageView2);
            TextView textView=(TextView)base.findViewById(R.id.textView2);
            textView.setText(typ1);
            //if(id1)
            SharedPreferences commonpreferences = con.getSharedPreferences("Common", Context.MODE_PRIVATE);
            String k="-1";

                fav.setImageResource(android.R.drawable.btn_star_big_on);

            ImageView detail=(ImageView) base.findViewById(R.id.imageView3);

            //////////////////////////onClick function
            detail.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    String[] ident = new String[2];
                    ident[0]=id1[position];
                    TextView textView=(TextView)base.findViewById(R.id.textView2);
                    ident[1]=textView.getText().toString();
                    j= new Intent(v.getContext(), DetailActivity.class);

                    AsynchronousTask task2 = new AsynchronousTask();
                    task2.execute(ident);

                }
            });
            //TextView tv=(TextView)base.findViewById(R.id.name_thing);
            //tv.setText(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));
            return base;
        }
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Users";
                case 1:
                    return "Pages";
                case 2:
                    return "Events";
                case 3:
                    return "Places";
                case 4:
                    return "Groups";
            }
            return null;
        }
    }
}

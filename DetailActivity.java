package com.example.searchonfb;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.example.searchonfb.R.id.container;

public class DetailActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    ShareDialog shareDialog;
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
    public String id_fav;
    public String url_fav;
    public String name_fav;
    public String type_fav;
    public static Intent inte;
/*
    public void favourite(View view)
    {
            System.out.print('h');
    }*/
    static class MyAdapter extends ArrayAdapter<String>{

        public Context con;
        public JSONObject jso;
        public String[] time1;
        public MyAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull String[] objects,JSONObject js,String[] time) {
            super(context, resource, textViewResourceId, objects);
            con=context;
            jso=js;
            time1=time;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View base=super.getView(position, convertView, parent);
            ImageView iv=(ImageView) base.findViewById(R.id.profile_pic);
            try {
                Picasso.with(this.getContext()).load(jso.getJSONObject("picture").getJSONObject("data").getString("url")).into(iv);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            TextView detail=(TextView) base.findViewById(R.id.time);
            //////////////////////////onClick function

            try {
                detail.setText(jso.getString("name")+time1[position]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return base;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //////////////////////////////facebook


        ////////////////////////////

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        //R.menu.menu_detail;

        //Menu nv=(Menu)findViewById(R.id.menu_id);
               //getMenuInflater().inflate(R.menu.menu_detail,nv);
        //MenuItem tool = (MenuItem) findViewById(R.id.action_settings);
        //MenuItem mi=nv.findItem(R.id.action_settings);
        //mi.setTitle("Remove from Favorites");

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        inte=getIntent();
        String j=inte.getStringExtra("detailJSON");
        try {
            JSONObject js=new JSONObject(j);
            //id_fav=js.getJSONObject("data").getString("id");
            id_fav=js.getString("id");
            name_fav=js.getString("name");
            url_fav=js.getJSONObject("picture").getJSONObject("data").getString("url");
            //R.l
            /*
            String type_int=inte.getStringExtra("store_type");
            if(type_int.equals("2"))
                type_fav="Users";
            else if(type_int.equals("3"))
                type_fav="Pages";
            else if(type_int.equals("4"))
                type_fav="Events";
            else if(type_int.equals("5"))
                type_fav="Places";
            else if(type_int.equals("6"))
                type_fav="Groups";
            */
            type_fav=inte.getStringExtra("type_last_hope");

            int i=0;
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem mi=menu.findItem(R.id.action_settings);
        SharedPreferences commonpreference=getSharedPreferences("Common",MODE_PRIVATE);
        if(commonpreference.contains(id_fav))
            mi.setTitle("Remove from Favorites");
        else
            mi.setTitle("Add to Favorites");
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
            if(item.getTitle().equals("Add to Favorites")) {
                SharedPreferences commonpreferences = getSharedPreferences("Common", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = commonpreferences.edit();

                String value1 = url_fav + " " + name_fav + " " + type_fav;


                editor1.putString(id_fav, value1);
                editor1.commit();
                if (type_fav.equals("Users")) {
                    SharedPreferences userpreferences = getSharedPreferences("Users", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = userpreferences.edit();

                    String value = url_fav + " " + name_fav + " " + type_fav;


                    editor.putString(id_fav, value);
                    editor.commit();
                } else if (type_fav.equals("Pages")) {
                    SharedPreferences pagepreferences = getSharedPreferences("Pages", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pagepreferences.edit();

                    String value = url_fav + " " + name_fav + " " + type_fav;


                    editor.putString(id_fav, value);
                    editor.commit();
                } else if (type_fav.equals("Events")) {
                    SharedPreferences eventpreferences = getSharedPreferences("Events", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = eventpreferences.edit();

                    String value = url_fav + " " + name_fav + " " + type_fav;


                    editor.putString(id_fav, value);
                    editor.commit();
                } else if (type_fav.equals("Places")) {
                    SharedPreferences placepreferences = getSharedPreferences("Places", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = placepreferences.edit();

                    String value = url_fav + " " + name_fav + " " + type_fav;


                    editor.putString(id_fav, value);
                    editor.commit();
                } else if (type_fav.equals("Groups")) {
                    SharedPreferences grouppreferences = getSharedPreferences("Groups", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = grouppreferences.edit();

                    String value = url_fav + " " + name_fav + " " + type_fav;


                    editor.putString(id_fav, value);
                    editor.commit();
                }

                Toast.makeText(getApplicationContext(), "Added to Favorites", Toast.LENGTH_LONG).show();
            }
            else
            {
                SharedPreferences commonpreferences = getSharedPreferences("Common", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = commonpreferences.edit();
                editor1.remove(id_fav);
                editor1.commit();

                if (type_fav.equals("Users")) {
                    SharedPreferences userpreferences = getSharedPreferences("Users", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = userpreferences.edit();

                    editor.remove(id_fav);
                    editor.commit();
                } else if (type_fav.equals("Pages")) {
                    SharedPreferences pagepreferences = getSharedPreferences("Pages", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pagepreferences.edit();

                    editor.remove(id_fav);
                    editor.commit();
                } else if (type_fav.equals("Events")) {
                    SharedPreferences eventpreferences = getSharedPreferences("Events", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = eventpreferences.edit();

                    editor.remove(id_fav);
                    editor.commit();
                } else if (type_fav.equals("Places")) {
                    SharedPreferences placepreferences = getSharedPreferences("Places", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = placepreferences.edit();

                    editor.remove(id_fav);
                    editor.commit();
                } else if (type_fav.equals("Groups")) {
                    SharedPreferences grouppreferences = getSharedPreferences("Groups", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = grouppreferences.edit();

                    editor.remove(id_fav);
                    editor.commit();
                }


                Toast.makeText(getApplicationContext(), "Removed from Favorites", Toast.LENGTH_LONG).show();
            }
           //SharedPreferences.Editor editor = sharedpreferences.edit();
            //editor.putString("Name", "Rachit");
            //SharedPreferences events=getSharedPreferences("Events", Context.MODE_PRIVATE);
            //String l="-1";
            //String value=events.getString(id_fav,l);
            //editor.commit();
            return true;
        }
        else if (id == R.id.share) {
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                        .build();
                shareDialog.show(linkContent);
            }

        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
            args.putString("alb_post",inte.getStringExtra("detailJSON"));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            String message=getArguments().getString("alb_post");

            int num=getArguments().getInt(ARG_SECTION_NUMBER);
            if(num==1)
            {rootView = inflater.inflate(R.layout.fragment_detail, container, false);
                try {
                    JSONObject json = new JSONObject(message);
                    if(json.getJSONObject("albums")!=null) {

                        JSONArray array = json.getJSONObject("albums").getJSONArray("data");
                        int len = array.length();

                        //JSONObject obj=json.getJSONObject("data");
                        String[] name = new String[len];
                        for (int i = 0; i < len; i++) {
                            name[i] = array.getJSONObject(i).getString("name");
                            //Long l=array.getJSONObject(i).getLong("id");
                        }


                        // declare array List for all headers in list
                        ArrayList<String> headersArrayList = new ArrayList<String>();
                        // Declare Hash map for all headers and their corresponding values
                        HashMap<String, ArrayList<String>> childArrayList = new HashMap<String, ArrayList<String>>();


                        int leng = array.length();
                        for (int i = 0; i < leng; i++) {
                            headersArrayList.add(array.getJSONObject(i).getString("name"));
                            ArrayList<String> images = new ArrayList<String>();
                            JSONArray ar = array.getJSONObject(i).getJSONObject("photos").getJSONArray("data");
                            images.add(ar.getJSONObject(0).getString("picture"));
                            images.add(ar.getJSONObject(1).getString("picture"));
                            childArrayList.put(array.getJSONObject(i).getString("name"), images);
                        }


                        ListAdapterExpandible adapter = new ListAdapterExpandible(getContext(), headersArrayList,
                                childArrayList);
                        //R.layout.layout
                        //Toolbar toolbar = (Taoolbr) this.findViewById(R.id.button_next);
                        // bt.

                        ExpandableListView lv = (ExpandableListView) rootView.findViewById(R.id.expandableView);
                        lv.setAdapter(adapter);
                        lv.setFocusable(false);
                        //ListView lv2 = (ListView) rootView.findViewById(R.id.detail_listview);
                        //lv2.setFocusable(false);
                        lv.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                            @Override
                            public void onGroupCollapse(int groupPosition) {
                                // TODO: Do your stuff

                            }
                        });

                        lv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                            @Override
                            public void onGroupExpand(int groupPosition) {
                                // TODO: Do your stuff

                                // Toast.makeText(getContext().getApplicationContext(), "Child is Expanded", Toast.LENGTH_LONG).show();
                            }
                        });

                        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                            @Override
                            public boolean onChildClick(ExpandableListView parent, View v,
                                                        int groupPosition, int childPosition, long id) {
                                // Toast.makeText(getContext().getApplicationContext(), "Child is clicked", Toast.LENGTH_LONG).show();
                                return false;
                            }
                        });

                        lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                            @Override
                            public boolean onGroupClick(ExpandableListView parent, View v,
                                                        int groupPosition, long id) {
                                // TODO: Do your stuff
                                //Toast.makeText(getContext().getApplicationContext(), "Group is Clicked", Toast.LENGTH_LONG).show();
                                return false;
                            }
                        });
                    }
                    else{

                        //t1.setFontFeatureSettings();

                    }

                } catch (JSONException e) {
                    TextView t1=(TextView)rootView.findViewById(R.id.err);
                    //String tex="No Albums";
                    t1.setText("No Albums");
                    e.printStackTrace();
                }
            }
            else
            { rootView= inflater.inflate(R.layout.fragment_detail1, container, false);
                JSONObject json = null;
                try {
                    json = new JSONObject(message);
                    if(json.getJSONObject("albums")!=null) {


                        JSONArray array = json.getJSONObject("posts").getJSONArray("data");
                        int len = array.length();

                        //JSONObject obj=json.getJSONObject("data");
                        String[] name = new String[len];
                        for (int i = 0; i < len; i++) {
                            name[i] = array.getJSONObject(i).getString("message");
                            //Long l=array.getJSONObject(i).getLong("id");
                        }
                        String[] time = new String[len];
                        for (int i = 0; i < len; i++) {

                        /*
                        String t= array.getJSONObject(i).getString("created_time");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                        try {
                            Date d = format.parse(t);
                            SimpleDateFormat eFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            String tim = eFormat.format(d)+"+0000";
                            */


                            time[i] = array.getJSONObject(i).getString("created_time");
                            ;
                        /*} catch (ParseException e) {
                            e.printStackTrace();
                        }*/


                            //Long l=array.getJSONObject(i).getLong("id");
                        }

                        MyAdapter adapter = new MyAdapter(this.getContext(), R.layout.post, R.id.message, name, json, time);
                        //IMP             //ListView lv=(ListView)rootView.findViewById(R.id.detail_listview);
                        //IMP             //lv.setAdapter(adapter);
                        ListView lv = (ListView) rootView.findViewById(R.id.detail_listview);
                        lv.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    TextView t1=(TextView)rootView.findViewById(R.id.err);
                    //String tex="No Albums";
                   // t1.setText("No Posts");

                    e.printStackTrace();
                }

            }

            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
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
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Albums";
                case 1:
                    return "Posts";

            }
            return null;
        }
    }
}

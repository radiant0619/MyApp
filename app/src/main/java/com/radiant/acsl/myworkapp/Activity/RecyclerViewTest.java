package com.radiant.acsl.myworkapp.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.radiant.acsl.myworkapp.Adapters.OnItemClickListener;
import com.radiant.acsl.myworkapp.Adapters.OnLoadMoreListener;
import com.radiant.acsl.myworkapp.Other.FeedItem;
import com.radiant.acsl.myworkapp.Other.FormsList;
import com.radiant.acsl.myworkapp.R;
import com.radiant.acsl.myworkapp.Adapters.RecycleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewTest extends AppCompatActivity {
    private RecyclerView myRecyclerView;
    private RecycleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ProgressBar progressBar;
    private List<FeedItem> feedList;
    private List<FormsList> formList;
    private static final String TAG = "RecyclerViewExample";

    //    http://stacktips.com/tutorials/android/android-recyclerview-example
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_test);
        myRecyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mLayout = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(mLayout);
        myRecyclerView.setHasFixedSize(true);

        String url = "http://45.118.182.50:8088/static/file_name.json";
        String url1 = "http://stacktips.com/?json=get_category_posts&slug=news&count=30";
        String vType = "Own";

        new DownloadTask().execute(url, url1, vType);

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                feedList.add(null);
                mAdapter.notifyItemInserted(feedList.size() - 1);

                feedList.remove(feedList.size() - 1);
                mAdapter.notifyItemRemoved(feedList.size() - 1);


                mAdapter.setFeedItemList(feedList.subList(6, 10));
                mAdapter.notifyDataSetChanged();
                mAdapter.setLoaded();
            }
        });

    }

    public class DownloadTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;

            HttpURLConnection urlConnection;
            try {

                URL url = new URL(params[0]);
                if (params[2] == "Own")
                    url = new URL(params[1]);

                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                if (statusCode == 200 && params[2] != "Own") {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful

                } else if (statusCode == 200 && params[2] == "Own") {
                    parseResult(new InputStreamReader(urlConnection.getInputStream()));
                } else {
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }

            return result;

        }

        private void parseResult(String response) {

            try {
                JSONObject jsonObj = new JSONObject(response);
                JSONArray posts = jsonObj.optJSONArray("posts");
                feedList = new ArrayList<>();
                for (int i = 0; i < posts.length(); i++) {
                    JSONObject post = posts.optJSONObject(i);
                    FeedItem item = new FeedItem();
                    item.setmTitle(post.optString("title"));
                    item.setmThumbnail(post.optString("thumbnail"));
                    feedList.add(item);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void parseResult(InputStreamReader in) {
            formList = new ArrayList<>();

            JsonReader jsonReader = new JsonReader(in);

            try {
                jsonReader.beginArray();

                while (jsonReader.hasNext()) {
                    jsonReader.beginObject();
                    String name = jsonReader.nextName();
                    formList=new ArrayList<>();
                    FormsList list = new FormsList();
                    if (name.equals("file_name")) {
                        list.setFormDesc(jsonReader.nextString());
                    } else if (name.equals("file_path")) {
                        list.setFormDesc(jsonReader.nextString());
                    } else {
                        jsonReader.skipValue();
                    }
                    formList.add(list);
                    jsonReader.endObject();

                }

                jsonReader.endArray();
                jsonReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);
            if (result == 1) {

                mAdapter = new RecycleAdapter(RecyclerViewTest.this, feedList, myRecyclerView);
//                mAdapter = new RecycleAdapter(RecyclerViewTest.this, formList, myRecyclerView);
                mAdapter.setOnItemClickListner(new OnItemClickListener() {
                    @Override
                    public void onItemClick(FeedItem item) {
                        Toast.makeText(RecyclerViewTest.this, item.getmTitle(), Toast.LENGTH_LONG).show();
                    }
                });
                myRecyclerView.setAdapter(mAdapter);


            } else {
                Toast.makeText(RecyclerViewTest.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

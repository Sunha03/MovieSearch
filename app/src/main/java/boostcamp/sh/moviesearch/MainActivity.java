package boostcamp.sh.moviesearch;

import android.graphics.Canvas;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

public class MainActivity extends AppCompatActivity {
    EditText edit_movie_name;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;

    ArrayList<MovieInfo> movieInfoArrayList = null;

    String strUrl = null;
    String movie_name = null;

    JSONArray movie = null;
    String myJSON = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_movie_name = (EditText)findViewById(R.id.edit_movie_name);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        movieInfoArrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_search) {
            //Get Search Keyword
            movie_name = edit_movie_name.getText().toString();
            //Set URL
            strUrl = "https://openapi.naver.com/v1/search/movie.json?query=" + movie_name;
            //Search
            getMovieInfo(strUrl);
        }
    }

    //Get Movie Info from Naver open API
    private void getMovieInfo(String url) {
        class getDataJSON extends AsyncTask<String, Integer, String> {

            @Override
            protected String doInBackground(String... urls) {
                String strUrl = urls[0];

                try {
                    URL url = new URL(strUrl);

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("X-Naver-Client-Id", getString(R.string.client_Id));
                    con.setRequestProperty("X-Naver-Client-Secret", getString(R.string.client_secret));

                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;

                    while ((json = br.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    br.close();
                    con.disconnect();
                    //System.out.println(sb.toString());            //Print JSON

                    return sb.toString().trim();
                } catch (MalformedURLException e) {
                    return e.getMessage();
                } catch (ProtocolException e) {
                    return e.getMessage();
                } catch (IOException e) {
                    return e.getMessage();
                }
            }

            protected void onPostExecute(String result) {
                myJSON = result;

                parseJSON();

                //Set recyclerAdapter
                RecyclerAdapter adapter = new RecyclerAdapter(movieInfoArrayList, getApplicationContext());
                recyclerView.setAdapter(adapter);
            }
        }

        getDataJSON g = new getDataJSON();
        g.execute(strUrl);
    }

    private void parseJSON() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            movie = jsonObj.getJSONArray("items");

            for(int i=0;i<movie.length();i++) {
                JSONObject obj = movie.getJSONObject(i);

                String title = obj.optString("title");
                //remove <b>, </b> tag
                title = title.replace("<b>", "");
                title = title.replace("</b>", "");
                String image = obj.optString("image");
                float userRating = (float)obj.optDouble("userRating");
                int pubDate = obj.optInt("pubDate");
                String director = obj.optString("director");
                String actor = obj.optString("actor");
                String link = obj.optString("link");

                movieInfoArrayList.add(new MovieInfo(title, image, userRating, pubDate, director, actor, link));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

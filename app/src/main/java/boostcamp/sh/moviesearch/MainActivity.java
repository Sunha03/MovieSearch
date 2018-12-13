package boostcamp.sh.moviesearch;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText edit_movie_name;

    String strUrl = null;
    String movie_name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_movie_name = (EditText) findViewById(R.id.edit_movie_name);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_search) {
            movie_name = edit_movie_name.getText().toString();

            strUrl = "https://openapi.naver.com/v1/search/movie.json?query=" + movie_name;

            getMovieInfo(strUrl);
        }
    }

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
                    System.out.println(sb.toString());

                    return sb.toString().trim();
                } catch (MalformedURLException e) {
                    return e.getMessage();
                } catch (ProtocolException e) {
                    return e.getMessage();
                } catch (IOException e) {
                    return e.getMessage();
                }
            }
        }

        getDataJSON g = new getDataJSON();
        g.execute(strUrl);
    }
}

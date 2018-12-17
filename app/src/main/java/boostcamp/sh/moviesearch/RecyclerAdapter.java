package boostcamp.sh.moviesearch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MovieInfo> movieInfoArrayList;
    Context context;
    Bitmap bitmap = null;

    RecyclerAdapter(ArrayList<MovieInfo> movieInfoArrayList, Context context) {
        this.movieInfoArrayList = movieInfoArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int pos = position;

        RecyclerViewHolder viewHolder = (RecyclerViewHolder)holder;
        final String uri = movieInfoArrayList.get(position).link;

        viewHolder.tv_title.setText(movieInfoArrayList.get(position).title);

        //Movie Image
        Thread imgThread = new Thread() {
            public void run() {
                try {
                    URL imgUrl = new URL(movieInfoArrayList.get(pos).image);
                    HttpURLConnection con = (HttpURLConnection)imgUrl.openConnection();
                    con.setDoInput(true);
                    con.connect();

                    InputStream inputStream = con.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        imgThread.start();
        try {
            imgThread.join();
            viewHolder.img_image.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        viewHolder.rb_userRating.setRating(movieInfoArrayList.get(position).userRating/2);
        viewHolder.tv_pubDate.setText(String.valueOf(movieInfoArrayList.get(position).pubDate));
        viewHolder.tv_director.setText(movieInfoArrayList.get(position).director);
        viewHolder.tv_actor.setText(movieInfoArrayList.get(position).actor);
        //Row Click
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieInfoArrayList.size();
    }
}
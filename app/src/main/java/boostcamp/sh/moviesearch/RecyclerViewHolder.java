package boostcamp.sh.moviesearch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView tv_title;
    ImageView img_image;
    RatingBar rb_userRating;
    TextView tv_pubDate;
    TextView tv_director;
    TextView tv_actor;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        tv_title = itemView.findViewById(R.id.tv_title);
        img_image = itemView.findViewById(R.id.img_image);
        rb_userRating = itemView.findViewById(R.id.rb_userRating);
        tv_pubDate = itemView.findViewById(R.id.tv_pubDate);
        tv_director = itemView.findViewById(R.id.tv_director);
        tv_actor = itemView.findViewById(R.id.tv_actor);
    }
}

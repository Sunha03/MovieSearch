package boostcamp.sh.moviesearch;

public class MovieInfo {
    public String title;
    public String image;
    public float userRating;
    public int pubDate;
    public String director;
    public String actor;
    public String link;

    public MovieInfo(String title, String image, float userRating, int pubDate, String director, String actor, String link) {
        this.title = title;
        this.image = image;
        this.userRating = userRating;
        this.pubDate = pubDate;
        this.director = director;
        this.actor = actor;
        this.link = link;
    }
}

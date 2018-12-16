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

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public float getUserRating() {
        return userRating;
    }

    public int getPubDate() {
        return pubDate;
    }

    public String getDirector() {
        return director;
    }

    public String getActor() {
        return actor;
    }

    public String getLink() {
        return link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public void setPubDate(int pubDate) {
        this.pubDate = pubDate;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

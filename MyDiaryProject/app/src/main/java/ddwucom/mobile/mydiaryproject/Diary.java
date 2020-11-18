package ddwucom.mobile.mydiaryproject;

public class Diary implements java.io.Serializable {
    long _id;
    String title;
    String content;
    String picture;
    String place;
    String weather;
    String feeling;
    String category;
    String date;
    String rating;


    public Diary(String date, String title, String content, String picture, String place, String weather, String feeling, String category, String rating) {
        this.date = date;
        this.title = title;
        this.content = content;
        this.picture = picture;
        this.place = place;
        this.weather = weather;
        this.feeling = feeling;
        this.category = category;
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Diary(long _id, String date, String title, String content, String picture, String place, String weather, String feeling, String category, String rating) {
        this._id = _id;
        this.date = date;
        this.title = title;
        this.content = content;
        this.picture = picture;
        this.place = place;
        this.weather = weather;
        this.feeling = feeling;
        this.category = category;
        this.rating = rating;
    }

    public long get_id() {
        return _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}


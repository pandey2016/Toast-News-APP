package akash.amit.ashutosh.toast;

import java.io.Serializable;

/**
 * Created by AKASH on 8/24/2017.
 */
public class NewsItem{
    String title;
    String link;
    String imgpath;
    String description;
    String date;

    @Override
    public String toString() {
        return title;
    }

}

package dataClass;

import java.util.ArrayList;

public class SearchRequest {
    public int userID;
    public String title;
    public String category;
    public ArrayList<String> tags;
    public int[] time;
    public String description;

    public boolean match(Post post)
    {
        return true;
    }
}

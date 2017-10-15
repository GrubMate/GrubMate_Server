package dataClass;

public class SearchRequest {
    public int userID;
    public String title;
    public String category;
    public String[] tags;
    public int[] time;
    public String description;

    public boolean match(Post post)
    {
        return true;
    }
}

package dataClass;

import java.util.ArrayList;

public class SearchRequest {
    public int userID;
    public String keyword;
    public String category;
    public Integer[] time;
    public String timePeriod; //temp
    public Boolean[] allergy;

    public boolean match(Post post)
    {
        keyword = keyword.toLowerCase();
        category = category.toLowerCase();
        boolean keywordMatch = false;
        if(post.title.toLowerCase().contains(keyword))
        {
            keywordMatch = true;
        }
        else
        {
            for(String tag : post.tags)
            {
                if(tag.toLowerCase().contains(keyword))
                {
                    keywordMatch = true;
                    break;
                }
            }
        }
        boolean categoryMatch = (category=="") || category.equals(post.category.toLowerCase());
        //boolean timeMatch = time[0]<= post.timePeriod[0] && time[1]>=post.timePeriod[1];
        boolean timeMatch = true;
        boolean allergyMatch = true;
        //TODO
        /*
        for(int i = 0; i < allergy.length;i++)
        {
            if(!allergy[i]&&post.allergyInfo[i])
            {

            }
        }
        */
        return keywordMatch && categoryMatch && timeMatch && allergyMatch;
    }

}

package dataClass;

import java.util.ArrayList;

public class SearchRequest {
    public int userID;
    public String keyword;
    public String category;
    public String time;
    public String timePeriod; //temp
    public Boolean[] allergy;

    public boolean match(Post post) {

        if (post.category == null || post.timePeriod == null || post.title == null) {
            return false;
        }

        boolean keywordMatch = false;
        if (keyword != null) {
            keyword = keyword.toLowerCase();
            if(post.title.toLowerCase().contains(keyword)) {
                keywordMatch = true;
            }
            else {
                for(String tag : post.tags) {
                    if(tag.toLowerCase().contains(keyword)){
                        keywordMatch = true;
                        break;
                    }
                }
            }
        }
        else {
            keywordMatch = true;
        }



        boolean categoryMatch = (category == null) || category.toLowerCase().equals(post.category.toLowerCase());

        boolean timeMatch = (timePeriod == null) || timePeriod.toLowerCase().equals(post.timePeriod.toLowerCase());

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
//        System.out.println(timePeriod == null? " " : timePeriod.toLowerCase());
//        System.out.println(post.timePeriod.toLowerCase());
//        System.out.println(keywordMatch?"match":"no");
//        System.out.println(categoryMatch?"match":"no");
//        System.out.println(post.isActive?"match":"no");
        return keywordMatch && categoryMatch && timeMatch && allergyMatch && post.isActive && (post.leftQuantity>0);
    }

}

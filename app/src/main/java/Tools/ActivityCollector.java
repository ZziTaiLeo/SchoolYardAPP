package Tools;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {  //活动管理器

    public static List<AppCompatActivity> activities = new ArrayList<>();

    public static void addActivity(AppCompatActivity activity)
    {
        activities.add(activity);
    }

    public static void removeActivity(AppCompatActivity activity)
    {
        activities.remove(activity);
    }

    public static void finishAll()
    {
        for(AppCompatActivity activity:activities)
        {
            if(!activity.isFinishing())
            {
                activity.finish();
            }
        }
        activities.clear();
    }




}

package xappox.com.shoppinglist;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DataManager {

    private Context context;
    private static final String StoredStringsKey = "com.xappox.shoppinglist.storedstringskey";
    private static final String PreferencesLocation = "com.xappox.shoppinglist";

    public DataManager(Context c){
        context = c;
    }

    public ArrayList<String> getStoredStrings(){
        SharedPreferences preferences = context.getSharedPreferences(PreferencesLocation, Context.MODE_PRIVATE);
        Set<String> stringSet = preferences.getStringSet(StoredStringsKey, Collections.<String>emptySet());
        return new ArrayList<>(stringSet);
    }

    public void setStoredStrings(ArrayList<String> strings){
        SharedPreferences preferences = context.getSharedPreferences(PreferencesLocation, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> stringSet = new HashSet<>(strings);
        System.out.println("String Set = " + stringSet);
        editor.putStringSet(StoredStringsKey, stringSet);
        editor.apply();
    }
}

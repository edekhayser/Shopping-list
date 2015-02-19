package xappox.com.shoppinglist;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private CardScrollView mCardScroller;

    private View mView;

    private static final int SPEECH_ADD_ITEM = 1;
    private static final int SPEECH_REMOVE_ITEM = 2;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);

        mView = buildView();

        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardScrollAdapter() {
            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public Object getItem(int position) {
                return mView;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return mView;
            }

            @Override
            public int getPosition(Object item) {
                if (mView.equals(item)) {
                    return 0;
                }
                return AdapterView.INVALID_POSITION;
            }
        });
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Plays disallowed sound to indicate that TAP actions are not supported.
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(Sounds.DISALLOWED);
            }
        });
        setContentView(mCardScroller);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }
    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }
    private View buildView() {
        CardBuilder card = new CardBuilder(this, CardBuilder.Layout.TEXT);
        DataManager dataManager = new DataManager(getBaseContext());
        ArrayList<String> strings = dataManager.getStoredStrings();
        System.out.println(strings);
        StringBuilder builder = new StringBuilder();
        if (strings.size() == 0){
            builder.append("No Items!");
        } else {
            for (String s : strings) {
                builder.append("- " + s + "\n");
            }
        }
        card.setText(builder.toString());
        return card.getView();
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS){
            getMenuInflater().inflate(R.menu.activity_menu, menu);
            return true;
        }
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            switch (item.getItemId()) {
                case R.id.add_menu_item:
                    // handle add activity_menu item
                    startActivityForResult(i, SPEECH_ADD_ITEM);
                    break;
                case R.id.remove_menu_item:
                    // handle remove activity_menu item
                    startActivityForResult(i, SPEECH_REMOVE_ITEM);
                    break;
                default:
                    return true;
            }
            return true;
        }
        // Good practice to pass through to super if not handled
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_ADD_ITEM && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            System.out.println(spokenText);
            // Do something with spokenText.
            DataManager dataManager = new DataManager(getBaseContext());
            ArrayList<String> storedStrings = dataManager.getStoredStrings();
            storedStrings.add(spokenText);
            dataManager.setStoredStrings(storedStrings);
            mView = buildView();
            mCardScroller.getAdapter().notifyDataSetChanged();
        } else if (requestCode == SPEECH_REMOVE_ITEM && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            // Do something with spokenText.
            DataManager dataManager = new DataManager(getBaseContext());
            List<String> storedStrings = dataManager.getStoredStrings();
            if (storedStrings.contains(spokenText)){
                storedStrings.remove(spokenText);
                dataManager.setStoredStrings(new ArrayList<>(storedStrings));
            }
            mView = buildView();
            mCardScroller.getAdapter().notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}

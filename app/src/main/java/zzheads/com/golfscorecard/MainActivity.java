package zzheads.com.golfscorecard;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ListActivity {

    public static final int HOLES_NUMBER = 18;
    public static final String PREFS_FILENAME = "com.zzheads.golfscorecard.prefs";
    public static final String KEY_STROKECOUNT = "key_strokecount";

    private Hole[] mHoles = new Hole[HOLES_NUMBER];
    private ListAdapter mListAdapter;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPrefs = getSharedPreferences(PREFS_FILENAME, MODE_PRIVATE);
        mEditor = mPrefs.edit();

        // Initialize array of holes
        int strokes = 0;
        for (int i = 0; i < mHoles.length; i++) {
            strokes = mPrefs.getInt(KEY_STROKECOUNT+i, 0);
            mHoles[i] = new Hole("Hole " + (i+1) + " :", strokes);
        }

        
        mListAdapter = new ListAdapter(this, mHoles);
        setListAdapter(mListAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        for (int i=0;i<mHoles.length;i++) {
            mEditor.putInt(KEY_STROKECOUNT+i,mHoles[i].getStrokeCount());
        }
        mEditor.apply();
    }

    public void Load () {
        for (int i=0;i<mHoles.length;i++) {
            mHoles[i].setStrokeCount(mPrefs.getInt(String.format("Hole#%d", i), 0));
        }
    }

    public void Save () {
        for (int i=0;i<mHoles.length;i++) {
            mPrefs.edit().putInt(String.format("Hole#%d", i), mHoles[i].getStrokeCount());
            mPrefs.edit().apply();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear_strokes) {
            mEditor.clear();
            mEditor.apply();

            for (Hole hole: mHoles) {
                hole.setStrokeCount(0);
            }
            mListAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

package android.wk.com.kejarsubmission2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import android.wk.com.kejarsubmission2.fragments.MoviesAPIMenuFragment;
import android.wk.com.kejarsubmission2.fragments.TVShowsAPIMenuFragment;

public class MainActivity extends AppCompatActivity{

    Fragment fragment;
    public static String CURRENT_FRAGMENT = "current_fragment";
    private Context context = this;
    private Boolean onDoublePress = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.movies_api_nav:
                    fragment = new MoviesAPIMenuFragment();
                    loadFragment(fragment, "Movies");
                    return true;
                case R.id.tv_retrofit_nav:
                    fragment = new TVShowsAPIMenuFragment();
                    loadFragment(fragment, "TVShows");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setElevation(0);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null){
            fragment = new MoviesAPIMenuFragment();
            loadFragment(fragment, "Movies");
        }else{
            fragment = getSupportFragmentManager().getFragment(savedInstanceState,"fragment");
            loadFragment(fragment, "TVShows");
        }
    }

    void loadFragment(Fragment fragment, String KEY){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout,fragment, KEY)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Integer id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.language_setting:
                intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
            case R.id.fav:
                intent = new Intent(context, FavoriteActivity.class);
                startActivity(intent);
                break;
            case R.id.search_bar:
                intent = new Intent(context, SearchActivity.class);

                MoviesAPIMenuFragment myFragment = (MoviesAPIMenuFragment) getSupportFragmentManager().findFragmentByTag("Movies");
                TVShowsAPIMenuFragment myTVFragment = (TVShowsAPIMenuFragment) getSupportFragmentManager().findFragmentByTag("TVShows");
                if (myFragment != null && myFragment.isVisible()){
                    intent.putExtra(CURRENT_FRAGMENT,0);
                }else if (myTVFragment != null && myTVFragment.isVisible()){
                    intent.putExtra(CURRENT_FRAGMENT, 1);
                }

                startActivity(intent);
                break;
            case R.id.reminder_settings:
                intent = new Intent(context, ReminderSettingsActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState,"fragment",fragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (onDoublePress) {
            super.onBackPressed();
            return;
        }

        this.onDoublePress = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                onDoublePress=false;
            }
        }, 2000);
    }
}

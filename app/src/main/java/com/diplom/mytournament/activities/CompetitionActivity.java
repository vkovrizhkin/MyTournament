package com.diplom.mytournament.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.diplom.mytournament.PlayersDialogFragment;
import com.diplom.mytournament.PlayersDialogFragment.PlayersInterface;
import com.diplom.mytournament.R;
import com.diplom.mytournament.fragments.details_redact.MatchDetailFragment;
import com.diplom.mytournament.fragments.drawer.MatchesFragment;
import com.diplom.mytournament.fragments.drawer.StandingsFragment;
import com.diplom.mytournament.fragments.drawer.TeamsFragment;
import com.diplom.mytournament.models.Player;

public class CompetitionActivity extends AppCompatActivity
        implements PlayersInterface, NavigationView.OnNavigationItemSelectedListener {

    private int competitionId;

    private Player currentPlayer;

    public int getCompetitionId() {
        return competitionId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        competitionId = intent.getIntExtra("competitionId", 0);
        setContentView(R.layout.activity_competition);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TeamsFragment teamsFragment = new TeamsFragment(competitionId);

        getSupportFragmentManager().beginTransaction().add(R.id.competition_frame_layout, teamsFragment)
                .commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.competition, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displayView(id);

     /* if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void displayView(int viewId) {
        Fragment fragment = null;

        switch (viewId) {
            case R.id.nav_matches:
                fragment = new MatchesFragment(competitionId);
                break;
            case R.id.nav_standings:
                fragment = new StandingsFragment(competitionId);
                break;

            default:
                fragment = new TeamsFragment(competitionId);
        }

        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.competition_frame_layout, fragment).addToBackStack(null).commit();
        }
    }

    @Override
    public void dialogOK(Player player, MatchDetailFragment matchDetailFragment) {
        matchDetailFragment.setCurrentPlayer(player);
        currentPlayer = player;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}

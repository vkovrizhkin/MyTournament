package com.diplom.mytournament.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.diplom.mytournament.MyTournamentQueryHelper;
import com.diplom.mytournament.fragments.PlayersDialogFragment.PlayersInterface;
import com.diplom.mytournament.R;
import com.diplom.mytournament.fragments.details_redact.MatchDetailFragment;
import com.diplom.mytournament.fragments.drawer.MatchesFragment;
import com.diplom.mytournament.fragments.drawer.StandingsFragment;
import com.diplom.mytournament.fragments.drawer.TeamsFragment;
import com.diplom.mytournament.models.Competition;
import com.diplom.mytournament.models.Player;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CompetitionActivity extends AppCompatActivity
        implements PlayersInterface, NavigationView.OnNavigationItemSelectedListener {

    private long competitionId;

    private Toolbar toolbar;

    private Player currentPlayer;

    public long getCompetitionId() {
        return competitionId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        competitionId = intent.getLongExtra("competitionId", 0);
        setContentView(R.layout.activity_competition);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        MyTournamentQueryHelper qh = new MyTournamentQueryHelper(this);


       // Competition competition = qh.getCompetitionById(competitionId);



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        ImageView comp_logo = (ImageView) hView.findViewById(R.id.imageView);
        TextView title = (TextView)hView.findViewById(R.id.nav_title);
       // title.setText(competition.getName());
/*        if (competition.getLogoIdResource()!=null){
            Bitmap img = null;
            Uri uri = Uri.parse(competition.getLogoIdResource());

            try {
                img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            comp_logo.setImageBitmap(img);
        } else {
            comp_logo.setImageResource(R.drawable.ic_menu_camera);
        }*/

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
        getMenuInflater().inflate(R.menu.competition, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        displayView(id);
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
        matchDetailFragment.executeEventAdding(player);
        currentPlayer = player;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setTitle(String title){
        toolbar.setTitle(title);
    }
}

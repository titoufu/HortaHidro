package com.malob.hortafire;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  acrescentando toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  adicionando drawer toogle
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // implementando o NavigationView
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Fragment fragment = new FragmentDeAbertura();
        setTitle("Malob - Hidroponia");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, fragment);
        ft.commit();


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Intent intent = new Intent();
        switch (id) {
            case R.id.nav_semear:
                setTitle("Data do Plantio (Fase Escura)");
                fragment = new FragmentSemear();
                break;
            case R.id.nav_germinar:
                setTitle("Data da Germinação (Fase Clara)");
                fragment = new FragmentGerminar();
                break;
            case R.id.nav_adolescer:
                setTitle("Data do Berçário");
                fragment = new FragmentAdolescer();
                break;
            case R.id.nav_engordar:
                setTitle("Data da Engorda");
                fragment = new FragmentEngordar();
                break;

            case R.id.nav_diasEngorda:
                intent = new Intent(this, DiasNaEngorda.class);
                break;
            case R.id.nav_grafico:
                intent = new Intent(this, GraficoIndividualizado.class);
                break;
            case R.id.nav_dataEventos:
                intent = new Intent(this, DatasDosEventos.class);
                break;
            case R.id.nav_nutricao:
                intent = new Intent(this, Hortinutri.class);
                break;
            case R.id.nav_missao:
                setTitle("Nossa Missão");
                fragment = new FragmentMissao();
                break;
            case R.id.nav_meta:
                setTitle("Nossa Meta");
                fragment = new FragmentMeta();
                break;
            case R.id.nav_login:
                setTitle("Login");
                intent = new Intent(this, LoginActivity.class);
                break;
            default:
                setTitle("Lar Espírita Maria Lobato - Hidroponia");
                fragment = new FragmentDeAbertura();
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        } else {
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
}
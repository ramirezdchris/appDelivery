package com.fm.modules.app.pedidos;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.fm.modules.R;
import com.fm.modules.adapters.ViewPagerAdapter;
import com.fm.modules.app.perfil.PerfilDriver;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class Principal extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager vPager;
    TabItem tabPedidos, tabPedidoActual;

    ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_principal);

        tabLayout = findViewById(R.id.tabLayout);
        vPager = findViewById(R.id.vPager);
        tabPedidos = findViewById(R.id.tabPedidos);
        tabPedidoActual = findViewById(R.id.tabPedidoActual);

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabLayout.getTabCount());
        vPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_navigator_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuHome:
                Toast.makeText(Principal.this, "menuHome", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuPerfil:
                Toast.makeText(Principal.this, "menuShoppingCart", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Principal.this, PerfilDriver.class);
                startActivity(i);
                break;

        }
        return true;
    }
}

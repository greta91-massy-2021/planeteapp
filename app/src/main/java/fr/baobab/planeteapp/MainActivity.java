package fr.baobab.planeteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import fr.baobab.planeteapp.adapter.PlaneteAdapter;
import fr.baobab.planeteapp.model.Planete;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources resources = getResources();
        String[] nomsTab = resources.getStringArray(R.array.noms);
        int[] distancesTab = resources.getIntArray(R.array.distances);

        //créer un tableau des ressources images
        int[] idImages = new int[]{
                R.drawable.mercury,
                R.drawable.venus,
                R.drawable.earth,
                R.drawable.mars,
                R.drawable.jupiter,
                R.drawable.saturn,
                R.drawable.uranus,
                R.drawable.neptune,
                R.drawable.pluto
        };
        //créer la liste des planètes
        ArrayList<Planete> list = new ArrayList<>();
        for (int i = 0; i < nomsTab.length ; i++){
            Planete p = new Planete(nomsTab[i], distancesTab[i], idImages[i]);
            list.add(p);
        }
        //créer un adaptateur
        /*ArrayAdapter<Planete> adapter =
                new ArrayAdapter<>(this, R.layout.item, R.id.item_nom, list);*/
        RecyclerView rv = (RecyclerView)findViewById(R.id.list);
        //rv.setLayoutManager(new LinearLayoutManager(this));//gestionnaire de mise en forme
        //rv.setLayoutManager(new GridLayoutManager(this, 2));//gestionnaire de mise en forme
        LinearLayoutManager llm =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llm);//gestionnaire de mise en forme
        rv.setHasFixedSize(true);
        //séparateur
        DividerItemDecoration did = new DividerItemDecoration(rv.getContext(), llm.getOrientation());
        rv.addItemDecoration(did);

        PlaneteAdapter adapter = new PlaneteAdapter(list);
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.menu_create_planete:
                //afficher le formulaire de création de planete
                Log.i(TAG, "dans menu_create_planete");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

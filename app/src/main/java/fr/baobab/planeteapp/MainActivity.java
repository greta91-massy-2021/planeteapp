package fr.baobab.planeteapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.baobab.planeteapp.adapter.PlaneteAdapter;
import fr.baobab.planeteapp.model.Planete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final int PLANETE_CREATE_ACTIVITY = 1;
    private PlaneteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Resources resources = getResources();
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
        }*/

        //créer un adaptateur
        /*ArrayAdapter<Planete> adapter =
                new ArrayAdapter<>(this, R.layout.item, R.id.item_nom, list);*/
        ArrayList<Planete> list = new ArrayList<>();
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

        adapter = new PlaneteAdapter(list);
        adapter.setMenuListener(this);
        rv.setAdapter(adapter);

        //récupérer les données depuis le backend
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://3797423d0ba8.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaneteService service = retrofit.create(PlaneteService.class);
        Call<List<Planete>> planetes = service.getPlanetes();
        planetes.enqueue(new Callback<List<Planete>>() {
            @Override
            public void onResponse(Call<List<Planete>> call, Response<List<Planete>> response) {
                List<Planete> planetes = response.body();
                Log.i(TAG, planetes.toString());
                adapter.setPlanetes(planetes);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Planete>> call, Throwable t) {

            }
        });
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
                //Log.i(TAG, "dans menu_create_planete");
                //créer un Intent explicite
                Intent intent = new Intent(this, PlaneteCreateActivity.class);
                startActivityForResult(intent, PLANETE_CREATE_ACTIVITY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.menu_edit_planete:
                //afficher le formulaire de modification de planete
                Log.i(TAG, "dans menu_edit_planete");
                return false;
            case R.id.menu_delete_planete:
                //demander la confirmation avant de supprimer
                Log.i(TAG, "dans menu_delete_planete");
                return false;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLANETE_CREATE_ACTIVITY && resultCode == RESULT_OK && null != data){
            //récupérer les données envoyées par PlaneteCreateActivity
            final String nomPlanete = data.getStringExtra("nomPlanete");
            final int distancePlanete = data.getIntExtra("distancePlanete", 0);
            Planete planete = new Planete(nomPlanete, distancePlanete, R.drawable.earth);
            adapter.addPlanete(planete);
        }
    }
}

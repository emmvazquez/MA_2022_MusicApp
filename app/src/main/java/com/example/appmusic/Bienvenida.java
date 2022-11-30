package com.example.appmusic;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmusic.adapter.registrosImagenUrlAdapter;
import com.example.appmusic.entidades.Artista;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Bienvenida extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    RecyclerView recyclerArtista;
    ArrayList<Artista> listaArtista;
    ViewFlipper v_flipper;
    ProgressDialog dialogo;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ActionBar dialog;

    public Bienvenida() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TercerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Bienvenida newInstance(String param1, String param2) {
        Bienvenida fragment = new Bienvenida();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_bienvenida, container, false);

        return vista;


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {




        Button bt4a4 = view.findViewById(R.id.idGruposMusicales);



        //Se agrego el menu lateral

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.idNavegationView);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view) { drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id){

                    case R.id.nav_home:
                        Navigation.findNavController(view).navigate(R.id.bienvenida2);
                        break;
                    case R.id.nav_search:
                        Navigation.findNavController(view).navigate(R.id.action_bienvenida2_to_datosArtistas);
                        break;
                    case R.id.nav_user:
                        Navigation.findNavController(view).navigate(R.id.action_bienvenida2_to_gruposMusicales);
                        break;

                }
                return true;
            }
        });


//Termino le menu lateral



        bt4a4.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_bienvenida2_to_gruposMusicales);
            }
        }));

        listaArtista=new ArrayList<>();

        recyclerArtista = (RecyclerView) view.findViewById(R.id.idRecyclerArtista);
        recyclerArtista.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerArtista.setHasFixedSize(true);

        request= Volley.newRequestQueue(getContext());

        cargarWebService();


    }

    private void cargarWebService() {
        dialogo=new ProgressDialog(getContext());
        dialogo.setMessage("Consultando Imagenes");
        dialogo.show();

        String url="https://precatory-levels.000webhostapp.com/ConsutarArtistas.php";
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
        //VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }


    @Override
    public void onResponse(JSONObject response) {
        Artista artista=null;

        JSONArray json=response.optJSONArray("nuevoartista");

        try {

            //for (int i=0;i<json.length();i++){
                for (int i=0;i<1;i++){
                artista= new Artista();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);

                artista.setIdArtista(jsonObject.optInt("idArtista"));
                artista.setNombreGrupo((jsonObject.optString("nombreGrupo")));
                artista.setDescripcion(jsonObject.optString("descripcion"));
                artista.setGeneroMusical(jsonObject.optString("generoMusical"));
                artista.setCorreoGrupo(jsonObject.optString("correo"));
                artista.setRutaLogo(jsonObject.optString("rutaLogo"));
                listaArtista.add(artista);
            }
            dialogo.hide();
            registrosImagenUrlAdapter adapter=new registrosImagenUrlAdapter(listaArtista,getContext());
            recyclerArtista.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
            dialogo.hide();
        }
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        dialogo.hide();
        Log.d("ERROR: ", error.toString());

    }



}





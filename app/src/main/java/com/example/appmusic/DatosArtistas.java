package com.example.appmusic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

public class DatosArtistas extends Fragment {

    private List<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    int tiposervicio,generomuesical;
    Spinner servicio,genero;
    EditText nombregrupo,logo,direccion,municipio,correogrupo,telefonogrupo,passwordgrupo;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DatosArtistas() {
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
    public static DatosArtistas newInstance(String param1, String param2) {
        DatosArtistas fragment = new DatosArtistas();
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
        return inflater.inflate(R.layout.fragment_artistas, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Button btnDatos = view.findViewById(R.id.idPerfilArtista);
        servicio       = (Spinner)  view.findViewById(R.id.idTipoServicio);
        genero         = (Spinner)  view.findViewById(R.id.idGeneroMusical);
        nombregrupo    = (EditText) view.findViewById(R.id.idNombreGrupo);
        logo           = (EditText) view.findViewById(R.id.idLogo);
        direccion      = (EditText) view.findViewById(R.id.idDireccion);
        municipio      = (EditText) view.findViewById(R.id.idMunicipio);
        correogrupo    = (EditText) view.findViewById(R.id.idCorreoGrupo);
        passwordgrupo  = (EditText) view.findViewById(R.id.idPasswordGrupo);
        telefonogrupo  = (EditText) view.findViewById(R.id.idTelefonoGrupo);

        String[] valoresServicio = {"","1.Solista","2.Dueto","3.Trio","4.Agrupacion","5.Sonido"};
        servicio.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,valoresServicio));
        servicio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position1, long id)
            {
                Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position1), Toast.LENGTH_SHORT).show();
                tiposervicio=position1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // vacio

            }
        });


        String[] valoresGenero = {"","1.Banda","2.Cumbia","3.Norteña","4.Corridos","5.Grupera","6.Versatil"};
        genero.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,valoresGenero));
        genero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position2, long id)
            {
                Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position2), Toast.LENGTH_SHORT).show();

                generomuesical=position2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // vacio

            }
        });

        RequestQueue queue1 = Volley.newRequestQueue(getContext());

        btnDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sNombreGrupo   = nombregrupo.getText().toString();
                String sLogo          = logo.getText().toString();
                String sDireccion     = direccion.getText().toString();
                String sMunicipio     = municipio.getText().toString();
                String sCorreoGrupo   = correogrupo.getText().toString();
                String sPasswordGrupo = passwordgrupo.getText().toString();
                String sTelefonoGrupo = telefonogrupo.getText().toString();



                String url = "http://192.168.137.247:80/proyectoMoviles/nuevoArtista.php?tipoServicio="+tiposervicio+"&nombreGrupo="+sNombreGrupo+"&logo="+sLogo+"&direccion="+sDireccion+"&municipio="
                        +sMunicipio+"&generoMusical="+generomuesical+"&correo="+sCorreoGrupo+"&contrasenaGrupo="+sPasswordGrupo+
                        "&telefonoGrupo="+sTelefonoGrupo;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(view.getContext(), response.toString(), Toast.LENGTH_LONG).show();
                        Navigation.findNavController(view).navigate(R.id.action_datosArtistas_to_perfilArtista);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(view.getContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
                );

                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        Constants.MY_DEFAULT_TIMEOUT,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue1.add(jsonObjectRequest);




            }
        });


    }
    public class Constants {

        public static final int MY_DEFAULT_TIMEOUT = 2500;

        //...
    }
}
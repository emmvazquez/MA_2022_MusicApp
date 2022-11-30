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

import java.util.ArrayList;
import java.util.List;

public class Paquetes extends Fragment {

    private List<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    int opcionPaquete;
    Spinner tipoPaquete;
    EditText descripcion,precio;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Paquetes() {
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
    public static Paquetes newInstance(String param1, String param2) {
        Paquetes fragment = new Paquetes();
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
        return inflater.inflate(R.layout.fragment_paquetes, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Button registrarPaquete = view.findViewById(R.id.idService);
        tipoPaquete       = (Spinner)  view.findViewById(R.id.idTipoPaquete);
        descripcion         = (EditText)  view.findViewById(R.id.idDescripcion);
        precio    = (EditText) view.findViewById(R.id.idPrecio);

        String[] valoresPaquete = {"","1.Basico","2.Medio","3.Estandar","4.Premium"};
        tipoPaquete.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,valoresPaquete));
        tipoPaquete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                opcionPaquete=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // vacio

            }
        });

        RequestQueue queue1 = Volley.newRequestQueue(getContext());

        registrarPaquete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sDescripcion   = descripcion.getText().toString();
                String sPrecio          = precio.getText().toString();



                String url = "http://192.168.137.247:80/proyectoMoviles/paquetes.php?tipodePaquete="+opcionPaquete+
                        "&descripcion="+sDescripcion+"&precio="+Double.valueOf(sPrecio);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(view.getContext(), response.toString(), Toast.LENGTH_LONG).show();
                        Navigation.findNavController(view).navigate(R.id.action_paquetes_to_perfilArtista);
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
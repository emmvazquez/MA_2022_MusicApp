package com.example.appmusic;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainFragment extends Fragment {

    EditText usuariologin,contrasenalogin;
    ProgressDialog progressDialog;
    String HttpURI = "http://192.168.137.249:80/proyectoMoviles/login.php";

ImageView imagen;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
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
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        imagen = (ImageView) view.findViewById(R.id.idImagen);
        usuariologin = (EditText) view.findViewById(R.id.idUsuarioLogin);
        contrasenalogin = (EditText) view.findViewById(R.id.idContrasenaLogin);
        Button btnregistrar = view.findViewById(R.id.idRegistro);
        Button btnIngresar = view.findViewById(R.id.idBienvenida);
Picasso.get().load("http://www.logo.com").into(imagen);


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        progressDialog = new ProgressDialog(getContext());

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_registroUsuarios22);

            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String u = usuariologin.getText().toString();
                String p = contrasenalogin.getText().toString();
                if (u.isEmpty() || p.isEmpty())
                    Toast.makeText(getContext().getApplicationContext(), "Debes llenar todos los campos",
                            Toast.LENGTH_LONG).show();
                else {
                    progressDialog.setMessage("Verificando...");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURI,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String serverResponse) {
                                    progressDialog.dismiss();
                                    try {
                                        JSONObject obj = new JSONObject(serverResponse);
                                        Boolean error = obj.getBoolean("error");
                                        String mensaje = obj.getString("mensaje");
                                        int tipoUser = obj.getInt("tipouser");


                                        if (error == true)
                                            Toast.makeText(getContext().getApplicationContext(), mensaje,
                                                    Toast.LENGTH_LONG).show();
                                        else {
                                            Toast.makeText(getContext().getApplicationContext(), mensaje,
                                                    Toast.LENGTH_LONG).show();
                                            //Si el usuario y contraseña es correcto se abre el home

                                            usuariologin.setText("");
                                            contrasenalogin.setText("");
                                            if(tipoUser == 1) {
                                                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_bienvenida2);
                                            }
                                            else{
                                                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_perfilArtista);
                                            }




                                            // Intent intent = new Intent(getActivity(), menuc.class);
                                            //startActivity(intent);

                                        }

                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            progressDialog.dismiss();
                            Toast.makeText(getContext().getApplicationContext(), error.toString(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }){
                        protected Map<String, String> getParams(){
                            Map<String,String> parametros = new HashMap<>();
                            parametros.put("usuario",u);
                            parametros.put("contrasena",p);
                            parametros.put("opcion","login");

                            return parametros;
                        }
                    };
                    requestQueue.add(stringRequest);
                }



        }



        });
    }
}
package com.example.appmusic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class RegistroUsuarios extends Fragment {
    EditText nombre;
    EditText apellidoPaterno;
    EditText apellidoMaterno;
    EditText correo;
    EditText telefono;
    EditText usuario;
    EditText password;

    Button registrar, cancelar;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistroUsuarios() {
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
    public static RegistroUsuarios newInstance(String param1, String param2) {
        RegistroUsuarios fragment = new RegistroUsuarios();
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
        return inflater.inflate(R.layout.fragment_registrousuarios, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        nombre = (EditText) view.findViewById(R.id.idNombre);
        apellidoPaterno = (EditText) view.findViewById(R.id.idApellidoPaterno);
        apellidoMaterno = (EditText) view.findViewById(R.id.idApelldioMaterno);
        correo = (EditText) view.findViewById(R.id.idCorreo);
        telefono = (EditText) view.findViewById(R.id.idTelefonoUusuario);
        usuario = (EditText) view.findViewById(R.id.idUsuario);
        password = (EditText) view.findViewById(R.id.idPassword);
        registrar =(Button) view.findViewById(R.id.idLogin);
        cancelar = (Button) view.findViewById(R.id.idCancelar);



        RequestQueue queue = Volley.newRequestQueue(getContext());


        registrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String sNombre = nombre.getText().toString();
                String sApellidoP = apellidoPaterno.getText().toString();
                String sApellidoM = apellidoMaterno.getText().toString();
                String sCorreo = correo.getText().toString();
                String sTelefono = telefono.getText().toString();
                String sUsuario = usuario.getText().toString();
                String sPassword = password.getText().toString();



                String url = "http://192.168.137.247:80/proyectoMoviles/RegistroUsuario.php?nombre="+sNombre+"&apellidoPaterno="+sApellidoP+"&apellidoMaterno="+
                        sApellidoM+"&correo="+sCorreo+"&telefono="+sTelefono+"&usuario="+sUsuario+"&contrasena="+sPassword;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(view.getContext(), response.toString(), Toast.LENGTH_LONG).show();
                        Navigation.findNavController(view).navigate(R.id.action_registroUsuarios2_to_mainFragment3);
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
                queue.add(jsonObjectRequest);


            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Navigation.findNavController(view).navigate(R.id.action_registroUsuarios2_to_mainFragment3);
            }
        });

    }
    public class Constants {

        public static final int MY_DEFAULT_TIMEOUT = 2500;

        //...
    }
}

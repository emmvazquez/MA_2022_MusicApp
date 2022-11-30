package com.example.appmusic;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DatosArtistas extends Fragment {


    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;
    private ArrayAdapter<String> adapter;
    int tiposervicio,generomuesical;
    Spinner servicio,genero;
    EditText nombregrupo,direccion,logo,municipio,correogrupo,telefonogrupo,passwordgrupo,descripcion;

    ImageView Imagenlogo;
    Button cargarImagenLogo;
    private final String CARPETA_RAIZ="imagenesAppMusic/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misLogos";
    private String path;
    File fileImagen;
    Bitmap bitmap;

    StringRequest stringRequest;
    ProgressDialog progreso;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Object Context;

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

        Imagenlogo       = (ImageView) view.findViewById(R.id.idImageLogo);
        servicio         = (Spinner)  view.findViewById(R.id.idTipoServicio);
        genero           = (Spinner)  view.findViewById(R.id.idGeneroMusical);
        nombregrupo      = (EditText) view.findViewById(R.id.idNombreGrupo);
        descripcion      =(EditText)  view.findViewById(R.id.idDescripcion);
        logo             = (EditText) view.findViewById(R.id.idLogo);
        direccion        = (EditText) view.findViewById(R.id.idDireccion);
        municipio        = (EditText) view.findViewById(R.id.idMunicipio);
        correogrupo      = (EditText) view.findViewById(R.id.idCorreoGrupo);
        passwordgrupo    = (EditText) view.findViewById(R.id.idPasswordGrupo);
        telefonogrupo    = (EditText) view.findViewById(R.id.idTelefonoGrupo);
        cargarImagenLogo =(Button) view.findViewById(R.id.idCargarImagen);

        if (validaPermisos()){

            cargarImagenLogo.setEnabled(true);
        }else{

            cargarImagenLogo.setEnabled(false);
        }

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


        cargarImagenLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cargarImagen();
            }
        });


        RequestQueue queue1 = Volley.newRequestQueue(getContext());



        btnDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                progreso = new ProgressDialog(getContext());
                progreso.setMessage("Cargando...");
                progreso.show();


                String url = "https://precatory-levels.000webhostapp.com/nuevoArtista.php";


                stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progreso.hide();
                        Toast.makeText(getContext(),"Se ha registrado con exito"+nombregrupo,Toast.LENGTH_LONG).show();

                        if(response.trim().equalsIgnoreCase("Registra")){

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


                            String[] valoresGenero = {"","1.Banda","2.Cumbia","3.Tejana","4.Corridos","5.Grupera","6.Versatil"};
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

                            nombregrupo.setText("");
                            direccion.setText("");
                            municipio.setText("");
                            correogrupo.setText("");
                            passwordgrupo.setText("");
                            telefonogrupo.setText("");
                            logo.setText("");
                            Imagenlogo.setImageResource(android.R.drawable.ic_menu_report_image);

                            Navigation.findNavController(view).navigate(R.id.action_datosArtistas_to_perfilArtista);
                        }else
                        {

                            Toast.makeText(getContext(),"No se ha podido registrar",Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"No se ha podido conecatar",Toast.LENGTH_LONG).show();
                        progreso.hide();


                    }
                }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        String sNombreGrupo   = nombregrupo.getText().toString();
                        String sDescripcion   = descripcion.getText().toString();
                        String sLogo          = convertirImgString(bitmap);
                        String sDireccion     = direccion.getText().toString();
                        String sMunicipio     = municipio.getText().toString();
                        String sCorreoGrupo   = correogrupo.getText().toString();
                        String sPasswordGrupo = passwordgrupo.getText().toString();
                        String sTelefonoGrupo = telefonogrupo.getText().toString();

                        Map<String,String> parametros = new HashMap<>();
                        parametros.put("tipoServicio",String.valueOf(tiposervicio));
                        parametros.put("nombreGrupo",sNombreGrupo);
                        parametros.put("descripcion",sDescripcion);
                        parametros.put("logo",sLogo);
                        parametros.put("direccion",sDireccion);
                        parametros.put("municipio",sMunicipio);
                        parametros.put("generoMusical",String.valueOf(generomuesical));
                        parametros.put("correo",sCorreoGrupo);
                        parametros.put("contrasenaGrupo",sPasswordGrupo);
                        parametros.put("telefonoGrupo",sTelefonoGrupo);

                        return parametros;
                    }
                };



               queue1.add(stringRequest);




            }
        });


    }

    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }

    
    private boolean validaPermisos(){

        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){

            return true;

        }
        if((getContext().checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)&&
                (getContext().checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if ((shouldShowRequestPermissionRationale(CAMERA))||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){

            cargarDialogoRecomendacion();

        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA},100);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
            && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                cargarImagenLogo.setEnabled(true);

            }else{

                solicitarPermisosManual();
            }

        }
    }

    private void solicitarPermisosManual() {

        final CharSequence[] opciones ={"Si","No"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("¿Descea configurar los permisos de forma manual?");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (opciones[i].equals("Si")){


                   Intent intent = new Intent();
                   intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DetailsSettingsActivity" +
                           ""));
                   Uri uri2 = Uri.fromParts("pakage",getContext().getPackageName(),null);
                   intent.setData(uri2);
                   startActivity(intent);

                }
                else {

                    Toast.makeText(getContext(),"Los Permisos no fueron aceptados",Toast.LENGTH_LONG).show();
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debes aceptar los permisos correspondientes para el correcto funcioanmiento de la app");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){

                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA},100);

            }
        });
        dialogo.show();
    }

    private void cargarImagen(){
        final CharSequence[] opciones ={"Tomar foto","Cargar Logo","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una Opcion");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (opciones[i].equals("Tomar foto")){

                    tomarFotografia();

                }else {
                    if (opciones[i].equals("Cargar Logo")){
                        Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);

                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

            private  void tomarFotografia(){
            File fileImagen = new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
            boolean isCreada = fileImagen.exists();
                String nombreImagen="";
            if (isCreada == false){

                isCreada = fileImagen.mkdirs();
            }

            if (isCreada == true){

                 nombreImagen = (System.currentTimeMillis()/1000)+".jpg";
                 logo.setText(nombreImagen);
            }

            path = Environment.getExternalStorageDirectory()+
                    File.separator+RUTA_IMAGEN+File.separator+nombreImagen;

            File imagen =new File(path);

            Intent intentCam=null;
            intentCam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){

                    String authorities= getContext().getApplicationContext().getPackageName()+".provider";
                    Uri imageUri = FileProvider.getUriForFile(getContext(),authorities,imagen);
                    intentCam.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                }else{
                    intentCam.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(imagen));
                }

            startActivityForResult(intentCam,COD_FOTO);
            }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    switch (requestCode) {
        case 10:
            Uri miPath = data.getData();
            Imagenlogo.setImageURI(miPath);


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),miPath);
                Imagenlogo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            break;

        case 20:

            MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String s, Uri uri) {
                            Log.i("Ruta de almacenamiento", "Path: "+path);

                        }
                    });

            bitmap = BitmapFactory.decodeFile(path);
            Imagenlogo.setImageBitmap(bitmap);
            break;
    }


    bitmap=redimencionarImagen(bitmap,600,800);

    }

    private Bitmap redimencionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

        int ancho = bitmap.getWidth();
        int alto = bitmap.getHeight();

        if(ancho>anchoNuevo || alto>altoNuevo){

            float escalaAncho = anchoNuevo/ancho;
            float escalaAlto  = altoNuevo/alto;

            Matrix matrix = new Matrix();
            matrix.postScale(escalaAncho,escalaAlto);

            return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);

        }else{

            return bitmap;
        }


    }

}
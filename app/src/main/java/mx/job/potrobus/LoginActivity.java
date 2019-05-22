package mx.job.potrobus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import mx.job.potrobus.Entities.Encrypter;
import mx.job.potrobus.Entities.Usuario;
import mx.job.potrobus.Interfaces.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Retrofit retrofit;
    CheckBox rememberMe;
    EditText edUsernameLogin, edContrasenaLogin,
    
            edUsername, edContrasena, edNombre, edCorreo, edConfirmacion, edTelefono;
    Button btnLogin, btnSignup, btnRegistrar;
    TextInputLayout txtInLayoutUsername, txtInLayoutPassword, txtInLayoutRegPassword;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().hide();
        edUsername = findViewById(R.id.txtUsername);
        edContrasena = findViewById(R.id.txtContrasena);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        txtInLayoutUsername = findViewById(R.id.txtInLayoutUsername);
        txtInLayoutPassword = findViewById(R.id.txtInLayoutPassword);
        rememberMe = findViewById(R.id.checkRemeber);
        //SignUp's Button for showing registration page
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickSignUp();
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.17/potrobus/public/users/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClickLogin();
    }

    //This is method for doing operation of check activity_login
    private void ClickLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                edUsernameLogin = findViewById(R.id.loginUsername);
                edContrasenaLogin = findViewById(R.id.loginContrasena);
                rememberMe = findViewById(R.id.checkRemeber);
                String username="";
                String contrasena="";
                if (edUsernameLogin.getText().toString().trim().isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, "Porfavor llene el espacio",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    snackbar.show();
                    txtInLayoutUsername.setError("Debe escribir su usuario");
                } else {
                    username = edUsernameLogin.getText().toString();

                }
                if (edContrasenaLogin.getText().toString().trim().isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, "Porfavor llene el espacio",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    snackbar.show();
                    txtInLayoutPassword.setError("Debe escribir su contraseña");
                } else {
                    contrasena = edContrasenaLogin.getText().toString();

                }

                if (rememberMe.isChecked()) {
                    //TODO: Remember credentials
                    System.out.println("Recordar");
                }
                if (!username.isEmpty() && !contrasena.isEmpty()) {
                    String un = new Encrypter().getHash(username.getBytes());
                    String psw = new Encrypter().getHash(contrasena.getBytes());
                    Usuario u = new Usuario(null, un, null, psw, null);
                    UserAPI userAPI = retrofit.create(UserAPI.class);
                    Call<Usuario> call = userAPI.login(u);
                    call.enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            System.out.println(response.code());
                            if (response.code()==200){
                                //TODO: Save user data on SQLite locally
                                Intent intent = new Intent(view.getContext(), DrawerActivity.class);
                                startActivityForResult(intent, 0);
                            }else{
                                Toast.makeText(getApplicationContext(), "Credenciales rechazadas", Toast.LENGTH_SHORT).show();
                                edContrasenaLogin.setText("");
                                edUsernameLogin.setText("");
                            }
                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

        });

    }

    //The method for opening the registration page and another processes or checks for registering
    private void ClickSignUp() {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_register, null);
        dialog.setView(dialogView);
        final AlertDialog signDialog = dialog.create();

        edUsername = dialogView.findViewById(R.id.txtUsername);
        edContrasena = dialogView.findViewById(R.id.txtContrasena);
        edNombre = dialogView.findViewById(R.id.txtNombre);
        edTelefono = dialogView.findViewById(R.id.txtTelefono);
        edCorreo = dialogView.findViewById(R.id.txtCorreo);
        edConfirmacion = dialogView.findViewById(R.id.txtCorreoConfirmacion);
        btnRegistrar = dialogView.findViewById(R.id.btnRegistrar);
        txtInLayoutRegPassword = dialogView.findViewById(R.id.txtInLayoutRegPassword);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edUsername.getText().toString().trim().isEmpty()) {
                    edUsername.setError("Porfavor llene el espacio");
                } else if (edContrasena.getText().toString().trim().isEmpty()) {
                    txtInLayoutRegPassword.setPasswordVisibilityToggleEnabled(false);
                    edContrasena.setError("Porfavor llene el espacio");
                } else {
                    txtInLayoutRegPassword.setPasswordVisibilityToggleEnabled(true);
                } if (edNombre.getText().toString().trim().isEmpty()) {
                    edNombre.setError("Porfavor llene el espacio");
                } if (edTelefono.getText().toString().trim().isEmpty()) {
                    edTelefono.setError("Porfavor llene el espacio");
                } else
                if (edCorreo.getText().toString().trim().isEmpty()) {
                    edCorreo.setError("Porfavor llene el espacio");
                } else if (edConfirmacion.getText().toString().trim().isEmpty()) {
                    edConfirmacion.setError("Porfavor llene el espacio");
                } else {
                    Encrypter encrypter = new Encrypter();
                    Usuario u = new Usuario(encrypter.getHash(edNombre.getText().toString().getBytes()),
                            encrypter.getHash(edUsername.getText().toString().getBytes()),
                            encrypter.getHash(edCorreo.getText().toString().getBytes()),
                            encrypter.getHash(edContrasena.getText().toString().getBytes()),
                            encrypter.getHash(edTelefono.getText().toString().getBytes()));
                    UserAPI userAPI = retrofit.create(UserAPI.class);
                    Call<Usuario> call = userAPI.signup(u);
                    call.enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            Toast.makeText(getApplicationContext(), "Usuario registrado: " + response.body().getUsername(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Error al registrar usuario, intente mas tarde", Toast.LENGTH_LONG).show();
                        }
                    });
                    signDialog.cancel();
                }
            }
        });
        signDialog.show();
    }
}

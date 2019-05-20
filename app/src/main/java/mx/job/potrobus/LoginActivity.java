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

import com.google.android.gms.maps.model.MarkerOptions;

public class LoginActivity extends AppCompatActivity {


    EditText Usuario, Contraseña, regUsuario, regContraseña,
            regNombre, regTelefono, regCorreo, regConfirmacion;
    Button login, signUp, regIngresar;
    TextInputLayout txtInLayoutUsername, txtInLayoutPassword, txtInLayoutRegPassword;
    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().hide();
        Usuario = findViewById(R.id.Usuario);
        Contraseña = findViewById(R.id.Contraseña);
        login = findViewById(R.id.btnLogin);
        signUp = findViewById(R.id.btnSignup);
        txtInLayoutUsername = findViewById(R.id.txtInLayoutUsername);
        txtInLayoutPassword = findViewById(R.id.txtInLayoutPassword);
        rememberMe = findViewById(R.id.Recordar);


        ClickLogin();


        //SignUp's Button for showing registration page
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickSignUp();
            }
        });
        Button login = findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    //This is method for doing operation of check activity_login
    private void ClickLogin() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Usuario.getText().toString().trim().isEmpty()) {

                    Snackbar snackbar = Snackbar.make(view, "Porfavor llene el espacio",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    snackbar.show();
                    txtInLayoutUsername.setError("Debe escribir su usuario");
                } else {
                    //Here you can write the codes for checking username
                }
                if (Contraseña.getText().toString().trim().isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, "Porfavor llene el espacio",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorAccent

                    ));
                    snackbar.show();
                    txtInLayoutPassword.setError("Debe escribir su contraseña");
                } else {
                    //Here you can write the codes for checking password
                }

                if (rememberMe.isChecked()) {
                    //Here you can write the codes if box is checked
                } else {
                    //Here you can write the codes if box is not checked
                }

            }

        });

    }

    //The method for opening the registration page and another processes or checks for registering
    private void ClickSignUp() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_register, null);
        dialog.setView(dialogView);

        regUsuario = dialogView.findViewById(R.id.regUsuario);
        regContraseña = dialogView.findViewById(R.id.regContraseña);
        regNombre = dialogView.findViewById(R.id.regNombre);
        regTelefono = dialogView.findViewById(R.id.regTelefono);
        regCorreo = dialogView.findViewById(R.id.regCorreo);
        regConfirmacion = dialogView.findViewById(R.id.regConfirmacion);
        regIngresar = dialogView.findViewById(R.id.regIngresar);
        txtInLayoutRegPassword = dialogView.findViewById(R.id.txtInLayoutRegPassword);

        regIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (regUsuario.getText().toString().trim().isEmpty()) {

                    regUsuario.setError("Porfavor llene el espacio");
                } else {

                }
                if (regContraseña.getText().toString().trim().isEmpty()) {
                    txtInLayoutRegPassword.setPasswordVisibilityToggleEnabled(false);
                    regContraseña.setError("Porfavor llene el espacio");
                } else {
                    txtInLayoutRegPassword.setPasswordVisibilityToggleEnabled(true);

                }
                if (regNombre.getText().toString().trim().isEmpty()) {

                    regNombre.setError("Porfavor llene el espacio");
                } else {


                }
                if (regTelefono.getText().toString().trim().isEmpty()) {

                    regTelefono.setError("Porfavor llene el espacio");
                } else {

                }
                if (regCorreo.getText().toString().trim().isEmpty()) {

                    regCorreo.setError("Porfavor llene el espacio");
                } else {

                }
                if (regConfirmacion.getText().toString().trim().isEmpty()) {

                    regConfirmacion.setError("Porfavor llene el espacio");
                } else {

                }
            }
        });


        dialog.show();


    }


}

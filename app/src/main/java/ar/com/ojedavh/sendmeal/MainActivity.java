package ar.com.ojedavh.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.com.ojedavh.sendmeal.model.CuentaBancaria;
import ar.com.ojedavh.sendmeal.model.Tarjeta;
import ar.com.ojedavh.sendmeal.model.Usuario;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText_nombre = findViewById(R.id.FormReg_Nombre);
        EditText editText_pass = findViewById(R.id.FormReg_Pass);
        EditText editText_pass2 = findViewById(R.id.FormReg_Pass2);
        EditText editText_mail = findViewById(R.id.FormReg_Mail);
        RadioGroup radioGroup_tarjetaTipo = findViewById(R.id.FormReg_RadioGroup);
        EditText editText_tarjetaNro = findViewById(R.id.FormReg_NroCard);
        EditText editText_tarjetaCCV = findViewById(R.id.FormReg_CCV);
        EditText editText_tarjetaVenceMes = findViewById(R.id.FormReg_Date_Month);
        EditText editText_tarjetaVenceAnio = findViewById(R.id.FormReg_Date_Year);
        EditText editText_cbu = findViewById(R.id.FormReg_CBU);
        EditText editText_alias = findViewById(R.id.FormReg_Alias);
        Switch switch_cargaInicial = findViewById(R.id.FormReg_Carga);
        TextView textView_montoCarga = findViewById(R.id.FormReg_TV_MontoCarga);
        SeekBar seekBar_montoCarga = findViewById(R.id.FormReg_MontoCarga);
        CheckBox checkBox_terminosCondiciones = findViewById(R.id.FormReg_TyC);
        Button button_registrar = findViewById(R.id.FormReg_BtnRegistrar);

        checkBox_terminosCondiciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox_terminosCondiciones.isChecked())
                    button_registrar.setEnabled(true);
                else
                    button_registrar.setEnabled(false);
            }
        });

        switch_cargaInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switch_cargaInicial.isChecked()){
                    seekBar_montoCarga.setVisibility(View.VISIBLE);
                    textView_montoCarga.setVisibility(View.VISIBLE);
                }
                else{
                    seekBar_montoCarga.setVisibility(View.GONE);
                    textView_montoCarga.setVisibility(View.GONE);
                }
            }
        });

        seekBar_montoCarga.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView_montoCarga.setText("Crédito incial: $"+ progress+" ");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editText_nombre.getText().toString();
                String pass = editText_pass.getText().toString();
                String pass2 = editText_pass2.getText().toString();
                String mail = editText_mail.getText().toString();
                int tarjetaTipo = radioGroup_tarjetaTipo.getCheckedRadioButtonId();
                String tarjetaNro = editText_tarjetaNro.getText().toString();
                String tarjetaCCV = editText_tarjetaCCV.getText().toString();
                String tarjetaVenceMes = editText_tarjetaVenceMes.getText().toString();
                String tarjetaVenceAnio = editText_tarjetaVenceAnio.getText().toString();
                String cbu = editText_cbu.getText().toString();
                String alias = editText_alias.getText().toString();
                int montoCarga = seekBar_montoCarga.getProgress();


                boolean tarjetaEsCredito = (radioGroup_tarjetaTipo.getCheckedRadioButtonId() == R.id.FormReg_Credito);


                Context context = getApplicationContext();
                String mensaje = "Error en el registro";
                int duration = Toast.LENGTH_SHORT;

                if(nombre.isEmpty()) {
                    editText_nombre.requestFocus();
                    editText_nombre.setError("Campo obligatorio");
                }
                else if(mail.isEmpty()) {
                    editText_mail.requestFocus();
                    editText_mail.setError("Campo obligatorio");
                }
                else if(pass.isEmpty()) {
                    editText_pass.requestFocus();
                    editText_pass.setError("Campo obligatorio");
                }
                else if(tarjetaNro.isEmpty()) {
                    editText_tarjetaNro.requestFocus();
                    editText_tarjetaNro.setError("Campo obligatorio");
                }
                else if(tarjetaTipo <= 0) {
                    mensaje =  "Debe elegir un tipo de tarjeta";
                    radioGroup_tarjetaTipo.requestFocus();
                }
                else if(tarjetaCCV.isEmpty()) {
                    editText_tarjetaCCV.requestFocus();
                    editText_tarjetaCCV.setError("Campo obligatorio");
                }
                else if(tarjetaVenceMes.isEmpty()) {
                    editText_tarjetaVenceMes.requestFocus();
                    editText_tarjetaVenceMes.setError("Campo obligatorio");
                }
                else if(tarjetaVenceAnio.isEmpty()) {
                    editText_tarjetaVenceAnio.requestFocus();
                    editText_tarjetaVenceAnio.setError("Campo obligatorio");
                }
                else if (!MailValido(mail)) {
                    editText_mail.requestFocus();
                    editText_mail.setError("Correo inválido");
                }
                else if (!ClaveValida(pass, pass2)) {
                    editText_pass2.requestFocus();
                    editText_pass2.setError("Las contraseñas no coinciden");
                }
                else if (!FechaValida(tarjetaVenceMes, tarjetaVenceAnio)) {
                    editText_tarjetaVenceMes.requestFocus();
                    editText_tarjetaVenceMes.setError("La fecha de vencimiento de la tarjeta debe ser mayor a los próximos 3 meses");
                }
                else if (switch_cargaInicial.isChecked() && montoCarga <= 0) {
                    mensaje = "Debe ingresar un monto inicial";
                    seekBar_montoCarga.requestFocus();
                }
                else {
                    mensaje = "Registro completo";
                    Calendar tarjetaVenceFecha = new GregorianCalendar(Integer.parseInt(tarjetaVenceMes), Integer.parseInt(tarjetaVenceAnio)-1,1);
                    Tarjeta tarjeta = new Tarjeta(tarjetaNro,tarjetaCCV,tarjetaVenceFecha,tarjetaEsCredito);
                    CuentaBancaria cuentaBancaria = new CuentaBancaria(cbu,alias);
                    Usuario usuario = new Usuario((int)Math.floor(Math.random()*1000+1), nombre,pass,mail,montoCarga,tarjeta,cuentaBancaria);
                }

                Toast toast = Toast.makeText(context, mensaje, duration);

                toast.show();
            }
        });
    }

    private boolean MailValido(String mail){ //verificar si es un mail correcto
       boolean tieneArroba = mail.contains("@");
       String[] partesMail = mail.split("@");

        if(tieneArroba && partesMail[1].length()>2)
            return true;

        return false;
    }

    private boolean ClaveValida(String clave, String clave_repite){ //verificar que las contraseñas coinciden
        return clave.equals(clave_repite);
    }

    private boolean FechaValida(String mes, String anio){
        Calendar fecha = new GregorianCalendar(Integer.parseInt(anio), Integer.parseInt(mes)-1,1);
        Calendar fecha_hoy = Calendar.getInstance();
        fecha_hoy.add(GregorianCalendar.MONTH,3);

        return fecha.after(fecha_hoy);
    }
}

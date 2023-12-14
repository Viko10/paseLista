package com.itsoeh.vcruz.paselista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;




import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet. InternetAddress;
import javax.mail.internet.MimeMessage;


public class recuperacontra extends AppCompatActivity {

    private EditText correo;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacontra);
        correo = findViewById(R.id.rec_txt_correo);
        btnEnviar = findViewById(R.id.contr_btn_envia);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickenviar();

            }
        });


    }
    private String generateRandomPassword(int length) {
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }
        return sb.toString();
    }

    private void clickenviar() {
        String nuevaContrasena = generateRandomPassword(8);
        new EnviarCorreoAsyncTask(this,
                "asissmart.itsoeh@gmail.com",
                "vslm jlxf hide mxji",
                correo.getText().toString(),
                "Recuperacion de Contraseña",
                "Tu nueva contraseña es: " + nuevaContrasena
        ).execute();

    }

    private static class EnviarCorreoAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog progressDialog;
        private Context context;
        private String usuario;
        private String pass;
        private String destinatario;
        private String asunto;
        private String mensaje;

        public EnviarCorreoAsyncTask( Context context, String usuario, String pass, String destinatario, String asunto, String mensaje) {

            this.context = context;
            this.usuario = usuario;
            this.pass = pass;
            this.destinatario = destinatario;
            this.asunto = asunto;
            this.mensaje = mensaje;
            this.progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Progreso");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Enviando Correo...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override

        protected Boolean doInBackground(Void... params) {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(usuario, pass);
                        }
                    });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(usuario));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(destinatario));
                message.setSubject(asunto);
                message.setText(mensaje);
                Transport.send(message);
                return true;
            } catch (MessagingException e) {
                Toast.makeText(context, "Error: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        @Override

        protected void onPostExecute(Boolean result) {
            progressDialog.dismiss();

            if (result) {

                Toast.makeText(context, "Nueva contraseña Enviada con éxito" +
                                "Revisa Spam si es Posible",
                        Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(context, "Existió una falla al enviar la nueva contraseña e correo dado",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }
}
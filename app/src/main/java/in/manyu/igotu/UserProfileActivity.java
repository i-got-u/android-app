package in.manyu.igotu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    generatePublicKey(view);
                } catch (NoSuchAlgorithmException exc) {
                    return;
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static byte[] SHAsum (byte[] convertme) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        return md.digest(convertme);
    }

    public void generatePublicKey(View view) throws NoSuchAlgorithmException {
        TextView private_key_view = (TextView) findViewById(R.id.private_key_textbox);
        TextView public_key_view = (TextView) findViewById(R.id.public_key_textbox);

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");

        kpg.initialize(512);

        KeyPair keyPair = kpg.genKeyPair();
        byte[] pri = keyPair.getPrivate().getEncoded();
        String pri_encoded = Base64.encodeToString(pri, pri.length);

        byte[] pub = keyPair.getPublic().getEncoded();
        byte[] pub_fingerprint = SHAsum(pub);
        String pub_encoded = Base64.encodeToString(pub, pub.length);
        String pub_fingerprint_encoded = Base64.encodeToString(pub_fingerprint, pub_fingerprint.length);

        private_key_view.setText(pub_encoded);
        public_key_view.setText(pub_fingerprint_encoded);
    }

}

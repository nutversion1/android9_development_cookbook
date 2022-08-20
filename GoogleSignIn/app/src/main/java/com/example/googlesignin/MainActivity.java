package com.example.googlesignin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.signInButton).setOnClickListener(view -> {
            signIn();
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                findViewById(R.id.signInButton).setVisibility(View.GONE);

                Toast.makeText(this,
                        "Logged in: "+account.getDisplayName() + "\n"
                        + account.getEmail() + "\n" + account.getId() + "\n"
                        + account.getPhotoUrl() + "\n"
                        + account.getIdToken() + "\n"
                        +account.getFamilyName() + "\n"
                        + account.isExpired() + "\n",
                        Toast.LENGTH_SHORT).show();

                ((TextView) findViewById(R.id.textView)).setText("Logged in: "+account.getDisplayName() + "\n"
                        + account.getEmail() + "\n" + account.getId() + "\n"
                        + account.getPhotoUrl() + "\n"
                        + account.getIdToken() + "\n"
                        +account.getFamilyName() + "\n"
                        + account.isExpired() + "\n");

            } catch (ApiException e) {
                e.printStackTrace();

                Toast.makeText(this,
                        "Sign in failed: "+e.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
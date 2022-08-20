package com.example.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.buttonStart);

        startButton.setOnClickListener(view -> {

            AsyncTask countingTask = new CountingTask().execute(10);
            //countingTask.cancel(true);
        });
    }

    private class CountingTask extends AsyncTask<Integer, Integer, Integer>{

        @Override
        protected Integer doInBackground(Integer... params) {
            int count = params[0];
            for(int x = 0; x < count; x++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

            return count;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            startButton.setEnabled(false);


        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            startButton.setEnabled(true);

            Toast.makeText(MainActivity.this,
                    "finish",
                    Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onCancelled(Integer integer) {
            super.onCancelled(integer);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();


        }


    }
}
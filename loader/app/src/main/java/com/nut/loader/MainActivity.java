package com.nut.loader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    EditText mEditTextWord;
    EditText mEditTextDefinition;
    DictionaryDatabase mDB;
    ListView mListView;
    DictionaryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDB = new DictionaryDatabase(this);

        mEditTextWord = findViewById(R.id.editTextWord);
        mEditTextDefinition = findViewById(R.id.editTextDefinition);

        Button buttonAddUpdate = findViewById(R.id.buttonAddUpdate);
        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRecord();
            }
        });

        mListView = findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(MainActivity.this,
                        mDB.getDefinition(id),
                        Toast.LENGTH_SHORT).
                        show();
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(MainActivity.this,
                        "Records deleted = " + mDB.deleteRecord(id),
                        Toast.LENGTH_SHORT).
                        show();

                getSupportLoaderManager().restartLoader(0, null, MainActivity.this);

                return true;
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);
        mAdapter = new DictionaryAdapter(this, mDB.getWordList(), 0);
        mListView.setAdapter(mAdapter);
    }

    private void saveRecord(){
        mDB.saveRecord(
                mEditTextWord.getText().toString(),
                mEditTextDefinition.getText().toString());

        mEditTextWord.setText("");
        mEditTextDefinition.setText("");

        getSupportLoaderManager().restartLoader(0, null, MainActivity.this);
    }




    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new DictionaryLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
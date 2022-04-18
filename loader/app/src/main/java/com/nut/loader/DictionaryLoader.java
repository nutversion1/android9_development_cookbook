package com.nut.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

public class DictionaryLoader extends CursorLoader {
    Context mContext;

    public DictionaryLoader(Context context){
        super(context);
        mContext = context;
    }

    @Override
    public Cursor loadInBackground() {
        DictionaryDatabase db = new DictionaryDatabase(mContext);
        return db.getWordList();
    }
}

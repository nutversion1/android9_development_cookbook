package com.example.fragementcommunication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.ListFragment;

public class MasterFragment extends ListFragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] countries = new String[]{"China", "France", "Italy", "Japan"};
        ListAdapter countryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, countries);
        setListAdapter(countryAdapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mOnMasterSelectedListener != null){
                    mOnMasterSelectedListener.onItemSelected(((TextView)view).getText().toString());

                }
            }
        });
    }

    public interface OnMasterSelectedListener{
        public void onItemSelected(String countryName);
    }

    private OnMasterSelectedListener mOnMasterSelectedListener = null;

    public void setOnMasterSelectedListener(OnMasterSelectedListener listener){
        mOnMasterSelectedListener = listener;
    }

}

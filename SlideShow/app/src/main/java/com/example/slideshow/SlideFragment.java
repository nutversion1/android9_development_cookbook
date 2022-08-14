package com.example.slideshow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SlideFragment extends Fragment {
    private int mImageResourceID;

    public SlideFragment(){

    }

    public void setImage(int resourceID){
        mImageResourceID = resourceID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_slide,
                container,
                false);

        ImageView imageView = rootView.findViewById(R.id.imageView);
        imageView.setImageResource(mImageResourceID);

        // Inflate the layout for this fragment
        return rootView;
    }
}
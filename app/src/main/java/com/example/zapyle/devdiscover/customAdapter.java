package com.example.zapyle.devdiscover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class customAdapter extends BaseAdapter {

    ArrayList<String> image=new ArrayList<String>();
    Context context;
    LayoutInflater inflater;
    public customAdapter(Context context, ArrayList<String> Imagearray) {
        System.out.println("GRidview"+Imagearray);
        this.image=Imagearray;
        this.context=context;
        inflater=LayoutInflater.from(this.context);

    }

    @Override
    public int getCount() {
        return image.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class Holder
    {
        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View view;

        view=inflater.inflate(R.layout.list_item,null);

        holder.imageView=(ImageView)view.findViewById(R.id.image);
        Picasso.with(context).load(image.get(position)).into(holder.imageView);
        return view;



    }
}

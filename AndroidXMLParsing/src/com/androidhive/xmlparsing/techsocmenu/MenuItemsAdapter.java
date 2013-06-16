package com.androidhive.xmlparsing.techsocmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidhive.xmlparsing.R;

/**
 * Created by user on 6/13/13.
 */
public class MenuItemsAdapter extends BaseAdapter {

    private Context mContext;
    private String[] items_text;

    public MenuItemsAdapter(Context c,String[] strings ) {  // constructor

      mContext = c;
      items_text = strings;

    }


    public int getCount() {
        return items_text.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;


        // Instantiates a layout XML file into its corresponding View objects.
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        if (convertView == null) {  // if it's not recycled, initialize some attributes

            view = new View(mContext);

            // Inflate a new view hierarchy from the specified xml resource
            // second parameter is ViewGroup (special view that can contain other views (called children.) which we are not going to use here)
            view = inflater.inflate(R.layout.item, null);

            String itemText = items_text[position];

            TextView textview = (TextView)view.findViewById(R.id.grid_item_text);
            textview.setText(itemText);


            ImageView pictureview = (ImageView) view.findViewById(R.id.grid_item_image);


            if (itemText.equals("News")) pictureview.setImageResource(R.drawable.news);
            else if (itemText.equals("Events")) pictureview.setImageResource(R.drawable.events);
            else if (itemText.equals("Projects")) pictureview.setImageResource(R.drawable.projects);
            else if (itemText.equals("Games")) pictureview.setImageResource(R.drawable.games);



        } else {
            view = (View) convertView;
        }

        //imageView.setImageResource(mThumbIds[position]);

        return view;
    }




}

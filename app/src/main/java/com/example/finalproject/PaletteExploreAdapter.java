package com.example.finalproject;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PaletteExploreAdapter extends RecyclerView.Adapter<PaletteExploreAdapter.ViewHolder> {
    private List<Palette> palettes;

    // pass this list into the constructor of the adapter
    public PaletteExploreAdapter(List<Palette> palettes) {
        this.palettes = palettes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // inflate the custom layout
        View paletteView = inflater.inflate(R.layout.item_palette_explore, parent, false);
        // return a new ViewHolder
        ViewHolder viewHolder = new ViewHolder(paletteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Palette palette = palettes.get(position);
        holder.textView_name.setText(palette.getName());

        ArrayList<String> colors = palette.getColors();
        ArrayList<ImageView> views = new ArrayList<>();
        views.add(holder.color1);
        views.add(holder.color2);
        views.add(holder.color3);
        views.add(holder.color4);
        views.add(holder.color5);

        for (int i = 0; i < colors.size(); i++) {
            String string = colors.get(i);

            ArrayList<Integer> rgb = new ArrayList<>();
            int r = Integer.valueOf(string.substring(0,2), 16);
            int g = Integer.valueOf(string.substring(2,4), 16);
            int b = Integer.valueOf(string.substring(4,6), 16);
            rgb.add(r);
            rgb.add(g);
            rgb.add(b);

            views.get(i).setBackgroundColor(Color.rgb(r, g, b));
        }
    }

    @Override
    public int getItemCount() {
        return palettes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView_name;
        ImageView color1;
        ImageView color2;
        ImageView color3;
        ImageView color4;
        ImageView color5;
        LinearLayout linearLayout;
        Button button_save;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_explore_name);
            color1 = itemView.findViewById(R.id.color1_explore);
            color2 = itemView.findViewById(R.id.color2_explore);
            color3 = itemView.findViewById(R.id.color3_explore);
            color4 = itemView.findViewById(R.id.color4_explore);
            color5 = itemView.findViewById(R.id.color5_explore);
            linearLayout = itemView.findViewById(R.id.linearLayout_item_palette_explore);
            button_save = itemView.findViewById(R.id.button_explore_save);
            button_save.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

}

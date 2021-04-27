package com.example.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
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
    private int viewWidth;

    // pass this list into the constructor of the adapter
    public PaletteExploreAdapter(List<Palette> palettes, int viewWidth) {
        this.palettes = palettes;
        this.viewWidth = viewWidth;
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

        int width = viewWidth;
        int height = 525;
        int sectionWidth = width / palette.getColors().size();

        ArrayList<ArrayList<Integer>> colors = palette.getColors();
        ArrayList<ImageView> views = new ArrayList<>();
        views.add(holder.color1);
        views.add(holder.color2);
        views.add(holder.color3);
        views.add(holder.color4);
        views.add(holder.color5);

        for (int i = 0; i < colors.size(); i++) {
            Bitmap bitmap;
            Canvas canvas;

            bitmap = Bitmap.createBitmap(sectionWidth, height, Bitmap.Config.ARGB_8888);
            views.get(i).setImageBitmap(bitmap);
            canvas = new Canvas(bitmap);

            ArrayList<Integer> values = colors.get(i);
            int r = values.get(0);
            int g = values.get(1);
            int b = values.get(2);
            Paint paint = new Paint();
            paint.setColor(Color.rgb(r, g, b));

            int left = 0;
            int top = 0;
            int right = bitmap.getWidth();
            int bottom = bitmap.getHeight();

            Rect rect = new Rect(left, top, right, bottom);
            canvas.drawRect(rect, paint);
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

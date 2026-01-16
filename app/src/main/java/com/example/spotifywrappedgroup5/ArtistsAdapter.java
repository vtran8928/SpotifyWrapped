package com.example.spotifywrappedgroup5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder> {
    private List<String> artistsNames;
    private List<String> imageUrls;


    public ArtistsAdapter(List<String> artistsNames, List<String> imageUrls) {
        this.artistsNames = artistsNames;
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_item, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        String name = artistsNames.get(position);
        String imageUrl = imageUrls.get(position);
        holder.artistNameTextView.setText(name);
        Picasso.get().load(imageUrl).into(holder.artistImageView); // Using Picasso library to load images
    }

    @Override
    public int getItemCount() {
        return artistsNames.size();
    }

    public static class ArtistViewHolder extends RecyclerView.ViewHolder {
        TextView artistNameTextView;
        ImageView artistImageView;


        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            artistNameTextView = itemView.findViewById(R.id.artist_name);
            artistImageView = itemView.findViewById(R.id.artist_image);
        }
    }
}

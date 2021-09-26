package com.example.not_decided;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    private ArrayList<Trip_information> mtriplist;

    private BtnClickListener mListener;

    public interface BtnClickListener {
        void onBtnClick(int position);
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {

        public TextView name, email, time, phone, path;
        public Button send_message;
        public BtnClickListener mClickListener;
        public ImageView profileImageView;


        public TripViewHolder(@NonNull View itemView, BtnClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            time = itemView.findViewById(R.id.date);
            path = itemView.findViewById(R.id.path);
            send_message = itemView.findViewById(R.id.send_message);
            profileImageView = itemView.findViewById(R.id.picture);
            mClickListener = listener;
        }
    }

    public TripAdapter(ArrayList<Trip_information> triplist, BtnClickListener listener) {
        mtriplist = triplist;
        mListener = listener;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip, parent, false);
        TripViewHolder tvh = new TripViewHolder(v, mListener);
        return tvh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final TripViewHolder holder, final int position) {
        Trip_information trip = mtriplist.get(position);
        holder.name.setText(trip.getName());
        holder.time.setText(trip.getTime() + " " + trip.getDate());
        holder.email.setText(trip.getEmail());
        holder.phone.setText(trip.getPhone());
        holder.path.setText("From: " +  trip.getSource() + "\nTo: " + trip.getDestination());
        holder.send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mClickListener != null)
                    holder.mClickListener.onBtnClick(position);
            }
        });
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(trip.getUID()).child("Images").child("ProfilePicture.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Using "Picasso" (http://square.github.io/picasso/) after adding the dependency in the Gradle.
                // ".fit().centerInside()" fits the entire image into the specified area.
                // Finally, add "READ" and "WRITE" external storage permissions in the Manifest.
                Picasso.get().load(uri).fit().centerInside().into(holder.profileImageView);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mtriplist.size();
    }


}

package com.example.aplikasiabsensi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiabsensi.R;
import com.example.aplikasiabsensi.model.Absensi;

import java.util.List;

public class AbsensiAdapter extends RecyclerView.Adapter<AbsensiAdapter.AbsensiViewHolder> {
    private List<Absensi> absensiList;

    public AbsensiAdapter(List<Absensi> absensiList) {
        this.absensiList = absensiList;
    }

    @NonNull
    @Override
    public AbsensiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_absensi, parent, false);
        return new AbsensiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsensiViewHolder holder, int position) {
        Absensi absensi = absensiList.get(position);

        holder.tvTanggal.setText("Tanggal: " + absensi.getTanggal());
        holder.tvWaktu.setText("Waktu: " + absensi.getWaktu());
    }

    @Override
    public int getItemCount() {
        return absensiList.size();
    }

    public static class AbsensiViewHolder extends RecyclerView.ViewHolder {
        TextView tvTanggal;
        TextView tvWaktu;

        public AbsensiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvWaktu = itemView.findViewById(R.id.tvWaktu);
        }
    }
}
package com.example.spacecolony.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spacecolony.crewmembers.CrewMember;
import com.example.spacecolony.R;
import java.util.List;
import java.util.Locale;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewViewHolder> {
    private List<CrewMember> crewList;
    private OnItemClickListener listener;

    public interface OnItemClickListener { void onItemClick(CrewMember member, int position); }
    public CrewAdapter(List<CrewMember> crewList) { this.crewList = crewList; }
    public void setOnItemClickListener(OnItemClickListener listener) { this.listener = listener; }

    @NonNull
    @Override
    public CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CrewViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crew_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CrewViewHolder holder, int position) {
        CrewMember m = crewList.get(position);
        holder.txtName.setText(m.getName());
        holder.txtRole.setText(m.getType());
        holder.txtXP.setText(String.format(Locale.getDefault(), "EXP: %d", m.getExp()));
        holder.txtLv.setText(String.format(Locale.getDefault(), "Lv. %d", m.getLevel()));
        holder.txtStats.setText(String.format(Locale.getDefault(), "ENERGY: %d/%d | SKILL: %d | RES: %d",
                m.getEnergy(), m.getMaxEnergy(), m.getSkill(), m.getResilience()));
        holder.imgAvatar.setImageResource(m.getAvatarResource());
        holder.itemView.setOnClickListener(v -> { if (listener != null) listener.onItemClick(m, position); });
    }

    @Override
    public int getItemCount() { return crewList.size(); }

    static class CrewViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtRole, txtXP, txtLv, txtStats;
        ImageView imgAvatar;
        public CrewViewHolder(@NonNull View v) {
            super(v);
            txtName = v.findViewById(R.id.txt_name);
            txtRole = v.findViewById(R.id.txt_role);
            txtXP = v.findViewById(R.id.txt_xp);
            txtLv = v.findViewById(R.id.txt_lv);
            txtStats = v.findViewById(R.id.txt_stats);
            imgAvatar = v.findViewById(R.id.img_avatar);
        }
    }
}

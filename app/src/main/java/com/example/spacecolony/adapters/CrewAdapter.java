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

// link to the data from DataStorage to cardview
public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewViewHolder> {
    private List<CrewMember> crewList;

    public CrewAdapter(List<CrewMember> crewList) {
        this.crewList = crewList;
    }

    @NonNull
    @Override
    public CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crew_card, parent, false);
        return new CrewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewViewHolder holder, int position) {
        CrewMember member = crewList.get(position);

        holder.txtName.setText(member.getName());
        holder.txtRole.setText(member.getType());
        
        // XP and Level (Placeholder for Lv)
        holder.txtXP.setText(String.format(Locale.getDefault(), "XP: %d", member.getExp()));
        holder.txtLv.setText("Lv. 1");

        // stats logic
        String stats = String.format(Locale.getDefault(), "HP: %d/%d | ATK: %d | DEF: %d",
                member.getEnergy(), member.getMaxEnergy(), member.getSkill(), member.getResilience());
        holder.txtStats.setText(stats);

        // image avatar
        holder.imgAvatar.setImageResource(R.drawable.ic_launcher_background);
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    static class CrewViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtRole, txtXP, txtLv, txtStats;
        ImageView imgAvatar;

        public CrewViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_name);
            txtRole = itemView.findViewById(R.id.txt_role);
            txtXP = itemView.findViewById(R.id.txt_xp);
            txtLv = itemView.findViewById(R.id.txt_lv);
            txtStats = itemView.findViewById(R.id.txt_stats);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
        }
    }
}

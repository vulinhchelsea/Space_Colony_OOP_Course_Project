package com.example.spacecolony.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacecolony.R;
import com.example.spacecolony.crewmembers.CrewMember;
import com.example.spacecolony.util.Storage;

import java.util.List;

public class RecruitAdapter extends RecyclerView.Adapter<RecruitAdapter.RecruitViewHolder> {

    private List<CrewMember> candidates;
    private Context context;

    public RecruitAdapter(Context context, List<CrewMember> candidates) {
        this.context = context;
        this.candidates = candidates;
    }

    @NonNull
    @Override
    public RecruitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recruit_member, parent, false);
        return new RecruitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecruitViewHolder holder, int position) {
        CrewMember member = candidates.get(position);
        
        holder.txtName.setText(member.getName());
        holder.txtRole.setText(member.getType());
        holder.txtStats.setText(String.format("HP: %d | ATK: %d | DEF: %d",member.getMaxEnergy(), member.getSkill(), member.getResilience()));

        // set icon based on type (Placeholder logic)
        // holder.imgAvatar.setImageResource(...);

        holder.btnRecruit.setOnClickListener(v -> {
            Storage.addCrewMember(context, member);
            candidates.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, candidates.size());
            Toast.makeText(context, member.getName() + " has joined the colony!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return candidates.size();
    }

    public static class RecruitViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtRole, txtStats;
        ImageView imgAvatar;
        Button btnRecruit;

        public RecruitViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_recruit_name);
            txtRole = itemView.findViewById(R.id.txt_recruit_role);
            txtStats = itemView.findViewById(R.id.txt_recruit_stats);
            imgAvatar = itemView.findViewById(R.id.img_recruit_avatar);
            btnRecruit = itemView.findViewById(R.id.btn_recruit_action);
        }
    }
}

package com.example.spacecolony.adapters;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spacecolony.R;
import com.example.spacecolony.crewmembers.CrewMember;
import com.example.spacecolony.util.Storage;
import java.util.List;
import java.util.Locale;

public class RecruitAdapter extends RecyclerView.Adapter<RecruitAdapter.RecruitViewHolder> {
    private List<CrewMember> candidates;
    private Context context;
    private Runnable onRecruitCallback;

    public RecruitAdapter(Context context, List<CrewMember> candidates, Runnable onRecruitCallback) {
        this.context = context;
        this.candidates = candidates;
        this.onRecruitCallback = onRecruitCallback;
    }

    @NonNull
    @Override
    public RecruitViewHolder onCreateViewHolder(@NonNull ViewGroup p, int vt) {
        return new RecruitViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recruit_member, p, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecruitViewHolder h, int pos) {
        CrewMember m = candidates.get(pos);
        h.txtName.setText(m.getType()); // Show Type as Title instead of random name
        h.txtRole.setText("Base Stats");
        h.txtStats.setText(String.format(Locale.getDefault(), "ENERGY: %d | SKILL: %d | RES: %d", 
                m.getMaxEnergy(), m.getSkill(), m.getResilience()));
        h.imgAvatar.setImageResource(m.getAvatarResource());
        
        h.btnRecruit.setOnClickListener(v -> showNameInputDialog(m));
    }

    private void showNameInputDialog(CrewMember template) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Name your " + template.getType());

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        input.setHint("Enter name here");
        builder.setView(input);

        builder.setPositiveButton("Confirm", (dialog, which) -> {
            String name = input.getText().toString().trim();
            if (!name.isEmpty()) {
                // create a NEW instance with the chosen name
                CrewMember newMember = createCrewFromTemplate(template, name);
                Storage.addCrewMember(context, newMember);
                Toast.makeText(context, name + " has joined!", Toast.LENGTH_SHORT).show();
                if (onRecruitCallback != null) {
                    onRecruitCallback.run();
                }
            } else {
                Toast.makeText(context, "Name cannot be empty!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private CrewMember createCrewFromTemplate(CrewMember t, String name) {
        // Reflection-free way: check type and return new instance
        switch (t.getType()) {
            case "Medic": return new com.example.spacecolony.crewmembers.Medic(name);
            case "Pilot": return new com.example.spacecolony.crewmembers.Pilot(name);
            case "Engineer": return new com.example.spacecolony.crewmembers.Engineer(name);
            case "Scientist": return new com.example.spacecolony.crewmembers.Scientist(name);
            case "Soldier": return new com.example.spacecolony.crewmembers.Soldier(name);
            default: return new CrewMember(name, t.getType(), t.getSkill(), t.getResilience(), t.getMaxEnergy(), "");
        }
    }

    @Override
    public int getItemCount() { return candidates.size(); }

    static class RecruitViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtRole, txtStats;
        ImageView imgAvatar;
        Button btnRecruit;
        public RecruitViewHolder(@NonNull View v) {
            super(v);
            txtName = v.findViewById(R.id.txt_recruit_name);
            txtRole = v.findViewById(R.id.txt_recruit_role);
            txtStats = v.findViewById(R.id.txt_recruit_stats);
            imgAvatar = v.findViewById(R.id.img_recruit_avatar);
            btnRecruit = v.findViewById(R.id.btn_recruit_action);
        }
    }
}

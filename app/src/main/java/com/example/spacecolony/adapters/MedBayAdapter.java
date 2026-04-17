package com.example.spacecolony.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spacecolony.R;
import com.example.spacecolony.crewmembers.CrewMember;
import com.example.spacecolony.util.Storage;
import java.util.List;

public class MedBayAdapter extends RecyclerView.Adapter<MedBayAdapter.H> {
    private final List<CrewMember> list;
    private final Context ctx;
    private final Runnable callback;
    private boolean available;

    public MedBayAdapter(Context ctx, List<CrewMember> list, boolean available, Runnable callback) {
        this.ctx = ctx;
        this.list = list;
        this.available = available;
        this.callback = callback;
    }

    @NonNull @Override public H onCreateViewHolder(@NonNull ViewGroup p, int t) {
        return new H(LayoutInflater.from(ctx).inflate(R.layout.item_medbay_member, p, false));
    }

    @Override public void onBindViewHolder(@NonNull H h, int p) {
        CrewMember m = list.get(p);
        h.n.setText(m.getName());
        h.r.setText(m.getType());
        h.e.setText("ENERGY: " + m.getEnergy() + "/" + m.getMaxEnergy());
        h.i.setImageResource(m.getAvatarResource());
        
        View btn = h.itemView.findViewById(R.id.btn_medbay_heal);
        btn.setAlpha(available ? 1.0f : 0.5f);
        
        btn.setOnClickListener(v -> {
            if (!available) {
                Toast.makeText(ctx, "MedBay is cooling down...", Toast.LENGTH_SHORT).show();
                return;
            }
            m.setEnergy(m.getMaxEnergy());
            List<CrewMember> all = Storage.loadCrewMembers(ctx);
            for (int i = 0; i < all.size(); i++) if (all.get(i).getName().equals(m.getName())) all.set(i, m);
            Storage.saveCrewMembers(ctx, all);
            list.remove(p);
            notifyDataSetChanged();
            if (callback != null) callback.run();
        });
    }

    @Override public int getItemCount() { return list.size(); }

    static class H extends RecyclerView.ViewHolder {
        TextView n, r, e; ImageView i;
        H(View v) {
            super(v);
            n = v.findViewById(R.id.txt_medbay_name);
            r = v.findViewById(R.id.txt_medbay_role);
            e = v.findViewById(R.id.txt_medbay_energy);
            i = v.findViewById(R.id.img_medbay_avatar);
        }
    }
}

package com.example.creativematch.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creativematch.R;
import com.example.creativematch.listeners.UserListener;
import com.example.creativematch.models.ViewPagerItem;

import java.util.ArrayList;


public class VPAdapter extends RecyclerView.Adapter<VPAdapter.ViewHolder> {
    ArrayList<ViewPagerItem> viewPagerItemArrayList;
    private final UserListener userListener;
    private final String TAG = "Denna";
    public VPAdapter(ArrayList<ViewPagerItem> viewPagerItemArrayList, UserListener userListener) {
        this.viewPagerItemArrayList = viewPagerItemArrayList;
        this.userListener = userListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewpager_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d(TAG, "checkpoint1");
        ViewPagerItem viewPagerItem = viewPagerItemArrayList.get(position);
        Log.d(TAG, "checkpoint2");
        holder.imageView.setImageDrawable(viewPagerItem.imageID);
        Log.d(TAG, "checkpoint3");
        holder.tcName.setText(viewPagerItem.name);
        Log.d(TAG, "checkpoint4");
        holder.tvPro.setText(viewPagerItem.profession);
        Log.d(TAG, "checkpoint5");
        /*
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status
                viewPagerItem.setChecked(isChecked);
            }
        });

         */
        holder.layout.setOnClickListener(v -> userListener.onUserClicked(position));
        Log.d(TAG, "checkpoint6");

    }

    @Override
    public int getItemCount() {
        return viewPagerItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tcName, tvPro;
        //CheckBox checkBox;
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ivimage);
            tcName = itemView.findViewById(R.id.tvName);
            tvPro = itemView.findViewById(R.id.tvProfession);
            layout = itemView.findViewById(R.id.viewPager);
            //checkBox = (CheckBox) itemView.findViewById(R.id.checkBox2);
        }
    }
}

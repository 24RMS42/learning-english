package timer.fityfor.me.beginner.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import timer.fityfor.me.beginner.R;
import timer.fityfor.me.beginner.controllers.ViewController;
import timer.fityfor.me.beginner.entities.Animal;
import timer.fityfor.me.beginner.fragments.LearnFragment;
import timer.fityfor.me.beginner.interfacies.RecyclerViewOnClickListener;

/**
 * Created by Hovhannisyan.Karo on 25.08.2017.
 */

public class LearnAdapter extends RecyclerView.Adapter<LearnAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Animal> animalsData;
    private RecyclerViewOnClickListener recyclerViewOnClickListener;

    public LearnAdapter(RecyclerViewOnClickListener recyclerViewOnClickListener) {
        this.context = ViewController.getViewController().getContex();
        inflater = LayoutInflater.from(context);
        this.recyclerViewOnClickListener = recyclerViewOnClickListener;
    }

    public void setData(ArrayList<Animal> animalsData) {
        resetData();
        this.animalsData = animalsData;
        notifyDataSetChanged();
    }

    private void resetData() {
        this.animalsData = null;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = inflater.inflate(R.layout.item_learn, parent, false);
        MyViewHolder holder = new MyViewHolder(rowView);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Animal animal = animalsData.get(position);

        holder.ivImage.setImageDrawable(animal.getImageFromDrawable());
        holder.tvEName.setText(animal.geteName().replace("_", " "));
        holder.tvPName.setText(animal.getpName());
    }

    @Override
    public int getItemCount() {
        return animalsData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvEName;
        TextView tvPName;
        RelativeLayout rlEName;
        RelativeLayout rlPName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_learn_image);
            tvEName = itemView.findViewById(R.id.tv_learn_ename);
            tvPName = itemView.findViewById(R.id.tv_learn_pname);
            rlEName = itemView.findViewById(R.id.rl_eName);
            rlPName = itemView.findViewById(R.id.rl_pname);

            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (LearnFragment.canClick) {
                        LearnFragment.canClick = false;
                        recyclerViewOnClickListener.itemClicked(view, getAdapterPosition());
                    }
                }
            });

            rlEName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewOnClickListener.itemClicked(view, getAdapterPosition());
                }
            });

            rlPName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewOnClickListener.itemClicked(view, getAdapterPosition());
                }
            });
        }
    }
}
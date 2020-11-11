package com.peter.android.mymusicapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.request.RequestOptions;
import com.peter.android.mymusicapplication.R;
import com.peter.android.mymusicapplication.models.AudioBlogModel;

import java.util.List;

import io.gresse.hugo.vumeterlibrary.VuMeterView;

public class AudioBlogsRvAdapter extends RecyclerView.Adapter<AudioBlogsRvAdapter.AudioBlogViewHolder> {


    private final RequestOptions mRequestOptions;
    private List<AudioBlogModel> audioBlogModels;
    private Context context;
    private AudioBlogModel selectedaudioBlogModel;
    private int selectedPosition  =-1;

    public AudioBlogsRvAdapter(@NonNull List<AudioBlogModel> audioBlogModels,int selected, Context context,@NonNull OnItemClicked onClickListener) {
        this.audioBlogModels = audioBlogModels;
        this.context = context;
        mRequestOptions = new RequestOptions().placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image);
        this.onClickListener = onClickListener;
        if(selectedPosition !=-1){
            selectedaudioBlogModel=audioBlogModels.get(selectedPosition);
        }
    }

    @NonNull
    @Override
    public AudioBlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.audio_blog_item, parent, false);
        return new AudioBlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioBlogViewHolder holder, int position) {

        AudioBlogModel audioBlogModel = audioBlogModels.get(position);
        holder.view.setBackgroundColor(ContextCompat.getColor(context,android.R.color.white));
        if(position==selectedPosition||audioBlogModel.isSelected()||audioBlogModel.equals(selectedaudioBlogModel)){
            holder.name.setTextColor(ContextCompat.getColor(context,R.color.purple));
            holder.vuMeterView.setVisibility(View.VISIBLE);
            holder.vuMeterView.resume(true);
        }else{
            holder.name.setTextColor(ContextCompat.getColor(context,R.color.black));
            holder.vuMeterView.stop(true);
            holder.vuMeterView.setVisibility(View.GONE);
        }
        holder.name.setText(audioBlogModel.getAudioFileName());
        holder.size.setText(String.format("%s Mb", Math.floor(audioBlogModel.getAudioSize()/(1024*1024))));
        holder.year.setText(audioBlogModel.getReadableFormat());
        holder.keepListening.setChecked(audioBlogModel.isKeepListening());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle externally
                onClickListener.onItemClick(view,position);
            }
        });
        holder.year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle externally
                onClickListener.onItemClick(view,position);
            }
        });
        holder.size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle externally
                onClickListener.onItemClick(view,position);
            }
        });
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle externally
                onClickListener.onItemClick(view,position);
            }
        });
        holder.keepListening.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                audioBlogModel.setKeepListening(b);
                onClickListener.onItemClick(compoundButton,position);
            }
        });

//        // glide is a library for image loading and caching
//        Glide.with(context).load(carModel.getImage()).apply(mRequestOptions).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return audioBlogModels.size();
    }

    static class AudioBlogViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView size;
        TextView year;
        VuMeterView vuMeterView;
        CheckBox keepListening;
        View view;

        public AudioBlogViewHolder(@NonNull View itemView) {
            super(itemView);

            this.view = itemView;
            image = itemView.findViewById(R.id.audio_iv);
            name = itemView.findViewById(R.id.tv_name);
            size = itemView.findViewById(R.id.tv_size);
            year = itemView.findViewById(R.id.tv_date);
            keepListening = itemView.findViewById(R.id.keepPlaying_cb);
            vuMeterView = itemView.findViewById(R.id.play_state);
        }
    }

    //declare interface
    private OnItemClicked onClickListener;

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(View view,int position);
    }

    public void setSelected(int position){
        this.selectedaudioBlogModel = audioBlogModels.get(position);
        selectedaudioBlogModel.setSelected(true);
        this.selectedPosition = position;
        notifyDataSetChanged();
    }


}

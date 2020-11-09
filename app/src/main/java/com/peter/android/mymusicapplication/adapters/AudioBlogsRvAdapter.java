package com.peter.android.mymusicapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.request.RequestOptions;
import com.peter.android.mymusicapplication.R;
import com.peter.android.mymusicapplication.models.AudioBlogModel;

import java.util.List;

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
        if(position==selectedPosition||audioBlogModel.isSelected()||audioBlogModel.equals(selectedaudioBlogModel)){
            holder.layout.setBackgroundColor(ContextCompat.getColor(context,android.R.color.darker_gray));
        }else{
            holder.layout.setBackgroundColor(ContextCompat.getColor(context,android.R.color.white));
        }
        holder.name.setText(audioBlogModel.getAudioFileName());
        holder.size.setText(String.format("%s byte", audioBlogModel.getAudioSize()));
        holder.year.setText(audioBlogModel.getReadableFormat());
        holder.keepListening.setChecked(audioBlogModel.isKeepListening());
        holder.layout.setOnClickListener(new View.OnClickListener() {
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
        CheckBox keepListening;
        View layout ;

        public AudioBlogViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView;
            image = itemView.findViewById(R.id.audio_iv);
            name = itemView.findViewById(R.id.tv_name);
            size = itemView.findViewById(R.id.tv_size);
            year = itemView.findViewById(R.id.tv_date);
            keepListening = itemView.findViewById(R.id.keepPlaying_cb);
        }
    }

    //declare interface
    private OnItemClicked onClickListener;

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(View view,int position);
    }

    public void setSelected(AudioBlogModel audioBlogModel,int position){
        this.selectedaudioBlogModel = audioBlogModel;
        this.selectedPosition = position;
        notifyDataSetChanged();
    }


}

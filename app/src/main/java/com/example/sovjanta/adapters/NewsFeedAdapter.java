package com.example.sovjanta.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.sovjanta.R;
import com.example.sovjanta.activities.CustomTabActivity;
import com.example.sovjanta.models.NewsFeedAbstractModel;

import java.util.ArrayList;


public class NewsFeedAdapter extends RecyclerView.Adapter {
    public OnItemLongClickListener mItemLongClickListener;
    public OnItemClickListener mItemClickListener;
    public OnBottomReachedListener onBottomReachedListener;

    Context context;
    Object object = new Object();
    ArrayList<NewsFeedAbstractModel> menuList;



    public NewsFeedAdapter(Context context, ArrayList<NewsFeedAbstractModel> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_feed, viewGroup, false);

        return new ViewHolder(v);

    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (position == menuList.size() - 1){

            onBottomReachedListener.onBottomReached(position);

        }
        if (holder instanceof ViewHolder) {
            final NewsFeedAbstractModel name = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;
            genericViewHolder.tv_status.setText(name.getTv_status());
            genericViewHolder.tv_name.setText(name.getStory());
            genericViewHolder.imgView_proPic.setImageResource(name.getThumb());
            genericViewHolder.tv_time.setText(name.getTime());
            Glide.with(context)
                    //.load(mImageUri) // Load image from assets
                    .load(name.getUrl()) // Image URL
                    .centerCrop() // Image scale type
                    .crossFade()
                    .override(800,500) // Resize image
                    .placeholder(R.drawable.ic_newspaper) // Place holder image
                    .error(R.drawable.ic_newspaper) // On error image
                    .into(genericViewHolder.newsthumb); // Im
            genericViewHolder.like_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setType("text/plain");
                    whatsappIntent.setPackage("com.whatsapp");
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, "For more information please visit"+"\n"+name.getWebUrl());
                    try {
                        context.startActivity(whatsappIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(context, "Whatsapp not installed", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            genericViewHolder.share_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            genericViewHolder.save_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try
                    {
                        // TODO: This part does not work properly based on my test
                        Intent fbIntent = new Intent(Intent.ACTION_SEND);
                        fbIntent.setType("text/plain");
                        fbIntent.putExtra(Intent.EXTRA_TEXT,"For more information please visit"+" "+"\n");
                        fbIntent.putExtra(Intent.EXTRA_STREAM, name.getWebUrl());
                        fbIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                        fbIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        fbIntent.setComponent(new ComponentName("com.facebook.katana",
                                "com.facebook.composer.shareintent.ImplicitShareIntentHandler"));
                        context.startActivity(fbIntent);

                        return;
                    }
                    catch (Exception e)
                    {
                        // User doesn't have Facebook app installed. Try sharing through browser.
                    }
                }
            });
            genericViewHolder.btnSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCustomTab(name.getWebUrl());
                }
            });
            genericViewHolder.newsthumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openImage(name.getUrl());
                }
            });


            //genericViewHolder.imgView.setImageResource(name.getActivityIcon());
            // genericViewHolder.tvName.setText(name.getActivityName());
        }
    }
    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        this.onBottomReachedListener = onBottomReachedListener;
    }

    private void openImage(String thumb1) {
        String thumb=thumb1;
        Dialog builder = new Dialog(context);
        builder.setCancelable(true);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(context);
        Glide.with(context)
                //.load(mImageUri) // Load image from assets
                .load(thumb) // Image URL
                .centerCrop() // Image scale type
                .crossFade()
                .override(800,500) // Resize image
                .placeholder(R.drawable.ic_newspaper) // Place holder image
                .error(R.drawable.ic_newspaper) // On error image
                .into(imageView);

        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    private void openCustomTab(String url1) {
        String url=url1;
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
// set toolbar color and/or setting custom actions before invoking build()
// Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
        CustomTabsIntent customTabsIntent = builder.build();
        builder.setToolbarColor(ContextCompat.getColor(context,R.color.colorPrimary));

// and launch the desired Url with CustomTabsIntent.launchUrl()
        customTabsIntent.launchUrl(context, Uri.parse(url));
    }


    //    need to override this method
    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    void setOnItemLongClickListener(final OnItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }

    private NewsFeedAbstractModel getItem(int position) {
        return menuList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, ArrayList<NewsFeedAbstractModel> menulist);
    }
    public interface OnBottomReachedListener {

        void onBottomReached(int position);

    }
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position, ArrayList<NewsFeedAbstractModel> names);
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        ImageView like, share, save;
        RelativeLayout like_layout, save_layout, share_layout;
        private ImageView imgView_proPic, newsthumb;
        TextView tv_name,tv_status,tv_time;
        Button btnSeeMore;


        ViewHolder(final View itemView) {
            super(itemView);
            this.btnSeeMore=itemView.findViewById(R.id.btnSeeMore);
            this.tv_time=itemView.findViewById(R.id.tv_time);

            this.tv_status=itemView.findViewById(R.id.tv_status);
            this.imgView_proPic = itemView.findViewById(R.id.imgView_proPic);
            this.newsthumb = itemView.findViewById(R.id.newsthumb);
            tv_name=itemView.findViewById(R.id.tv_name);



            this.like_layout = itemView.findViewById(R.id.like_layout);
            this.save_layout = itemView.findViewById(R.id.save_layout);
            this.share_layout = itemView.findViewById(R.id.share_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), menuList);

                }
            });


          /* itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mItemLongClickListener.onItemLongClick(itemView, getAdapterPosition(), menuList);

                    return true;
                }
            });*/


        }
    }
}
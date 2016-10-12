package com.raywenderlich.galacticon;

/**
 * Created by joeng on 2016-10-12.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PhotoHolder> {

    private ArrayList<Photo> mPhotos;

    public RecyclerAdapter(ArrayList<Photo> Photos) {
        mPhotos = Photos;
    }

//    ViewHolder는 static inner class 로 지정해야하면 이는 나의 뷰를 참고하기 위해 사용한다.
    public static class PhotoHolder extends RecyclerView.ViewHolder implements ViewGroup.OnClickListener {

    private ImageView mItemImage;
    private TextView mItemDate;
    private TextView mItemDescription;
    private Photo mPhoto;

    private static final String PHOTO_KEY = "PHOTO";

    public PhotoHolder(View v) {
        super(v);

        mItemImage = (ImageView) v.findViewById(R.id.item_image);
        mItemDate = (TextView) v.findViewById(R.id.item_date);
        mItemDescription = (TextView) v.findViewById(R.id.item_description);
        v.setOnClickListener(this);
    }

    public void bindPhoto(Photo photo) {
        mPhoto = photo;
        Picasso.with(mItemImage.getContext()).load(photo.getUrl()).into(mItemImage);
        mItemDate.setText(photo.getHumanDate());
        mItemDescription.setText(photo.getExplanation());
    }

    @Override
    public void onClick(View v) {
        Context context = itemView.getContext();
        Intent showPhotoIntent = new Intent(context, PhotoActivity.class);
        showPhotoIntent.putExtra(PHOTO_KEY, mPhoto);
        context.startActivity(showPhotoIntent);
    }
}

    //만약에 PHOTOHOLDER가 이용불가능하다면 onCreateViewHolder에서 새로운것을 만들것이다.

    @Override
    public RecyclerAdapter.PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_row, parent, false);
        return new PhotoHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.PhotoHolder holder, int position) {

        Photo itemPhoto = mPhotos.get(position);
        holder.bindPhoto(itemPhoto);

    }

    //pretty simple and should be familiar from your work with ListViews or GridViews
    // The adapter will work out how many items to display, In this case you want the adapter to show evet photo you've downloaded from NASA's API

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }
}

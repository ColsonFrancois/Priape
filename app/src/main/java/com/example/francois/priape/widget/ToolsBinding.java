package com.example.francois.priape.widget;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.francois.priape.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Francois on 04/05/2016.
 *
 */
public class ToolsBinding {

    public static Resources res;

    @BindingAdapter("bind:pictureUrlProfessional")
    public static void pictureBinding(ImageView imageView, String pictureUrl) {
        imageView.setImageDrawable(null);
        if (pictureUrl != null && !pictureUrl.isEmpty()) {
            Picasso.with(imageView.getContext()).load(pictureUrl).fit().centerCrop().into(imageView);
        } else {
            imageView.setImageResource(R.drawable.ic_no_picture);
        }
    }
}
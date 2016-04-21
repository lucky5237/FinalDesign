package zjut.jianlu.breakfast.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.squareup.picasso.Picasso;

import java.io.File;

import zjut.jianlu.breakfast.R;

/**
 * Created by jianlu on 16/3/13.
 */
public class ScanPicPopWindow extends PopupWindow {

    private ImageView mImageView;

    public ScanPicPopWindow(Context context, String url) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_scan_image, null);
        mImageView = (ImageView) view.findViewById(R.id.iv_image);
        Picasso.with(context).load(url).into(mImageView);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing())
                    dismiss();
            }
        });


    }

    public ScanPicPopWindow(Context context, File file) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_scan_image, null);
        mImageView = (ImageView) view.findViewById(R.id.iv_image);
        Picasso.with(context).load(file).into(mImageView);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing())
                    dismiss();
            }
        });


    }


}

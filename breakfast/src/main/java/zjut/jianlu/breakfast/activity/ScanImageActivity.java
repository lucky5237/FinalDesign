package zjut.jianlu.breakfast.activity;

import android.content.Intent;
import android.os.Bundle;

import zjut.jianlu.breakfast.R;
import zjut.jianlu.breakfast.base.BaseActivity;

/**
 * Created by jianlu on 16/3/13.
 */
public class ScanImageActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent =getIntent();
        if (intent!=null){

        }
    }
}

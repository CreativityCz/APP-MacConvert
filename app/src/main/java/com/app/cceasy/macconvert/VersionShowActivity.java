package com.app.cceasy.macconvert;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by czchina on 2015/7/25.
 */
public class VersionShowActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_versionshow);

        versionShow();

    }

    public void versionShow (){
        //Intent intent = getIntent();
        //String messageVs = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        PackageInfo packageInfo = null;
        try{
            packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
        }catch (Exception e){e.printStackTrace();}

        TextView textView = (TextView) findViewById(R.id.version_name_text);
        textView.setText("版本：v"+ (packageInfo != null ? packageInfo.versionName : null));
    }
}

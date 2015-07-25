package com.app.cceasy.macconvert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by czchina on 2015/7/25.
 */
public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

    }

    public void appInfoShow(View view){

        Intent intent = new Intent(this,VersionShowActivity.class);

        startActivity(intent);
    }

    public void exitApp(View view){
        MainActivity.instance.finish();

        SettingActivity.this.finish();
    }

}

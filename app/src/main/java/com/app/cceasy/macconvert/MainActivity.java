package com.app.cceasy.macconvert;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    GridLayout gridLayout;
    String [] strs = new String[]{
            "0","1","2","3",
            "4","5","6","7",
            "8","9","A","B",
            "C","D","E","F"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridLayout = (GridLayout) findViewById(R.id.gridlt);

        EditText edit_text1 = (EditText) findViewById(R.id.edit_text1);
        EditText edit_text2 = (EditText) findViewById(R.id.edit_text2);
        EditText edit_text3 = (EditText) findViewById(R.id.edit_text3);
        edit_text1.setInputType(InputType.TYPE_NULL); //屏蔽软键盘弹出
        edit_text2.setInputType(InputType.TYPE_NULL); //屏蔽软键盘弹出
        edit_text3.setInputType(InputType.TYPE_NULL); //屏蔽软键盘弹出

        for(int i=0;i<16;i++){
            Button bn = new Button(this);
            bn.setText(strs[i]);
            bn.setId(i);
            bn.setTextSize(20);
            bn.setWidth(180);
            bn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText edit_text1 = (EditText) findViewById(R.id.edit_text1);
                    EditText edit_text2 = (EditText) findViewById(R.id.edit_text2);
                    EditText edit_text3 = (EditText) findViewById(R.id.edit_text3);
                    Button b = (Button) findViewById(v.getId());
                    //判断焦点，将键盘内容追加给已获得焦点的EditText --五星好评
                    if (edit_text1.isFocused()) {
                        edit_text1.append(b.getText());//内容追加
                        edit_text1.setError(null);//清除错误消息框
                    } else if (edit_text2.isFocused()) {
                        edit_text2.append(b.getText());
                        edit_text2.setError(null);
                    } else if (edit_text3.isFocused()) {
                        edit_text3.append(b.getText());
                        edit_text3.setError(null);
                    }
                }
            });


            GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(i/4),GridLayout.spec(i%4));
            params.setGravity(Gravity.FILL);
            //将键盘体填入网格布局
            gridLayout.addView(bn, params);
        }

    }

    public boolean getResult(View view){

        EditText edit_text1 = (EditText) findViewById(R.id.edit_text1);
        EditText edit_text2 = (EditText) findViewById(R.id.edit_text2);
        EditText edit_text3 = (EditText) findViewById(R.id.edit_text3);
        TextView result = (TextView) findViewById(R.id.textview_macEnd);
        if("".equals(edit_text1.getText().toString())){

            edit_text1.setError("请输入内容");
            return false;
        }else if("".equals(edit_text2.getText().toString())){
            edit_text2.setError("请输入内容");
            return false;
        }else if("".equals(edit_text3.getText().toString())){
            edit_text3.setError("请输入内容");
            return false;
        }

        Long macStart = null;
        Long num1 = null;
        Long num2 = null;
        try{
            macStart = Long.parseLong(edit_text1.getText().toString(),16);
        }catch (Exception e){
            edit_text1.setError("输入有误");
            return false;
        }

        try{
            num1 = Long.parseLong(edit_text2.getText().toString(),10);
        }catch (Exception e){
            edit_text2.setError("输入有误");
            return false;
        }
        try{
            num2 = Long.parseLong(edit_text3.getText().toString(),10);
        }catch (Exception e){
            edit_text3.setError("输入有误");
            return false;
        }

        result.setTextSize(20);
        result.setTextColor(Color.BLACK);
        result.setText("0x" + macStart.toHexString(macStart+num1*num2-1));
        //result.setText(edit_text1.getText().toString()+edit_text2.getText().toString()+edit_text3.getText().toString());
        return true;
    }
    public void clearData(View view){
        EditText edit_text1 = (EditText) findViewById(R.id.edit_text1);
        EditText edit_text2 = (EditText) findViewById(R.id.edit_text2);
        EditText edit_text3 = (EditText) findViewById(R.id.edit_text3);
        TextView result = (TextView) findViewById(R.id.textview_macEnd);
        edit_text1.setText("");
        edit_text2.setText("");
        edit_text3.setText("");
        result.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
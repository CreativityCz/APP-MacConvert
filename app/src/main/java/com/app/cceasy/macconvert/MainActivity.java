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

        /** ======== 输入框初始化 ===========*/
        EditText edit_text1 = (EditText) findViewById(R.id.edit_text1);
        EditText edit_text2 = (EditText) findViewById(R.id.edit_text2);
        EditText edit_text3 = (EditText) findViewById(R.id.edit_text3);
        edit_text1.setInputType(InputType.TYPE_NULL); //屏蔽软键盘弹出
        edit_text2.setInputType(InputType.TYPE_NULL); //屏蔽软键盘弹出
        edit_text3.setInputType(InputType.TYPE_NULL); //屏蔽软键盘弹出
        //edit_text3.setInputType(InputType.TYPE_CLASS_NUMBER);
        edit_text1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText edit_text1 = (EditText) findViewById(R.id.edit_text1);
                if(hasFocus){

                }else {
                    edit_text1.setError(null);
                }
            }
        });
        edit_text2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText edit_text2 = (EditText) findViewById(R.id.edit_text2);
                if(hasFocus){

                }else {
                    edit_text2.setError(null);
                }
            }
        });
        edit_text3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText edit_text3 = (EditText) findViewById(R.id.edit_text3);
                if(hasFocus){

                }else {
                    edit_text3.setError(null);
                }
            }
        });

        /**===== 将16个键盘button填入gridLayout网格布局   =======*/
        gridLayout = (GridLayout) findViewById(R.id.gridlt);
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
                    //判断焦点（是否获得了焦点）          ==五星好评
                    //   检查键入的内容（是否合格）
                    //     将键入的内容追加给已获得焦点的EditText
                    //
                    if (edit_text1.isFocused()) {
                        try {
                            Long.parseLong(b.getText().toString(), 16);  //检查是否可转化为十进制，即检查输入是否是十进制数
                            edit_text1.append(b.getText());//内容追加
                            edit_text1.setError(null);//清除错误消息框
                        } catch (Exception e) {
                            edit_text1.setError("请输入0~~F之内的十六进制数.");
                        }
                    } else if (edit_text2.isFocused()) {
                        try {
                            Long.parseLong(b.getText().toString(), 10);  //检查是否可转化为十进制，即检查输入是否是十进制数
                            edit_text2.append(b.getText());
                            edit_text2.setError(null);
                        } catch (Exception e) {
                            edit_text2.setError("请输入0~9之内的十进制数.");
                        }
                    } else if (edit_text3.isFocused()) {
                        try {
                            Long.parseLong(b.getText().toString(), 10);  //检查是否可转化为十进制，即检查输入是否是十进制数
                            edit_text3.append(b.getText());
                            edit_text3.setError(null);
                        } catch (Exception e) {
                            edit_text3.setError("请输入0~9之内的十进制数.");
                        }
                    }
                }
            });

            GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(i/4),GridLayout.spec(i%4));
            params.setGravity(Gravity.FILL);
            //将键盘体填入网格布局
            gridLayout.addView(bn, params);
        }

    }

    /**----- 计算末端MAC地址 ------------*/
    public void getResult(View view){

        EditText edit_text1 = (EditText) findViewById(R.id.edit_text1);
        EditText edit_text2 = (EditText) findViewById(R.id.edit_text2);
        EditText edit_text3 = (EditText) findViewById(R.id.edit_text3);
        TextView result = (TextView) findViewById(R.id.textview_macEnd);
        //清除错误消息框
        edit_text1.setError(null);
        edit_text2.setError(null);
        edit_text3.setError(null);

        //对所有输入框的文本内容进行检查--是否为空
        if("".equals(edit_text1.getText().toString())){

            edit_text1.setError("请输入内容");
            return;
        }else if("".equals(edit_text2.getText().toString())){
            edit_text2.setError("请输入内容");
            return;
        }else if("".equals(edit_text3.getText().toString())){
            edit_text3.setError("请输入内容");
            return;
        }
        //对所有输入框的 文本内容 进行检查--内容是否合格
        Long macStart;
        Long num1;
        Long num2;
        try{
            macStart = Long.parseLong(edit_text1.getText().toString(),16);
        }catch (Exception e){
            edit_text1.setError("输入有误:输入过多");
            return;
        }

        try{
            num1 = Long.parseLong(edit_text2.getText().toString(),10);
        }catch (Exception e){
            edit_text2.setError("输入有误：输入过多");
            return;
        }
        try{
            num2 = Long.parseLong(edit_text3.getText().toString(),10);
        }catch (Exception e){
            edit_text3.setError("输入有误：输入过多");
            return;
        }
        //得出结果并打印
        result.setTextSize(20);
        result.setTextColor(Color.BLACK);
        result.setText("0x" + Long.toHexString(macStart+num1*num2-1));
        //result.setText(edit_text1.getText().toString()+edit_text2.getText().toString()+edit_text3.getText().toString());

    }

    /**--------清空所有输入，清空计算结果、清空所有错误消息框 -----------*/
    public void clearData(View view){
        EditText edit_text1 = (EditText) findViewById(R.id.edit_text1);
        EditText edit_text2 = (EditText) findViewById(R.id.edit_text2);
        EditText edit_text3 = (EditText) findViewById(R.id.edit_text3);
        TextView result = (TextView) findViewById(R.id.textview_macEnd);
        edit_text1.setText("");
        edit_text2.setText("");
        edit_text3.setText("");
        result.setText("");

        edit_text1.setError(null);
        edit_text2.setError(null);
        edit_text3.setError(null);

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
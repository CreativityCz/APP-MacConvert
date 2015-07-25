package com.app.cceasy.macconvert;
/**以后的大版本号更新基于以下需求，或重大bug
 * 需求：1、增加退格-----更改布局 ---OK--version 1.1  --2015-7-25
 *      2、增加setting--右上角
 *      3、增加分享------分享计算结果至QQ好友或电脑
 * */

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import java.lang.reflect.Method;


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

        /**：
         * 1、EditText 输入框初始化
         *      EditText屏蔽软键盘弹出--光标依然能闪动
         *      edit_text2、edit_text3 输入框只允许输入数字
         *      EditText失去焦点时，马上去掉错误提示消息标志
         * 2、GridLayout网格布局 ：：将16个键盘button填入
         */
        //------------>

        /** ========EditText 输入框初始化 ===========*/
        EditText edit_text1 = (EditText) findViewById(R.id.edit_text1);
        EditText edit_text2 = (EditText) findViewById(R.id.edit_text2);
        EditText edit_text3 = (EditText) findViewById(R.id.edit_text3);
        //EditText屏蔽软键盘弹出--光标依然能闪动
        hideSoftInputMethod(edit_text1);
        hideSoftInputMethod(edit_text2);
        hideSoftInputMethod(edit_text3);
        //edit_text2、edit_text3 输入框只允许输入数字
        edit_text2.setInputType(InputType.TYPE_CLASS_NUMBER);
        edit_text3.setInputType(InputType.TYPE_CLASS_NUMBER);
        //EditText失去焦点时，马上去掉错误提示消息标志
        edit_text1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText edit_text1 =  (EditText) findViewById(v.getId());
                if(hasFocus){

                }else {
                    edit_text1.setError(null);
                }
            }
        });
        edit_text2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText edit_text2 = (EditText) findViewById(v.getId());
                if(hasFocus){

                }else {
                    edit_text2.setError(null);
                }
            }
        });
        edit_text3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText edit_text3 = (EditText) findViewById(v.getId());
                if(hasFocus){

                }else {
                    edit_text3.setError(null);
                }
            }
        });

        /**===== GridLayout网格布局 ：：将16个键盘button填入=======*/
        gridLayout = (GridLayout) findViewById(R.id.gridlt);
        for(int i=0;i<16;i++){
            Button bn = new Button(this);
            bn.setText(strs[i]);
            //一定要设置Id，因为后面有调用到ID，v.getID()。（否则会有异常-找不到ID）
            bn.setId(i);
            bn.setTextSize(20);
            bn.setBackgroundResource(R.drawable.keyboardbutton_shape);
            bn.setWidth(160);

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
                            Long.parseLong(b.getText().toString(), 16);  //检查是否可转化为十六进制，即检查输入是否是十六进制数
                            edit_text1.append(b.getText());//内容追加
                            edit_text1.setError(null);//清除错误消息框
                        } catch (Exception e) {
                            edit_text1.setError("请输入0~~F之内的十六进制数.");
                        }
                    }else if (edit_text2.isFocused()) {
                        edit_text2.append(b.getText());
                        edit_text2.setError(null);
                    } else if (edit_text3.isFocused()) {
                        edit_text3.append(b.getText());
                        edit_text3.setError(null);
                    }
                }
            });
            //GridLayout.LayoutParams(第a行，第b列)
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(i/4+1),GridLayout.spec(i%4));
            params.setGravity(Gravity.FILL);

            //将键盘体填入网格布局
            gridLayout.addView(bn, params);
        }
    }

    /**----- 退格 ----------------------*/
    public void backGrid(View view){
        EditText edit_text1 = (EditText) findViewById(R.id.edit_text1);
        EditText edit_text2 = (EditText) findViewById(R.id.edit_text2);
        EditText edit_text3 = (EditText) findViewById(R.id.edit_text3);
        if (edit_text1.isFocused()) {
            //动作按下
            int action = KeyEvent.ACTION_DOWN;
            //code:删除，其他code也可以，例如 code = 0
            int code = KeyEvent.KEYCODE_DEL;
            KeyEvent event = new KeyEvent(action, code);
            edit_text1.onKeyDown(KeyEvent.KEYCODE_DEL, event); //抛给系统处理了
        }else if (edit_text2.isFocused()) {
            //动作按下
            int action = KeyEvent.ACTION_DOWN;
            //code:删除，其他code也可以，例如 code = 0
            int code = KeyEvent.KEYCODE_DEL;
            KeyEvent event = new KeyEvent(action, code);
            edit_text2.onKeyDown(KeyEvent.KEYCODE_DEL, event); //抛给系统处理了
        } else if (edit_text3.isFocused()) {
            //动作按下
            int action = KeyEvent.ACTION_DOWN;
            //code:删除，其他code也可以，例如 code = 0
            int code = KeyEvent.KEYCODE_DEL;
            KeyEvent event = new KeyEvent(action, code);
            edit_text3.onKeyDown(KeyEvent.KEYCODE_DEL, event); //抛给系统处理了
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
            //获取焦点--让用户可以直接输入---更加人性化
            edit_text1.requestFocus();
            return;
        }else if("".equals(edit_text2.getText().toString())){
            edit_text2.setError("请输入内容");
            //获取焦点--让用户可以直接输入---更加人性化
            edit_text2.requestFocus();
            return;
        }else if("".equals(edit_text3.getText().toString())){
            edit_text3.setError("请输入内容");
            //获取焦点--让用户可以直接输入---更加人性化
            edit_text3.requestFocus();
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


    /** ==== 隐藏系统键盘 ======*/
    //用这个方法关闭系统键盘就不会出现光标消失的问题了
    public void hideSoftInputMethod(EditText ed){

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        String methodName = null;
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        if(currentVersion >= 16){
            // 4.2
            methodName = "setShowSoftInputOnFocus";  //
        }else if(currentVersion >= 14){
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }

        if(methodName == null){
            //最低级最不济的方式，这个方式会把光标给屏蔽
            ed.setInputType(InputType.TYPE_NULL);
        }else{
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
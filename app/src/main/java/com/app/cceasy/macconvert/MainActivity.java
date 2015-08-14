package com.app.cceasy.macconvert;
/**以后的大版本号更新基于以下需求，或重大bug
 * 需求：1、增加退格-----更改布局 ---OK--version 1.1.1 --稳定版本--可投入使用 --2015-7-25---
 *      2、增加setting--右上角
 *          改动：2.1退格、计算、重置三个按钮的android:layout_width="0dp" 设置为0dp， ok
 *                    ---->那么Button的宽度就会按周围控件的比例进行自动适配，保证了键盘阵的均匀
 *               2.2---已添加设置item
 *               2.3---总共3个activity，测试无误--version 1.2 --稳定版本--可投入使用 --2015-7-25---
 *      3、增加分享------分享计算结果至QQ好友或电脑
 * */

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
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
import android.widget.Toast;

import java.lang.reflect.Method;
import java.security.spec.ECField;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.app.cceasy.MESSAGE";
    private String macStartPreToShare = null;
    private String num1PreToShare;
    private String num2PreToShare;
    private String macEndPreToShare = null;

    public static MainActivity instance = null;

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
        instance = this;

        /** ========EditText 输入框初始化 ===========*/
        /**
        * 1、EditText 输入框初始化
        *  EditText屏蔽软键盘弹出--光标依然能闪动
        *  edit_text2、edit_text3 输入框只允许输入数字
        **/
        EditText edit_text1 = (EditText) findViewById(R.id.edit_text1);
        EditText edit_text2 = (EditText) findViewById(R.id.edit_text2);
        EditText edit_text3 = (EditText) findViewById(R.id.edit_text3);
        EditText result = (EditText) findViewById(R.id.textview_macEnd);
        //EditText屏蔽软键盘弹出--光标依然能闪动
        hideSoftInputMethod(edit_text1);
        hideSoftInputMethod(edit_text2);
        hideSoftInputMethod(edit_text3);
        //屏蔽软键盘弹出 - 光标不闪动
        result.setInputType(InputType.TYPE_NULL);
        //edit_text2、edit_text3 输入框只允许输入数字
        edit_text2.setInputType(InputType.TYPE_CLASS_NUMBER);
        edit_text3.setInputType(InputType.TYPE_CLASS_NUMBER);

        /**数据恢复--------------*/
        SharedPreferences sharedPreferences = getSharedPreferences("mydata",MODE_PRIVATE);
        macStartPreToShare = sharedPreferences.getString("macStartPreToShare","");
        num1PreToShare = sharedPreferences.getString("num1PreToShare","");
        num2PreToShare = sharedPreferences.getString("num2PreToShare","");
        macEndPreToShare = sharedPreferences.getString("macEndPreToShare", "");
        edit_text1.setText(macStartPreToShare);
        edit_text2.setText(num1PreToShare);
        edit_text3.setText(num2PreToShare);
        result.setText(macEndPreToShare);
        //Toast toast = Toast.makeText(this,"恢复完成.",Toast.LENGTH_SHORT);
        //toast.setGravity(Gravity.CENTER, 0, 0);
        //toast.show();
        /**：
         * 1、EditText失去焦点时，马上去掉错误提示消息标志
         * 2、GridLayout网格布局 ：：将16个键盘button填入
         */
        //------------>
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
                        edit_text1.setError(null);//清除错误消息框

                        //光标处插入文字
                        int index = edit_text1.getSelectionStart();
                        if(index >= edit_text1.getText().length()){
                            edit_text1.append(b.getText());//内容追加
                        }else {
                            Editable editable = edit_text1.getEditableText();
                            editable.insert(index,b.getText());
                        }

                    }else if (edit_text2.isFocused()) {
                        edit_text2.setError(null);

                        //光标处插入文字
                        int index = edit_text2.getSelectionStart();
                        if(index >= edit_text2.getText().length()){
                            edit_text2.append(b.getText());//内容追加
                        }else {
                            Editable editable = edit_text2.getEditableText();
                            editable.insert(index,b.getText());
                        }

                    } else if (edit_text3.isFocused()) {
                        edit_text3.setError(null);

                        //光标处插入文字
                        int index = edit_text3.getSelectionStart();
                        if(index >= edit_text3.getText().length()){
                            edit_text3.append(b.getText());//内容追加
                        }else {
                            Editable editable = edit_text3.getEditableText();
                            editable.insert(index,b.getText());
                        }
                        edit_text3.append(b.getText());
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
        EditText textview_macEnd = (EditText) findViewById(R.id.textview_macEnd);
        //清除错误消息框
        edit_text1.setError(null);
        edit_text2.setError(null);
        edit_text3.setError(null);
        textview_macEnd.setError(null);

        String macStartStr = edit_text1.getText().toString();
        String num1str = edit_text2.getText().toString();
        String num2str = edit_text3.getText().toString();

        //对所有输入框的文本内容进行检查--是否为空
        if("".equals(macStartStr)){
            edit_text1.setError("请输入内容");
            //获取焦点--让用户可以直接输入---更加人性化
            edit_text1.requestFocus();
            return;
        }else if("".equals(num1str)){
            edit_text2.setError("请输入内容");
            //获取焦点--让用户可以直接输入---更加人性化
            edit_text2.requestFocus();
            return;
        }else if("".equals(num2str)){
            edit_text3.setError("请输入内容");
            //获取焦点--让用户可以直接输入---更加人性化
            edit_text3.requestFocus();
            return;
        }

        //计算结果
        Long macStart;
        Long num1;
        Long num2;
        String resultStr;
        try{
            macStart = Long.parseLong(macStartStr,16);
            num1 = Long.parseLong(num1str,10);
            num2 = Long.parseLong(num2str,10);

            resultStr = Long.toHexString(macStart + num1 * num2 - 1);
        }catch (Exception e){
            Toast toast = Toast.makeText(this, "无法计算.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }

        //mac地址字符串长度整理
        if("ERROR".equals(macStartStr = macStrLengthSet(macStartStr,12))){
            return;
        }
        if ("ERROR".equals(resultStr = macStrLengthSet(resultStr,12))){
            return;
        }
        //输出
        macStartPreToShare = macStartStr;
        num1PreToShare = num1str;
        num2PreToShare = num2str;
        macEndPreToShare = resultStr;


        //显示
        textview_macEnd.setTextSize(20);
        textview_macEnd.setTextColor(Color.BLACK);
        textview_macEnd.setText(resultStr);
        textview_macEnd.requestFocus();

        edit_text1.setText(macStartStr);
    }


    public String macStrLengthSet(String str,int lengthPreset){
        if(null == str) return null;
        if(str.length() > lengthPreset){
            Toast toast = Toast.makeText(this,"温馨提示：Mac地址长度已超过"+lengthPreset,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return "ERROR";
        }else if (str.length() == lengthPreset){
        }else {
            while (str.length() < lengthPreset){
                str = "0"+ str;
            }
        }
        return str;
    }

    /**--------清空所有输入，清空计算结果、清空所有错误消息框 -----------*/
    public void clearData(View view){
        EditText edit_text1 = (EditText) findViewById(R.id.edit_text1);
        EditText edit_text2 = (EditText) findViewById(R.id.edit_text2);
        EditText edit_text3 = (EditText) findViewById(R.id.edit_text3);
        TextView textview_macEnd = (TextView) findViewById(R.id.textview_macEnd);
        edit_text1.setText("");
        edit_text2.setText("");
        edit_text3.setText("");
        textview_macEnd.setText("");

        edit_text1.setError(null);
        edit_text2.setError(null);
        edit_text3.setError(null);
        textview_macEnd.setError(null);

        edit_text1.requestFocus();

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


    // 在这个activity中判断是否有系统按键事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 如果是手机上的返回键

        }
        return super.onKeyDown(keyCode, event);
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
            Intent intent = new Intent(this,SettingActivity.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.action_share){
            String macStrShare = "0x"+macStartPreToShare+">>"+num1PreToShare+">>"+num2PreToShare+">>"+"0x"+macEndPreToShare;
            if(null != macEndPreToShare){
                Intent intent=new Intent(Intent.ACTION_SEND);

                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT, macStrShare);
                intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(Intent.createChooser(intent, "请选择"));
            }
         }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        SharedPreferences sharedPreferences = getSharedPreferences("mydata",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("macStartPreToShare",macStartPreToShare);
        editor.putString("num1PreToShare",num1PreToShare);
        editor.putString("num2PreToShare",num2PreToShare);
        editor.putString("macEndPreToShare",macEndPreToShare);

        editor.commit();

        super.onDestroy();
    }
}

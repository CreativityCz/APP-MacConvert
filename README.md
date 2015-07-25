# APP-MacConvert
APP-MacConvert  -->git

/**以后的大版本号更新基于以下需求，或重大bug
 * 需求：1、增加退格-----更改布局 ---OK--version 1.1  --2015-7-25
 *      2、增加setting--右上角
 *      3、增加分享------分享计算结果至QQ好友或电脑
 * */
 
### version 1.1 主界面基本确定
![image](https://github.com/CreativityCz/APP-MacConvert/blob/master/static/image/Screenshot_2015-07-25-10-28-16.jpeg)

### version 1.2 左上角增加了设置，能activity切换
增加setting--右上角
 *   2.1退格、计算、重置三个按钮的android:layout_width="0dp" 设置为0dp， ok
 *      ---->那么Button的宽度就会按周围控件的比例进行自动适配，保证了键盘阵的均匀
 *   2.2---已添加设置item
 *   2.3---总共3个activity，测试无误--version 1.2 --稳定版本--可投入使用 --2015-7-25---
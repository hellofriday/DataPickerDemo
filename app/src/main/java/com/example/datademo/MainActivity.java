package com.example.datademo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.utils.MeasureUtil;
import cn.aigestudio.datepicker.views.DatePicker;

public class MainActivity extends AppCompatActivity {

    private float angle = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        SignInBo signInBo = new SignInBo();
        ArrayList<String> needSignInDays = new ArrayList<>();

        needSignInDays.add("2017-10-1");
        needSignInDays.add("2017-10-2");
        needSignInDays.add("2017-10-3");
        needSignInDays.add("2017-10-4");
        needSignInDays.add("2017-10-5");
        needSignInDays.add("2017-10-6");
        needSignInDays.add("2017-10-7");
        needSignInDays.add("2017-10-8");
        needSignInDays.add("2017-10-9");
        needSignInDays.add("2017-10-10");
        needSignInDays.add("2017-10-11");
        needSignInDays.add("2017-10-17");
        needSignInDays.add("2017-10-18");
        needSignInDays.add("2017-10-19");
        signInBo.setNeedSignInDays(needSignInDays);

        ArrayList<String> hasSignedInDays = new ArrayList<>();
        hasSignedInDays.add("2017-10-1");
        hasSignedInDays.add("2017-10-2");
        hasSignedInDays.add("2017-10-3");

        signInBo.setHasSignInDays(hasSignedInDays);

        drawDatePicker(signInBo);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 绘制签到日历
     * @param signInBo 封装已经签到的日期和需要签到的日期
     */
    private void drawDatePicker(@NonNull final SignInBo signInBo){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH) + 1;

        final DatePicker picker = new DatePicker(MainActivity.this);
        picker.setDate(year, month);
        picker.setMode(DPMode.SINGLE);
        picker.setHolidayDisplay(false);
        picker.setFestivalDisplay(false);
        picker.setDeferredDisplay(false);

        DPCManager.getInstance().setDecorBG(signInBo.getNeedSignInDays());

        picker.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorBG(Canvas canvas, Rect rect, Paint paint, String data) {
                paint.setColor(ContextCompat.getColor(MainActivity.this, R.color.bg_gray));
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(6f);
                canvas.drawRect(rect, paint);

                for (String hasSignedInDay : signInBo.getHasSignInDays()) {
                    if (data.equals(hasSignedInDay)){
                        paint.setColor(ContextCompat.getColor(MainActivity.this, R.color.bg_cyne));
                        paint.setStyle(Paint.Style.STROKE);
                        canvas.drawCircle(rect.centerX(), rect.centerY(), MeasureUtil.dp2px(MainActivity.this, 17), paint);

                    }
                }

                //恢复画笔
                paint.setStrokeWidth(1f);
                paint.setStyle(Paint.Style.FILL);

            }
        });

        /**
         * 日历点击时间处理
         *
         */
        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                if (signInBo.getNeedSignInDays().contains(date)){
                    if (signInBo.getHasSignInDays().contains(date)){
                        Toast.makeText(MainActivity.this, date + "已签到", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, date + "未签到", Toast.LENGTH_LONG).show();
                    }
                } else {
                    //TODO 不处理

                }
            }
        });

        RelativeLayout dataPickerContainer = (RelativeLayout) findViewById(R.id.data_picker_container);
        dataPickerContainer.addView(picker);

    }


}

package soul.listener.com.imeipro;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Method;

import static android.os.Build.UNKNOWN;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView) findViewById(R.id.tv_imei);
        Button imei  = (Button) findViewById(R.id.btn_getimei);

        imei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText(getIMEI(MainActivity.this));
            }
        });

        Button imei1 = (Button) findViewById(R.id.btn_getimei1);

        imei1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText(getIMEI1(MainActivity.this));
            }
        });

        Button imei2 = (Button) findViewById(R.id.btn_getimei2);

        imei2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText(getIMEI2(MainActivity.this));
            }
        });

    }

    /**获取MeID号*/
    public static String getIMEI(Context context) {
        try {
            TelephonyManager teleMgr;
            teleMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return teleMgr.getDeviceId();
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**通过反射来获取双卡imei1号*/
    public static String getIMEI1(Context context) {
        try {
            TelephonyManager teleMgr;
            teleMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class clazz = teleMgr.getClass();
            Method getImei=clazz.getDeclaredMethod("getImei",int.class);//(int slotId)
            return (String) getImei.invoke(teleMgr, 0);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**通过反射来获取双卡imei2号*/
    public static String getIMEI2(Context context) {
        try {
            TelephonyManager teleMgr;
            teleMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class clazz = teleMgr.getClass();
            Method getImei=clazz.getDeclaredMethod("getImei",int.class);//(int slotId)
            return (String) getImei.invoke(teleMgr, 1);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }
}

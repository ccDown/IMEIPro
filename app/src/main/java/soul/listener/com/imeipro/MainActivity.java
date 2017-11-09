package soul.listener.com.imeipro;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
                tv.setText(getIMEI(getApplicationContext()));
            }
        });

        Button imei1 = (Button) findViewById(R.id.btn_getimei1);

        imei1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText(getIMEI1(getApplicationContext()));
            }
        });

        Button imei2 = (Button) findViewById(R.id.btn_getimei2);

        imei2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText(getIMEI2(getApplicationContext()));
            }
        });

        final Button btn_getPermission = (Button) findViewById(R.id.btn_getPermission);
        btn_getPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPermission();
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
            Log.e("MainActivity","Failed to get the hw info." + e);
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
            Log.e("MainActivity","Failed to get the hw info." + e);
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
            Log.e("MainActivity","Failed to get the hw info." + e);
            return UNKNOWN;
        }
    }

    public void getPermission(){
        //Android6.0需要动态获取权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
        }
    }
    /**
     *加个获取权限的监听
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        if (requestCode == 0 && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(),"权限成功",Toast.LENGTH_SHORT).show();
        }
    }
}

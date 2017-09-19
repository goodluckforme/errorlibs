package much.com.errorlibs;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import much.com.errorlibs.aop.CheckAffair;
import much.com.errorlibs.aop.CheckCrash;
import much.com.errorlibs.aop.CheckLog;
import much.com.errorlibs.aop.CheckLogin;
import much.com.errorlibs.aop.CheckNULL;
import much.com.errorlibs.aop.CheckNet;
import much.com.errorlibs.aop.CheckParam;
import much.com.errorlibs.aop.CheckPassword;
import much.com.errorlibs.aop.CheckPermission;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText pswEditText;

    @CheckParam
    //TODO 不成功
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.check_net).setOnClickListener(this);
        findViewById(R.id.check_permisson).setOnClickListener(this);
        findViewById(R.id.check_log).setOnClickListener(this);
        findViewById(R.id.check_crash).setOnClickListener(this);
        findViewById(R.id.check_safe).setOnClickListener(this);
        findViewById(R.id.check_affair).setOnClickListener(this);
        findViewById(R.id.check_login).setOnClickListener(this);
        findViewById(R.id.check_password).setOnClickListener(this);
        findViewById(R.id.check_param).setOnClickListener(this);
        pswEditText = findViewById(R.id.check_password_ed);
        
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_net:
                checkNet();
                break;
            case R.id.check_permisson:
                checkPerssion();
                break;
            case R.id.check_log:
                checkLog();
                break;
            case R.id.check_crash:
                try {
                    checkCrash("处理异常");
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.check_safe:
                checkSafe(this, "", "");
                break;
            case R.id.check_affair:
                checkAffair();
                break;
            case R.id.check_login:
                checkLogin();
                break;
            case R.id.check_password:
                checkPcassword(pswEditText.getText().toString());
                break;
            case R.id.check_param:
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_LONG).show();
                break;
        }
    }


    @CheckPassword
    private void checkPcassword(String password) {

    }

    @CheckLogin
    private void checkLogin() {

    }

    @CheckAffair
    private void checkAffair() {
        Log.i("CheckAffair", "执行数据库操作");
    }

    @CheckNULL
    private void checkSafe(Context context, String key, String value) {
        Toast.makeText(context, "checkSafe", Toast.LENGTH_LONG).show();
    }

    @CheckCrash
    private void checkCrash(String name) throws Exception {
        throw new Exception(name);
    }

    @CheckLog
    private void checkLog() {

    }

    @CheckPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
    private void checkPerssion() {

    }


    @CheckNet
    private void checkNet() {
    }
}

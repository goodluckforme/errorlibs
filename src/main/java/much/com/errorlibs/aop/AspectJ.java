package much.com.errorlibs.aop;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.regex.Pattern;

import much.com.errorlibs.SPUtil;

/**
 * Created by MaQi on 2017/9/14.
 */
@Aspect
public class AspectJ {
    @Pointcut("execution(@much.com.errorlibs.aop.CheckNet  * *(..))")
    public void CheckNet() {

    }

    @Pointcut("execution(@much.com.errorlibs.aop.CheckPermission  * *(..))")
    public void CheckPermission() {

    }

    @Pointcut("execution(@much.com.errorlibs.aop.CheckLog  * *(..))")
    public void CheckLog() {

    }

    @Pointcut("execution(@much.com.errorlibs.aop.CheckCrash  * *(..))")
    public void CheckCrash() {

    }

    @Pointcut("execution(@much.com.errorlibs.aop.CheckNULL  * *(..))")
    public void CheckNULL() {

    }

    @Pointcut("execution(@much.com.errorlibs.aop.CheckAffair  * *(..))")
    public void CheckAffair() {

    }

    @Pointcut("execution(@much.com.errorlibs.aop.CheckLogin  * *(..))")
    public void CheckLogin() {
    }

    @Pointcut("execution(@much.com.errorlibs.aop.CheckPassword  * *(..))")
    public void CheckPassword() {
    }

    @Around("CheckNet()")
    public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        CheckNet checkNet = methodSignature.getMethod().getAnnotation(CheckNet.class);
        Object object = joinPoint.getThis();// View Activity Fragment ； getThis() 当前切点方法所在的类
        Context context = getContext(object);
        Object proceed = null;
        if (isNetworkAvailable(context)) {
            Toast.makeText(context, "网络可用", Toast.LENGTH_LONG).show();
            proceed = joinPoint.proceed();
        } else {
            Toast.makeText(context, "网络不可用", Toast.LENGTH_LONG).show();
        }
        return proceed;
    }

    @Around("CheckLog()")
    public Object CheckLog(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        CheckLog checkLog = methodSignature.getMethod().getAnnotation(CheckLog.class);
        Object object = joinPoint.getThis();// View Activity Fragment ； getThis() 当前切点方法所在的类
        Context context = getContext(object);
        Object[] args = joinPoint.getArgs();
        String kind = joinPoint.getKind();
        Object target = joinPoint.getTarget();
        Class returnType = methodSignature.getReturnType();
        Log.i(context.getClass().getSimpleName(),
                "args.toString()====" + args.toString()
                        + "\nkind====" + kind
                        + "\ntarget====" + target
                        + "\nreturnType====" + returnType);
        Toast.makeText(context, "记录日志", Toast.LENGTH_LONG).show();
        return joinPoint.proceed();
    }

    @Around("CheckPermission()")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        CheckPermission checkPermission = methodSignature.getMethod().getAnnotation(CheckPermission.class);
        Object object = joinPoint.getThis();// View Activity Fragment ； getThis() 当前切点方法所在的类
        Context context = getContext(object);
        String permission = checkPermission.value();
        Object o = null;
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "权限可用" + permission, Toast.LENGTH_LONG).show();
            o = joinPoint.proceed();
        } else {
            Toast.makeText(context, "没有权限，不给用" + permission, Toast.LENGTH_LONG).show();
        }
        return o;
    }

    @AfterThrowing("CheckCrash()")
    public void checkCrash() {
        Log.i("AfterThrowing", "捕获到一个异常");
    }

    @Around("CheckCrash()")
    public Object CheckCrash2(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        CheckCrash checkCrash = methodSignature.getMethod().getAnnotation(CheckCrash.class);
        Object object = joinPoint.getThis();// View Activity Fragment ； getThis() 当前切点方法所在的类
        Context context = getContext(object);
        Object[] args = joinPoint.getArgs();
        String kind = joinPoint.getKind();
        Object target = joinPoint.getTarget();
        Class returnType = methodSignature.getReturnType();
        Log.i(context.getClass().getSimpleName(),
                "args.toString()====" + args[0]
                        + "\nkind====" + kind
                        + "\ntarget====" + target
                        + "\nreturnType====" + returnType);
        return joinPoint.proceed();
    }

    @Around("CheckNULL()")
    public Object checkNULL(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        CheckNULL checkNULL = methodSignature.getMethod().getAnnotation(CheckNULL.class);
        Object object = joinPoint.getThis();// View Activity Fragment ； getThis() 当前切点方法所在的类
        Context context = getContext(object);
        Object[] args = joinPoint.getArgs();
        Object proceed = null;
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg == null) {
                    Toast.makeText(context, "参数" + i + "不能为空", Toast.LENGTH_LONG).show();
                    return proceed;
                }
            }
            proceed = joinPoint.proceed();
        }
        return proceed;
    }

    @After("CheckAffair()")
    public void checkAffairAfter() throws Throwable {
        Log.i("CheckAffair", "数据库操作结束");
    }

    @Before("CheckAffair()")
    public void checkAffairBefore() throws Throwable {
        Log.i("CheckAffair", "数据库操作开始");
    }

    @Around("CheckLogin()")
    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        Object object = joinPoint.getThis();// View Activity Fragment ； getThis() 当前切点方法所在的类
        Context context = getContext(object);
        Object proceed = null;
        if (!TextUtils.isEmpty(SPUtil.getString(context, "user_id"))) {
            proceed = joinPoint.proceed();
        } else {
            Toast.makeText(context, "请登录", Toast.LENGTH_LONG).show();
        }
        return proceed;
    }

    @Around("CheckPassword()")
    public Object checkPassword(ProceedingJoinPoint joinPoint) throws Throwable {
        Object object = joinPoint.getThis();// View Activity Fragment ； getThis() 当前切点方法所在的类
        Context context = getContext(object);
        Object proceed = null;
        Object[] args = joinPoint.getArgs();
        if (args != null && !isPassword(args[0] + "")) {
            Toast.makeText(context, "密码格式正确", Toast.LENGTH_LONG).show();
            proceed = joinPoint.proceed();
        } else {
            Toast.makeText(context, "密码格式不正确", Toast.LENGTH_LONG).show();
        }
        return proceed;
    }


    /**
     * 通过对象获取上下文
     *
     * @param object
     * @return
     */
    private Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;
    }

    public boolean isPassword(String password) {
        String regex = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}";
        return !Pattern.matches(regex, password);
    }

    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    private boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

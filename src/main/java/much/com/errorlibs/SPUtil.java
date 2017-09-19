package much.com.errorlibs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPUtil {
    static SharedPreferences sp;

    private SPUtil(Context context) {
        sp = context.getSharedPreferences("errorlibs", context.MODE_PRIVATE);
    }

    private static SharedPreferences getSPUtil(Context context) {
        if (sp == null) {
            return new SPUtil(context).sp;
        } else return sp;
    }

    public static void put(Context context, String key, Object value) {
        Editor edit = getSPUtil(context).edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        }
        edit.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = getSPUtil(context);
        if (!sp.contains(key)) return "";
        else return sp.getString(key, "");
    }

    public static Boolean getBoolean(Context context, String key) {
        return getSPUtil(context).getBoolean(key, false);
    }

    public static int getInt(Context context, String key) {
        return getSPUtil(context).getInt(key, -1);
    }

    public static void clear(Context context) {
        getSPUtil(context).edit().clear().commit();
    }
}

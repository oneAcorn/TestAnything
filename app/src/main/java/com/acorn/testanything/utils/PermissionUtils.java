package com.acorn.testanything.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Chen Wei
 * time: 16/11/7
 * desc: 描述
 */

public class PermissionUtils {

    private static final String TAG = PermissionUtils.class.getCanonicalName();

    /**
     * 获取没有的权限集合
     *
     * @param context
     * @param perms
     * @return
     */

    public static List<String> getDenidPermissions(Context context, String... perms) {

        List<String> list = new ArrayList<>();
        for (String perm : perms) {
            boolean hasPerm = (ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                list.add(perm);
            }
        }
        return list;
    }

    public static boolean hasPermissions(Context context, String... perms) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String perm : perms) {
            boolean hasPerm = (ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                return false;
            }
        }

        return true;
    }

    public static List hasPermissions2(Context context, String... perms) {
        ArrayList<Object> objects = new ArrayList<>();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return objects;
        }

        for (String perm : perms) {
            boolean hasPerm = (ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                objects.add(perm);
            }
        }
        return objects;
    }

    public static void requestPermissions(final Object object, final int requestCode, final boolean isShowCancle, final String... perms) {
        checkCallingObjectSuitability(object);
        List<String> permsList = getDenidPermissions(getActivity(object), perms);
        if (permsList == null) {
            return;
        }
        // 直接申请权限
        executePermissionsRequest(object, permsList.toArray(new String[permsList.size()]), requestCode);
    }

    @TargetApi(23)
    public static void executePermissionsRequest(Object object, String[] perms, int requestCode) {
        checkCallingObjectSuitability(object);
        if (object instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) object, perms, requestCode);
        } else if (object instanceof Fragment) {
            ((Fragment) object).requestPermissions(perms, requestCode);
        } else if (object instanceof android.app.Fragment) {
            ((android.app.Fragment) object).requestPermissions(perms, requestCode);
        }
    }

    @TargetApi(23)
    public static boolean shouldShowRequestPermissionRationale(Object object, String perm) {
        if (object instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) object, perm);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else {
            return false;
        }
    }

    @TargetApi(11)
    private static Activity getActivity(Object object) {
        if (object instanceof Activity) {
            return ((Activity) object);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).getActivity();
        } else {
            return null;
        }
    }

    /**
     * 是否勾选了 "不再询问"
     *
     * @param object
     * @param deniedPermissions
     * @return
     */
    public static boolean somePermissionsPermanentlyDenied(@NonNull Object object,
                                                           @NonNull List<String> deniedPermissions) {
        for (String deniedPermission : deniedPermissions) {
            if (permissionPermanentlyDenied(object, deniedPermission)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 点击拒绝 未勾选了 "不再询问"
     *
     * @param object
     * @param deniedPermissions
     * @return
     */
    public static boolean somePermissionsPermanentlyRefuse(@NonNull Object object,
                                                           @NonNull List<String> deniedPermissions) {
        //true 未勾选 不再询问
        boolean flag = false;
        for (String deniedPermission : deniedPermissions) {
            if (shouldShowRequestPermissionRationale(object, deniedPermission)) {
                flag = true;
            } else {
                return false;
            }
        }

        return flag;
    }

    public static boolean permissionPermanentlyDenied(@NonNull Object object,
                                                      @NonNull String deniedPermission) {
        return !shouldShowRequestPermissionRationale(object, deniedPermission);
        //  return !shouldShowRequestPermissionRationale(object, deniedPermission);
    }


    private static void checkCallingObjectSuitability(Object object) {
        if (object == null) {
            throw new NullPointerException("Activity or Fragment should not be null");
        }

        boolean isActivity = object instanceof Activity;
        boolean isSupportFragment = object instanceof Fragment;
        boolean isAppFragment = object instanceof android.app.Fragment;
        boolean isMinSdkM = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;

        if (!(isSupportFragment || isActivity || (isAppFragment && isMinSdkM))) {
            if (isAppFragment) {
                throw new IllegalArgumentException(
                        "Target SDK needs to be greater than 23 if caller is android.app.Fragment");
            } else {
                throw new IllegalArgumentException(
                        "Caller must be an Activity or a Fragment");
            }
        }
    }

}

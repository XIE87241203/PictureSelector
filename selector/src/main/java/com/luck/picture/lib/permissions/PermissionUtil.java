package com.luck.picture.lib.permissions;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.luck.picture.lib.utils.SdkVersionUtils;

/**
 * @author：luck
 * @date：2021/11/18 10:12 上午
 * @describe：PermissionUtil
 */
public class PermissionUtil {

    public static boolean hasPermissions(@NonNull Context context, @Size(min = 1) @NonNull String... perms) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllGranted(int[] grantResults) {
        boolean isAllGranted = true;
        if (grantResults.length > 0) {
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
        } else {
            isAllGranted = false;
        }
        return isAllGranted;
    }

    /**
     * 跳转到系统设置页面
     */
    public static void goIntentSetting(Fragment fragment, boolean isManageFiles, int requestCode) {
        try {
            if (SdkVersionUtils.isR() && isManageFiles) {
                fragment.startActivityForResult(new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION),
                        requestCode);
            } else {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", fragment.getActivity().getPackageName(), null);
                intent.setData(uri);
                fragment.startActivityForResult(intent, requestCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
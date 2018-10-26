package com.ycc.simplemvp.utils

import android.content.Context
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import java.io.File

/**
 * @author CCYANGF
 * @date 2017/10/17
 * @description FileUtil 提供文件相关操作
 * @version 1.0
 */
class FileUtil {


    fun getUriForPath(filePath: String, activity: Context): Uri {
        return getUriForFile(File(filePath), activity)
    }

    fun getUriForFile(photoFile: File, activity: Context): Uri {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return Uri.fromFile(photoFile)
        } else {
            return FileProvider.getUriForFile(
                    activity,
                    activity.packageName + ".fileprovider",
                    photoFile)
        }
    }
}
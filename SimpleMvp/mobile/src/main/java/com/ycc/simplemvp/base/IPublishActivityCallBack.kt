package com.ycc.simplemvp.base

import android.os.Bundle

/**
 * /界面跳转的接口
 * Created by issuser on 2017/5/3.
 */

interface IPublishActivityCallBack {

    /**
     * 打开新界面

     * @param cls 新开页面
     * *
     * @param bundle    参数
     */
    fun startActivity(cls: Class<*>, bundle: Bundle)

    /**
     * 打开新界面，期待返回

     * @param cls 新界面
     * *
     * @param requestCode 请求码
     * *
     * @param bundle 参数
     */
    fun startActivityForResult(cls: Class<*>, bundle: Bundle, requestCode: Int)

    /**
     * 返回到上个页面

     * @param bundle 参数
     */
    fun setResultOk(bundle: Bundle)
}

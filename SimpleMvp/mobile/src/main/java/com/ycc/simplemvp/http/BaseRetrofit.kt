package com.ycc.simplemvp.http

import retrofit2.Retrofit

/**
 * @author CCYANGF
 * @date 2018/10/19
 * @description BaseRetrofit
 * @version
 */
class BaseRetrofit {


    companion object {
        val TAG = "BaseRetrofit"
        val TIME_OUT = 30//超时时间 s
        val RETRY_TIMES = 1//重试次数
        val retrofit: Retrofit? = null
        fun getInstance() = Holder.INSTANCE
    }

    private object Holder {
        val INSTANCE = BaseRetrofit
    }
}
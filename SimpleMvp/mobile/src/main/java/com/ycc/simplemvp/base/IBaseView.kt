package com.ycc.simplemvp.base

/**
 * @author CCYANGF
 * @date 2018/10/18
 * @description IBaseView []
 * @version
 */
interface IBaseView {

    fun showProgress()

    fun onSuccess(code: Int, resp: String)

    fun onError(code: Int, err: String)

    fun dismissProgress()
}
package com.ycc.simplemvp.http

/**
 * 接口最外层的请求响应对象
 * Created by issuser on 2017/4/12.
 */

class BaseResp {

    var status: Int = 0

    var msg: String? = null

    var index: Int = 0

    var count: Int = 0

    var data: String? = null
}

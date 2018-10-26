package com.ycc.simplemvp.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import com.ycc.simplemvp.R
import com.ycc.simplemvp.api.bean.UserBean
import com.ycc.simplemvp.databinding.ActivityLoginBinding
import java.io.DataOutputStream
import java.io.File


/**
 *登录界面
 */
class LoginActivity : AppCompatActivity() {

    private var loginBinding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val user = UserBean("Test", "a123456")
//        loginBinding!!.email.setText("a123456")
//        loginBinding!!.password.setText(user.password)
        loginBinding?.user = user
//        write(this)
//        read(this)
    }

    fun write(c: Context) {
        val outStream = c.openFileOutput("ceshi.txt",
                Context.MODE_PRIVATE)
        outStream.write("测试".toByteArray())
        outStream.close()
    }

    fun read(c: Context) {
        val file1 = c.filesDir
        val path = file1.absolutePath
        val file = File(path + "ceshi.txt")
        println("file_length=" + file.length())
        var files = c.fileList()
        var uri = FileProvider.getUriForFile(
                c,
                "com.ycc.simplemvp.fileprovider",
                file)
        println(path)
        share("Test", uri, c)
    }

    fun share(title: String, uri: Uri, c: Context) {
        val content = "测试分享"
        val shareIntent = Intent(Intent.ACTION_SEND)

        //uri 是图片的地址
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.type = "image/*"
//    shareIntent.setPackage("com.tencent.mm")
        shareIntent.putExtra("sms_body", title)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, content)
        shareIntent.putExtra(Intent.EXTRA_TITLE, title)
        //系统默认标题
        c.startActivity(Intent.createChooser(shareIntent, "选择分享"))

    }

    fun execShellCmd(cmd: String) {
        try {
            // 申请获取root权限，这一步很重要，不然会没有作用
            val process = Runtime.getRuntime().exec("su")
            // 获取输出流
            val outputStream = process.outputStream
            val dataOutputStream = DataOutputStream(
                    outputStream)
            dataOutputStream.writeBytes(cmd)
            dataOutputStream.flush()
            dataOutputStream.close()
            outputStream.close()
            process.destroy()
        } catch (t: Throwable) {
            t.printStackTrace()
        }

    }


}

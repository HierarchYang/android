//package com.ycc.simplemvp.utils
//
//import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
//import com.sun.deploy.trace.Trace.flush
//import java.io.BufferedReader
//import java.io.DataOutputStream
//import java.io.IOException
//import java.io.InputStreamReader
//
//
///**
// * @author CCYANGF
// * @date 2018/10/10
// * @description ShellUtils
// * @version
// */
//class ShellUtils {
//
//
//    val COMMAND_SU = "su"
//    val COMMAND_SH = "sh"
//    val COMMAND_EXIT = "exit\n"
//    val COMMAND_LINE_END = "\n"
//
//
//    /**
//     * check whether has root permission
//     *
//     * @return
//     */
//    fun checkRootPermission(): Boolean {
//        return execCommand("echo root", true, false).result === 0
//    }
//
//    /**
//     * execute shell command, default return result msg
//     *
//     * @param command command
//     * @param isRoot whether need to run with root
//     * @return
//     * @see ShellUtils.execCommand
//     */
//    fun execCommand(command: String, isRoot: Boolean): CommandResult {
//        return execCommand(arrayOf(command), isRoot, true)
//    }
//
//    /**
//     * execute shell commands, default return result msg
//     *
//     * @param commands command list
//     * @param isRoot whether need to run with root
//     * @return
//     * @see ShellUtils.execCommand
//     */
//    fun execCommand(commands: List<*>?, isRoot: Boolean): CommandResult {
//        return execCommand(commands?.toTypedArray(), isRoot, true)
//    }
//
//    /**
//     * execute shell commands, default return result msg
//     *
//     * @param commands command array
//     * @param isRoot whether need to run with root
//     * @return
//     * @see ShellUtils.execCommand
//     */
//    fun execCommand(commands: Array<String>, isRoot: Boolean): CommandResult {
//        return execCommand(commands, isRoot, true)
//    }
//
//    /**
//     * execute shell command
//     *
//     * @param command command
//     * @param isRoot whether need to run with root
//     * @param isNeedResultMsg whether need result msg
//     * @return
//     * @see ShellUtils.execCommand
//     */
//    fun execCommand(command: String, isRoot: Boolean, isNeedResultMsg: Boolean): CommandResult {
//        return execCommand(arrayOf(command), isRoot, isNeedResultMsg)
//    }
//
//
//    /**
//     * execute shell commands
//     *
//     * @param commands command list
//     * @param isRoot whether need to run with root
//     * @param isNeedResultMsg whether need result msg
//     * @return
//     * @see ShellUtils.execCommand
//     */
//    fun execCommand(commands: List<*>?, isRoot: Boolean, isNeedResultMsg: Boolean): CommandResult {
//        return execCommand(commands?.toTypedArray(), isRoot, isNeedResultMsg)
//    }
//
//    /**
//     * execute shell commands
//     *
//     * @param commands command array
//     * @param isRoot whether need to run with root
//     * @param isNeedResultMsg whether need result msg
//     * @return
//     *
//     *
//     * if isNeedResultMsg is false, [CommandResult.successMsg] is null and
//     * [CommandResult.errorMsg] is null.
//     *
//     *
//     * if [CommandResult.result] is -1, there maybe some excepiton.
//     */
//    fun execCommand(commands: Array<String>?, isRoot: Boolean, isNeedResultMsg: Boolean): CommandResult {
//        var result = -1
//        if (commands == null || commands.size == 0) {
//            return CommandResult(result, null, null)
//        }
//
//
//        var process: Process? = null
//        var successResult: BufferedReader? = null
//        var errorResult: BufferedReader? = null
//        var successMsg: StringBuilder? = null
//        var errorMsg: StringBuilder? = null
//
//
//        var os: DataOutputStream? = null
//        try {
//            process = Runtime.getRuntime().exec(if (isRoot) COMMAND_SU else COMMAND_SH)
//            os = DataOutputStream(process!!.outputStream)
//            for (command in commands) {
//                if (command == null) {
//                    continue
//                }
//                // donnot use os.writeBytes(commmand), avoid chinese charset error
//                os!!.write(command.toByteArray())
//                os!!.writeBytes(COMMAND_LINE_END)
//                os!!.flush()
//            }
//            os!!.writeBytes(COMMAND_EXIT)
//            os!!.flush()
//
//
//            result = process!!.waitFor()
//            // get command result
//            if (isNeedResultMsg) {
//                successMsg = StringBuilder()
//                errorMsg = StringBuilder()
//                successResult = BufferedReader(InputStreamReader(process.inputStream))
//                errorResult = BufferedReader(InputStreamReader(process.errorStream))
//                var s: String
//                while ((s = successResult!!.readLine()) != null) {
//                    successMsg.append(s)
//                }
//                while ((s = errorResult!!.readLine()) != null) {
//                    errorMsg.append(s)
//                }
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
//            try {
//                if (os != null) {
//                    os!!.close()
//                }
//                if (successResult != null) {
//                    successResult!!.close()
//                }
//                if (errorResult != null) {
//                    errorResult!!.close()
//                }
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//
//
//            process?.destroy()
//        }
//        return CommandResult(result, successMsg?.toString(), errorMsg?.toString())
//    }
//
//}
package com.ycc.simplemvp.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * @author CCYANGF
 * @date 2017/10/10
 * @description CommandExecution
 */
public class CommandExecution {


    public static final String TAG = "CommandExecution";

    public final static String COMMAND_SU = "su";
    public final static String COMMAND_SH = "sh";
    public final static String COMMAND_EXIT = "exit\n";
    public final static String COMMAND_LINE_END = "\n";

    /**
     * Command执行结果
     *
     * @author Mountain
     */
    public static class CommandResult {
        public int result = -1;
        public String errorMsg;
        public String successMsg;
    }

    /**
     * 执行命令—单条
     *
     * @param command
     * @param isRoot
     * @return
     */
    public static CommandResult execCommand(String command, boolean isRoot) {
        String[] commands = {command};
        return execCommand(commands, isRoot);
    }

    /**
     * 执行命令-多条
     *
     * @param commands
     * @param isRoot
     * @return
     */
    public static CommandResult execCommand(String[] commands, boolean isRoot) {
        CommandResult commandResult = new CommandResult();
        if (commands == null || commands.length == 0) return commandResult;
        Process process = null;
        DataOutputStream os = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command != null) {
                    os.write(command.getBytes());
                    os.writeBytes(COMMAND_LINE_END);
                    os.flush();
                }
            }
            os.writeBytes(COMMAND_EXIT);
            os.flush();
//                commandResult.result = process.waitFor();
            //获取错误信息
            successMsg = new StringBuilder();
            errorMsg = new StringBuilder();
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;
            while ((s = successResult.readLine()) != null) successMsg.append(s);
            while ((s = errorResult.readLine()) != null) errorMsg.append(s);
            commandResult.successMsg = successMsg.toString();
            commandResult.errorMsg = errorMsg.toString();
            Log.e(TAG, commandResult.result + " | " + commandResult.successMsg
                    + " | " + commandResult.errorMsg);
        } catch (IOException e) {
            String errmsg = e.getMessage();
            if (errmsg != null) {
                Log.e(TAG, errmsg);
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            String errmsg = e.getMessage();
            if (errmsg != null) {
                Log.e(TAG, errmsg);
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                if (os != null) os.close();
                if (successResult != null) successResult.close();
                if (errorResult != null) errorResult.close();
            } catch (IOException e) {
                String errmsg = e.getMessage();
                if (errmsg != null) {
                    Log.e(TAG, errmsg);
                } else {
                    e.printStackTrace();
                }
            }
            if (process != null) process.destroy();
        }
        return commandResult;
    }


    /**
     * 执行shell命令
     *
     * @param cmd
     */
    public static void execShellCmd(String cmd) {

        try {
            // 申请获取root权限，这一步很重要，不然会没有作用
            Process process = Runtime.getRuntime().exec(COMMAND_SU);
            // 获取输出流
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(
                    outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static String exec(boolean isRoot) {
        Process process = null;
        BufferedReader shellErrorResultReader = null, shellInfoResultReader = null;
        String infoLine = "";
        try {

            process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
            shellErrorResultReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            shellInfoResultReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((infoLine = shellInfoResultReader.readLine()) != null) {
                Log.e("脚本文件执行信息:{}", infoLine);
            }
            while ((infoLine = shellErrorResultReader.readLine()) != null) {
                Log.e("脚本文件执行信息:{}", infoLine);
            }
            // 等待程序执行结束并输出状态
            int exitCode = process.waitFor();
            Log.e("脚本文件执行失败:", "错误码：" + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("shell脚本执行错误", e.toString());
        } finally {


            if (null != shellInfoResultReader) {
                try {
                    shellInfoResultReader.close();
                } catch (IOException e) {
                    Log.e("流文件关闭异常：", e.toString());
                }
            }
            if (null != shellErrorResultReader) {
                try {
                    shellErrorResultReader.close();
                } catch (IOException e) {
                    Log.e("流文件关闭异常：", e.toString());
                }
            }
            if (null != process) {
                process.destroy();
            }
        }
        return infoLine;
    }

}

package com.wangjf.library.command;

import java.io.InputStream;

/**
 * Created by wangjf on 2018/3/21.
 */

public class CommandUtils {

    /**
     * 运行普通linux命令
     *
     * @param command
     * @return
     */
    public static String runCommand(String command) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            process.waitFor();

            InputStream localInputStream = process.getInputStream();
            byte[] arrayOfByte = new byte[localInputStream.available()];

            localInputStream.read(arrayOfByte);
            return (new String(arrayOfByte));

        } catch (Exception e) {
            return null;
        }
    }

}

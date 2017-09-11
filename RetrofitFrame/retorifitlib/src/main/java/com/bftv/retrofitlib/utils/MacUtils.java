package com.bftv.retrofitlib.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author caojiaming
 * @version 1.0
 * @title 类的名称
 * @description 该类主要功能描述
 * @company 北京奔流网络信息技术有限公司
 * @created 2016/7/28
 * @changeRecord [修改记录] <br/>
 */
public class MacUtils {

    //根据busybox获取本地Mac
    public static String getLocalMacAddressFromBusybox() {
        String result;
        String Mac;
        result = callCmd("busybox ifconfig", "HWaddr");

        //如果返回的result == null，则说明网络不可取
        if (result == null) {
            return "";
        }

        //对该行数据进行解析
        //例如：eth0      Link encap:Ethernet  HWaddr 00:16:E8:3E:DF:67
        if (result.length() > 0 && result.contains("HWaddr") == true) {
            Mac = result.substring(result.indexOf("HWaddr") + 6, result.length() - 1);
            result = Mac;
        }
        return result;
    }

    private static String callCmd(String cmd, String filter) {
        String result = null;
        String line;
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);

            //执行命令cmd，只取结果中含有filter的这一行
            while ((line = br.readLine()) != null && line.contains(filter) == false) {
                //result += line;
            }
            result = line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

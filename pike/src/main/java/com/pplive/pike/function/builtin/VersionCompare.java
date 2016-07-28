package com.pplive.pike.function.builtin;

import org.apache.commons.lang3.math.NumberUtils;

import com.pplive.pike.base.AbstractUDF;

public class VersionCompare extends AbstractUDF {
    
    private static final long serialVersionUID = -7932979857959266878L;
    private static String defaultDelimiter = "\\.";
    
    public static Boolean evaluate(String version, String targetVersion) {
        return evaluate(version, targetVersion, defaultDelimiter);
    }

    private static Boolean evaluate(String version, String targetVersion, String delimiter) {
        if(version == null) {
            return false;
        }
        if(targetVersion == null) {
            return true;
        }
        String[] versionArr = version.split(delimiter);
        String[] targetArr = targetVersion.split(delimiter);
        int length = versionArr.length < targetArr.length? versionArr.length : targetArr.length;
        for(int i = 0; i < length; i++) {
            int v1 = NumberUtils.toInt(versionArr[i], 0),
                v2 = NumberUtils.toInt(targetArr[i], 0);
            if(v1 > v2) {
                return true;
            } else if(v1 < v2) {
                return false;
            }
            
        }
        if(versionArr.length >= targetArr.length) {
            return true;
        } else {
            return false;
        }
    }
    
}
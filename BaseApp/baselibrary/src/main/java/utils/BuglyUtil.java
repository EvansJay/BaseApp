package utils;

import com.tencent.bugly.beta.Beta;

/**
 * @author Andlei
 * @date 2019/9/18.
 */
public class BuglyUtil {
    public static void checkUpgrade(boolean isManual,boolean isSilence){
        Beta.checkUpgrade(isManual,isSilence);
    }
}

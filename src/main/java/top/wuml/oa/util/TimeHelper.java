package top.wuml.oa.util;

import java.util.Date;

public class TimeHelper {
    public static long getDiffHours(Date startTime,Date endTime){
        long diff = endTime.getTime() - startTime.getTime();
        return diff / (1000 * 60 * 60);
    }

}

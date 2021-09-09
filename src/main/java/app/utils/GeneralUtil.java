package app.utils;

import app.config.Configer;
import app.log.LogSystem;
import app.log.LogSystemFactory;

/**
 * @ClassName : app.utils.GeneralUtil
 * @Description :
 * @Date 2021-09-09 15:18:00
 * @Author ZhangHL
 */

public class GeneralUtil {

    private static Configer configer = new Configer();

    private static LogSystem log = LogSystemFactory.getLogSystem();

    public static String readConfig(String property){
        return configer.readConfig(property);
    }

    public static void writeConfig(String filePath,String key,String value){
        configer.updateConfig(key,value,filePath);
    }

    public static void info(String msg,Object... args){
        log.info(msg, args);
    }

    public static void error(String msg,Object... args){
        log.error(msg, args);
    }


}

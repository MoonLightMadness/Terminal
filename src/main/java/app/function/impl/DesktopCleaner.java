package app.function.impl;

import app.function.Function;
import app.function.constant.TimeConstant;
import app.utils.GeneralUtil;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * @ClassName : app.function.impl.DesktopCleaner
 * @Description :
 * @Date 2021-09-13 15:24:46
 * @Author ZhangHL
 */
public class DesktopCleaner implements Function {

    String userName = System.getProperty("user.name");

    String desktopPath = "C:\\Users\\"+userName+"\\Desktop\\";

    @Override
    public String doFunc(String[] args) {
        //初始化，环境检测与搭建
        init();
        return null;
    }

    private void init(){
        File file = new File(desktopPath+"\\temp");
        if(file.isDirectory() || !file.exists()){
            file.mkdir();
        }
    }

    private File[] getCurrentList(){
        File file = new File(desktopPath);
        return file.listFiles();
    }

    private File[] getWhiteList(){
        List<String> res = GeneralUtil.readConfigList("clean.white");
        int count = res.size();
        File[] files = new File[count];
        for (int i=0;i<count;i++){
            files[i] = new File(desktopPath+"\\"+res);
        }
        return files;
    }



    @Test
    public void test(){
        File file = new File("C:\\Users\\Administrator\\Desktop\\temp2");
        if(file.isDirectory() || !file.exists()){
            file.mkdir();
        }
    }

    public long parseConfig() {
        String autosave = GeneralUtil.readConfig("clean.time.interval");
        char[] cas = autosave.toCharArray();
        StringBuilder sb = new StringBuilder();
        long total = 0L;
        // y M d h m s
        for (char ca : cas) {
            if (ca == 'y') {
                total += Integer.parseInt(sb.toString().trim()) * TimeConstant.YearOfMillSeconds;
                sb = new StringBuilder();
            } else if (ca == 'M') {
                total += Integer.parseInt(sb.toString().trim()) * TimeConstant.MonthOfMillSeconds;
                sb = new StringBuilder();
            } else if (ca == 'd') {
                total += Integer.parseInt(sb.toString().trim()) * TimeConstant.DayOfMillSeconds;
                sb = new StringBuilder();
            } else if (ca == 'h') {
                total += Integer.parseInt(sb.toString().trim()) * TimeConstant.HourOfMillSeconds;
                sb = new StringBuilder();
            } else if (ca == 'm') {
                total += Integer.parseInt(sb.toString().trim()) * TimeConstant.MinuteOfMillSeconds;
                sb = new StringBuilder();
            } else if (ca == 's') {
                total += Integer.parseInt(sb.toString().trim()) * TimeConstant.SecondOfMillSeconds;
                sb = new StringBuilder();
            }else {
                sb.append(ca);
            }
        }
        return total;
    }


}

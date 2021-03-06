package app.function.impl;

import app.config.Configer;
import app.function.Function;
import app.function.constant.TimeConstant;
import app.utils.GeneralUtil;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
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

    String tempLog;

    @Override
    public String doFunc(String[] args) {
        //初始化，环境检测与搭建
        init();
        while (true){
            try {
                removeToTemp();
                GeneralUtil.info("清理完成");
                long interval = parseConfig();
                GeneralUtil.info("下次清理时间:{}ms后",interval);
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                GeneralUtil.error("出现异常，原因:{}",e);
                e.printStackTrace();
            }
        }
    }

    private void init(){
        File file = new File(desktopPath+"\\temp");
        if(file.isDirectory() || !file.exists()){
            file.mkdir();
            file = new File(desktopPath+"\\temp\\log.txt");
            try {
                file.createNewFile();
                tempLog = file.getPath();
            } catch (IOException e) {
                GeneralUtil.error("新建文件发生错误，原因:{}",e);
                e.printStackTrace();
            }
        }
        GeneralUtil.info("初始化完成");
    }

    private void removeToTemp(){
        File[] current = getCurrentList();
        File[] white = getWhiteList();
        boolean isMatch = false;
        for (File c : current){
            for (File w : white){
                if(c.getName().equals(w.getName())){
                    isMatch = true;
                    break;
                }
            }
            if(!isMatch){
                GeneralUtil.info("将{}移动到{}",c.getName(),desktopPath+"\\temp\\"+c.getName());
                GeneralUtil.writeConfig(tempLog,c.getName(), LocalDateTime.now().toString());
                c.renameTo(new File(desktopPath+"\\temp\\"+c.getName()));
            }
            isMatch = false;
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
            files[i] = new File(desktopPath+"\\"+res.get(i));
        }
        return files;
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

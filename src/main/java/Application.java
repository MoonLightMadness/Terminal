import app.utils.GeneralUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @ClassName : PACKAGE_NAME.Application
 * @Description :
 * @Date 2021-09-09 15:01:48
 * @Author ZhangHL
 */
public class Application {


    public static void main(String[] args) {
        //启动输入流
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while (true){
            try {
                input = reader.readLine();
                System.out.println(input);
            } catch (IOException e) {
                GeneralUtil.info("发生错误,原因:{}",e);
                e.printStackTrace();
            }
        }
    }
}

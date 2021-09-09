import app.domain.vo.PathChangeVO;
import app.handler.impl.PathHandler;
import app.host.Host;
import app.http.HttpResponseBuilder;
import app.utils.GeneralUtil;
import app.utils.SimpleUtils;

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


    static Host host = new Host();

    static PathHandler pathHandler = new PathHandler();

    public static void main(String[] args) {

        host.init();

        //启动输入流
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while (true){
            try {
                showIndicator();
                input = reader.readLine();
                handleSend(input);
                handleReceive();
            } catch (IOException e) {
                GeneralUtil.info("发生错误,原因:{}",e);
                e.printStackTrace();
            }
        }
    }

    public static void showIndicator(){
        System.out.print(pathHandler.getPath()+">");
    }

    public static void handleSend(String msg){
        HttpResponseBuilder httpBuilder = new HttpResponseBuilder();
        httpBuilder.setCode("200").setServer("DSMServer/1.0")
                .setHost(GeneralUtil.readConfig("ip") + " " + GeneralUtil.readConfig("port"));
        httpBuilder.setData(msg);
        host.send(httpBuilder.toString());
    }

    public static void handleReceive(){
        byte[] data = host.read();
        PathChangeVO pathChangeVO = (PathChangeVO) SimpleUtils.parseTo(data,PathChangeVO.class);
        if(pathChangeVO.getChangedPath() != null){
            pathHandler.setPath(pathChangeVO.getChangedPath());
        }
        System.out.println(new String(data));
    }
}

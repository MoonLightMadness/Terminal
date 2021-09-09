package app.handler;

/**
 * 指令处理器
 *
 * @author zhl
 * @date 2021-09-09 17:52
 */
public interface Handler {

    Handler handle(String cmd);

}

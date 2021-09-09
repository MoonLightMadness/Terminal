package app.handler;

/**
 * 根据输入指令生成Handler实例
 *
 * @author zhl
 * @date 2021-09-09 17:52
 */
public interface Handler {

    Handler getInstance(String cmd);

}

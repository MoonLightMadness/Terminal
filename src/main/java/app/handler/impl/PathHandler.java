package app.handler.impl;

import app.utils.GeneralUtil;

public class PathHandler {

    public String getPath(){
        return GeneralUtil.readConfig("server.path");
    }

    public void setPath(String path){
        GeneralUtil.writeConfig(GeneralUtil.readConfig("system.config.path"),"server.path",path);
    }


}

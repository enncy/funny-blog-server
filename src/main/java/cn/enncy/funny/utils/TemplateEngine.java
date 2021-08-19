package cn.enncy.funny.utils;


import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.net.URL;
import java.util.Objects;

/**
 * 模板引擎渲染
 *
 * @author: enncy
 */
public class TemplateEngine {

    /**
     *  渲染后返回字符串结果
     *
     * @param context 渲染信息
     * @param templatePath 模板文件资源路径
     * @return: java.lang.String
     */
    public static String  render(VelocityContext context,String templatePath){
        VelocityEngine velocity = new VelocityEngine();
        //模板文件所在的路径
        String sourcePath = templatePath.substring(0, templatePath.lastIndexOf("/"));
        String fileName = templatePath.substring(templatePath.lastIndexOf("/"));
        String path = Objects.requireNonNull(TemplateEngine.class.getClassLoader().getResource(sourcePath)).getPath();
        //设置参数
        velocity.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path);
        //处理中文问题
        velocity.setProperty(Velocity.INPUT_ENCODING, "GBK");
        //初始化模板
        velocity.init();

        StringWriter stringWriter = new StringWriter();
        velocity.mergeTemplate(fileName, "utf-8", context,stringWriter);
        return stringWriter.toString();
    }


    /**
     *  渲染成一个文件
     *
     * @param context   渲染信息
     * @param templatePath  模板文件资源路径
     * @param outputPath    输出路径
     * @return: void
     */
    public  static void renderFile(VelocityContext context,String templatePath,String outputPath) throws IOException {
        String render = TemplateEngine.render(context, templatePath);

        File file = new File(outputPath);
        if(!file.exists()){
            URL resource = TemplateEngine.class.getClassLoader().getResource(outputPath);
            if(resource!=null){
                String path = resource.getPath();
                file = new File(path);
            }else{
                throw new NullPointerException("资源文件不存在!");
            }

        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(render);
        fileWriter.close();
    }


}

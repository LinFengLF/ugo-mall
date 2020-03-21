package com.edu.mju.ugomall.controller;

import com.edu.mju.ugomall.utils.ServerConfig;
import com.edu.mju.ugomall.utils.UploadUtil;
import org.apache.commons.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileUploadController {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    private static String uploadPath = "D://images";
    private String storagePath = null;
    private final static String fileDir = "files";
    private final static String rootPath = uploadPath + File.separator + fileDir + File.separator;
    private ServerConfig serverConfig = new ServerConfig();
    private UploadUtil uploadUtil = new UploadUtil();

    @RequestMapping("/upload")
    @ResponseBody
    public Object uploadFile(@RequestParam("file") MultipartFile[] multipartFiles) {
        Map<String,Object> map = new HashMap<>();
        String path = serverConfig.getUrl();
        logger.info("端口号加ip为：" + path);
        File fileDir = new File(rootPath);
        if (!fileDir.exists() && !fileDir.isDirectory()) {
            fileDir.mkdirs();
        }
        try {
            if (multipartFiles != null && multipartFiles.length > 0) {
                for (int i = 0; i < multipartFiles.length; i++) {
                    try {
                        //获取文件的原本名字
                        String FileRealName = multipartFiles[i].getOriginalFilename();
                        //通过时间规则生成文件名
                        String uuid_name = uploadUtil.generateName(FileRealName);

                        storagePath = rootPath + uuid_name;
                        logger.info("上传的文件：" + multipartFiles[i].getName() + "," +
                                multipartFiles[i].getContentType() + "," + "，保存的路径为：" + storagePath);
                        Streams.copy(multipartFiles[i].getInputStream(), new FileOutputStream(storagePath), true);
//                        map.put("url", "/images/" + storagePath);
                        map.put("url", "http://127.1.1.1:8080" + "/images/" + uuid_name);
                        map.put("msg","上传成功");

                    } catch (IOException e) {
                        map.put("url","error");
                        map.put("msg","上传失败");
                    }
                }
            }
        } catch (Exception e) {
            map.put("url","error");
            map.put("msg","上传失败");
        }
        return map;
    }

}


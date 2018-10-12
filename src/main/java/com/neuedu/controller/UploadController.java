package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class UploadController {

    @Autowired
    IProductService productService;

    @RequestMapping(value = "/upload",method = RequestMethod.GET)
    public String upload(){
        return "upload";
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> upload(MultipartFile upload){

        return productService.upload(upload);

    }

   /* @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(@RequestParam("upload") MultipartFile upload){

        // 重新生成文件名 UUID+扩展名--》保存到本地
        if (upload!=null) {
            // step1：获取原文扩展名件
            String originFilename = upload.getOriginalFilename();
            if (originFilename != null && !originFilename.equals("")) {
                int index = originFilename.lastIndexOf('.');
                String extendname = originFilename.substring(index); // [)
                // step2：生成新的文件名
                String uuid = UUID.randomUUID().toString();
                String newFilename = uuid + extendname; // aasdas.jsp
                // step3：把生成好的图片保存到你想要的路径下
                String filePath = "D:\\ftpfile";
                File file = new File(filePath, newFilename);
                try {
                    upload.transferTo(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "upload";
    }*/
}

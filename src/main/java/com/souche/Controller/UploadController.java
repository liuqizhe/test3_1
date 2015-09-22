package com.souche.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuqizhe on 15/9/22.
 */
@Controller
@RequestMapping("/upload")
public class UploadController {
    @RequestMapping("/toUpload")
    public String toUpload() {
        return "upload" ;
    }

    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest req, Model model) throws Exception {
        System.out.println("上传文件："+file);
        //检查上传文件类型
        //1.file.getOringinalFilename(),截取扩展名
        //2.判断是否允许该类型，不允许，跳转到upload.jsp,
        //带一个error消息出去，终止后面执行
        //--------------------------------
        String path = req.getSession().getServletContext().getRealPath("upload") ;
        String filePath = path+ File.separatorChar+file.getOriginalFilename() ;
        System.out.println(filePath);

        //写文件操作
        InputStream fis = file.getInputStream() ;
        FileOutputStream fos = new FileOutputStream(new File(filePath)) ;
        byte[] bbs = new byte[1024] ;
        int len = -1 ;
        while((len=fis.read(bbs)) != -1) {
            fos.write(bbs, 0, len);
        }
        fos.close();
        fis.close();

        //将上传的文件放入模型,在ok显示
        model.addAttribute("fname", file.getOriginalFilename()) ;
        return "ok" ;

    }

    @ExceptionHandler
    public ModelAndView doException(Exception e) {
        Map<String, Object> map = new HashMap<String,Object>() ;
        if(e instanceof MaxUploadSizeExceededException) {
            long maxSize = ((MaxUploadSizeExceededException)e).getMaxUploadSize() ;
            map.put("error", "文件上传太大，不能超过"+maxSize+"字节") ;
        } else {
            map.put("error", "上传失败") ;
        }

        return new ModelAndView("upload", map) ;
    }

}

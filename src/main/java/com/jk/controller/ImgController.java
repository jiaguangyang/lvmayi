package com.jk.controller;

import com.jk.utils.CheckImgUtil;
import com.jk.utils.OSSClientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("img")
public class ImgController {


    /**
     * OSS阿里云上传图片
     */
    @RequestMapping("updaloadImg")
    @ResponseBody
    public String uploadImg(MultipartFile imgg)throws IOException {
        if (imgg == null || imgg.getSize() <= 0) {
            throw new IOException("file不能为空");
        }
        OSSClientUtil ossClient=new OSSClientUtil();
        String name = ossClient.uploadImg2Oss(imgg);
        String imgUrl = ossClient.getImgUrl(name);
        String[] split = imgUrl.split("\\?");
        //System.out.println(split[0]);
        return split[0];
    }

    @RequestMapping("getImgCode")
    public  void getImgCode(HttpServletRequest request , HttpServletResponse response) throws Exception{

        CheckImgUtil.checkImg(request, response);
        String str = (String) request.getSession().getAttribute("checkcode");
        System.out.println(str);
    }
}

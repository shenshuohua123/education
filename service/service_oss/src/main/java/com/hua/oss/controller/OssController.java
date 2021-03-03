package com.hua.oss.controller;

import com.hua.commonutils.R;
import com.hua.oss.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(description = "Oss文件上传")
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;
    @PostMapping
    public R uploadOssFile(MultipartFile file){
        String url=ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}

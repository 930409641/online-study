package com.yxq.oss.controller;

import com.yxq.commonutils.R;
import com.yxq.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        //获取上传文件 MultipartFile
        String url = null;
        try {
            url = ossService.uploadFileAvatar(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return R.ok().data("url",url);
    }
}

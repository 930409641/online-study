package com.yxq.oss.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OssService {
    String uploadFileAvatar(MultipartFile file) throws IOException;
}

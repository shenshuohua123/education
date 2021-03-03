package com.hua.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public interface OssService {
    public String uploadFileAvatar(MultipartFile file);
}

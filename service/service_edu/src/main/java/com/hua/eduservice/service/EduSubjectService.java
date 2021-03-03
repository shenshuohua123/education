package com.hua.eduservice.service;

import com.hua.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hua.eduservice.entity.subject.OneSubject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-02-09
 */
@Service
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    List<OneSubject> getAllOneTwoSubject();
}

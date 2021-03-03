package com.hua.eduservice.controller;


import com.hua.commonutils.R;
import com.hua.eduservice.entity.subject.OneSubject;
import com.hua.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-09
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    //课程分类列表
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list=eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}


package com.hua.eduservice.controller;


import com.hua.commonutils.R;
import com.hua.eduservice.entity.vo.CourseInfoVo;
import com.hua.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-09
 */
@RestController
@RequestMapping("/eduservice/course")
@Api("课程前端控制器")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;
    //添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo=eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }
}


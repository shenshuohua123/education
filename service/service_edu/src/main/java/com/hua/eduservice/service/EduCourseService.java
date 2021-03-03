package com.hua.eduservice.service;

import com.hua.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hua.eduservice.entity.vo.CourseInfoVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-02-09
 */
@Service
public interface EduCourseService extends IService<EduCourse> {

    void saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);
}

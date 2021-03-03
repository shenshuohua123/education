package com.hua.eduservice.service.impl;

import com.hua.eduservice.entity.EduCourse;
import com.hua.eduservice.entity.EduCourseDescription;
import com.hua.eduservice.entity.vo.CourseInfoVo;
import com.hua.eduservice.mapper.EduCourseMapper;
import com.hua.eduservice.service.EduCourseDescriptionService;
import com.hua.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hua.servicebase.exceptionhandler.MyselfException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-09
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Override
    public void saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表添加基本信息
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert<=0){
            throw new MyselfException(20001,"添加课程信息失败");
        }
        String cid=eduCourse.getId();
        System.out.println("课程id——————"+cid);
        //向课程表简介表添加课程简介
        EduCourseDescription eduCourseDescription=new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        System.out.println("CourseId:-----------"+courseId);
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo=new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update=baseMapper.updateById(eduCourse);
        if(update==0){
            throw new MyselfException(20001,"修改课程信息失败");
        }
        //修改描述表
        EduCourseDescription description=new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(description);
    }

}

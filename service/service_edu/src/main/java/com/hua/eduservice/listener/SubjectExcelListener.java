package com.hua.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hua.eduservice.entity.EduSubject;
import com.hua.eduservice.entity.excel.SubjectData;
import com.hua.eduservice.service.EduSubjectService;
import com.hua.servicebase.exceptionhandler.MyselfException;

import java.sql.Wrapper;
import java.util.Map;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    //因为SubjectExcelListener不能交给spring进行管理，所以交给Spring管理的对象不能自动注入
    //也不能实现数据库操作
    //但我们可以通过构造器引入我们想要使用的对象
    private EduSubjectService eduSubjectService;
    public SubjectExcelListener(EduSubjectService eduSubjectService){
        this.eduSubjectService=eduSubjectService;
    }

    public SubjectExcelListener() {}

    //一行一行读取excel内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            throw new MyselfException(20001,"excel表不能为空");
        }
        //判断一级分类是否为空
        EduSubject oneSubject=exitOneSubject(eduSubjectService,subjectData.getOneSubjectName());
        if(oneSubject==null){
            oneSubject=new EduSubject();
            oneSubject.setTitle(subjectData.getOneSubjectName());
            oneSubject.setParentId("0");
            eduSubjectService.save(oneSubject);
        }
        String pid=oneSubject.getId();
        //判断二级分类是否为空
        EduSubject twoSubject=exitTwoSubject(eduSubjectService,subjectData.getTwoSubjectName(),pid);
        if(twoSubject==null){
            twoSubject=new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(twoSubject);

        }
    }

    //一级分类不能重复添加
    //select * from edu_subject where title=? and parent_id='0'   0代表一级分类
    private EduSubject exitOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = eduSubjectService.getOne(wrapper);
        return oneSubject;
    }

    //二级分类不能重复添加
    private EduSubject exitTwoSubject(EduSubjectService eduSubjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = eduSubjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

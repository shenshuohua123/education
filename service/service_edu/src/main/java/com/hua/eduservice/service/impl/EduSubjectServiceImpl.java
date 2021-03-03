package com.hua.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hua.eduservice.entity.EduSubject;
import com.hua.eduservice.entity.excel.SubjectData;
import com.hua.eduservice.entity.subject.OneSubject;
import com.hua.eduservice.entity.subject.TwoSubject;
import com.hua.eduservice.listener.SubjectExcelListener;
import com.hua.eduservice.mapper.EduSubjectMapper;
import com.hua.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-09
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try{
            InputStream is = file.getInputStream();
            EasyExcel.read(is, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询所有的一级分类
        QueryWrapper<EduSubject> oneSubjectWrapper=new QueryWrapper<>();
        oneSubjectWrapper.eq("parent_id","0");
        List<EduSubject> oneSubjectList=baseMapper.selectList(oneSubjectWrapper);
        //查询所有的二级分类
        QueryWrapper<EduSubject> twoSubejctWrappper=new QueryWrapper<>();
        twoSubejctWrappper.ne("parent_id","0");
        List<EduSubject> twoSubjectList=baseMapper.selectList(twoSubejctWrappper);
        List<OneSubject> res=new ArrayList<>();
        //封装一级分类
        for(int i=0;i<oneSubjectList.size();i++) {
            EduSubject eduSubject = oneSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject, oneSubject);
            res.add(oneSubject);
            List<TwoSubject> chil = new ArrayList<>();
            //封装二级分类
            for (int j = 0; j < twoSubjectList.size(); j++) {
                EduSubject eduSubject1 = twoSubjectList.get(j);
                if (eduSubject1.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubject1, twoSubject);
                    chil.add(twoSubject);
                }
            }
            oneSubject.setChildren(chil);
        }

        return res;
    }
}

package com.hua.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hua.eduservice.entity.EduChapter;
import com.hua.eduservice.entity.EduVideo;
import com.hua.eduservice.entity.chapter.ChapterVo;
import com.hua.eduservice.entity.chapter.VideoVo;
import com.hua.eduservice.mapper.EduChapterMapper;
import com.hua.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hua.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-02-09
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapperChapter=new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChaptersList = baseMapper.selectList(wrapperChapter);

        QueryWrapper<EduVideo> wrapperVideo=new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);

        List<ChapterVo> res=new ArrayList<>();
        for(int i=0;i<eduChaptersList.size();i++){
            EduChapter eduChapter=eduChaptersList.get(i);
            ChapterVo chapterVo=new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            res.add(chapterVo);
            List<VideoVo> videoList=new ArrayList<>();
            for(int j=0;j<eduVideoList.size();j++){
                EduVideo eduVideo=eduVideoList.get(j);
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo=new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoList);
        }
        System.out.println("==========="+res.toString());
        return res;
    }
}

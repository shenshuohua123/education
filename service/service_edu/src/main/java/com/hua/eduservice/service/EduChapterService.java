package com.hua.eduservice.service;

import com.hua.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hua.eduservice.entity.chapter.ChapterVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-02-09
 */
@Service
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);
}

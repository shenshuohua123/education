package com.hua.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hua.commonutils.R;
import com.hua.eduservice.entity.EduTeacher;
import com.hua.eduservice.entity.vo.TeacherQuery;
import com.hua.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-06
 */
@Api(description = "讲师i管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value="所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        /*
        try {
            int i = 1 / 0;
        }catch(Exception e){
            throw new MyselfException(20001,"自定义异常处理");
        }*/
        //wrapper为null表示条件为空
        List<EduTeacher> list=teacherService.list(null);
        return R.ok().data("items",list);
    }
    //逻辑删除
    @ApiOperation(value="逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable String id){
        return teacherService.removeById(id)?R.ok():R.error();
    }

    @ApiOperation(value="分页查询讲师信息")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        teacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records=pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }
    @ApiOperation(value="条件查询讲师并分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,@PathVariable long limit
                           ,@RequestBody(required=false) TeacherQuery teacherQuery){
        Page<EduTeacher> pageTeacher=new Page<EduTeacher>(current,limit);
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        //根据时间排序
        wrapper.orderByDesc("gmt_create");

        teacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("total",total);
        map.put("data",records);
        return R.ok().data(map);
    }

    @ApiOperation(value = "讲师添加")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.save(eduTeacher);
        return flag?R.ok():R.error();
    }

    @ApiOperation(value="根据讲师id查询讲师信息")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher teacher=teacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    @ApiOperation(value="修改讲师信息")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher teacher) {
        boolean flag = teacherService.updateById(teacher);
        return flag ? R.ok() : R.error();
    }
    /*
    @ApiOperation(value="修改讲师信息")
    @PutMapping("{id}")
    public R updateTeacher(@RequestBody EduTeacher teacher,
                           @PathVariable String id){
        teacher.setId(id);
        boolean flag=teacherService.updateById(teacher);
        return flag?R.ok():R.error();
    }
*/

}


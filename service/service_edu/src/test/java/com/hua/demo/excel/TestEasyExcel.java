package com.hua.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        //写操作
        /*
        String filename="D:/write.xlsx";
        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());
         */
        //读操作
        String fileName="D:\\write.xlsx";
        EasyExcel .read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();
    }
    private static List<DemoData> getData(){
        List<DemoData> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            DemoData data=new DemoData();
            data.setSname("hua"+i);
            data.setSno(i);
            list.add(data);
        }
        return list;
    }
}

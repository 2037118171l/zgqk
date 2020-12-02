package com.zgqk.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zgqk.bean.Record;
import com.zgqk.config.ResponseWrapper;
import com.zgqk.dao.RecordMapper;
import com.zgqk.method.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import java.util.List;


@Service
public class RecordService {
    @Autowired
    RecordMapper recordMapper;

    /**
     * @Description:增加记录
     * @param record
     * @return
     */
    public ResponseWrapper addRecord(Record record) {
        if (record == null) {
            return ResponseWrapper.markError();
        } else {
            int i = recordMapper.insertSelective(record);
            if (i == 1) {
                return ResponseWrapper.markSuccess(i);
            }else {
                return ResponseWrapper.markError();
            }
        }
    }
    public ResponseWrapper deleteRecord(Integer id){
        if (id == null){
            return ResponseWrapper.markError();
        }else {
            int i = recordMapper.deleteByPrimaryKey(id);
            if (i == 1){
                return  ResponseWrapper.markSuccess(i);
            }else {
                return ResponseWrapper.markError();
            }
        }
    }

    /**
     * @Description: 修改记录
     * @param record
     * @return
     */
    public ResponseWrapper updateRecord(Record record){
        if (record == null){
            return ResponseWrapper.markError();
        }else {
            int i = recordMapper.updateByPrimaryKeySelective(record);
            if (i == 1){
                return  ResponseWrapper.markSuccess(i);
            }else {
                return  ResponseWrapper.markError();
            }
        }
    }

    /**
     * @Description:查询所有数据并分页
     * @param pageSize
     * @param pageNum
     * @return
     */
    public ResponseWrapper findRecord(Integer pageSize,Integer pageNum,String weeks){
        PageHelper.startPage(pageNum,pageSize);
        List<Record> records = recordMapper.findRecord(weeks);
        PageInfo record = new PageInfo(records,10);
        return ResponseWrapper.markSuccess(record);
    }
    //模糊查询
    public ResponseWrapper findBySearch(Integer pageSize,Integer pageNum,Integer id,String weeks){
        PageHelper.startPage(pageNum,pageSize);
        List<Record> records = recordMapper.findBySearch(id,weeks);
        PageInfo record = new PageInfo(records,10);
        return ResponseWrapper.markSuccess(record);
    }

    /**
     * @Description：导入excel并解析到数据库
     * @param file
     * @return
     */
    public ResponseWrapper UploadExcel(MultipartFile file) throws ParseException {
        if (file.isEmpty()) {
            try {
                throw new Exception("文件不存在！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        InputStream in = null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<List<Object>> listob = null;
        try {
            listob = new ExcelUtils().getBankListByExcel(in,file.getOriginalFilename());
            //          System.out.println("listob触发了"+listob);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
        for (int i = 0; i < listob.size(); i++) {
            List<Object> lo = listob.get(i);
            Record vo = new Record();
            Record j = null;

            //这里是主键验证，根据实际需要添加，可要可不要，加上之后，可以对现有数据进行批量修改和导入
            try {
                j = recordMapper.selectByPrimaryKey(Integer.valueOf(String.valueOf(lo.get(0))));
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
            }
            if (j == null) {
//                假如id为空则表示没有这条数据，创建这条数据
                vo.setId(Integer.valueOf(String.valueOf(lo.get(0))));
                vo.setContent(String.valueOf(lo.get(1)));
                vo.setNumber(String.valueOf(lo.get(2)));
                vo.setDeadline(String.valueOf(lo.get(3)));
                vo.setCompany(String.valueOf(lo.get(4)));
                vo.setWeeks(String.valueOf(lo.get(5)));
                addRecord(vo);
            } else {
//                如果这条数据存在则调用修改方法，修改这条数据
                vo.setId(Integer.valueOf(String.valueOf(lo.get(0))));
                vo.setContent(String.valueOf(lo.get(1)));
                vo.setNumber(String.valueOf(lo.get(2)));
                vo.setDeadline(String.valueOf(lo.get(3)));
                vo.setCompany(String.valueOf(lo.get(4)));
                vo.setWeeks(String.valueOf(lo.get(5)));
                updateRecord(vo);
            }
        }
        return ResponseWrapper.markSuccess(null);
    }



}

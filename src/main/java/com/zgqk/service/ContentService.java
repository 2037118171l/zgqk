package com.zgqk.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zgqk.bean.Content;
import com.zgqk.config.ResponseWrapper;
import com.zgqk.dao.ContentMapper;
import com.zgqk.method.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ContentService {
    @Autowired
    ContentMapper contentMapper;

    /**
     * @Description:增加方法
     * @param content
     * @return
     */
    public ResponseWrapper addContent(Content content){
        if (content == null){
            return ResponseWrapper.markError();
        }else {
            int i = contentMapper.insertSelective(content);
            if (i == 1){
                return ResponseWrapper.markSuccess(i);
            }else {
                return ResponseWrapper.markError();
            }
        }
    }

    /**
     * @Description：修改状态
     * @param content
     * @return
     */
    public ResponseWrapper updateContent(Content content){
        if (content == null){
            return ResponseWrapper.markError();
        }else {
            int i = contentMapper.updateByPrimaryKeySelective(content);
            if (i == 1){
                return ResponseWrapper.markSuccess(i);
            }else{
                return ResponseWrapper.markError();
            }
        }
    }

    /**
     * @Description:按上一级编号查询信息
     * @param supernum
     * @param pageSize
     * @param pageNum
     * @return
     */
    public ResponseWrapper findBySupernum(Integer supernum,Integer pageSize,Integer pageNum){
        PageHelper.startPage(pageNum,pageSize);
        List<Content> contents = contentMapper.findBySupernum(supernum);
        PageInfo content = new PageInfo(contents,10);
        return ResponseWrapper.markSuccess(content);
    }
    public ResponseWrapper findByCategory(String category,Integer pageSize,Integer pageNum){
        PageHelper.startPage(pageNum,pageSize);
        List<Content> contents = contentMapper.findByCategory(category);
        PageInfo content = new PageInfo(contents,10);
        return ResponseWrapper.markSuccess(content);
    }

    public ResponseWrapper findByState(Integer pageSize,Integer pageNum,String category){
        PageHelper.startPage(pageNum,pageSize);
        List<Content> contents = contentMapper.findByState(category);
        PageInfo content = new PageInfo(contents,10);
        return ResponseWrapper.markSuccess(content);
    }
    /**
     * @Description:按照id查询单条信息
     * @param id
     * @return
     */
    public ResponseWrapper findContent(Integer id){
        Content content = contentMapper.selectByPrimaryKey(id);
        return ResponseWrapper.markSuccess(content);
    }
    public ResponseWrapper UploadExc(MultipartFile file){
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
            Content vo = new Content();
            Content j = null;

            //这里是主键验证，根据实际需要添加，可要可不要，加上之后，可以对现有数据进行批量修改和导入
            try {
                j = contentMapper.selectByPrimaryKey(Integer.valueOf(String.valueOf(lo.get(0))));
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
            }
            if (j == null) {
                //  假如id为空则表示没有这条数据，创建这条数据
                vo.setId(Integer.valueOf(String.valueOf(lo.get(0))));
                vo.setTitle(String.valueOf(lo.get(1)));
                vo.setTitleNum(Integer.valueOf(String.valueOf(lo.get(2))));
                vo.setSuperNum(Integer.valueOf(String.valueOf(lo.get(3))));
                vo.setNum(String.valueOf(lo.get(4)));
                vo.setState(Integer.valueOf(String.valueOf(lo.get(5))));
                vo.setCategory(String.valueOf(lo.get(6)));
                addContent(vo);
            } else {
                // 如果这条数据存在则调用修改方法，修改这条数据
                vo.setId(Integer.valueOf(String.valueOf(lo.get(0))));
                vo.setTitle(String.valueOf(lo.get(1)));
                vo.setTitleNum(Integer.valueOf(String.valueOf(lo.get(2))));
                vo.setSuperNum(Integer.valueOf(String.valueOf(lo.get(3))));
                vo.setNum(String.valueOf(lo.get(4)));
                vo.setState(Integer.valueOf(String.valueOf(lo.get(5))));
                vo.setCategory(String.valueOf(lo.get(6)));
                updateContent(vo);
            }
        }
        return ResponseWrapper.markSuccess(null);
    }
}

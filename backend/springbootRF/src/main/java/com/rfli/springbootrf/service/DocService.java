package com.rfli.springbootrf.service;

import com.rfli.springbootrf.dao.DocDao;
import com.rfli.springbootrf.entity.DocEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocService {
    final private DocDao docDao;

    public DocService(DocDao docDao) {
        this.docDao = docDao;
    }

    /**
     * 添加文件信息
     */
    public int save(DocEntity docEntity) {
        return this.docDao.insertDoc(docEntity);
    }

    /**
     * 添加文件信息
     */
    public int saveAndInit(DocEntity docEntity) {
        return this.docDao.insertDocWithCount(docEntity);
    }

    /**
     * 更新文件信息
     */
    public int updateDoc(DocEntity docEntity) {
        return this.docDao.updateDoc(docEntity);
    }

    /**
     * 根据文件id删除文件
     */
    public int deleteDocById(int id) {
        return this.docDao.deleteDoc(id);
    }

    /**
     * 根据文件id查询文件
     */
    public DocEntity getDocById(int id) {
        return this.docDao.selectDocById(id);
    }

    /**
     * 根据文件名查询文件
     */
    public DocEntity getDocByName(String doc_identifier) {
        return this.docDao.selectDocByName(doc_identifier);
    }

    /**
     * 查询所有文件信息
     */
    public List<DocEntity> listAll() {
        return this.docDao.selectAll();
    }
}

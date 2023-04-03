package com.rfli.springbootrf.dao;

import com.rfli.springbootrf.entity.DocEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface DocDao {
    /**
     * 插入一条数据
     */
    @Insert("insert into document(doc_identifier) values(#{doc_identifier})")
    int insertDoc(DocEntity docEntity);

    /**
     * 插入一条数据，并且初始化它的 count 值
     */
    @Insert("insert into document(doc_identifier,count) values(#{doc_identifier},#{count})")
    int insertDocWithCount(DocEntity docEntity);

    /**
     * 更新文件信息
     */
    @Update("update document set doc_identifier=#{doc_identifier},count=#{count} where id=#{id}")
    int updateDoc(DocEntity docEntity);

    /**
     * 根据文件id删除文件
     */
    @Delete("delete from document where id=#{id}")
    int deleteDoc(int id);

    /**
     * 根据文件id查询文件
     */
    @Select("select id,doc_identifier,count from document where id = #{id}")
    DocEntity selectDocById(int id);

    /**
     * 根据文件名查询文件
     */
    @Select("select id,doc_identifier,count from document where doc_identifier = #{doc_identifier}")
    DocEntity selectDocByName(String doc_identifier);

    /**
     * 查询所有文件信息
     */
    @Select("select id,doc_identifier,count from document")
    List<DocEntity> selectAll();
}

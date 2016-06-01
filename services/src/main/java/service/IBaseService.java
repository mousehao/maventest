package service;


import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public interface IBaseService<T, PK extends Serializable> {
    int add(T entity);
    int addSelective(T entity);
    int updateByPrimaryKey(T entity);
    int updateByPrimaryKeySelective(T entity);
    int deleteByPrimaryKey(PK key);
    T findByPrimaryKey(PK key);
    List<T> findAll();
    List<T> findSelective(T entity);
    PageList<T> findAllByPage(PageBounds pageBounds);
    PageList<T> findSelectiveByPage(T entity, PageBounds pageBounds);
    Object findOne(String selectId, Object obj);
    List<Object> findCondition(String selectId, Object obj);
    PageList<Object> findConditionByPage(String selectId, Object obj, PageBounds pageBounds);

    void batchInsert(List<T> collection);

    int update(String sqlid, Object obj);
}

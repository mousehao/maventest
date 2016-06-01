package mybatis;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public interface IBaseDao<T, PK extends Serializable> {
    int insert(T entity);
    int insertSelective(T entity);
    int updateByPrimaryKey(T entity);
    int updateByPrimaryKeySelective(T entity);

    int delete(String sqlid, Object object);

    int deleteByPrimaryKey(PK key);
    T selectByPrimaryKey(PK key);
    List<T> selectAll();
    List<T> selectSelective(T entity);
    PageList<T> selectAllByPage(PageBounds pageBounds);
    PageList<T> selectSelectiveByPage(T entity, PageBounds pageBounds);
    Object selectOne(String selectId, Object obj);

    int update(String sqlid, Object obj);
    List<Object> selectCondition(String selectId, Object obj);
    List<T> selectConditionResultList(String selectId, Object obj);
    PageList<Object> selectConditionByPage(String selectId, Object obj, PageBounds pageBounds);

    void batchInsert(List<T> collection);

    void batchInsertByList(List<Object> collection, String selectId);
}

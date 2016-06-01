package service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import mybatis.IBaseDao;
import service.IBaseService;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public abstract class BaseServiceImpl<T, PK extends Serializable> implements IBaseService<T, PK> {
    protected IBaseDao<T, PK> baseDao;

	public void setBaseDao(IBaseDao<T, PK> baseDao) {
	};

    @Override
    public int add(T entity) {
        return baseDao.insert(entity);
    }

    @Override
    public int addSelective(T entity) {
        return baseDao.insertSelective(entity);
    }

    @Override
    public int updateByPrimaryKey(T entity) {
        return baseDao.updateByPrimaryKey(entity);
    }

    @Override
    public int updateByPrimaryKeySelective(T entity) {
        return baseDao.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int deleteByPrimaryKey(PK key) {
        return baseDao.deleteByPrimaryKey(key);
    }

    @Override
    public T findByPrimaryKey(PK key) {
        return baseDao.selectByPrimaryKey(key);
    }

    @Override
    public List<T> findAll() {
        return baseDao.selectAll();
    }

    @Override
    public List<T> findSelective(T entity) {
        return baseDao.selectSelective(entity);
    }

    @Override
    public PageList<T> findAllByPage(PageBounds pageBounds) {
        return baseDao.selectAllByPage(pageBounds);
    }

    @Override
    public PageList<T> findSelectiveByPage(T entity, PageBounds pageBounds) {
        return baseDao.selectSelectiveByPage(entity, pageBounds);
    }

    @Override
    public Object findOne(String selectId, Object obj) {
        return baseDao.selectOne(selectId, obj);
    }

    @Override
    public List<Object> findCondition(String selectId, Object obj) {
        return baseDao.selectCondition(selectId, obj);
    }

    @Override
    public PageList<Object> findConditionByPage(String selectId, Object obj, PageBounds pageBounds) {
        return baseDao.selectConditionByPage(selectId, obj, pageBounds);
	}

	@Override
    public void batchInsert(List<T> collection) {
        baseDao.batchInsert(collection);
    }

    @Override
    public int update(String sqlid, Object obj) {
        return baseDao.update(sqlid, obj);
    }
}

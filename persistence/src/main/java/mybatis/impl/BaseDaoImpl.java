package mybatis.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import mybatis.IBaseDao;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import util.ReflectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class BaseDaoImpl<T, PK extends Serializable> extends SqlSessionDaoSupport implements IBaseDao<T, PK> {
	private final static Log log = LogFactory.getLog(BaseDaoImpl.class);
	public static final String SQLID_INSERT = "insert";
	public static final String SQLID_INSERT_SELECTIVE = "insertSelective";
	public static final String SQLID_UPDATE = "updateByPrimaryKey";
	public static final String SQLID_UPDATE_SELECTIVE = "updateByPrimaryKeySelective";
	public static final String SQLID_DELETE = "deleteByPrimaryKey";
	public static final String SQLID_SELECT = "selectByPrimaryKey";
	public static final String SQLID_SELECT_ALL = "selectAll";
	public static final String SQLID_SELECT_SELECTIVE = "selectSelective";
	public static final String SQLID_BATCH_INSERT = "batchInsert";

	public static final int BATCH_SIZE = 500;

	protected String basePackage = "maven.mapper";
	protected String mapperSuffix = "Mapper";
	protected String sqlNameSpace = "";

	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	public BaseDaoImpl() {
		sqlNameSpace = ReflectionUtils.getSuperClassGenericType(getClass()).getSimpleName();
		if (sqlNameSpace.equals("Object")) {
			// sqlNameSpace = ReflectionUtils.get
		}
	}

	protected String statementName(String sqlId) {
		return (StringUtils.isNotBlank(basePackage) ? basePackage + "." : "") + sqlNameSpace + mapperSuffix + "." + sqlId;
	}

	@Override
	public int insert(T entity) {
		return getSqlSession().insert(statementName(SQLID_INSERT), entity);
	}

	@Override
	public int insertSelective(T entity) {
		return getSqlSession().insert(statementName(SQLID_INSERT_SELECTIVE), entity);
	}

	@Override
	public int updateByPrimaryKey(T entity) {
		return getSqlSession().update(statementName(SQLID_UPDATE), entity);
	}

	@Override
	public int updateByPrimaryKeySelective(T entity) {
		return getSqlSession().update(statementName(SQLID_UPDATE_SELECTIVE), entity);
	}

	@Override
	public int update(String sqlid, Object object) {
		return getSqlSession().update(statementName(sqlid), object);
	}

	@Override
	public int delete(String sqlid, Object object) {
		return getSqlSession().delete(statementName(sqlid), object);
	}

	@Override
	public int deleteByPrimaryKey(PK key) {
		return getSqlSession().delete(statementName(SQLID_DELETE), key);
	}

	@Override
	public T selectByPrimaryKey(PK key) {
		return getSqlSession().selectOne(statementName(SQLID_SELECT), key);
	}

	@Override
	public List<T> selectAll() {
		return getSqlSession().selectList(statementName(SQLID_SELECT_ALL));
	}

	@Override
	public List<T> selectSelective(T entity) {
		return getSqlSession().selectList(statementName(SQLID_SELECT_SELECTIVE), entity);
	}

	@Override
	public PageList<T> selectAllByPage(PageBounds pageBounds) {
		return (PageList<T>) getSqlSession().selectList(statementName(SQLID_SELECT_ALL), null, pageBounds);
	}

	@Override
	public PageList<T> selectSelectiveByPage(T entity, PageBounds pageBounds) {
		return (PageList<T>) getSqlSession().selectList(statementName(SQLID_SELECT_SELECTIVE), entity, pageBounds);
	}

	@Override
	public Object selectOne(String selectId, Object obj) {
		return getSqlSession().selectOne(statementName(selectId), obj);
	}

	@Override
	public List<Object> selectCondition(String selectId, Object obj) {
		return getSqlSession().selectList(statementName(selectId), obj);
	}

	@Override
	public List<T> selectConditionResultList(String selectId, Object obj) {
		return getSqlSession().selectList(statementName(selectId), obj);
	}

	@Override
	public PageList<Object> selectConditionByPage(String selectId, Object obj, PageBounds pageBounds) {
		return (PageList<Object>) getSqlSession().selectList(statementName(selectId), obj, pageBounds);
	}

	@Override
	public void batchInsert(List<T> list) {
		log.debug("current batchInsert size:" + list.size());
		long start = System.currentTimeMillis();
		int batchCount = list.size() / BATCH_SIZE + 1;
		for (int i = 0; i < batchCount; i++) {
			if (i < (batchCount - 1)) {
				int ret = getSqlSession().insert(statementName(SQLID_BATCH_INSERT), list.subList(i * BATCH_SIZE, (i +
						1) * BATCH_SIZE));
				if (0 == ret) {
					logger.warn("try to update data,but update rows is 0");
				}
			} else if (list.size() % BATCH_SIZE > 0) {
				int ret = getSqlSession().insert(statementName(SQLID_BATCH_INSERT), list.subList(i * BATCH_SIZE, list
						.size()));
				if (0 == ret) {
					logger.warn("try to update data,but update rows is 0");
				}
			}
		}
		log.debug("current batchInsert cost:" + (System.currentTimeMillis() - start));
	}

	@Override
	public void batchInsertByList(List<Object> collection, String sqlId) {
		log.debug("current batchInsert size:" + collection.size());
		long start = System.currentTimeMillis();
		int batchCount = collection.size() / BATCH_SIZE + 1;
		for (int i = 0; i < batchCount; i++) {
			if (i < (batchCount - 1)) {
				int ret = getSqlSession().insert(statementName(sqlId), collection.subList(i * BATCH_SIZE, (i +
						1) * BATCH_SIZE));
				if (0 == ret) {
					logger.warn("try to update data,but update rows is 0");
				}
			} else if (collection.size() % BATCH_SIZE > 0) {
				int ret = getSqlSession().insert(statementName(sqlId), collection.subList(i * BATCH_SIZE, collection
						.size()));
				if (0 == ret) {
					logger.warn("try to update data,but update rows is 0");
				}
			}
		}
		log.debug("current batchInsert cost:" + (System.currentTimeMillis() - start));
	}

//	public void batchInsertWithReturnValue(Collection<T> collection) {
//		SqlSession session = getSqlSession();
//		Configuration c = this.getSqlSession().getConfiguration();
//		ManagedTransactionFactory managedTransactionFactory = new ManagedTransactionFactory();
//		BatchExecutor batchExecutor = new BatchExecutor(c, managedTransactionFactory.newTransaction(session.getConnection()
//		));
//		try {
//			for (T t : collection) {
//				batchExecutor.doUpdate(c.getMappedStatement(statementName(SQLID_INSERT)), t);
//			}
//			List<BatchResult> batchResultList = batchExecutor.doFlushStatements(false);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
}

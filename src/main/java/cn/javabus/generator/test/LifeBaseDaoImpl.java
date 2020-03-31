package cn.javabus.generator.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * @author ou.zhenxing on 2020-03-31.
 */
public class LifeBaseDaoImpl {


    @Autowired
    @Qualifier("lifeObject")
    private Object sqlMapClientTemplate;


    protected Object getObject() {
        return this.sqlMapClientTemplate;
    }

    /**
     * 获取sequence_id
     *
     * @param sequenceName
     * @return
     */
    protected Long querySeq(String sequenceName) {
        return null;
    }

    /**
     * 插入数据到数据库
     *
     * @param statementName SQL名称
     * @param object        参数
     * @return Object
     */
    protected Object saveObject(String statementName, Object object) {
        return null;
    }

    /**
     * 更新数据到数据库
     *
     * @param statementName SQL名称
     * @param object        参数
     * @return Object
     */
    protected int updateObject(String statementName, Object object) {
        return 0;
    }

    /**
     * 删除数据库数据
     *
     * @param statementName SQL名称
     * @return Object
     */
    protected int deleteObject(String statementName) {
        return 0;
    }

    /**
     * 删除数据库数据
     *
     * @param statementName   SQL名称
     * @param parameterObject 参数
     * @return Object
     */
    protected int deleteObject(String statementName, Object parameterObject) {
        return 0;
    }

    /**
     * 查询数据返回List数据集
     *
     * @param statementName SQL名称
     * @return Object
     */
    protected List queryForList(String statementName) {
        return null;
    }

    /**
     * 查询数据返回List数据集
     *
     * @param statementName   SQL名称
     * @param parameterObject 参数
     * @return Object
     */
    protected List queryForList(String statementName, Object parameterObject) {
        return null;
    }

    /**
     * 查询数据返回Object数据
     *
     * @param statementName SQL名称
     * @return Object
     */
    protected Object queryForObject(String statementName) {
        return null;
    }

    /**
     * 查询数据返回Object数据
     *
     * @param statementName   SQL名称
     * @param parameterObject 参数
     * @return Object
     */
    protected Object queryForObject(String statementName, Object parameterObject) {
        return null;
    }

    /**
     * 分页查询方法
     *
     * @param statementName   SQL名称
     * @param parameterObject 参数
     * @return Object
     */
    protected Object pageForObject(String statementName, Object parameterObject) {
        return null;
    }


}

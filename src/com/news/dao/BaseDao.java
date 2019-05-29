//package com.news.dao;
//
//import org.apache.commons.dbutils.DbUtils;
//
//import java.io.Serializable;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Map;
//
//import java.util.List;
//import com.accp.db.DBM;
//import com.accp.vo.UserRoleVo;
//import org.apache.commons.dbutils.QueryRunner;
//import org.apache.commons.dbutils.handlers.BeanListHandler;
//
//
//public class BaseDao {
//    /**
//     * 分页列出所有对象
//     * @param page
//     * @param size
//     * @return
//     */
//    public List<? extends POJO> List(int page, int size) {
//        String sql = "SELECT * FROM " + TableName() + " ORDER BY id DESC";
//        return QueryHelper.query_slice(getClass(), sql, page, size);
//    }
//
//    public List<? extends POJO> Filter(String filter, int page, int size) {
//        String sql = "SELECT * FROM " + TableName() + " WHERE " + filter + " ORDER BY id DESC";
//        return QueryHelper.query_slice(getClass(), sql, page, size);
//    }
//    /**
//     * 根据主键读取对象详细资料，根据预设方法自动判别是否需要缓存
//     * @param id
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    public <T extends POJO> T Get(long id) {
//        if(id <= 0) return null;
//        String sql = "SELECT * FROM " + TableName() + " WHERE id=?";
//        boolean cached = IsObjectCachedByID();
//        return (T)QueryHelper.read_cache(getClass(), cached?CacheRegion():null, id, sql, id);
//    }
//
//    public List<? extends POJO> BatchGet(List<Long> ids) {
//        if(ids==null || ids.size()==0)
//            return null;
//        StringBuilder sql = new StringBuilder("SELECT * FROM " + TableName() + " WHERE id IN (");
//        for(int i=1;i<=ids.size();i++) {
//            sql.append('?');
//            if(i < ids.size())
//                sql.append(',');
//        }
//        sql.append(')');
//        List<? extends POJO> beans = QueryHelper.query(getClass(), sql.toString(), ids.toArray(new Object[ids.size()]));
//        if(IsObjectCachedByID()){
//            for(Object bean : beans){
//                CacheManager.set(CacheRegion(), ((POJO)bean).getId(), (Serializable)bean);
//            }
//        }
//        return beans;
//    }
//    /**
//     * 插入对象
//     * @param obj
//     * @return 返回插入对象的主键
//     */
//
//    private static long _InsertObject(POJO obj) {
//        Map<String, Object> pojo_bean = obj.ListInsertableFields();
//        String[] fields = pojo_bean.keySet().toArray(new String[pojo_bean.size()]);
//        StringBuilder sql = new StringBuilder("INSERT INTO ") ;
//        sql.append(obj.TableName());
//        sql.append('(');
//        for(int i=0;i<fields.length;i++){
//            if(i > 0) sql.append(',');
//            sql.append(fields[i]);
//        }
//        sql.append(") VALUES(");
//        for(int i=0;i<fields.length;i++){
//            if(i > 0) sql.append(',');
//            sql.append('?');
//        }
//        sql.append(')');
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try{
//            ps = QueryHelper.getConnection().prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
//            for(int i=0;i<fields.length;i++){
//                ps.setObject(i+1, pojo_bean.get(fields[i]));
//            }
//            ps.executeUpdate();
//            rs = ps.getGeneratedKeys();
//            return rs.next()?rs.getLong(1):-1;
//        }catch(SQLException e){
//            throw new DBException(e);
//        }finally{
//            DbUtils.closeQuietly(rs);
//            DbUtils.closeQuietly(ps);
//            sql = null;
//            fields = null;
//            pojo_bean = null;
//        }
//    }
//}

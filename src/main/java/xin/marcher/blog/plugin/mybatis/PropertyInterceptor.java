package xin.marcher.blog.plugin.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;
import xin.marcher.blog.common.annotion.CreateTime;
import xin.marcher.blog.common.annotion.ModifyTime;
import xin.marcher.blog.utils.DateUtil;
import xin.marcher.blog.utils.EmptyUtil;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

/**
 * Mybatis 拦截器: 给实体对象在save update时, 自动添加操作属性信息.
 *
 * @author marcher
 */
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class })})
public class PropertyInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

        // 获取SQL
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

        // 获取参数
        // 情况一 --> Object
        Object parameter = invocation.getArgs()[1];
        // 情况二 --> Map (1)使用@Param多参数传入, 由Mybatis包装成map. (2) 原始传入Map
        if (invocation.getArgs()[1] instanceof Map) {
            Map map = (Map) invocation.getArgs()[1];
            parameter = map.values().iterator().next();
        }

        if (EmptyUtil.isNotEmpty(parameter)) {
            Long now = DateUtil.getTimestamp();
            // 获取私有成员变量
            Field[] fields = parameter.getClass().getDeclaredFields();

            for (Field field : fields) {
                // inset 语句 --> 更新createTime
                if (null != field.getAnnotation(CreateTime.class)) {
                    if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                        setProperty(field, parameter, now);
                    }
                }
                // insert或update --> 更新createTime或updateTime
                if (null != field.getAnnotation(ModifyTime.class)) {
                    if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
                        setProperty(field, parameter, now);
                    }
                }
            }
        }

        return invocation.proceed();
    }

    /**
     * 为对象的操作属性赋值
     *
     * @param field     属性
     * @param parameter 参数
     * @param value     属性值
     */
    private void setProperty(Field field, Object parameter, Long value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(parameter, value);
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof org.apache.ibatis.executor.Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }
}

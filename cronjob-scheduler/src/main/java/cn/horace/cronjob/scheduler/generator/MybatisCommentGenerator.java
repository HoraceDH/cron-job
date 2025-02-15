package cn.horace.cronjob.scheduler.generator;

import cn.horace.cronjob.commons.constants.Constants;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author Horace
 */
public class MybatisCommentGenerator implements CommentGenerator {
    private Properties properties;
    private boolean suppressDate;
    private boolean suppressAllComments;
    private String nowTime;

    public MybatisCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
        nowTime = (new SimpleDateFormat(Constants.DATE_FORMAT)).format(new Date());
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        CommentGenerator.super.addModelClassComment(topLevelClass, introspectedTable);
        if (suppressAllComments) {
            return;
        }
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * Create in " + nowTime + ".");
        topLevelClass.addJavaDocLine(" * <p>");
        topLevelClass.addJavaDocLine(" * 对应数据库表：" + introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(" * ");
        topLevelClass.addJavaDocLine(" * @author Horace ");
        topLevelClass.addJavaDocLine(" */");
    }

    /**
     * 类的注释
     *
     * @param innerClass
     * @param introspectedTable
     * @param markAsDoNotDelete
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }
        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine(" * Create in " + nowTime + ".");
        innerClass.addJavaDocLine(" * <p>");
        innerClass.addJavaDocLine(" * 对应数据库表：" + introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(" * ");
        innerClass.addJavaDocLine(" * @author Horace ");
        innerClass.addJavaDocLine(" */");
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine(" * Create in " + nowTime + ".");
        innerClass.addJavaDocLine(" * <p>");
        innerClass.addJavaDocLine(" * 对应数据库表：" + introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(" * ");
        innerClass.addJavaDocLine(" * @author Horace ");
        innerClass.addJavaDocLine(" */");
    }

    /**
     * 设置字段注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks() + " " + introspectedColumn.getActualColumnName());
        field.addJavaDocLine(sb.toString().replace("\n", " "));
        field.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        field.addJavaDocLine(sb.toString().replace("\n", " "));
        field.addJavaDocLine(" */");
    }

    /**
     * 设置setter方法注释
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        sb.setLength(0);
        sb.append(" * ");
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        sb.setLength(0);
        Parameter parm = method.getParameters().get(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        sb.append(" ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        method.addJavaDocLine(" */");
    }

    /**
     * 设置getter方法注释
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        sb.setLength(0);
        sb.append(" * ");
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        sb.setLength(0);
        sb.append(" * @return ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        method.addJavaDocLine(" */");
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        if (suppressAllComments) {
            return;
        }
        return;
    }

    @Override
    public void addComment(XmlElement xmlElement) {
        return;
    }

    @Override
    public void addRootComment(XmlElement rootElement) {
        return;
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        suppressDate = Boolean.valueOf(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        suppressAllComments = Boolean.valueOf(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerEnum.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString().replace("\n", " "));
        innerEnum.addJavaDocLine(" */");
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        if ("deleteByPrimaryKey".equals(method.getName())) {
            method.addJavaDocLine(" * 根据主键删除记录");
        }
        if ("insert".equals(method.getName())) {
            method.addJavaDocLine(" * 插入记录");
        }
        if ("selectByPrimaryKey".equals(method.getName())) {
            method.addJavaDocLine(" * 根据主键查询记录");
        }
        if ("selectAll".equals(method.getName())) {
            method.addJavaDocLine(" * 查询全表记录，请慎用");
        }
        if ("updateByPrimaryKey".equals(method.getName())) {
            method.addJavaDocLine(" * 根据主键更新记录");
        }
        if ("deleteByExample".equals(method.getName())) {
            method.addJavaDocLine(" * 根据条件删除记录");
        }
        if ("insertSelective".equals(method.getName())) {
            method.addJavaDocLine(" * 插入记录，不为空的字段将作为插入字段");
        }
        if ("selectByExample".equals(method.getName())) {
            method.addJavaDocLine(" * 根据条件查询记录");
        }
        if ("updateByPrimaryKeySelective".equals(method.getName())) {
            method.addJavaDocLine(" * 根据主键将不为空的字段更新记录");
        }
        if ("countByExample".equals(method.getName())) {
            method.addJavaDocLine(" * 根据条件统计符合的记录数");
        }
        if ("updateByExampleSelective".equals(method.getName())) {
            method.addJavaDocLine(" * 根据条件更新不为空的字段");
        }
        if ("updateByExample".equals(method.getName())) {
            method.addJavaDocLine(" * 根据条件更新所有字段");
        }
        if ("batchInsert".equals(method.getName())) {
            method.addJavaDocLine(" * 批量插入记录");
        }
        if ("batchInsertSelective".equals(method.getName())) {
            method.addJavaDocLine(" * 根据字段批量插入记录");
        }
        method.addJavaDocLine(" */");
    }
}
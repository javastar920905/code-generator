package cn.javabus.generator.plugins;

import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Context;


/**
 * plugin 通过默认构造函数创建
 * 添加 findAll 方法
 * 
 * @author Administrator
 * 
 */
public class FindAllMethodPlugin extends PluginAdapter {
	private static final String M_FINDALL="findAll";
	private static final String M_COUNT="count";
	private static final String M_COUNTBYPRIMARYKEY="countByPrimaryKey";

	@Override
	public boolean validate(List<String> warnings) {
		// 返回false 其他方法不会被调用
		return true;
	}

	@Override
	public void setContext(Context context) {
		// TODO Auto-generated method stub
		super.setContext(context);
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		super.setProperties(properties);
	}

	// 对于配置中的每个表 该方法被调用
	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		// TODO Auto-generated method stub
		super.initialized(introspectedTable);
	}
	/**
	 *生成model javabean  添加默认继承的类
	 */
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		topLevelClass.addImportedType("com.mybatisdemo.dao.BaseTest");
		topLevelClass.setSuperClass("com.mybatisdemo.dao.BaseTest");
		return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
	}

	/***
	 * java客户端interface的方法
	 * clientXXXMethodGenerated(Method,TopLevelClass,IntrospectedTable) - 当Java客户端实现类生成的时候这些方法被调用.
	 * clientXXXMethodGenerated(Method, Interface,IntrospectedTable)-当Java客户端接口生成的时候这些方法被调用。
	 * clientGenerated(Interface,TopLevelClass,IntrospectedTable)方法被调用
	 * 
	 * 一个是topLevelClass，该类的实例就是表示当前正在生成的类的DOM结构
	 * 第二个introspectedTable是代表的runtime环境，包含了所有context中的配置，一般从这个类中去查询生成对象的一些规则；
	 */
	@Override
	public boolean clientGenerated(Interface interfaze,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		interfaze.addMethod(generatorFindAll(interfaze, topLevelClass,introspectedTable));
		interfaze.addMethod(generatorCount(interfaze, topLevelClass,
				introspectedTable));
		interfaze.addMethod(generatorCountByPrimaryKey(interfaze,
				topLevelClass, introspectedTable));
		return super.clientGenerated(interfaze, topLevelClass,
				introspectedTable);
	}

	private Method generatorFindAll(Interface interfaze,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		Method method = new Method(M_FINDALL);
		method.setVisibility(JavaVisibility.PUBLIC);
		FullyQualifiedJavaType ListType=new FullyQualifiedJavaType("java.util.List");
		interfaze.addImportedType(ListType);
		FullyQualifiedJavaType tableType=new FullyQualifiedJavaType("List<"+introspectedTable.getBaseRecordType()+">");
		method.setReturnType(tableType);
		return method;
	}

	//该方法构造成的类型如下 Counter countByPrimaryKey(Counter Counter);
	private Method generatorCountByPrimaryKey(Interface interfaze,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		Method method = new Method(M_COUNTBYPRIMARYKEY);
		method.setVisibility(JavaVisibility.PUBLIC);
		FullyQualifiedJavaType tableType=new FullyQualifiedJavaType("long");
		method.setReturnType(tableType);
		Parameter p= new Parameter(FullyQualifiedJavaType.getStringInstance(),"id");
		method.addParameter(p);
		return method;
	}

	private Method generatorCount(Interface interfaze,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		Method method = new Method(M_COUNT);
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType("long"));
		return method;
	}

	/**
	 * 模型方法：1 modelFieldGenerated,
	 * modelGetterMethodGenerated,modelSetterMethodGenerated for each field in
	 * the class modelExampleClassGenerated(TopLevelClass, IntrospectedTable)
	 * modelPrimaryKeyClassGenerated(TopLevelClass, IntrospectedTable)
	 * modelBaseRecordClassGenerated(TopLevelClass, IntrospectedTable)
	 * modelRecordWithBLOBsClassGenerated(TopLevelClass, IntrospectedTable)
	 */

	/**
	 * SQL映射方法：1 sqlMapXXXElementGenerated(XmlElement, IntrospectedTable)
	 * -当生成SQL映射的每个元素的时候这些方法被调用
	 * sqlMapDocumentGenerated(Document,IntrospectedTable)
	 * sqlMapDocument(GeneratedXmlFile, IntrospectedTable)
	 * contextGenerateAdditionalJavaFiles(IntrospectedTable)方法被调用
	 * contextGenerateAdditionalXmlFiles(IntrospectedTable)方法被调用
	 * contextGenerateAdditionalJavaFiles()方法被调用
	 * contextGenerateAdditionalXmlFiles()方法被调用
	 */
	@Override
	public boolean sqlMapDocumentGenerated(Document document,
			IntrospectedTable introspectedTable) {
		addSelectNode(document,introspectedTable);
		addSelectCountNode(document,introspectedTable);
		addSelectCountByPrimaryKeyNode(document,introspectedTable);
		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}
	
	//生成count方法
	private void addSelectCountNode(Document document,
			IntrospectedTable introspectedTable){
		// 数据库表名
		String tableName = introspectedTable
						.getAliasedFullyQualifiedTableNameAtRuntime();
		List<IntrospectedColumn> primaryKeys = introspectedTable.getPrimaryKeyColumns();
		IntrospectedColumn uniqueKey=primaryKeys.get(0);
		//生成insert元素
		XmlElement element=new XmlElement("select");
				element.addAttribute(new Attribute("id", M_COUNT));
		element.addAttribute(new Attribute("resultType","java.lang.long"));
		//填充内容到元素
		StringBuffer sb=new StringBuffer();
		sb.append("select count("+uniqueKey.getActualColumnName()+") from ");
		sb.append(tableName);
		element.addElement(new TextElement(sb.toString()));
		
		XmlElement rootElement = document.getRootElement();
		//设置生成位置
		rootElement.addElement(4, element);
	}
	//生成CountByPrimaryKey方法
	private void addSelectCountByPrimaryKeyNode(Document document,
			IntrospectedTable introspectedTable){
		// 数据库表名
		String tableName = introspectedTable
						.getAliasedFullyQualifiedTableNameAtRuntime();
		List<IntrospectedColumn> primaryKeys = introspectedTable.getPrimaryKeyColumns();
		IntrospectedColumn uniqueKey=primaryKeys.get(0);
		//生成insert元素
		XmlElement element=new XmlElement("select");
				element.addAttribute(new Attribute("id",M_COUNTBYPRIMARYKEY));
		element.addAttribute(new Attribute("resultType","java.lang.long"));
		//填充内容到元素
		StringBuffer sb=new StringBuffer();
		sb.append("select count("+uniqueKey.getActualColumnName()+") from ");
		sb.append(tableName);
		if (primaryKeys.size()==1) {
			String property=uniqueKey.getJavaProperty();
			String type=uniqueKey.getJdbcTypeName();
			sb.append(" where  ");
			sb.append(uniqueKey.getActualColumnName());
			sb.append("= #{");
			sb.append(property);
			sb.append(",jdbcType=");
			sb.append(type);
			sb.append("}");
		}else {//如果有多个主键
			sb.append(" where 1=1 ");
			for (IntrospectedColumn key:primaryKeys){
				String property=key.getJavaProperty();
				String type=key.getJdbcTypeName();
				//添加<if> 标签
				sb.append("<if test=\"");
				sb.append(property);
				sb.append(" !=null and ");
				sb.append(property);
				sb.append(" !='' \">");
				
				//if 中生成的内容
				sb.append(" and ");
				sb.append(key.getActualColumnName());
				sb.append("= #{");
				sb.append(property);
				sb.append(",jdbcType=");
				sb.append(type);
				sb.append("}");
				
				//关闭 <if> 标签
				sb.append("</if>");
			}
		}
		element.addElement(new TextElement(sb.toString()));
		
		XmlElement rootElement = document.getRootElement();
		//设置生成位置
		rootElement.addElement(4, element);
	}
	/**向xml中生成节点
	 * 该方法在每一个mapper.xml文件的insert/update/select节点生成是调用，我们要做的就是判断是否要插入三个属性，
	 * 如果需要插入，就往XmlElement添加三个元素即可；
	 */
	private void addSelectNode(Document document,
			IntrospectedTable introspectedTable) {
		// 数据库表名
		String tableName = introspectedTable
				.getAliasedFullyQualifiedTableNameAtRuntime();
		//生成insert元素
		XmlElement element=new XmlElement("select");
		element.addAttribute(new Attribute("id", M_FINDALL));
		//element.addAttribute(new Attribute("parameterType",tableName));
		element.addAttribute(new Attribute("resultMap","BaseResultMap"));
		
		//填充内容到元素
		StringBuffer sb=new StringBuffer();
		
		
		String bid= introspectedTable.getBlobColumnListId();
		
		sb.append("select <include refid=");
		sb.append(introspectedTable.getBaseColumnListId());
		sb.append("/>");
		//对Blob_Column_List处理
		if (isNotEmpty(bid)) {
			sb.append(", <include refid=");
			sb.append(bid);
			sb.append("/>");
		}
		
		sb.append(" from ");
		sb.append(tableName);
		
		/**
		 * 添加if查询条件
		 */
		/*sb.append(" where 1=1 ");
		List<IntrospectedColumn> columns=introspectedTable.getAllColumns();
		//循环生成if条件
		for (IntrospectedColumn column:columns) {
			String property=column.getJavaProperty();
			String type=column.getJdbcTypeName();
			sb.append("<if test=\"");
			sb.append(property);
			sb.append(" !=null and ");
			sb.append(property);
			sb.append(" !='' \">");
			
			sb.append(" and ");
			sb.append(column.getActualColumnName());
			if ("VARCHAR".equals(type)) {//
				sb.append(" like concat('%', ");
				sb.append("#{");
				sb.append(property);
				sb.append(",jdbcType=");
				sb.append(type);
				sb.append("}");
				sb.append(",'%')");
			}else {
				sb.append("= #{");
				sb.append(property);
				sb.append(",jdbcType=");
				sb.append(type);
				sb.append("}");
			}
			sb.append("</if>");
		}
		//对Blob_Column_List处理
		List<IntrospectedColumn> Blobcolumns=introspectedTable.getBLOBColumns();
		if (isNotEmpty(bid)) {
			//循环生成if条件
			for (IntrospectedColumn column:columns) {
				String property=column.getJavaProperty();
				String type=column.getJdbcTypeName();
				sb.append("<if test=\"");
				sb.append(property);
				sb.append(" !=null and ");
				sb.append(property);
				sb.append(" !='' \">");
				
				sb.append(" and ");
				sb.append(column.getActualColumnName());
				if ("VARCHAR".equals(type)) {//
					sb.append(" like concat('%', ");
					sb.append("#{");
					sb.append(property);
					sb.append(",jdbcType=");
					sb.append(type);
					sb.append("}");
					sb.append(",'%')");
				}else {
					sb.append("= #{");
					sb.append(property);
					sb.append(",jdbcType=");
					sb.append(type);
					sb.append("}");
				}
				sb.append("</if>");
			}
		}*/
		
		element.addElement(new TextElement(sb.toString()));
		
		XmlElement rootElement = document.getRootElement();
		//设置生成位置
		if (isNotEmpty(bid)) {
			rootElement.addElement(4, element);
		}else{
			rootElement.addElement(2, element);
		}
	}
	/**
	 * 判断字符串不为空  则返回true
	 * @param str
	 * @return
	 */
	private boolean isNotEmpty(String str){
		boolean flag=false;
		if (str!=null&&!"".equals(str)&&str.length()!=0) {
			flag=true;
		}
		return flag;
	}
}

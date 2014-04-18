package meng;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSON;

public class testmain {
	private static final String FILEPATH = "initDataBase.xml";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		try {
//			Document document = new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream(FILEPATH));
//			
//			doGetxmlValue(document);
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
//		String strinfo = "f.sdfa";
//		System.out.println(strinfo.indexOf("."));
		
		String strFilter = "QUERY_user#id_S_EQ";
		String strFilter2 = "QUERY_t#tresourcetype#id_S_EQ";
		doFileter(strFilter);
		doFileter(strFilter2);

	}
	
	public static void doGetxmlValue(Document document)
	{
		List<Node> childNodes = document.selectNodes("//organizations_resources/organization");
//		for (Node node : childNodes) {
//			Organization organization = (Organization) baseDao.getById(Organization.class, node.valueOf("@id"));
//			if (organization != null) {
//				organization.setResources(new HashSet());
//				List<Node> cNodes = node.selectNodes("resource");
//				for (Node n : cNodes) {
//					Resource resource = (Resource) baseDao.getById(Resource.class, n.valueOf("@id"));
//					if (resource != null) {
//						organization.getResources().add(resource);
//					}
//				}
//				logger.info(JSON.toJSONStringWithDateFormat(organization, "yyyy-MM-dd HH:mm:ss"));
//				baseDao.saveOrUpdate(organization);
//			}
//		}
		for(Node node : childNodes){
			System.out.println(node.valueOf("@id"));
			List<Node> cNodes = node.selectNodes("resource");
			for(Node n : cNodes){
				System.out.println(n.valueOf("@id"));
			}
		}
	}
	
	public static void doFileter(String name){
		if (name != null) {
			if (name.startsWith("QUERY_")) {// 如果有需要过滤的字段
				String[] filterParams = StringUtils.split(name, "_");
				if (filterParams.length == 4) {
					String columnName = filterParams[1].replaceAll("#", ".");// 要过滤的字段名称
					String columnType = filterParams[2];// 字段类型
					String operator = filterParams[3];// SQL操作符
					String placeholder = UUID.randomUUID().toString().replace("-", "");// 生成一个随机的参数名称
					System.out.println("doFileter()columnName= "+columnName
							+" ,columnType="+columnType
							+" ,operator="+operator
							+" ,placeholder="+placeholder
							);
				}
			}
		}
	}

}

package meng.service.impl;

import java.util.HashSet;
import java.util.List;

import meng.dao.base.BaseDaoI;
import meng.model.base.Torganization;
import meng.model.base.Tresource;
import meng.model.base.Tresourcetype;
import meng.model.base.Trole;
import meng.model.base.Tuser;
import meng.service.InitServiceI;
import meng.util.MD5Util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service
public class InitServiceImpl implements InitServiceI{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(InitServiceImpl.class);

	private static final String FILEPATH = "initDataBase.xml";

	@Autowired
	private BaseDaoI baseDao;

	@Override
	synchronized public void initDb() {

		try{
			Document document = new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream(FILEPATH));

			initResourcetype(document);// 初始化资源类型

			initResource(document);// 初始化资源

			initRole(document);// 初始化角色

			initRoleResource(document);// 初始化角色和资源的关系

			initOrganization(document);// 初始化机构

			initOrganizationResource(document);// 初始化机构和资源的关系

			initUser(document);// 初始化用户

			initUserRole(document);// 初始化用户和角色的关系

			initUserOrganization(document);// 初始化用户和机构的关系

		}catch(DocumentException e){
			e.printStackTrace();
		}
	}

	//初始化资源类型
	private void initResourcetype(Document document){
		logger.info("初始化资源类型\n");
		List childNodes = document.selectNodes("//resourcetypes/resourcetype");
		for (Object obj : childNodes) {
			Node node = (Node) obj;
			Tresourcetype type = (Tresourcetype) baseDao.getById(Tresourcetype.class, node.valueOf("@id"));
			if (type == null) {
				type = new Tresourcetype();
			}
			type.setId(node.valueOf("@id"));
			type.setName(node.valueOf("@name"));
			type.setDescription(node.valueOf("@description"));
			logger.info(JSON.toJSONStringWithDateFormat(type, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(type);
		}
	}

	//初始化资源
	private void initResource(Document document) {
		logger.info("初始化资源====\n");

		List childNodes = document.selectNodes("//resources/resource");
		for (Object obj : childNodes) {
			Node node = (Node) obj;
			Tresource resource = (Tresource) baseDao.getById(Tresource.class, node.valueOf("@id"));
			if (resource == null) {
				resource = new Tresource();
			}
			resource.setId(node.valueOf("@id"));
			resource.setName(node.valueOf("@name"));
			resource.setUrl(node.valueOf("@url"));
			resource.setDescription(node.valueOf("@description"));
			resource.setIconCls(node.valueOf("@iconCls"));
			resource.setSeq(Integer.parseInt(node.valueOf("@seq")));

			if (!StringUtils.isBlank(node.valueOf("@target"))) {
				resource.setTarget(node.valueOf("@target"));
			} else {
				resource.setTarget("");
			}

			if (!StringUtils.isBlank(node.valueOf("@pid"))) {
				resource.setTresource((Tresource) baseDao.getById(Tresource.class, node.valueOf("@pid")));
			} else {
				resource.setTresource(null);
			}

			Node n = node.selectSingleNode("resourcetype");
			Tresourcetype type = (Tresourcetype) baseDao.getById(Tresourcetype.class, n.valueOf("@id"));
			if (type != null) {
				resource.setTresourcetype(type);
			}

			logger.info(JSON.toJSONStringWithDateFormat(resource, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(resource);
		}
	}

	//初始化角色
	private void initRole(Document document) {
		logger.info("初始化角色====\n");

		List childNodes = document.selectNodes("//roles/role");
		for (Object obj : childNodes) {
			Node node = (Node) obj;
			Trole role = (Trole) baseDao.getById(Trole.class, node.valueOf("@id"));
			if (role == null) {
				role = new Trole();
			}
			role.setId(node.valueOf("@id"));
			role.setName(node.valueOf("@name"));
			role.setDescription(node.valueOf("@description"));
			role.setSeq(Integer.parseInt(node.valueOf("@seq")));
			logger.info(JSON.toJSONStringWithDateFormat(role, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(role);
		}
	}

	//初始化角色资源
	private void initRoleResource(Document document) {
	logger.info("初始化角色资源====\n");
		
		List<Node> childNodes = document.selectNodes("//roles_resources/role");
		for (Node node : childNodes) {
			Trole role = (Trole) baseDao.getById(Trole.class, node.valueOf("@id"));
			if (role != null) {
				role.setTresources(new HashSet());
				List<Node> cNodes = node.selectNodes("resource");
				for (Node n : cNodes) {
					Tresource resource = (Tresource) baseDao.getById(Tresource.class, n.valueOf("@id"));
					if (resource != null) {
						role.getTresources().add(resource);
					}
				}
				logger.info(JSON.toJSONStringWithDateFormat(role, "yyyy-MM-dd HH:mm:ss"));
				baseDao.saveOrUpdate(role);
			}
		}

		Trole role = (Trole) baseDao.getById(Trole.class, "0");// 将角色为0的超级管理员角色，赋予所有权限
		if (role != null) {
			role.getTresources().addAll(baseDao.find("from Tresource t"));
			logger.info(JSON.toJSONStringWithDateFormat(role, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(role);
		}
	}

	//初始化组织
	private void initOrganization(Document document) {
logger.info("初始化组织====\n");
		
		List childNodes = document.selectNodes("//organizations/organization");
		for (Object obj : childNodes) {
			Node node = (Node) obj;
			Torganization organization = (Torganization) baseDao.getById(Torganization.class, node.valueOf("@id"));
			if (organization == null) {
				organization = new Torganization();
			}
			organization.setId(node.valueOf("@id"));
			organization.setName(node.valueOf("@name"));
			organization.setAddress(node.valueOf("@address"));
			organization.setSeq(Integer.parseInt(node.valueOf("@seq")));
			organization.setIconcls(node.valueOf("@iconCls"));

			if (!StringUtils.isBlank(node.valueOf("@pid"))) {
				organization.setTorganization((Torganization) baseDao.getById(Torganization.class, node.valueOf("@pid")));
			} else {
				organization.setTorganization(null);
			}

			logger.info(JSON.toJSONStringWithDateFormat(organization, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(organization);
		}
	}

	private void initOrganizationResource(Document document) {
		logger.info("初始化组织资源====\n");
		
		List<Node> childNodes = document.selectNodes("//organizations_resources/organization");
		for (Node node : childNodes) {
			Torganization organization = (Torganization) baseDao.getById(Torganization.class, node.valueOf("@id"));
			if (organization != null) {
				organization.setTresources(new HashSet());
				List<Node> cNodes = node.selectNodes("resource");
				for (Node n : cNodes) {
					Tresource resource = (Tresource) baseDao.getById(Tresource.class, n.valueOf("@id"));
					if (resource != null) {
						organization.getTresources().add(resource);
					}
				}
				logger.info(JSON.toJSONStringWithDateFormat(organization, "yyyy-MM-dd HH:mm:ss"));
				baseDao.saveOrUpdate(organization);
			}
		}
	}

	private void initUser(Document document) {
		logger.info("初始化用户====\n");
		
		List<Node> childNodes = document.selectNodes("//users/user");
		for (Node node : childNodes) {
			Tuser user = (Tuser) baseDao.getById(Tuser.class, node.valueOf("@id"));
			if (user == null) {
				user = new Tuser();
			}
			user.setId(node.valueOf("@id"));
			user.setName(node.valueOf("@name"));
			user.setLoginname(node.valueOf("@loginname"));
			user.setPwd(MD5Util.md5(node.valueOf("@pwd")));
			user.setSex(node.valueOf("@sex"));
			user.setAge(Integer.valueOf(node.valueOf("@age")));
			logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
			List<Tuser> ul = baseDao.find("from Tuser u where u.loginname = '" + user.getLoginname() + "' and u.id != '" + user.getId() + "'");
			for (Tuser u : ul) {
				baseDao.delete(u);
			}
			baseDao.saveOrUpdate(user);
		}
	}

	private void initUserRole(Document document) {
		logger.info("初始化用户角色====\n");
		
		List<Node> childNodes = document.selectNodes("//users_roles/user");
		for (Node node : childNodes) {
			Tuser user = (Tuser) baseDao.getById(Tuser.class, node.valueOf("@id"));
			if (user != null) {
				user.setTroles(new HashSet());
				List<Node> cNodes = node.selectNodes("role");
				for (Node n : cNodes) {
					Trole role = (Trole) baseDao.getById(Trole.class, n.valueOf("@id"));
					if (role != null) {
						user.getTroles().add(role);
					}
				}
				logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
				baseDao.saveOrUpdate(user);
			}
		}

		Tuser user = (Tuser) baseDao.getById(Tuser.class, "0");// 用户为0的超级管理员，赋予所有角色
		if (user != null) {
			user.getTroles().addAll(baseDao.find("from Trole"));
			logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(user);
		}
	}

	private void initUserOrganization(Document document) {
		logger.info("初始化用户组织====\n");
		
		List<Node> childNodes = document.selectNodes("//users_organizations/user");
		for (Node node : childNodes) {
			Tuser user = (Tuser) baseDao.getById(Tuser.class, node.valueOf("@id"));
			if (user != null) {
				user.setTorganizations(new HashSet());
				List<Node> cNodes = node.selectNodes("organization");
				for (Node n : cNodes) {
					Torganization organization = (Torganization) baseDao.getById(Torganization.class, n.valueOf("@id"));
					if (organization != null) {
						user.getTorganizations().add(organization);
					}
				}
				logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
				baseDao.saveOrUpdate(user);
			}
		}

		Tuser user = (Tuser) baseDao.getById(Tuser.class, "0");// 用户为0的超级管理员，赋予所有机构
		if (user != null) {
			user.getTorganizations().addAll(baseDao.find("from Torganization"));
			logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(user);
		}
	}

}

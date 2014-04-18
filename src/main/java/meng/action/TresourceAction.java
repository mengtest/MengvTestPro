package meng.action;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meng.action.base.BaseAction;
import meng.model.base.Tresource;
import meng.model.base.SessionInfo;
import meng.model.easyui.Json;
import meng.model.easyui.Tree;
import meng.service.TresourceServiceI;
import meng.util.BeanUtils;
import meng.util.ConfigUtil;
import meng.util.HqlFilter;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
@Namespace("/base")
@Action
public class TresourceAction extends BaseAction<Tresource>{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TresourceAction.class);

	/**
	 * 注入业务逻辑， 使当前action调用servie.xxx的时候，直接是调用基础业务逻辑
	 * 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
	 * @param service
	 */
	@Autowired
	public void setService(TresourceServiceI service)
	{
		this.service = service;
	}

	/**
	 * 更新资源
	 */
	public void update() {
		Json json = new Json();
		if (!StringUtils.isBlank(data.getId())) {
			if (data.getTresource() != null && StringUtils.equals(data.getId(), data.getTresource().getId())) {
				json.setMsg("父资源不可以是自己！");
			} else {
				((TresourceServiceI) service).updateResource(data);
				json.setSuccess(true);
			}
		}
		writeJson(json);
	}

	/**
	 * 获得主菜单tree，也用于获得上级资源菜单combotree
	 */
	public void doNotNeedSecurity_getMainMenu() {
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
		
		hqlFilter.addFilter("QUERY_user#id_S_EQ", sessionInfo.getTuser().getId());
		hqlFilter.addFilter("QUERY_t#tresourcetype#id_S_EQ", "0");// 0就是只查菜单
		List<Tresource> resources = ((TresourceServiceI) service).getMainMenu(hqlFilter);
		List<Tree> tree = new ArrayList<Tree>();
		for (Tresource resource : resources) {
			Tree node = new Tree();
			BeanUtils.copyNotNullProperties(resource, node);
			node.setText(resource.getName());
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("url", resource.getUrl());
			attributes.put("target", resource.getTarget());
			node.setAttributes(attributes);
			tree.add(node);
		}
		writeJson(tree);
	}

	/**
	 * 获得资源treeGrid
	 */
	public void treeGrid() {
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
		hqlFilter.addFilter("QUERY_user#id_S_EQ", sessionInfo.getTuser().getId());
		writeJson(((TresourceServiceI) service).resourceTreeGrid(hqlFilter));
	}

	/**
	 * 获得角色的资源列表
	 */
	public void doNotNeedSecurity_getRoleResources() {
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		hqlFilter.addFilter("QUERY_role#id_S_EQ", id);
		writeJson(((TresourceServiceI) service).findResourceByFilter(hqlFilter));
	}

	/**
	 * 获得机构的资源列表
	 */
	public void doNotNeedSecurity_getOrganizationResources() {
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		hqlFilter.addFilter("QUERY_organization#id_S_EQ", id);
		writeJson(((TresourceServiceI) service).findResourceByFilter(hqlFilter));
	}

	/**
	 * 获得资源树
	 */
	public void doNotNeedSecurity_getResourcesTree() {
		treeGrid();
	}

	/**
	 * 保存一个资源
	 */
	public void save() {
		Json json = new Json();
		if (data != null) {
			SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
			((TresourceServiceI) service).saveResource(data, sessionInfo.getTuser().getId());
			json.setSuccess(true);
		}
		writeJson(json);
	}
}

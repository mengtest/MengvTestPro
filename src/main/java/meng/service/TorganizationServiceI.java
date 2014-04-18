package meng.service;

import java.util.List;

import meng.model.base.Torganization;
import meng.service.base.BaseServiceI;
import meng.util.HqlFilter;

public interface TorganizationServiceI extends BaseServiceI<Torganization>{
	
	/**
	 * 更新机构
	 * @param organization
	 */
	public void updateOrganization(Torganization torganization);
	
	/**
	 * 机构授权
	 * @param id 机构ID
	 * @param resourceIds 资源IDS
	 */
	public void grant(String id, String tresourceIds);

	/**
	 * 查找机构
	 * @param hqlFilter
	 * @return
	 */
	public List<Torganization> findOrganizationByFilter(HqlFilter hqlFilter);
	
	/**
	 * 保存机构
	 * @param organization
	 */
	public void saveOrganization(Torganization torganization, String tuserId);
}

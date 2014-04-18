package meng.service;

import java.util.List;

import meng.model.base.Tresource;
import meng.service.base.BaseServiceI;
import meng.util.HqlFilter;

public interface TresourceServiceI extends BaseServiceI<Tresource>{

	/**
	 * 获得资源树，或者combotree(只查询菜单类型的节点)
	 * @param hqlFilter
	 * @return
	 */
	public List<Tresource> getMainMenu(HqlFilter hqlFilter);
	
	/**
	 * 获得资源treeGrid
	 * @param hqlFilter
	 * @return
	 */
	public List<Tresource> resourceTreeGrid(HqlFilter hqlFilter);
	
	/**
	 * 更新资源
	 * @param resource
	 */
	public void updateResource(Tresource tresource);
	
	/**
	 * 保存一个资源
	 * @param resource
	 */
	public void saveResource(Tresource tresource, String userId);
	
	/**
	 * 查找一个符号条件的资源
	 * @param hqlFilter
	 * @return
	 */
	public List<Tresource> findResourceByFilter(HqlFilter hqlFilter);
}

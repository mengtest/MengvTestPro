package meng.service.impl;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meng.dao.base.BaseDaoI;
import meng.model.base.Torganization;
import meng.model.base.Tresource;
import meng.model.base.Trole;
import meng.model.base.Tuser;
import meng.service.TresourceServiceI;
import meng.service.base.impl.BaseServiceImpl;
import meng.util.BeanUtils;
import meng.util.HqlFilter;

@Service
public class TresourceServiceImpl extends BaseServiceImpl<Tresource> implements TresourceServiceI{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TresourceServiceImpl.class);

	@Autowired
	private BaseDaoI<Tuser> userDao;
	
	/**
	 * 由于角色与资源关联，机构也与资源关联，所以查询用户能看到的资源菜单应该查询两次，最后合并到一起。
	 */
	@Override
	public List<Tresource> getMainMenu(HqlFilter hqlFilter) {
		List<Tresource> l = new ArrayList<Tresource>();
		String hql = "select distinct t from Tresource t join t.troles role join role.tusers user";
		List<Tresource> resource_role = find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
		l.addAll(resource_role);
		
		hql = "select distinct t from Tresource t join t.torganizations organization join organization.tusers user";
		List<Tresource> resource_organization = find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
		l.addAll(resource_organization);
		
		l = new ArrayList<Tresource>(new HashSet<Tresource>(l));// 去重
		Collections.sort(l, new Comparator<Tresource>() {// 排序
					@Override
					public int compare(Tresource o1, Tresource o2) {
						if (o1.getSeq() == null) {
							o1.setSeq(1000);
						}
						if (o2.getSeq() == null) {
							o2.setSeq(1000);
						}
						return o1.getSeq().compareTo(o2.getSeq());
					}
				});
		return l;
	}

	@Override
	public List<Tresource> resourceTreeGrid(HqlFilter hqlFilter) {
		List<Tresource> l = new ArrayList<Tresource>();
		String hql = "select distinct t from Tresource t join t.troles role join role.tusers user";
		List<Tresource> resource_role = find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
		l.addAll(resource_role);
		hql = "select distinct t from Tresource t join t.torganizations organization join organization.tusers user";
		List<Tresource> resource_organization = find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
		l.addAll(resource_organization);
		l = new ArrayList<Tresource>(new HashSet<Tresource>(l));// 去重
		Collections.sort(l, new Comparator<Tresource>() {// 排序
					@Override
					public int compare(Tresource o1, Tresource o2) {
						if (o1.getSeq() == null) {
							o1.setSeq(1000);
						}
						if (o2.getSeq() == null) {
							o2.setSeq(1000);
						}
						return o1.getSeq().compareTo(o2.getSeq());
					}
				});
		return l;
	}

	@Override
	public void updateResource(Tresource tresource) {
		if (!StringUtils.isBlank(tresource.getId())) {
			Tresource t = getById(tresource.getId());
			Tresource oldParent = t.getTresource();
			BeanUtils.copyNotNullProperties(tresource, t, new String[] { "createdatetime" });
			if (tresource.getTresource() != null) {// 说明要修改的节点选中了上级节点
				Tresource pt = getById(tresource.getTresource().getId());// 上级节点
				isParentToChild(t, pt, oldParent);// 说明要将当前节点修改到当前节点的子或者孙子下
				t.setTresource(pt);
			} else {
				t.setTresource(null);
			}
		}
	}

	/**
	 * 判断是否是将当前节点修改到当前节点的子节点下
	 * 
	 * @param t
	 *            当前节点
	 * @param pt
	 *            要修改到的节点
	 * 
	 * @param oldParent
	 *            原上级节点
	 * @return
	 */
	private boolean isParentToChild(Tresource t, Tresource pt, Tresource oldParent) {
		if (pt != null && pt.getTresource() != null) {
			if (StringUtils.equals(pt.getTresource().getId(), t.getId())) {
				pt.setTresource(oldParent);
				return true;
			} else {
				return isParentToChild(t, pt.getTresource(), oldParent);
			}
		}
		return false;
	}

	/**
	 * 由于新添加的资源，当前用户的角色或者机构并没有访问此资源的权限，所以这个地方重写save方法，将新添加的资源放到用户的第一个角色里面或者第一个机构里面
	 */
	@Override
	public void saveResource(Tresource tresource, String userId) {
		save(tresource);

		Tuser user = userDao.getById(Tuser.class, userId);
		Set<Trole> roles = user.getTroles();
		if (roles != null && !roles.isEmpty()) {// 如果用户有角色，就将新资源放到用户的第一个角色里面
			List<Trole> l = new ArrayList<Trole>();
			l.addAll(roles);
			Collections.sort(l, new Comparator<Trole>() {
				@Override
				public int compare(Trole o1, Trole o2) {
					if (o1.getCreatedatetime().getTime() > o2.getCreatedatetime().getTime()) {
						return 1;
					}
					if (o1.getCreatedatetime().getTime() < o2.getCreatedatetime().getTime()) {
						return -1;
					}
					return 0;
				}
			});
			l.get(0).getTresources().add(tresource);
		} else {// 如果用户没有角色
			Set<Torganization> organizations = user.getTorganizations();
			if (organizations != null && !organizations.isEmpty()) {// 如果用户没有角色，但是有机构，那就将新资源放到第一个机构里面
				List<Torganization> l = new ArrayList<Torganization>();
				l.addAll(organizations);
				Collections.sort(l, new Comparator<Torganization>() {
					@Override
					public int compare(Torganization o1, Torganization o2) {
						if (o1.getCreatedatetime().getTime() > o2.getCreatedatetime().getTime()) {
							return 1;
						}
						if (o1.getCreatedatetime().getTime() < o2.getCreatedatetime().getTime()) {
							return -1;
						}
						return 0;
					}
				});
				l.get(0).getTresources().add(tresource);
			}
		}
	}

	@Override
	public List<Tresource> findResourceByFilter(HqlFilter hqlFilter) {
		String hql = "select distinct t from Tresource t left join t.troles role left join t.torganizations organization";
		return find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
	}
}

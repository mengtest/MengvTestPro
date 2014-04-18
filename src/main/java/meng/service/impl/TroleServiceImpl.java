package meng.service.impl;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meng.dao.base.BaseDaoI;
import meng.model.base.Tresource;
import meng.model.base.Trole;
import meng.model.base.Tuser;
import meng.service.TroleServiceI;
import meng.service.TuserServiceI;
import meng.service.base.impl.BaseServiceImpl;
import meng.util.HqlFilter;

@Service
public class TroleServiceImpl extends BaseServiceImpl<Trole> implements TroleServiceI {

	@Autowired
	private BaseDaoI<Tuser> userDao;
	
	@Autowired
	private BaseDaoI<Tresource> resourceDao;
	
	@Override
	public List<Trole> findRoleByFilter(HqlFilter hqlFilter, int page, int rows) {
		String hql = "select distinct t from Trole t join t.tusers user";
		return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams(), page, rows);
	}

	@Override
	public List<Trole> findRoleByFilter(HqlFilter hqlFilter) {
		String hql = "select distinct t from Trole t join t.tusers user";
		return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams());
	}

	@Override
	public Long countRoleByFilter(HqlFilter hqlFilter) {
		String hql = "select count(distinct t) from Trole t join t.tusers user";
		return count(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
	}

	@Override
	public void saveRole(Trole trole, String userId) {
		save(trole);

		Tuser user = userDao.getById(Tuser.class, userId);
		user.getTroles().add(trole);// 把新添加的角色与当前用户关联
	}

	@Override
	public void grant(String id, String resourceIds) {
		Trole role = getById(id);
		if (role != null) {
			role.setTresources(new HashSet<Tresource>());
			for (String resourceId : resourceIds.split(",")) {
				if (!StringUtils.isBlank(resourceId)) {
					Tresource resource = resourceDao.getById(Tresource.class, resourceId);
					if (resource != null) {
						role.getTresources().add(resource);
					}
				}
			}
		}
	}
}

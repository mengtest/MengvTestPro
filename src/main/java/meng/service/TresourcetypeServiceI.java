package meng.service;

import java.util.List;

import meng.model.base.Tresourcetype;
import meng.service.base.BaseServiceI;

/**
 * 资源类型获取
 * @author dell
 *
 */
public interface TresourcetypeServiceI extends BaseServiceI<Tresourcetype>{

	/**
	 * 获取所有资源类型
	 */
	public List<Tresourcetype> findResourcetype();
}

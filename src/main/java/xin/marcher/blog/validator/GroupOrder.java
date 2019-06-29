package xin.marcher.blog.validator;

import xin.marcher.blog.validator.group.FirstGroup;
import xin.marcher.blog.validator.group.UpdateGroup;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * 组排序
 * 指定组的验证顺序，前面组验证不通过的，后面组不进行验证
 *
 * @author marcher
 */
@GroupSequence(value = {Default.class, FirstGroup.class, UpdateGroup.class})
public interface GroupOrder {

}

package com.yxq.educenter.mapper;

import com.yxq.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2023-04-16
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {


    Integer countRegisterDay(String day);
}

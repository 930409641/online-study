package com.yxq.educenter.service;

import com.yxq.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yxq.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-04-16
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    Integer countRegister(String day);
}

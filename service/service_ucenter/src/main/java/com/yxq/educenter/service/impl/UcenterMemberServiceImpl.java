package com.yxq.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yxq.commonutils.JwtUtils;
import com.yxq.commonutils.MD5;
import com.yxq.educenter.entity.UcenterMember;
import com.yxq.educenter.entity.vo.RegisterVo;
import com.yxq.educenter.mapper.UcenterMemberMapper;
import com.yxq.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yxq.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-04-16
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(UcenterMember member) {
        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //手机号和密码非空的判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);

        //判断查询对象是否为空
        if(mobile == null) {
            throw new GuliException(20001,"登录失败");
        }

        // 可以判断是否被禁用
        if(mobileMember.getIsDisabled()) {
            throw new GuliException(20001,"登录失败");
        }

        //判断密码
        //因为存到数据库的密码是加密过后的
        //把输入的密码进行加密，再和数据库密码进行比较
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new GuliException(20001,"登录失败");
        }


        //登录成功
        // 通过用户id和用户名生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());


        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String nickname = registerVo.getNickname(); //昵称
        String mobile = registerVo.getMobile(); //手机号
        String code = registerVo.getCode(); //验证啊
        String password = registerVo.getPassword(); //密码

        //非空判断
        if(StringUtils.isEmpty(nickname) || StringUtils.isEmpty(mobile)
           || StringUtils.isEmpty(code) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001,"注册失败");
        }

        //判断验证码
        //获取redis中验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)) {
            throw new GuliException(20001,"注册失败");
        }
        //判断手机号是否存在，如果存在就不能注册
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count>0) {
            throw new GuliException(20001,"注册失败");
        }

        //数据添加到数据库
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false); //用户不禁用
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }

    @Override
    public Integer countRegister(String day) {
        return baseMapper.countRegisterDay(day);
    }
}

package net.javadog.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.javadog.chat.model.entity.Invitation;
import net.javadog.chat.model.vo.InvitationVo;

import java.util.List;

/**
 * @Description: 好友邀请表接口
 * @Author: hdx
 * @Date: 2022/1/13 16:27
 * @Version: 1.0
 */
public interface InvitationService extends IService<Invitation> {

    /**
     * 好友邀请申请
     *
     * @param friendInvitationVo
     * @return net.javadog.chat.model.vo.FriendInvitationVo
     */
    InvitationVo add(InvitationVo friendInvitationVo);

    /**
     * 好友邀请申请
     *
     * @param friendInvitationVo
     * @return
     */
    void update(InvitationVo friendInvitationVo);

    /**
     * 好友邀请-通过/拒绝
     *
     * @param friendInvitationVo
     * @return
     */
    void handle(InvitationVo friendInvitationVo);

    /**
     * 好友邀请申请列表
     *
     * @param friendInvitationVo
     * @return List<FriendInvitationVo>
     */
    List<InvitationVo> list(InvitationVo friendInvitationVo);

}

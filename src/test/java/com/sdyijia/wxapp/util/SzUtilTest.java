package com.sdyijia.wxapp.util;

import com.sdyijia.wxapp.modules.sys.bean.WxUser;
import org.junit.Test;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SzUtilTest {

    @Test
    public void nonNull() {
        WxUser wxUser = new WxUser();
        wxUser.setId(12l);
        wxUser.setSessionId("session");
        wxUser.setOpenid("123");
        wxUser.setSalt("321");
        System.out.println(SzUtil.nonNull(wxUser, WxUser.class, "sysUser", "wxUserInfo", "sessionKey"));
    }
}

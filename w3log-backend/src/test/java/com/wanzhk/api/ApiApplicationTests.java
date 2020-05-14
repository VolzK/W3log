package com.wanzhk.api;

import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiApplicationTests {

    @Test
    void contextLoads() {
        // TbUser tbUser = new TbUser();
        // tbUser.setId("1");
        // tbUser.setUsername("admin");
        // tbUser.setPassword("pas");
        //
        // String token = JwtUtils.createToken(tbUser);
        // System.out.println(token);
        //
        // boolean b = JwtUtils.verifyToken(token);
        // System.out.println(b);
        //
        System.out.println(Md5Crypt.md5Crypt("admin".getBytes()));

    }

}

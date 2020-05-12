package com.fisheep.test;

import com.fisheep.controller.UserHasGroupController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.ServletContext;


@RunWith(value = SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:springContext.xml", "classpath:springMVC.xml"})
public class TestController {
    @Autowired
    UserHasGroupController userHasGroupController;//测试的controller类
//
//    @Autowired
//    ServletContext context;

    MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userHasGroupController).build();
    }

    @Test // get请求
    public void testGetGroupByUid() throws Exception {
        // 发送请求
        ResultActions resultActions = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/getGroupByUid/4").accept(MediaType.APPLICATION_JSON));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("客户端获的数据:" + result);
    }

    @Test
    public void testGetHomeworksByUid() throws Exception {
        // 发送请求
        ResultActions resultActions = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/getHomeworksByUid").accept(MediaType.APPLICATION_JSON));
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("客户端获的数据:" + result);
    }
}

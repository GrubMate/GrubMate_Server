/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import dataClass.Group;
import dataClass.Post;
import dataClass.Request;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.junit.runners.MethodSorters;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroupControllerTests {

    public static int groupID = 0;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void aPostAGroup() throws Exception {
        Group group = new Group();
        group.groupName = "GGG";
        group.groupOwnerID = 1;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson= ow.writeValueAsString(group);
        String s;
        String response = this.mockMvc.perform(MockMvcRequestBuilders.post("/group/" + group.groupOwnerID).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        groupID = Integer.valueOf(response);
    }

    @Test
    public void aPostAGroupWithNonExistingUser() throws Exception {
        Group group = new Group();
        group.groupName = "GGG";
        group.groupOwnerID = 123456;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson= ow.writeValueAsString(group);
        String s;
        this.mockMvc.perform(MockMvcRequestBuilders.post("/group/" + group.groupOwnerID).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("-1"));
    }

    @Test
    public void bGetGroupFeedWithExistingUser() throws Exception {
        int userID = 1;
        this.mockMvc.perform(get("/group/" + userID))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void bGetGroupFeedWithNonExistingUser() throws Exception {
        int userID = 99999;
        this.mockMvc.perform(get("/group/" + userID))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void cEditAnExistingUser() throws Exception {
        Group group = new Group();
        group.groupName = "GGG";
        group.groupOwnerID = 1;
        group.groupID = groupID;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson= ow.writeValueAsString(group);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/group/" + group.groupOwnerID).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    public void cEditANonExistingUser() throws Exception {
        Group group = new Group();
        group.groupName = "GGG";
        group.groupOwnerID = 123456;
        group.groupID = 454395;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson= ow.writeValueAsString(group);
        String s;
        this.mockMvc.perform(MockMvcRequestBuilders.put("/group/" + group.groupOwnerID).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("failure"));
    }

}

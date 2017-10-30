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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import dataClass.Post;
import dataClass.User;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addEmptyUser () throws Exception {
        User user = new User();
        user.facebookID = "12345";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(user);

        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("-1"));
    }

    @Test
    public void addNonEmptyUser () throws Exception {
        User user = new User();
        user.facebookID = "12345";
        user.friendList = new ArrayList<String>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(user);

        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void addNonEmptyUserAgain () throws Exception {
        User user = new User();
        user.facebookID = "54321";
        user.friendList = new ArrayList<String>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(user);

        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    public void addExistingUser () throws Exception {
        User user = new User();
        user.facebookID = "12345";
        user.friendList = new ArrayList<String>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(user);
        String s;
        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void getNonExistingUser () throws Exception {
        int userID = 1;
        this.mockMvc.perform(get("/user/" + userID))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getExistingUser () throws Exception {
        int userID = 1;
        this.mockMvc.perform(get("/user/" + userID))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.facebookID").value("12345"));
    }


    @Test
    public void editExistingUser () throws Exception {
        User user = new User();
        user.userID = 1;
        user.facebookID = "12345";
        user.friendList = new ArrayList<String>();
        user.allFriends = new ArrayList<Integer>();
        user.allFriends.add(2);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(user);
        this.mockMvc.perform(put("/user/" + user.userID).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void editExistingUserAgain () throws Exception {
        User user = new User();
        user.userID = 2;
        user.facebookID = "54321";
        user.friendList = new ArrayList<String>();
        user.allFriends = new ArrayList<Integer>();
        user.allFriends.add(1);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(user);
        String s;
        this.mockMvc.perform(put("/user/" + user.userID).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void editNonExistingUser () throws Exception {
        User user = new User();
        user.userID = 2222;
        user.facebookID = "2222";
        user.friendList = new ArrayList<String>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(user);
        String s;
        this.mockMvc.perform(put("/user/" + user.userID).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }


}

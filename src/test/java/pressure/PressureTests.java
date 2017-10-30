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
public class PressureTests {

    @Autowired
    private MockMvc mockMvc;


    class RepeatedTask extends Thread {
        PressureTests pt;
        String FBID;

        @Override
        public void run() {
            try {
                addUser();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public RepeatedTask(PressureTests pt, String FBID) {
            this.pt = pt;
            this.FBID = FBID;
        }

        @Test
        public void addUser () throws Exception {
            User user = new User();
            user.facebookID = FBID;
            user.friendList = new ArrayList<String>();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            String requestJson=ow.writeValueAsString(user);

            pt.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                    .andDo(print()).andExpect(status().isOk());
        }
    }

    class RepeatedTask2 extends Thread {
        PressureTests pt;
        String FBID;

        @Override
        public void run() {
            try {
                addPost();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public RepeatedTask2(PressureTests pt, String FBID) {
            this.pt = pt;
            this.FBID = FBID;
        }

        @Test
        public void addPost () throws Exception {
            Post post = new Post();
            post.posterID = 1;
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            String requestJson=ow.writeValueAsString(post);

            pt.mockMvc.perform(post("/post/" + post.posterID).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                    .andDo(print()).andExpect(status().isOk());
        }
    }

    @Test
    public void bFiftyLoginAtSameTime () throws Exception {
        for (int i=0;i<50;i++) {
            new RepeatedTask(this,i+"").start();
        }
    }

    @Test
    public void add10kUsers () throws Exception {
        for (int i=10000;i<10000;i++) {
            User user = new User();
            user.facebookID = i+"";
            user.friendList = new ArrayList<String>();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            String requestJson=ow.writeValueAsString(user);

            this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                    .andDo(print()).andExpect(status().isOk());
        }
    }

    @Test
    public void cFiftyPostsSameTime () throws Exception {
        for (int i = 2000; i < 2050; i++) {
            new RepeatedTask2(this, i + "").start();
        }
    }

}

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
public class RequestControllerTests {

    public static int postID = 0;
    public static int reqID = 0;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void aPostAPost() throws Exception {

        Post post = new Post();
        post.posterID = 1;
        post.isActive = true;
        post.leftQuantity = 3;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(post);
        String s;
        String response = this.mockMvc.perform(MockMvcRequestBuilders.post("/post/" + post.posterID).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        postID = Integer.valueOf(response);
    }

    @Test
    public void bPostARequest() throws Exception {
        Request req = new Request();
        req.targetPostID = postID;
        req.requesterID = 2;
        req.status = "PENDING";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(req);
        NotificationManager.createNotificationManager();
        String response = this.mockMvc.perform(MockMvcRequestBuilders.post("/request/" + req.requesterID).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        reqID = Integer.valueOf(response);
    }


    @Test
    public void cCheckPosterGetsNotification() throws Exception { ;
        this.mockMvc.perform(get("/notification/" + 1))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(Notification.REQUEST));
    }


    @Test
    public void dAcceptRequest() throws Exception { ;
        this.mockMvc.perform(get("/request/" + 1 + "/" + reqID + "/1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    public void eCheckAcceptNotification() throws Exception { ;
        this.mockMvc.perform(get("/notification/" + 2))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(Notification.ACCEPTED));
    }


    @Test
    public void fPostARequestToNonExistingPost() throws Exception {
        Request req = new Request();
        req.targetPostID = 99999;
        req.requesterID = 2;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(req);
        NotificationManager.createNotificationManager();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/request/" + req.requesterID).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("-1"));
    }

    @Test
    public void fPostARequestByNonExistingUser() throws Exception {
        Request req = new Request();
        req.targetPostID = postID;
        req.requesterID = -1;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(req);
        NotificationManager.createNotificationManager();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/request/" + req.requesterID).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("-1"));
    }


}

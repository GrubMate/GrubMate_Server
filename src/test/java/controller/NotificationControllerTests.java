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
public class NotificationControllerTests {

    public static int groupID = 0;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void MatchNotification() throws Exception {
        int receiverID = 1;
        int type = Notification.MATCH;
        NotificationManager.createNotificationManager();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/populate/1024?receiverID=" + receiverID +"&message=hello&what=" + type))
                .andDo(print()).andExpect(status().isOk());


        this.mockMvc.perform(MockMvcRequestBuilders.get("/notification/" + receiverID))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(type));
    }

    @Test
    public void DeniedNotification() throws Exception {
        int receiverID = 1;
        int type = Notification.DENIED;
        NotificationManager.createNotificationManager();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/populate/1024?receiverID=" + receiverID +"&message=hello&what=" + type))
                .andDo(print()).andExpect(status().isOk());


        this.mockMvc.perform(MockMvcRequestBuilders.get("/notification/" + receiverID))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(type));
    }
    @Test
    public void AcceptedNotification() throws Exception {
        int receiverID = 1;
        int type = Notification.ACCEPTED;
        NotificationManager.createNotificationManager();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/populate/1024?receiverID=" + receiverID +"&message=hello&what=" + type))
                .andDo(print()).andExpect(status().isOk());


        this.mockMvc.perform(MockMvcRequestBuilders.get("/notification/" + receiverID))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(type));
    }
    @Test
    public void RatingNotification() throws Exception {
        int receiverID = 1;
        int type = Notification.RATING;
        NotificationManager.createNotificationManager();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/populate/1024?receiverID=" + receiverID +"&message=hello&what=" + type))
                .andDo(print()).andExpect(status().isOk());


        this.mockMvc.perform(MockMvcRequestBuilders.get("/notification/" + receiverID))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(type));
    }
    @Test
    public void RequestNotification() throws Exception {
        int receiverID = 1;
        int type = Notification.REQUEST;
        NotificationManager.createNotificationManager();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/populate/1024?receiverID=" + receiverID +"&message=hello&what=" + type))
                .andDo(print()).andExpect(status().isOk());


        this.mockMvc.perform(MockMvcRequestBuilders.get("/notification/" + receiverID))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(type));
    }




}

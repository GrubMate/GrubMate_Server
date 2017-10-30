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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import dataClass.Post;
import dataClass.Subscription;
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
public class SubscriptionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    public static int subID = 0;

    @Test
    public void aPostASubscription() throws Exception {
        Subscription sub = new Subscription();
        sub.subscriberID = 1;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(sub);

        String response = this.mockMvc.perform(MockMvcRequestBuilders.post("/subscription/" + sub.subscriberID).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        subID = Integer.valueOf(response);
    }

    @Test
    public void bGetExistingUserSubscription () throws Exception {
        int userID = 1;
        this.mockMvc.perform(get("/subscription/" + userID))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userID));
    }

    @Test
    public void bGetNonExistingUserSubscription () throws Exception {
        this.mockMvc.perform(get("/subscription/" + 12345))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void cDeleteExistingSubscription () throws Exception {
        int userID = 1;
        this.mockMvc.perform(delete("/subscription/" + userID + "/" + subID))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    public void cDeleteNonExistingSubscription () throws Exception {
        int userID = 1;
        this.mockMvc.perform(delete("/subscription/" + userID + "/" + subID))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("failure"));
    }


}

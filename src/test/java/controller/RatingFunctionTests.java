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
import org.junit.Test;
import org.junit.runner.RunWith;
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

public class RatingFunctionTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void rateExistingUser () throws Exception {
        Integer usrID = 1;
        Integer rating = 5;
        this.mockMvc.perform(post("/user/1/" + usrID + "/" + rating))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("success"));

    }

    @Test
    public void rateNonExistingUser () throws Exception {
        Integer usrID = 999999;
        Integer rating = 5;
        this.mockMvc.perform(post("/user/1/" + usrID + "/" + rating))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("failure"));

    }

    @Test
    public void rateWithInvalidRating () throws Exception {
        Integer usrID = 1;
        Integer rating = 5000;
        this.mockMvc.perform(post("/user/1/" + usrID + "/" + rating))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("failure"));
    }


}

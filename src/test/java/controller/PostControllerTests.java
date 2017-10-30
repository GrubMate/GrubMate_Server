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
public class PostControllerTests {


    public static int postID = 0;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getPostsVisibleToANonExistingUserPost () throws Exception {
        this.mockMvc.perform(get("/post/" + 123456)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getPostsVisibleToAnExistingUserPost () throws Exception {
        this.mockMvc.perform(get("/post/" + 1)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void getNonExistingUserPost () throws Exception {
        this.mockMvc.perform(get("/post/" + -1 + "/true")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(get("/post/" + -1 + "/false")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getExistingUserPost () throws Exception {
        this.mockMvc.perform(get("/post/" + 1 + "/true")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        this.mockMvc.perform(get("/post/" + 1 + "/false")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void aPostAPost() throws Exception {

        Post post = new Post();
        post.posterID = 1;
        post.isActive = true;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(post);

        String response = this.mockMvc.perform(MockMvcRequestBuilders.post("/post/" + post.posterID).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        postID = Integer.valueOf(response);
    }

    @Test
    public void bEditAnExistingPost() throws Exception {

        Post post = new Post();
        post.posterID = 1;
        post.postID = postID;
        post.isActive = true;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(post);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/post/" + post.posterID).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    public void bEditANonExistingPost() throws Exception {

        Post post = new Post();
        post.posterID = 10000;
        post.postID = 99999;
        post.isActive = true;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(post);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/post/" + post.posterID).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("failure"));
    }

    @Test
    public void cDeleteAnExistingPost() throws Exception {
        Integer posterID = 1;
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/post/" + posterID + "/" + postID))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    public void cDeleteANonExistingPost() throws Exception {
        Integer posterID = 1;
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/post/" + posterID + "/" + 99999))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("failure"));
    }

    @Test
    public void dPostAPost() throws Exception {

        Post post = new Post();
        post.posterID = 1;
        post.isActive = true;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(post);

        String response = this.mockMvc.perform(MockMvcRequestBuilders.post("/post/" + post.posterID).contentType(MediaType.APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        postID = Integer.valueOf(response);
    }


//    @Test
//    public void paramGreetingShouldReturnTailoredMessage() throws Exception {
//
//        this.mockMvc.perform(get("/greeting").param("name", "Spring Community"))
//                .andDo(print()).andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").value("Hello, Spring Community!"));
//    }

}

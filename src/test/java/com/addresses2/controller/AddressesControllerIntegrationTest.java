package com.addresses2.controller;

import com.addresses2.categories.IntegrationTestCategory;
import com.addresses2.repository.UserRepository;
import com.google.gson.Gson;
import com.addresses2.dto.UserAddressRequestDTO;
import com.addresses2.repository.UserAddressRepository;
import com.addresses2.service.UserAddressService;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("testing")
@Category(IntegrationTestCategory.class)
public class AddressesControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserAddressRepository userAddressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAddressService userAddressService;

    @Before
    public void setUp() {
        mockMvc = getMockMvc();
        userAddressRepository.deleteAll();
        userRepository.deleteAll();
    }

    private MockMvc getMockMvc() {
        return MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    private String getValidEmail(){
        return "user@mail.com";
    }

    private String getValidCep(){
        return "01401-971";
    }

    private MediaType getContentType(){
        return new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    }

    protected String json(Object o) throws IOException {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    @Test
    public void testAddressGetWithValidCep() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/addresses/{cep}", getValidCep())
                .contentType(getContentType())
                .characterEncoding("utf-8"));

        result.andDo(print());

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.cep", equalTo("01401-971")));
    }

    @Test
    public void testAddressGetWithInvalidCep() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/addresses/{cep}", "00ABCDEF")
                .contentType(getContentType())
                .characterEncoding("utf-8")
                .with(user(getValidEmail())));

        result.andDo(print());

        result.andExpect(status().is4xxClientError());
    }

    @Test
    public void testAddressGetWithValidInexistentCep() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/addresses/{cep}", "00000000")
                .contentType(getContentType())
                .characterEncoding("utf-8")
                .with(user(getValidEmail())));

        result.andDo(print());

        result.andExpect(status().is4xxClientError());
    }

    @Test
    public void testAddUserAddress() throws Exception{
        //given
        UserAddressRequestDTO dto = UserAddressRequestDTO.builder()
                .email(getValidEmail())
                .cep("80730360")
                .logradouro("Rua Euclides da Cunha")
                .bairro("Bigorrilho")
                .uf("PR")
                .cidade("Curitiba")
                .numero(123)
                .build();

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/addresses/user")
                .contentType(getContentType())
                .characterEncoding("utf-8")
                .with(user(getValidEmail()))
                .content(json(dto)));

        //then
        result.andDo(print());

        result.andExpect(status().isOk());
        assertEquals(1L, userAddressRepository.count());
    }

    @Test
    public void testAddIncompleteUserAddress() throws Exception{
        //given
        String dto = "{" +
                "\"email\"=\""+getValidEmail() + "\"," +
                "\"cep\"=\"80730360\"" +
                "}";

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/addresses/user")
                .contentType(getContentType())
                .characterEncoding("utf-8")
                .with(user(getValidEmail()))
                .content(dto));

        //then
        result.andDo(print());

        result.andExpect(status().is4xxClientError());
        assertEquals(0, userAddressRepository.count());
    }

    @Test
    public void testGetUserAddresses() throws Exception{
        //given
        UserAddressRequestDTO dto = UserAddressRequestDTO.builder()
                .email(getValidEmail())
                .cep("80730-360")
                .logradouro("Rua Euclides da Cunha")
                .bairro("Bigorrilho")
                .uf("PR")
                .cidade("Curitiba")
                .numero(123)
                .build();
        userAddressService.addUserAddress(dto);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/addresses/user/{email}", getValidEmail())
                .contentType(getContentType())
                .characterEncoding("utf-8")
                .with(user(getValidEmail())));

        result.andDo(print());

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.[0]", notNullValue()))
                .andExpect(jsonPath("$.data.[0].cep", equalTo("80730-360")));

    }
}

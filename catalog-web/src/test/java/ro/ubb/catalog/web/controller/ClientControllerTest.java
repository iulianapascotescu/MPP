package ro.ubb.catalog.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.charts.ChartLegend;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.service.ClientServiceInterface;
import ro.ubb.catalog.web.converter.ClientConverter;
import ro.ubb.catalog.web.dto.ClientDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ClientControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientServiceInterface clientService;

    @Mock
    private ClientConverter clientConverter;

    private Client client1;
    private Client client2;
    private ClientDto clientDto1;
    private ClientDto clientDto2;


    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(clientController)
                .build();
        initData();
    }

    private void initData() {
        client1 = Client.builder().name("sn1").age(55).build();
        client1.setId(1);
        client2 = Client.builder().name("sn2").age(33).build();
        client2.setId(2);

        clientDto1 = createClientDto(client1);
        clientDto2 = createClientDto(client2);

    }

    private ClientDto createClientDto(Client client) {
        ClientDto clientDto = ClientDto.builder()
                .name(client.getName())
                .age(client.getAge())
                .build();
        clientDto.setId(client.getId());
        return clientDto;
    }


    @Test
    public void getClients() throws Exception {
        List<Client> clients = Arrays.asList(client1, client2);
        List<ClientDto> clientDtos = new ArrayList<>();
        clientDtos.add(clientDto1);
        clientDtos.add(clientDto2);

        when(clientService.getAllClients()).thenReturn(clients);
        when(clientConverter.convertModelsToDtos(clients)).thenReturn(clientDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", anyOf(is("sn1"), is("sn2"))))
                .andExpect(jsonPath("$[1].age", anyOf(is(55), is(33))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        verify(clientService, times(1)).getAllClients();
        verify(clientConverter, times(1)).convertModelsToDtos(clients);
        verifyNoMoreInteractions(clientService, clientConverter);
    }

    @Test
    public void deleteClient() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/clients/{client_name}", client1.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(client1.getName()))
                .andExpect(status().isOk());
    }

    private String toJsonString(ClientDto studentDto) {
        try {
            return new ObjectMapper().writeValueAsString(studentDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createClient() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/clients", clientDto1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(clientDto1)))
                .andExpect(status().isOk());
    }

    /*
    @Test
    public void updateClient() throws Exception {
        Client client = new Client(clientDto1.getName(), clientDto1.getAge(), null);
        client.setId(clientDto1.getId());
        clientService.updateClient(client);
        when(clientService.findByName(clientDto1.getName())).thenReturn(client1);

        when(clientConverter.convertModelToDto(client1)).thenReturn(clientDto1);


        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/clients/{client_name}", client1.getName(), clientDto1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(clientDto1)))

                .andExpect(status().isOk());

        //verify(clientService, times(1)).updateClient(client1.getName(),
          //      new Client(clientDto1.getId(),clientDto1.getName(),clientDto1.getAge()));
        verify(clientConverter, times(0)).convertModelToDto(client1);

    }
     */


    /*
    @Test
    public void updateStudent() throws Exception {

        when(studentService.updateStudent(student1.getId(),
                studentDto1.getSerialNumber(),studentDto1.getName(),
                studentDto1.getGroupNumber(),studentDto1.getDisciplines()))
        .thenReturn(student1);

        when(studentConverter.convertModelToDto(student1)).thenReturn(studentDto1);

//        Map<String, StudentDto> studentDtoMap = new HashMap<>();
//        studentDtoMap.put("student", studentDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                .put("/students/{studentId}", student1.getId(), studentDto1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(studentDto1)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is("s1")));

        verify(studentService, times(1)).updateStudent(student1.getId(),
                studentDto1.getSerialNumber(),studentDto1.getName(),
                studentDto1.getGroupNumber(),studentDto1.getDisciplines());
        verify(studentConverter, times(1)).convertModelToDto(student1);
        verifyNoMoreInteractions(studentService, studentConverter);
    }

    private String toJsonString(Map<String, StudentDto> studentDtoMap) {
        try {
            return new ObjectMapper().writeValueAsString(studentDtoMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJsonString(StudentDto studentDto) {
        try {
            return new ObjectMapper().writeValueAsString(studentDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createStudent() throws Exception {

    }

    @Test
    public void deleteStudent() throws Exception {

    }

     */
}
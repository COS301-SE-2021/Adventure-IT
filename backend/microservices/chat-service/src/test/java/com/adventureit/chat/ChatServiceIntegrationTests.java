package com.adventureit.chat;


import com.adventureit.chat.controller.ChatController;
import com.adventureit.chat.entity.GroupChat;
import com.adventureit.chat.repository.ColorPairRepository;
import com.adventureit.chat.repository.DirectChatRepository;
import com.adventureit.chat.repository.GroupChatRepository;
import com.adventureit.shareddtos.chat.responses.GroupChatResponseDTO;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"service-registry-client.instance.hostname=localhost","service-registry-client.client.service-url.defaultZone=http://localhost:8761/eureka/",
        "service-registry-client.client.register-with-eureka=true", "service-registry-client.client.fetch-registry=true","chat-microservice.application-name=CHAT-MICROSERVICE",
        "chat-microservice.datasource.url=jdbc:postgresql://adventure-it-db.c9gozrkqo8dv.us-east-2.rds.amazonaws.com/adventureit?socketTimeout=5",
        "chat-microservice.datasource.username=postgres","chat-microservice.datasource.password=310PB!Gq%f&J","chat-microservice.datasource.hikari.maximum-pool-size=2",
        "chat-microservice.jpa.hibernate.ddl-auto=update","chat-microservice.jpa.show-sql=false","chat-microservice.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect",
        "chat-microservice.jpa.properties.hibernate.format_sql=true", "firebase-path: C:\\Users\\sgood\\Documents\\CS\\SEM 2\\COS301\\adventure-it-bc0b6-firebase-adminsdk-o2fq8-ad3a51fb5e.json",
        "firebase-type: service_account", "firebase-project_id: adventure-it-bc0b6", "firebase-private_key_id: ad3a51fb5ea33531bf0dae65cb89dc82ea839f0c",
        "firebase-private_key: -----BEGIN PRIVATE KEY-----\\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDScrmjF/vCUU7e\\nBef/Vd/oaskhCeSH2o0JqoR5S5eU6JB9YFQeLDhrg1awnSIQbqe29n+XyILYQlHF\\nwlVjo1LzZkCkY8qfl0KMZl1q9PMEEK7VSfN7LQjMC+wOjc55HrL2a1NIZ3Dw52Ln\\nsfA6Mb1R4aiZeQBsuVSUdKwXZMsydzl17kbLf1d99I4HI7SsaBvnsk3IhiI9Lk7F\\nBkD9qLhR6xbRDVBms6QSZiNMeqavrZryinnODsH+kBWecpp5GN1LIsPVeap2S5tM\\nX6iY8CKDFKbC3igSn9kVvEFYL8n1/qRrFwkut7kKVynX4thXwnDVfv8IWQx5tVMV\\nnHECGsBTAgMBAAECggEAXF3fTY6ewNyIBZnJCEBMPMn1yir544jQo1/0sfo0Jzbm\\nCClp8i5Nex7Tw0PMajLvKLZLLTbj+wAsvOQ9LzTFmTAVijGEgwRUQKRDN0kYin26\\nBsJk+/i5pjlLW93wtCd9u/tCPAKuxwV/2xq1ygz/v7sQEYBS5+V2Eoyc5c2nA9gV\\nc08ShvNaqoavU/NlOE3wke1BKegGrqZqSAlUiriNNSVtZhuz6EH1AzH38qXV8YJj\\nX+Dsu8HGCfsY0vZi5PvzaG8PMrqLhELCax1CGW0bbguJB6xd30KrtCSayumIEc7H\\nNMVZ2Aaii2OedErGEIDEd8K2ut35ZVijzbbU+77hiQKBgQDx3mEnglnIxBd7jNLE\\nx+5UA5ZCQUQMN99dSppiEVwV3xltDF9S+jcdK2jQsRKyrEZqnbo3EChAtXwwFiLr\\npESVeEAL7vPZP4MVmO4wqMwZc9YguH01gDMt5thhSkpeitxmZ3eJzlMD2uUBbew2\\nWCvnsYtFpfSD2KbibdPW1VZ42wKBgQDevmPZvxj8RWELh756vw4JKe9ZYOFZJFP1\\nfhLihcfye7IGFO3EJQa2camxol9EH09h+Ot4lZajtllW6WS8oJai+0qQsIi1H5Hr\\nh6HRzYAsJnyM7VtwKUSP0Vhtp8R0Zl6Gexb8bfdC2BwzMUPLvZ+vXXYZKNJ4qPD9\\nNS3NAFOT6QKBgBHkUWuKyPmBB/urvyuvXoH4gfUEvvPobi2Ih0MZ5aX4ivj2IVcS\\nC2GtBGPrtWZiOBNK96t7Fn8y7azg9lRYInqsGpDHbGJ1wEyos3YGBpMbboudGiYL\\nBb4vhXIs/LNhskwg+0bGbH2sg6RHbWHXw+evyo2saRoXvMCjPzh1L6BhAoGAWAjL\\nkg3zJBGPr2zxHbZRJ9IJJTwjFIZFIKu5bwoM4ot86uZuqq0voAAAX5KbMGNnjoNB\\nHaGRrhat7KnGBL87iiLjb5g2D8/wbjRnAnLEC68SXuiY0RWeYXEOEBjUjmS/S0tu\\n5EnaBfNAAgOgle/WIws/V+ZIeSPcS1cvSOyuG7ECgYBLbwb3TfGPIo+Kr2QRg84v\\nmrTSkTPeyWEdLYyxldPrfkyZFLAvDwjBhtSxUMTRQOswtXV2jOFa4K/ymo2PcTvC\\nOv3PAOjG8sgfjHvCnXLnOt2bngh80TrYjVH7azydOmMOAgc+BGwnV0EK/ZxW9bN0\\nksAkgPmSheUkFAP6WHVwHg==\\n-----END PRIVATE KEY-----\\n",
        "firebase-client_email: firebase-adminsdk-o2fq8@adventure-it-bc0b6.iam.gserviceaccount.com", "firebase-client_id: 100165871578157035845", "firebase-auth_uri: https://accounts.google.com/o/oauth2/auth",
        "firebase-token_uri: https://oauth2.googleapis.com/token", "firebase-auth_provider_x509_cert_url: https://www.googleapis.com/oauth2/v1/certs", "firebase-client_x509_cert_url: https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-o2fq8%40adventure-it-bc0b6.iam.gserviceaccount.com"})
public class ChatServiceIntegrationTests {
    @Autowired
    private ChatController chatController;

    @Autowired
    GroupChatRepository groupChatRepository;
    @Autowired
    DirectChatRepository directChatRepository;
    @Autowired
    ColorPairRepository colorPairRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Description("Ensure that the Budget Controller loads")
    public void chatControllerLoads() throws Exception {
        Assertions.assertNotNull(chatController);
    }

    @Test
    @Description("Ensure that the controller is accepting traffic and responding")
    public void httpTest_returnResponse(){
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/chat/test", String.class),"Chat Controller is functioning");
    }

    @Test
    @Description("Ensure that the get group chat by adventure id is working")
    public void httpGetGroupChatByAdventureID_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        GroupChat chat = new GroupChat(id, adventureID, new ArrayList<>(), new ArrayList<>(), "Mock");
        groupChatRepository.saveAndFlush(chat);
        GroupChatResponseDTO responseDTO = this.restTemplate.getForObject("http://localhost:" + port + "/chat/getGroupChatByAdventureID/" + adventureID, GroupChatResponseDTO.class);
        Assertions.assertTrue(responseDTO != null);
    }

    @Test
    @Description("Ensure that the get group chat by id is working")
    public void httpGetGroupChatByID_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        GroupChat chat = new GroupChat(id, adventureID, new ArrayList<>(), new ArrayList<>(), "Mock");
        groupChatRepository.saveAndFlush(chat);
        GroupChatResponseDTO responseDTO = this.restTemplate.getForObject("http://localhost:" + port + "/chat/getGroupChat/" + id, GroupChatResponseDTO.class);
        Assertions.assertTrue(responseDTO != null);
    }

    @Test
    @Description("Ensure that the add participant to group chat function is working")
    public void httpAddParticipant_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID chatID = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID userID1 = UUID.randomUUID();
        UUID userID2 = UUID.randomUUID();
        GroupChat chat = new GroupChat(chatID, adventureID, List.of(userID2), new ArrayList<>(), "Mock");
        groupChatRepository.saveAndFlush(chat);
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/chat/addParticipant/" + adventureID + "/" + userID1, String.class),userID1 +" successfully added to chat "+adventureID);
    }
}

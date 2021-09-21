package com.adventureit.notificationservice;

import com.adventureit.notificationservice.entity.Notification;
import com.adventureit.notificationservice.service.NotificationService;
//import com.adventureit.userservice.Entities.Users;
//import com.adventureit.userservice.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.UUID;
@TestPropertySource(properties = {"service-registry-client.instance.hostname=localhost","service-registry-client.client.service-url.defaultZone=http://localhost:8761/eureka/","service-registry-client.client.register-with-eureka=true", "service-registry-client.client.fetch-registry=true","notification-microservice.application-name=NOTIFICATION-MICROSERVICE", "notification-microservice.datasource.url=jdbc:postgresql://adventure-it-db.c9gozrkqo8dv.us-east-2.rds.amazonaws.com/adventureit?socketTimeout=5","notification-microservice.datasource.username=postgres","notification-microservice.datasource.password=310PB!Gq%f&J","notification-microservice.datasource.hikari.maximum-pool-size=2","notification-microservice.jpa.hibernate.ddl-auto=update","notification-microservice.jpa.show-sql=false","notification-microservice.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect","notification-microservice.jpa.properties.hibernate.format_sql=true" })
public class NotificationIntegrationTest {


}

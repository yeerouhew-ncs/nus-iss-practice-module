package tbs.tbsapi.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queue.name}")
    private String queue;

    @Value("${spring.rabbitmq.queue.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.queue.routing.key}")
    private String routingkey;

//    @Value("${spring.rabbitmq.addresses}")
//    private String uri;
//
//    @Value("${spring.rabbitmq.virtual-host}")
//    private String rabbitmqVirtualHost;
//
//    @Value("${spring.rabbitmq.username}")
//    private String rabbitmqUsername;
//
//    @Value("${spring.rabbitmq.password}")
//    private String rabbitmqPassword;
//
//    @Bean
//    public CachingConnectionFactory rabbitConnectionFactory() throws Exception
//    {
//
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.getRabbitConnectionFactory().setUri(uri);
//        connectionFactory.setUsername(rabbitmqUsername);
//        connectionFactory.setPassword(rabbitmqPassword);
//        connectionFactory.setVirtualHost(rabbitmqVirtualHost);
//        connectionFactory.setConnectionTimeout(600);
//        return connectionFactory;
//    }



    @Bean
    public Queue queue(){
        return new Queue(queue);
    }
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(routingkey);
    }
    @Bean
    public MessageConverter converter(){
        return  new Jackson2JsonMessageConverter();
    }
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }


}

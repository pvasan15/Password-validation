package com.password.validation.client;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.password.validation.config.AppConfig;
import com.password.validation.service.PasswordService;

public class Client {

	@Autowired
	private PasswordService passwordService;
	
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        Client client = ctx.getBean(Client.class);
        StringBuilder sb = new StringBuilder();
        for(int len=0; len<args.length; len++)
        {
        	sb.append(args[len]);
        	if(len != args.length-1)
        		sb.append(" ");
        }
        Set<String> errors = client.validatePassword(sb.toString());
        for (String error : errors) {
            System.out.println(error);
        }
        ctx.close();
    }

    public Set<String> validatePassword(String password) {
        return passwordService.validate(password);
    }

}

package com.cdl.escrow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class EscrowApplication {

	public static void main(String[] args) {
		SpringApplication.run(EscrowApplication.class, args);
	}

}

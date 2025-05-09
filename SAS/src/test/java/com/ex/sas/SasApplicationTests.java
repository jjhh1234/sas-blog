package com.ex.sas;


import com.ex.sas.Domain.Post.Entity.PostEntity;
import com.ex.sas.Domain.Post.Repository.PostRepository;
import com.ex.sas.Domain.User.Entity.UserEntity;
import com.ex.sas.Domain.User.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
class SasApplicationTests {

	@Transactional
	@Test
	void contextLoads() {
		
	}
}

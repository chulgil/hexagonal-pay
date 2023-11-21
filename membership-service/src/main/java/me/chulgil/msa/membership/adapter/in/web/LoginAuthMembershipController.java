package me.chulgil.msa.membership.adapter.in.web;

import me.chulgil.msa.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
class LoginAuthMembershipController {

	@PostMapping(path = "/membership/login/")
	ResponseEntity<Object> loginByMembershipIdPw(){

		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

	@PostMapping(path = "/membership/auth/")
	ResponseEntity<Object> authByToken(){

		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}
}

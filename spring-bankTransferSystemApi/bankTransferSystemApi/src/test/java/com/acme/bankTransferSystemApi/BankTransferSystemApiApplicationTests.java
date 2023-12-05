package com.acme.bankTransferSystemApi;

import com.acme.bankTransferSystemApi.repository.TransferRepository;
import com.acme.bankTransferSystemApi.service.TransferService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class BankTransferSystemApiApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TransferService service;

	@Mock
	TransferRepository repository;

	@Test
	void shouldScheduleTransfer() throws Exception {
		HashMap<String, String> transfer = new HashMap<>();

		transfer.put("userId", UUID.randomUUID().toString());
		transfer.put("sourceAccount", "2396407699");
		transfer.put("destinationAccount", "9573327091");
		transfer.put("amount", "1000");
		transfer.put("dateToTransfer", "01/01/2024");
		transfer.put("scheduleDate", getTodaysDate());

		mockMvc.perform(post("/transfer")
				.content(asJsonString(transfer))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(201));
	}

	@Test
	void shouldNotScheduleTransfer() throws Exception {
		HashMap<String, String> transfer = new HashMap<>();

		transfer.put("userId", UUID.randomUUID().toString());
		transfer.put("sourceAccount", "2396407699");
		transfer.put("destinationAccount", "9573327091");
		transfer.put("amount", "1000");
		transfer.put("dateToTransfer", "01/01/2030");
		transfer.put("scheduleDate", getTodaysDate());

		mockMvc.perform(post("/transfer")
				.content(asJsonString(transfer))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(500));
	}

	@Test
	void shouldNotReturnStatementsIncaseUserNeverScheduledATransfer() throws Exception {
		String userId = UUID.randomUUID().toString();

		mockMvc.perform(get("/statement/"+userId))
				.andExpect(status().is(200));

		assertEquals("Should have returned an empty array", 0, service.findByUserId(userId).size());
	}

	private String getTodaysDate() {
		SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		return pattern.format(today);
	}

	private String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}

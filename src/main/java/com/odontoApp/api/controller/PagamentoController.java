package com.odontoApp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.time.LocalDateTime;
import com.odontoApp.api.domain.pagamento.*;
import com.odontoApp.api.domain.consulta.ConsultaRepository;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("api/v1/pagamentos")
@SecurityRequirement(name = "bearer-key")
public class PagamentoController {

	private static final Logger logger = LogManager.getLogger(PagamentoController.class);

	private final PagamentoRepository pagamentoRepository;
	private final ConsultaRepository consultaRepository;

	@Autowired
	public PagamentoController(PagamentoRepository pagamentoRepository, ConsultaRepository consultaRepository) {
		this.pagamentoRepository = pagamentoRepository;
		this.consultaRepository = consultaRepository;
	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastroPagamento dados, UriComponentsBuilder uriBuilder) {

		var consulta = consultaRepository.getReferenceById(dados.idConsulta());

		LocalDateTime dataPagamento = dados.dataPagamento();
		if (dataPagamento == null) {
			dataPagamento = LocalDateTime.now();
		}

		var pagamento = new Pagamento(null, consulta, dataPagamento, dados.formaPagamento(),
				dados.valorPago(), dados.quemPagou(), dados.observacao());

		pagamentoRepository.save(pagamento);
		URI uri = uriBuilder.path("/pagamentos/{id}").buildAndExpand(pagamento.getId()).toUri();

		logger.info("O pagamento de codigo " + pagamento.getId() + " foi realizado e cadastrado com sucesso no sistema");

		return ResponseEntity.created(uri).body(new DadosDetalhamentoPagamento(pagamento));
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemPagamento>> listar(@PageableDefault(size = 10, sort = {"dataPagamento"}) Pageable paginacao) {
		var page = pagamentoRepository.findAll(paginacao).map(DadosListagemPagamento::new);

		logger.info("Listando informacoes dos pagamentos");
		return ResponseEntity.ok(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity detalhar(@PathVariable Long id) {
		var pagamento = pagamentoRepository.getReferenceById(id);

		logger.info("Obtendo informacoes do pagamento de codigo " + id);

		return ResponseEntity.ok(new DadosDetalhamentoPagamento(pagamento));
	}
}

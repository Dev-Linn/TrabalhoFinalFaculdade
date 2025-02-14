package com.example.trabalhoFinalVinicius.controller;

import com.example.trabalhoFinalVinicius.model.Movimentacao;
import com.example.trabalhoFinalVinicius.service.MovimentacaoService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;



    @RestController
    @RequestMapping("/movimentacoes")
    public class MovimentacaoController {
        private final MovimentacaoService service;

        public MovimentacaoController(MovimentacaoService service) {
            this.service = service;
        }

        @GetMapping
        public List<Movimentacao> listar() {
            return service.listarMovimentacoes();
        }

        @PostMapping
        public Movimentacao adicionar(@RequestBody Movimentacao movimentacao) {
            return service.salvar(movimentacao);
        }

        @GetMapping("/saldo/{ano}/{mes}")
        public String calcularSaldo(@PathVariable int ano, @PathVariable int mes) {
            BigDecimal saldo = service.calcularSaldoMensal(ano, mes);
            return saldo.compareTo(BigDecimal.ZERO) >= 0 ? "Lucro: R$ " + saldo : "Prejuízo: R$ " + saldo.abs();
        }

        @DeleteMapping("{id}")
        public String remover(@PathVariable Long id) {
            service.excluirPorId(id);
            return "Movimentacao removida com sucesso!";
        }
    }


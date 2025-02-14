package com.example.trabalhoFinalVinicius.service;

import com.example.trabalhoFinalVinicius.model.Movimentacao;
import com.example.trabalhoFinalVinicius.repositories.MovimentacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

    @Service
    public class MovimentacaoService {
        private final MovimentacaoRepository repository;

        public MovimentacaoService(MovimentacaoRepository repository) {
            this.repository = repository;
        }

        public List<Movimentacao> listarMovimentacoes() {
            return repository.findAll();
        }

        public Movimentacao salvar(Movimentacao movimentacao) {
            return repository.save(movimentacao);
        }

        public String excluirPorId(Long id) {
            repository.deleteById(id);
            return "Movimentacao removida com sucesso";
        }

        public BigDecimal calcularSaldoMensal(int ano, int mes) {
            LocalDate inicio = LocalDate.of(ano, mes, 1);
            LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());
            List<Movimentacao> movimentacoes = repository.findByDataBetween(inicio, fim);

            BigDecimal totalEntrada = movimentacoes.stream()
                    .filter(m -> "Entrada".equalsIgnoreCase(m.getTipo()))
                    .map(Movimentacao::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalSaida = movimentacoes.stream()
                    .filter(m -> "Saida".equalsIgnoreCase(m.getTipo()))
                    .map(Movimentacao::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return totalEntrada.subtract(totalSaida);
        }
    }

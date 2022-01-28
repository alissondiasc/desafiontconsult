package br.com.julgamento.repository;

import br.com.julgamento.model.entity.SessaoJulgamento;
import br.com.julgamento.model.entity.Usuario;
import br.com.julgamento.model.entity.VotoParticipacao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VotoParticipacaoRepository extends MongoRepository<VotoParticipacao, String> {
    Optional<VotoParticipacao> findByAssociadoAndSessaoJulgamento(Usuario associado, SessaoJulgamento sessaoJulgamento);

    List<VotoParticipacao> findBySessaoJulgamento(SessaoJulgamento sessaoJulgamento);
}

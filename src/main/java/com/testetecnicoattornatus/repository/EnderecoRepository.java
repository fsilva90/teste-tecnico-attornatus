package com.testetecnicoattornatus.repository;

import com.testetecnicoattornatus.domain.Endereco;
import com.testetecnicoattornatus.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findByPessoa(Pessoa pessoaExistente);

    @Query("select e from Endereco e where e.pessoa = ?1 and e.isPrincipal = ?2")
    List<Endereco> findByPessoaAndPrincipal(Pessoa pessoa, boolean isPrincipal);

    @Query("select e from Endereco e where e.pessoa.id = ?1 and e.isPrincipal = true")
    Optional<Endereco> findByEnderecoPrincipalPorPessoa(Long idPessoa);

    @Transactional
    @Modifying
    @Query("update Endereco e set e.isPrincipal = ?1 where e.id = ?2")
    void updateIsPrincipalById(boolean isPrincipal, Long id);

    @Query("select e from Endereco e join fetch e.pessoa where e.id = ?1")
    Optional<Endereco> findEnderecoById(Long id);
}
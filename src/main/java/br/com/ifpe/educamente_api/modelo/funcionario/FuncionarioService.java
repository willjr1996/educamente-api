package br.com.ifpe.educamente_api.modelo.funcionario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.educamente_api.modelo.acesso.Perfil;
import br.com.ifpe.educamente_api.modelo.acesso.PerfilRepository;
import br.com.ifpe.educamente_api.modelo.acesso.ContaService;
import jakarta.transaction.Transactional;

@Service
public class FuncionarioService  {
    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private ContaService contaService;

    @Autowired
    private PerfilRepository perfilUsuarioRepository;


    @Transactional
    public Funcionario save(Funcionario funcionario) {

        contaService.save(funcionario.getConta());

       for (Perfil perfil : funcionario.getConta().getRoles()) {
           perfil.setHabilitado(Boolean.TRUE);
           perfilUsuarioRepository.save(perfil);
       }


        funcionario.setHabilitado(Boolean.TRUE);
        return repository.save(funcionario);
    }

    public List<Funcionario> listarTodos() {
  
        return repository.findAll();
    }

    public Funcionario obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Funcionario funcionarioAlterado) {

        Funcionario funcionario = repository.findById(id).get();
        funcionario.setNome(funcionarioAlterado.getNome());
        funcionario.setCpf(funcionarioAlterado.getCpf());
        funcionario.setDataNascimento(funcionarioAlterado.getDataNascimento());
        funcionario.setFoneCelular(funcionarioAlterado.getFoneCelular());
        
        repository.save(funcionario);
    }

    @Transactional
    public void delete(Long id) {

        Funcionario funcionario = repository.findById(id).get();
        funcionario.setHabilitado(Boolean.FALSE);
        repository.save(funcionario);
    }
}

package br.com.verdemar.config;

import br.com.verdemar.model.Cadastrar;
import br.com.verdemar.model.Documentos;
import br.com.verdemar.model.Usuario;
import br.com.verdemar.repository.CadastrarRepository;
import br.com.verdemar.repository.DocumentosRepository;
import br.com.verdemar.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Profile("dev")
public class DataBaseSeeder implements CommandLineRunner {

        @Autowired
        private CadastrarRepository cadastrarRepository;

        @Autowired
        private DocumentosRepository documentosRepository;

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Override
        @Transactional
        public void run(String... args) throws Exception {
                // Criando e salvando o usuário
                Usuario usuario = Usuario.builder()
                        .nomeCompleto("João Silva")
                        .build();
                usuarioRepository.save(usuario);

                // Criando e salvando os documentos
                Documentos documentos = Documentos.builder()
                        .cpf("123.456.789-00")
                        .rg("MG-12.345.678")
                        .build();
                documentosRepository.save(documentos);

                // Carregando as entidades gerenciadas
                Usuario savedUsuario = usuarioRepository.findById(usuario.getId()).orElseThrow();
                Documentos savedDocumentos = documentosRepository.findById(documentos.getId()).orElseThrow();

                // Criando e salvando o cadastro
                Cadastrar cadastrar = Cadastrar.builder()
                        .nomeUsuario("joao123")
                        .senha("senha123")
                        .email("joao@example.com")
                        .usuario(savedUsuario)
                        .documentos(savedDocumentos)
                        .build();
                cadastrarRepository.save(cadastrar);

                System.out.println("Database seeding completed.");
        }
}



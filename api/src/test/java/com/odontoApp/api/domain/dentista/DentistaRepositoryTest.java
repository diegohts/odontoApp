package com.odontoApp.api.domain.dentista;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Por padrão, o Spring não usará o mesmo banco de dados
// da aplicação, mas um banco de dados in-memory, ou seja, um banco embutido (embedded), o H2.
//estamos indicando que queremos fazer uma configuração do banco de dados de teste e pedindo para não substituir as configurações do
//banco de dados pelas do banco em memória
@ActiveProfiles("test")
public class DentistaRepositoryTest {

	@Test
	void escolherDentistaAleatorioLivreNaData() {

	}

}

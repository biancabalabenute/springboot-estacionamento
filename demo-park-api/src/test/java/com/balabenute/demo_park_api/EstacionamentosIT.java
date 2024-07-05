package com.balabenute.demo_park_api;

import com.balabenute.demo_park_api.web.dto.EstacionamentoCreateDto;
import com.balabenute.demo_park_api.web.dto.PageableDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/estacionamentos/estacionamentos-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/estacionamentos/estacionamentos-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class EstacionamentosIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void criarCheckin_ComDadosValidos_RetornarCreatedAndLocation() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("WER-1111").marca("FIAT").modelo("PALIO 1.0")
                .cor("AZUL").clienteCpf("39166418089")
                .build();

        testClient.post()
                .uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION)
                .expectBody()
                .jsonPath("placa").isEqualTo("WER-1111")
                .jsonPath("marca").isEqualTo("FIAT")
                .jsonPath("modelo").isEqualTo("PALIO 1.0")
                .jsonPath("cor").isEqualTo("AZUL")
                .jsonPath("clienteCpf").isEqualTo("39166418089")
                .jsonPath("recibo").exists()
                .jsonPath("dataEntrada").exists()
                .jsonPath("vagaCodigo").exists();
    }

    @Test
    public void criarCheckin_ComRoleCliente_RetornarErrorStatus403() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("WER-1111").marca("FIAT").modelo("PALIO 1.0")
                .cor("AZUL").clienteCpf("39166418089")
                .build();

        testClient.post()
                .uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "jose@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Test
    public void criarCheckin_ComDadosInvalidos_RetornarErrorStatus422() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("").marca("").modelo("")
                .cor("").clienteCpf("")
                .build();

        testClient.post()
                .uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "jose@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo("422")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Test
    public void criarCheckin_ComCpfInexistente_RetornarErrorStatus404() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("WER-1111").marca("FIAT").modelo("PALIO 1.0")
                .cor("AZUL").clienteCpf("49642861003")
                .build();

        testClient.post()
                .uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Sql(scripts = "/sql/estacionamentos/estacionamento-insert-vagas-ocupadas.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/estacionamentos/estacionamento-delete-vagas-ocupadas.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void criarCheckin_ComVagasOcupadas_RetornarErrorStatus404() {
        EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                .placa("WER-1111").marca("FIAT").modelo("PALIO 1.0")
                .cor("AZUL").clienteCpf("39166418089")
                .build();

        testClient.post()
                .uri("/api/v1/estacionamentos/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                .jsonPath("method").isEqualTo("POST");
    }

    @Test
    public void buscaCheckin_ComPerfilAdmin_RetornarDadosStatus200() {
        testClient.get()
                .uri("/api/v1/estacionamentos/check-in/{recibo}", "20230315-101500")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("placa").isEqualTo("FIT-1020")
                .jsonPath("marca").isEqualTo("FIAT")
                .jsonPath("modelo").isEqualTo("PALIO")
                .jsonPath("cor").isEqualTo("VERDE")
                .jsonPath("clienteCpf").isEqualTo("39166418089")
                .jsonPath("recibo").isEqualTo("20230315-101500")
                .jsonPath("dataEntrada").isEqualTo("2023-03-14 10:15:00")
                .jsonPath("vagaCodigo").isEqualTo("A-03");
    }

    @Test
    public void buscaCheckin_ComPerfilCliente_RetornarDadosStatus200() {
        testClient.get()
                .uri("/api/v1/estacionamentos/check-in/{recibo}", "20230315-101500")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "jose@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("placa").isEqualTo("FIT-1020")
                .jsonPath("marca").isEqualTo("FIAT")
                .jsonPath("modelo").isEqualTo("PALIO")
                .jsonPath("cor").isEqualTo("VERDE")
                .jsonPath("clienteCpf").isEqualTo("39166418089")
                .jsonPath("recibo").isEqualTo("20230315-101500")
                .jsonPath("dataEntrada").isEqualTo("2023-03-14 10:15:00")
                .jsonPath("vagaCodigo").isEqualTo("A-03");
    }

    @Test
    public void buscaCheckin_ComReciboInexistente_RetornarErrorStatus404() {
        testClient.get()
                .uri("/api/v1/estacionamentos/check-in/{recibo}", "20233315-102200")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "jose@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in/20233315-102200")
                .jsonPath("method").isEqualTo("GET");
    }

    @Test
    public void criarCheckOut_ComReciboExistente_RetornarSucesso() {
        testClient.put()
                .uri("/api/v1/estacionamentos/check-out/{recibo}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("placa").isEqualTo("FIT-1020")
                .jsonPath("marca").isEqualTo("FIAT")
                .jsonPath("modelo").isEqualTo("PALIO")
                .jsonPath("cor").isEqualTo("VERDE")
                .jsonPath("dataEntrada").isEqualTo("2023-03-13 10:15:00")
                .jsonPath("clienteCpf").isEqualTo("39166418089")
                .jsonPath("vagaCodigo").isEqualTo("A-01")
                .jsonPath("recibo").isEqualTo("20230313-101300")
                .jsonPath("dataSaida").exists()
                .jsonPath("valor").exists()
                .jsonPath("desconto").exists();
    }

    @Test
    public void criarCheckOut_ComReciboInexistente_RetornarErrorStatus404() {
        testClient.put()
                .uri("/api/v1/estacionamentos/check-out/{recibo}", "20230313-000300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-out/20230313-000300")
                .jsonPath("method").isEqualTo("PUT");
    }

    @Test
    public void criarCheckOut_ComRoleCliente_RetornarErrorStatus403() {
        testClient.put()
                .uri("/api/v1/estacionamentos/check-out/{recibo}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "jose@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-out/20230313-101300")
                .jsonPath("method").isEqualTo("PUT");
    }

    @Test
    public void buscarEstacionamentos_PorClienteCpf_RetornarSucesso() {
        PageableDto responseBody = testClient.get()
                .uri("/api/v1/estacionamentos/cpf/{cpf}?size=1&page=0", "39166418089")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);

        responseBody = testClient.get()
                .uri("/api/v1/estacionamentos/cpf/{cpf}?size=1&page=1", "39166418089")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);
    }

    @Test
    public void buscarEstacionamentos_PorClienteCpfPerfilCliente_RetornarErrorStatus403() {
        testClient.get()
                .uri("/api/v1/estacionamentos/cpf/{cpf}", "39166418089")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "jose@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/cpf/39166418089")
                .jsonPath("method").isEqualTo("GET");
    }

    @Test
    public void buscarEstacionamentos_DoClienteLogado_RetornarSucesso() {
        PageableDto responseBody = testClient.get()
                .uri("/api/v1/estacionamentos?size=1&page=0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "jose@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);

        responseBody = testClient.get()
                .uri("/api/v1/estacionamentos?size=1&page=1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "jose@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);
    }

    @Test
    public void buscarEstacionamentos_PorDoClienteLogadoComPerfilAdmin_RetornarErrorStatus403() {
        testClient.get()
                .uri("/api/v1/estacionamentos", "39166418089")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos")
                .jsonPath("method").isEqualTo("GET");
    }
}

package com.baz.divisas.general.filters;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Pruebas unitarias para la clase LoggingFilter.
 * Valida el comportamiento del filtro de logging de peticiones HTTP.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LoggingFilterTest {

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private LoggingFilter loggingFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = mock(FilterChain.class);
    }

    @Test
    @DisplayName("doFilter - Petición POST con body JSON válido debe loggear correctamente")
    void testDoFilter_PostConBodyJson() throws IOException, ServletException {
        String jsonBody = "{\"id\":\"001\",\"nombre\":\"Test\",\"monto\":100}";
        request.setMethod("POST");
        request.setServletPath("/api/usuarios");
        request.setContent(jsonBody.getBytes());
        request.addHeader("x-id-usuario", "USR001");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Petición GET sin body debe loggear correctamente")
    void testDoFilter_GetSinBody() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/api/catalogos/divisas");
        request.addHeader("x-id-usuario", "USR002");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Petición PUT debe loggear correctamente")
    void testDoFilter_PutConBody() throws IOException, ServletException {
        String jsonBody = "{\"activo\":true,\"descripcion\":\"Region Norte\"}";
        request.setMethod("PUT");
        request.setServletPath("/api/tef/regiones");
        request.setContent(jsonBody.getBytes());
        request.addHeader("x-id-usuario", "USR003");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Petición DELETE debe loggear correctamente")
    void testDoFilter_Delete() throws IOException, ServletException {
        request.setMethod("DELETE");
        request.setServletPath("/api/tef/segmentos");
        request.addHeader("x-id-usuario", "USR004");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /api/llaves/generar no debe loggear")
    void testDoFilter_PathExcluidoLlavesGenerar() throws IOException, ServletException {
        request.setMethod("POST");
        request.setServletPath("/api/llaves/generar");
        request.addHeader("x-id-usuario", "USR005");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /api/llaves/encriptar no debe loggear")
    void testDoFilter_PathExcluidoLlavesEncriptar() throws IOException, ServletException {
        request.setMethod("POST");
        request.setServletPath("/api/llaves/encriptar");
        request.addHeader("x-id-usuario", "USR006");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /swagger-ui.html no debe loggear")
    void testDoFilter_PathExcluidoSwagger() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/swagger-ui.html");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /v2/api-docs no debe loggear")
    void testDoFilter_PathExcluidoApiDocs() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/v2/api-docs");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /api/tarjetaregalo/ no debe loggear")
    void testDoFilter_PathExcluidoTarjetaRegalo() throws IOException, ServletException {
        request.setMethod("POST");
        request.setServletPath("/api/tarjetaregalo/consulta");
        request.addHeader("x-id-usuario", "USR007");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /actualizacion-masiva no debe loggear")
    void testDoFilter_PathExcluidoActualizacionMasiva() throws IOException, ServletException {
        request.setMethod("POST");
        request.setServletPath("/api/core/clientes/actualizacion-masiva");
        request.addHeader("x-id-usuario", "USR008");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /scim/v2/token no debe loggear")
    void testDoFilter_PathExcluidoScimToken() throws IOException, ServletException {
        request.setMethod("POST");
        request.setServletPath("/scim/v2/token");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /api/core/reporteria/descarga-incidencias no debe loggear")
    void testDoFilter_PathExcluidoDescargaIncidencias() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/api/core/reporteria/descarga-incidencias");
        request.addHeader("x-id-usuario", "USR009");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /api/tef/spots no debe loggear")
    void testDoFilter_PathExcluidoSpots() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/api/tef/spots");
        request.addHeader("x-id-usuario", "USR010");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /api/core/reporteria/operaciones/descarga no debe loggear")
    void testDoFilter_PathExcluidoReporteriaOperacionesDescarga() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/api/core/reporteria/operaciones/descarga");
        request.addHeader("x-id-usuario", "USR011");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /api/digital/reporteria/operaciones/descarga no debe loggear")
    void testDoFilter_PathExcluidoDigitalReporteriaDescarga() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/api/digital/reporteria/operaciones/descarga");
        request.addHeader("x-id-usuario", "USR012");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /api/core/reporteria/ganadores-rascadito/descarga no debe loggear")
    void testDoFilter_PathExcluidoGanadoresRascaditoDescarga() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/api/core/reporteria/ganadores-rascadito/descarga");
        request.addHeader("x-id-usuario", "USR013");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /api/siex/configuraciones/campos-restringidos/descarga no debe loggear")
    void testDoFilter_PathExcluidoSiexDescarga() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/api/siex/configuraciones/campos-restringidos/descarga");
        request.addHeader("x-id-usuario", "USR014");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /api/core/configuraciones/notificaciones no debe loggear")
    void testDoFilter_PathExcluidoNotificaciones() throws IOException, ServletException {
        request.setMethod("POST");
        request.setServletPath("/api/core/configuraciones/notificaciones");
        request.addHeader("x-id-usuario", "USR015");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /api/core/reporteria/incidencias/descarga no debe loggear")
    void testDoFilter_PathExcluidoIncidenciasDescarga() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/api/core/reporteria/incidencias/descarga");
        request.addHeader("x-id-usuario", "USR016");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Body JSON inválido no debe lanzar excepción")
    void testDoFilter_BodyJsonInvalido() throws IOException, ServletException {
        String invalidJson = "{invalid json body";
        request.setMethod("POST");
        request.setServletPath("/api/usuarios");
        request.setContent(invalidJson.getBytes());
        request.addHeader("x-id-usuario", "USR017");

        assertDoesNotThrow(() -> loggingFilter.doFilter(request, response, filterChain));
        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Body vacío debe funcionar correctamente")
    void testDoFilter_BodyVacio() throws IOException, ServletException {
        request.setMethod("POST");
        request.setServletPath("/api/tef/regiones");
        request.setContent("".getBytes());
        request.addHeader("x-id-usuario", "USR018");

        assertDoesNotThrow(() -> loggingFilter.doFilter(request, response, filterChain));
        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Sin header x-id-usuario debe funcionar correctamente")
    void testDoFilter_SinHeaderUsuario() throws IOException, ServletException {
        String jsonBody = "{\"dato\":\"valor\"}";
        request.setMethod("POST");
        request.setServletPath("/api/catalogos");
        request.setContent(jsonBody.getBytes());

        assertDoesNotThrow(() -> loggingFilter.doFilter(request, response, filterChain));
        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Response con status 404 debe loggear correctamente")
    void testDoFilter_ResponseStatus404() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/api/usuarios/inexistente");
        request.addHeader("x-id-usuario", "USR019");

        doAnswer(invocation -> {
            MockHttpServletResponse resp = (MockHttpServletResponse) 
                ((org.springframework.web.util.ContentCachingResponseWrapper) invocation.getArgument(1))
                .getResponse();
            return null;
        }).when(filterChain).doFilter(any(), any());

        assertDoesNotThrow(() -> loggingFilter.doFilter(request, response, filterChain));
    }

    @Test
    @DisplayName("doFilter - Response con status 500 debe loggear correctamente")
    void testDoFilter_ResponseStatus500() throws IOException, ServletException {
        request.setMethod("POST");
        request.setServletPath("/api/tef/limites");
        request.setContent("{\"region\":1}".getBytes());
        request.addHeader("x-id-usuario", "USR020");

        assertDoesNotThrow(() -> loggingFilter.doFilter(request, response, filterChain));
    }

    @Test
    @DisplayName("doFilter - RuntimeException en chain no propaga la excepción")
    void testDoFilter_RuntimeExceptionEnChain() throws IOException, ServletException {
        request.setMethod("POST");
        request.setServletPath("/api/tef/regiones");
        request.setContent("{\"test\":true}".getBytes());
        request.addHeader("x-id-usuario", "USR021");

        doThrow(new RuntimeException("Error simulado en chain"))
            .when(filterChain).doFilter(any(), any());

        assertDoesNotThrow(() -> loggingFilter.doFilter(request, response, filterChain));
    }

    @Test
    @DisplayName("doFilter - Petición con atributo tiempoInicial calcula duración")
    void testDoFilter_ConTiempoInicial() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/api/core/clientes");
        request.addHeader("x-id-usuario", "USR022");
        request.setAttribute("tiempoInicial", System.currentTimeMillis() - 150L);

        assertDoesNotThrow(() -> loggingFilter.doFilter(request, response, filterChain));
        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Petición con body JSON complejo (anidado)")
    void testDoFilter_BodyJsonComplejo() throws IOException, ServletException {
        String jsonBody = "{\"region\":{\"id\":1,\"descripcion\":\"Norte\"},\"divisas\":[\"USD\",\"EUR\"],\"activo\":true}";
        request.setMethod("POST");
        request.setServletPath("/api/tef/regiones");
        request.setContent(jsonBody.getBytes());
        request.addHeader("x-id-usuario", "USR023");

        assertDoesNotThrow(() -> loggingFilter.doFilter(request, response, filterChain));
        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Petición PATCH debe loggear correctamente")
    void testDoFilter_PatchConBody() throws IOException, ServletException {
        String jsonBody = "{\"activo\":false}";
        request.setMethod("PATCH");
        request.setServletPath("/api/core/configuraciones");
        request.setContent(jsonBody.getBytes());
        request.addHeader("x-id-usuario", "USR024");

        assertDoesNotThrow(() -> loggingFilter.doFilter(request, response, filterChain));
        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /webjars/ no debe loggear")
    void testDoFilter_PathExcluidoWebjars() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/webjars/springfox-swagger-ui/swagger-ui.css");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /configuration/ no debe loggear")
    void testDoFilter_PathExcluidoConfiguration() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/configuration/ui");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /swagger-resources/ no debe loggear")
    void testDoFilter_PathExcluidoSwaggerResources() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/swagger-resources/configuration/ui");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /public no debe loggear")
    void testDoFilter_PathExcluidoPublic() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/public/health");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /index.jsp no debe loggear")
    void testDoFilter_PathExcluidoIndexJsp() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/index.jsp");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("Constructor por defecto no lanza excepción")
    void testConstructor() {
        assertDoesNotThrow(() -> new LoggingFilter());
    }

    @Test
    @DisplayName("doFilter - Petición con IP remota registra dirección correctamente")
    void testDoFilter_ConIpRemota() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/api/core/beneficios");
        request.setRemoteAddr("192.168.1.100");
        request.addHeader("x-id-usuario", "USR025");

        assertDoesNotThrow(() -> loggingFilter.doFilter(request, response, filterChain));
        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Petición con body que contiene caracteres especiales")
    void testDoFilter_BodyConCaracteresEspeciales() throws IOException, ServletException {
        String jsonBody = "{\"nombre\":\"José García\",\"dirección\":\"Calle Ñoño #123\"}";
        request.setMethod("POST");
        request.setServletPath("/api/core/clientes");
        request.setContent(jsonBody.getBytes("UTF-8"));
        request.addHeader("x-id-usuario", "USR026");

        assertDoesNotThrow(() -> loggingFilter.doFilter(request, response, filterChain));
        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - IOException en chain captura correctamente la excepción")
    void testDoFilter_IOExceptionEnChain() throws IOException, ServletException {
        request.setMethod("POST");
        request.setServletPath("/api/tef/spots/registra");
        request.setContent("{\"divisa\":\"USD\"}".getBytes());
        request.addHeader("x-id-usuario", "USR027");

        doThrow(new RuntimeException(new IOException("Error de IO simulado")))
            .when(filterChain).doFilter(any(), any());

        assertDoesNotThrow(() -> loggingFilter.doFilter(request, response, filterChain));
    }

    @Test
    @DisplayName("doFilter - Path de TEF sucursales (no excluido) debe loggear")
    void testDoFilter_PathTefSucursalesNoExcluido() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/api/tef/sucursales/tc");
        request.addHeader("x-id-usuario", "USR028");

        assertDoesNotThrow(() -> loggingFilter.doFilter(request, response, filterChain));
        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Path excluido /api/tef/tablawscecostos/sucursales no debe loggear")
    void testDoFilter_PathExcluidoTablaCeCostos() throws IOException, ServletException {
        request.setMethod("GET");
        request.setServletPath("/api/tef/tablawscecostos/sucursales");
        request.addHeader("x-id-usuario", "USR029");

        loggingFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    @DisplayName("doFilter - Body con array JSON debe parsear correctamente")
    void testDoFilter_BodyConArrayJson() throws IOException, ServletException {
        String jsonBody = "[{\"id\":1,\"divisa\":\"USD\"},{\"id\":2,\"divisa\":\"EUR\"}]";
        request.setMethod("POST");
        request.setServletPath("/api/tef/limites/region");
        request.setContent(jsonBody.getBytes());
        request.addHeader("x-id-usuario", "USR030");

        assertDoesNotThrow(() -> loggingFilter.doFilter(request, response, filterChain));
        verify(filterChain, times(1)).doFilter(any(), any());
    }
}

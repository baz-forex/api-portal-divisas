package com.baz.divisas.general.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PropAppTest {

	@Test
	public void valid() {
		PropApp.getAlgoritmoAES();
		PropApp.getAlgoritmoRSA();
		PropApp.getConsumerKeyApi();
		PropApp.getConsumerSecretApi();
		PropApp.getCorreoGerente();
		PropApp.getDescripcionIdentificacion("INE");
		PropApp.getDestinatariosNotificaciones();
		PropApp.getGrantTypeApi();
		PropApp.getHostMail();
		PropApp.getHostMailCertificacion();
		PropApp.getIpMail();
		PropApp.getIpMailCertificacion();
		PropApp.getPrivateKeyPath();
		PropApp.getRemitenteMail();
		PropApp.getRemitenteMailCertificacion();
		PropApp.getTokenJwtExpiracion();
		PropApp.getUrlTokenApi();
		PropApp.getUrlTokenApiAWSINT();
		PropApp.getUrlRegistraTiposCambioMM();
		PropApp.getConsumerKeyApiAWS();
		PropApp.getConsumerSecretApiAWS();
		PropApp.getSucursalMM();
		PropApp.getUrlApiAWSINT();
		PropApp.getUrlApigeeOnPrem();
		PropApp.getUrlApiSeguridadDivisas();
		PropApp.getUrlRegistraTiposCambioDivisas();
		PropApp.getConsumerKeyApiDivisas();
		PropApp.getConsumerSecretApiDivisas();
		PropApp.getUrlConsultaParametros();
		PropApp.getUrlGestionParametros();
		PropApp.getUrlGestionParametrosMasivos();
		PropApp.getUrlConsultaAsignacionesParametros();
		PropApp.getUrlGestionAsignaciones();
		PropApp.getUrlGestionAsignacionesMasivas();
		PropApp.getConsumerKeyParametros();
		PropApp.getConsumerSecretParametros();
		PropApp.getUrlTipoCambioTesoreria();
		PropApp.getClaveNegocio();
		PropApp.getReporteriaOperacionAlnova();
		PropApp.getReporteriaRegulatoriosAlnova();
		PropApp.getReporteSpotOperacionAlnova();
		PropApp.getReporteSpotRegulatoriosAlnova();
	}
}

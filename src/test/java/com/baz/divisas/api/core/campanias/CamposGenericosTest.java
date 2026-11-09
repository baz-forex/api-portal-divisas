package com.baz.divisas.api.core.campanias;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baz.divisas.api.core.beneficios.model.BeanCampania;
import com.baz.divisas.api.core.beneficios.model.BeanPremio;
import com.baz.divisas.api.core.beneficios.util.CamposGenericos;

@ExtendWith(MockitoExtension.class)
public class CamposGenericosTest {

	@Test
	void testPerfilCliente() {
		BeanCampania bc = new BeanCampania();
		bc.setPremios(null);
		CamposGenericos.perfilCliente(bc);
		bc.setPremios(new ArrayList<>());
		CamposGenericos.perfilCliente(bc);
		ArrayList<BeanPremio> ls = new ArrayList<>();
		ls.add(new BeanPremio());
		BeanPremio p = new BeanPremio();
		p.setPerfilCliente(new ArrayList<>());
		ls.add(p);
		bc.setPremios(ls);
		CamposGenericos.perfilCliente(bc);
		ArrayList<String> lss = new ArrayList<>();
		lss.add("EMPLEADO");
		p.setPerfilCliente(lss);
		ls.add(p);
		bc.setPremios(ls);
		lss.add("NINGUNO");
		p.setPerfilCliente(lss);
		ls.add(p);
		bc.setPremios(ls);
		CamposGenericos.perfilCliente(bc);
		assertThat(bc).isNotNull();
	}
}

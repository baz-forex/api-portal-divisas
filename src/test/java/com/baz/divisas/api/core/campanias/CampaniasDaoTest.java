package com.baz.divisas.api.core.campanias;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baz.divisas.api.core.beneficios.dao.CampaniasDao;
import com.baz.divisas.api.core.beneficios.dto.ReqAltaPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqEliminaPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqEstatusCampania;
import com.baz.divisas.api.core.beneficios.dto.ReqEstatusPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqTipoCampania;
import com.baz.divisas.api.core.beneficios.model.BeanCampania;
import com.baz.divisas.api.core.beneficios.model.BeanPremio;
import com.baz.divisas.general.model.EntCorreo;
import com.baz.divisas.general.util.MailService;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
public class CampaniasDaoTest {
	@InjectMocks
	private CampaniasDao dao;
	@Mock
	private MongoCollection<BeanCampania> col;
	@Mock
	private MailService mail;
	@Mock
	private FindIterable<BeanCampania> iterable;
	@Mock
	private UpdateResult upRes;
	@Test
	void testInsert() {
		when(col.insertOne(isA(BeanCampania.class))).thenReturn(null);
		BeanCampania b = new BeanCampania();
		dao.insert(b);
		verify(col,times(1)).insertOne(b);
	}
	@Test
	void testConsultar() {
		when(col.find()).thenReturn(iterable);
		when(iterable.into(any())).thenReturn(new ArrayList<BeanCampania>());
		List<BeanCampania> res = dao.consultar();
		assertThat(res).isNotNull();
	}
	@Test
	void testConsultaUnica() {
		when(col.find(isA(Bson.class))).thenReturn(iterable);
		when(iterable.first()).thenReturn(new BeanCampania());
		BeanCampania res = dao.consultaUnica("6438547dcf82e103a0287bc9");
		assertThat(res).isNotNull();
	}
	@Test
	void testActualizar() {
		when(col.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(null);
		BeanCampania b = new BeanCampania();
		b.setId("6438547dcf82e103a0287bc9");
		dao.actualizar(b);
		verify(col,times(1)).updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class));
	}
	@Test
	void testEliminarCampania() {
		when(col.deleteOne(isA(Bson.class))).thenReturn(null);
		dao.eliminarCampania("6438547dcf82e103a0287bc9");
		verify(col,times(1)).deleteOne(isA(Bson.class));
	}
	@Test
	void testActualizarTipo() {
		when(col.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(null);
		ReqTipoCampania req = new ReqTipoCampania();
		req.setIdCampania("6438547dcf82e103a0287bc9");
		dao.actualizarTipo(req);
		verify(col,times(1)).updateOne(isA(Bson.class), isA(Bson.class));
	}
	@Test
	void testActualizarEstatus() {
		when(col.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(null);
		ReqEstatusCampania req = new ReqEstatusCampania();
		req.setIdCampania("6438547dcf82e103a0287bc9");
		dao.actualizarEstatus(req);
		verify(col,times(1)).updateOne(isA(Bson.class), isA(Bson.class));
	}
	@Test
	void testInsertPremio() {
		when(col.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(null);
		dao.insertPremio("6438547dcf82e103a0287bc9", new BeanPremio());
		verify(col,times(1)).updateOne(isA(Bson.class), isA(Bson.class));
	}
	@Test
	void testActualizarPremio() {
		when(col.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(null);
		ReqAltaPremio req = new ReqAltaPremio();
		req.setIdCampania("6438547dcf82e103a0287bc9");
		BeanPremio p = new BeanPremio();
		p.setId("6438547dcf82e103a0287bc9");
		req.setPremio(p);
		dao.actualizarPremio(req);
		verify(col,times(1)).updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class));
	}
	@Test
	void testEliminarPremio() {
		when(col.updateOne(isA(Bson.class), isA(Bson.class))).thenReturn(null);
		ReqEliminaPremio req = new ReqEliminaPremio();
		req.setIdCampania("6438547dcf82e103a0287bc9");
		req.setIdPremio("6438547dcf82e103a0287bc9");
		dao.eliminarPremio(req);
		verify(col,times(1)).updateOne(isA(Bson.class), isA(Bson.class));
	}
	@Test
	void testActualizarEstatusPremio() {
		when(col.updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class))).thenReturn(null);
		ReqEstatusPremio req = new ReqEstatusPremio();
		req.setIdCampania("6438547dcf82e103a0287bc9");
		req.setIdPremio("6438547dcf82e103a0287bc9");
		dao.actualizarEstatusPremio(req);
		verify(col,times(1)).updateOne(isA(Bson.class), isA(Bson.class), isA(UpdateOptions.class));
	}
	@Test
	void testActualizaCampaniasFueraVigencia() {
		when(col.updateMany(isA(Bson.class), isA(Bson.class))).thenReturn(upRes);
		when(upRes.getMatchedCount()).thenReturn(0L);
		when(upRes.getModifiedCount()).thenReturn(0L);
		doNothing().when(mail).enviaMail(isA(EntCorreo.class));
		dao.actualizaCampaniasFueraVigencia();
		when(upRes.getMatchedCount()).thenReturn(1L);
		dao.actualizaCampaniasFueraVigencia();
		when(upRes.getModifiedCount()).thenReturn(1L);
		dao.actualizaCampaniasFueraVigencia();
		verify(col,times(3)).updateMany(isA(Bson.class), isA(Bson.class));
		dao = new CampaniasDao();
	}
}

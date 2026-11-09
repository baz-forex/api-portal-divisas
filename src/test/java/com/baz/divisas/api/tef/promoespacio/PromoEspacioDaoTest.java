package com.baz.divisas.api.tef.promoespacio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.tef.general.model.SucursalTC;
import com.baz.divisas.api.tef.promoespacio.dao.PromoEspacioDao;
import com.baz.divisas.api.tef.promoespacio.model.DivisasBean;
import com.baz.divisas.api.tef.promoespacio.model.TcSpotBean;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PromoEspacioDaoTest {

	@InjectMocks
	private PromoEspacioDao dao;
	@Mock
	private MongoCollection<DivisasBean> colSucursales;
	@Mock
	private MongoCollection<TcSpotBean> colCatSpot;
	@Mock
	private MongoCollection<SucursalTC> coleccionSucursalesTC;
	@Mock
	private AggregateIterable<DivisasBean> aggregate;
	
	@Mock
	private AggregateIterable<TcSpotBean> aggregateCatSpot;
	
	@Mock
	private AggregateIterable<SucursalTC> aggregateSucursal;
	
	@Test
	void testObtenerTiposCambioSucursales() {
		dao = new PromoEspacioDao(colSucursales, colCatSpot,coleccionSucursalesTC);
		when(colSucursales.aggregate(anyList())).thenReturn(aggregate);
		when(aggregate.into(anyList())).thenReturn(null);
		
		List<String> ls = new ArrayList<>();
		ls.add("0172");
		List<DivisasBean> ltc = dao.obtenerTiposCambioSucursales(ls); 
		assertThat(ltc).isNull();
	}
	
	@Test
	void testObtenerTiposCambioSpot() {
		dao = new PromoEspacioDao(colSucursales, colCatSpot,coleccionSucursalesTC);
		when(colCatSpot.aggregate(anyList())).thenReturn(aggregateCatSpot);
		when(aggregateCatSpot.into(anyList())).thenReturn(null);
		
		List<TcSpotBean> ltc = dao.obtenerTiposCambioSpot(); 
		assertThat(ltc).isNull();
	}
	
//	@Test
//	void testObtenerTiposCambioSucursalesOlp() {
//		dao = new PromoEspacioDao(colSucursales, colCatSpot,coleccionSucursalesTC);
//		when(colSucursales.aggregate(anyList())).thenReturn(aggregate);
//		when(aggregate.into(anyList())).thenReturn(null);
//		
//		List<String> ls = new ArrayList<>();
//		ls.add("0172");
//		List<DivisasBean> ltc = dao.obtenerTiposCambioSucursalesOLP(ls); 
//		assertThat(ltc).isNull();
//	}
	
	@Test
	void testConsultaSucursalesTC() {
		dao = new PromoEspacioDao(colSucursales, colCatSpot,coleccionSucursalesTC);
		when(coleccionSucursalesTC.aggregate(anyList())).thenReturn(aggregateSucursal);
		when(aggregateSucursal.first()).thenReturn(null);
		
		SucursalTC sucu = dao.consultaSucursalesTC("1827", "USD"); 
		assertThat(sucu).isNull();
	}
}

package com.baz.divisas.api.core.reporteria.dao;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.baz.divisas.api.core.beneficios.model.CuponMasSalud;
import com.baz.divisas.api.core.reporteria.model.GanadorCostalSanta;
import com.baz.divisas.api.core.reporteria.model.GanadorGolDeOro;
import com.baz.divisas.api.core.reporteria.model.GanadorPromoDigital;
import com.baz.divisas.api.core.reporteria.model.GanadorVeranoMundialista;
import com.baz.divisas.api.core.reporteria.model.InventarioCostalSanta;
import com.baz.divisas.api.core.reporteria.model.InventarioMundialVerde;
import com.baz.divisas.api.core.reporteria.model.InventarioVeranoMundialista;
import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BeneficiosDaoTest {
	
	@InjectMocks
	private BeneficiosDao beneficiosDao;
	@Mock 
	private MongoCollection<Document> mockCollection;
	@Mock
	private FindIterable<Document> mockFindIterable;
	@Mock
	private AggregateIterable<Document> mockAggIterable;
	
	private MongoCollection<CuponMasSalud> collectionCuponesMasSalud;
	@Mock
	private FindIterable<CuponMasSalud> mockFindIterableMasSalud;
	
	private MongoCollection<GanadorCostalSanta> collectionGanadoresCostalSanta;
	@Mock
	private FindIterable<GanadorCostalSanta> mockFindIterableGanadorCostalSanta;
	
	private MongoCollection<InventarioCostalSanta> collectionInventarioCostalSanta;
	@Mock
	private FindIterable<InventarioCostalSanta> mockFindIterableInventarioCostalSanta;

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() throws Exception {
		collectionCuponesMasSalud = org.mockito.Mockito.mock(MongoCollection.class);
		Field field = BeneficiosDao.class.getDeclaredField("collectionCuponesMasSalud");
		field.setAccessible(true);
		field.set(beneficiosDao, collectionCuponesMasSalud);
		
		collectionGanadoresCostalSanta = org.mockito.Mockito.mock(MongoCollection.class);
		Field fieldGanadorCostal = BeneficiosDao.class.getDeclaredField("collectionGanadoresCostalSanta");
		fieldGanadorCostal.setAccessible(true);
		fieldGanadorCostal.set(beneficiosDao, collectionGanadoresCostalSanta);
		
		collectionInventarioCostalSanta = org.mockito.Mockito.mock(MongoCollection.class);
		Field fieldInventarioCostal = BeneficiosDao.class.getDeclaredField("collectionInventarioCostalSanta");
		fieldInventarioCostal.setAccessible(true);
		fieldInventarioCostal.set(beneficiosDao, collectionInventarioCostalSanta);
	}

	@Test
	public void testAcumulado() {

        when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.allowDiskUse(true)).thenReturn(mockFindIterable);
        when(mockFindIterable.into(any())).thenReturn(Arrays.asList());
                
        List<GanadorPromoDigital> resultado = beneficiosDao.consultaGanadoresPromoDigital(new Document());       
        assertThat(resultado).isNotNull();
        
        ///////////
        when(mockCollection.find(isA(Bson.class))).thenThrow(new MongoException("Test Exception"));
        resultado = beneficiosDao.consultaGanadoresPromoDigital(new Document());       
        assertThat(resultado).isNotNull();
	}
	
	@Test
	public void testconsultaGanadoresGolDeOro() {

        when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.allowDiskUse(true)).thenReturn(mockFindIterable);
        when(mockFindIterable.into(any())).thenReturn(Arrays.asList());
                
        List<GanadorGolDeOro> resultado = beneficiosDao.consultaGanadoresGolDeOro(new Document());       
        assertThat(resultado).isNotNull();
        
        ///////////
        when(mockCollection.find(isA(Bson.class))).thenThrow(new MongoException("Test Exception"));
        resultado = beneficiosDao.consultaGanadoresGolDeOro(new Document());       
        assertThat(resultado).isNotNull();
	}
	
	
	@Test
	public void testconsultaInventarioMundialVerde() {
	
		when(mockCollection.find()).thenReturn(mockFindIterable);
        when(mockFindIterable.allowDiskUse(true)).thenReturn(mockFindIterable);
        when(mockFindIterable.into(any())).thenReturn(Arrays.asList());
                
        List<InventarioMundialVerde> resultado = beneficiosDao.consultaInventarioMundialVerde();  
        assertThat(resultado).isNotNull();
        
        ///////////
        when(mockCollection.find()).thenThrow(new MongoException("Test Exception"));
        resultado = beneficiosDao.consultaInventarioMundialVerde();       
        assertThat(resultado).isNotNull();
	
	}
	
	@Test
	public void testconsultaEmpleadosBloqueados() {
	   
        when(mockCollection.aggregate(any())).thenReturn(mockAggIterable);
        when(mockAggIterable.allowDiskUse(isA(Boolean.class))).thenReturn(mockAggIterable);
        when(mockAggIterable.first()).thenReturn(new Document());

        
        List<String> resultado = beneficiosDao.consultaEmpleadosBloqueados();                
        assertThat(resultado).isNotNull();  
        
        when(mockCollection.aggregate(any())).thenThrow(new MongoException("Test Exception"));
        resultado = beneficiosDao.consultaEmpleadosBloqueados();                
        assertThat(resultado).isNotNull();
        
        beneficiosDao= new BeneficiosDao();
	}
	

	@Test
	public void testconsultaGanadoresVeranoMundialista() {

        when(mockCollection.find(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(isA(Bson.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.allowDiskUse(true)).thenReturn(mockFindIterable);
        when(mockFindIterable.into(any())).thenReturn(Arrays.asList());
                
        List<GanadorVeranoMundialista> resultado = beneficiosDao.consultaGanadoresVeranoMundialista(new Document());       
        assertThat(resultado).isNotNull();
        
        ///////////
        when(mockCollection.find(isA(Bson.class))).thenThrow(new MongoException("Test Exception"));
        resultado = beneficiosDao.consultaGanadoresVeranoMundialista(new Document());       
        assertThat(resultado).isNotNull();
	}
	
	
	@Test
	public void testconsultaInventarioVeranoMundialista() {
	
		when(mockCollection.find()).thenReturn(mockFindIterable);
        when(mockFindIterable.allowDiskUse(true)).thenReturn(mockFindIterable);
        when(mockFindIterable.into(any())).thenReturn(Arrays.asList());
                
        List<InventarioVeranoMundialista> resultado = beneficiosDao.consultaInventarioVeranoMundialista();  
        assertThat(resultado).isNotNull();
        
        ///////////
        when(mockCollection.find()).thenThrow(new MongoException("Test Exception"));
        resultado = beneficiosDao.consultaInventarioVeranoMundialista();       
        assertThat(resultado).isNotNull();
	
	}
	
	@Test
	public void testConsultaCuponesMasSalud() {

        when(collectionCuponesMasSalud.find(isA(Bson.class))).thenReturn(mockFindIterableMasSalud);
        when(mockFindIterableMasSalud.sort(isA(Bson.class))).thenReturn(mockFindIterableMasSalud);
        when(mockFindIterableMasSalud.allowDiskUse(true)).thenReturn(mockFindIterableMasSalud);
        when(mockFindIterableMasSalud.into(any())).thenReturn(Arrays.asList());
                
        List<CuponMasSalud> resultado = beneficiosDao.consultaCuponesMasSalud();       
        assertThat(resultado).isNotNull();
        
        ///////////
        when(collectionCuponesMasSalud.find(isA(Bson.class))).thenThrow(new MongoException("Test Exception"));
        resultado = beneficiosDao.consultaCuponesMasSalud();       
        assertThat(resultado).isNotNull();
	}
	
	@Test
	public void testConsultaGanadoresCostalSanta() {

        when(collectionGanadoresCostalSanta.find(isA(Bson.class))).thenReturn(mockFindIterableGanadorCostalSanta);
        when(mockFindIterableGanadorCostalSanta.sort(isA(Bson.class))).thenReturn(mockFindIterableGanadorCostalSanta);
        when(mockFindIterableGanadorCostalSanta.allowDiskUse(true)).thenReturn(mockFindIterableGanadorCostalSanta);
        when(mockFindIterableGanadorCostalSanta.into(any())).thenReturn(Arrays.asList());
                
        List<GanadorCostalSanta> resultado = beneficiosDao.consultaGanadoresCostalSanta(new Document());       
        assertThat(resultado).isNotNull();
        
        ///////////
        when(collectionGanadoresCostalSanta.find(isA(Bson.class))).thenThrow(new MongoException("Test Exception"));
        resultado = beneficiosDao.consultaGanadoresCostalSanta(new Document());       
        assertThat(resultado).isNotNull();
	}
	
	@Test
	public void testConsultaInventarioCostalSanta() {

        when(collectionInventarioCostalSanta.find()).thenReturn(mockFindIterableInventarioCostalSanta);
        when(mockFindIterableInventarioCostalSanta.allowDiskUse(true)).thenReturn(mockFindIterableInventarioCostalSanta);
        when(mockFindIterableInventarioCostalSanta.into(any())).thenReturn(Arrays.asList());
                
        List<InventarioCostalSanta> resultado = beneficiosDao.consultaInventarioCostalSanta();       
        assertThat(resultado).isNotNull();
        
        ///////////
        when(collectionInventarioCostalSanta.find()).thenThrow(new MongoException("Test Exception"));
        resultado = beneficiosDao.consultaInventarioCostalSanta();       
        assertThat(resultado).isNotNull();
	}
}

package com.swkoan.gallows.gt.data;

import com.swkoan.gallows.config.DataSourceConfig;
import com.swkoan.gallows.config.GallowsException;
import com.swkoan.gallows.config.LayerConfig;
import com.swkoan.gallows.data.DataSourceFactory;
import com.vividsolutions.jts.geom.Point;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.geotools.data.DataStore;
import org.geotools.data.FeatureSource;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.Layer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeType;
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.feature.type.GeometryType;
import org.opengis.feature.type.Name;
import org.opengis.filter.Filter;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.util.InternationalString;

/**
 *
 */
public class GTLayerFactoryTest {

    public GTLayerFactoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDataStoreGood() {
        GTLayerFactory gtlf = new GTLayerFactory();
        DataSourceFactory dsfMock = mock(DataSourceFactory.class);
        DataSourceConfig dsCfgMock = new MockDataSourceConfig();
        when(dsfMock.createDataSource(dsCfgMock)).thenReturn(mock(DataStore.class));
        when(dsfMock.getDataSourceConfigClassname()).
                thenReturn("com.swkoan.gallows.gt.data.MockDataSourceConfig");
        gtlf.registerDataSourceFactory(dsfMock);
        DataStore ds = gtlf.createDataSource(dsCfgMock);
        assertNotNull(ds);
    }

    @Test
    public void testDataStoreBad() {
        GTLayerFactory gtlf = new GTLayerFactory();
        DataSourceFactory dsfMock = mock(DataSourceFactory.class);
        DataSourceConfig dsCfgMock = new MockDataSourceConfig();
        when(dsfMock.createDataSource(dsCfgMock)).thenReturn(mock(DataStore.class));
        when(dsfMock.getDataSourceConfigClassname()).
                thenReturn("NonexistentClassName");
        gtlf.registerDataSourceFactory(dsfMock);
        DataStore ds = gtlf.createDataSource(dsCfgMock);
        assertNull(ds);
    }

    @Test
    public void testCreateLayerGood() {
        GTLayerFactory gtlf = new GTLayerFactory();
        DataSourceFactory dsfMock = mock(DataSourceFactory.class);
        DataSourceConfig dsCfgMock = new MockDataSourceConfig();
        LayerConfig lCfgMock = mock(LayerConfig.class);
        DataStore gtDsMock = mock(DataStore.class);
        SimpleFeatureSource fsMock = mock(SimpleFeatureSource.class);
        SimpleFeatureType schemaMock = mock(SimpleFeatureType.class);
        GeometryDescriptor geomMock = mock(GeometryDescriptor.class);
        GeometryType gTypeMock = new MockGeometryType();

        when(lCfgMock.getName()).thenReturn("layerName");
        try {
            when(gtDsMock.getFeatureSource("layerName")).thenReturn(fsMock);
        } catch (IOException e) {
            fail("Unexpected / impossible exception");
        }
        when(fsMock.getSchema()).thenReturn(schemaMock);
        when(lCfgMock.getDataSourceConfig()).thenReturn(dsCfgMock);
        when(dsfMock.createDataSource(dsCfgMock)).thenReturn(gtDsMock);
        when(dsfMock.getDataSourceConfigClassname()).
                thenReturn("com.swkoan.gallows.gt.data.MockDataSourceConfig");
        when(schemaMock.getGeometryDescriptor()).thenReturn(geomMock);
        when(geomMock.getType()).thenReturn(gTypeMock);

        gtlf.registerDataSourceFactory(dsfMock);
        Layer layer = gtlf.createLayer(lCfgMock);
        assertNotNull(layer);
    }

    @Test
    public void testCreateLayerNull() {
        DataSourceFactory dsfMock = mock(DataSourceFactory.class);
        try {
            GTLayerFactory gtlf = new GTLayerFactory();
            gtlf.registerDataSourceFactory(dsfMock);
            gtlf.createLayer(null);
            fail("Expected a NPE.");
        } catch (NullPointerException e) {
            // expected.
        }
    }

    @Test
    public void testCreateLayerNoSuitableDSFactory() {
        DataSourceFactory dsfMock = mock(DataSourceFactory.class);
        DataSourceConfig dsCfgMock = new MockDataSourceConfig();
        LayerConfig lCfgMock = mock(LayerConfig.class);
        
        when(dsfMock.getDataSourceConfigClassname()).
                thenReturn("NonexistentClassName");
        when(lCfgMock.getDataSourceConfig()).thenReturn(dsCfgMock);
        
        GTLayerFactory gtlf = new GTLayerFactory();
        gtlf.registerDataSourceFactory(dsfMock);
        try {
            Layer layer = gtlf.createLayer(lCfgMock);
            fail("Expected exception no thrown.");
        }
        catch(GallowsException e) {
            // Expected.
        }
    }
}

class MockDataSourceConfig implements DataSourceConfig {
}

class MockGeometryType implements GeometryType {

    @Override
    public CoordinateReferenceSystem getCoordinateReferenceSystem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIdentified() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AttributeType getSuper() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Name getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<?> getBinding() {
        return Point.class;
    }

    @Override
    public boolean isAbstract() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Filter> getRestrictions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InternationalString getDescription() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Object, Object> getUserData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

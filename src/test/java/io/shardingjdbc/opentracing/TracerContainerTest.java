/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 *
 *
 *
 */

package io.shardingjdbc.opentracing;

import io.opentracing.NoopTracerFactory;
import io.opentracing.util.GlobalTracer;
import io.shardingjdbc.core.exception.ShardingJdbcException;
import io.shardingjdbc.opentracing.config.OptsConfigParser;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(OptsConfigParser.class)
public class TracerContainerTest {
    
    @Before
    public void setUp() throws Exception {
        mockStatic(System.class);
        clearGlobalTracer();
    }
    
    @Test
    public void assertTracer() {
        when(System.getProperty("sjdbc.opentracing.tracer.class")).thenReturn("io.shardingjdbc.opentracing.fixture.FooTracer");
        assertThat((GlobalTracer) TracerContainer.init(), Is.isA(GlobalTracer.class));
        assertTrue(GlobalTracer.isRegistered());
        assertThat(TracerContainer.init(), Is.is(TracerContainer.init()));
    }
    
    @Test(expected = ShardingJdbcException.class)
    public void assertTracerClassError() {
        when(System.getProperty("sjdbc.opentracing.tracer.class")).thenReturn("com.foo.FooTracer");
        TracerContainer.init();
        
    }
    
    static void clearGlobalTracer() throws NoSuchFieldException, IllegalAccessException {
        Field tracerField = GlobalTracer.class.getDeclaredField("tracer");
        tracerField.setAccessible(true);
        tracerField.set(GlobalTracer.class, NoopTracerFactory.create());
    }
}

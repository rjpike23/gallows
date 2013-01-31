package com.swkoan.gallows.service;

import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 */
public class OperationDispatcherTest {
    
    public OperationDispatcherTest() {
    }
    
    @Test
    public void testSuccesfulDispatch() {
        OperationDispatcher dispatcher = new OperationDispatcher();
        try {
            Request request = mock(Request.class, "Request");
            ResponseHandler response = mock(ResponseHandler.class, "Response");
            Operation op1 = mock(Operation.class, "Op1");
            when(op1.handles(request)).thenReturn(Boolean.FALSE);
            Operation op2 = mock(Operation.class, "Op2");
            when(op2.handles(request)).thenReturn(Boolean.TRUE);
            Operation op3 = mock(Operation.class, "Op3");
            when(op3.handles(request)).thenReturn(Boolean.FALSE);
            dispatcher.setOperations(Arrays.asList(new Operation[]{op1, op2, op3}));
            
            dispatcher.dispatch(request, response);
            
            verify(op1).handles(request);
            verify(op1, never()).execute(request, response);
            verify(op2).handles(request);
            verify(op2).execute(request, response);
            verify(op3, never()).handles(request);
            verify(op3, never()).execute(request, response);
        }
        catch(Exception e) {
            e.printStackTrace();
            fail("Unexpected exception");
        }
    }
    
    @Test
    public void testUnsuccesfulDispatch() {
        OperationDispatcher dispatcher = new OperationDispatcher();
        try {
            Request request = mock(Request.class, "Request");
            ResponseHandler response = mock(ResponseHandler.class, "Response");
            Operation op1 = mock(Operation.class, "Op1");
            when(op1.handles(request)).thenReturn(Boolean.FALSE);
            dispatcher.setOperations(Arrays.asList(new Operation[]{op1}));
            
            dispatcher.dispatch(request, response);
            
            verify(op1).handles(request);
            verify(op1, never()).execute(request, response);
            verify(response).unableToDispatchRequest();
        }
        catch(Exception e) {
            e.printStackTrace();
            fail("Unexpected exception");
        }
    }
    
    @Test
    public void testDispatchExceptionOnHandles() {
        OperationDispatcher dispatcher = new OperationDispatcher();
        try {
            Request request = mock(Request.class, "Request");
            ResponseHandler response = mock(ResponseHandler.class, "Response");
            Operation op1 = mock(Operation.class, "Op1");
            when(op1.handles(request)).thenThrow(new RuntimeException());
            Operation op2 = mock(Operation.class, "Op2");
            when(op2.handles(request)).thenReturn(Boolean.TRUE);
            dispatcher.setOperations(Arrays.asList(new Operation[]{op1, op2}));
            
            dispatcher.dispatch(request, response);
            
            verify(op1).handles(request);
            verify(op1, never()).execute(request, response);
            verify(op2).handles(request);
            verify(op2).execute(request, response);
        }
        catch(Exception e) {
            e.printStackTrace();
            fail("Unexpected exception");
        }
    }
    
    @Test
    public void testDispatchExceptionOnExecute() {
        OperationDispatcher dispatcher = new OperationDispatcher();
        try {
            Request request = mock(Request.class, "Request");
            ResponseHandler response = mock(ResponseHandler.class, "Response");
            Operation op1 = mock(Operation.class, "Op1");
            when(op1.handles(request)).thenReturn(Boolean.TRUE);
            Exception e = new RuntimeException();
            doThrow(e).when(op1).execute(request, response);

            dispatcher.setOperations(Arrays.asList(new Operation[]{op1}));
            
            dispatcher.dispatch(request, response);
            
            verify(op1).handles(request);
            verify(op1).execute(request, response);
            verify(response).exceptionOnExecute(e);
        }
        catch(Exception e) {
            e.printStackTrace();
            fail("Unexpected exception");
        }
    }    
}

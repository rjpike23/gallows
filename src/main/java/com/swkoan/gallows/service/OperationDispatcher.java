package com.swkoan.gallows.service;

import java.util.List;

/**
 *
 */
public class OperationDispatcher {
    private List<Operation> operations;

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public void dispatch(Request request, ResponseHandler response) {
        for(Operation op: operations) {
            try {
                if(op.handles(request)) {
                    try {
                        op.execute(request, response);
                        return;
                    }
                    catch(Exception e) {
                        response.exceptionDuringDispatch(e);
                        return;
                    }
                }
            }
            catch(Exception e) {
                // Treat this the same as a "false" from handles().
            }
        }
        response.unableToDispatchRequest();
    }
}

package com.swkoan.gallows.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class OperationDispatcher {

    private static final Logger LOG = Logger.getLogger(OperationDispatcher.class.getName());
    private List<Operation> operations;

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public void dispatch(Request request, ResponseHandler response) {
        for (Operation op : operations) {
            try {
                if (op.handles(request)) {
                    try {
                        LOG.log(Level.INFO, "Dispatching request to {0}", op.getClass().getName());
                        op.execute(request, response);
                        return;
                    }
                    catch (Exception e) {
                        LOG.log(Level.SEVERE, "Exception thrown from op.execute(). Returning service exception.", e);
                        response.exceptionOnExecute(e);
                        return;
                    }
                }
            }
            catch (Exception e) {
                LOG.log(Level.WARNING, "Exception thrown from op.handles(). Ignoring op.", e);
            }
        }
        response.unableToDispatchRequest();
    }
}

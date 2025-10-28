package com.seibel.basicspring.common.exception.datafeed;

/**
 * @formatter:off
 * <p>Copyright: Copyright (c) 2021</p>
 * <p>Company: Greenman Financial Services, Inc.</p>
 *  
 * <p>Title: DataFeedNoDataFoundException</p>
 * <p>Description: This exception is used when the external datafeed service returns no data.  This occurs because we are querying for a price data range before the security was listed and contains data,
 *    or if the security is no longer listed.</p>
 *
 * @author Monte Seibel
 * @version 7.0
 * @formatter:on
 */

public class DataFeedNoDataFoundException extends Exception {
    public static final String CLASSNAME = "DataFeedNoDataFoundException";

    private static final long serialVersionUID = 1L;

    /**
     * The message that will be included in this exception for display to the user.
     */
    protected String message;

    public DataFeedNoDataFoundException(String messageString) {
        message = messageString;
    }

    /**
     * Public accessor to set the exception's message string.
     */
    public final String getMessage() {
        return message;
    }

    /**
     * Public accessor to return the exception's message string.
     */
    public final void setMessage(String newMessageString) {
        message = newMessageString;
    }
}

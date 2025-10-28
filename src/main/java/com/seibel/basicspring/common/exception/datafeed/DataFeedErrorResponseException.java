package com.seibel.basicspring.common.exception.datafeed;

/**
 * @formatter:off
 * <p>Copyright: Copyright (c) 2021</p>
 * <p>Company: Greenman Financial Services, Inc.</p>
 *  
 * <p>Title: DataFeedErrorResponseException</p>
 * <p>Description: This exception is used when the external datafeed service returns an error response.</p>
 *
 * @author Monte Seibel
 * @version 7.0
 * @formatter:on
 */

public class DataFeedErrorResponseException extends Exception {
    public static final String CLASSNAME = "DataFeedErrorResponseException";

    private static final long serialVersionUID = 1L;

    /**
     * The message that will be included in this exception for display to the user.
     */
    protected String message;

    public DataFeedErrorResponseException(String messageString) {
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

package com.seibel.basicspring.common.exception.datafeed;

/**
 * @formatter:off
 * <p>Copyright: Copyright (c) 2021</p>
 * <p>Company: Greenman Financial Services, Inc.</p>
 *  
 * <p>Title: DataFeedException</p>
 * <p>Description: This exception is used when the our datafeed service needs to throw a general exception.</p>
 *
 * @author Monte Seibel
 * @version 7.0
 * @formatter:on
 */

public class DataFeedException extends Exception {
    public static final String CLASSNAME = "DataFeedException";

    private static final long serialVersionUID = 1L;
    /**
     * The message that will be included in this exception for display to the user.
     */
    protected String message;

    public DataFeedException() {
    }

    /**
     * This constructor creates an DataFeedException object and initializes its message attribute with the input messageString parameter.
     *
     * @param messageString Any message that will be included in this exception for display to the user.
     * @return This Object
     */
    public DataFeedException(String messageString) {
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

package com.seibel.basicspring.common.exception.datafeed;

/**
 * @formatter:off
 * <p>Copyright: Copyright (c) 2021</p>
 * <p>Company: Greenman Financial Services, Inc.</p>
 *  
 * <p>Title: NoDataFromFeedException</p>
 * <p>Description: This exception is used when the external datafeed service returns no data.  This occurs because we are querying for a price data range before the security was listed and contains data,
 *    or if the security is no longer listed.
 *    
 *    NOTE: We should consider deprecating this exception and only use the DataFeedNoDataFoundException.</p>
 *
 * @author Monte Seibel
 * @version 7.0
 * @formatter:on
 */

public class NoDataFromFeedException extends Exception {
    public static final String CLASSNAME = "NoDataFromFeedException";

    private static final long serialVersionUID = 1L;
}

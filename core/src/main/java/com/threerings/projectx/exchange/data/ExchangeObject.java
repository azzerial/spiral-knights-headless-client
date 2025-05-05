package com.threerings.projectx.exchange.data;

import com.threerings.presents.dobj.DObject;

public class ExchangeObject extends DObject {

    public ExchangeMarshaller exchangeService;
    public int lastPrice;
    public ConsolidatedOffer[] buyOffers;
    public ConsolidatedOffer[] sellOffers;

    /* Constructors */

    public ExchangeObject() {}
}
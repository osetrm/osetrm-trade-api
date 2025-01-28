package com.osetrm.api;

import com.osetrm.api.trade.UniqueTransactionIdentifier;
import org.apache.commons.lang3.RandomStringUtils;

public class TestData {

    public static UniqueTransactionIdentifier randomUniqueTransactionIdentifier() {
        return new UniqueTransactionIdentifier(RandomStringUtils.secure().next(42));
    }

}

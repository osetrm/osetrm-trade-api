package com.osetrm.trade;

import com.osetrm.api.trade.UniqueTransactionIdentifier;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class TradeService {

    private Map<UniqueTransactionIdentifier, Trade> tradeMap = new HashMap<>();

    public Optional<Trade> getTrade(UniqueTransactionIdentifier uniqueTransactionIdentifier) {
        return Optional.ofNullable(tradeMap.get(uniqueTransactionIdentifier));
    }

    public Trade createTrade(Trade trade) {
        this.tradeMap.put(trade.uniqueTransactionIdentifier(), trade);
        return trade;
    }

}

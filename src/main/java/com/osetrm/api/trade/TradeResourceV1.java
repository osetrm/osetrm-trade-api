package com.osetrm.api.trade;

import com.osetrm.api.Role;
import com.osetrm.trade.Trade;
import com.osetrm.trade.TradeService;
import io.smallrye.common.constraint.NotNull;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;

@Path("/api/v1/trades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "customer", description = "Customer Operations")
public class TradeResourceV1 {

    private final TradeService tradeService;

    public TradeResourceV1(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GET
    @Path("/{uniqueTransactionIdentifier}")
    @APIResponse(responseCode = "200", description = "Get Trade by uniqueTransactionIdentifier",
            content = @Content(schema = @Schema(implementation = TradeV1.class))
    )
    @APIResponse(responseCode = "404", description = "Trade does not exist for uniqueTransactionIdentifier")
    @RolesAllowed(Role.TRADE_READ)
    public Response getByUniqueTransactionIdentifier(@Parameter(name = "uniqueTransactionIdentifier", required = true) @PathParam("uniqueTransactionIdentifier") String uniqueTransactionIdentifier) {
        return tradeService.getTrade(new UniqueTransactionIdentifier(uniqueTransactionIdentifier))
                .map(trade -> Response.ok(new TradeV1(trade.uniqueTransactionIdentifier())).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @APIResponse(responseCode = "201", description = "Trade Created",
            content = @Content(schema = @Schema(implementation = TradeV1.class))
    )
    @APIResponse(responseCode = "400", description = "Invalid Trade")
    @RolesAllowed(Role.TRADE_WRITE)
    public Response post(@NotNull @Valid TradeV1 tradeV1, @Context UriInfo uriInfo) {
        Trade created = tradeService.createTrade(new Trade(tradeV1.uniqueTransactionIdentifier()));
        URI uri = uriInfo.getAbsolutePathBuilder().path(tradeV1.uniqueTransactionIdentifier().value()).build();
        return Response.created(uri).entity(tradeV1).build();
    }

}

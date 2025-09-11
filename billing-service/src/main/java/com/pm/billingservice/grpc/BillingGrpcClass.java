package com.pm.billingservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcClass extends BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcClass.class);

    @Override
    public void createBillingAccount (BillingRequest billingRequest,
                                      StreamObserver<billing.BillingResponse> responseObserver) {
        log.info("createBillingAccount request received : {}", billingRequest.toString());

        //Busiess logic e.g. save to database ...

        BillingResponse response = BillingResponse.newBuilder()
                .setAccuntId("1234")
                .setStatus("ACTIVE")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

}

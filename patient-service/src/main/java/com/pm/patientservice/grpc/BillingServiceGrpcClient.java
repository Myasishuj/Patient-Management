package com.pm.patientservice.grpc;

// Importing the generated gRPC stub for BillingService
import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service // Marks this class as a Spring-managed service bean
public class BillingServiceGrpcClient {

    // Logger for debug/info logs
    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);

    // The blocking (synchronous) stub generated from the .proto definition
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    // Constructor injects values from application.properties or defaults
    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress, // <-- typo fix: missing '}' before closing quote
            @Value("${billing.service.grpc.port:9001}") int serverPort           // <-- same here
    ) {
        // Logs the target gRPC server address and port for visibility
        log.info("Connecting to BillingServiceGrpc server on address {} and port {}", serverAddress, serverPort);

        // Creates a gRPC channel to the given server (plaintext = no TLS)
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort)
                .usePlaintext()
                .build();

        // Initializes the blocking stub for making synchronous RPC calls
        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }
    public BillingResponse createBillingAccount(String patientId, String name, String email) {
        BillingRequest request = BillingRequest.newBuilder().setPatientId(patientId).setName(name).setEmail(email).build();

        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Recieved BillingResponse from BillingServiceGrpc : {}", response);
        return response;
    }

}

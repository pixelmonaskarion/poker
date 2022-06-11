package poker.networking;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: poker.proto")
public final class PokerGrpc {

  private PokerGrpc() {}

  public static final String SERVICE_NAME = "poker.networking.Poker";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<poker.networking.PokerOuterClass.JoinRequest,
      poker.networking.PokerOuterClass.JoinResponse> getPlayerJoinMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PlayerJoin",
      requestType = poker.networking.PokerOuterClass.JoinRequest.class,
      responseType = poker.networking.PokerOuterClass.JoinResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<poker.networking.PokerOuterClass.JoinRequest,
      poker.networking.PokerOuterClass.JoinResponse> getPlayerJoinMethod() {
    io.grpc.MethodDescriptor<poker.networking.PokerOuterClass.JoinRequest, poker.networking.PokerOuterClass.JoinResponse> getPlayerJoinMethod;
    if ((getPlayerJoinMethod = PokerGrpc.getPlayerJoinMethod) == null) {
      synchronized (PokerGrpc.class) {
        if ((getPlayerJoinMethod = PokerGrpc.getPlayerJoinMethod) == null) {
          PokerGrpc.getPlayerJoinMethod = getPlayerJoinMethod = 
              io.grpc.MethodDescriptor.<poker.networking.PokerOuterClass.JoinRequest, poker.networking.PokerOuterClass.JoinResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "poker.networking.Poker", "PlayerJoin"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  poker.networking.PokerOuterClass.JoinRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  poker.networking.PokerOuterClass.JoinResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new PokerMethodDescriptorSupplier("PlayerJoin"))
                  .build();
          }
        }
     }
     return getPlayerJoinMethod;
  }

  private static volatile io.grpc.MethodDescriptor<poker.networking.PokerOuterClass.Empty,
      poker.networking.PokerOuterClass.GameData> getGetGameDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetGameData",
      requestType = poker.networking.PokerOuterClass.Empty.class,
      responseType = poker.networking.PokerOuterClass.GameData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<poker.networking.PokerOuterClass.Empty,
      poker.networking.PokerOuterClass.GameData> getGetGameDataMethod() {
    io.grpc.MethodDescriptor<poker.networking.PokerOuterClass.Empty, poker.networking.PokerOuterClass.GameData> getGetGameDataMethod;
    if ((getGetGameDataMethod = PokerGrpc.getGetGameDataMethod) == null) {
      synchronized (PokerGrpc.class) {
        if ((getGetGameDataMethod = PokerGrpc.getGetGameDataMethod) == null) {
          PokerGrpc.getGetGameDataMethod = getGetGameDataMethod = 
              io.grpc.MethodDescriptor.<poker.networking.PokerOuterClass.Empty, poker.networking.PokerOuterClass.GameData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "poker.networking.Poker", "GetGameData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  poker.networking.PokerOuterClass.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  poker.networking.PokerOuterClass.GameData.getDefaultInstance()))
                  .setSchemaDescriptor(new PokerMethodDescriptorSupplier("GetGameData"))
                  .build();
          }
        }
     }
     return getGetGameDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<poker.networking.PokerOuterClass.Choice,
      poker.networking.PokerOuterClass.Empty> getSendChoiceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendChoice",
      requestType = poker.networking.PokerOuterClass.Choice.class,
      responseType = poker.networking.PokerOuterClass.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<poker.networking.PokerOuterClass.Choice,
      poker.networking.PokerOuterClass.Empty> getSendChoiceMethod() {
    io.grpc.MethodDescriptor<poker.networking.PokerOuterClass.Choice, poker.networking.PokerOuterClass.Empty> getSendChoiceMethod;
    if ((getSendChoiceMethod = PokerGrpc.getSendChoiceMethod) == null) {
      synchronized (PokerGrpc.class) {
        if ((getSendChoiceMethod = PokerGrpc.getSendChoiceMethod) == null) {
          PokerGrpc.getSendChoiceMethod = getSendChoiceMethod = 
              io.grpc.MethodDescriptor.<poker.networking.PokerOuterClass.Choice, poker.networking.PokerOuterClass.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "poker.networking.Poker", "SendChoice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  poker.networking.PokerOuterClass.Choice.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  poker.networking.PokerOuterClass.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new PokerMethodDescriptorSupplier("SendChoice"))
                  .build();
          }
        }
     }
     return getSendChoiceMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PokerStub newStub(io.grpc.Channel channel) {
    return new PokerStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PokerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PokerBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PokerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PokerFutureStub(channel);
  }

  /**
   */
  public static abstract class PokerImplBase implements io.grpc.BindableService {

    /**
     */
    public void playerJoin(poker.networking.PokerOuterClass.JoinRequest request,
        io.grpc.stub.StreamObserver<poker.networking.PokerOuterClass.JoinResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPlayerJoinMethod(), responseObserver);
    }

    /**
     */
    public void getGameData(poker.networking.PokerOuterClass.Empty request,
        io.grpc.stub.StreamObserver<poker.networking.PokerOuterClass.GameData> responseObserver) {
      asyncUnimplementedUnaryCall(getGetGameDataMethod(), responseObserver);
    }

    /**
     */
    public void sendChoice(poker.networking.PokerOuterClass.Choice request,
        io.grpc.stub.StreamObserver<poker.networking.PokerOuterClass.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getSendChoiceMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPlayerJoinMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                poker.networking.PokerOuterClass.JoinRequest,
                poker.networking.PokerOuterClass.JoinResponse>(
                  this, METHODID_PLAYER_JOIN)))
          .addMethod(
            getGetGameDataMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                poker.networking.PokerOuterClass.Empty,
                poker.networking.PokerOuterClass.GameData>(
                  this, METHODID_GET_GAME_DATA)))
          .addMethod(
            getSendChoiceMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                poker.networking.PokerOuterClass.Choice,
                poker.networking.PokerOuterClass.Empty>(
                  this, METHODID_SEND_CHOICE)))
          .build();
    }
  }

  /**
   */
  public static final class PokerStub extends io.grpc.stub.AbstractStub<PokerStub> {
    private PokerStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PokerStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PokerStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PokerStub(channel, callOptions);
    }

    /**
     */
    public void playerJoin(poker.networking.PokerOuterClass.JoinRequest request,
        io.grpc.stub.StreamObserver<poker.networking.PokerOuterClass.JoinResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPlayerJoinMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getGameData(poker.networking.PokerOuterClass.Empty request,
        io.grpc.stub.StreamObserver<poker.networking.PokerOuterClass.GameData> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetGameDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendChoice(poker.networking.PokerOuterClass.Choice request,
        io.grpc.stub.StreamObserver<poker.networking.PokerOuterClass.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSendChoiceMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PokerBlockingStub extends io.grpc.stub.AbstractStub<PokerBlockingStub> {
    private PokerBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PokerBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PokerBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PokerBlockingStub(channel, callOptions);
    }

    /**
     */
    public poker.networking.PokerOuterClass.JoinResponse playerJoin(poker.networking.PokerOuterClass.JoinRequest request) {
      return blockingUnaryCall(
          getChannel(), getPlayerJoinMethod(), getCallOptions(), request);
    }

    /**
     */
    public poker.networking.PokerOuterClass.GameData getGameData(poker.networking.PokerOuterClass.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetGameDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public poker.networking.PokerOuterClass.Empty sendChoice(poker.networking.PokerOuterClass.Choice request) {
      return blockingUnaryCall(
          getChannel(), getSendChoiceMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PokerFutureStub extends io.grpc.stub.AbstractStub<PokerFutureStub> {
    private PokerFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PokerFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PokerFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PokerFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<poker.networking.PokerOuterClass.JoinResponse> playerJoin(
        poker.networking.PokerOuterClass.JoinRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPlayerJoinMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<poker.networking.PokerOuterClass.GameData> getGameData(
        poker.networking.PokerOuterClass.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetGameDataMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<poker.networking.PokerOuterClass.Empty> sendChoice(
        poker.networking.PokerOuterClass.Choice request) {
      return futureUnaryCall(
          getChannel().newCall(getSendChoiceMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PLAYER_JOIN = 0;
  private static final int METHODID_GET_GAME_DATA = 1;
  private static final int METHODID_SEND_CHOICE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PokerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PokerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PLAYER_JOIN:
          serviceImpl.playerJoin((poker.networking.PokerOuterClass.JoinRequest) request,
              (io.grpc.stub.StreamObserver<poker.networking.PokerOuterClass.JoinResponse>) responseObserver);
          break;
        case METHODID_GET_GAME_DATA:
          serviceImpl.getGameData((poker.networking.PokerOuterClass.Empty) request,
              (io.grpc.stub.StreamObserver<poker.networking.PokerOuterClass.GameData>) responseObserver);
          break;
        case METHODID_SEND_CHOICE:
          serviceImpl.sendChoice((poker.networking.PokerOuterClass.Choice) request,
              (io.grpc.stub.StreamObserver<poker.networking.PokerOuterClass.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PokerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PokerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return poker.networking.PokerOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Poker");
    }
  }

  private static final class PokerFileDescriptorSupplier
      extends PokerBaseDescriptorSupplier {
    PokerFileDescriptorSupplier() {}
  }

  private static final class PokerMethodDescriptorSupplier
      extends PokerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PokerMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PokerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PokerFileDescriptorSupplier())
              .addMethod(getPlayerJoinMethod())
              .addMethod(getGetGameDataMethod())
              .addMethod(getSendChoiceMethod())
              .build();
        }
      }
    }
    return result;
  }
}

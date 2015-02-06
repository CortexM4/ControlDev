// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: DeviceState.proto

package netcontrol;

public final class DeviceStateProtos {
  private DeviceStateProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface DeviceStateOrBuilder extends
      // @@protoc_insertion_point(interface_extends:netcontrol.DeviceState)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional float sound = 1;</code>
     */
    boolean hasSound();
    /**
     * <code>optional float sound = 1;</code>
     */
    float getSound();

    /**
     * <code>optional bool power = 2;</code>
     */
    boolean hasPower();
    /**
     * <code>optional bool power = 2;</code>
     */
    boolean getPower();
  }
  /**
   * Protobuf type {@code netcontrol.DeviceState}
   */
  public static final class DeviceState extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:netcontrol.DeviceState)
      DeviceStateOrBuilder {
    // Use DeviceState.newBuilder() to construct.
    private DeviceState(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private DeviceState(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final DeviceState defaultInstance;
    public static DeviceState getDefaultInstance() {
      return defaultInstance;
    }

    public DeviceState getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private DeviceState(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 13: {
              bitField0_ |= 0x00000001;
              sound_ = input.readFloat();
              break;
            }
            case 16: {
              bitField0_ |= 0x00000002;
              power_ = input.readBool();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return netcontrol.DeviceStateProtos.internal_static_netcontrol_DeviceState_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return netcontrol.DeviceStateProtos.internal_static_netcontrol_DeviceState_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              netcontrol.DeviceStateProtos.DeviceState.class, netcontrol.DeviceStateProtos.DeviceState.Builder.class);
    }

    public static com.google.protobuf.Parser<DeviceState> PARSER =
        new com.google.protobuf.AbstractParser<DeviceState>() {
      public DeviceState parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new DeviceState(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<DeviceState> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    public static final int SOUND_FIELD_NUMBER = 1;
    private float sound_;
    /**
     * <code>optional float sound = 1;</code>
     */
    public boolean hasSound() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional float sound = 1;</code>
     */
    public float getSound() {
      return sound_;
    }

    public static final int POWER_FIELD_NUMBER = 2;
    private boolean power_;
    /**
     * <code>optional bool power = 2;</code>
     */
    public boolean hasPower() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>optional bool power = 2;</code>
     */
    public boolean getPower() {
      return power_;
    }

    private void initFields() {
      sound_ = 0F;
      power_ = false;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeFloat(1, sound_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBool(2, power_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(1, sound_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(2, power_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static netcontrol.DeviceStateProtos.DeviceState parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static netcontrol.DeviceStateProtos.DeviceState parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static netcontrol.DeviceStateProtos.DeviceState parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static netcontrol.DeviceStateProtos.DeviceState parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static netcontrol.DeviceStateProtos.DeviceState parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static netcontrol.DeviceStateProtos.DeviceState parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static netcontrol.DeviceStateProtos.DeviceState parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static netcontrol.DeviceStateProtos.DeviceState parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static netcontrol.DeviceStateProtos.DeviceState parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static netcontrol.DeviceStateProtos.DeviceState parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(netcontrol.DeviceStateProtos.DeviceState prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code netcontrol.DeviceState}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:netcontrol.DeviceState)
        netcontrol.DeviceStateProtos.DeviceStateOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return netcontrol.DeviceStateProtos.internal_static_netcontrol_DeviceState_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return netcontrol.DeviceStateProtos.internal_static_netcontrol_DeviceState_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                netcontrol.DeviceStateProtos.DeviceState.class, netcontrol.DeviceStateProtos.DeviceState.Builder.class);
      }

      // Construct using netcontrol.DeviceStateProtos.DeviceState.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        sound_ = 0F;
        bitField0_ = (bitField0_ & ~0x00000001);
        power_ = false;
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return netcontrol.DeviceStateProtos.internal_static_netcontrol_DeviceState_descriptor;
      }

      public netcontrol.DeviceStateProtos.DeviceState getDefaultInstanceForType() {
        return netcontrol.DeviceStateProtos.DeviceState.getDefaultInstance();
      }

      public netcontrol.DeviceStateProtos.DeviceState build() {
        netcontrol.DeviceStateProtos.DeviceState result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public netcontrol.DeviceStateProtos.DeviceState buildPartial() {
        netcontrol.DeviceStateProtos.DeviceState result = new netcontrol.DeviceStateProtos.DeviceState(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.sound_ = sound_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.power_ = power_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof netcontrol.DeviceStateProtos.DeviceState) {
          return mergeFrom((netcontrol.DeviceStateProtos.DeviceState)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(netcontrol.DeviceStateProtos.DeviceState other) {
        if (other == netcontrol.DeviceStateProtos.DeviceState.getDefaultInstance()) return this;
        if (other.hasSound()) {
          setSound(other.getSound());
        }
        if (other.hasPower()) {
          setPower(other.getPower());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        netcontrol.DeviceStateProtos.DeviceState parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (netcontrol.DeviceStateProtos.DeviceState) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private float sound_ ;
      /**
       * <code>optional float sound = 1;</code>
       */
      public boolean hasSound() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>optional float sound = 1;</code>
       */
      public float getSound() {
        return sound_;
      }
      /**
       * <code>optional float sound = 1;</code>
       */
      public Builder setSound(float value) {
        bitField0_ |= 0x00000001;
        sound_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional float sound = 1;</code>
       */
      public Builder clearSound() {
        bitField0_ = (bitField0_ & ~0x00000001);
        sound_ = 0F;
        onChanged();
        return this;
      }

      private boolean power_ ;
      /**
       * <code>optional bool power = 2;</code>
       */
      public boolean hasPower() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>optional bool power = 2;</code>
       */
      public boolean getPower() {
        return power_;
      }
      /**
       * <code>optional bool power = 2;</code>
       */
      public Builder setPower(boolean value) {
        bitField0_ |= 0x00000002;
        power_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bool power = 2;</code>
       */
      public Builder clearPower() {
        bitField0_ = (bitField0_ & ~0x00000002);
        power_ = false;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:netcontrol.DeviceState)
    }

    static {
      defaultInstance = new DeviceState(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:netcontrol.DeviceState)
  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_netcontrol_DeviceState_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_netcontrol_DeviceState_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021DeviceState.proto\022\nnetcontrol\"+\n\013Devic" +
      "eState\022\r\n\005sound\030\001 \001(\002\022\r\n\005power\030\002 \001(\010B\023B\021" +
      "DeviceStateProtos"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_netcontrol_DeviceState_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_netcontrol_DeviceState_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_netcontrol_DeviceState_descriptor,
        new java.lang.String[] { "Sound", "Power", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}

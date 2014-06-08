// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SensorProtocol.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "SensorProtocol.pb.h"

#include <algorithm>

#include <google/protobuf/stubs/common.h>
#include <google/protobuf/stubs/once.h>
#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/wire_format_lite_inl.h>
#include <google/protobuf/descriptor.h>
#include <google/protobuf/generated_message_reflection.h>
#include <google/protobuf/reflection_ops.h>
#include <google/protobuf/wire_format.h>
// @@protoc_insertion_point(includes)

namespace eaglesakura_ace {

namespace {

const ::google::protobuf::Descriptor* RawCadence_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  RawCadence_reflection_ = NULL;
const ::google::protobuf::EnumDescriptor* RawCadence_CadenceZone_descriptor_ = NULL;
const ::google::protobuf::Descriptor* RawHeartrate_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  RawHeartrate_reflection_ = NULL;
const ::google::protobuf::EnumDescriptor* RawHeartrate_HeartrateZone_descriptor_ = NULL;
const ::google::protobuf::Descriptor* SensorPayload_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  SensorPayload_reflection_ = NULL;
const ::google::protobuf::EnumDescriptor* SensorType_descriptor_ = NULL;

}  // namespace


void protobuf_AssignDesc_SensorProtocol_2eproto() {
  protobuf_AddDesc_SensorProtocol_2eproto();
  const ::google::protobuf::FileDescriptor* file =
    ::google::protobuf::DescriptorPool::generated_pool()->FindFileByName(
      "SensorProtocol.proto");
  GOOGLE_CHECK(file != NULL);
  RawCadence_descriptor_ = file->message_type(0);
  static const int RawCadence_offsets_[2] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(RawCadence, rpm_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(RawCadence, cadencezone_),
  };
  RawCadence_reflection_ =
    new ::google::protobuf::internal::GeneratedMessageReflection(
      RawCadence_descriptor_,
      RawCadence::default_instance_,
      RawCadence_offsets_,
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(RawCadence, _has_bits_[0]),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(RawCadence, _unknown_fields_),
      -1,
      ::google::protobuf::DescriptorPool::generated_pool(),
      ::google::protobuf::MessageFactory::generated_factory(),
      sizeof(RawCadence));
  RawCadence_CadenceZone_descriptor_ = RawCadence_descriptor_->enum_type(0);
  RawHeartrate_descriptor_ = file->message_type(1);
  static const int RawHeartrate_offsets_[2] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(RawHeartrate, bpm_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(RawHeartrate, heartratezone_),
  };
  RawHeartrate_reflection_ =
    new ::google::protobuf::internal::GeneratedMessageReflection(
      RawHeartrate_descriptor_,
      RawHeartrate::default_instance_,
      RawHeartrate_offsets_,
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(RawHeartrate, _has_bits_[0]),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(RawHeartrate, _unknown_fields_),
      -1,
      ::google::protobuf::DescriptorPool::generated_pool(),
      ::google::protobuf::MessageFactory::generated_factory(),
      sizeof(RawHeartrate));
  RawHeartrate_HeartrateZone_descriptor_ = RawHeartrate_descriptor_->enum_type(0);
  SensorPayload_descriptor_ = file->message_type(2);
  static const int SensorPayload_offsets_[2] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(SensorPayload, type_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(SensorPayload, buffer_),
  };
  SensorPayload_reflection_ =
    new ::google::protobuf::internal::GeneratedMessageReflection(
      SensorPayload_descriptor_,
      SensorPayload::default_instance_,
      SensorPayload_offsets_,
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(SensorPayload, _has_bits_[0]),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(SensorPayload, _unknown_fields_),
      -1,
      ::google::protobuf::DescriptorPool::generated_pool(),
      ::google::protobuf::MessageFactory::generated_factory(),
      sizeof(SensorPayload));
  SensorType_descriptor_ = file->enum_type(0);
}

namespace {

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AssignDescriptors_once_);
inline void protobuf_AssignDescriptorsOnce() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AssignDescriptors_once_,
                 &protobuf_AssignDesc_SensorProtocol_2eproto);
}

void protobuf_RegisterTypes(const ::std::string&) {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
    RawCadence_descriptor_, &RawCadence::default_instance());
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
    RawHeartrate_descriptor_, &RawHeartrate::default_instance());
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
    SensorPayload_descriptor_, &SensorPayload::default_instance());
}

}  // namespace

void protobuf_ShutdownFile_SensorProtocol_2eproto() {
  delete RawCadence::default_instance_;
  delete RawCadence_reflection_;
  delete RawHeartrate::default_instance_;
  delete RawHeartrate_reflection_;
  delete SensorPayload::default_instance_;
  delete SensorPayload_reflection_;
}

void protobuf_AddDesc_SensorProtocol_2eproto() {
  static bool already_here = false;
  if (already_here) return;
  already_here = true;
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::eaglesakura_ace::protobuf_AddDesc_AceConstants_2eproto();
  ::google::protobuf::DescriptorPool::InternalAddGeneratedFile(
    "\n\024SensorProtocol.proto\022\017eaglesakura_ace\032"
    "\022AceConstants.proto\"\211\001\n\nRawCadence\022\013\n\003rp"
    "m\030d \002(\005\022<\n\013cadenceZone\030e \002(\0162\'.eaglesaku"
    "ra_ace.RawCadence.CadenceZone\"0\n\013Cadence"
    "Zone\022\010\n\004Easy\020\000\022\014\n\010Beginner\020\001\022\t\n\005Ideal\020\002\""
    "\334\001\n\014RawHeartrate\022\013\n\003bpm\030d \002(\005\022B\n\rheartra"
    "teZone\030e \001(\0162+.eaglesakura_ace.RawHeartr"
    "ate.HeartrateZone\"{\n\rHeartrateZone\022\n\n\006Re"
    "pose\020\000\022\010\n\004Easy\020\001\022\021\n\rFatCombustion\020\002\022\032\n\026P"
    "ossessionOxygenMotion\020\003\022\027\n\023NonOxygenated"
    "Motion\020\004\022\014\n\010Overwork\020\005\"J\n\rSensorPayload\022"
    ")\n\004type\030\002 \002(\0162\033.eaglesakura_ace.SensorTy"
    "pe\022\016\n\006buffer\030\n \002(\014*5\n\nSensorType\022\024\n\020Hear"
    "trateMonitor\020\000\022\021\n\rCadenceSensor\020\001B$\n\"com"
    ".eaglesakura.andriders.protocol", 591);
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedFile(
    "SensorProtocol.proto", &protobuf_RegisterTypes);
  RawCadence::default_instance_ = new RawCadence();
  RawHeartrate::default_instance_ = new RawHeartrate();
  SensorPayload::default_instance_ = new SensorPayload();
  RawCadence::default_instance_->InitAsDefaultInstance();
  RawHeartrate::default_instance_->InitAsDefaultInstance();
  SensorPayload::default_instance_->InitAsDefaultInstance();
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_SensorProtocol_2eproto);
}

// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_SensorProtocol_2eproto {
  StaticDescriptorInitializer_SensorProtocol_2eproto() {
    protobuf_AddDesc_SensorProtocol_2eproto();
  }
} static_descriptor_initializer_SensorProtocol_2eproto_;
const ::google::protobuf::EnumDescriptor* SensorType_descriptor() {
  protobuf_AssignDescriptorsOnce();
  return SensorType_descriptor_;
}
bool SensorType_IsValid(int value) {
  switch(value) {
    case 0:
    case 1:
      return true;
    default:
      return false;
  }
}


// ===================================================================

const ::google::protobuf::EnumDescriptor* RawCadence_CadenceZone_descriptor() {
  protobuf_AssignDescriptorsOnce();
  return RawCadence_CadenceZone_descriptor_;
}
bool RawCadence_CadenceZone_IsValid(int value) {
  switch(value) {
    case 0:
    case 1:
    case 2:
      return true;
    default:
      return false;
  }
}

#ifndef _MSC_VER
const RawCadence_CadenceZone RawCadence::Easy;
const RawCadence_CadenceZone RawCadence::Beginner;
const RawCadence_CadenceZone RawCadence::Ideal;
const RawCadence_CadenceZone RawCadence::CadenceZone_MIN;
const RawCadence_CadenceZone RawCadence::CadenceZone_MAX;
const int RawCadence::CadenceZone_ARRAYSIZE;
#endif  // _MSC_VER
#ifndef _MSC_VER
const int RawCadence::kRpmFieldNumber;
const int RawCadence::kCadenceZoneFieldNumber;
#endif  // !_MSC_VER

RawCadence::RawCadence()
  : ::google::protobuf::Message() {
  SharedCtor();
}

void RawCadence::InitAsDefaultInstance() {
}

RawCadence::RawCadence(const RawCadence& from)
  : ::google::protobuf::Message() {
  SharedCtor();
  MergeFrom(from);
}

void RawCadence::SharedCtor() {
  _cached_size_ = 0;
  rpm_ = 0;
  cadencezone_ = 0;
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
}

RawCadence::~RawCadence() {
  SharedDtor();
}

void RawCadence::SharedDtor() {
  if (this != default_instance_) {
  }
}

void RawCadence::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* RawCadence::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return RawCadence_descriptor_;
}

const RawCadence& RawCadence::default_instance() {
  if (default_instance_ == NULL) protobuf_AddDesc_SensorProtocol_2eproto();
  return *default_instance_;
}

RawCadence* RawCadence::default_instance_ = NULL;

RawCadence* RawCadence::New() const {
  return new RawCadence;
}

void RawCadence::Clear() {
  if (_has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    rpm_ = 0;
    cadencezone_ = 0;
  }
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
  mutable_unknown_fields()->Clear();
}

bool RawCadence::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!(EXPRESSION)) return false
  ::google::protobuf::uint32 tag;
  while ((tag = input->ReadTag()) != 0) {
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // required int32 rpm = 100;
      case 100: {
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_VARINT) {
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int32, ::google::protobuf::internal::WireFormatLite::TYPE_INT32>(
                 input, &rpm_)));
          set_has_rpm();
        } else {
          goto handle_uninterpreted;
        }
        if (input->ExpectTag(808)) goto parse_cadenceZone;
        break;
      }

      // required .eaglesakura_ace.RawCadence.CadenceZone cadenceZone = 101;
      case 101: {
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_VARINT) {
         parse_cadenceZone:
          int value;
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   int, ::google::protobuf::internal::WireFormatLite::TYPE_ENUM>(
                 input, &value)));
          if (::eaglesakura_ace::RawCadence_CadenceZone_IsValid(value)) {
            set_cadencezone(static_cast< ::eaglesakura_ace::RawCadence_CadenceZone >(value));
          } else {
            mutable_unknown_fields()->AddVarint(101, value);
          }
        } else {
          goto handle_uninterpreted;
        }
        if (input->ExpectAtEnd()) return true;
        break;
      }

      default: {
      handle_uninterpreted:
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_END_GROUP) {
          return true;
        }
        DO_(::google::protobuf::internal::WireFormat::SkipField(
              input, tag, mutable_unknown_fields()));
        break;
      }
    }
  }
  return true;
#undef DO_
}

void RawCadence::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // required int32 rpm = 100;
  if (has_rpm()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt32(100, this->rpm(), output);
  }

  // required .eaglesakura_ace.RawCadence.CadenceZone cadenceZone = 101;
  if (has_cadencezone()) {
    ::google::protobuf::internal::WireFormatLite::WriteEnum(
      101, this->cadencezone(), output);
  }

  if (!unknown_fields().empty()) {
    ::google::protobuf::internal::WireFormat::SerializeUnknownFields(
        unknown_fields(), output);
  }
}

::google::protobuf::uint8* RawCadence::SerializeWithCachedSizesToArray(
    ::google::protobuf::uint8* target) const {
  // required int32 rpm = 100;
  if (has_rpm()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt32ToArray(100, this->rpm(), target);
  }

  // required .eaglesakura_ace.RawCadence.CadenceZone cadenceZone = 101;
  if (has_cadencezone()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteEnumToArray(
      101, this->cadencezone(), target);
  }

  if (!unknown_fields().empty()) {
    target = ::google::protobuf::internal::WireFormat::SerializeUnknownFieldsToArray(
        unknown_fields(), target);
  }
  return target;
}

int RawCadence::ByteSize() const {
  int total_size = 0;

  if (_has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    // required int32 rpm = 100;
    if (has_rpm()) {
      total_size += 2 +
        ::google::protobuf::internal::WireFormatLite::Int32Size(
          this->rpm());
    }

    // required .eaglesakura_ace.RawCadence.CadenceZone cadenceZone = 101;
    if (has_cadencezone()) {
      total_size += 2 +
        ::google::protobuf::internal::WireFormatLite::EnumSize(this->cadencezone());
    }

  }
  if (!unknown_fields().empty()) {
    total_size +=
      ::google::protobuf::internal::WireFormat::ComputeUnknownFieldsSize(
        unknown_fields());
  }
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = total_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void RawCadence::MergeFrom(const ::google::protobuf::Message& from) {
  GOOGLE_CHECK_NE(&from, this);
  const RawCadence* source =
    ::google::protobuf::internal::dynamic_cast_if_available<const RawCadence*>(
      &from);
  if (source == NULL) {
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
    MergeFrom(*source);
  }
}

void RawCadence::MergeFrom(const RawCadence& from) {
  GOOGLE_CHECK_NE(&from, this);
  if (from._has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    if (from.has_rpm()) {
      set_rpm(from.rpm());
    }
    if (from.has_cadencezone()) {
      set_cadencezone(from.cadencezone());
    }
  }
  mutable_unknown_fields()->MergeFrom(from.unknown_fields());
}

void RawCadence::CopyFrom(const ::google::protobuf::Message& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void RawCadence::CopyFrom(const RawCadence& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

bool RawCadence::IsInitialized() const {
  if ((_has_bits_[0] & 0x00000003) != 0x00000003) return false;

  return true;
}

void RawCadence::Swap(RawCadence* other) {
  if (other != this) {
    std::swap(rpm_, other->rpm_);
    std::swap(cadencezone_, other->cadencezone_);
    std::swap(_has_bits_[0], other->_has_bits_[0]);
    _unknown_fields_.Swap(&other->_unknown_fields_);
    std::swap(_cached_size_, other->_cached_size_);
  }
}

::google::protobuf::Metadata RawCadence::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = RawCadence_descriptor_;
  metadata.reflection = RawCadence_reflection_;
  return metadata;
}


// ===================================================================

const ::google::protobuf::EnumDescriptor* RawHeartrate_HeartrateZone_descriptor() {
  protobuf_AssignDescriptorsOnce();
  return RawHeartrate_HeartrateZone_descriptor_;
}
bool RawHeartrate_HeartrateZone_IsValid(int value) {
  switch(value) {
    case 0:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
      return true;
    default:
      return false;
  }
}

#ifndef _MSC_VER
const RawHeartrate_HeartrateZone RawHeartrate::Repose;
const RawHeartrate_HeartrateZone RawHeartrate::Easy;
const RawHeartrate_HeartrateZone RawHeartrate::FatCombustion;
const RawHeartrate_HeartrateZone RawHeartrate::PossessionOxygenMotion;
const RawHeartrate_HeartrateZone RawHeartrate::NonOxygenatedMotion;
const RawHeartrate_HeartrateZone RawHeartrate::Overwork;
const RawHeartrate_HeartrateZone RawHeartrate::HeartrateZone_MIN;
const RawHeartrate_HeartrateZone RawHeartrate::HeartrateZone_MAX;
const int RawHeartrate::HeartrateZone_ARRAYSIZE;
#endif  // _MSC_VER
#ifndef _MSC_VER
const int RawHeartrate::kBpmFieldNumber;
const int RawHeartrate::kHeartrateZoneFieldNumber;
#endif  // !_MSC_VER

RawHeartrate::RawHeartrate()
  : ::google::protobuf::Message() {
  SharedCtor();
}

void RawHeartrate::InitAsDefaultInstance() {
}

RawHeartrate::RawHeartrate(const RawHeartrate& from)
  : ::google::protobuf::Message() {
  SharedCtor();
  MergeFrom(from);
}

void RawHeartrate::SharedCtor() {
  _cached_size_ = 0;
  bpm_ = 0;
  heartratezone_ = 0;
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
}

RawHeartrate::~RawHeartrate() {
  SharedDtor();
}

void RawHeartrate::SharedDtor() {
  if (this != default_instance_) {
  }
}

void RawHeartrate::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* RawHeartrate::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return RawHeartrate_descriptor_;
}

const RawHeartrate& RawHeartrate::default_instance() {
  if (default_instance_ == NULL) protobuf_AddDesc_SensorProtocol_2eproto();
  return *default_instance_;
}

RawHeartrate* RawHeartrate::default_instance_ = NULL;

RawHeartrate* RawHeartrate::New() const {
  return new RawHeartrate;
}

void RawHeartrate::Clear() {
  if (_has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    bpm_ = 0;
    heartratezone_ = 0;
  }
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
  mutable_unknown_fields()->Clear();
}

bool RawHeartrate::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!(EXPRESSION)) return false
  ::google::protobuf::uint32 tag;
  while ((tag = input->ReadTag()) != 0) {
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // required int32 bpm = 100;
      case 100: {
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_VARINT) {
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int32, ::google::protobuf::internal::WireFormatLite::TYPE_INT32>(
                 input, &bpm_)));
          set_has_bpm();
        } else {
          goto handle_uninterpreted;
        }
        if (input->ExpectTag(808)) goto parse_heartrateZone;
        break;
      }

      // optional .eaglesakura_ace.RawHeartrate.HeartrateZone heartrateZone = 101;
      case 101: {
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_VARINT) {
         parse_heartrateZone:
          int value;
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   int, ::google::protobuf::internal::WireFormatLite::TYPE_ENUM>(
                 input, &value)));
          if (::eaglesakura_ace::RawHeartrate_HeartrateZone_IsValid(value)) {
            set_heartratezone(static_cast< ::eaglesakura_ace::RawHeartrate_HeartrateZone >(value));
          } else {
            mutable_unknown_fields()->AddVarint(101, value);
          }
        } else {
          goto handle_uninterpreted;
        }
        if (input->ExpectAtEnd()) return true;
        break;
      }

      default: {
      handle_uninterpreted:
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_END_GROUP) {
          return true;
        }
        DO_(::google::protobuf::internal::WireFormat::SkipField(
              input, tag, mutable_unknown_fields()));
        break;
      }
    }
  }
  return true;
#undef DO_
}

void RawHeartrate::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // required int32 bpm = 100;
  if (has_bpm()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt32(100, this->bpm(), output);
  }

  // optional .eaglesakura_ace.RawHeartrate.HeartrateZone heartrateZone = 101;
  if (has_heartratezone()) {
    ::google::protobuf::internal::WireFormatLite::WriteEnum(
      101, this->heartratezone(), output);
  }

  if (!unknown_fields().empty()) {
    ::google::protobuf::internal::WireFormat::SerializeUnknownFields(
        unknown_fields(), output);
  }
}

::google::protobuf::uint8* RawHeartrate::SerializeWithCachedSizesToArray(
    ::google::protobuf::uint8* target) const {
  // required int32 bpm = 100;
  if (has_bpm()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt32ToArray(100, this->bpm(), target);
  }

  // optional .eaglesakura_ace.RawHeartrate.HeartrateZone heartrateZone = 101;
  if (has_heartratezone()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteEnumToArray(
      101, this->heartratezone(), target);
  }

  if (!unknown_fields().empty()) {
    target = ::google::protobuf::internal::WireFormat::SerializeUnknownFieldsToArray(
        unknown_fields(), target);
  }
  return target;
}

int RawHeartrate::ByteSize() const {
  int total_size = 0;

  if (_has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    // required int32 bpm = 100;
    if (has_bpm()) {
      total_size += 2 +
        ::google::protobuf::internal::WireFormatLite::Int32Size(
          this->bpm());
    }

    // optional .eaglesakura_ace.RawHeartrate.HeartrateZone heartrateZone = 101;
    if (has_heartratezone()) {
      total_size += 2 +
        ::google::protobuf::internal::WireFormatLite::EnumSize(this->heartratezone());
    }

  }
  if (!unknown_fields().empty()) {
    total_size +=
      ::google::protobuf::internal::WireFormat::ComputeUnknownFieldsSize(
        unknown_fields());
  }
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = total_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void RawHeartrate::MergeFrom(const ::google::protobuf::Message& from) {
  GOOGLE_CHECK_NE(&from, this);
  const RawHeartrate* source =
    ::google::protobuf::internal::dynamic_cast_if_available<const RawHeartrate*>(
      &from);
  if (source == NULL) {
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
    MergeFrom(*source);
  }
}

void RawHeartrate::MergeFrom(const RawHeartrate& from) {
  GOOGLE_CHECK_NE(&from, this);
  if (from._has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    if (from.has_bpm()) {
      set_bpm(from.bpm());
    }
    if (from.has_heartratezone()) {
      set_heartratezone(from.heartratezone());
    }
  }
  mutable_unknown_fields()->MergeFrom(from.unknown_fields());
}

void RawHeartrate::CopyFrom(const ::google::protobuf::Message& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void RawHeartrate::CopyFrom(const RawHeartrate& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

bool RawHeartrate::IsInitialized() const {
  if ((_has_bits_[0] & 0x00000001) != 0x00000001) return false;

  return true;
}

void RawHeartrate::Swap(RawHeartrate* other) {
  if (other != this) {
    std::swap(bpm_, other->bpm_);
    std::swap(heartratezone_, other->heartratezone_);
    std::swap(_has_bits_[0], other->_has_bits_[0]);
    _unknown_fields_.Swap(&other->_unknown_fields_);
    std::swap(_cached_size_, other->_cached_size_);
  }
}

::google::protobuf::Metadata RawHeartrate::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = RawHeartrate_descriptor_;
  metadata.reflection = RawHeartrate_reflection_;
  return metadata;
}


// ===================================================================

#ifndef _MSC_VER
const int SensorPayload::kTypeFieldNumber;
const int SensorPayload::kBufferFieldNumber;
#endif  // !_MSC_VER

SensorPayload::SensorPayload()
  : ::google::protobuf::Message() {
  SharedCtor();
}

void SensorPayload::InitAsDefaultInstance() {
}

SensorPayload::SensorPayload(const SensorPayload& from)
  : ::google::protobuf::Message() {
  SharedCtor();
  MergeFrom(from);
}

void SensorPayload::SharedCtor() {
  _cached_size_ = 0;
  type_ = 0;
  buffer_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
}

SensorPayload::~SensorPayload() {
  SharedDtor();
}

void SensorPayload::SharedDtor() {
  if (buffer_ != &::google::protobuf::internal::kEmptyString) {
    delete buffer_;
  }
  if (this != default_instance_) {
  }
}

void SensorPayload::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* SensorPayload::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return SensorPayload_descriptor_;
}

const SensorPayload& SensorPayload::default_instance() {
  if (default_instance_ == NULL) protobuf_AddDesc_SensorProtocol_2eproto();
  return *default_instance_;
}

SensorPayload* SensorPayload::default_instance_ = NULL;

SensorPayload* SensorPayload::New() const {
  return new SensorPayload;
}

void SensorPayload::Clear() {
  if (_has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    type_ = 0;
    if (has_buffer()) {
      if (buffer_ != &::google::protobuf::internal::kEmptyString) {
        buffer_->clear();
      }
    }
  }
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
  mutable_unknown_fields()->Clear();
}

bool SensorPayload::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!(EXPRESSION)) return false
  ::google::protobuf::uint32 tag;
  while ((tag = input->ReadTag()) != 0) {
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // required .eaglesakura_ace.SensorType type = 2;
      case 2: {
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_VARINT) {
          int value;
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   int, ::google::protobuf::internal::WireFormatLite::TYPE_ENUM>(
                 input, &value)));
          if (::eaglesakura_ace::SensorType_IsValid(value)) {
            set_type(static_cast< ::eaglesakura_ace::SensorType >(value));
          } else {
            mutable_unknown_fields()->AddVarint(2, value);
          }
        } else {
          goto handle_uninterpreted;
        }
        if (input->ExpectTag(82)) goto parse_buffer;
        break;
      }

      // required bytes buffer = 10;
      case 10: {
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_LENGTH_DELIMITED) {
         parse_buffer:
          DO_(::google::protobuf::internal::WireFormatLite::ReadBytes(
                input, this->mutable_buffer()));
        } else {
          goto handle_uninterpreted;
        }
        if (input->ExpectAtEnd()) return true;
        break;
      }

      default: {
      handle_uninterpreted:
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_END_GROUP) {
          return true;
        }
        DO_(::google::protobuf::internal::WireFormat::SkipField(
              input, tag, mutable_unknown_fields()));
        break;
      }
    }
  }
  return true;
#undef DO_
}

void SensorPayload::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // required .eaglesakura_ace.SensorType type = 2;
  if (has_type()) {
    ::google::protobuf::internal::WireFormatLite::WriteEnum(
      2, this->type(), output);
  }

  // required bytes buffer = 10;
  if (has_buffer()) {
    ::google::protobuf::internal::WireFormatLite::WriteBytes(
      10, this->buffer(), output);
  }

  if (!unknown_fields().empty()) {
    ::google::protobuf::internal::WireFormat::SerializeUnknownFields(
        unknown_fields(), output);
  }
}

::google::protobuf::uint8* SensorPayload::SerializeWithCachedSizesToArray(
    ::google::protobuf::uint8* target) const {
  // required .eaglesakura_ace.SensorType type = 2;
  if (has_type()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteEnumToArray(
      2, this->type(), target);
  }

  // required bytes buffer = 10;
  if (has_buffer()) {
    target =
      ::google::protobuf::internal::WireFormatLite::WriteBytesToArray(
        10, this->buffer(), target);
  }

  if (!unknown_fields().empty()) {
    target = ::google::protobuf::internal::WireFormat::SerializeUnknownFieldsToArray(
        unknown_fields(), target);
  }
  return target;
}

int SensorPayload::ByteSize() const {
  int total_size = 0;

  if (_has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    // required .eaglesakura_ace.SensorType type = 2;
    if (has_type()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::EnumSize(this->type());
    }

    // required bytes buffer = 10;
    if (has_buffer()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::BytesSize(
          this->buffer());
    }

  }
  if (!unknown_fields().empty()) {
    total_size +=
      ::google::protobuf::internal::WireFormat::ComputeUnknownFieldsSize(
        unknown_fields());
  }
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = total_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void SensorPayload::MergeFrom(const ::google::protobuf::Message& from) {
  GOOGLE_CHECK_NE(&from, this);
  const SensorPayload* source =
    ::google::protobuf::internal::dynamic_cast_if_available<const SensorPayload*>(
      &from);
  if (source == NULL) {
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
    MergeFrom(*source);
  }
}

void SensorPayload::MergeFrom(const SensorPayload& from) {
  GOOGLE_CHECK_NE(&from, this);
  if (from._has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    if (from.has_type()) {
      set_type(from.type());
    }
    if (from.has_buffer()) {
      set_buffer(from.buffer());
    }
  }
  mutable_unknown_fields()->MergeFrom(from.unknown_fields());
}

void SensorPayload::CopyFrom(const ::google::protobuf::Message& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void SensorPayload::CopyFrom(const SensorPayload& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

bool SensorPayload::IsInitialized() const {
  if ((_has_bits_[0] & 0x00000003) != 0x00000003) return false;

  return true;
}

void SensorPayload::Swap(SensorPayload* other) {
  if (other != this) {
    std::swap(type_, other->type_);
    std::swap(buffer_, other->buffer_);
    std::swap(_has_bits_[0], other->_has_bits_[0]);
    _unknown_fields_.Swap(&other->_unknown_fields_);
    std::swap(_cached_size_, other->_cached_size_);
  }
}

::google::protobuf::Metadata SensorPayload::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = SensorPayload_descriptor_;
  metadata.reflection = SensorPayload_reflection_;
  return metadata;
}


// @@protoc_insertion_point(namespace_scope)

}  // namespace eaglesakura_ace

// @@protoc_insertion_point(global_scope)
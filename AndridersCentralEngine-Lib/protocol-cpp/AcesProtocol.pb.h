// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: AcesProtocol.proto

#ifndef PROTOBUF_AcesProtocol_2eproto__INCLUDED
#define PROTOBUF_AcesProtocol_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#if GOOGLE_PROTOBUF_VERSION < 2005000
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 2005000 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/message.h>
#include <google/protobuf/repeated_field.h>
#include <google/protobuf/extension_set.h>
#include <google/protobuf/unknown_field_set.h>
#include "AceConstants.pb.h"
// @@protoc_insertion_point(includes)

namespace eaglesakura_ace {

// Internal implementation detail -- do not call these.
void  protobuf_AddDesc_AcesProtocol_2eproto();
void protobuf_AssignDesc_AcesProtocol_2eproto();
void protobuf_ShutdownFile_AcesProtocol_2eproto();

class SensorPayload;
class VersionInfo;
class CentralStatus;
class MasterPayload;

// ===================================================================

class SensorPayload : public ::google::protobuf::Message {
 public:
  SensorPayload();
  virtual ~SensorPayload();

  SensorPayload(const SensorPayload& from);

  inline SensorPayload& operator=(const SensorPayload& from) {
    CopyFrom(from);
    return *this;
  }

  inline const ::google::protobuf::UnknownFieldSet& unknown_fields() const {
    return _unknown_fields_;
  }

  inline ::google::protobuf::UnknownFieldSet* mutable_unknown_fields() {
    return &_unknown_fields_;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const SensorPayload& default_instance();

  void Swap(SensorPayload* other);

  // implements Message ----------------------------------------------

  SensorPayload* New() const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const SensorPayload& from);
  void MergeFrom(const SensorPayload& from);
  void Clear();
  bool IsInitialized() const;

  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // required .eaglesakura_ace.SensorType type = 2;
  inline bool has_type() const;
  inline void clear_type();
  static const int kTypeFieldNumber = 2;
  inline ::eaglesakura_ace::SensorType type() const;
  inline void set_type(::eaglesakura_ace::SensorType value);

  // required bytes buffer = 10;
  inline bool has_buffer() const;
  inline void clear_buffer();
  static const int kBufferFieldNumber = 10;
  inline const ::std::string& buffer() const;
  inline void set_buffer(const ::std::string& value);
  inline void set_buffer(const char* value);
  inline void set_buffer(const void* value, size_t size);
  inline ::std::string* mutable_buffer();
  inline ::std::string* release_buffer();
  inline void set_allocated_buffer(::std::string* buffer);

  // @@protoc_insertion_point(class_scope:eaglesakura_ace.SensorPayload)
 private:
  inline void set_has_type();
  inline void clear_has_type();
  inline void set_has_buffer();
  inline void clear_has_buffer();

  ::google::protobuf::UnknownFieldSet _unknown_fields_;

  ::std::string* buffer_;
  int type_;

  mutable int _cached_size_;
  ::google::protobuf::uint32 _has_bits_[(2 + 31) / 32];

  friend void  protobuf_AddDesc_AcesProtocol_2eproto();
  friend void protobuf_AssignDesc_AcesProtocol_2eproto();
  friend void protobuf_ShutdownFile_AcesProtocol_2eproto();

  void InitAsDefaultInstance();
  static SensorPayload* default_instance_;
};
// -------------------------------------------------------------------

class VersionInfo : public ::google::protobuf::Message {
 public:
  VersionInfo();
  virtual ~VersionInfo();

  VersionInfo(const VersionInfo& from);

  inline VersionInfo& operator=(const VersionInfo& from) {
    CopyFrom(from);
    return *this;
  }

  inline const ::google::protobuf::UnknownFieldSet& unknown_fields() const {
    return _unknown_fields_;
  }

  inline ::google::protobuf::UnknownFieldSet* mutable_unknown_fields() {
    return &_unknown_fields_;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const VersionInfo& default_instance();

  void Swap(VersionInfo* other);

  // implements Message ----------------------------------------------

  VersionInfo* New() const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const VersionInfo& from);
  void MergeFrom(const VersionInfo& from);
  void Clear();
  bool IsInitialized() const;

  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // required int64 protocolVersion = 1;
  inline bool has_protocolversion() const;
  inline void clear_protocolversion();
  static const int kProtocolVersionFieldNumber = 1;
  inline ::google::protobuf::int64 protocolversion() const;
  inline void set_protocolversion(::google::protobuf::int64 value);

  // required string appVersionName = 2;
  inline bool has_appversionname() const;
  inline void clear_appversionname();
  static const int kAppVersionNameFieldNumber = 2;
  inline const ::std::string& appversionname() const;
  inline void set_appversionname(const ::std::string& value);
  inline void set_appversionname(const char* value);
  inline void set_appversionname(const char* value, size_t size);
  inline ::std::string* mutable_appversionname();
  inline ::std::string* release_appversionname();
  inline void set_allocated_appversionname(::std::string* appversionname);

  // @@protoc_insertion_point(class_scope:eaglesakura_ace.VersionInfo)
 private:
  inline void set_has_protocolversion();
  inline void clear_has_protocolversion();
  inline void set_has_appversionname();
  inline void clear_has_appversionname();

  ::google::protobuf::UnknownFieldSet _unknown_fields_;

  ::google::protobuf::int64 protocolversion_;
  ::std::string* appversionname_;

  mutable int _cached_size_;
  ::google::protobuf::uint32 _has_bits_[(2 + 31) / 32];

  friend void  protobuf_AddDesc_AcesProtocol_2eproto();
  friend void protobuf_AssignDesc_AcesProtocol_2eproto();
  friend void protobuf_ShutdownFile_AcesProtocol_2eproto();

  void InitAsDefaultInstance();
  static VersionInfo* default_instance_;
};
// -------------------------------------------------------------------

class CentralStatus : public ::google::protobuf::Message {
 public:
  CentralStatus();
  virtual ~CentralStatus();

  CentralStatus(const CentralStatus& from);

  inline CentralStatus& operator=(const CentralStatus& from) {
    CopyFrom(from);
    return *this;
  }

  inline const ::google::protobuf::UnknownFieldSet& unknown_fields() const {
    return _unknown_fields_;
  }

  inline ::google::protobuf::UnknownFieldSet* mutable_unknown_fields() {
    return &_unknown_fields_;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const CentralStatus& default_instance();

  void Swap(CentralStatus* other);

  // implements Message ----------------------------------------------

  CentralStatus* New() const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const CentralStatus& from);
  void MergeFrom(const CentralStatus& from);
  void Clear();
  bool IsInitialized() const;

  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // required bool connectedHeartrate = 1;
  inline bool has_connectedheartrate() const;
  inline void clear_connectedheartrate();
  static const int kConnectedHeartrateFieldNumber = 1;
  inline bool connectedheartrate() const;
  inline void set_connectedheartrate(bool value);

  // required bool connectedCadence = 2;
  inline bool has_connectedcadence() const;
  inline void clear_connectedcadence();
  static const int kConnectedCadenceFieldNumber = 2;
  inline bool connectedcadence() const;
  inline void set_connectedcadence(bool value);

  // @@protoc_insertion_point(class_scope:eaglesakura_ace.CentralStatus)
 private:
  inline void set_has_connectedheartrate();
  inline void clear_has_connectedheartrate();
  inline void set_has_connectedcadence();
  inline void clear_has_connectedcadence();

  ::google::protobuf::UnknownFieldSet _unknown_fields_;

  bool connectedheartrate_;
  bool connectedcadence_;

  mutable int _cached_size_;
  ::google::protobuf::uint32 _has_bits_[(2 + 31) / 32];

  friend void  protobuf_AddDesc_AcesProtocol_2eproto();
  friend void protobuf_AssignDesc_AcesProtocol_2eproto();
  friend void protobuf_ShutdownFile_AcesProtocol_2eproto();

  void InitAsDefaultInstance();
  static CentralStatus* default_instance_;
};
// -------------------------------------------------------------------

class MasterPayload : public ::google::protobuf::Message {
 public:
  MasterPayload();
  virtual ~MasterPayload();

  MasterPayload(const MasterPayload& from);

  inline MasterPayload& operator=(const MasterPayload& from) {
    CopyFrom(from);
    return *this;
  }

  inline const ::google::protobuf::UnknownFieldSet& unknown_fields() const {
    return _unknown_fields_;
  }

  inline ::google::protobuf::UnknownFieldSet* mutable_unknown_fields() {
    return &_unknown_fields_;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const MasterPayload& default_instance();

  void Swap(MasterPayload* other);

  // implements Message ----------------------------------------------

  MasterPayload* New() const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const MasterPayload& from);
  void MergeFrom(const MasterPayload& from);
  void Clear();
  bool IsInitialized() const;

  int ByteSize() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const;
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // required string createdDate = 3;
  inline bool has_createddate() const;
  inline void clear_createddate();
  static const int kCreatedDateFieldNumber = 3;
  inline const ::std::string& createddate() const;
  inline void set_createddate(const ::std::string& value);
  inline void set_createddate(const char* value);
  inline void set_createddate(const char* value, size_t size);
  inline ::std::string* mutable_createddate();
  inline ::std::string* release_createddate();
  inline void set_allocated_createddate(::std::string* createddate);

  // required .eaglesakura_ace.CentralStatus centralStatus = 1;
  inline bool has_centralstatus() const;
  inline void clear_centralstatus();
  static const int kCentralStatusFieldNumber = 1;
  inline const ::eaglesakura_ace::CentralStatus& centralstatus() const;
  inline ::eaglesakura_ace::CentralStatus* mutable_centralstatus();
  inline ::eaglesakura_ace::CentralStatus* release_centralstatus();
  inline void set_allocated_centralstatus(::eaglesakura_ace::CentralStatus* centralstatus);

  // repeated .eaglesakura_ace.SensorPayload sensorPayloads = 2;
  inline int sensorpayloads_size() const;
  inline void clear_sensorpayloads();
  static const int kSensorPayloadsFieldNumber = 2;
  inline const ::eaglesakura_ace::SensorPayload& sensorpayloads(int index) const;
  inline ::eaglesakura_ace::SensorPayload* mutable_sensorpayloads(int index);
  inline ::eaglesakura_ace::SensorPayload* add_sensorpayloads();
  inline const ::google::protobuf::RepeatedPtrField< ::eaglesakura_ace::SensorPayload >&
      sensorpayloads() const;
  inline ::google::protobuf::RepeatedPtrField< ::eaglesakura_ace::SensorPayload >*
      mutable_sensorpayloads();

  // @@protoc_insertion_point(class_scope:eaglesakura_ace.MasterPayload)
 private:
  inline void set_has_createddate();
  inline void clear_has_createddate();
  inline void set_has_centralstatus();
  inline void clear_has_centralstatus();

  ::google::protobuf::UnknownFieldSet _unknown_fields_;

  ::std::string* createddate_;
  ::eaglesakura_ace::CentralStatus* centralstatus_;
  ::google::protobuf::RepeatedPtrField< ::eaglesakura_ace::SensorPayload > sensorpayloads_;

  mutable int _cached_size_;
  ::google::protobuf::uint32 _has_bits_[(3 + 31) / 32];

  friend void  protobuf_AddDesc_AcesProtocol_2eproto();
  friend void protobuf_AssignDesc_AcesProtocol_2eproto();
  friend void protobuf_ShutdownFile_AcesProtocol_2eproto();

  void InitAsDefaultInstance();
  static MasterPayload* default_instance_;
};
// ===================================================================


// ===================================================================

// SensorPayload

// required .eaglesakura_ace.SensorType type = 2;
inline bool SensorPayload::has_type() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
inline void SensorPayload::set_has_type() {
  _has_bits_[0] |= 0x00000001u;
}
inline void SensorPayload::clear_has_type() {
  _has_bits_[0] &= ~0x00000001u;
}
inline void SensorPayload::clear_type() {
  type_ = 0;
  clear_has_type();
}
inline ::eaglesakura_ace::SensorType SensorPayload::type() const {
  return static_cast< ::eaglesakura_ace::SensorType >(type_);
}
inline void SensorPayload::set_type(::eaglesakura_ace::SensorType value) {
  assert(::eaglesakura_ace::SensorType_IsValid(value));
  set_has_type();
  type_ = value;
}

// required bytes buffer = 10;
inline bool SensorPayload::has_buffer() const {
  return (_has_bits_[0] & 0x00000002u) != 0;
}
inline void SensorPayload::set_has_buffer() {
  _has_bits_[0] |= 0x00000002u;
}
inline void SensorPayload::clear_has_buffer() {
  _has_bits_[0] &= ~0x00000002u;
}
inline void SensorPayload::clear_buffer() {
  if (buffer_ != &::google::protobuf::internal::kEmptyString) {
    buffer_->clear();
  }
  clear_has_buffer();
}
inline const ::std::string& SensorPayload::buffer() const {
  return *buffer_;
}
inline void SensorPayload::set_buffer(const ::std::string& value) {
  set_has_buffer();
  if (buffer_ == &::google::protobuf::internal::kEmptyString) {
    buffer_ = new ::std::string;
  }
  buffer_->assign(value);
}
inline void SensorPayload::set_buffer(const char* value) {
  set_has_buffer();
  if (buffer_ == &::google::protobuf::internal::kEmptyString) {
    buffer_ = new ::std::string;
  }
  buffer_->assign(value);
}
inline void SensorPayload::set_buffer(const void* value, size_t size) {
  set_has_buffer();
  if (buffer_ == &::google::protobuf::internal::kEmptyString) {
    buffer_ = new ::std::string;
  }
  buffer_->assign(reinterpret_cast<const char*>(value), size);
}
inline ::std::string* SensorPayload::mutable_buffer() {
  set_has_buffer();
  if (buffer_ == &::google::protobuf::internal::kEmptyString) {
    buffer_ = new ::std::string;
  }
  return buffer_;
}
inline ::std::string* SensorPayload::release_buffer() {
  clear_has_buffer();
  if (buffer_ == &::google::protobuf::internal::kEmptyString) {
    return NULL;
  } else {
    ::std::string* temp = buffer_;
    buffer_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
    return temp;
  }
}
inline void SensorPayload::set_allocated_buffer(::std::string* buffer) {
  if (buffer_ != &::google::protobuf::internal::kEmptyString) {
    delete buffer_;
  }
  if (buffer) {
    set_has_buffer();
    buffer_ = buffer;
  } else {
    clear_has_buffer();
    buffer_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
  }
}

// -------------------------------------------------------------------

// VersionInfo

// required int64 protocolVersion = 1;
inline bool VersionInfo::has_protocolversion() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
inline void VersionInfo::set_has_protocolversion() {
  _has_bits_[0] |= 0x00000001u;
}
inline void VersionInfo::clear_has_protocolversion() {
  _has_bits_[0] &= ~0x00000001u;
}
inline void VersionInfo::clear_protocolversion() {
  protocolversion_ = GOOGLE_LONGLONG(0);
  clear_has_protocolversion();
}
inline ::google::protobuf::int64 VersionInfo::protocolversion() const {
  return protocolversion_;
}
inline void VersionInfo::set_protocolversion(::google::protobuf::int64 value) {
  set_has_protocolversion();
  protocolversion_ = value;
}

// required string appVersionName = 2;
inline bool VersionInfo::has_appversionname() const {
  return (_has_bits_[0] & 0x00000002u) != 0;
}
inline void VersionInfo::set_has_appversionname() {
  _has_bits_[0] |= 0x00000002u;
}
inline void VersionInfo::clear_has_appversionname() {
  _has_bits_[0] &= ~0x00000002u;
}
inline void VersionInfo::clear_appversionname() {
  if (appversionname_ != &::google::protobuf::internal::kEmptyString) {
    appversionname_->clear();
  }
  clear_has_appversionname();
}
inline const ::std::string& VersionInfo::appversionname() const {
  return *appversionname_;
}
inline void VersionInfo::set_appversionname(const ::std::string& value) {
  set_has_appversionname();
  if (appversionname_ == &::google::protobuf::internal::kEmptyString) {
    appversionname_ = new ::std::string;
  }
  appversionname_->assign(value);
}
inline void VersionInfo::set_appversionname(const char* value) {
  set_has_appversionname();
  if (appversionname_ == &::google::protobuf::internal::kEmptyString) {
    appversionname_ = new ::std::string;
  }
  appversionname_->assign(value);
}
inline void VersionInfo::set_appversionname(const char* value, size_t size) {
  set_has_appversionname();
  if (appversionname_ == &::google::protobuf::internal::kEmptyString) {
    appversionname_ = new ::std::string;
  }
  appversionname_->assign(reinterpret_cast<const char*>(value), size);
}
inline ::std::string* VersionInfo::mutable_appversionname() {
  set_has_appversionname();
  if (appversionname_ == &::google::protobuf::internal::kEmptyString) {
    appversionname_ = new ::std::string;
  }
  return appversionname_;
}
inline ::std::string* VersionInfo::release_appversionname() {
  clear_has_appversionname();
  if (appversionname_ == &::google::protobuf::internal::kEmptyString) {
    return NULL;
  } else {
    ::std::string* temp = appversionname_;
    appversionname_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
    return temp;
  }
}
inline void VersionInfo::set_allocated_appversionname(::std::string* appversionname) {
  if (appversionname_ != &::google::protobuf::internal::kEmptyString) {
    delete appversionname_;
  }
  if (appversionname) {
    set_has_appversionname();
    appversionname_ = appversionname;
  } else {
    clear_has_appversionname();
    appversionname_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
  }
}

// -------------------------------------------------------------------

// CentralStatus

// required bool connectedHeartrate = 1;
inline bool CentralStatus::has_connectedheartrate() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
inline void CentralStatus::set_has_connectedheartrate() {
  _has_bits_[0] |= 0x00000001u;
}
inline void CentralStatus::clear_has_connectedheartrate() {
  _has_bits_[0] &= ~0x00000001u;
}
inline void CentralStatus::clear_connectedheartrate() {
  connectedheartrate_ = false;
  clear_has_connectedheartrate();
}
inline bool CentralStatus::connectedheartrate() const {
  return connectedheartrate_;
}
inline void CentralStatus::set_connectedheartrate(bool value) {
  set_has_connectedheartrate();
  connectedheartrate_ = value;
}

// required bool connectedCadence = 2;
inline bool CentralStatus::has_connectedcadence() const {
  return (_has_bits_[0] & 0x00000002u) != 0;
}
inline void CentralStatus::set_has_connectedcadence() {
  _has_bits_[0] |= 0x00000002u;
}
inline void CentralStatus::clear_has_connectedcadence() {
  _has_bits_[0] &= ~0x00000002u;
}
inline void CentralStatus::clear_connectedcadence() {
  connectedcadence_ = false;
  clear_has_connectedcadence();
}
inline bool CentralStatus::connectedcadence() const {
  return connectedcadence_;
}
inline void CentralStatus::set_connectedcadence(bool value) {
  set_has_connectedcadence();
  connectedcadence_ = value;
}

// -------------------------------------------------------------------

// MasterPayload

// required string createdDate = 3;
inline bool MasterPayload::has_createddate() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
inline void MasterPayload::set_has_createddate() {
  _has_bits_[0] |= 0x00000001u;
}
inline void MasterPayload::clear_has_createddate() {
  _has_bits_[0] &= ~0x00000001u;
}
inline void MasterPayload::clear_createddate() {
  if (createddate_ != &::google::protobuf::internal::kEmptyString) {
    createddate_->clear();
  }
  clear_has_createddate();
}
inline const ::std::string& MasterPayload::createddate() const {
  return *createddate_;
}
inline void MasterPayload::set_createddate(const ::std::string& value) {
  set_has_createddate();
  if (createddate_ == &::google::protobuf::internal::kEmptyString) {
    createddate_ = new ::std::string;
  }
  createddate_->assign(value);
}
inline void MasterPayload::set_createddate(const char* value) {
  set_has_createddate();
  if (createddate_ == &::google::protobuf::internal::kEmptyString) {
    createddate_ = new ::std::string;
  }
  createddate_->assign(value);
}
inline void MasterPayload::set_createddate(const char* value, size_t size) {
  set_has_createddate();
  if (createddate_ == &::google::protobuf::internal::kEmptyString) {
    createddate_ = new ::std::string;
  }
  createddate_->assign(reinterpret_cast<const char*>(value), size);
}
inline ::std::string* MasterPayload::mutable_createddate() {
  set_has_createddate();
  if (createddate_ == &::google::protobuf::internal::kEmptyString) {
    createddate_ = new ::std::string;
  }
  return createddate_;
}
inline ::std::string* MasterPayload::release_createddate() {
  clear_has_createddate();
  if (createddate_ == &::google::protobuf::internal::kEmptyString) {
    return NULL;
  } else {
    ::std::string* temp = createddate_;
    createddate_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
    return temp;
  }
}
inline void MasterPayload::set_allocated_createddate(::std::string* createddate) {
  if (createddate_ != &::google::protobuf::internal::kEmptyString) {
    delete createddate_;
  }
  if (createddate) {
    set_has_createddate();
    createddate_ = createddate;
  } else {
    clear_has_createddate();
    createddate_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
  }
}

// required .eaglesakura_ace.CentralStatus centralStatus = 1;
inline bool MasterPayload::has_centralstatus() const {
  return (_has_bits_[0] & 0x00000002u) != 0;
}
inline void MasterPayload::set_has_centralstatus() {
  _has_bits_[0] |= 0x00000002u;
}
inline void MasterPayload::clear_has_centralstatus() {
  _has_bits_[0] &= ~0x00000002u;
}
inline void MasterPayload::clear_centralstatus() {
  if (centralstatus_ != NULL) centralstatus_->::eaglesakura_ace::CentralStatus::Clear();
  clear_has_centralstatus();
}
inline const ::eaglesakura_ace::CentralStatus& MasterPayload::centralstatus() const {
  return centralstatus_ != NULL ? *centralstatus_ : *default_instance_->centralstatus_;
}
inline ::eaglesakura_ace::CentralStatus* MasterPayload::mutable_centralstatus() {
  set_has_centralstatus();
  if (centralstatus_ == NULL) centralstatus_ = new ::eaglesakura_ace::CentralStatus;
  return centralstatus_;
}
inline ::eaglesakura_ace::CentralStatus* MasterPayload::release_centralstatus() {
  clear_has_centralstatus();
  ::eaglesakura_ace::CentralStatus* temp = centralstatus_;
  centralstatus_ = NULL;
  return temp;
}
inline void MasterPayload::set_allocated_centralstatus(::eaglesakura_ace::CentralStatus* centralstatus) {
  delete centralstatus_;
  centralstatus_ = centralstatus;
  if (centralstatus) {
    set_has_centralstatus();
  } else {
    clear_has_centralstatus();
  }
}

// repeated .eaglesakura_ace.SensorPayload sensorPayloads = 2;
inline int MasterPayload::sensorpayloads_size() const {
  return sensorpayloads_.size();
}
inline void MasterPayload::clear_sensorpayloads() {
  sensorpayloads_.Clear();
}
inline const ::eaglesakura_ace::SensorPayload& MasterPayload::sensorpayloads(int index) const {
  return sensorpayloads_.Get(index);
}
inline ::eaglesakura_ace::SensorPayload* MasterPayload::mutable_sensorpayloads(int index) {
  return sensorpayloads_.Mutable(index);
}
inline ::eaglesakura_ace::SensorPayload* MasterPayload::add_sensorpayloads() {
  return sensorpayloads_.Add();
}
inline const ::google::protobuf::RepeatedPtrField< ::eaglesakura_ace::SensorPayload >&
MasterPayload::sensorpayloads() const {
  return sensorpayloads_;
}
inline ::google::protobuf::RepeatedPtrField< ::eaglesakura_ace::SensorPayload >*
MasterPayload::mutable_sensorpayloads() {
  return &sensorpayloads_;
}


// @@protoc_insertion_point(namespace_scope)

}  // namespace eaglesakura_ace

#ifndef SWIG
namespace google {
namespace protobuf {


}  // namespace google
}  // namespace protobuf
#endif  // SWIG

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_AcesProtocol_2eproto__INCLUDED

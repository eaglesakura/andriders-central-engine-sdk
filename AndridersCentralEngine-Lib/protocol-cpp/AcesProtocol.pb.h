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
#include "SensorProtocol.pb.h"
#include "CommandProtocol.pb.h"
#include "GeoProtocol.pb.h"
// @@protoc_insertion_point(includes)

namespace eaglesakura_ace {

// Internal implementation detail -- do not call these.
void  protobuf_AddDesc_AcesProtocol_2eproto();
void protobuf_AssignDesc_AcesProtocol_2eproto();
void protobuf_ShutdownFile_AcesProtocol_2eproto();

class VersionInfo;
class CentralStatus;
class MasterPayload;

// ===================================================================

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

  // required bool connectedTwitter = 3;
  inline bool has_connectedtwitter() const;
  inline void clear_connectedtwitter();
  static const int kConnectedTwitterFieldNumber = 3;
  inline bool connectedtwitter() const;
  inline void set_connectedtwitter(bool value);

  // @@protoc_insertion_point(class_scope:eaglesakura_ace.CentralStatus)
 private:
  inline void set_has_connectedheartrate();
  inline void clear_has_connectedheartrate();
  inline void set_has_connectedcadence();
  inline void clear_has_connectedcadence();
  inline void set_has_connectedtwitter();
  inline void clear_has_connectedtwitter();

  ::google::protobuf::UnknownFieldSet _unknown_fields_;

  bool connectedheartrate_;
  bool connectedcadence_;
  bool connectedtwitter_;

  mutable int _cached_size_;
  ::google::protobuf::uint32 _has_bits_[(3 + 31) / 32];

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

  // required string uniqueId = 4;
  inline bool has_uniqueid() const;
  inline void clear_uniqueid();
  static const int kUniqueIdFieldNumber = 4;
  inline const ::std::string& uniqueid() const;
  inline void set_uniqueid(const ::std::string& value);
  inline void set_uniqueid(const char* value);
  inline void set_uniqueid(const char* value, size_t size);
  inline ::std::string* mutable_uniqueid();
  inline ::std::string* release_uniqueid();
  inline void set_allocated_uniqueid(::std::string* uniqueid);

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

  // required string senderPackage = 7;
  inline bool has_senderpackage() const;
  inline void clear_senderpackage();
  static const int kSenderPackageFieldNumber = 7;
  inline const ::std::string& senderpackage() const;
  inline void set_senderpackage(const ::std::string& value);
  inline void set_senderpackage(const char* value);
  inline void set_senderpackage(const char* value, size_t size);
  inline ::std::string* mutable_senderpackage();
  inline ::std::string* release_senderpackage();
  inline void set_allocated_senderpackage(::std::string* senderpackage);

  // optional string targetPackage = 6;
  inline bool has_targetpackage() const;
  inline void clear_targetpackage();
  static const int kTargetPackageFieldNumber = 6;
  inline const ::std::string& targetpackage() const;
  inline void set_targetpackage(const ::std::string& value);
  inline void set_targetpackage(const char* value);
  inline void set_targetpackage(const char* value, size_t size);
  inline ::std::string* mutable_targetpackage();
  inline ::std::string* release_targetpackage();
  inline void set_allocated_targetpackage(::std::string* targetpackage);

  // optional .eaglesakura_ace.CentralStatus centralStatus = 1;
  inline bool has_centralstatus() const;
  inline void clear_centralstatus();
  static const int kCentralStatusFieldNumber = 1;
  inline const ::eaglesakura_ace::CentralStatus& centralstatus() const;
  inline ::eaglesakura_ace::CentralStatus* mutable_centralstatus();
  inline ::eaglesakura_ace::CentralStatus* release_centralstatus();
  inline void set_allocated_centralstatus(::eaglesakura_ace::CentralStatus* centralstatus);

  // optional .eaglesakura_ace.GeoStatus geoStatus = 8;
  inline bool has_geostatus() const;
  inline void clear_geostatus();
  static const int kGeoStatusFieldNumber = 8;
  inline const ::eaglesakura_ace::GeoStatus& geostatus() const;
  inline ::eaglesakura_ace::GeoStatus* mutable_geostatus();
  inline ::eaglesakura_ace::GeoStatus* release_geostatus();
  inline void set_allocated_geostatus(::eaglesakura_ace::GeoStatus* geostatus);

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

  // repeated .eaglesakura_ace.CommandPayload commandPayloads = 5;
  inline int commandpayloads_size() const;
  inline void clear_commandpayloads();
  static const int kCommandPayloadsFieldNumber = 5;
  inline const ::eaglesakura_ace::CommandPayload& commandpayloads(int index) const;
  inline ::eaglesakura_ace::CommandPayload* mutable_commandpayloads(int index);
  inline ::eaglesakura_ace::CommandPayload* add_commandpayloads();
  inline const ::google::protobuf::RepeatedPtrField< ::eaglesakura_ace::CommandPayload >&
      commandpayloads() const;
  inline ::google::protobuf::RepeatedPtrField< ::eaglesakura_ace::CommandPayload >*
      mutable_commandpayloads();

  // @@protoc_insertion_point(class_scope:eaglesakura_ace.MasterPayload)
 private:
  inline void set_has_uniqueid();
  inline void clear_has_uniqueid();
  inline void set_has_createddate();
  inline void clear_has_createddate();
  inline void set_has_senderpackage();
  inline void clear_has_senderpackage();
  inline void set_has_targetpackage();
  inline void clear_has_targetpackage();
  inline void set_has_centralstatus();
  inline void clear_has_centralstatus();
  inline void set_has_geostatus();
  inline void clear_has_geostatus();

  ::google::protobuf::UnknownFieldSet _unknown_fields_;

  ::std::string* uniqueid_;
  ::std::string* createddate_;
  ::std::string* senderpackage_;
  ::std::string* targetpackage_;
  ::eaglesakura_ace::CentralStatus* centralstatus_;
  ::eaglesakura_ace::GeoStatus* geostatus_;
  ::google::protobuf::RepeatedPtrField< ::eaglesakura_ace::SensorPayload > sensorpayloads_;
  ::google::protobuf::RepeatedPtrField< ::eaglesakura_ace::CommandPayload > commandpayloads_;

  mutable int _cached_size_;
  ::google::protobuf::uint32 _has_bits_[(8 + 31) / 32];

  friend void  protobuf_AddDesc_AcesProtocol_2eproto();
  friend void protobuf_AssignDesc_AcesProtocol_2eproto();
  friend void protobuf_ShutdownFile_AcesProtocol_2eproto();

  void InitAsDefaultInstance();
  static MasterPayload* default_instance_;
};
// ===================================================================


// ===================================================================

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

// required bool connectedTwitter = 3;
inline bool CentralStatus::has_connectedtwitter() const {
  return (_has_bits_[0] & 0x00000004u) != 0;
}
inline void CentralStatus::set_has_connectedtwitter() {
  _has_bits_[0] |= 0x00000004u;
}
inline void CentralStatus::clear_has_connectedtwitter() {
  _has_bits_[0] &= ~0x00000004u;
}
inline void CentralStatus::clear_connectedtwitter() {
  connectedtwitter_ = false;
  clear_has_connectedtwitter();
}
inline bool CentralStatus::connectedtwitter() const {
  return connectedtwitter_;
}
inline void CentralStatus::set_connectedtwitter(bool value) {
  set_has_connectedtwitter();
  connectedtwitter_ = value;
}

// -------------------------------------------------------------------

// MasterPayload

// required string uniqueId = 4;
inline bool MasterPayload::has_uniqueid() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
inline void MasterPayload::set_has_uniqueid() {
  _has_bits_[0] |= 0x00000001u;
}
inline void MasterPayload::clear_has_uniqueid() {
  _has_bits_[0] &= ~0x00000001u;
}
inline void MasterPayload::clear_uniqueid() {
  if (uniqueid_ != &::google::protobuf::internal::kEmptyString) {
    uniqueid_->clear();
  }
  clear_has_uniqueid();
}
inline const ::std::string& MasterPayload::uniqueid() const {
  return *uniqueid_;
}
inline void MasterPayload::set_uniqueid(const ::std::string& value) {
  set_has_uniqueid();
  if (uniqueid_ == &::google::protobuf::internal::kEmptyString) {
    uniqueid_ = new ::std::string;
  }
  uniqueid_->assign(value);
}
inline void MasterPayload::set_uniqueid(const char* value) {
  set_has_uniqueid();
  if (uniqueid_ == &::google::protobuf::internal::kEmptyString) {
    uniqueid_ = new ::std::string;
  }
  uniqueid_->assign(value);
}
inline void MasterPayload::set_uniqueid(const char* value, size_t size) {
  set_has_uniqueid();
  if (uniqueid_ == &::google::protobuf::internal::kEmptyString) {
    uniqueid_ = new ::std::string;
  }
  uniqueid_->assign(reinterpret_cast<const char*>(value), size);
}
inline ::std::string* MasterPayload::mutable_uniqueid() {
  set_has_uniqueid();
  if (uniqueid_ == &::google::protobuf::internal::kEmptyString) {
    uniqueid_ = new ::std::string;
  }
  return uniqueid_;
}
inline ::std::string* MasterPayload::release_uniqueid() {
  clear_has_uniqueid();
  if (uniqueid_ == &::google::protobuf::internal::kEmptyString) {
    return NULL;
  } else {
    ::std::string* temp = uniqueid_;
    uniqueid_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
    return temp;
  }
}
inline void MasterPayload::set_allocated_uniqueid(::std::string* uniqueid) {
  if (uniqueid_ != &::google::protobuf::internal::kEmptyString) {
    delete uniqueid_;
  }
  if (uniqueid) {
    set_has_uniqueid();
    uniqueid_ = uniqueid;
  } else {
    clear_has_uniqueid();
    uniqueid_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
  }
}

// required string createdDate = 3;
inline bool MasterPayload::has_createddate() const {
  return (_has_bits_[0] & 0x00000002u) != 0;
}
inline void MasterPayload::set_has_createddate() {
  _has_bits_[0] |= 0x00000002u;
}
inline void MasterPayload::clear_has_createddate() {
  _has_bits_[0] &= ~0x00000002u;
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

// required string senderPackage = 7;
inline bool MasterPayload::has_senderpackage() const {
  return (_has_bits_[0] & 0x00000004u) != 0;
}
inline void MasterPayload::set_has_senderpackage() {
  _has_bits_[0] |= 0x00000004u;
}
inline void MasterPayload::clear_has_senderpackage() {
  _has_bits_[0] &= ~0x00000004u;
}
inline void MasterPayload::clear_senderpackage() {
  if (senderpackage_ != &::google::protobuf::internal::kEmptyString) {
    senderpackage_->clear();
  }
  clear_has_senderpackage();
}
inline const ::std::string& MasterPayload::senderpackage() const {
  return *senderpackage_;
}
inline void MasterPayload::set_senderpackage(const ::std::string& value) {
  set_has_senderpackage();
  if (senderpackage_ == &::google::protobuf::internal::kEmptyString) {
    senderpackage_ = new ::std::string;
  }
  senderpackage_->assign(value);
}
inline void MasterPayload::set_senderpackage(const char* value) {
  set_has_senderpackage();
  if (senderpackage_ == &::google::protobuf::internal::kEmptyString) {
    senderpackage_ = new ::std::string;
  }
  senderpackage_->assign(value);
}
inline void MasterPayload::set_senderpackage(const char* value, size_t size) {
  set_has_senderpackage();
  if (senderpackage_ == &::google::protobuf::internal::kEmptyString) {
    senderpackage_ = new ::std::string;
  }
  senderpackage_->assign(reinterpret_cast<const char*>(value), size);
}
inline ::std::string* MasterPayload::mutable_senderpackage() {
  set_has_senderpackage();
  if (senderpackage_ == &::google::protobuf::internal::kEmptyString) {
    senderpackage_ = new ::std::string;
  }
  return senderpackage_;
}
inline ::std::string* MasterPayload::release_senderpackage() {
  clear_has_senderpackage();
  if (senderpackage_ == &::google::protobuf::internal::kEmptyString) {
    return NULL;
  } else {
    ::std::string* temp = senderpackage_;
    senderpackage_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
    return temp;
  }
}
inline void MasterPayload::set_allocated_senderpackage(::std::string* senderpackage) {
  if (senderpackage_ != &::google::protobuf::internal::kEmptyString) {
    delete senderpackage_;
  }
  if (senderpackage) {
    set_has_senderpackage();
    senderpackage_ = senderpackage;
  } else {
    clear_has_senderpackage();
    senderpackage_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
  }
}

// optional string targetPackage = 6;
inline bool MasterPayload::has_targetpackage() const {
  return (_has_bits_[0] & 0x00000008u) != 0;
}
inline void MasterPayload::set_has_targetpackage() {
  _has_bits_[0] |= 0x00000008u;
}
inline void MasterPayload::clear_has_targetpackage() {
  _has_bits_[0] &= ~0x00000008u;
}
inline void MasterPayload::clear_targetpackage() {
  if (targetpackage_ != &::google::protobuf::internal::kEmptyString) {
    targetpackage_->clear();
  }
  clear_has_targetpackage();
}
inline const ::std::string& MasterPayload::targetpackage() const {
  return *targetpackage_;
}
inline void MasterPayload::set_targetpackage(const ::std::string& value) {
  set_has_targetpackage();
  if (targetpackage_ == &::google::protobuf::internal::kEmptyString) {
    targetpackage_ = new ::std::string;
  }
  targetpackage_->assign(value);
}
inline void MasterPayload::set_targetpackage(const char* value) {
  set_has_targetpackage();
  if (targetpackage_ == &::google::protobuf::internal::kEmptyString) {
    targetpackage_ = new ::std::string;
  }
  targetpackage_->assign(value);
}
inline void MasterPayload::set_targetpackage(const char* value, size_t size) {
  set_has_targetpackage();
  if (targetpackage_ == &::google::protobuf::internal::kEmptyString) {
    targetpackage_ = new ::std::string;
  }
  targetpackage_->assign(reinterpret_cast<const char*>(value), size);
}
inline ::std::string* MasterPayload::mutable_targetpackage() {
  set_has_targetpackage();
  if (targetpackage_ == &::google::protobuf::internal::kEmptyString) {
    targetpackage_ = new ::std::string;
  }
  return targetpackage_;
}
inline ::std::string* MasterPayload::release_targetpackage() {
  clear_has_targetpackage();
  if (targetpackage_ == &::google::protobuf::internal::kEmptyString) {
    return NULL;
  } else {
    ::std::string* temp = targetpackage_;
    targetpackage_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
    return temp;
  }
}
inline void MasterPayload::set_allocated_targetpackage(::std::string* targetpackage) {
  if (targetpackage_ != &::google::protobuf::internal::kEmptyString) {
    delete targetpackage_;
  }
  if (targetpackage) {
    set_has_targetpackage();
    targetpackage_ = targetpackage;
  } else {
    clear_has_targetpackage();
    targetpackage_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
  }
}

// optional .eaglesakura_ace.CentralStatus centralStatus = 1;
inline bool MasterPayload::has_centralstatus() const {
  return (_has_bits_[0] & 0x00000010u) != 0;
}
inline void MasterPayload::set_has_centralstatus() {
  _has_bits_[0] |= 0x00000010u;
}
inline void MasterPayload::clear_has_centralstatus() {
  _has_bits_[0] &= ~0x00000010u;
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

// optional .eaglesakura_ace.GeoStatus geoStatus = 8;
inline bool MasterPayload::has_geostatus() const {
  return (_has_bits_[0] & 0x00000020u) != 0;
}
inline void MasterPayload::set_has_geostatus() {
  _has_bits_[0] |= 0x00000020u;
}
inline void MasterPayload::clear_has_geostatus() {
  _has_bits_[0] &= ~0x00000020u;
}
inline void MasterPayload::clear_geostatus() {
  if (geostatus_ != NULL) geostatus_->::eaglesakura_ace::GeoStatus::Clear();
  clear_has_geostatus();
}
inline const ::eaglesakura_ace::GeoStatus& MasterPayload::geostatus() const {
  return geostatus_ != NULL ? *geostatus_ : *default_instance_->geostatus_;
}
inline ::eaglesakura_ace::GeoStatus* MasterPayload::mutable_geostatus() {
  set_has_geostatus();
  if (geostatus_ == NULL) geostatus_ = new ::eaglesakura_ace::GeoStatus;
  return geostatus_;
}
inline ::eaglesakura_ace::GeoStatus* MasterPayload::release_geostatus() {
  clear_has_geostatus();
  ::eaglesakura_ace::GeoStatus* temp = geostatus_;
  geostatus_ = NULL;
  return temp;
}
inline void MasterPayload::set_allocated_geostatus(::eaglesakura_ace::GeoStatus* geostatus) {
  delete geostatus_;
  geostatus_ = geostatus;
  if (geostatus) {
    set_has_geostatus();
  } else {
    clear_has_geostatus();
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

// repeated .eaglesakura_ace.CommandPayload commandPayloads = 5;
inline int MasterPayload::commandpayloads_size() const {
  return commandpayloads_.size();
}
inline void MasterPayload::clear_commandpayloads() {
  commandpayloads_.Clear();
}
inline const ::eaglesakura_ace::CommandPayload& MasterPayload::commandpayloads(int index) const {
  return commandpayloads_.Get(index);
}
inline ::eaglesakura_ace::CommandPayload* MasterPayload::mutable_commandpayloads(int index) {
  return commandpayloads_.Mutable(index);
}
inline ::eaglesakura_ace::CommandPayload* MasterPayload::add_commandpayloads() {
  return commandpayloads_.Add();
}
inline const ::google::protobuf::RepeatedPtrField< ::eaglesakura_ace::CommandPayload >&
MasterPayload::commandpayloads() const {
  return commandpayloads_;
}
inline ::google::protobuf::RepeatedPtrField< ::eaglesakura_ace::CommandPayload >*
MasterPayload::mutable_commandpayloads() {
  return &commandpayloads_;
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

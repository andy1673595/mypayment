/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Using: /Users/andyhuang/Library/Android/sdk/build-tools/35.0.0/aidl -p/Users/andyhuang/Library/Android/sdk/platforms/android-36/framework.aidl -o/Users/andyhuang/AndroidStudioProjects/mypayment/client/build/generated/aidl_source_output_dir/debug/out -I/Users/andyhuang/AndroidStudioProjects/mypayment/client/src/main/aidl -I/Users/andyhuang/AndroidStudioProjects/mypayment/client/src/debug/aidl -I/Users/andyhuang/.gradle/caches/8.13/transforms/4e2ea1b68093355765e9395a7be484ff/transformed/core-1.16.0/aidl -I/Users/andyhuang/.gradle/caches/8.13/transforms/5f77eb4018f7d54121c8e9c343bb6c8e/transformed/versionedparcelable-1.1.1/aidl -d/var/folders/tr/ynwwxdvd29g3_t8xqyldj7dr0000gn/T/aidl13770798502883438926.d /Users/andyhuang/AndroidStudioProjects/mypayment/client/src/main/aidl/com/example/mypayment/IPaymentService.aidl
 */
package com.example.mypayment;
// AIDL interface definition for cross-process payment operations.
// Both the server (app) and client modules must have an identical copy of this file.
public interface IPaymentService extends android.os.IInterface
{
  /** Default implementation for IPaymentService. */
  public static class Default implements com.example.mypayment.IPaymentService
  {
    @Override public int getBalance() throws android.os.RemoteException
    {
      return 0;
    }
    @Override public boolean pay(int amount) throws android.os.RemoteException
    {
      return false;
    }
    @Override public void topUp(int amount) throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.example.mypayment.IPaymentService
  {
    /** Construct the stub at attach it to the interface. */
    @SuppressWarnings("this-escape")
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.example.mypayment.IPaymentService interface,
     * generating a proxy if needed.
     */
    public static com.example.mypayment.IPaymentService asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.example.mypayment.IPaymentService))) {
        return ((com.example.mypayment.IPaymentService)iin);
      }
      return new com.example.mypayment.IPaymentService.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      if (code >= android.os.IBinder.FIRST_CALL_TRANSACTION && code <= android.os.IBinder.LAST_CALL_TRANSACTION) {
        data.enforceInterface(descriptor);
      }
      if (code == INTERFACE_TRANSACTION) {
        reply.writeString(descriptor);
        return true;
      }
      switch (code)
      {
        case TRANSACTION_getBalance:
        {
          int _result = this.getBalance();
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_pay:
        {
          int _arg0;
          _arg0 = data.readInt();
          boolean _result = this.pay(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          break;
        }
        case TRANSACTION_topUp:
        {
          int _arg0;
          _arg0 = data.readInt();
          this.topUp(_arg0);
          reply.writeNoException();
          break;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
      return true;
    }
    private static class Proxy implements com.example.mypayment.IPaymentService
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      @Override public int getBalance() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getBalance, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public boolean pay(int amount) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(amount);
          boolean _status = mRemote.transact(Stub.TRANSACTION_pay, _data, _reply, 0);
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public void topUp(int amount) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(amount);
          boolean _status = mRemote.transact(Stub.TRANSACTION_topUp, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }
    static final int TRANSACTION_getBalance = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_pay = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_topUp = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
  }
  /** @hide */
  public static final java.lang.String DESCRIPTOR = "com.example.mypayment.IPaymentService";
  public int getBalance() throws android.os.RemoteException;
  public boolean pay(int amount) throws android.os.RemoteException;
  public void topUp(int amount) throws android.os.RemoteException;
}

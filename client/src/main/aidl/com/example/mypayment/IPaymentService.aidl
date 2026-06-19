// IPaymentService.aidl
package com.example.mypayment;

// AIDL interface definition for cross-process payment operations.
// Both the server (app) and client modules must have an identical copy of this file.
interface IPaymentService {
    int getBalance();
    boolean pay(int amount);
    void topUp(int amount);
}

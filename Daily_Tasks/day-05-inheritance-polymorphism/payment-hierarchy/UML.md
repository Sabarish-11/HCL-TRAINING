PAYMENT HIERARCHY

                 <<abstract>>
                    Payment
                      |
        +-------------+-------------+
        |             |             |
        v             v             v
   CardPayment    UpiPayment    CashPayment
        |
        | implements
        v
   <<interface>>
     Refundable


Payment
--------------------------------
- amount : double
--------------------------------
+ pay() : void
+ pay(reference : String) : void
+ pay(amount : double, reference : String) : void
+ getAmount() : double


Refundable
--------------------------------
+ refund() : void

Rebase exercise branch demonstration.
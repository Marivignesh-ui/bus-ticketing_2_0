// package com.mari.repository;

// import com.mari.domain.User;
// import com.mari.util.SessionUtil;
// import com.razorpay.Order;

// import org.hibernate.Session;

// public class OrderRepository {

//     public void save(Order order,String userId){
//         try(Session session=SessionUtil.getSession()){
//             session.beginTransaction();
//             User user=session.get(User.class, userId);
//             String orderString=order.toString();
//             com.mari.domain.Order myOrder=new com.mari.domain.Order(order.getId, order.getReceipt, order.getAmount(), order.getCreatedAt(), user);
//         }
//     }
// }

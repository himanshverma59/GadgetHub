/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.utillity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.mail.MessagingException;

/**
 *
 * @author himan
 */
public class MailMessage {
     public static void registrationSuccess(String recipientMailId, String name) throws MessagingException {
        String subject = "Registration Successfull";
        String htmlTextMessage = "" + "<html>" + "<body>"
                + "<h2 style='color:green;'>Welcome to " + AppInfo.appName + "</h2>" + "" + "Hi " + name + ","
                + "<br><br>Thanks for singing up with " + AppInfo.appName + ".<br>"
                + "We are glad that you choose us. We invite you to check out our latest collection of new electonics appliances."
                + "<br>We are providing upto 60% OFF on most of the electronic gadgets. So please visit our site and explore the collections."
                + "<br><br>Our Online electronics is growing in a larger amount these days and we are in high demand so we thanks all of you for "
                + "making us up to that level. We Deliver Product to your house with no extra delivery charges and we also have collection of most of the"
                + "branded items.<br><br>As a Welcome gift for our New Customers we are providing additional 10% OFF Upto 500 Rs for the first product purchase. "
                + "<br>To avail this offer you only have "
                + "to enter the promo code given below.<br><br><br> PROMO CODE: " + "" + AppInfo.appName.toUpperCase() + "500<br><br><br>"
                + "Have a good day!<br>" + "" + "</body>" + "</html>";
        JavaMailUtil.sendMail(recipientMailId, subject, htmlTextMessage);
    }
     public static void transactionSuccess(String recipientMailId, String name,double amount) throws MessagingException {
        String subject = "Transaction Successful – Thank You for Shopping!";  
String htmlTextMessage = "" + "<html>" + "<body>"  
    + "<h2 style='color:green;'>Payment Confirmed – Order Placed Successfully!</h2>"  
    + "Hi " + name + ","  
    + "<br><br>Great news! Your payment has been successfully processed, and your order is now confirmed. 🎉"  
    + "<br>Thank you for choosing " + AppInfo.appName + " for your shopping needs!"  
    + "<br><br><b>Order Details:</b>"  
    + "<br>📦 Your items are being packed with care."  
    + "<br>🚀 We will notify you once your order is shipped." 
         + "<br>💳 Payment Amount: <b>Rs. " + amount + "</b>"  
    + "<br><br>✨ <b>Exclusive Offer for You:</b>"  
    + "<br>As a valued customer, we are giving you an exclusive <b>Flat 10% OFF</b> on your next purchase!"  
    + "<br>Use the promo code: <b>" + AppInfo.appName.toUpperCase() + "NEXT10</b> at checkout."  
    + "<br><br>💡 *Stay updated with the latest offers and new arrivals by visiting our website!*"  
    + "<br><br>We truly appreciate your trust in us. If you have any questions, feel free to contact our support team."  
    + "<br><br>Happy Shopping! 🛍️"  
    + "<br><br>Best Regards,<br>" + AppInfo.appName + " Team"  
    + "</body>" + "</html>";  

JavaMailUtil.sendMail(recipientMailId, subject, htmlTextMessage);

    }
      public static void orderShipped(String recipientMailId, String name) throws MessagingException {
          Calendar calendar = Calendar.getInstance();
calendar.add(Calendar.DAY_OF_YEAR, 3);
Date estimatedDeliveryDate = calendar.getTime();
SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy"); // Example: Saturday, Feb 17, 2025
String deliveryDate = dateFormat.format(estimatedDeliveryDate);

        String subject = "Your Order Has Been Shipped – Get Ready!";  
String htmlTextMessage = "" + "<html>" + "<body>"  
    + "<h2 style='color:green;'>Your Order is on the Way! 🚀</h2>"  
    + "Hi " + name + ","  
    + "<br><br>Exciting news! Your order has been shipped and is on its way to your doorstep. 📦"  
    + "<br>We know you're eagerly waiting, and we assure you it's coming soon!"  
    + "<br><br><b>Order Details:</b>"  
    + "<br>🚚 Courier Partner: <b> Bluedart </b>"
    + "<br>📅 Estimated Delivery: <b>" + deliveryDate + "</b>"  
    + "<br><br>✨ <b>Track Your Order:</b>"  
    + "<br><br>💡 *Tip: Make sure someone is available to receive your package.*"  
    + "<br><br>🎁 **Special Offer Just for You!** 🎁"  
    + "<br>Enjoy <b>Flat 10% OFF</b> on your next purchase with us! Use code: <b>" + AppInfo.appName.toUpperCase() + "NEXT10</b> at checkout."  
    + "<br><br>Need Help? Our support team is always here for you!"  
    + "<br><br>Happy Shopping! 🛍️"  
    + "<br><br>Best Regards,<br>" + AppInfo.appName + " Team"  
    + "</body>" + "</html>";  

JavaMailUtil.sendMail(recipientMailId, subject, htmlTextMessage);


    }

    

}

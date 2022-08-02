# FreshOnline_Backend

FreshOnline is a simple full stack online shopping project.
This repository is the backend part of it.

# what framework/third library is used

* spring boot
* mysql for storing structural records
* redis with lua script
* mongodb for storing pictures
* spring security for method-level authorization)
* JWT for authentication

# supported features

1. user registration and login (2 types of user: CUSTOMER and ADMINISTRATOR)
2. identity authentication (using JWT inside interceptor)
3. method-level identity authorization (using spring security)
4. Customize exception handling
5. enable many customers to place orders concurrently (using redis and lua script)

6. add/delete/modify stocked goods
![](/Users/fanzekun/Desktop/Screen Shot 2022-08-01 at 8.58.14 PM.png)
7. add/delete category of stocked goods
![](/Users/fanzekun/Desktop/Screen Shot 2022-08-01 at 8.58.14 PM.png)
8. query and sort stocked goods by different filter condition 
(category, name, price, brand)
![](/Users/fanzekun/Desktop/Screen Shot 2022-08-01 at 8.22.17 PM.png)

8. add/remove goods into cart
![](/Users/fanzekun/Desktop/Screen Shot 2022-08-01 at 8.24.19 PM.png)

9. view/pay/cancer placed orders

![](/Users/fanzekun/Desktop/Screen Shot 2022-08-01 at 8.28.30 PM.png)
10. deliver paid orders (simulated by scheduled tasks)
![](/Users/fanzekun/Desktop/Screen Shot 2022-08-01 at 8.29.05 PM.png)
11. view/edit customer account information
![](/Users/fanzekun/Desktop/Screen Shot 2022-08-01 at 8.23.04 PM.png)
12. view/edit customer address
![](/Users/fanzekun/Desktop/Screen Shot 2022-08-01 at 8.23.13 PM.png)
13. view/edit customer payment method
![](/Users/fanzekun/Desktop/Screen Shot 2022-08-01 at 8.23.27 PM.png)

    
# test accounts

email: ct@qq.com, password:123, user type: CUSTOMER




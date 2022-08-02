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
5. add/delete/modify stocked goods 
6. add/delete category of stocked goods
7. query and sort stocked goods by different filter condition 
(category, name, price, brand)
8. add/remove goods into cart
9. add/remove goods into favorite list
10. enable many customers to place orders concurrently (using redis and lua script)
11. view/pay/cancer placed orders
12. deliver paid orders (simulated by scheduled tasks)
13. view/edit customer account information
14. view/edit customer address
15. view/edit customer payment method




# test accounts


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

![https://user-images.githubusercontent.com/89892494/182271682-0ed836ff-13f7-4969-8ce4-9913617aac30.png]

7. add/delete category of stocked goods

![https://user-images.githubusercontent.com/89892494/182270614-de33f46f-69d5-4e40-b3b7-2861f689ac43.png]


8. query and sort stocked goods by different filter condition 
(category, name, price, brand)
![https://user-images.githubusercontent.com/89892494/182270657-328c6c35-2770-4a5c-9685-a909b0d918d3.png]
9. add/remove goods into cart
![https://user-images.githubusercontent.com/89892494/182270647-7da22192-966c-45e1-baec-744cf113ece0.png]
10. view/pay/cancer placed orders
![https://user-images.githubusercontent.com/89892494/182270645-84987154-bede-49bf-88e5-941704e04580.png]
11. deliver paid orders (simulated by scheduled tasks)
![https://user-images.githubusercontent.com/89892494/182270640-19493d37-9090-428b-87b1-415bde9ef478.png]
12. view/edit customer account information
![https://user-images.githubusercontent.com/89892494/182270652-fc466a32-67d8-42a2-8dc8-27e6fe52aa9d.png]
13. view/edit customer address
![https://user-images.githubusercontent.com/89892494/182270650-eb70e3f9-5247-4966-9ddc-5e4f8074ea12.png]
14. view/edit customer payment method
![https://user-images.githubusercontent.com/89892494/182270648-e4eba293-3b60-4f96-a7d0-aadfa6da7be1.png]
15. rate and comment on the goods you bought

![https://user-images.githubusercontent.com/89892494/182270639-35359241-0917-429b-90d8-08b48dcc112a.png]

16. add/remove goods into favorite list
![https://user-images.githubusercontent.com/89892494/182270634-79dbe44d-ecb5-469d-b985-d7567662e1ec.png]
    
# test accounts

email: ct@qq.com, password:123, user type: CUSTOMER




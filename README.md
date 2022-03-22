.  
├── src  
│   ├── main  
│   │   ├── java   
│   │   │   └── com  
│   │   │       └── example  
│   │   │           └── freshonline  
│   │   │               ├── advice   **global exception handler config**   
│   │   │               ├── config    **Spring custom config**    
│   │   │               ├── constants  **some constants**   
│   │   │               ├── controller  **handler http requests**  
│   │   │               ├── dao  **read/write directly database**   
│   │   │               ├── dto  **data transfer object**    
│   │   │               ├── enums  **some enumerates**  
│   │   │               ├── exception  **customized exception**     
│   │   │               ├── interceptor  
│   │   │               ├── listener  
│   │   │               ├── model  **database entity class(POJO)**    
│   │   │               ├── schedule  
│   │   │               ├── service  **mediate between controller and dao**  
│   │   │               └── utils  **some common utils**  
│   │   └── resources  
│   │       ├── mapper   **mybatis mapper config files**  
│   │       ├── static  
│   │       │   └── images  **store static images**    
│   │       └── templates  
│   └── test  
│       └── java  
│           └── com  
│               └── example  
│                   └── freshonline  
│                       └── controller  **unit tests for controllers**    

winbuild.exe is for windows docker build. Run "Set-ExecutionPolicy RemoteSigned" if no authorization.
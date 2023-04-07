## Интернет магазин
#### ️✔️интегрирован при помощи RabbitMQ с сервисом доставки Delivery Service    
<a href="https://github.com/EvgenySaenko/delivery-service" target="_blank">
  <img src="https://img.shields.io/badge/DeliveryService-FF3300" height="25" alt="Delivery Service" />
</a>

---

### 🛠️ Стек технологий:

__FRONTEND:__  
![SpringBoot](https://img.shields.io/badge/AngularJS-FF0033)
![SpringBoot](https://img.shields.io/badge/Bootstrap-0000FF)
![SpringBoot](https://img.shields.io/badge/HTML5-FF3300)

__BACKEND:__  
![SpringBoot](https://img.shields.io/badge/SpringBoot-66FF00)
![SpringBoot](https://img.shields.io/badge/SpringWeb-66FF00)
![SpringBoot](https://img.shields.io/badge/SpringDataJPA-66FF00)
![SpringBoot](https://img.shields.io/badge/SpringSecurity-66FF00)
![SpringBoot](https://img.shields.io/badge/SpringAMQP-66FF00)
![SpringBoot](https://img.shields.io/badge/Hibernate-000000)
![SpringBoot](https://img.shields.io/badge/RabbitMQ-FF3300)
![SpringBoot](https://img.shields.io/badge/Flyway-0000FF)
![SpringBoot](https://img.shields.io/badge/JJWT-0000FF)
![SpringBoot](https://img.shields.io/badge/Maven-000033)
![SpringBoot](https://img.shields.io/badge/H2-000000)
![SpringBoot](https://img.shields.io/badge/PostgreSQL-003399)

---

### 🔥 Функционал:

 💠 __Регистрация__(пользователю высылается код авторизации на email со ссылкой для подтверждения email)\
 💠 __Аутентификация__(Spring Security JWT, гостевая корзина)\
 💠 __Авторизация__(ограниченный функционал и UI для разных ролей пользователя)\
 💠 __При обновлении страницы пользователь остается в системе пока не выйдет__(сохранение токена на фронте)\
 💠 __Админ панель__(добавление/удаление/редактирование товара)\
 💠 __Добавление товара в корзину:__
 - ️️✔️ гостевая корзина, если пользователь не вошел
 - ️️✔️ войдя, корзина мержится и становится личной для этого пользователя
 - ️️✔️ корзина может храниться в БД

 💠 __Реализован полноценный алгоритм покупки товара__
- ️️✔️ наполнение корзины
- ️️✔️ формирование заказа с указанием адреса доставки
- ️️✔️ После подтверждения заказа -> отправка HTML формы с указанием данных о заказе(номера заказа, списка товаров, адреса доставки ит.д)
- ️️✔️ отправка данных о заказе в сервис доставки через RabbitMQ
- ️️✔️ и многое другое.

---

<img src="https://img.shields.io/badge/DeliveryService-FF3300" height="25" alt="Delivery Service" />





  


  

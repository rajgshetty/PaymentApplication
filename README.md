# PaymentApplication
payment application crud using spring boot + mongodb + apache kafka


                                     Payments Transaction POC
                                     
Project set up guide - Technical


paymentapp-crud-mongodb-kafka-producer


Step 1: Import the code shared project's from github, one acts as kafka producer and other is for consuming kafka messages using CRUD(included only for withdraw and deposit) operations.


Step 2: Update the shared projects, right click on project ->maven->update as maven project.(resolve if you get any dependency issues)


Step 3: Install MongoDB, Set path for the same, open Mongo DB compass community, in which create folder by name payment(it's an mongo DB database name)->Account(it's an collection)


Step 4: Go though the code and understand it, explore basic crud operations first.


Step 5: Install and set up Apache kafka.


Step 6: Coming to Kafka implementation, go through the application.properties, in that the key value pair "payment.alert.topic=payment" ->been used in the kafka producer class as an input to the kafka topic.(this is to produce the messages)


Step 7: You can test the crud operations using POSTMAN api request and response for all the http verbs. you can also validate by using mongoDB compass community. paymentapp-crud-mongodb-kafka-alert


Step 8: Open https://www.twilio.com/login, create your own twilio account by providing your personal email id and mobile number. Twilio account will facilitate us to fetch the messages from the kafka producer to consumer. for this we need to do some setup while registering the accunt in application.properties file. i.e this is the conf how it looks like, keys would be same for everyone(sid,token,number), but values should be different to get messages to be delivered on your provided number.

twilio.account.sid=ACaccecd964d6335d5fa9c07d2be0fc0df 
twilio.auth.token=91a7dd23d7a06b18796b573006f16464 
twilio.trial.number=+17042439124


Step 9 : In the KafkaConsumerPaymentAlert class you need to provide the topics name which you have provided in your kafkaproducer application.properties file, that you need to provide in kafkaconsumer to read the messages coming i.e @KafkaListener(topics = "payment")


Step 10: Run the Application Launcher, first run for Producer -> then perform withdraw or delete operations, then you can run consumer application launcher class. Open command prompt go to the apache kafka folder C:\kafka_2.12-2.5.0> ran the below commands in the same order as mentioned below zookeeper-server-start.bat config\zookeeper.properties kafka-server-start.bat config\server.properties kafka-topics --bootstrap-server localhost:9092 --topic payment --create --partitions 3 --replication-factor 2 kafka-console-producer --broker-list 127.0.0.1:9092 --topic payment kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic payment once you perform these steps you will get an message to your registered mobile number.

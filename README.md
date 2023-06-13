# Obeng App - Online Bengkel - Cloud Computing
## Bangkit Capstone Project 2023

Bangkit Capstone Team ID : C23 - PS321 <br>
Here is our repository for the Bangkit 2023 Capstone project - Cloud Computing.

## DESCRIPTION
In this project, Cloud Computing is responsible for creating and handling APIs, databases and servers. With the above matters, data and information entered by users and technicians, such as names, e-mails, pictures, feedback, and others can be stored and maintained properly.

## METHOD
The API is built using node js and the express js framework.

## TOOLS
- JavaScript
- Node js
- Framework : Express js
- Firebase
- Postman
- Midtrans

## CLOUD ARCHITECTURE
TBA

## DEPLOYMENT 
TBA

## SECURITY
TBA
## DOCUMENTATION OBENG API
## Obeng Endpoint Documentation 
![RunCode](https://documenter.getpostman.com/view/26556240/2s93sdZrs1)<br>

## How to run this code
* To use this code, need to connect your app with Firebase project first
* After connect your app with Firebase project, you need to setting Firestore database
* Download this repository, and open IDE app, we use VS Code to make this APIs
* Open the folder in VSCode and then open VSCode terminal
* Type ```npm i express multer uuid-v4 firebase-admin firebase-auth path``` and hit enter
* Then type ```npm start``` to start the server
* It will run on http://localhost:5000/

![RunCode](https://github.com/nabhanyuzqi1/Bangkit-Capstone-C23-PS321/blob/cloud_computing/assets/CC%20Documentation/run_code.jpeg)
<br>

## How to use the endpoint
* To use this endpoint, need to use a serviceAccountKey.json that our team provided
* After getting the serviceAccountKey.json, cut and paste that file into Cloud Computing folder 
* Open Postman app, enter URL request bar with http://localhost:5000/api/service-requests
* Select method POST then Send the request
* If success, there will announce "status": "success" on tab below

![UseEndpoint](https://github.com/nabhanyuzqi1/Bangkit-Capstone-C23-PS321/blob/cloud_computing/assets/CC%20Documentation/run_endpointPOST_Service-request.jpeg)

* If you want to GET data users service-request, change the method to GET, then send the request
* If success, there will also announce "status": "success" on tab below

![UseEndpoint](https://github.com/nabhanyuzqi1/Bangkit-Capstone-C23-PS321/blob/cloud_computing/assets/CC%20Documentation/run_endpointGET_Service-request.jpeg)
<br>
### For complete Documentation, TBA

## REFERENCES
- TBA


# Obeng App - Online Bengkel - Cloud Computing
## Bangkit Capstone Project 2023

Bangkit Capstone Team ID : C23 - PS321 <br>
Here is our repository for the Bangkit 2023 Capstone project - Cloud Computing.

## DESCRIPTION
Cloud Computing have responsible for creating and managing APIs, databases and servers. We also provide services needed by the Mobile Development and Machine Learning divisions, so that the features we have designed in this mobile application, the data and information entered by users and technicians, such as names, emails, images, feedback, and others can be properly used, stored and maintained.

## Cloud Development Schedule
|  Task  |     Week 1     |       Week 2        |            Week 3          |           Week 4          |
| :----: | :------------: | :-----------------: | :------------------------: |:------------------------: |
| Task 1 | Research services that needed for app features   | Setup Cloud Services      | Connect APIs to Firebase and GCP  | Testing and Evaluation API  |
| Task 2 | Design ERD and Cloud Architecture for App | Build API | Deploy APIs in Cloud Run             | -             |
| Task 3 |       -         | Debugging APIs program on localhost |     -     | -             |

## TOOLS
- JavaScript
- Node js
- Framework : Express js
- Google Cloud Platform
- Firebase
- Postman
- Midtrans

## CLOUD ARCHITECTURE
![CloudArchitecture](https://github.com/nabhanyuzqi1/Bangkit-Capstone-C23-PS321/blob/cloud_computing/assets/CC%20Documentation/CloudArchitecture.png)

## API Description
The API is built using node js and express js as our API framework, we use Firebase Authentication for registration and login functions, and also use Firestore Database to store user and technician data and utilize Google Cloud Storage to store images KTP and Profile. We deploy and implement this API and Machine Learning model with cloud run.

## SECURITY
To access our APIs which stores data and images in our databases, we keep serviceAccountKey.json that  authenticate and authorize access to Google Cloud Platform (GCP) services through Firebase only for admins.

## DOCUMENTATION OBENG API
### Obeng Endpoint Documentation 
[Obeng Endpoint Documentation](https://documenter.getpostman.com/view/26556240/2s93sdZrs1)

### Midtrans API
[Midtrans API Documentation](https://docs.midtrans.com/)
We use this API to facilitate payment method, so it doesn't increase the system load and more efficient in transactions activities. This API is also free, so we can save more budget.

### Google Maps Platform API
[Google Maps Platform API Documentation](https://developers.google.com/maps/documentation)
We use this API to facilitate location features, we trust this API because it is handled directly by Google Cloud Platform, so that the user's location and whereabouts are securely maintained.

## How to run this code
* To use this code, need to connect your app with Firebase project first
* After connect your app with Firebase project, you need to setting Firestore database
* Download this repository, and open IDE app, we use VS Code to make this APIs
* Open the folder in VSCode and then open VSCode terminal
* Download the "package.json", thentype ```npm i``` and hit enter to intall all dependencies
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


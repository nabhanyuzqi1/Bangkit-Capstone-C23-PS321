# Obeng App - Online Bengkel - Machine Learning	
## Bangkit Capstone Project 2023

Bangkit Capstone Team ID : C23 - PS321 <br>
Here is our repository for the Bangkit 2023 Capstone project - Machine Learning.

## DESCRIPTION
The Machine Learning model can be used to provide information about a content-based filtering approach for its recommendation system. This involves analyzing the characteristics of technicians and their skills to identify which technicians are most suitable for a particular type of repair job, providing personalized and accurate recommendations for users.

## METHOD
Content-based Recommendation Systems with Neural Networks

## TOOLS
- Python
- TensorFlow
- NumPy
- Pandas
- Matplotlib
- Google Colab

## DATASETS 
We made the dataset from scratch by specifying the variables manually in Excel. The variables are user id, technicians id, repair needed, repair covered, rating with explanation as following : 

- user_id: sequence id number from 1 to 100
- technicians_id: random id number from 1 to 20
- needed: the type of repair that user needed to fix
- covered: the type of repair that technicians that experts in (ban, oli, mesin, bodi, interior)
- rating: rating of technicians from user that already use the service (1 to 5) 

[Latest Dataset](https://github.com/nabhanyuzqi1/Bangkit-Capstone-C23-PS321/blob/machine_learning/Dataset/dataset_6.csv)

## HOW TO RECOMMEND
The Machine Learning model can recommend the user with the best technicians that match with the type of user that user needs by entering inputs such as:
- Ban	
- Oli	
- Mesin	
- Bodi	
- Interior	

## DEPLOYMENT 
We use TensorFlow Lite to deploy a trained Machine Learning model on Android.

const admin = require('firebase-admin');
const tf = require('@tensorflow/tfjs-node');
const pd = require('pandas-js');
const Papa = require('papaparse');
const fs = require('fs');

admin.initializeApp();

async function loadTechnicianData() {
    try {
      const bucketName = 'loginsignup-auth-dc6a9.appspot.com';
      const filename = 'technicians_data.csv';
  
      // Download the file from the storage bucket
      const [file] = await admin.storage().bucket(bucketName).file(filename).download();
  
      // Read the CSV file and create a DataFrame
      const csvData = Papa.parse(file.toString(), { header: true }).data;
      const techData = new pd.DataFrame(csvData);
  
      // Print or log the CSV data
      //printCSVData(csvData);
  
      return techData;
    } catch (error) {
      console.error('Error loading technician data:', error);
      throw new Error('Error loading technician data');
    }
  }

// Define the custom metric function
function meanAveragePrecision(yTrue, yPred) {
  // Extract the relevant information from yTrue and yPred
  const user_id = yTrue.get('user_id').values;
  const needed = yTrue.iloc({ columns: ['needed_ban', 'needed_mesin', 'needed_bodi', 'needed_interior', 'needed_oli'] }).values;
  const technicians_id = yPred.get('technicians_id').values;
  const rating = yPred.get('rating').values;
  const covered = yPred.iloc({ columns: ['covered_mesin', 'covered_ban', 'covered_bodi', 'covered_interior', 'covered_oli'] }).values;

  // Compute the Average Precision (AP) for each user
  const apValues = [];
  for (let i = 0; i < user_id.length; i++) {
    // Get the relevant items for the user from yTrue
    const relevantItems = needed[i].reduce((acc, val, index) => {
      if (val === 1) acc.push(index);
      return acc;
    }, []);

    // Get the predicted items for the user from yPred
    const predictedItems = covered[i].reduce((acc, val, index) => {
      if (val === 1) acc.push(index);
      return acc;
    }, []);

    // Compute the precision at each position
    const precision = [];
    for (let j = 0; j < predictedItems.length; j++) {
      if (relevantItems.includes(predictedItems[j])) {
        precision.push(predictedItems.slice(0, j + 1).filter(item => item === predictedItems[j]).length / (j + 1));
      }
    }

    // Compute the Average Precision (AP) for the user
    const ap = precision.length > 0 ? precision.reduce((acc, val) => acc + val, 0) / precision.length : 0.0;
    apValues.push(ap);
  }

  // Compute the Mean Average Precision (MAP) across all users
  const mapValue = apValues.reduce((acc, val) => acc + val, 0) / apValues.length;

  return mapValue;
}

// Register the custom metric function
tf.metrics.meanAveragePrecision = meanAveragePrecision;

async function loadModel() {
    try {
      // Load the model architecture from Firebase Storage
      const bucketName = 'loginsignup-auth-dc6a9.appspot.com';
      const modelArchitectureFilename = 'model.json';
  
      // Download the model architecture file from the storage bucket
      const [modelArchitectureFile] = await admin.storage().bucket(bucketName).file(modelArchitectureFilename).download();
  
      // Parse the model architecture JSON
      const modelConfig = JSON.parse(modelArchitectureFile.toString());
  
      // Define the weight filenames
      const weightFilenames = ['weights1.bin', 'weights2.bin', 'weights3.bin'];
  
      // Load each weight file and assign it to the corresponding layer
      for (let i = 0; i < weightFilenames.length; i++) {
        const weightFilename = weightFilenames[i];
  
        // Download the weight file from the storage bucket
        const [weightFile] = await admin.storage().bucket(bucketName).file(weightFilename).download();
  
        // Decode the weight data from base64
        const weightData = Buffer.from(weightFile.toString(), 'base64');
  
        // Assign the weight data to the corresponding layer
        modelConfig.weightsManifest[0].weights[i].data = weightData;
      }
  
      // Load the model with architecture and weights
      const model = await tf.loadLayersModel(tf.io.fromMemory(modelConfig));
  
      return model;
    } catch (error) {
      console.error('Error loading model:', error);
      throw new Error('Error loading model');
    }
  }
  

function printCSVData(data) {
    // Loop through the data and log each row
    data.forEach(row => {
      console.log(row);
    });
  }



  async function modelRequest(req, res) {
    const { user_id, needed_ban, needed_mesin, needed_bodi, needed_interior, needed_oli } = req.body;
    console.log(user_id, needed_ban, needed_bodi)
    try {
      // Load the technician data
      const techData = await loadTechnicianData();
  
      // Load the model
      const model = await loadModel();
  
      // Parse the input values as numbers
      const parsedUserId = parseInt(user_id);
      const parsedNeededBan = parseInt(needed_ban);
      const parsedNeededMesin = parseInt(needed_mesin);
      const parsedNeededBodi = parseInt(needed_bodi);
      const parsedNeededInterior = parseInt(needed_interior);
      const parsedNeededOli = parseInt(needed_oli);
  
      // Encode the user's input
      const userFeatures = [
        [parsedUserId, parsedNeededBan, parsedNeededMesin, parsedNeededBodi, parsedNeededInterior, parsedNeededOli]
      ];
  
      // Convert the user's encoded features to tf.tensor2d
      const userFeaturesFloat32 = tf.tensor(userFeatures, [1, 6]);
  
      // Make predictions
      const predictions = await model.predict(userFeaturesFloat32);
      const tensorValues = predictions.arraySync();
  
      console.log(tensorValues);
      const predictedTechniciansId = Math.round(predictions.arraySync()[0][0]);
      console.log(predictedTechniciansId)
      const predictedRating = Math.round(predictions.arraySync()[0][1]);
      console.log(predictedRating)
      // Get the covered columns
      const coveredColumns = ['covered_mesin', 'covered_ban', 'covered_bodi', 'covered_interior', 'covered_oli'];
  
      // Convert technician data to an array
      const techDataArray = techData.values;
  
      // Find the recommended technicians based on the user's needs
      const recommendedTechnicians = techDataArray
        .filter(row =>
          row[2] === parsedNeededMesin &&
          row[1] === parsedNeededBan &&
          row[3] === parsedNeededBodi &&
          row[4] === parsedNeededInterior &&
          row[5] === parsedNeededOli
        )
        .map(row => parseInt(row[0]));
  
      // Prepare the response payload
      const responsePayload = {
        'Predicted Technicians ID': predictedTechniciansId,
        'Predicted Rating': predictedRating,
        'Recommended Technicians ID': recommendedTechnicians
      };
  
      res.status(200).json(responsePayload);
    } catch (error) {
      console.error('Error in modelRequest:', error);
      res.status(500).send('Error in modelRequest');
    }
  }
  
  module.exports = {
    modelRequest
  };

function printFilteredData(data) {
    // Loop through the data and log each row
    data.forEach(row => {
      console.log(row);
      console.log("Filtered Data:");
    });
  }
  

module.exports = {
  modelRequest
};
### ReadMe file for Smart Home Gesture Recognition Assignment

#### Below are the contents of the project:  

1) The main.py file which is the driver code that assembles all the logic and gives the end result

2) The handshape_feature_extractor class which has the given as part of the source code, which gets the features in the form of vectors for comparision

3) The cnn_model that is used for training the model (given as part of source code)

4) The Results.csv file that gets generated which contains the predicted labels.

5) The traindata videos that are used as part of training the model (Zipped)

6) A report with the details of the solution.

#### Quick Description:  
Upon running the main.py file, it extracts the features of the training data, which gets added to a csv file that gets generated. Similarly, a csv file gets generated with feature details of the test videos. These values are compared with all the values from training csv using cosine similarity, which is the output predicted.

#### How to run locally:
1) Make sure you have the right environment setUp that satisfies all the packages (Suggested python version: 3.7)
2) Packages required: tensorflow, opencv-python, numpy
3) Once the configuration is set, run the main.py file, which generates the 'Results.csv' file in the source code directory
